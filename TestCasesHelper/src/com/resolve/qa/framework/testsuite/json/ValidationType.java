package com.resolve.qa.framework.testsuite.json;

import java.util.ArrayList;
import java.util.List;

public class ValidationType extends JsonAbstract {
    private String key;
    private List<String> validCase = new ArrayList<String>();
    private NegativeCase negativeCase;
    
    public ValidationType() {
    	super();
    }

    public ValidationType(String key, List<String> validCase,
			NegativeCase negativeCase) {
		super();
		this.key = key;
		this.validCase = validCase;
		this.negativeCase = negativeCase;
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

    public NegativeCase getNegativeCase() {
        return negativeCase;
    }

    public void setNegativeCase(NegativeCase negativeCase) {
        this.negativeCase = negativeCase;
    }
    
    public void addValidCase(String validCase) {
    	this.validCase.add(validCase);
    }
}
