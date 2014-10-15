package com.resolve.qa.framework.numa.testsuite.impl;

public class CheckType extends JsonAbstract{
	public static enum SOURCE_TYPE {
		PLAIN, REFERENCE, JSON
	}
	
	public static enum COMPARE_METHOD {
		EQUAL, LESS, LESSOREQUAL, GREATER, GREATEROREQUAL, CONTAIN, NOCONTAIN, INCREASEEQUAL, NOTEMPTY, ISEMPLTY, SIZEEQUAL
	}
	
	public static enum TARGET_TYPE {
		PLAIN, REFERENCE, JSON
	}
	
	private SOURCE_TYPE sourceType;
    private String sourceKey;
    private COMPARE_METHOD compareMethod;
    private TARGET_TYPE targetType;
    private String targetKey;

    public CheckType() {
		super();
	}

	public CheckType(SOURCE_TYPE sourceType, String sourceKey, COMPARE_METHOD compareMethod,
			TARGET_TYPE targetType, String targetKey) {
		super();
		this.sourceType = sourceType;
		this.sourceKey = sourceKey;
		this.compareMethod = compareMethod;
		this.targetType = targetType;
		this.targetKey = targetKey;
	}
	
	public SOURCE_TYPE getSourceType() {
        return sourceType;
    }

    public void setSourceType(SOURCE_TYPE sourceType) {
        this.sourceType = sourceType;
    }
    
    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public COMPARE_METHOD getCompareMethod() {
        return compareMethod;
    }

    public void setCompareMethod(COMPARE_METHOD compareMethod) {
        this.compareMethod = compareMethod;
    }
    
    public TARGET_TYPE getTargetType() {
        return targetType;
    }

    public void setTargetType(TARGET_TYPE targetType) {
        this.targetType = targetType;
    }
    
    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }
}
