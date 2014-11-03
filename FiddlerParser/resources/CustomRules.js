import System;
import System.Collections;
import System.Windows.Forms;
import Fiddler;

// INTRODUCTION
// This is the FiddlerScript Rules file, which creates some of the menu commands and
// other features of Fiddler. You can edit this file to modify or add new commands.
//
// The original version of this file is named SampleRules.js and it is in the
// \Program Files\Fiddler\ folder. When Fiddler first starts, it creates a copy named
// CustomRules.js inside your \Documents\Fiddler2\Scripts folder. If you make a 
// mistake in editing this file, simply delete the CustomRules.js file and restart
// Fiddler. A fresh copy of the default rules will be created from the original
// sample rules file.

// GLOBALIZATION NOTE:
// Be sure to save this file with UTF-8 Encoding if using any non-ASCII characters
// in strings, etc.

// JScript.NET Reference
// http://fiddler2.com/r/?msdnjsnet
//
// FiddlerScript Reference
// http://fiddler2.com/r/?fiddlerscriptcookbook
//
// FiddlerScript Editor: 
// http://fiddler2.com/fiddlerscript-editor

class Handlers
{
    // The following snippet demonstrates a custom-bound column for the Web Sessions list.
    // See http://fiddler2.com/r/?fiddlercolumns for more info
    /*
    public static BindUIColumn("Method", 60)
    function FillMethodColumn(oS: Session): String {
    return oS.RequestMethod;
    }
    */

    // The following snippet demonstrates how to create a custom tab that shows simple text
    /*
    public BindUITab("Flags")
    static function FlagsReport(arrSess: Session[]):String {
    var oSB: System.Text.StringBuilder = new System.Text.StringBuilder();
    for (var i:int = 0; i<arrSess.Length; i++)
    {
    oSB.AppendLine("SESSION FLAGS");
    oSB.AppendFormat("{0}: {1}\n", arrSess[i].id, arrSess[i].fullUrl);
    for(var sFlag in arrSess[i].oFlags)
    {
    oSB.AppendFormat("\t{0}:\t\t{1}\n", sFlag.Key, sFlag.Value);
    }
    }
    return oSB.ToString();
    }
    */

    // You can create a custom menu like so:
    /*
    QuickLinkMenu("&Links") 
    QuickLinkItem("IE GeoLoc TestDrive", "http://ie.microsoft.com/testdrive/HTML5/Geolocation/Default.html")
    QuickLinkItem("FiddlerCore", "http://fiddler2.com/fiddlercore")
    public static function DoLinksMenu(sText: String, sAction: String)
    {
    Utilities.LaunchHyperlink(sAction);
    }
    */
    // ====================================================================================================
    // Json Test Tab
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Tab in Fiddler with a Json Test Case for selected Sessions 
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================
    public BindUITab("Json Test")
    static function JsonTest(oSession: Session[]):String {
        var index: Number   = 0;
        var testCase: Object = Fiddler.WebFormats.JSON.JsonDecode('{jsonFileName: "", baseURL: "", username: "", password: "", tests: []}');
        testCase.JSONObject["jsonFileName"] = "Fiddler Generated Test";
        testCase.JSONObject["baseURL"]      = "http://fiddler:8080/resolve/service";
        testCase.JSONObject["username"]     = "admin";
        testCase.JSONObject["password"]     = "resolve";
        

        for (var i:int = 0; i<oSession.Length; i++) {
            var test: String = JsonTest(oSession[i]);
            
            if (!test.Equals(String.Empty)) {
                var testObject: Object = Fiddler.WebFormats.JSON.JsonDecode(test);
                testCase.JSONObject["tests"].Add(testObject.JSONObject);
                index++;
            }
        }
        
        if (index > 0) return Fiddler.WebFormats.JSON.JsonEncode(testCase.JSONObject);
        
        return String.Empty;
    }
    
    // ====================================================================================================
    // Parameters String Tab (Just for Testing purposes, this Tab will be deprecated shortly)
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Tab in Fiddler with Response Data Parameters
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================    
    public BindUITab("Parameters String")
    static function ValidateResponseData(oSession: Session[]):String {
        var oSB: System.Text.StringBuilder = new System.Text.StringBuilder();
		
        for (var i:int = 0; i<oSession.Length; i++) {
 
            
  
            var dataObject: Object = Fiddler.WebFormats.JSON.JsonDecode(oSession[i].GetResponseBodyAsString()).JSONObject["data"];
            
            
            if (dataObject != null) {
                for (var dataAttribute:DictionaryEntry in dataObject) {
                    var key: String     = dataAttribute.Key;
                    var value: String   = dataAttribute.Value;
                    if (value == null || value.Equals("undefined")) {
                        value = String.Empty;
                    }
                    
                    oSB.AppendLine(key + ":" + value);
                }
 
            }


        }
        
        if (oSession.Length > 0) return oSB.ToString();
        
        return String.Empty;
    }
    
    // ====================================================================================================
    // Method Column
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Fiddler Column with Request Method 
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================
    public static BindUIColumn("Method")
    function Method(oSession: Session): String{
        if (responseReady(oSession)) return oSession.oRequest.headers.HTTPMethod;
        return String.Empty;
    }
    
    // ====================================================================================================
    // Operation Column
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Fiddler Column with Resolve Operation being called
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================
    public static BindUIColumn("Operation")
    function Operation(oSession: Session): String{
        if (responseReady(oSession)) {
            var baseUrl: String = "/resolve/service";
        
            if (oSession.url.Contains(baseUrl)) {
                var startPosition:Number = oSession.host.Length + baseUrl.Length;
                var endPosition:Number = oSession.url.Length;
            
                if (oSession.url.Contains("?")) {
                    endPosition = oSession.url.IndexOf("?");
                }
            
                return oSession.url.Substring(startPosition, endPosition - startPosition);
            } else {
                return oSession.url.Substring(oSession.host.Length);
            }
        }
    }
    
    // ====================================================================================================
    // Payload Column
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Fiddler Column with Request Payload(Json or Request Form Parameters)
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================    
    public static BindUIColumn("Payload")
    function Payload(oSession: Session): String{
        if (responseReady(oSession)) return oSession.GetRequestBodyAsString();
        return String.Empty;
    }
    
    // ====================================================================================================
    // Success Column
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Fiddler Column with Response Success
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================
    public static BindUIColumn("Success")
    function Success(oSession: Session): String{
        if (responseReady(oSession)) return readResponseValue(oSession, "success");
        return String.Empty;
    }
    
    // ====================================================================================================
    // Message Column
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Fiddler Column with Response Message
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================    
    public static BindUIColumn("Message")
    function Message(oSession: Session): String{
        if (responseReady(oSession)) return readResponseValue(oSession, "message");
        return String.Empty;
    }
    
    // ====================================================================================================
    // Total Column
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Fiddler Column with Response Total
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================    
    public static BindUIColumn("Total")
    function Total(oSession: Session): String{
        if (responseReady(oSession)) return readResponseValue(oSession, "total");
        return String.Empty;
    }
    
    // ====================================================================================================
    // Parameters Column
    // ----------------------------------------------------------------------------------------------------
    // Description : Show a Fiddler Column with Query String Parameters white spaces separated and unescape
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================     
    public static BindUIColumn("Parameters")
    function Parameters(oSession: Session): String{

        var params: Array       = ParametersString(oSession).split("&");
        var parameters: String  = "";

        for (var i: Number in params) {
            var key: String      = params[i].split("=")[0];
            var value: String    = unescape(params[i].split("=")[1]);

            if (key != null && !key.Equals(String.Empty) && !key.Equals("_dc")) {
                parameters = parameters + key + "=" + value + " ";
            }
        }
        
        return parameters;
    }
    
    // ====================================================================================================
    // Test Column
    // ----------------------------------------------------------------------------------------------------
    // Description : Used to trigger Json Generation and Message to Fiddler Log
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================
    public static BindUIColumn("Test")
    function Test(oSession: Session): String{
        var test: String = JsonTest(oSession);
        
        if (!test.Equals(String.Empty)) {
        
            FiddlerApplication.Log.LogString("[JSON Test] " + JsonTest(oSession));
            return "Available";
        }
        
        return String.Empty;
    }
    
        
    // ====================================================================================================
    // JsonTest: String
    // ----------------------------------------------------------------------------------------------------
    // Description : Create Json Test Content for Fiddler Sessions
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================
    public static function JsonTest(oSession: Session): String {
        if (responseReady(oSession) && oSession.fullUrl.Contains("/resolve/service") && !oSession.fullUrl.Contains("/client/poll")) {
            // ====================================================================================================
            // JSON Strings
            // ----------------------------------------------------------------------------------------------------
            var testString  : String  = '{name: "", path: "", description: "", method: "", requestType: "", responseType: "", queryParams: [], requestForm: [], jsonPayload: {baseNode: ""}, handleResponse: {statusCode: "", failLevel: "", failureMessage: "", responseChecks: []}, testOps: []}';
            var paramString : String = '{key: "", type: "", value: ""}';
            var checkString : String = '{method: "", sourceType: "", sourceKey: "", targetKey: ""}';
            var tesOpString : String = '{compareMethod: "", sourceType: "", sourceKey: "", targetType: "", targetKey: ""}';
            
            // ====================================================================================================
            // Variables
            // ----------------------------------------------------------------------------------------------------
            var test        : Object = Fiddler.WebFormats.JSON.JsonDecode(testString);
            var paramObject : Object = Fiddler.WebFormats.JSON.JsonDecode(paramString);
            var checkObject : Object = Fiddler.WebFormats.JSON.JsonDecode(checkString);
            var tesOpObject : Object = Fiddler.WebFormats.JSON.JsonDecode(tesOpString);
            
            var response    : Object = Fiddler.WebFormats.JSON.JsonDecode(oSession.GetResponseBodyAsString());
            var data        : Object = response.JSONObject["data"];
            
            var payload     : String = Payload(oSession);
            var parameters  : String = ParametersString(oSession);
            
            var params      : Array;
            var i           : Number;
            var key         : String;
            var value       : String;
    
            // ====================================================================================================
            // Test Case
            // ----------------------------------------------------------------------------------------------------
            test.JSONObject["name"]           = Operation(oSession);
            test.JSONObject["path"]           = Operation(oSession);
            test.JSONObject["description"]    = Operation(oSession);
            test.JSONObject["method"]         = Method(oSession);
            test.JSONObject["requestType"]    = "URLENCODED_FORM_APPLICATION";
            test.JSONObject["responseType"]   = "JSON_APPLICATION";

            // ====================================================================================================
            // JsoN Payload
            // ----------------------------------------------------------------------------------------------------
            if(oSession.oRequest.headers.ExistsAndContains("Content-Type", "json")) {
                test.JSONObject["requestType"] = "JSON_APPLICATION";
                test.JSONObject["jsonPayload"]["baseNode"] = Payload(oSession);
            } else if(oSession.oRequest.headers.ExistsAndContains("Content-Type", "urlencoded")) {
                // ====================================================================================================
                // Request Form Parameters
                // ----------------------------------------------------------------------------------------------------
                test.JSONObject["requestType"] = "URLENCODED_FORM_APPLICATION";
        
                if (payload != null && !payload.Equals(String.Empty)) {
                    params  = payload.split("&");
        
                    for (i in params) {
                        key      = params[i].split("=")[0];
                        value    = unescape(params[i].split("=")[1]);
            
                        if (key != null && !key.Equals(String.Empty) && !key.Equals("_dc")) {
                            paramObject = Fiddler.WebFormats.JSON.JsonDecode('{key: "", type: "", value: ""}');
                            paramObject.JSONObject["key"]   = key;
                            paramObject.JSONObject["type"]  = "PLAIN";
                            paramObject.JSONObject["value"] = value;
                            
                            test.JSONObject["requestForm"].Add(paramObject.JSONObject);
                        }
        
                    }
                }
        
            }
            
            // ====================================================================================================
            // Query String Parameters
            // ----------------------------------------------------------------------------------------------------
            if (parameters != null && !parameters.Equals(String.Empty)) {
                params  = parameters.split("&");
    
                for (i in params) {
                    key      = params[i].split("=")[0];
                    value    = unescape(params[i].split("=")[1]);

                    if (key != null && !key.Equals(String.Empty) && !key.Equals("_dc")) {
                        paramObject = Fiddler.WebFormats.JSON.JsonDecode('{key: "", type: "", value: ""}');
                        paramObject.JSONObject["key"]   = key;
                        paramObject.JSONObject["type"]  = "PLAIN";
                        paramObject.JSONObject["value"] = value;
                            
                        test.JSONObject["queryParams"].Add(paramObject.JSONObject);
                    }
                }
            }
            
            // ====================================================================================================
            // Handle Response
            // ----------------------------------------------------------------------------------------------------
            test.JSONObject["handleResponse"]["statusCode"]      = oSession.responseCode;
            test.JSONObject["handleResponse"]["failLevel"]       = "ERROR";
            test.JSONObject["handleResponse"]["failureMessage"]  = "Failed";
            
            // ====================================================================================================
            // Response Checks (Common to all Tests)
            // ----------------------------------------------------------------------------------------------------
            checkObject = CheckObject("JSON", "$.success", "EQUAL", "PLAIN", readResponseValue(oSession, "success"));
            test.JSONObject["handleResponse"]["responseChecks"].Add(checkObject.JSONObject);
            
            checkObject = CheckObject("JSON", "$.data", "EQUAL", "PLAIN", "");
            if (data != null) checkObject.JSONObject["compareMethod"] = "NOTEMPTY";
            test.JSONObject["handleResponse"]["responseChecks"].Add(checkObject.JSONObject);
            
            checkObject = CheckObject("JSON", "$.message", "EQUAL", "PLAIN", readResponseValue(oSession, "message"));
            test.JSONObject["handleResponse"]["responseChecks"].Add(checkObject.JSONObject);
            
            checkObject = CheckObject("JSON", "$.records", "EQUAL", "PLAIN", "");
            if (!readResponseValue(oSession, "records").Equals(String.Empty)) {
                checkObject.JSONObject["compareMethod"] = "SIZEEQUAL";
                checkObject.JSONObject["targetKey"]     = readResponseValue(oSession, "total");
            }
            test.JSONObject["handleResponse"]["responseChecks"].Add(checkObject.JSONObject);
            
            checkObject = CheckObject("JSON", "$.total", "EQUAL", "PLAIN", readResponseValue(oSession, "total"));
            test.JSONObject["handleResponse"]["responseChecks"].Add(checkObject.JSONObject);
            
            // ====================================================================================================
            // Test Operations and Response Checks for $.data Elements
            // ----------------------------------------------------------------------------------------------------
            if (data != null) {
                for (var dataAttr:DictionaryEntry in data) {
                    var dataKey: String     = dataAttr.Key;
                    var dataValue: String   = dataAttr.Value;
                    if (dataValue == null || dataValue.Equals("undefined")) {
                        dataValue = String.Empty;
                    }
                    
                    checkObject = CheckObject("JSON", "$.data." + dataKey, "NOTEMPTY", "PLAIN", "");
                    if (dataValue.Equals(String.Empty) || dataValue.Equals("UNDEFINED")) {
                        checkObject.JSONObject["compareMethod"]  = "EQUAL";
                        checkObject.JSONObject["targetKey"]      = dataValue;
                    }
                    
                    tesOpObject = Fiddler.WebFormats.JSON.JsonDecode('{method: "", sourceType: "", sourceKey: "", targetKey: ""}');
                    tesOpObject.JSONObject["method"]        = "ASSIGN";
                    tesOpObject.JSONObject["sourceType"]    = "JSON";
                    tesOpObject.JSONObject["sourceKey"]     = "$.data." + dataKey;
                    tesOpObject.JSONObject["targetKey"]     = "data_" + dataKey;
                    
                    test.JSONObject["handleResponse"]["responseChecks"].Add(checkObject.JSONObject);
                    test.JSONObject["testOps"].Add(tesOpObject.JSONObject);
                }
 
            }
            
            // ====================================================================================================
            // Test Operation for Total
            // ----------------------------------------------------------------------------------------------------
            tesOpObject = Fiddler.WebFormats.JSON.JsonDecode('{method: "", sourceType: "", sourceKey: "", targetKey: ""}');
            tesOpObject.JSONObject["method"]        = "ASSIGN";
            tesOpObject.JSONObject["sourceType"]    = "JSON";
            tesOpObject.JSONObject["sourceKey"]     = "$.total";
            tesOpObject.JSONObject["targetKey"]     = "total";
            test.JSONObject["testOps"].Add(tesOpObject.JSONObject);
            
            return Fiddler.WebFormats.JSON.JsonEncode(test.JSONObject);
        }
        
        return String.Empty;
    }
        
    // ====================================================================================================
    // ParametersString: String
    // ----------------------------------------------------------------------------------------------------
    // Description : Extract Parameters String from URL
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================
    public static function ParametersString(oSession: Session): String {
        var baseUrl: String = "/resolve/service";
        
        if (oSession.url.Contains(baseUrl)) {
            var startPosition: Number = oSession.url.Length;
            
            if (oSession.url.Contains("?")) {
                startPosition = oSession.url.IndexOf("?") + 1;
            }
            
            return oSession.url.Substring(startPosition, oSession.url.Length - startPosition);
        }
        
        return unescape(oSession.url.Substring(oSession.host.Length));;
    }

    // ====================================================================================================
    // responseReady: boolean
    // ----------------------------------------------------------------------------------------------------
    // Description : Confirm that response was received and Content is Json
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================    
    public static function responseReady(oSession: Session): boolean {
        return oSession.responseCode != 0 && oSession.oResponse.headers.ExistsAndContains("Content-Type", "json");
    }
    
    public static function readResponseValue(oSession: Session, value: String): String {
        var jsonString: String = oSession.GetResponseBodyAsString();
        
        if (jsonString.Contains(value)) {
            var jsonObject: Object = Fiddler.WebFormats.JSON.JsonDecode(jsonString).JSONObject[value];
            if (jsonObject == null) return String.Empty;
            
            return Fiddler.WebFormats.JSON.JsonEncode(jsonObject);
        }
        
        return String.Empty;
    }
    
    // ====================================================================================================
    // CheckObject: Object
    // ----------------------------------------------------------------------------------------------------
    // Description : Build a Check Response Json Object
    // Author      : Numa Canedo
    // Version     : 1.0
    // Date        : November 02, 2014
    // ====================================================================================================    
    public static function CheckObject(sourceType: String, sourceKey: String, compareMethod: String, targetType: String, targetKey: String) : Object {
        var checkObject:Object = Fiddler.WebFormats.JSON.JsonDecode('{compareMethod: "", sourceType: "", sourceKey: "", targetType: "", targetKey: ""}');
        checkObject.JSONObject["sourceType"]     = sourceType;
        checkObject.JSONObject["sourceKey"]      = sourceKey;
        checkObject.JSONObject["compareMethod"]  = compareMethod;
        checkObject.JSONObject["targetType"]     = targetType;
        checkObject.JSONObject["targetKey"]      = targetKey;
        
        return checkObject;
    }
        
    public static RulesOption("Hide 304s")
    BindPref("fiddlerscript.rules.Hide304s")
    var m_Hide304s: boolean = false;

    // Cause Fiddler to override the Accept-Language header with one of the defined values
    public static RulesOption("Request &Japanese Content")
    var m_Japanese: boolean = false;

    // Automatic Authentication
    public static RulesOption("&Automatically Authenticate")
    BindPref("fiddlerscript.rules.AutoAuth")
    var m_AutoAuth: boolean = false;

    // Cause Fiddler to override the User-Agent header with one of the defined values
    RulesString("&User-Agents", true) 
    BindPref("fiddlerscript.ephemeral.UserAgentString")
    RulesStringValue(0,"Netscape &3", "Mozilla/3.0 (Win95; I)")
    RulesStringValue(1,"WinPhone7", "Mozilla/4.0 (compatible: MSIE 7.0; Windows Phone OS 7.0; Trident/3.1; IEMobile/7.0; SAMSUNG; SGH-i917)")
    RulesStringValue(2,"WinPhone8.1", "Mozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 520) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537")
    RulesStringValue(3,"&Safari5 (Win7)", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.21.1 (KHTML, like Gecko) Version/5.0.5 Safari/533.21.1")
    RulesStringValue(4,"Safari7 (Mac)", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9) AppleWebKit/537.71 (KHTML, like Gecko) Version/7.0 Safari/537.71")
    RulesStringValue(5,"iPad", "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A403 Safari/8536.25")
    RulesStringValue(6,"iPhone6", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25")
    RulesStringValue(7,"IE &6 (XPSP2)", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)")
    RulesStringValue(8,"IE &7 (Vista)", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; SLCC1)")
    RulesStringValue(9,"IE 8 (Win2k3 x64)", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; WOW64; Trident/4.0)")
    RulesStringValue(10,"IE &8 (Win7)", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)")
    RulesStringValue(11,"IE 9 (Win7)", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
    RulesStringValue(12,"IE 10 (Win8)", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; WOW64; Trident/6.0)")
    RulesStringValue(13,"IE 11 (Surface2)", "Mozilla/5.0 (Windows NT 6.3; ARM; Trident/7.0; Touch; rv:11.0) like Gecko")
    RulesStringValue(14,"IE 11 (Win8.1)", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
    RulesStringValue(15,"&Opera", "Opera/9.80 (Windows NT 6.2; WOW64) Presto/2.12.388 Version/12.17")
    RulesStringValue(16,"&Firefox 3.6", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.7) Gecko/20100625 Firefox/3.6.7")
    RulesStringValue(17,"&Firefox 31", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0")
    RulesStringValue(18,"&Firefox Phone", "Mozilla/5.0 (Mobile; rv:18.0) Gecko/18.0 Firefox/18.0")
    RulesStringValue(19,"&Firefox (Mac)", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:24.0) Gecko/20100101 Firefox/24.0")
    RulesStringValue(20,"Chrome", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.44 Safari/537.36")
    RulesStringValue(21,"ChromeBook", "Mozilla/5.0 (X11; CrOS armv7l 2913.260.0) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.99 Safari/537.11")
    RulesStringValue(22,"GoogleBot Crawler", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
    RulesStringValue(23,"Kindle Fire (Silk)", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-us; Silk/1.0.22.79_10013310) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16 Silk-Accelerated=true")
    RulesStringValue(24,"&Custom...", "%CUSTOM%")
    public static var sUA: String = null;

    // Cause Fiddler to delay HTTP traffic to simulate typical 56k modem conditions
    public static RulesOption("Simulate &Modem Speeds", "Per&formance")
    var m_SimulateModem: boolean = false;

    // Removes HTTP-caching related headers and specifies "no-cache" on requests and responses
    public static RulesOption("&Disable Caching", "Per&formance")
    var m_DisableCaching: boolean = false;

    public static RulesOption("Cache Always &Fresh", "Per&formance")
    var m_AlwaysFresh: boolean = false;
        
    // Force a manual reload of the script file.  Resets all
    // RulesOption variables to their defaults.
    public static ToolsAction("Reset Script")
    function DoManualReload() { 
        FiddlerObject.ReloadScript();
    }

    public static ContextAction("Decode Selected Sessions")
    function DoRemoveEncoding(oSessions: Session[]) {
        for (var x:int = 0; x < oSessions.Length; x++){
            oSessions[x].utilDecodeRequest();
            oSessions[x].utilDecodeResponse();
        }
        UI.actUpdateInspector(true,true);
    }

    static function OnBoot() {
        // MessageBox.Show("Fiddler has finished booting");
        // System.Diagnostics.Process.Start("iexplore.exe");

        // UI.ActivateRequestInspector("HEADERS");
        // UI.ActivateResponseInspector("HEADERS");
    }
    
    static function OnShutdown() {
        // MessageBox.Show("Fiddler has shutdown");
    }

    static function OnAttach() {
        // MessageBox.Show("Fiddler is now the system proxy");
    }

    static function OnDetach() {
        // MessageBox.Show("Fiddler is no longer the system proxy");
    }

    static function OnBeforeRequest(oSession: Session) {
        // Sample Rule: Color ASPX requests in RED
        // if (oSession.uriContains(".aspx")) {	oSession["ui-color"] = "red";	}

        // Sample Rule: Flag POSTs to fiddler2.com in italics
        // if (oSession.HostnameIs("www.fiddler2.com") && oSession.HTTPMethodIs("POST")) {	oSession["ui-italic"] = "yup";	}

        // Sample Rule: Break requests for URLs containing "/sandbox/"
        // if (oSession.uriContains("/sandbox/")) {
        //     oSession.oFlags["x-breakrequest"] = "yup";	// Existence of the x-breakrequest flag creates a breakpoint; the "yup" value is unimportant.
        // }
        // ====================================================================================================
        // Add the ability to show requests comming from eclipse
        // ----------------------------------------------------------------------------------------------------
        if (oSession.host.toLowerCase() == "fiddler:8080") oSession.host = "resolve:8080";
        
        // ====================================================================================================
        // Hide Client Poll Requests
        // ----------------------------------------------------------------------------------------------------
        if (oSession.fullUrl.Contains("/client/poll")) oSession["ui-hide"] = "do not want to see";
        
        // ====================================================================================================
        // Show only requests for resolve/service
        // ----------------------------------------------------------------------------------------------------
        if (!oSession.fullUrl.Contains("/resolve/service")) oSession["ui-hide"] = "do not want to see";
        
        // ====================================================================================================
        // Show only requests comming from Eclipse
        // ----------------------------------------------------------------------------------------------------
        //if (!oSession.m_clientIP.Contains("192.168")) oSession["ui-hide"] = "do not want to see";
        //if (oSession.fullUrl.Contains("/resolve/service/client/poll")) oSession["ui-hide"] = "do not want to see";
        //if (oSession.fullUrl.Contains("/resolve/service/client/getUser")) oSession["ui-hide"] = "do not want to see";
        //if (oSession.fullUrl.Contains("/resolve/service/user/downloadProfile")) oSession["ui-hide"] = "do not want to see";
        //if (
        //        !oSession.fullUrl.Contains("/resolve/service")
        //   ) oSession["ui-hide"] = "do not want to see";
        //if (oSession.fullUrl.Contains("/resolve/service/wiki/impex/download")) 

        if ((null != gs_ReplaceToken) && (oSession.url.indexOf(gs_ReplaceToken)>-1)) {   // Case sensitive
            oSession.url = oSession.url.Replace(gs_ReplaceToken, gs_ReplaceTokenWith); 
        }
        if ((null != gs_OverridenHost) && (oSession.host.toLowerCase() == gs_OverridenHost)) {
            oSession["x-overridehost"] = gs_OverrideHostWith; 
        }

        if ((null!=bpRequestURI) && oSession.uriContains(bpRequestURI)) {
            oSession["x-breakrequest"]="uri";
        }

        if ((null!=bpMethod) && (oSession.HTTPMethodIs(bpMethod))) {
            oSession["x-breakrequest"]="method";
        }

        if ((null!=uiBoldURI) && oSession.uriContains(uiBoldURI)) {
            oSession["ui-bold"]="QuickExec";
        }

        if (m_SimulateModem) {
            // Delay sends by 300ms per KB uploaded.
            oSession["request-trickle-delay"] = "300"; 
            // Delay receives by 150ms per KB downloaded.
            oSession["response-trickle-delay"] = "150"; 
        }

        if (m_DisableCaching) {
            oSession.oRequest.headers.Remove("If-None-Match");
            oSession.oRequest.headers.Remove("If-Modified-Since");
            oSession.oRequest["Pragma"] = "no-cache";
        }

        // User-Agent Overrides
        if (null != sUA) {
            oSession.oRequest["User-Agent"] = sUA; 
        }

        if (m_Japanese) {
            oSession.oRequest["Accept-Language"] = "ja";
        }

        if (m_AutoAuth) {
            // Automatically respond to any authentication challenges using the 
            // current Fiddler user's credentials. You can change (default)
            // to a domain\\username:password string if preferred.
            //
            // WARNING: This setting poses a security risk if remote 
            // connections are permitted!
            oSession["X-AutoAuth"] = "(default)";
        }

        if (m_AlwaysFresh && (oSession.oRequest.headers.Exists("If-Modified-Since") || oSession.oRequest.headers.Exists("If-None-Match")))
        {
            oSession.utilCreateResponseAndBypassServer();
            oSession.responseCode = 304;
            oSession["ui-backcolor"] = "Lavender";
        }
    }

    // This function is called immediately after a set of request headers has
    // been read from the client. This is typically too early to do much useful
    // work, since the body hasn't yet been read, but sometimes it may be useful.
    //
    // For instance, see 
    // http://blogs.msdn.com/b/fiddler/archive/2011/11/05/http-expect-continue-delays-transmitting-post-bodies-by-up-to-350-milliseconds.aspx
    // for one useful thing you can do with this handler.
    //
    // Note: oSession.requestBodyBytes is not available within this function!
    /*
    static function OnPeekAtRequestHeaders(oSession: Session) {
    }
    */

    //
    // If a given session has response streaming enabled, then the OnBeforeResponse function 
    // is actually called AFTER the response was returned to the client.
    //
    // In contrast, this OnPeekAtResponseHeaders function is called before the response headers are 
    // sent to the client (and before the body is read from the server).  Hence this is an opportune time 
    // to disable streaming (oSession.bBufferResponse = true) if there is something in the response headers 
    // which suggests that tampering with the response body is necessary.
    // 
    // Note: oSession.responseBodyBytes is not available within this function!
    //
    static function OnPeekAtResponseHeaders(oSession: Session) {
        //FiddlerApplication.Log.LogFormat("Session {0}: Response header peek shows status is {1}", oSession.id, oSession.responseCode);
        if (m_DisableCaching) {
            oSession.oResponse.headers.Remove("Expires");
            oSession.oResponse["Cache-Control"] = "no-cache";
        }

        if ((bpStatus>0) && (oSession.responseCode == bpStatus)) {
            oSession["x-breakresponse"]="status";
            oSession.bBufferResponse = true;
        }
        
        if ((null!=bpResponseURI) && oSession.uriContains(bpResponseURI)) {
            oSession["x-breakresponse"]="uri";
            oSession.bBufferResponse = true;
        }

    }

    static function OnBeforeResponse(oSession: Session) {
        if (m_Hide304s && oSession.responseCode == 304) {
            oSession["ui-hide"] = "true";
        }
        
        if (responseReady(oSession) && (oSession.responseCode == 200) && oSession.fullUrl.Contains("/resolve/service/wiki/impex/download")) { 
            oSession.SaveResponseBody("C:\\Users\\ncanedo\\Desktop\\RunBooks\\" + oSession.SuggestedFilename);
        }
        
        
        if (false) {
            if (responseReady(oSession)) {
                var baseUrl:String  = "/resolve/service";
                var filename:String = "";
            
                if (oSession.url.Contains(baseUrl) && !oSession.url.Contains("/client/poll")) {
                    var JSONResponse: Object    = Fiddler.WebFormats.JSON.JsonDecode(oSession.GetResponseBodyAsString());
                    var success:String          = JSONResponse.JSONObject["success"].ToString();
                    var startPosition:Number    = oSession.host.Length + baseUrl.Length + 1;
                    var endPosition:Number      = oSession.url.Length;
                
                    if (success.Equals("True")) {
                        success = "Success";
                    } else {
                        success = "False";
                    }
                
                    if (oSession.url.Contains("?")) {
                        endPosition = oSession.url.IndexOf("?");
                    }
                    
                    if (success.Equals("False")) {
                
                        filename = oSession.url.Substring(startPosition, endPosition - startPosition).Replace("/", ".") + "-" + success + "_" + oSession.SuggestedFilename.Replace("_", "");
                
                        oSession.SaveSession("C:\\Users\\ncanedo\\Desktop\\FiddlerSessions\\" + filename, false);
                    }
                }
            }
        }
    }

/*
    // This function executes just before Fiddler returns an error that it has 
    // itself generated (e.g. "DNS Lookup failure") to the client application.
    // These responses will not run through the OnBeforeResponse function above.
    static function OnReturningError(oSession: Session) {
    }
*/
/*
    // This function executes after Fiddler finishes processing a Session, regardless
    // of whether it succeeded or failed. Note that this typically runs AFTER the last
    // update of the Web Sessions UI listitem, so you must manually refresh the Session's
    // UI if you intend to change it.
    static function OnDone(oSession: Session) {
    }
*/

    // The Main() function runs everytime your FiddlerScript compiles
    static function Main() {
        var today: Date = new Date();
        FiddlerObject.StatusText = " CustomRules.js was loaded at: " + today;

        // Uncomment to add a "Server" column containing the response "Server" header, if present
        // UI.lvSessions.AddBoundColumn("Server", 50, "@response.server");

        // Uncomment to add a global hotkey (Win+G) that invokes the ExecAction method below...
        // UI.RegisterCustomHotkey(HotkeyModifiers.Windows, Keys.G, "screenshot"); 
    }

    // These static variables are used for simple breakpointing & other QuickExec rules 
    BindPref("fiddlerscript.ephemeral.bpRequestURI")
    public static var bpRequestURI:String = null;

    BindPref("fiddlerscript.ephemeral.bpResponseURI")
    public static var bpResponseURI:String = null;

    BindPref("fiddlerscript.ephemeral.bpMethod")
    public static var bpMethod: String = null;

    static var bpStatus:int = -1;
    static var uiBoldURI: String = null;
    static var gs_ReplaceToken: String = null;
    static var gs_ReplaceTokenWith: String = null;
    static var gs_OverridenHost: String = null;
    static var gs_OverrideHostWith: String = null;

    // The OnExecAction function is called by either the QuickExec box in the Fiddler window,
    // or by the ExecAction.exe command line utility.
    static function OnExecAction(sParams: String[]): Boolean {

        FiddlerObject.StatusText = "ExecAction: " + sParams[0];

        var sAction = sParams[0].toLowerCase();
        switch (sAction) {
            case "bold":
                if (sParams.Length<2) {uiBoldURI=null; FiddlerObject.StatusText="Bolding cleared"; return false;}
                uiBoldURI = sParams[1]; FiddlerObject.StatusText="Bolding requests for " + uiBoldURI;
                return true;
            case "bp":
                FiddlerObject.alert("bpu = breakpoint request for uri\nbpm = breakpoint request method\nbps=breakpoint response status\nbpafter = breakpoint response for URI");
                return true;
            case "bps":
                if (sParams.Length<2) {bpStatus=-1; FiddlerObject.StatusText="Response Status breakpoint cleared"; return false;}
                bpStatus = parseInt(sParams[1]); FiddlerObject.StatusText="Response status breakpoint for " + sParams[1];
                return true;
            case "bpv":
            case "bpm":
                if (sParams.Length<2) {bpMethod=null; FiddlerObject.StatusText="Request Method breakpoint cleared"; return false;}
                bpMethod = sParams[1].toUpperCase(); FiddlerObject.StatusText="Request Method breakpoint for " + bpMethod;
                return true;
            case "bpu":
                if (sParams.Length<2) {bpRequestURI=null; FiddlerObject.StatusText="RequestURI breakpoint cleared"; return false;}
                bpRequestURI = sParams[1]; 
                FiddlerObject.StatusText="RequestURI breakpoint for "+sParams[1];
                return true;
            case "bpa":
            case "bpafter":
                if (sParams.Length<2) {bpResponseURI=null; FiddlerObject.StatusText="ResponseURI breakpoint cleared"; return false;}
                bpResponseURI = sParams[1]; 
                FiddlerObject.StatusText="ResponseURI breakpoint for "+sParams[1];
                return true;
            case "overridehost":
                if (sParams.Length<3) {gs_OverridenHost=null; FiddlerObject.StatusText="Host Override cleared"; return false;}
                gs_OverridenHost = sParams[1].toLowerCase();
                gs_OverrideHostWith = sParams[2];
                FiddlerObject.StatusText="Connecting to [" + gs_OverrideHostWith + "] for requests to [" + gs_OverridenHost + "]";
                return true;
            case "urlreplace":
                if (sParams.Length<3) {gs_ReplaceToken=null; FiddlerObject.StatusText="URL Replacement cleared"; return false;}
                gs_ReplaceToken = sParams[1];
                gs_ReplaceTokenWith = sParams[2].Replace(" ", "%20");  // Simple helper
                FiddlerObject.StatusText="Replacing [" + gs_ReplaceToken + "] in URIs with [" + gs_ReplaceTokenWith + "]";
                return true;
            case "allbut":
            case "keeponly":
                if (sParams.Length<2) { FiddlerObject.StatusText="Please specify Content-Type to retain during wipe."; return false;}
                UI.actSelectSessionsWithResponseHeaderValue("Content-Type", sParams[1]);
                UI.actRemoveUnselectedSessions();
                UI.lvSessions.SelectedItems.Clear();
                FiddlerObject.StatusText="Removed all but Content-Type: " + sParams[1];
                return true;
            case "stop":
                UI.actDetachProxy();
                return true;
            case "start":
                UI.actAttachProxy();
                return true;
            case "cls":
            case "clear":
                UI.actRemoveAllSessions();
                return true;
            case "g":
            case "go":
                UI.actResumeAllSessions();
                return true;
            case "goto":
                if (sParams.Length != 2) return false;
                Utilities.LaunchHyperlink("http://www.google.com/search?hl=en&btnI=I%27m+Feeling+Lucky&q=" + Utilities.UrlEncode(sParams[1]));
                return true;
            case "help":
                Utilities.LaunchHyperlink("http://fiddler2.com/r/?quickexec");
                return true;
            case "hide":
                UI.actMinimizeToTray();
                return true;
            case "log":
                FiddlerApplication.Log.LogString((sParams.Length<2) ? "User couldn't think of anything to say..." : sParams[1]);
                return true;
            case "nuke":
                UI.actClearWinINETCache();
                UI.actClearWinINETCookies(); 
                return true;
            case "screenshot":
                UI.actCaptureScreenshot(false);
                return true;
            case "show":
                UI.actRestoreWindow();
                return true;
            case "tail":
                if (sParams.Length<2) { FiddlerObject.StatusText="Please specify # of sessions to trim the session list to."; return false;}
                UI.TrimSessionList(int.Parse(sParams[1]));
                return true;
            case "quit":
                UI.actExit();
                return true;
            case "dump":
                UI.actSelectAll();
                UI.actSaveSessionsToZip(CONFIG.GetPath("Captures") + "dump.saz");
                UI.actRemoveAllSessions();
                FiddlerObject.StatusText = "Dumped all sessions to " + CONFIG.GetPath("Captures") + "dump.saz";
                return true;

            default:
                if (sAction.StartsWith("http") || sAction.StartsWith("www.")) {
                    System.Diagnostics.Process.Start(sParams[0]);
                    return true;
                }
                else
                {
                    FiddlerObject.StatusText = "Requested ExecAction: '" + sAction + "' not found. Type HELP to learn more.";
                    return false;
                }
        }
    }
}