package com.resolve.qa.framework.numa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.resolve.qa.framework.ControllerTest;
import com.resolve.qa.framework.ExpectResponse;
import com.resolve.qa.framework.LocalValue;
import com.resolve.qa.framework.Test;
import com.resolve.qa.framework.TestSuite;
import com.resolve.qa.framework.common.HTTP_METHODS;
import com.resolve.qa.framework.common.LOCAL_VALUE_TYPE;
import com.resolve.qa.framework.common.MEDIA_TYPE;
import com.resolve.qa.framework.numa.ci.analizer.ParseTestResults;

public class AnalizeResults extends Test{
	public static final String TEST_CASES_HOME = "C:\\project\\test\\ControllerTest\\JsonFiles\\";
	public static final String FILTER_ALL = "[{\"field\":\"taskName\",\"type\":\"auto\",\"condition\":\"contains\",\"value\":\"execute\"}, {\"field\":\"condition\",\"type\":\"auto\",\"condition\":\"equals\",\"value\":\"BAD\"}]";
	public static final String FILTER_M1 = "[{\"field\":\"taskName\",\"type\":\"auto\",\"condition\":\"contains\",\"value\":\"execute\"},{\"field\":\"condition\",\"type\":\"auto\",\"condition\":\"equals\",\"value\":\"BAD\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxCatalog\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notEquals\",\"value\":\"AjaxWikiNamespaceAdmin\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxTag\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AutomationBuilder\"}],{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"PublicController\"}],{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxWikiLookupMapping\"}]";
	public static final String FILTER_M2 = "[{\"field\":\"taskName\",\"type\":\"auto\",\"condition\":\"contains\",\"value\":\"execute\"},{\"field\":\"condition\",\"type\":\"auto\",\"condition\":\"equals\",\"value\":\"BAD\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxModelController\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"ExecuteController\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxActionTaskProperties\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxSystemProperties\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxActionTask\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxActionTaskCNS\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxActionTaskAssessor\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxActionTaskPreprocessor\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxActionTaskParser\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxSystemScripts\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"ViewController\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxWorksheet\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxWiki\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AjaxWSDATA\"},{\"field\":\"summary\",\"type\":\"auto\",\"condition\":\"notContains\",\"value\":\"AutomationBuilder\"}]";
	
	public static final String GOOGLE_USER = "";
	public static final String GOOGLE_PASSWORD = "";
	public static final String FILTER = FILTER_M1;
	
	public static void main(String args[]) throws Exception {		
//		analize(DOWNLOADS, "ci_results (2)");
//		analize("d9b5c3f521aa4fb19c2950a009f18cf2"); // Before 03/19
//		analize("22d25b633d61486fbf95267457155001"); // 03/19
//		analize("51311bfcd8764ebd80fc28262efe5820"); // 03/20
//		analize("f946d30ac1fc45b1a3a254509d5d0af8"); // 03/21
//		analize("135e5df534c043869a534416544023ec"); // 03/22 
//		analize("32329c59e5514eb49bcb26bbe99241ec"); // 03/23
		
//		analize("7343a1fc02e348b697190762c19ed02f"); // 03/24
//		analize("6829c63ee7bc4590a95c34947a164c98"); // 03/25
//		analize("88bb6e5c813344e6b07fbee77be9572c"); // 03/05
//		analize("8e0ee67e095941f8bf91f81345ec4390"); // 03/15
//		analize("97b62a025ea4472fbf2bb9c598222295"); // 03/27
//		analize("4ba9f03cab0b440888c303a0d7846bff"); // 03/30 
//		analize("2f40ec6975464c5bbdddf9826a8584d3"); // 03/31
//		analize("56722cc5668842ccabd592774aefa8dd"); // 04/03
//		analize("b0dd8d741dd44d75881b8d258c21d598"); // 04/03
		
//		compare("4ba9f03cab0b440888c303a0d7846bff", "2f40ec6975464c5bbdddf9826a8584d3"); // 03/31
//		compare("2f40ec6975464c5bbdddf9826a8584d3", "e9d3b137bdac497a889ceae6365a2cad"); // 04/01
//		compare("88bb6e5c813344e6b07fbee77be9572c", "b0dd8d741dd44d75881b8d258c21d598"); // 03-05 / 04-06
		
//		analize("88bb6e5c813344e6b07fbee77be9572c"); // 03-05
//		analize("b0dd8d741dd44d75881b8d258c21d598"); // 04-06
//		analize("8026f5b945bb46e294ffd30c8b53fb5f"); // 04-08
//		analize("95216c10816b4eedb4c56f9dc899d0fb"); // 04-09
		analize("f37ec40611c545c8b0ec0cab29d35047"); // 04-10
		
		// Encode Password
//		System.out.println(org.glassfish.jersey.internal.util.Base64.encodeAsString("TestPassword"));
	}
	
	public static void compare(String previousWorksheetId, String currentWorksheetId) {
		ParseTestResults.log = false;
		getCIResults(previousWorksheetId);
		ParseTestResults.log = true;
		getCIResults(currentWorksheetId);
	}
	
	public static void analize(String worksheetId) {
		ParseTestResults.log = true;
		getCIResults(worksheetId);
	}

	
	public static void getCIResults(String worksheetId) {
		try {
			TestSuite testSuite = new TestSuite();
			testSuite.setUsername("admin");
			testSuite.setPassword("resolveQA1");
			testSuite.setBaseURL("http://ci:8080/resolve/service");
			
			ControllerTest test = new ControllerTest();
			test.setMethod(HTTP_METHODS.GET);
			test.setRequestType(MEDIA_TYPE.URLENCODED_FORM_APPLICATION);
			test.setResponseType(MEDIA_TYPE.JSON_APPLICATION);
			test.setDescription("Get workshet results");
			test.setPath("/worksheet/results");
			
			List<LocalValue> queryParams = new ArrayList<LocalValue>();
			
			queryParams.add(new LocalValue("id", LOCAL_VALUE_TYPE.PLAIN, worksheetId));
			queryParams.add(new LocalValue("start", LOCAL_VALUE_TYPE.PLAIN, "0"));
			queryParams.add(new LocalValue("limit", LOCAL_VALUE_TYPE.PLAIN, "500"));
			queryParams.add(new LocalValue("page", LOCAL_VALUE_TYPE.PLAIN, "1"));
			queryParams.add(new LocalValue("hidden", LOCAL_VALUE_TYPE.PLAIN, "true"));
			queryParams.add(new LocalValue("sort", LOCAL_VALUE_TYPE.PLAIN, "[{\"property\":\"summary\",\"direction\":\"DESC\"}]"));
			queryParams.add(new LocalValue("filter", LOCAL_VALUE_TYPE.PLAIN, FILTER));
			ExpectResponse expectResponse = new ExpectResponse();
			expectResponse.setStatusCode(200);
			
			test.setQueryParams(queryParams);
			test.setHandleResponse(expectResponse);
			
			List<ControllerTest> tests = new ArrayList<ControllerTest>();
			tests.add(test);
			
			testSuite.setTests(tests);
			testSuite.execute();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
