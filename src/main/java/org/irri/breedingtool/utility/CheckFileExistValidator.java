package org.irri.breedingtool.utility;

import java.io.File;

import org.eclipse.jface.dialogs.IInputValidator;

public class CheckFileExistValidator implements IInputValidator  {
	  /**
	   * Validates the String. Returns null for no error, or an error message
	   * 
	   * @param fileName the String to validate
	   * @return String
	   */
	private String folderPath = "";
	private String defaultFileName = "";
	private boolean canOverwrite = false;
	
	public CheckFileExistValidator(String fileName, String folderURL, boolean flag_CanOverwrite){
		folderPath = folderURL;
		defaultFileName = fileName;
		canOverwrite = flag_CanOverwrite;
	}
	
	public String isValid(String fileName) {
		   
		  File newFile = new File(folderPath + File.separator + fileName);
		  if(newFile.getName().toString().equals(defaultFileName)) 
		  {
			 if(canOverwrite == true) return null;
			  return "";
			  
			  
		  }
		  if(fileName.length() < 4) return "";
			  if(!GeneralUtility.validateFileName(fileName)){
				  return "Not a valid File/Folder name.";
			  }
		  if(newFile.exists()){
			  if(newFile.isDirectory()) return "Folder already exist";
			  return "File already exist!";
		  }
	    // Input must be OK
	    return null;
	  }
}