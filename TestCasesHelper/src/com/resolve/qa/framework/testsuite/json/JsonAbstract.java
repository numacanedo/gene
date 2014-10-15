package com.resolve.qa.framework.testsuite.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.gson.GsonBuilder;

public abstract class JsonAbstract {
    public String json() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
    
    public File jsonFile(File path, String name) {
		try {
			PrintWriter writer = new PrintWriter(path.getAbsolutePath() + File.separator + name + ".json", "UTF-8");
			
			writer.write(json());
			writer.flush();
			writer.close();
			
			return new File(path.getAbsoluteFile() + File.separator + name + ".json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
    
    public File jsonFile(String path, String name) {
    	return jsonFile(new File(path), name);
    }
    
    public String toString() {
    	return json();
    }
}
