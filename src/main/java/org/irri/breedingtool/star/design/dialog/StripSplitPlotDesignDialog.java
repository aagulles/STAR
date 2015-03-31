package org.irri.breedingtool.star.design.dialog;

import java.io.File;
import java.util.ArrayList;

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

public class StripSplitPlotDesignDialog extends Dialog {
	private Spinner txtTotalBlocks;
	private Spinner txtTotalTrials;
	private Text txtVerticalFactorNames;
	private Text txtVerticalFactorID;
	private Text txtHorizontalFactorNames;
	private Text txtHorizontalFactorID;
	private Text txtSubPlotFactorNames;
	private Text txtSubPlotFactorID;
	private Spinner txtSubPlotFactorLevels;
	private Spinner txtHorizontalFactorLevels;
	private Spinner txtVerticalFactorLevels;
	private Text txtFileName;
	private Button btnOk;
	private Spinner txtRows;
	private Spinner txtFieldRows;
	private Combo cmbOrder;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public StripSplitPlotDesignDialog(Shell parentShell) {
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
		lblFactorialDesign.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		lblFactorialDesign.setText("Strip-Split Plot Design");
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group grpDesignParameters = new Group(container, SWT.NONE);
		grpDesignParameters.setLayout(new GridLayout(1, false));
		GridData gd_grpDesignParameters = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpDesignParameters.heightHint = 237;
		grpDesignParameters.setLayoutData(gd_grpDesignParameters);
		
		Group group = new Group(grpDesignParameters, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		group.setText("Factor Definition:");
		group.setLayout(new GridLayout(4, false));
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		label_1.setText("Factor");
		label_1.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		label_2.setText("Name");
		label_2.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setText("Factor ID");
		label_3.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		Label label_4 = new Label(group, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1));
		label_4.setText("Levels");
		label_4.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		Label lblVertical = new Label(group, SWT.NONE);
		lblVertical.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblVertical.setText("Vertical ");
		lblVertical.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		txtVerticalFactorNames = new Text(group, SWT.BORDER);
		txtVerticalFactorNames.setText("vertical");
		GridData gd_txtVerticalFactorNames = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_txtVerticalFactorNames.widthHint = 80;
		txtVerticalFactorNames.setLayoutData(gd_txtVerticalFactorNames);
		
		txtVerticalFactorID = new Text(group, SWT.BORDER);
		txtVerticalFactorID.setText("A");
		GridData gd_txtVerticalFactorID = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtVerticalFactorID.widthHint = 50;
		txtVerticalFactorID.setLayoutData(gd_txtVerticalFactorID);
		
		txtVerticalFactorLevels = new Spinner(group, SWT.BORDER);
		GridData gd_txtVerticalFactorLevels = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_txtVerticalFactorLevels.widthHint = 20;
		txtVerticalFactorLevels.setLayoutData(gd_txtVerticalFactorLevels);
		txtVerticalFactorLevels.setMaximum(500);
		txtVerticalFactorLevels.setMinimum(2);
		
		Label lblHorizontal = new Label(group, SWT.NONE);
		lblHorizontal.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblHorizontal.setText("Horizontal");
		lblHorizontal.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		txtHorizontalFactorNames = new Text(group, SWT.BORDER);
		txtHorizontalFactorNames.setText("horizontal");
		GridData gd_txtHorizontalFactorNames = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtHorizontalFactorNames.widthHint = 80;
		txtHorizontalFactorNames.setLayoutData(gd_txtHorizontalFactorNames);
		
		txtHorizontalFactorID = new Text(group, SWT.BORDER);
		txtHorizontalFactorID.setText("B");
		GridData gd_txtHorizontalFactorID = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtHorizontalFactorID.widthHint = 50;
		txtHorizontalFactorID.setLayoutData(gd_txtHorizontalFactorID);
		
		txtHorizontalFactorLevels = new Spinner(group, SWT.BORDER);
		GridData gd_txtHorizontalFactorLevels = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtHorizontalFactorLevels.widthHint = 20;
		txtHorizontalFactorLevels.setLayoutData(gd_txtHorizontalFactorLevels);
		txtHorizontalFactorLevels.setMaximum(500);
		txtHorizontalFactorLevels.setMinimum(2);
		
		Label lblSubPlot = new Label(group, SWT.NONE);
		lblSubPlot.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSubPlot.setText("Subplot");
		lblSubPlot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		txtSubPlotFactorNames = new Text(group, SWT.BORDER);
		txtSubPlotFactorNames.setText("sub");
		GridData gd_txtSubPlotFactorNames = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubPlotFactorNames.widthHint = 80;
		txtSubPlotFactorNames.setLayoutData(gd_txtSubPlotFactorNames);
		
		txtSubPlotFactorID = new Text(group, SWT.BORDER);
		txtSubPlotFactorID.setText("C");
		GridData gd_txtSubPlotFactorID = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubPlotFactorID.widthHint = 50;
		txtSubPlotFactorID.setLayoutData(gd_txtSubPlotFactorID);
		
		txtSubPlotFactorLevels = new Spinner(group, SWT.BORDER);
		GridData gd_txtSubPlotFactorLevels = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubPlotFactorLevels.widthHint = 20;
		txtSubPlotFactorLevels.setLayoutData(gd_txtSubPlotFactorLevels);
		txtSubPlotFactorLevels.setMaximum(500);
		txtSubPlotFactorLevels.setMinimum(2);
		
		Composite composite_1 = new Composite(grpDesignParameters, SWT.NONE);
		composite_1.setLayout(new GridLayout(3, false));
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_composite_1.widthHint = 60;
		composite_1.setLayoutData(gd_composite_1);
		
		Label lblNumberOfReplicates = new Label(composite_1, SWT.NONE);
		lblNumberOfReplicates.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lblNumberOfReplicates.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfReplicates.setText("Number of Blocks");
		
		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 10;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		txtTotalBlocks = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtTotalBlocks = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_txtTotalBlocks.widthHint = 20;
		txtTotalBlocks.setLayoutData(gd_txtTotalBlocks);
		txtTotalBlocks.setMaximum(500);
		txtTotalBlocks.setMinimum(2);
		
		Label lblNumberOfRows = new Label(composite_1, SWT.NONE);
		lblNumberOfRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNumberOfRows.setText("Number of Subplots along the Length of Each VxH plot");
		lblNumberOfRows.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);
		
		txtRows = new Spinner(composite_1, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtRows.setMinimum(1);
		
		Label lblNumberOfField = new Label(composite_1, SWT.NONE);
		lblNumberOfField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNumberOfField.setText("Number of Field Rows");
		lblNumberOfField.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);
		
		txtFieldRows = new Spinner(composite_1, SWT.BORDER);
		txtFieldRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtFieldRows.setMaximum(500);
		txtFieldRows.setMinimum(2);
		
		Label lblNumberOfTrials = new Label(composite_1, SWT.NONE);
		lblNumberOfTrials.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lblNumberOfTrials.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfTrials.setText("Number of Trials");
		new Label(composite_1, SWT.NONE);
		
		txtTotalTrials = new Spinner(composite_1, SWT.BORDER);
		txtTotalTrials.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		txtTotalTrials.setMaximum(100);
		txtTotalTrials.setMinimum(1);
		
		Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		group_1.setText("Field Book ");
		group_1.setLayout(new GridLayout(4, false));
		
		Label label_5 = new Label(group_1, SWT.NONE);
		GridData gd_label_5 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_5.widthHint = 40;
		label_5.setLayoutData(gd_label_5);
		label_5.setText("Name");
		
		txtFileName = new Text(group_1, SWT.BORDER);
		txtFileName.setText("fieldbookStripSplit");
		GridData gd_txtFileName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFileName.widthHint = 100;
		txtFileName.setLayoutData(gd_txtFileName);
		
		Label label_7 = new Label(group_1, SWT.NONE);
		GridData gd_label_7 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_label_7.widthHint = 40;
		label_7.setLayoutData(gd_label_7);
		label_7.setText("Order");
		
		cmbOrder = new Combo(group_1, SWT.READ_ONLY);
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
			txtVerticalFactorNames.setText("vertical");
			txtHorizontalFactorNames.setText("horizontal");
			txtSubPlotFactorNames.setText("sub");
			txtVerticalFactorID.setText("A");
			txtHorizontalFactorID.setText("B");
			txtSubPlotFactorID.setText("C");
			txtVerticalFactorLevels.setSelection(2);
			txtHorizontalFactorLevels.setSelection(2);
			txtSubPlotFactorLevels.setSelection(2);
			txtTotalBlocks.setSelection(2);
			txtRows.setSelection(1);
			txtTotalTrials.setSelection(1);
			txtFieldRows.setSelection(2);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldbookStripSplit");
		}
		super.buttonPressed(buttonId);
	}
	
	@Override
	protected void okPressed(){  	
		ArrayList<Text> listText = new ArrayList<Text>();
		listText.add(txtVerticalFactorID);
		listText.add(txtHorizontalFactorID);
		listText.add(txtSubPlotFactorID);
		
		if(!StarRandomizationUtilities.isTextUnique(listText)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Similar variable names detected. Make sure all the variables are unique.", SWT.NONE);
			return ; 

		}

		if(txtVerticalFactorNames.getText().contains(" ") || txtHorizontalFactorNames.getText().contains(" ")){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor Name must not contain space", SWT.ERROR);
			return;
		}
		if(txtSubPlotFactorNames.getText().contains(" ")){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor Name must not contain space", SWT.ERROR);
			return;
		}
		if(txtVerticalFactorID.getText().contains(" ") || txtHorizontalFactorID.getText().contains(" ")){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must not contain space", SWT.ERROR);
			return;
		}
		if(txtSubPlotFactorID.getText().contains(" ")){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must not contain space", SWT.ERROR);
			return;
		}
		
		for(int i = 0; i < listText.size(); i++){
			if(listText.get(i) != txtFileName){
				if(!StarRandomizationUtilities.validateVariableText(listText.get(i).getText())){
					MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "'" + listText.get(i).getText() + "' is not a valid name. \nAll name fields must start with a letter and must contain:[a-z,A-Z] only. ", SWT.NONE);
					
					return ; 
				}
			}
		}
		
		if(txtFileName.getText().equals(""))  { 
			MessageDialog.openError(getShell(), "Error", "Field Filename must not be empty."); 
			return ; 
		}

		
		
		if(!StarRandomizationUtilities.validateTextFiles(txtVerticalFactorNames,txtHorizontalFactorNames,txtSubPlotFactorNames)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Fields must not be empty", SWT.ERROR);
			return;
		}
		
		if(!StarRandomizationUtilities.validateTextFiles(txtVerticalFactorID,txtHorizontalFactorID,txtSubPlotFactorID)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Fields must not be empty", SWT.ERROR);
			return;
		}
		
		String mainfactorID = txtVerticalFactorID.getText().toString();
		if(!StarRandomizationUtilities.validateVariableText(mainfactorID)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only.", SWT.ERROR);
			return;
		}
		String subfactorID = txtHorizontalFactorID.getText().toString();
		if(!StarRandomizationUtilities.validateVariableText(subfactorID)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only.", SWT.ERROR);
			return;
		}
		String subsubfactorID = txtSubPlotFactorID.getText().toString();
		if(!StarRandomizationUtilities.validateVariableText(subsubfactorID)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only.", SWT.ERROR);
			return;
		}
		
		int plot = (txtTotalBlocks.getSelection() * txtVerticalFactorLevels.getSelection() * txtHorizontalFactorLevels.getSelection() * txtSubPlotFactorLevels.getSelection() );
		if ((plot  % txtFieldRows.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Error", "Number of field rows must divide number of plots."); 
			return ;
		}
		
		if ((txtFieldRows.getSelection()  % (txtHorizontalFactorLevels.getSelection() * txtRows.getSelection())) !=0 ){
			MessageDialog.openError(getShell(), "Error", "Number of field rows must be divisible by the product of the levels of the horizontal and number of plots along the length of each VxH plot."); 
			return ;
		}
		
		if (txtRows.getSelection()> txtSubPlotFactorLevels.getSelection()){
			MessageDialog.openError(getShell(), "Error", "Number of plots along the length of each VxH plot should divide the levels of the sub plot factor."); 
			return ;
		}
		
		if(txtVerticalFactorID.getCharCount()>4){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
			return;	
		}
		if(txtHorizontalFactorID.getCharCount()>4){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
			return;	
		}
		
		if(txtSubPlotFactorID.getCharCount()>4){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
			return;	
		}
		
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		btnOk.setEnabled(false);
		
		String outputFile = StarRandomizationUtilities.createOutputFolder("StripSplitPlot");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";
		
		Integer factorLevels[] = {txtVerticalFactorLevels.getSelection(),txtHorizontalFactorLevels.getSelection(),txtSubPlotFactorLevels.getSelection()};
		String factorID[] = {txtVerticalFactorID.getText(), txtHorizontalFactorID.getText(), txtSubPlotFactorID.getText()};
		
		ProjectExplorerView.rJavaManager.getSTARDesignManager().doDesignStrip(
				outputFileTxt.replace(File.separator, "/"),
				outputFileCsv.replace(File.separator, "/"), 
				txtVerticalFactorNames.getText(), 
				txtHorizontalFactorNames.getText(), 
				txtSubPlotFactorNames.getText(), 
				null, 
				factorID,
				factorLevels,
				txtTotalBlocks.getSelection(),
				txtTotalTrials.getSelection(),
				txtFieldRows.getSelection(),
				txtRows.getSelection(),
				null,
				fieldOrder);
		
		StarRandomizationUtilities.testVarArgs(
				outputFileTxt.replace(File.separator, "/"),
				outputFileCsv.replace(File.separator, "/"), 
				txtVerticalFactorNames.getText(), 
				txtHorizontalFactorNames.getText(), 
				txtSubPlotFactorNames.getText(), 
				null, 
				factorID,
				factorLevels,
				txtTotalBlocks.getSelection(),
				txtTotalTrials.getSelection(),
				txtFieldRows.getSelection(),
				txtRows.getSelection(),
				null,
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
		return new Point(394, 472);
	}

}
