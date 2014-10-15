package com.gene.testcases.vo;

import com.gene.testcases.util.Util;

public class ReplaceJsonFieldElement {
	private String key;
	private String type;
	private String value;
	
	public ReplaceJsonFieldElement() {
		super();
		key = null;
		type = null;
		value = null;
	}
	
	public ReplaceJsonFieldElement(String key, String type, String value) {
		super();
		this.key = key;
		this.type = type;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuffer string;
		
		string = new StringBuffer();
		string.append("{");
		string.append(Util.toJsonAttributeString("key"		, key)	+ ",");
		string.append(Util.toJsonAttributeString("type"		, type)	+ ",");
		string.append(Util.toJsonAttributeString("value"	, value));
		string.append("}");
		
		return string.toString();
	}
}
