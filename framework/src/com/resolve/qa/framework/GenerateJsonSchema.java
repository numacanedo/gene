package com.resolve.qa.framework;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenerateJsonSchema
{
    public static void main(String[] args) throws JsonMappingException 
    {

        ObjectMapper mapper = new ObjectMapper();
        TestSuite testSuite = new TestSuite();
        
       System.out.println(mapper.generateJsonSchema(testSuite.getClass()).toString());
        
        
    }
}
