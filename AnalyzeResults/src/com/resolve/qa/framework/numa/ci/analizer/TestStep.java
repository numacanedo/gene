package com.resolve.qa.framework.numa.ci.analizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.resolve.qa.framework.TestSuite;
import com.resolve.qa.framework.numa.AnalizeResults;

public class TestStep {
	private TestSuite testSuite;
	private String testStep;
	private String number;
	private String path;
	private String description;
	private List<TestFail> failCheckList;
	private int iteration;
	
	public TestStep(TestSuite testSuite, String testStep, int iteration) {
		this.testSuite = testSuite;
		this.testStep = testStep;
		this.number = new String();
		this.path = new String();
		this.description = new String();
		this.failCheckList = new ArrayList<TestFail>();
		this.iteration = iteration;
		
		parse();
	}
	
	private void parse() {
		this.testStep = this.testStep.replaceAll("\r\n", "\n");
		this.testStep = this.testStep.replaceAll("\\\n  FAIL", "" + (char) 1);
		
		StringTokenizer testStepToken = new StringTokenizer(this.testStep, "" + (char) 1, false);
		
		// Parse Step
		try {
			String step = testStepToken.nextToken();
			int numberIndex = Util.getIndex(step, ":");
			
			if (numberIndex > 0) {
				this.number = step.substring(0, numberIndex - 1).trim();
				this.description = step.substring(numberIndex, step.length()).trim();
//				this.description = this.description.replaceAll("\r\n", "\n");
				if (this.description.contains("\n")) {
					this.description = this.description.substring(0, this.description.indexOf("\n"));
				}
			} else {
				this.description = step;
			}
		} catch (Exception e) {}
		
		// Parse Path
		try {
			if (!this.number.trim().equals("")) {
				this.path = this.testSuite.getTests().get(Integer.valueOf(this.number).intValue()).getPath();
			}
		} catch (Exception e) {}
		
		// Validate Description
		try {
			if (!this.number.trim().equals("")) {
				if (!this.description.trim().equals(this.testSuite.getTests().get(Integer.valueOf(this.number).intValue()).getDescription().trim())) {
					System.out.println("[WARNING] File: " + this.testSuite.getJsonFileName() + " Step:" + this.number + " Changed");
					this.path = new String();
				}
			}
		} catch (Exception e) {}
		
		while (testStepToken.hasMoreTokens()) {
			TestFail testFail = new TestFail(testStepToken.nextToken());
			
			if (!this.failCheckList.contains(testFail)) {
				this.failCheckList.add(testFail);
			}
		}
	}
	
	public String getTestStep() {
		return testStep;
	}
	
	public String getNumber() {
		return number;
	}

	public String getPath() {
		return path;
	}

	public String getDescription() {
		return description;
	}

	public List<TestFail> getFailCheckList() {
		return failCheckList;
	}
	
	
	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	@Override
	public String toString() {
		return "(" 
				+ "testSuite:" + this.testSuite.getJsonFileName() + ", "
				+ "number:" + this.number + ", "
				+ "description:" + this.description + ", "
				+ "path:" + this.path + ", "
				+ "failCheckList:" + this.failCheckList.size() + ", "
				+ "iteration:" + this.iteration + ")";
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String a = " 6: Retrieve wiki document and validate catalog was added\r\nFAIL - Not get expected response ConditionCheck [sourceType=JSON, sourceKey=$.records[0].bla, compareMethod=EQUAL, targetType=PLAIN, targetKey=true]source:false target:true\r\nResponse from request:{\"success\":false,\"message\":\"Error:Fullname is mandatory and must be in '.' format.\",\"data\":null,\"records\":null,\"total\":0}\r\nFAIL - Not get expected response ConditionCheck [sourceType=JSON, sourceKey=$.message, compareMethod=EQUAL, targetType=PLAIN, targetKey=null]source:Error:Fullname is mandatory and must be in '.' format. target:\r\nResponse from request:{\"success\":false,\"message\":\"Error:Fullname is mandatory and must be in '.' format.\",\"data\":null,\"records\":null,\"total\":0}\r\nFAIL - Not get expected response ConditionCheck [sourceType=JSON, sourceKey=$.data.ucatalog, compareMethod=EQUAL, targetType=PLAIN, targetKey=/Tile_Catalog_Test/New Group/New Item]source: target:/Tile_Catalog_Test/New Group/New Item\r\nResponse from request:{\"success\":false,\"message\":\"Error:Fullname is mandatory and must be in '.' format.\",\"data\":null,\"records\":null,\"total\":0}";
		
		TestSuite testSuite = new ObjectMapper().readValue(new File(AnalizeResults.TEST_CASES_HOME + "AjaxCatalog/AjaxCatalog-Rename-COMPLETE-1.json"), TestSuite.class);
		
		TestStep testStep = new TestStep(testSuite, a, 1);
		
		System.out.println(testStep);
	}
}
