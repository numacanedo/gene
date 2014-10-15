package com.gene.testcases.util;

import java.util.List;

public class Util {
	public static int factorial(int number) {
		int factorial = 1;
		
		for (int index = factorial; index < number; index++) {
			factorial *= (index+1);
		}
		
		return factorial;
	}
	
	public static int combinationsNumber(int elementsSize, int elementsNumber) {
		return factorial(elementsSize)
				/ (factorial(elementsNumber) * factorial(elementsSize - elementsNumber));
	}
	
	public static int combinationsNumber(List<Object> elements) {
		int total = 0;
		for (int index = 0; index < elements.size(); index++) {
			total += combinationsNumber(elements.size(), index + 1);
		}
		
		return total;
	}
	
	public static String toJsonAttributeString(String key, String value) {
		return "\"" + key + "\": \"" + value + "\"";
	}
	
	public static String toJsonElementString(String name) {
		return "\"" + name + "\": [";
	}
	
	public static boolean isSet(String string) {
		return(string != null && !string.trim().equals(""));
	}
}
