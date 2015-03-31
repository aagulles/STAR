package org.irri.breedingtool.pbtools.analysis.generationmean.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.SpecifyRowColDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.PBToolAnalysisUtilities;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;

public class SummaryStatisticsDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private FileDialog fd;

	//specify parameters
	private String generationMean = "NULL"; 
	private String weights = "NULL"; 
	private String generationStandardDeviation = "NULL"; 
	private String numberObservations = "NULL"; 
	private String generation = "NULL";

	private Text levelOfSignificance;
	private Text fGenText;
	private Text bc1GenText;
	private Text bc2GenText;
	private List varList;
	private String[] allVariables;
	private Button btnWithComputedWeights;
	private Label lblWeights;
	private Button addWeightBtn;
	private List weightVarList;
	private Button addMeanBtn;
	private Button addStdDevBtn;
	private Button addNumOfObsBtn;
	private Button addGenBtn;
	private Label lblGen;
	private Label lblNumberOfObservations;
	private Label lblStandardDeviation;
	private Label lblMean;
	private List meanVarList;
	private List stdDevVarList;
	private List numOfObsVarList;
	private List genVarList;
	private Group grpRecordingGenerationLevels;
	private List generationLevels;
	private Button btnP1;
	private Button btnP2;
	private Button btnF;
	private Button btnBc1;
	private Button btnBc2;
	private List notationList;
	private Button removeNotationBtn;
	protected String[] genotypeLevels;
	private ArrayList<String> userNotationList = new ArrayList<String>();
	private ArrayList<String> convertedNotationList = new ArrayList<String>();
	private Button btnNoComputedWeights;
	private String analysisType;
	private Button addBtn;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SummaryStatisticsDialog(Shell parentShell, String analysisType, File file) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		setBlockOnOpen(false);
		this.analysisType = analysisType;
		this.file=file;
		setFactors();
		allVariables = dataManipulationManager.getListItems(varInfo);
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

		String[] filterExt = {"*.csv"};
		fd.setFilterExtensions(filterExt);

		parent.getShell().setText("Generation Mean Analysis(Summary Statistics): "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 5;

		Group grpStatisticalWieghts = new Group(container, SWT.NONE);
		grpStatisticalWieghts.setLayout(new GridLayout(2, false));
		grpStatisticalWieghts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 5, 1));
		grpStatisticalWieghts.setText("Statistical Weights");

		btnNoComputedWeights = new Button(grpStatisticalWieghts, SWT.RADIO);
		btnNoComputedWeights.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				//disable weights variable
				lblWeights.setEnabled(false);
				weightVarList.setEnabled(false);
				weights = "NULL"; 
				if(weightVarList.getItemCount()>0){
					varList.add(weightVarList.getItems()[0]);
					weightVarList.removeAll();
				}
				addWeightBtn.setEnabled(false);

				//enable Standard Deviation variable
				lblStandardDeviation.setEnabled(true);
				stdDevVarList.setEnabled(true);
				addStdDevBtn.setEnabled(true);

				//enable Number of Observations variable
				lblNumberOfObservations.setEnabled(true);
				numOfObsVarList.setEnabled(true);
				addNumOfObsBtn.setEnabled(true);
			}
		});
		btnNoComputedWeights.setSelection(true);
		btnNoComputedWeights.setText("No computed Weights");

		btnWithComputedWeights = new Button(grpStatisticalWieghts, SWT.RADIO);
		btnWithComputedWeights.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//enable weights variable
				lblWeights.setEnabled(true);
				weightVarList.setEnabled(true);

				//disable Standard Deviation variable
				lblStandardDeviation.setEnabled(false);
				stdDevVarList.setEnabled(false);
				generationStandardDeviation = "NULL";
				if(stdDevVarList.getItemCount()>0){
					varList.add(stdDevVarList.getItems()[0]);
					stdDevVarList.removeAll();
				}
				addStdDevBtn.setEnabled(false);
				//disable Number of Observations variable
				lblNumberOfObservations.setEnabled(false);
				numOfObsVarList.setEnabled(false);
				addNumOfObsBtn.setEnabled(false);
				numberObservations = "NULL";
				if(numOfObsVarList.getItemCount()>0){
					varList.add(numOfObsVarList.getItems()[0]);
					numOfObsVarList.removeAll();
				}
			}
		});
		btnWithComputedWeights.setText("With Computed Weights");

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNewLabel.setText("Variables:");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		varList = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_varList = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 5);
		gd_varList.heightHint = 153;
		varList.setLayoutData(gd_varList);
		varList.setItems(allVariables);
		varList.addListener(SWT.MouseDown, new Listener(){
			@Override
			public void handleEvent(Event event) {
				enableAddVariableButtons(false);
				if(varList.getSelectionIndex()>-1){
					enableAddVariableButtons(true);
				}
			}
		});

		addMeanBtn = new Button(container, SWT.NONE);
		addMeanBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if(varList.getSelectionCount()>0) {//if the purpose is to add
					String selectedStrings[] = varList.getSelection();
					dataManipulationManager.moveSelectedStringFromTo( varList, meanVarList);
					generationMean = selectedStrings[0];
					addMeanBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					generationMean = "NULL"; 
					dataManipulationManager.moveSelectedStringFromTo( meanVarList, varList);
				}
				enableAddVariableButtons(false);
			}
		});
		GridData gd_addMeanBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addMeanBtn.widthHint = 52;
		addMeanBtn.setLayoutData(gd_addMeanBtn);
		addMeanBtn.setText("Add");

		lblMean = new Label(container, SWT.NONE);
		lblMean.setText("Mean:");

		meanVarList = new List(container, SWT.BORDER);
		GridData gd_meanVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_meanVarList.heightHint = 22;
		meanVarList.setLayoutData(gd_meanVarList);
		meanVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				generationMean = "NULL"; 
				dataManipulationManager.moveSelectedStringFromTo( meanVarList, varList);
				addMeanBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(meanVarList.getSelectionCount()>0){
					varList.setSelection(-1);
					addMeanBtn.setText("Remove");
					addMeanBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addWeightBtn = new Button(container, SWT.NONE);
		addWeightBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(varList.getSelectionCount()>0) {//if the purpose is to add
					String selectedStrings[] = varList.getSelection();
					dataManipulationManager.moveSelectedStringFromTo( varList, weightVarList);
					weights = selectedStrings[0]; 
					addWeightBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					weights = "NULL"; 
					dataManipulationManager.moveSelectedStringFromTo( weightVarList, varList);
				}
				enableAddVariableButtons(false);
			}
		});
		addWeightBtn.setEnabled(false);
		GridData gd_addWeightBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addWeightBtn.widthHint = 52;
		addWeightBtn.setLayoutData(gd_addWeightBtn);
		addWeightBtn.setText("Add");

		lblWeights = new Label(container, SWT.NONE);
		lblWeights.setEnabled(false);
		lblWeights.setText("Weights:");

		weightVarList = new List(container, SWT.BORDER);
		weightVarList.setEnabled(false);
		GridData gd_weightVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_weightVarList.heightHint = 22;
		weightVarList.setLayoutData(gd_weightVarList);
		weightVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				weights = "NULL"; 
				dataManipulationManager.moveSelectedStringFromTo( weightVarList, varList);
				addWeightBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(weightVarList.getSelectionCount()>0){
					varList.setSelection(-1);
					addWeightBtn.setText("Remove");
					addWeightBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addStdDevBtn = new Button(container, SWT.NONE);
		addStdDevBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(varList.getSelectionCount()>0) {//if the purpose is to add
					String selectedStrings[] = varList.getSelection();
					dataManipulationManager.moveSelectedStringFromTo( varList, stdDevVarList);
					generationStandardDeviation = selectedStrings[0]; 
					addStdDevBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					generationStandardDeviation = "NULL"; 
					dataManipulationManager.moveSelectedStringFromTo( stdDevVarList, varList);
				}
				enableAddVariableButtons(false);
			}
		});

		GridData gd_addStdDevBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addStdDevBtn.widthHint = 52;
		addStdDevBtn.setLayoutData(gd_addStdDevBtn);
		addStdDevBtn.setText("Add");

		lblStandardDeviation = new Label(container, SWT.NONE);
		lblStandardDeviation.setText("Standard Deviation:");

		stdDevVarList = new List(container, SWT.BORDER);
		GridData gd_stdDevVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_stdDevVarList.heightHint = 22;
		stdDevVarList.setLayoutData(gd_stdDevVarList);
		stdDevVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				generationStandardDeviation = "NULL"; 
				dataManipulationManager.moveSelectedStringFromTo( stdDevVarList, varList);
				addStdDevBtn.setEnabled(false);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(stdDevVarList.getSelectionCount()>0){
					varList.setSelection(-1);
					addStdDevBtn.setText("Remove");
					addStdDevBtn.setEnabled(true);
				}
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});


		addNumOfObsBtn = new Button(container, SWT.NONE);
		addNumOfObsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(varList.getSelectionCount()>0) {//if the purpose is to add
					String selectedStrings[] = varList.getSelection();
					dataManipulationManager.moveSelectedStringFromTo( varList, numOfObsVarList);
					numberObservations = selectedStrings[0];
					addNumOfObsBtn.setEnabled(false);
				}
				else{//if the purpose is to remove
					numberObservations = "NULL";
					dataManipulationManager.moveSelectedStringFromTo( numOfObsVarList, varList);
				}
				enableAddVariableButtons(false);
			}
		});
		GridData gd_addNumOfObsBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addNumOfObsBtn.widthHint = 52;
		addNumOfObsBtn.setLayoutData(gd_addNumOfObsBtn);
		addNumOfObsBtn.setText("Add");

		lblNumberOfObservations = new Label(container, SWT.NONE);
		lblNumberOfObservations.setText("Number of Observations:");

		numOfObsVarList = new List(container, SWT.BORDER);
		GridData gd_numOfObsVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_numOfObsVarList.heightHint = 22;
		numOfObsVarList.setLayoutData(gd_numOfObsVarList);
		numOfObsVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				numberObservations = "NULL";
				dataManipulationManager.moveSelectedStringFromTo( numOfObsVarList, varList);
				addNumOfObsBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(numOfObsVarList.getSelectionCount()>0){
					varList.setSelection(-1);
					addNumOfObsBtn.setText("Remove");
					addNumOfObsBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addGenBtn = new Button(container, SWT.NONE);
		addGenBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(varList.getSelectionCount()>0) {//if the purpose is to add
					String selectedStrings[] = varList.getSelection();
					genotypeLevels = DataManipulationManager.getEnvtLevels(selectedStrings[0], file);
					dataManipulationManager.moveSelectedStringFromTo( varList, genVarList);
					generation = selectedStrings[0];
					addGenBtn.setEnabled(false);
					grpRecordingGenerationLevels.setEnabled(true);
					generationLevels.setItems(genotypeLevels);
				}
				else{//if the purpose is to remove
					generation = "NULL";
					dataManipulationManager.moveSelectedStringFromTo( genVarList, varList);
					grpRecordingGenerationLevels.setEnabled(false);
					generationLevels.removeAll();
					notationList.removeAll();
				}
				enableAddVariableButtons(false);
			}
		});

		GridData gd_addGenBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addGenBtn.widthHint = 52;
		addGenBtn.setLayoutData(gd_addGenBtn);
		addGenBtn.setText("Add");

		lblGen = new Label(container, SWT.NONE);
		lblGen.setText("Generation:");

		genVarList = new List(container, SWT.BORDER);
		GridData gd_genVarList = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_genVarList.heightHint = 22;
		genVarList.setLayoutData(gd_genVarList);
		genVarList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				generation = "NULL";
				dataManipulationManager.moveSelectedStringFromTo( genVarList, varList);
				grpRecordingGenerationLevels.setEnabled(false);
				generationLevels.removeAll();
				notationList.removeAll();
				addGenBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(genVarList.getSelectionCount()>0){
					varList.setSelection(-1);
					addGenBtn.setText("Remove");
					addGenBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		grpRecordingGenerationLevels = new Group(container, SWT.NONE);
		grpRecordingGenerationLevels.setEnabled(false);
		grpRecordingGenerationLevels.setText("Recording Generation Levels:");
		grpRecordingGenerationLevels.setLayout(new GridLayout(4, false));
		grpRecordingGenerationLevels.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));

		Label lblNewLabel_2 = new Label(grpRecordingGenerationLevels, SWT.NONE);
		lblNewLabel_2.setText("Generation Levels:");

		Label lblNewLabel_3 = new Label(grpRecordingGenerationLevels, SWT.WRAP);
		GridData gd_lblNewLabel_3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_3.widthHint = 159;
		gd_lblNewLabel_3.heightHint = 16;
		lblNewLabel_3.setLayoutData(gd_lblNewLabel_3);
		lblNewLabel_3.setText("Convert to General Notation of:");
		new Label(grpRecordingGenerationLevels, SWT.NONE);

		Label lblNotationList = new Label(grpRecordingGenerationLevels, SWT.NONE);
		GridData gd_lblNotationList = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNotationList.widthHint = 73;
		lblNotationList.setLayoutData(gd_lblNotationList);
		lblNotationList.setText("Notation List:       ");

		generationLevels = new List(grpRecordingGenerationLevels, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_generationLevels = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_generationLevels.widthHint = 88;
		generationLevels.setLayoutData(gd_generationLevels);
		generationLevels.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notationList.setSelection(-1); // unselect everything from notation List
				removeNotationBtn.setEnabled(false); // disable remove button
				addBtn.setEnabled(true);
			}
		});
		Group group = new Group(grpRecordingGenerationLevels, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));
		group.setLayout(new GridLayout(4, false));

		btnP1 = new Button(group, SWT.RADIO);
		btnP1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnP1.setSelection(true);
		btnP1.setText("P1");
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);

		btnP2 = new Button(group, SWT.RADIO);
		btnP2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnP2.setText("P2");
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);

		btnF = new Button(group, SWT.RADIO);
		btnF.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnF.setText("F");

		Label lblGeneration = new Label(group, SWT.NONE);
		lblGeneration.setEnabled(true);
		lblGeneration.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblGeneration.setText("Generation:");

		fGenText = new Text(group, SWT.BORDER);
		fGenText.setEnabled(false);
		GridData gd_fGenText = new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1);
		gd_fGenText.widthHint = 15;
		fGenText.setLayoutData(gd_fGenText);

		btnBc1 = new Button(group, SWT.RADIO);
		btnBc1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnBc1.setText("BC1");

		Label label = new Label(group, SWT.NONE);
		label.setEnabled(true);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label.setText("Generation:");

		bc1GenText = new Text(group, SWT.BORDER);
		bc1GenText.setEnabled(false);
		bc1GenText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));

		btnBc2 = new Button(group, SWT.RADIO);
		btnBc2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnBc2.setText("BC2");

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setEnabled(true);
		label_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label_1.setText("Generation:");

		bc2GenText = new Text(group, SWT.BORDER);
		bc2GenText.setEnabled(false);
		bc2GenText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		new Label(grpRecordingGenerationLevels, SWT.NONE);

		notationList = new List(grpRecordingGenerationLevels, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_notationList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_notationList.widthHint = 70;
		notationList.setLayoutData(gd_notationList);
		notationList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				if(notationList.getSelectionCount()>0){
					generationLevels.add(userNotationList.get(notationList.getSelectionIndex()));
					userNotationList.remove(userNotationList.get(notationList.getSelectionIndex()));
					convertedNotationList.remove(notationList.getSelectionIndex());
					notationList.remove(notationList.getSelectionIndex());
					removeNotationBtn.setEnabled(false);
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(notationList.getSelectionCount()>0){
					generationLevels.setSelection(-1); // unselect everything from generationLevels
					addBtn.setEnabled(false);
					removeNotationBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addBtn = new Button(grpRecordingGenerationLevels, SWT.NONE);
		addBtn.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				String genLevels[] = generationLevels.getSelection();
				if(genLevels.length <1) MessageDialog.openError(getShell(), "Error", "Please select a variable from the generation levels!");
				else if(btnF.getSelection() && fGenText.getText().isEmpty()) MessageDialog.openError(getShell(), "Error", "Please add a generation value for the F general notation!");
				else if(btnBc1.getSelection() && bc1GenText.getText().isEmpty()) MessageDialog.openError(getShell(), "Error", "Please add a generation value for the BC1 general notation!");
				else if(btnBc2.getSelection() && bc2GenText.getText().isEmpty()) MessageDialog.openError(getShell(), "Error", "Please add a generation value for the BC2 general notation!");
				else{	
					try{
						if(btnF.getSelection()){
							if( Double.parseDouble(fGenText.getText())<1){//check level if 0<levelOfSignificance<1
								MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the generation value of F should be should be atleast 1.");
							}
						}
						else if(btnBc1.getSelection() ){
							if( Double.parseDouble(bc1GenText.getText())<1){//check level if 0<levelOfSignificance<1
								MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the generation value of BC1 should be should be atleast 1.");
							}
						}
						else if(btnBc2.getSelection()){
							if( Double.parseDouble(bc2GenText.getText())<1){//check level if 0<levelOfSignificance<1
								MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the generation value of BC2 should be should be atleast 1.");
							}
						}
						
						String convertedNotation = getConvertedGeneralNotationOf();
						userNotationList.add(genLevels[0]);
						convertedNotationList.add(convertedNotation);
						notationList.add(genLevels[0]+"->"+convertedNotation);
						generationLevels.remove(generationLevels.getSelectionIndex());
						
					}catch(NumberFormatException nfe){//if not a number
						MessageDialog.openWarning(getShell(), "Invalid Format", "Sorry, the generation value should be between 0 and 1.");
					}
				}
			}
		});
		GridData gd_addBtn = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_addBtn.widthHint = 52;
		addBtn.setLayoutData(gd_addBtn);
		addBtn.setText("Add");

		removeNotationBtn = new Button(grpRecordingGenerationLevels, SWT.NONE);
		removeNotationBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(notationList.getSelectionCount()>0){
					generationLevels.add(userNotationList.get(notationList.getSelectionIndex()));
					userNotationList.remove(userNotationList.get(notationList.getSelectionIndex()));
					convertedNotationList.remove(notationList.getSelectionIndex());
					notationList.remove(notationList.getSelectionIndex());
					removeNotationBtn.setEnabled(false);
				}
			}
		});
		removeNotationBtn.setEnabled(false);
		GridData gd_removeNotationBtn = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_removeNotationBtn.widthHint = 78;
		removeNotationBtn.setLayoutData(gd_removeNotationBtn);
		removeNotationBtn.setText("Remove");
		new Label(grpRecordingGenerationLevels, SWT.NONE);

		Label lblLevelOfSignificance = new Label(container, SWT.NONE);
		lblLevelOfSignificance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLevelOfSignificance.setText("Level of Significance:");

		levelOfSignificance = new Text(container, SWT.BORDER);
		levelOfSignificance.setText("0.05");
		GridData gd_levelOfSignificance = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_levelOfSignificance.widthHint = 50;
		levelOfSignificance.setLayoutData(gd_levelOfSignificance);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		return container;

	}

	protected String getConvertedGeneralNotationOf() {
		// TODO Auto-generated method stub
		if(btnP1.getSelection()) return "B0F0P1";
		else if(btnP2.getSelection()) return "B0F0P2"; 
		else if(btnF.getSelection()) return "B0F"+fGenText.getText()+"P0";
		else if(btnBc1.getSelection()) return "B"+bc1GenText.getText()+"F1P1";
		else  return "B"+bc2GenText.getText()+"F1P2";
	}

	protected void activateGeneralNotationText() {
		// TODO Auto-generated method stub
		fGenText.setEnabled(false);
		bc1GenText.setEnabled(false);
		bc2GenText.setEnabled(false);

		if(btnF.getSelection()) fGenText.setEnabled(true);
		else if(btnBc1.getSelection()) bc1GenText.setEnabled(true);
		else if(btnBc2.getSelection()) bc2GenText.setEnabled(true);
	}

	protected void enableAddVariableButtons(boolean state) {
		// TODO Auto-generated method stub
		//un-select factor fields
		meanVarList.setSelection(-1);
		stdDevVarList.setSelection(-1);
		genVarList.setSelection(-1);
		numOfObsVarList.setSelection(-1);
		weightVarList.setSelection(-1);

		if(state){//if enable all buttons
			//change button text
			addMeanBtn.setText("Add");
			addNumOfObsBtn.setText("Add");
			addGenBtn.setText("Add");
			addWeightBtn.setText("Add");
			addStdDevBtn.setText("Add");

			//enable empty fields
			if(weightVarList.getItemCount()<1 && lblWeights.getEnabled()) addWeightBtn.setEnabled(state);
			if(numOfObsVarList.getItemCount()<1 && lblNumberOfObservations.getEnabled()) addNumOfObsBtn.setEnabled(state);
			if(genVarList.getItemCount()<1) addGenBtn.setEnabled(state);
			if(stdDevVarList.getItemCount()<1 && lblStandardDeviation.getEnabled()) addStdDevBtn.setEnabled(state);
			if(meanVarList.getItemCount()<1 && lblMean.getEnabled()) addMeanBtn.setEnabled(state);
		}
		else{
			//disable all buttons
			addMeanBtn.setEnabled(state);
			addNumOfObsBtn.setEnabled(state);
			addGenBtn.setEnabled(state);
			addWeightBtn.setEnabled(state);
			addStdDevBtn.setEnabled(state);
		}
	}

	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
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
		if(meanVarList.getItemCount()<1){
			MessageDialog.openError(getShell(), "Error", "Please enter a mean variable.");
		}
		else if(weightVarList.getItemCount()<1 && btnWithComputedWeights.getSelection() ){
			MessageDialog.openError(getShell(), "Error", "Please enter a weight variable.");
		}
		else if(stdDevVarList.getItemCount()<1 && btnNoComputedWeights.getSelection() ){
			MessageDialog.openError(getShell(), "Error", "Please enter a standard deviation variable.");
		}
		else if(numOfObsVarList.getItemCount()<1 && btnNoComputedWeights.getSelection() ){
			MessageDialog.openError(getShell(), "Error", "Please enter a number of observations variable.");
		}
		else if(genVarList.getItemCount()<1 ){
			MessageDialog.openError(getShell(), "Error", "Please enter a generation variable.");
		}
		else if(userNotationList.size()<1){
			MessageDialog.openError(getShell(), "Error", "Please specify variables on the notation list.");
		}
		else{
			//specify parameters
			try{
				if( Double.parseDouble(levelOfSignificance.getText())<=0 || Double.parseDouble(levelOfSignificance.getText())>=1){//check level if 0<levelOfSignificance<1
					MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the level of signifcance should have a value of between 0 and 1.");
				}else if(convertedNotationList.isEmpty()){
					MessageDialog.openWarning(getShell(), "Empty Field", "Please add variables to the Notation List.");
				}else{
					String[] usersNotation = userNotationList.toArray(new String[userNotationList.size()]); 
					String[] generalNotation= convertedNotationList.toArray(new String[convertedNotationList.size()]); 
					String alpha = levelOfSignificance.getText();			
					String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");

					File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);

					String newOutputFileName = outputFolder.getAbsolutePath().replaceAll("\\\\+", "/")+"/output.txt";
					OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Generation Mean Analysis (Summary as input)");
					rInfo.open();
					ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doGenerationMeanSummaryStats(dataFileName, newOutputFileName, generationMean, weights, generationStandardDeviation, numberObservations, generation, usersNotation, generalNotation, alpha);
					rInfo.close();
					WindowDialogControlUtil.hideAllWindowDialog();

					PBToolAnalysisUtilities.openFolder(outputFolder);
				}
			}catch(NumberFormatException nfe){//if not a number
				MessageDialog.openWarning(getShell(), "Invalid Format", "The Level of Signifcance should have a numeric value of between 0 and 1.");
			}
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(774, 687);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}