package org.irri.breedingtool.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableUtility {

	
	public static String[] saveTableToCsv(Table[] table, String folderPath){
		ArrayList<String> returnPaths = new ArrayList<String>();
		for(Table data: table){
			String filePath  = folderPath + File.separator + + Math.random() + "tmp.csv";
			saveTableToCsv(data,filePath);
			returnPaths.add(filePath);
			
		}
		return returnPaths.toArray(new String[returnPaths.size()]);
	}
	
	public static void saveTableToCsv(Table table, String filePath){

		ArrayList<String> data = new ArrayList<String>();
		//Column First
		String[] columns = new String[table.getColumnCount()];
		
		for(int i = 0; i < table.getColumnCount(); i++){
			columns[i] = table.getColumn(i).getText();
		}
		data.add(implode(columns,","));
		for(int i = 0; i < table.getItemCount(); i++){
			data.add(implode(table.getItem(i),",",table.getColumnCount()));
		}
		  FileWriter writer = null;
			try {
				writer = new FileWriter(filePath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				write(data,writer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	private static void write(ArrayList<String> records, Writer writer) throws IOException {
	    long start = System.currentTimeMillis();
	    for (String record: records) {
	        writer.write(record + System.getProperty("line.separator"));
	    }
	    writer.flush();
	    writer.close();
	    long end = System.currentTimeMillis();
	    System.out.println((end - start) / 1000f + " seconds");
	}
	  public static String implode(String[] array, String separator) {
		    StringBuffer out = new StringBuffer();
		    boolean first = true;
		    for (String v : array) {
		      if (first)
		        first = false;
		      else
		        out.append(separator);
		      out.append(v);
		    }
		    return out.toString();
		  }
	  public static String implode(TableItem tableItem, String separator, int columnCount) {
		  	String[] array = new String[columnCount];
		  	for(int i = 0; i < columnCount; i++){
		  		array[i] = tableItem.getText(i);
		  	}
		    StringBuffer out = new StringBuffer();
		    boolean first = true;
		    for (String v : array) {
		      if (first)
		        first = false;
		      else
		        out.append(separator);
		      out.append(v);
		    }
		    return out.toString();
		  }
}
