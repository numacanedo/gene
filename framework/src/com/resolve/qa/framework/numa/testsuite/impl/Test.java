package com.resolve.qa.framework.numa.testsuite.impl;

import java.util.ArrayList;
import java.util.List;

import com.resolve.qa.framework.numa.testsuite.impl.CheckType.COMPARE_METHOD;
import com.resolve.qa.framework.numa.testsuite.impl.HandleResponse.FAIL_LEVEL;
import com.resolve.qa.framework.numa.testsuite.impl.ParamType.TYPE;

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
	
    private String name;
    private String description;
    private String path;
    private METHOD method;
    private REQUEST_TYPE requestType;
    private RESPONSE_TYPE responseType;
    private List<CheckType> exeCondition = new ArrayList<CheckType>();
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
		this.setHandleResponse(new HandleResponse(200, HandleResponse.FAIL_LEVEL.ERROR, "", null));
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
    
    public REQUEST_TYPE getRequestType() {
        return requestType;
    }

    public void setRequestType(REQUEST_TYPE requestType) {
        this.requestType = requestType;
    }
    
    public RESPONSE_TYPE getResponseType() {
        return responseType;
    }

    public void setResponseType(RESPONSE_TYPE responseType) {
        this.responseType = responseType;
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
    
    public void addValidationQueryParam(ValidationType validationQueryParam) {
    	this.validationQueryParams.add(validationQueryParam);
    }
    
    public void addRequestForm(ParamType requestForm) {
    	this.requestForm.add(requestForm);
    }
    
    public void addRequestForm(String key, TYPE type, String value) {
    	this.requestForm.add(new ParamType(key, type, value));
    }
    
    public void addValidationRequestForm(ValidationType validationRequestForm) {
    	this.validationRequestForm.add(validationRequestForm);
    }
    
    public void addTestOp(TestOp testOp) {
    	if (this.testOps == null) {
    		this.setTestOps(new ArrayList<TestOp>());
    	}
    	
    	this.testOps.add(testOp);
    }
    
    public void addEndConditon(EndConditon endConditon) {
    	this.endConditons.add(endConditon);
    }
    
    public Test copy() {
    	return (Test)abstractCopy();
    }
    
    public void setStatusCode(int statusCode) {
		this.getHandleResponse().setStatusCode(statusCode);
	}
	
	public void setFailLevel(FAIL_LEVEL failLevel) {
		this.getHandleResponse().setFailLevel(failLevel);
	}
	
	public void setFailureMessage(String failureMessage) {
		this.getHandleResponse().setFailureMessage(failureMessage);
	}
	
	public void cleanResponseChecks() {
		this.getHandleResponse().setResponseChecks(null);
	}
	
	public void addResponseCheck(CheckType responseCheck){
		if (this.getHandleResponse().getResponseChecks() == null) {
			this.getHandleResponse().setResponseChecks(new ArrayList<CheckType>());
		}
		
    	this.getHandleResponse().addResponseCheck(responseCheck);
    }
	
	public void addJsonPlainResponseCheck(String sourceKey, COMPARE_METHOD compareMethod, String targetKey){
    	this.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, sourceKey, compareMethod, CheckType.TARGET_TYPE.PLAIN, targetKey));
    }
    
    public void addJsonEqualPlainResponseCheck(String sourceKey, String targetKey){
    	this.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, sourceKey, CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, targetKey));
    }
    
    public void setResponseCheck(int index, CheckType responseCheck) {
    	this.getHandleResponse().getResponseChecks().set(index, responseCheck);
    }
    
    public void cleanQueryParams() {
    	this.setQueryParams(null);
    }
    
    public void cleanTestOps() {
    	this.setTestOps(null);
    }
    
    public void setQueryParam(int index, ParamType queryParam) {
    	this.getQueryParams().set(index, queryParam);
    }
    
    public void addQueryParam(ParamType paramType) {
    	this.getQueryParams().add(paramType);
    }
    
    public void setQueryParam(ParamType paramType) {
    	boolean found = false;
    	
    	if (this.getQueryParams() != null) {
    		for (int index = 0; index < this.getQueryParams().size(); index++) {
    			if(this.getQueryParams().get(index).getKey().equalsIgnoreCase(paramType.getKey())) {
    				this.getQueryParams().set(index, paramType);
    				found = true;
    			}
    		}
    	} else {
    		this.setQueryParams(new ArrayList<ParamType>());
    	}
    	
    	if (!found) {
    		this.getQueryParams().add(paramType);
    	}
    }
    
    public void setResponseCheck(CheckType responseCheck) {
    	boolean found = false;
    	
    	if (this.getHandleResponse() != null && this.getHandleResponse().getResponseChecks() != null) {
    		for (int index = 0; index < this.getHandleResponse().getResponseChecks().size(); index++) {
    			if(this.getHandleResponse().getResponseChecks().get(index).getSourceKey().equalsIgnoreCase(responseCheck.getSourceKey())) {
    				this.getHandleResponse().getResponseChecks().set(index, responseCheck);
    				
    				found = true;
    			}
    		}
    	} else {
    		if (this.getHandleResponse() == null) {
    			this.setHandleResponse(new HandleResponse(200, HandleResponse.FAIL_LEVEL.ERROR, "", null));
    		}
    		this.getHandleResponse().setResponseChecks(new ArrayList<CheckType>());
    	}
    	
    	if (!found) {
    		this.getHandleResponse().getResponseChecks().add(responseCheck);
    	}
    }
    
    public void deleteResponseCheck(String sourceKey) {
    	
    	if (this.getHandleResponse().getResponseChecks() != null) {
    		for (int index = 0; index < this.getHandleResponse().getResponseChecks().size(); index++) {
    			if(this.getHandleResponse().getResponseChecks().get(index).getSourceKey().equalsIgnoreCase(sourceKey)) {
    				this.getHandleResponse().getResponseChecks().remove(index);
    				break;
    			}
    		}
    	}
    }
    
    public void setJsonEqualPlainResponseCheck(String sourceKey, String targetKey){
    	this.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, sourceKey, CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, targetKey));
    }
}
