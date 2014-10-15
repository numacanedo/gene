package com.gene.testcases.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.DynAnyPackage.Invalid;

import com.resolve.qa.framework.testsuite.json.CheckType;
import com.resolve.qa.framework.testsuite.json.EndConditon;
import com.resolve.qa.framework.testsuite.json.HandleResponse;
import com.resolve.qa.framework.testsuite.json.InvalidCase;
import com.resolve.qa.framework.testsuite.json.JsonPayload;
import com.resolve.qa.framework.testsuite.json.NegativeCase;
import com.resolve.qa.framework.testsuite.json.ParamType;
import com.resolve.qa.framework.testsuite.json.Test;
import com.resolve.qa.framework.testsuite.json.TestCase;
import com.resolve.qa.framework.testsuite.json.TestOp;
import com.resolve.qa.framework.testsuite.json.ValidationType;

public class AjaxActionTaskProperties {
	public static final String BASE_NODE_TEMPLATE = "{\\\"uname\\\":\\\"\\\",\\\"umodule\\\":\\\"\\\",\\\"utype\\\":\\\"\\\",\\\"uvalue\\\":\\\"\\\",\\\"id\\\":\\\"\\\"}";
	
	public static void main(String[] args) {
		TestCase testCase = createBasicSaveTestCase();
		testCase.jsonFile("C:\\Users\\ncanedo\\Downloads");
		System.out.println(testCase);
	}
	
	public static TestCase createBasicSaveTestCase() {
		TestCase testCase = new TestCase();
		
		testCase.setJsonFileName("JsonAutomationPOCAlexEx");
		testCase.setBaseURL("http://fiddler:8080/resolve/service");
		testCase.setUsername("admin");
		testCase.setPassword("resolve");
		
		
		Test save1 = createBasicSaveOperation("aaA", "aa", "Plain", "aaa");
		Test save2 = createBasicSaveOperation("dfdfdfd", "fdfdfd", "Plain", "aafdfdfdfa");
		
		Test list = new Test();
		
		list.setName("List");
		list.setDescription("List");
		list.setPath("/atproperties/list");
		list.setRequestType(Test.REQUEST_TYPE.URLENCODED_FORM_APPLICATION);
		list.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		list.addQueryParam(new ParamType("filter", ParamType.TYPE.PLAIN, "[]"));
		list.addQueryParam(new ParamType("start", ParamType.TYPE.PLAIN, "0"));
		list.addQueryParam(new ParamType("limit", ParamType.TYPE.PLAIN, "50"));
		list.addQueryParam(new ParamType("page", ParamType.TYPE.PLAIN, "1"));
		
		save1.setTestOps(createBasicSaveTestOp("sys_id_1", "uname_1", "umodule_1", "utype_1", "uvalue_1"));
		save2.setTestOps(createBasicSaveTestOp("sys_id_2", "uname_2", "umodule_2", "utype_2", "uvalue_2"));
		
		
		testCase.addTest(save1);
		testCase.addTest(save2);
		testCase.addTest(list);
		
		
		
		return testCase;
	}
	
	public static Test createBasicSaveOperation(String uname, String umodule, String utype, String uvalue) {
		String name						= "Save";
		String description				= "Save";
		String path						= "/atproperties/save";
		Test.METHOD method				= Test.METHOD.POST;
		Test.REQUEST_TYPE requestType	= Test.REQUEST_TYPE.JSON_APPLICATION;
		Test.RESPONSE_TYPE responseType	= Test.RESPONSE_TYPE.JSON_APPLICATION;
		
		Test test = new Test();
		
		test.setName(name);
		test.setDescription(description);
		test.setPath(path);
		test.setMethod(method);
		test.setRequestType(requestType);
		test.setResponseType(responseType);
		
		test.setJsonPayload(createBasicSavePayload(uname, umodule, utype, uvalue));
		test.setHandleResponse(createBasicSaveSucessHandleResponse());
		test.setTestOps(createBasicSaveTestOp());
		
		return test;
	}
	
	public static JsonPayload createBasicSavePayload(String uname, String umodule, String utype, String uvalue) {
		JsonPayload jsonPayload = new JsonPayload();
		
		jsonPayload.setBaseNode(BASE_NODE_TEMPLATE);
		jsonPayload.addReplaceJsonField(new ParamType("$.uname"		, ParamType.TYPE.PLAIN, uname));
		jsonPayload.addReplaceJsonField(new ParamType("$.umodule"	, ParamType.TYPE.PLAIN, umodule));
		jsonPayload.addReplaceJsonField(new ParamType("$.utype"		, ParamType.TYPE.PLAIN, utype));
		jsonPayload.addReplaceJsonField(new ParamType("$.uvalue"	, ParamType.TYPE.PLAIN, uvalue));
		
		return jsonPayload;
	}
	
	public static HandleResponse createBasicSaveSucessHandleResponse() {
		HandleResponse handleResponse = new HandleResponse();
		
		handleResponse.setStatusCode(200);
		handleResponse.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.success"	, CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "true"));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.message"	, CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, ""));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records"	, CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, ""));
		handleResponse.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.total"		, CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "0"));
		
		return handleResponse;
	}
	
	public static List<TestOp> createBasicSaveTestOp() {
		return createBasicSaveTestOp("sys_id", "uname", "umodule", "utype", "uvalue");
	}
	
	public static List<TestOp> createBasicSaveTestOp(String sys_idName, String unameName, String umoduleName, String utypeName, String uvalueName) {
		List<TestOp> testOps = new ArrayList<TestOp>();
		
		testOps.add(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.sys_id"	, sys_idName));
		testOps.add(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.uname"		, unameName));
		testOps.add(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.umodule"	, umoduleName));
		testOps.add(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.utype"		, utypeName));
		testOps.add(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.uvalue"	, uvalueName));
		
		return testOps;
	}
	
}
