package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class NonParametricOneSampleTestDialog extends Dialog {
	private String filePath = PartStackHandler.getActiveElementID();
	private List lstNumericVariables;
	private Button btnAdd;
	private Spinner txtTestValue;
	private DialogFormUtility listManager = new DialogFormUtility();
	private List lstTestVariables;
	private Button btnOkay;
	private Label label_2;
	private Combo cmbAlternativeHypothesis;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NonParametricOneSampleTestDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("One Sample Non-Parametric : " + new File(filePath).getName());
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		FillLayout fl_container = new FillLayout(SWT.HORIZONTAL);
		fl_container.marginHeight = 8;
		fl_container.marginWidth = 8;
		container.setLayout(fl_container);
		
		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		
		TabItem tbtmTitleCase = new TabItem(tabFolder, SWT.NONE);
		tbtmTitleCase.setText("Variable Description");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmTitleCase.setControl(composite);
		composite.setLayout(new GridLayout(3, false));
		
		Label lblNumericVariables = new Label(composite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblNumericVariables.setText("Numeric Variable(s)");
		new Label(composite, SWT.NONE);
		
		Label lblTestVariables = new Label(composite, SWT.NONE);
		lblTestVariables.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblTestVariables.setText("Test Variable(s)");
		
		lstNumericVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_lstNumericVariables.heightHint = 200;
		gd_lstNumericVariables.widthHint = 80;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);
		
		btnAdd = new Button(composite, SWT.NONE);
		GridData gd_btnAdd = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_btnAdd.widthHint = 90;
		btnAdd.setLayoutData(gd_btnAdd);
		btnAdd.setText("Add");
		
		lstTestVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstTestVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstTestVariables.widthHint = 80;
		gd_lstTestVariables.heightHint = 170;
		lstTestVariables.setLayoutData(gd_lstTestVariables);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblTestValue = new Label(composite, SWT.NONE);
		lblTestValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTestValue.setText("Test Value:");
		
		txtTestValue = new Spinner(composite, SWT.BORDER);
		txtTestValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		txtTestValue.setMaximum(100000);
				
						
						
						label_2 = new Label(composite, SWT.NONE);
						label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
						label_2.setText("Alternative Hypothesis:");
		
		cmbAlternativeHypothesis = new Combo(composite, SWT.READ_ONLY);
		cmbAlternativeHypothesis.setItems(new String[] {"less", "greater", "two.sided"});
		cmbAlternativeHypothesis.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbAlternativeHypothesis.setText("two.sided");
		
		initializeContent();
		return container;
	}
	
	void initializeContent(){
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeSingleButtonList(lstNumericVariables, lstTestVariables, btnAdd);
		
	}
	void resetDialog(){
		lstNumericVariables.removeAll();
		lstTestVariables.removeAll();
		txtTestValue.setSelection(0);
		cmbAlternativeHypothesis.select(2);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		btnAdd.setText("Add");
		btnAdd.setEnabled(false);
		
	}
	@Override
	protected void buttonPressed(int ID){
		if(ID == IDialogConstants.OK_ID){
			okPressed();
		}
		else if(ID == IDialogConstants.CANCEL_ID){
			cancelPressed();
		}
		else if (ID == IDialogConstants.RETRY_ID){
			resetDialog();
		}
	}
	@Override
	protected void okPressed(){

		if (lstTestVariables.getItemCount() >= 1) {
			btnOkay.setEnabled(false);
			String[] respvars = lstTestVariables.getItems();
			Integer testValue = txtTestValue.getSelection() / 100;
			String altHypo = cmbAlternativeHypothesis.getText();
	
			String outputFolder = StarAnalysisUtilities
					.createOutputFolder(filePath,"NonParametricOneSample");
			boolean  confInt = true;
			OperationProgressDialog rInfo = new OperationProgressDialog(
					getShell(), "Performing Analysis");
			rInfo.open();
			String dataFileName = filePath.replace(File.separator, "/");
			//supply path and name of text file where text output is going to be saved
			String outFileName = outputFolder + "//Output.txt";
			StarAnalysisUtilities.testVarArgs(
					dataFileName,
					outputFolder.replace(File.separator, "/"),
					respvars,
					testValue,
					altHypo,
					confInt,
					0.95);
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doOneMedian(
					dataFileName,
					outputFolder.replace(File.separator, "/"),
					respvars,
					testValue,
					altHypo,
					confInt,
					(float) 0.95);
			this.getShell().setMinimized(true);
			StarAnalysisUtilities.openAndRefreshFolder(outFileName);
			btnOkay.setEnabled(true);
			rInfo.close();
		}
		
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.RETRY_ID, "Reset", false);
		btnOkay = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(525, 433);
	}

}
