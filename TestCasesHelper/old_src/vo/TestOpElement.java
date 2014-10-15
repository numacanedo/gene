package com.gene.testcases.vo;

import com.gene.testcases.util.Util;

public class TestOpElement {
	private String method;
	private String sourceType;
	private String sourceKey;
	private String targetKey;
	
	public TestOpElement(String method, String sourceType, String sourceKey, String targetKey) {
		super();
		this.method = method;
		this.sourceType = sourceType;
		this.sourceKey = sourceKey;
		this.targetKey = targetKey;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceKey() {
		return sourceKey;
	}
	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}
	public String getTargetKey() {
		return targetKey;
	}
	public void setTargetKey(String targetKey) {
		this.targetKey = targetKey;
	}
	
	@Override
	public String toString() {
		StringBuffer string;
		
		string = new StringBuffer();
		string.append("{");
		string.append(Util.toJsonAttributeString("method"		, method)		+ ",");
		string.append(Util.toJsonAttributeString("sourceType"	, sourceType)	+ ",");
		string.append(Util.toJsonAttributeString("sourceKey"	, sourceKey)	+ ",");
		string.append(Util.toJsonAttributeString("targetKey"	, targetKey));
		string.append("}");
		
		return string.toString();
	}
}
