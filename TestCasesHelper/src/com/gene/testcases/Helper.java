package com.gene.testcases;

import java.util.ArrayList;
import java.util.List;

public class Helper {
	public static void main(String[] args) {
		List<String> invalidScenarios = new ArrayList<String>();
		String invalidValue = "";
		
		invalidScenarios.add("abc 123");
		invalidScenarios.add("Multiple not continuous spaces");
		invalidScenarios.add("abc_123 abc 123_");
		invalidScenarios.add("");
		invalidScenarios.add(" ");
		invalidScenarios.add(" abc123");
		invalidScenarios.add("abc123 ");
		invalidScenarios.add(" _");
		invalidScenarios.add("_ ");
		invalidScenarios.add("Continuous  SpacesTest");
		invalidScenarios.add("SpecialCharacterTest <");
		invalidScenarios.add("SpecialCharacterTest >");
		invalidScenarios.add("SpecialCharacterTest (");
		invalidScenarios.add("SpecialCharacterTest )");
		invalidScenarios.add("SpecialCharacterTest {");
		invalidScenarios.add("SpecialCharacterTest }");
		invalidScenarios.add("SpecialCharacterTest {");
		invalidScenarios.add("SpecialCharacterTest }");
		invalidScenarios.add("SpecialCharacterTest .");
		invalidScenarios.add("SpecialCharacterTest :");
		invalidScenarios.add("SpecialCharacterTest ,");
		invalidScenarios.add("SpecialCharacterTest ;");
		invalidScenarios.add("SpecialCharacterTest -");
		invalidScenarios.add("SpecialCharacterTest +");
		invalidScenarios.add("SpecialCharacterTest /");
		invalidScenarios.add("SpecialCharacterTest *");
		invalidScenarios.add("SpecialCharacterTest =");
		invalidScenarios.add("SpecialCharacterTest ¡");
		invalidScenarios.add("SpecialCharacterTest !");
		invalidScenarios.add("SpecialCharacterTest ¿");
		invalidScenarios.add("SpecialCharacterTest ?");
		invalidScenarios.add("SpecialCharacterTest '");
		invalidScenarios.add("SpecialCharacterTest \\\"");
		invalidScenarios.add("SpecialCharacterTest |");
		invalidScenarios.add("SpecialCharacterTest #");
		invalidScenarios.add("SpecialCharacterTest $");
		invalidScenarios.add("SpecialCharacterTest %");
		invalidScenarios.add("SpecialCharacterTest &");
		invalidScenarios.add("SpecialCharacterTest ´");
		invalidScenarios.add("SpecialCharacterTest ¨");
		invalidScenarios.add("SpecialCharacterTest ^");
		invalidScenarios.add("SpecialCharacterTest `");
		invalidScenarios.add("SpecialCharacterTest ~");
		invalidScenarios.add("SpecialCharacterTest \\\\");
		invalidScenarios.add("SpecialCharacterTest ¬");
		invalidScenarios.add("AbCdEfGhI1AbCdEfGhI2AbCdEfGhI3AbCdEfGhI4AbCdEfGhI5AbCdEfGhI6AbCdEfGhI7AbCdEfGhI8AbCdEfGhI9AbCdEfGhI0AbCdEfGhI1AbCdEfGhI2AbCdEfGhI3AbCdEfGhI4AbCdEfGhI5AbCdEfGhI6AbCdEfGhI7AbCdEfGhI8AbCdEfGhI9AbCdEfGhI0AbCdEfGhI1AbCdEfGhI2AbCdEfGhI3AbCdEfGhI4AbCdEfGhI5123456");
		
//		System.out.println("[{\"field\":\"uname\",\"type\":\"auto\",\"condition\":\"equals\",\"value\":\"BasicWorkingTestCase\"}]");
		
		for (int index=0; index < invalidScenarios.size(); index++) {
			invalidValue = invalidScenarios.get(index);
			System.out.println("              {");
			System.out.println("                \"value\": \"" + invalidValue + "\",");
			System.out.println("                \"handleResponse\": {");
			System.out.println("                  \"statusCode\": 200,");
			System.out.println("                  \"failLevel\": \"ERROR\",");
			System.out.println("                  \"failureMessage\": \"\"");
			System.out.println("                }");
			System.out.println("              },");
		}
	}
}
