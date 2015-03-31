package org.irri.breedingtool.pbtools.analysis.dialog;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.datamanipulation.handler.ProgressBarDialogHandler;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.SpecifyRowColDialog;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.SingleEnvironmentDialog.genotypeVarValidator;
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
import org.eclipse.swt.custom.CCombo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;

public class QTLAnalysisDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
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
	private Button moveBtn;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private DialogFormUtility dlgManager = new DialogFormUtility();
	private Button btnAddGen;
	private Label lblBlock;
	private Label lblRep;
	private Group variableGroup;
	private Label lblCol;
	private Label lblRow;
	private FileDialog fd;

	//specify parameters
	private int design = 0;
	private String[] respvars = {"Y1", "Y2"};
	private String environment = "NULL";
	private String[] environmentLevels = {"Env1", "Env2"};
	private String genotype = "Gen";
	private String block = "Blk";
	private String rep = "NULL";
	private String row = "NULL";
	private String column = "NULL";
	private String[] controlLevels = {"1", "2", "3"};
	boolean isInputRawData = false;
	int designIndex = 0;
	String[] traits = {"pred"};
	String selectedEnvironmentLevel = "NULL";
	boolean heterozygousPresent = true;
	String crossType ="riself";
	String step = "10";
	String windowSize = "20";
	String minDistance = "10";
	String qtlMethod = "CIM";
	boolean thresholdLiJi = true; 
	String thresholdNumericValue = "NULL";
	boolean estimatePlotMarkerMap = true; 
	double allelicDiffThreshold = 0.10;
	double cutOffMissingData = 0.10;
	double significanceLevelChiSquare = 0.05; 
	private boolean satisfiedAllConditions = true;
	private Label lblLevels;
	private TabItem tbtmDataInput;
	private TabItem tbtmOptions;
	private Composite dataInputComposite;
	private Composite composite_1;
	private Composite optionsComposite;
	private Composite composite_2;
	private Label lblPhenotypicDataUsed;
	private Button btnPredicted;
	private Button btnRaw;
	private Button btnBrowse;
	private Label lblFilename;
	private Label lblGenotypicDataFile;
	private Label lblGenotypicMapFile;
	private Label label_1;
	private Button button;
	private Label label;
	private Button heterozygotesPresentBtn;
	private Label lblChiSquare;
	private Combo envLevelsCombo;
	private Composite composite;
	private Button btnLiAndJi;
	private Button btnNumerical;
	private Composite composite_3;
	private Button btnSim;
	private Button btnCim;
	private Label label_3;
	private Spinner chiSquareSignificanceSpinner;
	private Spinner allelicProportionsSpinner;
	private Spinner cutOffSpinner;
	private Spinner windowSizeSpinner;
	private Spinner stepSpinner;
	private Spinner minDistanceSpinner;
	private Label lblCutoffForReporting;
	private Spinner numericThresholdSpinner;
	private Combo crossTypeSelection;
	private Button btnEstimateAndPlot;
	private String genotypicDataFileName;
	private String mapFileName;
	private String analysisType;
	private Label lblTypeOfDesignLabel;
	private CCombo genDataFileText;
	private CCombo genMapFileText;
	private Label lblGen;
	private Label lblEnv;
	private Text txtGenVar;
	private Text txtEnvVar;
	private Text txtBlockVar;
	private Text txtRepVar;
	private Text txtRowVar;
	private Text txtColVar;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public QTLAnalysisDialog(Shell parentShell, String analysisType, File file) {
		super(parentShell);
		setBlockOnOpen(false);
		this.analysisType = analysisType;
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
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

		fd = new FileDialog(getShell(), SWT.OPEN);
		fd.setText("Choose File Input");
		ProjectExplorerManager projectExplorerManger = new ProjectExplorerManager();
		fd.setFilterPath(((ProjectExplorerTreeNodeModel)projectExplorerManger.getDataFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getAbsolutePath());
		String[] filterExt = {"*.txt"};
		fd.setFilterExtensions(filterExt);

		parent.getShell().setText("QTL Analysis: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tabFolder.heightHint = 489;
		tabFolder.setLayoutData(gd_tabFolder);

		tbtmDataInput = new TabItem(tabFolder, SWT.NONE);
		tbtmDataInput.setText("Data Input");

		dataInputComposite = new Composite(tabFolder, SWT.NONE);
		tbtmDataInput.setControl(dataInputComposite);
		dataInputComposite.setBackground(getShell().getBackground());
		dataInputComposite.setLayout(new GridLayout(5, false));
		new Label(dataInputComposite, SWT.NONE);

		label = new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);

		lblPhenotypicDataUsed = new Label(dataInputComposite, SWT.NONE);
		lblPhenotypicDataUsed.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblPhenotypicDataUsed.setText("Phenotypic Data Used:");

		btnPredicted = new Button(dataInputComposite, SWT.RADIO);
		btnPredicted.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				isInputRawData = false;
				activatePredictedDataOptions(false);
				//				clearRawDataOptions(true);
			}
		});
		btnPredicted.setSelection(true);
		btnPredicted.setText("Estimated Means");

		btnRaw = new Button(dataInputComposite, SWT.RADIO);
		btnRaw.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isInputRawData = true;
				activatePredictedDataOptions(true);
			}
		});
		btnRaw.setText("Raw");
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);

		lblGenotypicDataFile = new Label(dataInputComposite, SWT.NONE);
		lblGenotypicDataFile.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblGenotypicDataFile.setText("Genotypic data file (.txt):");
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);

		lblFilename = new Label(dataInputComposite, SWT.NONE);
		lblFilename.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFilename.setText("Filename:");


		genDataFileText = new CCombo(dataInputComposite, SWT.BORDER);
		genDataFileText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		genDataFileText.setEditable(false);
		genDataFileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		genDataFileText.setItems(dataManipulationManager.getTxtFilesFromDir(file));
		genDataFileText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				genotypicDataFileName = file.getParentFile().getAbsolutePath()+"\\"+genDataFileText.getText();
			}
		});

		btnBrowse = new Button(dataInputComposite, SWT.NONE);
		GridData gd_btnBrowse = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnBrowse.widthHint = 73;
		btnBrowse.setLayoutData(gd_btnBrowse);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{	String selected = fd.open();
				genotypicDataFileName=selected;
				genDataFileText.setText(new File(selected).getName());
				}catch(NullPointerException npe){}
			}
		});
		btnBrowse.setText("Browse");
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);

		lblGenotypicMapFile = new Label(dataInputComposite, SWT.NONE);
		lblGenotypicMapFile.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblGenotypicMapFile.setText("Genetic map file (.txt):");
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);
		new Label(dataInputComposite, SWT.NONE);

		label_1 = new Label(dataInputComposite, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("Filename:");

		genMapFileText = new CCombo(dataInputComposite, SWT.BORDER);
		genMapFileText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		genMapFileText.setEditable(false);
		genMapFileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		genMapFileText.setItems(dataManipulationManager.getTxtFilesFromDir(file));
		genMapFileText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mapFileName = file.getParentFile().getAbsolutePath()+"\\"+genMapFileText.getText();
			}
		});

		button = new Button(dataInputComposite, SWT.NONE);
		GridData gd_button = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 79;
		button.setLayoutData(gd_button);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{	String selected = fd.open();
				mapFileName = selected;
				genMapFileText.setText(new File(selected).getName());
				}catch(NullPointerException npe){}
			}
		});
		button.setText("Browse");

		TabItem tbtmModelSpecifications_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setText("Model Specifications");

		Composite modelComposite = new Composite(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setControl(modelComposite);
		modelComposite.setLayout(new GridLayout(5, false));
		modelComposite.setBackground(getShell().getBackground());
		lblTypeOfDesignLabel = new Label(modelComposite, SWT.NONE);
		lblTypeOfDesignLabel.setEnabled(false);
		lblTypeOfDesignLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblTypeOfDesignLabel.setText("Type of Design:");

		designType = new Combo(modelComposite, SWT.READ_ONLY);
		designType.setEnabled(false);
		designType.setItems(new String[] {"Randomized Complete Block (RCB)", "Augmented RCB", "Augmented Latin Square", "Alpha-Lattice", "Row-Column"});
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
					lblCol.setEnabled(true);
					lblRep.setEnabled(true);
					disable("block");

				}
				else if(designType.getSelectionIndex()==3){//Alpha lattice
					lblRep.setEnabled(true);
					lblBlock.setEnabled(true);
					disable("row");
					disable("column");
				}
				else if(designType.getSelectionIndex()==4){//Row Column
					lblRow.setEnabled(true);
					lblCol.setEnabled(true);
					lblRep.setEnabled(true);
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
		gd_numVarList.widthHint = 171;
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
				moveBtn.setEnabled(true);
				if(numVarList.getSelectionCount()>0){
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
		new Label(modelComposite, SWT.NONE);

		addBtn = new Button(modelComposite, SWT.NONE);
		addBtn.setEnabled(false);
		GridData gd_addBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addBtn.widthHint = 52;
		addBtn.setLayoutData(gd_addBtn);
		addBtn.setText("Add");

		responseVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_responseVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_responseVarList.widthHint = 111;
		responseVarList.setLayoutData(gd_responseVarList);
		Label lblFactors = new Label(modelComposite, SWT.NONE);
		lblFactors.setText("Factors:");

		moveBtn = new Button(modelComposite, SWT.NONE);
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
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
		gd_factorVarList.widthHint = 65;
		factorVarList.setLayoutData(gd_factorVarList);
		factorVarList.setItems(factorVariables);

		Group group = new Group(modelComposite, SWT.NONE);
		group.setLayout(new GridLayout(5, false));
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_group.heightHint = 133;
		gd_group.widthHint = 255;
		group.setLayoutData(gd_group);

		btnAddGen = new Button(group, SWT.NONE);
		btnAddGen.setText("Add");
		GridData gd_btnAddGen = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddGen.widthHint = 52;
		btnAddGen.setLayoutData(gd_btnAddGen);

		lblGen = new Label(group, SWT.NONE);
		lblGen.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblGen.setText("Genotype:");
		
		txtGenVar = new Text(group, SWT.BORDER);
		txtGenVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtGenVar.setEditable(false);
		txtGenVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		btnAddEnv = new Button(group, SWT.NONE);
		btnAddEnv.setText("Add");
		GridData gd_btnAddEnv = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddEnv.widthHint = 52;
		btnAddEnv.setLayoutData(gd_btnAddEnv);

		lblEnv = new Label(group, SWT.NONE);
		lblEnv.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblEnv.setText("Environment:");
		
		txtEnvVar = new Text(group, SWT.BORDER);
		txtEnvVar.setEditable(false);
		txtEnvVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtEnvVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		txtEnvVar.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				envLevelsCombo.setEnabled(false);
				envLevelsCombo.removeAll();
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
		new Label(group, SWT.NONE);

		lblLevels = new Label(group, SWT.NONE);
		lblLevels.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLevels.setText("Level(s):");

		envLevelsCombo = new Combo(group, SWT.READ_ONLY);
		envLevelsCombo.setEnabled(false);
		envLevelsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		variableGroup = new Group(modelComposite, SWT.NONE);
		variableGroup.setEnabled(false);
		variableGroup.setLayout(new GridLayout(3, false));
		GridData gd_variableGroup = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		gd_variableGroup.heightHint = 135;
		variableGroup.setLayoutData(gd_variableGroup);

		btnAddBlock = new Button(variableGroup, SWT.NONE);
		btnAddBlock.setEnabled(false);
		btnAddBlock.setText("Add");
		GridData gd_btnAddBlock = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddBlock.widthHint = 52;
		btnAddBlock.setLayoutData(gd_btnAddBlock);


		lblBlock = new Label(variableGroup, SWT.NONE);
		lblBlock.setEnabled(false);
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

		optionsComposite = new Composite(tabFolder, SWT.NONE);
		tbtmOptions.setControl(optionsComposite);
		optionsComposite.setBackground(getShell().getBackground());
		optionsComposite.setLayout(new GridLayout(5, false));

		label_3 = new Label(optionsComposite, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));

		heterozygotesPresentBtn = new Button(optionsComposite, SWT.CHECK);
		heterozygotesPresentBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		heterozygotesPresentBtn.setText("Heterozygotes present");
		new Label(optionsComposite, SWT.NONE);
		new Label(optionsComposite, SWT.NONE);
		new Label(optionsComposite, SWT.NONE);

		Label lblTypeOfCross = new Label(optionsComposite, SWT.NONE);
		lblTypeOfCross.setText("Type of Cross:");

		crossTypeSelection = new Combo(optionsComposite, SWT.READ_ONLY);
		crossTypeSelection.setItems(new String[] {"f2", "bc", "dh", "am", "riself", "ri4self", "ri8self", "risib", "ri4sib", "ri8sib"});
		crossTypeSelection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		crossTypeSelection.select(0);

		Label label_2 = new Label(optionsComposite, SWT.NONE);

		Label lblStep = new Label(optionsComposite, SWT.NONE);
		lblStep.setText("Step:");

		stepSpinner = new Spinner(optionsComposite, SWT.BORDER);
		GridData gd_stepSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_stepSpinner.widthHint = 10;
		stepSpinner.setLayoutData(gd_stepSpinner);
		stepSpinner.setSelection(10);

		Label lblWindowSize = new Label(optionsComposite, SWT.NONE);
		lblWindowSize.setText("Window size:");

		windowSizeSpinner = new Spinner(optionsComposite, SWT.BORDER);
		windowSizeSpinner.setSelection(20);
		GridData gd_windowSizeSpinner = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_windowSizeSpinner.widthHint = 10;
		windowSizeSpinner.setLayoutData(gd_windowSizeSpinner);
		new Label(optionsComposite, SWT.NONE);

		Label lblMinimumDistance = new Label(optionsComposite, SWT.NONE);
		lblMinimumDistance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMinimumDistance.setText("Minimum distance:");

		minDistanceSpinner = new Spinner(optionsComposite, SWT.BORDER);
		minDistanceSpinner.setSelection(10);
		GridData gd_minDistanceSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_minDistanceSpinner.widthHint = 10;
		minDistanceSpinner.setLayoutData(gd_minDistanceSpinner);

		Label lblMethodForDetecting = new Label(optionsComposite, SWT.NONE);
		lblMethodForDetecting.setText("Method for detecting QTL:");

		composite_3 = new Composite(optionsComposite, SWT.NONE);
		composite_3.setLayout(new GridLayout(3, false));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));

		btnSim = new Button(composite_3, SWT.RADIO);
		btnSim.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				qtlMethod = "SIM";
			}
		});
		GridData gd_btnSim = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSim.widthHint = 59;
		btnSim.setLayoutData(gd_btnSim);
		btnSim.setText("SIM");
		new Label(composite_3, SWT.NONE);

		btnCim = new Button(composite_3, SWT.RADIO);
		btnCim.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				qtlMethod = "CIM";
			}
		});
		btnCim.setSelection(true);
		btnCim.setText("CIM");
		new Label(optionsComposite, SWT.NONE);

		Label lblThresholdForPvalue = new Label(optionsComposite, SWT.NONE);
		lblThresholdForPvalue.setText("Threshold for p-value:");

		composite = new Composite(optionsComposite, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		gd_composite.widthHint = 166;
		composite.setLayoutData(gd_composite);

		btnLiAndJi = new Button(composite, SWT.RADIO);
		btnLiAndJi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				thresholdLiJi = true; 
				numericThresholdSpinner.setEnabled(false);
			}
		});
		btnLiAndJi.setSelection(true);
		GridData gd_btnLiAndJi = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnLiAndJi.widthHint = 59;
		btnLiAndJi.setLayoutData(gd_btnLiAndJi);
		btnLiAndJi.setText("Li and Ji");
		new Label(composite, SWT.NONE);

		btnNumerical = new Button(composite, SWT.RADIO);
		btnNumerical.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				thresholdLiJi = false;
				numericThresholdSpinner.setEnabled(true);

			}
		});
		btnNumerical.setText("Numerical");

		numericThresholdSpinner = new Spinner(optionsComposite, SWT.BORDER);
		numericThresholdSpinner.setEnabled(false);
		numericThresholdSpinner.setPageIncrement(0);
		numericThresholdSpinner.setMaximum(95);
		numericThresholdSpinner.setMinimum(5);
		numericThresholdSpinner.setSelection(10);
		numericThresholdSpinner.setIncrement(5);
		numericThresholdSpinner.setDigits(2);
		new Label(optionsComposite, SWT.NONE);
		new Label(optionsComposite, SWT.NONE);
		new Label(optionsComposite, SWT.NONE);
		new Label(optionsComposite, SWT.NONE);
		new Label(optionsComposite, SWT.NONE);

		Group grpMarkerQualitOptions = new Group(optionsComposite, SWT.NONE);
		grpMarkerQualitOptions.setText("Marker Quality Options:");
		grpMarkerQualitOptions.setLayout(new GridLayout(2, false));
		grpMarkerQualitOptions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 5, 1));

		btnEstimateAndPlot = new Button(grpMarkerQualitOptions, SWT.CHECK);
		btnEstimateAndPlot.setText("Estimate and plot new marker map");
		new Label(grpMarkerQualitOptions, SWT.NONE);

		Label lblAllelicDifferencesThreshold = new Label(grpMarkerQualitOptions, SWT.NONE);
		lblAllelicDifferencesThreshold.setText("Allelic differences threshold (proportion):");

		//		SelectionListener floatingSpinnerListener = new SelectionAdapter() {
		//		      public void widgetSelected(SelectionEvent e) {
		//		        Spinner activeSpinner= (Spinner) (e.getSource());
		//		    	  int selection = activeSpinner.getSelection();
		//		        int digits = activeSpinner.getDigits();
		//		        activeSpinner.setSelection(Integer.parseInt(Double.toString(selection / Math.pow(10, digits))));
		//		      }
		//		 };

		allelicProportionsSpinner = new Spinner(grpMarkerQualitOptions, SWT.BORDER);
		allelicProportionsSpinner.setMaximum(95);
		allelicProportionsSpinner.setMinimum(5);
		allelicProportionsSpinner.setSelection(10);
		allelicProportionsSpinner.setIncrement(5);
		allelicProportionsSpinner.setPageIncrement(0);
		allelicProportionsSpinner.setDigits(2);
		GridData gd_allelicProportionsSpinner = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_allelicProportionsSpinner.widthHint = 41;
		allelicProportionsSpinner.setLayoutData(gd_allelicProportionsSpinner);
		//		allelicProportionsSpinner.addSelectionListener(floatingSpinnerListener);
		lblCutoffForReporting = new Label(grpMarkerQualitOptions, SWT.NONE);
		lblCutoffForReporting.setText("Cut-off for reporting missing data:");

		cutOffSpinner = new Spinner(grpMarkerQualitOptions, SWT.BORDER);
		cutOffSpinner.setMaximum(95);
		cutOffSpinner.setMinimum(5);
		cutOffSpinner.setSelection(10);
		cutOffSpinner.setDigits(2);
		cutOffSpinner.setPageIncrement(0);
		cutOffSpinner.setIncrement(5);
		GridData gd_cutOffSpinner = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_cutOffSpinner.widthHint = 46;
		cutOffSpinner.setLayoutData(gd_cutOffSpinner);
		//		cutOffSpinner.addSelectionListener(floatingSpinnerListener);

		lblChiSquare = new Label(grpMarkerQualitOptions, SWT.WRAP);
		GridData gd_lblChiSquare = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblChiSquare.widthHint = 199;
		lblChiSquare.setLayoutData(gd_lblChiSquare);
		lblChiSquare.setText("Level of significance for Chi-square test for Segregation Distortion:");

		chiSquareSignificanceSpinner = new Spinner(grpMarkerQualitOptions, SWT.BORDER);
		chiSquareSignificanceSpinner.setMaximum(95);
		chiSquareSignificanceSpinner.setMinimum(5);
		chiSquareSignificanceSpinner.setSelection(5);
		chiSquareSignificanceSpinner.setPageIncrement(0);
		chiSquareSignificanceSpinner.setIncrement(5);
		chiSquareSignificanceSpinner.setDigits(2);
		GridData gd_chiSquareSignificanceSpinner = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_chiSquareSignificanceSpinner.widthHint = 20;
		chiSquareSignificanceSpinner.setLayoutData(gd_chiSquareSignificanceSpinner);

		initializeForm();
		return container;

	}
	void initializeForm(){
		dlgManager.initializeSingleSelectionList(factorVarList, txtEnvVar,moveBtn, btnAddEnv, new envVarValidator(factorVarList,  true,  file,  txtEnvVar, lblEnv));
		dlgManager.initializeSingleSelectionList(factorVarList, txtGenVar,moveBtn, btnAddGen, new TextVarValidator(txtGenVar, lblGen));
		dlgManager.initializeSingleSelectionList(factorVarList, txtBlockVar,moveBtn, btnAddBlock, new TextVarValidator(txtBlockVar, lblBlock));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRepVar,moveBtn, btnAddRep, new TextVarValidator(txtRepVar, lblRep));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRowVar,moveBtn, btnAddRow, new TextVarValidator(txtRowVar, lblRow));
		dlgManager.initializeSingleSelectionList(factorVarList, txtColVar,moveBtn, btnAddCol, new TextVarValidator(txtColVar, lblCol));
		dlgManager.initializeVariableMoveList(numVarList, factorVarList, moveBtn, file.getAbsolutePath());
		dlgManager.initializeSingleButtonList(numVarList, responseVarList,moveBtn, addBtn);
	}
	protected void activatePredictedDataOptions(boolean state) {
		// TODO Auto-generated method stub
		designType.setEnabled(state);
		variableGroup.setEnabled(state);
		lblTypeOfDesignLabel.setEnabled(state);
		if(!state){
			design = 0;
			block = "NULL";
			rep = "NULL";
			row = "NULL";
			column = "NULL";
			lblRep.setEnabled(false);
			lblBlock.setEnabled(false);
			lblRow.setEnabled(false);
			lblCol.setEnabled(false);

			if(!txtRepVar.getText().isEmpty()) dataManipulationManager.removeItemOfThenAddTo(txtRepVar, factorVarList);
			if(!txtBlockVar.getText().isEmpty()) dataManipulationManager.removeItemOfThenAddTo(txtBlockVar,factorVarList);
			if(!txtRowVar.getText().isEmpty()) dataManipulationManager.removeItemOfThenAddTo(txtRowVar,factorVarList);
			if(!txtColVar.getText().isEmpty()) dataManipulationManager.removeItemOfThenAddTo(txtColVar,factorVarList);
		}
		else{
			switch (designType.getSelectionIndex()) {
			case 0://Randomized Complete Block (RCB)
			case 1://Augmented RCB
				lblBlock.setEnabled(state);
				break;
			case 4://Alpha-Lattice
				lblBlock.setEnabled(state);
				lblRep.setEnabled(state);
				break;
			case 3://Augmented Latin Square
			case 2://Row-Column
				lblRep.setEnabled(state);
				lblRow.setEnabled(state);
				lblCol.setEnabled(state);
				break;
			}
		}
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
			if(txtBlockVar.getText().isEmpty()&& lblBlock.getEnabled()) btnAddBlock.setEnabled(state);
			if(txtGenVar.getText().isEmpty()) btnAddGen.setEnabled(state);
			if(txtRepVar.getText().isEmpty()&& lblRep.getEnabled()) btnAddRep.setEnabled(state);
			if(txtRowVar.getText().isEmpty() && lblRow.getEnabled()) btnAddRow.setEnabled(state);
			if(txtColVar.getText().isEmpty() && lblCol.getEnabled()) btnAddCol.setEnabled(state);
		}
		else{
			//disable all buttons

			moveBtn.setEnabled(state);
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
		moveBtn.setEnabled(state);
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
		System.out.println("MAP: "+mapFileName);
		System.out.println("DATA: "+genotypicDataFileName);
		design = designType.getSelectionIndex();
		satisfiedAllConditions=true;
		if(responseVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please add a response variable.");
		}
		//		else if(envVarList.getItemCount()<1){
		//			MessageDialog.openError(getShell(), "Error", "Please enter an environment variable!");
		//		}
		else if(txtGenVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a genotype factor.");
		}
		else{
			if(btnRaw.getSelection()){//if raw data is selected
				if(lblBlock.getEnabled() && txtBlockVar.getText().isEmpty()){
					MessageDialog.openError(getShell(), "Error", "Please add a block factor.");
					satisfiedAllConditions = false;
				}
				else if(lblRep.getEnabled() && txtRepVar.getText().isEmpty()){
					MessageDialog.openError(getShell(), "Error", "Please add a replicate factor.");
					satisfiedAllConditions = false;
				}
				else if(lblRow.getEnabled() && txtRowVar.getText().isEmpty()){
					MessageDialog.openError(getShell(), "Error", "Please add a row factor.");
					satisfiedAllConditions = false;
				}
				else if(lblCol.getEnabled() && txtColVar.getText().isEmpty()){
					MessageDialog.openError(getShell(), "Error", "Please add a column factor.");
					satisfiedAllConditions = false;
				}
			}
			if(satisfiedAllConditions){
				if(lblBlock.getEnabled()  && !txtBlockVar.getText().isEmpty())block = txtBlockVar.getText();
				if(lblRep.getEnabled() && !txtRepVar.getText().isEmpty())rep = txtRepVar.getText();
				if(lblRow.getEnabled() && !txtRowVar.getText().isEmpty())row = txtRowVar.getText();
				if(lblCol.getEnabled() && !txtColVar.getText().isEmpty())column = txtColVar.getText();

				heterozygousPresent = heterozygotesPresentBtn.getSelection();
				crossType = crossTypeSelection.getText();
				step = stepSpinner.getText();
				windowSize = windowSizeSpinner.getText();
				minDistance = minDistanceSpinner.getText();
				if(btnNumerical.getSelection()) thresholdNumericValue = Double.toString(dataManipulationManager.convertInttoDouble(numericThresholdSpinner.getSelection(),numericThresholdSpinner.getDigits()));
				else thresholdNumericValue = "NULL";
				estimatePlotMarkerMap = btnEstimateAndPlot.getSelection(); 
				allelicDiffThreshold = dataManipulationManager.convertInttoDouble(allelicProportionsSpinner.getSelection(),allelicProportionsSpinner.getDigits());
				cutOffMissingData = dataManipulationManager.convertInttoDouble(cutOffSpinner.getSelection(),cutOffSpinner.getDigits()); 
				significanceLevelChiSquare = dataManipulationManager.convertInttoDouble(chiSquareSignificanceSpinner.getSelection(),chiSquareSignificanceSpinner.getDigits()); 

				traits = responseVarList.getItems();
				if(!txtEnvVar.getText().isEmpty()){
					environment = txtEnvVar.getText();
					selectedEnvironmentLevel = envLevelsCombo.getText();
				}
				genotype = txtGenVar.getText();


				genotypicDataFileName = genotypicDataFileName.replaceAll("\\\\+", "/");
				mapFileName = mapFileName.replaceAll("\\\\+", "/");
				final String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");
			

				File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);
				final String newOutputFileName = outputFolder.getAbsolutePath()+"/output.txt";
				System.out.println("is unput raw data?"+isInputRawData);
				//call rJavaManager Function here
				
				OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "QTL Analysis");
				rInfo.open();
				ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doQtl(dataFileName, isInputRawData, newOutputFileName.replaceAll("\\\\+", "/"),  outputFolder.getAbsolutePath().replaceAll("\\\\+", "/")+"/", genotypicDataFileName, mapFileName, designIndex, traits, 
						block, rep, row, column, genotype, environment, environmentLevels, selectedEnvironmentLevel, 
						heterozygousPresent, crossType, step, windowSize, minDistance, qtlMethod, thresholdLiJi, thresholdNumericValue, 
						estimatePlotMarkerMap, allelicDiffThreshold, cutOffMissingData, significanceLevelChiSquare);
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
		return new Point(658, 686);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	public class envVarValidator extends DialogListValidator {
		DataManipulationManager dataManipulationManager = new DataManipulationManager();

		List factorVarList;
		Boolean performMultiEnv;
		File file;
		Text txtEnvVar;
		Label lblEnvironment;

		public envVarValidator(List factorVarList, Boolean performMultiEnv, File file, Text envVarText, Label lblEnvironment) {
					super();
					this.factorVarList = factorVarList;
					this.performMultiEnv = performMultiEnv;
					this.file = file;
					this.txtEnvVar = envVarText;
					this.lblEnvironment = lblEnvironment;
					// TODO Auto-generated constructor stub
				}
				
				@Override
				public boolean validate(){
					return txtEnvVar.getText().isEmpty();
				}
				
				@Override
				public boolean performAddProcess(){
					String selectedNumVars[] = factorVarList.getSelection();
					String selectedStrings[] = factorVarList.getSelection();
					String[] levelItems = dataManipulationManager.getEnvtLevels(selectedNumVars[0], file);
//					if(levelItems.length<2 && performMultiEnv){
//						MessageDialog.openWarning(factorVarList.getShell(), "Invalid factor", "The environment factor for a Multi-Environment Analysis should have atleast 2(two) levels.\n\n" +
//								selectedNumVars[0]+" has 1 level only. \n\n" +
//								"Please choose a different environment factor.");
//						return false;
//					}
					environmentLevels = DataManipulationManager.getEnvtLevels(selectedStrings[0], file);
					envLevelsCombo.setEnabled(true);
					envLevelsCombo.setItems(environmentLevels);
					envLevelsCombo.add("[ALL]",0);
					envLevelsCombo.select(0);
					
					return true;
				}
				
				@Override
				public boolean performRemoveProcess(){
					envLevelsCombo.setEnabled(false);
					envLevelsCombo.removeAll();
					return true;
				}
			}
}