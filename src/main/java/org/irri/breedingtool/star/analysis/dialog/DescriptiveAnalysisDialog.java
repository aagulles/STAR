package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

public class DescriptiveAnalysisDialog extends Dialog {
	private static final int RESET = IDialogConstants.CLIENT_ID + 1111;




	private DialogFormUtility  listManager = new DialogFormUtility();
	private String filePath = PartStackHandler.getActivePart().getElementId().toString(); 
	private Button btnOk;
	private List lstVariables;
	private List lstByVariables;
	private ArrayList<Composite>  groups = new ArrayList<Composite>();
	private Button btnDefaults;
	private Button btnAddFactorVariable;
	private Button btnAddSingleVariable;
	private Button btnFactornumeric;
	private List lstNumericVariables;
	private List lstFactorialVariables;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DescriptiveAnalysisDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Descriptive Statistics: " + new File(filePath).getName());
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.marginHeight = 10;
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginWidth = 10;
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
				GridData gd_lstNumericVariables_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
				gd_lstNumericVariables_1.heightHint = 150;
				gd_lstNumericVariables_1.widthHint = 200;
				lstNumericVariables.setLayoutData(gd_lstNumericVariables_1);
		
				btnAddSingleVariable = new Button(composite, SWT.NONE);
				GridData gd_btnAddSingleVariable = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				gd_btnAddSingleVariable.widthHint = 90;
				btnAddSingleVariable.setLayoutData(gd_btnAddSingleVariable);
				
						btnAddSingleVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						
								btnAddSingleVariable.setText("Add");
		
				lstVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
				GridData gd_lstVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
				gd_lstVariables.widthHint = 200;
				gd_lstVariables.heightHint = 150;
				lstVariables.setLayoutData(gd_lstVariables);

		btnFactornumeric = new Button(composite, SWT.NONE);
		GridData gd_btnFactornumeric = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
		gd_btnFactornumeric.widthHint = 110;
		gd_btnFactornumeric.minimumHeight = 25;
		gd_btnFactornumeric.minimumWidth = 30;
		gd_btnFactornumeric.heightHint = 24;
		btnFactornumeric.setLayoutData(gd_btnFactornumeric);
		btnFactornumeric.setText("Set to Factor");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
				Label lblFactors = new Label(composite, SWT.NONE);
				GridData gd_lblFactors = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
				gd_lblFactors.heightHint = 15;
				lblFactors.setLayoutData(gd_lblFactors);
				lblFactors.setText("Factor(s)");
				lblFactors.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite, SWT.NONE);
		
				Label lblByVariables = new Label(composite, SWT.NONE);
				GridData gd_lblByVariables = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblByVariables.heightHint = 15;
				lblByVariables.setLayoutData(gd_lblByVariables);
				lblByVariables.setText("By Variable(s)");
				lblByVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
				lstFactorialVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
				GridData gd_lstFactorialVariables_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
				gd_lstFactorialVariables_1.widthHint = 200;
				gd_lstFactorialVariables_1.heightHint = 100;
				lstFactorialVariables.setLayoutData(gd_lstFactorialVariables_1);

		btnAddFactorVariable = new Button(composite, SWT.NONE);
		GridData gd_btnAddFactorVariable = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddFactorVariable.widthHint = 90;
		btnAddFactorVariable.setLayoutData(gd_btnAddFactorVariable);
		btnAddFactorVariable.setText("Add");
		btnAddFactorVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		

		lstByVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstByVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstByVariables.widthHint = 200;
		gd_lstByVariables.heightHint = 100;
		lstByVariables.setLayoutData(gd_lstByVariables);










		TabItem tbtmStatistics = new TabItem(tabFolder, SWT.NONE);
		tbtmStatistics.setText("Statistics");

		Composite cmpStatistics = new Composite(tabFolder, SWT.NONE);
		tbtmStatistics.setControl(cmpStatistics);
		cmpStatistics.setLayout(new GridLayout(4, false));


		final Group grpOthers = new Group(cmpStatistics, SWT.NONE);
		grpOthers.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		grpOthers.setLayout(new GridLayout(1, false));
		grpOthers.setText("Others");

		//Start
		Button btnNoOfObs = new Button(grpOthers, SWT.CHECK);
		btnNoOfObs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnNoOfObs.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnNoOfObs.setText("No. of Obs.");
		btnNoOfObs.setData("n");

		final Button btnNoOfNonmissing = new Button(grpOthers, SWT.CHECK);
		btnNoOfNonmissing.setSelection(true);
		btnNoOfNonmissing.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnNoOfNonmissing.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnNoOfNonmissing.setText("No. of Non-missing Obs");
		btnNoOfNonmissing.setData("nnmiss");

		Button btnNoOfMissing = new Button(grpOthers, SWT.CHECK);
		btnNoOfMissing.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnNoOfMissing.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnNoOfMissing.setText("No. of Missing Obs.");
		btnNoOfMissing.setData("nmiss");

		Button btnSumOfValues = new Button(grpOthers, SWT.CHECK);
		btnSumOfValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnSumOfValues.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnSumOfValues.setText("Sum of Values");
		btnSumOfValues.setData("sum");

		Button btnCorrectedSumOf = new Button(grpOthers, SWT.CHECK);
		btnCorrectedSumOf.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnCorrectedSumOf.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnCorrectedSumOf.setText("Corrected Sum of Squares");
		btnCorrectedSumOf.setData("css");

		Button btnUncorrectedSumOf = new Button(grpOthers, SWT.CHECK);
		btnUncorrectedSumOf.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnUncorrectedSumOf.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnUncorrectedSumOf.setText("Uncorrected Sum of Squares");
		btnUncorrectedSumOf.setData("ucss");



		Button btnStandardErrorOfSkewness = new Button(grpOthers, SWT.CHECK);
		btnStandardErrorOfSkewness.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnStandardErrorOfSkewness.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnStandardErrorOfSkewness.setText("Standard of Error of Skewness");
		btnStandardErrorOfSkewness.setData("se.skew");

		Button btnStandardErrorOfKurtosis = new Button(grpOthers, SWT.CHECK);
		btnStandardErrorOfKurtosis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnStandardErrorOfKurtosis.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnStandardErrorOfKurtosis.setText("Standard Error of Kurtosis");
		btnStandardErrorOfKurtosis.setData("se.kurtosis");
		new Label(cmpStatistics, SWT.NONE);
		//




		Group grpCentralTendency = new Group(cmpStatistics, SWT.NONE);
		grpCentralTendency.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpCentralTendency.setLayout(new GridLayout(1, false));
		grpCentralTendency.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpCentralTendency.setText("Central Tendency");

		final Button btnMean = new Button(grpCentralTendency, SWT.CHECK);
		btnMean.setSelection(true);
		btnMean.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnMean.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnMean.setText("Mean");
		btnMean.setData("mean");

		Button btnMedian = new Button(grpCentralTendency, SWT.CHECK);
		btnMedian.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnMedian.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnMedian.setText("Median");
		btnMedian.setData("median");
		new Label(cmpStatistics, SWT.NONE);








		Group grpLocation = new Group(cmpStatistics, SWT.NONE);
		grpLocation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpLocation.setText("Location");
		grpLocation.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpLocation.setLayout(new GridLayout(1, false));

		final Button btnMinimum = new Button(grpLocation, SWT.CHECK);
		btnMinimum.setSelection(true);
		btnMinimum.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnMinimum.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnMinimum.setText("Minimum");
		btnMinimum.setData("min");

		final Button btnMaximum = new Button(grpLocation, SWT.CHECK);
		btnMaximum.setSelection(true);
		btnMaximum.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnMaximum.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnMaximum.setText("Maximum");
		btnMaximum.setData("max");

		Button btnStQuartile = new Button(grpLocation, SWT.CHECK);
		btnStQuartile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnStQuartile.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnStQuartile.setText("1st Quartile");
		btnStQuartile.setData("q1");

		Button btnrdQuartile = new Button(grpLocation, SWT.CHECK);
		btnrdQuartile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnrdQuartile.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnrdQuartile.setText("3rd Quartile");
		btnrdQuartile.setData("q3");



		Group grpDispersion = new Group(cmpStatistics, SWT.NONE);
		grpDispersion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		grpDispersion.setText("Dispersion");
		grpDispersion.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpDispersion.setLayout(new GridLayout(1, false));

		Button btnRange = new Button(grpDispersion, SWT.CHECK);
		btnRange.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnRange.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnRange.setText("Range");
		btnRange.setData("range");

		Button btnInterquartileRange = new Button(grpDispersion, SWT.CHECK);
		btnInterquartileRange.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnInterquartileRange.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnInterquartileRange.setText("Interquartile Range");
		btnInterquartileRange.setData("iqr");

		Button btnVariance = new Button(grpDispersion, SWT.CHECK);
		btnVariance.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnVariance.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnVariance.setText("Variance");
		btnVariance.setData("var");

		final Button btnStandardDeviation = new Button(grpDispersion, SWT.CHECK);
		btnStandardDeviation.setSelection(true);
		btnStandardDeviation.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnStandardDeviation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnStandardDeviation.setText("Standard Deviation");
		btnStandardDeviation.setData("sd");

		Button btnStandardErrorOfTheMean = new Button(grpDispersion, SWT.CHECK);
		btnStandardErrorOfTheMean.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnStandardErrorOfTheMean.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnStandardErrorOfTheMean.setText("Standard Error of the Mean");
		btnStandardErrorOfTheMean.setData("se.mean");

		Button btnCoefficientOfVariation = new Button(grpDispersion, SWT.CHECK);
		btnCoefficientOfVariation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnCoefficientOfVariation.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnCoefficientOfVariation.setText("Coefficient of Variation");
		btnCoefficientOfVariation.setData("cv");
		new Label(cmpStatistics, SWT.NONE);








		Group grpDistribution = new Group(cmpStatistics, SWT.NONE);
		grpDistribution.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpDistribution.setLayout(new GridLayout(1, false));
		grpDistribution.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpDistribution.setText("Distribution");

		Button btnSkewness = new Button(grpDistribution, SWT.CHECK);
		btnSkewness.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnSkewness.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnSkewness.setText("Skewness");
		btnSkewness.setData("skew");

		Button btnKurtosis = new Button(grpDistribution, SWT.CHECK);
		btnKurtosis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnKurtosis.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnKurtosis.setText("Kurtosis");
		btnKurtosis.setData("kurtosis");
		new Label(cmpStatistics, SWT.NONE);

		Composite grpButtons = new Composite(cmpStatistics, SWT.NONE);
		grpButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		GridLayout gl_grpButtons = new GridLayout(1, false);
		grpButtons.setLayout(gl_grpButtons);

		Button btnUncheckAll = new Button(grpButtons, SWT.NONE);
		GridData gd_btnUncheckAll = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_btnUncheckAll.widthHint = 20;
		btnUncheckAll.setLayoutData(gd_btnUncheckAll);
		btnUncheckAll.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnUncheckAll.setText("Uncheck All");

		Button btnCheckAll = new Button(grpButtons, SWT.NONE);
		GridData gd_btnCheckAll = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_btnCheckAll.widthHint = 20;
		btnCheckAll.setLayoutData(gd_btnCheckAll);

		btnCheckAll.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnCheckAll.setText("Check All");














		btnDefaults = new Button(grpButtons, SWT.NONE);
		GridData gd_btnDefaults = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_btnDefaults.widthHint = 20;
		btnDefaults.setLayoutData(gd_btnDefaults);
		btnDefaults.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listManager.setCheckBoxesToBoolean(groups.toArray(new Composite[groups.size()]), false);

				btnNoOfNonmissing.setSelection(true);
				btnStandardDeviation.setSelection(true);
				btnMean.setSelection(true);
				btnMinimum.setSelection(true);
				btnMaximum.setSelection(true);
			}
		});
		btnDefaults.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnDefaults.setText("Defaults");

		btnCheckAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				listManager.setCheckBoxesToBoolean(groups.toArray(new Composite[groups.size()]), true);

			}
		});
		btnUncheckAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				listManager.setCheckBoxesToBoolean(groups.toArray(new Composite[groups.size()]), false);

			}
		});


		groups.addAll(Arrays.asList(grpOthers, grpDispersion, grpCentralTendency, grpLocation, grpDistribution));
		new Label(cmpStatistics, SWT.NONE);

		initializeList();
		return container;
	}


	@Override
	protected void buttonPressed(int buttonId){

		if (IDialogConstants.OK_ID == buttonId) {
			okPressed();
		}
		else if(buttonId == RESET){
			btnDefaults.notifyListeners(SWT.Selection, null);
			lstFactorialVariables.removeAll();
			lstNumericVariables.removeAll();
			lstVariables.removeAll();
			lstByVariables.removeAll();
			btnAddSingleVariable.setText("Add");
			btnAddSingleVariable.setEnabled(false);
			btnAddFactorVariable.setText("Add");
			btnAddFactorVariable.setEnabled(false);
			btnFactornumeric.setText("Set to Factor");
			btnFactornumeric.setEnabled(false);
			
			
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
			ProjectExplorerManager projectMan = new ProjectExplorerManager();
			Calendar lCDateTime = Calendar.getInstance();
			File outputFolder =  new File( StarAnalysisUtilities.createOutputFolder(filePath, "DescriptiveAnalysis"));
			OperationProgressDialog rInfo = new OperationProgressDialog(
					getShell(), "Performing Analysis");
			rInfo.open();
			String outputFile = (outputFolder.getPath().toString()
					+ File.separator + "Output.txt")
					.toString().replace(File.separator, "/");
			outputFolder.mkdir();
			String file = filePath.replace(File.separator, "/");
			String[] respVar = lstVariables.getItems();
			String[] grpVar = (lstByVariables.getItemCount() > 0) ? lstByVariables.getItems() : null;
			String[] statistics = listManager.getCheckBoxesValue(groups
					.toArray(new Composite[groups.size()]));
			System.out.println("DATA:  " + file + " " + outputFile + " "
					+ respVar + " " + grpVar + " " + statistics);
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager()
			.doDescriptiveStatistics(file, outputFile, respVar, grpVar,
					statistics);
			projectMan
			.refreshFolder((ProjectExplorerTreeNodeModel) projectMan
					.getOutputFolder(ProjectExplorerView.projectTree)
					.getData());
			projectMan
			.refreshFolder(projectMan
					.getTreeNodeModelbyFilePath(outputFolder
							.getAbsolutePath()));
			this.getShell().setMinimized(true);
			PartStackHandler.execute(projectMan
					.getTreeNodeModelbyFilePath(outputFile.replace("/", File.separator)));
			rInfo.close();
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

	private void initializeList(){

		listManager.initializeSingleButtonList(lstNumericVariables, lstVariables,btnFactornumeric, btnAddSingleVariable);
		listManager.initializeSingleButtonList(lstFactorialVariables, lstByVariables,btnFactornumeric, btnAddFactorVariable);
		listManager.initializeFactorList(lstFactorialVariables, filePath);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeVariableMoveList(lstNumericVariables, lstFactorialVariables, btnFactornumeric, filePath);
	}
	private boolean validateForm(List lstVariables, List lstByVariables, Composite[] groupComposites, Button btnOkay){
		if(lstVariables.getItemCount() >= 1  && listManager.isCheckBoxesHasBoolean(groupComposites))
		{
			return true;
		}
		else {
			MessageDialog.openError(this.getShell(), "Error", "At least one required field is empty.  Variable(s) list box of the Variable Description tab should contain at least one item and at least one item in the Statistics tab should be chosen");
			return false;
		}

	}



	@Override
	protected Point getInitialSize() {
		return new Point(499, 479);
	}
}
