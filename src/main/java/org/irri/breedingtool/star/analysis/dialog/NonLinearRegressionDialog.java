package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;

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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.star.analysis.dialog.NonLinearRegressionAnalysisParameters.ParameterModel;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.GeneralUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class NonLinearRegressionDialog extends Dialog {

	private Button btnOk;
	private Button btnAddIndependentVariable;
	private Button btnAddDependentVariable;
	private List lstAvailableData;
	private List lstDependentVariables;
	private String filePath = PartStackHandler.getActiveElementID();
	private DialogFormUtility listManager = new DialogFormUtility();
//	private ArrayList<String> tableData = new ArrayList<String>();
	private TabItem tbtmOptions_1;
	private Composite composite_1;
	private Button btnCoefficientInterval;
	private Button btnCovarianceMatrix;
	private Button btnShapirowilk;
	private Group grpDisplay;
	private Group grpRegressionCoefficients;
	private Spinner txtConfidenceInterval;
	private Label lblModel;
	private Combo cmboModel;
	private Text txtExpression;
	private Button btnDescriptiveStatistics;
	private Button btnSpecifyExpression;
	private String startValues;
	private Label lblNewLabel;
	private Text txtIndependentVariables;
	private String modelExp = null;
	private String[] modelItems = {"quadratic", "cubic", "logis", "fpl", "gompertz", "weibull", "custom"};
	private ArrayList<ParameterModel> lstParameter = new ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel>();

	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NonLinearRegressionDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Nonlinear Regression Analysis : " + new File(filePath).getName());
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
		composite.setLayout(new GridLayout(5, false));
		
		Label lblAvailableData = new Label(composite, SWT.NONE);
		lblAvailableData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblAvailableData.setText("Numeric Variables:");
		new Label(composite, SWT.NONE);
		
		Label lblDependentVariables = new Label(composite, SWT.NONE);
		lblDependentVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblDependentVariables.setText("Dependent Variable(s):");
		
		lblNewLabel = new Label(composite, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 25;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		lstAvailableData = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstAvailableData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			
			}
		});
		lstAvailableData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 3));
		
		btnAddDependentVariable = new Button(composite, SWT.NONE);
		GridData gd_btnAddDependentVariable = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		gd_btnAddDependentVariable.widthHint = 90;
		btnAddDependentVariable.setLayoutData(gd_btnAddDependentVariable);
		btnAddDependentVariable.setText("Add");
		
		lstDependentVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstDependentVariables.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		new Label(composite, SWT.NONE);
		
		Label lblIndependentVariables = new Label(composite, SWT.NONE);
		lblIndependentVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblIndependentVariables.setText("Independent Variable:");
		new Label(composite, SWT.NONE);
		
		btnAddIndependentVariable = new Button(composite, SWT.NONE);

		GridData gd_btnAddIndependentVariable = new GridData(SWT.CENTER, SWT.TOP, true, true, 1, 1);
		gd_btnAddIndependentVariable.widthHint = 90;
		btnAddIndependentVariable.setLayoutData(gd_btnAddIndependentVariable);
		btnAddIndependentVariable.setText("Add");
		
		txtIndependentVariables = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		txtIndependentVariables.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtIndependentVariables.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		
		lblModel = new Label(composite, SWT.NONE);
		lblModel.setText("Model:");
		
		cmboModel = new Combo(composite, SWT.READ_ONLY);
		cmboModel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(cmboModel.getSelectionIndex() == 6){
					btnSpecifyExpression.setEnabled(true);
				} else btnSpecifyExpression.setEnabled(false);
				
			}
		});
		cmboModel.setItems(new String[] {"Quadratic Polynomial", "Cubic Polynomial", "Logistic", "Four-parameter Logistic", "Gompertz Growth", "Weibull Growth Curve", "[Custom]"});
		cmboModel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmboModel.select(0);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnSpecifyExpression = new Button(composite, SWT.NONE);
		btnSpecifyExpression.setEnabled(false);
		btnSpecifyExpression.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtIndependentVariables.getText().equals("")){
					//"Error", "Please add an Independent Variable."
					MessageDialog.openError(getParentShell(),"Error", "Please add an Independent Variable.");
					return;
				}
				String indepVar = txtIndependentVariables.getText();

				NonLinearRegressionAnalysisModelSpecDialog dlgNonLiRegAnalysis = new NonLinearRegressionAnalysisModelSpecDialog(getShell(), indepVar, lstParameter, txtExpression.getText());
				if(dlgNonLiRegAnalysis.open() == Dialog.OK){
//					if(!txtExpression.getText().isEmpty()){}
					System.out.println(dlgNonLiRegAnalysis.getReturnCode());
					if(dlgNonLiRegAnalysis.getReturnVal() != null){
					txtExpression.setText(dlgNonLiRegAnalysis.getReturnVal());
					
					lstParameter = dlgNonLiRegAnalysis.parameterList;
					}
				}
			}
		});
		btnSpecifyExpression.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 2, 1));
		btnSpecifyExpression.setText("Specify expression:");
		
		txtExpression = new Text(composite, SWT.BORDER);
		txtExpression.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtExpression.setEditable(false);
		txtExpression.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 3, 1));
//		listManager.initializeSingleButtonList(lstAvailableData, lstIndependentVariables, btnAddIndependentVariable);
//		listManager.initializeSingleButtonList(lstAvailableData, lstIndependentVariables, btnAddIndependentVariable, new varValidate(cmboMethod,btnAddIndependentVariable));	
//		listManager.initializeNumericList(lstAvailableData, filePath);
		
		tbtmOptions_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions_1.setText("Options");
		
		composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions_1.setControl(composite_1);
		composite_1.setLayout(new GridLayout(1, false));
		
		grpDisplay = new Group(composite_1, SWT.NONE);
		grpDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpDisplay.setText("Display");
		grpDisplay.setLayout(new GridLayout(1, false));
		
		btnDescriptiveStatistics = new Button(grpDisplay, SWT.CHECK);
		btnDescriptiveStatistics.setSelection(true);
		btnDescriptiveStatistics.setText("Descriptive Statistics");
		
		btnShapirowilk = new Button(grpDisplay, SWT.CHECK);
		btnShapirowilk.setText("Shapiro-Wilk Test for Normality");
		
		grpRegressionCoefficients = new Group(composite_1, SWT.NONE);
		grpRegressionCoefficients.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpRegressionCoefficients.setText("Regression Coefficients");
		grpRegressionCoefficients.setLayout(new GridLayout(3, false));
		
		btnCoefficientInterval = new Button(grpRegressionCoefficients, SWT.CHECK);
		btnCoefficientInterval.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtConfidenceInterval.setEnabled(btnCoefficientInterval.getSelection());
			}
		});
		btnCoefficientInterval.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		btnCoefficientInterval.setText("Coefficient Interval");
		
		txtConfidenceInterval = new Spinner(grpRegressionCoefficients, SWT.BORDER);
		txtConfidenceInterval.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtConfidenceInterval.setMaximum(99);
		txtConfidenceInterval.setMinimum(95);
		txtConfidenceInterval.setEnabled(false);
		
		Label label_1 = new Label(grpRegressionCoefficients, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label_1.setText("%");
		
		btnCovarianceMatrix = new Button(grpRegressionCoefficients, SWT.CHECK);
		btnCovarianceMatrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		btnCovarianceMatrix.setText("Covariance Matrix");
		new Label(grpRegressionCoefficients, SWT.NONE);
		new Label(grpRegressionCoefficients, SWT.NONE);

		initializeContent();
		return container;
	}
	void initializeContent(){
		listManager.initializeNumericList(lstAvailableData, filePath);
		listManager.initializeSingleButtonList(lstAvailableData, lstDependentVariables, btnAddDependentVariable);
		listManager.initializeSingleSelectionList(lstAvailableData, txtIndependentVariables, btnAddIndependentVariable);
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
	lstAvailableData.removeAll();
	lstDependentVariables.removeAll();
	txtIndependentVariables.setText("");
	txtExpression.setText("");
	cmboModel.setText("Quadratic Polynomial");
	btnSpecifyExpression.setEnabled(false);
	btnAddDependentVariable.setText("Add");
	btnAddIndependentVariable.setText("Add");
	btnAddDependentVariable.setEnabled(false);
	btnAddIndependentVariable.setEnabled(false);
	btnDescriptiveStatistics.setSelection(true);
	btnShapirowilk.setSelection(false);
	btnCoefficientInterval.setSelection(false);
	btnCovarianceMatrix.setSelection(false);
	txtConfidenceInterval.setEnabled(false);
	txtConfidenceInterval.setSelection(95);
	lstParameter.clear();
	
	listManager.initializeNumericList(lstAvailableData, filePath);
}
protected void	okPressed(){
	
	String outputFolder = StarAnalysisUtilities.createOutputFolder(filePath,"NonLinearRegression") + File.separator;
	OperationProgressDialog rInfo = new OperationProgressDialog(
			getShell(), "Performing Analysis");
	rInfo.open();
	btnOk.setEnabled(false);
	String dataFileName = filePath.replace(File.separator, "/");
	String outputPath = outputFolder;
	String[] depvar = lstDependentVariables.getItems(); 
	String indepvar = txtIndependentVariables.getText();
	boolean statistics = true; 
	String model = modelItems[cmboModel.getSelectionIndex()]; 
	
	if(!txtExpression.getText().isEmpty())modelExp  = txtExpression.getText(); 
	else modelExp = null;

	boolean covMatrix = btnCovarianceMatrix.getSelection();
	boolean normality= btnShapirowilk.getSelection(); 
	boolean confInterval= btnCoefficientInterval.getSelection(); 
	double confLevel = txtConfidenceInterval.getSelection();

	String[] arrTmpParam = new String[lstParameter.size()];
	
	for(int i = 0; i < lstParameter.size(); i++){
		arrTmpParam[i] = lstParameter.get(i).name + " = " + lstParameter.get(i).value;
	}
	
	if(!lstParameter.isEmpty()){
		startValues = "list(" + GeneralUtility.combineArrayOfString(arrTmpParam, ", ") + ")";
	}
	
	
	ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doNonlinearRegression(			
			dataFileName, 
			outputPath.replace(File.separator, "/"),
			depvar, 
			indepvar, 
			statistics, 
			model,
			modelExp, 
			startValues, 
			covMatrix, 
			normality,
			confInterval, 
			confLevel);
	
	StarAnalysisUtilities.openAndRefreshFolder(outputFolder);
	this.getShell().setMinimized(true);
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
	btnOk= createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
			true);
	createButton(parent, IDialogConstants.CANCEL_ID,
			IDialogConstants.CANCEL_LABEL, false);
}
/**    
 * Return the initial size of the dialog.
 */
@Override
protected Point getInitialSize() {
	return new Point(509, 505);
}
}
