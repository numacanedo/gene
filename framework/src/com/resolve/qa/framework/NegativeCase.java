package com.resolve.qa.framework;

import java.util.ArrayList;

public class NegativeCase
{
    ExpectResponse handleResponse;
    ArrayList<TestOp> testOps;
    ArrayList<InvalidCase> invalidCase = new ArrayList<InvalidCase>();
    public NegativeCase()
    {
        super();
        // TODO Auto-generated constructor stub
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
    public ArrayList<InvalidCase> getInvalidCase()
    {
        return invalidCase;
    }
    public void setInvalidCase(ArrayList<InvalidCase> invalidCase)
    {
        this.invalidCase = invalidCase;
    }
    
    
}
