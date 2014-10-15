package com.gene.testcases.vo;

import com.gene.testcases.util.Util;

public class Test {
	private String name;
	private String description;
	private String path;
	private String method;
	private String requestType;
	private String responseType;
	private Payload jsonPayload;
	private HandleResponse handleResponse;
	private TestOp testOps;
	
	public Test() {
		super();
	}
	
	public Test(String name, String description, String path,
			String method, String requestType, String responseType,
			Payload jsonPayload, HandleResponse handleResponse,
			TestOp testOps) {
		super();
		this.name = name;
		this.description = description;
		this.path = path;
		this.method = method;
		this.requestType = requestType;
		this.responseType = responseType;
		this.jsonPayload = jsonPayload;
		this.handleResponse = handleResponse;
		this.testOps = testOps;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public Payload getJsonPayload() {
		return jsonPayload;
	}
	public void setJsonPayload(Payload jsonPayload) {
		this.jsonPayload = jsonPayload;
	}
	public HandleResponse getHandleResponse() {
		return handleResponse;
	}
	public void setHandleResponse(HandleResponse handleResponse) {
		this.handleResponse = handleResponse;
	}
	public TestOp getTestOps() {
		return testOps;
	}
	public void setTestOps(TestOp testOps) {
		this.testOps = testOps;
	}
	public void addTestOp(TestOpElement element) {
		this.testOps.addElement(element);
	}
	public void addTestOp(String method, String sourceType, String sourceKey,
			String targetKey) {
		this.testOps.addElement(new TestOpElement(method, sourceType, sourceKey, targetKey));
	}

	@Override
	public String toString() {
		StringBuffer string;

		string = new StringBuffer();
		string.append("{");
		string.append(Util.toJsonAttributeString("name"			, name) + ",");
		string.append(Util.toJsonAttributeString("description"	, description) + ",");
		string.append(Util.toJsonAttributeString("path"			, path) + ",");
		string.append(Util.toJsonAttributeString("method"		, method) + ",");
		string.append(Util.toJsonAttributeString("requestType"	, requestType) + ",");
		string.append(Util.toJsonAttributeString("responseType"	, responseType));
		
		if (jsonPayload != null) {
			string.append(",");
			string.append(jsonPayload.toString());
		}
		
		if (handleResponse != null) {
			string.append(",");
			string.append(handleResponse.toString());
		}
		
		if (testOps != null) {
			string.append(",");
			string.append(testOps.toString());
		}
		
		string.append("}");
		return string.toString();
	}
	
	
}
