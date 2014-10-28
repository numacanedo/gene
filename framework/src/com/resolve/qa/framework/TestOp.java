package com.resolve.qa.framework;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.resolve.qa.framework.common.SOURCE_TARGET_TYPE;
import com.resolve.qa.framework.common.TEST_OPERATION_TYPE;
import com.resolve.qa.framework.util.JsonUtil;
import com.resolve.qa.framework.common.Log;

import org.codehaus.jackson.JsonNode;

public class TestOp 
{
    @JsonProperty(required = true)
    TEST_OPERATION_TYPE method;
    @JsonProperty(required = true)
    SOURCE_TARGET_TYPE sourceType;
    String sourceKey;
    String targetKey;
    
    public TestOp() {
        
    }
    
    public TestOp(String method, String sourceType, String sourceKey, String targetType, String  targetKey) {
        this.sourceKey=sourceKey;
        this.targetKey=targetKey;
        this.method=TEST_OPERATION_TYPE.valueOf(method.toUpperCase());
        this.sourceType=SOURCE_TARGET_TYPE.valueOf(sourceType.toUpperCase());
    }
    
    public TEST_OPERATION_TYPE getMethod()
    {
        return method;
    }

    public void setMethod(TEST_OPERATION_TYPE method)
    {
        this.method = method;
    }

    public SOURCE_TARGET_TYPE getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(SOURCE_TARGET_TYPE sourceType)
    {
        this.sourceType = sourceType;
    }

    public String getSourceKey()
    {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey)
    {
        this.sourceKey = sourceKey;
    }

    public String getTargetKey()
    {
        return targetKey;
    }

    public void setTargetKey(String targetKey)
    {
        this.targetKey = targetKey;
    }

    
    @Override
    public String toString()
    {
        return "TestOp [method=" + method + ", sourceType=" + sourceType + ", sourceKey=" + sourceKey + ", targetKey=" + targetKey + "]";
    }

    public void execute(JsonNode jsonObj, Map<String, Object> testData) 
    {
        String source = null;
        switch (sourceType)
        {
            case JSON:
                try
                {
            		source = JsonUtil.getJsonValue(jsonObj, sourceKey);
                }
                catch (NullPointerException e) {
                    source = new String("");
                }
                catch (Exception e)
                {
                    Log.log("Cannot get value from Json");
                }
                break;
            case PLAIN:
        		source = sourceKey;
                break;
            case REFERENCE:
            	if (method.equals(TEST_OPERATION_TYPE.REPLACE)) {
            		StringTokenizer sourceTokens;
            		String sourceString;
            		String keyString;
            		String valueString;
            		int valueTokenNumber = 0;
            		
                	try {
                		sourceTokens	= new StringTokenizer(sourceKey, ".");
            			sourceString	= (String)testData.get(sourceTokens.nextToken());
            			keyString		= sourceTokens.nextToken();
            			valueString		= "";
            			while (sourceTokens.hasMoreTokens()) {
            				if (valueTokenNumber > 0) {
            					valueString	+= ".";
            				}
            				valueString	+= sourceTokens.nextToken();
            				valueTokenNumber++;
            			}
            			
            			source			= sourceString.replaceAll(keyString, valueString);
                	} catch (NoSuchElementException nsee) {
                		Log.log(Log.getIndent() + "FAIL - NoSuchElementException: source-" + sourceKey);
                		Log.log(Log.getIndent() + "FAIL - Expected source format: source.key.value");
                	}
            	} else if (method.equals(TEST_OPERATION_TYPE.REPLACE_REFERENCE)) {
            		StringTokenizer sourceTokens;
            		String sourceString;
            		String keyString;
            		String valueString;
            		int valueTokenNumber = 0;
            		
                	try {
                		sourceTokens	= new StringTokenizer(sourceKey, ".");
            			sourceString	= (String)testData.get(sourceTokens.nextToken());
            			keyString		= sourceTokens.nextToken();
            			valueString		= (String)testData.get(sourceTokens.nextToken());
            			source			= sourceString.replaceAll(keyString, valueString);
                	} catch (NoSuchElementException nsee) {
                		Log.log(Log.getIndent() + "FAIL - NoSuchElementException: source-" + sourceKey);
                		Log.log(Log.getIndent() + "FAIL - Expected source format: source.key.reference_value");
                	}
            	}
            	
            	else {
            		source = (String)testData.get(sourceKey);
            	}
            	break;
            default:
                Log.log(" FAIL - Not implement source type in value assign:\n " + this.toString());
                return;
        }
        if (source == null)
        {
            Log.log("Cannot get source value in value assign:\n " + this.toString());
            return;
        }
        
        switch (method) {
            case SLEEP:             
                try {   
                    int sleep = Integer.parseInt(source);
                    Thread.sleep(sleep * 1000);
                } catch (InterruptedException ie) {
                    Log.log(Log.getIndent() + "WARNING - Sleep Test Operation Failed");
                } catch(NumberFormatException nfe){
                    Log.log(Log.getIndent() + "FAIL -NumberFormatException: source-" + source);
                }
                return;
        }
       
        String target = (String)testData.get(targetKey);
        switch (method)
        {
            case ADD:
                try {
                    target = Integer.toString(Integer.parseInt(target)+Integer.parseInt(source));
                }
                catch(NumberFormatException e){
                    Log.log(Log.getIndent() + "FAIL -NumberFormatException: target-" + target + " source-" + source);
                }
                break;
      
            case ASSIGN:
                target = source;
                break;
            case CONCATENATE:
                target += source;
                break;
            case REPLACE:
        		target = source;
                break;
            case REPLACE_REFERENCE:
        		target = source;
                break;
            
        }
        testData.put(targetKey, target);
        return;

    }
}
