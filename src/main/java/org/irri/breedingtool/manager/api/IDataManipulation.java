package org.irri.breedingtool.manager.api;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;

public interface IDataManipulation {

	/**
	 * Adds the specified variable name to the var_info.tmp file of the given file path. 
	 * The automatic variable type will be set as Factor.
	 * 
	 * @param absolutePath
	 *            the path of the csv file where you want to add the variable.
	 * @param varName
	 * 			  the name of the variable that you want to add to the var_info.
	 */
	void addNewVariableNameToTmpVarInfo(String absolutePath, String varName);

	/**
	 * Returns true if the specified variable name is found inside the list of the specified column header names. 
	 * 
	 * @param value
	 *            the name of the variable that you want to search for.
	 * @param columnHeaders
	 * 			  the list of the existing column header names.
	 */
	boolean checkColumnNameIfUsed(String value, String[] columnHeaders);
	
	/**
	 * Converts a delimited txt file to a comma-delimited(csv) file.
	 * 
	 * @param selectedTxtFilePath
	 *            absolute path of the source txt file.
	 * @param newCsvFilePath
	 *            absolute path of the output converted (.csv) file.
	 * @param selectedFiledelimiter
	 * 			  the delimiter used in the source txt file.
	 */
	void convertTxtToCsv(String selectedTxtFilePath, String newCsvFilePath, String selectedFiledelimiter);

	/**
	 * Adds a new column to the beginning of the specified table which will
	 * indicate the number(counter) for each row depending on the content of the table.
	 * 
	 *@param table
	 *            the table where you want to add a row number column
	 */
	public void createRowNumberColumn(Table table);
	
	/**
	 * Deletes a specific column from a given table in accordance to the given column index.
	 * 
	 * @param table
	 *            the table where you want to delete a column.
	 * @param columnIndex
	 * 			  the index of the column you want to delete.
	 */
	public void deleteTableColumn(Table table, int columnIndex);

	/**
	 * Deletes a specific row from a given table in accordance to the given row index.
	 * 
	 * @param table
	 *            the table where you want to delete a row.
	 * @param rowIndex
	 * 			  the index of the row you want to delete.
	 */
	public void deleteTableRow(Table table, int rowIndex);

	/**
	 * Deletes the specified variable from the list of variables inside the corresponding var_info.tmp
	 * of the indicated file.
	 * 
	 * @param filePath
	 *            the absolute path of the csv file.
	 * @param varName
	 * 			  the name of the variable you want to delete.
	 */
	void deleteVariableFromVarInfoTmp(String filePath, String varName);

	
	/**
	 * Edits the variable type of a specific variable from the var_info.tmp
	 * 
	 * 
	 * @param filePath
	 *            the absolute path of the csv file.
	 * @param varInfo
	 * 			  the original list of the variables' infomation (see getVarInfoFromFile)
	 * @param varName
	 * 			  the name of the variable you want to edit.
	 * @param newType
	 * 			  the new variable type either Factor or Numeric.
	 */
	void editVariableType(String filePath, ArrayList<String> varInfo,
			String varName, String newType);


	/**
	 * Returns the address of the file with specific subfolders which is used for title display.
	 * 
	 * @param absolutePath
	 * 			  the absolute path of the file.
	 */
	String getDisplayFileName(String absolutePath);

	String[] getCsvFilesFromDir(File file);

	/**
	 * Returns the names of the numeric variables only.
	 * 
	 * @param varInfo
	 * 			  the original list of the variables' infomation (see getVarInfoFromFile)
	 */
	public String[] getNumericVars(ArrayList<String> varInfo);

	/**
	 * Returns the names of the factor variables only.
	 * 
	 * @param varInfo
	 * 			  the original list of the variables' infomation (see getVarInfoFromFile).
	 */
	public String[] getFactorVars(ArrayList<String> varInfo);

	
	String[] getFileNamesFromDir(File file);
	
	/**
	 * Returns the variable names from a given array of variable information.
	 * 
	 * @param table
	 * 			  
	 * @param columnHeaderNames
	 * 			  
	 */
	public String[] getFilesFromDir(ProjectExplorerTreeNodeModel fileModel);
	

	/**
	 * Returns the variable names from a given array of variable information.
	 * 
	 * @param table
	 * 			  
	 * @param columnHeaderNames
	 * 			  
	 */
	public String[] getListItems(ArrayList<String> varInfo);

	String getManipulatedFileNameExtension(String originalExtension, String dataFilePath);
	
	/**
	 * 
	 * 
	 * @param table
	 * 			  
	 * @param columnHeaderNames
	 * 			  
	 */
	public  String[] getTableItemText(TableItem[] items);
	
	String[] getTxtFilesFromDir(File file);

	/**
	 * Returns the variable names from a given array of variable information.
	 * 
	 * @param table
	 * 			  
	 * @param columnHeaderNames
	 * 			  
	 */
	public ArrayList<String> getVarInfoFromFile(String fileName);
	
	/**
	 * Inserts a column to a given table in accordance to the given column index and then sets the column name.
	 * 
	 * @param table
	 *            the table where you want to insert a column.
	 * @param newColumnIndex
	 * 			  the index of the column you want to insert.
	 * @param newColumnHeaderName
	 * 			  the name of the column you want to insert.
	 */
	public void insertTableColumn(Table table, int newColumnIndex, String newColumnHeaderName);
	
	/**
	 * Inserts a row to a given table in accordance to the given row index.
	 * 
	 * @param table
	 *            the table where you want to insert a row.
	 * @param newRowIndex
	 * 			  the index of the row you want to insert.
	 */
	public void insertTableRow(Table table, int newRowIndex);


	void insertTableRow(Table table, int newRowIndex, ArrayList<String> tableData);

	boolean isMatchColumnHeaders(String[] fileColumnHeaders, ArrayList<String> tmpVarInfo);
	
	String isNumeric(String absolutePath, String columnName);


	
	
	void keepOriginalFile(File projectFile);

	void moveSelectedStringFromTo(List fromList, List toList);

	void removeItemOfThenAddTo(List varList, List returnList);
	
	/**
	 * Saves the table data as a new delimited file to the directory of the source file.
	 * 
	 * @param currDir
	 * 			  the absoluteFilePath of the source file.
	 * @param table
	 *            the table where you want to get the data from.
	 * @param delimiter
	 * 			  the delimiter used in the data (eg: if csv, then ",").
	 */
	public void saveDataAs(String currDir, Table table, String delimiter);
	
	/**
	 * 
	 * 
	 * @param file
	 * 			  
	 * @param table
	 *            
	 * @param delimiter
	 * 			  
	 */
	public void saveFileChanges(File file, Table table, String delimiter );
	

	void saveOriginal(File file, Table table, String delimiter);

	/**
	 * 
	 * 
	 * @param table
	 * 			  
	 * @param columnHeaderNames
	 * 			  
	 */
	public  String[] updateColHeaderNames(Table table, String[] columnHeaderNames);
	
	/**
	 *
	 */
	public void updateRowNumberColumn(Table table);

	void updateVarInfoTempFile(File file);

	String variableNameInputValidation(String inputName);

	void moveSelectedStringFromListToText(List fromList, Text toText);

	void moveSelectedStringFromTextToList(Text fromText, List toList);
	
}
