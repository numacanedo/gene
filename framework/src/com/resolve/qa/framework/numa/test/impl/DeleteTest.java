package com.resolve.qa.framework.numa.test.impl;

import com.resolve.qa.framework.numa.testsuite.impl.HandleResponse;
import com.resolve.qa.framework.numa.testsuite.impl.ParamType;
import com.resolve.qa.framework.numa.testsuite.impl.Test;

public class DeleteTest extends Test{
	public DeleteTest() {
		// ============================== Test ==============================
		super();
		this.setName("Delete");
		this.setDescription("Delete");
		this.setPath("");
		this.setMethod(Test.METHOD.POST);
		this.setRequestType(Test.REQUEST_TYPE.URLENCODED_FORM_APPLICATION);
		this.setResponseType(Test.RESPONSE_TYPE.JSON_APPLICATION);
		
		this.setAll(false);
		
		this.setStatusCode(200);
		this.setFailLevel(HandleResponse.FAIL_LEVEL.ERROR);
		this.setFailureMessage("Error on Delete Operation");
		
		this.setJsonEqualPlainResponseCheck("$.success", "true");
		this.setJsonEqualPlainResponseCheck("$.message", null);
		this.setJsonEqualPlainResponseCheck("$.data", null);
		this.setJsonEqualPlainResponseCheck("$.records", null);
		this.setJsonEqualPlainResponseCheck("$.total", "0");		
		// ------------------------------------------------------------------
	}
	
	public void setAll(boolean all) {
		boolean found = false;
		
		for (int index = 0; index < this.getRequestForm().size(); index++) {
			if (this.getRequestForm().get(index).getKey().equalsIgnoreCase("all")) {
				this.getRequestForm().set(index, new ParamType("all", ParamType.TYPE.PLAIN, Boolean.toString(all)));
				found = true;
				break;
			}
		}
		
		if (!found) {
			this.addRequestForm("all", ParamType.TYPE.PLAIN, Boolean.toString(all));
		}
	}
	
	public void addPlainId(String id) {
		this.addRequestForm("ids", ParamType.TYPE.PLAIN, id);
	}
	
	public void addReferenceId(String reference) {
		this.addRequestForm("ids", ParamType.TYPE.REFERENCE, reference);
	}
	
	public void cleanIds() {
		int requestFormParamCount = this.getRequestForm().size();
		
		for (int index = 0; index < requestFormParamCount; index++) {
			if (this.getRequestForm().get(index).getKey().equalsIgnoreCase("ids")) {
				this.getRequestForm().remove(index);
				requestFormParamCount--;
			}
		}
	}
}
