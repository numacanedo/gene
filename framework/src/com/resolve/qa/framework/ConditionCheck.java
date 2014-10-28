package com.resolve.qa.framework;

import java.util.Map;

import com.resolve.qa.framework.common.COMPARE_METHOD_TYPE;
import com.resolve.qa.framework.common.Log;
import com.resolve.qa.framework.common.SOURCE_TARGET_TYPE;
import com.resolve.qa.framework.util.JsonUtil;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.codehaus.jackson.JsonNode;

public class ConditionCheck
{   
    @JsonProperty(required = true)
    SOURCE_TARGET_TYPE sourceType;
    String sourceKey;
    @JsonProperty(required = true)
    COMPARE_METHOD_TYPE compareMethod;
    @JsonProperty(required = true)
    SOURCE_TARGET_TYPE targetType;
    String targetKey;
    
    String info=null;
    
    public ConditionCheck() {
        
    }
    
    public ConditionCheck(SOURCE_TARGET_TYPE sourceType, String sourceKey, COMPARE_METHOD_TYPE compareMethod, SOURCE_TARGET_TYPE targetType, String targetKey)
    {
        super();
        this.sourceType = sourceType;
        this.sourceKey = sourceKey;
        this.compareMethod = compareMethod;
        this.targetType = targetType;
        this.targetKey = targetKey;
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
    public COMPARE_METHOD_TYPE getCompareMethod()
    {
        return compareMethod;
    }
    public void setCompareMethod(COMPARE_METHOD_TYPE compareMethod)
    {
        this.compareMethod = compareMethod;
    }
    public SOURCE_TARGET_TYPE getTargetType()
    {
        return targetType;
    }
    public void setTargetType(SOURCE_TARGET_TYPE targetType)
    {
        this.targetType = targetType;
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
        return "ConditionCheck [sourceType=" + sourceType + ", sourceKey=" + sourceKey + ", compareMethod=" + compareMethod + ", targetType=" + targetType + ", targetKey=" + targetKey + "]" + info;
    }

    public boolean execute(JsonNode jsonObj, Map<String, Object> testData) {
        String source = null;
        String target = null;
        
        switch (sourceType)
        {
            case JSON:
                try
                {
                    if(compareMethod.toString().equals("SIZEEQUAL")) source = new Integer(JsonUtil.getJsonSize(jsonObj, sourceKey)).toString();
                    else source = JsonUtil.getJsonValue(jsonObj, sourceKey);
                }
                catch (NullPointerException e) {
                    source = new String("");
                }
                catch (Exception e)
                {
                    Log.log(e.getMessage());
                }
                break;
            case PLAIN:
                source = sourceKey;
                break;
            case REFERENCE:
                source = (String)testData.get(sourceKey);
                break;
        }
        
        boolean result = false;
        //Handle unary operation
        switch (compareMethod)
        {
            case NOTEMPTY:
                return !(source==null || source.isEmpty());
            case ISEMPLTY:
                return source==null || source.isEmpty();
        }
        if (source == null)
        {
            Log.log("Cannot get source value in condition check:\n " + this.toString());
            return false;
        }

        switch (targetType)
        {
            case JSON:
                try
                {
                    target = JsonUtil.getJsonValue(jsonObj, targetKey);
                }
                catch (Exception e)
                {
                    Log.log(e.getMessage());
                }
                break;
            case PLAIN:
                target = targetKey;
                if(target == null) target = new String("");
                break;
            case REFERENCE:
                target = (String)testData.get(targetKey);
                break;
        }
        if (target == null)
        {
            Log.log("Cannot get target value in condition check:\n " + this.toString());
            return false;
        }

        
        switch (compareMethod)
        {
            case EQUAL:
                result = source.equals(target);
                break;
            case NOEQUAL:
                result = !source.equals(target);
                break;
            case CONTAIN:
                result = source.contains(target);
                break;
            case NOCONTAIN:
                result = !source.contains(target);
                break;
            case LESS:
                result = Integer.parseInt(source) < Integer.parseInt(target) ? true : false;
                break;
            case LESSOREQUAL:
                result = Integer.parseInt(source) <= Integer.parseInt(target) ? true : false;
                break;
            case GREATEROREQUAL:
                result = Integer.parseInt(source) >= Integer.parseInt(target) ? true : false;
                break;
            case GREATER:
                result = Integer.parseInt(source) > Integer.parseInt(target) ? true : false;
                break;
            case INCREASEEQUAL:
                source = Integer.toString(Integer.parseInt(source) + 1);
                result = source.equals(target);
                break;
            case SIZEEQUAL:
                result = source.equals(target);
                break;
            default:
                Log.log(" FAIL - Not implement compare method in condition check:\n " + this.toString());
                return false;
        }
        if(result==false) {
            info = "source:" + source + " target:" + target;
        }
        return result;
    }
        
    
}
