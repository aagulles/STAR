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

public class SplitSplitSplitCompletelyRandomizedDesignDialog extends Dialog {
	private Spinner txtTotalReplicates;
	private Spinner txtTotalTrials;
	private Text txtMainPlotFactorName;
	private Text txtMainPlotFactorID;
	private Text txtSubPlotFactorName;
	private Text txtSubPlotFactorID;
	private Text txtSubSubPlotFactorName;
	private Text txtSubSubPlotFactorID;
	private Text txtSubSubSubPlotFactorName;
	private Text txtSubSubSubPlotFactorID;
	private Spinner txtMainPlotFactorLevels;
	private Spinner txtSubPlotFactorLevels;
	private Spinner txtSubSubPlotFactorLevels;
	private Spinner txtSubSubSubPlotFactorLevels;
	private Text txtFileName;
	private Button btnOk;
	private Spinner txtRows;
	private Spinner txtRowsPerSub;
	private Spinner txtRowsPerSubSub;
	private Spinner txtFieldRows;
	private Combo cmbOrder;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SplitSplitSplitCompletelyRandomizedDesignDialog(Shell parentShell) {
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
		lblFactorialDesign.setText("Split-Split-Split Plot in CRD");
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group grpDesignParameters = new Group(container, SWT.NONE);
		grpDesignParameters.setLayout(new GridLayout(1, false));
		GridData gd_grpDesignParameters = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpDesignParameters.heightHint = 319;
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
		label_2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1));
		label_2.setText("Name");
		label_2.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setText("Factor ID");
		label_3.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		Label label_4 = new Label(group, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1));
		label_4.setText("Levels");
		label_4.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		Label lblMainplot = new Label(group, SWT.NONE);
		lblMainplot.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblMainplot.setText("Mainplot");
		lblMainplot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		txtMainPlotFactorName = new Text(group, SWT.BORDER);
		txtMainPlotFactorName.setText("main");
		GridData gd_txtMainPlotFactorName = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_txtMainPlotFactorName.widthHint = 80;
		txtMainPlotFactorName.setLayoutData(gd_txtMainPlotFactorName);
		
		txtMainPlotFactorID = new Text(group, SWT.BORDER);
		txtMainPlotFactorID.setText("A");
		GridData gd_txtMainPlotFactorID = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtMainPlotFactorID.widthHint = 50;
		txtMainPlotFactorID.setLayoutData(gd_txtMainPlotFactorID);
		
		txtMainPlotFactorLevels = new Spinner(group, SWT.BORDER);
		GridData gd_txtMainPlotFactorLevels = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_txtMainPlotFactorLevels.widthHint = 20;
		txtMainPlotFactorLevels.setLayoutData(gd_txtMainPlotFactorLevels);
		txtMainPlotFactorLevels.setMaximum(500);
		txtMainPlotFactorLevels.setMinimum(2);
		
		Label lblSubplot = new Label(group, SWT.NONE);
		lblSubplot.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSubplot.setText("Subplot");
		lblSubplot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		txtSubPlotFactorName = new Text(group, SWT.BORDER);
		txtSubPlotFactorName.setText("sub");
		GridData gd_txtSubPlotFactorName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubPlotFactorName.widthHint = 80;
		txtSubPlotFactorName.setLayoutData(gd_txtSubPlotFactorName);
		
		txtSubPlotFactorID = new Text(group, SWT.BORDER);
		txtSubPlotFactorID.setText("B");
		GridData gd_txtSubPlotFactorID = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubPlotFactorID.widthHint = 50;
		txtSubPlotFactorID.setLayoutData(gd_txtSubPlotFactorID);
		
		txtSubPlotFactorLevels = new Spinner(group, SWT.BORDER);
		GridData gd_txtSubPlotFactorLevels = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubPlotFactorLevels.widthHint = 20;
		txtSubPlotFactorLevels.setLayoutData(gd_txtSubPlotFactorLevels);
		txtSubPlotFactorLevels.setMaximum(500);
		txtSubPlotFactorLevels.setMinimum(2);
		
		Label lblSubsubPlot = new Label(group, SWT.NONE);
		lblSubsubPlot.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSubsubPlot.setText("Sub-subplot");
		lblSubsubPlot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		txtSubSubPlotFactorName = new Text(group, SWT.BORDER);
		txtSubSubPlotFactorName.setText("ssub");
		GridData gd_txtSubSubPlotFactorName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubSubPlotFactorName.widthHint = 80;
		txtSubSubPlotFactorName.setLayoutData(gd_txtSubSubPlotFactorName);
		
		txtSubSubPlotFactorID = new Text(group, SWT.BORDER);
		txtSubSubPlotFactorID.setText("C");
		GridData gd_txtSubSubPlotFactorID = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubSubPlotFactorID.widthHint = 50;
		txtSubSubPlotFactorID.setLayoutData(gd_txtSubSubPlotFactorID);
		
		txtSubSubPlotFactorLevels = new Spinner(group, SWT.BORDER);
		GridData gd_txtSubSubPlotFactorLevels = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtSubSubPlotFactorLevels.widthHint = 20;
		txtSubSubPlotFactorLevels.setLayoutData(gd_txtSubSubPlotFactorLevels);
		txtSubSubPlotFactorLevels.setMaximum(500);
		txtSubSubPlotFactorLevels.setMinimum(2);
		
		Label lblSubsubsubPlot = new Label(group, SWT.NONE);
		lblSubsubsubPlot.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSubsubsubPlot.setText("Sub-sub-subplot");
		lblSubsubsubPlot.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		
		txtSubSubSubPlotFactorName = new Text(group, SWT.BORDER);
		txtSubSubSubPlotFactorName.setText("sssub");
		GridData gd_txtSubSubSubPlotFactorName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubSubSubPlotFactorName.widthHint = 80;
		txtSubSubSubPlotFactorName.setLayoutData(gd_txtSubSubSubPlotFactorName);
		
		txtSubSubSubPlotFactorID = new Text(group, SWT.BORDER);
		txtSubSubSubPlotFactorID.setText("D");
		GridData gd_txtSubSubSubPlotFactorID = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSubSubSubPlotFactorID.widthHint = 0;
		txtSubSubSubPlotFactorID.setLayoutData(gd_txtSubSubSubPlotFactorID);
		
		txtSubSubSubPlotFactorLevels = new Spinner(group, SWT.BORDER);
		GridData gd_txtSubSubSubPlotFactorLevels = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtSubSubSubPlotFactorLevels.widthHint = 20;
		txtSubSubSubPlotFactorLevels.setLayoutData(gd_txtSubSubSubPlotFactorLevels);
		txtSubSubSubPlotFactorLevels.setMaximum(500);
		txtSubSubSubPlotFactorLevels.setMinimum(2);
		
		Composite composite_1 = new Composite(grpDesignParameters, SWT.NONE);
		composite_1.setLayout(new GridLayout(3, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Label lblNumberOfReplicates = new Label(composite_1, SWT.NONE);
		lblNumberOfReplicates.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfReplicates.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNumberOfReplicates.setText("Number of Replicates");
		
		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 10;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		txtTotalReplicates = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtTotalReplicates = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_txtTotalReplicates.widthHint = 20;
		txtTotalReplicates.setLayoutData(gd_txtTotalReplicates);
		txtTotalReplicates.setMaximum(500);
		txtTotalReplicates.setMinimum(2);
		
		Label lblNumberOfRows = new Label(composite_1, SWT.NONE);
		lblNumberOfRows.setText("Number of Subplots along the Length of Each Mainplot");
		lblNumberOfRows.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);
		
		txtRows = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtRows = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtRows.widthHint = 20;
		txtRows.setLayoutData(gd_txtRows);
		txtRows.setMaximum(500);
		txtRows.setMinimum(1);
		
		Label lblNumberOfRows_1 = new Label(composite_1, SWT.NONE);
		lblNumberOfRows_1.setText("Number of Sub-subplots along the Length of Each Subplot");
		lblNumberOfRows_1.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);
		
		txtRowsPerSub = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtRowsPerSub = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtRowsPerSub.widthHint = 20;
		txtRowsPerSub.setLayoutData(gd_txtRowsPerSub);
		txtRowsPerSub.setMaximum(500);
		txtRowsPerSub.setMinimum(1);
		
		Label lblNumberOfRows_2 = new Label(composite_1, SWT.NONE);
		lblNumberOfRows_2.setText("Number of Sub-sub-subplots along the Length of Each Sub-subplot");
		lblNumberOfRows_2.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);
		
		txtRowsPerSubSub = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtRowsPerSubSub = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtRowsPerSubSub.widthHint = 20;
		txtRowsPerSubSub.setLayoutData(gd_txtRowsPerSubSub);
		txtRowsPerSubSub.setMaximum(500);
		txtRowsPerSubSub.setMinimum(1);
		
		Label lblNumberOfField = new Label(composite_1, SWT.NONE);
		lblNumberOfField.setText("Number of Field Rows");
		lblNumberOfField.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(composite_1, SWT.NONE);
		
		txtFieldRows = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtFieldRows = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtFieldRows.widthHint = 20;
		txtFieldRows.setLayoutData(gd_txtFieldRows);
		txtFieldRows.setMaximum(500);
		txtFieldRows.setMinimum(1);
		
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
		
		Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		group_1.setText("Field Book ");
		group_1.setLayout(new GridLayout(4, false));
		
		Label label_8 = new Label(group_1, SWT.NONE);
		GridData gd_label_8 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_8.widthHint = 40;
		label_8.setLayoutData(gd_label_8);
		label_8.setText("Name");
		
		txtFileName = new Text(group_1, SWT.BORDER);
		txtFileName.setText("fieldbookSplit3CRD");
		GridData gd_txtFileName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFileName.widthHint = 100;
		txtFileName.setLayoutData(gd_txtFileName);
		
		Label label_10 = new Label(group_1, SWT.NONE);
		GridData gd_label_10 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_label_10.widthHint = 40;
		label_10.setLayoutData(gd_label_10);
		label_10.setText("Order");
		
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
			txtMainPlotFactorName.setText("main");
			txtSubPlotFactorName.setText("sub");
			txtSubSubPlotFactorName.setText("ssub");
			txtSubSubSubPlotFactorName.setText("sssub");
			txtMainPlotFactorID.setText("A");
			txtSubPlotFactorID.setText("B");
			txtSubSubPlotFactorID.setText("C");
			txtSubSubSubPlotFactorID.setText("D");
			txtMainPlotFactorLevels.setSelection(2);
			txtSubPlotFactorLevels.setSelection(2);
			txtSubSubPlotFactorLevels.setSelection(2);
			txtSubSubSubPlotFactorLevels.setSelection(2);
			txtTotalReplicates.setSelection(2);
			txtRows.setSelection(1);
			txtRowsPerSub.setSelection(1);
			txtRowsPerSubSub.setSelection(1);
			txtTotalTrials.setSelection(1);
			txtFieldRows.setSelection(1);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldbookSplit3CRD");
		}
		super.buttonPressed(buttonId);
	}
	
	
	@Override
	protected void okPressed(){  	
		ArrayList<Text> listText = new ArrayList<Text>();
		listText.add(txtMainPlotFactorID);
		listText.add(txtSubPlotFactorID);
		listText.add(txtSubSubPlotFactorID);
		listText.add(txtSubSubSubPlotFactorID);
		
		if(!StarRandomizationUtilities.isTextUnique(listText)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Similar variable names detected. Make sure all the variables are unique.", SWT.NONE);
			return ; 

		}
		if(txtMainPlotFactorName.getText().contains(" ") || txtSubPlotFactorName.getText().contains(" ")){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor Name must not contain space", SWT.ERROR);
			return;
		}
		if(txtSubSubPlotFactorName.getText().contains(" ") || txtSubSubSubPlotFactorName.getText().contains(" ")){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor Name must not contain space", SWT.ERROR);
			return;
		}	
		if(txtMainPlotFactorID.getText().contains(" ") || txtSubPlotFactorID.getText().contains(" ")){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must not contain space", SWT.ERROR);
			return;
		}
		if(txtSubSubPlotFactorID.getText().contains(" ") || txtSubSubSubPlotFactorID.getText().contains(" ")){
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
		if(!StarRandomizationUtilities.validateTextFiles(txtMainPlotFactorName,txtSubPlotFactorName, txtSubSubPlotFactorName, txtSubSubSubPlotFactorName)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Fields must not be empty", SWT.ERROR);
			return;
		}

		if(!StarRandomizationUtilities.validateTextFiles(txtMainPlotFactorID,txtSubPlotFactorID, txtSubSubPlotFactorID, txtSubSubSubPlotFactorID)){
		MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Fields must not be empty", SWT.ERROR);
		return;
		}
		
		String mainfactorID = txtMainPlotFactorID.getText().toString();
		if(!StarRandomizationUtilities.validateVariableText(mainfactorID)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only.", SWT.ERROR);
			return;
		}
		String subfactorID = txtSubPlotFactorID.getText().toString();
		if(!StarRandomizationUtilities.validateVariableText(subfactorID)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only.", SWT.ERROR);
			return;
		}
		
		String subsubfactorID = txtSubSubPlotFactorID.getText().toString();
		if(!StarRandomizationUtilities.validateVariableText(subsubfactorID)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only.", SWT.ERROR);
			return;
		}
		String subsubsubfactorID = txtSubSubSubPlotFactorID.getText().toString();
		if(!StarRandomizationUtilities.validateVariableText(subsubsubfactorID)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must start with a letter and must contain:[a-z,A-Z] only.", SWT.ERROR);
			return;
		}
		
		
		int plot = (txtTotalReplicates.getSelection() *  txtMainPlotFactorLevels.getSelection() * txtSubPlotFactorLevels.getSelection() * txtSubSubPlotFactorLevels.getSelection() * txtSubSubSubPlotFactorLevels.getSelection());
		if ((plot  % txtFieldRows.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Error", "Number of field rows must divide number of plots."); 
			return ;
		}
		
		if ((txtFieldRows.getSelection()  % (txtRows.getSelection() * txtRowsPerSub.getSelection() * txtRowsPerSubSub.getSelection())) !=0 ){
			MessageDialog.openError(getShell(), "Error", "Number of field rows must be divisible by the number of plots along the length of each main plot, along the length of each sub plot and along the length og each sub-sub plot."); 
			return ;
		}
		
		if ((txtRows.getSelection() * txtRowsPerSub.getSelection()* txtRowsPerSubSub.getSelection()) > txtFieldRows.getSelection()){
			MessageDialog.openError(getShell(), "Error", " The product of the number of plots along the length of each main plot, along the length of each sub plot and along the length of each sub-sub plot should be less than or equal to the number of field rows and should divide the number of field rows."); 
			return ;
		}
		
		if (txtRows.getSelection() > txtSubPlotFactorLevels.getSelection()){
			MessageDialog.openError(getShell(), "Error", "Number of plots along the length of each main plot should divide the levels of sub plot factor."); 
			return ;
		}
		
		if (txtRowsPerSub.getSelection() > txtSubSubPlotFactorLevels.getSelection()){
			MessageDialog.openError(getShell(), "Error", "Number of plots along the length of each sub plot should divide the levels of sub-sub plot factor."); 
			return ;
		}
		
		if (txtRowsPerSubSub.getSelection() > txtSubSubSubPlotFactorLevels.getSelection()){
			MessageDialog.openError(getShell(), "Error", "Number of plots along the length of each sub-sub plot should divide the levels of sub-sub-sub plot factor."); 
			return ;
		}
		
		if(txtMainPlotFactorID.getCharCount()>4){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
			return;	
		}
		if(txtSubPlotFactorID.getCharCount()>4){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
			return;	
		}
		
		if(txtSubSubPlotFactorID.getCharCount()>4){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
			return;	
		}
		
		if(txtSubSubSubPlotFactorID.getCharCount()>4){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor ID must contain not more than four characters. ", SWT.ERROR);
			return;	
		}
		
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		btnOk.setEnabled(false);
		
		String outputFile = StarRandomizationUtilities.createOutputFolder("SplitSplitSplitPlotCRD");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";
		
		Integer factorLevels[] = {txtMainPlotFactorLevels.getSelection(),txtSubPlotFactorLevels.getSelection(),txtSubSubPlotFactorLevels.getSelection(), txtSubSubSubPlotFactorLevels.getSelection()};
		String factorID[] = {txtMainPlotFactorID.getText(), txtSubPlotFactorID.getText(), txtSubSubPlotFactorID.getText(), txtSubSubSubPlotFactorID.getText()};
		String design = "crd";
		
		ProjectExplorerView.rJavaManager.getSTARDesignManager().doDesignSplit(
				outputFileTxt.replace(File.separator, "/"),
				outputFileCsv.replace(File.separator, "/"), 
				txtMainPlotFactorName.getText(), 
				txtSubPlotFactorName.getText(), 
				txtSubSubPlotFactorName.getText(),  
				txtSubSubSubPlotFactorName.getText(),  
				factorID,
				factorLevels, 
				txtTotalReplicates.getSelection(), 
				txtTotalTrials.getSelection(),
				design,
				txtFieldRows.getSelection(),
				null,
				txtRows.getSelection(),
				txtRowsPerSub.getSelection(),
				txtRowsPerSubSub.getSelection(),
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
		return new Point(453, 536);
	}

}
