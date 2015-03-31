package org.irri.breedingtool.datamanipulation.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class ReshapeWideToLongDialog extends Dialog {
	private int targetVarCount=0;
	private int selectedRowIndex;
	private int targetVarItemsCount;
	private Text targetVarText;
	private Text indexFactorText;
	private List activeVarCombo, targetVarList, varRetainList;
	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private String[] columnHeaders;
	private Button btnAddTargetVar, btnAddVarRetain, btnAddTargetVariable;
	private ArrayList<String[]> reshapeVariables= new ArrayList<String[]>();
	private ArrayList<String> targetVariableList= new ArrayList<String>();
	private Table table;
	private TableColumn tblclmnTargetVariable;
	private TableColumn tblclmnNewColumn;
	private Button deleteTargetBtn;
	private Group group;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager(); 

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ReshapeWideToLongDialog(Shell parentShell, ArrayList<String> varInfo, ProjectExplorerTreeNodeModel fileModel) {
		super(parentShell);
		this.fileModel = fileModel;
		this.varInfo = varInfo;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Reshape Data (Wide to Long): "+dataManipulationManager.getDisplayFileName(fileModel.getProjectFile().getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 5;

		Label lblNewLabel = new Label(container, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_lblNewLabel.widthHint = 157;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNewLabel.setText("Variables in the active data:");

		Group grpVariablesToBe = new Group(container, SWT.NONE);
		grpVariablesToBe.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		grpVariablesToBe.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpVariablesToBe.setText("Variable to Reshape:");
		grpVariablesToBe.setLayout(new GridLayout(4, false));
		GridData gd_grpVariablesToBe = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 7);
		gd_grpVariablesToBe.heightHint = 277;
		gd_grpVariablesToBe.widthHint = 253;
		grpVariablesToBe.setLayoutData(gd_grpVariablesToBe);

		Label lblTargetVariable = new Label(grpVariablesToBe, SWT.NONE);
		lblTargetVariable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblTargetVariable.setText("Target Variable:");

		targetVarText = new Text(grpVariablesToBe, SWT.BORDER);
		targetVarText.setText("targetvar1");
		targetVarText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		targetVarText.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				String string = event.text;
				char [] chars = new char [string.length ()];
				string.getChars (0, chars.length, chars, 0);

				if(targetVarText.getText().isEmpty()){
					if(Character.isDigit(chars[0])){
						event.doit=false;
						MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, you can't have an integer as the first character of your variable name.");
						return;
					}
				}
			}
		});
		btnAddTargetVar= new Button(grpVariablesToBe, SWT.NONE);
		btnAddTargetVar.setEnabled(false);
		btnAddTargetVar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(activeVarCombo.getItemCount()>0){
					String selectedNumVars[]= activeVarCombo.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						targetVarList.add(selectedNumVars[i]);
					}
					activeVarCombo.remove(activeVarCombo.getSelectionIndices());
					btnAddTargetVar.setEnabled(false);
					btnAddVarRetain.setEnabled(false);
					btnAddTargetVariable.setEnabled(true);
				}
			}
		});
		GridData gd_btnAddTargetVar = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddTargetVar.widthHint = 95;
		btnAddTargetVar.setLayoutData(gd_btnAddTargetVar);
		btnAddTargetVar.setText("Add");

		targetVarList = new List(grpVariablesToBe, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
		GridData gd_targetVarList = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_targetVarList.heightHint = 87;
		targetVarList.setLayoutData(gd_targetVarList);
		new Label(grpVariablesToBe, SWT.NONE);
		new Label(grpVariablesToBe, SWT.NONE);

		btnAddTargetVariable = new Button(grpVariablesToBe, SWT.NONE);
		btnAddTargetVariable.setEnabled(false);
		btnAddTargetVariable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//if variable name does not exist
				if(dataManipulationManager.checkColumnNameIfUsed(targetVarText.getText(), columnHeaders)){
					MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the variable name \""+ targetVarText.getText() +"\" already exist.\n Please rename your target variable!");
				}
				else if(targetVarText.getText().isEmpty()){
					MessageDialog.openWarning(getShell(), "Invalid Input", "Please enter a target variable name.");
				}
				if(!dataManipulationManager.variableNameInputValidation(targetVarText.getText()).equals("true")){
					MessageDialog.openWarning(getShell(), "Invalid Input", "'"+targetVarText.getText()+"' is an invalid variable name.");
				}
				else if(targetVarList.getItems().length<1){//if there are no reshape variables
					MessageDialog.openWarning(getShell(), "Invalid Input", "Please add some reshape variables from the active variables table.");
				}
				else if(targetVarText.getText().equals(indexFactorText.getText())){//if the targetVariable and the index factor has the same name
					MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, you already used \""+ targetVarText.getText() +"\" as your index factor. Please change your target variable name.");
				}
				else if(alreadyUsedasTargetVariable(targetVarText.getText())){//if the targetVariable and the index factor has the same name
					MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, you already used \""+ targetVarText.getText() +"\" as a targetVariableName. Please change your target variable name.");
				}
				else if(targetVarList.getItemCount() < 2){//if the targetVariables have different numbers of reshape variables
					MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, you should have 2 or more reshape variables for each target variable.");
				}
				else if(targetVarList.getItemCount()!= targetVarItemsCount && targetVarItemsCount>0){//if the targetVariables have different numbers of reshape variables
					MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, your targetVariables should have the same number of reshape variables. The current number of your reshape variables for each is "+ Integer.toString(targetVarItemsCount));
				}
				else{
					String[] targetVariableInput=targetVarList.getItems();
					String reshapeVariableString = "";
					if(table.getItemCount()<1) targetVarItemsCount = targetVariableInput.length;

					reshapeVariables.add(targetVariableInput);
					targetVariableList.add(targetVarText.getText());
					TableItem tableItem= new TableItem(table, SWT.CENTER);
					tableItem.setText(0, targetVarText.getText());
					for(String s: targetVariableInput){
						reshapeVariableString = reshapeVariableString+" "+s+",";
					}
					tableItem.setText(1, reshapeVariableString);
					targetVarText.setText("targetvar1");
					targetVarList.removeAll();
					targetVarCount++;
				}
			}
		});
		GridData gd_btnAddTargetVariable = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnAddTargetVariable.heightHint = 37;
		btnAddTargetVariable.setLayoutData(gd_btnAddTargetVariable);
		btnAddTargetVariable.setText("Add Target Variable");
		new Label(grpVariablesToBe, SWT.NONE);

		table = new Table(grpVariablesToBe, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 3);
		gd_table.heightHint = 87;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if(table.getSelectionIndex()!=-1){//delete selected row
					deleteTargetBtn.setEnabled(true); //enable delete button
					selectedRowIndex = table.getSelectionIndex();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		tblclmnTargetVariable = new TableColumn(table, SWT.NONE);
		tblclmnTargetVariable.setWidth(135);
		tblclmnTargetVariable.setText("Target Variable");

		tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(162);
		tblclmnNewColumn.setText("Reshape Variables");
		new Label(grpVariablesToBe, SWT.NONE);
		new Label(grpVariablesToBe, SWT.NONE);

		deleteTargetBtn = new Button(grpVariablesToBe, SWT.NONE);
		GridData gd_deleteTargetBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_deleteTargetBtn.heightHint = 38;
		deleteTargetBtn.setLayoutData(gd_deleteTargetBtn);
		deleteTargetBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(Integer.toString(selectedRowIndex));
				System.out.println("removing "+ selectedRowIndex +" from reshapeVariables = "+Integer.toString(reshapeVariables.size()));
				targetVariableList.remove(selectedRowIndex);
				String selectedRowItems[]= reshapeVariables.get(selectedRowIndex);
				for(String s: selectedRowItems){
					activeVarCombo.add(s);
					System.out.println(s);
				}
				reshapeVariables.remove(selectedRowIndex);
				table.remove(selectedRowIndex);
				deleteTargetBtn.setEnabled(false);
				if(table.getItemCount()<1) targetVarItemsCount = 0;
			}
		});
		deleteTargetBtn.setEnabled(false);
		deleteTargetBtn.setText("Delete Target Variable");
		new Label(grpVariablesToBe, SWT.NONE);

		activeVarCombo = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL  | SWT.MULTI);
		activeVarCombo.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		activeVarCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 11));
		activeVarCombo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(activeVarCombo.getSelectionCount()>0){

					btnAddVarRetain.setText("Add");
					btnAddVarRetain.setEnabled(true);
					btnAddTargetVar.setEnabled(true);
				}
			}
		});
		populateActiveVarCombo();

		group = new Group(container, SWT.NONE);
		group.setLayout(new GridLayout(2, false));
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 5);
		gd_group.heightHint = 120;
		gd_group.widthHint = 253;
		group.setLayoutData(gd_group);
		Label lblVariablesToRetain = new Label(group, SWT.NONE);
		lblVariablesToRetain.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblVariablesToRetain.setText("Variables to retain:");

		btnAddVarRetain = new Button(group, SWT.NONE);
		GridData gd_btnAddVarRetain = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddVarRetain.widthHint = 60;
		btnAddVarRetain.setLayoutData(gd_btnAddVarRetain);
		btnAddVarRetain.setEnabled(false);
		btnAddVarRetain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnAddVarRetain.getText().equals("Add")){//if the purpose is to add
					if(activeVarCombo.getSelectionCount()>0){
						dataManipulationManager.moveSelectedStringFromTo( activeVarCombo, varRetainList);
						btnAddVarRetain.setEnabled(false);
						activeVarCombo.remove(activeVarCombo.getSelectionIndices());
						btnAddVarRetain.setEnabled(false);
						btnAddTargetVar.setEnabled(false);
					}
				}else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( varRetainList, activeVarCombo);
				}
				btnAddVarRetain.setEnabled(false);
			}
		});
		btnAddVarRetain.setText("Add");

		varRetainList = new List(group, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
		GridData gd_varRetainList = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_varRetainList.heightHint = 35;
		varRetainList.setLayoutData(gd_varRetainList);
		varRetainList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				dataManipulationManager.moveSelectedStringFromTo( varRetainList, activeVarCombo);
				btnAddVarRetain.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(varRetainList.getSelectionIndex()>-1){
					btnAddVarRetain.setEnabled(true);
					activeVarCombo.setSelection(-1);
					String[] s=varRetainList.getSelection();
					btnAddVarRetain.setText("Remove");
					btnAddVarRetain.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setText("Index Factor:");

		indexFactorText = new Text(group, SWT.BORDER);
		GridData gd_indexFactorText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_indexFactorText.heightHint = 30;
		indexFactorText.setLayoutData(gd_indexFactorText);
		indexFactorText.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				String string = event.text;
				char [] chars = new char [string.length ()];
				string.getChars (0, chars.length, chars, 0);

				if(indexFactorText.getText().isEmpty()){
					try{
						if(Character.isDigit(chars[0])){
							event.doit=false;
							MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, you can't have an integer as the first character of your variable name.");
							return;
						}
					}catch(Exception e){}
				}
			}
		});
		return container;
	}
	protected boolean alreadyUsedasTargetVariable(String string) {
		// TODO Auto-generated method stub
		for(int i=0; i<targetVariableList.size(); i++){
			if(string.equals(targetVariableList.get(i))) return true;
		}
		return false;
	}

	@Override
	protected void okPressed(){
		
		if(!dataManipulationManager.variableNameInputValidation(indexFactorText.getText()).equals("true")){
			MessageDialog.openWarning(getShell(), "Invalid Input", "'"+indexFactorText.getText()+"' is an invalid variable name.");
		}//check if the IndexFactor input variable already exist!
		else if(dataManipulationManager.checkColumnNameIfUsed(indexFactorText.getText(), columnHeaders)){
			MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the variable name \""+ indexFactorText.getText() +"\" already exist.");
		}
		else if(alreadyUsedasTargetVariable(indexFactorText.getText())){//if the targetVariable and the index factor has the same name
			MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, you already used \""+ indexFactorText.getText() +"\" as your target variable name.\n\n Please change your  index factor name.");
		}
		//check if the user input a variable with an integer as the first digit.
		else if(indexFactorText.getText().isEmpty()){//if the targetVariable and the index factor has the same name
			MessageDialog.openWarning(getShell(), "Invalid Input", "Please enter an index factor.");
		}
		else if(varRetainList.getItemCount()<1){//if the targetVariable and the index factor has the same name
			MessageDialog.openWarning(getShell(), "Invalid Input", "Please choose your retain variables from the active variable list.");
		}
		else if(targetVariableList.size()<1){//if the targetVariable and the index factor has the same name
			MessageDialog.openWarning(getShell(), "Invalid Input", "Please enter the variables you want to reshape.");
		}
		else{
			String dataFileName = fileModel.getProjectFile().getAbsolutePath().toString().replaceAll("\\\\+", "/");
			String fileNameExtension = dataManipulationManager.getManipulatedFileNameExtension("_ReshapeToLong", fileModel.getProjectFile().getAbsolutePath());
			String newFileName = dataFileName.replaceAll(".csv", fileNameExtension);

			String[] retainVariables=varRetainList.getItems();
			String[] indexFactor={indexFactorText.getText()};
			String[] targetVariables=targetVariableList.toArray(new String[targetVariableList.size()]);

			DefineLevelsDialog defineLevelsDlg= new DefineLevelsDialog(getShell(), indexFactor[0], targetVariables[0], reshapeVariables.get(0));
			defineLevelsDlg.open();
			String[] newVarCategory=defineLevelsDlg.getLevels();
			if(defineLevelsDlg.getReturnCode()==0){
				ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().toLong(dataFileName, newFileName, reshapeVariables, retainVariables, indexFactor, targetVariables, newVarCategory);
				close();
				ProjectExplorerManager projExpMan =  new ProjectExplorerManager();
				String newFile=fileModel.getProjectFile().getName().replaceAll(".csv", fileNameExtension);
				try{
					TreeItem sortedTree = projExpMan.getTreeNodeByName(fileModel.getParentTreeItem(), newFile);

					if(((ProjectExplorerTreeNodeModel) sortedTree.getData()).getElementInStack() == null){//if the filename created hasn't been opened
						PartStackHandler.execute(projExpMan.createNewProjectExplorerModel((ProjectExplorerTreeNodeModel) fileModel.getParentTreeItem().getData(), newFile));
					}
					else{//re-open file
						PartStackHandler.reOpenPart((ProjectExplorerTreeNodeModel) sortedTree.getData());
					}
				}catch(NullPointerException npe){
					MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Reshape Wide to Long was Unsuccessful", "Failed to reshape variables from data.\n\n");
				}
			}
		}
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Reset",
				true);
		createButton(parent, IDialogConstants.OK_ID, "Next",
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	private void populateActiveVarCombo() {
		for(String s:varInfo){
			String[] tmp = s.split(":");
			activeVarCombo.add(tmp[0]);
			activeVarCombo.setData(tmp[0], tmp[1]);
		}
		columnHeaders = activeVarCombo.getItems();
	}
	@Override
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
			activeVarCombo.removeAll();
			populateActiveVarCombo();
			table.removeAll();
			varRetainList.removeAll();
			targetVarList.removeAll();
			indexFactorText.setText("");
			targetVarItemsCount = 0;
			reshapeVariables.clear();
			targetVariableList.clear();
		}
		super.buttonPressed(buttonId);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(678, 605);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}
