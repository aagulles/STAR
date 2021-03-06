package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class DescriptiveStatisticsHeteroskedasticityDialog extends Dialog {
	private static final int RESET = IDialogConstants.CLIENT_ID + 1111;




	private DialogFormUtility  listManager = new DialogFormUtility();
	private String filePath = PartStackHandler.getActiveElementID(); 
	private Button btnOk;
	private List lstVariables;
	private List lstByVariables;
	private ArrayList<Composite>  groups = new ArrayList<Composite>();
	private Button btnBartlettsTest;
	private Button btnBoxplot;
	private Button btnHistogram;
	private Button btnLevenesTest;
	private List lstNumericVariables;
	private List lstFactorialVariables;
	private Button btnAddSingleVariable;
	private Button btnAddFactorVariable;
	private Button btnSettofactor;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DescriptiveStatisticsHeteroskedasticityDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Homogeniety of Variances: " + new File(filePath).getName());
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginHeight = 8;
		gl_container.marginWidth = 8;
		container.setLayout(gl_container);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TabItem tbtmVariableDescription = new TabItem(tabFolder, SWT.NONE);
		tbtmVariableDescription.setText("Variable Description");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmVariableDescription.setControl(composite);
		composite.setLayout(new GridLayout(3, false));

		Label lblAvailableData = new Label(composite, SWT.NONE);
		lblAvailableData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblAvailableData.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblAvailableData.setText("Numeric Variable(s):");
		new Label(composite, SWT.NONE);

		Label lblVariables = new Label(composite, SWT.NONE);
		lblVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblVariables.setText("Variable(s):");
		lblVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		lstNumericVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_lstNumericVariables.heightHint = 121;
		gd_lstNumericVariables.widthHint = 148;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		GridData gd_composite_1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.widthHint = 99;
		composite_1.setLayoutData(gd_composite_1);

		btnAddSingleVariable = new Button(composite_1, SWT.NONE);

		btnAddSingleVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		GridData gd_btnAddSingleVariable = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddSingleVariable.widthHint = 90;
		btnAddSingleVariable.setLayoutData(gd_btnAddSingleVariable);

		btnAddSingleVariable.setText("Add");

		lstVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstVariables = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_lstVariables.widthHint = 148;
		gd_lstVariables.heightHint = 121;
		lstVariables.setLayoutData(gd_lstVariables);
		
		btnSettofactor = new Button(composite, SWT.NONE);
		GridData gd_btnSettofactor = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnSettofactor.widthHint = 110;
		btnSettofactor.setLayoutData(gd_btnSettofactor);
		btnSettofactor.setText("Set to Factor");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Label lblFactors = new Label(composite, SWT.NONE);
		GridData gd_lblFactors = new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1);
		gd_lblFactors.heightHint = 15;
		lblFactors.setLayoutData(gd_lblFactors);
		lblFactors.setText("Factor(s):");
		lblFactors.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite, SWT.NONE);

		Label lblByVariables = new Label(composite, SWT.NONE);
		lblByVariables.setText("By Variable(s):");
		lblByVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		lstFactorialVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstFactorialVariables = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_lstFactorialVariables.widthHint = 148;
		gd_lstFactorialVariables.heightHint = 121;
		lstFactorialVariables.setLayoutData(gd_lstFactorialVariables);

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(1, false));

		btnAddFactorVariable = new Button(composite_2, SWT.NONE);

		GridData gd_btnAddFactorVariable = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1);
		gd_btnAddFactorVariable.widthHint = 90;
		btnAddFactorVariable.setLayoutData(gd_btnAddFactorVariable);
		btnAddFactorVariable.setText("Add");
		btnAddFactorVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		lstByVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstByVariables = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_lstByVariables.widthHint = 148;
		gd_lstByVariables.heightHint = 121;
		lstByVariables.setLayoutData(gd_lstByVariables);







		listVariables(lstNumericVariables, lstFactorialVariables);
		
		TabItem tbtmOptions = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions.setText("Options");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions.setControl(composite_3);
		composite_3.setLayout(new GridLayout(1, false));
		
		Group grpTestProcedures = new Group(composite_3, SWT.NONE);
		grpTestProcedures.setLayout(new GridLayout(1, false));
		GridData gd_grpTestProcedures = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_grpTestProcedures.heightHint = 47;
		gd_grpTestProcedures.widthHint = 245;
		grpTestProcedures.setLayoutData(gd_grpTestProcedures);
		grpTestProcedures.setText("Test Procedures");
		
		btnBartlettsTest = new Button(grpTestProcedures, SWT.CHECK);
		btnBartlettsTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnBartlettsTest.setSelection(true);
		btnBartlettsTest.setText("Bartlett's Test");
		btnBartlettsTest.setData("bartlett");
		
		btnLevenesTest = new Button(grpTestProcedures, SWT.CHECK);
		btnLevenesTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnLevenesTest.setText("Levene's Test");
		btnLevenesTest.setData("levene");
		
		Group grpGraph = new Group(composite_3, SWT.NONE);
		grpGraph.setText("Graph");
		grpGraph.setLayout(new GridLayout(1, false));
		GridData gd_grpGraph = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_grpGraph.heightHint = 46;
		grpGraph.setLayoutData(gd_grpGraph);
		
		btnBoxplot = new Button(grpGraph, SWT.CHECK);
		btnBoxplot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnBoxplot.setText("Boxplot");
		
		btnHistogram = new Button(grpGraph, SWT.CHECK);
		btnHistogram.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnHistogram.setText("Histogram");
		

		groups.add(grpTestProcedures);
		initializeContent();
		return container;
	}

	void initializeContent(){
		listManager.initializeSingleButtonList(lstNumericVariables, lstVariables,btnSettofactor, btnAddSingleVariable);
		listManager.initializeSingleButtonList(lstFactorialVariables, lstByVariables,btnSettofactor, btnAddFactorVariable);
		listManager.initializeVariableMoveList(lstNumericVariables, lstFactorialVariables, btnSettofactor, filePath);
//		listManager.initializeFactorList(lstFactorialVariables, filePath);
//		listManager.initializeNumericList(lstNumericVariables, filePath);
	}

	private void listVariables(List lstNumericVariables, List lstFactorialVariables){
		DataManipulationManager dataManipulationManager = new DataManipulationManager();

		ArrayList<String> varList = dataManipulationManager.getVarInfoFromFile(filePath);

		for(int i = 0; i < varList.size(); i++){
			if(varList.get(i).toString().split(":")[1].equals("Numeric"))
				lstNumericVariables.add(varList.get(i).toString().split(":")[0]);
			else
				lstFactorialVariables.add(varList.get(i).toString().split(":")[0]);

		}

	}
	@Override
	protected void buttonPressed(int buttonId){

		if (IDialogConstants.OK_ID == buttonId) {
			okPressed();
		}
		else if(buttonId == RESET){
		//	btnDefaults.notifyListeners(SWT.Selection, null);
			lstFactorialVariables.removeAll();
			lstNumericVariables.removeAll();
			lstVariables.removeAll();
			lstByVariables.removeAll();
			
			listManager.initializeFactorList(lstFactorialVariables, filePath);
			listManager.initializeNumericList(lstNumericVariables, filePath);
			btnBartlettsTest.setSelection(true);
			btnLevenesTest.setSelection(false);
			btnBoxplot.setSelection(false);
			btnHistogram.setSelection(false);
			btnAddSingleVariable.setText("Add");
			btnAddSingleVariable.setEnabled(false);
			btnAddFactorVariable.setText("Add");
			btnAddFactorVariable.setEnabled(false);
			btnSettofactor.setText("Set to Factor");
			btnSettofactor.setEnabled(false);
		}
		
		else if (IDialogConstants.CANCEL_ID == buttonId) {
			close();
		}
	}
	@Override
	protected void okPressed(){
		if (validateForm(lstVariables, lstByVariables, groups.toArray(new Composite[groups.size()]), btnOk)) {
			btnOk.setEnabled(false);
			String outputFolder = StarAnalysisUtilities.createOutputFolder(filePath,"Heteroskedasticity");
			OperationProgressDialog rInfo = new OperationProgressDialog(
					getShell(), "Performing Analysis");
			rInfo.open();
			String outFileName = outputFolder.replace(File.separator,"/") + "/Output.txt"; 
			//supply path and name of boxplot file
			String graphBoxFileName = outputFolder.replace(File.separator,"/") + "/Boxplot_"; 
			//supply path and name of histogram file
			String graphHistFileName = outputFolder.replace(File.separator,"/") + "/Histogram_"; 
			
			//specify parameters
			String[] method = listManager.getCheckBoxesValue(groups.toArray(new Composite[groups.size()]));
			
			String outputBoxplot = (btnBoxplot.getSelection()) ? "TRUE" : "FALSE";
			
			String outputHist = (btnHistogram.getSelection()) ? "TRUE" : "FALSE";
			
			StarAnalysisUtilities.testVarArgs(filePath.replace(File.separator,"/"), outFileName, lstVariables.getItems(), lstByVariables.getItems(), method, outputBoxplot, graphBoxFileName, outputHist, graphHistFileName);
			
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doHeteroskedasticity(filePath.replace(File.separator,"/"), outFileName, lstVariables.getItems(), lstByVariables.getItems(), method, outputBoxplot, graphBoxFileName, outputHist, graphHistFileName);
			this.getShell().setMinimized(true);
			StarAnalysisUtilities.openAndRefreshFolder(outputFolder);
			rInfo.close();
			btnOk.setEnabled(true);
		}

	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button btnReset = createButton(parent, RESET, "Reset", false);
		btnOk = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}



	/**
	 * Return the initial size of the dialog.
	 */

	private boolean validateForm(List lstVariables, List lstByVariables, Composite[] groupComposites, Button btnOkay){
		if(lstVariables.getItemCount() >= 1 && lstByVariables.getItemCount() >= 1  && listManager.isCheckBoxesHasBoolean(groupComposites))
		{
			return true;
		}
		else {
			MessageDialog.openError(this.getShell(), "Error", "At least one required field is empty.  Variable(s) and By Variable(s) list box of the Variable Description tab should both contain at least one item and at least one item of the Test Procedure in the Options tab should be chosen.");
			return false;
		}

	}



	@Override
	protected Point getInitialSize() {
		return new Point(482, 501);
	}
}
