package org.irri.breedingtool.manager.impl;

import java.io.File;

import org.eclipse.jface.dialogs.IInputValidator;

public class FileNameValidator implements IInputValidator {
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private File currFile;

	public FileNameValidator(File currFile) {
		this.currFile = currFile;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String isValid(String newText) {
		// TODO Auto-generated method stub
		if(!newText.endsWith(".csv")) return "Please enter a valid file name with the correct file extension(.csv)";
		else if(filenameExist(newText))  return "This filename already exist!";
		else return null;
	}

	private boolean filenameExist(String newText) {
		// TODO Auto-generated method stub
		String fileNames[] = dataManipulationManager.getFileNamesFromDir(currFile);
		for(String s: fileNames){
			if(s.equals(newText))return true;
		}
		return false;
	}

}
