package com.resolve.qa.framework.numa.ci.analizer;

public class Util {
	public static int getIndex(String source, String string) {
		if (!source.toUpperCase().contains(string.toUpperCase())) {
			return -1;
		}

		return source.toUpperCase().indexOf(string.toUpperCase()) + string.length();
	}
}
