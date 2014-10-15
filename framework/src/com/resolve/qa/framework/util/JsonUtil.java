package com.resolve.qa.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Collection;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;

import com.jayway.jsonpath.JsonPath;

public class JsonUtil
{
    
    public static String getJsonValue(JsonNode jsonObj, String key) throws Exception {
        if(key==null || key.equals("")) {
            return jsonObj.toString();
        }
        return JsonPath.read(jsonObj.toString(), key).toString();
    }
    
    public static int getJsonSize(JsonNode jsonObj, String key) throws Exception {
        if(key==null || key.equals("")) {
            return 0;
        }
        Object obj = JsonPath.read(jsonObj.toString(), key);
        if(obj instanceof Map<?,?>) return ((Map)obj).size();
        else return ((Collection<?>)obj).size();
    }
    
    public static JsonNode setJsonValue(JsonNode jsonObj, String key, String value) throws Exception {
        ObjectMapper tempMapper = new ObjectMapper();
        JsonPath path= JsonPath.compile(key);
        return tempMapper.convertValue(path.write(jsonObj.toString(), value), JsonNode.class);
    }

    public static void putFileDataToObject(Object obj, File jsonFile)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            obj  = mapper.readValue(jsonFile, obj.getClass());
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 /*   
    public final static String DOCUMENT =
                    "{ \"store\": {\n" +
                            "    \"book\": [ \n" +
                            "      { \"category\": \"reference\",\n" +
                            "        \"author\": \"Nigel Rees\",\n" +
                            "        \"title\": \"Sayings of the Century\",\n" +
                            "        \"display-price\": 8.95\n" +
                            "      },\n" +
                            "      { \"category\": \"fiction\",\n" +
                            "        \"author\": \"Evelyn Waugh\",\n" +
                            "        \"title\": \"Sword of Honour\",\n" +
                            "        \"display-price\": 12.99\n" +
                            "      },\n" +
                            "      { \"category\": \"fiction\",\n" +
                            "        \"author\": \"Herman Melville\",\n" +
                            "        \"title\": \"Moby Dick\",\n" +
                            "        \"isbn\": \"0-553-21311-3\",\n" +
                            "        \"display-price\": 8.99\n" +
                            "      },\n" +
                            "      { \"category\": \"fiction\",\n" +
                            "        \"author\": \"J. R. R. Tolkien\",\n" +
                            "        \"title\": \"The Lord of the Rings\",\n" +
                            "        \"isbn\": \"0-395-19395-8\",\n" +
                            "        \"display-price\": 22.99\n" +
                            "      }\n" +
                            "    ],\n" +
                            "    \"bicycle\": {\n" +
                            "      \"color\": \"red\",\n" +
                            "      \"display-price\": 19.95,\n" +
                            "      \"foo:bar\": \"fooBar\",\n" +
                            "      \"dot.notation\": \"new\",\n" +
                            "      \"dash-notation\": \"dashes\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}";
    
    public static void main(String[] args) throws Exception
    {
        ObjectMapper tempMapper = new ObjectMapper();
        System.out.print(getJsonSize(tempMapper.readTree(DOCUMENT), "$.store.book"));

    }*/
}
