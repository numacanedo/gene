package com.resolve.qa.framework.common;

public enum MEDIA_TYPE
{
    JSON_APPLICATION("application/json"),
    URLENCODED_FORM_APPLICATION("application/x-www-form-urlencoded");
    
    private String value;
    private MEDIA_TYPE(String value)
    {
       this.value = value;
    }

    public String toString()
    {
       return this.value; 
    }
}
