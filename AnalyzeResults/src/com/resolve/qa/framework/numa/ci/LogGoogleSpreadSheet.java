package com.resolve.qa.framework.numa.ci;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.glassfish.jersey.internal.util.Base64;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;
import com.resolve.qa.framework.numa.AnalizeResults;

public class LogGoogleSpreadSheet {
	public static WorksheetEntry createWorkshet(ArrayList<String> logMap) throws IOException, ServiceException {
		String spreadsheetURLString = "https://spreadsheets.google.com/feeds/spreadsheets/private/full/1osV-3jyIXK-zCJnpVQ3PxlX-Gz8BUR77qCuUbZYkoyk";

		SpreadsheetService service = new SpreadsheetService("com.example");
		service.setUserCredentials(AnalizeResults.GOOGLE_USER, Base64.decodeAsString(AnalizeResults.GOOGLE_PASSWORD));

		URL spreadsheetURL = new URL(spreadsheetURLString);

		SpreadsheetEntry spreadsheet = service.getEntry(spreadsheetURL, SpreadsheetEntry.class);

		WorksheetEntry worksheet = new WorksheetEntry();
		worksheet.setTitle(new PlainTextConstruct("Fail Results" + (new Date())));
		worksheet.setRowCount(1);
		worksheet.setColCount(logMap.size());
		worksheet = service.insert(spreadsheet.getWorksheetFeedUrl(), worksheet);
		
		int cellIndex = 1;
		
		Iterator<String> columnIterator = logMap.iterator();
		
		while(columnIterator.hasNext()) {
			service.insert(worksheet.getCellFeedUrl(), new CellEntry(1, cellIndex++, columnIterator.next()));
		}
		
		return worksheet;
	}
	
	public static void logResult(WorksheetEntry worksheet, HashMap<String, String> logMap, int iteration) throws IOException, ServiceException {
    	ListEntry row = new ListEntry();
    	
    	Iterator<String> columnIterator = logMap.keySet().iterator();
		
		while(columnIterator.hasNext()) {
			String columnName = columnIterator.next();
			row.getCustomElements().setValueLocal(columnName, logMap.get(columnName));    					
		}
		
		row.getCustomElements().setValueLocal("Iteration", "" + iteration);
    	
    	row = worksheet.getService().insert(worksheet.getListFeedUrl(), row);
    	
	}
}
