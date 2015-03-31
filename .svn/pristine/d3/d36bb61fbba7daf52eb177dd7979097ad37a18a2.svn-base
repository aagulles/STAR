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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class NonParametricSeveralRelatedSamplesDialog extends Dialog {
	private String filePath = PartStackHandler.getActiveElementID();
	private List lstNumericVariables;
	private Button btnToFactor;
	private DialogFormUtility listManager = new DialogFormUtility();
	private List lstTestVariables;
	private Button btnOkay;
	private Label lblFactors;
	private List lstFactors;
	private Text txtGroupingVariable;
	private Button btnAddRemove2;
	private Label lblNewLabel;
	private Button btnAddRemove1;
	private Button btnBlock;
	private Text txtBlock;
	private Label lblBlock;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NonParametricSeveralRelatedSamplesDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Test Several Related Samples : " + new File(filePath).getName());
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
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstNumericVariables.heightHint = 80;
		gd_lstNumericVariables.widthHint = 80;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);
		
		btnAddRemove1 = new Button(composite, SWT.NONE);
		GridData gd_btnAddRemove1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnAddRemove1.heightHint = 26;
		gd_btnAddRemove1.widthHint = 90;
		btnAddRemove1.setLayoutData(gd_btnAddRemove1);
		btnAddRemove1.setText("Add");
		
		lstTestVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstTestVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstTestVariables.widthHint = 80;
		gd_lstTestVariables.heightHint = 80;
		lstTestVariables.setLayoutData(gd_lstTestVariables);
		
		btnToFactor = new Button(composite, SWT.NONE);
		GridData gd_btnToFactor = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnToFactor.heightHint = 26;
		gd_btnToFactor.widthHint = 110;
		btnToFactor.setLayoutData(gd_btnToFactor);
		btnToFactor.setText("Set to Factor");
		new Label(composite, SWT.NONE);

		new Label(composite, SWT.NONE);
		
		lblFactors = new Label(composite, SWT.NONE);
		lblFactors.setText("Factor(s)");
		new Label(composite, SWT.NONE);
		
		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("Grouping Variable");
		
		lstFactors = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_lstFactors = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_lstFactors.widthHint = 80;
		gd_lstFactors.heightHint = 50;
		lstFactors.setLayoutData(gd_lstFactors);
		
		btnAddRemove2 = new Button(composite, SWT.NONE);
		GridData gd_btnAddRemove2 = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
		gd_btnAddRemove2.heightHint = 26;
		gd_btnAddRemove2.widthHint = 90;
		btnAddRemove2.setLayoutData(gd_btnAddRemove2);
		btnAddRemove2.setText("Add");
		
		txtGroupingVariable = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		txtGroupingVariable.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		GridData gd_txtGroupingVariable = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_txtGroupingVariable.heightHint = 15;
		txtGroupingVariable.setLayoutData(gd_txtGroupingVariable);
		new Label(composite, SWT.NONE);
		
		lblBlock = new Label(composite, SWT.NONE);
		lblBlock.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		lblBlock.setText("Block");
		
		btnBlock = new Button(composite, SWT.NONE);
		GridData gd_btnBlock = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
		gd_btnBlock.heightHint = 26;
		gd_btnBlock.widthHint = 90;
		btnBlock.setLayoutData(gd_btnBlock);
		btnBlock.setText("Add");
		
		txtBlock = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		txtBlock.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		GridData gd_txtBlock = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_txtBlock.heightHint = 15;
		txtBlock.setLayoutData(gd_txtBlock);
		
		initializeContent();
		return container;
	}
	
	void initializeContent(){
		listManager.initializeSingleButtonList(lstNumericVariables, lstTestVariables,btnToFactor, btnAddRemove1);
		listManager.initializeSingleSelectionList(lstFactors, txtGroupingVariable,btnToFactor, btnAddRemove2);
		listManager.initializeSingleSelectionList(lstFactors, txtBlock, btnBlock);
		listManager.initializeVariableMoveList(lstNumericVariables, lstFactors, btnToFactor, filePath);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeFactorList(lstFactors, filePath);
		
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
	void resetDialog(){
		lstNumericVariables.removeAll();
		lstTestVariables.removeAll();
		lstFactors.removeAll();
		txtGroupingVariable.setText("");
		txtBlock.setText("");
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeFactorList(lstFactors, filePath);
		btnAddRemove1.setText("Add");
		btnAddRemove2.setText("Add");
		btnBlock.setText("Add");
		btnAddRemove1.setEnabled(false);
		btnAddRemove2.setEnabled(false);
		btnBlock.setEnabled(false);
		btnToFactor.setText("Set to Factor");
		btnToFactor.setEnabled(false);
	}
	protected void okPressed(){

		if (lstTestVariables.getItemCount() >= 1) {
			btnOkay.setEnabled(false);
			String[] respvars = lstTestVariables.getItems();
				String grp = txtGroupingVariable.getText();
			String outputFolder = StarAnalysisUtilities
					.createOutputFolder(filePath,"NonParametricTwoIndependentSample");
			OperationProgressDialog rInfo = new OperationProgressDialog(
					getShell(), "Performing Analysis");
			rInfo.open();
			String dataFileName = filePath.replace(File.separator, "/");
			//supply path and name of text file where text output is going to be saved
			String outFileName = outputFolder + File.separator + "Output.txt";

			StarAnalysisUtilities.testVarArgs(dataFileName, outputFolder.replace(File.separator, "/"),txtGroupingVariable.getText(), txtBlock.getText());
		ProjectExplorerView.rJavaManager.getSTARAnalysisManager()
					.doFriedman(dataFileName, outputFolder.replace(File.separator, "/"), respvars, txtGroupingVariable.getText(), txtBlock.getText());
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
		return new Point(501, 429);
	}

}
