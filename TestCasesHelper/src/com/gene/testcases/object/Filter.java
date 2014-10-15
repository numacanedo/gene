package com.gene.testcases.object;

public class Filter {
	public static final String TEMPLATE = "{\\\"field\\\":\\\"_FIELD\\\",\\\"type\\\":\\\"_TYPE\\\",\\\"condition\\\":\\\"_CONDITION\\\",\\\"value\\\":\\\"_VALUE\\\"}";
	
	public String field;
	public String type;
	public String condition;
	public String value;
	
	public Filter() {
		super();
		this.field = "";
		this.type = "";
		this.condition = "";
		this.value = "";
	}
	
	public Filter(String field, String type, String condition, String value) {
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
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return TEMPLATE.replaceFirst("_FIELD"		, this.field)
					   .replaceFirst("_TYPE"		, this.type)
					   .replaceFirst("_CONDITION"	, this.condition)
					   .replaceFirst("_VALUE"		, this.value);
	}
}
