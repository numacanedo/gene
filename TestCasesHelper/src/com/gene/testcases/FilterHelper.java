package com.gene.testcases;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.gene.testcases.object.Filter;
import com.gene.testcases.util.Combine;

public class FilterHelper {
	public static void main(String[] args) throws InterruptedException {
		PrintStream origOut = System.out;
	    PrintStream interceptor = new Interceptor(origOut);
	    System.setOut(interceptor);// just add the interceptor
		generateFilter();
		
	}
	
	public static List<String> generateFilter() throws InterruptedException {
		List<Object> elements = new ArrayList<Object>();
		List<String> filters = new ArrayList<String>();
		
		elements.add(new Filter("uname", "auto", "equals", "ListFilterTesting_uNameString123"));
		elements.add(new Filter("utype", "auto", "equals", "Plain"));
		elements.add(new Filter("umodule", "auto", "equals", "ListFilterTesting.uModuleString123"));
		elements.add(new Filter("uvalue", "auto", "equals", "ListFilterTesting uValueString123"));
		elements.add(new Filter("sysCreatedOn", "date", "on", "sysDate"));
		elements.add(new Filter("sysCreatedBy", "auto", "equals", "Admin"));
		elements.add(new Filter("sysUpdatedOn", "date", "on", "sysDate"));
		elements.add(new Filter("sysUpdatedBy", "auto", "equals", "Admin"));
//		elements.add(new Filter("id", "auto", "equals", "8a9482e83af705ed013af71e89100006"));
		//ListFilterTesting_uNameString123
		//ListFilterTesting.uModuleString123
		//Plain
		//ListFilterTesting uValueString123
		
		
		List<List<Object>> combinations = Combine.combine(elements);
		
		for (int index = 0; index < combinations.size(); index++) {
			StringBuffer filter = new StringBuffer();
			
			filter.append("[");
			
			for (int combIndex = 0; combIndex < combinations.get(index).size(); combIndex++) {
				filter.append(combinations.get(index).get(combIndex).toString());
				filter.append(",");
			}
			
			filter.deleteCharAt(filter.length() - 1);
			filter.append("]");
			
			System.out.println("\"" + filter + "\",");
		}
		
		return filters;
	}
	
	private static class Interceptor extends PrintStream
	{	
		PrintWriter writer;
		
		public Interceptor(PrintStream printStream) {
			super(printStream);
			try {
				this.writer = new PrintWriter("C:\\Users\\ncanedo\\Downloads\\log.log", "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
	    @Override
		public void println(String x) {
			// TODO Auto-generated method stub
			super.println(x);
			writer.println(x);
			this.writer.flush();
		}
		@Override
		public void println(Object x) {
			super.println(x);
			this.println(x.toString());
			
		}
		public Interceptor(OutputStream out)
	    {
	        super(out, true);
	    }
	    @Override
	    public void print(String s)
	    {//do what ever you like
	        super.print(s);
	    }
	}
}
