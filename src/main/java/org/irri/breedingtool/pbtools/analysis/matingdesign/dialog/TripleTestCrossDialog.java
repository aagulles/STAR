package org.irri.breedingtool.pbtools.analysis.matingdesign.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.DialogListValidator;
import org.irri.breedingtool.utility.EnvVarValidator;
import org.irri.breedingtool.utility.PBToolAnalysisUtilities;
import org.irri.breedingtool.utility.TextVarValidator;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.wb.swt.SWTResourceManager;

public class TripleTestCrossDialog extends Dialog {
	String analysisType;
	DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private DialogFormUtility dlgManager = new DialogFormUtility();
	ArrayList<String> varInfo;
	private File file;
	private String[] factorVariables;
	private String[] numericVariables;
	private List numVarList;
	private Button addBtn;
	private Button moveBtn;
	private List factorVarList;
	private Button btnAddRep;
	private Button btnAddF2;
	private Button btnAddTester;
	private Button btnAddEnv;
	private Button performMultiRadio;
	private List responseVarList;
	private Text significanceLevel;
	private String[] envtLevels;
	private List testerLevels;
	private Button btnAddP1;
	private Button btnAddP2;
	private Button btnAddF1;
	private String[] designs={"CRD","RCB","Alpha","RowColumn"};
	private String design="CRD";
	private String[] respvars;
	private String tester;
	private String f2lines;
	private String rep = "NULL";
	private String block = "NULL";
	private String row = "NULL";
	private String column = "NULL";
	private String environment="NULL";
	private String individual;
	private String codeP1;
	private String codeP2;
	private String codeF1;
	private String alpha;
	private Label lblRep;
	private Label lblBlock;
	private Combo designCombo;
	private Button btnAddBlock;
	private ShellListener newShellListener;
	protected boolean reFocused;
	protected boolean pressedOkButton;
	private Button btnAddRow;
	private Label lblRow;
	private Label lblCol;
	private Button btnAddCol;
	private Text txtEnvVar;
	private Text txtTesterVar;
	private Text txtP1Var;
	private Text txtP2Var;
	private Text txtF1Var;
	private Text txtF2Var;
	private Text txtBlockVar;
	private Text txtRepVar;
	private Text txtRowVar;
	private Text txtColVar;
	private Label lblTester;
	private Label lblP1;
	private Label lblP2;
	private Label lblF1;
	private Label lblF2;
	private Label lblEnv;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public TripleTestCrossDialog(Shell parentShell, String analysisType, File file) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		setBlockOnOpen(false);
		this.analysisType = analysisType;
		this.file = file;
		setFactors();
		ttcDialogSetOpenState(analysisType,true);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 7;
		

		getShell().setData("analysis",analysisType);
		getShell().setData("filePathID",file.getAbsolutePath());
		WindowDialogControlUtil.addWindowDialogToList(getShell());
		
		parent.addDisposeListener(new DisposeListener(){
			@Override
			public void widgetDisposed(DisposeEvent e) {
				// TODO Auto-generated method stub
				WindowDialogControlUtil.removeWindowDialogToList(getShell());
			}
			
		});
		
		Group group = new Group(container, SWT.NONE);
		group.setLayout(new GridLayout(2, false));
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, false, 5, 1);
		gd_group.heightHint = 20;
		gd_group.widthHint = 318;
		group.setLayoutData(gd_group);
		
		Label lblTypeOfDesign = new Label(group, SWT.NONE);
		lblTypeOfDesign.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblTypeOfDesign.setText("TYPE OF DESIGN:");
		
		designCombo = new Combo(group, SWT.READ_ONLY);
		designCombo.setItems(new String[] {"Completely Randomized Design (CRD)", "Randomized Complete Block (RCB)", "Alpha-Lattice", "Row-Column"});
		designCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		designCombo.select(1);
		designCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableFactorButtons(false);
				if(designCombo.getSelectionIndex()==1){//RCB
					lblBlock.setEnabled(true);
					disable("replicate");
					disable("row");
					disable("column");
				}
				else if(designCombo.getSelectionIndex()==0){//CRD
					disable("replicate");
					disable("block");
					disable("row");
					disable("column");
				}
				else if(designCombo.getSelectionIndex()==2){//Alpha lattice
					lblRep.setEnabled(true);
					lblBlock.setEnabled(true);
					disable("row");
					disable("column");
				}
				else if(designCombo.getSelectionIndex()==3){//Row-Column
					lblRep.setEnabled(true);
					lblRow.setEnabled(true);
					lblCol.setEnabled(true);
					disable("block");
				}
			}
		});
		
		Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayout(new GridLayout(1, false));
		GridData gd_group_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_group_1.heightHint = 60;
		gd_group_1.widthHint = 104;
		group_1.setLayoutData(gd_group_1);
		
		Label lblDataInput = new Label(group_1, SWT.NONE);
		lblDataInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDataInput.setText("DATA INPUT:");
		
		Button btnPlotMeans = new Button(group_1, SWT.RADIO);
		btnPlotMeans.setSelection(true);
		btnPlotMeans.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnPlotMeans.setText("Plot Means");
		
		Button btnDataMeans = new Button(group_1, SWT.RADIO);
		btnDataMeans.setEnabled(false);
		btnDataMeans.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnDataMeans.setText("Individual Values");
		
		Label lblNumericVariables = new Label(container, SWT.NONE);
		GridData gd_lblNumericVariables = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_lblNumericVariables.widthHint = 147;
		lblNumericVariables.setLayoutData(gd_lblNumericVariables);
		lblNumericVariables.setText("Numeric Variable(s):");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblResponseVariables = new Label(container, SWT.NONE);
		lblResponseVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblResponseVariables.setText("Response Variable(s):");
		
		numVarList = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		numVarList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0){
					enableFactorButtons(false);
					enableNumericButtons(true);
					
				}
			}
		});
		GridData gd_numVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 4);
		gd_numVarList.heightHint = 30;
		gd_numVarList.widthHint = 45;
		numVarList.setLayoutData(gd_numVarList);
		new Label(container, SWT.NONE);
		
		addBtn = new Button(container, SWT.NONE);
		addBtn.setEnabled(false);
		GridData gd_addBtn = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_addBtn.widthHint = 60;
		addBtn.setLayoutData(gd_addBtn);
		addBtn.setText("Add");
		numVarList.setItems(numericVariables);
		new Label(container, SWT.NONE);
		
		responseVarList = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_responseVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2);
		gd_responseVarList.heightHint = 30;
		responseVarList.setLayoutData(gd_responseVarList);
		
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Group performAnalysisGroup = new Group(container, SWT.NONE);
		performAnalysisGroup.setLayout(new GridLayout(4, false));
		GridData gd_performAnalysisGroup = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 4);
		gd_performAnalysisGroup.heightHint = 91;
		performAnalysisGroup.setLayoutData(gd_performAnalysisGroup);
		
		Button performPerSiteRadio = new Button(performAnalysisGroup, SWT.RADIO);
		performPerSiteRadio.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		performPerSiteRadio.setTouchEnabled(false);
		performPerSiteRadio.setSelection(true);
		performPerSiteRadio.setGrayed(false);
		performPerSiteRadio.setEnabled(true);
		performPerSiteRadio.setText("Perform Analysis Per Environment");
		
		performMultiRadio = new Button(performAnalysisGroup, SWT.RADIO);
		performMultiRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedNumVars = txtEnvVar.getText();
				if(!txtEnvVar.getText().isEmpty()){
					String[] levelItems = DataManipulationManager.getEnvtLevels(selectedNumVars, file);
						if(levelItems.length<2){
							factorVarList.add(selectedNumVars);
							txtEnvVar.setText("");
							MessageDialog.openWarning(getShell(), "Invalid factor", "The environment factor for a Multi-Environment Analysis should have atleast 2(two) levels.\n\n" +
									selectedNumVars+" has 1 level only. \n\n" +
									"Please choose a different environment factor.");
						}
					}

			}
		});
		performMultiRadio.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		performMultiRadio.setText("Perform Multiple Environment Analysis");
		
		btnAddEnv = new Button(performAnalysisGroup, SWT.NONE);
		btnAddEnv.setEnabled(false);
		GridData gd_btnAddEnv = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddEnv.widthHint = 62;
		btnAddEnv.setLayoutData(gd_btnAddEnv);
		btnAddEnv.setText("Add");
		
		lblEnv = new Label(performAnalysisGroup, SWT.NONE);
		GridData gd_lblEnv = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblEnv.widthHint = 75;
		lblEnv.setLayoutData(gd_lblEnv);
		lblEnv.setText("Environment:");
		
		txtEnvVar = new Text(performAnalysisGroup, SWT.BORDER);
		txtEnvVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtEnvVar.setEditable(false);
		GridData gd_txtEnvVar = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_txtEnvVar.widthHint = 206;
		txtEnvVar.setLayoutData(gd_txtEnvVar);
		
		Label lblFactors = new Label(container, SWT.NONE);
		lblFactors.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		lblFactors.setText("Factors:");
		
		moveBtn = new Button(container, SWT.NONE);
		
		GridData gd_moveBtn = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_moveBtn.heightHint = 24;
		gd_moveBtn.widthHint = 85;
		moveBtn.setLayoutData(gd_moveBtn);
		moveBtn.setText("Set to Factor");
		
		
		factorVarList = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		factorVarList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0){
					enableFactorButtons(true);
					enableNumericButtons(false);
					
				}
			}
		});
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 3);
		gd_factorVarList.widthHint = 53;
		gd_factorVarList.heightHint = 239;
		factorVarList.setLayoutData(gd_factorVarList);
		factorVarList.setItems(factorVariables);
		
		Group dynamicGroup = new Group(container, SWT.NONE);
		dynamicGroup.setLayout(new GridLayout(4, false));
		GridData gd_dynamicGroup = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		gd_dynamicGroup.widthHint = 368;
		gd_dynamicGroup.heightHint = 178;
		dynamicGroup.setLayoutData(gd_dynamicGroup);
		
		btnAddTester = new Button(dynamicGroup, SWT.NONE);
		btnAddTester.setEnabled(false);
		GridData gd_btnAddTester = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddTester.widthHint = 62;
		btnAddTester.setLayoutData(gd_btnAddTester);
		btnAddTester.setText("Add");
		
		lblTester = new Label(dynamicGroup, SWT.NONE);
		GridData gd_lblTester = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblTester.widthHint = 66;
		lblTester.setLayoutData(gd_lblTester);
		lblTester.setText("Tester:");
		
		txtTesterVar = new Text(dynamicGroup, SWT.BORDER);
		txtTesterVar.setEditable(false);
		txtTesterVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_txtTesterVar = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_txtTesterVar.widthHint = 206;
		txtTesterVar.setLayoutData(gd_txtTesterVar);
		txtTesterVar.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// If an item is double-clicked from the replicate Variable List
					testerLevels.removeAll();
					txtP1Var.setText("");
					txtP2Var.setText("");
					txtF1Var.setText("");
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
		
		Label lblLevelsOfTester = new Label(dynamicGroup, SWT.NONE);
		lblLevelsOfTester.setText("Levels of Tester:");
		new Label(dynamicGroup, SWT.NONE);
		new Label(dynamicGroup, SWT.NONE);
		new Label(dynamicGroup, SWT.NONE);
		
		testerLevels = new List(dynamicGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		testerLevels.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		testerLevels.addListener(SWT.MouseDown, new Listener(){
			
			@Override
			public void handleEvent(Event event) {
				if(testerLevels.getSelectionIndex()>-1){
					enableNumericButtons(false);
					enableFactorButtons(false);
					txtP1Var.setSelection(-1);
					txtP2Var.setSelection(-1);
					txtF1Var.setSelection(-1);
					 
					 enableTesterButtons(true);
					 
				}
			}
		});
		btnAddP1 = new Button(dynamicGroup, SWT.NONE);
		GridData gd_btnAddP1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddP1.widthHint = 52;
		btnAddP1.setLayoutData(gd_btnAddP1);
		btnAddP1.setText("Add");
		
		lblP1 = new Label(dynamicGroup, SWT.NONE);
		lblP1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblP1.setText("P1:");
		
		txtP1Var = new Text(dynamicGroup, SWT.BORDER);
		txtP1Var.setEditable(false);
		txtP1Var.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtP1Var.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		btnAddP2 = new Button(dynamicGroup, SWT.NONE);
		GridData gd_btnAddP2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddP2.widthHint = 52;
		btnAddP2.setLayoutData(gd_btnAddP2);
		btnAddP2.setText("Add");
		
		lblP2 = new Label(dynamicGroup, SWT.NONE);
		lblP2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblP2.setText("P2:");
		
		txtP2Var = new Text(dynamicGroup, SWT.BORDER);
		txtP2Var.setEditable(false);
		txtP2Var.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtP2Var.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		btnAddF1 = new Button(dynamicGroup, SWT.NONE);
		GridData gd_btnAddF1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddF1.widthHint = 51;
		btnAddF1.setLayoutData(gd_btnAddF1);
		btnAddF1.setText("Add");
		
		lblF1 = new Label(dynamicGroup, SWT.NONE);
		lblF1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblF1.setText("F1:");
		
		txtF1Var = new Text(dynamicGroup, SWT.BORDER);
		txtF1Var.setEditable(false);
		txtF1Var.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtF1Var.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		btnAddF2 = new Button(dynamicGroup, SWT.NONE);
		btnAddF2.setEnabled(false);
		GridData gd_btnAddF2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddF2.widthHint = 62;
		btnAddF2.setLayoutData(gd_btnAddF2);
		btnAddF2.setText("Add");
		
		lblF2 = new Label(dynamicGroup, SWT.NONE);
		GridData gd_lblF2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblF2.widthHint = 21;
		lblF2.setLayoutData(gd_lblF2);
		lblF2.setText("F2:");
		
		txtF2Var = new Text(dynamicGroup, SWT.BORDER);
		txtF2Var.setEditable(false);
		txtF2Var.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtF2Var.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		

		getShell().setText(getDialogName(analysisType, file));
		
		Group group_3 = new Group(container, SWT.NONE);
		group_3.setLayout(new GridLayout(3, false));
		GridData gd_group_3 = new GridData(SWT.FILL, SWT.FILL, true, false, 5, 1);
		gd_group_3.heightHint = 162;
		group_3.setLayoutData(gd_group_3);
		
		btnAddBlock = new Button(group_3, SWT.NONE);
		btnAddBlock.setEnabled(false);
		GridData gd_btnAddBlock = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddBlock.widthHint = 62;
		btnAddBlock.setLayoutData(gd_btnAddBlock);
		btnAddBlock.setText("Add");
		
		lblBlock = new Label(group_3, SWT.NONE);
		GridData gd_lblBlock = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblBlock.widthHint = 75;
		lblBlock.setLayoutData(gd_lblBlock);
		lblBlock.setText("Block:");
		
		txtBlockVar = new Text(group_3, SWT.BORDER);
		txtBlockVar.setEditable(false);
		txtBlockVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtBlockVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		
		btnAddRep = new Button(group_3, SWT.NONE);
		btnAddRep.setEnabled(false);
		GridData gd_btnAddRep = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddRep.widthHint = 62;
		btnAddRep.setLayoutData(gd_btnAddRep);
		btnAddRep.setText("Add");
		
		lblRep = new Label(group_3, SWT.NONE);
		GridData gd_lblRep = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblRep.widthHint = 75;
		lblRep.setLayoutData(gd_lblRep);
		lblRep.setText("Replicate:");
		
		txtRepVar = new Text(group_3, SWT.BORDER);
		txtRepVar.setEditable(false);
		txtRepVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtRepVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		btnAddRow = new Button(group_3, SWT.NONE);
		btnAddRow.setEnabled(false);
		btnAddRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddRow.setText("Add");
		
		lblRow = new Label(group_3, SWT.NONE);
		lblRow.setEnabled(false);
		lblRow.setText("Row:");
		
		txtRowVar = new Text(group_3, SWT.BORDER);
		txtRowVar.setEditable(false);
		txtRowVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtRowVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		btnAddCol = new Button(group_3, SWT.NONE);
		btnAddCol.setEnabled(false);
		btnAddCol.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddCol.setText("Add");
		
		lblCol = new Label(group_3, SWT.NONE);
		lblCol.setEnabled(false);
		lblCol.setText("Column:");
		
		txtColVar = new Text(group_3, SWT.BORDER);
		txtColVar.setEditable(false);
		txtColVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtColVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Button button_4 = new Button(group_3, SWT.NONE);
		button_4.setEnabled(false);
		GridData gd_button_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button_4.widthHint = 62;
		button_4.setLayoutData(gd_button_4);
		button_4.setText("Add");
		
		Label lblIndividual = new Label(group_3, SWT.NONE);
		GridData gd_lblIndividual = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblIndividual.widthHint = 75;
		lblIndividual.setLayoutData(gd_lblIndividual);
		lblIndividual.setEnabled(false);
		lblIndividual.setText("Individual:");
		
		List list_8 = new List(group_3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_list_8 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_list_8.heightHint = 20;
		gd_list_8.widthHint = 169;
		list_8.setLayoutData(gd_list_8);
		
		Label lblLevelOfSignificance = new Label(container, SWT.NONE);
		GridData gd_lblLevelOfSignificance = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblLevelOfSignificance.widthHint = 108;
		lblLevelOfSignificance.setLayoutData(gd_lblLevelOfSignificance);
		lblLevelOfSignificance.setText("Level of Significance: ");
		
		significanceLevel = new Text(container, SWT.BORDER);
		significanceLevel.setText("0.05");
		significanceLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		disable("replicate");

		initializeForm();
		return container;
	}
	void initializeForm(){
		dlgManager.initializeSingleSelectionList(factorVarList, txtEnvVar,moveBtn, btnAddEnv, new EnvVarValidator(factorVarList,  performMultiRadio.getSelection(),  file,  txtEnvVar, lblEnv));
		dlgManager.initializeSingleSelectionList(factorVarList, txtTesterVar,moveBtn, btnAddTester, new testerVarValidator());
		dlgManager.initializeSingleSelectionList(testerLevels, txtP1Var,moveBtn, btnAddP1, new TextVarValidator(txtP1Var, lblP1));
		dlgManager.initializeSingleSelectionList(testerLevels, txtP2Var,moveBtn, btnAddP2, new TextVarValidator(txtP2Var, lblP2));
		dlgManager.initializeSingleSelectionList(testerLevels, txtF1Var,moveBtn, btnAddF1, new TextVarValidator(txtF1Var, lblF1));
		dlgManager.initializeSingleSelectionList(factorVarList, txtF2Var, moveBtn,btnAddF2, new TextVarValidator(txtF2Var, lblF2));
		dlgManager.initializeSingleSelectionList(factorVarList, txtBlockVar, moveBtn,btnAddBlock, new TextVarValidator(txtBlockVar, lblBlock));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRepVar,moveBtn, btnAddRep, new TextVarValidator(txtRepVar, lblRep));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRowVar,moveBtn, btnAddRow, new TextVarValidator(txtRowVar, lblRow));
		dlgManager.initializeSingleSelectionList(factorVarList, txtColVar,moveBtn, btnAddCol, new TextVarValidator(txtColVar, lblCol));
		dlgManager.initializeVariableMoveList(numVarList, factorVarList, moveBtn, file.getAbsolutePath());
		dlgManager.initializeSingleButtonList(numVarList, responseVarList, moveBtn,addBtn);
	}
	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
	}

	protected void reset() {
		// TODO Auto-generated method stub
		 testerLevels.removeAll();
		 numVarList.removeAll();
		 factorVarList.removeAll();
		 responseVarList.removeAll();
		 txtRepVar.setText("");
		 txtBlockVar.setText("");
		 txtEnvVar.setText("");
		 txtTesterVar.setText("");
		 txtF2Var.setText("");
		 txtP1Var.setText("");
		 txtP2Var.setText("");
		 txtF1Var.setText("");
		 txtRowVar.setText("");
		 txtColVar.setText("");
	}

	
	public void enableFactorButtons(boolean state){
		 //unselect factor fields
		 txtTesterVar.setSelection(-1);
		 txtF2Var.setSelection(-1);
		 txtRepVar.setSelection(-1);
		 txtBlockVar.setSelection(-1);
		 txtEnvVar.setSelection(-1);
		 txtP1Var.setSelection(-1);
		 txtP2Var.setSelection(-1);
		 txtF1Var.setSelection(-1);
		 txtRowVar.setSelection(-1);
		 txtColVar.setSelection(-1);
			
		 if(state){//if enable all buttons
			 //change button text
			 btnAddF2.setText("Add");
			 btnAddRep.setText("Add");
			 btnAddBlock.setText("Add");
			 btnAddEnv.setText("Add");
			 btnAddTester.setText("Add");
			 btnAddRow.setText("Add");
			 btnAddCol.setText("Add");
			 
			 
			 //enable empty fields
			if(txtEnvVar.getText().isEmpty()) btnAddEnv.setEnabled(state);
			if(txtTesterVar.getText().isEmpty())  btnAddTester.setEnabled(state);
			if(txtF2Var.getText().isEmpty()) btnAddF2.setEnabled(state);
			if(txtRepVar.getText().isEmpty()&& lblRep.getEnabled())  btnAddRep.setEnabled(state);
			if(txtBlockVar.getText().isEmpty() && lblBlock.getEnabled())  btnAddBlock.setEnabled(state);
			if(txtRowVar.getText().isEmpty() && lblRow.getEnabled())  btnAddRow.setEnabled(state);
			if(txtColVar.getText().isEmpty()  && lblCol.getEnabled()) btnAddCol.setEnabled(state);
		 }
		 else{
			 //disable all buttons
			 btnAddRep.setEnabled(state);
			 btnAddBlock.setEnabled(state);
			 btnAddRow.setEnabled(state);
			 btnAddCol.setEnabled(state);
			 btnAddTester.setEnabled(state);
			 btnAddF2.setEnabled(state);
			 btnAddEnv.setEnabled(state);
			 btnAddP1.setEnabled(state);
			 btnAddP2.setEnabled(state);
			 btnAddF1.setEnabled(state);
		 }
	}
	public void enableTesterButtons(boolean state){
		 //unselect factor fields
		
		 txtP1Var.setSelection(-1);
		 txtP2Var.setSelection(-1);
		 txtF1Var.setSelection(-1);
			
		 if(state){//if enable all buttons
			 //change button text
			
			 btnAddP1.setText("Add");
			 btnAddP2.setText("Add");
			 btnAddF1.setText("Add");
			 
			 
			 //enable empty fields
			if(txtP1Var.getText().isEmpty())  btnAddP1.setEnabled(state);
			if(txtP2Var.getText().isEmpty())  btnAddP2.setEnabled(state);
			if(txtF1Var.getText().isEmpty()) btnAddF1.setEnabled(state);
		 }
		 else{
			 //disable all buttons
			 btnAddP1.setEnabled(state);
			 btnAddP2.setEnabled(state);
			 btnAddF1.setEnabled(state);
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
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
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
	
	protected void ttcDialogSetOpenState(String analysisType2,
			boolean state) {
		// TODO Auto-generated method stub
		
	}

	private String getDialogName(String analysisType2, File file2) {
		// TODO Auto-generated method stub
		String dialogName = "TripleTestCross";
		if(analysisType2.equals("TripleTestCross")) dialogName="Triple Test Cross (no parent): "+dataManipulationManager.getDisplayFileName(file2.getAbsolutePath());
		return dialogName;
	}
	
	@Override
	protected void okPressed(){
		pressedOkButton=true;
		//check if required fields are empty
		if(responseVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please add a Response variable.");
		}
		else if(performMultiRadio.getSelection() && txtEnvVar.getText().isEmpty()){//if perform for multiple environment
				MessageDialog.openError(getShell(), "Error", "An environment factor is required for a multiple environment analysis.\n\nPlease add an environment factor.");
		}
		else if(txtTesterVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a Tester factor.");
		}
		else if(txtP1Var.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please specify the level of Tester which corresponds to P1.");
		}
		else if(txtP2Var.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please specify the level of Tester which corresponds to P2.");
		}
		else if(txtF1Var.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please specify the level of Tester which corresponds to F1.");
		}
		else if(txtF2Var.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add an F2 factor.");
		}
		else if(txtRepVar.getText().isEmpty() && lblRep.getEnabled()){
			MessageDialog.openError(getShell(), "Error", "Please add a Replicate factor.");
		}
		else if(txtBlockVar.getText().isEmpty() && lblBlock.getEnabled()){
			MessageDialog.openError(getShell(), "Error", "Please add a Block factor.");
		}
		else if(txtRowVar.getText().isEmpty() && lblRow.getEnabled()){
			MessageDialog.openError(getShell(), "Error", "Please add a Row factor.");
		}
		else if(txtColVar.getText().isEmpty() && lblCol.getEnabled()){
			MessageDialog.openError(getShell(), "Error", "Please add a Column factor.");
		}
		//call RJAVA function here
		else{
			//specify parameters
			respvars = responseVarList.getItems();
			tester = txtTesterVar.getText();
			f2lines = txtF2Var.getText();
			environment="NULL";
			if(lblRep.getEnabled())rep = txtRepVar.getText();
			if(lblBlock.getEnabled())block = txtBlockVar.getText();
			if(!txtColVar.getText().isEmpty()) column = txtColVar.getText();
			if(!txtRowVar.getText().isEmpty()) row = txtRowVar.getText();
			if(!txtEnvVar.getText().isEmpty()) environment = txtEnvVar.getText();
			individual = "NULL";
			codeP1 = txtP1Var.getText();
			codeP2 = txtP2Var.getText();
			codeF1 = txtF1Var.getText();
			alpha = significanceLevel.getText();
			design = designs[designCombo.getSelectionIndex()];
			String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");
			File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);
	
			String newOutputFileName ="output.txt";
			String newFileName = outputFolder.getAbsolutePath().toString().replaceAll("\\\\+", "/") + "/" + newOutputFileName;
			OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Triple Test Cross (no parent) Analysis");
			rInfo.open();
			if(performMultiRadio.getSelection())ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doTTCMETest(dataFileName, newFileName, design, respvars, tester, f2lines, rep, block, row, column, individual, environment, codeP1, codeP2, codeF1, alpha);
			else ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doTTCTest(dataFileName, newFileName, design, respvars, tester, f2lines, rep, block, row, column, individual, environment, codeP1, codeP2, codeF1, alpha);
			rInfo.close();
			WindowDialogControlUtil.hideAllWindowDialog();
			
			PBToolAnalysisUtilities.openFolder(outputFolder);
		}
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(594, 738);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	public class testerVarValidator extends DialogListValidator{

		public testerVarValidator() {
			super();
			
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public boolean validate(){

			return true;
			
		}
		@Override
		public boolean performAddProcess(){
			String selectedNumVars[] = factorVarList.getSelection();
			
			String[] levelItems = DataManipulationManager.getEnvtLevels(selectedNumVars[0], file);
			if(levelItems.length==3){
				testerLevels.setItems(levelItems);
			}
			else{
				MessageDialog.openWarning(getShell(), "Invalid factor", "The tester factor should have three levels.\n" +
								selectedNumVars[0]+" has "+ levelItems.length + " level(s). \n\n" +
								"Please choose a different tester factor.");

				return false;
			}
			return true;
		}
		
		@Override
		public boolean performRemoveProcess(){
			String selectedNumVars = txtTesterVar.getText();
			factorVarList.add(selectedNumVars);
			testerLevels.removeAll();
			txtP1Var.setText("");
			txtP2Var.setText("");
			txtF1Var.setText("");
			txtTesterVar.setText("");
			return true;
		}
	}
}
