package com.gene.testcases.vo;

import java.util.ArrayList;
import java.util.List;

import com.gene.testcases.util.Util;

public class ResponseCheck {
	private List<ResponseCheckElement> elements;

	public ResponseCheck() {
		super();
		elements = new ArrayList<ResponseCheckElement>();
	}
	
	public ResponseCheck(List<ResponseCheckElement> elements) {
		super();
		this.elements = elements;
	}

	public List<ResponseCheckElement> getElements() {
		return elements;
	}

	public void setElements(List<ResponseCheckElement> elements) {
		this.elements = elements;
	}
	
	public void addElement(ResponseCheckElement element) {
		this.elements.add(element);
	}
	
	public void addElement(String sourceType, String sourceKey,
			String compareMethod, String targetType, String targetKey) {
		this.elements.add(new ResponseCheckElement(sourceType, sourceKey, compareMethod, targetType, targetKey));
	}
	
	public int size() {
		return this.elements.size();
	}

	@Override
	public String toString() {
		StringBuffer string;

		string = new StringBuffer();
		string.append(Util.toJsonElementString("responseChecks"));

		for (int index = 0; index < this.elements.size(); index++) {
			string.append(this.elements.get(index).toString() + ",");
		}
		
		string.deleteCharAt(string.length() - 1);
		
		string.append("]");
		
		return string.toString();			
	}
}
