package com.resolve.qa.framework.numa.testsuite.impl;

public class Filter extends JsonAbstract{
	public static enum CONDITION {
		equals, notEquals, contains, notContains, startsWith, notStartsWith, endWith, notEndWith, on, before, after;
	}
	
	private String field;
	private String type;
	private CONDITION condition;
	private String value;
	
	public Filter() {
		super();
	}
	
	public Filter(String field, String type, CONDITION condition, String value) {
		super();
		this.field = field;
		this.type = type;
		this.condition = condition;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CONDITION getCondition() {
		return condition;
	}

	public void setCondition(CONDITION condition) {
		this.condition = condition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
