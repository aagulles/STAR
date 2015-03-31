package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;

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
import org.irri.breedingtool.utility.DialogListValidator;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class MultipleLinearRegressionAnalysisDialog extends Dialog {

	private Button btnOk;
	private Button btnAddIndependentVariable;
	private Button btnAddDependentVariable;
	private List lstAvailableData;
	private List lstDependentVariables;
	private List lstIndependentVariables;
	private String filePath = PartStackHandler.getActiveElementID();
	private DialogFormUtility listManager = new DialogFormUtility();
//	private ArrayList<String> tableData = new ArrayList<String>();
	private TabItem tbtmOptions_1;
	private Composite composite_1;
	private Group grpSelection;
	private Combo cmboMethod;
	private Combo cmboStatistic;
	private Button btnVarianceInflationFactors;
	private Button btnDurbinwatson;
	private Button btnIncludeConstantEquation;
	private Button btnCoefficientInterval;
	private Button btnCovarianceMatrix;
	private Button btnBreuschpagan;
	private Button btnGoldfeldquandt;
	private Button btnShapirowilk;
	private Button btnShapirofrancia;
	private Button btnLilliefors;
	private Button btnCramervonMises;
	private Button btnAndersondarling;
	private Group grpDisplay;
	private Group grpSave;
	private Group grpRegressionCoefficients;
	private Group grpTestForHeteroskedasticity;
	private Group grpNormalityTest;
	private Spinner txtConfidenceInterval;
	private Button btnLeverageValues;
	private Button btnCooksDistance;
	private Label lblMethod;
	private Label lblStatistic;
	private Button btnCorrelationMatrix;
	private Button btnScatterplotMatrix;
	private String[] method = {"none","allposs","forward","backward","stepwise"};
	private String[] stat = {"adjrsq","rsquare","mallow"};
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public MultipleLinearRegressionAnalysisDialog(Shell parentShell) {
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
//		listManager.initializeSingleButtonList(lstAvailableData, lstDependentVariables, btnAddDependentVariable);

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
//		listManager.initializeSingleButtonList(lstAvailableData, lstIndependentVariables, btnAddIndependentVariable);
//		listManager.initializeSingleButtonList(lstAvailableData, lstIndependentVariables, btnAddIndependentVariable, new varValidate(cmboMethod,btnAddIndependentVariable));	
//		listManager.initializeNumericList(lstAvailableData, filePath);
		
		tbtmOptions_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions_1.setText("Options");
		
		composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions_1.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));
		
		grpSelection = new Group(composite_1, SWT.NONE);
		grpSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSelection.setLayout(new GridLayout(4, false));
		grpSelection.setText("Selection");
		
		lblMethod = new Label(grpSelection, SWT.NONE);
		lblMethod.setEnabled(false);
		lblMethod.setText("Method:");
		new Label(grpSelection, SWT.NONE);
		
		cmboMethod = new Combo(grpSelection, SWT.READ_ONLY);
		cmboMethod.setEnabled(false);
		cmboMethod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				cmboMethod.setEnabled(lstIndependentVariables.getSelectionCount()>1);
				if (cmboMethod.getSelectionIndex() == 1){
					lblStatistic.setEnabled(true);cmboStatistic.setEnabled(true);
					btnScatterplotMatrix.setEnabled(false);btnVarianceInflationFactors.setEnabled(false);
					btnDurbinwatson.setEnabled(false);btnLeverageValues.setEnabled(false);
					btnCooksDistance.setEnabled(false);btnCoefficientInterval.setEnabled(false);
					btnCovarianceMatrix.setEnabled(false);btnBreuschpagan.setEnabled(false);
					btnGoldfeldquandt.setEnabled(false);btnShapirowilk.setEnabled(false);
					btnShapirofrancia.setEnabled(false);btnLilliefors.setEnabled(false);
					btnCramervonMises.setEnabled(false);btnAndersondarling.setEnabled(false);
				}else {
					lblStatistic.setEnabled(false);cmboStatistic.setEnabled(false);
					btnScatterplotMatrix.setEnabled(true);btnVarianceInflationFactors.setEnabled(true);
					btnDurbinwatson.setEnabled(true);btnLeverageValues.setEnabled(true);
					btnCooksDistance.setEnabled(true);btnCoefficientInterval.setEnabled(true);
					btnCovarianceMatrix.setEnabled(true);btnBreuschpagan.setEnabled(true);
					btnGoldfeldquandt.setEnabled(true);btnShapirowilk.setEnabled(true);
					btnShapirofrancia.setEnabled(true);btnLilliefors.setEnabled(true);
					btnCramervonMises.setEnabled(true);btnAndersondarling.setEnabled(true);
				}
			
			}
		});
		cmboMethod.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		cmboMethod.setItems(new String[] {"[None]", "All possible regression", "Forward selection", "Backward elimination", "Stepwise regression"});
		cmboMethod.select(0);
		
		lblStatistic = new Label(grpSelection, SWT.NONE);
		lblStatistic.setEnabled(false);
		lblStatistic.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblStatistic.setText("Statistic:");
		
		cmboStatistic = new Combo(grpSelection, SWT.READ_ONLY);
		cmboStatistic.setEnabled(false);
		cmboStatistic.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		cmboStatistic.setItems(new String[] {"Adjusted R-squared", "R-squared", "Mallow's C(p)"});
		cmboStatistic.select(0);
		
		grpRegressionCoefficients = new Group(composite_1, SWT.NONE);
		grpRegressionCoefficients.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpRegressionCoefficients.setText("Regression Coefficients");
		grpRegressionCoefficients.setLayout(new GridLayout(3, false));
		
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
		txtConfidenceInterval.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtConfidenceInterval.setMaximum(99);
		txtConfidenceInterval.setMinimum(95);
		txtConfidenceInterval.setEnabled(false);
		
		Label label_1 = new Label(grpRegressionCoefficients, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label_1.setText("%");
		
		btnCovarianceMatrix = new Button(grpRegressionCoefficients, SWT.CHECK);
		btnCovarianceMatrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnCovarianceMatrix.setText("Covariance Matrix");
		new Label(grpRegressionCoefficients, SWT.NONE);
		new Label(grpRegressionCoefficients, SWT.NONE);
		
		grpDisplay = new Group(composite_1, SWT.NONE);
		grpDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		grpDisplay.setText("Display");
		grpDisplay.setLayout(new GridLayout(1, false));
		
		btnIncludeConstantEquation = new Button(grpDisplay, SWT.CHECK);
		btnIncludeConstantEquation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnIncludeConstantEquation.setText("Include constant in the equation");
		btnIncludeConstantEquation.setSelection(true);
		
		btnCorrelationMatrix = new Button(grpDisplay, SWT.CHECK);
		btnCorrelationMatrix.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnCorrelationMatrix.setText("Correlation Matrix");
		
		btnScatterplotMatrix = new Button(grpDisplay, SWT.CHECK);
		btnScatterplotMatrix.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnScatterplotMatrix.setText("Scatterplot Matrix");
		
		btnVarianceInflationFactors = new Button(grpDisplay, SWT.CHECK);
		btnVarianceInflationFactors.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnVarianceInflationFactors.setText("Variance Inflation Factors");
		
		btnDurbinwatson = new Button(grpDisplay, SWT.CHECK);
		btnDurbinwatson.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnDurbinwatson.setText("Durbin-Watson");
		
		grpTestForHeteroskedasticity = new Group(composite_1, SWT.NONE);
		grpTestForHeteroskedasticity.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpTestForHeteroskedasticity.setText("Test for Heteroscedasticity");
		grpTestForHeteroskedasticity.setLayout(new GridLayout(1, false));
		
		btnBreuschpagan = new Button(grpTestForHeteroskedasticity, SWT.CHECK);
		btnBreuschpagan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnBreuschpagan.setText("Breusch-Pagan");
		btnBreuschpagan.setData("bpagan");
		
		btnGoldfeldquandt = new Button(grpTestForHeteroskedasticity, SWT.CHECK);
		btnGoldfeldquandt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnGoldfeldquandt.setText("Goldfeld-Quandt");
		btnGoldfeldquandt.setData("gquandt");
		
		grpNormalityTest = new Group(composite_1, SWT.NONE);
		grpNormalityTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		grpNormalityTest.setText("Normality Test for Residuals");
		grpNormalityTest.setLayout(new GridLayout(1, false));
		
		btnShapirowilk = new Button(grpNormalityTest, SWT.CHECK);
		btnShapirowilk.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnShapirowilk.setText("Shapiro-Wilk");
		
		btnShapirofrancia = new Button(grpNormalityTest, SWT.CHECK);
		btnShapirofrancia.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnShapirofrancia.setText("Shapiro-Francia");
		
		btnLilliefors = new Button(grpNormalityTest, SWT.CHECK);
		btnLilliefors.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnLilliefors.setText("Lilliefors (Kolmogorov-Smirnov)");
		
		btnCramervonMises = new Button(grpNormalityTest, SWT.CHECK);
		btnCramervonMises.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnCramervonMises.setText("Cramer-Von Mises");
		
		btnAndersondarling = new Button(grpNormalityTest, SWT.CHECK);
		btnAndersondarling.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnAndersondarling.setText("Anderson-Darling");
		
		grpSave = new Group(composite_1, SWT.NONE);
		grpSave.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSave.setText("Save");
		grpSave.setLayout(new GridLayout(1, false));
		
		btnLeverageValues = new Button(grpSave, SWT.CHECK);
		btnLeverageValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnLeverageValues.setText("Leverage Values");
		
		btnCooksDistance = new Button(grpSave, SWT.CHECK);
		btnCooksDistance.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnCooksDistance.setText("Cook's Distance");

		initializeContent();
		return container;
	}
	void initializeContent(){
		listManager.initializeSingleButtonList(lstAvailableData, lstIndependentVariables, btnAddIndependentVariable, new varValidate(cmboMethod,btnAddIndependentVariable, lblMethod));		
		listManager.initializeSingleButtonList(lstAvailableData, lstDependentVariables,btnAddIndependentVariable, btnAddDependentVariable);
		listManager.initializeNumericList(lstAvailableData, filePath);

	}

@Override
	protected void buttonPressed(int buttonID){
		if(buttonID == IDialogConstants.OK_ID) okPressed();
		else if(buttonID == IDialogConstants.RETRY_ID){
			listManager.setCheckBoxesToBoolean(new Composite[]{grpDisplay,grpSave,grpNormalityTest,grpTestForHeteroskedasticity}, false);
			btnCoefficientInterval.setSelection(false);btnCovarianceMatrix.setSelection(false);
			cmboMethod.setText("[None]");cmboStatistic.setText("Adjusted R-squared");
			cmboMethod.setEnabled(false); lblMethod.setEnabled(false);
			btnIncludeConstantEquation.setSelection(true);txtConfidenceInterval.setSelection(95);
			btnAddDependentVariable.setText("Add");btnAddIndependentVariable.setText("Add");
			btnAddDependentVariable.setEnabled(false);btnAddIndependentVariable.setEnabled(false);
			lstAvailableData.removeAll();lstDependentVariables.removeAll();lstIndependentVariables.removeAll();
			listManager.initializeNumericList(lstAvailableData, filePath);
			txtConfidenceInterval.setEnabled(false);lblStatistic.setEnabled(false);cmboStatistic.setEnabled(false);
			btnScatterplotMatrix.setEnabled(true);btnVarianceInflationFactors.setEnabled(true);
			btnDurbinwatson.setEnabled(true);btnLeverageValues.setEnabled(true);
			btnCooksDistance.setEnabled(true);btnCoefficientInterval.setEnabled(true);
			btnCovarianceMatrix.setEnabled(true);btnBreuschpagan.setEnabled(true);
			btnGoldfeldquandt.setEnabled(true);btnShapirowilk.setEnabled(true);
			btnShapirofrancia.setEnabled(true);btnLilliefors.setEnabled(true);
			btnCramervonMises.setEnabled(true);btnAndersondarling.setEnabled(true);
			
		}
		else this.close();
	}
	
protected void	okPressed(){
	
	String outputFolder = StarAnalysisUtilities.createOutputFolder(filePath,"MultipleLinearRegression") + File.separator;
	String varSelection = method[cmboMethod.getSelectionIndex()]; 
	String statistic = stat[cmboStatistic.getSelectionIndex()]; 

	
	OperationProgressDialog rInfo = new OperationProgressDialog(
			getShell(), "Performing Analysis");
	rInfo.open();
	btnOk.setEnabled(false);
	
	
	boolean scatterplot = btnScatterplotMatrix.getSelection();
	
	StarAnalysisUtilities.testVarArgs(
			filePath.replace("\\", "/"),
			outputFolder.replace("\\", "/") ,
			lstDependentVariables.getItems(), 
			lstIndependentVariables.getItems(),
			btnIncludeConstantEquation.getSelection(),
			btnCorrelationMatrix.getSelection(),
			varSelection,
			statistic,
			btnCoefficientInterval.getSelection(),
			txtConfidenceInterval.getSelection(),
			btnCovarianceMatrix.getSelection(),
			listManager.getCheckBoxesValue(grpNormalityTest),
			listManager.getCheckBoxesValue(btnBreuschpagan,btnGoldfeldquandt),
			btnDurbinwatson.getSelection(),
			btnVarianceInflationFactors.getSelection(),
			btnCooksDistance.getSelection(),
			btnLeverageValues.getSelection(),
			scatterplot );
	
	ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doLinearRegSelection(			
			filePath.replace("\\", "/"),
			outputFolder.replace("\\", "/") ,
			lstDependentVariables.getItems(), 
			lstIndependentVariables.getItems(),
			btnIncludeConstantEquation.getSelection(),
			btnCorrelationMatrix.getSelection(),
			varSelection,
			statistic,
			btnCoefficientInterval.getSelection(),
			txtConfidenceInterval.getSelection(),
			btnCovarianceMatrix.getSelection(),
			listManager.getCheckBoxesValue(grpNormalityTest),
			listManager.getCheckBoxesValue(btnBreuschpagan,btnGoldfeldquandt),
			btnDurbinwatson.getSelection(),
			btnVarianceInflationFactors.getSelection(),
			btnCooksDistance.getSelection(),
			btnLeverageValues.getSelection(),
			scatterplot);
	
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
		return new Point(475, 428);
	}
	
	public class varValidate extends DialogListValidator{
		Combo cmboMethod;
		Button btnAddIndependentVariable;
		Label lblMethod;
		public varValidate( Combo cmboMethod, Button btnAddIndependentVariable, Label lblMethod) {
			super();
			this.btnAddIndependentVariable = btnAddIndependentVariable;
			this.cmboMethod = cmboMethod;
			this.lblMethod = lblMethod;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void  postAddProcess(){
			cmboMethod.setEnabled((lstIndependentVariables.getItemCount()>1));								
			lblMethod.setEnabled((lstIndependentVariables.getItemCount()>1));
			
			
		}
		@Override
		public void postRemoveProcess(){
			cmboMethod.setEnabled((lstIndependentVariables.getItemCount()>1));								
			lblMethod.setEnabled((lstIndependentVariables.getItemCount()>1));
			
		}
		
	}
	
	
}
