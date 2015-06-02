package org.irri.breedingtool.pbtools.randomization.dialog;

import java.io.File;

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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.irri.breedingtool.utility.StarRandomizationUtilities;

public class AugmentedLatinSquareDesignDialog extends Dialog {
	//private String filePath = PartStackHandler.getActiveElementID();
	private Spinner txtTotalReplicatedTreatments;
	private Spinner txtTotalUnreplicatedTreatments;
	private Composite composite;
	private Spinner txtTotalTrials;
	private Button btnOk;
	private Text txtPlots;
	private Spinner txtRows;
	private Text txtFileName;
	private Combo cmbOrder;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AugmentedLatinSquareDesignDialog(Shell parentShell) {
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
		lblFactorialDesign.setText("Augmented Latin Square Design");
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		composite.setLayout(new GridLayout(3, false));
		
		Label lblNumberOfReplicated = new Label(composite, SWT.NONE);
		lblNumberOfReplicated.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfReplicated.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNumberOfReplicated.setText("Number of Replicated Treatments");
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 60;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		txtTotalReplicatedTreatments = new Spinner(composite, SWT.BORDER);
		txtTotalReplicatedTreatments.setMaximum(20);
		
		GridData gd_txtTotalReplicatedTreatments = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtTotalReplicatedTreatments.widthHint = 20;
		txtTotalReplicatedTreatments.setLayoutData(gd_txtTotalReplicatedTreatments);
		txtTotalReplicatedTreatments.setSelection(2);
		
				
				Label lblNumberOfUnreplicated = new Label(composite, SWT.NONE);
				lblNumberOfUnreplicated.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
				lblNumberOfUnreplicated.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
				lblNumberOfUnreplicated.setText("Number of Unreplicated Treatments");
				new Label(composite, SWT.NONE);
				
				txtTotalUnreplicatedTreatments = new Spinner(composite, SWT.BORDER);
				
						GridData gd_txtTotalUnreplicatedTreatments = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
						gd_txtTotalUnreplicatedTreatments.widthHint = 20;
						txtTotalUnreplicatedTreatments.setLayoutData(gd_txtTotalUnreplicatedTreatments);
						txtTotalUnreplicatedTreatments.setMaximum(2000);
						txtTotalUnreplicatedTreatments.setSelection(2);
						
						Label lblNumberOfRows = new Label(composite, SWT.NONE);
						lblNumberOfRows.setText("Number of Field Rows");
						lblNumberOfRows.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						new Label(composite, SWT.NONE);
						
						txtRows = new Spinner(composite, SWT.BORDER);
						GridData gd_txtRows = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
						gd_txtRows.widthHint = 20;
						txtRows.setLayoutData(gd_txtRows);
						txtRows.setMaximum(10000);
						txtRows.setSelection(2);
						
						Label lblNumberOfPlots = new Label(composite, SWT.NONE);
						lblNumberOfPlots.setText("Number of Plots in the Design");
						lblNumberOfPlots.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						new Label(composite, SWT.NONE);
						
						txtPlots = new Text(composite, SWT.BORDER);
						txtPlots.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
						txtPlots.setText("6");
						txtPlots.setEditable(false);
						GridData gd_txtPlots = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
						gd_txtPlots.widthHint = 20;
						txtPlots.setLayoutData(gd_txtPlots);
						
						Label lblNumberOfTrials = new Label(composite, SWT.NONE);
						lblNumberOfTrials.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
						lblNumberOfTrials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
						lblNumberOfTrials.setText("Number of Trials");
						new Label(composite, SWT.NONE);
						
						txtTotalTrials = new Spinner(composite, SWT.BORDER);
						GridData gd_txtTotalTrials = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
						gd_txtTotalTrials.widthHint = 20;
						txtTotalTrials.setLayoutData(gd_txtTotalTrials);
						txtTotalTrials.setMaximum(100);
						txtTotalTrials.setMinimum(1);
						
						txtTotalReplicatedTreatments.addModifyListener(new ModifyListener() {
							public void modifyText(ModifyEvent e) {
								setPlotValue();
							}
						});
						
						txtTotalUnreplicatedTreatments.addModifyListener(new ModifyListener() {
							public void modifyText(ModifyEvent e) {
								setPlotValue();
							}
						});
		
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		group.setText("Field Book ");
		group.setLayout(new GridLayout(5, false));
		
		Label label_1 = new Label(group, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_1.widthHint = 40;
		label_1.setLayoutData(gd_label_1);
		label_1.setText("Name");
		
		txtFileName = new Text(group, SWT.BORDER);
		txtFileName.setText("fieldbookAugLSD");
		GridData gd_txtFileName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFileName.widthHint = 100;
		txtFileName.setLayoutData(gd_txtFileName);
		
		Label label_2 = new Label(group, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.widthHint = 10;
		label_2.setLayoutData(gd_label_2);
		
		Label label_3 = new Label(group, SWT.NONE);
		GridData gd_label_3 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_label_3.widthHint = 40;
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

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	
	public void setPlotValue(){
		int i = ((txtTotalReplicatedTreatments.getSelection()* txtTotalReplicatedTreatments.getSelection()) + txtTotalUnreplicatedTreatments.getSelection());
		String value = Integer.toString(i);	
		txtPlots.setText(value);
	}
	
	@Override
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.RETRY_ID) {
			txtTotalReplicatedTreatments.setSelection(2);
			txtTotalUnreplicatedTreatments.setSelection(2);
			txtRows.setSelection(2);
			txtPlots.setSelection(6);
			txtTotalTrials.setSelection(1);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldbookAugLSD");
		}
		super.buttonPressed(buttonId);
	}
	
	
	
	@Override
	protected void okPressed(){
		
		if(txtTotalReplicatedTreatments.getSelection()<2){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of replicated treatments is equal to 2."); 
			txtTotalReplicatedTreatments.setSelection(2);
			return;
		}
		if(txtTotalUnreplicatedTreatments.getSelection()<2){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of unreplicated treatments is equal to 2."); 
			txtTotalUnreplicatedTreatments.setSelection(2);
			return;
		}
		if(txtRows.getSelection()<2){
			MessageDialog.openError(getShell(), "Error", "The minimum value of the number of field rows is equal to 2."); 
			txtRows.setSelection(2);
			return;
		}
				
		if(txtFileName.getText().equals(""))  { 
			MessageDialog.openError(getShell(), "Error", "Field Filename must not be empty."); 
			return ; 
			}
		
		if(txtTotalReplicatedTreatments.getSelection() > txtRows.getSelection()){
			MessageDialog.openError(getShell(), "Error", "Too few field rows for blocking."); 
			return ;
		}
		
		if((Double.parseDouble(txtPlots.getText()) % txtRows.getSelection()) != 0){
			MessageDialog.openError(getShell(), "Error", "Number of field rows must divide number of plots."); 
			return ; 
		}
		
		if(txtTotalReplicatedTreatments.getSelection() > (Double.parseDouble(txtPlots.getText()) / txtRows.getSelection())){
			MessageDialog.openError(getShell(), "Error", "Too few field column for blocking."); 
			return ; 
		}
		
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		btnOk.setEnabled(false);	
		
		String outputFile = StarRandomizationUtilities.createOutputFolder("AugmentedLSD");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";
		
		ProjectExplorerView.rJavaManager.getPbToolRandomizationManager().doDesignAugLSD(
				outputFileTxt.replace(File.separator, "/"), 
				outputFileCsv.replace(File.separator, "/"), 
				txtTotalReplicatedTreatments.getSelection(), 
				txtTotalUnreplicatedTreatments.getSelection(),
				null, 
				txtTotalTrials.getSelection(),
				txtRows.getSelection(),
				fieldOrder, null, null, null);
		
		StarAnalysisUtilities.testVarArgs(			
				outputFileTxt.replace(File.separator, "/"), 
				outputFileCsv.replace(File.separator, "/"), 
				txtTotalReplicatedTreatments.getSelection(), 
				txtTotalUnreplicatedTreatments.getSelection(), 
				txtRows.getSelection(),
				txtTotalTrials.getSelection(), 
				fieldOrder);
		
		
		rInfo.close();
		this.getShell().setMinimized(true);
		StarRandomizationUtilities.openAndRefreshFileFolder(outputFile  + outputFileCsv + ".csv");
		
		btnOk.setEnabled(true);
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
		return new Point(382, 362);
	}

}
