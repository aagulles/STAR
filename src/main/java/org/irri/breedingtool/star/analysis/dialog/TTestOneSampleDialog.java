package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
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

public class TTestOneSampleDialog extends Dialog {
	private String filePath = PartStackHandler.getActiveElementID();
	private List lstNumericVariables;
	private Button btnAdd;
	private Spinner txtTestValue;
	private Button btnSummaryStatistics;
	private Combo cmbAlternativeHypothesis;
	private Spinner txtConfidenceInterval;
	private Button btnAndersondarling;
	private Button btnCramervonMises;
	private Button btnLilliefors;
	private Button btnShapirofrancia;
	private Button btnShapirowilk;
	private DialogFormUtility listManager = new DialogFormUtility();
	private Button btnConfidenceInterval;
	private List lstTestVariables;
	private Group grpTestForNormality;
	private Button btnOkay;
	private Group grpDisplay;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public TTestOneSampleDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("One Sample t-Test: " + new File(filePath).getName());
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
		composite.setLayout(new GridLayout(4, false));
		
		Label lblNumericVariables = new Label(composite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblNumericVariables.setText("Numeric Variable(s):");
		new Label(composite, SWT.NONE);
		
		Label lblTestVariables = new Label(composite, SWT.NONE);
		lblTestVariables.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		lblTestVariables.setText("Test Variable(s):");
		
		lstNumericVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		gd_lstNumericVariables.heightHint = 204;
		gd_lstNumericVariables.widthHint = 143;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);
		
		btnAdd = new Button(composite, SWT.NONE);
		GridData gd_btnAdd = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		gd_btnAdd.widthHint = 90;
		btnAdd.setLayoutData(gd_btnAdd);
		btnAdd.setText("Add");
	
		
		lstTestVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstTestVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_lstTestVariables.widthHint = 143;
		gd_lstTestVariables.heightHint = 204;
		lstTestVariables.setLayoutData(gd_lstTestVariables);
		new Label(composite, SWT.NONE);
		
		Label lblTestValue = new Label(composite, SWT.NONE);
		lblTestValue.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblTestValue.setText("Test Value:");
		
		txtTestValue = new Spinner(composite, SWT.BORDER);
		txtTestValue.setMaximum(100000);
		
		TabItem tbtmOption = new TabItem(tabFolder, SWT.NONE);
		tbtmOption.setText("Options");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOption.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));
		
		Label lblAlternativeHypothesis = new Label(composite_1, SWT.NONE);
		lblAlternativeHypothesis.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAlternativeHypothesis.setText("Alternative Hypothesis:");
		
		cmbAlternativeHypothesis = new Combo(composite_1, SWT.READ_ONLY);
		cmbAlternativeHypothesis.setItems(new String[] {"less", "greater", "two.sided"});
		GridData gd_cmbAlternativeHypothesis = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_cmbAlternativeHypothesis.widthHint = 5;
		cmbAlternativeHypothesis.setLayoutData(gd_cmbAlternativeHypothesis);
		cmbAlternativeHypothesis.setText("two.sided");
		
		grpDisplay = new Group(composite_1, SWT.NONE);
		grpDisplay.setText("Display");
		grpDisplay.setLayout(new GridLayout(3, false));
		grpDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		btnSummaryStatistics = new Button(grpDisplay, SWT.CHECK);
		btnSummaryStatistics.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnSummaryStatistics.setText("Summary Statistics");
		new Label(grpDisplay, SWT.NONE);
		
		
		btnConfidenceInterval = new Button(grpDisplay, SWT.CHECK);
		btnConfidenceInterval.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtConfidenceInterval.setEnabled(btnConfidenceInterval.getSelection());
				
			}
		});
		btnConfidenceInterval.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		btnConfidenceInterval.setText("Confidence Interval");
		
		txtConfidenceInterval = new Spinner(grpDisplay, SWT.BORDER);
		txtConfidenceInterval.setIncrement(100);
		txtConfidenceInterval.setDigits(2);
		txtConfidenceInterval.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		txtConfidenceInterval.setMaximum(9900);
		txtConfidenceInterval.setMinimum(9000);
		txtConfidenceInterval.setSelection(9500);
		txtConfidenceInterval.setEnabled(false);
		
		Label label = new Label(grpDisplay, SWT.NONE);
		GridData gd_label = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_label.widthHint = 127;
		label.setLayoutData(gd_label);
		label.setText("%");
		
		grpTestForNormality = new Group(composite_1, SWT.NONE);
		grpTestForNormality.setText("Test for Normality");
		grpTestForNormality.setLayout(new GridLayout(1, false));
		grpTestForNormality.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		btnShapirowilk = new Button(grpTestForNormality, SWT.CHECK);
		btnShapirowilk.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnShapirowilk.setText("Shapiro-Wilk");
		btnShapirowilk.setData("swilk");
		
		btnShapirofrancia = new Button(grpTestForNormality, SWT.CHECK);
		btnShapirofrancia.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnShapirofrancia.setText("Shapiro-Francia");
		btnShapirofrancia.setData("sfrancia");
		
		btnLilliefors = new Button(grpTestForNormality, SWT.CHECK);
		btnLilliefors.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnLilliefors.setText("Lilliefors (Kolmogorov-Smirnov)");
		btnLilliefors.setData("ks");
		
		btnCramervonMises = new Button(grpTestForNormality, SWT.CHECK);
		btnCramervonMises.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnCramervonMises.setText("Cramer-Von Mises");
		btnCramervonMises.setData("cramer");
		
		btnAndersondarling = new Button(grpTestForNormality, SWT.CHECK);
		btnAndersondarling.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnAndersondarling.setText("Anderson-Darling");
		btnAndersondarling.setData("anderson");
		
		new Label(composite_1, SWT.NONE);
		
		Label label_1 = new Label(composite_1, SWT.NONE);
		listManager.initializeSingleButtonList(lstNumericVariables, lstTestVariables, btnAdd);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		
		
		return container;
	}
	
	
	protected void okPressed(){

		if (lstTestVariables.getItemCount() >= 1) {
			btnOkay.setEnabled(false);
			String[] respvars = lstTestVariables.getItems();
			Integer testValue = txtTestValue.getSelection();
			String altHypo = cmbAlternativeHypothesis.getText();
			boolean statistics = btnSummaryStatistics.getSelection();
			boolean confInt = btnConfidenceInterval.getSelection();
			float confLevel = (float) txtConfidenceInterval.getSelection() / 100;
			Composite[] cmpGroup = { grpTestForNormality };
			String[] method = listManager.getCheckBoxesValue(cmpGroup);
			String outputFolder = StarAnalysisUtilities
					.createOutputFolder(filePath,"TTestOneSample");
			OperationProgressDialog rInfo = new OperationProgressDialog(
					getShell(), "Performing Analysis");
			rInfo.open();
			String dataFileName = filePath.replace(File.separator, "/");
			//supply path and name of text file where text output is going to be saved
			String outFileName = outputFolder + "//Output.txt";
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager()
					.doOneMean(dataFileName, outFileName.replace(File.separator, "/"),
							respvars, testValue, altHypo, statistics, confInt,
							confLevel, method);
			this.getShell().setMinimized(true);
			StarAnalysisUtilities.openAndRefreshFolder(outFileName);
			rInfo.close();
			btnOkay.setEnabled(true);
			//createContents(this.getParentShell());
		}
		else{
			MessageDialog.openError(getShell(), "Error", "Test Variable(s) listbox of the Variable Description Tab should contain atleast one item.");
		}
		
	}
	@Override
	protected void buttonPressed(int buttonId){
		if (IDialogConstants.OK_ID == buttonId) {
			okPressed();
		}
		else if(buttonId == IDialogConstants.RETRY_ID){
			lstNumericVariables.removeAll();
			lstTestVariables.removeAll();
			listManager.initializeNumericList(lstNumericVariables, filePath);
			listManager.setCheckBoxesToBoolean(false, grpDisplay,grpTestForNormality);
			cmbAlternativeHypothesis.select(2);
			txtConfidenceInterval.setSelection(9500);
			txtTestValue.setSelection(0);
			txtConfidenceInterval.setEnabled(false);
			btnAdd.setText("Add");
			btnAdd.setEnabled(false);
			
		}
		
		else if (IDialogConstants.CANCEL_ID == buttonId) {
			close();
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
		return new Point(417, 398);
	}

}
