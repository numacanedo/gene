package com.resolve.qa.framework.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

//jersey and javax.ws.rs libraries with package name masked via the tool, JarJar
import com.resolve.rsqa.ws.rs.client.Entity;
import com.resolve.rsqa.ws.rs.core.Cookie;
import com.resolve.rsqa.ws.rs.core.Form;
import com.resolve.rsqa.ws.rs.core.MediaType;
import com.resolve.rsqa.ws.rs.core.MultivaluedMap;
import com.resolve.rsqa.ws.rs.core.Response;


import com.resolve.qa.framework.common.HTTP_METHODS;

/**
 * RsqaWebClient - used for testing REST web services.  Created to reduce the
 * complexity and tedium of building web client use cases from scratch.  Masks much of
 * the setup targets, urls, forms, etc. via wrapper classes.  Also adds descriptive methods
 * to directly incorporate data-driven tests via textual representation of the HTTP method calls
 * in a data-driver file.
 * 
 *
 */
public class RsqaWebClient
{
    /*******************************************************************************
     * Constants                                                                   *
     *******************************************************************************/
        private static final String DEFAULT_CONTENT_RESPONSE_TYPE = MediaType.TEXT_PLAIN;
        private static final String DEFAULT_CONTENT_REQUEST_TYPE = MediaType.APPLICATION_FORM_URLENCODED;
        private static final String AuthCookieName = "RSTOKEN";
            
    /***************************************************************
     *Private Members                                              *
     ***************************************************************/    
        /**
         * webTargetMap - ties a particular WebTarget to a page name for lookup.
         * Right now we need these to be unique, if there are collisions, we'll overwrite the
         * existing itme in the map.  This allows testers to provide a page name and have that pertain to a given
         * WebTarget(url) object.
         * HashMap<String,WebTarget>
         */
        private Map<String,JerseyWebTarget> webTargetMap = new HashMap<String,JerseyWebTarget>();
        
        /**
         * contentRequestType - used for setting the type of object data we're serializing in our
         * requests.  Default is url-encoding.
         */
        private String contentRequestType = "";
        
        /**
         * contentResponseType - used for setting the type of content we expect to be returned
         * options are text/html, application/xml, application/json, or response 
         */
        private String contentResponseType = "";
          
        /**
         * authenticated - boolean value used to track whether or not this client is 
         * logged in to the resolve instance pointed to by the base url.
         */
        private boolean authenticated = false;//we assume that we are starting out non-authed 
        
        /**
         * authCookie - this holds the value of the authentication cookie if we have truly been
         * authenticated.  We can pass this for our subsequent requests 
         */
        private Cookie authCookie = null;
        
        /**
         * statusCode - We have the latest response code in this variable.
         */
        private Integer statusCode;
       
        
        /**
         * username - our login username for authenticating to resolve
         */
        private String username = "";
        
        /**
         * password - our login password for authenticating to resolve
         */
        private String password = "";
        
        /**
         * WebTarget - used to set our request target (url for our request) 
         */
        private JerseyWebTarget target = null;
        
        /**
         * paramsList - holds a list of params for adding to a request target
         */
        private MultivaluedMap<String,Object> paramsList = null;
        
        /**
         * url - this is the URL that we use to direct our HTTP requests
         */
        private String url;
        
        /**
         * client - this is the underlying Jersey Client object from which our HTTP 
         * requests originate.
         */
        private JerseyClient client;
        
        /**
         * responseObject - we will store all responses in this object in order to
         * centralize the type of information we wish to extract.  Will add a helper method
         * and wrappers to extract the requisite data types
         */
        private Response responseObject;
        
        /**
         * form - object level form used to store information to be passed to requests
         * 
         */
        private Form form;

        private ClientConfig initialConfig;
        
        /**
         * responseContentString - this is the data from our response page
         */
        private String responseContentString = "";
        
        
        private String jsonPayloadString = "";
        /**
         * responseHeaderMap - this is the HashMap we use to store our headers
         */
        private HashMap<String,Object> responseHeaderMap = null;
        private Date lastDateModified;
        private int responseLength;
        private Set<String> allowedMethods;
        
    /***************************************************************
     *Getters and Setters                                          *
     ***************************************************************/
        
        public void setJsonPayloadString(String json) {
            jsonPayloadString=json;
        }
        
        public String getJsonPayloadString() {
            return this.jsonPayloadString;
        }
        
        /**
         * getResponseContentString - Returns a string from the actual response content
         * @param - empty
         * @returns - The String representing the content in the current response
         */
        public String getResponseContentString()
        {
            return this.responseContentString;
        }
        
        /**
         * getResponseStatusCode - returns an integer representing the status code of our
         * HTTP response
         * @param - empty
         * @returns - the int http status code
         */
        public int getResponseStatusCode()
        {      
            return this.statusCode;
        }
        
        /**
         * getHeaderString - returns the string representing the specific header from our list
         * @param name - the actual ID for the header we're looking up
         * @returns - String representation of the HTTP response header
         */
        public Object getHeaderString(String name)
        {
            return this.responseHeaderMap.get(name);
        }
        
       
        /**
         * getDateModified - returns the response's last modified date
         * 
         * @returns - a Date object
         */
        public Date getDateModified()
        {
            return this.lastDateModified;
        }
        
        /**
         * getAllHeaders - returns the response's full set of headers
         * @return - returns a HashMap instead of the multivaluedMap since it's more conventional
         */
        public HashMap<String,Object> getAllHeaders()
        {
            
            MultivaluedMap<String,Object> headerMap = responseObject.getHeaders();
            HashMap<String,Object> returnMap = new HashMap<String,Object>();
            for (String key:headerMap.keySet())
            {
                //just transfer from our multivaluedmap to a more conventional HashMap
                returnMap.put(key, headerMap.getFirst(key));
            }
            return returnMap;//return our HashMap of headers
        }    
        
        /**
         * setBaseUrl - gives us the main url for this Web Client and web target.
         * This will be the point from which all of our paths will be added.
         * @param url - string that represents the url for the base of this client
         */
         public void setBaseUrl(String url)
         {
             target = client.target(url);
         }
         
        /**
         * getWebTarget - gets the current WebTarget so that we can have multiple WebTargets to whic
         * we can add paths.
         * @return WebTarget represented the target currently held by this client 
         */
         public JerseyWebTarget getWebTarget()
         {
             return target;
         }
         
        /**
         * getLoginStatus - returns whether or not we're logged into the Resolve instance
         * @return - boolean status of our logged in state (true/false)
         */
        public boolean isAuthenticated()
        {
            return authenticated;
        }
        
        
        /**
         * setReturnType - make sure that we're checking for a particular return type:
         *   application/xml, application/json, text/html, etc.
         * default is text/html
         * @param type - String value storing our return type based on our Enum
         */    
         public void setReturnType(String type)
         {
             //could add some error checking here, but leave it up to the tester for now
             this.contentResponseType = type;
         }
         
         /**
          * setRequestType - gives us the ability to choose our return types:
          * the constants MediaType.APPLICATION_JSON_TYPE and XML_REQUEST can be used
          * @param type - String value representing our request type values (ie. 
          * DEFAULT_CONTENT_REQUEST_TYPE, MediaType.APPLICATION_JSON_TYPE, XML_REQUEST, "text/plain", et al.).
          */
         public void setRequestType(String type)
         {
             contentRequestType = type;
         }
             
        /**
         * getLastHeader - the headers from our last request
         * @return - MultivaluedMap<String,Object>
         */
        public MultivaluedMap<String,Object> getLastHeader()
        {
            System.out.println("getLastHeader - is not yet implemented");
            return null;
        }
        
        /**
         * getAllParams - the list of parameters that we intend to pass
         * via our request
         * @return - MultivaluedMap<String,Object>
         */
        public MultivaluedMap<String,Object> getAllParams()
        {
            return paramsList;
        }
        
        /**
         * setWebTarget - pass an existing WebTarget to our client held
         * WebTarget (target).
         * @param wTarget
         */
        public void setWebTarget(JerseyWebTarget wTarget)
        {
            target = wTarget;
        }    
    /**************************************************************************************************************
     * Constructors                                                                                               *
     **************************************************************************************************************/
        /*
         * RSQA_WebTester - Constructor for url, username and password entries
         * @param url - String value for a full url to which we should redirect
         * @param username - Non-default value for username if we don't want to use "admin"
         * @param password - Non-default value for password if we don't want to use "resolve"
         */
        public RsqaWebClient(String url, String username,String password)
        {
            //this.url = url;
            this.username = username;
            this.password = password;
            initialConfig();
            JerseyClientBuilder builder = new JerseyClientBuilder();
            client = builder.withConfig(initialConfig).build();       // client.getConfiguration().getClasses().add(MultiPartWriter.class);        
            buildWebTarget(url);
            form = new Form();
        }
        
        /*
         * RSQA_WebTester - Constructor assuming username/password ("admin"/"resolve")
         * @param url - String value for a full url to which we should redirect after login
         */    
        public RsqaWebClient(String url)
        {
            this.url = url;
            initialConfig();
            JerseyClientBuilder builder = new JerseyClientBuilder();
            client = builder.withConfig(initialConfig).build();
            buildWebTarget(this.url);
            form = new Form();
        }
        
        /**
         * Default Constructor - assumes "localhost:8080" and admin/resolve - login
         */
        public RsqaWebClient()
        {
            initialConfig();
            JerseyClientBuilder builder = new JerseyClientBuilder();
            client = builder.withConfig(initialConfig).register(MultiPartFeature.class).build();
            buildWebTarget();
            form = new Form();
        }    
        /**
         * getAllowedMethods - returns a set of methods supported by this response
         */
        public Set<String> getAllowedMethods()
        {
            return this.allowedMethods;
        }
        
        /**
         * getResponseLength - returns the length of the response
         */
        public int getResponseLength()
        {
            return this.responseLength;
        }
    /**************************************************************************************************************
     * Public Methods                                                                                             *
     * ************************************************************************************************************/
     
        /**
         * httpMethod - used for descriptive calls to our methods.  Allows the user to declare a method via data driven
         * mechanism.  Calls the correct method in this class to correctly drive a test from a String based table.
         * Examples of data inputs:
         * <ul>
         * <li>GET with no additional params - "GET", , - 2nd field would indicate no further details to pass
         * <li>GET with url queryparams - GET,QUERY,username=admin,password=resolve - do a get with QueryParams = ?username=admin&password=resolve
         * <li>POST with form params - POST,FORM,sysId,39392fa39ad
         * <li>POST with a passed Object - POST,FORM,OBJECT,*obj*java.io.File*obj*,<filepath> - create a fileobject and pass it directly
         * </ul>
         * We use this to support the sending of no parameter web client calls.  So any query params, or form parameters need to be taken care of prior
         * to using this method call.  Best used for "list" controller method calls.
         * usage: httpMethod("POST") - post with whatever is configured
         * @param methodName - String value to represent HTTP Methods: GET, POST, PUT, DELETE or HEAD
         * @param pageUrl - the url of the page to which we are sending the request
         * @throws NoSuchMethodException 
         */
        public void httpMethod(String method, String pageUrl) throws NoSuchMethodException
        {
            method=method.toUpperCase();
            switch (HTTP_METHODS.valueOf(method))
            {
                case GET:
                    get(pageUrl); 
                    break;
                case POST:
                    post(pageUrl);
                    break;
                case DELETE:
                    delete(pageUrl);  
                    break;
                case HEAD:
                    head(pageUrl);
                    break;
                case PUT:
                    put(pageUrl);
                    break;
                default:
                    throw new NoSuchMethodException("You've entered an HTTP method not supported with httpMethod(param)");         
            }
        }
        
        /**
         * httpMethod - used for descriptive calls to our methods.  Allows the user to declare a method via data driven
         * mechanism.  Calls the correct method in this class to correctly drive a test from a String based table.
         * We use this when we want to send a command that supports passing an object to our request.  This is great for
         * passing serialized object information to a POST or PUT.  Anything else will throw an exception.
         * Examples of data inputs:
         * <ul>
         * <li>POST with a passed Object - POST,OBJECT,*obj*java.io.File*obj*,<filepath> - create a fileobject and pass it directly
         * <li>PUT with a passed Object - PUT,OBJECT,*obj*<object's fully qualfied class name>*obj*, params for object (if needed to construct)
         * </ul>
         * usage: httpMethod(MethodName, url, Object) - if we're using queryparams, form or object
         * @param methodName - String value to represent HTTP Methods: GET, POST, PUT, DELETE or HEAD
         * @param pageUrl - the url of the page to which you're submitting request
         * @param formObj - list with one or more items item 0 - blank or "FORM", "QUERY", "OBJECT",
         * @throws NoSuchMethodException - sort of forcing this one, but we'll use for now, if the method selected doesn't match the list
         * of valid HTTP methods
         * items 1 - N, items being added 
         */    
        public void httpMethod(String method, String pageUrl, Object obj) throws NoSuchMethodException
        {
            method=method.toUpperCase();
            switch(HTTP_METHODS.valueOf(method))
            {
                case POST:
                    post(pageUrl,obj);
                    break;
                case PUT:
                    put(pageUrl,obj);
                    break;
                default:
                    throw new NoSuchMethodException("You've entered an HTTP method not supported with httpMethod(param)");                        
            }
            
        }
        
        /**
         * httpMethod - used for descriptive calls to our methods.  Allows the user to declare a method via data driven
         * mechanism.  Calls the correct method in this class to correctly drive a test from a String based table.
         * We use this when we want to send a command that supports passing an object to our request.  This is great for
         * passing serialized object information to a POST or PUT.  Anything else will throw an exception.
         * Examples of data inputs:
         * <ul>
         * <li>POST with a passed Object - POST,OBJECT,*obj*java.io.File*obj*,<filepath> - create a fileobject and pass it directly
         * <li>PUT with a passed Object - PUT,OBJECT,*obj*<object's fully qualfied class name>*obj*, params for object (if needed to construct)
         * </ul>
         * usage: httpMethod(MethodName, Object, {kvp1, kvp2, kvp3...kvpN) - if we're using queryparams, form or object
         * @param methodName - String value to represent HTTP Methods: GET, POST, PUT, DELETE or HEAD
         * @param pageUrl - the url of the page to which we are sending the request
         * @param obj - our object we'd like to pass along with our list of form data
         * @param paramList - a string array containing a set of key value pairs to be passed to the form.  pairs are stored as a 
         * single string with a pipe character as the delimiter so "key|value" is the correct format.
         * @throws Exception 
         */
        public void httpMethod(String method, String pageUrl, Object obj, String ...paramList) throws Exception
        {
            method=method.toUpperCase();
            String [] kvp = null;//list of kvps from our test-data list
            if (paramList == null)
            {
                System.out.println("empty param list passed to command expecting data...throwing exception");
                throw new Exception("Cannot pass a null paramList to this method.");
            }
            
            for (String s:paramList)
            {
                kvp = s.split("|");
                this.addParamToForm(kvp[0], kvp[1]);
            }
            
            switch(HTTP_METHODS.valueOf(method))
            {
                case POST:
                    post(pageUrl,obj);
                    break;
                case PUT:
                    put(pageUrl,obj);
                    break;
                default:
                    throw new NoSuchMethodException("You've entered an HTTP method not supported with httpMethod(param)");                                   
            }        
        }

        /**
         * httpMethod - used for descriptive calls to our methods.  Allows the user to declare a method via data driven
         * mechanism.  Calls the correct method in this class to correctly drive a test from a String based table.
         * We use this when we want to send a command that supports passing an object to our request.  This is great for
         * passing serialized object information to a POST or PUT.  Anything else will throw an exception.
         * Examples of data inputs:
         * <ul>
         * <li>POST with a passed Object - POST,OBJECT,*obj*java.io.File*obj*,<filepath> - create a fileobject and pass it directly
         * <li>PUT with a passed Object - PUT,OBJECT,*obj*<object's fully qualfied class name>*obj*, params for object (if needed to construct)
         * </ul>
         * usage: httpMethod(MethodName, {kvp1, kvp2, kvp3...kvpN) - if we're using queryparams, form or object
         * @param methodName - String value to represent HTTP Methods: GET, POST, PUT, DELETE or HEAD
         * @param pageUrl - the url of the page to which we are sending the request
         * @param obj - our object we'd like to pass along with our list of form data
         * @param paramList - a string array containing a set of key value pairs to be passed to the form/querylist.
         * pairs are stored as a single string with a pipe character as the delimiter so "key|value" is the correct format.
         * @throws Exception 
         */
    /*    public void httpMethod(String method, String pageUrl, String ...paramList) throws Exception
        {
            method=method.toUpperCase();
            String [] kvp = null;//list of kvps from our test-data list
            if (paramList == null)
            {
                System.out.println("empty param list passed to command expecting data...throwing exception");
                throw new Exception("Cannot pass a null paramList to this method.");
            }
            for (String s:paramList)
            {
                kvp = s.split("|");
                if (HTTP_METHODS.valueOf(method).equals(HTTP_METHODS.POST)||(HTTP_METHODS.valueOf(method).equals(HTTP_METHODS.PUT)))
                {
                    this.addParamToForm(kvp[0], kvp[1]);
                }
                else if(HTTP_METHODS.valueOf(method).equals(HTTP_METHODS.GET))
                {
                    this.target.queryParam(kvp[0], kvp[1]);
                }
            }
            switch(HTTP_METHODS.valueOf(method))
            {
                case POST:
                    post(pageUrl);
                    break;
                case PUT:
                    put(pageUrl);
                    break;
                case GET:
                    get(pageUrl);
                    break;
                default:
                    throw new NoSuchMethodException("You've entered an HTTP method not supported with httpMethod(param)");                                   
            }        
        }*/
        
        public void httpMethod(HTTP_METHODS method, String pageUrl, Map<String, String> params) throws Exception
        {
            switch(method)
            {
                case POST:
                    post(pageUrl);
                    break;
                case PUT:
                    put(pageUrl);
                    break;
                case GET:
                    get(pageUrl, params);
                    break;
                default:
                    throw new NoSuchMethodException("You've entered an HTTP method not supported with httpMethod(param)");                                   
            }        
        }
        
        /**
         * headPage(page) - gets the header information for the page specified in our param
         * @param - page mapping to the correct page url from which we are attempting to retrieve the header.
         * @returns - Response object containing the header information
         */
        public void headPage(String page)
        {
            if (!webTargetMap.containsKey(page))
            {
                System.out.println("Looking for a page which hasn't yet been mapped, ("+page+")...returning null.");
                responseObject=null;
                return;
            }
            //Call get the webtarget mapped to this page from the map and assign it to the object web target
            target = webTargetMap.get(page);
            //Call the no parameter version of the post command and return the result
            head();        
        }
        
        /**
         * head() - get the header information only for the current webtarget
         * great for doing a pull of the header info without having to grab the entire payload of
         * a post or get method.
         * 
         */
        public void head()
        {
            if (isAuthenticated())
            {
                target.request().cookie(authCookie).head();
                this.setAllResponseObjects();
            }
            else
            {
                target.request().head();
                this.setAllResponseObjects();
            }
        }

        /**
         * head(String url) - get the header information only for the current webtarget
         * great for doing a pull of the header info without having to grab the entire payload of
         * a post or get method.
         * @parm url - a string representing the url we wish to target for this Head command
         * @returns Response object representing the header information
         */
        public void head(String url)
        {
            this.setTargetUri(url);
            JerseyWebTarget t = this.getWebTarget();
            if (isAuthenticated())
            {
                t.request().cookie(authCookie).head();
            }
            else
            {
                t.request().head();
            }
            this.setAllResponseObjects();
        }    
        
        /**
         * loginClient - used to authenticate our client and grab our RSTOKEN cookie
         * This method checks to see if we're currently logged in, if we are then we will skip.
         * If we aren't logged in, then we'll go through the process of setting follow redirects to false, 
         * going to the target with the provided authentication information.
         */
        public void loginClient()
        {
            if (isAuthenticated()==true)
            {
                System.out.println("We are already authenticated and not in force login mode.  Exiting...");
                return;
            }
            loginClientForce();
        }
        
        /**
         * logoutClient
         * This is a cheat, as it really calls the logout in the AuthController
         */
        public void logoutClient(String baseUrl)
        {
            String host = this.target.getUri().getHost();
            String url = "";
            int port = this.target.getUri().getPort();
            if (port < 0)
            {
                url = "http://"+host+"/resolve/service/logout";
            }
            else
            {
                url = "http://"+host+":"+port+"/resolve/service/logout";
            }
            get(url);
            System.out.println("Client logged-out");
        }
        
        /**
         * loginClient - used to authenticate our client and grab our RSTOKEN cookie
         * This method checks to see if we're currently logged in, if we are then we will skip.
         * If we aren't logged in, then we'll go through the process of setting follow redirects to false, 
         * going to the target with the provided authentication information.
         * @param username - username that we send that ignores the object field username
         * @param password - password that we send to authenticate, overrides the object field password
         */
        public void loginClient(String username, String password)
        {
            if (isAuthenticated()==true)
            {
                System.out.println("We are already authenticated and not in force login mode.  Exiting...");
                return;
            }
            loginClientForce(username,password);
        }
        
        /**
         * loginClientForce - used to authenticate our client and grab our RSTOKEN cookie
         * This method checks to see if we're currently logged in, if we are then we will skip if force = false.
         * Otherwise, we'll login the existing object level username and password.
         * If we aren't logged in, then we'll go through the process of setting follow redirects to false, 
         * going to the target with the provided authentication information.
         */
        public void loginClientForce()
        {
            //make sure that we are able to stop on redirects and get our cookies
            target.property(ClientProperties.FOLLOW_REDIRECTS, false);
            
            //response used to store headers and cookies
            JerseyWebTarget t = loginTarget();
            form.param("username", this.username);
            form.param("password", this.password);
            responseObject = t.request(MediaType.APPLICATION_OCTET_STREAM_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
            setLoginAuthAndHeaders(responseObject);      
            responseObject = t.request(MediaType.APPLICATION_OCTET_STREAM_TYPE).cookie(authCookie).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
            
            
        }
        
        /**
         * loginClientForce - used to authenticate our client and grab our RSTOKEN cookie
         * This method checks to see if we're currently logged in, if we are then we will skip if force = false;
         * otherwise we'll login with the information passed.
         * If we aren't logged in, then we'll go through the process of setting follow redirects to false, 
         * going to the target with the provided authentication information.
         * @param username - username that we send that ignores the object field username
         * @param password - password that we send to authenticate, overrides the object field password
         */
        public void loginClientForce(String username, String password)
        {
            this.username=username;
            this.password=password;
            loginClientForce();

        }

        /**
         * addPath - allows the user to utilize the ability to add paths to
         * a url for our WebTarget.  This is just a wrapper for the method in the
         * client API.
         * 
         * @param path - the string component to add to the url.
         */
        public void addPath(String path)
        {
            target.path(path);        
        }
        
        /**
         * addParamToForm - gives the user the ability to add a set of parameters to a form, 
         * instead of adding direcly via the .property() method.
         * @param property - Represents the name of the property we're adding to the form.
         * @param value - This String is the value being stored in the property
         */
        public void addParamToForm(String property, String value)
        {
            if(value!=null && value.startsWith("[") && value.endsWith("]")) {
                String[] values = value.substring(1, value.length()-1).split(",");
                for(String temp : values) {
                    form.param(property, temp);
                }
            }
            else form.param(property, value);
  //          System.out.println("Value of:  "+value+" added to our form property:  "+property);
        }
        
        /**
         * setQueryParam - adds a parameter to your url string
         * @param name - queryParam name
         * @param value - queryParam value
         * url?name=value
         */
        public void setQueryParam(String paramName, String paramValue)
        {
            this.target = this.target.queryParam(paramName, paramValue);
            System.out.println("QueryParam ("+paramName+"="+paramValue+"), added to your url");
        }
        
        /**
         * clearQueryParams - wipes out the query params for the current webtarget.
         * We use this get rid of any lingering query params from previous HTTP requests.
         * This is to prevent us from getting snagged by old query information.
         */
        public void clearQueryParams()
        {
            setQueryParam("name",null);
            System.out.println("QueryParam list cleared");
        }

  

        
        /**
         * get - perform a get request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param url - Explicity url for our get command
         * @return - response content
         */
        public void get(String url)
        {
            this.setTargetUri(url);
            
            get();
        }    

        public void get(String url, Map<String, String> params)
        {
            this.setTargetUri(url);
            if(params!=null) {
                for(Map.Entry<String, String> entry: params.entrySet())
                {
                    this.setQueryParam(entry.getKey(), entry.getValue());
                }
            }
            get();
        }  
        
        /**
         * get - perform a get request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param url - Explicity url for our get command
         */
        public void get()
        {
            String cType = getResponseContentType();
            
            if (!isAuthenticated())
            {
                System.out.println("Sending GET Request without authentication");
          /*      if (cType instanceof MediaType)
                {
                    this.responseObject = target.request((MediaType)cType).get();
                    this.setAllResponseObjects();
                }
                else if (cType instanceof String)
                {*/
                    this.responseObject = target.request((String)cType).get();
                    this.setAllResponseObjects();
                
            }
            else
            {
                System.out.println("Authenticated GET request being sent...");
          /*      if (cType instanceof MediaType)
                {
                    responseObject = target.request((MediaType)cType).cookie(authCookie).get();
                    this.setAllResponseObjects();
                }
                else if (cType instanceof String)
                {*/
                    responseObject = target.request((String)cType).cookie(authCookie).get();
                    this.setAllResponseObjects();
            /*    }
                else
                {
                    throw new IllegalArgumentException("Invalid media type selected for request content:  "+cType.getClass().getName()+".");
                }*/
            }
        }    

        /**
         * get - perform a get request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param returnObj - the class type of the return object
         * @param url - Explicity url for our get command
         * @return - response content
         */
        public <T> Object getObject(Class<T> returnObj)
        {
            Object response = null;
            Object cType = getResponseContentType();
            
            if (!isAuthenticated())
            {

                System.out.println("Sending GET Request without authentication");
                if (cType instanceof MediaType)
                {
                    response = target.request((MediaType)cType).get(returnObj);
                }
                else if (cType instanceof String)
                {
                    response = target.request((String)cType).get(returnObj);
                }
            }
            else
            {
                System.out.println("Authenticated GET request being sent...");
                if (cType instanceof MediaType)
                {
                    response = target.request((MediaType)cType).cookie(authCookie).get(returnObj);
                }
                else if (cType instanceof String)
                {
                    response = target.request((String)cType).cookie(authCookie).get(returnObj);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid media type selected for request content:  "+cType.getClass().getName()+".");
                }
            }
            return response;
        }    

        /**
         * getPage - wrapper class for the get() method pulls an existing page from our map
         * and uses it to find our target and make an HTTP GET request
         * 
         * @param page - String representing our page key
         * @return - String value of our return content from our HTTP request
         */
        public <T> Object getObjectPage(String page, Class<T> obj)
        {
            if (!webTargetMap.containsKey(page))
            {
                System.out.println("Looking for a page which hasn't yet been mapped, ("+page+")...returning null.");
                return null;
            }
            //Call get the webtarget mapped to this page from the map and assign it to the object web target
            target = webTargetMap.get(page);
            //Call the no parameter version of the post command and return the result
            return getObject(obj);
        }    
        
        /**
         * getPage - wrapper class for the get() method pulls an existing page from our map
         * and uses it to find our target and make an HTTP GET request
         * 
         * @param page - String representing our page key
         * @return - String value of our return content from our HTTP request
         */
        public void getPage(String page)
        {
            if (!webTargetMap.containsKey(page))
            {
                System.out.println("Looking for a page which hasn't yet been mapped, ("+page+")...returning null.");
                return;
            }
            //Call get the webtarget mapped to this page from the map and assign it to the object web target
            target = webTargetMap.get(page);
            //Call the no parameter version of the post command and return the result
            get();
        }
        
        /**
         * delete - perform a delete request on the currently set url
         * sets our local headers, response code, current url, etc.
         * @return - response content
         */
        public void delete()
        {
            if (!isAuthenticated())
            {
                //if we aren't logged-in, then let's just make sure that we document that
                System.out.println("Sending delete Request without authentication");
                responseObject = target.request(MediaType.APPLICATION_JSON_TYPE).delete(Response.class);
                this.setAllResponseObjects();

            }
            else
            {
                System.out.println("Authenticated delete request being sent...");
                responseObject = target.request(MediaType.APPLICATION_JSON_TYPE).cookie(authCookie).delete(Response.class);
                this.setAllResponseObjects();
            }
        }
        
        /**
         * delete - perform a delete request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param url - Explicity url for our delete command
         * @return - response content
         */
        public void delete(String url)
        {
            this.setTargetUri(url);
            delete();
        }        
        
        /**
         * post - perform a post with the assumption that params and url were already set and
         * in a form object.  This will pass requests via url-encoded content type.
         * sets our local headers, response code, current url, etc.
         * @return - response content
         */
        public void post()
        {
            String cType = getRequestContentType();
            if (!isAuthenticated())
            {
                System.out.println("Sending POST Request without authentication");
                if( cType.equals(MediaType.APPLICATION_JSON)) {
                    this.responseObject = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.json(this.jsonPayloadString));
                    this.setAllResponseObjects();
                }
                else if ( cType.equals(MediaType.APPLICATION_FORM_URLENCODED))
                {
                    this.responseObject = target.request(getResponseContentType()).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    this.setAllResponseObjects();
                }
                else
                {
                    this.responseObject = target.request(getResponseContentType()).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    this.setAllResponseObjects();
                }
            }
            else
            {
                System.out.println("Sending POST Request with authentication");
                if( cType.equals(MediaType.APPLICATION_JSON)) {
                    responseObject = target.request().accept(MediaType.APPLICATION_JSON).cookie(authCookie).post(Entity.json(this.jsonPayloadString));
                    this.setAllResponseObjects();
                }
                else if ( cType.equals(MediaType.APPLICATION_FORM_URLENCODED))
                {
                    responseObject = target.request(getResponseContentType()).cookie(authCookie).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    this.setAllResponseObjects();
                }
                else
                {
                    responseObject = target.request(getResponseContentType()).cookie(authCookie).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                    this.setAllResponseObjects();
                }
            }
        } 
        
        /**
         * post - perform a post request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param url - Explicity url for our get command
         * @return - response content
         */
        public void post(String url)
        {
            this.setTargetUri(url);
            post();
        }            

        /**
         * post - perform a post request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param obj - Our Object being passed to the service in a serialized form (like JSON)
         * @return - response content
         */
        public void post(Object obj)
        {
            Object cType = getRequestContentType();
            if (!isAuthenticated())
            {
                System.out.println("Sending POST Request without authentication");
                if (cType instanceof MediaType)
                {
                    this.responseObject = target.request(getResponseContentType()).post(Entity.entity(obj, (MediaType)cType));
                    this.setAllResponseObjects();

                }
                else if (cType instanceof String)
                {
                    this.responseObject = target.request(getResponseContentType()).post(Entity.entity(obj, (String)cType));
                    this.setAllResponseObjects();
                }
                /**
                 * @Todo - add exception handling
                 */
            }
            else
            {
                System.out.println("Sending POST Request with authentication");
                if (cType instanceof MediaType)
                {
                    this.responseObject = target.request(getResponseContentType()).cookie(authCookie).post(Entity.entity(obj, (MediaType)cType));
                    this.setAllResponseObjects();

                }
                else if (cType instanceof String)
                {
                    this.responseObject = target.request(getResponseContentType()).cookie(authCookie).post(Entity.entity(obj, (String)cType));
                    this.setAllResponseObjects();
                }
                /**
                 * @Todo - add exception handling
                 */  
            }
        }        
        
        
        /**
         * post - perform a post request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param url - Explicity url for our get command
         * @param obj - Object used to pass to service should be a serializable object
         * @return - response content
         */
        public void post(String url, Object obj)
        {
            this.setTargetUri(url);
            post(obj);        
        }        
     
        /**
         * put - perform a put with the assumption that params and url were already set
         * sets our local headers, response code, current url, etc.
         * @return - response content
         */
        public void put()
        {
            Object cType = getResponseContentType();
            if (!isAuthenticated())
            {
                System.out.println("Sending PUT Request without authentication");
                if (cType instanceof MediaType)
                {
                    responseObject = target.request(getResponseContentType()).put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                }
                else if (cType instanceof String)
                {
                    responseObject = target.request(getResponseContentType()).put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                 
                }
                else
                {
                    throw new IllegalArgumentException("Invalid object type submitted for request Content Type:  "+cType.getClass().getName()+".");
                }
            }
            else
            {
                System.out.println("Sending PUT Request with authentication");
                if (cType instanceof MediaType)
                {
                    responseObject = target.request(getResponseContentType()).cookie(authCookie).put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                }
                else if (cType instanceof String)
                {
                    responseObject = target.request(getResponseContentType()).cookie(authCookie).put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));            
                }
                else
                {
                    throw new IllegalArgumentException("Invalid object type submitted for request Content Type:  "+cType.getClass().getName()+".");
                }
            }
        } 
        
        /**
         * put - perform a put request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param url - Explicity url for our get command
         * @return - response content
         */
        public void put(String url)
        {
            this.setTargetUri(url);
            put();
        }            

        /**
         * put - perform a put request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param obj - Our Object being passed to the service in a serialized form (like JSON)
         * @return - response content
         */
        public void put(Object obj)
        {
            Object cType = getRequestContentType();
            
            if (!isAuthenticated())
            {
                System.out.println("Sending PUT Request without authentication");
                if (cType instanceof MediaType)
                {
                    responseObject = target.request(getResponseContentType()).put(Entity.entity(obj, (MediaType)cType));
                    this.setAllResponseObjects();
                }
                else if (cType instanceof String)
                {
                    responseObject = target.request(getResponseContentType()).put(Entity.entity(obj, (String)cType));
                    this.setAllResponseObjects();
                }
                /**
                 * @Todo - add exception handling
                 */
            }
            else
            {
                System.out.println("Sending PUT Request with authentication");
                if (cType instanceof MediaType)
                {
                    responseObject = target.request(getResponseContentType()).cookie(authCookie).put(Entity.entity(obj,(MediaType)cType));
                    this.setAllResponseObjects();
                }
                else if (cType instanceof String)
                {
                    responseObject = target.request(getResponseContentType()).cookie(authCookie).put(Entity.entity(obj, (String)cType));
                    this.setAllResponseObjects();
                }
                /**
                 * @Todo - add exception handling
                 */  
            }
        }        
        
        
        /**
         * put - perform a put request on the provided url
         * sets our local headers, response code, current url, etc.
         * @param url - Explicity url for our get command
         * @param obj - Object used to pass to service should be a serializable object
         * @return - response content
         */
        public void put(String url, Object obj)
        {
            this.setTargetUri(url);
            put(obj);
        }
        
        /**
         * putPage - a wrapper class for the put() method which
         * @param list - varargs list [0] - page name, [1] - Object, [2] - returnType, [3] - requestType
         * @return - String result of our contents retrieved from page request
         */
        public void postPage(Object...list)
        {
            if (!webTargetMap.containsKey(list[0]))
            {
                System.out.println("Looking for a page which hasn't yet been mapped, ("+list[0]+")...returning null.");
                responseObject = null;
                return;
            }
            //Call get the webtarget mapped to this page from the map and assign it to the object web target
            target = webTargetMap.get((String)list[0]);
            if (list.length==1)
            {
                //Call the no parameter version of the post command and return the result
                post();
            }
            else if (list.length>=2)
            {
                //Call the post(Object) method and return its result
                if (list.length >2 && list.length <5)
                {
                    setRequestType((String)list[2]);
                    if (list.length==4)
                    {
                        setReturnType((String)list[3]);
                    }
                   
                }
                else if (list.length==2)
                {
                    post(list[1]);
                }
                else
                {
                    System.out.println("Too many arguments, "+list.length+", passed to the postPage method");
                    responseObject=null;
                    return;
                }

            }
            //if we get here...something went wrong, so return null
        }
        
        /**
         * addPage - used to associate a webtarget to a more readable pagename each page/url
         * association is stored as a page/WebTarget combination in a backing hashmap.
         * @param pageName - The name to which a user wants to associate their URL
         * @param url - A full http url (<host>:<port>/path)
         */
        public void addPage(String pageName, String url)
        {
            URI uri = null;
            try
            {
                uri = new URI(url);
            }
            catch (URISyntaxException e)
            {
                System.out.println("Invalid URI creation attempt...cannot add page.");
            }
            if (webTargetMap.containsKey(pageName))
            {
                System.out.println("Replacing existing page, "+pageName+", in our map with new url:  "+url+".");
            }
            else 
            {
                System.out.println("Adding new web page, "+pageName+", associated with url:  "+url+".");
            }
            webTargetMap.put(pageName, client.target(uri));
        }
        /**
         * clearForm - used to wipe the existing parameters from our form object
         */
        public void clearForm()
        {
            form = null;//refers to nothing
            form = new Form();//new reference
            System.out.println("Form cleared");
        }
        
        /**
         * removeFormParam - Deletes one parameter from the form instead of removing
         * everything from the form.
         * @param formField - remove the existing parameter from the form
         */
        public void removeFormParam(String formField)
        {
            MultivaluedMap<String,String> tempForm;
            tempForm = form.asMap();
            if (tempForm.containsKey(formField))
            {
                tempForm.remove(formField);
            }
            else
            {
                System.out.println(formField+":  no such parameter exists on our form.");
                tempForm.clear();
                tempForm = null;
                return;
            }
            form = null;
            form = new Form(tempForm);
            System.out.println("Form Parameter, "+formField+", successfully removed");
        }
        

    /**********************************************************************************************************************************************************************************
     * private methods                                                                                                                                                                *
     **********************************************************************************************************************************************************************************/
     
        /**
         * setLoginAuthAndHeaders - takes information stored in our Response object and stores it
         * in access fields.  authCookie for our RSTOKEN auth cookie and lastHeaders for our header map
         * we also clear out the Form field that we last used to pass information in our post
         * @param resp - the resposne we received from our HTTP request
         */
        private void setLoginAuthAndHeaders(Response resp)
        {
            authCookie = resp.getCookies().get(AuthCookieName);
            if (authCookie != null)
            {
                System.out.println("We received an RSTOKEN cookie from our login attempt");
                authenticated=true;
            }
            else
            {
                System.out.println("Authentication attempt was unsuccessful, setting our authenticated flag to false");
                authenticated=false;
            }

            this.setAllResponseObjects();
            form = null;
            form = new Form();
            
            //set the ability to follow redirects back to true
            target.property(ClientProperties.FOLLOW_REDIRECTS, true);
            System.out.println("Follow_redirects property now set back to true");

        }
        
        /**
         * loginTarget() - private method to set the target for our webClient to run authentication
         * @return
         */
        private JerseyWebTarget loginTarget()
        {
            form.param("url",RsqaConstants.RESOLVE_REDIR_PATH);
            form.param("hashState", "");
            
            String host = target.getUri().getHost();
            int port = target.getUri().getPort();
            StringBuilder loginUrl = new StringBuilder("http://").append(host);
            if (port>0)
            {
                loginUrl.append(":").append(port);
            }
            loginUrl.append(RsqaConstants.RESOLVE_BASE_PATH);
            loginUrl.append("/login?url=").append(RsqaConstants.RESOLVE_REDIR_PATH);
            JerseyWebTarget t = client.target(loginUrl.toString());
            return t;
        }
        private void initialConfig()
        {
            initialConfig = new ClientConfig();
            //initialConfig
           // initialConfig.register(FormDataMultiPart.class);
           ((ClientConfig) initialConfig).property(ClientProperties.FOLLOW_REDIRECTS, false);
           //((ClientConfig) initialConfig).getClasses().add(MultiPartWriter.class);
         //  initialConfig.register(MultiPartWriter.class);
        }
        private void buildWebTarget(String url)
        {
            target = client.target(url);
        }
        
        private void buildWebTarget()
        {
             buildWebTarget("http://localhost:8080/resolve/service");
        }

        private String getResponseContentType()
        {
            if ("".equals(contentResponseType))
            {
                return DEFAULT_CONTENT_RESPONSE_TYPE;
            }
            else
            {
                return contentResponseType;
            }
        }
        
        private String getRequestContentType()
        {
            if ("".equals(contentRequestType)||(contentRequestType==null))
            {
                return DEFAULT_CONTENT_REQUEST_TYPE;
            }
            else
            {
                return contentRequestType;
            }
        }
        
        /**
         * getResponseEntity - returns the entityType content requested by our client user
         * it is called directly by one of our wrapper classes.
         * @param target - The class type we're looking for
         * @return - return a generic object of the type specified by target
         */
        private <T> Object getResponseEntity(Class<T> target)
        {
            return responseObject.readEntity(target);
        }
        
        /**
         * setAllResponseObjects - takes our responseObject and dumpsInfo for us before the
         * stream closes
         */
        private void setAllResponseObjects()
        {
            this.statusCode = responseObject.getStatus();
            this.allowedMethods =  responseObject.getAllowedMethods();
            this.responseHeaderMap = getAllHeaders();
            this.lastDateModified = responseObject.getLastModified();
            this.responseLength = responseObject.getLength();
            this.responseContentString = (String)this.getResponseEntity(String.class);
        }
        
        /**
         * targetSetUri - sets the webclient target to the uri specified by the
         * passed url String
         * @param url
         */
        private void setTargetUri(String url)
        {
            URI uri = null;
            try
            {
                uri = new URI(url);
            }
            catch (URISyntaxException e)
            {
                System.out.println("Invalid URI creation attempt...cannot add page.");
            }
            target = client.target(uri);
        }


}
    