package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.SpecifyRowColDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.irri.breedingtool.utility.WindowDialogControlUtil;

public class IncompleteBlockDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private Combo designType;
	private List numVarList;
	private List factorVarList;
	private Button addEnvBtn;
	private Button addBlockBtn;
	private Button addRepBtn;
	private Button addRowBtn;
	private Button addColBtn;
	private List responseVarList;
	private Button addBtn;
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
	private List blockVarList;
	private List replicateVarList;
	private List rowVarList;
	private List columnVarList;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private Button addGenBtn;
	private TabItem tbtmOptions;
	private Label lblLevelOfSignificance;
	private Label lblSpecifyControls;
	private Label levelsOfGenotypeLbl;
	private Label controlsLbl;
	private Label lblBlock;
	private Label lblReplicate;
	private Group variableGroup;
	private Label lblColumn;
	private Label lblRow;

	//specify parameters
	private int design = 0;
	private String[] respvars = {"Y1", "Y2"};
	private String environment = "Site";
	private String[] environmentLevels = {"Env1", "Env2"};
	private String genotype = "Gen";
	private String block = "Blk";
	private String rep = "NULL";
	private String row = "NULL";
	private String column = "NULL";
	private boolean descriptiveStat = true; 
	private boolean varianceComponents = true;
	private boolean boxplotRawData = true;
	private boolean histogramRawData = true;
	private boolean heatmapResiduals = true;
	private String heatmapRow = null;
	private String heatmapColumn = null;
	private boolean diagnosticPlot = true;
	private boolean genotypeFixed = true;
	private String performPairwise;
	private String pairwiseAlpha = "0.05";
	private String[] genotypeLevels = {"1", "2", "3", "4", "5", "6", "7", "8"};
	private String[] controlLevels = {"1", "2", "3"};
	private boolean compareControl = true;
	private boolean performAllPairwise = false;
	private boolean genotypeRandom = false;
	private boolean excludeControls = true;
	private String[] controlLevels2 = {"1", "2", "3"};
	private boolean genoPhenoCorrelation = false;
	private Button checkBoxPlotBtn;
	private Button checkHistogramBtn;
	private Button checkHeatmapBtn;
	private Button checkDiagnosticPlotsBtn;
	private boolean satisfiedAllConditions = true;
	private boolean selectedHeatMapVars=false;
	private String analysisType;
	private Button specifyHeatMapVarsBtn;
	private String filePath = PartStackHandler.getActiveElementID();
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public IncompleteBlockDialog(Shell parentShell, String analysisType, File file) {
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

		parent.getShell().setText("Incomplete Block Analysis: "+file.getName());
		Composite container = (Composite) super.createDialogArea(parent);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
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
		designType.setItems(new String[] {"Balanced Incomplete Block Design", "Augmented RCB", "Augmented Latin Square", "Alpha-Lattice", "Row-Column"});
		designType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		designType.select(0);
		designType.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableFactorButtons(false);
				if(designType.getSelectionIndex()==0){//RCB
					lblBlock.setEnabled(true);
					disable("replicate");
					disable("row");
					disable("column");
				}
				else if(designType.getSelectionIndex()==1){//AugmentedRCB
					lblBlock.setEnabled(true);
					disable("replicate");
					disable("row");
					disable("column");
				}
				else if(designType.getSelectionIndex()==2){//AugmentedLatin square
					lblRow.setEnabled(true);
					lblColumn.setEnabled(true);
//					lblReplicate.setEnabled(true);
					disable("replicate");
					disable("block");

				}
				else if(designType.getSelectionIndex()==3){//Alpha lattice
					lblReplicate.setEnabled(true);
					lblBlock.setEnabled(true);
					disable("row");
					disable("column");
				}
				else if(designType.getSelectionIndex()==4){//Row Column
					lblRow.setEnabled(true);
					lblColumn.setEnabled(true);
					lblReplicate.setEnabled(true);
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
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(numVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					responseVarList.setSelection(-1);
					addBtn.setText("Add");
					moveBtn.setText("Set to Factor");
					enableNumericButtons(true);
					enableFactorButtons(false);
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
			
				enableNumericButtons(false);
			}
		});
		addBtn.setEnabled(false);
		GridData gd_addBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_addBtn.widthHint = 52;
		addBtn.setLayoutData(gd_addBtn);
		addBtn.setText("Add");

		responseVarList = new List(modelComposite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_responseVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_responseVarList.widthHint = 111;
		responseVarList.setLayoutData(gd_responseVarList);
		responseVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( responseVarList, numVarList);
				responseVarList.remove(responseVarList.getSelectionIndices());
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

					enableFactorButtons(false);
				}
			}
		});
		moveBtn.setEnabled(false);
		GridData gd_moveBtn = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_moveBtn.heightHint = 24;
		gd_moveBtn.widthHint = 80;
		moveBtn.setLayoutData(gd_moveBtn);
		moveBtn.setText("Set to Factor");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		factorVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
		gd_factorVarList.widthHint = 65;
		factorVarList.setLayoutData(gd_factorVarList);
		factorVarList.setItems(factorVariables);
		factorVarList.addListener(SWT.MouseDown, new Listener(){

			@Override
			public void handleEvent(Event event) {
				if(factorVarList.getSelectionIndex()>-1){
					enableNumericButtons(false);
					enableFactorButtons(false);
					numVarList.setSelection(-1);
					String[] s=factorVarList.getSelection();
					String isNumeric = dataManipulationManager.isNumeric(file.getAbsolutePath().replaceAll(File.separator,"/"), s[0]);
					if(isNumeric.equals("TRUE")){
						moveBtn.setText("Set to Numeric");
						moveBtn.setEnabled(true);
					}
					else moveBtn.setEnabled(false);
					enableFactorButtons(true);

				}
			}
		});

		Group group = new Group(modelComposite, SWT.NONE);
		group.setLayout(new GridLayout(3, false));
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_group.widthHint = 255;
		group.setLayoutData(gd_group);

		addEnvBtn = new Button(group, SWT.NONE);
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
				enableFactorButtons(false);
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
		addGenBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedStrings[] = factorVarList.getSelection();
					genotypeLevels = DataManipulationManager.getEnvtLevels(selectedStrings[0], file);
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, genVarList);
					genLevelsList.setItems(genotypeLevels);
					activatePerformPairwiseOptions(checkPerformPairwiseBtn.getSelection());
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( genVarList, factorVarList);
					genLevelsList.removeAll();
					controlsList.removeAll();
					resetGenotypeControls();
				}

				enableFactorButtons(false);
			}
		});
		addGenBtn.setText("Add");
		GridData gd_addGenBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addGenBtn.widthHint = 52;
		addGenBtn.setLayoutData(gd_addGenBtn);

		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setText("Factor:");

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

		variableGroup = new Group(modelComposite, SWT.NONE);
		variableGroup.setLayout(new GridLayout(3, false));
		GridData gd_group_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_group_1.heightHint = 135;
		variableGroup.setLayoutData(gd_group_1);

		addBlockBtn = new Button(variableGroup, SWT.NONE);
		addBlockBtn.setEnabled(false);
		addBlockBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, blockVarList);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( blockVarList, factorVarList);
				}
				enableFactorButtons(false);
			}
		});
		addBlockBtn.setText("Add");
		GridData gd_addBlockBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addBlockBtn.widthHint = 52;
		addBlockBtn.setLayoutData(gd_addBlockBtn);


		lblBlock = new Label(variableGroup, SWT.NONE);
		lblBlock.setText("Block:");

		blockVarList = new List(variableGroup, SWT.BORDER);
		GridData gd_blockVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_blockVarList.heightHint = 25;
		blockVarList.setLayoutData(gd_blockVarList);
		blockVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( blockVarList, factorVarList);
				addBlockBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(blockVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addBlockBtn.setText("Remove");
					addBlockBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addRepBtn = new Button(variableGroup, SWT.NONE);
		addRepBtn.setEnabled(false);
		addRepBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, replicateVarList);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( replicateVarList, factorVarList);
				}
				enableFactorButtons(false);
			}
		});
		addRepBtn.setText("Add");
		GridData gd_addRepBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addRepBtn.widthHint = 52;
		addRepBtn.setLayoutData(gd_addRepBtn);

		lblReplicate = new Label(variableGroup, SWT.NONE);
		lblReplicate.setEnabled(false);
		lblReplicate.setText("Replicate:");

		replicateVarList = new List(variableGroup, SWT.BORDER);
		GridData gd_replicateVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_replicateVarList.heightHint = 25;
		replicateVarList.setLayoutData(gd_replicateVarList);
		replicateVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// If an item is double-clicked from the replicate Variable List
				dataManipulationManager.moveSelectedStringFromTo( replicateVarList, factorVarList);
				addRepBtn.setEnabled(false);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// If an item is selected from the replicate Variable List
				if(replicateVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addRepBtn.setText("Remove");
					addRepBtn.setEnabled(true);
				}
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		addRowBtn = new Button(variableGroup, SWT.NONE);
		addRowBtn.setEnabled(false);
		addRowBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, rowVarList);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( rowVarList, factorVarList);
				}

				enableFactorButtons(false);
			}
		});
		addRowBtn.setText("Add");
		GridData gd_addRowBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addRowBtn.widthHint = 52;
		addRowBtn.setLayoutData(gd_addRowBtn);

		lblRow = new Label(variableGroup, SWT.NONE);
		lblRow.setEnabled(false);
		lblRow.setText("Row:");

		rowVarList = new List(variableGroup, SWT.BORDER);
		GridData gd_rowVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_rowVarList.heightHint = 25;
		rowVarList.setLayoutData(gd_rowVarList);
		rowVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( rowVarList, factorVarList);
				addRowBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(rowVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addRowBtn.setText("Remove");
					addRowBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		addColBtn = new Button(variableGroup, SWT.NONE);
		addColBtn.setEnabled(false);
		addColBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, columnVarList);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( columnVarList, factorVarList);
				}
				enableFactorButtons(false);
			}
		});
		addColBtn.setText("Add");
		GridData gd_addColBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addColBtn.widthHint = 52;
		addColBtn.setLayoutData(gd_addColBtn);

		lblColumn = new Label(variableGroup, SWT.NONE);
		lblColumn.setEnabled(false);
		lblColumn.setText("Column:");

		columnVarList = new List(variableGroup, SWT.BORDER);
		GridData gd_columnVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_columnVarList.heightHint = 25;
		columnVarList.setLayoutData(gd_columnVarList);
		columnVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( columnVarList, factorVarList);
				addColBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(columnVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addColBtn.setText("Remove");
					addColBtn.setEnabled(true);
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
		composite_1.setLayout(new GridLayout(9, false));
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
					
				}
			}
		});
		checkPerformPairwiseBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 8, 1));
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
					
				}
			}
		});
		compareWithControlsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		compareWithControlsBtn.setText("Compare with control(s)");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		performAllComparisonsBtn = new Button(composite_1, SWT.RADIO);
		performAllComparisonsBtn.setEnabled(false);
		performAllComparisonsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		performAllComparisonsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateLevelOfConrolsOptions(false);
			}
		});
		performAllComparisonsBtn.setText("Perform all comparisons");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		lblLevelOfSignificance = new Label(composite_1, SWT.NONE);
		lblLevelOfSignificance.setEnabled(false);
		lblLevelOfSignificance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));
		lblLevelOfSignificance.setText("Level of Significance:");

		significanceLevel = new Text(composite_1, SWT.BORDER);
		significanceLevel.setEnabled(false);
		significanceLevel.setText("0.05");
		GridData gd_significanceLevel = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
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
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
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
		lblSpecifyControls.setEnabled(false);
		lblSpecifyControls.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 9, 1));
		lblSpecifyControls.setText("Specify Control(s)");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		levelsOfGenotypeLbl = new Label(composite_1, SWT.NONE);
		levelsOfGenotypeLbl.setEnabled(false);
		levelsOfGenotypeLbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		levelsOfGenotypeLbl.setText("Levels of Genotype:");

		controlsLbl = new Label(composite_1, SWT.NONE);
		controlsLbl.setEnabled(false);
		controlsLbl.setText("Control(s):");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		genLevelsList = new List(composite_1, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		genLevelsList.setEnabled(false);
		GridData gd_genLevelsList = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 3);
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
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		controlsList = new List(composite_1, SWT.BORDER | SWT.MULTI);
		controlsList.setEnabled(false);
		GridData gd_controlsList = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 3);
		gd_controlsList.widthHint = 125;
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
		GridData gd_addControlsBtn = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_addControlsBtn.widthHint = 71;
		addControlsBtn.setLayoutData(gd_addControlsBtn);
		addControlsBtn.setText("Add");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label lblNewLabel_3 = new Label(composite_1, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 9, 1));
		lblNewLabel_3.setText("Display:");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		checkDescriptiveStatistics = new Button(composite_1, SWT.CHECK);
		checkDescriptiveStatistics.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		checkDescriptiveStatistics.setText("Descriptive Statistics");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		checkVarianceComponents = new Button(composite_1, SWT.CHECK);
		checkVarianceComponents.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		checkVarianceComponents.setText("Variance Components");


		TabItem tbtmGraph_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmGraph_1.setText("Graph");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmGraph_1.setControl(composite);
		composite.setBackground(getShell().getBackground());
		composite.setLayout(new GridLayout(2, false));

		checkBoxPlotBtn = new Button(composite, SWT.CHECK);
		checkBoxPlotBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2));
		checkBoxPlotBtn.setText("Boxplot of the raw data");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		checkHistogramBtn = new Button(composite, SWT.CHECK);
		GridData gd_checkHistogramBtn = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_checkHistogramBtn.widthHint = 156;
		checkHistogramBtn.setLayoutData(gd_checkHistogramBtn);
		checkHistogramBtn.setText("Histogram of the raw data");
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
		specifyHeatMapVarsBtn.setText("Specify Row/Column");

		checkDiagnosticPlotsBtn = new Button(composite, SWT.CHECK);
		checkDiagnosticPlotsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		checkDiagnosticPlotsBtn.setText("Diagnostic plots");
		new Label(composite, SWT.NONE);

		return container;

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
			lblReplicate.setEnabled(false);
			if(replicateVarList.getItemCount()>0) factorVarList.add(replicateVarList.getItem(0));
			replicateVarList.removeAll();
			addRepBtn.setEnabled(false);
		}
		else if(string.equals("block")){
			lblBlock.setEnabled(false);
			if(blockVarList.getItemCount()>0) factorVarList.add(blockVarList.getItem(0));
			blockVarList.removeAll();
			addBlockBtn.setEnabled(false);
		}
		else if(string.equals("row")){
			lblRow.setEnabled(false);
			if(rowVarList.getItemCount()>0) factorVarList.add(rowVarList.getItem(0));
			rowVarList.removeAll();
			addRowBtn.setEnabled(false);
		}
		else if(string.equals("column")){
			lblColumn.setEnabled(false);
			if(columnVarList.getItemCount()>0) factorVarList.add(columnVarList.getItem(0));
			columnVarList.removeAll();
			addColBtn.setEnabled(false);
		}
	}

	protected void activateFixedOptions(boolean state) {
		// TODO Auto-generated method stub
	
		checkPerformPairwiseBtn.setEnabled(state);
		activatePerformPairwiseOptions(state);
		if(state) checkPerformPairwiseBtn.setSelection(state);
	
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
			}
		}
	}

	protected void activateRandomOptions(boolean state) {
		// TODO Auto-generated method stub
	
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

	public void enableFactorButtons(boolean state){
		//un-select factor fields
		replicateVarList.setSelection(-1);
		blockVarList.setSelection(-1);
		envVarList.setSelection(-1);
		genVarList.setSelection(-1);
		rowVarList.setSelection(-1);
		columnVarList.setSelection(-1);

		if(state){//if enable all buttons
			//change button text
			addBlockBtn.setText("Add");
			addRepBtn.setText("Add");
			addGenBtn.setText("Add");
			addRowBtn.setText("Add");
			addColBtn.setText("Add");
			addEnvBtn.setText("Add");

			//enable empty fields
			if(envVarList.getItemCount()<1) addEnvBtn.setEnabled(state);
			if(blockVarList.getItemCount()<1 && lblBlock.getEnabled()) addBlockBtn.setEnabled(state);
			if(genVarList.getItemCount()<1) addGenBtn.setEnabled(state);
			if(replicateVarList.getItemCount()<1 && lblReplicate.getEnabled()) addRepBtn.setEnabled(state);
			if(rowVarList.getItemCount()<1 && lblRow.getEnabled()) addRowBtn.setEnabled(state);
			if(columnVarList.getItemCount()<1 && lblColumn.getEnabled()) addColBtn.setEnabled(state);
		}
		else{
			//disable all buttons
			addBlockBtn.setEnabled(state);
			addRepBtn.setEnabled(state);
			addGenBtn.setEnabled(state);
			addRowBtn.setEnabled(state);
			addColBtn.setEnabled(state);
			addEnvBtn.setEnabled(state);
		}
	}

	public void enableNumericButtons(boolean state){
		addBtn.setEnabled(state);
		moveBtn.setEnabled(state);
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		//		createButton(parent, IDialogConstants.DESELECT_ALL_ID, "RESET", true);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}


	@Override
	protected void okPressed(){

		design = designType.getSelectionIndex();
		String designType = null;
		switch(design){
		case 0:
			designType = "BIBD";
			break;
		case 1:
			designType = "AugRCB";
			break;
		case 2:
			designType = "AugLS";
			break;
		case 3:
			designType = "Alpha";
			break;
		case 4:
			designType = "RowCol";
			break;
	
			
		}

		descriptiveStat = checkDescriptiveStatistics.getSelection(); 
		varianceComponents = checkVarianceComponents.getSelection();
		boxplotRawData = checkBoxPlotBtn.getSelection();
		histogramRawData = checkHistogramBtn.getSelection();
		heatmapResiduals = checkHeatmapBtn.getSelection();
		diagnosticPlot = checkDiagnosticPlotsBtn.getSelection();
		satisfiedAllConditions=true;
		if(responseVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please enter a response variable.");
		}
		else if(genVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please enter a genotype variable.");
		}
		else if(!genotypeFixed && !genotypeRandom){
			MessageDialog.openError(getShell(), "Error", "Please specify whether the genotype variable is fixed or random.");
		}
		else if(lblBlock.getEnabled() && blockVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please enter a block variable.");
		}
		else if(lblReplicate.getEnabled() && replicateVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please enter a replicate variable.");
		}
		else if(lblRow.getEnabled() && rowVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please enter a row variable.");
		}
		else if(lblColumn.getEnabled() && columnVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please enter a column variable.");
		}
		else if(heatmapResiduals && !selectedHeatMapVars){
			MessageDialog.openError(getShell(), "Error", "Please specify the heatmap rows/colums.");
		}
		else{
				if(checkPerformPairwiseBtn.getSelection()){
					if(compareWithControlsBtn.getSelection()){
						performPairwise = "control";
					}
					else{
						performPairwise = "all";
								
					}
				}
				else{
					performPairwise = "null";
				}
				if(checkPerformPairwiseBtn.getSelection()){
					pairwiseAlpha = significanceLevel.getText();
					if(pairwiseAlpha.equals("")){
						MessageDialog.openError(getShell(), "Error", "Please enter a value for the pairwise level of significance.");
						satisfiedAllConditions=false;
					}
					else{
						compareControl = compareWithControlsBtn.getSelection();
						performAllPairwise = performAllComparisonsBtn.getSelection();
						if(compareControl){
							controlLevels = controlsList.getItems();
							if(controlLevels.length<1){
								MessageDialog.openError(getShell(), "Error", "Please specify control variables from the genotype levels.");
								satisfiedAllConditions=false;
							}
						}
					}
				}

//			if(genotypeRandom){//if random is checked
//
//				if(excludeControls){
//					controlLevels2 = controlsList.getItems();
//					if(controlLevels2.length<1){
//						MessageDialog.openError(getShell(), "Error", "Please specify control variables from the genotype levels.");
//						satisfiedAllConditions=false;
//					}
//				}
//			}

			if(satisfiedAllConditions){
				if(lblBlock.getEnabled())block = blockVarList.getItem(0);
				if(lblReplicate.getEnabled())rep = replicateVarList.getItem(0);
				if(lblRow.getEnabled())row = rowVarList.getItem(0);
				if(lblColumn.getEnabled())column = columnVarList.getItem(0);
				respvars = responseVarList.getItems();
				if(envVarList.getItemCount()<1)environment = "";
				else environment = envVarList.getItem(0);
				genotype = genVarList.getItem(0);

				String dataFileName = file.getAbsolutePath().toString().replaceAll(File.separator, "/");
				String newOutputFileName = "output.txt";

				File outputFolder =  new File(StarAnalysisUtilities.createOutputFolder(new File(filePath).getName(), "Incomplete Block"));
				

				OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Incomplete Block Analysis");
				rInfo.open();
//				rInfo.showProgressBar();
				try {
					if(environment.equals("")) environment = null;
					if(genotype.equals("")) genotype = null;
					if(block.equals("")) block = null;
					if(rep.equals("")) rep = null;
					if(row.equals("")) row = null;
					if(column.equals("")) column = null;
						
					ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doUnbalancedAnalysis(
							dataFileName,
							outputFolder.getAbsolutePath().replace(File.separator, "/") + "/",
							designType,
							respvars,
							environment,
							genotype,
							block,
							rep,
							row,
							column,
							descriptiveStat, 
							varianceComponents,
							performPairwise,
							Double.parseDouble(significanceLevel.getText()),
							controlLevels,
							boxplotRawData,
							histogramRawData, 
							heatmapResiduals,
							heatmapRow,
							heatmapColumn,
							diagnosticPlot);
					
					StarAnalysisUtilities.testVarArgs(dataFileName, outputFolder.getAbsolutePath().replace(File.separator, "/"), designType, respvars, environment, genotype, block, rep, row, column, descriptiveStat, 
							varianceComponents, performPairwise, Double.parseDouble(significanceLevel.getText()), controlLevels, boxplotRawData, histogramRawData, 
							heatmapResiduals, heatmapRow, heatmapColumn, diagnosticPlot);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rInfo.close();
				WindowDialogControlUtil.hideAllWindowDialog();
				StarAnalysisUtilities.openAndRefreshFolder(outputFolder.getAbsolutePath());
			}
		}
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(643, 695);
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}