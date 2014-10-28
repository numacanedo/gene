package com.resolve.qa.framework.numa;

import com.resolve.qa.framework.numa.test.impl.DeleteTest;
import com.resolve.qa.framework.numa.test.impl.ListTest;
import com.resolve.qa.framework.numa.testsuite.impl.CheckType;
import com.resolve.qa.framework.numa.testsuite.impl.HandleResponse;
import com.resolve.qa.framework.numa.testsuite.impl.JsonPayload;
import com.resolve.qa.framework.numa.testsuite.impl.ParamType;
import com.resolve.qa.framework.numa.testsuite.impl.Test;
import com.resolve.qa.framework.numa.testsuite.impl.TestCase;
import com.resolve.qa.framework.numa.testsuite.impl.TestOp;

public class AjaxActionTaskPropertiesListCRUD {
public static final String TEST_CASES_HOME = "C:\\Users\\ncanedo\\Desktop\\GenE\\Json\\";
	
	public static void main(String[] args) throws Exception {
//		TestCase testCase1 = createTestCase1();
//		TestCase testCase2 = createTestCase2(new TestCase(TEST_CASES_HOME + "AjaxActionTaskProperties-List-CRUD-2.json"));
		TestCase testCase3 = createTestCase3();
//		testCase1.execute();
//		testCase1.print();
//		testCase1.jsonFile(TEST_CASES_HOME);
//		testCase1.getTests().get(16).print();
//		testCase2.jsonFile(TEST_CASES_HOME);
		testCase3.execute();
	}
	
	public static TestCase createTestCase1() {
		int numberRecords = 5; // Number of records to create on this test case
		
		// =========================== Variables ============================
		String expectedTotal = "ExpectedTotal";
		String totalRecordsExpected = "TotalRecordsExpected";
		
		String testPrefix = "AjaxActionTaskPropertiesListCRUD1";
		String unamePrefix = testPrefix + "Uname_";
		String umodulePrefix = testPrefix + "Umodule_";
		String utypePrefix = testPrefix + "Utype_";
		String uvaluePrefix = testPrefix + "Uvalue_";
		
		String sysIdVar = testPrefix + "sys_id";
		String unameVar = testPrefix + "uname";
		String umoduleVar = testPrefix + "umodule";
		String uvalueVar = testPrefix + "uvalue";
		// ------------------------------------------------------------------
		
		TestCase testCase = new TestCase();
		testCase.setJsonFileName("AjaxActionTaskProperties-List-CRUD-1");
		testCase.setBaseURL("http://fiddler:8080/resolve/service");
		testCase.setUsername("admin");
		testCase.setPassword("resolve");
		
		// ============================== Test0 =============================
		ListTest test0 = listTest();
		test0.setDescription("List AjaxActionTaskProperties and validate number of records retrieved records matched with the filter criteria used. No Pagination involve");
		
		test0.setLimit("-1");
		test0.setSort("[{\"property\":\"sysCreatedOn\",\"direction\":\"ASC\"}]");
		
		test0.deleteResponseCheck("$.total");
		test0.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.SIZEEQUAL, CheckType.TARGET_TYPE.JSON, "$.total"));
		
		testCase.addTest(test0);
		// ------------------------------------------------------------------
		// ============================== Test1 =============================
		ListTest test1 = listTest();
		test1.setDescription("List AjaxActionTaskProperties and validate number of records retrieved records matched with the filter criteria used. No Pagination involve");
		
		test1.setFilter("[{\"field\":\"uname\",\"type\":\"auto\",\"condition\":\"startsWith\",\"value\":\"" + testPrefix + "\"}]");
		test1.setLimit("-1");
		test1.setSort("[{\"property\":\"sysCreatedOn\",\"direction\":\"ASC\"}]");
		
		test1.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.SIZEEQUAL, CheckType.TARGET_TYPE.PLAIN, "0"));
		
		test1.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.total", expectedTotal));
		test1.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.PLAIN, "0", totalRecordsExpected));
		
		testCase.addTest(test1);
		// ------------------------------------------------------------------
		for (int index = 1; index < (numberRecords + 1); index++) {
			// ============================== Test2 =============================
			Test test2 = new Test();
			test2.setName("Save AjaxActionTaskProperties");
			test2.setDescription("Create AjaxActionTaskProperties to be used for listing purposes - CRUD");
			test2.setPath("/atproperties/save");
			test2.setMethod(Test.METHOD.POST);
			test2.setRequestType(Test.REQUEST_TYPE.JSON_APPLICATION);
			test2.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
			
			JsonPayload jsonPayload2 = new JsonPayload();
			jsonPayload2.setBaseNode("{\"uname\":\"\",\"umodule\":\"\",\"utype\":\"\",\"uvalue\":\"\",\"id\":\"\"}");
			jsonPayload2.addReplaceJsonField(new ParamType("$.uname", ParamType.TYPE.PLAIN, unamePrefix + index));
			jsonPayload2.addReplaceJsonField(new ParamType("$.umodule", ParamType.TYPE.PLAIN, umodulePrefix + index));
			jsonPayload2.addReplaceJsonField(new ParamType("$.utype", ParamType.TYPE.PLAIN, utypePrefix + index));
			jsonPayload2.addReplaceJsonField(new ParamType("$.uvalue", ParamType.TYPE.PLAIN, uvaluePrefix + index));
			
			test2.setStatusCode(200);
			test2.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
			test2.setFailureMessage("Error during AjaxActionTaskProperty creation");
			test2.addJsonEqualPlainResponseCheck("$.success", "true");
			test2.addJsonEqualPlainResponseCheck("$.message", null);
			test2.addResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.data", CheckType.COMPARE_METHOD.NOTEMPTY, CheckType.TARGET_TYPE.PLAIN, ""));
			test2.addJsonEqualPlainResponseCheck("$.records", null);
			
			test2.setJsonPayload(jsonPayload2);
			
			test2.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.sys_id", sysIdVar + index));
			test2.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.uname", unameVar + index));
			test2.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.umodule", umoduleVar + index));
			test2.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.utype", "Plain"));
			test2.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.uvalue", uvalueVar + index));
			test2.addTestOp(new TestOp(TestOp.METHOD.ADD, TestOp.SOURCE_TYPE.PLAIN, "1", expectedTotal));
			test2.addTestOp(new TestOp(TestOp.METHOD.ADD, TestOp.SOURCE_TYPE.PLAIN, "1", totalRecordsExpected));
			
			testCase.addTest((Test) test2.copy());
			// ------------------------------------------------------------------
		}
		
		for (int index = 0; index < (numberRecords + 1); index++) {
			// ============================== Test7 =============================
			ListTest test7 = test1.copy();
			
			test7.setDescription("List AjaxActionTaskProperties and validate number of records retrived based on START " + index + " parameter and filter criteria");
			
			test7.setStart("" + index);
			test7.setLimit("5");
			
			test7.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.total", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.REFERENCE, expectedTotal));
			test7.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.SIZEEQUAL, CheckType.TARGET_TYPE.REFERENCE, totalRecordsExpected));
			
			test7.cleanTestOps();
			test7.addTestOp(new TestOp(TestOp.METHOD.ADD, TestOp.SOURCE_TYPE.PLAIN, "-1", totalRecordsExpected));
			
			testCase.addTest(test7.<ListTest>copy());
			// ------------------------------------------------------------------			
		}
		
		// ============================= Test13 =============================
		ListTest test13 = test1.copy();
		test13.setDescription("List AjaxActionTaskProperties, use Start greater than number of records to receive. Records retrieved 0");
		
		test13.setStart("6");
		test13.setLimit("0");
		
		test13.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.total", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.REFERENCE, expectedTotal));
		test13.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.SIZEEQUAL, CheckType.TARGET_TYPE.PLAIN, "0"));
		
		testCase.addTest(test13);
		// ------------------------------------------------------------------
		// ============================= Test14 =============================
		ListTest test14 = test13.copy();
		test14.setDescription("List AjaxActionTaskProperties, use Negative Start value, No records should be retrieved");
		
		test14.setStart("-1");
		
		testCase.addTest(test14);
		// ------------------------------------------------------------------
		// ============================= Test15 =============================
		ListTest test15 = test13.copy();
		test15.setDescription("List AjaxActionTaskProperties, Not sending an Start value, behaviour should be the same as start 0");
		
		test15.setStart(null);
		test15.setStatusCode(200);
		
		testCase.addTest(test15);
		// ------------------------------------------------------------------
		// ============================= Test16 =============================
		ListTest test16 = test13.copy();
		test16.setDescription("List AjaxActionTaskProperties, Sort DESC by Created On and validate records were sorted propertly");
		
		test16.setStart("0");
		test16.setLimit("-1");
		test16.setSort("[{\"property\":\"sysCreatedOn\",\"direction\":\"DESC\"}]");
		
		test16.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.total", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.REFERENCE, expectedTotal));
		test16.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.SIZEEQUAL, CheckType.TARGET_TYPE.REFERENCE, expectedTotal));
		
		for (int index = numberRecords; index > 0; index--) {
			test16.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records[" + (numberRecords - index) + "].sys_id", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.REFERENCE, sysIdVar + index));
		}		
		
		testCase.addTest(test16);
		// ------------------------------------------------------------------
		// ============================= Test17 =============================
		ListTest test17 = test16.copy();
		
		test17.setDescription("List AjaxActionTaskProperties, Sort DESC by Name On and validate records were sorted propertly");
		test17.setSort("[{\"property\":\"uname\",\"direction\":\"DESC\"}]");
		
		testCase.addTest(test17);
		// ------------------------------------------------------------------
		// ============================= Test18 =============================
		ListTest test18 = test16.copy();
		
		test18.setDescription("List AjaxActionTaskProperties, Sort DESC by Module and validate records were sorted propertly");
		test18.setSort("[{\"property\":\"umodule\",\"direction\":\"DESC\"}]");
		
		testCase.addTest(test18);
		// ------------------------------------------------------------------
		// ============================= Test19 =============================
		ListTest test19 = test16.copy();
		
		test19.setDescription("List AjaxActionTaskProperties, Sort DESC by Value and validate records were sorted propertly");
		test19.setSort("[{\"property\":\"uvalue\",\"direction\":\"DESC\"}]");
		
		testCase.addTest(test19);
		// ------------------------------------------------------------------
		// ============================= Test20 =============================
		ListTest test20 = test13.copy();
		test20.setDescription("List AjaxActionTaskProperties, Sort ASC by Created On and validate records were sorted propertly");
		
		test20.setStart("0");
		test20.setLimit("-1");
		test20.setSort("[{\"property\":\"sysCreatedOn\",\"direction\":\"ASC\"}]");
		
		test20.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.total", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.REFERENCE, expectedTotal));
		test20.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records", CheckType.COMPARE_METHOD.SIZEEQUAL, CheckType.TARGET_TYPE.REFERENCE, expectedTotal));
		
		for (int index = 0; index < numberRecords; index++) {
			test20.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.JSON, "$.records[" + index + "].sys_id", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.REFERENCE, sysIdVar + (index + 1)));
		}		
		
		testCase.addTest(test20);
		// ------------------------------------------------------------------
		// ============================= Test21 =============================
		ListTest test21 = test20.copy();
		
		test21.setDescription("List AjaxActionTaskProperties, Sort ASC by Name On and validate records were sorted propertly");
		test21.setSort("[{\"property\":\"uname\",\"direction\":\"ASC\"}]");
		
		testCase.addTest(test21);
		// ------------------------------------------------------------------
		// ============================= Test22 =============================
		ListTest test22 = test20.copy();
		
		test22.setDescription("List AjaxActionTaskProperties, Sort ASC by Module and validate records were sorted propertly");
		test22.setSort("[{\"property\":\"umodule\",\"direction\":\"ASC\"}]");
		
		testCase.addTest(test22);
		// ------------------------------------------------------------------
		// ============================= Test23 =============================
		ListTest test23 = test20.copy();
		
		test23.setDescription("List AjaxActionTaskProperties, Sort ASC by Value and validate records were sorted propertly");
		test23.setSort("[{\"property\":\"uvalue\",\"direction\":\"ASC\"}]");
		
		testCase.addTest(test23);
		// ------------------------------------------------------------------
		
		for (int index = 1; index < (numberRecords + 1); index++) { 
			// ============================= Test25 =============================
			DeleteTest test25 = new DeleteTest();
			test25.setName("Delete AjaxActionTaskProperties");
			test25.setDescription("Clean up the property that was created on previous step");
			test25.setPath("/atproperties/delete");
			
			test25.addReferenceId(sysIdVar + index);
			
			test25.setFailureMessage("Error while deleting AjaxActionTaskProperty created for test case");
			
			testCase.addTest(test25);
			// ------------------------------------------------------------------
		}
		
		return testCase;
	}
	
	public static ListTest listTest() {
		// ============================== Test ==============================
		ListTest test = new ListTest();
		test.setName("List AjaxActionTaskProperties");
		test.setDescription("List AjaxActionTaskProperties");
		test.setPath("/atproperties/list");
				
		test.setFilter("[]");
		test.setPage("1");
		test.setStart("0");
		test.setLimit("50");
		test.setSort("[{\"property\":\"uname\",\"direction\":\"ASC\"}]");

		test.setFailureMessage("Error on AjaxActionTaskProperties List Operation");
		
		test.setJsonEqualPlainResponseCheck("$.success", "true");
		test.setJsonEqualPlainResponseCheck("$.message", null);
		test.setJsonEqualPlainResponseCheck("$.data", null);
		test.setJsonEqualPlainResponseCheck("$.records", null);
		test.setJsonEqualPlainResponseCheck("$.total", "0");
		// ------------------------------------------------------------------
		
		return test;
	}

	public static TestCase createTestCase2(TestCase testCase) {
		Test test0 = testCase.getTests().get(0);
		
		test0.cleanTestOps();
		test0.addTestOp(new TestOp(TestOp.METHOD.ASSIGN, TestOp.SOURCE_TYPE.JSON, "$.data.sys_id", "sys_id"));
		
		DeleteTest test2 = new DeleteTest();
		test2.setName("Delete AjaxActionTaskProperties");
		test2.setDescription("Clean up the property that was created on previous step");
		test2.setPath("/atproperties/delete");
		
		test2.addReferenceId("sys_id");
		
		test2.setFailureMessage("Error while deleting AjaxActionTaskProperty created for test case");
		
		if (testCase.getTests().size() > 2) {
			testCase.getTests().set(2, test2);
		} else {
			testCase.addTest(test2);
		}
		
		return testCase;
	}
	
	public static TestCase createTestCase3() {
		TestCase testCase = new TestCase();
		testCase.setJsonFileName("AjaxActionTaskProperties-List-CRUD-3");
		testCase.setBaseURL("http://fiddler:8080/resolve/service");
		testCase.setUsername("numa.canedo");
		testCase.setPassword("Numa.Canedo_7");
		
		ListTest test0 = listTest();
		
		test0.setResponseCheck(new CheckType(CheckType.SOURCE_TYPE.PLAIN, "$.sucess", CheckType.COMPARE_METHOD.EQUAL, CheckType.TARGET_TYPE.PLAIN, "0"));
		
		testCase.addTest(test0);
		
		return testCase;
	}
}
