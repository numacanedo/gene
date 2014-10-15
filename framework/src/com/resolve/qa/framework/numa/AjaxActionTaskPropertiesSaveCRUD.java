package com.resolve.qa.framework.numa;

import com.resolve.qa.framework.numa.testsuite.impl.CheckType;
import com.resolve.qa.framework.numa.testsuite.impl.HandleResponse;
import com.resolve.qa.framework.numa.testsuite.impl.JsonPayload;
import com.resolve.qa.framework.numa.testsuite.impl.Test;
import com.resolve.qa.framework.numa.testsuite.impl.TestCase;

public class AjaxActionTaskPropertiesSaveCRUD {
public static final String TEST_CASES_HOME = "C:\\Users\\ncanedo\\Desktop\\GenE\\Json";
	
	public static void main(String[] args) throws Exception {
		TestCase testCase1 = createTestCase1();
		TestCase testCase2 = createTestCase2();

		testCase1.execute();
//		testCase1.jsonFile(TEST_CASES_HOME);
		
	}
	
	public static TestCase createTestCase1() {
		TestCase testCase = new TestCase();
		testCase.setJsonFileName("testCase");
		testCase.setBaseURL("http://fiddler:8080/resolve/service");
		testCase.setUsername("admin");
		testCase.setPassword("resolve");
		
		// ============================== Test ==============================
		Test test1 = new Test();
		test1.setName("");
		test1.setDescription("");
		test1.setPath("");
		test1.setMethod(Test.METHOD.GET);
		test1.setRequestType(Test.REQUEST_TYPE.JSON_APPLICATION);
		test1.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		JsonPayload jsonPayload1 = new JsonPayload();
		jsonPayload1.setBaseNode("");
		jsonPayload1.addReplaceJsonField(null);
		jsonPayload1.addValidationReplaceJsonField(null);
		
		HandleResponse handleResponse1 = new HandleResponse();
		handleResponse1.setStatusCode(200);
		handleResponse1.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		handleResponse1.setFailureMessage("");
		handleResponse1.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.success", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "true"));
		handleResponse1.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.message", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, null));
		handleResponse1.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.data", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, null));
		handleResponse1.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "0"));
		
		test1.setJsonPayload(jsonPayload1);
		test1.setHandleResponse(handleResponse1);
		// ------------------------------------------------------------------
		// ============================== Test ==============================
		Test test2 = new Test();
		test2.setName("");
		test2.setDescription("");
		test2.setPath("");
		test2.setMethod(Test.METHOD.GET);
		test2.setRequestType(Test.REQUEST_TYPE.JSON_APPLICATION);
		test2.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		JsonPayload jsonPayload2 = new JsonPayload();
		jsonPayload2.setBaseNode("");
		jsonPayload2.addReplaceJsonField(null);
		jsonPayload2.addValidationReplaceJsonField(null);
		
		HandleResponse handleResponse2 = new HandleResponse();
		handleResponse2.setStatusCode(200);
		handleResponse2.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		handleResponse2.setFailureMessage("");
		handleResponse2.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.success", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "true"));
		handleResponse2.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.message", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, null));
		handleResponse2.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.data", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, null));
		handleResponse2.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "0"));
		
		test2.setJsonPayload(jsonPayload2);
		test2.setHandleResponse(handleResponse2);
		// ------------------------------------------------------------------
		testCase.addTest(test2);
		
		return testCase;
	}
	
	public static TestCase createTestCase2() {
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
}
