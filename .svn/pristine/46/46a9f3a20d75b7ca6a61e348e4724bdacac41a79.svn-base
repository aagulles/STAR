package org.irri.breedingtool.manager.impl;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.data.view.FileTableViewer;
import org.irri.breedingtool.datamanipulation.dialog.ChooseDelimiterDialog;
import org.irri.breedingtool.manager.api.IDataManipulation;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;
import org.irri.breedingtool.utility.DialogListValidator;

public class DataManipulationManager implements IDataManipulation{
	String lineSeparator = System.getProperty("line.separator");
	String fileSeparator = System.getProperty("file.separator");
	private ProjectExplorerManager projExpManager;

	public static ArrayList<Table> tableList = new ArrayList<Table>();

	public DataManipulationManager(){
		projExpManager = new ProjectExplorerManager();
	}

	@Override
	public void deleteTableRow(Table table, int rowIndex){
		table.remove(rowIndex);
	}

	@Override
	public void deleteTableColumn(Table table, int columnIndex) {
		// TODO Auto-generated method stub
		TableColumn column = table.getColumn(columnIndex);
		column.dispose();
		if(table.getColumnCount()==1){//if there's no column left except the row column,
			table.removeAll();//delete all table Items.
		}
	}

	@Override
	public void insertTableRow(Table table, int newRowIndex, ArrayList<String> tableData) {
		// TODO Auto-generated method stub
		TableItem newItem;
		tableData.add(newRowIndex, tableData.get(0).replaceAll("[^"+table.getData("delimiter")+"]",""));
		if(newRowIndex >= table.getItemCount()){
			newItem = new TableItem(table, SWT.CENTER);
		}
		else{
			newItem = new TableItem(table,SWT.NONE,newRowIndex);
		}
		newItem.setText(0, String.valueOf(newRowIndex));
		newItem.setText(tableData.get(0).replaceAll("[^"+table.getData("delimiter")+"]","").split((String) table.getData("delimiter")));
	}

	@Override
	public void insertTableColumn(Table table, int newColumnIndex,
			String newColumnHeaderName) {
		TableColumn column;
		// TODO Auto-generated method stub
		if(newColumnIndex >= table.getColumnCount()) column = new TableColumn(table, SWT.CENTER);
		else column = new TableColumn(table, SWT.CENTER, newColumnIndex);
		column.setText(newColumnHeaderName);
		column.setWidth(100);
	}

	@Override
	public void createRowNumberColumn(Table table) {
		// TODO Auto-generated method stub
		TableColumn column = new TableColumn(table, SWT.CENTER, 0);
		column.setText("");
		column.pack();
		column.setWidth(50);
		for(int i=0; i<table.getItemCount(); i++){
			TableItem item = table.getItem(i);
			item.setText(0, Integer.toString(i+1));
			item.setBackground(0,table.getShell().getBackground());
		}
	}

	@Override
	public void updateRowNumberColumn(Table table) {
		// TODO Auto-generated method stub
		for(int i=0; i<table.getItemCount(); i++){
			TableItem item = table.getItem(i);
			item.setText(0, Integer.toString(i+1));
			item.setBackground(0,table.getShell().getBackground());
		}
		((Label) table.getData("tableInfoLabel")).setText("Column(s): "+Integer.toString(table.getColumnCount()-1)+"   Row(s): "+Integer.toString(table.getItemCount()));
	}

	@Override
	public void saveDataAs(String currDir, Table table, String delimiter) {
		File currFile = new File(currDir);
		String filename = null;
		FileWriter writer = null;
		InputDialog fd = new InputDialog(Display.getCurrent().getActiveShell(),
				"Save File As", "Enter file name", "copyOf"+currFile.getName(), new FileNameValidator(currFile));
		try{
			fd.open();
			if(fd.getReturnCode()==0){
				String selected = currFile.getParentFile().getAbsolutePath()+fileSeparator+fd.getValue();

				String bSlash = "\\";
				String fSlash = "/";
				if (selected.lastIndexOf(bSlash) != -1) {
					filename = selected.replace(bSlash, fSlash);
				}

				StringBuilder sb = new StringBuilder();

				int numCols = table.getColumnCount();

				//check if the chosen file is .csv, then automatic its gonna be comma delineated. else, then just use the passed delimiter from the parameter.
				delimiter = ",";

				for (int colCount = 1; colCount < numCols; colCount++) {
					sb.append(table.getColumn(colCount).getText());
					if (colCount != numCols - 1) {
						sb.append(delimiter);
					} else {
						sb.append(lineSeparator);
					}
				}

				int numRows = table.getItemCount();
				for (int rowCount = 0; rowCount < numRows; rowCount++) {
					for (int colCount = 1; colCount < numCols; colCount++) {
						String itemText = table.getItem(rowCount).getText(colCount).replaceAll("\"", "");
						if(itemText.contains(",")) sb.append("\""+itemText+"\"");
						else sb.append(itemText);

						if (colCount != numCols - 1) {
							sb.append(delimiter);
						}
					}
					if (rowCount != numRows - 1) {
						sb.append(lineSeparator);
					}
				}
				try {
					writer = new FileWriter(filename);
					writer.write(sb.toString());
					if (writer != null){
						FileTableViewer.refreshExplorer();
						MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Save As", "Successfully created '"+new File(filename).getName()+"'!");
						writer.close();
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}catch(Exception npe){}
	}



	@Override
	public void saveFileChanges(File file, Table table, String delimiter ){
		System.out.println("Saving..."+ file.getName()+"...");

		FileWriter writer = null;
		StringBuilder sb = new StringBuilder();
		for (int colCount = 1; colCount < table.getColumnCount(); colCount++) {
			sb.append(table.getColumn(colCount).getText());
			if (colCount != table.getColumnCount() - 1) {
				sb.append(delimiter);
			} else {
				sb.append(lineSeparator);
			}
		}

		int numRows = table.getItemCount();
		for (int rowCount = 0; rowCount < numRows; rowCount++) {
			for (int colCount = 1; colCount < table.getColumnCount(); colCount++) {
				String itemText = table.getItem(rowCount).getText(colCount).replaceAll("\"", "");

				if(itemText.contains(",")) sb.append("\""+itemText+"\"");
				else sb.append(itemText);

				if (colCount != table.getColumnCount() - 1) {
					sb.append(delimiter);
				}
			}
			if (rowCount != numRows - 1) {
				sb.append(lineSeparator);
			}
		}
		try {
			writer = new FileWriter(file);
			writer.write(sb.toString());
			if (writer != null)
				writer.close();
		} catch (Exception e) {
		}

		//		try{
		//		updateVarInfoTempFile(file);
		//		}catch(IllegalArgumentException iae){
		//			
		//		}
	}

	@Override
	public void saveOriginal(File file, Table table, String delimiter ){
		String originalFile = file.getAbsolutePath().replaceAll(".csv", RJavaManagerInitializer.VARINFO_ORIGINALFILE_EXTENSION);
		System.out.println("Saving original "+originalFile+"...");
		FileWriter writer = null;
		StringBuilder sb = new StringBuilder();
		for (int colCount = 1; colCount < table.getColumnCount(); colCount++) {
			sb.append(table.getColumn(colCount).getText());
			if (colCount != table.getColumnCount() - 1) {
				sb.append(delimiter);
			} else {
				sb.append(lineSeparator);
			}
		}

		int numRows = table.getItemCount();
		for (int rowCount = 0; rowCount < numRows; rowCount++) {
			for (int colCount = 1; colCount < table.getColumnCount(); colCount++) {
				String itemText = table.getItem(rowCount).getText(colCount).replaceAll("\"", "");

				if(itemText.contains(",")) sb.append("\""+itemText+"\"");
				else sb.append(itemText);

				if (colCount != table.getColumnCount() - 1) {
					sb.append(delimiter);
				}
			}
			if (rowCount != numRows - 1) {
				sb.append(lineSeparator);
			}
		}
		try {
			writer = new FileWriter(originalFile);
			writer.write(sb.toString());
			if (writer != null)
				writer.close();
		} catch (Exception e) {
		}
		File original = new File(originalFile);
		original.deleteOnExit();
	}


	@Override
	public boolean checkColumnNameIfUsed(String value, String[] columnHeaders) {
		// TODO Auto-generated method stub
		for(int i=0; i<columnHeaders.length; i++){
			if(value.equals(columnHeaders[i]))return true;
		}
		return false;
	}


	@Override
	public String[] getListItems(ArrayList<String> varInfo) {
		ArrayList<String> variableNames = new ArrayList<String>();
		for(String s:varInfo){
			String[] tmp = s.split(":");
			variableNames.add(tmp[0]);
		}
		// TODO Auto-generated method stub
		return variableNames.toArray(new String[variableNames.size()]);
	}


	@Override
	public String[] getFilesFromDir(ProjectExplorerTreeNodeModel fileModel) {
		// TODO Auto-generated method stub
		int ctr = 0;
		File dir = new File( fileModel.getProjectFile().getParentFile().toString());
		String[] children = dir.list();
		ArrayList<String> transactionFiles = new ArrayList<String>();
		for(int i=0; i<children.length; i++){
			if(children[i].endsWith(".txt") || children[i].endsWith(".csv")){
				if(!children[i].equals(fileModel.getProjectFile().getName())){
					transactionFiles.add(children[i]);
					ctr++;
				}
			}
		}
		return transactionFiles.toArray(new String[(transactionFiles.size())]);
	}


	@Override
	public String[] getFileNamesFromDir(File file) {
		// TODO Auto-generated method stub
		int ctr = 0;
		File dir = new File( file.getParentFile().toString());
		String[] children = dir.list();
		ArrayList<String> transactionFiles = new ArrayList<String>();
		for(int i=0; i<children.length; i++){
			if(children[i].endsWith(".txt") || children[i].endsWith(".csv")){
				if(!children[i].equals(file.getName())){
					transactionFiles.add(children[i]);
					ctr++;
				}
			}
		}
		return transactionFiles.toArray(new String[(transactionFiles.size())]);
	}
	

	@Override
	public ArrayList<String> getVarInfoFromFile(String fileName) {
		// TODO Auto-generated method stub
		ArrayList<String> varInfo = new ArrayList<String>();
		String tmpFile = null;
		if(fileName.endsWith(".csv")) tmpFile = fileName.replaceAll(".csv", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);
		else tmpFile = tmpFile.replaceAll(".txt", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);
		try {
			Scanner newScanner = new Scanner(new File(tmpFile));
			newScanner.hasNext();
			while(newScanner.hasNext()){
				String readLine = newScanner.nextLine();
				readLine = readLine.replaceAll("\"", "");//remove quotation
				if(readLine.toLowerCase().endsWith("numeric") || readLine.toLowerCase().endsWith("factor"))varInfo.add(readLine);
			}
			newScanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return varInfo;
	}

	@Override
	public void updateVarInfoTempFile(File file){
		String fileName = file.getAbsolutePath();
		String tmpFile = null;
		if(fileName.endsWith(".csv")) tmpFile= fileName.replaceAll(".csv", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);
		else tmpFile = tmpFile.replaceAll(".txt", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);


		fileName=fileName.replace("\\", "/");
		String rJavaTmpFile = tmpFile.replace("\\", "/");
		try{
			ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().getVariableInfo(fileName, rJavaTmpFile, DataManipulationManager.getActiveDataFileFormat(file.getAbsolutePath()), DataManipulationManager.getActiveDataDelimiter(file.getAbsolutePath()));
		}catch(NullPointerException npe){
			projExpManager.deleteFile(tmpFile);
		}
	}


	@Override
	public boolean isMatchColumnHeaders(String[] fileColumnHeaders, ArrayList<String> tmpVarInfo) {
		// TODO Auto-generated method stub

		if(fileColumnHeaders.length != tmpVarInfo.size()) return false;
		try{
			for(int i=0; i< fileColumnHeaders.length; i++){
				String[] lineRead = tmpVarInfo.get(i).split(":");

				System.out.println(fileColumnHeaders[i]+" vs "+lineRead[0]);
				String columnHeaderName = fileColumnHeaders[i].replaceAll("\"","");
				if(!columnHeaderName.equals(lineRead[0])){
					return false;
				}
			}
		} catch(IndexOutOfBoundsException outOfBounds){
			return false;
		}
		return true;
	}

	@Override
	public  String[] updateColHeaderNames(Table table, String[] columnHeaderNames) {
		TableColumn[] columnNames=table.getColumns();
		columnHeaderNames = new String[table.getColumnCount()-1];
		for(int i=0; i<table.getColumnCount()-1; i++){
			columnHeaderNames[i]=columnNames[i+1].getText();
			table.getColumn(i).setData("index", i);//update indices
		}
		table.setData("columnHeaderNames", columnHeaderNames);
		((Label) table.getData("tableInfoLabel")).setText("Column(s): "+Integer.toString(table.getColumnCount()-1)+"   Row(s): "+Integer.toString(table.getItemCount()));
		return columnHeaderNames;
	}

	@Override
	public String[] getTableItemText(TableItem[] items) {
		// TODO Auto-generated method stub
		String tableItemText[] = new String[items.length];
		for(int i=0; i< items.length; i++){
			tableItemText[i] = items[i].getText();
			System.out.println(tableItemText[i]);
		}
		return tableItemText;
	}

	@Override
	public String[] getNumericVars(ArrayList<String> varInfo) {
		ArrayList<String> numVars = new ArrayList<String>();
		for(String s:varInfo){
			String[] tmp = s.split(":");
			if(tmp[1].toLowerCase().equals("numeric")){//If numeric
				numVars.add(tmp[0]);
			}
		}
		return numVars.toArray(new String[numVars.size()]);
	}

	@Override
	public String[] getFactorVars(ArrayList<String> varInfo) {
		ArrayList<String> factors = new ArrayList<String>();
		for(String s:varInfo){
			String[] tmp = s.split(":");
			if(tmp[1].toLowerCase().equals("factor")){//If numeric
				factors.add(tmp[0]);
			}
		}
		return factors.toArray(new String[factors.size()]);
	}

	public static Table getActiveTable(String elementId) {
		// TODO Auto-generated method stub
		System.out.println("Element ID:"+elementId);
		for(Table table: tableList){
			if(table.getData("ID").toString().equals(elementId)) return table;
		}
		return null;
	}

	public static int getActiveDataFileFormat(String elementId) {
		// TODO Auto-generated method stub
		for(Table table: tableList){
			System.out.println(table.getData("fileFormat").toString());
			if(table.getData("ID").toString().equals(elementId)) return Integer.parseInt(table.getData("fileFormat").toString());
		}
		return 0;
	}

	public static String getActiveDataDelimiter(String elementId) {
		// TODO Auto-generated method stub
		for(Table table: tableList){
			if(table.getData("ID").toString().equals(elementId)) return table.getData("delimiter").toString();
		}
		return null;
	}
	public static void addTable(Table newTable) {
		// TODO Auto-generated method stub
		tableList.add(newTable);
		System.out.println("adding Table .. "+newTable.getData("ID"));
	}

	public static void removeTable(Table table) {
		// TODO Auto-generated method stub
		System.out.println("removing Table .. "+table.getData("ID"));
		tableList.remove(table);
	}

	public static String getTimeStamp() {
		// TODO Auto-generated method stub
		Calendar now = Calendar.getInstance();
		return Long.toString(now.getTimeInMillis());
	}

	public static String[] getEnvtLevels(String facEnvt, File file) {
		int envtColumn = 0;
		Table table = DataManipulationManager.getActiveTable(file.getAbsolutePath());
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (table.getColumn(i).getText()
					.equals(facEnvt)) {
				envtColumn = i;
			}
		}

		ArrayList<String> envts = new ArrayList<String>();
		for (int j = 0; j < table.getItemCount(); j++) {
			String level = table.getItem(j).getText(
					envtColumn);
			if (!envts.contains(level)&& !level.isEmpty()) {
				envts.add(level);
			}
		}

		String[] envtLevels = new String[envts.size()];
		for (int k = 0; k < envts.size(); k++) {
			envtLevels[k] = (String) envts.get(k);
		}

		return envtLevels;
	}
	
	public static void saveVarTempFile(String absolutePath, ArrayList<String> varInfo) {
		// TODO Auto-generated method stub
		String fileName = absolutePath;
		String tmpFile = null;
		if(fileName.endsWith(".csv")) tmpFile = fileName.replaceAll(".csv", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);
		else tmpFile = fileName.replaceAll(".txt", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);
		FileWriter writer = null;
		StringBuilder sb = new StringBuilder();
		System.out.println("saving variables");
		for(String s:varInfo){
			System.out.println(s);
			sb.append(s);
			sb.append(System.getProperty("line.separator"));
		}
		try {
			writer = new FileWriter(tmpFile);
			writer.write(sb.toString());
			if (writer != null)
				writer.close();
		} catch (Exception e) {
			System.out.println("can't write file to:"+tmpFile);
		}
	}

	@Override
	public void editVariableType(String filePath, ArrayList<String> varInfo, String varName, String newType) {
		// TODO Auto-generated method stub
		int ctr=0;
		for(String s:varInfo){
			String[] tmp = s.split(":");
			if(varName.equals(tmp[0])){
				s=tmp[0]+":"+newType;
				varInfo.set(ctr, s);
			}
			ctr++;
		}
		saveVarTempFile(filePath, varInfo);
	}

	@Override
	public void deleteVariableFromVarInfoTmp(String filePath, String varName) {
		// TODO Auto-generated method stub
		int ctr=0;
		int deleteVar=0;

		ArrayList<String> varInfo = getVarInfoFromFile(filePath);
		String dottedVarName=varName.replaceAll(" ",".");

		for(String s:varInfo){
			String[] tmp = s.split(":");
			if(varName.equals(tmp[0]) || dottedVarName.equals(tmp[0])){
				deleteVar=ctr;
			}
			ctr++;
		}
		varInfo.remove(deleteVar);
		saveVarTempFile(filePath, varInfo);
	}


	@Override
	public void convertTxtToCsv(String selectedTxtFilePath, String newCsvFilePath, String selectedFiledelimiter){
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		try {
			Scanner newScanner = new Scanner(new File(selectedTxtFilePath));//If the tmpFile already exist

			while(newScanner.hasNext()){
				String readLine = newScanner.nextLine();
				System.out.println(readLine);
				readLine=readLine.replaceAll(selectedFiledelimiter,",");
				//				readLine=readLine.replaceAll(" ",",");
				//				readLine=readLine.replaceAll("\t", ",");
				sb.append(readLine);
				sb.append(System.getProperty("line.separator"));
			}
			newScanner.close();
		} catch (FileNotFoundException e) {
		}

		try {
			FileWriter writer = new FileWriter(newCsvFilePath);
			writer.write(sb.toString());
			if (writer != null)
				writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public double convertInttoDouble(int selection, int digits) {
		// TODO Auto-generated method stub
		Double decimal = selection / Math.pow(10, digits);
		return decimal;
	}

	@Override
	public void insertTableRow(Table table, int newRowIndex) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keepOriginalFile(File projectFile) {
		// TODO Auto-generated method stub
		String fileName = projectFile.getName();
		String originalFilePath = projectFile.getAbsolutePath().replaceAll(".csv",RJavaManagerInitializer.VARINFO_ORIGINALFILE_EXTENSION);

	
			//delete the current file
			projExpManager.deleteFile(projectFile.getAbsolutePath());
			//replace it with the originalfile
			projExpManager.renameFile(originalFilePath, fileName);
		

	}


	@Override
	public void addNewVariableNameToTmpVarInfo(String absolutePath, String varName) {
		// TODO Auto-generated method stub
		BufferedWriter bw = null;
		String tmpFile = absolutePath.replaceAll(".csv", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);
		try {
			bw = new BufferedWriter(new FileWriter(tmpFile, true));
			bw.write(varName+":"+"Factor");
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {                       // always close the file
			if (bw != null) try {
				bw.close();
			} catch (IOException ioe2) {
				// just ignore it
			}
		} // end try/catch/finally
	}


	@Override
	public String[] getTxtFilesFromDir(File file) {
		// TODO Auto-generated method stub
		int ctr = 0;
		File dir = new File( file.getParentFile().toString());
		String[] children = dir.list();
		ArrayList<String> transactionFiles = new ArrayList<String>();
		for(int i=0; i<children.length; i++){
			if(children[i].endsWith(".txt")){
				if(!children[i].equals(file.getName())){
					transactionFiles.add(children[i]);
					ctr++;
				}
			}
		}
		return transactionFiles.toArray(new String[(transactionFiles.size())]);
	}


	@Override
	public String[] getCsvFilesFromDir(File file) {
		// TODO Auto-generated method stub
		int ctr = 0;
		File dir = new File( file.getParentFile().toString());
		String[] children = dir.list();
		ArrayList<String> transactionFiles = new ArrayList<String>();
		for(int i=0; i<children.length; i++){
			if(children[i].endsWith(".csv")){
				if(!children[i].equals(file.getName())){
					transactionFiles.add(children[i]);
					ctr++;
				}
			}
		}
		return transactionFiles.toArray(new String[(transactionFiles.size())]);
	}


	@Override
	public String isNumeric(String absolutePath, String columnName){
		// TODO Auto-generated method stub
		System.out.println("called isNumeric");
		String isNumeric = "FALSE";
		try{
			isNumeric = ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().isColumnNumeric(absolutePath.replaceAll("\\\\","/"), columnName);
		}
		catch(NullPointerException npe){
			isNumeric = ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().isColumnNumeric(absolutePath.replaceAll("\\\\","/"), columnName.replaceAll(" ","."));
			System.out.println("caught Exception from isNumericable");
		}

		return isNumeric;
	}

	@Override
	public void removeItemOfThenAddTo(List varList, List returnList){
		// TODO Auto-generated method stub
		if(varList.getItemCount()>0){
			returnList.add(varList.getItem(0));
			varList.setToolTipText("");
			varList.removeAll();
		}
	}


	@Override
	public void moveSelectedStringFromTo(List fromList, List toList) {
		// TODO Auto-generated method stub
		String[] selectedStrings = fromList.getSelection();
		if(selectedStrings.length>0){
			for(int i=0; i< selectedStrings.length; i++){
				toList.add(selectedStrings[i]);
			}
			fromList.remove(fromList.getSelectionIndices());
			if(toList.getItemCount()==1){
				toList.setToolTipText(selectedStrings[0]);
			}else toList.setToolTipText("");
		}
	}
	

	public void moveSelectedStringFromTo(List fromList, List toList, Button moveBtn) {
		// TODO Auto-generated method stub
		String[] selectedStrings = fromList.getSelection();
		if(selectedStrings.length>0){
			for(int i=0; i< selectedStrings.length; i++){
				toList.add(selectedStrings[i]);
			}
			fromList.remove(fromList.getSelectionIndices());
			if(toList.getItemCount()==1){
				toList.setToolTipText(selectedStrings[0]);
			}else toList.setToolTipText("");
			moveBtn.setEnabled(false);
		}
	}
	

	@Override
	public void moveSelectedStringFromListToText(List fromList, Text toText) {
		// TODO Auto-generated method stub
		String[] selectedStrings = fromList.getSelection();
		if(selectedStrings.length>0){
			toText.setText(fromList.getItem(0));
			fromList.remove(fromList.getSelectionIndices());
		}
	}

	@Override
	public void moveSelectedStringFromTextToList( Text fromText, List toList) {
		// TODO Auto-generated method stub
		toList.add(fromText.getText());
		fromText.setText("");
	}
	@Override
	public String getManipulatedFileNameExtension(String originalExtension,
			String dataFilePath) {
		String newExtension = originalExtension+".csv";
		String createdFilePath = "";
		int ctr=1;

		createdFilePath = dataFilePath.replaceAll(".csv", newExtension);
		File newFile = new File(createdFilePath);
		System.out.println("created path: "+createdFilePath);
		while(newFile.exists()){//If the file already exist
			newExtension = originalExtension+"("+Integer.toString(ctr)+").csv";
			System.out.println(Integer.toString(ctr)+" : "+newExtension);
			createdFilePath = dataFilePath.replaceAll(".csv", newExtension);
			newFile = new File(createdFilePath);
			ctr++;
		}
		return newExtension;
	}


	@Override
	public String variableNameInputValidation(String inputName){
		System.out.println("validating variable Name: "+inputName);
		for (int i=0;i<inputName.length();++i) {
			if(i==0 && !Character.isLetter(inputName.charAt(i))) return "Invalid Variable Name.\n";
			else{
				if (!Character.isLetterOrDigit(inputName.charAt(i))) {
					if(inputName.charAt(i)!='.' && inputName.charAt(i)!='_') return "Invalid Variable Name.\n";
				}
			}
		}
		return "true";
	}


	@Override
	public String getDisplayFileName(String absolutePath){
		String newFileName = "";
		String dataPath = ((ProjectExplorerTreeNodeModel)projExpManager.getDataFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getAbsolutePath();
		String outputFolderPath = ((ProjectExplorerTreeNodeModel)projExpManager.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getAbsolutePath();
		if(new File(absolutePath).isFile()){
			if(!absolutePath.contains("Output"))newFileName = absolutePath.replace(dataPath+fileSeparator,"");
			else newFileName = absolutePath.replace(outputFolderPath+fileSeparator,"");
		}
		else newFileName = absolutePath.replace(outputFolderPath+fileSeparator,"");
		return newFileName;
	}

	public void removeItemOfThenAddTo(Text txtFrom, List lstTo) {
		// TODO Auto-generated method stub
		lstTo.add(txtFrom.getText());
		txtFrom.setText("");
	}
	
}
