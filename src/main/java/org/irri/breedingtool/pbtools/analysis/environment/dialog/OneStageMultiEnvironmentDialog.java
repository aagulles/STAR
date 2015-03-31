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
import org.irri.breedingtool.datamanipulation.handler.ProgressBarDialogHandler;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.pbtools.analysis.matingdesign.dialog.TripleTestCrossDialog.testerVarValidator;
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

public class OneStageMultiEnvironmentDialog extends Dialog {

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
	private Button checkRandomBtn;
	private Label lblBlock;
	private Label lblRep;
	private Group variableGroup;
	private Label lblCol;
	private Label lblRow;

	//specify parameters
	private int design = 0;
	private String[] respvars = {};
	private String environment = "Site";
	private String[] environmentLevels;
	private String genotype = "Gen";
	private String block = "Blk";
	private String rep = "NULL";
	private String row = "NULL";
	private String column = "NULL";
	private boolean descriptiveStat = true; 
	private boolean varianceComponents;

	private boolean boxplotRawData = true;
	private boolean histogramRawData = true;
	private boolean heatmapResiduals = true;
	private String heatmapRow = null;
	private String heatmapColumn = null;
	private boolean diagnosticPlot = true;
	private boolean genotypeFixed = true;
	private boolean performPairwise = true;
	private String pairwiseAlpha = "0.05";
	private String[] genotypeLevels = {};
	private String[] controlLevels = {};
	private boolean compareControl = true;
	private boolean performAllPairwise = false;
	private boolean genotypeRandom = true;
	private boolean excludeControls = true;
	private String[] controlLevels2 = {};
	private boolean genoPhenoCorrelation = true;
	private Button checkBoxPlotBtn;
	private Button checkHistogramBtn;
	private Button checkDiagnosticPlotsBtn;
	private boolean satisfiedAllConditions=true;
//	private boolean testGenotypicEffect;
//	private boolean testGxEEffect;
	private String analysisType;
	private Label lblEnv;
	private Label lblGen;
	private Text txtEnvVar;
	private Text txtGenVar;
	private Text txtBlockVar;
	private Text txtRepVar;
	private Text txtRowVar;
	private Text txtColVar;
	private Label lblNewLabel_4;
	private Label lblNewLabel_1;
	private Label lblNewLabel_5;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public OneStageMultiEnvironmentDialog(Shell parentShell, String analysisType, File file) {
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

		parent.getShell().setText(" One-Stage Multi-Environment Analysis: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
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
		designType.setItems(new String[] {"Randomized Complete Block (RCB)", "Augmented RCB", "Augmented Latin Square", "Alpha-Lattice", "Row-Column", "Latinized Alpha-Lattice", "Latinized Row-Column"});
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
					disable("replicate");
					disable("block");

				}
				else if(designType.getSelectionIndex()==1){//Alpha lattice
					lblRep.setEnabled(true);
					lblBlock.setEnabled(true);
					disable("row");
					disable("column");
				}
				else if(designType.getSelectionIndex()==2){//Row Column
					lblRow.setEnabled(true);
					lblCol.setEnabled(true);
					lblRep.setEnabled(true);
					disable("block");
				}
				else if(designType.getSelectionIndex()==3){//Latinized Alpha lattice
					lblRep.setEnabled(true);
					lblBlock.setEnabled(true);
					disable("row");
					disable("column");
				} 
				else if(designType.getSelectionIndex()==4){//Latinized Row-Column
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
		numVarList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0){
					enableFactorButtons(false);
					enableNumericButtons(true);
					
				}
			}
		});
		GridData gd_numVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_numVarList.heightHint = 95;
		gd_numVarList.widthHint = 167;
		numVarList.setLayoutData(gd_numVarList);
		numVarList.setItems(numericVariables);

		addBtn = new Button(modelComposite, SWT.NONE);
		
		addBtn.setEnabled(false);
		GridData gd_addBtn = new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1);
		gd_addBtn.widthHint = 66;
		addBtn.setLayoutData(gd_addBtn);
		addBtn.setText("Add");

		responseVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_responseVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_responseVarList.widthHint = 111;
		responseVarList.setLayoutData(gd_responseVarList);

		Label lblFactors = new Label(modelComposite, SWT.NONE);
		lblFactors.setText("Factors:");

		moveBtn = new Button(modelComposite, SWT.NONE);
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
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
		gd_factorVarList.widthHint = 65;
		factorVarList.setLayoutData(gd_factorVarList);
		factorVarList.setItems(factorVariables);

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
		lblEnv.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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
		lblGen.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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
		lblBlock.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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
		lblRow.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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
		lblCol.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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
		composite_1.setLayout(new GridLayout(7, false));

		lblOptionsForGenotype = new Label(composite_1, SWT.NONE);
		lblOptionsForGenotype.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		GridData gd_lblOptionsForGenotype = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_lblOptionsForGenotype.heightHint = 21;
		gd_lblOptionsForGenotype.widthHint = 228;
		lblOptionsForGenotype.setLayoutData(gd_lblOptionsForGenotype);
		lblOptionsForGenotype.setText("Options for Genotype as Fixed:");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
				
						checkPerformPairwiseBtn = new Button(composite_1, SWT.CHECK);
						checkPerformPairwiseBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
						checkPerformPairwiseBtn.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
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
						checkPerformPairwiseBtn.setText("Pairwise mean comparisons");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
				new Label(composite_1, SWT.NONE);
		
				compareWithControlsBtn = new Button(composite_1, SWT.RADIO);
				compareWithControlsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
				compareWithControlsBtn.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
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
				compareWithControlsBtn.setText("Compare with control(s)");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
				new Label(composite_1, SWT.NONE);
		
				performAllComparisonsBtn = new Button(composite_1, SWT.RADIO);
				performAllComparisonsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
				performAllComparisonsBtn.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
				performAllComparisonsBtn.setEnabled(false);
				performAllComparisonsBtn.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
					}
				});
				performAllComparisonsBtn.setText("Perform all comparisons");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		lblLevelOfSignificance = new Label(composite_1, SWT.NONE);
		lblLevelOfSignificance.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblLevelOfSignificance.setEnabled(false);
		lblLevelOfSignificance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblLevelOfSignificance.setText("Level of Significance:");
		
				significanceLevel = new Text(composite_1, SWT.BORDER);
				significanceLevel.setEnabled(false);
				significanceLevel.setText("0.05");
				GridData gd_significanceLevel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
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
		
				lblSpecifyControls = new Label(composite_1, SWT.NONE);
				lblSpecifyControls.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
				lblSpecifyControls.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
				lblSpecifyControls.setEnabled(false);
				lblSpecifyControls.setText("Specify Control(s)");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		levelsOfGenotypeLbl = new Label(composite_1, SWT.NONE);
		levelsOfGenotypeLbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		levelsOfGenotypeLbl.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		levelsOfGenotypeLbl.setEnabled(false);
		levelsOfGenotypeLbl.setText("Levels of Genotype:");
		new Label(composite_1, SWT.NONE);

		controlsLbl = new Label(composite_1, SWT.NONE);
		controlsLbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		controlsLbl.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		controlsLbl.setEnabled(false);
		controlsLbl.setText("Control(s):");
		
		lblNewLabel_1 = new Label(composite_1, SWT.NONE);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_1.widthHint = 5;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
				
						genLevelsList = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
						genLevelsList.setEnabled(false);
						GridData gd_genLevelsList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
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
				addControlsBtn.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
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
				gd_addControlsBtn.widthHint = 68;
				addControlsBtn.setLayoutData(gd_addControlsBtn);
				addControlsBtn.setText("Add");
		
				controlsList = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
				controlsList.setEnabled(false);
				GridData gd_controlsList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
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
		
		lblNewLabel_5 = new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label lblNewLabel_3 = new Label(composite_1, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNewLabel_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		lblNewLabel_3.setText("Display:");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
				
						checkDescriptiveStatistics = new Button(composite_1, SWT.CHECK);
						checkDescriptiveStatistics.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						checkDescriptiveStatistics.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
						checkDescriptiveStatistics.setText("Descriptive Statistics");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
				
						checkVarianceComponents = new Button(composite_1, SWT.CHECK);
						checkVarianceComponents.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						checkVarianceComponents.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
						checkVarianceComponents.setText("Variance Components");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		lblNewLabel_4 = new Label(composite_1, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);


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


		initializeForm();
		return container;

	}
	void initializeForm(){
		dlgManager.initializeSingleSelectionList(factorVarList, txtEnvVar,moveBtn, btnAddEnv, new EnvVarValidator(factorVarList,  true,  file,  txtEnvVar, lblEnv));
		dlgManager.initializeSingleSelectionList(factorVarList, txtGenVar,moveBtn, btnAddGen, new genotypeVarValidator());
		dlgManager.initializeSingleSelectionList(factorVarList, txtBlockVar,moveBtn, btnAddBlock, new TextVarValidator(txtBlockVar, lblBlock));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRepVar,moveBtn, btnAddRep, new TextVarValidator(txtRepVar, lblRep));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRowVar,moveBtn, btnAddRow, new TextVarValidator(txtRowVar, lblRow));
		dlgManager.initializeSingleSelectionList(factorVarList, txtColVar,moveBtn, btnAddCol, new TextVarValidator(txtColVar, lblCol));
		dlgManager.initializeVariableMoveList(numVarList, factorVarList, moveBtn, file.getAbsolutePath());
		dlgManager.initializeSingleButtonList(numVarList, responseVarList,moveBtn, addBtn);
	}
	protected void resetGenotypeControls() {
		// TODO Auto-generated method stub
		genLevelsList.removeAll();
		controlsList.removeAll();
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
			if(txtBlockVar.getText().isEmpty()&& lblBlock.getEnabled()) btnAddBlock.setEnabled(state);
			if(txtGenVar.getText().isEmpty()) btnAddGen.setEnabled(state);
			if(txtRepVar.getText().isEmpty() && lblRep.getEnabled()) btnAddRep.setEnabled(state);
			if(txtRowVar.getText().isEmpty()&& lblRow.getEnabled()) btnAddRow.setEnabled(state);
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
		genotypeFixed = checkFixedBtn.getSelection();
		genotypeRandom = checkRandomBtn.getSelection();
		design = designType.getSelectionIndex();

		descriptiveStat = checkDescriptiveStatistics.getSelection(); 
		varianceComponents = checkVarianceComponents.getSelection();
		boxplotRawData = checkBoxPlotBtn.getSelection();
		histogramRawData = checkHistogramBtn.getSelection();
		diagnosticPlot = checkDiagnosticPlotsBtn.getSelection();
		satisfiedAllConditions=true;
		if(responseVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please add a response variable.");
		}
		else if(txtEnvVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add an environment factor.");
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
				if(lblBlock.getEnabled())block = txtBlockVar.getText();
				if(lblRep.getEnabled())rep = txtRepVar.getText();
				if(lblRow.getEnabled())row = txtRowVar.getText();
				if(lblCol.getEnabled())column = txtColVar.getText(); 
				respvars = responseVarList.getItems();
				environment = txtEnvVar.getText();
				genotype = txtGenVar.getText();
				
				String selectedStrings = txtEnvVar.getText();
				environmentLevels = DataManipulationManager.getEnvtLevels(selectedStrings, file);
				final String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");

				File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);
				final String newOutputFileName = outputFolder.getAbsolutePath()+"/output.txt";

				OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "One-Stage Multi-Environment Analysis");
				rInfo.open();
				ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doMultiEnvironmentOneStage(dataFileName, newOutputFileName.replaceAll("\\\\+", "/"), outputFolder.getAbsolutePath().replaceAll("\\\\+", "/")+"/", design, respvars, environment, environmentLevels,
						genotype, block, rep, row, column, descriptiveStat, varianceComponents, boxplotRawData, histogramRawData, diagnosticPlot, 
						genotypeFixed, performPairwise, pairwiseAlpha, genotypeLevels, controlLevels, compareControl, performAllPairwise, genotypeRandom);
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
		return new Point(538, 664);
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