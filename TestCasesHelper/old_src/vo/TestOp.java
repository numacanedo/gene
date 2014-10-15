package com.gene.testcases.vo;

import java.util.ArrayList;
import java.util.List;

import com.gene.testcases.util.Util;

public class TestOp {
	private List<TestOpElement> elements;

	public TestOp() {
		super();
		elements = new ArrayList<TestOpElement>();
	}
	
	public TestOp(List<TestOpElement> elements) {
		super();
		this.elements = elements;
	}

	public List<TestOpElement> getElements() {
		return elements;
	}

	public void setElements(List<TestOpElement> elements) {
		this.elements = elements;
	}
	
	public void addElement(TestOpElement element) {
		this.elements.add(element);
	}
	
	public void addElement(String method, String sourceType, String sourceKey, String targetKey) {
		this.elements.add(new TestOpElement(method, sourceType, sourceKey, targetKey));
	}
	
	public int size() {
		return this.elements.size();
	}

	@Override
	public String toString() {
		StringBuffer string;

		string = new StringBuffer();
		string.append(Util.toJsonElementString("testOps"));

		for (int index = 0; index < this.elements.size(); index++) {
			string.append(this.elements.get(index).toString() + ",");
		}
		
		string.deleteCharAt(string.length() - 1);
		
		string.append("]");
		
		return string.toString();			
	}
}
