package com.resolve.qa.framework.numa;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.resolve.qa.framework.Test;
import com.resolve.qa.framework.common.Log;
import com.resolve.qa.framework.numa.testsuite.impl.TestCase;

public class TestCases extends Test{
	public static final String TEST_CASES_HOME = "C:\\Users\\ncanedo\\Desktop\\GenE\\Json\\";
	public static final String TEST_CASES_NICOLAS = "C:\\Users\\ncanedo\\Desktop\\GenE\\Json\\";
	public static final String DOWNLOADS = "C:\\Users\\ncanedo\\Downloads\\";
	public static final String JSON_TYPE = ".json";
	
	public static void main(String args[]) throws Exception {
		TestCase testCase = TestCase.parse(new File(TEST_CASES_HOME + "AjaxActionTaskProperties-List-CRUD-1" + JSON_TYPE));
		
		testCase.generateRunBook("");
        System.out.println(Log.out());
	}
}
