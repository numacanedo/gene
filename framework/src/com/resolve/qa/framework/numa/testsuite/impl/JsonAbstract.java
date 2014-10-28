package com.resolve.qa.framework.numa.testsuite.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonAbstract {
    public String json() {
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
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
    
    @SuppressWarnings("unchecked")
	public  <T extends JsonAbstract> T copy() {
    	try {
			return (T) new ObjectMapper().readValue(json(), this.getClass());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public String toString() {
    	return json();
    }
    
    public void print() {
    	System.out.println(this);
    }
}
