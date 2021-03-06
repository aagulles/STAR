package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class DistributionFittingDialog extends Dialog {
	private String filePath = PartStackHandler.getActiveElementID();
	private DialogFormUtility listManager = new DialogFormUtility();
	private Button btnOkay;
	private List lstNumericVars;   
	private List lstFactors;
	private Button btnAdd1;
	private Button btnAdd2; 
	private Button btnSetToFactor;
	private Text txtVariable;
	private Text txtGroupBy;
	//private String[] Distributions = {"beta", "exp", "gamma", "geom", "lnorm", "logis", "nbinom", "norm", "pois"};
	private String[] FittingMethods = {"mle", "mme", "mge"};
	private Label lblAvailableDist;
	private List lstAvailableDist;
	private ListViewer listViewer;
	private Label lblSelectedDist;
	private List lstSelectedDist;
	
	private ListViewer listViewer_1;
	
	private Button btnAdd;
	private Label lblFittingMethod;
	private Combo cmboFittingMethod;
	private Label lblNewLabel;
	private Label label;
	private Label label_4;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DistributionFittingDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Fit Distributions : " + new File(filePath).getName());
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout(6, false);
		gl_container.horizontalSpacing = 0;
		container.setLayout(gl_container);
		
		Label lblNumericVariables = new Label(container, SWT.NONE);
		GridData gd_lblNumericVariables = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblNumericVariables.horizontalIndent = 6;
		lblNumericVariables.setLayoutData(gd_lblNumericVariables);
		lblNumericVariables.setText("Numeric Variable(s):");
		lblNumericVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblVariable = new Label(container, SWT.NONE);
		lblVariable.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblVariable.setText("Variable:");
		lblVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(container, SWT.NONE);
		
		ListViewer listViewer_2 = new ListViewer(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstNumericVars = listViewer_2.getList();
		GridData gd_lstNumericVars = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstNumericVars.widthHint = 150;
		gd_lstNumericVars.horizontalIndent = 6;
		gd_lstNumericVars.heightHint = 100;
		lstNumericVars.setLayoutData(gd_lstNumericVars);
		lstNumericVars.setItems(new String[] {});
		
		label_4 = new Label(container, SWT.NONE);
		GridData gd_label_4 = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
		gd_label_4.widthHint = 14;
		label_4.setLayoutData(gd_label_4);
		
		btnAdd1 = new Button(container, SWT.NONE);
		GridData gd_btnAdd1 = new GridData(SWT.FILL, SWT.TOP, false, true, 1, 1);
		gd_btnAdd1.widthHint = 90;
		btnAdd1.setLayoutData(gd_btnAdd1);
		btnAdd1.setText("Add");
		
		label = new Label(container, SWT.NONE);
		GridData gd_label = new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1);
		gd_label.widthHint = 13;
		label.setLayoutData(gd_label);
		
		txtVariable = new Text(container, SWT.BORDER);
		txtVariable.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtVariable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtVariable.setEditable(false);
		GridData gd_txtVariable = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		gd_txtVariable.widthHint = 150;
		txtVariable.setLayoutData(gd_txtVariable);
		
		lblNewLabel = new Label(container, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
		gd_lblNewLabel.widthHint = 8;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		btnSetToFactor = new Button(container, SWT.NONE);
		GridData gd_btnSetToFactor = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnSetToFactor.heightHint = 24;
		gd_btnSetToFactor.widthHint = 110;
		btnSetToFactor.setLayoutData(gd_btnSetToFactor);
		btnSetToFactor.setText("Set to Factor");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblFactors = new Label(container, SWT.NONE);
		GridData gd_lblFactors = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblFactors.horizontalIndent = 6;
		lblFactors.setLayoutData(gd_lblFactors);
		lblFactors.setText("Factor(s):");
		lblFactors.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblGroupBy = new Label(container, SWT.NONE);
		lblGroupBy.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblGroupBy.setText("Group by:");
		lblGroupBy.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(container, SWT.NONE);
		
		ListViewer listViewer_3 = new ListViewer(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstFactors = listViewer_3.getList();
		GridData gd_lstFactors = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstFactors.widthHint = 150;
		gd_lstFactors.horizontalIndent = 6;
		gd_lstFactors.heightHint = 100;
		lstFactors.setLayoutData(gd_lstFactors);
		lstFactors.setItems(new String[] {});
		new Label(container, SWT.NONE);
		
		btnAdd2 = new Button(container, SWT.NONE);
		btnAdd2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		GridData gd_btnAdd2 = new GridData(SWT.FILL, SWT.TOP, false, true, 1, 1);
		gd_btnAdd2.widthHint = 90;
		btnAdd2.setLayoutData(gd_btnAdd2);
		btnAdd2.setText("Add");
		new Label(container, SWT.NONE);
		
		txtGroupBy = new Text(container, SWT.BORDER);
		txtGroupBy.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtGroupBy.setEditable(false);
		GridData gd_txtGroupBy = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		gd_txtGroupBy.widthHint = 150;
		txtGroupBy.setLayoutData(gd_txtGroupBy);
		new Label(container, SWT.NONE);
		
		lblAvailableDist = new Label(container, SWT.NONE);
		GridData gd_lblAvailableDist = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblAvailableDist.horizontalIndent = 6;
		lblAvailableDist.setLayoutData(gd_lblAvailableDist);
		lblAvailableDist.setText("Available Distributions:");
		lblAvailableDist.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		lblSelectedDist = new Label(container, SWT.NONE);
		lblSelectedDist.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSelectedDist.setText("Selected Distribution(s):");
		lblSelectedDist.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(container, SWT.NONE);
		
		listViewer = new ListViewer(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstAvailableDist = listViewer.getList();
		GridData gd_lstAvailableDist = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstAvailableDist.widthHint = 150;
		gd_lstAvailableDist.horizontalIndent = 6;
		gd_lstAvailableDist.heightHint = 70;
		lstAvailableDist.setLayoutData(gd_lstAvailableDist);
		lstAvailableDist.setItems(new String[] {"Beta", "Exponential", "Gamma", "Geometric", "Log-Normal", "Logistic", "Negative Binomial", "Normal", "Poisson"});
		new Label(container, SWT.NONE);
		
		btnAdd = new Button(container, SWT.NONE);
		GridData gd_btnAdd = new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1);
		gd_btnAdd.widthHint = 90;
		btnAdd.setLayoutData(gd_btnAdd);
		btnAdd.setText("Add");
		new Label(container, SWT.NONE);
		
		listViewer_1 = new ListViewer(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstSelectedDist = listViewer_1.getList();
		GridData gd_lstSelectedDist = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstSelectedDist.heightHint = 70;
		gd_lstSelectedDist.widthHint = 150;
		lstSelectedDist.setLayoutData(gd_lstSelectedDist);
		lstSelectedDist.setItems(new String[] {});
		new Label(container, SWT.NONE);
		
		lblFittingMethod = new Label(container, SWT.NONE);
		GridData gd_lblFittingMethod = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblFittingMethod.horizontalIndent = 6;
		lblFittingMethod.setLayoutData(gd_lblFittingMethod);
		lblFittingMethod.setText("Fitting Method:");
		lblFittingMethod.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		cmboFittingMethod = new Combo(container, SWT.NONE);
		cmboFittingMethod.setItems(new String[] {"maximum likelihood", "moment matching", "maximum goodness-of-fit"});
		cmboFittingMethod.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmboFittingMethod.select(0);
		new Label(container, SWT.NONE);
		
		initializeContent();
		return container;
	}
	
	void initializeContent(){
		listManager.initializeSingleButtonList(lstAvailableDist, lstSelectedDist,btnSetToFactor, btnAdd);
		listManager.initializeSingleSelectionList(lstNumericVars, txtVariable, btnAdd1);
		listManager.initializeSingleSelectionList(lstFactors, txtGroupBy, btnAdd2);
		listManager.initializeVariableMoveList(lstNumericVars, lstFactors, btnSetToFactor , filePath);
		listManager.initializeNumericList(lstNumericVars, filePath);
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
		
		lstSelectedDist.removeAll();
		lstNumericVars.removeAll();
		lstFactors.removeAll();
		txtVariable.setText("");
		txtGroupBy.setText("");
		listManager.initializeNumericList(lstNumericVars, filePath);
		listManager.initializeFactorList(lstFactors, filePath);
		btnAdd.setText("Add");
		btnAdd1.setText("Add");
		btnAdd2.setText("Add");
		btnSetToFactor.setText("Set to Factor");
		btnAdd.setEnabled(false);
		btnAdd1.setEnabled(false);
		btnAdd2.setEnabled(false);
		btnSetToFactor.setEnabled(false);
		lstAvailableDist.setItems(new String[] {"Beta", "Exponential", "Gamma", "Geometric", "Log-Normal", "Logistic", "Negative Binomial", "Normal", "Poisson"});
		cmboFittingMethod.setText("maximum likelihood");
	}

	@Override
	protected void okPressed(){
		
		if(lstSelectedDist.getItemCount() < 1 ){
			MessageDialog.openError(this.getShell(), "Error", "Could not perform the analysis. \n\n Reason: Incomplete requirements. Make sure to fill up all the required fields");
			return;
		}

		if(txtVariable.getText().isEmpty()){
			MessageDialog.openError(this.getShell(), "Error", "Could not perform the analysis. \n\n Reason: Incomplete requirements. Make sure to fill up all the required fields");
			return;
		}
		
		OperationProgressDialog rInfo = new OperationProgressDialog(
				getShell(), "Performing Analysis");
		rInfo.open();
		
		btnOkay.setEnabled(false);
		String dataFileName = filePath.replace(File.separator, "/");
		String outputPath = StarAnalysisUtilities.createOutputFolder(filePath, "FittingDistributions");
		String yVar = txtVariable.getText();
		String byVar = txtGroupBy.getText();
		String[] distrib = getDistributions(lstSelectedDist.getItems());
		String fitMethod = FittingMethods[cmboFittingMethod.getSelectionIndex()]; 
		if(byVar.equals("")) byVar = null;
		
		StarAnalysisUtilities.testVarArgs(
				
				dataFileName,
				outputPath.replace(File.separator, "/"),
				yVar,
				byVar,
				distrib,
				fitMethod
				);
		ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doFitDistribution(
				dataFileName,
				outputPath.replace(File.separator, "/"),
				yVar,
				byVar,
				distrib,
				fitMethod
				);
		
//		else {
//			MessageDialog
//					.openError(
//							this.getShell(),
//							"Error",
//							"Could not perform the analysis. \n\n Reason: Incomplete requirements. Make sure to fill up all the required fields");
//		}
		
		this.getShell().setMinimized(true);
		StarAnalysisUtilities.openAndRefreshFolder(outputPath);
		btnOkay.setEnabled(true);
		rInfo.close();
	}
	
	private String[] getDistributions(String[] items) {
		
		String distributions[] = new String[items.length];
		for(int i=0; i<items.length; i++){
			String s = items[i];
			if(s.equals("Beta")) distributions[i] = "beta";
			else if(s.equals("Exponential")) distributions[i] = "exp";	
			else if(s.equals("Gamma")) distributions[i] = "gamma";
			else if(s.equals("Geometric")) distributions[i] = "geom";	
			else if(s.equals("Log-Normal")) distributions[i] = "lnorm";	
			else if(s.equals("Logistic")) distributions[i] = "logis";
			else if(s.equals("Negative Binomial")) distributions[i] = "nbinom";
			else if(s.equals("Normal")) distributions[i] = "norm";	
			else distributions[i] = "pois";
		}
		return distributions;
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
		return new Point(445, 551);
	}

}
