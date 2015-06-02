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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.StarRandomizationUtilities;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class IncompleteBlockAlphaLatticeDesignDialog extends Dialog {

	private Spinner txtTotalTreatments;
	private Spinner txtTotalReplicates;
	private Spinner txtTotalBlockSize;
	private Spinner txtTotalTrials;
	private Text txtFileName;
	private Button btnOk;
	private Combo cmbOrder;
	private Spinner txtFieldRows;
	private Text txtBlksPerRep;
	private Spinner txtRowsEachRep;
	private Spinner txtRowsEachBlk;
	private Button btnLatinizedAlphaLattice;
	private Label lblNumberOfRows;
	private Label lblNumberOfRows_1;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public IncompleteBlockAlphaLatticeDesignDialog(Shell parentShell) {
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
		lblFactorialDesign.setText("Alpha Lattice Design");

		Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
				Composite composite_1 = new Composite(container, SWT.BORDER);
				composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
				composite_1.setLayout(new GridLayout(3, false));
				
						Label lblNumberOfReplicated = new Label(composite_1, SWT.NONE);
						lblNumberOfReplicated.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						lblNumberOfReplicated.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
						lblNumberOfReplicated.setText("Number of Treatments");
								
								Label lblNewLabel = new Label(composite_1, SWT.NONE);
								GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
								gd_lblNewLabel.widthHint = 60;
								lblNewLabel.setLayoutData(gd_lblNewLabel);
						
								txtTotalTreatments = new Spinner(composite_1, SWT.BORDER);
								
								GridData gd_txtTotalTreatments = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
								gd_txtTotalTreatments.widthHint = 20;
								txtTotalTreatments.setLayoutData(gd_txtTotalTreatments);
								txtTotalTreatments.setMaximum(750);
								txtTotalTreatments.setMinimum(9);
								txtTotalTreatments.setSelection(9);
								
										Label lblNumberOfReplicates = new Label(composite_1, SWT.NONE);
										lblNumberOfReplicates.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
										lblNumberOfReplicates.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
										lblNumberOfReplicates.setText("Number of Replicates");
												new Label(composite_1, SWT.NONE);
										
												txtTotalReplicates = new Spinner(composite_1, SWT.BORDER);
												GridData gd_txtTotalReplicates = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
												gd_txtTotalReplicates.widthHint = 20;
												txtTotalReplicates.setLayoutData(gd_txtTotalReplicates);
												txtTotalReplicates.setMaximum(10000);
												txtTotalReplicates.setSelection(2);
												
														Label lblNumberOfPlots = new Label(composite_1, SWT.NONE);
														lblNumberOfPlots.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
														lblNumberOfPlots.setText("Number of Plots per Block ");
														lblNumberOfPlots.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
																new Label(composite_1, SWT.NONE);
														
																txtTotalBlockSize = new Spinner(composite_1, SWT.BORDER);
																txtTotalBlockSize.setMaximum(1000);
																
																GridData gd_txtTotalBlockSize = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
																gd_txtTotalBlockSize.widthHint = 20;
																txtTotalBlockSize.setLayoutData(gd_txtTotalBlockSize);
																txtTotalBlockSize.setSelection(3);
																
																Label lblNumberOfBlocks = new Label(composite_1, SWT.NONE);
																lblNumberOfBlocks.setText("Number of Blocks per Replicate");
																lblNumberOfBlocks.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
																new Label(composite_1, SWT.NONE);
																
																txtBlksPerRep = new Text(composite_1, SWT.BORDER);
																txtBlksPerRep.setText("3");
																txtBlksPerRep.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
																txtBlksPerRep.setEditable(false);
																txtBlksPerRep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
																
																lblNumberOfRows = new Label(composite_1, SWT.NONE);
																lblNumberOfRows.setText("Number of Rows in Each Block per Replicate");
																lblNumberOfRows.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
																new Label(composite_1, SWT.NONE);
																
																txtRowsEachBlk = new Spinner(composite_1, SWT.BORDER);
																txtRowsEachBlk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
																txtRowsEachBlk.setMaximum(1000);
															
																txtRowsEachBlk.setMinimum(1);
																txtRowsEachBlk.setSelection(3);
																
																lblNumberOfRows_1 = new Label(composite_1, SWT.NONE);
																lblNumberOfRows_1.setText("Number of Rows in Each Replicate");
																lblNumberOfRows_1.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
																new Label(composite_1, SWT.NONE);
																
																txtRowsEachRep = new Spinner(composite_1, SWT.BORDER);
																txtRowsEachRep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
																txtRowsEachRep.setMaximum(1000);
																
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
																txtFieldRows.setMaximum(1500);
																
																txtFieldRows.setMinimum(1);
																txtFieldRows.setSelection(3);
																
																		Label lblNumberOfTrials = new Label(composite_1, SWT.NONE);
																		lblNumberOfTrials.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
																		lblNumberOfTrials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
																		lblNumberOfTrials.setText("Number of Trials");
																				new Label(composite_1, SWT.NONE);
																		
																				txtTotalTrials = new Spinner(composite_1, SWT.BORDER);
																				GridData gd_txtTotalTrials = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
																				gd_txtTotalTrials.widthHint = 20;
																				txtTotalTrials.setLayoutData(gd_txtTotalTrials);
																				txtTotalTrials.setMaximum(100);
																				txtTotalTrials.setMinimum(1);
																				
																				txtTotalTreatments.addModifyListener(new ModifyListener() {
																					public void modifyText(ModifyEvent e) {
																						setPlotValue();
																					}
																				});
																				txtTotalBlockSize.addModifyListener(new ModifyListener() {
																					public void modifyText(ModifyEvent e) {
																						setPlotValue();
																					}
																				});
		
		btnLatinizedAlphaLattice = new Button(container, SWT.CHECK);
		btnLatinizedAlphaLattice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnLatinizedAlphaLattice.getSelection()) {
					lblNumberOfRows.setEnabled(false);
					txtRowsEachBlk.setEnabled(false);
					lblNumberOfRows_1.setEnabled(false);
					txtRowsEachRep.setEnabled(false);
				} else {
					lblNumberOfRows.setEnabled(true);
					txtRowsEachBlk.setEnabled(true);
					lblNumberOfRows_1.setEnabled(true);
					txtRowsEachRep.setEnabled(true);
				}
			}
		});
		btnLatinizedAlphaLattice.setEnabled(true);
		btnLatinizedAlphaLattice.setSelection(false);
		btnLatinizedAlphaLattice.setText("Latinized Alpha Lattice");
		
		Label label_3 = new Label(container, SWT.NONE);
		
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		group.setText("Field Book ");
		group.setLayout(new GridLayout(4, false));
		
		Label label_2 = new Label(group, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_2.widthHint = 40;
		label_2.setLayoutData(gd_label_2);
		label_2.setText("Name");
		
		txtFileName = new Text(group, SWT.BORDER);
		txtFileName.setText("fieldbookAlpha");
		GridData gd_txtFileName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFileName.widthHint = 100;
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

	public void setPlotValue(){
		double i = (double) txtTotalTreatments.getSelection() / txtTotalBlockSize.getSelection();
		String value = Double.toString(i);
		txtBlksPerRep.setText(value);
	}
	
	
	@Override
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.RETRY_ID) {
			txtTotalTreatments.setSelection(9);
			txtTotalBlockSize.setSelection(3);
			txtTotalReplicates.setSelection(2); 
			txtBlksPerRep.setSelection(3);
			txtRowsEachBlk.setSelection(3);
			txtRowsEachRep.setSelection(3);
			txtFieldRows.setSelection(3);
			txtTotalTrials.setSelection(1);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldbookAlpha");
			btnLatinizedAlphaLattice.setSelection(false);
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
		if(txtTotalBlockSize.getSelection()<2){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of plots per block is equal to 2."); 
			txtTotalBlockSize.setSelection(3);
			return;
		}
		if(txtFileName.getText().equals(""))  { 
			MessageDialog.openError(getShell(), "Error", "Field Filename must not be empty."); 
			return ;
		} 
		
		if(txtTotalTreatments.getSelection() > 1500){
			MessageDialog.openError(getShell(), "Error", "The maximum total number of experimental units is 1500."); 
			return ; 
		}
		if(txtTotalTreatments.getSelection() % txtTotalBlockSize.getSelection() != 0){
			MessageDialog.openError(getShell(), "Error", "The number of treatments should be divisible by the number of plots per block."); 
			return ; 	
		}
		
		if (btnLatinizedAlphaLattice.getSelection()) {
			if(txtFieldRows.getSelection() > txtTotalTreatments.getSelection()){
				MessageDialog.openError(getShell(), "Error", "The number of field rows should not be greater than the number of treatments."); 
				return ; 	
			} 
			
			if(txtFieldRows.getSelection() != txtTotalTreatments.getSelection()){
				if(txtFieldRows.getSelection() != ((double) txtTotalTreatments.getSelection() / txtTotalBlockSize.getSelection())){
					if(txtFieldRows.getSelection() %  txtTotalBlockSize.getSelection() != 0 ){
						MessageDialog.openError(getShell(), "Error", "The number of field rows should be equal to the number of blocks per replicate or divisible by the number of plots per block."); 
						return ; 
						
					}else {
						if(txtFieldRows.getSelection() != (txtTotalReplicates.getSelection() * txtTotalBlockSize.getSelection())){
							MessageDialog.openError(getShell(), "Error", "The number of field rows should be equal to the product of the number of total replicates and the number of plots per block."); 
							return ; 
						}
					}
				}
			}else{
				if(txtFieldRows.getSelection() != (txtTotalReplicates.getSelection() * txtTotalBlockSize.getSelection())){
					MessageDialog.openError(getShell(), "Error", "The number of field rows should be equal to the product of the number of total replicates and the number of plots per block."); 
					return ; 
				}
			}

		} else {
			if(txtRowsEachRep.getSelection()< txtRowsEachBlk.getSelection()){
				MessageDialog.openError(getShell(), "Error", "The minimum value of the number of rows in each replicate is equal to the number of rows in each block."); 
				txtRowsEachRep.setSelection(txtRowsEachBlk.getSelection());
				return;
			}
			if(txtFieldRows.getSelection()< txtRowsEachRep.getSelection()){
				MessageDialog.openError(getShell(), "Error", "The minimum value of the field rows is equal to the number of rows in each replicate."); 
				txtFieldRows.setSelection(txtRowsEachRep.getSelection());
				return;
			}
			if(txtRowsEachBlk.getSelection()> txtTotalBlockSize.getSelection()){
				MessageDialog.openError(getShell(), "Error", "The maximum value of the number of rows in each block is equal to the number of plots per block."); 
				txtRowsEachBlk.setSelection(txtTotalBlockSize.getSelection());
				return;
			}
			if(txtRowsEachRep.getSelection()> txtTotalTreatments.getSelection()){
				MessageDialog.openError(getShell(), "Error", "The maximum value of the number of rows in each replicate is equal to the number of treatments."); 
				txtRowsEachRep.setSelection(txtTotalTreatments.getSelection());
				return;
			}
			if(txtFieldRows.getSelection()> (txtRowsEachRep.getSelection()* txtTotalReplicates.getSelection())){
				MessageDialog.openError(getShell(), "Error", "The maximum value of the field rows is equal to the product of number of rows in each replicate and total number of replicates."); 
				txtFieldRows.setSelection((txtRowsEachRep.getSelection()* txtTotalReplicates.getSelection()));
				return;
			}
			if(txtTotalBlockSize.getSelection() % txtRowsEachBlk.getSelection() != 0){
				MessageDialog.openError(getShell(), "Error", "The number of plots per block should be divisible by the number of rows in each block"); 
				return ; 
			}
			if(txtFieldRows.getSelection() % txtRowsEachRep.getSelection() != 0){
				MessageDialog.openError(getShell(), "Error", "The number of field rows should be divisible by the number of rows in each replicate."); 
				return ; 
			}
			if(txtTotalReplicates.getSelection() % (txtFieldRows.getSelection()/txtRowsEachRep.getSelection()) != 0 ){
				MessageDialog.openError(getShell(), "Error", "The quotient of the number of field rows and number of rows in each replicate should be a factor of the number of the replicates."); 
				return ;
			}
			if(txtTotalTreatments.getSelection() % txtRowsEachRep.getSelection() != 0){
				MessageDialog.openError(getShell(), "Error", "The number of treatment should be divisible by the number of rows in each replicate."); 
				return ;
			}
			if(txtRowsEachRep.getSelection() % txtRowsEachBlk.getSelection() != 0){
				MessageDialog.openError(getShell(), "Error", "The number of rows per replicate should be divisible by the number of rows per block."); 
				return ;
			}
			if((txtTotalTreatments.getSelection() / txtTotalBlockSize.getSelection()) % (txtRowsEachRep.getSelection()/txtRowsEachBlk.getSelection()) != 0){
				MessageDialog.openError(getShell(), "Error", "The quotient of the number of rows in each replicate and number of rows in each block should be a factor of the number of blocks per replicate."); 
				return ;
			}
		}
		
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		btnOk.setEnabled(false);
			
		String outputFile = StarRandomizationUtilities.createOutputFolder("AlphaLattice");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";
		
		if (btnLatinizedAlphaLattice.getSelection()) {
			ProjectExplorerView.rJavaManager.getPbToolRandomizationManager().doDesignLatinizedAlpha(
					outputFileTxt.replace(File.separator, "/"),
					outputFileCsv.replace(File.separator, "/"), 
					txtTotalTreatments.getSelection(), 
					txtTotalBlockSize.getSelection(), 
					txtTotalReplicates.getSelection(), 
					txtTotalTrials.getSelection(),
					txtFieldRows.getSelection(),
					fieldOrder);
		} else {
			ProjectExplorerView.rJavaManager.getPbToolRandomizationManager().doDesignAlpha(
					outputFileTxt.replace(File.separator, "/"),
					outputFileCsv.replace(File.separator, "/"), 
					txtTotalTreatments.getSelection(), 
					txtTotalBlockSize.getSelection(), 
					txtTotalReplicates.getSelection(), 
					txtTotalTrials.getSelection(),
					txtRowsEachBlk.getSelection(),
					txtRowsEachRep.getSelection(),
					txtFieldRows.getSelection(),
					fieldOrder);
		}
		
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
		return new Point(399, 476);
	}

}