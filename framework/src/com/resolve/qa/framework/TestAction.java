package com.resolve.qa.framework;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestAction
{
    @JsonProperty(required = true)
    List<TestOp> testOps = new ArrayList<TestOp>();
    
    public TestAction() {
        
    }
    
    public TestAction(List<TestOp> testOps) {
        this.testOps=testOps;
    }
    
    public void setTestOp(List<TestOp> testOps) {
        this.testOps=testOps;
    }
    
    public List<TestOp> getTestOp() {
        return this.testOps;
    }
    
    public void addTestOp(TestOp testOp) {
        this.testOps.add(testOp);
    }

    @Override
    public String toString()
    {
        return "TestAction [testOps=" + testOps + "]";
    }
    
    
}
