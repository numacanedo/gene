package com.resolve.qa.framework;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpectResponse
{
    int statusCode;
    @JsonProperty(required = true)
    FAIL_LEVEL failLevel;
    String failureMessage;
    List<ConditionCheck> responseChecks = new ArrayList<ConditionCheck>();

    
    public ExpectResponse()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public ExpectResponse(ExpectResponse temp)
    {
        super();
        this.statusCode = temp.getStatusCode();
        this.failLevel = temp.getFailLevel();
        this.failureMessage = temp.getFailureMessage() ;
        this.responseChecks = new ArrayList<ConditionCheck>();
        this.responseChecks.addAll(temp.getResponseChecks());
    }

    public enum FAIL_LEVEL{
        ERROR, WARNING
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public FAIL_LEVEL getFailLevel()
    {
        return failLevel;
    }

    public void setFailLevel(FAIL_LEVEL failLevel)
    {
        this.failLevel = failLevel;
    }

    public String getFailureMessage()
    {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage)
    {
        this.failureMessage = failureMessage;
    }

    public List<ConditionCheck> getResponseChecks()
    {
        return responseChecks;
    }

    public void setResponseChecks(List<ConditionCheck> responseChecks)
    {
        this.responseChecks = responseChecks;
    }

    @Override
    public String toString()
    {
        return "ExpectResponse [statusCode=" + statusCode + ", failLevel=" + failLevel + ", failureMessage=" + failureMessage + ", responseChecks=" + responseChecks + "]";
    }
    
   
}
