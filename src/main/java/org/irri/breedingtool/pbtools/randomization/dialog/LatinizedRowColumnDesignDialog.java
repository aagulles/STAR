
package org.irri.breedingtool.pbtools.randomization.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.StarRandomizationUtilities;
import org.eclipse.swt.widgets.Combo;

public class LatinizedRowColumnDesignDialog extends Dialog {

	private Spinner txtTotalTreatments;
	private Spinner txtTotalReplicates;
	private Spinner txtTotalTrials;
	private Text txtFileName;
	private Button btnOk;
	private Spinner txtFieldRows;
	private Combo cmbOrder;
	private Spinner txtRowsEachRep;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LatinizedRowColumnDesignDialog(Shell parentShell) {
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
		Label lblFactorialDesign = new Label(container, SWT.NONE);
		lblFactorialDesign.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblFactorialDesign.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lblFactorialDesign.setText("Latinized Row-Column Design");

		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
				Composite composite_1 = new Composite(container, SWT.NONE);
				composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
				composite_1.setLayout(new GridLayout(3, false));
				
						Label lblNumberOfReplicated = new Label(composite_1, SWT.NONE);
						lblNumberOfReplicated.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						lblNumberOfReplicated.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						lblNumberOfReplicated.setText("Number of Treatments");
								
								Label lblNewLabel = new Label(composite_1, SWT.NONE);
								GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
								gd_lblNewLabel.widthHint = 60;
								lblNewLabel.setLayoutData(gd_lblNewLabel);
						
								txtTotalTreatments = new Spinner(composite_1, SWT.BORDER);
								txtTotalTreatments.setMaximum(10000);
								GridData gd_txtTotalTreatments = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
								gd_txtTotalTreatments.widthHint = 20;
								txtTotalTreatments.setLayoutData(gd_txtTotalTreatments);
//								txtTotalTreatments.setMaximum(144);
//								txtTotalTreatments.setMinimum(9);
								txtTotalTreatments.setSelection(9);
								
										Label lblNumberOfReplicates = new Label(composite_1, SWT.NONE);
										lblNumberOfReplicates.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
										lblNumberOfReplicates.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
										lblNumberOfReplicates.setText("Number of Replicates");
												new Label(composite_1, SWT.NONE);
										
												txtTotalReplicates = new Spinner(composite_1, SWT.BORDER);
												GridData gd_txtTotalReplicates = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
												gd_txtTotalReplicates.widthHint = 20;
												txtTotalReplicates.setLayoutData(gd_txtTotalReplicates);
												txtTotalReplicates.setSelection(2);
														
														Label lblNumberOfRows = new Label(composite_1, SWT.NONE);
														lblNumberOfRows.setText("Number of Rows in each Replicate");
														lblNumberOfRows.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
														new Label(composite_1, SWT.NONE);
														
														txtRowsEachRep = new Spinner(composite_1, SWT.BORDER);
														txtRowsEachRep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
														txtRowsEachRep.setMinimum(1);
														txtRowsEachRep.setSelection(3);
														
														Label lblNumberOfField = new Label(composite_1, SWT.NONE);
														lblNumberOfField.setText("Number of Field Rows");
														lblNumberOfField.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
														new Label(composite_1, SWT.NONE);
														
														txtFieldRows = new Spinner(composite_1, SWT.BORDER);
														GridData gd_txtFieldRows = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
														gd_txtFieldRows.widthHint = 20;
														txtFieldRows.setLayoutData(gd_txtFieldRows);
														txtFieldRows.setMinimum(1);
														txtFieldRows.setSelection(3);
														txtFieldRows.setMaximum(500);
												
														Label lblNumberOfTrials = new Label(composite_1, SWT.NONE);
														lblNumberOfTrials.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
														lblNumberOfTrials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
														lblNumberOfTrials.setText("Number of Trials");
																new Label(composite_1, SWT.NONE);
														
																txtTotalTrials = new Spinner(composite_1, SWT.BORDER);
																GridData gd_txtTotalTrials = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
																gd_txtTotalTrials.widthHint = 20;
																txtTotalTrials.setLayoutData(gd_txtTotalTrials);
																txtTotalTrials.setMaximum(1000);
																txtTotalTrials.setMinimum(1);
		
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		group.setText("Field Book ");
		group.setLayout(new GridLayout(4, false));
		
		Label label_2 = new Label(group, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_2.widthHint = 40;
		label_2.setLayoutData(gd_label_2);
		label_2.setText("Name");
		
		txtFileName = new Text(group, SWT.BORDER);
		txtFileName.setText("fieldbookLatinizedRowCol");
		GridData gd_txtFileName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFileName.widthHint = 138;
		txtFileName.setLayoutData(gd_txtFileName);
		
		Label label_4 = new Label(group, SWT.NONE);
		GridData gd_label_4 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_label_4.widthHint = 40;
		label_4.setLayoutData(gd_label_4);
		label_4.setText("Order");
		
		cmbOrder = new Combo(group, SWT.READ_ONLY);
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
			txtTotalTreatments.setSelection(9);
			txtTotalReplicates.setSelection(2); 
			txtFieldRows.setSelection(3);
			txtRowsEachRep.setSelection(3); 
			txtTotalTrials.setSelection(1);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldbookRowCol");
		}
		super.buttonPressed(buttonId);
	}
	
	
	@Override
	protected void okPressed(){ 

		if(txtTotalTreatments.getSelection()<9){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of treatments is equal to 9."); 
			txtTotalTreatments.setSelection(9);
			return;
		}
		if(txtTotalReplicates.getSelection()<2){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of replicates is equal to 2."); 
			txtTotalReplicates.setSelection(2);
			return;
		}
//		if(txtRowsEachRep.getSelection()<2){
//			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of rows in each replicate is equal to 2."); 
//			txtRowsEachRep.setSelection(2);
//			return;
//		}
		if(txtFieldRows.getSelection()<2){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of field rows is equal to 2."); 
			txtFieldRows.setSelection(2);
			return;
		}
		if(txtRowsEachRep.getSelection() >= txtTotalTreatments.getSelection()){
			MessageDialog.openError(getShell(), "Error", "The maximum value of the number of rows in each replicate should be less than the total number of treatments."); 
			txtRowsEachRep.getSelection();
			return;
		}
		
		if(txtFieldRows.getSelection() > (txtRowsEachRep.getSelection()*txtTotalReplicates.getSelection())){
			MessageDialog.openError(getShell(), "Error", "The maximum value of the number of field rows should be less than or equal to the product of rows in each replicate and total number of replicates.");
			txtFieldRows.setSelection(txtRowsEachRep.getSelection()*txtTotalReplicates.getSelection());
			return;
		}
		
		if(txtFieldRows.getSelection() != txtTotalTreatments.getSelection()){
			if(txtFieldRows.getSelection() != txtRowsEachRep.getSelection()){
				if(txtFieldRows.getSelection() % (txtRowsEachRep.getSelection()*txtTotalReplicates.getSelection()) != 0){
					MessageDialog.openError(getShell(), "Error", "The number of field rows should be equal to the number of treatments or equal to the number of rows per replicate or should be divisible by the product of rows in each replicate and total number of replicates.");
					return;
				} else {
					if(txtFieldRows.getSelection() != (txtRowsEachRep.getSelection()*txtTotalReplicates.getSelection())){
						MessageDialog.openError(getShell(), "Error", "The number of field rows should be equal to the number of treatments or equal to the number of rows per replicate or equal to the product of rows in each replicate and total number of replicates.");
						return;
					}
				}
			}
		} else {
			if(txtFieldRows.getSelection() != (txtRowsEachRep.getSelection()*txtTotalReplicates.getSelection())){
				MessageDialog.openError(getShell(), "Error", "The number of field rows should be equal to the number of treatments or equal to the number of rows per replicate or equal to the product of rows in each replicate and total number of replicates.");
				return;
			}
		}
		
		
		if(txtFileName.getText().equals(""))  { 
			MessageDialog.openError(getShell(), "Error", "Field Filename must not be empty."); 
			return ; 
		}
		 
		if((txtTotalTreatments.getSelection() * txtTotalReplicates.getSelection()) > 1500){
			MessageDialog.openError(getShell(), "Error", "The maximum total number of experimental units is 1500."); 
			return ; 
		}
		
		if((txtTotalTreatments.getSelection() % txtRowsEachRep.getSelection() != 0) || txtRowsEachRep.getSelection()<2){
			MessageDialog.openError(getShell(), "Error", "The number of rows in each replicate should be a factor of the number of treatments and should be greater than 1."); 
			return ; 
		}
//		if((txtFieldRows.getSelection() % txtRowsEachRep.getSelection() != 0) || (txtFieldRows.getSelection() != txtRowsEachRep.getSelection())){
//			MessageDialog.openError(getShell(), "Error", "The number of field rows should be divisible by or equal to the number of rows in each replicate."); 
//			return ; 
//		}
		if((txtRowsEachRep.getSelection() == 1) || (txtRowsEachRep.getSelection() == txtTotalTreatments.getSelection()) ){
			MessageDialog.openError(getShell(), "Error", "The number of rows should not be equal to one or the number of treatments."); 
			return ;
		} 
//		if((txtRowsEachRep.getSelection() * txtTotalReplicates.getSelection()) > txtFieldRows.getSelection()){
//			MessageDialog.openError(getShell(), "Error", "The maximum number of field rows should be equal to the product of the number of rows in each replicate and number of replicates."); 
//			return ;
//		}
		if(txtTotalReplicates.getSelection() % (txtFieldRows.getSelection()/txtRowsEachRep.getSelection()) != 0 ){
			MessageDialog.openError(getShell(), "Error", "The quotient of the number of field rows and number of rows in each replicate should be a factor of the number of the replicates."); 
			return ;
		}
		
		
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		btnOk.setEnabled(false);

		String outputFile = StarRandomizationUtilities.createOutputFolder("LatinizedRow-Column");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";
		
		StarRandomizationUtilities.testVarArgs(outputFileCsv.replace(File.separator, "/"),outputFileTxt.replace(File.separator, "/"), txtTotalTreatments.getSelection(),txtTotalReplicates.getSelection(), txtTotalTrials.getSelection());
		
		ProjectExplorerView.rJavaManager.getPbToolRandomizationManager().doDesignLatinizedRowColumn(
				outputFileTxt.replace(File.separator, "/"), 
				outputFileCsv.replace(File.separator, "/"),
				txtTotalTreatments.getSelection(),
				txtTotalReplicates.getSelection(), 
				txtTotalTrials.getSelection(),
				txtRowsEachRep.getSelection(),
				txtFieldRows.getSelection(),
				fieldOrder);
		
		rInfo.close(); this.getShell().setMinimized(true);
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
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(412, 390);
	}
	boolean isPerfectSquare(long n)
	{
		if (n < 0)
			return false;

		switch((int)(n & 0xF))
		{
		case 0: case 1: case 4: case 9:
			long tst = (long)Math.sqrt(n);
			return tst*tst == n;

		default:
			return false;
		}
	}
	
}
