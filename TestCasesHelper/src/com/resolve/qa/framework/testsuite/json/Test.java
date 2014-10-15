package com.resolve.qa.framework.testsuite.json;

import java.util.ArrayList;
import java.util.List;

public class Test extends JsonAbstract {
	public static enum METHOD {
		GET, POST, PUT, DELETE, HEAD, GETOBJ, POSTOBJ, DELETEOBJ, GETPAGE, GETOBJPAGE, POSTPAGE, HEADPAGE
	}
	
	public static enum REQUEST_TYPE {
		JSON_APPLICATION, URLENCODED_FORM_APPLICATION
	}
	
	public static enum RESPONSE_TYPE {
		JSON_APPLICATION, URLENCODED_FORM_APPLICATION
	}
	
	private List<CheckType> exeCondition = new ArrayList<CheckType>();
    private String name;
    private String description;
    private String path;
    private METHOD method;
    private REQUEST_TYPE requestType;
    private RESPONSE_TYPE responseType;
    private List<ParamType> queryParams = new ArrayList<ParamType>();
    private List<ValidationType> validationQueryParams = new ArrayList<ValidationType>();
    private List<ParamType> requestForm = new ArrayList<ParamType>();
    private List<ValidationType> validationRequestForm = new ArrayList<ValidationType>();
    private JsonPayload jsonPayload;
    private HandleResponse handleResponse;
    private List<TestOp> testOps = new ArrayList<TestOp>();
    private List<EndConditon> endConditons = new ArrayList<EndConditon>();
    
	public Test() {
		super();
	}
	
    public Test(List<CheckType> exeCondition, String name,
			String description, String path, METHOD method, REQUEST_TYPE requestType,
			RESPONSE_TYPE responseType, List<ParamType> queryParams,
			List<ValidationType> validationQueryParams,
			List<ParamType> requestForm,
			List<ValidationType> validationRequestForm,
			JsonPayload jsonPayload, HandleResponse handleResponse,
			List<TestOp> testOps, List<EndConditon> endConditons) {
		super();
		this.exeCondition = exeCondition;
		this.name = name;
		this.description = description;
		this.path = path;
		this.method = method;
		this.requestType = requestType;
		this.responseType = responseType;
		this.queryParams = queryParams;
		this.validationQueryParams = validationQueryParams;
		this.requestForm = requestForm;
		this.validationRequestForm = validationRequestForm;
		this.jsonPayload = jsonPayload;
		this.handleResponse = handleResponse;
		this.testOps = testOps;
		this.endConditons = endConditons;
	}
    
    public Test(List<CheckType> exeCondition, String name,
			String description, String path, String method, String requestType,
			String responseType, List<ParamType> queryParams,
			List<ValidationType> validationQueryParams,
			List<ParamType> requestForm,
			List<ValidationType> validationRequestForm,
			JsonPayload jsonPayload, HandleResponse handleResponse,
			List<TestOp> testOps, List<EndConditon> endConditons) {
		super();
		this.exeCondition = exeCondition;
		this.name = name;
		this.description = description;
		this.path = path;
		this.method = METHOD.valueOf(method);
		this.requestType = REQUEST_TYPE.valueOf(requestType);
		this.responseType = RESPONSE_TYPE.valueOf(responseType);
		this.queryParams = queryParams;
		this.validationQueryParams = validationQueryParams;
		this.requestForm = requestForm;
		this.validationRequestForm = validationRequestForm;
		this.jsonPayload = jsonPayload;
		this.handleResponse = handleResponse;
		this.testOps = testOps;
		this.endConditons = endConditons;
	}

	public List<CheckType> getExeCondition() {
        return exeCondition;
    }

    public void setExeCondition(List<CheckType> exeCondition) {
        this.exeCondition = exeCondition;
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

    public METHOD getMethod() {
        return method;
    }

    public void setMethod(METHOD method) {
        this.method = method;
    }
    
    public void setMethod(String method) {
        this.method = METHOD.valueOf(method);
    }

    public REQUEST_TYPE getRequestType() {
        return requestType;
    }

    public void setRequestType(REQUEST_TYPE requestType) {
        this.requestType = requestType;
    }
    
    public void setRequestType(String requestType) {
        this.requestType = REQUEST_TYPE.valueOf(requestType);
    }

    public RESPONSE_TYPE getResponseType() {
        return responseType;
    }

    public void setResponseType(RESPONSE_TYPE responseType) {
        this.responseType = responseType;
    }
    
    public void setResponseType(String responseType) {
        this.responseType = RESPONSE_TYPE.valueOf(responseType);
    }

    public List<ParamType> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<ParamType> queryParams) {
        this.queryParams = queryParams;
    }

    public List<ValidationType> getValidationQueryParams() {
        return validationQueryParams;
    }

    public void setValidationQueryParams(List<ValidationType> validationQueryParams) {
        this.validationQueryParams = validationQueryParams;
    }

    public List<ParamType> getRequestForm() {
        return requestForm;
    }

    public void setRequestForm(List<ParamType> requestForm) {
        this.requestForm = requestForm;
    }

    public List<ValidationType> getValidationRequestForm() {
        return validationRequestForm;
    }

    public void setValidationRequestForm(List<ValidationType> validationRequestForm) {
        this.validationRequestForm = validationRequestForm;
    }

    public JsonPayload getJsonPayload() {
        return jsonPayload;
    }

    public void setJsonPayload(JsonPayload jsonPayload) {
        this.jsonPayload = jsonPayload;
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

    public List<EndConditon> getEndConditons() {
        return endConditons;
    }

    public void setEndConditons(List<EndConditon> endConditons) {
        this.endConditons = endConditons;
    }
    
    public void addExeCondition(CheckType exeCondition) {
    	this.exeCondition.add(exeCondition);
    }
    
    public void addQueryParam(ParamType queryParam) {
    	this.queryParams.add(queryParam);
    }
    
    public void addValidationQueryParam(ValidationType validationQueryParam) {
    	this.validationQueryParams.add(validationQueryParam);
    }
    
    public void addRequestForm(ParamType requestForm) {
    	this.requestForm.add(requestForm);
    }
    
    public void addValidationRequestForm(ValidationType validationRequestForm) {
    	this.validationRequestForm.add(validationRequestForm);
    }
    
    public void addTestOp(TestOp testOp) {
    	this.testOps.add(testOp);
    }
    
    public void addEndConditon(EndConditon endConditon) {
    	this.endConditons.add(endConditon);
    }
}
