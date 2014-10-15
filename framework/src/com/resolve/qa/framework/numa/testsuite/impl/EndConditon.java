package com.resolve.qa.framework.numa.testsuite.impl;

import java.util.ArrayList;
import java.util.List;

public class EndConditon extends JsonAbstract {
	private List<CheckType> endCheck = new ArrayList<CheckType>();
    private List<CheckType> finalCheck = new ArrayList<CheckType>();
    
    public EndConditon() {
		super();
	}

	public List<CheckType> getEndCheck() {
        return endCheck;
    }

    public void setEndCheck(List<CheckType> endCheck) {
        this.endCheck = endCheck;
    }

    public List<CheckType> getFinalCheck() {
        return finalCheck;
    }

    public void setFinalCheck(List<CheckType> finalCheck) {
        this.finalCheck = finalCheck;
    }
    
    public void addEndCheck(CheckType endCheck){
    	this.endCheck.add(endCheck);
    }
    
    public void addFinalCheck(CheckType finalCheck){
    	this.finalCheck.add(finalCheck);
    }
}
