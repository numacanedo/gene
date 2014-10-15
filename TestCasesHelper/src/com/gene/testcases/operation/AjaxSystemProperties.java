package com.gene.testcases.operation;

import com.resolve.qa.framework.testsuite.json.TestCase;

public class AjaxSystemProperties {
	public static void main(String[] args) {
		TestCase testCase = new TestCase();
		
		testCase.setJsonFileName("AjaxSystemProperties_CRUD_SAVE_2");
		testCase.setBaseURL("http://fiddler:8080/resolve/service");
		testCase.setUsername("admin");
		testCase.setPassword("resolve");
		
		
	}

}
