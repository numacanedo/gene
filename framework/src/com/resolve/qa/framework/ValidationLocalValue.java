package com.resolve.qa.framework;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resolve.qa.framework.util.GenerateFilterUtil;

public class ValidationLocalValue
{
    @JsonProperty(required = true)
    String key;
    
    ArrayList<String> validCase = new ArrayList<String>();
    NegativeCase negativeCase;
    
    public ValidationLocalValue()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public ValidationLocalValue(String key, ArrayList<String> validCase, NegativeCase negativeCase)
    {
        super();
        this.key = key;
        this.validCase = validCase;
        this.negativeCase = negativeCase;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public ArrayList<String> getValidCase()
    {
        return validCase;
    }

    public void setValidCase(ArrayList<String> validCase) throws Exception
    {
        if(this.key.equals("filter")){
            this.validCase = GenerateFilterUtil.getInstance().generateFilter(validCase, true);
        }
        else this.validCase = validCase;
    }


    public NegativeCase getNegativeCase()
    {
        return negativeCase;
    }

    public void setNegativeCase(NegativeCase negativeCase) throws Exception
    {
        if(this.key.equals("filter")){
            this.negativeCase = new NegativeCase();
            this.negativeCase.setTestOps(negativeCase.getTestOps());
            this.negativeCase.setHandleResponse(negativeCase.getHandleResponse());
            this.negativeCase.setInvalidCase(GenerateFilterUtil.getInstance().generateFilter(negativeCase.getInvalidCase(), false));
        }
        else this.negativeCase = negativeCase;
    }
    
}
