package com.resolve.qa.framework.numa.ci.analizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.jayway.jsonpath.JsonPath;
import com.resolve.qa.framework.numa.AnalizeResults;
import com.resolve.qa.framework.numa.ci.LogGoogleSpreadSheet;

public class ParseTestResults {
	public static List<TestResult> testResultList;
	public static boolean log;
	private static WorksheetEntry worksheet;
	
	public static void parse(String response) {
		Integer total = JsonPath.read(response, "$.total");
		testResultList = new ArrayList<TestResult>();
		
		ArrayList<String> logMap = new ArrayList<String>();
		logMap.add("Path");
		logMap.add("Directory");
		logMap.add("Json");
		logMap.add("Iteration");
		logMap.add("StepNumber");
		logMap.add("StepDescription");
		logMap.add("StepPath");
		logMap.add("sourceType");
		logMap.add("sourceKey");
		logMap.add("compareMethod");
		logMap.add("targetType");
		logMap.add("targetKey");
		logMap.add("source");
		logMap.add("target");
		int totalFailures = 0;
		
		if (worksheet == null) {
			try {
				worksheet = LogGoogleSpreadSheet.createWorkshet(logMap);
			} catch (Exception e1) {
				e1.printStackTrace();
			}			
		}
		
		for (int index = 0; index < total; index++) {
			HashMap<String, String> testResult = JsonPath.read(response, "$.records[" + index + "]");
			TestResult result = new TestResult(AnalizeResults.TEST_CASES_HOME, testResult);
			testResultList.add(result);
			try {
				result.logToGoogle(worksheet, log);
				totalFailures++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Total Failures: " + totalFailures);
	}
}
