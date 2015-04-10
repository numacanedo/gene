package com.resolve.qa.framework.numa.ci.analizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;
import com.jayway.jsonpath.JsonPath;
import com.resolve.qa.framework.TestSuite;
import com.resolve.qa.framework.numa.AnalizeResults;
import com.resolve.qa.framework.numa.ci.LogGoogleSpreadSheet;

public class TestResult {
	private HashMap<String, String> testResults;
	private String testCasesHome;
	private String directory;
	private File jsonFile;
	private TestSuite testSuite;
	private List<TestStep> testStepList;
	private static HashMap<HashMap<String, String>, Integer> recordMap;
	
	public TestResult(String testCasesHome, HashMap<String, String> testResults) {
		this.testCasesHome = testCasesHome;
		this.testResults = testResults;
		this.directory = new String();
		this.jsonFile = null;
		this.testSuite = new TestSuite();
		this.testStepList = new ArrayList<TestStep>();
		
		parse();
	}
	
	public void parse() {
		try {
			// Parse summary
			String summary = this.testResults.get("summary");
			summary = summary.replaceAll("Controller Test Suite ", "").replaceAll(" with failed test", "").replaceAll("TIMEOUT: ", "");
			
			this.directory = summary.substring(0, summary.indexOf("/"));
			this.jsonFile = new File(this.testCasesHome + summary);
			this.testSuite = new ObjectMapper().readValue(this.jsonFile, TestSuite.class);
			
			// Parse detail (iterations)
			String detail = this.testResults.get("detail");
			detail = detail.replaceAll(Pattern.quote("***************************************************************************************************"), "" + (char) 1);
			
			StringTokenizer detailToken = new StringTokenizer(detail, "" + (char) 1, false);
			
			int iterationNumber = 1;
			
			while(detailToken.hasMoreTokens()) {
				// Parse iteration
				String iteration = detailToken.nextToken();
				iteration = iteration.replaceAll("TEST", "" + (char) 1);
				
				StringTokenizer iterationToken = new StringTokenizer(iteration, "" + (char) 1, false);
				
				while (iterationToken.hasMoreTokens()) {
					// Parse test
					String test = iterationToken.nextToken();
					
					if (!test.contains("Start execute test suite")) {
						TestStep testStep = new TestStep(this.testSuite, test, iterationNumber);
						
						if (!this.contains(testStep)) {
							this.testStepList.add(testStep);
						}
					}
				}
				iterationNumber++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean contains(TestStep testStep) {
		Iterator<TestStep> iterator = this.testStepList.iterator();
		boolean result = false;
		
		while(iterator.hasNext()) {
			TestStep testStepElement = iterator.next();
			int testStepElementIteration = testStepElement.getIteration();
			int testStepIteration = testStep.getIteration();
			
			testStepElement.setIteration(0);
			testStep.setIteration(0);
			
			if (testStepElement.equals(testStep)) {
				result = true;
				testStepElement.setIteration(testStepElementIteration);
				testStep.setIteration(testStepIteration);
				break;
			}
			
			testStepElement.setIteration(testStepElementIteration);
			testStep.setIteration(testStepIteration);
		}
		
		return result;
	}

	public HashMap<String, String> getTestResults() {
		return testResults;
	}

	public String getDirectory() {
		return directory;
	}

	public File getJsonFile() {
		return jsonFile;
	}

	public TestSuite getTestSuite() {
		return testSuite;
	}

	public List<TestStep> getTestStepList() {
		return testStepList;
	}
	
	@Override
	public String toString() {
		return "(" 
				+ "directory:" + this.directory + ", "
				+ "jsonFile:" + this.jsonFile.getName() + ", "
				+ "testSteps:" + this.testStepList.size() + ")";
	}
	
	public void logToGoogle(WorksheetEntry worksheet, boolean log) throws IOException, ServiceException {
		if (recordMap == null) {
			recordMap = new HashMap<HashMap<String, String>, Integer>();
		}
		
		Iterator<TestStep> steps = this.testStepList.iterator();
		
		while(steps.hasNext()) {
			TestStep testStep = steps.next();
			
			if (testStep.getFailCheckList().size() > 0) {
				Iterator<TestFail> fails = testStep.getFailCheckList().iterator();
				
				while(fails.hasNext()) {
					TestFail testFail = fails.next();
					
					HashMap<String, String> logMap = new HashMap<String, String>();
					logMap.put("Path", this.jsonFile.getAbsolutePath());
					logMap.put("Directory", this.directory);
					logMap.put("Json", this.testSuite.getJsonFileName());
					logMap.put("StepNumber", testStep.getNumber());
					logMap.put("StepDescription", testStep.getDescription());
					logMap.put("StepPath", testStep.getPath());
					logMap.put("sourceType", testFail.getSourceType());
					logMap.put("sourceKey", testFail.getSourceKey());
					logMap.put("compareMethod", testFail.getCompareMethod());
					logMap.put("targetType", testFail.getTargetType());
					logMap.put("targetKey", testFail.getTargetKey());
					
					if (!recordMap.containsKey(logMap)) {
						recordMap.put(logMap, testStep.getIteration());
						if (log) {
							logMap.put("source", testFail.getSource());
							logMap.put("target", testFail.getTarget());
							LogGoogleSpreadSheet.logResult(worksheet, logMap, testStep.getIteration());
							logMap.remove("source");
							logMap.remove("target");

						}
					}
				}
				
			}
		}
	}
	
	public int count() {
		Iterator<TestStep> steps = this.testStepList.iterator();
		int count = 0;
		
		while(steps.hasNext()) {
			TestStep testStep = steps.next();
			
			if (testStep.getFailCheckList().size() > 0) {
				Iterator<TestFail> fails = testStep.getFailCheckList().iterator();
				
				while(fails.hasNext()) {
					fails.next();
					count++;
					
				}
				
			}
		}
		
		return count;
	}

	public static void main(String[] args) {
		String testCasesHome = AnalizeResults.TEST_CASES_HOME;
		String testResults = "{\"success\":true,\"message\":null,\"data\":{\"open\":0,\"waiting\":0},\"records\":[{\"sysId\":\"9ac8759a30ea0463c98badd9feec7482\",\"sysOrg\":null,\"sysUpdatedBy\":\"system\",\"sysUpdatedOn\":1427274395242,\"sysUpdatedDt\":1427274395242,\"sysCreatedBy\":\"system\",\"sysCreatedOn\":1427274395242,\"sysCreatedDt\":1427274395242,\"sysTtl\":-1,\"address\":\"RSREMOTE\",\"completion\":\"TRUE\",\"condition\":\"BAD\",\"duration\":23,\"esbaddr\":\"RSREMOTE\",\"severity\":\"GOOD\",\"timestamp\":1427274395242,\"executeRequestId\":\"CI.Run::4+++1863672227:nullPROCESSID_DELIMITER266PROCESSID_DELIMITER179:474678928:4+++executeControllerTest\",\"executeRequestNumber\":\"EXEC82-1487\",\"executeResultId\":\"\",\"executeResultCommand\":\"\",\"executeResultReturncode\":0,\"problemId\":\"6829c63ee7bc4590a95c34947a164c98\",\"problemNumber\":\"PRB82-61\",\"processId\":\"688c820a4c0548a69e993ba0e4af40ed\",\"processNumber\":\"PROC82-78\",\"targetGUID\":\"8B6377C25F9F3C993A4D43FE0B55ACD5\",\"taskId\":\"8a948299498298530149fda6cce306f2\",\"taskName\":\"executeControllerTest\",\"taskNamespace\":\"CI\",\"taskSummary\":\"Used by CI Test. Do not modify.\",\"taskRoles\":[],\"taskTags\":[],\"detail\":\"Start execute test suite AjaxWikiNamespaceAdmin-MoveRenameCopy-CRUD-1\\nTEST 0: Verify if wiki already exists\\nTEST 2: Create wiki with a namespace to be renamed\\nTEST 3: Verify that wiki was created successfuly \\nTEST 4: List new namespace added\\nTEST 5: Rename the namespace and verify it does as well under wikis\\nTEST 6: Verify that wiki was created successfuly \\nTEST 7: Purge Name space\\nTEST 8: Verify that wiki was deleted successfully at the time namespace was deleted \\n  FAIL - Not get expected response ConditionCheck [sourceType=JSON, sourceKey=$.data.id, compareMethod=EQUAL, targetType=PLAIN, targetKey=UNDEFINED]source:ff8080814c4ffc29014c502e9c680d17 target:UNDEFINED\\n  Response from request:{\\\"success\\\":true,\\\"message\\\":null,\\\"data\\\":{\\\"id\\\":\\\"ff8080814c4ffc29014c502e9c680d17\\\",\\\"sys_id\\\":\\\"ff8080814c4ffc29014c502e9c680d17\\\",\\\"sysCreatedOn\\\":1427274374000,\\\"sysCreatedBy\\\":\\\"admin\\\",\\\"sysUpdatedOn\\\":1427274374000,\\\"sysUpdatedBy\\\":\\\"admin\\\",\\\"sysModCount\\\":0,\\\"sysPerm\\\":\\\"UNDEFINED\\\",\\\"sysOrg\\\":\\\"\\\",\\\"sysIsDeleted\\\":null,\\\"sysOrganizationName\\\":\\\"UNDEFINED\\\",\\\"rbGeneralVO\\\":null,\\\"accessRights\\\":{\\\"id\\\":\\\"ff8080814c4ffc29014c502e9c6b0d18\\\",\\\"sys_id\\\":\\\"ff8080814c4ffc29014c502e9c6b0d18\\\",\\\"sysCreatedOn\\\":1427274374000,\\\"sysCreatedBy\\\":\\\"admin\\\",\\\"sysUpdatedOn\\\":1427274374000,\\\"sysUpdatedBy\\\":\\\"admin\\\",\\\"sysModCount\\\":0,\\\"sysPerm\\\":\\\"UNDEFINED\\\",\\\"sysOrg\\\":null,\\\"sysIsDeleted\\\":null,\\\"sysOrganizationName\\\":\\\"UNDEFINED\\\",\\\"ureadAccess\\\":\\\"admin,resolve_dev,resolve_process,resolve_user\\\",\\\"uwriteAccess\\\":\\\"admin,resolve_dev,resolve_process,resolve_user\\\",\\\"uexecuteAccess\\\":\\\"action_execute,admin\\\",\\\"uadminAccess\\\":\\\"admin,resolve_dev,resolve_process,resolve_user\\\",\\\"uresourceType\\\":\\\"wikidoc\\\",\\\"uresourceName\\\":\\\"AjaxWikiNamespaceAdminNS.testdata\\\",\\\"uresourceId\\\":\\\"ff8080814c4ffc29014c502e9c680d17\\\"},\\\"wikidocAttachmentRels\\\":null,\\\"wikidocResolveTagRels\\\":null,\\\"wikidocStatistics\\\":null,\\\"roleWikidocHomepageRels\\\":null,\\\"wikidocQualityRating\\\":null,\\\"softLock\\\":false,\\\"lockedByUsername\\\":null,\\\"utitle\\\":\\\"AjaxWikiNamespaceAdminNS.testdata\\\",\\\"uversion\\\":1,\\\"ufullname\\\":\\\"AjaxWikiNamespaceAdminNS.testdata\\\",\\\"unamespace\\\":\\\"AjaxWikiNamespaceAdminNS\\\",\\\"uname\\\":\\\"testdata\\\",\\\"utag\\\":\\\"\\\",\\\"umodelProcess\\\":\\\"\\\",\\\"uhasActiveModel\\\":false,\\\"udecisionTree\\\":\\\"\\\",\\\"uisRoot\\\":false,\\\"uimpexSysId\\\":null,\\\"ucontent\\\":\\\"\\\",\\\"uisDeleted\\\":false,\\\"umodelException\\\":\\\"\\\",\\\"umodelFinal\\\":null,\\\"ureadRoles\\\":\\\"admin,resolve_dev,resolve_process,resolve_user\\\",\\\"uwriteRoles\\\":\\\"admin,resolve_dev,resolve_process,resolve_user\\\",\\\"uexecuteRoles\\\":\\\"action_execute,admin\\\",\\\"uadminRoles\\\":\\\"admin,resolve_dev,resolve_process,resolve_user\\\",\\\"uisHidden\\\":false,\\\"uisLocked\\\":false,\\\"uisActive\\\":true,\\\"uisDefaultRole\\\":true,\\\"uisDocumentStreamLocked\\\":false,\\\"uisCurrentUserFollowing\\\":true,\\\"usummary\\\":\\\"AjaxWikiNamespaceAdminNS.testdata\\\",\\\"uwysiwyg\\\":null,\\\"ulanguage\\\":null,\\\"ucatalog\\\":null,\\\"ulockedBy\\\":\\\"\\\",\\\"uratingBoost\\\":null,\\\"wikidocRatingResolution\\\":null,\\\"uisRequestSubmission\\\":false,\\\"ureqestSubmissionOn\\\":null,\\\"ulastReviewedOn\\\":null,\\\"ulastReviewedBy\\\":\\\"\\\",\\\"uexpireOn\\\":null,\\\"udtabortTime\\\":0,\\\"udisplayMode\\\":\\\"wiki\\\",\\\"ucatalogId\\\":\\\"\\\",\\\"uwikiParameters\\\":\\\"\\\",\\\"uresolutionBuilderId\\\":\\\"\\\",\\\"uhasResolutionBuilder\\\":false},\\\"records\\\":null,\\\"total\\\":0}\\nEnd execute test suite AjaxWikiNamespaceAdmin-MoveRenameCopy-CRUD-1\\n\",\"summary\":\"Controller Test Suite AjaxWikiNamespaceAdmin/AjaxWikiNamespaceAdmin-MoveRenameCopy-CRUD-1.json with failed test\",\"raw\":\"Raw results not logged\",\"wiki\":\"CI.Execute Controller Tests\",\"nodeId\":\"1863672227:nullPROCESSID_DELIMITER266PROCESSID_DELIMITER179:474678928:4\",\"hidden\":false,\"taskFullName\":\"executeControllerTest#CI\",\"id\":\"9ac8759a30ea0463c98badd9feec7482\"}],\"total\":117}";
		HashMap<String, String> result = JsonPath.read(testResults, "$.records[0]");
		
		TestResult execution = new TestResult(testCasesHome, result);
		System.out.println(execution);
	}
}