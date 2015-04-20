package org.irri.breedingtool.pbtools.analysis.environment.dialog;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
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
import org.eclipse.swt.widgets.TreeItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.OneStageMultiEnvironmentDialog.genotypeVarValidator;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.DialogListValidator;
import org.irri.breedingtool.utility.EnvVarValidator;
import org.irri.breedingtool.utility.PBToolAnalysisUtilities;
import org.irri.breedingtool.utility.TextVarValidator;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;

public class SingleEnvironmentDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo, spatialStrucList;
	private Combo designType;
	private List numVarList;
	private List factorVarList;
	private Button btnAddEnv;
	private Button btnAddBlock;
	private Button btnAddRep;
	private Button btnAddRow;
	private Button btnAddCol;
	private List responseVarList;
	private Button addBtn;
	private Button checkPerformPairwiseBtn;
	private Button compareWithControlsBtn;
	private Button performAllComparisonsBtn;
	private Text significanceLevel;
	private Button excludeControlsBtn;
	private Button estimateGenotypeBtn;
	private List genLevelsList;
	private Button addControlsBtn;
	private List controlsList;
	private Button checkDescriptiveStatistics;
	private Button checkVarianceComponents;
	private Button moveBtn;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private DialogFormUtility dlgManager = new DialogFormUtility();
	private Button btnAddGen;
	private TabItem tbtmOptions;
	private Button checkFixedBtn;
	private Label lblLevelOfSignificance;
	private Label lblOptionsForGenotype;
	private Label lblSpecifyControls;
	private Label levelsOfGenotypeLbl;
	private Label controlsLbl;
	private Label lblOptionsForGenotype_1;
	private Button checkRandomBtn;
	private Label lblBlock;
	private Label lblRep;
	private Group variableGroup;
	private Label lblCol;
	private Label lblRow;

	//specify parameters
	private int design = 0;
	private String[] respvars = {};
	private String environment = "NULL";
	private String[] environmentLevels = {};
	private String genotype = "NULL";
	private String block = "NULL";
	private String rep = "NULL";
	private String row = "NULL";
	private String column = "NULL";
	private boolean descriptiveStat = false; 
	private boolean varianceComponents = false;
	private boolean boxplotRawData = false;
	private boolean histogramRawData = false;
	private boolean heatmapResiduals = false;
	private String heatmapRow = null;
	private String heatmapColumn = null;
	private boolean diagnosticPlot = false;
	private boolean genotypeFixed = false;
	private boolean performPairwise = false;
	private String pairwiseAlpha = "0.05";
	private String[] genotypeLevels={};
	private String[] controlLevels={};
	private boolean compareControl = false;
	private boolean performAllPairwise = false;
	private boolean genotypeRandom = false;
	private boolean excludeControls = false;
//	private String[] controlLevels2 = {"1", "2", "3"};
	private boolean genoPhenoCorrelation = false;
	private Button checkBoxPlotBtn;
	private Button checkHistogramBtn;
	private Button checkHeatmapBtn;
	private Button checkDiagnosticPlotsBtn;
	private boolean satisfiedAllConditions = true;
	private boolean selectedHeatMapVars=false;
	private String analysisType;
	private Button specifyHeatMapVarsBtn;
	private Label lblEnv;
	private Label lblGen;
	private Text txtEnvVar;
	private Text txtGenVar;
	private Text txtBlockVar;
	private Text txtRepVar;
	private Text txtRowVar;
	private Text txtColVar;
	private Button btnMoransTest;
	private Label lblSpatialStructure;
	private Button btnCompoundSymmetry;
	private Button btnGaussian;
	private Button btnExponential;
	private Button btnSpherical;
	private boolean moransTest;
	private String[] spatialStruc;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SingleEnvironmentDialog(Shell parentShell, String analysisType, File file) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		setBlockOnOpen(false);
		this.analysisType = analysisType;
		this.file=file;
		setFactors();
		spatialStrucList = new ArrayList<String>();
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

		parent.getShell().setText("Single-Environment Analysis: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TabItem tbtmModelSpecifications_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setText("Model Specifications");

		Composite modelComposite = new Composite(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setControl(modelComposite);
		modelComposite.setLayout(new GridLayout(5, false));
		modelComposite.setBackground(getShell().getBackground());
		Label lblNewLabel = new Label(modelComposite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNewLabel.setText("Type of Design:");

		designType = new Combo(modelComposite, SWT.READ_ONLY);
		designType.setItems(new String[] {"Randomized Complete Block (RCB)", "Augmented RCB", "Augmented Latin Square", "Alpha-Lattice", "Row-Column", "Latinized Alpha-Lattice", "Latinized Row-Column", "Augmented Alpha-Lattice", "Augmented Row-Column", "P-Rep"});
		designType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		designType.select(0);
		designType.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableFactorButtons(false);
				if(designType.getSelectionIndex()==0){//RCB
					enableAugmentedOptions(false);
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					lblBlock.setEnabled(true);
					disable("replicate");
					disable("row");
					disable("column");
					enablePrepOptions(false);
				}
				else if(designType.getSelectionIndex()==1){//AugmentedRCB

					enableAugmentedOptions(true);	
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					lblBlock.setEnabled(true);
					disable("replicate");
					disable("row");
					disable("column");
					enablePrepOptions(false);
				}
				else if(designType.getSelectionIndex()==2){//Augmented Latin square
					enableAugmentedOptions(true);
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					lblRow.setEnabled(true);
					lblCol.setEnabled(true);
//					lblReplicate.setEnabled(true);
					disable("replicate");
					disable("block");
					enablePrepOptions(false);

				}
				else if(designType.getSelectionIndex()==3){//Alpha lattice
					enableAugmentedOptions(false);
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					lblRep.setEnabled(true);
					lblBlock.setEnabled(true);
					disable("row");
					disable("column");
					enablePrepOptions(false);
				}
				else if(designType.getSelectionIndex()==4){//Row Column
					enableAugmentedOptions(false);
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					lblRow.setEnabled(true);
					lblCol.setEnabled(true);
					lblRep.setEnabled(true);
					disable("block");
					enablePrepOptions(false);
				}
				else if(designType.getSelectionIndex()==5){//Augmented Alpha lattice

					enableAugmentedOptions(true);	
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					
					lblRep.setEnabled(true);
					lblBlock.setEnabled(true);
					disable("row");
					disable("column");
					enablePrepOptions(false);
				}
				else if(designType.getSelectionIndex()==6){//Augmented Row Column

					enableAugmentedOptions(true);	
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					
					lblRow.setEnabled(true);
					lblCol.setEnabled(true);
					lblRep.setEnabled(true);
					disable("block");
					enablePrepOptions(false);
				}
				else if(designType.getSelectionIndex()==7){// Latinized Alpha lattice
					enableAugmentedOptions(false);
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					lblRep.setEnabled(true);
					lblBlock.setEnabled(true);
					disable("row");
					disable("column");
					enablePrepOptions(false);
				}
				else if(designType.getSelectionIndex()==8){// Latinized Row Column
					enableAugmentedOptions(false);
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					lblRow.setEnabled(true);
					lblCol.setEnabled(true);
					lblRep.setEnabled(true);
					disable("block");
					enablePrepOptions(false);
				}
				else if(designType.getSelectionIndex()==9){// P-Rep
					enableAugmentedOptions(false);
					if(checkPerformPairwiseBtn.getSelection()){
						activateLevelOfConrolsOptions(true);
					}else activateLevelOfConrolsOptions(false);
					enablePrepOptions(true);
					lblRow.setEnabled(true);
					lblCol.setEnabled(true);
					disable("replicate");
					disable("block");
				}
			}
		});

		Label lblNumericVariables = new Label(modelComposite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNumericVariables.setText("Numeric Variables:");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		Label lblResponseVariables = new Label(modelComposite, SWT.NONE);
		lblResponseVariables.setText("Response Variable(s):");

		numVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_numVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_numVarList.heightHint = 95;
		gd_numVarList.widthHint = 126;
		numVarList.setLayoutData(gd_numVarList);
		numVarList.setItems(numericVariables);
		numVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				String selectedNumVars[] = numVarList.getSelection();
				dataManipulationManager.moveSelectedStringFromTo( numVarList, responseVarList);
				numVarList.remove(numVarList.getSelectionIndices());
//				activateRandomOptions((responseVarList.getItemCount() > 1) ? true : false);
				if(responseVarList.getItemCount()<2)estimateGenotypeBtn.setEnabled(false); estimateGenotypeBtn.setSelection(false);
				if(checkRandomBtn.getSelection())activateRandomOptions(true);
				if(designType.getSelectionIndex() == 1 || designType.getSelectionIndex() == 2){
					lblOptionsForGenotype_1.setEnabled(false);
					excludeControlsBtn.setEnabled(false);
					if(responseVarList.getItemCount()>1){
						lblOptionsForGenotype_1.setEnabled(true);
						estimateGenotypeBtn.setEnabled(true);
						
					}
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(numVarList.getSelectionCount()>0){
					
					moveBtn.setEnabled(true);
					factorVarList.setSelection(-1);
					
					responseVarList.setSelection(-1);
					addBtn.setText("Add");
					moveBtn.setText("Set to Factor");
					enableFactorButtons(false);
					enableNumericButtons(true);
					
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addBtn = new Button(modelComposite, SWT.NONE);
		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						responseVarList.add(selectedNumVars[i]);
					}
					numVarList.remove(numVarList.getSelectionIndices());
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = responseVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						numVarList.add(selectedNumVars[i]);
					}
					responseVarList.remove(responseVarList.getSelectionIndices());
					resetHeatMap();
				}
				if(responseVarList.getItemCount()<2)estimateGenotypeBtn.setEnabled(false);estimateGenotypeBtn.setSelection(false);
//				else if(checkRandomBtn.getEnabled())estimateGenotypeBtn.setEnabled(true);
				enableNumericButtons(false);
			
//				activateRandomOptions((responseVarList.getItemCount() > 1) ? true : false);
				if (checkRandomBtn.getSelection())activateRandomOptions(true);
				if(designType.getSelectionIndex() == 1 || designType.getSelectionIndex() == 2){
					lblOptionsForGenotype_1.setEnabled(false);
					excludeControlsBtn.setEnabled(false);
					if(responseVarList.getItemCount()>1){
						lblOptionsForGenotype_1.setEnabled(true);
						estimateGenotypeBtn.setEnabled(true);
						
					}
				}
				moveBtn.setEnabled(false);
			}
		});
		addBtn.setEnabled(false);
		GridData gd_addBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_addBtn.widthHint = 52;
		addBtn.setLayoutData(gd_addBtn);
		addBtn.setText("Add");

		responseVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_responseVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_responseVarList.widthHint = 111;
		responseVarList.setLayoutData(gd_responseVarList);
		responseVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				moveBtn.setEnabled(false);
				dataManipulationManager.moveSelectedStringFromTo( responseVarList, numVarList);
				responseVarList.remove(responseVarList.getSelectionIndices()); 
//				activateRandomOptions((responseVarList.getItemCount() > 1) ? true : false);
				if(responseVarList.getItemCount()<2)estimateGenotypeBtn.setEnabled(false);estimateGenotypeBtn.setSelection(false);
				if(checkRandomBtn.getSelection())activateRandomOptions(true);
				if(designType.getSelectionIndex() == 1 || designType.getSelectionIndex() == 2){
					lblOptionsForGenotype_1.setEnabled(false);
					excludeControlsBtn.setEnabled(false);
					if(responseVarList.getItemCount()>1){
						lblOptionsForGenotype_1.setEnabled(true);
						estimateGenotypeBtn.setEnabled(true);
						
					}
				}
				moveBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(responseVarList.getSelectionCount()>0){
					numVarList.setSelection(-1);
					addBtn.setText("Remove");
					addBtn.setEnabled(true);
					moveBtn.setEnabled(false);
					enableFactorButtons(false);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Label lblFactors = new Label(modelComposite, SWT.NONE);
		lblFactors.setText("Factors:");

		moveBtn = new Button(modelComposite, SWT.NONE);
//		moveBtn.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e){
//				if(numVarList.getSelectionCount()>0){//if move Down
//					String selectedNumVars[] = numVarList.getSelection();
//					for(int i=0; i< selectedNumVars.length; i++){
//						factorVarList.add(selectedNumVars[i]);
//						dataManipulationManager.editVariableType(file.getAbsolutePath(),varInfo, selectedNumVars[i],"Factor");
//					}
//					numVarList.remove(numVarList.getSelectionIndices());
//					enableNumericButtons(false);
//				}
//				else{// move up
//					String selectedNumVars[] = factorVarList.getSelection();
//					for(int i=0; i< selectedNumVars.length; i++){
//						numVarList.add(selectedNumVars[i]);
//						dataManipulationManager.editVariableType(file.getAbsolutePath(),varInfo, selectedNumVars[i],"Numeric");
//					}
//					factorVarList.remove(factorVarList.getSelectionIndices());
//
//					enableFactorButtons(false);
//				}
//			}
//		});
		moveBtn.setEnabled(false);
		GridData gd_moveBtn = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_moveBtn.heightHint = 24;
		gd_moveBtn.widthHint = 90;
		moveBtn.setLayoutData(gd_moveBtn);
		moveBtn.setText("Set to Factor");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		factorVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		factorVarList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0){
					enableFactorButtons(true);
					enableNumericButtons(false);
					
				}
			}
		});
//		factorVarList.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(factorVarList.getSelectionIndex()>-1){
//				enableNumericButtons(false);
//				enableFactorButtons(false);
//				numVarList.setSelection(-1);
//				String[] s=factorVarList.getSelection();
//				String isNumeric = dataManipulationManager.isNumeric(file.getAbsolutePath().replaceAll("\\\\","/"), s[0]);
//				if(isNumeric.equals("TRUE")){
//					moveBtn.setText("Set to Numeric");
//					moveBtn.setEnabled(true);
//				}
//				else moveBtn.setEnabled(false);
//				enableFactorButtons(true);
//
//			}
//			}
//		});
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
		gd_factorVarList.widthHint = 65;
		factorVarList.setLayoutData(gd_factorVarList);
		factorVarList.setItems(factorVariables);
//		factorVarList.addListener(SWT.MouseDown, new Listener(){
//
//			@Override
//			public void handleEvent(Event event) {
//				if(factorVarList.getSelectionIndex()>-1){
//					enableNumericButtons(false);
//					enableFactorButtons(false);
//					numVarList.setSelection(-1);
//					String[] s=factorVarList.getSelection();
//					String isNumeric = dataManipulationManager.isNumeric(file.getAbsolutePath().replaceAll("\\\\","/"), s[0]);
//					if(isNumeric.equals("TRUE")){
//						moveBtn.setText("Set to Numeric");
//						moveBtn.setEnabled(true);
//					}
//					else moveBtn.setEnabled(false);
//					enableFactorButtons(true);
//
//				}
//			}
//		});

		Group group = new Group(modelComposite, SWT.NONE);
		group.setLayout(new GridLayout(3, false));
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_group.widthHint = 255;
		group.setLayoutData(gd_group);

		btnAddEnv = new Button(group, SWT.NONE);
		
		btnAddEnv.setText("Add");
		GridData gd_btnAddEnv = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddEnv.widthHint = 52;
		btnAddEnv.setLayoutData(gd_btnAddEnv);

		lblEnv = new Label(group, SWT.NONE);
		lblEnv.setText("Environment:");
		
		txtEnvVar = new Text(group, SWT.BORDER);
		txtEnvVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtEnvVar.setEditable(false);
		txtEnvVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		btnAddGen = new Button(group, SWT.NONE);
		
		btnAddGen.setText("Add");
		GridData gd_btnAddGen = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddGen.widthHint = 52;
		btnAddGen.setLayoutData(gd_btnAddGen);

		lblGen = new Label(group, SWT.NONE);
		lblGen.setText("Genotype:");
		
		txtGenVar = new Text(group, SWT.BORDER);
		txtGenVar.setEditable(false);
		txtGenVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtGenVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		txtGenVar.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// If an item is double-clicked from the replicate Variable List
				genLevelsList.removeAll();
				controlsList.removeAll();
				resetGenotypeControls();
				}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
						if(compareWithControlsBtn.getSelection() && checkPerformPairwiseBtn.getSelection()) activateLevelOfConrolsOptions(true);
				}
				else{//if unchecked
					activateFixedOptions(false);
					if(!excludeControlsBtn.getSelection() || !excludeControlsBtn.getEnabled()) activateLevelOfConrolsOptions(false);
					else if(designType.getSelectionIndex()==1 || designType.getSelectionIndex()==2) activateLevelOfConrolsOptions(false);

				}
			}
		});
		checkFixedBtn.setSelection(true);
		checkFixedBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		checkFixedBtn.setText("Fixed");

		checkRandomBtn = new Button(group, SWT.CHECK);
		checkRandomBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkRandomBtn.getSelection()){//if checked
					activateRandomOptions(true);
					activateLevelOfConrolsOptions(false);
					if(excludeControlsBtn.getSelection()) activateLevelOfConrolsOptions(true);
					if(designType.getSelectionIndex() == 1 || designType.getSelectionIndex() == 2){
						enableAugmentedOptions(true);
					}
				}
				else{//if unchecked
					activateRandomOptions(false);
					if((!compareWithControlsBtn.getSelection() || !compareWithControlsBtn.getEnabled())) activateLevelOfConrolsOptions(false);
					
				}
			}
		});
		checkRandomBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		checkRandomBtn.setText("Random");

		variableGroup = new Group(modelComposite, SWT.NONE);
		variableGroup.setLayout(new GridLayout(3, false));
		GridData gd_group_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_group_1.heightHint = 135;
		variableGroup.setLayoutData(gd_group_1);

		btnAddBlock = new Button(variableGroup, SWT.NONE);
		btnAddBlock.setEnabled(false);
		
		btnAddBlock.setText("Add");
		GridData gd_btnAddBlock = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddBlock.widthHint = 52;
		btnAddBlock.setLayoutData(gd_btnAddBlock);

		lblBlock = new Label(variableGroup, SWT.NONE);
		lblBlock.setText("Block:");
		
		txtBlockVar = new Text(variableGroup, SWT.BORDER);
		txtBlockVar.setEditable(false);
		txtBlockVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtBlockVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		btnAddRep = new Button(variableGroup, SWT.NONE);
		btnAddRep.setEnabled(false);
		
		btnAddRep.setText("Add");
		GridData gd_btnAddRep = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddRep.widthHint = 52;
		btnAddRep.setLayoutData(gd_btnAddRep);

		lblRep = new Label(variableGroup, SWT.NONE);
		lblRep.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRep.setEnabled(false);
		lblRep.setText("Replicate:");
		
		txtRepVar = new Text(variableGroup, SWT.BORDER);
		txtRepVar.setEditable(false);
		txtRepVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtRepVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		btnAddRow = new Button(variableGroup, SWT.NONE);
		btnAddRow.setEnabled(false);
		
		btnAddRow.setText("Add");
		GridData gd_btnAddRow = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddRow.widthHint = 52;
		btnAddRow.setLayoutData(gd_btnAddRow);

		lblRow = new Label(variableGroup, SWT.NONE);
		lblRow.setEnabled(false);
		lblRow.setText("Row:");
		
		txtRowVar = new Text(variableGroup, SWT.BORDER);
		txtRowVar.setEditable(false);
		txtRowVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtRowVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		btnAddCol = new Button(variableGroup, SWT.NONE);
		btnAddCol.setEnabled(false);
		
		btnAddCol.setText("Add");
		GridData gd_btnAddCol = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddCol.widthHint = 52;
		btnAddCol.setLayoutData(gd_btnAddCol);

		lblCol = new Label(variableGroup, SWT.NONE);
		lblCol.setEnabled(false);
		lblCol.setText("Column:");
		
		txtColVar = new Text(variableGroup, SWT.BORDER);
		txtColVar.setEditable(false);
		txtColVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtColVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		tbtmOptions = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions.setText("Options");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions.setControl(composite_1);
		composite_1.setBackground(getShell().getBackground());
		composite_1.setLayout(new GridLayout(6, false));

		lblOptionsForGenotype = new Label(composite_1, SWT.NONE);
		GridData gd_lblOptionsForGenotype = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_lblOptionsForGenotype.heightHint = 21;
		gd_lblOptionsForGenotype.widthHint = 223;
		lblOptionsForGenotype.setLayoutData(gd_lblOptionsForGenotype);
		lblOptionsForGenotype.setText("Options for Genotype as Fixed:");
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
					if(!excludeControlsBtn.getSelection() || !excludeControlsBtn.getEnabled()) activateLevelOfConrolsOptions(false);
					else if(designType.getSelectionIndex() == 1 || designType.getSelectionIndex()==2) activateLevelOfConrolsOptions(false);
				}
			}
		});
		checkPerformPairwiseBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
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
					if(!excludeControlsBtn.getSelection() || !excludeControlsBtn.getEnabled()) activateLevelOfConrolsOptions(false);
					else if(designType.getSelectionIndex()==1 || designType.getSelectionIndex()==2)activateLevelOfConrolsOptions(false);
				}
			}
		});
		compareWithControlsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));
		compareWithControlsBtn.setText("Compare with control(s)");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		performAllComparisonsBtn = new Button(composite_1, SWT.RADIO);
		performAllComparisonsBtn.setEnabled(false);
		performAllComparisonsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));
		performAllComparisonsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(performAllComparisonsBtn.getSelection()){//if selected
					if(!excludeControlsBtn.getSelection() || !excludeControlsBtn.getEnabled()) activateLevelOfConrolsOptions(false);
				}
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

		lblOptionsForGenotype_1 = new Label(composite_1, SWT.NONE);
		lblOptionsForGenotype_1.setEnabled(false);
		lblOptionsForGenotype_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		lblOptionsForGenotype_1.setText("Options for Genotype as Random:");
		new Label(composite_1, SWT.NONE);

		excludeControlsBtn = new Button(composite_1, SWT.CHECK);
		excludeControlsBtn.setEnabled(false);
		excludeControlsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(excludeControlsBtn.getSelection()){//if selected
					if(!(designType.getSelectionIndex() == 1 || designType.getSelectionIndex()==2))activateLevelOfConrolsOptions(true);
				}
				else{
					if(checkPerformPairwiseBtn.getSelection() && checkPerformPairwiseBtn.getEnabled()){
						if((!compareWithControlsBtn.getSelection() || !compareWithControlsBtn.getEnabled())) activateLevelOfConrolsOptions(false);
					}
					else{
						activateLevelOfConrolsOptions(false);
					}
				}
			}
		});
		excludeControlsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		excludeControlsBtn.setText("Exclude controls in the estimation of genotypic variance");
		new Label(composite_1, SWT.NONE);

		estimateGenotypeBtn = new Button(composite_1, SWT.CHECK);
		estimateGenotypeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		estimateGenotypeBtn.setEnabled(false);
		estimateGenotypeBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		estimateGenotypeBtn.setText("Estimate genotypic and phenotypic correlations");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		lblSpecifyControls = new Label(composite_1, SWT.NONE);
		lblSpecifyControls.setEnabled(false);
		lblSpecifyControls.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		lblSpecifyControls.setText("Specify Control(s)");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		levelsOfGenotypeLbl = new Label(composite_1, SWT.NONE);
		levelsOfGenotypeLbl.setEnabled(false);
		levelsOfGenotypeLbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		levelsOfGenotypeLbl.setText("Levels of Genotype:");

		controlsLbl = new Label(composite_1, SWT.NONE);
		controlsLbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		controlsLbl.setEnabled(false);
		controlsLbl.setText("Control(s):");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
				
						genLevelsList = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
						genLevelsList.setEnabled(false);
						GridData gd_genLevelsList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
						gd_genLevelsList.widthHint = 128;
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
						GridData gd_addControlsBtn = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
						gd_addControlsBtn.widthHint = 71;
						addControlsBtn.setLayoutData(gd_addControlsBtn);
						addControlsBtn.setText("Add");
		
				controlsList = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
				controlsList.setEnabled(false);
				GridData gd_controlsList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
				gd_controlsList.widthHint = 128;
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

		Label lblNewLabel_3 = new Label(composite_1, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		lblNewLabel_3.setText("Display:");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		checkDescriptiveStatistics = new Button(composite_1, SWT.CHECK);
		checkDescriptiveStatistics.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));
		checkDescriptiveStatistics.setText("Descriptive Statistics");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		checkVarianceComponents = new Button(composite_1, SWT.CHECK);
		checkVarianceComponents.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));
		checkVarianceComponents.setText("Variance Components");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		btnMoransTest = new Button(composite_1, SWT.CHECK);
		btnMoransTest.setEnabled(false);
		btnMoransTest.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		btnMoransTest.setText("Moran's Test");
		
		lblSpatialStructure = new Label(composite_1, SWT.NONE);
		lblSpatialStructure.setEnabled(false);
		lblSpatialStructure.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		lblSpatialStructure.setText("Spatial Structure:");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		btnCompoundSymmetry = new Button(composite_1, SWT.CHECK);
		btnCompoundSymmetry.setEnabled(false);
		btnCompoundSymmetry.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCompoundSymmetry.setText("Compound Symmetry");
		new Label(composite_1, SWT.NONE);
		
		btnExponential = new Button(composite_1, SWT.CHECK);
		btnExponential.setEnabled(false);
		btnExponential.setText("Exponential");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		btnGaussian = new Button(composite_1, SWT.CHECK);
		btnGaussian.setEnabled(false);
		btnGaussian.setText("Gaussian");
		new Label(composite_1, SWT.NONE);
		
		btnSpherical = new Button(composite_1, SWT.CHECK);
		btnSpherical.setEnabled(false);
		btnSpherical.setText("Spherical");
		new Label(composite_1, SWT.NONE);


		TabItem tbtmGraph_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmGraph_1.setText("Graph");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmGraph_1.setControl(composite);
		composite.setBackground(getShell().getBackground());
		composite.setLayout(new GridLayout(3, false));

		Label lblOutputGraph = new Label(composite, SWT.NONE);
		lblOutputGraph.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblOutputGraph.setText("Output Graph:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		checkBoxPlotBtn = new Button(composite, SWT.CHECK);
		checkBoxPlotBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2));
		checkBoxPlotBtn.setText("Boxplot of the raw data");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		checkHistogramBtn = new Button(composite, SWT.CHECK);
		GridData gd_checkHistogramBtn = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_checkHistogramBtn.widthHint = 156;
		checkHistogramBtn.setLayoutData(gd_checkHistogramBtn);
		checkHistogramBtn.setText("Histogram of the raw data");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		checkHeatmapBtn = new Button(composite, SWT.CHECK);
		checkHeatmapBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!checkHeatmapBtn.getSelection()){//if unchecked
					specifyHeatMapVarsBtn.setEnabled(false);
					resetHeatMap();
				}
				else{
					specifyHeatMapVarsBtn.setEnabled(true);
				}
			}
		});
		checkHeatmapBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2));
		checkHeatmapBtn.setText("Heatmap of the residuals");

		specifyHeatMapVarsBtn = new Button(composite, SWT.NONE);
		specifyHeatMapVarsBtn.setEnabled(false);
		specifyHeatMapVarsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SpecifyRowColDialog rowColDialog = new SpecifyRowColDialog(getShell(), dataManipulationManager.getListItems(varInfo));
				rowColDialog.open();
				if(rowColDialog.getReturnCode()==0){
					heatmapRow = rowColDialog.getHeatRow();
					heatmapColumn = rowColDialog.getHeatColumn();
					selectedHeatMapVars=true;
				}
				else selectedHeatMapVars=false;
			}
		});
		GridData gd_specifyHeatMapVarsBtn = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2);
		gd_specifyHeatMapVarsBtn.widthHint = 157;
		specifyHeatMapVarsBtn.setLayoutData(gd_specifyHeatMapVarsBtn);
		specifyHeatMapVarsBtn.setText("Specify Field Row/Column");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		checkDiagnosticPlotsBtn = new Button(composite, SWT.CHECK);
		checkDiagnosticPlotsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		checkDiagnosticPlotsBtn.setText("Diagnostic plots");
		new Label(composite, SWT.NONE);

		initializeForm();
		return container;

	}
	protected void enableAugmentedOptions(boolean state) {
		// TODO Auto-generated method stub
		if(checkRandomBtn.getSelection()){
			lblOptionsForGenotype_1.setEnabled(true);
			excludeControlsBtn.setEnabled(true);
			excludeControlsBtn.setSelection(true);
		
			if(responseVarList.getItemCount()>1)estimateGenotypeBtn.setEnabled(true);
			activateLevelOfConrolsOptions(!state);
			if(!state){
				excludeControlsBtn.setSelection(false);
				activateLevelOfConrolsOptions(false);
			}
		}

	}

	private void enablePrepOptions(boolean state) {
		// TODO Auto-generated method stub
		btnMoransTest.setEnabled(state);
		lblSpatialStructure.setEnabled(state);
		btnCompoundSymmetry.setEnabled(state);
		btnGaussian.setEnabled(state);
		btnExponential.setEnabled(state);
		btnSpherical.setEnabled(state);
	}
	
	void initializeForm(){
		dlgManager.initializeVariableMoveList(numVarList, factorVarList, moveBtn, file.getAbsolutePath());
		
		dlgManager.initializeSingleSelectionList(factorVarList, txtEnvVar, moveBtn, btnAddEnv, new EnvVarValidator(factorVarList,  false,  file,  txtEnvVar, lblEnv));
		dlgManager.initializeSingleSelectionList(factorVarList, txtGenVar, moveBtn,btnAddGen, new genotypeVarValidator());
		dlgManager.initializeSingleSelectionList(factorVarList, txtBlockVar, moveBtn,btnAddBlock, new TextVarValidator(txtBlockVar, lblBlock));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRepVar, moveBtn,btnAddRep, new TextVarValidator(txtRepVar, lblRep));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRowVar, moveBtn,btnAddRow, new TextVarValidator(txtRowVar, lblRow));
		dlgManager.initializeSingleSelectionList(factorVarList, txtColVar, moveBtn,btnAddCol, new TextVarValidator(txtColVar, lblCol));
		dlgManager.initializeSingleButtonList(numVarList, responseVarList,moveBtn, addBtn);
	}
	protected void resetGenotypeControls() {
		// TODO Auto-generated method stub
		genLevelsList.removeAll();
		controlsList.removeAll();
	}

	protected void resetHeatMap() {
		// TODO Auto-generated method stub
		heatmapRow = null;
		heatmapColumn = null;
		selectedHeatMapVars=false;
	}

	protected void disable(String string) {
		// TODO Auto-generated method stub
		if(string.equals("replicate")){
			lblRep.setEnabled(false);
			if(!txtRepVar.getText().isEmpty())factorVarList.add(txtRepVar.getText());
			txtRepVar.setText("");
			btnAddRep.setEnabled(false);
		}
		else if(string.equals("block")){
			lblBlock.setEnabled(false);
			if(!txtBlockVar.getText().isEmpty())factorVarList.add(txtBlockVar.getText());
			txtBlockVar.setText("");
			btnAddBlock.setEnabled(false);
		}
		else if(string.equals("row")){
			lblRow.setEnabled(false);
			if(!txtRowVar.getText().isEmpty())factorVarList.add(txtRowVar.getText());
			txtRowVar.setText("");
			btnAddRow.setEnabled(false);
		}
		else if(string.equals("column")){
			lblCol.setEnabled(false);
			if(!txtColVar.getText().isEmpty())factorVarList.add(txtColVar.getText());
			txtColVar.setText("");
			btnAddCol.setEnabled(false);
		}
	}

	protected void activateFixedOptions(boolean state) {
		// TODO Auto-generated method stub
		lblOptionsForGenotype.setEnabled(state);
		checkPerformPairwiseBtn.setEnabled(state);
		activatePerformPairwiseOptions(state);
//		if(state && checkPerformPairwiseBtn.getSelection())activatePerformPairwiseOptions(state);
//		if(state) checkPerformPairwiseBtn.setSelection(state);
//		else 
		if(!state && !excludeControlsBtn.getSelection()) activateLevelOfConrolsOptions(false);
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
				//			performAllComparisonsBtn.setSelection(false);
			}
			else{
				performAllComparisonsBtn.setEnabled(true);
				if(performAllComparisonsBtn.getSelection()) compareWithControlsBtn.setSelection(false);
			}
		}
	}

	protected void activateRandomOptions(boolean state) {
		// TODO Auto-generated method stub
		lblOptionsForGenotype_1.setEnabled(state);
		excludeControlsBtn.setEnabled(state);
		if(responseVarList.getItemCount()>1)estimateGenotypeBtn.setEnabled(state);
	}
//	protected void augmentedOptions(boolean state){
//		// TODO Auto-generated method stub
//		lblOptionsForGenotype_1.setEnabled(state);
//		excludeControlsBtn.setEnabled(state);
//		estimateGenotypeBtn.setEnabled(state);
//	}
	

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

	public void enableFactorButtons(boolean state){
		//un-select factor fields
		txtRepVar.setSelection(-1);
		txtBlockVar.setSelection(-1);
		txtEnvVar.setSelection(-1);
		txtGenVar.setSelection(-1);
		txtRowVar.setSelection(-1);
		txtColVar.setSelection(-1);

		if(state){//if enable all buttons
			//change button text
			btnAddBlock.setText("Add");
			btnAddRep.setText("Add");
			btnAddGen.setText("Add");
			btnAddRow.setText("Add");
			btnAddCol.setText("Add");
			btnAddEnv.setText("Add");

			//enable empty fields
			if(txtEnvVar.getText().isEmpty()) btnAddEnv.setEnabled(state);
			if(txtBlockVar.getText().isEmpty() && lblBlock.getEnabled()) btnAddBlock.setEnabled(state);
			if(txtGenVar.getText().isEmpty()) btnAddGen.setEnabled(state);
			if(txtRepVar.getText().isEmpty() && lblRep.getEnabled()) btnAddRep.setEnabled(state);
			if(txtRowVar.getText().isEmpty() && lblRow.getEnabled()) btnAddRow.setEnabled(state);
			if(txtColVar.getText().isEmpty() && lblCol.getEnabled()) btnAddCol.setEnabled(state);
		}
		else{
			//disable all buttons
			btnAddBlock.setEnabled(state);
			btnAddRep.setEnabled(state);
			btnAddGen.setEnabled(state);
			btnAddRow.setEnabled(state);
			btnAddCol.setEnabled(state);
			btnAddEnv.setEnabled(state);
		}
	}

	public void enableNumericButtons(boolean state){
		addBtn.setEnabled(state);
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
		genotypeFixed = checkFixedBtn.getSelection();
		genotypeRandom = checkRandomBtn.getSelection();
		design = designType.getSelectionIndex();
		environment="NULL";
		descriptiveStat = checkDescriptiveStatistics.getSelection(); 
		varianceComponents = checkVarianceComponents.getSelection();
		boxplotRawData = checkBoxPlotBtn.getSelection();
		histogramRawData = checkHistogramBtn.getSelection();
		heatmapResiduals = checkHeatmapBtn.getSelection();
		diagnosticPlot = checkDiagnosticPlotsBtn.getSelection();
		satisfiedAllConditions=true;
		if(responseVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please add a response variable.");
		}
		else if(txtGenVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a genotype factor.");
		}
		else if(!genotypeFixed && !genotypeRandom){
			MessageDialog.openError(getShell(), "Error", "Please specify whether the genotype variable is fixed or random.");
		}
		else if(lblBlock.getEnabled() && txtBlockVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a block factor.");
		}
		else if(lblRep.getEnabled() && txtRepVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a replicate factor.");
		}
		else if(lblRow.getEnabled() && txtRowVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a row factor.");
		}
		else if(lblCol.getEnabled() && txtColVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a column factor.");
		}
		else if(heatmapResiduals && !selectedHeatMapVars){
			MessageDialog.openError(getShell(), "Error", "Please specify the heatmap rows/colums.");
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
							if(compareControl){
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

			if(genotypeRandom){//if random is checked
				excludeControls = excludeControlsBtn.getSelection();
				genoPhenoCorrelation = estimateGenotypeBtn.getSelection();
				if(excludeControls && !(compareWithControlsBtn.getSelection() && compareWithControlsBtn.getEnabled())){
					controlLevels = controlsList.getItems();
					if(controlLevels.length<1 && !(designType.getSelectionIndex() == 1 || designType.getSelectionIndex() == 2 )){
						MessageDialog.openError(getShell(), "Error", "Please specify control variables from the genotype levels.");
						satisfiedAllConditions=false;
					}
				}
			}

			if(satisfiedAllConditions){
				if(lblBlock.getEnabled())block = txtBlockVar.getText();
				if(lblRep.getEnabled())rep = txtRepVar.getText();
				if(lblRow.getEnabled())row = txtRowVar.getText();
				if(lblCol.getEnabled())column = txtColVar.getText();
				moransTest = btnMoransTest.getSelection();
			
				if( !btnCompoundSymmetry.getSelection() && !btnGaussian.getSelection() && !btnExponential.getSelection() && !btnSpherical.getSelection()){
					spatialStrucList.add("none");
				}else{ //, "CompSymm", "Gaus", "Exp", "Spher"
					if(btnCompoundSymmetry.getSelection())spatialStrucList.add("CompSymm");
					if(btnGaussian.getSelection())spatialStrucList.add("Gaus");
					if(btnExponential.getSelection())spatialStrucList.add("Exp");
					if(btnSpherical.getSelection())spatialStrucList.add("Spher");
				}
				
				spatialStruc = spatialStrucList.toArray(new String[spatialStrucList.size()]);
				respvars = responseVarList.getItems();
				if(txtEnvVar.getText().isEmpty())environment = "NULL";
				else environment = txtEnvVar.getText();
				genotype = txtGenVar.getText();
				String selectedStrings = txtEnvVar.getText();
				environmentLevels = DataManipulationManager.getEnvtLevels(selectedStrings, file);
				String selectedStrings2 = txtGenVar.getText();
				genotypeLevels = DataManipulationManager.getEnvtLevels(selectedStrings2, file);
				String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");

				File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);
				String newOutputFileName = outputFolder.getAbsolutePath()+"/output.txt";

				OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Single Environment Analysis");
				rInfo.open();
//				rInfo.showProgressBar();
				if(designType.getSelectionIndex()==9){
					ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doSingleEnvironmentAnalysisPRep(dataFileName,
							newOutputFileName.replaceAll("\\\\+", "/"),
							respvars,
							selectedStrings,
							selectedStrings2,
							dataFileName, newOutputFileName, genotypeFixed, genotypeRandom, excludeControls, controlLevels, moransTest, spatialStruc, descriptiveStat, varianceComponents, boxplotRawData, histogramRawData, heatmapResiduals, diagnosticPlot);
				}
				else{
					ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doSingleEnvironmentAnalysis(
						dataFileName, newOutputFileName.replaceAll("\\\\+", "/"), outputFolder.getAbsolutePath().replaceAll("\\\\+", "/")+"/", design, respvars, environment, environmentLevels,
						genotype, block, rep, row, column, descriptiveStat, varianceComponents, boxplotRawData, histogramRawData, heatmapResiduals, heatmapRow, 
						heatmapColumn, diagnosticPlot, genotypeFixed, performPairwise, pairwiseAlpha, genotypeLevels, controlLevels, compareControl, performAllPairwise,
						genotypeRandom, excludeControls, genoPhenoCorrelation);
				}
				rInfo.close();
				WindowDialogControlUtil.hideAllWindowDialog();
				PBToolAnalysisUtilities.openFolder(outputFolder);
			}
		}
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(568, 711);
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
	public class genotypeVarValidator extends DialogListValidator{

		public genotypeVarValidator() {
			super();
			
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public boolean validate(){
			return true;
		}
		@Override
		public boolean performAddProcess(){
			String selectedStrings[] = factorVarList.getSelection();
			genotypeLevels = DataManipulationManager.getEnvtLevels(selectedStrings[0], file);
			genLevelsList.setItems(genotypeLevels);
			if(checkFixedBtn.getSelection()&&checkPerformPairwiseBtn.getSelection())activatePerformPairwiseOptions(true);
			return true;
		}
		
		@Override
		public boolean performRemoveProcess(){
			genLevelsList.removeAll();
			controlsList.removeAll();
			resetGenotypeControls();
			return true;
		}
	}
}