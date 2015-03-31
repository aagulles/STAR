package org.irri.breedingtool.star.design.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.StarRandomizationUtilities;

public class IncompleteBlockBalancedIncompleteBlockDesignDialog extends Dialog {

	private Spinner txtTotalTreatments;
	private Spinner txtTotalBlockSize;
	private Spinner txtTotalTrials;
	private Text txtFileName;
	private Button btnOk;
	private Spinner txtFieldRows;
	private Spinner txtRowsPerBlk;
	private Combo cmbOrder;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public IncompleteBlockBalancedIncompleteBlockDesignDialog(Shell parentShell) {
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
		lblFactorialDesign.setText("Balanced Incomplete Block Design");

		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
				Composite composite_1 = new Composite(container, SWT.NONE);
				composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
				composite_1.setLayout(new GridLayout(3, false));
				
						Label lblNumberOfReplicated = new Label(composite_1, SWT.NONE);
						lblNumberOfReplicated.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						lblNumberOfReplicated.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						lblNumberOfReplicated.setText("Number of Treatments");
						
						Label lblNewLabel = new Label(composite_1, SWT.NONE);
						GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
						gd_lblNewLabel.widthHint = 80;
						lblNewLabel.setLayoutData(gd_lblNewLabel);
						
								txtTotalTreatments = new Spinner(composite_1, SWT.BORDER);
								GridData gd_txtTotalTreatments = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
								gd_txtTotalTreatments.widthHint = 20;
								txtTotalTreatments.setLayoutData(gd_txtTotalTreatments);
								txtTotalTreatments.setMaximum(100000);
								txtTotalTreatments.setSelection(3);
								
										Label lblNumberOfReplicates = new Label(composite_1, SWT.NONE);
										lblNumberOfReplicates.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
										lblNumberOfReplicates.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
										lblNumberOfReplicates.setText("Plots per Block (block size)");
										new Label(composite_1, SWT.NONE);
										
												txtTotalBlockSize = new Spinner(composite_1, SWT.BORDER);
												GridData gd_txtTotalBlockSize = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
												gd_txtTotalBlockSize.widthHint = 20;
												txtTotalBlockSize.setLayoutData(gd_txtTotalBlockSize);
												txtTotalBlockSize.setMaximum(100000);
												txtTotalBlockSize.setSelection(2);
												
												Label lblNumberOfRows = new Label(composite_1, SWT.NONE);
												lblNumberOfRows.setText("Number of Rows Per Block");
												lblNumberOfRows.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
												new Label(composite_1, SWT.NONE);
												
												txtRowsPerBlk = new Spinner(composite_1, SWT.BORDER);
												txtRowsPerBlk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
												txtRowsPerBlk.setMaximum(100000);
												txtRowsPerBlk.setMinimum(1);
												
												Label lblNumberOfField = new Label(composite_1, SWT.NONE);
												lblNumberOfField.setText("Number of Field Rows");
												lblNumberOfField.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
												new Label(composite_1, SWT.NONE);
												
												txtFieldRows = new Spinner(composite_1, SWT.BORDER);
												GridData gd_txtFieldRows = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
												gd_txtFieldRows.widthHint = 20;
												txtFieldRows.setLayoutData(gd_txtFieldRows);
												txtFieldRows.setMaximum(100000);
												txtFieldRows.setMinimum(1);
												
														Label lblNumberOfTrials = new Label(composite_1, SWT.NONE);
														lblNumberOfTrials.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
														lblNumberOfTrials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
														lblNumberOfTrials.setText("Number of Trials");
														new Label(composite_1, SWT.NONE);
														
																txtTotalTrials = new Spinner(composite_1, SWT.BORDER);
																GridData gd_txtTotalTrials = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
																gd_txtTotalTrials.widthHint = 20;
																txtTotalTrials.setLayoutData(gd_txtTotalTrials);
																txtTotalTrials.setMaximum(100000);
																txtTotalTrials.setMinimum(1);
		
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		group.setText("Field Book ");
		group.setLayout(new GridLayout(5, false));
		
		Label label_1 = new Label(group, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_label_1.widthHint = 30;
		label_1.setLayoutData(gd_label_1);
		label_1.setText("Name");
		
		txtFileName = new Text(group, SWT.BORDER);
		txtFileName.setText("fieldbookBIBD");
		GridData gd_txtFileName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFileName.widthHint = 100;
		txtFileName.setLayoutData(gd_txtFileName);
		
		Label label_2 = new Label(group, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.widthHint = 10;
		label_2.setLayoutData(gd_label_2);
		
		Label label_3 = new Label(group, SWT.NONE);
		GridData gd_label_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_label_3.widthHint = 30;
		label_3.setLayoutData(gd_label_3);
		label_3.setText("Order");
		
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
			txtTotalTreatments.setSelection(3);
			txtTotalBlockSize.setSelection(2);
			txtTotalTrials.setSelection(1);
			txtFieldRows.setSelection(1);
			txtRowsPerBlk.setSelection(1);
			txtFileName.setText("fieldbookBIBD");
		}
		super.buttonPressed(buttonId);
	}
	
	
	@Override
	protected void okPressed(){  
		
		if(txtTotalTreatments.getSelection() < 3){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of treatments is equal to 3.");
			txtTotalTreatments.setSelection(3);
			return;
		}
		if(txtTotalBlockSize.getSelection()<2){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of plots per block is equal to 2."); 
			txtTotalBlockSize.setSelection(2);
			return;
		}
		
		if(txtFileName.getText().equals(""))  { 
			MessageDialog.openError(getShell(), "Error", "Field Filename must not be empty.");
			return ; 
		} 
		if(txtTotalTreatments.getSelection() <= txtTotalBlockSize.getSelection()){
			MessageDialog.openError(getShell(), "Error", "Number of treatments must be greater than Total Block Size");
			return ; 
		}
		
	    int blks = fact(txtTotalTreatments.getSelection())/(txtTotalBlockSize.getSelection()*(fact(txtTotalTreatments.getSelection()-txtTotalBlockSize.getSelection())));
		
	    if (((txtTotalBlockSize.getSelection()*blks) % txtFieldRows.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Error", "Number of field rows must divide the total number of plots."); 
			return ;
		}
		
		if ((txtTotalBlockSize.getSelection() % txtRowsPerBlk.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Error", "Number of plots per block should be divisible by the number of rows per block."); 
			return ;
		}
			
		if(txtFieldRows.getSelection() < txtRowsPerBlk.getSelection()){
			MessageDialog.openError(getShell(), "Error", "Number of field rows must be greater than the number of rows per block.");
			return ; 
		}
		
		if ((txtFieldRows.getSelection() % txtRowsPerBlk.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Error", "Number of field rows should be divisible by the number of rows per block."); 
			return ;
		}
		
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		btnOk.setEnabled(false);
		
		String outputFile = StarRandomizationUtilities.createOutputFolder("BalancedIBD");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";
		
		ProjectExplorerView.rJavaManager.getSTARDesignManager().doDesignBIBD(
				(outputFileTxt.replace(File.separator, "/")),
				(outputFileCsv.replace(File.separator, "/")),
				txtTotalTreatments.getSelection(), 
				txtTotalBlockSize.getSelection(),
				txtTotalTrials.getSelection(),
				txtFieldRows.getSelection(),
				txtRowsPerBlk.getSelection(),
				fieldOrder);
		
		StarRandomizationUtilities.testVarArgs(
				(outputFileTxt.replace(File.separator, "/")),
				(outputFileCsv.replace(File.separator, "/")),
				txtTotalTreatments.getSelection(), 
				txtTotalBlockSize.getSelection(),
				txtTotalTrials.getSelection(),
				txtFieldRows.getSelection(),
				txtRowsPerBlk.getSelection(),
				fieldOrder);
		
		rInfo.close(); this.getShell().setMinimized(true);
		StarRandomizationUtilities.openAndRefreshFileFolder(outputFile  + outputFileCsv + ".csv");
		btnOk.setEnabled(true);
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	
	int fact(int n)
	    {
	        int result;

	       if(n==1)
	         return 1;

	       result = fact(n-1) * n;
	       return result;
	    }
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
		return new Point(368, 330);
	}

}
