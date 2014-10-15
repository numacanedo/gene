package com.gene.testcases.vo;

import com.gene.testcases.util.Util;

public class Payload {
	private String baseNode;
	private ReplaceJsonField replaceJsonField;
	
	public Payload() {
		super();
		baseNode = null;
		replaceJsonField = new ReplaceJsonField();
	}
	
	public Payload(String baseNode,
			ReplaceJsonField replaceJsonField) {
		super();
		this.baseNode = baseNode;
		this.replaceJsonField = replaceJsonField;
	}

	public String getBaseNode() {
		return baseNode;
	}

	public void setBaseNode(String baseNode) {
		this.baseNode = baseNode;
	}

	public ReplaceJsonField getReplaceJsonField() {
		return replaceJsonField;
	}

	public void setReplaceJsonField(ReplaceJsonField replaceJsonField) {
		this.replaceJsonField = replaceJsonField;
	}
	
	public void addReplaceJsonField(String key, String type, String value) {
		this.replaceJsonField.addElement(key, type, value);
	}

	@Override
	public String toString() {
		StringBuffer string;
		
		string = new StringBuffer();
		string.append("\"jsonPayload\": {");
		string.append(Util.toJsonAttributeString("baseNode", baseNode));
		
		if (null != replaceJsonField && replaceJsonField.size() > 0) {
			string.append(",");
			string.append(replaceJsonField.toString());
		}
		
		string.append("}");
		
		return string.toString();
	}
}
