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

public class HistogramDialog extends Dialog {

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

	//specify parameters
	private String[] nVar = null; //but should be provided by user
	private String mTitle = null; 
	private String[] yAxisLabs = null; 
	private String[] xAxisLabs = null;
	private String[] xMinValues = null; 
	private String[] xMaxValues = null;
	private String[] yMinValues = null; 
	private String[] yMaxValues = null;
	private String useFreq = "FALSE"; //"TRUE"; // 
	private String numBins = "Auto"; //for "Sturges" or integer
	private String byVar = null;
//	private String showLeg = "FALSE";
//	private String legPos = "bottomright"; //"bottomright" if showLeg = "TRUE"
//	private String legTitle = null;   
//	private String legHoriz = "FALSE";
	private String boxed = "FALSE"; 
	private String multGraphs = "FALSE";
	private int numRowsGraphs = 1;
	private int numColsGraphs = 1;
	private String orientGraphs = "top-bottom";
	private int axisLabelStyle = 1;
	
	private String barColor = "rgb(255,0,255, max = 255, alpha = 127)"; //{"rgb(255,0,255, max = 255, alpha = 127)","rgb(0,255,0, max = 255, alpha = 127)"}; 
	private String dispCurve = "TRUE"; //"FALSE"; //
	private String lineColor = "rgb(0,0,0, max = 255)"; //{"rgb(0,0,0, max = 255)","rgb(0,0,255, max = 255)"};
	private int lineType = 1; //{1, 2, 3}; //{1 for all lines, possible values 1 to 6 - see diagram};
	private double lineWidth = 1; //{1,1,1}; //{1 for all lines, possible values 0.5 to 3, increment: 0.5};

	private String analysisType;
	private Button addGroupsBtn;
	private List groupsVarList;
	private Text mainTitleText;
	private Button btnDrawBoxAround;
	private Button btnDisplayMultipleGraphs;
	private CCombo orientGraphsCombo;
	private Label lblOrientation;
	private Composite composite;
	private Label label_3;
	private Button btnFrequency;
	private Button btnProbabilityDensities;
	private Label lblUse;
	private Button btnOverlayKernelDensity;
	private Label lblNumberOfBars;
	private Text txtNumBars;
	private Shell parentShell;
	private Label lblNewLabel_1;
	private CCombo cmboAxisOrientation;
	private Table tableXaxis;
	private TableColumn tableColumn;
	private TableColumn tableColumn_7;
	private TableColumn tableColumn_8;
	private TableColumn tableColumn_9;
	private TabItem tbtmDisplayOptions;
	private Table tableYaxis;
	private TableColumn tableColumn_10;
	private TableColumn tableColumn_11;
	private TableColumn tableColumn_12;
	private TableColumn tableColumn_13;
	private Label lblNewLabel;
	private Spinner numRowsSpinner;
	private Label lblNumberOfColumns;
	private Spinner numColsSpinner;
	private Label lblLineType;
	private Label lineDefaultType;
	private Button lineDefaultTypebtn;
	private Label lblWidth;
	private Spinner lineWidthSpinner;
	private Label lblBarColor;
	private Label barDefaultColor;
	private Button barDefaultColorbtn;
	private Label lblLineColor;
	private Label lineDefaultColor;
	private Button lineDefaultColorbtn;
	private Label lblNewLabel_2;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public HistogramDialog(Shell parentShell, String analysisType, File file) {
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

		parent.getShell().setText("Histogram: "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
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
				String selectedNumVars[] = numVarList.getSelection();
				addItemToXaxisTable(selectedNumVars[0], responseVarList);
				addItemToYaxisTable(selectedNumVars[0], responseVarList);
				dataManipulationManager.moveSelectedStringFromTo( numVarList, responseVarList, moveBtn);
				numVarList.remove(numVarList.getSelectionIndices());
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
		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numVarList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = numVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){	
						addItemToXaxisTable(selectedNumVars[i], responseVarList);
						addItemToYaxisTable(selectedNumVars[i], responseVarList);
						responseVarList.add(selectedNumVars[i]);
//						getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
					}
					numVarList.remove(numVarList.getSelectionIndices());
				}
				else{//if the purpose is to remove

					String selectedNumVars[] = responseVarList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
//						removeItemFromVarTable(selectedNumVars[i]);
						removeItemFromXaxisTable(selectedNumVars[i]);
						removeItemFromYaxisTable(selectedNumVars[i]);
						numVarList.add(selectedNumVars[i]);
//						getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
					}
//					removeAllItemsFromVarTable();
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
//				removeItemFromVarTable(responseVarList.getSelection()[0]);
				removeItemFromXaxisTable(responseVarList.getSelection()[0]);
				removeItemFromYaxisTable(responseVarList.getSelection()[0]);
				dataManipulationManager.moveSelectedStringFromTo( responseVarList, numVarList, moveBtn);
//				removeAllItemsFromVarTable();
				responseVarList.remove(responseVarList.getSelectionIndices());
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
		
				Label lblGroupBy = new Label(modelComposite, SWT.NONE);
				lblGroupBy.setText("Group by:");
		
				factorVarList = new List(modelComposite, SWT.BORDER | SWT.V_SCROLL);
				GridData gd_factorVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
				gd_factorVarList.widthHint = 65;
				factorVarList.setLayoutData(gd_factorVarList);
				factorVarList.setItems(factorVariables);
				factorVarList.addMouseListener(new MouseListener(){
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						dataManipulationManager.moveSelectedStringFromTo( factorVarList, groupsVarList);
						addGroupsBtn.setEnabled(false);
					}

					@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						if(factorVarList.getSelectionCount()>0){
							groupsVarList.setSelection(-1);
							addGroupsBtn.setText("Remove");
							addGroupsBtn.setEnabled(true);
						}
					}

					@Override
					public void mouseUp(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				});
				
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
			}
		});
		GridData gd_addGroupsBtn = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
		gd_addGroupsBtn.widthHint = 52;
		addGroupsBtn.setLayoutData(gd_addGroupsBtn);
		addGroupsBtn.setText("Add");
		addGroupsBtn.setEnabled(false);

		groupsVarList = new List(modelComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_groupsVarList = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
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
		
		tbtmDisplayOptions = new TabItem(tabFolder, SWT.NONE);
		tbtmDisplayOptions.setText("Display Options");
		
				Composite composite_2 = new Composite(tabFolder, SWT.NONE);
				tbtmDisplayOptions.setControl(composite_2);
				composite_2.setLayout(new GridLayout(6, false));
				
						Label lblMainTitle = new Label(composite_2, SWT.NONE);
						lblMainTitle.setText("Main Title:");
								
										mainTitleText = new Text(composite_2, SWT.BORDER);
										int maxLen =45;
										mainTitleText.setTextLimit(maxLen);
										mainTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));
								
										lblUse = new Label(composite_2, SWT.NONE);
										lblUse.setText("Use:");
												
														btnFrequency = new Button(composite_2, SWT.RADIO);
														btnFrequency.addSelectionListener(new SelectionAdapter() {
															@Override
															public void widgetSelected(SelectionEvent e) {
																if(btnFrequency.getSelection()){
																	btnOverlayKernelDensity.setSelection(false);
																	btnProbabilityDensities.setSelection(false);
																	enableOverlayDensityOptions(false);
																	for(int i=0; i<tableYaxis.getItemCount(); i++){
																	TableItem tableItem = tableYaxis.getItem(i);
																	TableEditor[] tb = (TableEditor[]) tableItem.getData("Editors");																	
																	int c=0;
																	for(TableEditor te : tb){
																		if(c==0)((Text)te.getEditor()).setText("Frequency");
																		c++;
																		}
																	}
																}
																	
															}
														});
														btnFrequency.setSelection(true);
														btnFrequency.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
														btnFrequency.setText("frequencies");
														
																btnProbabilityDensities = new Button(composite_2, SWT.RADIO);
																btnProbabilityDensities.addSelectionListener(new SelectionAdapter() {
																	@Override
																	public void widgetSelected(SelectionEvent e) {
																		if(btnProbabilityDensities.getSelection()){
																			btnFrequency.setSelection(false);
																			for(int i=0; i<tableYaxis.getItemCount(); i++){
																				TableItem tableItem = tableYaxis.getItem(i);
																				TableEditor[] tb = (TableEditor[]) tableItem.getData("Editors");																	
																				int c=0;
																				for(TableEditor te : tb){
																					if(c==0)((Text)te.getEditor()).setText("Probability");
																					c++;
																					}
																				}
																		}
																	}
																});
																btnProbabilityDensities.setText("probability densities");
														new Label(composite_2, SWT.NONE);
														new Label(composite_2, SWT.NONE);
														new Label(composite_2, SWT.NONE);
														
																label_3 = new Label(composite_2, SWT.NONE);
																label_3.setText("X-Axis:");
																new Label(composite_2, SWT.NONE);
																new Label(composite_2, SWT.NONE);
																
																		Label lblValueAxisLabel = new Label(composite_2, SWT.NONE);
																		lblValueAxisLabel.setText("Y-Axis:");
																		new Label(composite_2, SWT.NONE);
																		new Label(composite_2, SWT.NONE);
																		
																		tableXaxis = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
																		tableXaxis.setLinesVisible(true);
																		tableXaxis.setHeaderVisible(true);
																		tableXaxis.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
																		tableXaxis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
																		
																		tableColumn = new TableColumn(tableXaxis, SWT.NONE);
																		tableColumn.setWidth(85);
																		tableColumn.setText("Variable");
																		
																		tableColumn_7 = new TableColumn(tableXaxis, SWT.NONE);
																		tableColumn_7.setWidth(83);
																		tableColumn_7.setText("Label");
																		
																		tableColumn_8 = new TableColumn(tableXaxis, SWT.NONE);
																		tableColumn_8.setWidth(87);
																		tableColumn_8.setText("Lower Limit");
																		
																		tableColumn_9 = new TableColumn(tableXaxis, SWT.NONE);
																		tableColumn_9.setWidth(72);
																		tableColumn_9.setText("Upper Limit");
																		
																		tableYaxis = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
																		tableYaxis.setLinesVisible(true);
																		tableYaxis.setHeaderVisible(true);
																		tableYaxis.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
																		tableYaxis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
																		
																		tableColumn_10 = new TableColumn(tableYaxis, SWT.NONE);
																		tableColumn_10.setWidth(85);
																		tableColumn_10.setText("Variable");
																		
																		tableColumn_11 = new TableColumn(tableYaxis, SWT.NONE);
																		tableColumn_11.setWidth(83);
																		tableColumn_11.setText("Label");
																		
																		tableColumn_12 = new TableColumn(tableYaxis, SWT.NONE);
																		tableColumn_12.setWidth(87);
																		tableColumn_12.setText("Lower Limit");
																		
																		tableColumn_13 = new TableColumn(tableYaxis, SWT.NONE);
																		tableColumn_13.setWidth(72);
																		tableColumn_13.setText("Upper Limit");
																		
																		lblNewLabel_1 = new Label(composite_2, SWT.NONE);
																		lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
																		lblNewLabel_1.setText("Axis Label Orientation:");
																		
																		cmboAxisOrientation = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
																		GridData gd_cmboAxisOrientation = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
																		gd_cmboAxisOrientation.widthHint = 193;
																		cmboAxisOrientation.setLayoutData(gd_cmboAxisOrientation);
																		cmboAxisOrientation.setItems(new String[] {"parallel to axis", "horizontal", "vertical", "perpendicular to axis"});
																		cmboAxisOrientation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
																		cmboAxisOrientation.select(0);
																		new Label(composite_2, SWT.NONE);
																		new Label(composite_2, SWT.NONE);
																		new Label(composite_2, SWT.NONE);
																		
																				btnDrawBoxAround = new Button(composite_2, SWT.CHECK);
																				btnDrawBoxAround.setSelection(true);
																				btnDrawBoxAround.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
																				btnDrawBoxAround.setText("Draw box around plot");
																				
																						btnDisplayMultipleGraphs = new Button(composite_2, SWT.CHECK);
																						btnDisplayMultipleGraphs.addSelectionListener(new SelectionAdapter() {
																							@Override
																							public void widgetSelected(SelectionEvent e) {
																								if(btnDisplayMultipleGraphs.getSelection()) displayMultipleGraphs(true);
																								else displayMultipleGraphs(false);
																							}
																						});
																						
																								btnDisplayMultipleGraphs.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
																								btnDisplayMultipleGraphs.setText("Display multiple graphs in a page");
																								new Label(composite_2, SWT.NONE);
																								
																								lblNewLabel = new Label(composite_2, SWT.NONE);
																								lblNewLabel.setText("Number of rows:");
																								lblNewLabel.setEnabled(false);
																								
																								numRowsSpinner = new Spinner(composite_2, SWT.BORDER | SWT.READ_ONLY);
																								numRowsSpinner.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
																								GridData gd_numRowsSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
																								gd_numRowsSpinner.widthHint = 10;
																								numRowsSpinner.setLayoutData(gd_numRowsSpinner);
																								numRowsSpinner.setMaximum(5);
																								numRowsSpinner.setMinimum(1);
																								numRowsSpinner.setSelection(2);
																								numRowsSpinner.setEnabled(false);
																								
																								lblNumberOfColumns = new Label(composite_2, SWT.NONE);
																								lblNumberOfColumns.setText("Number of columns:");
																								lblNumberOfColumns.setEnabled(false);
																								
																								numColsSpinner = new Spinner(composite_2, SWT.BORDER | SWT.READ_ONLY);
																								numColsSpinner.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
																								GridData gd_numColsSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
																								gd_numColsSpinner.widthHint = 10;
																								numColsSpinner.setLayoutData(gd_numColsSpinner);
																								numColsSpinner.setMaximum(5);
																								numColsSpinner.setMinimum(1);
																								numColsSpinner.setSelection(1);
																								numColsSpinner.setEnabled(false);
																								new Label(composite_2, SWT.NONE);
																								new Label(composite_2, SWT.NONE);
																								
																										lblOrientation = new Label(composite_2, SWT.NONE);
																										lblOrientation.setEnabled(false);
																										lblOrientation.setText("Orientation:");
																												
																														orientGraphsCombo = new CCombo(composite_2, SWT.BORDER | SWT.READ_ONLY);
																														orientGraphsCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
																														orientGraphsCombo.setEditable(false);
																														orientGraphsCombo.setEnabled(false);
																														orientGraphsCombo.setItems(new String[] {"Left-to-right", "Top-to-bottom"});
																														orientGraphsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
																														orientGraphsCombo.select(0);
																														new Label(composite_2, SWT.NONE);
		TabItem tbtmBars = new TabItem(tabFolder, SWT.NONE);
		tbtmBars.setText("Other Options");

		composite = new Composite(tabFolder, SWT.NONE);
		tbtmBars.setControl(composite);
		composite.setLayout(new GridLayout(6, false));

		lblNumberOfBars = new Label(composite, SWT.NONE);
		lblNumberOfBars.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		lblNumberOfBars.setText("Number of bars:");
		
				txtNumBars = new Text(composite, SWT.BORDER);
				txtNumBars.setText("Auto");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		btnOverlayKernelDensity = new Button(composite, SWT.CHECK);
		btnOverlayKernelDensity.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnOverlayKernelDensity.getSelection()){
					btnFrequency.setSelection(false);
					btnProbabilityDensities.setSelection(true);
					enableOverlayDensityOptions(true);
					for(int i=0; i<tableYaxis.getItemCount(); i++){
						TableItem tableItem = tableYaxis.getItem(i);
						TableEditor[] tb = (TableEditor[]) tableItem.getData("Editors");																	
						int c=0;
						for(TableEditor te : tb){
							if(c==0)((Text)te.getEditor()).setText("Probability");
							c++;
							}
						}
				}
				else enableOverlayDensityOptions(false);
			}
		});
		btnOverlayKernelDensity.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		btnOverlayKernelDensity.setText("Overlay kernel density estimates");

		Label lblPlot = new Label(composite, SWT.NONE);
		lblPlot.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 6, 1));
		lblPlot.setText("Format Components:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblBarColor = new Label(composite, SWT.NONE);
		lblBarColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblBarColor.setText("Bar Color:");
		
		barDefaultColor = new Label(composite, SWT.BORDER);
		GridData gd_barDefaultColor = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_barDefaultColor.widthHint = 102;
		barDefaultColor.setLayoutData(gd_barDefaultColor);
		barDefaultColor.setText(" ");
		barDefaultColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_MAGENTA));
		
		barDefaultColorbtn = new Button(composite, SWT.NONE);
		GridData gd_barDefaultColorbtn = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_barDefaultColorbtn.widthHint = 37;
		barDefaultColorbtn.setLayoutData(gd_barDefaultColorbtn);
		barDefaultColorbtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		barDefaultColorbtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				barColor = "rgb(255,0,255, max = 255, alpha = 127)";
				try{
					RGB rgbColor = GraphsUtilities.chooseColor();
					barDefaultColor.setBackground(new Color(Display.getCurrent(), rgbColor));
					String color = GraphsUtilities.convertToRrgbFormat(rgbColor.toString());
					barColor = color; 
				}catch(Exception ex){
					barColor = "rgb(255,0,255, max = 255, alpha = 127)"; 
					System.out.println("Exception at RGB choose color");
				}
			}
		});
		barDefaultColorbtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		
		lblNewLabel_2 = new Label(composite, SWT.NONE);
		GridData gd_lblNewLabel_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblNewLabel_2.widthHint = 429;
		lblNewLabel_2.setLayoutData(gd_lblNewLabel_2);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblLineType = new Label(composite, SWT.NONE);
		lblLineType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblLineType.setText("Line Type:");
		lblLineType.setEnabled(false);
		
		lineDefaultType = new Label(composite, SWT.BORDER);
		lineDefaultType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lineDefaultType.setImage(ResourceManager.getPluginImage("Star", "icons/line1.png"));
		lineDefaultType.setEnabled(false);
		lineDefaultType.setAlignment(SWT.CENTER);
		
		lineDefaultTypebtn = new Button(composite, SWT.NONE);
		lineDefaultTypebtn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lineDefaultTypebtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		lineDefaultTypebtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lineType = 1;
				ChooseLineTypesDialog chooseLineDlg = new ChooseLineTypesDialog(getShell());
				chooseLineDlg.open();
				if(chooseLineDlg.getReturnCode()==0){
					lineType = Integer.parseInt(chooseLineDlg.getChosenPattern());
					lineDefaultType.setImage(ResourceManager.getPluginImage("Star", "icons/line"+chooseLineDlg.getChosenPattern()+".png"));
				}
			}
		});
		lineDefaultTypebtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		lineDefaultTypebtn.setEnabled(false);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblLineColor = new Label(composite, SWT.NONE);
		lblLineColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblLineColor.setText("Line Color:");
		lblLineColor.setEnabled(false);
		
		lineDefaultColor = new Label(composite, SWT.BORDER);
		lineDefaultColor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lineDefaultColor.setText(" ");
		lineDefaultColor.setEnabled(false);
		lineDefaultColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		
		lineDefaultColorbtn = new Button(composite, SWT.NONE);
		lineDefaultColorbtn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lineDefaultColorbtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		lineDefaultColorbtn.setEnabled(false);
		lineDefaultColorbtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lineColor = "rgb(0,0,0, max = 255)"; 
				try{
					RGB rgbColor = GraphsUtilities.chooseColor();
					lineDefaultColor.setBackground(new Color(Display.getCurrent(), rgbColor));
					String color = GraphsUtilities.convertToRrgbFormat(rgbColor.toString());
					lineColor = color; 
				}catch(Exception ex){
					lineColor = "rgb(0,0,0, max = 255)"; 
					System.out.println("Exception at RGB choose color");
				}
			}
		});
		lineDefaultColorbtn.setImage(ResourceManager.getPluginImage("Star", "icons/ellipsis.png"));
		lineDefaultColorbtn.setEnabled(false);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblWidth = new Label(composite, SWT.NONE);
		lblWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblWidth.setText("Width:");
		lblWidth.setEnabled(false);
		
		lineWidthSpinner = new Spinner(composite, SWT.BORDER);
		lineWidthSpinner.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lineWidthSpinner.setMaximum(30);
		lineWidthSpinner.setMinimum(5);
		lineWidthSpinner.setSelection(10);
		lineWidthSpinner.setEnabled(false);
		lineWidthSpinner.setDigits(1);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);


		enableOverlayDensityOptions(false);
		return container;

	}
	

	
	protected void enableOverlayDensityOptions(boolean state) {
		// TODO Auto-generated method stub

		lblLineType.setEnabled(state);
		lineDefaultType.setEnabled(state);
		lineDefaultTypebtn.setEnabled(state);
		lblLineColor.setEnabled(state);
		lineDefaultColor.setEnabled(state);
		lineDefaultColorbtn.setEnabled(state);
		lblWidth.setEnabled(state);
		lineWidthSpinner.setEnabled(state);

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


//	protected void removeAllItemsFromVarTable() {
//		// TODO Auto-generated method stub
//		int item=0;
//		for(int i=0;i<table.getItemCount(); i++){
//			TableItem tableItem = table.getItem(i);
//			TableEditor[] editors = (TableEditor[])tableItem.getData("Editors");
//			for(TableEditor te : editors){
//				te.getEditor().dispose();
//			}
//		}
//		table.pack();
//		table.removeAll();
//		composite.pack();
//	}
	
	protected void removeItemFromXaxisTable(String varName) {
		// TODO Auto-generated method stub
		int item=0;	
		for(int i=0;i<tableXaxis.getItemCount(); i++){
			if(varName.equals(tableXaxis.getItem(i).getText(0)))item = i;
		}
		System.out.println(varName+"'s index is: "+Integer.toString(item));
		TableItem tableItem = tableXaxis.getItem(item);
		TableEditor[] editors = (TableEditor[])tableItem.getData("Editors");
		for(TableEditor te : editors){
			te.getEditor().dispose();
		}
		tableXaxis.remove(item);
		tableXaxis.pack();
		composite.pack();
	}

	protected void addItemToXaxisTable(String varName, List varList) {
		// TODO Auto-generated method stub
		int ctr=tableXaxis.getItemCount();
		if(varList.equals(responseVarList)) ctr=varList.getItemCount();
		
		final TableItem tableItem = new TableItem( tableXaxis, SWT.CENTER, ctr);
		tableItem.setText(0, varName);
		System.out.println("set tableItem name: "+varName);
		tableItem.setData("index",ctr);
		TableEditor[] tableEditors = new TableEditor[3];

		tableEditors[0] = new TableEditor(tableXaxis);//color chooser button
		tableEditors[1] = new TableEditor(tableXaxis);//color chooser button
		tableEditors[2] = new TableEditor(tableXaxis);//lineType chooser button

		Text newEditor = new Text(tableXaxis, SWT.NONE);
		newEditor.setText(varName);
		Text lowerLimitEditor = new Text(tableXaxis, SWT.NONE);
		lowerLimitEditor.setText("Auto");
		GraphsUtilities.addTextModifyListener(lowerLimitEditor);

		Text upperLimitEditor = new Text(tableXaxis, SWT.NONE);
		upperLimitEditor.setText("Auto");
		GraphsUtilities.addTextModifyListener(upperLimitEditor);

		tableEditors[0].grabHorizontal = true;
		tableEditors[1].grabHorizontal = true;
		tableEditors[2].grabHorizontal = true;

		tableEditors[0].setEditor(newEditor, tableItem, 1);
		tableEditors[1].setEditor(lowerLimitEditor, tableItem, 2);
		tableEditors[2].setEditor(upperLimitEditor, tableItem, 3);

		tableItem.setData("Editors", tableEditors);
		tableXaxis.pack();
		composite.pack();
		getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
		getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
	}
	
	protected void removeItemFromYaxisTable(String varName) {
		// TODO Auto-generated method stub
		int item=0;	
		for(int i=0;i<tableYaxis.getItemCount(); i++){
			if(varName.equals(tableYaxis.getItem(i).getText(0)))item = i;
		}
		System.out.println(varName+"'s index is: "+Integer.toString(item));
		TableItem tableItem = tableYaxis.getItem(item);
		TableEditor[] editors = (TableEditor[])tableItem.getData("Editors");
		for(TableEditor te : editors){
			te.getEditor().dispose();
		}
		tableYaxis.remove(item);
		tableYaxis.pack();
		composite.pack();
	}
	
	protected void addItemToYaxisTable(String varName, List varList) {
		// TODO Auto-generated method stub
		int ctr=tableYaxis.getItemCount();
		if(varList.equals(responseVarList)) ctr=varList.getItemCount();
		
		final TableItem tableItem = new TableItem( tableYaxis, SWT.CENTER, ctr);
		tableItem.setText(0, varName);
		System.out.println("set tableItem name: "+varName);
		tableItem.setData("index",ctr);
		TableEditor[] tableEditors = new TableEditor[3];

		tableEditors[0] = new TableEditor(tableYaxis);//color chooser button
		tableEditors[1] = new TableEditor(tableYaxis);//color chooser button
		tableEditors[2] = new TableEditor(tableYaxis);//lineType chooser button

		String useLabel;
		if(btnFrequency.getSelection()){
			useLabel = "Frequency";
		}else useLabel = "Probability";
		Text newEditor = new Text(tableYaxis, SWT.NONE);
		newEditor.setText(useLabel);
		Text lowerLimitEditor = new Text(tableYaxis, SWT.NONE);
		lowerLimitEditor.setText("Auto");
		GraphsUtilities.addTextModifyListener(lowerLimitEditor);

		Text upperLimitEditor = new Text(tableYaxis, SWT.NONE);
		upperLimitEditor.setText("Auto");
		GraphsUtilities.addTextModifyListener(upperLimitEditor);

		tableEditors[0].grabHorizontal = true;
		tableEditors[1].grabHorizontal = true;
		tableEditors[2].grabHorizontal = true;

		tableEditors[0].setEditor(newEditor, tableItem, 1);
		tableEditors[1].setEditor(lowerLimitEditor, tableItem, 2);
		tableEditors[2].setEditor(upperLimitEditor, tableItem, 3);

		tableItem.setData("Editors", tableEditors);
		tableYaxis.pack();
		composite.pack();
		getShell().setSize(getShell().getSize().x - 1, getShell().getSize().y);
		getShell().setSize(getShell().getSize().x + 1, getShell().getSize().y);
	}
	
	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
	}

	public void enableFactorButtons(boolean state){
		groupsVarList.setSelection(-1);

		addGroupsBtn.setEnabled(state);
		if(state){
			addGroupsBtn.setText("Add");
			if(groupsVarList.getItemCount()>0)addGroupsBtn.setEnabled(false);
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
			HistogramDialog graph = new HistogramDialog(parentShell,analysisType,file);
			close();
			graph.open();
		}
		super.buttonPressed(buttonId);
	}
	
	@Override
	protected void okPressed(){
		if(responseVarList.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add variable(s) from the numeric variables list.");
		else{//if all conditions are satisfied
			nVar = responseVarList.getItems();
			mTitle = mainTitleText.getText(); //null; //
//			yAxisLabs = valueAxisText.getText(); //null; //value axis label
//			xAxisLabs = categoryAxisText.getText(); //null; //category axis label
			
			if(groupsVarList.getItemCount()<1)byVar = null;
			else byVar = groupsVarList.getItem(0);
			
			if(btnFrequency.getSelection())useFreq = "TRUE";
			else useFreq = "FALSE";
			
			xAxisLabs = new String[nVar.length];
			xMinValues = new String[nVar.length];
			xMaxValues = new String[nVar.length];
			yAxisLabs =  new String[nVar.length];
			yMinValues = new String[nVar.length];
			yMaxValues = new String[nVar.length];

			int ctr=0;
			for(TableItem tableItem : tableXaxis.getItems()){
				TableEditor[] editors = (TableEditor[]) tableItem.getData("Editors");
				if(ctr<nVar.length){
					//					System.out.println("Still X.");
					xAxisLabs[ctr] = ((Text)editors[0].getEditor()).getText();
					xMinValues[ctr] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[1].getEditor()).getText());
					xMaxValues[ctr] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[2].getEditor()).getText());
				}
//				else{
//					System.out.println("Now Y: "+ Integer.toString(ctr-nVar.length));
//					yAxisLabs[ctr-nVar.length] = ((Text)editors[0].getEditor()).getText();
//					yMinValues[ctr-nVar.length] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[1].getEditor()).getText());
//					yMaxValues[ctr-nVar.length] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[2].getEditor()).getText());
//				}
				ctr++;
			}
			
			int ctr1=0;
			for(TableItem tableItem : tableYaxis.getItems()){
				TableEditor[] editors = (TableEditor[]) tableItem.getData("Editors");
				if(ctr1<nVar.length){
					//					System.out.println("Still X.");
					yAxisLabs[ctr1] = ((Text)editors[0].getEditor()).getText();
					yMinValues[ctr1] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[1].getEditor()).getText());
					yMaxValues[ctr1] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[2].getEditor()).getText());
				}
//				else{
//					System.out.println("Now Y: "+ Integer.toString(ctr1-nVar.length));
//					yAxisLabs[ctr1-nVar.length] = ((Text)editors[0].getEditor()).getText();
//					yMinValues[ctr1-nVar.length] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[1].getEditor()).getText());
//					yMaxValues[ctr1-nVar.length] = GraphsUtilities.checkIfAutoOrNumeric(((Text)editors[2].getEditor()).getText());
//				}
				ctr1++;
			}
			
//			if(lowerLimitY.getText().isEmpty() || lowerLimitY.getText().toLowerCase().equals("auto"))yMinValues = null; //"20";//
//			else yMinValues = lowerLimitY.getText();	
//			//Y AXIS RANGE
//			if(lowerLimitY.getText().isEmpty() || lowerLimitY.getText().toLowerCase().equals("auto"))yMinValues = null; //"20";//
//			else yMinValues = lowerLimitY.getText();
//			if(upperLimitY.getText().isEmpty() || upperLimitY.getText().toLowerCase().equals("auto"))yMaxValues = null; //"20";//
//			else yMaxValues = upperLimitY.getText();		
//			//X AXIS RANGE
//			if(lowerLimitX.getText().isEmpty() || lowerLimitX.getText().toLowerCase().equals("auto"))xMinValues = null; //"20";//
//			else xMinValues = lowerLimitX.getText();
//			if(upperLimitX.getText().isEmpty() || upperLimitX.getText().toLowerCase().equals("auto"))xMaxValues = null; //"20";//
//			else xMaxValues = upperLimitX.getText();

			numBins = txtNumBars.getText();
			
//			if(btnShowLegend.getSelection()){
//				showLeg = "TRUE"; //"FALSE"; //
//				legPos = legPositionComo.getText().replaceAll("-", ""); //eight other choices
//				
//				if(!legTitleText.getText().isEmpty())legTitle = legTitleText.getText(); //null; //
//				else legTitle = null;				
//				legTitle = legTitleText.getText(); //null; //
//			}
//			else{
//				showLeg = "FALSE";
//				legPos = null;
//				legTitle = null;
//			}			
//			if (cmboAlignment.getText().equals("horizontal")){
//				legHoriz = "TRUE";
//			}else legHoriz = "FALSE";

			axisLabelStyle = cmboAxisOrientation.getSelectionIndex();
			
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
			lineWidth = dataManipulationManager.convertInttoDouble(lineWidthSpinner.getSelection(), 1);
			
//			barColor = new String [table.getItemCount()];
//			lineColor = new String[table.getItemCount()];		
//			lineType  = new int[table.getItemCount()];
//			lineWidth  = new double[table.getItemCount()];
//			TableItem tableItem = table.getItem(0);
//			TableEditor editors[]= (TableEditor[]) tableItem.getData("Editors"); 
//			lineType = ((Integer) editors[1].getEditor().getData("lineType")).intValue();
//			lineWidth = dataManipulationManager.convertInttoDouble(((Spinner) editors[3].getEditor()).getSelection(), 1);
//			barColor = GraphsUtilities.convertToTransparentRrgbFormat(tableItem.getBackground(1).getRGB().toString());
//			lineColor = GraphsUtilities.convertToRrgbFormat(tableItem.getBackground(5).getRGB().toString());
//			int ctr11=0;
//			for(TableItem tableItem: table.getItems()){
//				int index = 0;
//				TableEditor editors[]= (TableEditor[]) tableItem.getData("Editors"); 
//				lineType[index] = ((Integer) editors[1].getEditor().getData("lineType")).intValue(); //{1 for all lines, possible values 1 to 6 - see diagram};
//				lineWidth[index] = dataManipulationManager.convertInttoDouble(((Spinner) editors[3].getEditor()).getSelection(), 1); //{1 for all lines, possible values 1 to 6 - see diagram};; //{1 for all lines, possible values 0.5 to 3, increment: 0.5};
//				barColor[index] = GraphsUtilities.convertToTransparentRrgbFormat(tableItem.getBackground(1).getRGB().toString());
//				lineColor[index] = GraphsUtilities.convertToRrgbFormat(tableItem.getBackground(5).getRGB().toString());
//				lineType = ((Integer) editors[1].getEditor().getData("lineType")).intValue();
//				lineWidth = dataManipulationManager.convertInttoDouble(((Spinner) editors[3].getEditor()).getSelection(), 1);
//				barColor = GraphsUtilities.convertToTransparentRrgbFormat(tableItem.getBackground(1).getRGB().toString());
//				lineColor = GraphsUtilities.convertToRrgbFormat(tableItem.getBackground(5).getRGB().toString());
//				ctr11++;
//			}
			
			if(btnOverlayKernelDensity.getSelection())dispCurve = "TRUE";
			else dispCurve = "FALSE";			
			String dataFileName = file.getAbsolutePath().replaceAll("\\\\", "/");

			File outputFolder = GraphsUtilities.createOutputFolder(file.getName(), analysisType);
			if(!outputFolder.exists()) outputFolder.mkdir();

			OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Creating Histogram");
			rInfo.open();
					
//			public void createGraphHist(String outputPath, String dataFileName, 
//					String[] nVar, String mTitle, String[] yAxisLabs, String[] xAxisLabs, 
//					String[] yMinValues, String[] yMaxValues, String[] xMinValues, String[] xMaxValues,
//					String useFreq, String numBins, int axisLabelStyle, String byVar, String barColor, 
//					String dispCurve, int lineType, String lineColor, double lineWidth, String boxed,
//					String multGraphs, int numRowsGraphs, int numColsGraphs, String orientGraphs) {
			
			
			
			ProjectExplorerView.rJavaManager.getRJavaGraphManager().createGraphHist(
					outputFolder.getAbsolutePath().replaceAll("\\\\", "/")+"/", 
					dataFileName,
					nVar, 
					mTitle,  
					yAxisLabs, 
					xAxisLabs,
					yMinValues,
					yMaxValues,
					xMinValues, 
					xMaxValues, 
					useFreq, 
					numBins,
					axisLabelStyle,
					byVar, 
			        barColor,
			        dispCurve, 
			        lineType, 
			        lineColor, 
			        lineWidth, 
			        boxed,
			        multGraphs, 
			        numRowsGraphs, 
			        numColsGraphs,
			        orientGraphs);
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
		return new Point(721, 510);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}