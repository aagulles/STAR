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
import org.eclipse.swt.graphics.Rectangle;
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
import org.eclipse.swt.events.MouseAdapter;

public class LineGraphDialog extends Dialog {

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

	//specify parameters
	private String[] yVars = null; //but should be provided by user
	private String xVar = null; //may not be provided by user
	private String mTitle = null;
	private String[] lineVars = null;
	private String[] yAxisLab = null; 
	private String xAxisLab = null; 
	private String[] yMinValue = null; 
	private String[] yMaxValue = null; 
//	private String plotData = "means"; 
	private int axisLabelStyle = 1;
//	private String legHoriz = "FALSE";
	private String byVar = null;
	private String errBars = "FALSE";
	private String typeErrBar = null; //"confInt" if errbars = TRUE;
	private int errMult = 1;
	private double confLev = 0.95;
	private String showLineLabels = "FALSE";
	private String showLeg = "FALSE";
	private String legPos = "bottomright"; //"bottomright" if showLeg = "TRUE"
	private String legTitle = null; 
	private int legCol = 1;
	private String boxed = "FALSE"; 
	private String multGraphs = "FALSE";
	int numRowsGraphs = 1;
	int numColsGraphs = 1;
	String orientGraphs = "top-bottom";
	private String[] plotCol = {"rgb(255,0,255, max = 255)","rgb(0,255,0, max = 255)","rgb(255,0,0, max = 255)"}; //default: RGB values for gray
	private String[] linePtTypes = {"l", "l", "l"};// null {"l" - line only, "b" - both, "o" - overplot points on lines}
	private int[] lineTypes = {1, 2, 3}; //{1 for all lines, possible values 1 to 6 - see diagram};
	private double[] lineWidths = {1,1,1}; //{1 for all lines, possible values 0.5 to 3, increment: 0.5};
	private String[] pointChars = {"3", "NA", "2"};//{0 for all lines, possible values 0 to 25 - see diagram};
	private double[] pointCharSizes = {1,1,1};//{1 for all lines, possible values 0.5 to 3, increment: 0.1};
//	private String xNumeric = "FALSE";// "TRUE";
	private String analysisType;
	private Button addAxisBtn;
	private Button addGroupsBtn;
	private List axisVarList;
	private List groupsVarList;
	private Text mainTitleText;
	private Text categoryAxisText;
	private Text legTitleText;
	private Table table;
	private TabItem tbtmDisplay;
	private Button btnDrawBoxAround;
	private Button btnShowLegend;
	private Button btnDisplayMultipleGraphs;
	private CCombo legPositionComo;
	private Spinner numRowsSpinner;
	private Spinner numColsSpinner;
	private CCombo orientGraphsCombo;
	private Button confidenceLvlBtn;
	private Spinner confidenceLvlValue;
	private Button stdErrorBtn;
	private Spinner stdErrorValue;
	private Button displayErrorBarsBtn;
	private Label errorBarsLabel;
	private Label lblLevel;
	private Label lblMultiplier;
	private Label lblPosition;
	private Label lblTitle;
	private Label lblNewLabel;
	private Label lblOrientation;
	private Label lblNumberOfColumns;
	private Composite composite;
	private Button btnDisplaylinelabels;
	private Shell parentShell;
	private Image plotImage;
	private List lstCreateLines;
	private Label lblCreateLinesBy;
	private Button btnCreateLines;
	private Table tableAxis;
	private TableColumn tableColumn;
	private TableColumn tableColumn_5;
	private TableColumn tableColumn_6;
	private TableColumn tableColumn_8;
	private Label label;
	private CCombo cmboAxisOrientation;
	private Label lblNewLabel_1;
	private Composite composite_2;
	private TableColumn tblclmnLevels;
	private Label label_1;
	private Spinner numColsSpinner1;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LineGraphDialog(Shell parentShell, String analysisType, File file) {
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


		plotImage = ResourceManager.getPluginImage("Star", "icons/plotsymbol1.png");

		parent.getShell().setText("Line Graph: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TabItem tbtmModelSpecifications_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setText("Variable Definition");

		Composite modelComposite = new Composite(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setControl(modelComposite);
		modelComposite.setLayout(new GridLayout(5, false));

		Label lblNumericVariables = new Label(modelComposite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNumericVariables.setText("Numeric Variables:");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		Label lblResponseVariables = new Label(modelComposite, SWT.NONE);
		lblResponseVariables.setText("Y Variable(s):");

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
				if(selectedNumVars.length>0){
//				addItemToVarTable(selectedNumVars[0]);
				addItemToTableAxis(selectedNumVars[0]);
				dataManipulationManager.moveSelectedStringFromTo( numVarList, responseVarList);
				getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
				numVarList.remove(numVarList.getSelectionIndices());
				moveBtn.setEnabled(false);
				addBtn.setEnabled(false);
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(numVarList.getSelectionCount()>0){
					moveBtn.setEnabled(true);
					factorVarList.setSelection(-1);
//					axisVarList.setSelection(-1);
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
						addItemToTableAxis(selectedNumVars[i]);
//						addItemToVarTable(selectedNumVars[i]);
//						getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
					}
					table.pack();
					numVarList.remove(numVarList.getSelectionIndices());
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = responseVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						numVarList.add(selectedNumVars[i]);
//						removeItemFromVarTable(selectedNumVars[i]);
						removeItemFromTableAxis(selectedNumVars[i]);
//						getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
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
				if(responseVarList.getSelection().length>0){
//					removeItemFromVarTable(responseVarList.getSelection()[0]);
					removeItemFromTableAxis(responseVarList.getSelection()[0]);
				dataManipulationManager.moveSelectedStringFromTo( responseVarList, numVarList);
				getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
				responseVarList.remove(responseVarList.getSelectionIndices());
				moveBtn.setEnabled(false);
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
		lblCategorizeBy.setText("X Variable:");

		factorVarList = new List(modelComposite, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 5);
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
//					xNumeric = "FALSE";
//				}
//				else if(numVarList.getSelectionCount()>0) {//if the purpose is to add
//					if(numVarList.getSelectionCount()==1){dataManipulationManager.moveSelectedStringFromTo( numVarList, axisVarList);
//					xNumeric = "TRUE";
//					}else MessageDialog.openWarning(getShell(), "Invalid Input", "You can only enter one X variable.");
//				}
//				else{//if the purpose is to remove
//					if(xNumeric.equals("FALSE")) dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
//					else dataManipulationManager.moveSelectedStringFromTo( axisVarList, numVarList);
//				}
//				enableFactorButtons(false);
//			}
//		});
		addAxisBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					
					String selectedNumVars[] = factorVarList.getSelection();
						axisVarList.add(selectedNumVars[0]);
						categoryAxisText.setText(axisVarList.getItems()[0]);
						if(lstCreateLines.getItemCount() == 0){
						tblclmnLevels.setText("");
						addItemToVarTable("");
						}
						getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
					table.pack();
					factorVarList.remove(factorVarList.getSelectionIndices());
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = axisVarList.getSelection();
						factorVarList.add(selectedNumVars[0]);
						categoryAxisText.setText("");
						tblclmnLevels.setText("Levels");
						getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
						if(lstCreateLines.getItemCount() == 0){
							removeAllItemsFromVarTable();
						}
						axisVarList.remove(axisVarList.getSelectionIndices());
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
//		axisVarList.addMouseListener(new MouseListener(){
//			@Override
//			public void mouseDoubleClick(MouseEvent e) {
//				// TODO Auto-generated method stub
//				if(xNumeric.equals("FALSE"))dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
//				else dataManipulationManager.moveSelectedStringFromTo( axisVarList, numVarList);
//				addAxisBtn.setEnabled(false);
//			}
//
//			@Override
//			public void mouseDown(MouseEvent e) {
//				// TODO Auto-generated method stub
//				if(axisVarList.getSelectionCount()>0){
//					numVarList.setSelection(-1);
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
		axisVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( axisVarList, factorVarList);
				addAxisBtn.setEnabled(false);
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
		
		lblCreateLinesBy = new Label(modelComposite, SWT.NONE);
		lblCreateLinesBy.setText("Create lines by:");
		
		btnCreateLines = new Button(modelComposite, SWT.NONE);
		btnCreateLines.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(factorVarList.getSelectionCount()>0) {//if the purpose is to add
					tblclmnLevels.setText("Levels");
					removeAllItemsFromVarTable();
					dataManipulationManager.moveSelectedStringFromTo( factorVarList, lstCreateLines);
					String[] catVar = DataManipulationManager.getEnvtLevels(lstCreateLines.getItem(0), file);
					System.out.println(catVar);
					for(String var : catVar)addItemToVarTable(var);
					getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
					numColsSpinner1.setEnabled(true);

				}
				else{//if the purpose is to remove
//					dataManipulationManager.moveSelectedStringFromTo( lstCreateLines, factorVarList);
					
					String[] catVar = DataManipulationManager.getEnvtLevels(lstCreateLines.getItem(0), file);
					System.out.println(catVar);
					for(String var : catVar)removeItemFromVarTable(var);
					dataManipulationManager.moveSelectedStringFromTo( lstCreateLines, factorVarList);				
					
					if(axisVarList.getItemCount()>0 && lstCreateLines.getItemCount() == 0){
						tblclmnLevels.setText("");
						addItemToVarTable("");
					}
					numColsSpinner1.setEnabled(false);
				}
				enableFactorButtons(false);
				moveBtn.setEnabled(false);

			}
		});
		btnCreateLines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 2, 1));
		btnCreateLines.setText("Add");
		btnCreateLines.setEnabled(false);
		
		lstCreateLines = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
//		lstCreateLines.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(lstCreateLines.getItemCount()== 0){
//					String selectedNumVars[] = factorVarList.getSelection();
//						axisVarList.add(selectedNumVars[0]);
//						tblclmnLevels.setText("");
//						addItemToVarTable("");
//						getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
//				}else{
//					String[] catVar = DataManipulationManager.getEnvtLevels(lstCreateLines.getItem(0), file);
//					for(String var : catVar)addItemToVarTable(var);
//					getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
//				}
//			}
//		});
		lstCreateLines.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				dataManipulationManager.moveSelectedStringFromTo( lstCreateLines, factorVarList);
				btnCreateLines.setEnabled(false);
				numColsSpinner1.setEnabled(false);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(lstCreateLines.getSelectionCount()>0){
					factorVarList.setSelection(-1);
					btnCreateLines.setText("Remove");
					btnCreateLines.setEnabled(true);
				}
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		lstCreateLines.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
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

		tbtmDisplay = new TabItem(tabFolder, SWT.NONE);
		tbtmDisplay.setText("Display Options");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmDisplay.setControl(composite_2);
		composite_2.setLayout(new GridLayout(7, false));

		Label lblMainTitle = new Label(composite_2, SWT.NONE);
		lblMainTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblMainTitle.setText("Main Title:");

		mainTitleText = new Text(composite_2, SWT.BORDER);
		int maxLen =45;
		mainTitleText.setTextLimit(maxLen);
		mainTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));

		Label lblHorizontalAxisLabel = new Label(composite_2, SWT.NONE);
		lblHorizontalAxisLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblHorizontalAxisLabel.setText("Horizontal Axis Label:");

		categoryAxisText = new Text(composite_2, SWT.BORDER);
		categoryAxisText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));

		Label lblValueAxisLabel = new Label(composite_2, SWT.NONE);
		lblValueAxisLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		lblValueAxisLabel.setText("Vertical Axis:");
		
		tableAxis = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tableAxis.setLinesVisible(true);
		tableAxis.setHeaderVisible(true);
		tableAxis.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_tableAxis = new GridData(SWT.FILL, SWT.FILL, true, true, 7, 1);
		gd_tableAxis.heightHint = 126;
		tableAxis.setLayoutData(gd_tableAxis);
		
		tableColumn = new TableColumn(tableAxis, SWT.NONE);
		tableColumn.setWidth(85);
		tableColumn.setText("Variable");
		
		tableColumn_5 = new TableColumn(tableAxis, SWT.NONE);
		tableColumn_5.setWidth(83);
		tableColumn_5.setText("Axis Label");
		
		tableColumn_6 = new TableColumn(tableAxis, SWT.NONE);
		tableColumn_6.setWidth(87);
		tableColumn_6.setText("Lower Limit");
		
		tableColumn_8 = new TableColumn(tableAxis, SWT.NONE);
		tableColumn_8.setWidth(72);
		tableColumn_8.setText("Upper Limit");
		
		label = new Label(composite_2, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label.setText("Axis Label Orientation:");
		
		cmboAxisOrientation = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
		cmboAxisOrientation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmboAxisOrientation.setItems(new String[] {"parallel to axis", "horizontal", "vertical", "perpendicular to axis"});
		cmboAxisOrientation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		cmboAxisOrientation.select(0);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		btnDrawBoxAround = new Button(composite_2, SWT.CHECK);
		btnDrawBoxAround.setSelection(true);
		btnDrawBoxAround.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		btnDrawBoxAround.setText("Draw box around plot");

		btnDisplaylinelabels = new Button(composite_2, SWT.CHECK);
		btnDisplaylinelabels.setEnabled(false);
		btnDisplaylinelabels.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnDisplaylinelabels.getSelection()){
					btnShowLegend.setEnabled(false);
				}else btnShowLegend.setEnabled(true);
			}
		});
		btnDisplaylinelabels.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		btnDisplaylinelabels.setText("Display line labels");

		btnShowLegend = new Button(composite_2, SWT.CHECK);
		btnShowLegend.setSelection(true);
		btnShowLegend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnShowLegend.getSelection()) {enableShowLegend(true);btnDisplaylinelabels.setEnabled(false);}
				else {enableShowLegend(false);btnDisplaylinelabels.setEnabled(true);}
			}
		});
		btnShowLegend.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		btnShowLegend.setText("Show legend");
		new Label(composite_2, SWT.NONE);
		
				lblPosition = new Label(composite_2, SWT.NONE);
				lblPosition.setText("Position:");
		
				legPositionComo = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
				legPositionComo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				legPositionComo.setEditable(true);
				legPositionComo.setItems(new String[] {"bottom", "bottom-left", "bottom-right", "center", "left", "right", "top", "top-left", "top-right"});
				legPositionComo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				legPositionComo.select(2);
		new Label(composite_2, SWT.NONE);
		
		lblNewLabel_1 = new Label(composite_2, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		label_1 = new Label(composite_2, SWT.NONE);
		label_1.setText("Number of columns:");
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		numColsSpinner1 = new Spinner(composite_2, SWT.BORDER | SWT.READ_ONLY);
		numColsSpinner1.setEnabled(false);
		numColsSpinner1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					String[] selectedStrings = lstCreateLines.getItems();
					String[] catLevels;
					int maxLevels = 1;
					for(int i=0; i< lstCreateLines.getItemCount(); i++){
						catLevels = DataManipulationManager.getEnvtLevels(selectedStrings[i], file);
						maxLevels = maxLevels * catLevels.length;
					}	
					numColsSpinner1.setMaximum(maxLevels);
					numColsSpinner1.setMinimum(1);
				}
			
		});
		numColsSpinner1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_numColsSpinner1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_numColsSpinner1.widthHint = 10;
		numColsSpinner1.setLayoutData(gd_numColsSpinner1);
		numColsSpinner1.setMaximum(5);
		numColsSpinner1.setMinimum(1);
		numColsSpinner1.setSelection(1);
		new Label(composite_2, SWT.NONE);
		
				lblTitle = new Label(composite_2, SWT.NONE);
				lblTitle.setText("Title:");
		
				legTitleText = new Text(composite_2, SWT.BORDER);
				legTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 5, 1));

		btnDisplayMultipleGraphs = new Button(composite_2, SWT.CHECK);
		btnDisplayMultipleGraphs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnDisplayMultipleGraphs.getSelection()) displayMultipleGraphs(true);
				else displayMultipleGraphs(false);
			}
		});

		btnDisplayMultipleGraphs.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
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
				numRowsSpinner.setMaximum(5);
				numRowsSpinner.setMinimum(1);
				numRowsSpinner.setSelection(2);
				numRowsSpinner.setEnabled(false);
		new Label(composite_2, SWT.NONE);
				new Label(composite_2, SWT.NONE);
		
				lblNumberOfColumns = new Label(composite_2, SWT.NONE);
				lblNumberOfColumns.setEnabled(false);
				lblNumberOfColumns.setText("Number of columns:");

		numColsSpinner = new Spinner(composite_2, SWT.BORDER | SWT.READ_ONLY);
		numColsSpinner.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_numColsSpinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_numColsSpinner.widthHint = 10;
		numColsSpinner.setLayoutData(gd_numColsSpinner);
		numColsSpinner.setMaximum(5);
		numColsSpinner.setMinimum(1);
		numColsSpinner.setSelection(1);
		numColsSpinner.setEnabled(false);
		new Label(composite_2, SWT.NONE);
		
				lblOrientation = new Label(composite_2, SWT.NONE);
				lblOrientation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
				lblOrientation.setEnabled(false);
				lblOrientation.setText("Orientation:");
		
				orientGraphsCombo = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
				orientGraphsCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				orientGraphsCombo.setEditable(false);
				orientGraphsCombo.setEnabled(false);
				orientGraphsCombo.setItems(new String[] {"Left-to-right", "Top-to-bottom"});
				orientGraphsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
				orientGraphsCombo.select(0);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		TabItem tbtmBars = new TabItem(tabFolder, SWT.NONE);
		tbtmBars.setText("Other Options");

		composite = new Composite(tabFolder, SWT.NONE);
		tbtmBars.setControl(composite);
		composite.setLayout(new GridLayout(5, false));

		Label lblPlot = new Label(composite, SWT.NONE);
		lblPlot.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 5, 1));
		lblPlot.setText("Format Components (lines and points):");

		table = new Table(composite, SWT.BORDER | SWT.NO_FOCUS | SWT.HIDE_SELECTION);
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setRedraw(true);

		tblclmnLevels = new TableColumn(table, SWT.NONE);
		tblclmnLevels.setWidth(75);
		tblclmnLevels.setText("Levels");

		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(75);
		tblclmnType.setText("Type");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(60);
		tableColumn_2.setText("Color");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(25);

		TableColumn tblclmnLine = new TableColumn(table, SWT.NONE);
		tblclmnLine.setWidth(60);
		tblclmnLine.setText("Line");

		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(25);

		TableColumn tblclmnWidth = new TableColumn(table, SWT.NONE);
		tblclmnWidth.setWidth(60);
		tblclmnWidth.setText("Width"); //SPINNER

		TableColumn tblclmnPoint = new TableColumn(table, SWT.NONE);
		tblclmnPoint.setWidth(30);
		tblclmnPoint.setText("Pt.");

		TableColumn tableColumn_7 = new TableColumn(table, SWT.NONE);
		tableColumn_7.setWidth(25);

		TableColumn tblclmnPointSize = new TableColumn(table, SWT.NONE);
		tblclmnPointSize.setWidth(70);
		tblclmnPointSize.setText("Point Size"); //SPINNER

		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				// height cannot be per row so simply set
				event.height = plotImage.getBounds().height;
				TableItem item = (TableItem) event.item;
				TableEditor te[] = (TableEditor[]) item.getData("Editors");
				
				for(TableEditor t: te){
					t.layout();
				}
			}
		});

		displayErrorBarsBtn = new Button(composite, SWT.CHECK);
		displayErrorBarsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(displayErrorBarsBtn.getSelection()) enableErrorBarsOptions(true);
				else enableErrorBarsOptions(false);
			}
		});
		displayErrorBarsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		displayErrorBarsBtn.setText("Display Error Bars");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		errorBarsLabel = new Label(composite, SWT.NONE);
		errorBarsLabel.setEnabled(false);
		errorBarsLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		errorBarsLabel.setText("ERROR BARS");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		confidenceLvlBtn = new Button(composite, SWT.RADIO);
		confidenceLvlBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableErrorBarsOptions(true);
			}
		});
		confidenceLvlBtn.setEnabled(false);
		confidenceLvlBtn.setSelection(true);
		confidenceLvlBtn.setText("Confidence level");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblLevel = new Label(composite, SWT.NONE);
		lblLevel.setEnabled(false);
		lblLevel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLevel.setText("Level (%):");

		confidenceLvlValue = new Spinner(composite, SWT.BORDER);
		confidenceLvlValue.setEnabled(false);
		GridData gd_confidenceLvlValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_confidenceLvlValue.widthHint = 10;
		confidenceLvlValue.setLayoutData(gd_confidenceLvlValue);
		confidenceLvlValue.setMaximum(99);
		confidenceLvlValue.setMinimum(1);
		confidenceLvlValue.setSelection(95);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		stdErrorBtn = new Button(composite, SWT.RADIO);
		stdErrorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableErrorBarsOptions(true);
			}
		});
		stdErrorBtn.setEnabled(false);
		stdErrorBtn.setText("Standard Error");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblMultiplier = new Label(composite, SWT.NONE);
		lblMultiplier.setEnabled(false);
		lblMultiplier.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMultiplier.setText("Multiplier:");

		stdErrorValue = new Spinner(composite, SWT.BORDER);
		stdErrorValue.setEnabled(false);
		GridData gd_stdErrorValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_stdErrorValue.widthHint = 10;
		stdErrorValue.setLayoutData(gd_stdErrorValue);
		stdErrorValue.setMaximum(3);
		stdErrorValue.setMinimum(1);
		new Label(composite, SWT.NONE);
//		initializeForm();
		return container;

	}

//	void initializeForm(){
//		dlgManager.initializeSingleButtonList(factorVarList, lstCreateLines, btnCreateLines);
//	}
	
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
		// TODO Auto-generated method stub
		lblPosition.setEnabled(state);
		legTitleText.setEnabled(state);
		lblTitle.setEnabled(state);
		legPositionComo.setEnabled(state);
		label_1.setEnabled(state);
//		numColsSpinner1.setEnabled(state);
	}

	private void enableErrorBarsOptions(boolean state) {
		// TODO Auto-generated method stub
		errorBarsLabel.setEnabled(state);
		confidenceLvlBtn.setEnabled(state);
		lblLevel.setEnabled(false);
		confidenceLvlValue.setEnabled(false);

		stdErrorBtn.setEnabled(state);
		lblMultiplier.setEnabled(false);
		stdErrorValue.setEnabled(false);
		if(state){
			if(confidenceLvlBtn.getSelection()){
				confidenceLvlValue.setEnabled(true);
				lblLevel.setEnabled(true);
			}
			else if(stdErrorBtn.getSelection()){
				stdErrorValue.setEnabled(true);
				lblMultiplier.setEnabled(true);
			}
		}
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

		for(int i=0;i<table.getItemCount(); i++){
			tableItem = table.getItem(i);
			editors = (TableEditor[])tableItem.getData("Editors");
			for(TableEditor te : editors){
				te.layout();
			}
		}

		table.update();
		table.pack();
		composite.pack();
	}
	
	protected void removeAllItemsFromVarTable() {
		// TODO Auto-generated method stub
		int item=0;
		for(int i=0;i<table.getItemCount(); i++){
			TableItem tableItem = table.getItem(i);
			TableEditor[] editors = (TableEditor[])tableItem.getData("Editors");
			for(TableEditor te : editors){
				te.getEditor().dispose();
			}
		}
//		System.out.println(varName+"'s index is: "+Integer.toString(item));

		table.pack();
		table.removeAll();
		
//		for(int i=0;i<table.getItemCount(); i++){
//			TableItem tableItem = table.getItem(i);
//			TableEditor[] editors = (TableEditor[])tableItem.getData("Editors");
//			for(TableEditor te : editors){
//				te.layout();
//			}
//		}
//
//		table.update();
		table.pack();
		composite.pack();
	}	
	

	protected void addItemToVarTable(String varName) {
		// TODO Auto-generated method stub
		final RGB[] colors = GraphsUtilities.getGrayShades(table.getItemCount()+1);
		final int ctr=table.getItemCount();
		final TableItem tableItem = new TableItem(table, SWT.CENTER);
		tableItem.setText(0, varName);

		TableEditor[] tableEditors = new TableEditor[8];
		tableEditors[0] = new TableEditor(table);//color chooser button
		tableEditors[1] = new TableEditor(table);//color chooser button
		tableEditors[2] = new TableEditor(table);//lineType
		tableEditors[3] = new TableEditor(table);//lineType chooser button
		tableEditors[4] = new TableEditor(table);//width spinner
		tableEditors[5] = new TableEditor(table);//pointType
		tableEditors[6] = new TableEditor(table);//pointType chooser button
		tableEditors[7] = new TableEditor(table);//point size spinner

		for(TableEditor te : tableEditors){
			//			te = new TableEditor(table);
			te.grabHorizontal = true;
			te.grabVertical = true;
			te.layout();
		}

		CCombo varTypeList = new CCombo(table, SWT.READ_ONLY | SWT.CENTER);
		varTypeList.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		varTypeList.setItems(new String[]{"lines only", "overplot", "both"});
		varTypeList.select(1);

		GraphsUtilities.refreshTableColors(table, 2, colors);

		//		tableItem.setBackground(2, new Color(Display.getCurrent(), colors[ctr]));

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
					tableItem.setBackground(2, new Color(Display.getCurrent(), rgbColor));
				}catch(Exception ex){
					System.out.println("Exception at RGB choose color");
				}
			}
		});


		final Label lineLabel = new Label(table, SWT.BORDER | SWT.CENTER);
		lineLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lineLabel.setImage(ResourceManager.getPluginImage("Star", "icons/line1.png"));
		lineLabel.setData("lineType", Integer.parseInt("1"));
		//		tableItem.setImage(4,ResourceManager.getPluginImage("Star", "icons/line1.png"));

		Button chooseLineButton = new Button(table,SWT.NONE | SWT.CENTER);
		chooseLineButton.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		chooseLineButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = ctr;
				ChooseLineTypesDialog lineDlg = new ChooseLineTypesDialog(getShell());
				lineDlg.open();
				if(lineDlg.getReturnCode()==0){
					lineLabel.setImage(ResourceManager.getPluginImage("Star", "icons/line"+lineDlg.getChosenPattern()+".png"));
					lineLabel.setData("lineType", Integer.parseInt(lineDlg.getChosenPattern()));
				}
			}
		});
		//		
		Spinner widthValue = new Spinner(table, SWT.BORDER);
		widthValue.setMaximum(30);
		widthValue.setMinimum(5);
		widthValue.setSelection(10);
		widthValue.setDigits(1);

		final Label pointLabel = new Label(table, SWT.BORDER | SWT.CENTER);
		pointLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		pointLabel.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol1.png"));
		pointLabel.setBounds(tableItem.getBounds());
		pointLabel.setData("pointChars", "1");

		Button choosePlotBtn = new Button(table, SWT.NONE | SWT.CENTER);
		choosePlotBtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		choosePlotBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ChoosePlotSymbolsDialog choosePlotDlg = new ChoosePlotSymbolsDialog(getShell());
				choosePlotDlg.open();
				if(choosePlotDlg.getReturnCode()==0){
					pointLabel.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol"+choosePlotDlg.getChosenSymbol()+".png"));
					pointLabel.setData("pointChars", choosePlotDlg.getChosenSymbol());
				}
			}
		});

		Spinner pointSize = new Spinner(table, SWT.BORDER);
		pointSize.setMaximum(30);
		pointSize.setMinimum(5);
		pointSize.setSelection(10);
		pointSize.setDigits(1);

		tableEditors[0].setEditor(varTypeList, tableItem, 1);
		tableEditors[1].setEditor(chooseColorButton, tableItem, 3);
		tableEditors[2].setEditor(lineLabel, tableItem, 4);
		tableEditors[3].setEditor(chooseLineButton, tableItem, 5);
		tableEditors[4].setEditor(widthValue, tableItem, 6);
		tableEditors[5].setEditor(pointLabel, tableItem, 7);
		tableEditors[6].setEditor(choosePlotBtn, tableItem, 8);
		tableEditors[7].setEditor(pointSize, tableItem, 9);

		for(TableEditor te: tableEditors){
			te.layout();
		}

		tableItem.setData("Editors", tableEditors);
		table.pack();
		composite.pack();
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
		lstCreateLines.setSelection(-1);

		addGroupsBtn.setEnabled(state);
		addAxisBtn.setEnabled(state);
		btnCreateLines.setEnabled(state);
		if(state){
			addGroupsBtn.setText("Add");
			addAxisBtn.setText("Add");
			btnCreateLines.setText("Add");
			if(groupsVarList.getItemCount()>0)addGroupsBtn.setEnabled(false);
			if(axisVarList.getItemCount()>0)addAxisBtn.setEnabled(false);
//			if(lstCreateLines.getItemCount()>0)btnCreateLines.setEnabled(false);
		}
	}

	public void enableNumericButtons(boolean state){
//		addAxisBtn.setEnabled(state);
//		axisVarList.setSelection(-1);
		
//		if(state){
//			addAxisBtn.setText("Add");
//			if(axisVarList.getItemCount()>0)addAxisBtn.setEnabled(false);
//		}
		addBtn.setEnabled(state);
		moveBtn.setEnabled(state);
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
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
			System.out.println("graph type: " + analysisType);
			LineGraphDialog graph = new LineGraphDialog(parentShell,analysisType,file);
			close();
			graph.open();
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void okPressed(){
		boolean errorBarsChecked = false;
		boolean continueWithErrorBars=false;

//		if(btnDataValues.getSelection()) plotData = "rawData";
//		else plotData = "means";


		if(responseVarList.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add variable(s) from the numeric variables list.");
		else if(axisVarList.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add x variable from the factors list.");
		else if(lstCreateLines.getItemCount()>3) MessageDialog.openError(getShell(), "Error", "Please enter three variables only on Create Lines List.");

		//		else if(plotData=="means" && axisVarList.getItemCount()<1){
//			MessageDialog.openWarning(getShell(), "Enter Required Fields for Category Means", "Please specify a category variable.");
//		}
		else{//if all conditions are satisfied
			yVars = responseVarList.getItems();
			mTitle = mainTitleText.getText(); //null; //
//			yAxisLab = valueAxisText.getText(); //null; //value axis label
			xAxisLab = categoryAxisText.getText(); //null; //category axis label
			lineVars = lstCreateLines.getItems();
			if(axisVarList.getItemCount()<1)xVar = null;
			else xVar = axisVarList.getItem(0);
			if(groupsVarList.getItemCount()<1)byVar = null;
			else byVar = groupsVarList.getItem(0);
			
//			if(lowerLimitText.getText().isEmpty() || lowerLimitText.getText().toLowerCase().equals("auto"))yMinValue = null; //"20";//
//			else yMinValue = lowerLimitText.getText();
//			if(upperLimitText.getText().isEmpty() || upperLimitText.getText().toLowerCase().equals("auto"))yMaxValue = null; //"20";//
//			else yMaxValue = upperLimitText.getText();

			if(displayErrorBarsBtn.getSelection() && displayErrorBarsBtn.getEnabled()){
				errBars = "TRUE";
				errorBarsChecked = true;
				if(confidenceLvlBtn.getSelection()){
					confLev = confidenceLvlValue.getSelection() / Math.pow(10, 2);
					typeErrBar = "confInt";
				}
				else if(stdErrorBtn.getSelection()){
					errMult = stdErrorValue.getSelection();
					typeErrBar = "stdErr";
				}
			}
			else errBars = "FALSE";

			if(btnShowLegend.getSelection()){
				showLeg = "TRUE"; //"FALSE";//
				legPos = legPositionComo.getText().replaceAll("-", ""); //eight other choices
				legCol = numColsSpinner1.getSelection();
				if(!legTitleText.getText().isEmpty())legTitle = legTitleText.getText(); //null; //
				else legTitle = null;

				//				legTitle = legTitleText.getText(); //null; //
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

			yAxisLab =  new String[yVars.length];
			yMinValue = new String[yVars.length];
			yMaxValue = new String[yVars.length];
			
			int ctr1=0;
			for(TableItem tableAxisItem : tableAxis.getItems()){
				TableEditor[] axisEditors = (TableEditor[]) tableAxisItem.getData("Editors");
				yAxisLab[ctr1] = ((Text)axisEditors[0].getEditor()).getText();
				yMinValue[ctr1] = GraphsUtilities.checkIfAutoOrNumeric(((Text)axisEditors[1].getEditor()).getText());
				yMaxValue[ctr1] = GraphsUtilities.checkIfAutoOrNumeric(((Text)axisEditors[2].getEditor()).getText());
				ctr1++;
			}
			
		if(table.getItemCount() > 0){	
			plotCol = new String[table.getItemCount()];
			linePtTypes = new String[table.getItemCount()];
			lineTypes  = new int[table.getItemCount()];
			lineWidths  = new double[table.getItemCount()];
			pointChars  = new String[table.getItemCount()];
			pointCharSizes  = new double[table.getItemCount()];
		}
			if(btnDisplaylinelabels.getSelection())	showLineLabels = "TRUE";
			else showLineLabels = "FALSE";

			int ctr=0;
			
			ArrayList<String> arrPlotCol = new ArrayList<String>();
			for(TableItem tableItem: table.getItems()){
				int index = ctr;
				TableEditor editors[]= (TableEditor[]) tableItem.getData("Editors"); 
				linePtTypes[index] =  ((CCombo) editors[0].getEditor()).getText().substring(0, 1); //{1 for all lines, possible values 1 to 6 - see diagram};
				lineTypes[index] = ((Integer) editors[2].getEditor().getData("lineType")).intValue(); //{1 for all lines, possible values 1 to 6 - see diagram};
				System.out.println(Integer.toString(index) +" : this linePtTypes "+ linePtTypes[index]);
				lineWidths[index] = dataManipulationManager.convertInttoDouble(((Spinner) editors[4].getEditor()).getSelection(), 1); //{1 for all lines, possible values 1 to 6 - see diagram};; //{1 for all lines, possible values 0.5 to 3, increment: 0.5};
				pointChars[index] =  (String) editors[5].getEditor().getData("pointChars");//{0 for all lines, possible values 0 to 25 - see diagram};
				pointCharSizes[index]  = dataManipulationManager.convertInttoDouble(((Spinner) editors[7].getEditor()).getSelection(), 1); //{1 for all lines, possible values 0.5 to 3, increment: 0.1};
				arrPlotCol.add( GraphsUtilities.convertToRrgbFormat(tableItem.getBackground(2).getRGB().toString()));
				ctr++;
			}
			if(!arrPlotCol.isEmpty())	plotCol = arrPlotCol.toArray(new String[arrPlotCol.size()]);
			String dataFileName = file.getAbsolutePath().replaceAll("\\\\", "/");
			
			axisLabelStyle = cmboAxisOrientation.getSelectionIndex();
			
//			if (cmboAlignment.getText().equals("horizontal")){
//				legHoriz = "TRUE";
//			}else legHoriz = "FALSE";
			
			if(axisVarList.getItemCount()<1){
				xVar = null;
			}
			else{
				xVar = axisVarList.getItem(0);
				if(errorBarsChecked){//if error bars is checked.
					boolean hasLevelsWithOneDatum = false;

					StringBuilder sb = new StringBuilder();
					for(String nvar: yVars){
						String[] levelsWithOneValue = ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().checkLevelsWithOneDatum(dataFileName.replaceAll("\\\\", "/"), xVar, nvar, byVar);
						System.out.println("value of levels with one value: "+levelsWithOneValue[0]);
						if(levelsWithOneValue[0].equals("FALSE")){
							hasLevelsWithOneDatum = true;
						}
						else if(!levelsWithOneValue[0].equals("TRUE") && !levelsWithOneValue[0].equals("FALSE")){
							hasLevelsWithOneDatum = true;
							sb.append("\n");
							sb.append(nvar);
							sb.append(" [Group: No. of level(s)]\n");
							
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
				}
			}

			if(errBars.equals("FALSE") || continueWithErrorBars ){
				File outputFolder = GraphsUtilities.createOutputFolder(file.getName(),analysisType);
				if(!outputFolder.exists()) outputFolder.mkdir();

				OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Creating Line Graph");
				rInfo.open();
				
				
				ProjectExplorerView.rJavaManager.getRJavaGraphManager().createGraphLineplot(
						outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/", 
						dataFileName, 
						yVars,
						xVar,
						lineVars,
						mTitle, 
						yAxisLab, 
						xAxisLab,
						yMinValue, 
						yMaxValue,
						axisLabelStyle,
						byVar, 
						errBars,
						typeErrBar, 
						errMult, 
						confLev, 
						plotCol, 
						showLineLabels, 
						showLeg, 
						legPos, 
						legTitle, 
						legCol,
						boxed,
						linePtTypes, 
						lineTypes, 
						lineWidths,
						pointChars,
						pointCharSizes, 
						multGraphs,
						numRowsGraphs, 
						numColsGraphs,
						orientGraphs);	
				
//				ProjectExplorerView.rJavaManager.getRJavaGraphManager().createGraphLineplot(outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/", dataFileName, nVar, cVar, xNumeric, 
//						mTitle, yAxisLab, xAxisLab, yMinValue, yMaxValue, plotData, 
//						byVar, errBars, typeErrBar, errMult, confLev, plotCol, showLineLabels, showLeg, legPos, legTitle, boxed,
//						linePtTypes, lineTypes, lineWidths, pointChars, pointCharSizes, 
//						multGraphs, numRowsGraphs, numColsGraphs, orientGraphs);
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
		return new Point(741, 612);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}