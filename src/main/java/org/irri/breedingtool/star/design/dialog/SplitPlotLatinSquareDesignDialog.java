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

public class SplitPlotLatinSquareDesignDialog extends Dialog {

	private Spinner txtTotalTrials;
	private Text txtMainPlotFactorName;
	private Text txtMainPlotFactorID;
	private Text txtSubPlotFactorName;
	private Text txtSubPlotFactorID;
	private Spinner txtSubPlotFactorLevels;
	private Spinner txtMainPlotFactorLevels;
	private Text txtFileName;
	private Button btnOk;
	private Spinner txtRows;
	private Combo cmbOrder;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SplitPlotLatinSquareDesignDialog(Shell parentShell) {
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
		lblFactorialDesign.setText("Split Plot in Latin Square Design");

		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group grpDesignParameters = new Group(container, SWT.NONE);
		grpDesignParameters.setLayout(new GridLayout(1, false));
		GridData gd_grpDesignParameters = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpDesignParameters.heightHint = 185;
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
		txtMainPlotFactorLevels.setMaximum(11);
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

		Composite composite_1 = new Composite(grpDesignParameters, SWT.NONE);
		composite_1.setLayout(new GridLayout(3, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Label lblNumberOfRows = new Label(composite_1, SWT.NONE);
		lblNumberOfRows.setText("Number of Subplots along the Length of Each Mainplot");
		
		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 10;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		txtRows = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtRows = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtRows.widthHint = 20;
		txtRows.setLayoutData(gd_txtRows);
		txtRows.setMaximum(100000);
		txtRows.setMaximum(500);
		txtRows.setMinimum(1);

		Label lblNumberOfTrials = new Label(composite_1, SWT.NONE);
		lblNumberOfTrials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNumberOfTrials.setText("Number of Trials");
		new Label(composite_1, SWT.NONE);

		txtTotalTrials = new Spinner(composite_1, SWT.BORDER);
		GridData gd_txtTotalTrials = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtTotalTrials.widthHint = 20;
		txtTotalTrials.setLayoutData(gd_txtTotalTrials);
		txtTotalTrials.setMaximum(100000);
		txtTotalTrials.setMaximum(100);
		txtTotalTrials.setMinimum(1);
		
		Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		group_1.setText("Field Book ");
		group_1.setLayout(new GridLayout(4, false));
		
		Label label_7 = new Label(group_1, SWT.NONE);
		GridData gd_label_7 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_label_7.widthHint = 40;
		label_7.setLayoutData(gd_label_7);
		label_7.setText("Name");
		
		txtFileName = new Text(group_1, SWT.BORDER);
		txtFileName.setText("fieldSplitLSD");
		GridData gd_txtFileName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFileName.widthHint = 100;
		txtFileName.setLayoutData(gd_txtFileName);
		
		Label label_9 = new Label(group_1, SWT.NONE);
		GridData gd_label_9 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_label_9.widthHint = 40;
		label_9.setLayoutData(gd_label_9);
		label_9.setText("Order");
		
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
			txtMainPlotFactorID.setText("A");
			txtSubPlotFactorID.setText("B");
			txtMainPlotFactorLevels.setSelection(2);
			txtSubPlotFactorLevels.setSelection(2);
			txtRows.setSelection(1);
			txtTotalTrials.setSelection(1);
			cmbOrder.setText("Plot Order");
			txtFileName.setText("fieldSplitLSD");
		}
		super.buttonPressed(buttonId);
	}
	
	
	
	@Override
	protected void okPressed(){  	
		ArrayList<Text> listText = new ArrayList<Text>();
		listText.add(txtMainPlotFactorID);
		listText.add(txtSubPlotFactorID);
		
		if(!StarRandomizationUtilities.isTextUnique(listText)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Similar variable names detected. Make sure all the variables are unique.", SWT.NONE);
			return ; 

		}
		if(txtMainPlotFactorName.getText().contains(" ") || txtSubPlotFactorName.getText().contains(" ")){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Factor Name must not contain space", SWT.ERROR);
			return;
		}
		if(txtMainPlotFactorID.getText().contains(" ") || txtSubPlotFactorID.getText().contains(" ")){
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
	
		if(!StarRandomizationUtilities.validateTextFiles(txtMainPlotFactorName,txtSubPlotFactorName)){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Fields must not be empty", SWT.ERROR);
			return;
		}
			
		if(!StarRandomizationUtilities.validateTextFiles(txtMainPlotFactorID,txtSubPlotFactorID)){
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
		
		if (txtRows.getSelection() > txtSubPlotFactorLevels.getSelection()){
			MessageDialog.open(SWT.ERROR, this.getShell(), "Error", "Number of plots along the length of each main plot should be less than or equal to the levels of the sub plot factor and must divider the levels of the sub plot factor", SWT.ERROR);
			return;
		}
		
		if ((txtSubPlotFactorLevels.getSelection()  % txtRows.getSelection()) !=0 ){
			MessageDialog.openError(getShell(), "Error", "Number of plots along the length of each main plot should divide the levels of sub plot factor."); 
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
		
		OperationProgressDialog rInfo = new OperationProgressDialog(getShell(),  "Performing Randomization");
		rInfo.open();
		btnOk.setEnabled(false);
			

		String outputFile = StarRandomizationUtilities.createOutputFolder("SplitPlotLSD");
		String outputFileTxt = outputFile;
		String outputFileCsv = txtFileName.getText();
		String fieldOrder = "Plot Order";
		if(cmbOrder.getText().equals("Serpentine")) fieldOrder = "Serpentine";
		
		Integer factorLevels[] = {txtMainPlotFactorLevels.getSelection(),txtSubPlotFactorLevels.getSelection()};
		String factorID[] = {txtMainPlotFactorID.getText(), txtSubPlotFactorID.getText()};
		String design = "lsd";
			
		ProjectExplorerView.rJavaManager.getSTARDesignManager().doDesignSplit(
				outputFileTxt.replace(File.separator, "/"),
				outputFileCsv.replace(File.separator, "/"), 
				txtMainPlotFactorName.getText(), 
				txtSubPlotFactorName.getText(), 
				null, 
				null,  
				factorID,
				factorLevels, 
				null, 
				txtTotalTrials.getSelection(),
				design,
				null,
				null,
				txtRows.getSelection(),
				null,
				null,
				fieldOrder);
		
		StarRandomizationUtilities.testVarArgs(
				outputFileTxt.replace(File.separator, "/"),
				outputFileCsv.replace(File.separator, "/"), 
				txtMainPlotFactorName.getText(), 
				txtSubPlotFactorName.getText(), 
				null, 
				null,  
				factorID,
				factorLevels, 
				null, 
				txtTotalTrials.getSelection(),
				design,
				null,
				null,
				txtRows.getSelection(),
				null,
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
		return new Point(425, 435);
	}

}
