package com.resolve.qa.framework.numa.ci.analizer;

import java.util.regex.Pattern;

import com.jayway.jsonpath.JsonPath;

public class TestFail {
	private final static String CONDITION_CHECK = "NOT GET EXPECTED RESPONSE CONDITIONCHECK ";
	private final static String RESPONSE = "RESPONSE FROM REQUEST:";
	private final static String SOURCE = "SOURCE:";
	private final static String TARGET = "TARGET:";
	private final static String SOURCE_NULL = "]null";
	
	
	private String failCheck;
	private String sourceType;
	private String sourceKey;
	private String compareMethod;
	private String targetType;
	private String targetKey;
	private String source;
	private String target;
	private String response;
	
	public TestFail(String failCheck) {
		this.failCheck = failCheck;
		this.sourceType = new String();
		this.sourceKey = new String();
		this.compareMethod = new String();
		this.targetType = new String();
		this.targetKey = new String();
		this.source = new String();
		this.target = new String();
		this.response = new String();
		
		parse();
	}
	
	private void parse() {
		int conditionCheckIndex = Util.getIndex(failCheck, CONDITION_CHECK);
		int responseIndex = Util.getIndex(failCheck, RESPONSE);
		int sourceIndex = Util.getIndex(failCheck, SOURCE);
		int targetIndex = Util.getIndex(failCheck, TARGET);
		
		if (sourceIndex < 0) {
			sourceIndex = Util.getIndex(failCheck, SOURCE_NULL);
			
			if (sourceIndex > 0) { 
				sourceIndex = sourceIndex - SOURCE_NULL.length() + SOURCE.length() + 1;
				this.source = "null";
			}
		}
		
		// Parse condition check
		if (conditionCheckIndex > 0 && sourceIndex > 0) {
			try {
				String conditionCheck = failCheck.substring(conditionCheckIndex, sourceIndex - SOURCE.length());
				conditionCheck = conditionCheck.replaceAll("\"", "\\\\\"");
				conditionCheck = conditionCheck.replaceAll("\\n", "\\\\n");
				
				conditionCheck = conditionCheck.replaceFirst(Pattern.quote("["), "{\"");				
				conditionCheck = conditionCheck.replaceAll("sourceType=", "sourceType\":\"");
				conditionCheck = conditionCheck.replaceAll(", sourceKey=", "\",\"sourceKey\":\"");
				conditionCheck = conditionCheck.replaceAll(", compareMethod=", "\",\"compareMethod\":\"");
				conditionCheck = conditionCheck.replaceAll(", targetType=", "\",\"targetType\":\"");
				conditionCheck = conditionCheck.replaceAll(", targetKey=", "\",\"targetKey\":\"");
				
				conditionCheck = conditionCheck.substring(0, conditionCheck.length() - 1) + "\"}";
				
				this.sourceType = read(conditionCheck, "$.sourceType").trim();
				this.sourceKey = read(conditionCheck, "$.sourceKey").trim();
				this.compareMethod = read(conditionCheck, "$.compareMethod").trim();
				this.targetType = read(conditionCheck, "$.targetType").trim();
				this.targetKey = read(conditionCheck, "$.targetKey").trim();
			} catch (Exception e) {}
		}
		
		// Parse source
		if (sourceIndex > 0 && targetIndex > 0) {
			try {
				this.source = failCheck.substring(sourceIndex, targetIndex - TARGET.length() - 1).trim();
			} catch (Exception e) {}
		}
		
		// Parse target
		if (targetIndex > 0 && responseIndex > 0) {
			try {
				this.target = failCheck.substring(targetIndex, responseIndex - RESPONSE.length() - 1).trim();
			} catch (Exception e) {}
		}
		
		// Parse response
		if (responseIndex > 0) {
			try {
				this.response = failCheck.substring(responseIndex, failCheck.length());
			} catch (Exception e) {}
		}
	}
	
	private String read(String json, String jsonPath) {
		return  JsonPath.read(json, jsonPath);
	}
	
	public String getFailCheck() {
		return failCheck;
	}

	public String getSourceType() {
		return sourceType;
	}

	public String getSourceKey() {
		return sourceKey;
	}

	public String getCompareMethod() {
		return compareMethod;
	}

	public String getTargetType() {
		return targetType;
	}

	public String getTargetKey() {
		return targetKey;
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public String getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return "(" 
				+ "sourceType:" + this.sourceType + ", "
				+ "sourceKey:" + this.sourceKey + ", "
				+ "compareMethod:" + this.compareMethod + ", "
				+ "targetType:" + this.targetType + ", "
				+ "targetKey:" + this.targetKey + ", "
				+ "source:" + this.source + ", "
				+ "target:" + this.target + ")";
	}

	public static void main(String[] args) {
		String a = " - Not get expected response ConditionCheck [sourceType=JSON, sourceKey=$.data.sysCreatedBy, compareMethod=NOTEMPTY, targetType=PLAIN, targetKey=null]null\r\nResponse from request:\r\nFailed";
		
		TestFail fail = new TestFail(a);
		System.out.println(fail);
	}
}
