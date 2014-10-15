package com.resolve.qa.framework;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import com.resolve.qa.framework.common.Log;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test
{

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
/*        ObjectMapper mapper = new ObjectMapper();
        TestSuite testSuite = new TestSuite();
        testSuite = mapper.readValue(new File("C:\\Users\\xia.hua\\Downloads\\AjaxTag.json"), testSuite.getClass());
        testSuite.execute();*/
        executeControllerTestInJson("C:\\Users\\xia.hua\\Downloads\\test2.json");
        System.out.println(Log.out());

    }
    public static String executeControllerTestInJson(String files) throws Exception {
        return executeControllerTestInJson(null, files, null, null, null);
    }
    

    public static String executeControllerTestInJson(String dir, String files, String URL, String user, String password) throws Exception {
        Log.clean();
        ObjectMapper mapper = new ObjectMapper();
        TestSuite testSuite = new TestSuite();
        if(dir!=null) dir=dir.trim();
        for(String file : files.split(",")) {
            file=file.trim();
            if(dir!=null) file=dir.concat(file);
            if(!file.endsWith(".json")) file=file.concat(new String(".json"));
            testSuite = mapper.readValue(new File(file), testSuite.getClass());
            if(URL!=null && !URL.isEmpty()) testSuite.setBaseURL(URL);
            if(user!=null && !user.isEmpty()) testSuite.setUsername(user);
            if(password!=null && !password.isEmpty()) testSuite.setPassword(password);
            testSuite.execute();
        }
        return Log.out();
    }

}
