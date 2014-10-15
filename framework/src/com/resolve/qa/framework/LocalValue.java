package com.resolve.qa.framework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.resolve.qa.framework.common.LOCAL_VALUE_TYPE;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalValue
{
    @JsonProperty(required = true)
    String key;
    @JsonProperty(required = true)
    LOCAL_VALUE_TYPE type;
    String value;
    
    public LocalValue()
    {

    }
    
    public LocalValue(String key, LOCAL_VALUE_TYPE type, String value)
    {
        this.key = key;
        this.type = type;
        this.value = value;
    }
    
    public String getKey()
    {
        return key;
    }
    public void setKey(String key)
    {
        this.key = key;
    }
    public LOCAL_VALUE_TYPE getType()
    {
        return type;
    }
    public void setType(LOCAL_VALUE_TYPE type)
    {
        this.type = type;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public String getRealValue(Map<String, Object> testData) throws UnsupportedEncodingException {
        switch (type)
        {
            case PLAIN:
                return value;//URLEncoder.encode(value,"UTF-8").replace("+", "%20");
            case REFERENCE:
                if(value.contains("(") && value.contains(")")) {
                    String temp = value.substring(value.indexOf('(')+1, value.indexOf(')'));
                    value=value.substring(0,value.indexOf('('));
                    if(!testData.containsKey(value)) {
                        testData.put(value,temp);
                    }
                }
                return (String)testData.get(value);
        }
        return null;
    }
    @Override
    public String toString()
    {
        return "LocalValue [key=" + key + ", type=" + type + ", value=" + value + "]";
    }
    
    
}

