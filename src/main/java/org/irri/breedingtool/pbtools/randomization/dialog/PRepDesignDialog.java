package org.irri.breedingtool.pbtools.randomization.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.irri.breedingtool.graphs.managers.GraphTableManager;
import org.irri.breedingtool.graphs.managers.RowEntityModel;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.StarRandomizationUtilities;

public class PRepDesignDialog extends Dialog {
	private Spinner txtTotalTrials;
	private Text txtFileName;
	private Button btnOk;
	private Spinner txtNumFactors;
	private Spinner txtFieldRows;
	private Combo cmbOrder;
	private Table table;
	private int maxLevel = 2000;
	private GraphTableManager tableManager;
	private TableColumn tblclmnReplicate;
	private TableColumn tblclmnNumberOfLevels;
	private TableColumn tblclmnGroup;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public PRepDesignDialog(Shell parentShell) {
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
		lblLbldialogtitle.setText("p-Rep Design");

		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group grpDesignParameters = new Group(container, SWT.NONE);
		grpDesignParameters.setLayout(new GridLayout(1, false));
		grpDesignParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Group grpFactorDefinition = new Group(grpDesignParameters, SWT.NONE);
		grpFactorDefinition.setLayout(new GridLayout(2, false));
		GridData gd_grpFactorDefinition = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpFactorDefinition.heightHint = 193;
		grpFactorDefinition.setLayoutData(gd_grpFactorDefinition);
		grpFactorDefinition.setText("Factor Definition:");

		Label lblNewLabel = new Label(grpFactorDefinition, SWT.NONE);
		lblNewLabel.setText("Number of Groups");

		txtNumFactors = new Spinner(grpFactorDefinition, SWT.BORDER);
		txtNumFactors.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_txtNumFactors = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtNumFactors.widthHint = 20;
		txtNumFactors.setLayoutData(gd_txtNumFactors);
		txtNumFactors.setMaximum(10);
		txtNumFactors.setMinimum(2);
		
		table = new Table(grpFactorDefinition, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_table.heightHint = 64;
		table.setLayoutData(gd_table);
		
		tblclmnGroup = new TableColumn(table, SWT.NONE);
		tblclmnGroup.setWidth(100);
		tblclmnGroup.setText("Group");
		
		tblclmnReplicate = new TableColumn(table, SWT.NONE);
		tblclmnReplicate.setWidth(100);
		tblclmnReplicate.setText("Replicate");
		
		tblclmnNumberOfLevels = new TableColumn(table, SWT.NONE);
		tblclmnNumberOfLevels.setWidth(110);
		tblclmnNumberOfLevels.setText("Number of Levels");
		ArrayList<Integer> tableHeaderIdentity = new ArrayList<Integer>();
		
		tableHeaderIdentity.add(GraphTableManager.ROW_TEXT);
		tableHeaderIdentity.add(GraphTableManager.ROW_TEXT);
		tableHeaderIdentity.add(GraphTableManager.ROW_TEXT);
		
		tableManager = new GraphTableManager(table, tableHeaderIdentity);

		tableManager.addItem(new Object[]{
				"Group1",
				"2",
				"2"
				
		});
		tableManager.addItem(new Object[]{
				"Group2",
				"1",
				"2"
				
		});
		
		txtNumFactors.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				createGroupsOnTable(((Spinner)e.getSource()).getSelection());
//				if(txtNumFactors.getSelection() < 2){
////					spinnerTableModel = new RowEntityModel(GraphTableManager.ROW_SPINNER, 2, 2, 2000);
//					maxLevel = 2000;
//				}
//				else if(txtNumFactors.getSelection() <= 10){
////					spinnerTableModel = new RowEntityModel(GraphTableManager.ROW_SPINNER, 2, 2, 500);
//					maxLevel = 500;
//				}
//				else{
//					 MessageDialog.openWarning(getShell(), "Validation Warning!", "The maximum allowable number of factors is 10.");
//					 txtNumFactors.setSelection(10);
//				}
//				
//				setTableRows(table,txtNumFactors.getSelection());

			}
		});

		Composite composite_1 = new Composite(grpDesignParameters, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
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
		GridData gd_grpFieldBookFilename = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		gd_grpFieldBookFilename.heightHint = 76;
		grpFieldBookFilename.setLayoutData(gd_grpFieldBookFilename);
		grpFieldBookFilename.setText("Field Book ");
		
		Label label_1 = new Label(grpFieldBookFilename, SWT.NONE);
		label_1.setText("Name");
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_1.widthHint = 40;
		label_1.setLayoutData(gd_label_1);

		txtFileName = new Text(grpFieldBookFilename, SWT.BORDER);
		txtFileName.setText("fieldbookPrep");
		GridData gd_txtFileName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
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
	
	protected void createGroupsOnTable(int i) {
		// TODO Auto-generated method stub
		
		if(tableManager.getTableRowsObject().size() > i){//delete last row
			tableManager.deleteRow(i);
		}
		else{//add row
			tableManager.addItem(new Object[]{
					"Group"+Integer.toString(i),
					"2",
					"2"
					
			});
		}
	}
	@Override
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.RETRY_ID) {
			tableManager.removeAll();
			txtNumFactors.setSelection(1);
			txtTotalTrials.setSelection(1);
			txtFieldRows.setSelection(1);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldbookRCBD");
		}
		super.buttonPressed(buttonId);
	}
	
	
	
	@Override
	protected void okPressed(){	

		if(txtFileName.getText().equals(""))  { 
			MessageDialog.openError(getShell(), "Validation Error", "Field Filename must not be empty."); 
			return ; 
		}

		ArrayList<String> groupNames = new ArrayList<String>();
		ArrayList<Integer> replicates = new ArrayList<Integer>();
		ArrayList<Integer> numLevels = new ArrayList<Integer>();
		long plot = 1;
		long perBlk = 1;

		for(String[] tableRow : tableManager.getDataToString()){
		
			boolean isValidFactorID = StarRandomizationUtilities.validateVariableText(tableRow[1]);

			if(!isValidFactorID){
				MessageDialog.open(SWT.ERROR, this.getShell(), "Validation Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only. ", SWT.NONE);
				return;
			}

			
			if(tableRow[0].equals("") || tableRow[1].equals("")){
				MessageDialog.open(SWT.ERROR, this.getShell(), "Validation Error", "All name fields must not be empty", SWT.NONE);
				return;
			}
//			if(groupNames.contains(tableRow[0]) || groupNames.contains(tableRow[1])){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Validation Error", "Similar variable names detected. Make sure all the variables are unique.", SWT.NONE);
//				return ; 
//			}
//			if(replicates.contains(tableRow[0]) || replicates.contains(tableRow[1])){
//				MessageDialog.open(SWT.ERROR, this.getShell(), "Validation Error", "Similar variable names detected. Make sure all the variables are unique.", SWT.NONE);
//				return ; 
//			}
			if(tableRow[1].length()>4){
				MessageDialog.open(SWT.ERROR, this.getShell(), "Validation Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
				return;	
			}
			groupNames.add(tableRow[0]);
			replicates.add(Integer.parseInt(tableRow[1]));
			numLevels.add(Integer.parseInt(tableRow[2]));
		}
		
		plot = getNumOfPlots(numLevels, replicates);
		if ((plot  % txtFieldRows.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Validation Error", "Number of plots should be divisible by the Number of field rows."); 
			return ;
		}
		
		btnOk.setEnabled(false);	
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		
		String outputFile = StarRandomizationUtilities.createOutputFolder("PRep");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";
		
		ProjectExplorerView.rJavaManager.getPbToolRandomizationManager().doDesignPRep(outputFileTxt.replace(File.separator, "/"), outputFileCsv.replace(File.separator, "/"), groupNames.toArray(new String[groupNames.size()]), numLevels.toArray(new Integer[groupNames.size()]), replicates.toArray(new Integer[replicates.size()]), "EntryNo", txtTotalTrials.getSelection(), txtFieldRows.getSelection(), fieldOrder, null, null);
		
				
		rInfo.close();
		this.getShell().setMinimized(true);
		StarRandomizationUtilities.openAndRefreshFileFolder(outputFile  + outputFileCsv + ".csv");
		btnOk.setEnabled(true);
	}
	private long getNumOfPlots(ArrayList<Integer> numLevels,
			ArrayList<Integer> replicates) {
		// TODO Auto-generated method stub
		int numPlot=0;
		for(int i=0; i<numLevels.size(); i++){
			numPlot=numPlot+(numLevels.get(i)*replicates.get(i));
		}
		
		return numPlot;
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
		return new Point(384, 534);
	}
	private void setTableRows(Table dialogTable,int levels){
		
		for(int i = 0; i < table.getItemCount(); i++){
			Spinner spinner = (Spinner) tableManager.getRowObjects(i)[2];
			spinner.setMaximum(99999);
		}
		
		int tableCount = tableManager.getTableRowsObject().size();
		if(levels > tableCount){
			
			for(int i = tableCount; i < levels; i++){
			tableManager.addItem(new Object[]{
					"Group" + (char) (65 + i),
					String.valueOf((char) (65 + i)),
					
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
