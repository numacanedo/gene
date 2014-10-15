package com.gene.testcases.vo;

import com.gene.testcases.util.Util;

public class ResponseCheckElement {
	private String sourceType;
	private String sourceKey;
	private String compareMethod;
	private String targetType;
	private String targetKey;
	public ResponseCheckElement(String sourceType, String sourceKey,
			String compareMethod, String targetType, String targetKey) {
		super();
		this.sourceType = sourceType;
		this.sourceKey = sourceKey;
		this.compareMethod = compareMethod;
		this.targetType = targetType;
		this.targetKey = targetKey;
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
	public String getCompareMethod() {
		return compareMethod;
	}
	public void setCompareMethod(String compareMethod) {
		this.compareMethod = compareMethod;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
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
		string.append(Util.toJsonAttributeString("sourceType"		, sourceType)	+ ",");
		string.append(Util.toJsonAttributeString("sourceKey"		, sourceKey)	+ ",");
		string.append(Util.toJsonAttributeString("compareMethod"	, compareMethod)+ ",");
		string.append(Util.toJsonAttributeString("targetType"		, targetType)	+ ",");
		string.append(Util.toJsonAttributeString("targetKey"		, targetKey));
		string.append("}");
		
		return string.toString();
	}
}
