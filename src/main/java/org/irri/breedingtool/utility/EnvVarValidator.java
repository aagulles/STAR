package org.irri.breedingtool.utility;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.irri.breedingtool.manager.impl.DataManipulationManager;

public class EnvVarValidator extends DialogListValidator {
DataManipulationManager dataManipulationManager = new DataManipulationManager();

List factorVarList;
Boolean performMultiEnv;
File file;
Text txtEnvVar;
Label lblEnvironment;

public EnvVarValidator(List factorVarList, Boolean performMultiEnv, File file, Text envVarText, Label lblEnvironment) {
			super();
			this.factorVarList = factorVarList;
			this.performMultiEnv = performMultiEnv;
			this.file = file;
			this.txtEnvVar = envVarText;
			this.lblEnvironment = lblEnvironment;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public boolean validate(){
			return txtEnvVar.getText().isEmpty();
		}
		
		@Override
		public boolean performAddProcess(){
			String selectedNumVars[] = factorVarList.getSelection();
			String[] levelItems = dataManipulationManager.getEnvtLevels(selectedNumVars[0], file);
			if(levelItems.length<2 && performMultiEnv){
				MessageDialog.openWarning(factorVarList.getShell(), "Invalid factor", "The environment factor should have at least two (2) levels.\n\n" +
						//selectedNumVars[0]+" has 1 level only. \n\n" +
						"Please choose a different environment factor.");
				return false;
			}
//			else{
//				txtEnvVar.setText(selectedNumVars[0]);
//				factorVarList.remove(factorVarList.getSelectionIndices());
//			}
			return true;
		}
	}
	

