package org.irri.breedingtool.graphs.dialog;

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
import org.eclipse.wb.swt.ResourceManager;

public class ChooseLineTypesDialog extends Dialog {

	private String chosenPattern="";
	private Button btn4, btn3, btn5, btn2;
	private Shell parentShell;
	private Button btnPlainTxt;
	private Button btn1;
	private Label label;
	private Label label_1;
	private Label label_3;
	private Label label_4;
	private Label label_5;
	private Button btn6;
	private Label label_6;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ChooseLineTypesDialog(Shell parentShell) {
		super(parentShell);
		this.parentShell = parentShell;
	}
	
	public String getChosenPattern(){
		return chosenPattern;
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Specify line pattern to be used.");
		Composite container = (Composite) super.createDialogArea(parent);
		
		Group grpLineUsedIn = new Group(container, SWT.NONE);
		grpLineUsedIn.setText("Line Patterns");
		grpLineUsedIn.setLayout(new GridLayout(2, false));
		GridData gd_grpLineUsedIn = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpLineUsedIn.heightHint = 139;
		gd_grpLineUsedIn.widthHint = 235;
		grpLineUsedIn.setLayoutData(gd_grpLineUsedIn);
		
		btn1 = new Button(grpLineUsedIn, SWT.RADIO);
		btn1.setSelection(true);
		
		label = new Label(grpLineUsedIn, SWT.NONE);
		label.setImage(ResourceManager.getPluginImage("Star", "icons/line1.png"));
		new Label(grpLineUsedIn, SWT.NONE);
		new Label(grpLineUsedIn, SWT.NONE);
		btn2 = new Button(grpLineUsedIn, SWT.RADIO);
		btn2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		label_1 = new Label(grpLineUsedIn, SWT.NONE);
		label_1.setImage(ResourceManager.getPluginImage("Star", "icons/line2.png"));
		new Label(grpLineUsedIn, SWT.NONE);
		new Label(grpLineUsedIn, SWT.NONE);
		
		btn3 = new Button(grpLineUsedIn, SWT.RADIO);
		btn3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		label_5 = new Label(grpLineUsedIn, SWT.NONE);
		label_5.setImage(ResourceManager.getPluginImage("Star", "icons/line3.png"));
		new Label(grpLineUsedIn, SWT.NONE);
		new Label(grpLineUsedIn, SWT.NONE);
		
		btn4 = new Button(grpLineUsedIn, SWT.RADIO);
		btn4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		label_3 = new Label(grpLineUsedIn, SWT.NONE);
		label_3.setImage(ResourceManager.getPluginImage("Star", "icons/line4.png"));
		new Label(grpLineUsedIn, SWT.NONE);
		new Label(grpLineUsedIn, SWT.NONE);
		
		btn5 = new Button(grpLineUsedIn, SWT.RADIO);
		btn5.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		label_4 = new Label(grpLineUsedIn, SWT.NONE);
		label_4.setImage(ResourceManager.getPluginImage("Star", "icons/line5.png"));
		new Label(grpLineUsedIn, SWT.NONE);
		new Label(grpLineUsedIn, SWT.NONE);
		
		btn6 = new Button(grpLineUsedIn, SWT.RADIO);
		
		label_6 = new Label(grpLineUsedIn, SWT.NONE);
		label_6.setImage(ResourceManager.getPluginImage("Star", "icons/line6.png"));
		
		return container;
	}

	@Override
	protected void okPressed(){
		//CALL THE RJAVA FUNCTION HERE
		if(btn1.getSelection()) chosenPattern = "1";
		if(btn2.getSelection()) chosenPattern = "2";
		if(btn3.getSelection()) chosenPattern = "3";
		if(btn4.getSelection()) chosenPattern = "4";
		if(btn5.getSelection()) chosenPattern = "5";
		if(btn6.getSelection()) chosenPattern = "6";
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
		return new Point(513, 408);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}
