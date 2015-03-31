package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;

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
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.DialogListValidator;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

public class ChiSquareIndependenceTestDialog extends Dialog {

	private String filePath = PartStackHandler.getActivePart().getElementId().toString(); 
	private List lstAvailableData;
	private List lstRowVariables;
	private List lstColumnVariables;
	private Button btnExpected;
	private Button btnRow;
	private Button btnColumn;
	private Button btnTotal;
	private Button btnStandardize;
	private Button btnPhiCoefficient;
	private Button btnCramersV;
	private Button btnContingencyCoefficient;
	private DialogFormUtility  listManager = new DialogFormUtility();
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private Button btnOk;
	private TabItem tbtmCellDisplay;
	private Composite cmpOptions;
	private Group grpCount;
	private Group grpPercentages;
	private Group grpResidual;
	private Group grpNominal;
	private Button btnAddRowVariables;
	private Button btnAddColumnVariables;
	private Label lblTypeOfInput;
	private Combo cmboType;
	private Button btnAddFreq;
	private Text txtFreq;
	private Label lblFrequencyVariable;
	private Label lblNewLabel;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ChiSquareIndependenceTestDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
		}
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setText("Test of Independence :" + new File(filePath).getName());
		}


	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginHeight = 8;
		gl_container.marginWidth = 8;
		container.setLayout(gl_container);
		
		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtmDescription = new TabItem(tabFolder, SWT.NONE);
		tbtmDescription.setText("Description");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmDescription.setControl(composite);
		composite.setLayout(new GridLayout(3, false));
		
		lblTypeOfInput = new Label(composite, SWT.NONE);
		lblTypeOfInput.setText("Type of Input");
		
		cmboType = new Combo(composite, SWT.READ_ONLY);
		cmboType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(cmboType.getSelectionIndex() == 0){
					lblFrequencyVariable.setEnabled(false);
					btnAddFreq.setEnabled(false);
					txtFreq.setEnabled(false);
					
				} else {
					lblFrequencyVariable.setEnabled(true);
					btnAddFreq.setEnabled(true);
					txtFreq.setEnabled(true);
//					dataManipulationManager.moveSelectedStringFromListToText(lstAvailableData, txtFreq);
//					listManager.initializeSingleSelectionList(lstAvailableData, txtFreq, btnAddFreq);

						for(int i = lstColumnVariables.getItemCount() - 1; i > 0; i--){
							lstAvailableData.add(lstColumnVariables.getItem(i));
							lstColumnVariables.remove(i);
						}
						for(int i = lstRowVariables.getItemCount() -1; i > 0; i--){
							lstAvailableData.add(lstRowVariables.getItem(i));
							lstRowVariables.remove(i);
						}
						btnAddColumnVariables.setEnabled(false);
						btnAddRowVariables.setEnabled(false);
				}
				
			}
		});
		cmboType.setItems(new String[] {"Raw Data", "Summary Data"});
		GridData gd_cmboType = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_cmboType.widthHint = 100;
		cmboType.setLayoutData(gd_cmboType);
		cmboType.select(0);
		
		Label lblAvailableData = new Label(composite, SWT.NONE);
		lblAvailableData.setText("Available Data");
		new Label(composite, SWT.NONE);
		
		Label lblRowVariabless = new Label(composite, SWT.NONE);
		lblRowVariabless.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblRowVariabless.setText("Row Variables(s)");
		
		lstAvailableData = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
//		lstAvailableData.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(lstAvailableData.getSelectionCount()>0 && cmboType.getSelectionIndex() == 0){
//					btnAddFreq.setEnabled(false);
//					lblFrequencyVariable.setEnabled(false);
//					txtFreq.setEnabled(false);
//				}else{
//					btnAddFreq.setEnabled(true);
//					lblFrequencyVariable.setEnabled(true);
//					txtFreq.setEnabled(true);
////					dataManipulationManager.moveSelectedStringFromListToText(lstAvailableData, txtFreq);
////					listManager.initializeSingleSelectionList(lstAvailableData, txtFreq, btnAddFreq);
//				}
//				
//			}
//		});
		GridData gd_lstAvailableData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 12);
		gd_lstAvailableData.widthHint = 50;
		gd_lstAvailableData.heightHint = 120;
		lstAvailableData.setLayoutData(gd_lstAvailableData);
		new Label(composite, SWT.NONE);
		
		lstRowVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstRowVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5);
		gd_lstRowVariables.widthHint = 50;
		gd_lstRowVariables.heightHint = 60;
		lstRowVariables.setLayoutData(gd_lstRowVariables);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnAddRowVariables = new Button(composite, SWT.NONE);
		GridData gd_btnAddRowVariables = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddRowVariables.heightHint = 23;
		gd_btnAddRowVariables.widthHint = 90;
		btnAddRowVariables.setLayoutData(gd_btnAddRowVariables);
		btnAddRowVariables.setText("Add");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblColumnVariables = new Label(composite, SWT.NONE);
		lblColumnVariables.setText("Column Variable(s)");
		new Label(composite, SWT.NONE);
		
		lstColumnVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstColumnVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_lstColumnVariables.widthHint = 50;
		gd_lstColumnVariables.heightHint = 60;
		lstColumnVariables.setLayoutData(gd_lstColumnVariables);
		new Label(composite, SWT.NONE);
		
		btnAddColumnVariables = new Button(composite, SWT.NONE);
		GridData gd_btnAddColumnVariables = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddColumnVariables.heightHint = 23;
		gd_btnAddColumnVariables.widthHint = 90;
		btnAddColumnVariables.setLayoutData(gd_btnAddColumnVariables);
		btnAddColumnVariables.setText("Add");
		new Label(composite, SWT.NONE);
		
		tbtmCellDisplay = new TabItem(tabFolder, SWT.NONE);
		tbtmCellDisplay.setText("Options");
		
		cmpOptions = new Composite(tabFolder, SWT.NONE);
		tbtmCellDisplay.setControl(cmpOptions);
		cmpOptions.setLayout(new GridLayout(2, false));
		
		Group grpStatistics = new Group(cmpOptions, SWT.NONE);
		grpStatistics.setLayout(new GridLayout(1, false));
		GridData gd_grpStatistics = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpStatistics.widthHint = 243;
		grpStatistics.setLayoutData(gd_grpStatistics);
		grpStatistics.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpStatistics.setText("Statistics");
		
		grpNominal = new Group(grpStatistics, SWT.NONE);
		grpNominal.setText("Nominal:");
		grpNominal.setLayout(new GridLayout(1, false));
		GridData gd_grpNominal = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		gd_grpNominal.heightHint = 115;
		grpNominal.setLayoutData(gd_grpNominal);
		
		btnPhiCoefficient = new Button(grpNominal, SWT.CHECK);
		btnPhiCoefficient.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnPhiCoefficient.setText("Phi Coefficient");
		
		btnCramersV = new Button(grpNominal, SWT.CHECK);
		btnCramersV.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnCramersV.setText("Cramers V");
		
		btnContingencyCoefficient = new Button(grpNominal, SWT.CHECK);
		btnContingencyCoefficient.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnContingencyCoefficient.setText("Contingency Coefficient");
		
		Group grpCellDisplay = new Group(cmpOptions, SWT.NONE);
		grpCellDisplay.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpCellDisplay.setLayout(new GridLayout(1, false));
		GridData gd_grpCellDisplay = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpCellDisplay.widthHint = 256;
		grpCellDisplay.setLayoutData(gd_grpCellDisplay);
		grpCellDisplay.setText("Cell Display");
		
		grpCount = new Group(grpCellDisplay, SWT.NONE);
		grpCount.setLayout(new GridLayout(1, false));
		grpCount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpCount.setText("Count:");
		
		btnExpected = new Button(grpCount, SWT.CHECK);
		btnExpected.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		btnExpected.setText("Expected");
		
		grpPercentages = new Group(grpCellDisplay, SWT.NONE);
		grpPercentages.setLayout(new GridLayout(1, false));
		grpPercentages.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpPercentages.setText("Percentages:");
		
		btnRow = new Button(grpPercentages, SWT.CHECK);
		btnRow.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		btnRow.setText("Row");
		
		btnColumn = new Button(grpPercentages, SWT.CHECK);
		btnColumn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		btnColumn.setText("Column");
		
		btnTotal = new Button(grpPercentages, SWT.CHECK);
		btnTotal.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		btnTotal.setText("Total");
		
		grpResidual = new Group(grpCellDisplay, SWT.NONE);
		grpResidual.setLayout(new GridLayout(1, false));
		grpResidual.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpResidual.setText("Residual:");
		
		btnStandardize = new Button(grpResidual, SWT.CHECK);
		btnStandardize.setText("Standardize");
	
		new Label(composite, SWT.NONE);
		
		lblFrequencyVariable = new Label(composite, SWT.NONE);
		lblFrequencyVariable.setEnabled(false);
		lblFrequencyVariable.setText("Frequency Variable:");
		
		btnAddFreq = new Button(composite, SWT.NONE);
//		btnAddFreq.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(lstAvailableData.getSelectionCount()>0 && cmboType.getSelectionIndex() == 0){
//					btnAddFreq.setEnabled(false);
//					lblFrequencyVariable.setEnabled(false);
//					txtFreq.setEnabled(false);
//				}else{
//					btnAddFreq.setEnabled(true);
//					lblFrequencyVariable.setEnabled(true);
//					txtFreq.setEnabled(true);
//						if(lstAvailableData.getSelectionCount()>0){
//							btnAddFreq.setText("Remove");
//							String selVar[] = lstAvailableData.getSelection();
//							for(int i=0; i< selVar.length; i++){
//								txtFreq.setText(selVar[i]);
//							}
//							lstAvailableData.remove(lstAvailableData.getSelectionIndices());
//						} else {
//							btnAddFreq.setEnabled(true);
//							btnAddFreq.setText("Add");
//							dataManipulationManager.moveSelectedStringFromTextToList(txtFreq, lstAvailableData);
//						}
////					dataManipulationManager.moveSelectedStringFromListToText(lstAvailableData, txtFreq);					
////					listManager.initializeSingleSelectionList(lstAvailableData, txtFreq, btnAddFreq);
//
//				}
//				
//			}
//		});
		btnAddFreq.setEnabled(false);
		GridData gd_btnAddFreq = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddFreq.heightHint = 23;
		gd_btnAddFreq.widthHint = 90;
		btnAddFreq.setLayoutData(gd_btnAddFreq);
		btnAddFreq.setText("Add");
		
		txtFreq = new Text(composite, SWT.BORDER);
//		txtFreq.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(lstAvailableData.getSelectionCount()>0 && cmboType.getSelectionIndex() == 0){
//					btnAddFreq.setEnabled(false);
//					lblFrequencyVariable.setEnabled(false);
//					txtFreq.setEnabled(false);
//				}else{
//					btnAddFreq.setEnabled(true);
//					lblFrequencyVariable.setEnabled(true);
//					txtFreq.setEnabled(true);
////					lstAvailableData.getSelection();
////					dataManipulationManager.moveSelectedStringFromListToText(lstAvailableData, txtFreq);
////					listManager.initializeSingleSelectionList(lstAvailableData, txtFreq, btnAddFreq);
//
//				}
//				
//			}
//		});
		txtFreq.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtFreq.setEnabled(false);
		GridData gd_txtFreq = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_txtFreq.heightHint = 15;
		txtFreq.setLayoutData(gd_txtFreq);
		
		lblNewLabel = new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		initializeContent();
		return container;
	}
	
	public void initializeContent(){
		listManager.initializeSingleButtonList(lstAvailableData, lstColumnVariables, btnAddColumnVariables, new validateAdding(lstColumnVariables));
		listManager.initializeVariableList(lstAvailableData, filePath);
		listManager.initializeSingleButtonList(lstAvailableData, lstRowVariables, btnAddRowVariables,new validateAdding(lstRowVariables));
		listManager.initializeSingleSelectionList(lstAvailableData, txtFreq, btnAddFreq, new validateType(cmboType,btnAddFreq));
	}
	@Override
	protected void buttonPressed(int buttonId){
		if(buttonId == IDialogConstants.OK_ID) okPressed();
		else if(buttonId == IDialogConstants.CANCEL_ID) cancelPressed();
		else if(buttonId == IDialogConstants.RETRY_ID){
			lstAvailableData.removeAll();
			lstColumnVariables.removeAll();
			lstRowVariables.removeAll();
			txtFreq.setText("");
			btnAddRowVariables.setText("Add");
			btnAddColumnVariables.setText("Add");
			btnAddFreq.setText("Add");
			btnAddRowVariables.setEnabled(false);
			btnAddColumnVariables.setEnabled(false);
			btnAddFreq.setEnabled(false);
			cmboType.setText("Raw Data");
			btnColumn.setSelection(false);
			btnContingencyCoefficient.setSelection(false);
			btnCramersV.setSelection(false);
			btnExpected.setSelection(false);
			btnPhiCoefficient.setSelection(false);
			btnRow.setSelection(false);
			btnStandardize.setSelection(false);
			btnTotal.setSelection(false);
			listManager.setCheckBoxesToBoolean(false, grpResidual,grpCount,grpPercentages,grpNominal);
		
			listManager.initializeVariableList(lstAvailableData, filePath);

		}
	}
	@Override
	protected void okPressed(){
		if(!txtFreq.getText().isEmpty()){
			String isNumeric = "TRUE";
			isNumeric = dataManipulationManager.isNumeric(filePath, txtFreq.getText());
			if(isNumeric.equals("FALSE")){
				MessageDialog.openError(getShell(), "Error", "The Frequency Variable should be a numeric variable");
				return;
			}
		}
		if(lstRowVariables.getItemCount() <= 0 || lstColumnVariables.getItemCount() <= 0 ){
			MessageDialog.openError(getShell(), 
					"Error",
					"Atleast one item in Column Variables and Row Variables and\n atleast one option is chosen in the Option tab");
			return;
		}
		
		btnOk.setEnabled(false);
		String dataFileName = filePath;
		String outFolder = StarAnalysisUtilities.createOutputFolder(filePath,"ChiSquareTestOfIndependence");
		String type = "raw";
		if(cmboType.getSelectionIndex() == 0){
			type = "raw";
		}else type= "summary";
		OperationProgressDialog rInfo = new OperationProgressDialog(
				getShell(), "Performing Analysis");
		rInfo.open();
		String outFileName = outFolder + "Output.txt";
		StarAnalysisUtilities.testVarArgs(dataFileName.replace(File.separator, "/"), outFileName.replace(File.separator, "/"), lstRowVariables.getItems(), lstColumnVariables.getItems(),  btnExpected.getSelection(), btnStandardize.getSelection(), btnTotal.getSelection(), btnRow.getSelection(), btnColumn.getSelection(), btnPhiCoefficient.getSelection(), btnCramersV.getSelection(), btnContingencyCoefficient.getSelection());
		
		ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doChiSqTestOfIndependence(
				dataFileName.replace(File.separator, "/"),
				outFileName.replace(File.separator, "/"),
				type,
				lstRowVariables.getItems(),
				lstColumnVariables.getItems(),
				txtFreq.getText(),
				true,
				btnExpected.getSelection(),
				btnStandardize.getSelection(),
				btnTotal.getSelection(),
				btnRow.getSelection(),
				btnColumn.getSelection(), 
				btnPhiCoefficient.getSelection(), 
				btnCramersV.getSelection(),
				btnContingencyCoefficient.getSelection()
				);
		this.getShell().setMinimized(true);
		
		StarAnalysisUtilities.openAndRefreshFolder(outFolder);
		btnOk.setEnabled(true);
		rInfo.close();
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

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(471, 442);
	}
	
	
	
	public class validateAdding extends DialogListValidator{
		
		
		private List varList;

		public validateAdding(List list){
			
			varList = list;
		}
		
		@Override
		public boolean validate(){
			if(cmboType.getSelectionIndex() == 1 && varList.getItemCount() > 0) return false;
			
			return true;
		}
		@Override
		public boolean performAddProcess(){
				if(cmboType.getSelectionIndex() == 1 && lstAvailableData.getSelectionCount()> 1){
					MessageDialog.openError(getShell(), "Error", "Cannot add multiple variable if type of input is Summary Data");
					return false;
				}
			return true;
		}
		
	}
	
	public class validateType extends DialogListValidator{
		Combo cmbType;
		Button btnAddFreq;
		public validateType( Combo cmbType, Button btnAddFreq) {
			super();
			this.btnAddFreq = btnAddFreq;
			this.cmbType = cmbType;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public boolean validate(){
			btnAddFreq.setEnabled(! cmbType.getText().equals("Raw Data"));
			return ! cmbType.getText().equals("Raw Data");
			
		}
	}
	
}
