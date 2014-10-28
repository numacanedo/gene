package com.resolve.qa.framework.numa;

import java.awt.List;

import com.resolve.qa.framework.numa.test.impl.ListTest;
import com.resolve.qa.framework.numa.testsuite.impl.CheckType;
import com.resolve.qa.framework.numa.testsuite.impl.HandleResponse;
import com.resolve.qa.framework.numa.testsuite.impl.JsonPayload;
import com.resolve.qa.framework.numa.testsuite.impl.ParamType;
import com.resolve.qa.framework.numa.testsuite.impl.Test;
import com.resolve.qa.framework.numa.testsuite.impl.TestCase;

public class AjaxWorksheetCRUD {
public static final String TEST_CASES_HOME = "C:\\Users\\ncanedo\\Desktop\\GenE\\Json";
	
	public static void main(String[] args) throws Exception {
		TestCase testCase = createTestCase();

		testCase.execute();
		testCase.jsonFile(TEST_CASES_HOME);
	}
	
	public static TestCase createTestCase() {
		TestCase testCase = new TestCase();
		testCase.setJsonFileName("testCase");
		testCase.setBaseURL("http://fiddler:8080/resolve/service");
		testCase.setUsername("admin");
		testCase.setPassword("resolve");
		
		// ============================== Test ==============================
		Test test = new Test();
		test.setName("");
		test.setDescription("");
		test.setPath("");
		test.setMethod(Test.METHOD.GET);
		test.setRequestType(Test.REQUEST_TYPE.JSON_APPLICATION);
		test.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		JsonPayload jsonPayload = new JsonPayload();
		jsonPayload.setBaseNode("");
		jsonPayload.addReplaceJsonField(null);
		jsonPayload.addValidationReplaceJsonField(null);
		
		HandleResponse handleResponse = new HandleResponse();
		handleResponse.setStatusCode(200);
		handleResponse.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		handleResponse.setFailureMessage("");
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.success", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "true"));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.message", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, null));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.data", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, null));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "0"));
		
		test.setJsonPayload(jsonPayload);
		test.setHandleResponse(handleResponse);
		// ------------------------------------------------------------------
		
		testCase.addTest(test);
		
		return testCase;
	}
	
	public Test createSaveTest() {
		return createSaveTest("", "", "", "", "", "", "", "", "", "", "", "", "", "");
	}
	
	public Test createSaveTest(
			String number
			, String alertId
			, String reference
			, String correlationId
			, String assignedTo
			, String assignedToName
			, String summary
			, String condition
			, String severity
			, String description
			, String workNotes
			, String debug
			, String sysOrg
			, String id) {
		// ============================== Test ==============================
		Test test = new Test();
		test.setName("SaveWorksheet");
		test.setDescription("Create a Worksheet filling all fields");
		test.setPath("/worksheet/save");
		test.setMethod(Test.METHOD.POST);
		test.setRequestType(Test.REQUEST_TYPE.JSON_APPLICATION);
		test.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		JsonPayload jsonPayload = new JsonPayload();
		test.setJsonPayloadBaseNode("{\"number\":\"\",\"alertId\":\"\",\"reference\":\"\",\"correlationId\":\"\",\"assignedTo\":\"\",\"assignedToName\":\"\",\"summary\":\"\",\"condition\":\"\",\"severity\":\"\",\"description\":\"\",\"workNotes\":\"\",\"debug\":\"\",\"sysOrg\":\"\",\"id\":\"\"}");
		test.setJsonPayloadReplaceJsonField(new ParamType("number"			, ParamType.TYPE.PLAIN, number));
		test.setJsonPayloadReplaceJsonField(new ParamType("alertId"			, ParamType.TYPE.PLAIN, alertId));
		test.setJsonPayloadReplaceJsonField(new ParamType("reference"		, ParamType.TYPE.PLAIN, reference));
		test.setJsonPayloadReplaceJsonField(new ParamType("correlationId"	, ParamType.TYPE.PLAIN, correlationId));
		test.setJsonPayloadReplaceJsonField(new ParamType("assignedTo"		, ParamType.TYPE.PLAIN, assignedTo));
		test.setJsonPayloadReplaceJsonField(new ParamType("assignedToName"	, ParamType.TYPE.PLAIN, assignedToName));
		test.setJsonPayloadReplaceJsonField(new ParamType("summary"			, ParamType.TYPE.PLAIN, summary));
		test.setJsonPayloadReplaceJsonField(new ParamType("condition"		, ParamType.TYPE.PLAIN, condition));
		test.setJsonPayloadReplaceJsonField(new ParamType("severity"		, ParamType.TYPE.PLAIN, severity));
		test.setJsonPayloadReplaceJsonField(new ParamType("description"		, ParamType.TYPE.PLAIN, description));
		test.setJsonPayloadReplaceJsonField(new ParamType("workNotes"		, ParamType.TYPE.PLAIN, workNotes));
		test.setJsonPayloadReplaceJsonField(new ParamType("debug"			, ParamType.TYPE.PLAIN, debug));
		test.setJsonPayloadReplaceJsonField(new ParamType("sysOrg"			, ParamType.TYPE.PLAIN, sysOrg));
		test.setJsonPayloadReplaceJsonField(new ParamType("id"				, ParamType.TYPE.PLAIN, id));
		
		HandleResponse handleResponse = new HandleResponse();
		handleResponse.setStatusCode(200);
		handleResponse.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		handleResponse.setFailureMessage("");
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.success", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "true"));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.message", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, null));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.data", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, null));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "0"));
		
		test.setHandleResponse(handleResponse);
		// ------------------------------------------------------------------
		
		return test;
	}
	
	
	public ListTest createListTest() {
		ListTest test = new ListTest();
		
		
		return test;
	}
	
}
