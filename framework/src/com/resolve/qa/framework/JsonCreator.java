package com.resolve.qa.framework;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonCreator
{
    String baseNode;
    List<LocalValue> replaceJsonField = new ArrayList<LocalValue>();
    List<ValidationLocalValue> validationReplaceJsonField = new ArrayList<ValidationLocalValue>();
    
    public JsonCreator()
    {

    }

    public String getBaseNode()
    {
        return baseNode;
    }

    public void setBaseNode(String baseNode)
    {
        this.baseNode = baseNode;
    }

    public List<LocalValue> getReplaceJsonField()
    {
        return replaceJsonField;
    }

    public void setReplaceJsonField(List<LocalValue> replaceJsonField)
    {
        this.replaceJsonField = replaceJsonField;
    }

    public List<ValidationLocalValue> getValidationReplaceJsonField()
    {
        return validationReplaceJsonField;
    }

    public void setValidationReplaceJsonField(List<ValidationLocalValue> validationReplaceJsonField)
    {
        this.validationReplaceJsonField = validationReplaceJsonField;
    }
    

   
}