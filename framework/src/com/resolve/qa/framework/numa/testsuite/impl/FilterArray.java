package com.resolve.qa.framework.numa.testsuite.impl;

import java.util.List;

public class FilterArray extends JsonAbstract{
	private List<Filter> filter;
	
	public FilterArray() {
		super();
	}

	public FilterArray(List<Filter> filter) {
		super();
		this.filter = filter;
	}

	public List<Filter> getFilter() {
		return filter;
	}

	public void setFilter(List<Filter> filter) {
		this.filter = filter;
	}
}
