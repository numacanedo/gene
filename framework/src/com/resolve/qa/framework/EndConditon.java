package com.resolve.qa.framework;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EndConditon
{
    @JsonProperty(required = true)
    List<ConditionCheck> endCheck = new ArrayList<ConditionCheck>();
    List<ConditionCheck> finalCheck =  new ArrayList<ConditionCheck>();
    
    public EndConditon() {
        
    }

    public List<ConditionCheck> getEndCheck()
    {
        return endCheck;
    }

    public void setEndCheck(List<ConditionCheck> endCheck)
    {
        this.endCheck = endCheck;
    }

    public List<ConditionCheck> getFinalCheck()
    {
        return finalCheck;
    }

    public void setFinalCheck(List<ConditionCheck> finalCheck)
    {
        this.finalCheck = finalCheck;
    }

    @Override
    public String toString()
    {
        return "EndConditon [endCheck=" + endCheck + ", finalCheck=" + finalCheck + "]";
    }
    
    
}
