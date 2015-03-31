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
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TableColumn;

public class MultiplicativeModelsNewDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private List numVarList;
	private List factorVarList;
	private Button addEnvBtn;
	private Button moveBtn;
	private List envVarList;
	private List genVarList;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private Button addGenBtn;
	private TabItem tbtmOptions;
	private Group group_1;
	private Label lblSummary;
	private Table table;
	private Button btnEditButton;
	private Button addRespBtn;
	private Label lblResponseVariable;
	private Button addResidVarBtn;
	private Button addReplicateBtn;
	private Label lblResidualVariance;
	private Label lblNumberOfReplicates;
	private Label lblProvideTheFollowing;
	private Button cancelTableBtn;
	private Button addToTableBtn;
	private List respVarList;
	private List residVarList;
	private List replicateVarList;
	private TableColumn tblclmnResponseVar;
	private TableColumn tblclmnVariance;
	private TableColumn tblclmnNoOfReps;
	private Button btnDeleteRow;

	//specify parameters
	ArrayList<Integer> incompleteRequirements= new ArrayList<Integer>();// add 0 for none, 1 for incomplete weights
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
	boolean responsePlot = true; 
	boolean doAMMI = true;
	boolean biplotPC12 = true;
	boolean biplotPC13 = true;
	boolean biplotPC23 = true;
	boolean ammi1Biplot = true;
	boolean adaptationMap = true;
	boolean doGGE = true;
	boolean graphSymmetricView = true;
	boolean graphEnvironmentView = true;
	boolean graphGenotypicView = true;
	//	//				testGxEEffect = true;

	private boolean satisfiedAllConditions;
	private String analysisType;
	private Group group_2;
	private Button btnAMMI;
	private Button btnGGE;
	private Button btnResponsePlots;
	private Button btnAMMI12;
	private Button btnAMMI13;
	private Button btnAMMI23;
	private Button btnAMMI1;
	private Button btnAdaptation;
	private Button btnGGESym;
	private Button btnGGEEnv;
	private Button btnGGEGeno;
	private Label label;
	private Label lblGraphs;
	private Label label_1;
	private Label lblNewLabel;
	private Label label_2;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public MultiplicativeModelsNewDialog(Shell parentShell, String analysisType, File file) {
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

		parent.getShell().setText("Multiplicative Models: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
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
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
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
						incompleteRequirements.add(1);
						//					
					}
					else{
						incompleteRequirements.add(0);
					}
					addVariablesToTable();
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
						incompleteRequirements.remove(index);
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
						String[] values = getVariableValuesFromTableAtRow(index);
						for(int i=0; i<3; i++){
							if(!values[i].equals(""))numVarList.add(values[i]);
						}
						dataManipulationManager.deleteTableRow(table, index);
						incompleteRequirements.remove(index);
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
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, envVarList);
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
					//					genLevelsList.setItems(genotypeLevels);
					//					if(checkFixedBtn.getSelection()&&checkPerformPairwiseBtn.getSelection())activatePerformPairwiseOptions(true);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( genVarList, factorVarList);
					//					genLevelsList.removeAll();
					//					controlsList.removeAll();
					//					resetGenotypeControls();
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
				//				genLevelsList.removeAll();
				//				controlsList.removeAll();
				//				resetGenotypeControls();
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
		tbtmOptions = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions.setText("Options");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions.setControl(composite_1);
		composite_1.setBackground(getShell().getBackground());
		composite_1.setLayout(new GridLayout(1, false));
		new Label(composite_1, SWT.NONE);

		group_2 = new Group(composite_1, SWT.NONE);
		group_2.setLayout(new GridLayout(4, false));
		group_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		group_2.setText("Analysis:");

		btnAMMI = new Button(group_2, SWT.CHECK);
		btnAMMI.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnAMMI.getSelection()) {
					enableAmmiGraphOptions(true);
					defaultSelectionAmmiGraphOptions();
				}
				else enableAmmiGraphOptions(false);
			}
		});
		btnAMMI.setText("AMMI");
		btnAMMI.setSelection(true);
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);

		btnGGE = new Button(group_2, SWT.CHECK);
		btnGGE.setAlignment(SWT.CENTER);
		btnGGE.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnGGE.getSelection()) {
					enableGGEGraphOptions(true);
					defaultSelectionGGEGraphOptions();
				}
				else enableGGEGraphOptions(false);
			}
		});
		btnGGE.setText("GGE");
						
						label = new Label(group_2, SWT.NONE);
						new Label(group_2, SWT.NONE);
						new Label(group_2, SWT.NONE);
						new Label(group_2, SWT.NONE);
						
						lblGraphs = new Label(group_2, SWT.NONE);
						lblGraphs.setText("Graphs:");
						new Label(group_2, SWT.NONE);
						new Label(group_2, SWT.NONE);
						new Label(group_2, SWT.NONE);
				
						btnResponsePlots = new Button(group_2, SWT.CHECK);
						btnResponsePlots.setText("Response Plots");
								new Label(group_2, SWT.NONE);
								new Label(group_2, SWT.NONE);
								new Label(group_2, SWT.NONE);
								
								label_1 = new Label(group_2, SWT.NONE);
								
								lblNewLabel = new Label(group_2, SWT.NONE);
								new Label(group_2, SWT.NONE);
								new Label(group_2, SWT.NONE);
						
								btnAMMI12 = new Button(group_2, SWT.CHECK);
								btnAMMI12.setText("AMMI Biplot - PC1 vs. PC2");
								btnAMMI12.setSelection(true);
								new Label(group_2, SWT.NONE);
								
								label_2 = new Label(group_2, SWT.NONE);
						
								btnGGESym = new Button(group_2, SWT.CHECK);
								btnGGESym.setEnabled(false);
								btnGGESym.setText("GGE Biplot- Symmetric View");
								
										btnAMMI13 = new Button(group_2, SWT.CHECK);
										btnAMMI13.setText("AMMI Biplot - PC1 vs. PC3");
										new Label(group_2, SWT.NONE);
										new Label(group_2, SWT.NONE);
								
										btnGGEEnv = new Button(group_2, SWT.CHECK);
										btnGGEEnv.setEnabled(false);
										btnGGEEnv.setText("GGE Biplot- Environment View");
										
												btnAMMI23 = new Button(group_2, SWT.CHECK);
												btnAMMI23.setText("AMMI Biplot - PC2 vs. PC3");
												new Label(group_2, SWT.NONE);
												new Label(group_2, SWT.NONE);
										
												btnGGEGeno = new Button(group_2, SWT.CHECK);
												btnGGEGeno.setEnabled(false);
												btnGGEGeno.setText("GGE Biplot- Genotype View");
												
														btnAMMI1 = new Button(group_2, SWT.CHECK);
														btnAMMI1.setText("AMMI1 Biplot");
														btnAMMI1.setSelection(true);
												new Label(group_2, SWT.NONE);
												new Label(group_2, SWT.NONE);
												new Label(group_2, SWT.NONE);
														
																btnAdaptation = new Button(group_2, SWT.CHECK);
																btnAdaptation.setText("Adaptation Map");
														new Label(group_2, SWT.NONE);
														new Label(group_2, SWT.NONE);
														new Label(group_2, SWT.NONE);

		return container;

	}


	protected void enableGGEGraphOptions(boolean state) {
		// TODO Auto-generated method stub
		enableGraphOption(btnGGESym, state);
		enableGraphOption(btnGGEEnv, state);
		enableGraphOption(btnGGEGeno, state);
	}

	protected void enableAmmiGraphOptions(boolean state) {
		// TODO Auto-generated method stub

		enableGraphOption(btnAMMI13, state);
		enableGraphOption(btnAMMI12, state);
		enableGraphOption(btnAMMI23, state);
		enableGraphOption(btnAMMI1, state);
		enableGraphOption(btnAdaptation, state);

	}
	
	//added by NSales
	protected void defaultSelectionAmmiGraphOptions() {
		// TODO Auto-generated method stub

		btnAMMI12.setSelection(true);
		btnAMMI13.setSelection(false);
		btnAMMI23.setSelection(false);
		btnAMMI1.setSelection(true);
		btnAdaptation.setSelection(false);
	}
	
	protected void defaultSelectionGGEGraphOptions() {
		// TODO Auto-generated method stub

		btnGGESym.setSelection(true);
		btnGGEEnv.setSelection(true);
		btnGGEGeno.setSelection(true);
	}

	private void enableGraphOption(Button graphBtn, boolean state) {
		// TODO Auto-generated method stub
		graphBtn.setEnabled(state);
		if(!state) graphBtn.setSelection(false);
	}

	protected void editRowValues(int index) {
		// TODO Auto-generated method stub
		cancelResponseValues();

		TableItem tableItem = table.getItem(index);
		//set Variable Values
		if(!tableItem.getText(0).equals(""))respVarList.add(tableItem.getText(0));
		if(!tableItem.getText(1).equals(""))residVarList.add(tableItem.getText(1));
		if(!tableItem.getText(2).equals(""))replicateVarList.add(tableItem.getText(2));

		dataManipulationManager.deleteTableRow(table, index);

	}

	protected String[] getVariableValuesFromTableAtRow(int index) {
		// TODO Auto-generated method stub
		TableItem tableItem = table.getItem(index);
		String[] values = new String[3];
		values[0]=tableItem.getText(0);
		values[1]=tableItem.getText(1);
		values[2]=tableItem.getText(2);
		return values;
	}

	protected void cancelResponseValues() {
		// TODO Auto-generated method stub

		if( respVarList.getItemCount()>0)numVarList.add(respVarList.getItem(0));
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
	}

	private String[] getVariableValues() {
		// TODO Auto-generated method stub
		String[] values = new String[4];

		if(respVarList.getItemCount()>0)values[0]=respVarList.getItem(0);
		else values[0]="";
		if(residVarList.getItemCount()>0)values[1]=residVarList.getItem(0);
		else values[1]="";
		if(replicateVarList.getItemCount()>0)values[2]=replicateVarList.getItem(0);
		else values[2]="";

		removeVarValues();
		return values;
	}

	private void removeVarValues() {
		// TODO Auto-generated method stub

		respVarList.removeAll();
		residVarList.removeAll();
		replicateVarList.removeAll();
	}



	protected void activateFixedOptions(boolean state) {
		// TODO Auto-generated method stub
		//		lblOptionsForGenotype.setEnabled(state);
		//		checkPerformPairwiseBtn.setEnabled(state);
		//		stabilityFinlaybtn.setEnabled(state);
		//		stabilityShuklabtn.setEnabled(state);
		//		btnAmmiAnalysis.setEnabled(state);
		//		btnGgeAnalysis.setEnabled(state);
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

		if(state){//if enable all buttons
			//change button text
			addReplicateBtn.setText("Add");
			addRespBtn.setText("Add");
			addResidVarBtn.setText("Add");

			//enable empty fields
			if(replicateVarList.getItemCount()<1) addReplicateBtn.setEnabled(state);
			if(respVarList.getItemCount()<1) addRespBtn.setEnabled(state);
			if(residVarList.getItemCount()<1) addResidVarBtn.setEnabled(state);

		}
		else{
			//disable all buttons
			moveBtn.setEnabled(state);
			addReplicateBtn.setEnabled(state);
			addRespBtn.setEnabled(state);
			addResidVarBtn.setEnabled(state);
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
		String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");
		File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);
		String newOutputFileName = outputFolder.getAbsolutePath()+"/output.txt";
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Multiplicative Models Analysis");

		respvar = getResponseVariables();
		//		standardErrors = getStandardErrors();
		residualVariances = getResidVars();
		numberOfReps = getNumberOfReps();

		responsePlot = btnResponsePlots.getSelection(); 
		doAMMI = btnAMMI.getSelection();
		biplotPC12 = btnAMMI12.getSelection();
		biplotPC13 = btnAMMI13.getSelection();
		biplotPC23 = btnAMMI23.getSelection();
		ammi1Biplot = btnAMMI1.getSelection();
		adaptationMap = btnAdaptation.getSelection();
		doGGE = btnGGE.getSelection();
		graphSymmetricView = btnGGESym.getSelection();
		graphEnvironmentView = btnGGEEnv.getSelection();
		graphGenotypicView = btnGGEGeno.getSelection();
		//		//				testGxEEffect = true;

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
		else if(!doGGE && !doAMMI){
			MessageDialog.openError(getShell(), "Error", "Please select the type of analysis.");
		}
		else if(incompleteRequirements.contains(1)){

			String [] environmentLevels = DataManipulationManager.getEnvtLevels(environment, file);
			environment = envVarList.getItem(0);
			genotype = genVarList.getItem(0);

			String[] respvar = getResponseVariables();

			//open alternative dialog.	
			TwoStageMseRepsDialog dlg = new TwoStageMseRepsDialog(getShell(),respvar);
			dlg.open();
			String[] mseValue = dlg.getMseValues();
			String[] repValue = dlg.getRepsValues();

			if(dlg.getReturnCode()==0){
			rInfo.open();
			ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doMultiplicativeModelsVersion2(dataFileName, newOutputFileName.replaceAll("\\\\+", "/"), outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/", respvar, environment, genotype, repValue, mseValue, responsePlot, doAMMI, biplotPC12, biplotPC13, biplotPC23, ammi1Biplot, adaptationMap, doGGE, graphSymmetricView, graphEnvironmentView, graphGenotypicView);
			rInfo.close();
			WindowDialogControlUtil.hideAllWindowDialog();
			PBToolAnalysisUtilities.openFolder(outputFolder);
			}

		}
		else{
				environment = envVarList.getItem(0);
				genotype = genVarList.getItem(0);

				rInfo.open();
				ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doMultiplicativeModels(dataFileName, newOutputFileName.replaceAll("\\\\+", "/"),outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/",respvar, environment, genotype, numberOfReps, residualVariances, responsePlot, 
						doAMMI, biplotPC12, biplotPC13, biplotPC23, ammi1Biplot, adaptationMap, doGGE, graphSymmetricView, graphEnvironmentView, graphGenotypicView);
				rInfo.close();
				WindowDialogControlUtil.hideAllWindowDialog();
				PBToolAnalysisUtilities.openFolder(outputFolder);
		}
	}

	private String[] getNumberOfReps() {
		// TODO Auto-generated method stub
		int itemCount=table.getItemCount();

		String[] RepVars = new String[itemCount];
		for(int i=0;i<itemCount;i++){
			TableItem item= table.getItem(i);
			RepVars[i]=item.getText(2);
		}
		return RepVars;
	}

	private String[] getResidVars() {
		// TODO Auto-generated method stub
		int itemCount=table.getItemCount();

		String[] ResidVars = new String[itemCount];
		for(int i=0;i<itemCount;i++){
			TableItem item= table.getItem(i);
			ResidVars[i]=item.getText(1);
		}
		return ResidVars;
	}

	//	private String[] getStandardErrors() {
	//		// TODO Auto-generated method stub
	//		int itemCount=table.getItemCount();
	//
	//		String[] stdErrVars = new String[itemCount];
	//		for(int i=0;i<itemCount;i++){
	//			TableItem item= table.getItem(i);
	//			stdErrVars[i]=item.getText(0);
	//		}
	//		return stdErrVars;
	//	}

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