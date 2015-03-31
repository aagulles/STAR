package org.irri.breedingtool.pbtools.analysis.matingdesign.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TreeItem;
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
import org.irri.breedingtool.star.analysis.dialog.ChiSquareGoodnessOfFitDialog.goodnessOfFitListValidator;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.DialogListValidator;
import org.irri.breedingtool.utility.PBToolAnalysisUtilities;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.irri.breedingtool.utility.EnvVarValidator;
import org.irri.breedingtool.utility.TextVarValidator;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.wb.swt.SWTResourceManager;

public class NorthCarolinaExperimentsDialog extends Dialog {
	private String analysisType;
	private DialogFormUtility dlgManager = new DialogFormUtility();
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private ArrayList<String> varInfo;
	private File file;
	private String[] factorVariables;
	private String[] numericVariables;
	private List numVarList;
	private Button addBtn;
	private Button moveBtn;
	private List factorVarList;
	private Button btnAddRep;
	private Button btnAddMale;
	private Button btnAddFemale;
	private Button btnAddEnv;
	private Button performMultiRadio;
	private List responseVarList;
	private Combo designCombo;
	private String[] designs={"CRD","RCB","Alpha","RowColumn"};
	private Button btnInbred;
	private Button btnAddBlock;
	private Label lblBlock;
	private Label lblRep;
	private String design= "NULL";
	private String[] respvars;
	private String female= "NULL";
	private String male= "NULL";
	private String rep= "NULL";
	private String block= "NULL";
	private String row = "NULL";
	private String column = "NULL";
	private String inbred= "TRUE";
	private String environment= "NULL";
	private String individual= "NULL";
	private Button performPerSiteRadio;
	private ShellListener newShellListener;
	protected boolean reFocused;
	private boolean pressedOkButton;
	private String dialogTitle;
	private Button btnCrossed;
	private Button btnAddRow;
	private Label lblRow;
	private Button btnAddCol;
	private Label lblCol;
	private Text txtEnvVar;
	private Label lblEnvironment;
	private Text txtFemaleVar;
	private Text txtMaleVar;
	private Text txtBlockVar;
	private Text txtRepVar;
	private Text txtRowVar;
	private Text txtColVar;
	private Text text;
	private Label lblFemale;
	private Label lblMale;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NorthCarolinaExperimentsDialog(Shell parentShell, String analysisType, File file) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		setBlockOnOpen(false);
		this.analysisType = analysisType;
		this.file = file;
		setFactors();
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {

		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 7;

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

		Group group = new Group(container, SWT.NONE);
		group.setLayout(new GridLayout(2, false));
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		gd_group.heightHint = 10;
		gd_group.widthHint = 318;
		group.setLayoutData(gd_group);

		Label lblTypeOfDesign = new Label(group, SWT.NONE);
		lblTypeOfDesign.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, true, 2, 1));
		lblTypeOfDesign.setText("TYPE OF DESIGN:");

		designCombo = new Combo(group, SWT.READ_ONLY);
		designCombo.setItems(new String[] {"Completely Randomized Design (CRD)", "Randomized Complete Block (RCB)", "Alpha-Lattice", "Row-Column"});
		designCombo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
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

		if(!analysisType.equals("NCIII")){
			btnInbred = new Button(group, SWT.RADIO);
			btnInbred.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					inbred = "TRUE";
				}
			});
			btnInbred.setSelection(true);
			btnInbred.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
			btnInbred.setText("Inbred");

			btnCrossed = new Button(group, SWT.RADIO);
			btnCrossed.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					inbred = "FALSE";
				}
			});
			btnCrossed.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
			btnCrossed.setText("Crossed");
		}
		Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayout(new GridLayout(1, false));
		GridData gd_group_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_group_1.heightHint = 44;
		gd_group_1.widthHint = 104;
		group_1.setLayoutData(gd_group_1);

		Label lblDataInput = new Label(group_1, SWT.NONE);
		lblDataInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lblDataInput.setText("DATA INPUT:");

		Button btnPlotMeans = new Button(group_1, SWT.RADIO);
		btnPlotMeans.setSelection(true);
		btnPlotMeans.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		btnPlotMeans.setText("Plot Means");

		Button btnDataMeans = new Button(group_1, SWT.RADIO);
		btnDataMeans.setEnabled(false);
		btnDataMeans.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		btnDataMeans.setText("Individual Values");

		Label lblNumericVariables = new Label(container, SWT.NONE);
		GridData gd_lblNumericVariables = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_lblNumericVariables.widthHint = 112;
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
		GridData gd_numVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_numVarList.heightHint = 50;
		gd_numVarList.widthHint = 45;
		numVarList.setLayoutData(gd_numVarList);

		addBtn = new Button(container, SWT.NONE);
		addBtn.setEnabled(false);
		GridData gd_addBtn = new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1);
		gd_addBtn.widthHint = 57;
		addBtn.setLayoutData(gd_addBtn);
		addBtn.setText("Add");
		numVarList.setItems(numericVariables);

		responseVarList = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_responseVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_responseVarList.heightHint = 50;
		responseVarList.setLayoutData(gd_responseVarList);

		Label lblFactors = new Label(container, SWT.NONE);
		GridData gd_lblFactors = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblFactors.widthHint = 65;
		lblFactors.setLayoutData(gd_lblFactors);
		lblFactors.setText("Factors:");

		moveBtn = new Button(container, SWT.NONE);
		GridData gd_moveBtn = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_moveBtn.heightHint = 24;
		gd_moveBtn.widthHint = 90;
		moveBtn.setLayoutData(gd_moveBtn);
		moveBtn.setText("Set to Factor");

		Group performAnalysisGroup = new Group(container, SWT.NONE);
		performAnalysisGroup.setLayout(new GridLayout(4, false));
		GridData gd_performAnalysisGroup = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 2);
		gd_performAnalysisGroup.heightHint = 28;
		gd_performAnalysisGroup.widthHint = 71;
		performAnalysisGroup.setLayoutData(gd_performAnalysisGroup);

		performPerSiteRadio = new Button(performAnalysisGroup, SWT.RADIO);
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
					if(!txtEnvVar.getText().isEmpty()){//envVarList list already has a variable
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

		lblEnvironment = new Label(performAnalysisGroup, SWT.NONE);
		GridData gd_lblEnvironment = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_lblEnvironment.widthHint = 50;
		lblEnvironment.setLayoutData(gd_lblEnvironment);
		lblEnvironment.setText("Environment:");
		
		txtEnvVar = new Text(performAnalysisGroup, SWT.BORDER);
		txtEnvVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtEnvVar.setEditable(false);
		GridData gd_txtEnvVar = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_txtEnvVar.widthHint = 206;
		txtEnvVar.setLayoutData(gd_txtEnvVar);

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
		dynamicGroup.setLayout(new GridLayout(3, false));
		GridData gd_dynamicGroup = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		gd_dynamicGroup.heightHint = 20;
		dynamicGroup.setLayoutData(gd_dynamicGroup);

		btnAddFemale = new Button(dynamicGroup, SWT.NONE);

		btnAddFemale.setEnabled(false);
		GridData gd_btnAddFemale = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddFemale.widthHint = 62;
		btnAddFemale.setLayoutData(gd_btnAddFemale);
		btnAddFemale.setText("Add");

		lblFemale = new Label(dynamicGroup, SWT.NONE);
		GridData gd_lblFemale = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblFemale.widthHint = 62;
		lblFemale.setLayoutData(gd_lblFemale);
		lblFemale.setText("Female:");
		
		txtFemaleVar = new Text(dynamicGroup, SWT.BORDER);
		txtFemaleVar.setEditable(false);
		txtFemaleVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtFemaleVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		btnAddMale = new Button(dynamicGroup, SWT.NONE);
		btnAddMale.setEnabled(false);
		GridData gd_btnAddMale  = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddMale.widthHint = 62;
		btnAddMale.setLayoutData(gd_btnAddMale);
		
		btnAddMale.setText("Add");

		lblMale = new Label(dynamicGroup, SWT.NONE);
		GridData gd_lblMale = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblMale.widthHint = 62;
		lblMale.setLayoutData(gd_lblMale);
		lblMale.setText("Male:");
		
		txtMaleVar = new Text(dynamicGroup, SWT.BORDER);
		txtMaleVar.setEditable(false);
		txtMaleVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtMaleVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		if(analysisType.equals("NCIII")){
			lblFemale.setText("Tester:");
			lblMale.setText("F2:");
		}
		dialogTitle = getDialogName(analysisType);
		dialogTitle = dialogTitle +": "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath());
		parent.getShell().setText(dialogTitle);

		Group group_3 = new Group(container, SWT.NONE);
		group_3.setLayout(new GridLayout(3, false));
		GridData gd_group_3 = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		gd_group_3.heightHint = 100;
		group_3.setLayoutData(gd_group_3);

		btnAddBlock = new Button(group_3, SWT.NONE);
		btnAddBlock.setEnabled(false);
		GridData gd_btnAddBlock  = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddBlock.widthHint = 62;
		btnAddBlock.setLayoutData(gd_btnAddBlock);
		btnAddBlock.setText("Add");

		lblBlock = new Label(group_3, SWT.NONE);
		GridData gd_lblBlock = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblBlock.widthHint = 62;
		lblBlock.setLayoutData(gd_lblBlock);
		lblBlock.setText("Block:");
		
		txtBlockVar = new Text(group_3, SWT.BORDER);
		txtBlockVar.setEditable(false);
		txtBlockVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtBlockVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		btnAddRep = new Button(group_3, SWT.NONE);
		
		btnAddRep.setEnabled(false);
		GridData gd_btnAddRep  = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddRep.widthHint = 62;
		btnAddRep.setLayoutData(gd_btnAddRep);
		btnAddRep.setText("Add");

		lblRep = new Label(group_3, SWT.NONE);
		GridData gd_lblRep = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblRep.widthHint = 62;
		lblRep.setLayoutData(gd_lblRep);
		lblRep.setText("Replicate:");
		
		txtRepVar = new Text(group_3, SWT.BORDER);
		txtRepVar.setEditable(false);
		txtRepVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtRepVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		btnAddRow = new Button(group_3, SWT.NONE);
		btnAddRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddRow.setText("Add");
		btnAddRow.setEnabled(false);

		lblRow = new Label(group_3, SWT.NONE);
		lblRow.setText("Row:");
		lblRow.setEnabled(false);
		
		txtRowVar = new Text(group_3, SWT.BORDER);
		txtRowVar.setEditable(false);
		txtRowVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtRowVar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		btnAddCol = new Button(group_3, SWT.NONE);
		btnAddCol.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddCol.setText("Add");
		btnAddCol.setEnabled(false);
		lblCol = new Label(group_3, SWT.NONE);
		lblCol.setText("Column:");
		lblCol.setEnabled(false);
		
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
		GridData gd_lblIndividual = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblIndividual.widthHint = 62;
		lblIndividual.setLayoutData(gd_lblIndividual);
		lblIndividual.setEnabled(false);
		lblIndividual.setText("Individual:");
		
		text = new Text(group_3, SWT.BORDER);
		text.setEditable(false);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 206;
		text.setLayoutData(gd_text);

		disable("replicate");
		initializeForm();
		return container;
	}
	
	void initializeForm(){
		dlgManager.initializeSingleSelectionList(factorVarList, txtEnvVar,moveBtn, btnAddEnv, new EnvVarValidator(factorVarList,  performMultiRadio.getSelection(),  file,  txtEnvVar, lblEnvironment));
		dlgManager.initializeSingleSelectionList(factorVarList, txtFemaleVar,moveBtn, btnAddFemale, new TextVarValidator(txtFemaleVar, lblFemale));
		dlgManager.initializeSingleSelectionList(factorVarList, txtMaleVar,moveBtn, btnAddMale, new TextVarValidator(txtMaleVar, lblMale));
		dlgManager.initializeSingleSelectionList(factorVarList, txtBlockVar,moveBtn, btnAddBlock, new TextVarValidator(txtBlockVar, lblBlock));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRepVar,moveBtn, btnAddRep, new TextVarValidator(txtRepVar, lblRep));
		dlgManager.initializeSingleSelectionList(factorVarList, txtRowVar,moveBtn, btnAddRow, new TextVarValidator(txtRowVar, lblRow));
		dlgManager.initializeSingleSelectionList(factorVarList, txtColVar,moveBtn, btnAddCol, new TextVarValidator(txtColVar, lblCol));
		dlgManager.initializeVariableMoveList(numVarList, factorVarList, moveBtn, file.getAbsolutePath());
		dlgManager.initializeSingleButtonList(numVarList, responseVarList, moveBtn,addBtn);
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

	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
	}

	protected void reset() {
		// TODO Auto-generated method stub
		txtMaleVar.setText("");
		txtFemaleVar.setText("");
		txtRepVar.setText("");
		txtBlockVar.setText("");
		txtEnvVar.setText("");
		txtColVar.setText("");
		txtRowVar.setText("");
		numVarList.removeAll();
		responseVarList.removeAll();
		factorVarList.removeAll();
	}

	private String getDialogName(String analysisType2) {
		// TODO Auto-generated method stub
		String dialogName = "North Carolina Experiment";
		if(analysisType2.equals("NCI")) dialogName="North Carolina Experiment I";
		else if(analysisType2.equals("NCII"))dialogName="North Carolina Experiment II";
		else if(analysisType2.equals("NCIII")) dialogName="North Carolina Experiment III";
		return dialogName;
	}

	public void enableFactorButtons(boolean state){
		//un-select factor fields
		txtFemaleVar.setSelection(-1);
		txtMaleVar.setSelection(-1);
		txtRepVar.setSelection(-1);
		txtBlockVar.setSelection(-1);
		txtEnvVar.setSelection(-1);
		txtRowVar.setSelection(-1);
		txtColVar.setSelection(-1);

		if(state){//if enable all buttons
			//change button text
			btnAddMale.setText("Add");
			btnAddRep.setText("Add");
			btnAddBlock.setText("Add");
			btnAddEnv.setText("Add");
			btnAddFemale.setText("Add");
			btnAddRow.setText("Add");
			btnAddCol.setText("Add");

			//enable empty fields
			if(txtEnvVar.getText().isEmpty()) btnAddEnv.setEnabled(state);
			if(txtRepVar.getText().isEmpty() && lblRep.getEnabled())  btnAddRep.setEnabled(state);
			if(txtBlockVar.getText().isEmpty()&& lblBlock.getEnabled())  btnAddBlock.setEnabled(state);
			if(txtFemaleVar.getText().isEmpty())  btnAddFemale.setEnabled(state);
			if(txtMaleVar.getText().isEmpty()) btnAddMale.setEnabled(state);
			if(txtRowVar.getText().isEmpty() && lblRow.getEnabled())  btnAddRow.setEnabled(state);
			if(txtColVar.getText().isEmpty()  && lblCol.getEnabled()) btnAddCol.setEnabled(state);
		}
		else{
			//disable all buttons

			btnAddRep.setEnabled(state);
			btnAddBlock.setEnabled(state);
			btnAddRow.setEnabled(state);
			btnAddCol.setEnabled(state);
			btnAddFemale.setEnabled(state);
			btnAddMale.setEnabled(state);
			btnAddEnv.setEnabled(state);
		}
	}

	public void enableNumericButtons(boolean state){
		addBtn.setEnabled(state);
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
		else if(txtFemaleVar.getText().isEmpty() && analysisType.equals("NCIII")){
			MessageDialog.openError(getShell(), "Error", "Please add a Tester factor.");
		}
		else if(txtMaleVar.getText().isEmpty() && analysisType.equals("NCIII")){
			MessageDialog.openError(getShell(), "Error", "Please add a F2 factor.");
		}
		else if(txtFemaleVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a Female factor.");
		}
		else if(txtMaleVar.getText().isEmpty()){
			MessageDialog.openError(getShell(), "Error", "Please add a Male factor.");
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
			System.out.println("okPressed");
			design = designs[designCombo.getSelectionIndex()];
			respvars = responseVarList.getItems();
			female = txtFemaleVar.getText();
			male = txtMaleVar.getText();
			environment="NULL";
			if(!txtRepVar.getText().isEmpty())rep = txtRepVar.getText();
			if(!txtBlockVar.getText().isEmpty())block = txtBlockVar.getText();
			if(!txtColVar.getText().isEmpty()) column = txtColVar.getText();
			if(!txtRowVar.getText().isEmpty()) row = txtRowVar.getText();
			if(!txtEnvVar.getText().isEmpty())environment = txtEnvVar.getText();
			individual = "NULL";
			design = designs[designCombo.getSelectionIndex()];
			String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");

			File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);

			String newFileName = outputFolder.getAbsolutePath().toString().replaceAll("\\\\+", "/") + "/output.txt";

			OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), getDialogName(analysisType));
			rInfo.open();

			if(analysisType.equals("NCI")){
				if(performPerSiteRadio.getSelection())ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doNC1Test(dataFileName, newFileName, design, respvars, female, male, rep, block, row, column, inbred, individual, environment);
				else ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doNC1METest(dataFileName, newFileName, design, respvars, female, male, rep, block, row, column, inbred, individual, environment);
			}
			else if(analysisType.equals("NCII")){
				if(performPerSiteRadio.getSelection())ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doNC2Test(dataFileName, newFileName, design, respvars, female, male, rep, block, row, column, inbred, individual, environment);
				else ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doNC2METest(dataFileName, newFileName, design, respvars, female, male, rep, block, row, column, inbred, individual, environment);
			}
			else if(analysisType.equals("NCIII")){
				if(performPerSiteRadio.getSelection()) ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doNC3Test(dataFileName, newFileName, design, respvars, female, male, rep, block, row, column, inbred, individual, environment);
				else 	ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doNC3METest(dataFileName, newFileName, design, respvars, female, male, rep, block, row, column, individual, environment);
			}

			rInfo.close();
			WindowDialogControlUtil.hideAllWindowDialog();

			PBToolAnalysisUtilities.openFolder(outputFolder);

		}
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

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(503, 630);
	}
	
}
