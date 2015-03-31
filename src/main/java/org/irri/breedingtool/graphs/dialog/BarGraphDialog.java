package org.irri.breedingtool.graphs.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.GraphsUtilities;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class BarGraphDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private List numVarList;
	private List factorVarList;
	private List responseVarList;
	private Button addBtn;
	private Button moveBtn;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private DialogFormUtility dlgManager = new DialogFormUtility();
	private String[] barsRepresentList={"freq", "mean", "median", "sum"};
	private Shell parentShell;
	//specify parameters (default values)
	private int maxLevels = 20;
	private String[] nVar = null; //but should be provided by user
	private String cVar = null;
	private String mTitle = null; 
	private String[] clustVars = null;
	private String[] yAxisLab = null; 
	private String xAxisLab = null; 
	private String[] minValue = null; 
	private String[] maxValue = null; 
	private String typeData = "raw"; 
	private String descStat = "mean"; //null if typeData = "sumStat"
	private String barsHoriz = "FALSE"; 
	private String barsClus = "TRUE"; 
	private String byVar = null; 
	private String errBars = "FALSE";
	private String typeErrBar = null; //"confInt" if errbars = TRUE;
	private int errMult = 1;
	private double confLev = 0.95;
	private int axisLabelStyle = 1;
	private String[] barColor = {"rgb(192,192,192, max = 255)"}; //default: RGB values for gray
	private String showLeg = "FALSE";
	private String legPos = null;; //"bottomright" if showLeg = "TRUE"
	private String legTitle = null; 
	private String legHoriz = "FALSE";
	private int legCol = 1;
	private String boxed = "FALSE"; 
	private String multGraphs = "FALSE";
	private int numRowsGraphs = 1;
	private int numColsGraphs = 1; 
	private String orientGraphs = "top-bottom"; // #c("left-right", "top-bottom"))
	private String showCatVarLevels = "FALSE"; 
	//	private int[] barLineAngle = null; //{0 - solid or horizontal, 45 - forward diagonal, 90 - vertical, 135 - backward diagonal
	private ArrayList<Integer> barDensityList = new ArrayList<Integer>();
	private int[] barDensity = {100}; //{0,10,10,10};
	private ArrayList<Integer> barLineAngleList = new ArrayList<Integer>();
	private int[] barLineAngle = {0,45};
	private String analysisType;
	private Table table;
	private TableColumn tableColumn;
	private Button addAxisBtn;
	private Button addGroupsBtn;
	private List axisVarList;
	private List groupsVarList;
	private Label barsRepresentLabel;
	private Button horizontalOrientationBtn;
	private Button verticalOrientationBtn;
	private Button clusteredStyleBtn;
	private Button stackedStyleBtn;
	private Button confidenceLvlBtn;
	private Button stdErrorBtn;
	private Button stdDevBtn;
	private Button displayErrorBars;
	private Label stdErrorLabel;
	private Label confidenceLvlLabel;
	private Label stdDevLabel;
	private Label styleLabel;
	private CCombo barsRepresentCombo;
	private Text mainTitleText;
	private Text categoryAxisText;
	private Text legTitleText;
	private Button summaryStatBtn;
	private TabItem tbtmModelSpecifications;
	private Label errorBarsLabel;
	private Spinner confidenceLvlValue;
	private Spinner stdErrorValue;
	private Spinner stdDevValue;
	private Button displayVarNamesBtn;
	private CCombo legPositionComo;
	private CCombo orientGraphsCombo;
	private Button rawDataBtn;
	private Button btnShowLegend;
	private Button btnDrawBoxAround;
	private Button btnDisplayMultipleGraphs;
	private Spinner numColsSpinner;
	private Spinner numRowsSpinner;
	private Composite compositeConfigureBars;
	private Label lblPosition;
	private Label lblTitle;
	private Label lblNewLabel;
	private Label lblNumberOfColumns;
	private Label lblOrientation;
	private Composite composite;
	private Table tableAxis;
	private TableColumn tableColumn_1;
	private TableColumn tableColumn_2;
	private TableColumn tableColumn_3;
	private TableColumn tableColumn_4;
	private Label label;
	private CCombo cmboAxisOrientation;
	private Label lblAlignment;
	private Label lblNewLabel_1;
	private List lstClusterBy;
	private Label lblClusterBarsBy;
	private Button addClusterBybtn;
	private Spinner numColsSpinner1;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public BarGraphDialog(Shell parentShell, String analysisType, File file) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		setBlockOnOpen(false);
		this.parentShell = parentShell;
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

		parent.getShell().setText("Bar Graph: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tbtmModelSpecifications = new TabItem(tabFolder, SWT.NONE);
		tbtmModelSpecifications.setText("Variable Definition");

		Composite modelComposite = new Composite(tabFolder, SWT.NONE);
		tbtmModelSpecifications.setControl(modelComposite);
		modelComposite.setLayout(new GridLayout(5, false));

		Label label_8 = new Label(modelComposite, SWT.NONE);
		label_8.setText("Input File Contains:");

		rawDataBtn = new Button(modelComposite, SWT.RADIO);
		rawDataBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableBarsRepresent(true);
			}
		});
		rawDataBtn.setText("Raw Data");
		rawDataBtn.setSelection(true);

		summaryStatBtn = new Button(modelComposite, SWT.RADIO);
		summaryStatBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableBarsRepresent(false);
			}
		});
		summaryStatBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		summaryStatBtn.setText("Summary Statistics");

		barsRepresentLabel = new Label(modelComposite, SWT.NONE);
		barsRepresentLabel.setText("Bars Represent:");

		barsRepresentCombo = new CCombo(modelComposite, SWT.BORDER);
		barsRepresentCombo.setItems(new String[] {"Frequency", "Mean", "Median", "Sum"});
		barsRepresentCombo.select(1);
		barsRepresentCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		barsRepresentCombo.setEditable(false);
		barsRepresentCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		barsRepresentCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(barsRepresentCombo.getSelectionIndex()==1){//if mean is selected
					if(clusteredStyleBtn.getSelection()) enableBarsRepresent(true);
				}
				else{//if mean was not selected
					enableDisplayErrorBars(false);
				}
			}
		});
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		Label lblNumericVariables = new Label(modelComposite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNumericVariables.setText("Numeric Variables:");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		Label lblResponseVariables = new Label(modelComposite, SWT.NONE);
		lblResponseVariables.setText("Variable(s):");

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
//				addItemToVarTable(numVarList.getSelection()[0]);
				addItemToTableAxis(numVarList.getSelection()[0]);
				dataManipulationManager.moveSelectedStringFromTo( numVarList, responseVarList,moveBtn);
				getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
				numVarList.remove(numVarList.getSelectionIndices());

//				if(responseVarList.getItemCount()>1) displayVarNamesBtn.setEnabled(true);
//				else displayVarNamesBtn.setEnabled(false);


				addBtn.setEnabled(false);
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
		addBtn.setForeground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
//						addItemToVarTable(selectedNumVars[i]);
						responseVarList.add(selectedNumVars[i]);
						addItemToTableAxis(selectedNumVars[i]);
//						getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
					}
					numVarList.remove(numVarList.getSelectionIndices());
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = responseVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
//						removeItemFromVarTable(responseVarList.getSelection()[0]);
						numVarList.add(selectedNumVars[i]);
						removeItemFromTableAxis(selectedNumVars[i]);
//						getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);

					}
					responseVarList.remove(responseVarList.getSelectionIndices());
				}

//				if(responseVarList.getItemCount()>1) displayVarNamesBtn.setEnabled(true);
//				else displayVarNamesBtn.setEnabled(false);
				
				enableNumericButtons(false);
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
				removeItemFromTableAxis(responseVarList.getSelection()[0]);
				dataManipulationManager.moveSelectedStringFromTo( responseVarList, numVarList);
				getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
				responseVarList.remove(responseVarList.getSelectionIndices());

//				if(responseVarList.getItemCount()>1) displayVarNamesBtn.setEnabled(true);
//				else displayVarNamesBtn.setEnabled(false);

				addBtn.setEnabled(false);
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
		GridData gd_moveBtn = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_moveBtn.heightHint = 24;
		gd_moveBtn.widthHint = 90;
		moveBtn.setLayoutData(gd_moveBtn);
		moveBtn.setText("Set to Factor");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		Label lblCategorizeBy = new Label(modelComposite, SWT.NONE);
		lblCategorizeBy.setText("Categorize by:");

		factorVarList = new List(modelComposite, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 5);
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
					String isNumeric = dataManipulationManager.isNumeric(file.getAbsolutePath().replaceAll("\\\\","/"), s[0]);
					if(isNumeric.equals("TRUE")){
						moveBtn.setText("Set to Numeric");
						moveBtn.setEnabled(true);
					}
					else moveBtn.setEnabled(false);
					enableFactorButtons(true);

				}
			}
		});

		addAxisBtn = new Button(modelComposite, SWT.NONE);
		addAxisBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, axisVarList);
					categoryAxisText.setText(axisVarList.getItems()[0]);
					String[] catVar = DataManipulationManager.getEnvtLevels(axisVarList.getItem(0), file);
					System.out.println(catVar);
					for(String var : catVar)addItemToVarTable(var);
					getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
					numColsSpinner1.setEnabled(true);
				}
				else{//if the purpose is to remove
					String[] catVar = DataManipulationManager.getEnvtLevels(axisVarList.getItem(0), file);
					System.out.println(catVar);
					for(String var : catVar)removeItemFromVarTable(var);
					dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
					categoryAxisText.setText("");
					getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
					numColsSpinner1.setEnabled(false);
				}
				enableFactorButtons(false);
				moveBtn.setEnabled(false);

			}
		});
		GridData gd_addAxisBtn = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
		gd_addAxisBtn.widthHint = 52;
		addAxisBtn.setLayoutData(gd_addAxisBtn);
		addAxisBtn.setText("Add");
		addAxisBtn.setEnabled(false);

		axisVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_axisVarList = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_axisVarList.heightHint = 5;
		axisVarList.setLayoutData(gd_axisVarList);
		axisVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
				addAxisBtn.setEnabled(false);
				numColsSpinner1.setEnabled(false);
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(axisVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addAxisBtn.setText("Remove");
					addAxisBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		
		lblClusterBarsBy = new Label(modelComposite, SWT.NONE);
		lblClusterBarsBy.setText("Cluster bars by:");
		
		addClusterBybtn = new Button(modelComposite, SWT.NONE);
		addClusterBybtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, lstClusterBy);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( lstClusterBy, factorVarList);
				}
				enableFactorButtons(false);
				moveBtn.setEnabled(false);
			}
		});
		addClusterBybtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		addClusterBybtn.setText("Add");
		addClusterBybtn.setEnabled(false);
		
		lstClusterBy = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstClusterBy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				dataManipulationManager.moveSelectedStringFromTo( lstClusterBy, factorVarList);
				addClusterBybtn.setEnabled(false);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(lstClusterBy.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addClusterBybtn.setText("Remove");
					addClusterBybtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		lstClusterBy.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		Label lblGroupBy = new Label(modelComposite, SWT.NONE);
		lblGroupBy.setText("Group by:");

		addGroupsBtn = new Button(modelComposite, SWT.NONE);
		addGroupsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, groupsVarList);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( groupsVarList, factorVarList);
				}
				enableFactorButtons(false);
				moveBtn.setEnabled(false);
			}
		});
		GridData gd_addGroupsBtn = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
		gd_addGroupsBtn.widthHint = 52;
		addGroupsBtn.setLayoutData(gd_addGroupsBtn);
		addGroupsBtn.setText("Add");
		addGroupsBtn.setEnabled(false);

		groupsVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_groupsVarList = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_groupsVarList.heightHint = 5;
		groupsVarList.setLayoutData(gd_groupsVarList);
		groupsVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( groupsVarList, factorVarList);
				addGroupsBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(groupsVarList.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					addGroupsBtn.setText("Remove");
					addGroupsBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		TabItem tbtmDisplay = new TabItem(tabFolder, SWT.NONE);
		tbtmDisplay.setText("Display Options");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmDisplay.setControl(composite_2);
		composite_2.setLayout(new GridLayout(6, false));

		Label lblMainTitle = new Label(composite_2, SWT.NONE);
		lblMainTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblMainTitle.setText("Main Title:");

		mainTitleText = new Text(composite_2, SWT.BORDER);
		int maxLen =45;
		mainTitleText.setTextLimit(maxLen);
		mainTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));

		Label lblCategoryAxisLabel = new Label(composite_2, SWT.NONE);
		lblCategoryAxisLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblCategoryAxisLabel.setText("Category Axis Label:");

		categoryAxisText = new Text(composite_2, SWT.BORDER);
		categoryAxisText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		displayVarNamesBtn = new Button(composite_2, SWT.CHECK);
		displayVarNamesBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		displayVarNamesBtn.setText("Display levels");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		Label lblValueAxisLabel = new Label(composite_2, SWT.NONE);
		lblValueAxisLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblValueAxisLabel.setText("Value Axis:");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		tableAxis = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tableAxis.setLinesVisible(true);
		tableAxis.setHeaderVisible(true);
		tableAxis.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_tableAxis = new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1);
		gd_tableAxis.heightHint = 119;
		tableAxis.setLayoutData(gd_tableAxis);
		
		tableColumn_1 = new TableColumn(tableAxis, SWT.NONE);
		tableColumn_1.setWidth(85);
		tableColumn_1.setText("Variable");
		
		tableColumn_2 = new TableColumn(tableAxis, SWT.NONE);
		tableColumn_2.setWidth(83);
		tableColumn_2.setText("Axis Label");
		
		tableColumn_3 = new TableColumn(tableAxis, SWT.NONE);
		tableColumn_3.setWidth(87);
		tableColumn_3.setText("Lower Limit");
		
		tableColumn_4 = new TableColumn(tableAxis, SWT.NONE);
		tableColumn_4.setWidth(85);
		tableColumn_4.setText("Upper Limit");
		
		label = new Label(composite_2, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label.setText("Axis Label Orientation:");
		
		cmboAxisOrientation = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
		cmboAxisOrientation.setItems(new String[] {"parallel to axis", "horizontal", "vertical", "perpendicular to axis"});
		cmboAxisOrientation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		cmboAxisOrientation.select(0);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		btnDrawBoxAround = new Button(composite_2, SWT.CHECK);
		btnDrawBoxAround.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		btnDrawBoxAround.setText("Draw box around plot");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		btnShowLegend = new Button(composite_2, SWT.CHECK);
		btnShowLegend.setSelection(true);
		btnShowLegend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnShowLegend.getSelection())enableShowLegend(true);
				else enableShowLegend(false);
			}
		});
		btnShowLegend.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		btnShowLegend.setText("Show legend");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
				lblPosition = new Label(composite_2, SWT.NONE);
				lblPosition.setText("Position:");
		
				legPositionComo = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
				legPositionComo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				legPositionComo.setEditable(false);
				legPositionComo.setItems(new String[] {"bottom", "bottom-left", "bottom-right", "center", "left", "right", "top", "top-left", "top-right"});
				legPositionComo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				legPositionComo.select(2);
		
		lblNewLabel_1 = new Label(composite_2, SWT.NONE);
		
		lblAlignment = new Label(composite_2, SWT.NONE);
		lblAlignment.setText("Number of columns:");

		
		numColsSpinner1 = new Spinner(composite_2, SWT.BORDER | SWT.READ_ONLY);
		numColsSpinner1.setEnabled(false);
		GridData gd_numColsSpinner1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_numColsSpinner1.widthHint = 10;
		numColsSpinner1.setLayoutData(gd_numColsSpinner1);
		numColsSpinner1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		numColsSpinner1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

					String selectedStrings = axisVarList.getItem(0);
					String[] catLevels = DataManipulationManager.getEnvtLevels(selectedStrings, file);
					maxLevels = catLevels.length;
					numColsSpinner1.setMaximum(maxLevels);
					numColsSpinner1.setMinimum(1);
				
			}
		});

		numColsSpinner1.setSelection(1);
		new Label(composite_2, SWT.NONE);
		
				lblTitle = new Label(composite_2, SWT.NONE);
				lblTitle.setText("Title:");
		
				legTitleText = new Text(composite_2, SWT.BORDER);
				legTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));

		btnDisplayMultipleGraphs = new Button(composite_2, SWT.CHECK);
		btnDisplayMultipleGraphs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnDisplayMultipleGraphs.getSelection()) displayMultipleGraphs(true);
				else displayMultipleGraphs(false);
			}
		});
		btnDisplayMultipleGraphs.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		btnDisplayMultipleGraphs.setText("Display multiple graphs in a page");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
				lblNewLabel = new Label(composite_2, SWT.NONE);
				lblNewLabel.setEnabled(false);
				lblNewLabel.setText("Number of rows:");
		
				numRowsSpinner = new Spinner(composite_2, SWT.BORDER | SWT.READ_ONLY);
				numRowsSpinner.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_numRowsSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_numRowsSpinner.widthHint = 10;
				numRowsSpinner.setLayoutData(gd_numRowsSpinner);
				numRowsSpinner.setEnabled(false);
				numRowsSpinner.setMaximum(5);
				numRowsSpinner.setMinimum(1);
				numRowsSpinner.setSelection(2);
		new Label(composite_2, SWT.NONE);
		
				lblNumberOfColumns = new Label(composite_2, SWT.NONE);
				lblNumberOfColumns.setEnabled(false);
				lblNumberOfColumns.setText("Number of columns:");
		
				numColsSpinner = new Spinner(composite_2, SWT.BORDER | SWT.READ_ONLY);
				numColsSpinner.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_numColsSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_numColsSpinner.widthHint = 10;
				numColsSpinner.setLayoutData(gd_numColsSpinner);
				numColsSpinner.setEnabled(false);
				numColsSpinner.setMaximum(5);
				numColsSpinner.setMinimum(1);
				numColsSpinner.setSelection(1);
		new Label(composite_2, SWT.NONE);
		
				lblOrientation = new Label(composite_2, SWT.NONE);
				lblOrientation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
				lblOrientation.setEnabled(false);
				lblOrientation.setText("Orientation:");
		
				orientGraphsCombo = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
				orientGraphsCombo.setEnabled(false);
				orientGraphsCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				orientGraphsCombo.setEditable(false);
				orientGraphsCombo.setItems(new String[] {"Left-to-right", "Top-to-bottom"});
				orientGraphsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
				orientGraphsCombo.select(1);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		TabItem tbtmBars = new TabItem(tabFolder, SWT.NONE);
		tbtmBars.setText("Other Options");

		composite = new Composite(tabFolder, SWT.NONE);
		tbtmBars.setControl(composite);
		composite.setLayout(new GridLayout(2, false));

		Label label_6 = new Label(composite, SWT.NONE);
		label_6.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		label_6.setText("CONFIGURE BARS:");

		compositeConfigureBars = new Composite(composite, SWT.BORDER);
		compositeConfigureBars.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		compositeConfigureBars.setLayout(new GridLayout(1, false));

		Composite composite_3 = new Composite(compositeConfigureBars, SWT.NONE);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_3.setLayout(new GridLayout(2, false));

		Label label_3 = new Label(composite_3, SWT.NONE);
		label_3.setText("Orientation:");
		new Label(composite_3, SWT.NONE);

		verticalOrientationBtn = new Button(composite_3, SWT.RADIO);
		verticalOrientationBtn.setText("Vertical");
		verticalOrientationBtn.setSelection(true);

		horizontalOrientationBtn = new Button(composite_3, SWT.RADIO);
		horizontalOrientationBtn.setText("Horizontal");

		Composite composite_4 = new Composite(compositeConfigureBars, SWT.NONE);
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_4.setLayout(new GridLayout(2, false));

		styleLabel = new Label(composite_4, SWT.NONE);
		styleLabel.setText("Style:");
		new Label(composite_4, SWT.NONE);

		clusteredStyleBtn = new Button(composite_4, SWT.RADIO);
		clusteredStyleBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(barsRepresentCombo.getEnabled() && barsRepresentCombo.getSelectionIndex()==1){ //mean is selected)
					enableDisplayErrorBars(true);
				}
			}
		});
		clusteredStyleBtn.setText("Clustered");
		clusteredStyleBtn.setSelection(true);

		stackedStyleBtn = new Button(composite_4, SWT.RADIO);
		stackedStyleBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				displayErrorBars.setSelection(false);
				enableDisplayErrorBars(false);
			}
		});
		stackedStyleBtn.setText("Stacked");

		Composite composite_5 = new Composite(compositeConfigureBars, SWT.NONE);
		composite_5.setLayout(new GridLayout(1, false));

		Label lblColorAndFill = new Label(composite_5, SWT.NONE);
		lblColorAndFill.setText("Color and Fill:");

		table = new Table(compositeConfigureBars, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.widthHint = 204;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(75);
		tblclmnNewColumn.setText("Level");

		TableColumn tblclmnNewColumnf = new TableColumn(table, SWT.NONE);
		tblclmnNewColumnf.setWidth(60);
		tblclmnNewColumnf.setText("Color");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(25);

		TableColumn tblclmnNewColumn3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn3.setWidth(91);
		tblclmnNewColumn3.setText("Fill Pattern");
		tblclmnNewColumn3.pack();

		TableColumn tblclmnNewColumn4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn4.setWidth(25);

		displayErrorBars = new Button(composite, SWT.CHECK);
		displayErrorBars.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(displayErrorBars.getSelection()){
					enableErrorBarsOptions(true);
					//					enableStyleOptions(false);
				}
				else{
					enableErrorBarsOptions(false);
					enableStyleOptions(true);
				}
			}
		});
		displayErrorBars.setText("Display Error Bars");

		errorBarsLabel = new Label(composite, SWT.NONE);
		errorBarsLabel.setEnabled(false);
		errorBarsLabel.setText("ERROR BARS:");

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		composite_1.setLayout(new GridLayout(2, false));

		confidenceLvlBtn = new Button(composite_1, SWT.RADIO);
		confidenceLvlBtn.setEnabled(false);
		confidenceLvlBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EnableConfidenceLevel(true);
				EnableStandardError(false);
				EnableStandardDeviation(false);
			}
		});
		confidenceLvlBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		confidenceLvlBtn.setText("Confidence Level");
		confidenceLvlBtn.setSelection(true);
		confidenceLvlBtn.setGrayed(true);

		confidenceLvlLabel = new Label(composite_1, SWT.NONE);
		confidenceLvlLabel.setEnabled(false);
		confidenceLvlLabel.setText("Level (%)");

		confidenceLvlValue = new Spinner(composite_1, SWT.BORDER);
		confidenceLvlValue.setEnabled(false);
		GridData gd_confidenceLvlValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_confidenceLvlValue.widthHint = 10;
		confidenceLvlValue.setLayoutData(gd_confidenceLvlValue);
		confidenceLvlValue.setPageIncrement(1);
		confidenceLvlValue.setMaximum(99);
		confidenceLvlValue.setMinimum(1);
		confidenceLvlValue.setSelection(95);

		stdErrorBtn = new Button(composite_1, SWT.RADIO);
		stdErrorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EnableConfidenceLevel(false);
				EnableStandardError(true);
				EnableStandardDeviation(false);
			}
		});
		stdErrorBtn.setEnabled(false);
		stdErrorBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		stdErrorBtn.setText("Standard Error");

		stdErrorLabel = new Label(composite_1, SWT.NONE);
		stdErrorLabel.setEnabled(false);
		stdErrorLabel.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		stdErrorLabel.setText("Multiplier");

		stdErrorValue = new Spinner(composite_1, SWT.BORDER);
		stdErrorValue.setEnabled(false);
		GridData gd_stdErrorValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_stdErrorValue.widthHint = 10;
		stdErrorValue.setLayoutData(gd_stdErrorValue);
		stdErrorValue.setPageIncrement(1);
		stdErrorValue.setMaximum(3);
		stdErrorValue.setMinimum(1);
		stdErrorValue.setSelection(1);

		stdDevBtn = new Button(composite_1, SWT.RADIO);
		stdDevBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EnableStandardError(false);
				EnableConfidenceLevel(false);
				EnableStandardDeviation(true);
			}
		});
		stdDevBtn.setEnabled(false);
		stdDevBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		stdDevBtn.setText("Standard Deviation");

		stdDevLabel = new Label(composite_1, SWT.NONE);
		stdDevLabel.setEnabled(false);
		stdDevLabel.setText("Multiplier");

		stdDevValue = new Spinner(composite_1, SWT.BORDER);
		stdDevValue.setEnabled(false);
		GridData gd_stdDevValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_stdDevValue.widthHint = 10;
		stdDevValue.setLayoutData(gd_stdDevValue);
		stdDevValue.setPageIncrement(1);
		stdDevValue.setMaximum(3);
		stdDevValue.setMinimum(1);
		stdDevValue.setSelection(1);
		
//		initializeForm();
		return container;
	}

//	void initializeForm(){
//		dlgManager.initializeSingleButtonList(factorVarList, lstClusterBy, addClusterBybtn);
//	}
	
	
	protected void EnableStandardDeviation(boolean state) {
		// TODO Auto-generated method stub
		stdDevLabel.setEnabled(state);
		stdDevValue.setEnabled(state);
	}

	protected void EnableStandardError(boolean state) {
		// TODO Auto-generated method stub
		stdErrorLabel.setEnabled(state);
		stdErrorValue.setEnabled(state);
	}

	protected void EnableConfidenceLevel(boolean state) {
		// TODO Auto-generated method stub
		confidenceLvlLabel.setEnabled(state);
		confidenceLvlValue.setEnabled(state);
	}

	protected void displayMultipleGraphs(boolean state) {
		// TODO Auto-generated method stub
		orientGraphsCombo.setEnabled(state);
		lblOrientation.setEnabled(state);
		lblNewLabel.setEnabled(state);
		numRowsSpinner.setEnabled(state);
		lblNumberOfColumns.setEnabled(state);
		numColsSpinner.setEnabled(state);
	}

	protected void enableShowLegend(boolean state) {
		// TODO Auto-generated method stub
		lblPosition.setEnabled(state);
		legTitleText.setEnabled(state);
		lblTitle.setEnabled(state);
		legPositionComo.setEnabled(state);
		lblAlignment.setEnabled(state);
//		numColsSpinner1.setEnabled(state);
	}

	protected void addItemToTableAxis(final String varAxisName) {

		int ctr1=tableAxis.getItemCount();
		final TableItem tableAxisItem = new TableItem(tableAxis, SWT.CENTER);
		tableAxisItem.setText(0, varAxisName);
		System.out.println("set tableItem name: "+varAxisName);
		tableAxisItem.setData("index",ctr1);
		TableEditor[] tableAxisEditors = new TableEditor[3];

		tableAxisEditors[0] = new TableEditor(tableAxis);
		tableAxisEditors[1] = new TableEditor(tableAxis);
		tableAxisEditors[2] = new TableEditor(tableAxis);

		Text newEditor = new Text(tableAxis, SWT.NONE);
		newEditor.setText(varAxisName);
		Text lowerLimitEditor = new Text(tableAxis, SWT.NONE);
		lowerLimitEditor.setText("Auto");
		GraphsUtilities.addTextModifyListener(lowerLimitEditor);
		Text upperLimitEditor = new Text(tableAxis, SWT.NONE);
		upperLimitEditor.setText("Auto");
		GraphsUtilities.addTextModifyListener(upperLimitEditor);

		tableAxisEditors[0].grabHorizontal = true;
		tableAxisEditors[1].grabHorizontal = true;
		tableAxisEditors[2].grabHorizontal = true;

		tableAxisEditors[0].setEditor(newEditor, tableAxisItem, 1);
		tableAxisEditors[1].setEditor(lowerLimitEditor, tableAxisItem, 2);
		tableAxisEditors[2].setEditor(upperLimitEditor, tableAxisItem, 3);

		tableAxisItem.setData("Editors", tableAxisEditors);

		table.pack();
//		composite_2.pack();
		getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
		getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
	}

	protected void removeItemFromTableAxis(String varAxisName) {
		// TODO Auto-generated method stub
		int item=0;	
		for(int i=0;i<tableAxis.getItemCount(); i++){
			if(varAxisName.equals(tableAxis.getItem(i).getText(0)))item = i;
		}
		System.out.println(varAxisName+"'s index is: "+Integer.toString(item));
		TableItem tableAxisItem = tableAxis.getItem(item);
		TableEditor[] axisEditors = (TableEditor[])tableAxisItem.getData("Editors");
		for(TableEditor tex : axisEditors){
			tex.getEditor().dispose();
		}
		tableAxis.remove(item);
		tableAxis.pack();
//		composite_2.pack();
	}
	
	
	
	protected void addItemToVarTable(final String varName) {
		// TODO Auto-generated method stub
		final RGB[] colors = GraphsUtilities.getGrayShades(table.getItemCount()+1);
		final int ctr=table.getItemCount();
		final TableItem tableItem = new TableItem(table, SWT.CENTER);
		tableItem.setData("index",ctr);
		barDensityList.add(100);
		barLineAngleList.add(0);
		
		tableItem.setText(0, varName); //factor Name
		
		GraphsUtilities.refreshTableColors(table, 1, colors);
//		tableItem.setBackground(1, Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));

		TableEditor[] tableEditors = new TableEditor[3];
		tableEditors[0] = new TableEditor(table);
		tableEditors[1] = new TableEditor(table);
		tableEditors[2]  = new TableEditor(table);

		Button chooseColorButton = new Button(table, SWT.NONE | SWT.CENTER);
		chooseColorButton.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		chooseColorButton.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		chooseColorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table.setSelection(-1);
				try{
					int index = ctr;
					RGB rgbColor = GraphsUtilities.chooseColor();
					tableItem.setBackground(1, new Color(Display.getCurrent(), rgbColor));
				}catch(Exception ex){
					System.out.println("Exception at RGB choose color");
				}
			}
		});
		Button chooseFillButton = new Button(table, SWT.NONE | SWT.CENTER);
		chooseFillButton.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		chooseFillButton.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));

		final Label lblImagePlaceHolder = new Label(table, SWT.FILL | SWT.CENTER);
		lblImagePlaceHolder.setImage(ResourceManager.getPluginImage("Star", "icons/solid.png"));

		chooseFillButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = ctr;
				ChooseFillPatternDialog fillDlg = new ChooseFillPatternDialog(getShell(), varName);
				fillDlg.open();
				if(fillDlg.getReturnCode()==0){table.setSelection(-1);
				try{
					barDensityList.remove(index);
					barLineAngleList.remove(index);
					lblImagePlaceHolder.setImage(ResourceManager.getPluginImage("Star", "icons/"+fillDlg.getChosenPattern()+".png"));
					barDensityList.add(index, fillDlg.getLineDensity());
					barLineAngleList.add(index, fillDlg.getPatternAngle());
				}catch(Exception ex){}
				//insert getAngle of fill pattern here
				}
			}
		});

		//			colorEditor.grabVertical = true;
		tableEditors[0].grabHorizontal = true;

		//			fillEditor.grabVertical = true;
		tableEditors[1].grabHorizontal = true;

		//			displayFillEditor.grabVertical = true;
		tableEditors[2].grabHorizontal = true;

		tableEditors[0].setEditor(chooseColorButton, tableItem, 2);
		tableEditors[1].setEditor(chooseFillButton, tableItem, 4);
		tableEditors[2].setEditor(lblImagePlaceHolder, tableItem, 3);

		tableItem.setData("Editors", tableEditors);
		table.pack();
		compositeConfigureBars.pack();
		composite.pack();
	}

	protected void removeItemFromVarTable(String varName) {
		int item=0;	
		for(int i=0;i<table.getItemCount(); i++){
			if(varName.equals(table.getItem(i).getText(0)))item = i;
		}
		System.out.println(varName+"'s index is: "+Integer.toString(item));
		TableItem tableItem = table.getItem(item);
		TableEditor[] editors = (TableEditor[])tableItem.getData("Editors");
		for(TableEditor te : editors){
			te.getEditor().dispose();
		}
		barDensityList.remove(item);
		barLineAngleList.remove(item);
		table.remove(item);
		table.pack();
		compositeConfigureBars.pack();
	}

	protected void enableStyleOptions(boolean state) {
		// TODO Auto-generated method stub
		styleLabel.setEnabled(state);
		clusteredStyleBtn.setEnabled(state);
		stackedStyleBtn.setEnabled(state);
	}

	protected void enableBarsRepresent(boolean state) {
		// TODO Auto-generated method stub
		barsRepresentLabel.setEnabled(state);
		barsRepresentCombo.setEnabled(state);
		if(state && barsRepresentCombo.getSelectionIndex()==1 && clusteredStyleBtn.getSelection())enableDisplayErrorBars(true);
		else enableDisplayErrorBars(false);

		if(state && displayErrorBars.getSelection())enableStyleOptions(false);
	}

	private void enableDisplayErrorBars(boolean state) {
		// TODO Auto-generated method stub
		displayErrorBars.setEnabled(state);
		if(state && displayErrorBars.getSelection()) enableErrorBarsOptions(true);
		else enableErrorBarsOptions(false);
	}

	private void enableErrorBarsOptions(boolean state) {
		// TODO Auto-generated method stub

		errorBarsLabel.setEnabled(state);
		
		confidenceLvlBtn.setEnabled(state);
		confidenceLvlLabel.setEnabled(false);
		confidenceLvlValue.setEnabled(false);

		stdErrorBtn.setEnabled(state);
		stdErrorLabel.setEnabled(false);
		stdErrorValue.setEnabled(false);

		stdDevBtn.setEnabled(state);
		stdDevLabel.setEnabled(false);
		stdDevValue.setEnabled(false);

		if(state){
			if(confidenceLvlBtn.getSelection()){
				confidenceLvlValue.setEnabled(true);
				confidenceLvlLabel.setEnabled(true);
			}
			else if(stdErrorBtn.getSelection()){
				stdErrorValue.setEnabled(true);
				stdErrorLabel.setEnabled(true);
			}
			else if(stdDevBtn.getSelection()){
				stdDevValue.setEnabled(true);
				stdDevLabel.setEnabled(true);
			}
		}
		else enableStyleOptions(true);
	}

	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
	}

	public void enableFactorButtons(boolean state){
		groupsVarList.setSelection(-1);
		axisVarList.setSelection(-1);
		lstClusterBy.setSelection(-1);

		addGroupsBtn.setEnabled(state);
		addAxisBtn.setEnabled(state);
		addClusterBybtn.setEnabled(state);
		if(state){
			addGroupsBtn.setText("Add");
			addAxisBtn.setText("Add");
			addClusterBybtn.setText("Add");
			if(groupsVarList.getItemCount()>0)addGroupsBtn.setEnabled(false);
			if(axisVarList.getItemCount()>0)addAxisBtn.setEnabled(false);
//			if(lstClusterBy.getItemCount()>0)addClusterBybtn.setEnabled(false);
		}
	}

	public void enableNumericButtons(boolean state){
		addBtn.setEnabled(state);
		moveBtn.setEnabled(state);
	}

	@Override
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
			System.out.println("graph type: " + analysisType);
			BarGraphDialog bar = new BarGraphDialog(parentShell,analysisType,file);
			close();
			bar.open();
		}
		super.buttonPressed(buttonId);
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Reset", true);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed(){
		String dataFileName = file.getAbsolutePath().replaceAll("\\\\", "/");
		boolean continueWithErrorBars=false;

		if(responseVarList.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add variable(s) from the numeric variables list.");
		else if(axisVarList.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please select a category axis variable.");
		else if(lstClusterBy.getItemCount()>3) MessageDialog.openError(getShell(), "Error", "Please enter three variables only on Cluster List.");

		else{//if all conditions are satisfied
			nVar = responseVarList.getItems();
			if(axisVarList.getItemCount()==0) cVar=null;
			else cVar = axisVarList.getItem(0);
			mTitle = mainTitleText.getText(); //null; //

//			yAxisLab = valueAxisText.getText(); //null; //value axis label
			xAxisLab = categoryAxisText.getText(); //null; //category axis label
			if(groupsVarList.getItemCount()==0)byVar = null;
			else byVar = groupsVarList.getItem(0);

			clustVars = lstClusterBy.getItems();
//			if(lowerLimitText.getText().isEmpty() || lowerLimitText.getText().toLowerCase().equals("auto"))minValue = null; //"20";//
//			else minValue = lowerLimitText.getText();
//			
//			if(upperLimitText.getText().isEmpty() || upperLimitText.getText().toLowerCase().equals("auto"))maxValue = null; //"20";//
//			else maxValue = upperLimitText.getText();

			if(rawDataBtn.getSelection()){
				typeData = "raw";
				descStat = barsRepresentList[barsRepresentCombo.getSelectionIndex()];
			}else{
				descStat = null;
				typeData = "sumStat"; 
			}

			if(verticalOrientationBtn.getSelection()) barsHoriz = "FALSE"; //"TRUE"; //
			else barsHoriz = "TRUE"; //

			if(clusteredStyleBtn.getSelection()) barsClus = "TRUE"; //"FALSE"; //
			else barsClus = "FALSE"; //

			if(displayErrorBars.getSelection() && displayErrorBars.getEnabled()) errBars = "TRUE";
			else  errBars = "FALSE";

			if(errBars.equals("TRUE")){
				if(confidenceLvlBtn.getSelection()){
					confLev = confidenceLvlValue.getSelection() / Math.pow(10, 2);
					typeErrBar = "confInt";
				}
				else if(stdErrorBtn.getSelection()){
					errMult = stdErrorValue.getSelection();
					typeErrBar = "stdErr";
				}
				else if(stdDevBtn.getSelection()){
					errMult = stdDevValue.getSelection();
					typeErrBar = "stdDev"; //null; // #c(NULL, "stdDev", "stdErr", "confInt")
				}

				boolean hasLevelsWithOneDatum = false;

				StringBuilder sb = new StringBuilder();
				for(String nvar: nVar){
					String[] levelsWithOneValue = ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().checkLevelsWithOneDatum(dataFileName.replaceAll("\\\\", "/"), cVar, nvar, byVar);
					System.out.println("value of levels with one value: "+levelsWithOneValue[0]);
					if(levelsWithOneValue[0].equals("FALSE")){
						hasLevelsWithOneDatum = true;
					}
					else if(!levelsWithOneValue[0].equals("TRUE") && !levelsWithOneValue[0].equals("FALSE")){
						hasLevelsWithOneDatum = true;
						sb.append("\n");
						sb.append(nvar);
						sb.append(" [Group:level(s)]\n");
						
						for(String s: levelsWithOneValue){
							sb.append(s);
							sb.append("\n");
						}
					}
				}

				if(hasLevelsWithOneDatum){
					System.out.println(sb.toString());	
					if(sb.length()<1){	if(MessageDialog.openQuestion(getShell(), "Invalid Input", "There are category level(s) with only one observation.\n" +
							"For these, error bars cannot be displayed.\n\n" +
							"Do you still want to proceed?")) continueWithErrorBars = true;
					}else{
						if(MessageDialog.openQuestion(getShell(), "Invalid Input", "There are category level(s) with only one observation:\n" + sb.toString() +
								"\nDo you still want to proceed?")) continueWithErrorBars = true;
					}
				}else{
					continueWithErrorBars=true;
				}
			}else typeErrBar = null;

			if(btnShowLegend.getSelection()){
				showLeg = "TRUE"; //"FALSE"; //
				legPos = legPositionComo.getText().replaceAll("-", ""); //eight other choices
				if(!legTitleText.getText().isEmpty())legTitle = legTitleText.getText(); //null; //
				else legTitle = null;
				legCol = numColsSpinner1.getSelection();
			}
			else{
				showLeg = "FALSE";
				legPos = null;
				legTitle = null;
			}

			if(btnDrawBoxAround.getSelection()) boxed = "TRUE"; //
			else boxed = "FALSE"; //"TRUE"; //

			if(btnDisplayMultipleGraphs.getSelection()){
				multGraphs = "TRUE"; //"TRUE"; //
				numRowsGraphs = numRowsSpinner.getSelection();
				numColsGraphs = numColsSpinner.getSelection();
				orientGraphs = orientGraphsCombo.getText().replace("to-", "").toLowerCase();
			}
			else{
				multGraphs = "FALSE";
				numRowsGraphs = 1;
				numColsGraphs = 1;
				orientGraphs = "top-bottom";
			}

			axisLabelStyle = cmboAxisOrientation.getSelectionIndex();
			
//			if (cmboAlignment.getText().equals("horizontal")){
//				legHoriz = "TRUE";
//			}else legHoriz = "FALSE";
			
			if(displayVarNamesBtn.getEnabled() && displayVarNamesBtn.getSelection()) showCatVarLevels = "TRUE"; //"TRUE"; //
			else showCatVarLevels = "FALSE"; //"TRUE"; //

			yAxisLab =  new String[nVar.length];
			minValue = new String[nVar.length];
			maxValue = new String[nVar.length];
			
			int ctr1=0;
			for(TableItem tableAxisItem : tableAxis.getItems()){
				TableEditor[] axisEditors = (TableEditor[]) tableAxisItem.getData("Editors");
				yAxisLab[ctr1] = ((Text)axisEditors[0].getEditor()).getText();
				minValue[ctr1] = GraphsUtilities.checkIfAutoOrNumeric(((Text)axisEditors[1].getEditor()).getText());
				maxValue[ctr1] = GraphsUtilities.checkIfAutoOrNumeric(((Text)axisEditors[2].getEditor()).getText());
				ctr1++;
			}
			
			
			
			barDensity = GraphsUtilities.convertIntListToIntAray(barDensityList);
			barLineAngle = GraphsUtilities.convertIntListToIntAray(barLineAngleList);
			barColor = new String[table.getItemCount()];
			
			int ctr=0;
			for(TableItem tableItem: table.getItems()){
				barColor[ctr] = GraphsUtilities.convertToRrgbFormat(tableItem.getBackground(1).getRGB().toString());
				ctr++;
			}
			
			if(errBars.equals("FALSE") || continueWithErrorBars ){
			File outputFolder = GraphsUtilities.createOutputFolder(file.getName(),analysisType);
			if(!outputFolder.exists()) outputFolder.mkdir();

			OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Creating Bar Graph");
			rInfo.open();
//			ProjectExplorerView.rJavaManager.getRJavaGraphManager().createGraphBarplot(outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/", dataFileName, nVar, cVar, 
//					mTitle, yAxisLab, xAxisLab, minValue, maxValue, typeData, descStat,
//					barsHoriz, barsClust, byVar, errBars, typeErrBar, errMult, confLev,
//					barColor, showLeg, legPos, legTitle, boxed,
//					multGraphs, numRowsGraphs, numColsGraphs, orientGraphs, showVarNames, barDensity, barLineAngle);
			
			ProjectExplorerView.rJavaManager.getRJavaGraphManager().createGraphBarplot(
					outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/", 
					dataFileName,
					nVar,
					cVar,
					clustVars, 
					mTitle, 
					yAxisLab, 
					xAxisLab, 
					minValue, 
					maxValue, 
					typeData, 
					descStat,
					barsHoriz, 
					barsClus, 
					byVar, 
					errBars,
					typeErrBar, 
					errMult, 
					confLev,
					axisLabelStyle,
					barColor, 
					showLeg, 
					legPos,
					legTitle, 
					legCol,
					boxed,
					multGraphs, 
					numRowsGraphs, 
					numColsGraphs, 
					orientGraphs, 
					showCatVarLevels, 
					barDensity, 
					barLineAngle
					);
			
			
			
			rInfo.close();
			WindowDialogControlUtil.hideAllWindowDialog();

			GraphsUtilities.openFolder(outputFolder);
			}
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */

	@Override
	protected Point getInitialSize() {
		return new Point(595, 580);
	}

	/**
	 * Return the initial size of the dialog.
	 */

	@Override
	protected boolean isResizable() {
		return true;
	}

}