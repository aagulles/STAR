package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.DialogListValidator;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class ClusterAnalysisDialog extends Dialog {
	private Button btnOkay;
	private DialogFormUtility listManager = new DialogFormUtility();
	private String filePath = PartStackHandler.getActiveElementID();
	private Label lblNumericVariables;
	private Button btnSetToFactor;
	private TabItem tbtmVariableDescription;
	private Label lblFactors;
	private List lstFactors;
	private ListViewer listViewer_1;
	private List lstSelectedVariables;
	private ListViewer listViewer_2;
	private Button btnAddNumeric;
	private Label lblIdVariable;
	private Label lblSelectedVariables;
	private Text txtIDVariable;
	private Button btnAddIdVar;
	private TabItem tbtmOptions_1;
	private Composite composite_1;
	private Label lblDistanceMeasures;
	private Combo cmboDistanceMeasures;
	private Group grpClusteringMethod;
	private Button btnAgglomerative;
	private Combo cmboClusterMethod;
	private Button btnDivisive;
	private Button btnKmeans;
	private Group grpDisplay;
	private Button btnDistanceMatrix;
	private Button btnSummaryStatistics;
	private Button btnHighlightClusters;
	private Button btnSaveClusterMembership;
	private Spinner txtNumCluster;
	private List lstNumericVariables;
	private Label lblNewLabel;
	private Label lblBinaryVariable;
	private Button btnAddBinVar;
	private List lstFactorVars;
	private ListViewer listViewer_3;
	private Label lblSelectedFactors;
	private Button btnAddFactorVar;
	private List lstBinVars;
	private ListViewer listViewer_4;
	private Label lblNewLabel_1;  
	private List lstAbinVar;
	private ListViewer listViewer_5;
	private List lstOfactor;
	private ListViewer listViewer_6;
	private Label lblAsymmetricBinaryVariables;
	private Label lblOrderedFactors;
	private Button btnAddAbinVar;
	private Button btnAddOfactor;
	private Group grpSaveToA;
	private String[] ClusterMethods = {"Single", "Complete", "Average", "Ward", "Centroid"};
	private Button btnCopheneticDistances;
	private Button btnSummaryStatisticsRaw;
	private Button btnDataStandardization;
	private Button btnCorrelationMatrix;
	private Button btnScatterplotMatrix;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ClusterAnalysisDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Cluster Analysis : " + new File(filePath).getName());
	}

	/**
	 * Create contents of the dialog.
	 * 
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
		tabFolder.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		tbtmVariableDescription = new TabItem(tabFolder, SWT.NONE);
		tbtmVariableDescription.setText("Variable Description");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmVariableDescription.setControl(composite);
		GridLayout gl_composite = new GridLayout(3, false);
		gl_composite.marginHeight = 11;
		gl_composite.marginWidth = 9;
		composite.setLayout(gl_composite);

		lblNumericVariables = new Label(composite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				true, false, 1, 1));
		lblNumericVariables.setText("Numeric Variable(s):");
		lblNumericVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite, SWT.NONE);

		lblSelectedVariables = new Label(composite, SWT.NONE);
		lblSelectedVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				true, false, 1, 1));
		lblSelectedVariables.setText("Variable(s):");
		lblSelectedVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		ListViewer listViewer = new ListViewer(composite, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstNumericVariables = listViewer.getList();
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL,
				true, false, 1, 1);
		gd_lstNumericVariables.widthHint = 140;
		gd_lstNumericVariables.heightHint = 100;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);
		lstNumericVariables.setItems(new String[] {});

		btnAddNumeric = new Button(composite, SWT.NONE);
		GridData gd_btnAddNumeric = new GridData(SWT.CENTER, SWT.CENTER, true,
				false, 1, 1);
		gd_btnAddNumeric.widthHint = 90;
		btnAddNumeric.setLayoutData(gd_btnAddNumeric);
		btnAddNumeric.setText("Add");

		listViewer_2 = new ListViewer(composite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI);
		lstSelectedVariables = listViewer_2.getList();
		GridData gd_lstSelectedVariables = new GridData(SWT.FILL, SWT.FILL,
				true, false, 1, 1);
		gd_lstSelectedVariables.heightHint = 100;
		gd_lstSelectedVariables.widthHint = 140;
		lstSelectedVariables.setLayoutData(gd_lstSelectedVariables);
		lstSelectedVariables.setItems(new String[] {});

		btnSetToFactor = new Button(composite, SWT.NONE);
		GridData gd_btnSetToFactor = new GridData(SWT.RIGHT, SWT.CENTER, true,
				false, 1, 1);
		gd_btnSetToFactor.widthHint = 110;
		btnSetToFactor.setLayoutData(gd_btnSetToFactor);
		btnSetToFactor.setText("Set to Factor");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblFactors = new Label(composite, SWT.NONE);
		lblFactors.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1));
		lblFactors.setText("Factor(s):");
		lblFactors
				.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite, SWT.NONE);

		lblIdVariable = new Label(composite, SWT.NONE);
		lblIdVariable.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1));
		lblIdVariable.setText("ID Variable:");
		lblIdVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		listViewer_1 = new ListViewer(composite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI);
		lstFactors = listViewer_1.getList();
		GridData gd_lstFactors = new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 9);
		gd_lstFactors.widthHint = 140;
		gd_lstFactors.heightHint = 74;
		lstFactors.setLayoutData(gd_lstFactors);
		lstFactors.setItems(new String[] {});

		btnAddIdVar = new Button(composite, SWT.NONE);
		GridData gd_btnAddIdVar = new GridData(SWT.CENTER, SWT.TOP, true,
				false, 1, 1);
		gd_btnAddIdVar.widthHint = 90;
		btnAddIdVar.setLayoutData(gd_btnAddIdVar);
		btnAddIdVar.setText("Add");

		txtIDVariable = new Text(composite, SWT.BORDER);
		txtIDVariable.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		txtIDVariable.setEditable(false);
		GridData gd_txtIDVariable = new GridData(SWT.FILL, SWT.TOP, true,
				false, 1, 1);
		gd_txtIDVariable.widthHint = 140;
		txtIDVariable.setLayoutData(gd_txtIDVariable);
		new Label(composite, SWT.NONE);

		lblBinaryVariable = new Label(composite, SWT.NONE);
		lblBinaryVariable.setText("Symmetric Binary Variable(s):");
		lblBinaryVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		btnAddBinVar = new Button(composite, SWT.NONE);
		GridData gd_btnAddBinVar = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_btnAddBinVar.widthHint = 90;
		btnAddBinVar.setLayoutData(gd_btnAddBinVar);
		btnAddBinVar.setText("Add");

		listViewer_4 = new ListViewer(composite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI);
		lstBinVars = listViewer_4.getList();
		GridData gd_lstBinVars = new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1);
		gd_lstBinVars.heightHint = 20;
		lstBinVars.setLayoutData(gd_lstBinVars);
		lstBinVars.setItems(new String[] {});
		new Label(composite, SWT.NONE);
		
		lblAsymmetricBinaryVariables = new Label(composite, SWT.NONE);
		lblAsymmetricBinaryVariables.setText("Asymmetric Binary Variable(s):");
		lblAsymmetricBinaryVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		btnAddAbinVar = new Button(composite, SWT.NONE);
		GridData gd_btnAddAbinVar = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnAddAbinVar.widthHint = 90;
		btnAddAbinVar.setLayoutData(gd_btnAddAbinVar);
		btnAddAbinVar.setText("Add");
		
		listViewer_5 = new ListViewer(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstAbinVar = listViewer_5.getList();
		GridData gd_lstAbinVar = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lstAbinVar.heightHint = 20;
		lstAbinVar.setLayoutData(gd_lstAbinVar);
		lstAbinVar.setItems(new String[] {});
		new Label(composite, SWT.NONE);
		
		lblOrderedFactors = new Label(composite, SWT.NONE);
		lblOrderedFactors.setText("Ordered Factor(s):");
		lblOrderedFactors.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		btnAddOfactor = new Button(composite, SWT.NONE);
		GridData gd_btnAddOfactor = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnAddOfactor.widthHint = 90;
		btnAddOfactor.setLayoutData(gd_btnAddOfactor);
		btnAddOfactor.setText("Add");
		
		listViewer_6 = new ListViewer(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		lstOfactor = listViewer_6.getList();
		GridData gd_lstOfactor = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lstOfactor.heightHint = 20;
		lstOfactor.setLayoutData(gd_lstOfactor);
		lstOfactor.setItems(new String[] {});
		new Label(composite, SWT.NONE);

		lblSelectedFactors = new Label(composite, SWT.NONE);
		lblSelectedFactors.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblSelectedFactors.setText("Nominal Factor(s):");

		btnAddFactorVar = new Button(composite, SWT.NONE);
		GridData gd_btnAddFactorVar = new GridData(SWT.CENTER, SWT.CENTER,
				false, true, 1, 1);
		gd_btnAddFactorVar.widthHint = 90;
		btnAddFactorVar.setLayoutData(gd_btnAddFactorVar);
		btnAddFactorVar.setText("Add");

		listViewer_3 = new ListViewer(composite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI);
		lstFactorVars = listViewer_3.getList();
		GridData gd_lstFactorVars = new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 1);
		gd_lstFactorVars.heightHint = 30;
		lstFactorVars.setLayoutData(gd_lstFactorVars);
		lstFactorVars.setItems(new String[] {});

		tbtmOptions_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions_1.setText("Options");

		composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions_1.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));

		lblNewLabel_1 = new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label lblNumberOfClusters_1 = new Label(composite_1, SWT.NONE);
		lblNumberOfClusters_1.setText("Number of Clusters:");

		txtNumCluster = new Spinner(composite_1, SWT.BORDER);
		txtNumCluster.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1));
		txtNumCluster.setMinimum(2);

		lblDistanceMeasures = new Label(composite_1, SWT.NONE);
		lblDistanceMeasures.setText("Distance Measure:");

		cmboDistanceMeasures = new Combo(composite_1, SWT.READ_ONLY);
		GridData gd_cmboDistanceMeasures = new GridData(SWT.LEFT, SWT.CENTER,
				true, false, 1, 1);
		gd_cmboDistanceMeasures.widthHint = 120;
		cmboDistanceMeasures.setLayoutData(gd_cmboDistanceMeasures);
		cmboDistanceMeasures.setItems(new String[] {"Euclidean", "Maximum", "Manhattan", "Minkowski", "Canberra", "Simple Matching", "Sokal & Sneath", "Hamann coefficient", "Jaccard", "Dice", "Gower"});
		cmboDistanceMeasures.select(0);
		
		btnDataStandardization = new Button(composite_1, SWT.CHECK);
		btnDataStandardization.setText("Data Standardization");
		btnDataStandardization.setSelection(true);
		new Label(composite_1, SWT.NONE);

		lblNewLabel = new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		grpClusteringMethod = new Group(composite_1, SWT.NONE);
		grpClusteringMethod.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, false, 2, 1));
		grpClusteringMethod.setText("Clustering Method");
		grpClusteringMethod.setLayout(new GridLayout(1, false));

		btnAgglomerative = new Button(grpClusteringMethod, SWT.RADIO);
		btnAgglomerative.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cmboDistanceMeasures.setEnabled(true);
				cmboClusterMethod.setEnabled(true);
				btnDataStandardization.setEnabled(true);
				btnDistanceMatrix.setEnabled(true);
				btnCopheneticDistances.setEnabled(true);
				btnHighlightClusters.setEnabled(true);
				btnSaveClusterMembership.setEnabled(true);
				btnSummaryStatistics.setEnabled(true);
//				btnClusterMembership.setEnabled(true);
				btnScatterplotMatrix.setEnabled(true);
			}
		});
		btnAgglomerative.setText("Agglomerative");
		btnAgglomerative.setSelection(true);

		cmboClusterMethod = new Combo(grpClusteringMethod, SWT.READ_ONLY);
		cmboClusterMethod.setItems(new String[] { "Single Linkage",
				"Complete Linkage", "Average Linkage", "Ward's Method",
				"Centroid Method" });
		GridData gd_cmboClusterMethod = new GridData(SWT.LEFT, SWT.FILL, true,
				false, 1, 1);
		gd_cmboClusterMethod.widthHint = 120;
		cmboClusterMethod.setLayoutData(gd_cmboClusterMethod);
		cmboClusterMethod.select(1);

		btnDivisive = new Button(grpClusteringMethod, SWT.RADIO);
		btnDivisive.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cmboDistanceMeasures.setEnabled(true);
				cmboClusterMethod.setEnabled(false);
				btnDataStandardization.setEnabled(true);
				btnDistanceMatrix.setEnabled(true);
				btnCopheneticDistances.setEnabled(true);
				btnHighlightClusters.setEnabled(true);
				btnSaveClusterMembership.setEnabled(true);
				btnSummaryStatistics.setEnabled(true);
//				btnClusterMembership.setEnabled(true);
				btnScatterplotMatrix.setEnabled(true);
			}
		});
		btnDivisive.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false,
				1, 1));
		btnDivisive.setText("Divisive");

		btnKmeans = new Button(grpClusteringMethod, SWT.RADIO);
		btnKmeans.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cmboDistanceMeasures.setEnabled(false);
				cmboClusterMethod.setEnabled(false);
				btnDataStandardization.setEnabled(false);
				btnDistanceMatrix.setEnabled(false);
				btnCopheneticDistances.setEnabled(false);
				btnHighlightClusters.setEnabled(false);
				btnSaveClusterMembership.setEnabled(true);
				btnSummaryStatistics.setEnabled(true);
//				btnClusterMembership.setEnabled(true);
				btnCopheneticDistances.setEnabled(false);
				btnScatterplotMatrix.setEnabled(false);

			}
		});
		btnKmeans.setText("K - Means");

		grpDisplay = new Group(composite_1, SWT.NONE);
		grpDisplay.setText("Display");
		grpDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				2, 1));
		grpDisplay.setLayout(new GridLayout(1, false));
		
		btnSummaryStatisticsRaw = new Button(grpDisplay, SWT.CHECK);
		btnSummaryStatisticsRaw.setText("Descriptive Statistics for raw data");
		
		btnScatterplotMatrix = new Button(grpDisplay, SWT.CHECK);
		btnScatterplotMatrix.setText("Scatterplot Matrix");
		
		btnCorrelationMatrix = new Button(grpDisplay, SWT.CHECK);
		btnCorrelationMatrix.setText("Correlation Matrix");

		btnSummaryStatistics = new Button(grpDisplay, SWT.CHECK);
		btnSummaryStatistics.setText("Descriptive Statistics for each Cluster");
		
				btnHighlightClusters = new Button(grpDisplay, SWT.CHECK);
				btnHighlightClusters.setText("Highlight Clusters on Dendrogram");
		
		grpSaveToA = new Group(composite_1, SWT.NONE);
		grpSaveToA.setLayout(new GridLayout(1, false));
		GridData gd_grpSaveToA = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_grpSaveToA.widthHint = 551;
		grpSaveToA.setLayoutData(gd_grpSaveToA);
		grpSaveToA.setText("Save to a File");
		
				btnDistanceMatrix = new Button(grpSaveToA, SWT.CHECK);
				btnDistanceMatrix.setText("Distance Matrix");
						
						btnCopheneticDistances = new Button(grpSaveToA, SWT.CHECK);
						btnCopheneticDistances.setText("Cophenetic Distances");
				
						btnSaveClusterMembership = new Button(grpSaveToA, SWT.CHECK);
						btnSaveClusterMembership.setText("Cluster Membership");
						btnSaveClusterMembership.setSelection(true);
		initializeContent();
		return container;
	}

	void initializeContent() {
		listManager.initializeSingleButtonList(lstNumericVariables,
				lstSelectedVariables, btnAddNumeric,new ClusterListValidator());
		listManager.initializeSingleButtonList(lstFactors, lstBinVars,
				btnAddBinVar,new ClusterListValidator());
		listManager.initializeSingleButtonList(lstFactors, lstAbinVar,
				btnAddAbinVar,new ClusterListValidator());
		listManager.initializeSingleButtonList(lstFactors, lstOfactor,
				btnAddOfactor,new ClusterListValidator());
		listManager.initializeSingleButtonList(lstFactors, lstFactorVars,
				btnAddFactorVar,new ClusterListValidator());
		listManager.initializeSingleSelectionList(lstFactors, txtIDVariable,
				btnAddIdVar);
		listManager.initializeVariableMoveList(lstNumericVariables, lstFactors,
				btnSetToFactor, filePath);
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeFactorList(lstFactors, filePath);

	}

	void resetDialog() {
		lstNumericVariables.removeAll();
		lstSelectedVariables.removeAll();
		lstFactors.removeAll();
		lstAbinVar.removeAll();
		lstBinVars.removeAll();
		lstFactorVars.removeAll();
		lstOfactor.removeAll();
		txtIDVariable.setText("");
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeFactorList(lstFactors, filePath);
		btnAddNumeric.setText("Add");
		btnAddNumeric.setEnabled(false);
		btnAddIdVar.setText("Add");
		btnAddIdVar.setEnabled(false);
		btnAddBinVar.setText("Add");
		btnAddBinVar.setEnabled(false);
		btnAddAbinVar.setText("Add");
		btnAddAbinVar.setEnabled(false);
		btnAddOfactor.setEnabled(false);
		btnAddOfactor.setText("Add");
		btnAddFactorVar.setText("Add");
		btnAddFactorVar.setEnabled(false);
		btnAgglomerative.setSelection(true);
		btnDivisive.setSelection(false);
		btnKmeans.setSelection(false);
		btnKmeans.setEnabled(true);
		btnSetToFactor.setEnabled(false);
		btnSetToFactor.setText("Set to Factor");
		btnDataStandardization.setSelection(true);
		btnDistanceMatrix.setSelection(false);
		btnDistanceMatrix.setEnabled(true);
		btnCopheneticDistances.setSelection(false);
		btnCopheneticDistances.setEnabled(true);
		btnHighlightClusters.setSelection(false);
		btnSaveClusterMembership.setSelection(true);
		btnSummaryStatistics.setEnabled(true);
		btnSummaryStatistics.setSelection(false);
//		btnClusterMembership.setSelection(true);
		cmboDistanceMeasures.setItems(new String[] { "Euclidean", "Maximum",
				"Manhattan", "Minkowski", "Canberra", "Simple Matching",
				"Jaccard", "Dice", "Gower" });
		cmboDistanceMeasures.select(0);
		txtNumCluster.setSelection(2);
		cmboClusterMethod.setText("Complete Linkage");
		btnScatterplotMatrix.setSelection(false);
		btnScatterplotMatrix.setEnabled(true);
		btnSummaryStatisticsRaw.setSelection(false);
		btnCorrelationMatrix.setSelection(false);
		btnCorrelationMatrix.setEnabled(true);
	}

	@Override
	protected void buttonPressed(int ID) {
		if (ID == IDialogConstants.OK_ID) {
			okPressed();
		} else if (ID == IDialogConstants.CANCEL_ID) {
			cancelPressed();
		} else if (ID == IDialogConstants.RETRY_ID) {
			resetDialog();
		}
	}

	@Override
	protected void okPressed() {

		if(txtIDVariable.getText().equals("")){
			MessageDialog.openError(this.getShell(),"Error","Please add an ID Variable.");
			return;
		}else{	
				String[] idLevels = DataManipulationManager.getEnvtLevels(txtIDVariable.getText(), new File(filePath)); 
				try {
					if(idLevels.length != (countLines(filePath)-1)){
						System.out.println(idLevels.length + ":" + (countLines(filePath)-1));
						MessageDialog.openError(this.getShell(),"Error","ID Variable is not unique.");
						return;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		String outputFolder = StarAnalysisUtilities.createOutputFolder(
				filePath, "ClusterAnalysis") + File.separator;
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),
				"Performing Analysis");
		rInfo.open();
		// + "//Output.txt"
		btnOkay.setEnabled(false);
		String dataFileName = filePath.replace(File.separator, "/");
		String outputPath = outputFolder;
		String[] var = lstSelectedVariables.getItems();
		
		String idVar = (txtIDVariable.getText().isEmpty()) ? null : txtIDVariable.getText();
		String[] sbinVar = (lstBinVars.getItemCount() <= 0  )? null :lstBinVars.getItems();
		String[] abinVar = (lstAbinVar.getItemCount() <=0 )? null : lstAbinVar.getItems();
		String[] ofactorVar = (lstOfactor.getItemCount() <= 0  )? null :lstOfactor.getItems();
		String[] factorVar = (lstFactorVars.getItemCount() <= 0  )? null :lstFactorVars.getItems();
		
		boolean stand = btnDataStandardization.getSelection();
		String distance = cmboDistanceMeasures.getText();
		String clusmethod = ClusterMethods[cmboClusterMethod.getSelectionIndex()]; 
//		String clusmethod = cmboClusterMethod.getText();
		boolean distMatrix = btnDistanceMatrix.getSelection();
		boolean copDistance = btnCopheneticDistances.getSelection();
		boolean clusterMem = true; //btnSaveClusterMembership.getSelection();//btnClusterMembership.getSelection();
		boolean descriptiveStatRaw = btnSummaryStatisticsRaw.getSelection();
		boolean corMatx = btnCorrelationMatrix.getSelection();
		boolean scatterMatx = btnScatterplotMatrix.getSelection();
		boolean descriptiveStat = btnSummaryStatistics.getSelection();
		Integer clusterNum = txtNumCluster.getSelection();
		boolean dendrogram = true;
		boolean clusterBox = btnHighlightClusters.getSelection();
		boolean kgraph = true;
		boolean saveMem = btnSaveClusterMembership.getSelection();

		if (btnAgglomerative.getSelection()) {
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager()
					.doClusterAgglo(dataFileName,
							outputPath.replace(File.separator, "/"), var,
							idVar, sbinVar, abinVar, ofactorVar,factorVar, stand, distance,
							clusmethod, distMatrix, copDistance,clusterMem,
							descriptiveStatRaw, corMatx, scatterMatx,descriptiveStat, clusterNum, dendrogram,
							clusterBox, saveMem);
		} else if (btnDivisive.getSelection()) {
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager()
					.doClusterDivisive(dataFileName,
							outputPath.replace(File.separator, "/"), var,
							idVar, sbinVar, abinVar, ofactorVar, factorVar, stand, distance,
							distMatrix, copDistance,clusterMem,descriptiveStatRaw, corMatx, scatterMatx, descriptiveStat,
							clusterNum, dendrogram, clusterBox, saveMem);
		} else if (btnKmeans.getSelection()) {
			ProjectExplorerView.rJavaManager.getSTARAnalysisManager()
					.doClusterKmeans(dataFileName,
							outputPath.replace(File.separator, "/"), var,
							idVar, clusterMem,descriptiveStatRaw, corMatx, descriptiveStat, clusterNum,
							kgraph, saveMem);
		}
		StarAnalysisUtilities.testVarArgs(dataFileName,
				outputPath.replace(File.separator, "/"), var, idVar, sbinVar, abinVar, ofactorVar,
				factorVar, stand, distance, clusmethod, distMatrix, clusterMem,
				descriptiveStat, clusterNum, dendrogram, clusterBox, saveMem,
				kgraph);

		this.getShell().setMinimized(true);
		StarAnalysisUtilities.openAndRefreshFolder(outputPath);
		btnOkay.setEnabled(true);
		rInfo.close();

	}

	public class ClusterListValidator extends DialogListValidator {

		@Override
		public void postAddProcess() {
			updateComboBox();
		}

		@Override
		public void postRemoveProcess() {
			updateComboBox();
		}
		
		public void updateComboBox() {
			
			if (!listEmpty(lstSelectedVariables) && listEmpty(lstBinVars)
					&& listEmpty(lstFactorVars) && listEmpty(lstAbinVar) && listEmpty(lstOfactor)) {
				cmboDistanceMeasures.setItems(new String[] { "Euclidean",
						"Maximum", "Manhattan", "Minkowski", "Canberra" });
				cmboDistanceMeasures.select(0);
			} else if (listEmpty(lstSelectedVariables) &&!listEmpty(lstBinVars)
					&& listEmpty(lstFactorVars) && listEmpty(lstAbinVar) && listEmpty(lstOfactor)) {

				cmboDistanceMeasures.setItems(new String[] {
							"Simple Matching", "Sokal & Sneath", "Hamann coefficient" });
					cmboDistanceMeasures.select(0);
			 } else if (listEmpty(lstSelectedVariables) &&listEmpty(lstBinVars)
						&& listEmpty(lstFactorVars) && !listEmpty(lstAbinVar) && listEmpty(lstOfactor)){
				 cmboDistanceMeasures.setItems(new String[] {
							"Jaccard", "Dice"});
					cmboDistanceMeasures.select(0);
			 }
			
			
			else{
				System.out.println(lstBinVars.getItemCount());
				cmboDistanceMeasures.setItems(new String[] { "Gower" });
				cmboDistanceMeasures.select(0);
			}			 
//				btnSummaryStatistics.setEnabled(!(!listEmpty(lstBinVars)
//						|| !listEmpty(lstFactorVars) || !listEmpty(lstAbinVar) || !listEmpty(lstOfactor)));
//				btnSummaryStatistics.setSelection(!(!listEmpty(lstBinVars)
//						|| !listEmpty(lstFactorVars) || !listEmpty(lstAbinVar) || !listEmpty(lstOfactor)));
				btnDataStandardization.setEnabled(!listEmpty(lstSelectedVariables));
				btnDataStandardization.setSelection(!listEmpty(lstSelectedVariables));
				btnSummaryStatisticsRaw.setEnabled(!listEmpty(lstSelectedVariables));
				btnSummaryStatisticsRaw.setSelection(!listEmpty(lstSelectedVariables));
				btnCorrelationMatrix.setEnabled(!listEmpty(lstSelectedVariables));
				btnCorrelationMatrix.setSelection(!listEmpty(lstSelectedVariables));
				btnSummaryStatistics.setEnabled(!listEmpty(lstSelectedVariables));
				btnSummaryStatistics.setSelection(!listEmpty(lstSelectedVariables));
				btnKmeans.setEnabled(!(!listEmpty(lstBinVars)
						|| !listEmpty(lstFactorVars) || !listEmpty(lstAbinVar) || !listEmpty(lstOfactor)));
		}

		public boolean listEmpty(List list) {
			return (list.getItemCount() <= 0);
		}
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */

	public int countLines(String filename) throws IOException {
	    LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
	int cnt = 0;
	String lineRead = "";
	while ((lineRead = reader.readLine()) != null) {}

	cnt = reader.getLineNumber(); 
	reader.close();
	return cnt;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.RETRY_ID, "Reset", false);
		btnOkay = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(605, 637);
	}
}
