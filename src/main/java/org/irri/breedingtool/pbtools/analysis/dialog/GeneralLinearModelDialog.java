package org.irri.breedingtool.pbtools.analysis.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.GraphsUtilities;
import org.irri.breedingtool.utility.PBToolAnalysisUtilities;
import org.irri.breedingtool.utility.TextVarValidator;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;

public class GeneralLinearModelDialog extends Dialog {

	//	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private List numVarList;
	private List factorVarList;
	private List covariateVarList;
	private Button addCovariateBtn;
	private Button moveBtn;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private DialogFormUtility dlgManager = new DialogFormUtility();

	//specify parameters
	private String[] xVar = null; 		//but should be provided by user 
	private String[] yVar = null; 		//but should be provided by user
	private String mTitle = null; 
	private String[] xAxisLab = null; //If erased, should be "" for each x-variable}
	private String[] yAxisLab = null; //If erased, should be "" for each y-variable}
	private String[] xMinValue = null;
	private String[] xMaxValue = null;
	private String[] yMinValue = null;
	private String[] yMaxValue = null;
	private String dispLine = "FALSE";
	private String byVar = null; 
	private String pointCol = "rgb(0 ,0,0, max = 255)"; 
	private int pointChar = 1;  		//possible values 0 to 25 - see diagram};
	private double pointCharSize = 1; 	//possible values 0.5 to 3, increment: 0.1};
	private int lineType = 1; 		//possible values 1 to 6 - see diagram};
	private String lineCol = "rgb(0,0,0, max = 255)"; 
	private double lineWidth = 1; 		//possible values 0.5 to 3, increment: 0.5};
	private String dispR2P = "FALSE";
	private String r2PPos = "bottomright";
	private String boxed = "FALSE";
	private String multGraphs = "FALSE";
	private int numRowsGraphs = 1;
	private int numColsGraphs = 1;
	private String orientGraphs = "top-bottom";
	private int axisLabelStyle = 1;

	private String analysisType;
	private Button addResponseBtn;
	private List responseVarList;
	//	private List plotsVarList;
	private Table tblFixedEffect;
	private Composite composite;
	private Shell parentShell;
	private Group grpSpecifyModelTo;
	private Label lblFixedEffect;
	private List listFixedEffect;
	private Label lblRandomEffects;
	private Button btnRandomEffect;
	private Label lblError;
	private Button btnError;
	private List listRandomEffect;
	private List listError;
	private CCombo combo_1;
	private Label lblFunctions;
	private Label lblRandomEffects_1;
	private Label lblFunctions_1;
	private CCombo combo_2;
	private Table tblRandomEffect;
	private TableColumn tblclmnEffects_1;
	private Label lblError_1;
	private Label lblFunctions_2;
	private CCombo combo_3;
	private Table tblError;
	private TableColumn tblclmnEffects_2;
	private Combo comboSign;
	private Label label;
	private Label label_1;
	private Button btnFixedEffect;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */

	public GeneralLinearModelDialog(Shell parentShell, String analysisType, File file) {
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

		parent.getShell().setText("Scatter Plot Graph: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TabItem tbtmModelSpecifications_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setText("Model Specification");

		Composite modelComposite = new Composite(tabFolder, SWT.NONE);
		tbtmModelSpecifications_1.setControl(modelComposite);
		modelComposite.setLayout(new GridLayout(5, false));

		Label lblNumericVariables = new Label(modelComposite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNumericVariables.setText("Numeric Variables:");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		Label lblYVariables = new Label(modelComposite, SWT.NONE);
		lblYVariables.setText("Response Variable(s):");

		numVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_numVarList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 3);
		gd_numVarList.heightHint = 95;
		gd_numVarList.widthHint = 126;
		numVarList.setLayoutData(gd_numVarList);
		numVarList.setItems(numericVariables);
		numVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				//				String selectedNumVars[] = numVarList.getSelection();
				//				addItemToVarTable(selectedNumVars[0], xVarList);
				//				dataManipulationManager.moveSelectedStringFromTo( numVarList, xVarList);
				//				numVarList.remove(numVarList.getSelectionIndices());
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(numVarList.getSelectionCount()>0){
					moveBtn.setEnabled(true);
					factorVarList.setSelection(-1);
					covariateVarList.setSelection(-1);
					addCovariateBtn.setText("Add");
					addResponseBtn.setText("Add");
					moveBtn.setText("Set to Factor");
					enableNumericButtons(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addResponseBtn = new Button(modelComposite, SWT.NONE);
		addResponseBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					dataManipulationManager.moveSelectedStringFromTo( numVarList, responseVarList,moveBtn);
				}
				else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( responseVarList, numVarList,moveBtn);
				}
				enableNumericButtons(false);
			}
		});

		GridData gd_addResponseBtn = new GridData(SWT.LEFT, SWT.CENTER, false, true, 2, 1);
		gd_addResponseBtn.widthHint = 52;
		addResponseBtn.setLayoutData(gd_addResponseBtn);
		addResponseBtn.setText("Add");
		addResponseBtn.setEnabled(false);

		responseVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		responseVarList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		responseVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( responseVarList, numVarList, moveBtn);
				addResponseBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(responseVarList.getSelectionCount()>0){
					numVarList.setSelection(-1);
					addResponseBtn.setText("Remove");
					addResponseBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		Label lblResponseVariables = new Label(modelComposite, SWT.NONE);
		lblResponseVariables.setText("Covariate(s):");

		addCovariateBtn = new Button(modelComposite, SWT.NONE);
		addCovariateBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						covariateVarList.add(selectedNumVars[i]);
					}
					numVarList.remove(numVarList.getSelectionIndices());
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = covariateVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						numVarList.add(selectedNumVars[i]);
					}
					covariateVarList.remove(covariateVarList.getSelectionIndices());
				}
				enableNumericButtons(false);
			}
		});
		addCovariateBtn.setEnabled(false);
		GridData gd_addCovariateBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_addCovariateBtn.widthHint = 52;
		addCovariateBtn.setLayoutData(gd_addCovariateBtn);
		addCovariateBtn.setText("Add");

		covariateVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_covariateVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_covariateVarList.widthHint = 111;
		covariateVarList.setLayoutData(gd_covariateVarList);
		covariateVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo(covariateVarList, numVarList, moveBtn);
				covariateVarList.remove(covariateVarList.getSelectionIndices());
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(covariateVarList.getSelectionCount()>0){
					numVarList.setSelection(-1);
					addCovariateBtn.setText("Remove");
					addCovariateBtn.setEnabled(true);
					moveBtn.setEnabled(false);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

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
		new Label(modelComposite, SWT.NONE);

		Label lblFactors = new Label(modelComposite, SWT.NONE);
		lblFactors.setText("Factors:");
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		grpSpecifyModelTo = new Group(modelComposite, SWT.NONE);
		grpSpecifyModelTo.setText("Specify Model to be Fitted:");
		grpSpecifyModelTo.setLayout(new GridLayout(3, false));
		grpSpecifyModelTo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 3));
		new Label(grpSpecifyModelTo, SWT.NONE);
		new Label(grpSpecifyModelTo, SWT.NONE);

		lblFixedEffect = new Label(grpSpecifyModelTo, SWT.NONE);
		GridData gd_lblFixedEffect = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblFixedEffect.widthHint = 111;
		lblFixedEffect.setLayoutData(gd_lblFixedEffect);
		lblFixedEffect.setText("Fixed Effect(s):");

		btnFixedEffect = new Button(grpSpecifyModelTo, SWT.NONE);
		btnFixedEffect.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnFixedEffect.setText("Add");
		btnFixedEffect.setEnabled(false);

		listFixedEffect = new List(grpSpecifyModelTo, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_listFixedEffect = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_listFixedEffect.widthHint = 111;
		gd_listFixedEffect.heightHint = 25;
		listFixedEffect.setLayoutData(gd_listFixedEffect);
		new Label(grpSpecifyModelTo, SWT.NONE);
		new Label(grpSpecifyModelTo, SWT.NONE);

		lblRandomEffects = new Label(grpSpecifyModelTo, SWT.NONE);
		GridData gd_lblRandomEffects = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblRandomEffects.widthHint = 111;
		lblRandomEffects.setLayoutData(gd_lblRandomEffects);
		lblRandomEffects.setText("Random Effect(s):");

		btnRandomEffect = new Button(grpSpecifyModelTo, SWT.NONE);
		btnRandomEffect.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnRandomEffect.setText("Add");
		btnRandomEffect.setEnabled(false);

		listRandomEffect = new List(grpSpecifyModelTo, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_listRandomEffect = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_listRandomEffect.widthHint = 111;
		gd_listRandomEffect.heightHint = 25;
		listRandomEffect.setLayoutData(gd_listRandomEffect);
		new Label(grpSpecifyModelTo, SWT.NONE);
		new Label(grpSpecifyModelTo, SWT.NONE);

		lblError = new Label(grpSpecifyModelTo, SWT.NONE);
		GridData gd_lblError = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblError.widthHint = 111;
		lblError.setLayoutData(gd_lblError);
		lblError.setText("Error:");

		btnError = new Button(grpSpecifyModelTo, SWT.NONE);
		btnError.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnError.setText("Add");
		btnError.setEnabled(false);

		listError = new List(grpSpecifyModelTo, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_listError = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_listError.widthHint = 111;
		gd_listError.heightHint = 25;
		listError.setLayoutData(gd_listError);

		factorVarList = new List(modelComposite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		factorVarList.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
		gd_factorVarList.widthHint = 65;
		factorVarList.setLayoutData(gd_factorVarList);
		factorVarList.setItems(factorVariables);
		new Label(modelComposite, SWT.NONE);
		new Label(modelComposite, SWT.NONE);

		comboSign = new Combo(modelComposite, SWT.READ_ONLY);
		comboSign.setItems(new String[] {"+", ":", "*"});
		GridData gd_comboSign = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_comboSign.widthHint = 36;
		comboSign.setLayoutData(gd_comboSign);
		comboSign.select(0);
		factorVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				moveBtn.setEnabled(false);
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
		factorVarList.addListener(SWT.MouseDown, new Listener(){
			@Override
			public void handleEvent(Event event) {
				if(factorVarList.getSelectionCount()==1){
					enableNumericButtons(false);
					numVarList.setSelection(-1);
					String[] s=factorVarList.getSelection();
					String isNumeric = dataManipulationManager.isNumeric(file.getAbsolutePath().replaceAll("\\\\","/"), s[0]);
					if(isNumeric.equals("TRUE")){
						moveBtn.setText("Set to Numeric");
						moveBtn.setEnabled(true);
					}
					else moveBtn.setEnabled(false);
					enableAddModelButtons(true);
				}
			}
		});
		TabItem tbtmBars = new TabItem(tabFolder, SWT.NONE);
		tbtmBars.setText("Options");

		composite = new Composite(tabFolder, SWT.NONE);
		tbtmBars.setControl(composite);
		composite.setLayout(new GridLayout(8, false));
		int maxLen=45;

		Label lblPlot = new Label(composite, SWT.NONE);
		lblPlot.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 8, 1));
		lblPlot.setText("Fixed Effects:");
		new Label(composite, SWT.NONE);

		lblFunctions = new Label(composite, SWT.NONE);
		lblFunctions.setText("Functions:");

		combo_1 = new CCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		combo_1.setItems(new String[] {"autoregressive order 1", "autoregressive order 2", "moving average order 1", "moving average order 2", "linear effect", "orthogonal polynomial"});
		combo_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		tblFixedEffect = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tblFixedEffect.setLinesVisible(true);
		tblFixedEffect.setHeaderVisible(true);
		tblFixedEffect.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tblFixedEffect.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));

		TableColumn tblclmnEffects = new TableColumn(tblFixedEffect, SWT.NONE);
		tblclmnEffects.setWidth(85);
		tblclmnEffects.setText("Effects");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		label = new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblRandomEffects_1 = new Label(composite, SWT.NONE);
		lblRandomEffects_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 8, 1));
		lblRandomEffects_1.setText("Random Effects:");
		new Label(composite, SWT.NONE);

		lblFunctions_1 = new Label(composite, SWT.NONE);
		lblFunctions_1.setText("Functions:");

		combo_2 = new CCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		combo_2.setItems(new String[] {"autoregressive order 1", "autoregressive order 2", "moving average order 1", "moving average order 2", "linear effect", "orthogonal polynomial"});
		combo_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		tblRandomEffect = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tblRandomEffect.setLinesVisible(true);
		tblRandomEffect.setHeaderVisible(true);
		tblRandomEffect.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tblRandomEffect.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));

		tblclmnEffects_1 = new TableColumn(tblRandomEffect, SWT.NONE);
		tblclmnEffects_1.setWidth(85);
		tblclmnEffects_1.setText("Effects");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		label_1 = new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblError_1 = new Label(composite, SWT.NONE);
		lblError_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 8, 1));
		lblError_1.setText("Error:");
		new Label(composite, SWT.NONE);

		lblFunctions_2 = new Label(composite, SWT.NONE);
		lblFunctions_2.setText(" Functions:");

		combo_3 = new CCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		combo_3.setItems(new String[] {"autoregressive order 1", "autoregressive order 2", "moving average order 1", "moving average order 2", "linear effect", "orthogonal polynomial"});
		combo_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		tblError = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tblError.setLinesVisible(true);
		tblError.setHeaderVisible(true);
		tblError.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tblError.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));

		tblclmnEffects_2 = new TableColumn(tblError, SWT.NONE);
		tblclmnEffects_2.setWidth(85);
		tblclmnEffects_2.setText("Effects");

		connectModelVarListToFactorVarList(factorVarList, listError, tblError, btnError);
		connectModelVarListToFactorVarList(factorVarList, listFixedEffect, tblFixedEffect, btnFixedEffect);
		connectModelVarListToFactorVarList(factorVarList, listRandomEffect, tblRandomEffect, btnRandomEffect);

		return container;
	}

	private void connectModelVarListToFactorVarList(final List fromList,
			final List toList, final Table table, final Button btn) {
		// TODO Auto-generated method stub
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(fromList.getSelectionCount()>0){// if add effect
					String model="";
					String selectedNumVars[] = fromList.getSelection();
					int missingColumns = selectedNumVars.length-table.getColumnCount();

					for(int i=0; i<=missingColumns; i++){
						TableColumn tableColumn = new TableColumn(table, SWT.NONE);
						tableColumn.setWidth(85);
						tableColumn.setText("Component"+Integer.toString(table.getColumnCount()-1));
					}

					TableItem tableItem =  new TableItem(table, SWT.CENTER);
					for(int i=0; i< selectedNumVars.length; i++){
						tableItem.setText(i+1, selectedNumVars[i]);
						model = model + selectedNumVars[i];
						if(i!=selectedNumVars.length-1) model = model + comboSign.getText();
					}

					if(doesTableContainKey(model,table)==-1){//add it to table
						tableItem.setText(0,model);
						toList.add(model);
						enableNumericButtons(false);
						btn.setEnabled(false);
					}else table.remove(table.getItemCount()-1);
				}
				else{// if remove effect
					String selectedString=toList.getSelection()[0];
					int deleteRow=doesTableContainKey(selectedString,table);
					if(deleteRow>=0){
						table.remove(deleteRow);
						toList.remove(toList.getSelectionIndices());	
						btn.setEnabled(false);
					}
				}
			}
		});

		toList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo(toList, fromList, btn);
				covariateVarList.remove(toList.getSelectionIndices());
				btn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(toList.getSelectionCount()>0){
					fromList.setSelection(-1);
					btn.setText("Remove");
					btn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected int doesTableContainKey(String selectedString, Table table) {
		// TODO Auto-generated method stub
		int deleteRow = -1;
		int c=0;
		for(TableItem tblItem: table.getItems()){
			if(tblItem.getText(0).equals(selectedString)){
				deleteRow=c;
				break;
			}
			c++;
		}
		return deleteRow;
	}

	protected void addFactorAsEffect(List fromList,
			List toList, Table tbl, Button btn) {
		// TODO Auto-generated method stub
		if(fromList.getSelectionCount()>0) {//if the purpose is to add
			dataManipulationManager.moveSelectedStringFromTo(fromList, toList, btn);
		}
		else{//if the purpose is to remove
			toList.remove(toList.getSelectionIndices());
		}
		enableNumericButtons(false);
	}

	protected void enableAddModelButtons(boolean state) {
		// TODO Auto-generated method stub
		btnFixedEffect.setEnabled(state);
		btnRandomEffect.setEnabled(state);
		btnError.setEnabled(state);
		
		if(state){
			btnFixedEffect.setText("Add");
			btnRandomEffect.setText("Add");
			btnError.setText("Add");
		}
	}

	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
	}

	public void enableNumericButtons(boolean state){
		addCovariateBtn.setEnabled(state);
		addResponseBtn.setEnabled(state);
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
			GeneralLinearModelDialog graph = new GeneralLinearModelDialog(parentShell,analysisType,file);
			close();
			graph.open();
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void okPressed(){
		if(covariateVarList.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add a covariate variable from the numeric variables list.");
		else if(responseVarList.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add a repsonse variable from the numeric variables list.");
		else if(listFixedEffect.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add a fixed effect variable from the factor variables list.");
		else{//if all conditions are satisfied
			
			xVar = covariateVarList.getItems();
			yVar = responseVarList.getItems(); 

			File outputFolder =  PBToolAnalysisUtilities.createOutputFolder(file.getName(), analysisType);
			if(!outputFolder.exists()) outputFolder.mkdir();

			String dataFileName = file.getAbsolutePath().replaceAll("\\\\", "/");

			OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Creating General Linear Model");
			
			rInfo.open();		
			//insert rjavamanager call here
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
		return new Point(449, 654);
	}

	/**
	 * Return th e initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}