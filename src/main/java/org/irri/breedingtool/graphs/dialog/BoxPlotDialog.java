package org.irri.breedingtool.graphs.dialog;

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
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
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
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.GraphsUtilities;
import org.irri.breedingtool.utility.PBToolAnalysisUtilities;
import org.irri.breedingtool.utility.TextVarValidator;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Spinner;

public class BoxPlotDialog extends Dialog {

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
	private int axisLabelStyle = 1;
	
	//specify parameters (default values)
	String nVar[] = null; 		//but should be provided by user 
	String cVars[] = null; 		
	String mTitle = null; 
	String nAxisLab[] = null; //If erased, should be "" for each x-variable}
	String cAxisLab = null; //If erased, should be "" for each y-variable}
	String yMinValue[] = null;
	String yMaxValue []= null;
	String byVar = null; 

	//EDIT
	double boxSize = 0.8;
	String boxColor = "rgb(0, 0, 0, max = 255)"; //black
	String boxFillColor = "rgb(255, 255, 255, max = 255)"; //white
	int medLineType = 1;
	double medLineWidth = 3;
	String medColor = "rgb(0, 0, 0, max = 255)"; //black
	int whiskLineType = 2;
	double whiskLineWidth = 1;
	String whiskColor = "rgb(0, 0, 0, max = 255)"; //black
	int outChar = 1;
	double outCharSize = 1;
	String outColor = "rgb(0, 0, 0, max = 255)"; //black
	String boxed = "FALSE";
	String multGraphs = "FALSE";
	int numRowsGraphs = 1;
	int numColsGraphs = 1;
	String orientGraphs = "top-bottom";
	String plotHoriz = "FALSE";

	
	//	private int[] barLineAngle = null; //{0 - solid or horizontal, 45 - forward diagonal, 90 - vertical, 135 - backward diagonal
	private ArrayList<Integer> barDensityList = new ArrayList<Integer>();
	private String analysisType;
	private TableColumn tableColumn;
	private Button addAxisBtn;
	private Button addGroupsBtn;
	private List axisVarList;
	private Text mainTitleText;
	private Text categoryAxisText;
	private TabItem tbtmModelSpecifications;
	private CCombo orientGraphsCombo;
	private Button btnDrawBoxAround;
	private Button btnDisplayMultipleGraphs;
	private Spinner numColsSpinner;
	private Spinner numRowsSpinner;
	private Label lblOrientation_1;
	private Button btnVertical;
	private Button btnHorizontal;
	private TabItem tbtmFormat;
	private Composite composite;
	private Label lblBox;
	private Label lblOutlineColor;
	private Label outlineColor;
	private Button outlineColorBtn;
	private Label lblFillColor;
	private Label fillColor;
	private Button fillColorBtn;
	private Label lblSize;
	private Spinner boxSizeSpinner;
	private Label lblMedian;
	private Label lblColor;
	private Label medianColor;
	private Button medianColorBtn;
	private Button medianLineTypeBtn;
	private Label medianLineType;
	private Label lblLineType;
	private Label lblWidth;
	private Spinner medianWidthSpinner;
	private Label lblWhiskers;
	private Label lblColor_1;
	private Label label_4;
	private Label label_5;
	private Label lblOutliers;
	private Label label_6;
	private Label lblSymbol;
	private Label label_8;
	private Spinner whiskersWidthSpinner;
	private Spinner outliersSizeSpinner;
	private Label outliersColor;
	private Label whiskersColor;
	private Button whiskersColorBtn;
	private Button outliersColorBtn;
	private Button whiskersLineTypeBtn;
	private Button outliersSymbolBtn;
	private Label outliersSymbol;
	private Label whiskersLineType;
	private Label lblAsdasd;
	private Label lblAdasd;
	private Table table;
	private Label lblNewLabel;
	private Label lblNumberOfColumns;
	private Label lblOrientation;
	private Composite composite_2;
	private Shell parentShell;
	private Label lblAxisLabelOrientation;
	private CCombo cmboAxisOrientation;
	private Text groupsVarTxt;
	private Label lblGroupBy;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public BoxPlotDialog(Shell parentShell, String analysisType, File file) {
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

		parent.getShell().setText("Box-and-Whiskers Plot: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tbtmModelSpecifications = new TabItem(tabFolder, SWT.NONE);
		tbtmModelSpecifications.setText("Variable Definition");

		Composite modelComposite = new Composite(tabFolder, SWT.NONE);
		tbtmModelSpecifications.setControl(modelComposite);
		modelComposite.setLayout(new GridLayout(5, false));

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
				if(numVarList.getSelectionCount()>0){
					addItemToVarTable(numVarList.getSelection()[0]);
					dataManipulationManager.moveSelectedStringFromTo( numVarList, responseVarList, moveBtn);
					numVarList.remove(numVarList.getSelectionIndices());

					addBtn.setEnabled(false);
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
						addItemToVarTable(selectedNumVars[i]);
						responseVarList.add(selectedNumVars[i]);
					}
					numVarList.remove(numVarList.getSelectionIndices());
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = responseVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						removeItemFromVarTable(selectedNumVars[i]);
						numVarList.add(selectedNumVars[i]);
					}
					responseVarList.remove(responseVarList.getSelectionIndices());
				}

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
				if(responseVarList.getSelectionCount()>0){
					removeItemFromVarTable(responseVarList.getSelection()[0]);
					dataManipulationManager.moveSelectedStringFromTo( responseVarList, numVarList, moveBtn);
					responseVarList.remove(responseVarList.getSelectionIndices());
					addBtn.setEnabled(false);
				}
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

		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 3);
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
//		addAxisBtn.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
//					dataManipulationManager.moveSelectedStringFromTo( factorVarList, axisVarList);
//				}
//				else{//if the purpose is to remove
//					dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
//				}
//				enableFactorButtons(false);
//			}
//			
//			public void widgetSelected(SelectionEvent e) {
//				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
//					for(int i=0; i< factorVarList.getSelectionCount(); i++){
//						addItemToVarTable(factorVarList.getSelection()[i]);
//					}
//					dataManipulationManager.moveSelectedStringFromTo( factorVarList, axisVarList);
//				}
//				else{//if the purpose is to remove
//					for(int i=0; i< axisVarList.getSelectionCount(); i++){
//						removeItemFromVarTable(axisVarList.getSelection()[i]);
//					}
//					dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
//				}
//				enableNumericButtons(false);
//				addGroupsBtn.setEnabled(false);
//			}
//			
//			
//			
//		});
		GridData gd_addAxisBtn = new GridData(SWT.LEFT, SWT.CENTER, false, true, 2, 1);
		gd_addAxisBtn.widthHint = 52;
		addAxisBtn.setLayoutData(gd_addAxisBtn);
		addAxisBtn.setText("Add");
		addAxisBtn.setEnabled(false);

		axisVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		axisVarList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		axisVarList.addMouseListener(new MouseListener(){
//			@Override
//			public void mouseDoubleClick(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
////				for(int i=0; i< axisVarList.getSelectionCount(); i++){
////					removeItemFromVarTable(axisVarList.getSelection()[i]);
////				}
////				dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
////				axisVarList.setEnabled(false);
//				
//				dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
//				addAxisBtn.setEnabled(false);
//			}
//
//			@Override
//			public void mouseDown(MouseEvent e) {
//				// TODO Auto-generated method stub
//				if(axisVarList.getSelectionCount()>0){
//					factorVarList.setSelection(-1);
//					addAxisBtn.setText("Remove");
//					addAxisBtn.setEnabled(true);
//				}
//			}
//
//			@Override
//			public void mouseUp(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		lblGroupBy = new Label(modelComposite, SWT.NONE);
		lblGroupBy.setText("Group by:");

		addGroupsBtn = new Button(modelComposite, SWT.NONE);
		addGroupsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
//		addGroupsBtn.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
//					dataManipulationManager.moveSelectedStringFromTo( factorVarList, groupsVarList);
//				}
//				else{//if the purpose is to remove
//					dataManipulationManager.moveSelectedStringFromTo( groupsVarList, factorVarList);
//				}
//				enableFactorButtons(false);
//			}
//		});
		GridData gd_addGroupsBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_addGroupsBtn.widthHint = 52;
		addGroupsBtn.setLayoutData(gd_addGroupsBtn);
		addGroupsBtn.setText("Add");
		addGroupsBtn.setEnabled(false);
		
		groupsVarTxt = new Text(modelComposite, SWT.BORDER | SWT.READ_ONLY);
		groupsVarTxt.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		groupsVarTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		TabItem tbtmDisplay = new TabItem(tabFolder, SWT.NONE);
		tbtmDisplay.setText("Display Options");

		composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmDisplay.setControl(composite_2);
		composite_2.setLayout(new GridLayout(5, false));

		Label lblMainTitle = new Label(composite_2, SWT.NONE);
		lblMainTitle.setText("Main Title:");

		mainTitleText = new Text(composite_2, SWT.BORDER);
		int maxLen =45;
		mainTitleText.setTextLimit(maxLen);
		mainTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

		Label lblCategoryAxisLabel = new Label(composite_2, SWT.NONE);
		lblCategoryAxisLabel.setText("Category Axis Label:");

		categoryAxisText = new Text(composite_2, SWT.BORDER);
		categoryAxisText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

		Label lblValueAxisLabel = new Label(composite_2, SWT.NONE);
		lblValueAxisLabel.setText("Value Axis:");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);


		table = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 2));

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(85);
		tableColumn_1.setText("Variable");

		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(83);
		tblclmnType.setText("Axis Label");

		TableColumn tblclmnLoewrLabel = new TableColumn(table, SWT.NONE);
		tblclmnLoewrLabel.setText("Lower Limit");
		tblclmnLoewrLabel.setWidth(87);

		TableColumn tblclmnUpperLimit = new TableColumn(table, SWT.NONE);
		tblclmnUpperLimit.setWidth(72);
		tblclmnUpperLimit.setText("Upper Limit");
		
		lblAxisLabelOrientation = new Label(composite_2, SWT.NONE);
		lblAxisLabelOrientation.setText("Axis Label Orientation:");
		
		cmboAxisOrientation = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
		cmboAxisOrientation.setItems(new String[] {"parallel to axis", "horizontal", "vertical", "perpendicular to axis"});
		cmboAxisOrientation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		cmboAxisOrientation.select(0);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		lblOrientation_1 = new Label(composite_2, SWT.NONE);
		lblOrientation_1.setText("Orientation:");

		btnVertical = new Button(composite_2, SWT.RADIO);
		btnVertical.setSelection(true);
		btnVertical.setText("Vertical");

		btnHorizontal = new Button(composite_2, SWT.RADIO);
		btnHorizontal.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnHorizontal.setText("Horizontal");
		new Label(composite_2, SWT.NONE);

		btnDrawBoxAround = new Button(composite_2, SWT.CHECK);
		btnDrawBoxAround.setSelection(true);
		btnDrawBoxAround.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		btnDrawBoxAround.setText("Draw box around plot");

		btnDisplayMultipleGraphs = new Button(composite_2, SWT.CHECK);
		btnDisplayMultipleGraphs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnDisplayMultipleGraphs.getSelection()) displayMultipleGraphs(true);
				else displayMultipleGraphs(false);
			}
		});
		btnDisplayMultipleGraphs.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		btnDisplayMultipleGraphs.setText("Display multiple graphs in a page");
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
		lblOrientation.setEnabled(false);
		lblOrientation.setText("Orientation:");

		orientGraphsCombo = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
		orientGraphsCombo.setEnabled(false);
		orientGraphsCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		orientGraphsCombo.setEditable(false);
		orientGraphsCombo.setItems(new String[] {"Left-to-right", "Top-to-bottom"});
		orientGraphsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		orientGraphsCombo.select(1);

		tbtmFormat = new TabItem(tabFolder, 0);
		tbtmFormat.setText("Other Options");

		composite = new Composite(tabFolder, SWT.NONE);
		tbtmFormat.setControl(composite);
		composite.setLayout(new GridLayout(11, false));

		lblAdasd = new Label(composite, SWT.NONE);
		lblAdasd.setText(" ");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblBox = new Label(composite, SWT.NONE);
		lblBox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblBox.setText("BOX:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblWhiskers = new Label(composite, SWT.NONE);
		lblWhiskers.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblWhiskers.setText("WHISKERS:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblOutlineColor = new Label(composite, SWT.NONE);
		lblOutlineColor.setText("Outline Color:");

		outlineColor = new Label(composite, SWT.BORDER);
		GridData gd_outlineColor = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_outlineColor.widthHint = 50;
		outlineColor.setLayoutData(gd_outlineColor);
		outlineColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		outlineColorBtn = new Button(composite, SWT.NONE);
		outlineColorBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		outlineColorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					RGB rgbColor = GraphsUtilities.chooseColor();
					boxColor = GraphsUtilities.convertToRrgbFormat(rgbColor.toString());
					outlineColor.setBackground( new Color(Display.getCurrent(), rgbColor));
				}catch(Exception ex){
					System.out.println("Exception at RGB choose color");
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblColor_1 = new Label(composite, SWT.NONE);
		lblColor_1.setText("Color:");

		whiskersColor = new Label(composite, SWT.BORDER);
		GridData gd_whiskersColor = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_whiskersColor.widthHint = 50;
		whiskersColor.setLayoutData(gd_whiskersColor);
		whiskersColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		whiskersColorBtn = new Button(composite, SWT.NONE);
		whiskersColorBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		whiskersColorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					RGB rgbColor = GraphsUtilities.chooseColor();
					whiskColor = GraphsUtilities.convertToRrgbFormat(rgbColor.toString());
					whiskersColor.setBackground( new Color(Display.getCurrent(), rgbColor));
				}catch(Exception ex){
					System.out.println("Exception at RGB choose color");
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblFillColor = new Label(composite, SWT.NONE);
		lblFillColor.setText("Fill Color:");

		fillColor = new Label(composite, SWT.BORDER);
		GridData gd_fillColor = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_fillColor.widthHint = 50;
		fillColor.setLayoutData(gd_fillColor);
		fillColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		fillColorBtn = new Button(composite, SWT.NONE);
		fillColorBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		fillColorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					RGB rgbColor = GraphsUtilities.chooseColor();
					boxFillColor = GraphsUtilities.convertToRrgbFormat(rgbColor.toString());
					fillColor.setBackground( new Color(Display.getCurrent(), rgbColor));
				}catch(Exception ex){
					System.out.println("Exception at RGB choose color");
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		label_4 = new Label(composite, SWT.NONE);
		label_4.setText("Line Type:");

		whiskersLineType = new Label(composite, SWT.BORDER);
		whiskersLineType.setAlignment(SWT.CENTER);
		GridData gd_whiskersLineType = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_whiskersLineType.widthHint = 50;
		whiskersLineType.setLayoutData(gd_whiskersLineType);
		whiskersLineType.setImage(ResourceManager.getPluginImage("Star", "icons/line3.png"));
		whiskersLineType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		whiskersLineTypeBtn = new Button(composite, SWT.NONE);
		whiskersLineTypeBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		whiskersLineTypeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ChooseLineTypesDialog fillDlg = new ChooseLineTypesDialog(getShell());
				fillDlg.open();
				if(fillDlg.getReturnCode()==0){
					whiskLineType = Integer.parseInt(fillDlg.getChosenPattern());
					whiskersLineType.setImage(ResourceManager.getPluginImage("Star", "icons/line"+fillDlg.getChosenPattern()+".png"));
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblSize = new Label(composite, SWT.NONE);
		lblSize.setText("Size:");

		boxSizeSpinner = new Spinner(composite, SWT.BORDER);
		boxSizeSpinner.setMaximum(30);
		boxSizeSpinner.setMinimum(5);
		boxSizeSpinner.setSelection(8);
		boxSizeSpinner.setDigits(1);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		label_5 = new Label(composite, SWT.NONE);
		label_5.setText("Width:");

		whiskersWidthSpinner = new Spinner(composite, SWT.BORDER);
		whiskersWidthSpinner.setDigits(1);
		whiskersWidthSpinner.setMaximum(30);
		whiskersWidthSpinner.setMinimum(5);
		whiskersWidthSpinner.setSelection(10);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblAsdasd = new Label(composite, SWT.NONE);
		lblAsdasd.setText(" ");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblMedian = new Label(composite, SWT.NONE);
		lblMedian.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblMedian.setText("MEDIAN:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblOutliers = new Label(composite, SWT.NONE);
		lblOutliers.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblOutliers.setText("OUTLIER(S):");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblColor = new Label(composite, SWT.NONE);
		lblColor.setText("Color:");

		medianColor = new Label(composite, SWT.BORDER);
		GridData gd_medianColor = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_medianColor.widthHint = 50;
		medianColor.setLayoutData(gd_medianColor);
		medianColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		medianColorBtn = new Button(composite, SWT.NONE);
		medianColorBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		medianColorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					RGB rgbColor = GraphsUtilities.chooseColor();
					medColor = GraphsUtilities.convertToRrgbFormat(rgbColor.toString());
					medianColor.setBackground( new Color(Display.getCurrent(), rgbColor));
				}catch(Exception ex){
					System.out.println("Exception at RGB choose color");
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		label_6 = new Label(composite, SWT.NONE);
		label_6.setText("Color:");

		outliersColor = new Label(composite, SWT.BORDER);
		GridData gd_outliersColor = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_outliersColor.widthHint = 50;
		outliersColor.setLayoutData(gd_outliersColor);
		outliersColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		outliersColorBtn = new Button(composite, SWT.NONE);
		outliersColorBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		outliersColorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					RGB rgbColor = GraphsUtilities.chooseColor();
					outColor = GraphsUtilities.convertToRrgbFormat(rgbColor.toString());
					outliersColor.setBackground( new Color(Display.getCurrent(), rgbColor));
				}catch(Exception ex){
					System.out.println("Exception at RGB choose color");
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblLineType = new Label(composite, SWT.NONE);
		lblLineType.setText("Line Type:");

		medianLineType = new Label(composite, SWT.BORDER);
		medianLineType.setImage(ResourceManager.getPluginImage("Star", "icons/line1.png"));
		GridData gd_medianLineType = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_medianLineType.widthHint = 50;
		medianLineType.setLayoutData(gd_medianLineType);
		medianLineType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		medianLineTypeBtn = new Button(composite, SWT.NONE);
		medianLineTypeBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		medianLineTypeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ChooseLineTypesDialog fillDlg = new ChooseLineTypesDialog(getShell());
				fillDlg.open();
				if(fillDlg.getReturnCode()==0){
					medLineType = Integer.parseInt(fillDlg.getChosenPattern());
					medianLineType.setImage(ResourceManager.getPluginImage("Star", "icons/line"+fillDlg.getChosenPattern()+".png"));
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblSymbol = new Label(composite, SWT.NONE);
		lblSymbol.setText("Symbol:");

		outliersSymbol = new Label(composite, SWT.BORDER);
		outliersSymbol.setAlignment(SWT.CENTER);
		outliersSymbol.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol1.png"));
		GridData gd_outliersSymbol = new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1);
		gd_outliersSymbol.widthHint = 50;
		outliersSymbol.setLayoutData(gd_outliersSymbol);
		outliersSymbol.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		outliersSymbolBtn = new Button(composite, SWT.NONE);
		outliersSymbolBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		outliersSymbolBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ChoosePlotSymbolsDialog fillDlg = new ChoosePlotSymbolsDialog(getShell());
				fillDlg.open();
				if(fillDlg.getReturnCode()==0){
					outChar = Integer.parseInt(fillDlg.getChosenSymbol());
					outliersSymbol.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol"+fillDlg.getChosenSymbol()+".png"));
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblWidth = new Label(composite, SWT.NONE);
		lblWidth.setText("Width:");

		medianWidthSpinner = new Spinner(composite, SWT.BORDER);
		medianWidthSpinner.setDigits(1);
		medianWidthSpinner.setMaximum(30);
		medianWidthSpinner.setMinimum(5);
		medianWidthSpinner.setSelection(30);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		label_8 = new Label(composite, SWT.NONE);
		label_8.setText("Size:");

		outliersSizeSpinner = new Spinner(composite, SWT.BORDER);
		outliersSizeSpinner.setDigits(1);
		outliersSizeSpinner.setMaximum(30);
		outliersSizeSpinner.setMinimum(5);
		outliersSizeSpinner.setSelection(10);
		new Label(composite, SWT.NONE);
		
		initializeForm();
		return container;
	}

	void initializeForm(){

		dlgManager.initializeSingleSelectionList(factorVarList, groupsVarTxt, moveBtn, addGroupsBtn, new TextVarValidator(groupsVarTxt, lblGroupBy));
		dlgManager.initializeSingleButtonList(factorVarList, axisVarList,moveBtn, addAxisBtn);

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

	protected void addItemToVarTable(final String varName) {

		int ctr=table.getItemCount();
		final TableItem tableItem = new TableItem(table, SWT.CENTER);
		tableItem.setText(0, varName);
		System.out.println("set tableItem name: "+varName);
		tableItem.setData("index",ctr);
		TableEditor[] tableEditors = new TableEditor[3];

		tableEditors[0] = new TableEditor(table);//color chooser button
		tableEditors[1] = new TableEditor(table);//color chooser button
		tableEditors[2] = new TableEditor(table);//lineType chooser button

		Text newEditor = new Text(table, SWT.NONE);
		newEditor.setText(varName);
		Text lowerLimitEditor = new Text(table, SWT.NONE);
		lowerLimitEditor.setText("Auto");
		GraphsUtilities.addTextModifyListener(lowerLimitEditor);
		Text upperLimitEditor = new Text(table, SWT.NONE);
		upperLimitEditor.setText("Auto");
		GraphsUtilities.addTextModifyListener(upperLimitEditor);

		tableEditors[0].grabHorizontal = true;
		tableEditors[1].grabHorizontal = true;
		tableEditors[2].grabHorizontal = true;

		tableEditors[0].setEditor(newEditor, tableItem, 1);
		tableEditors[1].setEditor(lowerLimitEditor, tableItem, 2);
		tableEditors[2].setEditor(upperLimitEditor, tableItem, 3);

		tableItem.setData("Editors", tableEditors);

		table.pack();
		composite_2.pack();
	}

	protected void removeItemFromVarTable(String varName) {
		// TODO Auto-generated method stub
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
		table.remove(item);
		table.pack();
		composite_2.pack();
	}
	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
	}

	public void enableFactorButtons(boolean state){
		groupsVarTxt.setSelection(-1);
		axisVarList.setSelection(-1);

		addGroupsBtn.setEnabled(state);
		addAxisBtn.setEnabled(state);
		if(state){
			addGroupsBtn.setText("Add");
			addAxisBtn.setText("Add");
			if(groupsVarTxt.getSelection() != null)addGroupsBtn.setEnabled(false);
			if(axisVarList.getItemCount()>0)addAxisBtn.setEnabled(false);
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
			BoxPlotDialog graph = new BoxPlotDialog(parentShell,analysisType,file);
			close();
			graph.open();
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
		if(responseVarList.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add variable(s) from the numeric variables list.");
//		else if(.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please select a category axis variable.");
		else if(axisVarList.getItemCount()>3) MessageDialog.openError(getShell(), "Error", "Please enter three categorical variables only.");
		else{//if all conditions are satisfied
			nVar = responseVarList.getItems();
			if(axisVarList.getItemCount() == 0) cVars=null;
			else cVars = axisVarList.getItems();

//			mTitle = mainTitleText.getText(); //null; //
//			cAxisLab = categoryAxisText.getText(); //null; //category axis label
//			if(groupsVarList.getItemCount()<1)byVar = null;
//			else byVar = groupsVarList.getItem(0);

			mTitle = mainTitleText.getText(); //null; //
			cAxisLab = categoryAxisText.getText(); //null; //category axis label
			if(groupsVarTxt.getText().isEmpty())byVar = null;
			else byVar = groupsVarTxt.getText();
			
			
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
			
			nAxisLab =  new String[nVar.length];
			yMinValue = new String[nVar.length];
			yMaxValue = new String[nVar.length];
			
			int ctr=0;
			for(TableItem tableItem : table.getItems()){
				TableEditor[] editors = (TableEditor[]) tableItem.getData("Editors");
				nAxisLab[ctr] = ((Text)editors[0].getEditor()).getText();
				yMinValue[ctr] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[1].getEditor()).getText());
				yMaxValue[ctr] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[2].getEditor()).getText());
				ctr++;
			}
			
//			if(groupsVarList.getItemCount()>0) byVar = groupsVarList.getItems()[0]; // NULL
//			else byVar = null; // NULL
			
			boxSize = dataManipulationManager.convertInttoDouble(boxSizeSpinner.getSelection(), 1);
	        medLineWidth = dataManipulationManager.convertInttoDouble(medianWidthSpinner.getSelection(), 1);
	        whiskLineWidth = dataManipulationManager.convertInttoDouble(whiskersWidthSpinner.getSelection(), 1);
	        outCharSize = dataManipulationManager.convertInttoDouble(outliersSizeSpinner.getSelection(), 1);
			
			if(btnVertical.getSelection()) plotHoriz = "FALSE";
			else plotHoriz = "TRUE";
			
			axisLabelStyle = cmboAxisOrientation.getSelectionIndex();
			
			String dataFileName = file.getAbsolutePath().replaceAll("\\\\", "/");

			File outputFolder = GraphsUtilities.createOutputFolder(file.getName(),analysisType);
			if(!outputFolder.exists()) outputFolder.mkdir();

			OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Creating Box Plot");
			rInfo.open();

			ProjectExplorerView.rJavaManager.getRJavaGraphManager().createGraphBoxplot(
					outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/", 
					dataFileName, nVar, cVars, 
					mTitle, nAxisLab, cAxisLab, yMinValue, yMaxValue, axisLabelStyle, plotHoriz, byVar, boxSize, boxColor, boxFillColor,
		            medLineType, medLineWidth, medColor, whiskLineType, whiskLineWidth,
		            whiskColor, outChar, outCharSize, outColor,
		            boxed, multGraphs, numRowsGraphs, numColsGraphs, orientGraphs);
			
			rInfo.close();
			WindowDialogControlUtil.hideAllWindowDialog();

			GraphsUtilities.openFolder(outputFolder);
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */

	@Override
	protected Point getInitialSize() {
		return new Point(644, 559);
	}

	/**
	 * Return the initial size of the dialog.
	 */

	@Override
	protected boolean isResizable() {
		return true;
	}

}