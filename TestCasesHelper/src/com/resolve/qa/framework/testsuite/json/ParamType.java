package com.resolve.qa.framework.testsuite.json;

public class ParamType extends JsonAbstract {
	public static enum TYPE{
		PLAIN, REFERENCE
	}
	
    private String key;
    private TYPE type;
    private String value;
    
    public ParamType() {
    	super();
    }
    
    public ParamType(String key, TYPE type, String value) {
		super();
		this.key = key;
		this.type = type;
		this.value = value;
    }
    
    public ParamType(String key, String type, String value) {
		super();
		this.key = key;
		this.type = TYPE.valueOf(type);
		this.value = value;
    }

	public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
    
    public void setType(String type) {
        this.type = TYPE.valueOf(type);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
