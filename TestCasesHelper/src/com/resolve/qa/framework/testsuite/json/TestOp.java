package com.resolve.qa.framework.testsuite.json;

public class TestOp  extends JsonAbstract{
	public static enum METHOD {
		ASSIGN, ADD
	}
	
	public static enum SOURCE_TYPE {
		PLAIN, REFERENCE, JSON
	}
	
    private METHOD method;
    private SOURCE_TYPE sourceType;
    private String sourceKey;
    private String targetKey;
    
    public TestOp(){
    	super();
    }
    
    public TestOp(METHOD method, SOURCE_TYPE sourceType, String sourceKey,
			String targetKey) {
		super();
		this.method = method;
		this.sourceType = sourceType;
		this.sourceKey = sourceKey;
		this.targetKey = targetKey;
	}
    
    public TestOp(String method, String sourceType, String sourceKey,
			String targetKey) {
		super();
		this.method = METHOD.valueOf(method);
		this.sourceType = SOURCE_TYPE.valueOf(sourceType);
		this.sourceKey = sourceKey;
		this.targetKey = targetKey;
	}
    
	public METHOD getMethod() {
        return method;
    }

    public void setMethod(METHOD method) {
        this.method = method;
    }
    
    public void setMethod(String method) {
        this.method = METHOD.valueOf(method);
    }

    public SOURCE_TYPE getSourceType() {
        return sourceType;
    }

    public void setSourceType(SOURCE_TYPE sourceType) {
        this.sourceType = sourceType;
    }
    
    public void setSourceType(String sourceType) {
        this.sourceType = SOURCE_TYPE.valueOf(sourceType);
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }
}
