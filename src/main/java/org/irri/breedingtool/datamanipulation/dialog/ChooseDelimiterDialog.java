package org.irri.breedingtool.datamanipulation.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;

public class ChooseDelimiterDialog extends Dialog {

	private String chosenDelimiter="";
	private Button btnSemicolon, btnComma, btnSpace, btnTab;
	private Shell parentShell;
	private Button btnPlainTxt;
	private boolean importCalled;
	private String fileName;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ChooseDelimiterDialog(Shell parentShell, boolean importCalled, String fileName) {
		super(parentShell);
		this.parentShell = parentShell;
		this.importCalled = importCalled;
		this.fileName = fileName;
	}
	
	public String getDelimiter(){
		return chosenDelimiter;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Specify Delimiter for: " + fileName);
		Composite container = (Composite) super.createDialogArea(parent);
		
		Group grpDelimiterUsedIn = new Group(container, SWT.NONE);
		grpDelimiterUsedIn.setText("Delimiter used in the text file: (choose one)");
		grpDelimiterUsedIn.setLayout(new GridLayout(1, false));
		GridData gd_grpDelimiterUsedIn = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpDelimiterUsedIn.heightHint = 139;
		gd_grpDelimiterUsedIn.widthHint = 235;
		grpDelimiterUsedIn.setLayoutData(gd_grpDelimiterUsedIn);
		
		btnComma = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnComma.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		btnComma.setText("Comma");
		
		if(!importCalled){
			btnPlainTxt = new Button(grpDelimiterUsedIn, SWT.RADIO);
			btnPlainTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			btnPlainTxt.setText("Plain Text");
		}
		btnTab = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnTab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnTab.setText("Tab");
		
		btnSemicolon = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnSemicolon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnSemicolon.setText("Semi-colon");
		
		btnSpace = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnSpace.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnSpace.setText("Space");
		
		return container;
	}

	@Override
	protected void okPressed(){
		//CALL THE RJAVA FUNCTION HERE
		if(btnSemicolon.getSelection()) chosenDelimiter = ";";
		else if(btnComma.getSelection())chosenDelimiter = ",";
		else if(btnTab.getSelection()) chosenDelimiter = "\t";
		else if(btnSpace.getSelection()) chosenDelimiter = " ";
		else close();
		close();
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(430, 319);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}
