package com.resolve.qa.framework.numa.testsuite.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resolve.qa.framework.TestSuite;

public class TestCase extends JsonAbstract{
    private String jsonFileName;
    private String baseURL;
    private String username;
    private String password;
    private List<Test> tests = new ArrayList<Test>();
    
    public TestCase() {
    	super();
    }
    
    public TestCase(String file) throws JsonParseException, JsonMappingException, IOException {
		this(new ObjectMapper().readValue(new File(file), new TestCase().getClass()));
    }
    
    public TestCase(File file) throws JsonParseException, JsonMappingException, IOException {
		this(new ObjectMapper().readValue(file, new TestCase().getClass()));
    }
    
    public TestCase(TestCase testCase) {
    	this.jsonFileName = testCase.getJsonFileName();
		this.baseURL = testCase.getBaseURL();
		this.username = testCase.getUsername();
		this.password = testCase.getPassword();
		this.tests = testCase.getTests();
    }
    
    public TestCase(String jsonFileName, String baseURL, String username,
			String password, List<Test> tests) {
		this.jsonFileName = jsonFileName;
		this.baseURL = baseURL;
		this.username = username;
		this.password = password;
		this.tests = tests;
	}
    
	public String getJsonFileName() {
        return jsonFileName;
    }

    public void setJsonFileName(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
    
    public void addTest(Test test) {
    	this.tests.add(test);
    }
    
    public File jsonFile(File path) {
    	return this.jsonFile(path, this.getJsonFileName());
    }
    
    public File jsonFile(String path) {
    	return this.jsonFile(path, this.getJsonFileName());
    }
    
    public static TestCase parse(File testCase) throws JsonParseException, JsonMappingException, IOException {
    	return (new ObjectMapper().readValue(testCase, new TestCase().getClass()));
    }
    
    public TestSuite toTestSuite() {
		return new ObjectMapper().convertValue(this, new TestSuite().getClass());
	}
    
    public static TestCase toTestCase(TestSuite testSuite) {
		return new ObjectMapper().convertValue(testSuite, new TestCase().getClass());
	}
    
    public void execute() throws Exception {
        System.out.println(toTestSuite().execute());
//    	toTestSuite().execute();
    }
}
