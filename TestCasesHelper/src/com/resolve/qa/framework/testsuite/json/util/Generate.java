package com.resolve.qa.framework.testsuite.json.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.gson.GsonBuilder;
import com.resolve.qa.framework.testsuite.json.TestCase;

public class Generate {
	public static String json(TestCase testCase) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(testCase);
	}
	
	public static void jsonFile(File path, TestCase testCase) {
		try {
			PrintWriter writer = new PrintWriter(path.getAbsolutePath() + File.separator + testCase.getJsonFileName() + ".json", "UTF-8");
			
			writer.write(json(testCase));
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
