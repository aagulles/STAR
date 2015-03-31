package org.irri.breedingtool.datamanipulation.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.custom.CCombo;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.WindowDialogControlUtil;

public class CreateNewVarDialog extends Dialog {
	private Text varNameText;
	private Combo values_combo;
	private Table table;
	private String formulaList[]={"Logarithm (base 10)", "Natural Logarithm", "Square root", "Power", "Exponential", "Standardized"};
	private String[] formulaListEquiv={"log", "ln", "sqrt", "power", "exp", "standardize"};
	private Group group;
	private Shell parentShell;
	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> checkedTableItems = new ArrayList<String>();
	DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private CCombo  comboVar, comboFormula;
	private ArrayList<String> varInfo;
	private String[] columnHeaders;
	private Composite container;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public CreateNewVarDialog(Shell parentShell, ArrayList<String> varInfo, ProjectExplorerTreeNodeModel fileModel) {
		super(parentShell);
		this.varInfo = varInfo;
		columnHeaders = new String[varInfo.size()];
		this.parentShell=parentShell;
		this.fileModel = fileModel;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		parent.getShell().setText("Create New Variable: "+dataManipulationManager.getDisplayFileName(fileModel.getProjectFile().getAbsolutePath()));
		container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
		lblNewLabel.setText("Name:");

		varNameText = new Text(container, SWT.BORDER);
		varNameText.setText("newVar");
		GridData gd_varNameText = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_varNameText.widthHint = 96;
		varNameText.setLayoutData(gd_varNameText);
		varNameText.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				String string = event.text;
				char [] chars = new char [string.length ()];
				string.getChars (0, chars.length, chars, 0);

				if(varNameText.getText().isEmpty()){
					if(Character.isDigit(chars[0])){
						event.doit=false;
						MessageDialog.openWarning(parent.getShell(), "Invalid Input", "Sorry, you can't have an integer as the first character of your variable name.");
						return;
					}
				}
			}
		});

		Label lblValues = new Label(container, SWT.NONE);
		GridData gd_lblValues = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblValues.widthHint = 45;
		lblValues.setLayoutData(gd_lblValues);
		lblValues.setText("Values:");
		lblValues.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));

		values_combo = new Combo(container, SWT.READ_ONLY);
		values_combo.setItems(new String[] {"Concatenate Columns", "Transform"});
		GridData gd_values_combo = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_values_combo.widthHint = 136;
		values_combo.select(1);
		values_combo.setLayoutData(gd_values_combo);
		group = new Group(container, SWT.NONE);
		group.setLayout(null);
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_group.heightHint = 230;
		gd_group.widthHint = 365;
		group.setLayoutData(gd_group);

		//default selected
		Label lblSpecifyTransformation = new Label(group, SWT.NONE);
		lblSpecifyTransformation.setBounds(10, 10, 228, 25);
		lblSpecifyTransformation.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
		lblSpecifyTransformation.setText("Specify transformation to be performed:");

		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
		lblNewLabel_1.setBounds(22, 43, 109, 25);
		lblNewLabel_1.setText("Input Variable:");

		comboVar = new CCombo(group, SWT.BORDER | SWT.READ_ONLY);
		comboVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		comboVar.setEditable(false);
		comboVar.setBounds(146, 47, 156, 21);
		populateNumVarCombo();		
		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
		lblNewLabel_2.setBounds(21, 79, 110, 21);
		lblNewLabel_2.setText("Formula:");

		comboFormula = new CCombo(group, SWT.BORDER | SWT.READ_ONLY);
		comboFormula.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		comboFormula.setBounds(146, 79, 156, 21);
		comboFormula.setItems(formulaList);

		values_combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(values_combo.getSelectionIndex()==1){ // if transform
					for (Control kid : group.getChildren()) {
						kid.dispose();
					}
					Label lblSpecifyTransformation = new Label(group, SWT.NONE);
					lblSpecifyTransformation.setBounds(10, 10, 228, 25);
					lblSpecifyTransformation.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
					lblSpecifyTransformation.setText("Specify transformation to be performed:");

					Label lblNewLabel_1 = new Label(group, SWT.NONE);
					lblNewLabel_1.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
					lblNewLabel_1.setBounds(22, 43, 85, 25);
					lblNewLabel_1.setText("Input Variable:");

					comboVar = new CCombo(group, SWT.BORDER | SWT.READ_ONLY);
					comboVar.setBounds(112, 41, 156, 21);
					comboVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					populateNumVarCombo();

					Label lblNewLabel_2 = new Label(group, SWT.NONE);
					lblNewLabel_2.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
					lblNewLabel_2.setBounds(21, 79, 49, 13);
					lblNewLabel_2.setText("Formula:");

					comboFormula = new CCombo(group, SWT.BORDER | SWT.READ_ONLY);
					comboFormula.setBounds(112, 79, 156, 21);
					comboFormula.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					comboFormula.setItems(formulaList);

					group.update();
					container.update();
				}
				else{ // else, concatenate
					for (Control kid : group.getChildren()) {
						kid.dispose();
					}
					Label lblSpecifyVariables = new Label(group, SWT.NONE);
					lblSpecifyVariables.setBounds(10, 10, 228, 25);
					lblSpecifyVariables.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
					lblSpecifyVariables.setText("Specify variables:");

					table = new Table(group,  SWT.CHECK | SWT.BORDER );
					table.setBounds(10, 53, 250, 183);
					table.setHeaderVisible(true);
					table.setLinesVisible(true);

					TableColumn column = new TableColumn(table, SWT.CENTER);
					column.setText("Column Names");
					column.setWidth(245);

					for( int i = 0; i < columnHeaders.length; i++ ){
						TableItem tableItem = new TableItem(table, SWT.CENTER);
						tableItem.setText(0, columnHeaders[i]);
					}

					table.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							TableItem tableItem;
							if (event.detail == SWT.CHECK) {
								tableItem = (TableItem) event.item;
								try{
									if(tableItem.getData("checked").equals("true")){//the data Item was unselected
										checkedTableItems.remove(tableItem.getText());
										tableItem.setData("checked", "false");
										System.out.println("unchecked " + event.item.toString());
									}else{//the item was checked
										tableItem.setData("checked", "true");//select this item
										checkedTableItems.add(tableItem.getText());
										System.out.println("checked " + event.item.toString());
									}
								}catch(NullPointerException npe){//If Its the item's first time to be selected
									event.item.setData("checked", "true");//select this item
									checkedTableItems.add(tableItem.getText());
									System.out.println("checked " + event.item.toString());
								}
							}
						}
					});
					group.update();
					container.update();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		return container;
	}

	@Override
	protected void okPressed(){
		//CALL THE RJAVA FUNCTION HERE
		String string = varNameText.getText();
		char [] chars = new char [string.length ()];
		string.getChars (0, chars.length, chars, 0);

		if(dataManipulationManager.checkColumnNameIfUsed(varNameText.getText(), columnHeaders )){ // if the entered variable name is already used.
			MessageDialog.openWarning(parentShell.getShell(), "Invalid Input", "Sorry, the variable name \""+ varNameText.getText() +"\" already exist.");
		}
		else if(varNameText.getText().isEmpty()){ // if the user didn't enter a variable name
			MessageDialog.openWarning(parentShell.getShell(), "Invalid Input", "Please enter a variable name.");
		}
		else if(!dataManipulationManager.variableNameInputValidation(varNameText.getText()).equals("true")){//if the first character of varNameText is a digit
			MessageDialog.openWarning(parentShell.getShell(), "Invalid Input", dataManipulationManager.variableNameInputValidation(varNameText.getText()));
		}
		else{
			String dataFileName = fileModel.getProjectFile().getAbsolutePath().toString().replaceAll("\\\\+", "/");
			String newFileName = dataFileName; //overwrite
			String newVariableName=varNameText.getText();
			Table fileTable = DataManipulationManager.getActiveTable(fileModel.getProjectFile().getAbsolutePath());
			if(values_combo.getSelectionIndex()==0){//If - concatenate
				if(checkedTableItems.isEmpty()){
					MessageDialog.openWarning(parentShell.getShell(), "Invalid Input", "Please select the variables to be combined.");
				}else if(checkedTableItems.size()<2){
					MessageDialog.openWarning(parentShell.getShell(), "Invalid Input", "Please select at least two variables to be combined.");
				}else{
					String[] inputVariables = checkedTableItems.toArray(new String[checkedTableItems.size()]);
					ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().combineFactorLevels(dataFileName, newFileName, inputVariables, newVariableName);
					close();
					DataManipulationManager.removeTable(fileTable);
					WindowDialogControlUtil.closeAllWindowDialogOfFile(fileModel.getProjectFile().getAbsolutePath());
					PartStackHandler.reOpenPart(fileModel);
				}
			}
			else{//else - transform
				if(comboVar.getText().isEmpty()){ // if there's no selected input variable
					MessageDialog.openWarning(parentShell.getShell(), "Invalid Input", "Please select the input variable to be transformed.");
				}else if(comboFormula.getSelectionIndex()<0){//if there's no selected transformation formula
					MessageDialog.openWarning(parentShell.getShell(), "Invalid Input", "Please select the transformation formula to be used.");
				}else{	
					String inputVariable=comboVar.getText();
					String formula=formulaListEquiv[comboFormula.getSelectionIndex()];
					ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().dataTransformation(dataFileName, newFileName, newVariableName, inputVariable,formula);
					close();
					try{
						DataManipulationManager.removeTable(fileTable);
						WindowDialogControlUtil.closeAllWindowDialogOfFile(fileModel.getProjectFile().getAbsolutePath());
						PartStackHandler.reOpenPart(fileModel);
					}catch(NullPointerException npe){
						MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Failed to create new variable in file.", "Please specify the correct variables." );
					}
				}
			}
		}
	}
	private void populateNumVarCombo(){
		int ctr=0;
		for(String s:varInfo){
			String[] tmp = s.split(":");
			columnHeaders[ctr] = tmp[0];
			if(tmp[1].toLowerCase().equals("numeric")){//If numeric
				comboVar.add(tmp[0]);
				comboVar.setData(tmp[0], tmp[1]);
			}
			ctr++;
		}
	}
	@Override
	protected void buttonPressed(int buttonId) { //when Reset button is pressed
		if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
			varNameText.setText("newVar");
			values_combo.select(1);
			checkedTableItems.clear();
			for (Control kid : group.getChildren()) {
				kid.dispose();
			}
			Label lblSpecifyTransformation = new Label(group, SWT.NONE);
			lblSpecifyTransformation.setBounds(10, 10, 228, 25);
			lblSpecifyTransformation.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
			lblSpecifyTransformation.setText("Specify transformation to be performed:");

			Label lblNewLabel_1 = new Label(group, SWT.NONE);
			lblNewLabel_1.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
			lblNewLabel_1.setBounds(22, 43, 85, 25);
			lblNewLabel_1.setText("Input Variable:");

			comboVar = new CCombo(group, SWT.BORDER | SWT.READ_ONLY);
			comboVar.setBounds(112, 41, 156, 21);
			comboVar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			populateNumVarCombo();

			Label lblNewLabel_2 = new Label(group, SWT.NONE);
			lblNewLabel_2.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
			lblNewLabel_2.setBounds(21, 79, 49, 13);
			lblNewLabel_2.setText("Formula:");

			comboFormula = new CCombo(group, SWT.BORDER | SWT.READ_ONLY);
			comboFormula.setBounds(112, 79, 156, 21);
			comboFormula.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			comboFormula.setItems(formulaList);

			group.update();
			container.update();
		}
		super.buttonPressed(buttonId);
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Reset", true);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}


	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(471, 483);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}
