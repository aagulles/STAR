package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

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
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.irri.breedingtool.utility.StarRandomizationUtilities;

public class DescriptiveStatisticsNormalityTestDialog extends Dialog {
	private static final int RESET = IDialogConstants.CLIENT_ID + 1111;




	private DialogFormUtility  listManager = new DialogFormUtility();
	private String filePath = PartStackHandler.getActiveElementID();
	private Button btnOk;
	private List lstVariables;
	private List lstByVariables;
	private ArrayList<Composite>  groups = new ArrayList<Composite>();
	private List lstNumericVariables;
	private List lstFactorialVariables;
	private Button btnBoxplot;
	private Button btnHistogram;
	private Button btnAddSingleVariable;
	private Button btnAddFactorVariable;
	private Button btnToFactor;
	private Button btnShapirowilk;
	private Button btnShapirofrancia;
	private Button btnLilliefors;
	private Button btnCramervonMises;
	private Button btnAndersondarling;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DescriptiveStatisticsNormalityTestDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Test for Normality : " + new File(filePath).getName());
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
		lblAvailableData.setText("Numeric Variable(s)");
		new Label(composite, SWT.NONE);

		Label lblVariables = new Label(composite, SWT.NONE);
		lblVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblVariables.setText("Variable(s)");
		lblVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		lstNumericVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstNumericVariables.heightHint = 80;
		gd_lstNumericVariables.widthHint = 80;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		GridData gd_composite_1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.widthHint = 100;
		composite_1.setLayoutData(gd_composite_1);

		btnAddSingleVariable = new Button(composite_1, SWT.NONE);

		btnAddSingleVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		GridData gd_btnAddSingleVariable = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1);
		gd_btnAddSingleVariable.widthHint = 90;
		btnAddSingleVariable.setLayoutData(gd_btnAddSingleVariable);

		btnAddSingleVariable.setText("Add");

		lstVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstVariables.widthHint = 80;
		gd_lstVariables.heightHint = 80;
		lstVariables.setLayoutData(gd_lstVariables);
		
		btnToFactor = new Button(composite, SWT.NONE);
		GridData gd_btnToFactor = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnToFactor.widthHint = 110;
		gd_btnToFactor.heightHint = 25;
		btnToFactor.setLayoutData(gd_btnToFactor);
		btnToFactor.setText("Set to Factor");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Label lblFactors = new Label(composite, SWT.NONE);
		GridData gd_lblFactors = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblFactors.heightHint = 15;
		lblFactors.setLayoutData(gd_lblFactors);
		lblFactors.setText("Factor(s)");
		lblFactors.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite, SWT.NONE);

		Label lblByVariables = new Label(composite, SWT.NONE);
		GridData gd_lblByVariables = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblByVariables.heightHint = 15;
		lblByVariables.setLayoutData(gd_lblByVariables);
		lblByVariables.setText("By Variable(s)");
		lblByVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		lstFactorialVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstFactorialVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstFactorialVariables.widthHint = 80;
		gd_lstFactorialVariables.heightHint = 60;
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
		GridData gd_lstByVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstByVariables.widthHint = 80;
		gd_lstByVariables.heightHint = 60;
		lstByVariables.setLayoutData(gd_lstByVariables);

		TabItem tbtmStatistics_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmStatistics_1.setText("Options");

		Composite cmpStatistics = new Composite(tabFolder, SWT.NONE);
		tbtmStatistics_1.setControl(cmpStatistics);
		cmpStatistics.setLayout(new GridLayout(2, false));


		final Group grpTestProcedures = new Group(cmpStatistics, SWT.NONE);
		grpTestProcedures.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 2));
		grpTestProcedures.setLayout(new GridLayout(1, false));
		grpTestProcedures.setText("Test Procedure");
		
		btnShapirowilk = new Button(grpTestProcedures, SWT.CHECK);
		btnShapirowilk.setSelection(true);
		btnShapirowilk.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		btnShapirowilk.setText("Shapiro-Wilk");
		btnShapirowilk.setData("swilk");
		
		btnShapirofrancia = new Button(grpTestProcedures, SWT.CHECK);
		btnShapirofrancia.setText("Shapiro-Francia");
		btnShapirofrancia.setData("sfrancia");
		
		btnLilliefors = new Button(grpTestProcedures, SWT.CHECK);
		btnLilliefors.setText("Lilliefors (Kolmogorov-Smirnov)");
		btnLilliefors.setData("ks");
		
		btnCramervonMises = new Button(grpTestProcedures, SWT.CHECK);
		btnCramervonMises.setText("Cramer-Von Mises");
		btnCramervonMises.setData("cramer");
		
		btnAndersondarling = new Button(grpTestProcedures, SWT.CHECK);
		btnAndersondarling.setText("Anderson-Darling");
		btnAndersondarling.setData("anderson");
		//




		Group grpGraph = new Group(cmpStatistics, SWT.NONE);
		GridData gd_grpGraph = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grpGraph.widthHint = 174;
		grpGraph.setLayoutData(gd_grpGraph);
		grpGraph.setLayout(new GridLayout(1, false));
		grpGraph.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpGraph.setText("Graph");
		
		btnBoxplot = new Button(grpGraph, SWT.CHECK);
		btnBoxplot.setText("Boxplot");
		
		btnHistogram = new Button(grpGraph, SWT.CHECK);
		btnHistogram.setText("Histogram");
		new Label(cmpStatistics, SWT.NONE);





		groups.clear();
		groups.add(grpTestProcedures);
		


		initializeContent();

		return container;
	}

	private void initializeContent(){
		
		listManager.initializeSingleButtonList(lstNumericVariables, lstVariables,btnToFactor, btnAddSingleVariable);
		listManager.initializeSingleButtonList(lstFactorialVariables, lstByVariables,btnToFactor, btnAddFactorVariable);
		listManager.initializeVariableMoveList(lstNumericVariables, lstFactorialVariables, btnToFactor, filePath);
		listManager.initializeFactorList(lstFactorialVariables, filePath);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		
	}
	@Override
	protected void buttonPressed(int buttonId){

		if (IDialogConstants.OK_ID == buttonId) {
			okPressed();
		}
		else if(buttonId == RESET){
			
			lstNumericVariables.removeAll();
			lstFactorialVariables.removeAll();
			lstVariables.removeAll();
			lstByVariables.removeAll();
			btnAddSingleVariable.setText("Add");
			btnAddSingleVariable.setEnabled(false);
			btnAddFactorVariable.setText("Add");
			btnAddFactorVariable.setEnabled(false);
			btnToFactor.setText("Set to Factor");
			btnToFactor.setEnabled(false);
			btnAndersondarling.setSelection(false);
			btnBoxplot.setSelection(false);
			btnCramervonMises.setSelection(false);
			btnHistogram.setSelection(false);
			btnLilliefors.setSelection(false);
			btnShapirofrancia.setSelection(false);
			btnShapirowilk.setSelection(false);
			listManager.initializeFactorList(lstFactorialVariables, filePath);
			listManager.initializeNumericList(lstNumericVariables, filePath);
		}
		else if (IDialogConstants.CANCEL_ID == buttonId) {
			close();
		}
	}
	@Override
	protected void okPressed(){
		if (validateForm(lstVariables, lstByVariables, groups.toArray(new Composite[groups.size()]), btnOk)) {
			btnOk.setEnabled(false);
			
			ProjectExplorerManager projectMan = new ProjectExplorerManager();
			Calendar lCDateTime = Calendar.getInstance();
			listManager.getCheckBoxesValue(groups.toArray(new Composite[groups.size()]));
			
			
			
			File outputFolder = 	new File(StarAnalysisUtilities.createOutputFolder(filePath, "Descriptive_Normality"));
			
			OperationProgressDialog rInfo = new OperationProgressDialog(
					getShell(), "Performing Analysis");
			rInfo.open();
			if(!outputFolder.exists()) outputFolder.mkdir();
			String dataFileName = filePath.replace(File.separator, "/"); 
			//supply path and name of text file where text output is going to be saved
			String outFileName = outputFolder.getAbsolutePath().replace(File.separator, "/") +  "/Output.txt"; 
			//supply path and name of boxplot file
			String graphBoxFileName = outputFolder.getAbsolutePath().replace(File.separator, "/") +  "/Boxplot_"; 
			//supply path and name of histogram file
			String graphHistFileName = outputFolder.getAbsolutePath().replace(File.separator, "/") + "/Histogram_"; 
			
			//specify parameters
			String[] respvars = lstVariables.getItems();
			// if grp is null
			// String[] grp = null;
			// if grp is not null
			String[] grp = (lstByVariables.getItemCount() > 0) ? lstByVariables.getItems() : null;
			String[] method = listManager.getCheckBoxesValue(groups.toArray(new Composite[groups.size()]));
			String outputBoxplot = (btnBoxplot.getSelection()) ? "TRUE":"FALSE";
			String outputHist = (btnHistogram.getSelection()) ? "TRUE":"FALSE";
			
			
			StarRandomizationUtilities.testVarArgs(filePath.replace(File.separator, "/"), outFileName, respvars, grp, method, outputBoxplot, graphBoxFileName, outputHist, graphHistFileName);
			
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doNormality(filePath.replace(File.separator, "/"), outFileName, respvars, grp, method, outputBoxplot, graphBoxFileName, outputHist, graphHistFileName);
			this.getShell().setMinimized(true);

			projectMan.refreshFolder((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData());
			PartStackHandler.execute(projectMan.getTreeNodeModelbyFilePath(outputFolder.getAbsolutePath()));
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
		if(lstVariables.getItemCount() >= 1  && listManager.isCheckBoxesHasBoolean(groupComposites))
		{
			return true;
		}
		else {
			MessageDialog.openError(this.getShell(), "Error", "At least one required field is empty.  Variable(s) list box of the Variable Description tab should contain at least one item and at least one item of the Test Procedure in the Options tab should be chosen.");
			return false;
		}

	}



	@Override
	protected Point getInitialSize() {
		return new Point(510, 420);
	}
}
