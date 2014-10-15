package com.resolve.qa.framework.testsuite.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestCase extends JsonAbstract{
    private String jsonFileName;
    private String baseURL;
    private String username;
    private String password;
    private List<Test> tests = new ArrayList<Test>();
    
    public TestCase() {
    	super();
    }

	public TestCase(String jsonFileName, String baseURL, String username,
			String password, List<Test> tests) {
		super();
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
}
