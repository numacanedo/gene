package com.gene.testcases.operation;

import com.gene.testcases.json.HandleResponse;
import com.gene.testcases.json.JsonPayload;
import com.gene.testcases.json.Test;
import com.gene.testcases.json.TestOp;

public class AjaxActionTaskProperties {
	public static final String BASE_NODE_TEMPLATE = "{\\\"uname\\\":\\\"\\\",\\\"umodule\\\":\\\"\\\",\\\"utype\\\":\\\"\\\",\\\"uvalue\\\":\\\"\\\",\\\"id\\\":\\\"\\\"}";
	
	public static void main(String[] args) {
		Test saveOperation = createStandardSaveTest();
		
		saveOperation.setJsonPayload(createStandardSavePayload("ListFilterTestName_1", "ListFilterTestModule_1", "Plain", "ListFilterTestValue_1"));
		saveOperation.setTestOps(createStandardSaveTestOp("sys_id_1", "uname_1", "umodule_1", "utype_1", "uvalue_1"));
		System.out.println(saveOperation + ",");
		
		saveOperation.setJsonPayload(createStandardSavePayload("ListFilterTestName_2", "ListFilterTestModule_2", "Plain", "ListFilterTestValue_2"));
		saveOperation.setTestOps(createStandardSaveTestOp("sys_id_2", "uname_2", "umodule_2", "utype_2", "uvalue_2"));
		System.out.println(saveOperation + ",");
		
		saveOperation.setJsonPayload(createStandardSavePayload("ListFilterTestName_3", "ListFilterTestModule_3", "Plain", "ListFilterTestValue_3"));
		saveOperation.setTestOps(createStandardSaveTestOp("sys_id_3", "uname_3", "umodule_3", "utype_3", "uvalue_3"));
		System.out.println(saveOperation + ",");
		
		saveOperation.setJsonPayload(createStandardSavePayload("ListFilterTestName_4", "ListFilterTestModule_4", "Plain", "ListFilterTestValue_4"));
		saveOperation.setTestOps(createStandardSaveTestOp("sys_id_4", "uname_4", "umodule_4", "utype_4", "uvalue_4"));
		System.out.println(saveOperation + ",");
		
		saveOperation.setJsonPayload(createStandardSavePayload("ListFilterTestName_5", "ListFilterTestModule_5", "Plain", "ListFilterTestValue_5"));
		saveOperation.setTestOps(createStandardSaveTestOp("sys_id_5", "uname_5", "umodule_5", "utype_5", "uvalue_5"));
		System.out.println(saveOperation + ",");
		
	}
	
	public static Test createBasicSave() {
		JsonPayload jsonPayload;
		Test saveOperation;
		
		saveOperation = createStandardSaveTest();
		jsonPayload = createStandardSavePayload("Name_", "Module_", "Plain", "Value_");
		
		saveOperation.setJsonPayload(jsonPayload);
		
		return saveOperation;
	}
	
	public static Test createStandardSaveTest() {
		Test saveOperation;
		HandleResponse handleResponse;
		TestOp testOps;
		
		String name			= "Save";
		String description	= "Save";
		String path			= "/atproperties/save";
		String method		= "POST";
		String requestType	= "JSON_APPLICATION";
		String responseType	= "JSON_APPLICATION";
		
		handleResponse		= createStandardSaveSucessHandleResponse();
		testOps				= createStandardSaveTestOp("sys_id", "uname", "umodule", "utype", "uvalue");
		
		saveOperation = new Test();
		saveOperation.setName(name);
		saveOperation.setDescription(description);
		saveOperation.setPath(path);
		saveOperation.setMethod(method);
		saveOperation.setRequestType(requestType);
		saveOperation.setResponseType(responseType);
		
		saveOperation.setHandleResponse(handleResponse);
		saveOperation.setTestOps(testOps);
		
		return saveOperation;
	}
	
	public static JsonPayload createStandardSavePayload(String uname, String umodule, String utype, String uvalue) {
		JsonPayload jsonPayload;
		
		jsonPayload = new JsonPayload();
		jsonPayload.setBaseNode(BASE_NODE_TEMPLATE);
		jsonPayload.addReplaceJsonField("$.uname", "PLAIN", uname);
		jsonPayload.addReplaceJsonField("$.umodule", "PLAIN", umodule);
		jsonPayload.addReplaceJsonField("$.utype", "PLAIN", utype);
		jsonPayload.addReplaceJsonField("$.uvalue", "PLAIN", uvalue);
		
		return jsonPayload;
	}
	
	public static HandleResponse createStandardSaveSucessHandleResponse() {
		HandleResponse handleResponse;
		
		handleResponse = new HandleResponse();
		handleResponse.setStatusCode("200");
		handleResponse.setFailLevel("ERROR");
		handleResponse.setFailureMessage("");
		handleResponse.addResponseCheck("JSON", "$.success"	, "EQUAL", "PLAIN", "true");
		handleResponse.addResponseCheck("JSON", "$.message"	, "EQUAL", "PLAIN", "");
		handleResponse.addResponseCheck("JSON", "$.records"	, "EQUAL", "PLAIN", "");
		handleResponse.addResponseCheck("JSON", "$.total"	, "EQUAL", "PLAIN", "0");
		
		return handleResponse;
	}
	
	public static TestOp createStandardSaveTestOp(String sys_idName, String unameName, String umoduleName, String utypeName, String uvalueName) {
		TestOp testOps;
		
		testOps = new TestOp();
		testOps.addElement("ASSIGN", "JSON", "$.data.sys_id"	, sys_idName);
		testOps.addElement("ASSIGN", "JSON", "$.data.uname"		, unameName);
		testOps.addElement("ASSIGN", "JSON", "$.data.umodule"	, umoduleName);
		testOps.addElement("ASSIGN", "JSON", "$.data.utype"		, utypeName);
		testOps.addElement("ASSIGN", "JSON", "$.data.uvalue"	, uvalueName);
		
		return testOps;
	}
}
