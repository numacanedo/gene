package com.resolve.qa.framework;

import java.util.ArrayList;

public class InvalidCase
{
    String value;
    ExpectResponse handleResponse;
    ArrayList<TestOp> testOps = new ArrayList<TestOp>();
    public InvalidCase()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public InvalidCase(String value, ExpectResponse handleResponse, ArrayList<TestOp> testOps)
    {
        super();
        this.value = value;
        this.handleResponse = handleResponse;
        this.testOps = testOps;
    }

    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
    public ExpectResponse getHandleResponse()
    {
        return handleResponse;
    }
    public void setHandleResponse(ExpectResponse handleResponse)
    {
        this.handleResponse = handleResponse;
    }
    public ArrayList<TestOp> getTestOps()
    {
        return testOps;
    }
    public void setTestOps(ArrayList<TestOp> testOps)
    {
        this.testOps = testOps;
    }

    
}
