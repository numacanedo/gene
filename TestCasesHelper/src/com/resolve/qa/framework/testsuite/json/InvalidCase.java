package com.resolve.qa.framework.testsuite.json;

import java.util.ArrayList;
import java.util.List;

public class InvalidCase extends JsonAbstract {
    private String value;
    private HandleResponse handleResponse;
    private List<TestOp> testOps = new ArrayList<TestOp>();

    public InvalidCase() {
		super();
	}
    
    public InvalidCase(String value, HandleResponse handleResponse) {
		super();
		this.value = value;
		this.handleResponse = handleResponse;
	}
    
	public InvalidCase(String value, HandleResponse handleResponse,
			List<TestOp> testOps) {
		super();
		this.value = value;
		this.handleResponse = handleResponse;
		this.testOps = testOps;
	}
	
	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HandleResponse getHandleResponse() {
        return handleResponse;
    }

    public void setHandleResponse(HandleResponse handleResponse) {
        this.handleResponse = handleResponse;
    }

    public List<TestOp> getTestOps() {
        return testOps;
    }

    public void setTestOps(List<TestOp> testOps) {
        this.testOps = testOps;
    }
    
    public void addTestOp(TestOp testOp){
    	this.testOps.add(testOp);
    }
}
