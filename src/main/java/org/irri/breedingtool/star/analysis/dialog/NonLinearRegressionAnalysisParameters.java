package org.irri.breedingtool.star.analysis.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Spinner;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.graphs.managers.GraphTableManager;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class NonLinearRegressionAnalysisParameters extends Dialog {

	private Button btnOk;
	private String filePath = PartStackHandler.getActiveElementID();
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private GraphTableManager tableManager;
	private DialogFormUtility listManager = new DialogFormUtility();
	private ArrayList<String> tableData = new ArrayList<String>();
	private Composite composite;
	private Label lblStartingValue;
	private Button btnAdd;
	private TableColumn tableName;
	private TableColumn tableStartValue;
	private Label lblName;
	private Label lblSummary;
	private Button btnEdit;
	private Button btnDelete;
	private Label lblNewLabel;
	private Label label;
	private Label lblNewLabel_1;
	private String returnStartVal;
	public ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel> parameterList = new ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel>();
	
	public ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel> getParameterList() {
		System.out.println(parameterList.isEmpty());
		return parameterList;
	}
	public void reloadParameterList() {
		parameterList.clear();
		System.out.println("it: " + table.getItemCount());
		for(int i = 0; i < table.getItemCount(); i++){
			parameterList.add(new ParameterModel(table.getItem(i).getText(0),table.getItem(i).getText(1)));
			System.out.println("i: " + 0);
		}
	}
	public void setParameterList(
			ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel> parameterList) {
		this.parameterList = parameterList;
	}

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	private String returnVal;
	private Table table;
	private Text txtName;
	private Text txtStartingValue;
		public String getReturnVal() {
		
			return returnVal;
		}
		public void setReturnVal(String returnVal) {
			this.returnVal = returnVal;
		}
		
	public NonLinearRegressionAnalysisParameters(Shell parentShell,ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel> parameterList) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE | SWT.SYSTEM_MODAL);
		this.parameterList = parameterList;
	}
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Nonlinear Regression: Parameters");
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
		
		composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(6, false));
		
		lblNewLabel = new Label(composite, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 10;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		lblName = new Label(composite, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblName.setText("Name:");
		
		txtName = new Text(composite, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.widthHint = 10;
		label.setLayoutData(gd_label);
		new Label(composite, SWT.NONE);
		
		lblStartingValue = new Label(composite, SWT.NONE);
		lblStartingValue.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblStartingValue.setText("Starting Value:");
		
		txtStartingValue = new Text(composite, SWT.BORDER);
		txtStartingValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblNewLabel_1 = new Label(composite, SWT.NONE);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_1.widthHint = 15;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		
		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtName.getText().equals("")){
					if(txtStartingValue.getText().equals("")){
						MessageDialog.openError(getShell(), "Error", "Please specify parameter name and a starting value.");
					}
					else MessageDialog.openError(getShell(), "Error", "Please specify a Parameter Name.");
				}
				else if(txtStartingValue.getText().equals("")){
					MessageDialog.openError(getShell(), "Error", "Please specify a starting value.");
				}
				else addVariablesToTable();
			}
		});
		GridData gd_btnAdd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnAdd.widthHint = 70;
		btnAdd.setLayoutData(gd_btnAdd);
		btnAdd.setText("Add");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblSummary = new Label(composite, SWT.NONE);
		lblSummary.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSummary.setText("Summary:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_table.heightHint = 66;
		gd_table.widthHint = 214;
		table.setLayoutData(gd_table);
		
		tableName = new TableColumn(table, SWT.NONE);
		tableName.setWidth(119);
		tableName.setText("Name");
		
		tableStartValue = new TableColumn(table, SWT.NONE);
		tableStartValue.setWidth(111);
		tableStartValue.setText("Starting Value");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnEdit = new Button(composite, SWT.NONE);
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(table.getItemCount()>0){
					if(table.getItemCount()==1 ||  table.getSelectionCount()>0){
						int index = table.getSelectionIndex();
						if(table.getSelectionCount()==0)index = 0;
						editRowValues(index);
					}else{
						MessageDialog.openWarning(getShell(), "No selected row", "Please select the row you want to delete.");
					}
				}else{
					MessageDialog.openWarning(getShell(), "Invalid input", "Table is empty. There is nothing to edit.");
				}
			}
		});
		GridData gd_btnEdit = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnEdit.widthHint = 77;
		btnEdit.setLayoutData(gd_btnEdit);
		btnEdit.setText("Edit");
		new Label(composite, SWT.NONE);
		
		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(table.getItemCount()>0){
					if(table.getItemCount()==1 ||  table.getSelectionCount()>0){
						int index=table.getSelectionIndex();
						if(table.getSelectionCount()==0)index = 0;
						dataManipulationManager.deleteTableRow(table, index);
					}else{
						MessageDialog.openWarning(getShell(), "No selected row", "Please select the row you want to delete.");
					}
				}
				else{
					MessageDialog.openWarning(getShell(),"Invalid input", "Table is empty. There is nothing to delete.");
				}
			}
		});
		GridData gd_btnDelete = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnDelete.widthHint = 70;
		btnDelete.setLayoutData(gd_btnDelete);
		btnDelete.setText("Delete");
		new Label(composite, SWT.NONE);
//		listManager.initializeSingleButtonList(lstAvailableData, lstDependentVariables, btnAddDependentVariable);
//		listManager.initializeSingleButtonList(lstAvailableData, lstIndependentVariables, btnAddIndependentVariable);		
//		listManager.initializeNumericList(lstAvailableData, filePath);
		
		initTable();
		return container;
	}
	
	protected void editRowValues(int index) {
		// TODO Auto-generated method stub
//		cancelResponseValues();

		TableItem tableItem = table.getItem(index);
		//set Variable Values
		
		if(!tableItem.getText(0).equals(""))txtName.setText(tableItem.getText(0));
		if(!tableItem.getText(1).equals(""))txtStartingValue.setText(tableItem.getText(1));

		dataManipulationManager.deleteTableRow(table, index);

	}

	protected void initTable() {
		// TODO Auto-generated method stub
		
		
		for(ParameterModel param : parameterList){
			TableItem tableItem = new TableItem(table, SWT.CENTER);

			tableItem.setText(0, param.name);
			tableItem.setText(1, param.value);
	
		}
			}

	protected void addVariablesToTable() {
		// TODO Auto-generated method stub
		String[] values = getVariableValues();
		TableItem tableItem = new TableItem(table, SWT.CENTER);

		tableItem.setText(0, values[0]);
		tableItem.setText(1, values[1]);
	}

	private String[] getVariableValues() {
		// TODO Auto-generated method stub
		String[] values = new String[2];

		if(txtName.getText() != null)values[0]=txtName.getText();
		else values[0]="";
		if(txtStartingValue.getText() != null)values[1]=txtStartingValue.getText();
		else values[1]="";
		
		removeVarValues();
		return values;
	}

	private void removeVarValues() {
		// TODO Auto-generated method stub

		txtName.setText("");
		txtStartingValue.setText("");
	}
	
	
@Override
	protected void buttonPressed(int buttonID){
		if(buttonID == IDialogConstants.OK_ID) okPressed();
		else if(buttonID == IDialogConstants.RETRY_ID){
			txtName.setText("");
			txtStartingValue.setText("");
			table.removeAll();
		}
		else this.close();
	}
	
protected void	okPressed(){
	
	btnOk.setEnabled(false);
	
	String indepVar = null;
	

	
	
	reloadParameterList();
	close();
		

	}

	public class ParameterModel{
		public String name;
		public String value;
		
		public ParameterModel(){
			
		}
		public ParameterModel(String name, String value){
			this.name = name;
			this.value = value;
		}
		
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
		return new Point(360, 412);
	}
	public String getReturnStartVal() {
		return returnStartVal;
	}
	public void setReturnStartVal(String returnStartVal) {
		this.returnStartVal = returnStartVal;
	}
}
