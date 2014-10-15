package com.gene.testcases.util;

import java.util.ArrayList;
import java.util.List;

public class Combine {
	public static void main(String[] args) {
		List<Object> elements = new ArrayList<Object>();
		
		elements.add(new Pair("name", "value"));
		elements.add("b");
		elements.add("c");
		elements.add("d");
		elements.add("e");
//		elements.add("f");
		
		print(combine(elements));
//		System.out.println(Util.combinationsNumber(elements));		
	}
	
	public static void print(List<List<Object>> combinations) {
		for (int index = 0; index < combinations.size(); index++) {
			List<Object> combination = combinations.get(index);
			System.out.println(combination);
		}
	}
	
	public static List<List<Object>> combine(List<Object> elements) {
		List<List<Object>> combinations;
		List<Integer> positions;
		int combinationsSize = 1;
		
		combinations = new ArrayList<List<Object>>();
		positions = new ArrayList<Integer>();
		
		for (int index = 0; index < combinationsSize; index++) {
			List<Object> existingCombination;
			int start;
			
			if (combinations.size() > 0) {
				combinationsSize = combinations.size();
				existingCombination = combinations.get(index-1);
				start = positions.get(index-1).intValue() + 1;
			} else {
				existingCombination = new ArrayList<Object>();
				start = 0;		
			}
			
			for (int elementsIndex = start; elementsIndex < elements.size(); elementsIndex++) {
				List<Object> newCombination;
				
				newCombination = new ArrayList<Object>(existingCombination);
				newCombination.add(elements.get(elementsIndex));
				
				combinations.add(newCombination);
				positions.add(new Integer(elementsIndex));
			}
			
			combinationsSize = combinations.size();
		}
		
		return combinations;
	}
}
