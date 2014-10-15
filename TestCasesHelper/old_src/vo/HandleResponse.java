package com.gene.testcases.vo;

import java.util.ArrayList;
import java.util.List;

import com.gene.testcases.util.Util;

public class HandleResponse {
	private String statusCode;
	private String failLevel;
	private String failureMessage;
	private ResponseCheck responseChecks;
	
	public HandleResponse() {
		super();
		statusCode = null;
		failLevel = null;
		failureMessage = null;
		responseChecks = new ResponseCheck();
	}
	
	public HandleResponse(String statusCode, String failLevel, String failureMessage
			, ResponseCheck responseChecks) {
		super();
		this.statusCode = statusCode;
		this.failLevel = failLevel;
		this.failureMessage = failureMessage;
		this.responseChecks = responseChecks;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getFailLevel() {
		return failLevel;
	}
	public void setFailLevel(String failLevel) {
		this.failLevel = failLevel;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	public ResponseCheck getResponseChecks() {
		return responseChecks;
	}
	public void setResponseChecks(ResponseCheck responseChecks) {
		this.responseChecks = responseChecks;
	}
	public void addResponseCheck(ResponseCheckElement responseCheck) {
		this.responseChecks.addElement(responseCheck);
	}
	public void addResponseCheck(String sourceType, String sourceKey,
			String compareMethod, String targetType, String targetKey) {
		this.responseChecks.addElement(new ResponseCheckElement(sourceType, sourceKey, compareMethod, targetType, targetKey));
	}
	@Override
	public String toString() {
		StringBuffer string;
		
		string = new StringBuffer();
		string.append("\"handleResponse\": {");
		string.append(Util.toJsonAttributeString("statusCode"	 	, statusCode) + ",");
		string.append(Util.toJsonAttributeString("failLevel"	 	, failLevel) + ",");
		string.append(Util.toJsonAttributeString("failureMessage"	, failureMessage));
		
		if (null != responseChecks && responseChecks.size() > 0) {
			string.append(",");
			string.append(responseChecks.toString());
		}
		
		string.append("}");
		
		return string.toString();
	}
}
