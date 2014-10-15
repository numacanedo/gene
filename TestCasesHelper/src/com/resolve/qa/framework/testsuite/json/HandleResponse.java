package com.resolve.qa.framework.testsuite.json;

import java.util.ArrayList;
import java.util.List;

public class HandleResponse extends JsonAbstract {
	public static enum FAIL_LEVEL{
		ERROR, WARNING
	}
	
	private int statusCode;
    private FAIL_LEVEL failLevel;
    private String failureMessage;
    private List<CheckType> responseChecks = new ArrayList<CheckType>();
    
    
    public HandleResponse() {
		super();
	}
    
	public HandleResponse(int statusCode, FAIL_LEVEL failLevel,
			String failureMessage, List<CheckType> responseChecks) {
		super();
		this.statusCode = statusCode;
		this.failLevel = failLevel;
		this.failureMessage = failureMessage;
		this.responseChecks = responseChecks;
	}
	
	public HandleResponse(int statusCode, String failLevel,
			String failureMessage, List<CheckType> responseChecks) {
		super();
		this.statusCode = statusCode;
		this.failLevel = FAIL_LEVEL.valueOf(failLevel);
		this.failureMessage = failureMessage;
		this.responseChecks = responseChecks;
	}

	public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public FAIL_LEVEL getFailLevel() {
        return failLevel;
    }

    public void setFailLevel(FAIL_LEVEL failLevel) {
        this.failLevel = failLevel;
    }
    
    public void setFailLevel(String failLevel) {
        this.failLevel = FAIL_LEVEL.valueOf(failLevel);
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public List<CheckType> getResponseChecks() {
        return responseChecks;
    }

    public void setResponseChecks(List<CheckType> responseChecks) {
        this.responseChecks = responseChecks;
    }
    
    public void addResponseCheck(CheckType responseCheck){
    	this.responseChecks.add(responseCheck);
    }
}
