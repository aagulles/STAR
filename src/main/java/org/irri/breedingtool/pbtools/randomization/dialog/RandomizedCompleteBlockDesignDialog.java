package org.irri.breedingtool.pbtools.randomization.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.graphs.managers.GraphTableManager;
import org.irri.breedingtool.graphs.managers.RowEntityModel;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.StarRandomizationUtilities;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class RandomizedCompleteBlockDesignDialog extends Dialog {
	private Spinner txtTotalBlocks;
	private Spinner txtTotalTrials;
	private Text txtFileName;
	private Button btnOk;
	private Spinner txtRowsPerBlk;
	private Spinner txtFieldRows;
	private Combo cmbOrder;
	private int maxLevel = 2000;
	private GraphTableManager tableManager;
	private RowEntityModel spinnerTableModel;
	private Text txtEntryno;
	private Combo table;
	private Spinner spnrNumTreatmentLevels;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public RandomizedCompleteBlockDesignDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Randomization and Layout");
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		Label lblLbldialogtitle = new Label(container, SWT.NONE);
		lblLbldialogtitle.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lblLbldialogtitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblLbldialogtitle.setText("Randomized Complete Block Design");

		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group grpDesignParameters = new Group(container, SWT.NONE);
		grpDesignParameters.setLayout(new GridLayout(1, false));
		grpDesignParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		ArrayList<Integer> tableHeaderIdentity = new ArrayList<Integer>();
		
		tableHeaderIdentity.add(GraphTableManager.ROW_TEXT);
		tableHeaderIdentity.add(GraphTableManager.ROW_TEXT);
		tableHeaderIdentity.add(GraphTableManager.ROW_SPINNER);
		
		
		spinnerTableModel = new RowEntityModel(GraphTableManager.ROW_SPINNER, 2, 2, 99999, new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				 if(((Spinner) e.getSource()).getSelection() > maxLevel){
					 MessageDialog.openError(getShell(), "Error", "The maximum allowable number of levels is " + maxLevel + " ");
					 ((Spinner) e.getSource()).setSelection(maxLevel);
				 }
			}});
		
		tableManager.addItem(new Object[]{
				"FactorA",
				"A",
				spinnerTableModel
				
		});

		Composite composite_1 = new Composite(grpDesignParameters, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Label lblTreatmentName = new Label(composite_1, SWT.NONE);
		lblTreatmentName.setText("Treatment Name");
		
		txtEntryno = new Text(composite_1, SWT.BORDER);
		txtEntryno.setText("EntryNo");
		txtEntryno.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNumberOfTreatment = new Label(composite_1, SWT.NONE);
		lblNumberOfTreatment.setText("Number of Treatment Levels");
		
		spnrNumTreatmentLevels = new Spinner(composite_1, SWT.BORDER);
		spnrNumTreatmentLevels.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		spnrNumTreatmentLevels.setMaximum(500);
		spnrNumTreatmentLevels.setSelection(2);

		Label lblNumberOfReplicates = new Label(composite_1, SWT.NONE);
		lblNumberOfReplicates.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNumberOfReplicates.setText("Number of Blocks");

		txtTotalBlocks = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtTotalBlocks = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtTotalBlocks.widthHint = 20;
		txtTotalBlocks.setLayoutData(gd_txtTotalBlocks);
		txtTotalBlocks.setMaximum(500);
		txtTotalBlocks.setSelection(2);
		
		Label lblNumberOfRows = new Label(composite_1, SWT.NONE);
		lblNumberOfRows.setText("Number of Rows Per Block");
		
		txtRowsPerBlk = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtRowsPerBlk = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtRowsPerBlk.widthHint = 20;
		txtRowsPerBlk.setLayoutData(gd_txtRowsPerBlk);
		txtRowsPerBlk.setMaximum(500);
		txtRowsPerBlk.setMinimum(1);
		
		Label lblNumberOfField = new Label(composite_1, SWT.NONE);
		lblNumberOfField.setText("Number of Field Rows");
		
		txtFieldRows = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtFieldRows = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtFieldRows.widthHint = 20;
		txtFieldRows.setLayoutData(gd_txtFieldRows);
		txtFieldRows.setMaximum(500);
		txtFieldRows.setMinimum(1);

		Label lblNumberOfTrials = new Label(composite_1, SWT.NONE);
		lblNumberOfTrials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNumberOfTrials.setText("Number of Trials");

		txtTotalTrials = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtTotalTrials = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtTotalTrials.widthHint = 20;
		txtTotalTrials.setLayoutData(gd_txtTotalTrials);
		txtTotalTrials.setMaximum(100);
		txtTotalTrials.setMinimum(1);

		Group grpFieldBookFilename = new Group(container, SWT.NONE);
		grpFieldBookFilename.setLayout(new GridLayout(2, false));
		GridData gd_grpFieldBookFilename = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_grpFieldBookFilename.heightHint = 119;
		grpFieldBookFilename.setLayoutData(gd_grpFieldBookFilename);
		grpFieldBookFilename.setText("Field Book ");
		
		Label label_1 = new Label(grpFieldBookFilename, SWT.NONE);
		label_1.setText("Name");
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_1.widthHint = 40;
		label_1.setLayoutData(gd_label_1);

		txtFileName = new Text(grpFieldBookFilename, SWT.BORDER);
		txtFileName.setText("fieldbookRCBD");
		GridData gd_txtFileName = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtFileName.widthHint = 100;
		txtFileName.setLayoutData(gd_txtFileName);
		
		Label label_2 = new Label(grpFieldBookFilename, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_2.widthHint = 40;
		label_2.setLayoutData(gd_label_2);
		label_2.setText("Order");
		
		cmbOrder = new Combo(grpFieldBookFilename, SWT.READ_ONLY);
		cmbOrder.setItems(new String[] {"Plot Order", "Serpentine"});
		GridData gd_cmbOrder = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_cmbOrder.widthHint = 70;
		cmbOrder.setLayoutData(gd_cmbOrder);
		cmbOrder.select(0);

		return container;
	}
	
	@Override
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.RETRY_ID) {
			tableManager.removeAll();
			spinnerTableModel = new RowEntityModel(GraphTableManager.ROW_SPINNER, 2, 2, 99999, new ModifyListener(){

				@Override
				public void modifyText(ModifyEvent e) {
					 if(((Spinner) e.getSource()).getSelection() > maxLevel){
						 MessageDialog.openError(getShell(), "Error", "The maximum allowable number of levels is " + maxLevel + " ");
						 ((Spinner) e.getSource()).setSelection(maxLevel);
					 }
				}});
			txtTotalBlocks.setSelection(2);
			txtTotalTrials.setSelection(1);
			txtRowsPerBlk.setSelection(1);
			txtFieldRows.setSelection(1);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldbookRCBD");
		}
		super.buttonPressed(buttonId);
	}
	
	@Override
	protected void okPressed(){	

		if(txtTotalBlocks.getSelection()<2){//
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of blocks is equal to 2."); 
			txtTotalBlocks.setSelection(2);
			return;
		}
		
		if(txtFileName.getText().equals(""))  { 
			MessageDialog.openError(getShell(), "Error", "Field Filename must not be empty."); 
			return ; 
		}

//		for(String[] tableRow : tableManager.getDataToString()){
//		
//			boolean isValidFactorID = StarRandomizationUtilities.validateVariableText(tableRow[1]);
//			
//			if(tableRow[0].contains(" ")){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor Name must not contain space. ", SWT.NONE);
//				return;
//			}
//			if(tableRow[1].contains(" ")){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must not contain space. ", SWT.NONE);
//				return;
//			}
//			if(!isValidFactorID){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only. ", SWT.NONE);
//				return;
//			}
//			if(tableRow[0].equals("") || tableRow[1].equals("")){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "All name fields must not be empty", SWT.NONE);
//				return;
//			}
//			if(tableNames.contains(tableRow[0]) || tableNames.contains(tableRow[1])){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Similar variable names detected. Make sure all the variables are unique.", SWT.NONE);
//				return ; 
//			}
//			if(tableID.contains(tableRow[0]) || tableID.contains(tableRow[1])){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Similar variable names detected. Make sure all the variables are unique.", SWT.NONE);
//				return ; 
//			}
//			if(tableRow[1].length()>4){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
//				return;	
//			}
//			tableNames.add(tableRow[0]);
//			tableID.add(tableRow[1]);
//			tableLevels.add(Integer.parseInt(tableRow[2]));
//			
//			plot = (plot * Long.valueOf(tableRow[2]));
//			perBlk = perBlk * Long.valueOf(tableRow[2]);
//		}
		
		int plot = txtTotalBlocks.getSelection()* spnrNumTreatmentLevels.getSelection();
		if ((plot  % txtFieldRows.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Error", "The total number of plots (" + plot + ") must be divisible by the number of field rows."); 
			return ;
		}
		
		if ((spnrNumTreatmentLevels.getSelection() % txtRowsPerBlk.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Error", "The total number of treatment levels (" + spnrNumTreatmentLevels.getSelection() + ") must be divisible by the number of rows per block."); 
			return ;
		}
		
		if(txtFieldRows.getSelection() < txtRowsPerBlk.getSelection()){
			MessageDialog.openError(getShell(), "Error", "The number of field rows must be equal or greater than the number of rows per block."); 
			return ;
		}
		
		if((txtFieldRows.getSelection() % txtRowsPerBlk.getSelection()) !=0){
			MessageDialog.openError(getShell(), "Error", "The number of field rows must be divisible by the number of rows per block."); 
			return ;
		}
		
		btnOk.setEnabled(false);	
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		
		String outputFile = StarRandomizationUtilities.createOutputFolder("RCBD");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";

		ProjectExplorerView.rJavaManager.getSTARDesignManager().doDesignRCBD(
				outputFileTxt.replace(File.separator, "/"), 
				outputFileCsv.replace(File.separator, "/"), 
				txtTotalBlocks.getSelection(),
				txtTotalTrials.getSelection(),
				txtFieldRows.getSelection(),
				txtRowsPerBlk.getSelection(),
				fieldOrder);
		
		rInfo.close();
		this.getShell().setMinimized(true);
		StarRandomizationUtilities.openAndRefreshFileFolder(outputFile  + outputFileCsv + ".csv");
		btnOk.setEnabled(true);
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
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(337, 492);
	}
	private void setTableRows(Table dialogTable,int levels){
		
		for(int i = 0; i < table.getItemCount(); i++){
			Spinner spinner = (Spinner) tableManager.getRowObjects(i)[2];
			spinner.setMaximum(99999);
		}
		
		spinnerTableModel = new RowEntityModel(GraphTableManager.ROW_SPINNER, 2, 2, 99999, new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				System.out.println(((Spinner) e.getSource()).getSelection() + " SELECTION SPINNER");
				 if(((Spinner) e.getSource()).getSelection() > maxLevel){
					 MessageDialog.openError(getShell(), "Error", "The maximum allowable number of levels is " + maxLevel + " ");
					 ((Spinner) e.getSource()).setSelection(maxLevel);
				 }
			}});
		
		int tableCount = tableManager.getTableRowsObject().size();
		if(levels > tableCount){
			
			for(int i = tableCount; i < levels; i++){
			tableManager.addItem(new Object[]{
					"Factor" + (char) (65 + i),
					String.valueOf((char) (65 + i)),
					spinnerTableModel
					
			});
			}
		}
		else if (levels < tableCount){
			for(int i = tableCount ; i > levels; i--){
				tableManager.deleteRow(i - 1);
			}
		}
		
	}
}