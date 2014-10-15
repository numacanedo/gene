package com.resolve.qa.framework.testsuite.json;

import java.util.ArrayList;
import java.util.List;

public class JsonPayload extends JsonAbstract {
	private String baseNode;
    private List<ParamType> replaceJsonField = new ArrayList<ParamType>();
    private List<ValidationType> validationReplaceJsonField = new ArrayList<ValidationType>();
    
    public JsonPayload() {
		super();
	}
    
    public JsonPayload(String baseNode) {
		super();
		this.baseNode = baseNode;
	}

	public JsonPayload(String baseNode, List<ParamType> replaceJsonField,
			List<ValidationType> validationReplaceJsonField) {
		super();
		this.baseNode = baseNode;
		this.replaceJsonField = replaceJsonField;
		this.validationReplaceJsonField = validationReplaceJsonField;
	}
	
	public String getBaseNode() {
        return baseNode;
    }

    public void setBaseNode(String baseNode) {
        this.baseNode = baseNode;
    }

    public List<ParamType> getReplaceJsonField() {
        return replaceJsonField;
    }

    public void setReplaceJsonField(List<ParamType> replaceJsonField) {
        this.replaceJsonField = replaceJsonField;
    }

    public List<ValidationType> getValidationReplaceJsonField() {
        return validationReplaceJsonField;
    }

    public void setValidationReplaceJsonField(List<ValidationType> validationReplaceJsonField) {
        this.validationReplaceJsonField = validationReplaceJsonField;
    }
    
    public void addReplaceJsonField(ParamType replaceJsonField) {
    	this.replaceJsonField.add(replaceJsonField);
    }
    
    public void addValidationReplaceJsonField(ValidationType validationReplaceJsonField) {
    	this.validationReplaceJsonField.add(validationReplaceJsonField);
    }
}
