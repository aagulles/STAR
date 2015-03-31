package org.irri.breedingtool.pbtools.analysis.environment.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.PBToolAnalysisUtilities;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TableColumn;

public class TwoStageMultiEnvironmentDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private List numVarList;
	private List factorVarList;
	private Button addEnvBtn;
	private Button checkPerformPairwiseBtn;
	private Button compareWithControlsBtn;
	private Button performAllComparisonsBtn;
	private Text significanceLevel;
	private List genLevelsList;
	private Button addControlsBtn;
	private List controlsList;
	private Button checkDescriptiveStatistics;
	private Button checkVarianceComponents;
	private Button moveBtn;
	private List envVarList;
	private List genVarList;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private Button addGenBtn;
	private TabItem tbtmOptions;
	private Button checkFixedBtn;
	private Label lblLevelOfSignificance;
	private Label lblOptionsForGenotype;
	private Label lblSpecifyControls;
	private Label levelsOfGenotypeLbl;
	private Label controlsLbl;
	private Button checkRandomBtn;
	private Button checkBoxPlotBtn;
	private Button checkHistogramBtn;
	private Button checkDiagnosticPlotsBtn;
	private Label lblWeightOption;
	private Combo combo;
	private Group group_1;
	private Label lblSummary;
	private Table table;
	private Button btnEditButton;
	private Button addRespBtn;
	private Label lblResponseVariable;
	private Button addStdErrBtn;
	private Button addResidVarBtn;
	private Button addReplicateBtn;
	private Label lblStandardError;
	private Label lblResidualVariance;
	private Label lblNumberOfReplicates;
	private Label lblProvideTheFollowing;
	private Button cancelTableBtn;
	private Button addToTableBtn;
	private List respVarList;
	private List stdErrVarList;
	private List residVarList;
	private List replicateVarList;
	private TableColumn tblclmnResponseVar;
	private TableColumn tblclmnStdError;
	private TableColumn tblclmnVariance;
	private TableColumn tblclmnNoOfReps;
	private Button btnDeleteRow;
	private boolean stabilityFinlay;
	private boolean stabilityShukla;
	private boolean ammi;
	private boolean gge;

	//specify parameters
	String weightOption = "stderr";
	String[] respvar = {};
	String[] standardErrors = {};
	String[] residualVariances = {};
	String[] numberOfReps = {};
	String environment = "Env";
	String[] environmentLevels = {};
	String[] genotypeLevels = {};
	String genotype = "Geno";
	boolean descriptiveStat = true; 
	boolean varianceComponents = true;
	//	boolean testGenotypicEffect = true;
	//	boolean testGxEEffect = true;
	boolean boxplotRawData = true;
	boolean histogramRawData = true;
	boolean diagnosticPlot = true;
	boolean genotypeFixed = true;
	boolean performPairwise = true;
	String pairwiseAlpha = "0.05";
	String[] controlLevels = {};
	boolean compareControl = true;
	boolean performAllPairwise = false;
	boolean genotypeRandom = true;
	private boolean satisfiedAllConditions;
	private String analysisType;
	private Label lblNewLabel;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public TwoStageMultiEnvironmentDialog(Shell parentShell, String analysisType, File file) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		setBlockOnOpen(false);
		this.analysisType = analysisType;
		this.file=file;
		setFactors();
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setData("analysis", analysisType);
		getShell().setData("filePathID",file.getAbsolutePath());
		WindowDialogControlUtil.addWindowDialogToList(getShell());

		parent.addDisposeListener(new DisposeListener(){
			@Override
			public void widgetDisposed(DisposeEvent e) {
				// TODO Auto-generated method stub
				WindowDialogControlUtil.removeWindowDialogToList(getShell());
			}

		});

		parent.getShell().setText("Two-Stage Multi-Environment Analysis: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TabItem tbtmModelSpecifications_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setText("Model Specifications");

		Composite modelComposite = new Composite(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setControl(modelComposite);
		modelComposite.setLayout(new GridLayout(6, false));
		modelComposite.setBackground(getShell().getBackground());

		Label lblNumericVariables = new Label(modelComposite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNumericVariables.setText("Numeric Variables:");

		lblWeightOption = new Label(modelComposite, SWT.NONE);
		lblWeightOption.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWeightOption.setText("Weight option:");

		combo = new Combo(modelComposite, SWT.READ_ONLY);
		combo.setItems(new String[] {"No weight", "1/(sem^2)"});
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		combo.select(0);
		combo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(table.getItemCount()>0){
					if(MessageDialog.openConfirm(getShell(), "Are you sure?", "Changing the weight option will delete all the rows in the summary table.\n")){
						for(int i=0; i<table.getItemCount(); i++){
							deleteTableRowAt(i);
						}
						changeWeightOption();
					}else{
						if(stdErrVarList.getEnabled()) combo.select(1);
						else combo.select(0);
					}
				}else changeWeightOption();
			}

			private void changeWeightOption() {
				// TODO Auto-generated method stub

				if(combo.getSelectionIndex()==0){
					addStdErrBtn.setEnabled(false);
					stdErrVarList.setEnabled(false);
					lblStandardError.setEnabled(false);
					if(stdErrVarList.getItemCount()>0) numVarList.add(stdErrVarList.getItems()[0]);
					stdErrVarList.removeAll();

				}
				else{
					addStdErrBtn.setEnabled(true);
					stdErrVarList.setEnabled(true);
					lblStandardError.setEnabled(true);
				}
			}
		});
		new Label(modelComposite, SWT.NONE);

		numVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_numVarList = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 3);
		gd_numVarList.heightHint = 95;
		gd_numVarList.widthHint = 181;
		numVarList.setLayoutData(gd_numVarList);
		numVarList.setItems(numericVariables);
		numVarList.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableNumericButtons(false);
				if(numVarList.getSelectionCount()>0){
					moveBtn.setEnabled(true);
					factorVarList.setSelection(-1);
					moveBtn.setText("Set to Factor");
					moveBtn.setEnabled(true);
					addEnvBtn.setEnabled(false);
					addGenBtn.setEnabled(false);
					enableNumericButtons(true);
				}
			}
		});

		group_1 = new Group(modelComposite, SWT.NONE);
		group_1.setLayout(new GridLayout(5, false));
		GridData gd_group_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_group_1.widthHint = 203;
		gd_group_1.heightHint = 237;
		group_1.setLayoutData(gd_group_1);

		addRespBtn = new Button(group_1, SWT.NONE);
		addRespBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						respVarList.add(selectedNumVars[i]);
					}
					numVarList.remove(numVarList.getSelectionIndices());
					addEnvBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = respVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						numVarList.add(selectedNumVars[i]);
					}
					respVarList.remove(respVarList.getSelectionIndices());
				}
				enableNumericButtons(false);
			}
		});
		GridData gd_addRespBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addRespBtn.widthHint = 52;
		addRespBtn.setLayoutData(gd_addRespBtn);
		addRespBtn.setText("Add");

		lblResponseVariable = new Label(group_1, SWT.NONE);
		lblResponseVariable.setText("Response Variable:");

		respVarList = new List(group_1, SWT.BORDER);
		GridData gd_respVarList = new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1);
		gd_respVarList.heightHint = 20;
		respVarList.setLayoutData(gd_respVarList);
		respVarList.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(respVarList.getSelectionCount()>0){
					numVarList.setSelection(-1);
					addRespBtn.setText("Remove");
					addRespBtn.setEnabled(true);
				}
			}
		});

		lblProvideTheFollowing = new Label(group_1, SWT.WRAP);
		GridData gd_lblProvideTheFollowing = new GridData(SWT.FILL, SWT.BOTTOM, false, false, 5, 1);
		gd_lblProvideTheFollowing.widthHint = 199;
		gd_lblProvideTheFollowing.heightHint = 27;
		lblProvideTheFollowing.setLayoutData(gd_lblProvideTheFollowing);
		lblProvideTheFollowing.setText("Provide the following for the response variable specified above:");

		addStdErrBtn = new Button(group_1, SWT.NONE);
		addStdErrBtn.setEnabled(false);
		addStdErrBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						stdErrVarList.add(selectedNumVars[i]);
					}
					numVarList.remove(numVarList.getSelectionIndices());
					addStdErrBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = stdErrVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						numVarList.add(selectedNumVars[i]);
					}
					stdErrVarList.remove(stdErrVarList.getSelectionIndices());
				}

				enableNumericButtons(false);
			}
		});
		GridData gd_addStdErrBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addStdErrBtn.widthHint = 52;
		addStdErrBtn.setLayoutData(gd_addStdErrBtn);
		addStdErrBtn.setText("Add");

		lblStandardError = new Label(group_1, SWT.NONE);
		lblStandardError.setEnabled(false);
		lblStandardError.setText("Standard Error:");

		stdErrVarList = new List(group_1, SWT.BORDER);
		stdErrVarList.setEnabled(false);
		GridData gd_stdErrVarList = new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1);
		gd_stdErrVarList.heightHint = 20;
		stdErrVarList.setLayoutData(gd_stdErrVarList);
		stdErrVarList.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(stdErrVarList.getSelectionCount()>0){
					numVarList.setSelection(-1);
					addStdErrBtn.setText("Remove");
					addStdErrBtn.setEnabled(true);
				}
			}
		});

		addResidVarBtn = new Button(group_1, SWT.NONE);
		GridData gd_addResidVarBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addResidVarBtn.widthHint = 52;
		addResidVarBtn.setLayoutData(gd_addResidVarBtn);
		addResidVarBtn.setText("Add");
		addResidVarBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						residVarList.add(selectedNumVars[i]);
					}
					numVarList.remove(numVarList.getSelectionIndices());
					addResidVarBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = residVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						numVarList.add(selectedNumVars[i]);
					}
					residVarList.remove(residVarList.getSelectionIndices());
				}

				enableNumericButtons(false);
			}
		});

		lblResidualVariance = new Label(group_1, SWT.NONE);
		lblResidualVariance.setText("Residual Variance:");

		residVarList = new List(group_1, SWT.BORDER);
		GridData gd_residVarList = new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1);
		gd_residVarList.heightHint = 20;
		residVarList.setLayoutData(gd_residVarList);
		residVarList.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(residVarList.getSelectionCount()>0){
					numVarList.setSelection(-1);
					addResidVarBtn.setText("Remove");
					addResidVarBtn.setEnabled(true);
				}
			}
		});
		addReplicateBtn = new Button(group_1, SWT.NONE);
		GridData gd_addReplicateBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addReplicateBtn.widthHint = 52;
		addReplicateBtn.setLayoutData(gd_addReplicateBtn);
		addReplicateBtn.setText("Add");
		addReplicateBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						replicateVarList.add(selectedNumVars[i]);
					}
					numVarList.remove(numVarList.getSelectionIndices());
					addReplicateBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = replicateVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						numVarList.add(selectedNumVars[i]);
					}
					replicateVarList.remove(replicateVarList.getSelectionIndices());
				}

				enableNumericButtons(false);
			}
		});

		lblNumberOfReplicates = new Label(group_1, SWT.NONE);
		lblNumberOfReplicates.setText("Number of Replicates:");

		replicateVarList = new List(group_1, SWT.BORDER);
		GridData gd_replicateVarList = new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1);
		gd_replicateVarList.heightHint = 20;
		replicateVarList.setLayoutData(gd_replicateVarList);
		new Label(group_1, SWT.NONE);
		replicateVarList.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(replicateVarList.getSelectionCount()>0){
					numVarList.setSelection(-1);
					addReplicateBtn.setText("Remove");
					addReplicateBtn.setEnabled(true);
				}
			}
		});

		cancelTableBtn = new Button(group_1, SWT.NONE);
		cancelTableBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cancelResponseValues();
			}
		});
		GridData gd_cancelTableBtn = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_cancelTableBtn.widthHint = 90;
		cancelTableBtn.setLayoutData(gd_cancelTableBtn);
		cancelTableBtn.setText("Cancel");

		addToTableBtn = new Button(group_1, SWT.NONE);
		addToTableBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(respVarList.getItemCount()>0){
					if(residVarList.getItemCount()==0 || replicateVarList.getItemCount()==0){
						MessageDialog.openWarning(getShell(), "Invalid Table Input", "The variables you specified were incomplete.");
						//					
					}
					else if(stdErrVarList.getItemCount()==0 && lblStandardError.getEnabled() && combo.getSelectionIndex()==1){
						MessageDialog.openWarning(getShell(), "Invalid Table Input", "The variables you specified were incomplete.");
						//					MessageDialog.openError(getShell(), "Error", "Please do not leave any variable field empty!");
					}
					else{
						addVariablesToTable();
					}

				}
				else MessageDialog.openError(getShell(), "Error", "Please specify a response variable.");
			}
		});
		GridData gd_addToTableBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_addToTableBtn.widthHint = 90;
		addToTableBtn.setLayoutData(gd_addToTableBtn);
		addToTableBtn.setText("Add to table");

		lblSummary = new Label(modelComposite, SWT.NONE);
		lblSummary.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblSummary.setText("Summary:");

		table = new Table(modelComposite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_table.widthHint = 258;
		gd_table.heightHint = 58;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tblclmnResponseVar = new TableColumn(table, SWT.NONE);
		tblclmnResponseVar.setWidth(109);
		tblclmnResponseVar.setText("Response Var");

		tblclmnStdError = new TableColumn(table, SWT.NONE);
		tblclmnStdError.setWidth(81);
		tblclmnStdError.setText("Std Error");

		tblclmnVariance = new TableColumn(table, SWT.NONE);
		tblclmnVariance.setWidth(103);
		tblclmnVariance.setText("Variance");

		tblclmnNoOfReps = new TableColumn(table, SWT.NONE);
		tblclmnNoOfReps.setWidth(87);
		tblclmnNoOfReps.setText("No. of Reps");

		Label lblFactors = new Label(modelComposite, SWT.NONE);
		lblFactors.setText("Factors:");

		moveBtn = new Button(modelComposite, SWT.NONE);
		moveBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(numVarList.getSelectionCount()>0){//if move Down
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						factorVarList.add(selectedNumVars[i]);
						dataManipulationManager.editVariableType(file.getAbsolutePath(),varInfo, selectedNumVars[i],"Factor");
					}
					numVarList.remove(numVarList.getSelectionIndices());
					enableNumericButtons(false);
				}
				else{// move up
					String selectedNumVars[] = factorVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						numVarList.add(selectedNumVars[i]);
						dataManipulationManager.editVariableType(file.getAbsolutePath(),varInfo, selectedNumVars[i],"Numeric");
					}
					factorVarList.remove(factorVarList.getSelectionIndices());
				}
			}
		});
		moveBtn.setEnabled(false);
		GridData gd_moveBtn = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_moveBtn.heightHint = 24;
		gd_moveBtn.widthHint = 90;
		moveBtn.setLayoutData(gd_moveBtn);
		moveBtn.setText("Set to Factor");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		btnEditButton = new Button(modelComposite, SWT.NONE);
		btnEditButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(table.getItemCount()>0){
					if(table.getItemCount()==1 ||  table.getSelectionCount()>0){
						int index = table.getSelectionIndex();
						if(table.getSelectionCount()==0)index = 0;
						editRowValues(index);
					}else{
						MessageDialog.openWarning(getShell(), "No selected row", "Please select the row you want to delete.");
					}
				}else{
					MessageDialog.openWarning(getShell(), "Invalid input", "Table is empty. There is nothing to edit.");
				}
			}
		});
		GridData gd_btnEditButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnEditButton.widthHint = 90;
		btnEditButton.setLayoutData(gd_btnEditButton);
		btnEditButton.setText("Edit Row");

		btnDeleteRow = new Button(modelComposite, SWT.NONE);
		btnDeleteRow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(table.getItemCount()>0){
					if(table.getItemCount()==1 ||  table.getSelectionCount()>0){
						int index=table.getSelectionIndex();
						if(table.getSelectionCount()==0)index = 0;
						deleteTableRowAt(index);
					}else{
						MessageDialog.openWarning(getShell(), "No selected row", "Please select the row you want to delete.");
					}
				}
				else{
					MessageDialog.openWarning(getShell(),"Invalid input", "Table is empty. There is nothing to delete.");
				}
			}
		});
		GridData gd_btnDeleteRow = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDeleteRow.widthHint = 90;
		btnDeleteRow.setLayoutData(gd_btnDeleteRow);
		btnDeleteRow.setText("Delete Row");

		factorVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_factorVarList.heightHint = 95;
		gd_factorVarList.widthHint = 60;
		factorVarList.setLayoutData(gd_factorVarList);
		factorVarList.setItems(factorVariables);
		factorVarList.addListener(SWT.MouseDown, new Listener(){

			@Override
			public void handleEvent(Event event) {

				if(factorVarList.getSelectionIndex()>-1){
					enableFactorButtons(false);
					enableNumericButtons(false);
					enableFactorButtons(true);
					numVarList.setSelection(-1);
					String[] s=factorVarList.getSelection();
					String isNumeric = dataManipulationManager.isNumeric(file.getAbsolutePath().replaceAll("\\\\","/"), s[0]);
					if(isNumeric.equals("TRUE")){
						moveBtn.setText("Set to Numeric");
						moveBtn.setEnabled(true);
					}
					else moveBtn.setEnabled(false);
				}
			}
		});

		Group group = new Group(modelComposite, SWT.NONE);
		group.setLayout(new GridLayout(3, false));
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1);
		gd_group.widthHint = 206;
		group.setLayoutData(gd_group);

		addEnvBtn = new Button(group, SWT.NONE);
		addEnvBtn.setEnabled(false);
		addEnvBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedStrings[] = factorVarList.getSelection();
					environmentLevels = DataManipulationManager.getEnvtLevels(selectedStrings[0], file);
										
					if(environmentLevels.length<2){
						MessageDialog.openWarning(factorVarList.getShell(), "Invalid factor", "The environment factor should have at least two (2) levels.\n\n" +
								//selectedNumVars[0]+" has 1 level only. \n\n" +
								"Please choose a different environment factor.");
					} else {
						dataManipulationManager.moveSelectedStringFromTo( factorVarList, envVarList);
					}
				}
				else{//if the purpose is to remove

					dataManipulationManager.moveSelectedStringFromTo( envVarList, factorVarList);
				}
				addEnvBtn.setEnabled(false);
			}
		});
		addEnvBtn.setText("Add");
		GridData gd_addEnvBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addEnvBtn.widthHint = 52;
		addEnvBtn.setLayoutData(gd_addEnvBtn);

		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setText("Environment:");

		envVarList = new List(group, SWT.BORDER);
		GridData gd_envVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_envVarList.widthHint = 101;
		gd_envVarList.heightHint = 20;
		envVarList.setLayoutData(gd_envVarList);
		envVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( envVarList, factorVarList);
				addEnvBtn.setEnabled(false);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(envVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addEnvBtn.setText("Remove");
					addEnvBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addGenBtn = new Button(group, SWT.NONE);
		addGenBtn.setEnabled(false);
		addGenBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedStrings[] = factorVarList.getSelection();
					genotypeLevels = DataManipulationManager.getEnvtLevels(selectedStrings[0], file);
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, genVarList);
					genLevelsList.setItems(genotypeLevels);
					if(checkFixedBtn.getSelection()&&checkPerformPairwiseBtn.getSelection())activatePerformPairwiseOptions(true);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( genVarList, factorVarList);
					genLevelsList.removeAll();
					controlsList.removeAll();
					resetGenotypeControls();
				}
				addGenBtn.setEnabled(false);

			}
		});
		addGenBtn.setText("Add");
		GridData gd_addGenBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addGenBtn.widthHint = 52;
		addGenBtn.setLayoutData(gd_addGenBtn);

		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setText("Genotype:");

		genVarList = new List(group, SWT.BORDER);
		GridData gd_genVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_genVarList.heightHint = 19;
		genVarList.setLayoutData(gd_genVarList);
		genVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( genVarList, factorVarList);
				genLevelsList.removeAll();
				controlsList.removeAll();
				resetGenotypeControls();
				addGenBtn.setEnabled(false);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(genVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addGenBtn.setText("Remove");
					addGenBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		new Label(group, SWT.NONE);

		checkFixedBtn = new Button(group, SWT.CHECK);
		checkFixedBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkFixedBtn.getSelection()){//if checked
					activateFixedOptions(true);
					if(checkPerformPairwiseBtn.getSelection()){
						activatePerformPairwiseOptions(true);
						if(compareWithControlsBtn.getSelection())activateLevelOfConrolsOptions(true);
					}
				}
				else{//if unchecked
					activateFixedOptions(false);
					activatePerformPairwiseOptions(false);
					activateLevelOfConrolsOptions(false);
				}
			}
		});
		checkFixedBtn.setSelection(true);
		checkFixedBtn.setText("Fixed");

		checkRandomBtn = new Button(group, SWT.CHECK);
		checkRandomBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		checkRandomBtn.setText("Random");
		tbtmOptions = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions.setText("Options");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions.setControl(composite_1);
		composite_1.setBackground(getShell().getBackground());
		composite_1.setLayout(new GridLayout(7, false));

		lblOptionsForGenotype = new Label(composite_1, SWT.NONE);
		GridData gd_lblOptionsForGenotype = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_lblOptionsForGenotype.heightHint = 21;
		gd_lblOptionsForGenotype.widthHint = 217;
		lblOptionsForGenotype.setLayoutData(gd_lblOptionsForGenotype);
		lblOptionsForGenotype.setText("Options for Genotype as Fixed:");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		checkPerformPairwiseBtn = new Button(composite_1, SWT.CHECK);
		checkPerformPairwiseBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkPerformPairwiseBtn.getSelection()){//if checked
					activatePerformPairwiseOptions(true);
					if(compareWithControlsBtn.getSelection()) activateLevelOfConrolsOptions(true);
				}
				else{//if unchecked
					activatePerformPairwiseOptions(false);
					activateLevelOfConrolsOptions(false);
				}

			}
		});
		checkPerformPairwiseBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		checkPerformPairwiseBtn.setText("Perform pairwise mean comparisons");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		compareWithControlsBtn = new Button(composite_1, SWT.RADIO);
		compareWithControlsBtn.setEnabled(false);
		compareWithControlsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(compareWithControlsBtn.getSelection()){//if selected
					activateLevelOfConrolsOptions(true);
				}
				else{
					activateLevelOfConrolsOptions(false);
				}
			}
		});
		compareWithControlsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 5, 1));
		compareWithControlsBtn.setText("Compare with control(s)");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		performAllComparisonsBtn = new Button(composite_1, SWT.RADIO);
		performAllComparisonsBtn.setEnabled(false);
		performAllComparisonsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 5, 1));
		performAllComparisonsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		performAllComparisonsBtn.setText("Perform all comparisons");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		lblLevelOfSignificance = new Label(composite_1, SWT.NONE);
		lblLevelOfSignificance.setEnabled(false);
		lblLevelOfSignificance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
		lblLevelOfSignificance.setText("Level of Significance:");

		significanceLevel = new Text(composite_1, SWT.BORDER);
		significanceLevel.setEnabled(false);
		significanceLevel.setText("0.05");
		GridData gd_significanceLevel = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_significanceLevel.widthHint = 39;
		significanceLevel.setLayoutData(gd_significanceLevel);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		lblSpecifyControls = new Label(composite_1, SWT.NONE);
		lblSpecifyControls.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		lblSpecifyControls.setEnabled(false);
		lblSpecifyControls.setText("Specify Control(s)");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		levelsOfGenotypeLbl = new Label(composite_1, SWT.NONE);
		levelsOfGenotypeLbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		levelsOfGenotypeLbl.setEnabled(false);
		levelsOfGenotypeLbl.setText("Levels of Genotype:");
		new Label(composite_1, SWT.NONE);

		controlsLbl = new Label(composite_1, SWT.NONE);
		controlsLbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		controlsLbl.setEnabled(false);
		controlsLbl.setText("Control(s):");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		genLevelsList = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		genLevelsList.setEnabled(false);
		GridData gd_genLevelsList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_genLevelsList.widthHint = 120;
		genLevelsList.setLayoutData(gd_genLevelsList);
		genLevelsList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				dataManipulationManager.moveSelectedStringFromTo( genLevelsList, controlsList);
				addControlsBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(genLevelsList.getSelectionIndex()>-1){
					addControlsBtn.setEnabled(true);
					controlsList.setSelection(-1);
					String[] s=genLevelsList.getSelection();
					addControlsBtn.setText("Add");
					addControlsBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Label(composite_1, SWT.NONE);

		controlsList = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		controlsList.setEnabled(false);
		GridData gd_controlsList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_controlsList.widthHint = 120;
		controlsList.setLayoutData(gd_controlsList);
		controlsList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				dataManipulationManager.moveSelectedStringFromTo( controlsList, genLevelsList);
				addControlsBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(controlsList.getSelectionIndex()>-1){
					addControlsBtn.setEnabled(true);
					genLevelsList.setSelection(-1);
					String[] s=controlsList.getSelection();
					addControlsBtn.setText("Remove");
					addControlsBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		addControlsBtn = new Button(composite_1, SWT.NONE);
		addControlsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(genLevelsList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = genLevelsList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						controlsList.add(selectedNumVars[i]);
					}
					genLevelsList.remove(genLevelsList.getSelectionIndices());
					addControlsBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = controlsList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						genLevelsList.add(selectedNumVars[i]);
					}
					controlsList.remove(controlsList.getSelectionIndices());
				}
				addControlsBtn.setEnabled(false);
			}
		});
		addControlsBtn.setEnabled(false);
		GridData gd_addControlsBtn = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_addControlsBtn.widthHint = 78;
		addControlsBtn.setLayoutData(gd_addControlsBtn);
		addControlsBtn.setText("Add");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		lblNewLabel = new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label lblNewLabel_3 = new Label(composite_1, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		lblNewLabel_3.setText("Display:");
		new Label(composite_1, SWT.NONE);

		checkDescriptiveStatistics = new Button(composite_1, SWT.CHECK);
		checkDescriptiveStatistics.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		checkDescriptiveStatistics.setText("Descriptive Statistics");
		new Label(composite_1, SWT.NONE);

		checkVarianceComponents = new Button(composite_1, SWT.CHECK);
		checkVarianceComponents.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		checkVarianceComponents.setText("Variance Components");


		TabItem tbtmGraph_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmGraph_1.setText("Graph");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmGraph_1.setControl(composite);
		composite.setBackground(getShell().getBackground());
		composite.setLayout(new GridLayout(2, false));

		Label lblOutputGraph = new Label(composite, SWT.NONE);
		lblOutputGraph.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblOutputGraph.setText("Output Graph:");
		new Label(composite, SWT.NONE);

		checkBoxPlotBtn = new Button(composite, SWT.CHECK);
		checkBoxPlotBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2));
		checkBoxPlotBtn.setText("Boxplot across environments");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		checkHistogramBtn = new Button(composite, SWT.CHECK);
		GridData gd_checkHistogramBtn = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_checkHistogramBtn.widthHint = 156;
		checkHistogramBtn.setLayoutData(gd_checkHistogramBtn);
		checkHistogramBtn.setText("Histograms across environments");
		new Label(composite, SWT.NONE);

		checkDiagnosticPlotsBtn = new Button(composite, SWT.CHECK);
		checkDiagnosticPlotsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2));
		checkDiagnosticPlotsBtn.setText("Diagnostic plots");
		new Label(composite, SWT.NONE);

		return container;

	}

	protected void deleteTableRowAt(int index) {
		// TODO Auto-generated method stub
		String[] values = getVariableValuesFromTableAtRow(index);
		for(int i=0; i<4; i++){
			if(!values[i].equals(""))numVarList.add(values[i]);
		}
		dataManipulationManager.deleteTableRow(table, index);
	}

	protected void editRowValues(int index) {
		// TODO Auto-generated method stub
		cancelResponseValues();

		TableItem tableItem = table.getItem(index);
		//set Variable Values
		if(!tableItem.getText(0).equals(""))respVarList.add(tableItem.getText(0));
		if(!tableItem.getText(1).equals(""))stdErrVarList.add(tableItem.getText(1));
		if(!tableItem.getText(2).equals(""))residVarList.add(tableItem.getText(2));
		if(!tableItem.getText(3).equals(""))replicateVarList.add(tableItem.getText(3));

		dataManipulationManager.deleteTableRow(table, index);

	}

	protected String[] getVariableValuesFromTableAtRow(int index) {
		// TODO Auto-generated method stub
		TableItem tableItem = table.getItem(index);
		String[] values = new String[4];
		values[0]=tableItem.getText(0);
		values[1]=tableItem.getText(1);
		values[2]=tableItem.getText(2);
		values[3]=tableItem.getText(3);
		return values;
	}

	protected void cancelResponseValues() {
		// TODO Auto-generated method stub

		if( respVarList.getItemCount()>0)numVarList.add(respVarList.getItem(0));
		if( stdErrVarList.getItemCount()>0)numVarList.add(stdErrVarList.getItem(0));
		if( residVarList.getItemCount()>0)numVarList.add(residVarList.getItem(0));
		if( replicateVarList.getItemCount()>0)numVarList.add(replicateVarList.getItem(0));

		removeVarValues();
	}

	protected void addVariablesToTable() {
		// TODO Auto-generated method stub
		String[] values = getVariableValues();
		TableItem tableItem = new TableItem(table, SWT.CENTER);

		tableItem.setText(0, values[0]);
		tableItem.setText(1, values[1]);
		tableItem.setText(2, values[2]);
		tableItem.setText(3, values[3]);
	}

	private String[] getVariableValues() {
		// TODO Auto-generated method stub
		String[] values = new String[4];

		if(respVarList.getItemCount()>0)values[0]=respVarList.getItem(0);
		else values[0]="";
		if(stdErrVarList.getItemCount()>0)values[1]=stdErrVarList.getItem(0);
		else values[1]="";
		if(residVarList.getItemCount()>0)values[2]=residVarList.getItem(0);
		else values[2]="";
		if(replicateVarList.getItemCount()>0)values[3]=replicateVarList.getItem(0);
		else values[3]="";

		removeVarValues();
		return values;
	}

	private void removeVarValues() {
		// TODO Auto-generated method stub

		stdErrVarList.removeAll();
		respVarList.removeAll();
		residVarList.removeAll();
		replicateVarList.removeAll();
	}

	protected void resetGenotypeControls() {
		// TODO Auto-generated method stub
		genLevelsList.removeAll();
		controlsList.removeAll();
	}


	protected void activateFixedOptions(boolean state) {
		// TODO Auto-generated method stub
		lblOptionsForGenotype.setEnabled(state);
		checkPerformPairwiseBtn.setEnabled(state);

	}

	protected void activatePerformPairwiseOptions(boolean state) {
		// TODO Auto-generated method stub
		compareWithControlsBtn.setEnabled(state);
		significanceLevel.setEnabled(state);
		lblLevelOfSignificance.setEnabled(state);
		compareWithControlsBtn.setSelection(state);
		performAllComparisonsBtn.setEnabled(state);
		if(state){
			if(genotypeLevels.length>15){
				compareWithControlsBtn.setSelection(true);
				activateLevelOfConrolsOptions(true);
				performAllComparisonsBtn.setSelection(false);
				performAllComparisonsBtn.setEnabled(false);
			}
			else{
				performAllComparisonsBtn.setEnabled(state);
			}
		}
	}


	protected void activateLevelOfConrolsOptions(boolean state) {
		// TODO Auto-generated method stub
		lblSpecifyControls.setEnabled(state);
		levelsOfGenotypeLbl.setEnabled(state);
		controlsLbl.setEnabled(state);
		genLevelsList.setEnabled(state);
		controlsList.setEnabled(state);
	}

	protected void activateDesign(String design) {
		// TODO Auto-generated method stub
	}

	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
	}


	public void enableNumericButtons(boolean state){
		moveBtn.setEnabled(state);
		replicateVarList.setSelection(-1);
		respVarList.setSelection(-1);
		residVarList.setSelection(-1);
		stdErrVarList.setSelection(-1);

		if(state){//if enable all buttons
			//change button text
			addReplicateBtn.setText("Add");
			addRespBtn.setText("Add");
			addResidVarBtn.setText("Add");
			addStdErrBtn.setText("Add");

			//enable empty fields
			if(replicateVarList.getItemCount()<1) addReplicateBtn.setEnabled(state);
			if(respVarList.getItemCount()<1) addRespBtn.setEnabled(state);
			if(residVarList.getItemCount()<1) addResidVarBtn.setEnabled(state);
			if(stdErrVarList.getItemCount()<1 && stdErrVarList.getEnabled()) addStdErrBtn.setEnabled(state);
			//			if(columnVarList.getItemCount()<1 && lblColumn.getEnabled()) addColBtn.setEnabled(state);
		}
		else{
			//disable all buttons
			moveBtn.setEnabled(state);
			addReplicateBtn.setEnabled(state);
			addRespBtn.setEnabled(state);
			addResidVarBtn.setEnabled(state);
			addStdErrBtn.setEnabled(state);
		}
	}

	public void enableFactorButtons(boolean state){
		//un-select factor fields
		genVarList.setSelection(-1);
		envVarList.setSelection(-1);

		if(state){//if enable all buttons
			//change button text
			addGenBtn.setText("Add");
			addEnvBtn.setText("Add");

			//enable empty fields
			if(genVarList.getItemCount()<1) addGenBtn.setEnabled(state);
			if(envVarList.getItemCount()<1) addEnvBtn.setEnabled(state);
		}
		else{
			//disable all buttons

			moveBtn.setEnabled(state);
			addGenBtn.setEnabled(state);
			addEnvBtn.setEnabled(state);
		}
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		//		createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Reset", true);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}


	@Override
	protected void okPressed(){
		satisfiedAllConditions=true;
		genotypeFixed = checkFixedBtn.getSelection();
		genotypeRandom = checkRandomBtn.getSelection();
		if(table.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please add values to the summary table.");
		}
		else if(envVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please add an environment factor.");
		}
		else if(genVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please add a genotype factor.");
		}
		else if(!genotypeFixed && !genotypeRandom){
			MessageDialog.openError(getShell(), "Error", "Please specify whether the genotype variable is fixed or random.");
		}

		else{
			if(genotypeFixed){//if fixed is checked
				performPairwise = checkPerformPairwiseBtn.getSelection();
				if(performPairwise){
					pairwiseAlpha = significanceLevel.getText();
					if(pairwiseAlpha.equals("")){
						MessageDialog.openError(getShell(), "Error", "Please add a value for the pairwise level of significance.");
						satisfiedAllConditions=false;
					}
					else{
						try{
							Double d = Double.parseDouble(pairwiseAlpha);
							compareControl = compareWithControlsBtn.getSelection();
							performAllPairwise = performAllComparisonsBtn.getSelection();
							if(!compareControl && !performAllPairwise){
								MessageDialog.openError(getShell(), "Error", "Please specify which pairwise mean comparison to perform.");
								satisfiedAllConditions=false;
							}
							else if(compareControl){
								controlLevels = controlsList.getItems();
								if(controlLevels.length<1){
									MessageDialog.openError(getShell(), "Error", "Please specify control variables from the genotype levels.");
									satisfiedAllConditions=false;
								}
							}
						}catch(Exception npe){
							MessageDialog.openError(getShell(), "Error", "Please add numeric a value for the pairwise level of significance.");
							satisfiedAllConditions=false;
						}
					}
				}
			}
			if(satisfiedAllConditions){
				respvar = getResponseVariables();
				standardErrors = getStandardErrors();
				residualVariances = getResidVars();
				numberOfReps = getNumberOfReps();
				environment = envVarList.getItem(0);
				genotype = genVarList.getItem(0);
				//				testGxEEffect = true;
				pairwiseAlpha = significanceLevel.getText();
				//				testGenotypicEffect = testGenotypicEffectBtn.getSelection();

				descriptiveStat = checkDescriptiveStatistics.getSelection(); 
				varianceComponents = checkVarianceComponents.getSelection();
				boxplotRawData = checkBoxPlotBtn.getSelection();
				histogramRawData = checkHistogramBtn.getSelection();
				diagnosticPlot = checkDiagnosticPlotsBtn.getSelection();
				if(combo.getText().equals("No weight")){
					weightOption = "none";
				}else weightOption = "stderr";

				String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");

				File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);
				String newOutputFileName = outputFolder.getAbsolutePath()+"/output.txt";
				OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Two-stage Multi-Environment Analysis");
				rInfo.open();
				ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doMultiEnvironmentSecondStage(dataFileName, newOutputFileName.replaceAll("\\\\+", "/"),outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/", weightOption, respvar, standardErrors, residualVariances, numberOfReps, 
						environment, environmentLevels, genotype, descriptiveStat, varianceComponents, boxplotRawData, histogramRawData, diagnosticPlot, genotypeFixed, performPairwise, pairwiseAlpha, genotypeLevels, 
						controlLevels, compareControl, performAllPairwise, genotypeRandom);
				rInfo.close();
				WindowDialogControlUtil.hideAllWindowDialog();

				PBToolAnalysisUtilities.openFolder(outputFolder);
			}}


	}

	private String[] getNumberOfReps() {
		// TODO Auto-generated method stub
		int itemCount=table.getItemCount();

		String[] RepVars = new String[itemCount];
		for(int i=0;i<itemCount;i++){
			TableItem item= table.getItem(i);
			RepVars[i]=item.getText(3);
		}
		return RepVars;
	}

	private String[] getResidVars() {
		// TODO Auto-generated method stub
		int itemCount=table.getItemCount();

		String[] ResidVars = new String[itemCount];
		for(int i=0;i<itemCount;i++){
			TableItem item= table.getItem(i);
			ResidVars[i]=item.getText(2);
		}
		return ResidVars;
	}

	private String[] getStandardErrors() {
		// TODO Auto-generated method stub
		int itemCount=table.getItemCount();

		String[] stdErrVars = new String[itemCount];
		for(int i=0;i<itemCount;i++){
			TableItem item= table.getItem(i);
			stdErrVars[i]=item.getText(1);
		}
		return stdErrVars;
	}

	private String[] getResponseVariables() {
		// TODO Auto-generated method stub
		int itemCount=table.getItemCount();

		String[] respVars = new String[itemCount];
		for(int i=0;i<itemCount;i++){
			TableItem item= table.getItem(i);
			respVars[i]=item.getText(0);
		}
		return respVars;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(624, 732);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}