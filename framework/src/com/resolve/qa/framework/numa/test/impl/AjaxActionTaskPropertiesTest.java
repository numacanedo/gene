package com.resolve.qa.framework.numa.test.impl;

import com.resolve.qa.framework.numa.testsuite.impl.HandleResponse;
import com.resolve.qa.framework.numa.testsuite.impl.ParamType;
import com.resolve.qa.framework.numa.testsuite.impl.Test;

public class AjaxActionTaskPropertiesTest extends Test{
	public static Test list(String filter, String page, String start, String limit, String sort) {
		// ============================== Test ==============================
		Test test = new Test();
		test.setName("List");
		test.setDescription("List");
		test.setPath("/atproperties/list");
		test.setMethod(Test.METHOD.GET);
		test.setRequestType(Test.REQUEST_TYPE.URLENCODED_FORM_APPLICATION);
		test.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		test.addQueryParam(new ParamType("filter"	, ParamType.TYPE.PLAIN, filter));
		test.addQueryParam(new ParamType("page"		, ParamType.TYPE.PLAIN, page));
		test.addQueryParam(new ParamType("start"	, ParamType.TYPE.PLAIN, start));
		test.addQueryParam(new ParamType("limit"	, ParamType.TYPE.PLAIN, limit));
		test.addQueryParam(new ParamType("sort"		, ParamType.TYPE.PLAIN, sort));
		
		HandleResponse handleResponse = new HandleResponse();
		handleResponse.setStatusCode(200);
		handleResponse.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		handleResponse.setFailureMessage("");
		
		handleResponse.addJsonEqualPlainResponseCheck("$.success", "true");
		handleResponse.addJsonEqualPlainResponseCheck("$.message", null);
		handleResponse.addJsonEqualPlainResponseCheck("$.data", null);
		handleResponse.addJsonEqualPlainResponseCheck("$.records", "0");
		handleResponse.addJsonEqualPlainResponseCheck("$.total", "0");
		
		test.setHandleResponse(handleResponse);
		// ------------------------------------------------------------------
		
		return test;
	}
}
