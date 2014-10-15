package com.resolve.qa.framework;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resolve.qa.framework.common.Log;
import com.resolve.qa.framework.util.RsqaWebClient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestSuite
{
    @JsonProperty(required = true)
    String jsonFileName;
    @JsonProperty(required = true)
    String baseURL;
    String username;
    String password;
    List<ControllerTest> tests = new ArrayList<ControllerTest>();
    
    public TestSuite() {
        
    }

    public String getJsonFileName()
    {
        return jsonFileName;
    }
    public void setJsonFileName(String jsonFileName)
    {
        this.jsonFileName = jsonFileName;
    }
   public String getBaseURL()
    {
        return baseURL;
    }
    public void setBaseURL(String baseURL)
    {
        this.baseURL = baseURL;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public List<ControllerTest> getTests()
    {
        return tests;
    }
    public void setTests(List<ControllerTest> tests)
    {
        this.tests = tests;
    }
    
    public String execute() throws Exception {
        Log.clean();
        Log.log("Start execute test suite " + getJsonFileName());
        RsqaWebClient wc = new RsqaWebClient(baseURL);
        Map<String, Object> testData = new HashMap<String, Object>();
        
        wc.loginClient(username, password);
        boolean completed=true;
        boolean exe=true;
         do{
             if(completed!=true) Log.log("***************************************************************************************************");
             completed = true;
             for(int i=0;i<tests.size();i++) {
                 exe=true;
                 if(tests.get(i).getExeCondition()!=null) {
                     for(int j=0; j<tests.get(i).getExeCondition().size();j++) {
                         exe=exe&tests.get(i).getExeCondition().get(j).execute(null, testData);
                     }
                 }
                 if(exe) {
                     if(tests.get(i).getAltUser()!=null && tests.get(i).getAltPass()!=null && !tests.get(i).getAltUser().isEmpty() ) {
                         RsqaWebClient  altWC = new RsqaWebClient(baseURL);
                         altWC.loginClient(tests.get(i).getAltUser(), tests.get(i).getAltPass());
                         completed = tests.get(i).execute(i, baseURL, altWC, testData) && completed;
                     }
                     else{
                         wc.clearForm();
                         wc.clearQueryParams();
                         completed = tests.get(i).execute(i, baseURL, wc, testData) && completed;
                     }
                 }
             }
        }while(completed!=true);
        Log.log("End execute test suite " + getJsonFileName());
        return Log.out();
    }
}
