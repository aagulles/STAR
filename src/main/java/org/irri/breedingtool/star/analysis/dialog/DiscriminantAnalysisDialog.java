package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.text.TableView.TableRow;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Spinner;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.graphs.managers.GraphTableManager;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.GeneralUtility;
import org.irri.breedingtool.utility.GraphsUtilities;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.CCombo;

public class DiscriminantAnalysisDialog extends Dialog {

	private Button btnOk;
	private Button btnAddVars;
	private List lstNumericVars;
	private List lstVars;
	private String filePath = PartStackHandler.getActiveElementID();
	private DialogFormUtility listManager = new DialogFormUtility();
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private ArrayList<String> tableData = new ArrayList<String>();
	private TabItem tbtmNewItem_1;
	private TableColumn tblclmnNewColumn;
	private TableColumn tblclmnNewColumn_2;
	private Composite composite_2;
	private Group grpDisplay;
	private Button btnBox;
	private Button btnDescriptives;
	private Label lblFactors;
	private List lstFactors;
	private Button btnMove;
	private Button btnAddGrp;
	private Text txtGrpVar;
	private Label lblIdVar;
	private Button btnProbabilities;
	private Button btnShapiro;
	private Button btnCases;
	private Button btnBrowse;
	private Label lblFile;
	private Text txtFile;
	private File file;
	private GraphTableManager tableManager;
	private Table table;
	private String dataFileName2 = "NULL";
	private String temp;
	private int dVal;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DiscriminantAnalysisDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Discriminant Analysis: " + new File(filePath).getName());
		
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
		composite.setLayout(new GridLayout(8, false));
		
		Label lblAvailableData = new Label(composite, SWT.NONE);
		GridData gd_lblAvailableData = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_lblAvailableData.widthHint = 102;
		lblAvailableData.setLayoutData(gd_lblAvailableData);
		lblAvailableData.setText("Numeric Variables:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblDependentVariables = new Label(composite, SWT.NONE);
		lblDependentVariables.setText("Variables:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lstNumericVars = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstNumericVars = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		gd_lstNumericVars.heightHint = 140;
		gd_lstNumericVars.widthHint = 160;
		lstNumericVars.setLayoutData(gd_lstNumericVars);
		
		btnAddVars = new Button(composite, SWT.NONE);
		GridData gd_btnAddVars = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_btnAddVars.widthHint = 90;
		btnAddVars.setLayoutData(gd_btnAddVars);
		btnAddVars.setText("Add");
		
		lstVars = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstVars = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		gd_lstVars.heightHint = 140;
		gd_lstVars.widthHint = 160;
		lstVars.setLayoutData(gd_lstVars);
		
		lblFactors = new Label(composite, SWT.NONE);
		lblFactors.setText("Factors:");
		new Label(composite, SWT.NONE);
		
		btnMove = new Button(composite, SWT.NONE);
		GridData gd_btnMove = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnMove.widthHint = 90;
		btnMove.setLayoutData(gd_btnMove);
		btnMove.setText("Set to Factor");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblIdVar = new Label(composite, SWT.NONE);
		lblIdVar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblIdVar.setText("Grouping variable:");
		new Label(composite, SWT.NONE);
		
		lstFactors = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstFactors = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		gd_lstFactors.heightHint = 140;
		gd_lstFactors.widthHint = 160;
		lstFactors.setLayoutData(gd_lstFactors);
		
		btnAddGrp = new Button(composite, SWT.NONE);
		btnAddGrp.addSelectionListener(new SelectionAdapter() {
			private double dVal;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(lstFactors.getSelectionCount()>0){
						String selectedVar[] = lstFactors.getSelection();
						txtGrpVar.setText(selectedVar[0]);
						String[] group = DataManipulationManager.getEnvtLevels(txtGrpVar.getText(), new File(filePath));
						
						for( String var : group){							
//							dVal= (double)1/group.length;
//							DecimalFormat df = new DecimalFormat("#.##");
//							String prob = ""+ df.format(dVal);
							
//							String prob ="" + (double) 1/group.length;
							String prob = "1/"+group.length;
							addGroup(var, prob);
						}
					}else{
						String[] group = DataManipulationManager.getEnvtLevels(txtGrpVar.getText(), new File(filePath));			
						for( String var : group){
//							dVal= (double)1/group.length;
//							DecimalFormat df = new DecimalFormat("#.##");
//							String prob = ""+ df.format(dVal);
							
//							String prob ="" + (double) 1/group.length;
							String prob = "1/"+group.length;
							removeGroup(var, prob);

						}
						
					}
				}
		});
		GridData gd_btnAddGrp = new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1);
		gd_btnAddGrp.widthHint = 90;
		btnAddGrp.setLayoutData(gd_btnAddGrp);
		btnAddGrp.setText("Add");
		
		txtGrpVar = new Text(composite, SWT.BORDER);
		txtGrpVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_txtGrpVar = new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1);
		gd_txtGrpVar.widthHint = 160;
		txtGrpVar.setLayoutData(gd_txtGrpVar);
		
		tbtmNewItem_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText("Options");
		
		composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem_1.setControl(composite_2);
		composite_2.setLayout(new GridLayout(3, false));
		
		grpDisplay = new Group(composite_2, SWT.NONE);
		GridData gd_grpDisplay = new GridData(SWT.LEFT, SWT.TOP, false, false, 3, 2);
		gd_grpDisplay.heightHint = 69;
		gd_grpDisplay.widthHint = 313;
		grpDisplay.setLayoutData(gd_grpDisplay);
		grpDisplay.setText("Display");
		grpDisplay.setLayout(new GridLayout(1, false));
		
		btnDescriptives = new Button(grpDisplay, SWT.CHECK);
		btnDescriptives.setText("Descriptive Statistics");
		
		btnShapiro = new Button(grpDisplay, SWT.CHECK);
		btnShapiro.setText("Shapiro-Wilk Multivariate Normality Test");
		
		btnBox = new Button(grpDisplay, SWT.CHECK);
		btnBox.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		btnBox.setText("Box's M Test for Homogeneity of Covariance Matrices");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		btnProbabilities = new Button(composite_2, SWT.CHECK);
		btnProbabilities.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnProbabilities.getSelection()){
					table.setEnabled(true);
					tableManager.setAllEnabled(true);
				}else 
				table.setEnabled(false);
				tableManager.setAllEnabled(false);
				tableManager.setAllEnabled(btnProbabilities.getSelection());
			}
		});
		btnProbabilities.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnProbabilities.setText("Specify prior probabilities");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		table = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
		table.setEnabled(false);
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_table = new GridData(SWT.LEFT, SWT.TOP, true, false, 2, 1);
		gd_table.heightHint = 156;
		gd_table.widthHint = 135;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(71);
		tblclmnNewColumn.setText("Group");
		
				tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
				tblclmnNewColumn_2.setWidth(77);
				tblclmnNewColumn_2.setText("Probability");
		
		btnCases = new Button(composite_2, SWT.CHECK);
		btnCases.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnCases.getSelection()){
					lblFile.setEnabled(true);
					txtFile.setEnabled(true);
					btnBrowse.setEnabled(true);
				}else{
					lblFile.setEnabled(false);
					txtFile.setEnabled(false);
					btnBrowse.setEnabled(false);
				}
			}
		});
		btnCases.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1));
		btnCases.setText("Classify new cases");
		new Label(composite_2, SWT.NONE);
		
		lblFile = new Label(composite_2, SWT.NONE);
		lblFile.setEnabled(false);
		lblFile.setText("File:");
		
		txtFile = new Text(composite_2, SWT.BORDER);
		txtFile.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtFile.setEnabled(false);
		GridData gd_txtFile = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtFile.widthHint = 245;
		txtFile.setLayoutData(gd_txtFile);
		
		btnBrowse = new Button(composite_2, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
				 
			        fd.setText("Open");
			        String[] filterExt = { "*.csv"};
			        fd.setFilterExtensions(filterExt);
			        String filePath = fd.open();
			      if(filePath != null) {
			    	  dataFileName2 = filePath;
			    	  txtFile.setText(dataFileName2);
			      
			      }
			}
		});
		btnBrowse.setEnabled(false);
		btnBrowse.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		btnBrowse.setText("Browse...");	
		
		initializeContent();
		return container;
	}
	
	
	void initializeContent(){
		listManager.initializeNumericList(lstNumericVars, filePath);
		listManager.initializeFactorList(lstFactors, filePath);
		listManager.initializeSingleButtonList(lstNumericVars, lstVars, btnMove, btnAddVars);
		listManager.initializeSingleSelectionList(lstFactors, txtGrpVar, btnMove, btnAddGrp);
		listManager.initializeVariableMoveList(lstNumericVars, lstFactors, btnMove, filePath);
		
		ArrayList<Integer> tableHeaderIdentity = new ArrayList<Integer>();
		tableHeaderIdentity.add(GraphTableManager.ROW_STATIC_TEXT);
		tableHeaderIdentity.add(GraphTableManager.ROW_TEXT);		
		tableManager = new GraphTableManager(table, tableHeaderIdentity);
		tableManager.setAllEnabled(false);
	}
	void addGroup(String group, String prob){
		tableManager.addItem(new Object[]{
				group,prob	
			});
	}
	void removeGroup(String group, String prob){
		tableManager.removeAll();
	}

@Override
	protected void buttonPressed(int buttonID){
		if(buttonID == IDialogConstants.OK_ID) okPressed();
		else if(buttonID == IDialogConstants.RETRY_ID){
			lstNumericVars.removeAll();
			lstVars.removeAll();
			lstFactors.removeAll();
			txtGrpVar.setText("");
			btnAddVars.setEnabled(false);
			btnAddVars.setText("Add");
			btnAddGrp.setEnabled(false);
			btnAddGrp.setText("Add");
			btnMove.setEnabled(false);
			btnDescriptives.setSelection(false);
			btnBox.setSelection(false);
			btnProbabilities.setSelection(false);
			btnCases.setSelection(false);
			btnBrowse.setEnabled(false);
			btnShapiro.setSelection(false);
			table.setEnabled(false);
			tableManager.removeAll();
			txtFile.setText("");
			listManager.initializeNumericList(lstNumericVars, filePath);
			listManager.initializeFactorList(lstFactors, filePath);
		}
		else this.close();
	}
	
protected void	okPressed(){
	
	if(lstVars.getItemCount() < 2){
		MessageDialog.openError(getShell(), "Error", "Please add at least two variables."); 
		return ;
	}
	
	if(txtGrpVar.getText().equals(""))  { 
		MessageDialog.openError(getShell(), "Error", "Grouping variable list box should not be empty."); 
		return ; 
	}
	String outputFolder = StarAnalysisUtilities.createOutputFolder(filePath,"Discriminant");
	String dataFileName = filePath.replace(File.separator, "/");
	String outputPath = outputFolder;
	String [] vars = lstVars.getItems();
	String grpVar = txtGrpVar.getText();
	boolean descriptive = btnDescriptives.getSelection();
	boolean doNormalTest = btnShapiro.getSelection();
	boolean doBoxM = btnBox.getSelection();
	
	
	
	String priorProb = "c(";
	String[] arrC = new String[table.getItemCount()]; 
	int x = 0;
	for(String[] row: tableManager.getDataToString()){
		arrC[x] = row[1];
		x++;		
	}
	String temp = GeneralUtility.combineArrayOfString(arrC, ",");	
	priorProb = priorProb + temp + ")";
	if(!btnProbabilities.getSelection()){
		priorProb = "NULL";
	}
	
	boolean classifyNew = btnCases.getSelection();
	if(btnCases.getSelection()){
		dataFileName2 = dataFileName2.replace(File.separator, "/");
	}
	 
	OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Performing Analysis");
	rInfo.open();
	btnOk.setEnabled(false);

	ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doDiscriminant(			
			dataFileName,
			outputPath.replace(File.separator, "/"),
			vars, 
			grpVar,
			descriptive,
			doNormalTest,
			doBoxM,
			priorProb,
			classifyNew,
			dataFileName2);
	StarAnalysisUtilities.testVarArgs(
			dataFileName,
			outputPath.replace(File.separator, "/"),
			vars, 
			grpVar,
			descriptive,
			doNormalTest,
			doBoxM,
			priorProb,
			classifyNew,
			dataFileName2);
	
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
		return new Point(545, 509);
	}
}
