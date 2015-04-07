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

public class AugmentedAlphaLatticeDesignDialog extends Dialog {

	private Spinner spinnerNumOfRepTreatments;
	private Spinner spinnerNumOfReps;
	private Spinner spinnerNumOfPlotsPerBlock;
	private Spinner spinnerNumOfTrials;
	private Text txtFileName;
	private Button btnOk;
	private Combo cmbOrder;
	private Spinner spinnerNumOfFieldRows;
	private Text txtBlksPerRep;
	private Spinner spinnerNumOfRowsEachRep;
	private Spinner spinnerNumOfRowsEachBlock;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AugmentedAlphaLatticeDesignDialog(Shell parentShell) {
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
		lblFactorialDesign.setText("Augmented Alpha Lattice Design");

		Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		composite_1.setLayout(new GridLayout(3, false));

		Label lblNumberOfReplicated = new Label(composite_1, SWT.NONE);
		lblNumberOfReplicated.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfReplicated.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNumberOfReplicated.setText("Number of Replicated Treatments");

		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 60;
		lblNewLabel.setLayoutData(gd_lblNewLabel);

		spinnerNumOfRepTreatments = new Spinner(composite_1, SWT.BORDER);

		GridData gd_spinnerNumOfRepTreatments = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_spinnerNumOfRepTreatments.widthHint = 20;
		spinnerNumOfRepTreatments.setLayoutData(gd_spinnerNumOfRepTreatments);
		spinnerNumOfRepTreatments.setMaximum(500);
		spinnerNumOfRepTreatments.setMinimum(9);
		spinnerNumOfRepTreatments.setSelection(9);

		Label lblNumberOfUnreplicated = new Label(composite_1, SWT.NONE);
		lblNumberOfUnreplicated.setText("Number of Unreplicated Treatments");
		lblNumberOfUnreplicated.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);

		Spinner spinnerNumOfUnrepTreatments = new Spinner(composite_1, SWT.BORDER);
		spinnerNumOfUnrepTreatments.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		spinnerNumOfUnrepTreatments.setMaximum(500);
		spinnerNumOfUnrepTreatments.setMinimum(9);
		spinnerNumOfUnrepTreatments.setSelection(9);

		Label lblNumberOfReplicates = new Label(composite_1, SWT.NONE);
		lblNumberOfReplicates.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfReplicates.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNumberOfReplicates.setText("Number of Replicates");
		new Label(composite_1, SWT.NONE);

		spinnerNumOfReps = new Spinner(composite_1, SWT.BORDER);
		GridData gd_spinnerNumOfReps = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_spinnerNumOfReps.widthHint = 20;
		spinnerNumOfReps.setLayoutData(gd_spinnerNumOfReps);
		spinnerNumOfReps.setMaximum(500);
		spinnerNumOfReps.setMinimum(2);
		spinnerNumOfReps.setSelection(2);

		Label lblNumberOfPlots = new Label(composite_1, SWT.NONE);
		lblNumberOfPlots.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNumberOfPlots.setText("Number of Plots per Block ");
		lblNumberOfPlots.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);

		spinnerNumOfPlotsPerBlock = new Spinner(composite_1, SWT.BORDER);

		GridData gd_spinnerNumOfPlotsPerBlock = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_spinnerNumOfPlotsPerBlock.widthHint = 20;
		spinnerNumOfPlotsPerBlock.setLayoutData(gd_spinnerNumOfPlotsPerBlock);
		spinnerNumOfPlotsPerBlock.setMaximum(500);
		spinnerNumOfPlotsPerBlock.setMinimum(3);
		spinnerNumOfPlotsPerBlock.setSelection(3);

		Label lblNumberOfBlocks = new Label(composite_1, SWT.NONE);
		lblNumberOfBlocks.setText("Number of Blocks per Replicate");
		lblNumberOfBlocks.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);

		txtBlksPerRep = new Text(composite_1, SWT.BORDER);
		txtBlksPerRep.setEditable(false);
		txtBlksPerRep.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtBlksPerRep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNumberOfRows = new Label(composite_1, SWT.NONE);
		lblNumberOfRows.setText("Number of Rows in Each Block");
		lblNumberOfRows.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);

		spinnerNumOfRowsEachBlock = new Spinner(composite_1, SWT.BORDER);
		spinnerNumOfRowsEachBlock.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		spinnerNumOfRowsEachBlock.setMaximum(500);
		spinnerNumOfRowsEachBlock.setMinimum(3);
		spinnerNumOfRowsEachBlock.setSelection(1);

		Label lblNumberOfRows_1 = new Label(composite_1, SWT.NONE);
		lblNumberOfRows_1.setText("Number of Rows in Each Replicate");
		lblNumberOfRows_1.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);

		spinnerNumOfRowsEachRep = new Spinner(composite_1, SWT.BORDER);
		spinnerNumOfRowsEachRep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		spinnerNumOfRowsEachRep.setMaximum(500);
		spinnerNumOfRowsEachRep.setMinimum(3);
		spinnerNumOfRowsEachRep.setSelection(3);

		Label lblNumberOfField = new Label(composite_1, SWT.NONE);
		lblNumberOfField.setText("Number of Field Rows");
		lblNumberOfField.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);

		spinnerNumOfFieldRows = new Spinner(composite_1, SWT.BORDER);
		GridData gd_spinnerNumOfFieldRows = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_spinnerNumOfFieldRows.widthHint = 20;
		spinnerNumOfFieldRows.setLayoutData(gd_spinnerNumOfFieldRows);
		spinnerNumOfFieldRows.setMaximum(500);
		spinnerNumOfFieldRows.setMinimum(3);
		spinnerNumOfFieldRows.setSelection(3);

		Label lblNumberOfTrials = new Label(composite_1, SWT.NONE);
		lblNumberOfTrials.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfTrials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNumberOfTrials.setText("Number of Trials");
		new Label(composite_1, SWT.NONE);

		spinnerNumOfTrials = new Spinner(composite_1, SWT.BORDER);
		GridData gd_spinnerNumOfTrials = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_spinnerNumOfTrials.widthHint = 20;
		spinnerNumOfTrials.setLayoutData(gd_spinnerNumOfTrials);
		spinnerNumOfTrials.setMaximum(500);
		spinnerNumOfTrials.setMinimum(1);

		spinnerNumOfRepTreatments.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPlotValue();
			}
		});
		spinnerNumOfPlotsPerBlock.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPlotValue();
			}
		});

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
		txtFileName.setText("fieldbookAugAlphaLattice");
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
		double i = (double) spinnerNumOfRepTreatments.getSelection() / spinnerNumOfPlotsPerBlock.getSelection();
		String value = Double.toString(i);
		txtBlksPerRep.setText(value);
	}


	@Override
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.RETRY_ID) {
			spinnerNumOfRepTreatments.setSelection(9);
			spinnerNumOfPlotsPerBlock.setSelection(3);
			spinnerNumOfReps.setSelection(2); 
			txtBlksPerRep.setSelection(3);
			spinnerNumOfRowsEachBlock.setSelection(3);
			spinnerNumOfRowsEachRep.setSelection(3);
			spinnerNumOfFieldRows.setSelection(3);
			spinnerNumOfTrials.setSelection(1);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldbookAlpha");
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void okPressed(){ 
		double numOfExperimentalUnits = spinnerNumOfRepTreatments.getSelection() + spinnerNumOfReps.getSelection();
		double numOfPlotsPerRep = (numOfExperimentalUnits/spinnerNumOfReps.getSelection());
				
		Double.parseDouble(txtBlksPerRep.getText());

		if(spinnerNumOfReps.getSelection()<2 || spinnerNumOfReps.getSelection()>4){
			MessageDialog.openError(getShell(), "Error", "Augmented Alpha design can only be generated for number of replicated treatments between 2 to 4"); 
			spinnerNumOfReps.setSelection(2);
			return;
		}

		if(numOfExperimentalUnits > 1500){
			MessageDialog.openError(getShell(), "Error", "The maximum total number of experimental units is 1500."); 
			return ; 
		}

		if((numOfExperimentalUnits % spinnerNumOfFieldRows.getSelection())!=0){
			MessageDialog.openError(getShell(), "Error", "The number of experimental units should be divisible by the number of fieldrows."); 
			return;
		}

		if((numOfExperimentalUnits % spinnerNumOfReps.getSelection())!=0){
			MessageDialog.openError(getShell(), "Error", "The number of experimental units should be divisible by the number of replicates."); 
			return;
		}

		if((numOfPlotsPerRep % spinnerNumOfRowsEachRep.getSelection())!=0){
			MessageDialog.openError(getShell(), "Error", "The number of experimental units per replicate should be divisible by the number of rows per replicate."); 
			return;
		}

		if((spinnerNumOfReps.getSelection() % spinnerNumOfRowsEachBlock.getSelection())!=0){
			MessageDialog.openError(getShell(), "Error", "Number of field rows is not divisible by the total number of blocks."); 
			return;
		}

		if(spinnerNumOfReps.getSelection()==2){
			if(spinnerNumOfPlotsPerBlock.getSelection() > numOfPlotsPerRep){
				MessageDialog.openError(getShell(), "Error", "For r==2,"); 
				return;
			}
			if(spinnerNumOfPlotsPerBlock.getSelection() > numOfPlotsPerRep){
				MessageDialog.openError(getShell(), "Error", "For r==3 and number of block is even, block size should be less than or equal to the number of blocks per replicate."); 
				return;
			}

		} else if(spinnerNumOfReps.getSelection()==3){
			if((spinnerNumOfPlotsPerBlock.getSelection() % 2)!=0){// if block is even
				if(spinnerNumOfPlotsPerBlock.getSelection() > numOfPlotsPerRep){
					MessageDialog.openError(getShell(), "Error", "For r==3 and number of block is even, block size should be less than or equal to the number of blocks per replicate."); 
					return;
				}
			}else{ // if block is odd
				if(spinnerNumOfPlotsPerBlock.getSelection() >= numOfPlotsPerRep){
					MessageDialog.openError(getShell(), "Error", "For r==3 and number of block is even, block size should be less than the number of blocks per replicate."); 
					return;
				}
			}

		} else if(spinnerNumOfReps.getSelection()==4 && ((spinnerNumOfPlotsPerBlock.getSelection() % 2)!=0)){
			if(spinnerNumOfPlotsPerBlock.getSelection() > numOfPlotsPerRep){
				if(spinnerNumOfPlotsPerBlock.getSelection() > numOfPlotsPerRep){
					MessageDialog.openError(getShell(), "Error", "For r==4 and number of block is even, block size should be less than or equal to the number of blocks per replicate."); 
					return;
				}
			}
		}

		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		btnOk.setEnabled(false);

		String outputFile = StarRandomizationUtilities.createOutputFolder("AlphaLatticeIBD");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";

		ProjectExplorerView.rJavaManager.getSTARDesignManager().doDesignAlpha(
				outputFileTxt.replace(File.separator, "/"),
				outputFileCsv.replace(File.separator, "/"), 
				spinnerNumOfRepTreatments.getSelection(), 
				spinnerNumOfPlotsPerBlock.getSelection(), 
				spinnerNumOfReps.getSelection(), 
				spinnerNumOfTrials.getSelection(),
				spinnerNumOfRowsEachBlock.getSelection(),
				spinnerNumOfRowsEachRep.getSelection(),
				spinnerNumOfFieldRows.getSelection(),
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
		return new Point(461, 439);
	}

}