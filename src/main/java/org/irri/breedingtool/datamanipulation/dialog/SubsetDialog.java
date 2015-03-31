package org.irri.breedingtool.datamanipulation.dialog;

import java.io.File;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.rjava.manager.RJavaManager;
import org.eclipse.swt.layout.GridLayout;

public class SubsetDialog extends Dialog {
	private Text conditionValue;
	private String operatorList[]={"equal (==)", "not equal (!=)","at least (>=)","at most (<=)","greater than (>)", "less than (<)", "within range"};
	private String operation[]={"==", "!=",">=","<=",">", "<", "?"};
	private String operationWords[]={"is equal to", "is not equal to","is at least","is at most","is greater than", "is less than", "is within the range of"};
	private ArrayList<String> subsetConditions= new ArrayList<String>();
	private ArrayList<String> conditionListContent= new ArrayList<String>();
	private List conditionList;
	private Combo comboVariable;
	private Combo comboOperator;
	private Button btnCondition;
	private Button deleteBtn;
	private ArrayList<String> varInfo;
	private ProjectExplorerTreeNodeModel fileModel;
	private Shell shell;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();


	/**
	 * Create the dialog.
	 * @param parentShell
	 * @param tableFile 
	 */
	public SubsetDialog(Shell parentShell, ArrayList<String> varInfo, ProjectExplorerTreeNodeModel fileModel){
		super(parentShell);
		this.varInfo = varInfo;
		this.fileModel = fileModel;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		container.setLayout(new GridLayout(1, false));
		Group grpSpecifyConditions = new Group(container, SWT.NONE);
		grpSpecifyConditions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSpecifyConditions.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		grpSpecifyConditions.setText("Specify Condition:");
		grpSpecifyConditions.setLayout(new GridLayout(4, false));
		//		comboVariable.setItems(columnHeaders);

		Label lblVariable = new Label(grpSpecifyConditions, SWT.NONE);
		lblVariable.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblVariable.setText("Variable:");

		Label lblOperator = new Label(grpSpecifyConditions, SWT.NONE);
		lblOperator.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblOperator.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblOperator.setText("Operator:");
		Label lblValuelabel = new Label(grpSpecifyConditions, SWT.NONE);
		lblValuelabel.setText("Value/Level:");
		lblValuelabel.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(grpSpecifyConditions, SWT.NONE);

		comboVariable = new Combo(grpSpecifyConditions, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd_comboVariable = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_comboVariable.widthHint = 123;
		comboVariable.setLayoutData(gd_comboVariable);
		populateComboVariable();
		comboOperator = new Combo(grpSpecifyConditions, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd_comboOperator = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_comboOperator.widthHint = 133;
		comboOperator.setLayoutData(gd_comboOperator);
		comboOperator.setItems(operatorList);

		conditionValue = new Text(grpSpecifyConditions, SWT.BORDER);
		GridData gd_conditionValue = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_conditionValue.widthHint = 108;
		conditionValue.setLayoutData(gd_conditionValue);
		conditionValue.setToolTipText("separate different values with a comma \",\"");
		btnCondition = new Button(grpSpecifyConditions, SWT.NONE);
		btnCondition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCondition.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String newCondition="";
				String addedCondition="";
				if(!comboVariable.getText().isEmpty() && !comboOperator.getText().isEmpty() && !conditionValue.getText().isEmpty()){
					if(comboOperator.getSelectionIndex()==6){//If within range is chosen
						//						isWithinRangeChosen=true;
						boolean addCondition = true;
						int dotCtr=0;
						String[] inputValues = conditionValue.getText().split(",");
						if(inputValues.length==2 ){
							String max;
							String min;
							try{
								if(Double.parseDouble(inputValues[0])<Double.parseDouble(inputValues[1])){
									max=inputValues[1];
									min=inputValues[0];
								}
								else{
									min=inputValues[1];
									max=inputValues[0];
								}
								//add lowerLimit condition
								addedCondition= comboVariable.getText()+":>=:"+ min +":"+ comboVariable.getData(comboVariable.getText());
								newCondition= comboVariable.getText()+" is at least "+ min+".";
								subsetConditions.add(addedCondition);
								conditionListContent.add(newCondition);

								//add upperLimit condition
								addedCondition= comboVariable.getText()+":<=:"+ max +":"+ comboVariable.getData(comboVariable.getText());
								newCondition= comboVariable.getText()+" is at most "+ max+".";
								subsetConditions.add(addedCondition);
								conditionListContent.add(newCondition);

								conditionList.setItems(conditionListContent.toArray(new String[conditionListContent.size()]));
								//Reset VALUES
								comboVariable.deselectAll();
								comboOperator.deselectAll();
								conditionValue.setText("");
								deleteBtn.setEnabled(false);
							}catch(NumberFormatException npe){
								MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Invalid Input", "The 'within range' condition requires numeric input.\n\n"+
										"Valid input values: lowerLimit,upperLimit\n\nExample value: 0,1");
							}
						}
						else MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Invalid Input for 'within range'", "Valid input values: lowerLimit,upperLimit\n\nExample value: 0,1" );
					}
					else{
						boolean addCondition = true;
						if(comboVariable.getData(comboVariable.getText()).equals("Numeric")){
							int dotCtr=0;
							String values[] = conditionValue.getText().split(",");
							for(String s: values){
								try{
									Double num = Double.parseDouble(s);
								}catch(NumberFormatException npe){
									MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Invalid Input", "'"+ comboVariable.getText() +"' is a numeric variable.");
									addCondition = false;
								}
							}
						}
						else{
							if(comboOperator.getSelectionIndex()>1){
								String values[] = conditionValue.getText().split(",");
								for(String s: values){
									try{
										Double num = Double.parseDouble(s);
									}catch(NumberFormatException npe){
										MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Invalid operator input", "The operator '"+ operation[comboOperator.getSelectionIndex()] +"' can only be used for numeric input conditions.");
										addCondition = false;
									}
								}
							}
						}
						
						if(addCondition){
							addedCondition= comboVariable.getText()+":"+ operation[comboOperator.getSelectionIndex()]+":"+ conditionValue.getText()+":"+ comboVariable.getData(comboVariable.getText());
							newCondition= comboVariable.getText()+" "+ operationWords[comboOperator.getSelectionIndex()]+" "+ conditionValue.getText()+".";
							subsetConditions.add(addedCondition);
							conditionListContent.add(newCondition);

							conditionList.setItems(conditionListContent.toArray(new String[conditionListContent.size()]));
							//Reset VALUES
							comboVariable.deselectAll();
							comboOperator.deselectAll();
							conditionValue.setText("");
							deleteBtn.setEnabled(false);
						}
					}
				}
				else{//if the user left some input fields empty
					MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Missing Input", "Please don't leave the variable, operator, and value/level fields empty." );
				}
			}
		});
		btnCondition.setText("Add Condition");

		Label lblConditions = new Label(grpSpecifyConditions, SWT.NONE);
		lblConditions.setText("Conditions:");
		lblConditions.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		new Label(grpSpecifyConditions, SWT.NONE);
		new Label(grpSpecifyConditions, SWT.NONE);
		new Label(grpSpecifyConditions, SWT.NONE);

		conditionList = new List(grpSpecifyConditions, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
		GridData gd_conditionList = new GridData(SWT.FILL, SWT.FILL, false, true, 4, 1);
		gd_conditionList.heightHint = 110;
		gd_conditionList.widthHint = 371;
		conditionList.setLayoutData(gd_conditionList);
		new Label(grpSpecifyConditions, SWT.NONE);
		new Label(grpSpecifyConditions, SWT.NONE);
		new Label(grpSpecifyConditions, SWT.NONE);


		deleteBtn = new Button(grpSpecifyConditions, SWT.NONE);
		deleteBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		deleteBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//				int[] selectedItemIndices = conditionList.getSelectionIndices();
				//				for(int i: selectedItemIndices){
				//				System.out.println("remove: "+Integer.toString(i)+ " : "+ subsetConditions.get(i));
				//				subsetConditions.remove(i);
				//				conditionListContent.remove(i);
				//				}
				subsetConditions.remove(conditionList.getSelectionIndex());
				conditionListContent.remove(conditionList.getSelectionIndex());
				conditionList.removeAll();
				conditionList.setItems(conditionListContent.toArray(new String[conditionListContent.size()]));
				deleteBtn.setEnabled(false);

			}
		});
		deleteBtn.setEnabled(false);
		deleteBtn.setText("Delete");

		conditionList.addListener (SWT.MouseDown, new Listener(){
			public void handleEvent(Event event){
				if(conditionList.getSelectionCount() > 0 ) deleteBtn.setEnabled(true);
			} 
		});

		parent.getShell().setText("Create Data Subset: "+dataManipulationManager.getDisplayFileName(fileModel.getProjectFile().getAbsolutePath()));
		return container;
	}

	private void populateComboVariable() {
		for(String s:varInfo){
			String[] tmp = s.split(":");
			System.out.println(tmp[0]);
			comboVariable.add(tmp[0]);
			comboVariable.setData(tmp[0], tmp[1]);
		}
	}

	@Override
	protected void okPressed(){
		//CALL THE RJAVA FUNCTION HERE
		if(subsetConditions.isEmpty()){
			MessageDialog.openWarning(getShell(), "Empty Field", "Please enter your subset conditions.");
		}else{
			String fileName = fileModel.getProjectFile().getAbsolutePath().toString().replaceAll("\\\\+", "/");
			String fileNameExtension = dataManipulationManager.getManipulatedFileNameExtension("_subset", fileModel.getProjectFile().getAbsolutePath());
			String newFileName = fileName.replaceAll(".csv", fileNameExtension);
			String newFile=fileModel.getProjectFile().getName().replaceAll(".csv", fileNameExtension);
			for(String s: subsetConditions){
			System.out.println("subsetConditions:");
			System.out.println(s);
			}
			ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().subSet(fileName, newFileName, subsetConditions);

			close();

			ProjectExplorerManager projExpMan =  new ProjectExplorerManager();
			TreeItem sortedTree = projExpMan.getTreeNodeByName(fileModel.getParentTreeItem(), newFile);

			try{
				if(((ProjectExplorerTreeNodeModel) sortedTree.getData()).getElementInStack() == null){//if the filename created hasn't been opened
					PartStackHandler.execute(projExpMan.createNewProjectExplorerModel((ProjectExplorerTreeNodeModel) fileModel.getParentTreeItem().getData(), newFile));
				}
				else{//re-open file
					PartStackHandler.reOpenPart((ProjectExplorerTreeNodeModel) sortedTree.getData());
				}
			}catch(NullPointerException npe){
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Create Subset was Unsuccessful", "Failed to create a subset from file.\n\n" +
						"Are you sure you specified valid conditions?" );
			}
		}
	}

	@Override
	protected void buttonPressed(int buttonId) { //wen Reset button is pressed
		if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
			subsetConditions.removeAll(subsetConditions);
			conditionListContent.removeAll(conditionListContent);
			conditionList.setItems(conditionListContent.toArray(new String[conditionListContent.size()]));

			//			  System.out.println("Combo Text:"+comboVariable.getText()+ comboVariable.getData(comboVariable.getText()).toString());

		}
		super.buttonPressed(buttonId);
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Reset", true);
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
		return new Point(611, 419);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
}
