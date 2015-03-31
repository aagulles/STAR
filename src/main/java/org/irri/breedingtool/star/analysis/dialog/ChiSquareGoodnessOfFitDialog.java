package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.DialogListValidator;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.irri.breedingtool.utility.StarRandomizationUtilities;


public class ChiSquareGoodnessOfFitDialog extends Dialog {
	private Table table;
	private String filePath = PartStackHandler.getActiveElementID();
	private Combo cmbInputType;
	private List lstAvailableVariables;
	private Button btnAllCategory;
	private Button btnSpecify;
	private Button button_2;
	private Text txtTestVariable;
	private Text txtFrequencyVariable;
	private Button btnAddFrequency;
	private DialogFormUtility dlgManager = new DialogFormUtility();
	private Button btnAddTest;
	private int EDITABLECOLUMN;
	private Label lblFrequencyVariable;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ChiSquareGoodnessOfFitDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(false);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Goodness of Fit Test : " +  new File(filePath).getName());
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setTouchEnabled(true);
		GridLayout gl_container = new GridLayout(4, false);
		gl_container.marginHeight = 8;
		gl_container.marginWidth = 8;
		container.setLayout(gl_container);
						
								Label lblTypeOfInput = new Label(container, SWT.NONE);
								lblTypeOfInput.setText("Type of Input");
						
								cmbInputType = new Combo(container, SWT.READ_ONLY);
								cmbInputType.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										txtFrequencyVariable.setEditable(false);
										btnAddFrequency.setEnabled(false);
										lblFrequencyVariable.setEnabled(false);
									}	
								});
								
										GridData gd_cmbInputType = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
										gd_cmbInputType.widthHint = 189;
										cmbInputType.setLayoutData(gd_cmbInputType);
										cmbInputType.setVisibleItemCount(2);
										cmbInputType.setItems(new String[] {"Raw Data", "Summary Data"});
										cmbInputType.select(0);
				new Label(container, SWT.NONE);
		
				Label lblAvailableVariables = new Label(container, SWT.NONE);
				GridData gd_lblAvailableVariables = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
				gd_lblAvailableVariables.heightHint = 16;
				lblAvailableVariables.setLayoutData(gd_lblAvailableVariables);
				lblAvailableVariables.setText("Available Variables");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		lstAvailableVariables = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		lstAvailableVariables.setEnabled(true);
		GridData gd_lstAvailableVariables = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3);
		gd_lstAvailableVariables.widthHint = 210;
		gd_lstAvailableVariables.heightHint = 60;
		lstAvailableVariables.setLayoutData(gd_lstAvailableVariables);
		
				btnAddTest = new Button(container, SWT.NONE);
				GridData gd_btnAddTest = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_btnAddTest.widthHint = 90;
				btnAddTest.setLayoutData(gd_btnAddTest);
				btnAddTest.setText("Add");
						
								Label lblTestVariable = new Label(container, SWT.NONE);
								lblTestVariable.setText("Test Variable");
				
						txtTestVariable = new Text(container, SWT.BORDER | SWT.READ_ONLY);
						txtTestVariable.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
						
								txtTestVariable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
								GridData gd_txtTestVariable = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
								gd_txtTestVariable.widthHint = 200;
								gd_txtTestVariable.heightHint = 15;
								txtTestVariable.setLayoutData(gd_txtTestVariable);
		
				btnAddFrequency = new Button(container, SWT.NONE);
				GridData gd_btnAddFrequency = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_btnAddFrequency.widthHint = 90;
				btnAddFrequency.setLayoutData(gd_btnAddFrequency);
				btnAddFrequency.setEnabled(false);
				btnAddFrequency.setText("Add");
				
						lblFrequencyVariable = new Label(container, SWT.NONE);
						lblFrequencyVariable.setEnabled(false);
						lblFrequencyVariable.setText("Frequency Variable");
		
				txtFrequencyVariable = new Text(container, SWT.BORDER | SWT.READ_ONLY);
				txtFrequencyVariable.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				txtFrequencyVariable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtFrequencyVariable.setTouchEnabled(true);
				GridData gd_txtFrequencyVariable = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
				gd_txtFrequencyVariable.heightHint = 15;
				txtFrequencyVariable.setLayoutData(gd_txtFrequencyVariable);
		new Label(container, SWT.NONE);
				new Label(container, SWT.NONE);
				new Label(container, SWT.NONE);
		
				Group grpExpectedValues = new Group(container, SWT.NONE);
				grpExpectedValues.setLayout(new GridLayout(1, false));
				grpExpectedValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
				grpExpectedValues.setText("Expected Values");
				
						btnAllCategory = new Button(grpExpectedValues, SWT.RADIO);
						btnAllCategory.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								table.setEnabled(false);
							}
						});
						btnAllCategory.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						btnAllCategory.setSelection(true);
						btnAllCategory.setText("All categories are equal");
						
								btnSpecify = new Button(grpExpectedValues, SWT.RADIO);
								btnSpecify.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										table.setEnabled(true);

									}
								});
								btnSpecify.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
								btnSpecify.setText("Specify");
								
										table = new Table(grpExpectedValues, SWT.BORDER | SWT.FULL_SELECTION);
										table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
										table.setEnabled(false);
										table.setHeaderVisible(true);
										table.setLinesVisible(true);
										
												TableColumn tblclmnNewColumn = new TableColumn(table, SWT.CENTER);
												tblclmnNewColumn.setWidth(88);
												tblclmnNewColumn.setText("Category");
												
														TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.CENTER);
														tblclmnNewColumn_1.setWidth(193);
														tblclmnNewColumn_1.setText("Expected Frequency/Ratio");
														// editing the second column

														final Spinner sprLevel = new Spinner(table, SWT.NONE);
														sprLevel.setIncrement(1);
		initializeForm();
		return container;
	}

	void initializeForm(){
		dlgManager.initializeSingleSelectionList(lstAvailableVariables, txtTestVariable, btnAddTest);
		dlgManager.initializeSingleSelectionList(lstAvailableVariables, txtFrequencyVariable,btnAddFrequency, new goodnessOfFitListValidator(cmbInputType,lblFrequencyVariable));
		dlgManager.initializeVariableList(lstAvailableVariables, filePath);
		txtTestVariable.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				table.removeAll();
				if(txtTestVariable.getText().equals("")) return ;
				DataManipulationManager dataMan = new DataManipulationManager();

				String[] envtLevels = dataMan.getEnvtLevels(txtTestVariable.getText(), new File(filePath));
				for(int i = 0; i < envtLevels.length; i++){
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(new String[]{envtLevels[i],"1"});
				}
			}
		});
		cmbInputType.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {

				
			}
		});




		final TableEditor editor = new TableEditor(table);

		table.addListener(SWT.MouseDown, new Listener() {


			@Override
			public void handleEvent(Event e) {



				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();

				// Identify the selected row
				TableItem item = table.getSelection()[0];
				if (item == null) return;
				Point mPoint = new Point(e.x,e.y);

				for(int i = 0; i < item.getParent().getColumnCount(); i++){
					if(item.getBounds(i).contains(mPoint))
						EDITABLECOLUMN = i;
				}

				System.out.println(EDITABLECOLUMN);

				if(EDITABLECOLUMN == 0) EDITABLECOLUMN = 1;
				// The control that will be the editor must be a child of the Table




				if (EDITABLECOLUMN == 1) {

					Spinner newEditor = new Spinner(table, SWT.NONE);
					newEditor.setIncrement(1);
					newEditor.setMaximum(9999999);
					newEditor.setMinimum(0);

					newEditor.setSelection(Integer.valueOf(item
							.getText(EDITABLECOLUMN)));

					newEditor.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent me) {



							Spinner spinner = (Spinner) editor.getEditor();

							editor.getItem().setText(EDITABLECOLUMN,
									spinner.getText());

							StarRandomizationUtilities.validateSpinner(spinner);



						}
					});
					newEditor.addFocusListener(new FocusListener(){

						@Override
						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void focusLost(FocusEvent e) {
							Spinner text = (Spinner) editor.getEditor();
							text.dispose();
						}

					});
					//		newEditor.selectAll();
					newEditor.setFocus();
					editor.setEditor(newEditor, item, EDITABLECOLUMN);

				}

			}
		});
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 70;

	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void okPressed(){



		String dataFileName = filePath.replace(File.separator, "/");


		//supply path and name of text file where text output is going to be saved

		String freqVar = null;
		//specify parameters
		String inputType = (cmbInputType.getText().equals("Raw Data")) ? "raw" : "summary";
		String testvar = txtTestVariable.getText();
		if((cmbInputType.getText().equals("Raw Data"))){
			inputType = "raw";
			freqVar = null;
		}
		else{
			inputType = "summary";
			freqVar = txtFrequencyVariable.getText();
		}

		boolean categoryEqual = btnAllCategory.getSelection();
		String[] specifiedCategory = new String[table.getItemCount()];
		int[] specifiedExpFreq = new int[table.getItemCount()];

		for(int i = 0; i < table.getItemCount(); i++){
			specifiedCategory[i] = table.getItem(i).getText(0);
			specifiedExpFreq[i] = Integer.parseInt(table.getItem(i).getText(1));
		}
		if(btnAllCategory.getSelection()){
			specifiedCategory = null;
			specifiedExpFreq = null;
		}
		String resultFolderPath = StarAnalysisUtilities.createOutputFolder(filePath, "ChiSquareGoodnessOfFit");
		OperationProgressDialog rInfo = new OperationProgressDialog(
				getShell(), "Performing Analysis");
		rInfo.open();
		//		StarAnalysisUtilities.testVarArgs(
		//				dataFileName,
		//				resultFolderPath.replace(File.separator, "/"),
		//				inputType, 
		//				testvar, 
		//				freqVar, 
		//				categoryEqual, 
		//				specifiedCategory, 
		//				specifiedExpFreq);
		//	
	
		ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doChiSqGoodnessOfFit(
				dataFileName,
				resultFolderPath.replace(File.separator, "/"),
				inputType, 
				testvar, 
				freqVar, 
				categoryEqual, 
				specifiedCategory, 
				specifiedExpFreq);


		StarAnalysisUtilities.openAndRefreshFolder(resultFolderPath);
		rInfo.close();
	}
	@Override
	protected void buttonPressed(int bID){
		if(IDialogConstants.OK_ID == bID){
			okPressed();
		}
		else if (IDialogConstants.CANCEL_ID  == bID){
			cancelPressed();
		}
		else if (IDialogConstants.RETRY_ID  == bID){
			table.removeAll();
			cmbInputType.select(0);
			lstAvailableVariables.removeAll();
			btnAllCategory.setSelection(true);
			btnSpecify.setSelection(false);

			txtTestVariable.setText("");
			txtFrequencyVariable.setText("");
			btnAddFrequency.setEnabled(false);
			btnAddFrequency.setText("Add");

			btnAddTest.setText("Add");
			btnAddTest.setEnabled(false);
			dlgManager.initializeVariableList(lstAvailableVariables, filePath);
		}
	}
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button_1 = createButton(parent, IDialogConstants.RETRY_ID, "New button", false);
		button_1.setText("Reset");
		button_2 = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(479, 403);
	}
	public class goodnessOfFitListValidator extends DialogListValidator{
		Combo cmbInputType;
		Label lblFreq;
		public goodnessOfFitListValidator( Combo cmbInputType, Label lbl) {
			super();
			this.lblFreq = lbl;
			this.cmbInputType = cmbInputType;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public boolean validate(){
			lblFreq.setEnabled(! cmbInputType.getText().equals("Raw Data"));
			return ! cmbInputType.getText().equals("Raw Data");
			
		}
		
	}
}
