package com.gene.testcases;

import java.io.File;

import com.resolve.qa.framework.testsuite.json.CheckType;
import com.resolve.qa.framework.testsuite.json.HandleResponse;
import com.resolve.qa.framework.testsuite.json.ParamType;
import com.resolve.qa.framework.testsuite.json.Test;
import com.resolve.qa.framework.testsuite.json.TestCase;
import com.resolve.qa.framework.testsuite.json.TestOp;

public class Implementations {
	public static void main(String[] args) {
		TestCase testCase = new TestCase();
		testCase.setJsonFileName("ActionTaskProperties_");
		testCase.setBaseURL("http://fiddler:8080/resolve/service");
		testCase.setUsername("admin");
		testCase.setPassword("resolve");
		
		Test test = new Test();
		test.setName("List ActionTaskProperties");
		test.setDescription("List ActionTaskProperties and validate number of records retrieved records matched with the filter criteria used. No Pagination involve");
		test.setPath("");
		test.setMethod(Test.METHOD.GET);
		test.setRequestType(Test.REQUEST_TYPE.URLENCODED_FORM_APPLICATION);
		test.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		test.addQueryParam(new ParamType("filter", ParamType.TYPE.PLAIN, "[]"));
		test.addQueryParam(new ParamType("page", ParamType.TYPE.PLAIN, "1"));
		test.addQueryParam(new ParamType("start", ParamType.TYPE.PLAIN, "0"));
		test.addQueryParam(new ParamType("limit", ParamType.TYPE.PLAIN, "-1"));
		test.addQueryParam(new ParamType("sort", ParamType.TYPE.PLAIN, "[{\"property\":\"sysCreatedOn\",\"direction\":\"ASC\"}]"));
		
		HandleResponse handleResponse = new HandleResponse();
		handleResponse.setStatusCode(200);
		handleResponse.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		handleResponse.setFailureMessage("Error while listing properties");
		
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.success", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "true"));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.message", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, ""));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.data", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, ""));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.SIZEEQUAL, CheckType.TARGET_TYPE.JSON, "$.total"));
		
		test.setHandleResponse(handleResponse);
		
		
		testCase.addTest(test);
		
		Test test2 = new Test();
		test2.setName("List ActionTaskProperties");
		test2.setDescription("List ActionTaskProperties and validate number of records retrieved records matched with the filter criteria used. No Pagination involve");
		test2.setPath("");
		test2.setMethod(Test.METHOD.GET);
		test2.setRequestType(Test.REQUEST_TYPE.URLENCODED_FORM_APPLICATION);
		test2.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		test2.addQueryParam(new ParamType("filter", ParamType.TYPE.PLAIN, "[[{\"field\":\"uname\",\"type\":\"auto\",\"condition\":\"startsWith\",\"value\":\"ControllerAPIProperty\"}]"));
		test2.addQueryParam(new ParamType("page", ParamType.TYPE.PLAIN, "1"));
		test2.addQueryParam(new ParamType("start", ParamType.TYPE.PLAIN, "0"));
		test2.addQueryParam(new ParamType("limit", ParamType.TYPE.PLAIN, "-1"));
		test2.addQueryParam(new ParamType("sort", ParamType.TYPE.PLAIN, "[{\"property\":\"sysCreatedOn\",\"direction\":\"ASC\"}]"));
		
		handleResponse = new HandleResponse();
		handleResponse.setStatusCode(200);
		handleResponse.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		handleResponse.setFailureMessage("Error while listing properties");
		
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.success", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "true"));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.message", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, ""));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.data", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, ""));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.SIZEEQUAL, CheckType.TARGET_TYPE.JSON, "$.total"));
		
		test2.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.total", "ExpectedTotal"));
		
		testCase.addTest(test2);
		
		testCase.jsonFile(new File("C:\\Users\\ncanedo\\Desktop\\GenE\\Json"));
		System.out.println(testCase);
	}
}
