package com.resolve.qa.framework.numa.testsuite.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resolve.qa.framework.TestSuite;
import com.resolve.qa.framework.numa.TestCases;

public class TestCase extends JsonAbstract{
    private String jsonFileName;
    private String baseURL;
    private String username;
    private String password;
    private List<Test> tests = new ArrayList<Test>();
    
    public static TestCase GENERATE_RUNBOOK;
	
	static {
		reload_generate_runbook();
	}
	
	public static void reload_generate_runbook(){
		try {
			GENERATE_RUNBOOK = TestCase.parse(new File("resources/GenerateRunbook.json"));
		} catch (JsonParseException jpe) {
			jpe.printStackTrace();
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
    
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
    
    public void generateRunBook(String tag) {
    	String description = "";
    	for (int index = 0; index < this.getTests().size(); index++) {
    		description += this.getTests().get(index).getDescription() + "\r\n";
    	}
    	
    	reload_generate_runbook();
    	
    	GENERATE_RUNBOOK.getTests().get(0).getTestOps().get(1).setSourceKey(this.getJsonFileName());
    	GENERATE_RUNBOOK.getTests().get(0).getTestOps().get(2).setSourceKey(tag);
    	GENERATE_RUNBOOK.getTests().get(0).getTestOps().get(3).setSourceKey(description);
    	
    	try {
			GENERATE_RUNBOOK.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
