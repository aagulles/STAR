package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class CorrelationAnalysisDialog extends Dialog {

	private static final int RESET = IDialogConstants.CLIENT_ID + 1111;

	private String filePath = PartStackHandler.getActivePart().getElementId().toString(); 
	private Button btnOk;
	private Button btnPearson;
	private Button btnKendallsTaub;
	private Button btnSpearman;
	private Button btnLessThan;
	private Button btnGreaterThan;
	private Button btnNotEqual;
	private Button btnSummaryStatistics;
	private Button btnScatterplot;
	private DialogFormUtility listManager = new DialogFormUtility();
	private List lstNumericVariables;
	private Button btnAdd;
	private Group grpCorrelationCoefficients;
	private List lstTestVariables;
	

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public CorrelationAnalysisDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Correlation Analysis : " + new File(filePath).getName());
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.marginHeight = 8;
		gridLayout.marginWidth = 8;

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TabItem tbtmVariableDescription = new TabItem(tabFolder, SWT.NONE);
		tbtmVariableDescription.setText("Variable Description");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmVariableDescription.setControl(composite);
		composite.setLayout(new GridLayout(1, false));

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(3, false));
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_composite_1.heightHint = 228;
		composite_1.setLayoutData(gd_composite_1);

		Label lblAvailableData = new Label(composite_1, SWT.NONE);
		lblAvailableData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblAvailableData.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblAvailableData.setText("Numeric Variables:");
		new Label(composite_1, SWT.NONE);

		Label lblTestVariables = new Label(composite_1, SWT.NONE);
		lblTestVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblTestVariables.setSize(109, 18);
		lblTestVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblTestVariables.setText("Test Variable(s):");

		lstNumericVariables = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);

		lstNumericVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstNumericVariables.widthHint = 80;
		gd_lstNumericVariables.heightHint = 130;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);

		DataManipulationManager dataManipulationManager = new DataManipulationManager();
		ArrayList<String> s = dataManipulationManager.getVarInfoFromFile(filePath);
		
				btnAdd = new Button(composite_1, SWT.NONE);
				GridData gd_btnAdd = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_btnAdd.widthHint = 90;
				btnAdd.setLayoutData(gd_btnAdd);
				btnAdd.setText("Add");
				
				lstTestVariables = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
				GridData gd_lstTestVariables = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
				gd_lstTestVariables.heightHint = 130;
				gd_lstTestVariables.widthHint = 80;
				lstTestVariables.setLayoutData(gd_lstTestVariables);
				lstTestVariables.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));

		grpCorrelationCoefficients = new Group(composite, SWT.NONE);
		grpCorrelationCoefficients.setLayout(new GridLayout(3, false));
		grpCorrelationCoefficients.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpCorrelationCoefficients.setText("Correlation Coefficients");

		btnPearson = new Button(grpCorrelationCoefficients, SWT.CHECK);
		btnPearson.setSelection(true);
		btnPearson.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnPearson.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnPearson.setText("Pearson");

		btnKendallsTaub = new Button(grpCorrelationCoefficients, SWT.CHECK);

		btnKendallsTaub.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnKendallsTaub.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnKendallsTaub.setText("Kendall's tau-b");

		btnSpearman = new Button(grpCorrelationCoefficients, SWT.CHECK);
		btnSpearman.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnSpearman.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnSpearman.setText("Spearman");

		TabItem tbtmOptions = new TabItem(tabFolder, SWT.NONE);
		tbtmOptions.setText("Options");

		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		tbtmOptions.setControl(composite_4);
		composite_4.setLayout(new GridLayout(1, false));

		Group grpAlternativeHypothesis = new Group(composite_4, SWT.NONE);
		grpAlternativeHypothesis.setLayout(new GridLayout(1, false));
		grpAlternativeHypothesis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpAlternativeHypothesis.setText("Alternative Hypothesis:");

		btnLessThan = new Button(grpAlternativeHypothesis, SWT.RADIO);
		btnLessThan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnLessThan.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnLessThan.setText("Less than");

		btnGreaterThan = new Button(grpAlternativeHypothesis, SWT.RADIO);
		btnGreaterThan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnGreaterThan.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnGreaterThan.setText("Greater than");

		btnNotEqual = new Button(grpAlternativeHypothesis, SWT.RADIO);
		btnNotEqual.setSelection(true);
		btnNotEqual.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnNotEqual.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnNotEqual.setText("Not Equal");

		Group grpDisplay = new Group(composite_4, SWT.NONE);
		grpDisplay.setText("Display:");
		grpDisplay.setLayout(new GridLayout(1, false));
		GridData gd_grpDisplay = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpDisplay.heightHint = 35;
		grpDisplay.setLayoutData(gd_grpDisplay);

		btnSummaryStatistics = new Button(grpDisplay, SWT.CHECK);
		btnSummaryStatistics.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		GridData gd_btnSummaryStatistics = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_btnSummaryStatistics.heightHint = 53;
		btnSummaryStatistics.setLayoutData(gd_btnSummaryStatistics);
		btnSummaryStatistics.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		btnSummaryStatistics.setText("Summary Statistics");

		Group grpGraph = new Group(composite_4, SWT.NONE);
		grpGraph.setLayout(new GridLayout(1, false));
		GridData gd_grpGraph = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpGraph.heightHint = 37;
		grpGraph.setLayoutData(gd_grpGraph);
		grpGraph.setText("Graph:");

		btnScatterplot = new Button(grpGraph, SWT.CHECK);
		btnScatterplot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		GridData gd_btnScatterplot = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_btnScatterplot.heightHint = 50;
		btnScatterplot.setLayoutData(gd_btnScatterplot);
		btnScatterplot.setText("Scatterplot");


		
			initializeContent();
			return container;
	}

	private void initializeContent(){
		listManager.initializeNumericList(lstNumericVariables, filePath);
		listManager.initializeSingleButtonList(lstNumericVariables, lstTestVariables, btnAdd);
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		 createButton(parent, IDialogConstants.RETRY_ID, "Reset", false);
		 btnOk = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed(){
		
		if(lstTestVariables.getItemCount() <= 1 || ! listManager.isCheckBoxesHasBoolean(grpCorrelationCoefficients)){
			MessageDialog.openError(this.getShell(), "Form validation error", "Validation Error: There should be atleast one variable in Test Variable(s) and one Correlation Coefficient box is checked");
			return;
		}
		btnOk.setEnabled(false);
		ProjectExplorerManager projectMan = new ProjectExplorerManager();
		//					InputTransform createRObject= new InputTransform();
		String fileName = filePath.replace(File.separator, "/");
		Calendar lCDateTime = Calendar.getInstance();
		File outputFolder =  new File( StarAnalysisUtilities.createOutputFolder(filePath, "CorrelationAnalysis"));
//		File outputFolder = new File(((ProjectExplorerTreeNodeModel)projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getPath().toString() +  File.separator +"CorrelationAnalysis_" + lCDateTime.getTimeInMillis());
		outputFolder.mkdir();
		OperationProgressDialog rInfo = new OperationProgressDialog(
				getShell(), "Performing Analysis");
		rInfo.open();
		String outputFile = (outputFolder.getPath().toString() +  File.separator ).toString().replace(File.separator, "/");
		String[] respvarVector = lstTestVariables.getItems();
//		String graphFileName = (outputFolder.getPath().toString() + File.separator +"scatterplot_").toString().replace(File.separator, "/");
		ArrayList<String> listMethod = new ArrayList<String>();
		if(btnPearson.getSelection()){
							listMethod.add("pearson");
		}

		if(btnSpearman.getSelection()){
			listMethod.add("spearman");
		}
		if(btnKendallsTaub.getSelection()){
			listMethod.add("kendall");
		}
		String alternative = "NULL";
		if(btnLessThan.getSelection()){
			alternative = "less";
		}
		else if(btnGreaterThan.getSelection()){
			alternative = "greater";
		}
		else if(btnNotEqual.getSelection()){
			alternative = "two.sided";
		}
		//					String method = createRObject.createRVector(listMethod.toArray(new String[listMethod.size()]));
		String[] method = listMethod.toArray(new String[listMethod.size()]);

		String statistics = convertToString(btnSummaryStatistics.getSelection());
		
		String outputPlot =  convertToString(btnScatterplot.getSelection());

		ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doBivariateCorr(fileName, 
				outputFile, respvarVector, 
				method, alternative, statistics, 
				outputPlot);
		
		
		StarAnalysisUtilities.testVarArgs(
		fileName, 
				outputFile, respvarVector, 
				method, alternative, statistics, 
				outputPlot);
		
		StarAnalysisUtilities.testVarArgs(	fileName, 
				outputFile, respvarVector, 
				method, alternative, statistics, 
				outputPlot);
		
		projectMan.refreshFolder((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData());
		projectMan.getOutputFolder(ProjectExplorerView.projectTree).setExpanded(true);
		getShell().setMinimized(true);
		btnOk.setEnabled(true);
		rInfo.close();
		PartStackHandler.execute(projectMan.getTreeNodeModelbyFilePath(outputFolder.getAbsolutePath()));
	}
	
	
	@Override
	protected void buttonPressed(int buttonId){
	
		if (IDialogConstants.OK_ID == buttonId) {
			okPressed();
		}
		else if(IDialogConstants.RETRY_ID  == buttonId){
			//System.out.println("WHY CLOSED??!!");
			btnOk.setEnabled(true);
			lstNumericVariables.removeAll();
			
			listManager.initializeNumericList(lstNumericVariables, filePath);
			lstTestVariables.removeAll();
			btnPearson.setSelection(true);
			btnKendallsTaub.setSelection(false);
			btnSpearman.setSelection(false);
			btnNotEqual.setSelection(true);
			btnGreaterThan.setSelection(false);
			btnLessThan.setSelection(false);
			btnSummaryStatistics.setSelection(false);
			btnScatterplot.setSelection(false);
			btnOk.setEnabled(true);

			
		}
		else if (IDialogConstants.CANCEL_ID == buttonId) {
			close();
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */

	private String convertToString(boolean value){
		if(value) return "TRUE";
		else return "FALSE";
	}

	@Override
	protected Point getInitialSize() {
		return new Point(562, 487);
	}

}
