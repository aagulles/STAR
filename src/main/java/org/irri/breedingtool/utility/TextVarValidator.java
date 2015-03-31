package org.irri.breedingtool.utility;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.irri.breedingtool.manager.impl.DataManipulationManager;

public class TextVarValidator extends DialogListValidator {
	List factorVarList;
	Button performMultiRadio;
	File file;
	Text txtVar;
	Label lblVar;

	public TextVarValidator(Text txtVar, Label lblVar) {
		super();

		this.txtVar = txtVar;
		this.lblVar = lblVar;

		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validate(){
		if(!lblVar.getEnabled()) return false;
		return txtVar.getText().isEmpty();
	}
}


