package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.graphs.managers.GraphTableManager;
import org.irri.breedingtool.graphs.managers.RowEntityModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.GraphsUtilities;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Spinner;

public class PrincipalComponentAnalysisDialog extends Dialog {

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	
	private String filePath = PartStackHandler.getActiveElementID();
	private File file;
	private DialogFormUtility listManager = new DialogFormUtility();
	private DataManipulationManager dataManipulationManager = new DataManipulationManager(); 
	private Button btnOkay;
	private Button btnScreePlot;
	private Button btnBiplot;
	private Button btnPcaPlot;
	private Button btnCorr; 
	private Button btnCov;
	private Combo cmboTransformation; 
	private Label lblTransformation;
	private Group grpAnalyze;
	private Group grpGraph;
	private TabItem tbtmVariableDescription_1;
	private Composite composite_2; 
	private Label lblNumericVariables;
	private Label lblSelectedVariables;
	private List lstSelectedVariables;
	private ListViewer listViewer_2;
	private List lstNumericVariables;
	private ListViewer listViewer_3;
	private Button btnAddNumeric;
	private Button btnSetToFactor;
	private Label lblFactors;
	private List lstFactors;
	private ListViewer listViewer_4;
	private Button btnAddFactor;
	private Label lblIdvar;
	private Text txtIdvar;
	private Label lblNewLabel;
	private Group grpDisplay;
	private Button btnSavePcScores;
	private String[] Transformations = {"zerocenter", "unitvar", "none"};
	private Button btnUseIdVariable;
	private Button btnDescriptiveStatistics;
	private Table table;
	private TableColumn tableColumn;
	private TableColumn tableColumn_1;
	private TableColumn tableColumn_2;
	private TableColumn tableColumn_3;
	private Button btnShowLeg;
	private Label lblPostion;
	private Label lblTitle;
	private Text txtTitle;
	private CCombo cmbPosition;
	private Label lblNoOfColumns;
	private Spinner spinColumns;
	private int CURRENT_COLOR = 0;
	private GraphTableManager tableManager;
	private String legPos = "bottom-right";
	private String legTitle = null;
	private int legNcol = 1;
	private int axesNum = 3;
	private String[] pCol = {"rgb(255,0,0,max = 255)", "rgb(0,255,0,max = 255)", "rgb(0,0,255,max = 255)", "rgb(0,255,255,max = 255)", "rgb(127,127,127,max = 255)"}; //default: RGB values for gray
	private int[] pChars = {1,2,3,4,5};//{0 for all lines, possible values 0 to 25 - see diagram};
	private Double[] pSizes = {1.0,1.0,1.0,1.0,1.0};//{1 for all lines, possible values 0.5 to 3, increment: 0.1};
	private Label lblNoOfAxes;
	private Spinner spinAxes;
	private Button btnScatterPlotMatrix;
	private Button btnCorrelationMatrix;
	private Button btnCovarianceMatrix;
		
	public PrincipalComponentAnalysisDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Principal Component Analysis : " + new File (filePath).getName());
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		FillLayout fl_container = new FillLayout(SWT.HORIZONTAL);
		fl_container.marginHeight = 8;
		fl_container.marginWidth = 8;
		container.setLayout(fl_container);
		
		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		tbtmVariableDescription_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmVariableDescription_1.setText("Variable Description");
		
		composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmVariableDescription_1.setControl(composite_2);
		GridLayout gl_composite_2 = new GridLayout(4, false);
		composite_2.setLayout(gl_composite_2);
		
		lblNumericVariables = new Label(composite_2, SWT.NONE);
		GridData gd_lblNumericVariables = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblNumericVariables.horizontalIndent = 5;
		lblNumericVariables.setLayoutData(gd_lblNumericVariables);
		lblNumericVariables.setText("Numeric Variable(s):");
		lblNumericVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_2, SWT.NONE);
		
		lblSelectedVariables = new Label(composite_2, SWT.NONE);
		lblSelectedVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSelectedVariables.setText("Selected Variables:");
		lblSelectedVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_2, SWT.NONE);
		
		listViewer_3 = new ListViewer(composite_2, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstNumericVariables = listViewer_3.getList();
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstNumericVariables.horizontalIndent = 5;
		gd_lstNumericVariables.heightHint = 130;
		gd_lstNumericVariables.widthHint = 100;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);
		lstNumericVariables.setItems(new String[] {});
		
		btnAddNumeric = new Button(composite_2, SWT.NONE);
		GridData gd_btnAddNumeric = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddNumeric.widthHint = 90;
		btnAddNumeric.setLayoutData(gd_btnAddNumeric);
		btnAddNumeric.setText("Add");
		
		listViewer_2 = new ListViewer(composite_2, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstSelectedVariables = listViewer_2.getList();
		GridData gd_lstSelectedVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstSelectedVariables.heightHint = 130;
		gd_lstSelectedVariables.widthHint = 100;
		lstSelectedVariables.setLayoutData(gd_lstSelectedVariables);
		lstSelectedVariables.setItems(new String[] {});
		
		lblNewLabel = new Label(composite_2, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 2;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		btnSetToFactor = new Button(composite_2, SWT.NONE);
		GridData gd_btnSetToFactor = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnSetToFactor.widthHint = 110;
		btnSetToFactor.setLayoutData(gd_btnSetToFactor);
		btnSetToFactor.setText("Set to Factor");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		lblFactors = new Label(composite_2, SWT.NONE);
		GridData gd_lblFactors = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblFactors.horizontalIndent = 5;
		lblFactors.setLayoutData(gd_lblFactors);
		lblFactors.setText("Factor(s):");
		lblFactors.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_2, SWT.NONE);
		
		lblIdvar = new Label(composite_2, SWT.NONE);
		lblIdvar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblIdvar.setText("ID Variable:");
		lblIdvar.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_2, SWT.NONE);
		
		listViewer_4 = new ListViewer(composite_2, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstFactors = listViewer_4.getList();
		GridData gd_lstFactors = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstFactors.widthHint = 100;
		gd_lstFactors.heightHint = 90;
		gd_lstFactors.verticalIndent = 2;
		gd_lstFactors.horizontalIndent = 5;
		lstFactors.setLayoutData(gd_lstFactors);
		lstFactors.setItems(new String[] {});
		
		btnAddFactor = new Button(composite_2, SWT.NONE);
		btnAddFactor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(lstFactors.getSelectionCount()>0) {//add
					btnShowLeg.setSelection(true);
					btnShowLeg.setEnabled(true);
					legendOptions(true);
					String selectedVar[] = lstFactors.getSelection();
					txtIdvar.setText(selectedVar[0]);
					String catVar[] = DataManipulationManager.getEnvtLevels(txtIdvar.getText(), new File (filePath));
					for(String var : catVar)addDataToTable(var);
					btnPcaPlot.setSelection(true);
				}
				else{//remove
					btnShowLeg.setSelection(false);
					btnShowLeg.setEnabled(false);
					legendOptions(false);
					tableManager.removeAll();
					btnPcaPlot.setSelection(false);
				}
			}
		});
		GridData gd_btnAddFactor = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		gd_btnAddFactor.widthHint = 90;
		btnAddFactor.setLayoutData(gd_btnAddFactor);
		btnAddFactor.setText("Add");
		
		txtIdvar = new Text(composite_2, SWT.BORDER);
		txtIdvar.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if((!txtIdvar.getText().isEmpty()) && btnPcaPlot.getSelection()){
					btnUseIdVariable.setEnabled(true);
				}else btnUseIdVariable.setEnabled(false);
			}
		});
		txtIdvar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtIdvar.setEditable(false);
		GridData gd_txtIdvar = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		gd_txtIdvar.widthHint = 100;
		txtIdvar.setLayoutData(gd_txtIdvar);
		new Label(composite_2, SWT.NONE);
		
		TabItem tbtmOptions_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions_1.setText("Options");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions_1.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));
		
		grpDisplay = new Group(composite_1, SWT.NONE);
		grpDisplay.setText("Display");
		grpDisplay.setLayout(new GridLayout(6, false));
		grpDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		btnDescriptiveStatistics = new Button(grpDisplay, SWT.CHECK);
		btnDescriptiveStatistics.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnDescriptiveStatistics.setText("Descriptive Statistics");
		new Label(grpDisplay, SWT.NONE);
		
		btnCovarianceMatrix = new Button(grpDisplay, SWT.CHECK);
		btnCovarianceMatrix.setText("Covariance Matrix");
		new Label(grpDisplay, SWT.NONE);
		
		btnCorrelationMatrix = new Button(grpDisplay, SWT.CHECK);
		btnCorrelationMatrix.setText("Correlation Matrix");
		
		grpAnalyze = new Group(composite_1, SWT.NONE);
		grpAnalyze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpAnalyze.setLayout(new GridLayout(3, false));
		grpAnalyze.setText("Analyze");
		
		btnCorr = new Button(grpAnalyze, SWT.RADIO);
		btnCorr.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblTransformation.setEnabled(false);
				cmboTransformation.setEnabled(false);
			}
		});
		btnCorr.setSelection(true);
		btnCorr.setText("Correlation Matrix");
		new Label(grpAnalyze, SWT.NONE);
		new Label(grpAnalyze, SWT.NONE);
		
		btnCov = new Button(grpAnalyze, SWT.RADIO);
		btnCov.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblTransformation.setEnabled(true);
				cmboTransformation.setEnabled(true);
			}
		});
		btnCov.setText("Variance Covariance Matrix");
		new Label(grpAnalyze, SWT.NONE);
		new Label(grpAnalyze, SWT.NONE);
		
		lblTransformation = new Label(grpAnalyze, SWT.NONE);
		lblTransformation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTransformation.setEnabled(false);
		lblTransformation.setText("Transformation");
		
		cmboTransformation = new Combo(grpAnalyze, SWT.READ_ONLY);
		cmboTransformation.setEnabled(false);
		cmboTransformation.setItems(new String[] {"Zero - Centered", "Unit Variance", "No transformation"});
		GridData gd_cmboTransformation = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_cmboTransformation.widthHint = 100;
		cmboTransformation.setLayoutData(gd_cmboTransformation);
		cmboTransformation.select(0);
		
		btnSavePcScores = new Button(composite_1, SWT.CHECK);
		btnSavePcScores.setText("Save PC Scores to a file");
		btnSavePcScores.setSelection(true);
		btnSavePcScores.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);
		
		lblNoOfAxes = new Label(composite_1, SWT.NONE);
		lblNoOfAxes.setText("Number of PC axes:");
		
		spinAxes = new Spinner(composite_1, SWT.BORDER);
		spinAxes.setMinimum(2);
		spinAxes.setSelection(3);
		
		grpGraph = new Group(composite_1, SWT.NONE);
		grpGraph.setLayout(new GridLayout(4, false));
		GridData gd_grpGraph = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_grpGraph.widthHint = 423;
		grpGraph.setLayoutData(gd_grpGraph);
		grpGraph.setText("Graph");
		
		btnScatterPlotMatrix = new Button(grpGraph, SWT.CHECK);
		btnScatterPlotMatrix.setText("Scatter Plot Matrix");
		btnScatterPlotMatrix.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		
		btnScreePlot = new Button(grpGraph, SWT.CHECK);
		btnScreePlot.setText("Scree Plot");
		btnScreePlot.setSelection(true);
		btnScreePlot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		
		btnBiplot = new Button(grpGraph, SWT.CHECK);
		btnBiplot.setText("Biplot");
		btnBiplot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		
		btnPcaPlot = new Button(grpGraph, SWT.CHECK);
		btnPcaPlot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnPcaPlot.getSelection() && (!txtIdvar.getText().isEmpty())){
					 btnUseIdVariable.setEnabled(true);
				}else btnUseIdVariable.setEnabled(false);
			}
		});
		btnPcaPlot.setText("PCA Plot");
		btnPcaPlot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		
		table = new Table(grpGraph, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_table.widthHint = 420;
		gd_table.heightHint = 150;
		table.setLayoutData(gd_table);
		
		tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(109);
		tableColumn.setText("Levels");
		
		tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(78);
		tableColumn_1.setText("Color");
		tableColumn_1.setResizable(false);
		
		tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(44);
		tableColumn_2.setText("Size");
		
		tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(75);
		tableColumn_3.setText("Symbol");
		tableColumn_3.setResizable(false);
		
		btnUseIdVariable = new Button(grpGraph, SWT.CHECK);
		btnUseIdVariable.setEnabled(false);
		btnUseIdVariable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableManager.setColumnsDisabled(!btnUseIdVariable.getSelection(), 3);
				if(btnUseIdVariable.getSelection()){
					btnShowLeg.setEnabled(false);
					btnShowLeg.setSelection(false);
					legendOptions(false);
				} else {
					btnShowLeg.setEnabled(true);
					btnShowLeg.setSelection(true);
					legendOptions(true);
				}
			}
		});
		btnUseIdVariable.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));
		btnUseIdVariable.setText("Use ID Variable ");
		
		btnShowLeg = new Button(grpGraph, SWT.CHECK);
		btnShowLeg.setEnabled(false);
		btnShowLeg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnShowLeg.getSelection()) legendOptions(true);
				else legendOptions(false);
			}
		});
		btnShowLeg.setText("Show legend");
		btnShowLeg.setSelection(true);
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		new Label(grpGraph, SWT.NONE);
		
		lblPostion = new Label(grpGraph, SWT.NONE);
		lblPostion.setEnabled(false);
		lblPostion.setText("Position:");
		
		cmbPosition = new CCombo(grpGraph, SWT.BORDER);
		cmbPosition.setEnabled(false);
		cmbPosition.setItems(new String[] {"bottom-right", "bottom", "bottom-left", "left", "top-left", "top", "top-right", "right", "center"});
		cmbPosition.setEditable(false);
		cmbPosition.select(0);
		cmbPosition.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		lblNoOfColumns = new Label(grpGraph, SWT.NONE);
		lblNoOfColumns.setEnabled(false);
		lblNoOfColumns.setText("No. of Columns:");
		
		spinColumns = new Spinner(grpGraph, SWT.BORDER);
		spinColumns.setEnabled(false);
		spinColumns.setMaximum(5);
		spinColumns.setMinimum(1);
		spinColumns.setSelection(1);
		
		lblTitle = new Label(grpGraph, SWT.NONE);
		lblTitle.setEnabled(false);
		lblTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		lblTitle.setText("Title:");
		
		txtTitle = new Text(grpGraph, SWT.BORDER);
		txtTitle.setEnabled(false);
		txtTitle.setText("Legend:");
		txtTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
		initializeContent();
		return container;
	}

	void initializeContent(){
		listManager.initializeSingleButtonList(lstNumericVariables, lstSelectedVariables, btnAddNumeric);
		listManager.initializeSingleSelectionList(lstFactors, txtIdvar, btnAddFactor);
		listManager.initializeVariableMoveList(lstNumericVariables, lstFactors, btnSetToFactor , filePath);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeFactorList(lstFactors, filePath);	
		
		//for tableManager
				ArrayList<Integer> headers = new ArrayList<Integer>();
				headers.add(GraphTableManager.ROW_STATIC_TEXT);
				headers.add(GraphTableManager.ROW_COLOR_PICKER);
				headers.add(GraphTableManager.ROW_SPINNER);
				headers.add(GraphTableManager.ROW_SYMBOL);
				
				tableManager = new GraphTableManager(table,headers);
				tableManager.ROW_HEIGHT = 28;
				tableManager.SYMBOL_HEIGHT = 25;
		

	}
	
	protected void addDataToTable(String selectedNumVars){
		final RGB[] colors = GraphsUtilities.getGrayShades(table.getItemCount()+1);
		tableManager.addItem(new Object[]{selectedNumVars,GraphsUtilities.getRandomColor(),new RowEntityModel(tableManager.ROW_SPINNER, 10, 10, -1,1),"0"});

		getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);

		getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
	}
	
	protected void legendOptions(boolean state) {
		// TODO Auto-generated method stub
		lblPostion.setEnabled(state);
		lblTitle.setEnabled(state);
		lblNoOfColumns.setEnabled(state);
		cmbPosition.setEnabled(state);
		txtTitle.setEnabled(state);
		spinColumns.setEnabled(state);
	}
	
	void resetDialog(){
		lstNumericVariables.removeAll();
		lstSelectedVariables.removeAll();
		lstFactors.removeAll();
		txtIdvar.setText("");
		btnAddNumeric.setText("Add");
		btnAddFactor.setText("Add");
		btnAddFactor.setEnabled(false);
		//btnNormalization.setSelection(true);
		//btnCentralization.setSelection(false);
		btnCorr.setSelection(true);
		btnCov.setSelection(false);
		btnScreePlot.setSelection(true);
		btnBiplot.setSelection(false);
		btnPcaPlot.setSelection(false);
		btnSavePcScores.setSelection(true);
		btnUseIdVariable.setEnabled(false);
		btnUseIdVariable.setSelection(false);
		btnShowLeg.setSelection(true);
		spinAxes.setSelection(3);
		btnShowLeg.setEnabled(false);
		btnShowLeg.setSelection(false);
		legendOptions(false);
		tableManager.removeAll();
		txtTitle.setText("Legend:");
		cmbPosition.setText("bottom-right");
		spinColumns.setSelection(1);
		btnDescriptiveStatistics.setSelection(false);
		btnCorrelationMatrix.setSelection(false);
		btnCovarianceMatrix.setSelection(false);
		lblTransformation.setEnabled(false);
		cmboTransformation.setEnabled(false);
		cmboTransformation.setText("Zero - Centered");
		btnScatterPlotMatrix.setSelection(false);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeFactorList(lstFactors, filePath);
	}
	
	@Override
	protected void buttonPressed(int ID){
		if(ID == IDialogConstants.OK_ID){
			okPressed();
		}
		else if(ID == IDialogConstants.CANCEL_ID){
			cancelPressed();
		}
		else if(ID == IDialogConstants.RETRY_ID){
			resetDialog();
			
		}
	}
	
	@Override
	protected void okPressed(){
		if(lstSelectedVariables.getItemCount() < 2){
			MessageDialog.openError(this.getShell(),"Error","Please add at least two Numeric Variables.");
			return;
		}
		if(spinAxes.getSelection() > lstSelectedVariables.getItemCount()){
			MessageDialog.openError(this.getShell(),"Error","The number of PC axes should be less than or equal to the selected variables");
			spinAxes.getSelection();
			return;
		}
		
		String outputFolder = StarAnalysisUtilities.createOutputFolder(filePath, "PrincipalComponentAnalysis");
		OperationProgressDialog rInfo = new OperationProgressDialog(
				getShell(), "Performing Analysis");
		rInfo.open();
		
		btnOkay.setEnabled(false);
		String dataFileName = filePath.replace(File.separator, "/");
		String outputPath = outputFolder;
		String[] var = lstSelectedVariables.getItems();
		String idVar = (txtIdvar.getText().isEmpty()) ? null : txtIdvar.getText();
		boolean corMatx = btnCorrelationMatrix.getSelection();
		boolean covMatx = btnCovarianceMatrix.getSelection();
		boolean scatterMatx = btnScatterPlotMatrix.getSelection();
		boolean descriptiveStat = btnDescriptiveStatistics.getSelection();
		boolean saveScore = btnSavePcScores.getSelection();	
		boolean scree = btnScreePlot.getSelection();
		boolean biplot = btnBiplot.getSelection();
		boolean pcaplot = btnPcaPlot.getSelection();
//		boolean useIdVar = btnUseIdVariable.getSelection();
		String matx = "corr";
		String transform= "zerocenter";
		
		if(btnCov.getSelection()){		
			 matx = "cov";
			 transform = Transformations[cmboTransformation.getSelectionIndex()];
		}
		if(table.getItemCount() > 0){	
			pCol = new String[table.getItemCount()];
			pSizes  = new Double[table.getItemCount()];
			pChars  = new int[table.getItemCount()];
		}		
		ArrayList<String> arrpCol = new ArrayList<String>();
		ArrayList<Double> arrpSizes = new ArrayList<Double>();
		ArrayList<Integer> arrpChars = new ArrayList<Integer>();
		
		for(String[] data : tableManager.getDataToString() ){
			arrpCol.add(GraphsUtilities.convertToRrgbFormat(data[1]));
			arrpSizes.add(Double.parseDouble(data[2]));
			arrpChars.add(Integer.parseInt(data[3]));
			System.out.println(data[1] + " " + data[2] + " " + data[3]);
		}
		
		if(!arrpCol.isEmpty()) pCol = arrpCol.toArray(new String[arrpCol.size()]);
		if(!arrpSizes.isEmpty()) pSizes = arrpSizes.toArray(new Double[arrpSizes.size()]);
		if(!arrpChars.isEmpty()) pChars = convertToPrimitiveInt(arrpChars.toArray(new Integer[arrpChars.size()]));
				
		boolean useIdVar = btnUseIdVariable.getSelection();
		boolean showLeg = false;
		if(btnShowLeg.getSelection()){
			showLeg = true;
			legPos = cmbPosition.getText().replaceAll("-", "");
			if(!txtTitle.getText().isEmpty())legTitle = txtTitle.getText();
			else legTitle = "";
			legNcol = spinColumns.getSelection();
		}
		
		axesNum = spinAxes.getSelection();
		
//		StarAnalysisUtilities.testVarArgs(
//				dataFileName, 
//				outputPath.replace(File.separator, "/"),
//				var, 
//				idVar,
//				matx,
//				transform,
//				saveScore,
//				scree, 
//				biplot, 
//				pcaplot,
//				useIdVar,
//				pChars,
//				convertToPrimitiveDouble(pSizes),
//				pCol,  
//				showLeg,		
//				legTitle ,
//				legPos,
//				legNcol);
		ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doPCA(
				dataFileName,
				outputPath.replace(File.separator , "/"),
				var,
				idVar,
				corMatx,
				covMatx,
				descriptiveStat,
				matx,
				transform,
				saveScore,
				scatterMatx,
				scree, 
				biplot, 
				pcaplot,
				useIdVar,
				pChars,
				convertToPrimitiveDouble(pSizes),
				pCol,
				showLeg,		
				legTitle ,
				legPos,
				legNcol,
				axesNum);
		this.getShell().setMinimized(true);
		StarAnalysisUtilities.openAndRefreshFolder(outputPath);
		btnOkay.setEnabled(true);
		rInfo.close();
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	
	int[] convertToPrimitiveInt(Integer[] val){
		int[] returnVal = new int[val.length];
		for(int i = 0; i < val.length; i++){
			returnVal[i] = val[i];
		}
		return returnVal;
	}
	
	double[] convertToPrimitiveDouble(Double[] val){
		double[] returnVal = new double[val.length];
		for(int i = 0; i < val.length; i++){
			returnVal[i] = val[i];
		}
		return returnVal;
	}	
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.RETRY_ID, "Reset", false);
		btnOkay = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	
	@Override
	protected Point getInitialSize() {
		return new Point(609, 725);
	}
}
