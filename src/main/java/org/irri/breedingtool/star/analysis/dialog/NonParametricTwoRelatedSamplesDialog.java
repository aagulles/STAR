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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class NonParametricTwoRelatedSamplesDialog extends Dialog {
	private String filePath = PartStackHandler.getActiveElementID();
	private List lstNumericVariables;
	private DialogFormUtility listManager = new DialogFormUtility();
	private Button btnOkay;
	private Label lblDataSet;
	private Button btnDataSet2;
	private Button btnDataSet1;
	private List lstDataSet1;
	private List lstDataSet2;
	private Label label_2;
	private Combo cmbAlternativeHypothesis;
	private TabItem tbtmVariableDescription;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NonParametricTwoRelatedSamplesDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Two Related Samples : " + new File(filePath).getName());
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
		
		tbtmVariableDescription = new TabItem(tabFolder, SWT.NONE);
		tbtmVariableDescription.setText("Variable Description");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmVariableDescription.setControl(composite);
		composite.setLayout(new GridLayout(3, false));
		
		Label lblNumericVariables = new Label(composite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblNumericVariables.setText("Numeric Variable(s)");
		new Label(composite, SWT.NONE);
		
		Label lblTestVariables = new Label(composite, SWT.NONE);
		lblTestVariables.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblTestVariables.setText("Data Set 1");
		
		lstNumericVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_lstNumericVariables.heightHint = 130;
		gd_lstNumericVariables.widthHint = 80;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);
		
		btnDataSet1 = new Button(composite, SWT.NONE);
		GridData gd_btnDataSet1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnDataSet1.widthHint = 90;
		btnDataSet1.setLayoutData(gd_btnDataSet1);
		btnDataSet1.setText("Add");
				
				lstDataSet1 = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
				GridData gd_lstDataSet1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
				gd_lstDataSet1.heightHint = 60;
				gd_lstDataSet1.widthHint = 80;
				lstDataSet1.setLayoutData(gd_lstDataSet1);
		
				new Label(composite, SWT.NONE);
				
				lblDataSet = new Label(composite, SWT.NONE);
				lblDataSet.setText("Data Set 2");
				
				btnDataSet2 = new Button(composite, SWT.NONE);
				btnDataSet2.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
					}
				});
				GridData gd_btnDataSet2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				gd_btnDataSet2.widthHint = 90;
				btnDataSet2.setLayoutData(gd_btnDataSet2);
				btnDataSet2.setText("Add");
				
				lstDataSet2 = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
				GridData gd_lstDataSet2 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
				gd_lstDataSet2.heightHint = 60;
				gd_lstDataSet2.widthHint = 80;
				lstDataSet2.setLayoutData(gd_lstDataSet2);
				
				label_2 = new Label(composite, SWT.NONE);
				label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				label_2.setText("Alternative Hypothesis:");
				
				cmbAlternativeHypothesis = new Combo(composite, SWT.READ_ONLY);
				cmbAlternativeHypothesis.setItems(new String[] {"less", "greater", "two.sided"});
				cmbAlternativeHypothesis.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
				cmbAlternativeHypothesis.setText("two.sided");
		
		initializeContent();
		
		
		return container;
	}
	
	public void initializeContent(){
		listManager.initializeSingleButtonList(lstNumericVariables, lstDataSet1, btnDataSet1);
		listManager.initializeSingleButtonList(lstNumericVariables, lstDataSet2, btnDataSet2);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		
	}
	void resetDialog(){
		lstNumericVariables.removeAll();
		lstDataSet1.removeAll();
		lstDataSet2.removeAll();
		listManager.initializeNumericList(lstNumericVariables, filePath);
		cmbAlternativeHypothesis.select(2);
		btnDataSet1.setText("Add");
		btnDataSet2.setText("Add");
		btnDataSet1.setEnabled(false);
		btnDataSet2.setEnabled(false);
		
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
	protected void okPressed(){

		if(lstDataSet1.getItemCount() != lstDataSet2.getItemCount()){
			MessageDialog.openError(this.getShell(), "Error", "Data Set 1 and 2 should have equal variables");
			return;
		}
		if (lstDataSet1.getItemCount() > 0 && lstDataSet2.getItemCount() > 0) {
			btnOkay.setEnabled(false);
			String altHypo = cmbAlternativeHypothesis.getText();
			String outputFolder = StarAnalysisUtilities
					.createOutputFolder(filePath,"NonParamtericTwoRelatedSamples");
			OperationProgressDialog rInfo = new OperationProgressDialog(
					getShell(), "Performing Analysis");
			rInfo.open();
			String dataFileName = filePath.replace(File.separator, "/");
			//supply path and name of text file where text output is going to be saved
			String outFileName = outputFolder + "//Output.txt";
			StarAnalysisUtilities.testVarArgs(
					dataFileName,
					outputFolder.replace(File.separator, "/"),
					lstDataSet1.getItems(),
					lstDataSet2.getItems(),
					altHypo,
					false,
					95);
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doPairedMedian(
					dataFileName,
					outputFolder.replace(File.separator, "/"),
					lstDataSet1.getItems(),
					lstDataSet2.getItems(),
					altHypo,
					false,
					95);
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
		return new Point(485, 436);
	}

}
