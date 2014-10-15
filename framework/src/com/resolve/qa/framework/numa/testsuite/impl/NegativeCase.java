package com.resolve.qa.framework.numa.testsuite.impl;

import java.util.ArrayList;
import java.util.List;

public class NegativeCase extends JsonAbstract {
    private HandleResponse handleResponse;
    private List<TestOp> testOps = new ArrayList<TestOp>();
    private List<InvalidCase> invalidCase = new ArrayList<InvalidCase>();
    
    public NegativeCase() {
    	super();
    }

    public NegativeCase(HandleResponse handleResponse, List<TestOp> testOps,
			List<InvalidCase> invalidCase) {
		super();
		this.handleResponse = handleResponse;
		this.testOps = testOps;
		this.invalidCase = invalidCase;
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

    public List<InvalidCase> getInvalidCase() {
        return invalidCase;
    }

    public void setInvalidCase(List<InvalidCase> invalidCase) {
        this.invalidCase = invalidCase;
    }
    
    public void addTestOp(TestOp testOp) {
    	this.testOps.add(testOp);
    }
    
    public void addInvalidCase(InvalidCase invalidCase) {
    	this.invalidCase.add(invalidCase);
    }
}
