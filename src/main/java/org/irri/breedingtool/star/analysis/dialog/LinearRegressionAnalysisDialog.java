package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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

public class LinearRegressionAnalysisDialog extends Dialog {

	private Button btnOk;
	private Button btnAddIndependentVariable;
	private Button btnAddDependentVariable;
	private List lstAvailableData;
	private List lstDependentVariables;
	private List lstIndependentVariables;
	private Button btnAndersondarling;
	private Button btnCramervonMises;
	private Button btnLilliefors;
	private Button btnShapirofrancia;
	private Button btnShapirowilk;
	private Composite cmpOptions;
	private Button btnVarianceInflationFactors;
	private Button btnCovarienceMatrix;
	private Button btnCoefficientInterval;
	private Spinner txtConfidenceInterval;
	private Button btnBreuschpagar;
	private Button btnGoldfeldquandt;
	private Group grpRegressionCoefficients;
	private Group grpTestForHeteroskedasticity;
	private Group grpNormalityTest;
	private String filePath = PartStackHandler.getActiveElementID();
	private Button btnIncludeConstantEquation;
	private Button btnCooksDistance;
	private Button btnLeverageValues;
	private TabItem tbtmOptions;
	private Button btnDurbinwatson;
	private DialogFormUtility listManager = new DialogFormUtility();
	private ArrayList<String> tableData = new ArrayList<String>();
	private Group grpDisplay;
	private Group grpSave;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LinearRegressionAnalysisDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Regression Analysis : " + new File(filePath).getName());
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
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblAvailableData = new Label(composite, SWT.NONE);
		lblAvailableData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblAvailableData.setText("Numeric Variables");
		new Label(composite, SWT.NONE);
		
		Label lblDependentVariables = new Label(composite, SWT.NONE);
		lblDependentVariables.setText("Dependent Variable(s)");
		
		lstAvailableData = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstAvailableData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 9);
		gd_lstAvailableData.heightHint = 130;
		gd_lstAvailableData.widthHint = 80;
		lstAvailableData.setLayoutData(gd_lstAvailableData);
		new Label(composite, SWT.NONE);
		
		lstDependentVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstDependentVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_lstDependentVariables.heightHint = 80;
		gd_lstDependentVariables.widthHint = 80;
		lstDependentVariables.setLayoutData(gd_lstDependentVariables);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnAddDependentVariable = new Button(composite, SWT.NONE);
		GridData gd_btnAddDependentVariable = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		gd_btnAddDependentVariable.widthHint = 90;
		btnAddDependentVariable.setLayoutData(gd_btnAddDependentVariable);
		btnAddDependentVariable.setText("Add");
		listManager.initializeSingleButtonList(lstAvailableData, lstDependentVariables, btnAddDependentVariable);
		new Label(composite, SWT.NONE);
		
		Label lblIndependentVariables = new Label(composite, SWT.NONE);
		lblIndependentVariables.setText("Independent Variable(s)");
		new Label(composite, SWT.NONE);
		
		lstIndependentVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstIndependentVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_lstIndependentVariables.heightHint = 80;
		gd_lstIndependentVariables.widthHint = 80;
		lstIndependentVariables.setLayoutData(gd_lstIndependentVariables);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnAddIndependentVariable = new Button(composite, SWT.NONE);
		GridData gd_btnAddIndependentVariable = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		gd_btnAddIndependentVariable.widthHint = 90;
		btnAddIndependentVariable.setLayoutData(gd_btnAddIndependentVariable);
		btnAddIndependentVariable.setText("Add");
		listManager.initializeSingleButtonList(lstAvailableData, lstIndependentVariables, btnAddIndependentVariable);
		
		tbtmOptions = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions.setText("Options");
		
		cmpOptions = new Composite(tabFolder, SWT.NONE);
		tbtmOptions.setControl(cmpOptions);
		cmpOptions.setLayout(new GridLayout(2, false));
		
		grpRegressionCoefficients = new Group(cmpOptions, SWT.NONE);
		grpRegressionCoefficients.setLayout(new GridLayout(3, false));
		GridData gd_grpRegressionCoefficients = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpRegressionCoefficients.heightHint = 74;
		grpRegressionCoefficients.setLayoutData(gd_grpRegressionCoefficients);
		grpRegressionCoefficients.setText("Regression Coefficients");
		
		btnCoefficientInterval = new Button(grpRegressionCoefficients, SWT.CHECK);
		btnCoefficientInterval.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtConfidenceInterval.setEnabled(btnCoefficientInterval.getSelection());
			}
		});
		btnCoefficientInterval.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		btnCoefficientInterval.setText("Coefficient Interval");
		
		txtConfidenceInterval = new Spinner(grpRegressionCoefficients, SWT.BORDER);
		txtConfidenceInterval.setEnabled(false);
		txtConfidenceInterval.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		txtConfidenceInterval.setMaximum(99);
		txtConfidenceInterval.setMinimum(95);
		
		Label label = new Label(grpRegressionCoefficients, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("%");
		
		btnCovarienceMatrix = new Button(grpRegressionCoefficients, SWT.CHECK);
		btnCovarienceMatrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnCovarienceMatrix.setText("Covarience Matrix");
		new Label(grpRegressionCoefficients, SWT.NONE);
		new Label(grpRegressionCoefficients, SWT.NONE);
		
		grpTestForHeteroskedasticity = new Group(cmpOptions, SWT.NONE);
		grpTestForHeteroskedasticity.setLayout(new GridLayout(1, false));
		GridData gd_grpTestForHeteroskedasticity = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpTestForHeteroskedasticity.heightHint = 78;
		grpTestForHeteroskedasticity.setLayoutData(gd_grpTestForHeteroskedasticity);
		grpTestForHeteroskedasticity.setText("Test for Heteroscedasticity");
		
		btnBreuschpagar = new Button(grpTestForHeteroskedasticity, SWT.CHECK);
		btnBreuschpagar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnBreuschpagar.setText("Breusch-Pagan");
		btnBreuschpagar.setData("bpagan");
		
		
		btnGoldfeldquandt = new Button(grpTestForHeteroskedasticity, SWT.CHECK);
		btnGoldfeldquandt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnGoldfeldquandt.setText("Goldfeld-Quandt");
		btnGoldfeldquandt.setData("gqtest");
		
		grpDisplay = new Group(cmpOptions, SWT.NONE);
		grpDisplay.setText("Display");
		grpDisplay.setLayout(new GridLayout(1, false));
		GridData gd_grpDisplay = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 7);
		gd_grpDisplay.heightHint = 99;
		grpDisplay.setLayoutData(gd_grpDisplay);
		
		btnVarianceInflationFactors = new Button(grpDisplay, SWT.CHECK);
		btnVarianceInflationFactors.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnVarianceInflationFactors.setText("Variance Inflation Factors");
		
		btnDurbinwatson = new Button(grpDisplay, SWT.CHECK);
		btnDurbinwatson.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnDurbinwatson.setText("Durbin-Watson");
		
		btnIncludeConstantEquation = new Button(grpDisplay, SWT.CHECK);
		btnIncludeConstantEquation.setSelection(true);
		btnIncludeConstantEquation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnIncludeConstantEquation.setText("Include constant in the equation");
		
		grpNormalityTest = new Group(cmpOptions, SWT.NONE);
		grpNormalityTest.setLayout(new GridLayout(1, false));
		GridData gd_grpNormalityTest = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 8);
		gd_grpNormalityTest.heightHint = 128;
		grpNormalityTest.setLayoutData(gd_grpNormalityTest);
		grpNormalityTest.setText("Normality Test for Residuals");
		
		
		btnShapirowilk = new Button(grpNormalityTest, SWT.CHECK);
		btnShapirowilk.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnShapirowilk.setText("Shapiro-Wilk");
		btnShapirowilk.setData("swilk");
		
		btnShapirofrancia = new Button(grpNormalityTest, SWT.CHECK);
		btnShapirofrancia.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnShapirofrancia.setText("Shapiro-Francia");
		btnShapirofrancia.setData("sfrancia");
		
		btnLilliefors = new Button(grpNormalityTest, SWT.CHECK);
		btnLilliefors.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnLilliefors.setText("Lilliefors (Kolmogorov-Smirnov)");
		btnLilliefors.setData("ks");
		
		btnCramervonMises = new Button(grpNormalityTest, SWT.CHECK);
		btnCramervonMises.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnCramervonMises.setText("Cramer-Von Mises");
		btnCramervonMises.setData("cramer");
		
		btnAndersondarling = new Button(grpNormalityTest, SWT.CHECK);
		btnAndersondarling.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnAndersondarling.setText("Anderson-Darling");
		btnAndersondarling.setData("anderson");
		
		grpSave = new Group(cmpOptions, SWT.NONE);
		grpSave.setLayout(new GridLayout(1, false));
		GridData gd_grpSave = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		gd_grpSave.heightHint = 81;
		grpSave.setLayoutData(gd_grpSave);
		grpSave.setText("Save");
		
		btnLeverageValues = new Button(grpSave, SWT.CHECK);
		btnLeverageValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnLeverageValues.setText("Leverage Values");
		
		btnCooksDistance = new Button(grpSave, SWT.CHECK);
		btnCooksDistance.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnCooksDistance.setText("Cook's Distance");
		
		Label lblNewLabel_3 = new Label(cmpOptions, SWT.NONE);
		GridData gd_lblNewLabel_3 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_lblNewLabel_3.heightHint = 16;
		lblNewLabel_3.setLayoutData(gd_lblNewLabel_3);

		
		listManager.initializeNumericList(lstAvailableData, filePath);
		
		return container;
	}
@Override
	protected void buttonPressed(int buttonID){
		if(buttonID == IDialogConstants.OK_ID) okPressed();
		else if(buttonID == IDialogConstants.RETRY_ID){
			listManager.setCheckBoxesToBoolean(new Composite[]{grpDisplay,grpSave,grpNormalityTest,grpTestForHeteroskedasticity}, false);
//			btnCoefficientInterval.setSelection(selected)(false);
//			btnCovarienceMatrix.setEnabled(true);
			btnAddDependentVariable.setText("Add");
			btnAddDependentVariable.setEnabled(false);
			btnAddIndependentVariable.setText("Add");
			btnAddIndependentVariable.setEnabled(false);
			btnIncludeConstantEquation.setSelection(true);
			txtConfidenceInterval.setSelection(95);
			lstAvailableData.removeAll();
			lstDependentVariables.removeAll();
			lstIndependentVariables.removeAll();
			listManager.initializeNumericList(lstAvailableData, filePath);
			txtConfidenceInterval.setEnabled(false);
			
		}
		else this.close();
	}
	
protected void	okPressed(){
	
	btnOk.setEnabled(false);
	

	String outputFolder = StarAnalysisUtilities.createOutputFolder(filePath,"LinearRegression") + File.separator;
	OperationProgressDialog rInfo = new OperationProgressDialog(
			getShell(), "Performing Analysis");
	rInfo.open();
	ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doLinearReg(			
			filePath.replace(File.separator, "/"),
			outputFolder.replace(File.separator, "/") ,
			lstDependentVariables.getItems(), 
			lstIndependentVariables.getItems(),
			btnIncludeConstantEquation.getSelection(),
			btnCoefficientInterval.getSelection(),
			txtConfidenceInterval.getSelection(),
			btnCovarienceMatrix.getSelection(),
			btnVarianceInflationFactors.getSelection(),
			btnDurbinwatson.getSelection(),
			listManager.getCheckBoxesValue(btnBreuschpagar,btnGoldfeldquandt),
			listManager.getCheckBoxesValue(grpNormalityTest),
			btnCooksDistance.getSelection(),
			btnLeverageValues.getSelection());
	
	this.getShell().setMinimized(true);
	StarAnalysisUtilities.openAndRefreshFolder(outputFolder);
	btnOk.setEnabled(true);	
	rInfo.close();

	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.RETRY_ID, "Reset", false);
		btnOk = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(524, 456);
	}
}
