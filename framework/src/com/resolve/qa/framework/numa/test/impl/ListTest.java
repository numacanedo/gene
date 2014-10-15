package com.resolve.qa.framework.numa.test.impl;

import com.resolve.qa.framework.numa.testsuite.impl.HandleResponse;
import com.resolve.qa.framework.numa.testsuite.impl.ParamType;
import com.resolve.qa.framework.numa.testsuite.impl.Test;

public class ListTest extends Test{
	public ListTest() {
		// ============================== Test ==============================
		super();
		this.setName("List");
		this.setDescription("List");
		this.setPath("");
		this.setMethod(Test.METHOD.GET);
		this.setRequestType(Test.REQUEST_TYPE.URLENCODED_FORM_APPLICATION);
		this.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		this.setFilter("[]");
		this.setPage("1");
		this.setStart("0");
		this.setLimit("50");
		this.setSort("[{\"property\":\"uname\",\"direction\":\"ASC\"}]");
		
		this.setStatusCode(200);
		this.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		this.setFailureMessage("Error on List Operation");
		
		this.setJsonEqualPlainResponseCheck("$.success", "true");
		this.setJsonEqualPlainResponseCheck("$.message", null);
		this.setJsonEqualPlainResponseCheck("$.data", null);
		this.setJsonEqualPlainResponseCheck("$.records", null);
		this.setJsonEqualPlainResponseCheck("$.total", "0");
		// ------------------------------------------------------------------
	}
	
	public ListTest(String filter, String page, String start, String limit, String sort) {
		this();
		
		this.setFilter(filter);
		this.setPage(page);
		this.setStart(start);
		this.setLimit(limit);
		this.setSort(sort);
	}
    
    public void setFilter(String filter) {
    	this.setQueryParam(new ParamType("filter", ParamType.TYPE.PLAIN, filter));
    }
    
    public void setPage(String page) {
    	this.setQueryParam(new ParamType("page", ParamType.TYPE.PLAIN, page));
    }
    
    public void setStart(String start) {
    	this.setQueryParam(new ParamType("start", ParamType.TYPE.PLAIN, start));
    }
    
    public void setLimit(String limit) {
    	this.setQueryParam(new ParamType("limit", ParamType.TYPE.PLAIN, limit));
    }
    
    public void setSort(String sort) {
    	this.setQueryParam(new ParamType("sort", ParamType.TYPE.PLAIN, sort));
    }
    
    public ListTest copy() {
    	return (ListTest)abstractCopy();
    }
	
}
