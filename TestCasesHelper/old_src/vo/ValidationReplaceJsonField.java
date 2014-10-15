package com.gene.testcases.vo;

import java.util.List;

public class ValidationReplaceJsonField {
	private String key;
	private List<String> validCase;
	
	
	public ValidationReplaceJsonField() {
		super();
	}

	public ValidationReplaceJsonField(String key, List<String> validCase) {
		super();
		this.key = key;
		this.validCase = validCase;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getValidCase() {
		return validCase;
	}

	public void setValidCase(List<String> validCase) {
		this.validCase = validCase;
	}

	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		
		string.append("\"replaceJsonField\": [");
		string.append("{");
		
		return null;
	}
}
