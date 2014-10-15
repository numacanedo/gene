package com.gene.testcases.vo;

import java.util.ArrayList;
import java.util.List;

import com.gene.testcases.util.Util;

public class ReplaceJsonField {
	private List<ReplaceJsonFieldElement> elements;

	public ReplaceJsonField() {
		super();
		elements = new ArrayList<ReplaceJsonFieldElement>();
	}
	
	public ReplaceJsonField(List<ReplaceJsonFieldElement> elements) {
		super();
		this.elements = elements;
	}

	public List<ReplaceJsonFieldElement> getElements() {
		return elements;
	}

	public void setElements(List<ReplaceJsonFieldElement> elements) {
		this.elements = elements;
	}
	
	public void addElement(ReplaceJsonFieldElement element) {
		this.elements.add(element);
	}
	
	public void addElement(String key, String type, String value) {
		this.elements.add(new ReplaceJsonFieldElement(key, type, value));
	}
	
	public int size() {
		return this.elements.size();
	}

	@Override
	public String toString() {
		StringBuffer string;

		string = new StringBuffer();
		string.append(Util.toJsonElementString("replaceJsonField"));

		for (int index = 0; index < this.elements.size(); index++) {
			string.append(this.elements.get(index).toString() + ",");
		}
		
		string.deleteCharAt(string.length() - 1);
		
		string.append("]");
		
		return string.toString();			
	}
}
