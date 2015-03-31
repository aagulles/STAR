package org.irri.breedingtool.datamanipulation.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.WindowDialogControlUtil;

public class AggregateDialog extends Dialog {

	private ArrayList<String> varInfo;
	private ArrayList<String> functionList = new ArrayList<String>();
	private Composite container;
	private List numVarCombo;
	private List factorVarCombo;
	private List varsToAggregate;
	private List groupingFactors;
	private List functionCombo;
	private List functionsCombo;
	private boolean toAppend = true;
	private ProjectExplorerTreeNodeModel fileModel;
	private String[]inputNumVariables;
	private String[]inputFactorVariables;
	private String[]function;
	private String[]functionItems={"minimum", "maximum", "mean", "median", "sum", "variance", "standard deviation"};
	private String[]functionEquiv={"min", "max", "mean", "median", "sum", "variance", "standard deviation"};
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AggregateDialog(Shell parentShell, ArrayList<String> varInfo, ProjectExplorerTreeNodeModel fileModel) {
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
		parent.getShell().setText("Aggregate Data: "+dataManipulationManager.getDisplayFileName(fileModel.getProjectFile().getAbsolutePath()));
		container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 3;
		
		Label lblAvailableVariables = new Label(container, SWT.NONE);
		lblAvailableVariables.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblAvailableVariables.setText("Available Variable(s):");
		new Label(container, SWT.NONE);
		
		Label lblVariablesToAggregate = new Label(container, SWT.NONE);
		lblVariablesToAggregate.setText("Variable(s) to aggregate:");
		
		numVarCombo = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_numVarCombo = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_numVarCombo.widthHint = 200;
		gd_numVarCombo.heightHint = 49;
		numVarCombo.setLayoutData(gd_numVarCombo);
		populateNumVarCombo();
		numVarCombo.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedNumVars[]= numVarCombo.getSelection();
				dataManipulationManager.moveSelectedStringFromTo( numVarCombo, varsToAggregate);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		Button addVarBtn = new Button(container, SWT.NONE);
		addVarBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedNumVars[]= numVarCombo.getSelection();
				for(int i=0; i< selectedNumVars.length; i++){
					varsToAggregate.add(selectedNumVars[i]);
				}
				numVarCombo.remove(numVarCombo.getSelectionIndices());
			}
		});
		GridData gd_addVarBtn = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_addVarBtn.widthHint = 72;
		addVarBtn.setLayoutData(gd_addVarBtn);
		addVarBtn.setText("Add");
		
		varsToAggregate = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_varsToAggregate = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_varsToAggregate.heightHint = 48;
		gd_varsToAggregate.widthHint = 197;
		varsToAggregate.setLayoutData(gd_varsToAggregate);
		varsToAggregate.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedNumVars[]= varsToAggregate.getSelection();
				dataManipulationManager.moveSelectedStringFromTo( varsToAggregate, numVarCombo);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		Label lblAvailableFactors = new Label(container, SWT.NONE);
		lblAvailableFactors.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblAvailableFactors.setText("Available Factor(s):");
		new Label(container, SWT.NONE);
		
		Label lblGroupingFactors = new Label(container, SWT.NONE);
		lblGroupingFactors.setText("Grouping Factor(s):");
		
		factorVarCombo = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_factorVarCombo = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_factorVarCombo.heightHint = 52;
		gd_factorVarCombo.widthHint = 200;
		factorVarCombo.setLayoutData(gd_factorVarCombo);
		populateFactorVarCombo();
		factorVarCombo.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedNumVars[]= factorVarCombo.getSelection();
				dataManipulationManager.moveSelectedStringFromTo( factorVarCombo, groupingFactors);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Button addFactorBtn = new Button(container, SWT.NONE);
		addFactorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedFactors[]= factorVarCombo.getSelection();
				for(int i=0; i< selectedFactors.length; i++){
					groupingFactors.add(selectedFactors[i]);
				}
				factorVarCombo.remove(factorVarCombo.getSelectionIndices());
			}
		});
		GridData gd_addFactorBtn = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_addFactorBtn.widthHint = 72;
		addFactorBtn.setLayoutData(gd_addFactorBtn);
		addFactorBtn.setText("Add");
		
		groupingFactors = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_groupingFactors = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_groupingFactors.heightHint = 53;
		gd_groupingFactors.widthHint = 197;
		groupingFactors.setLayoutData(gd_groupingFactors);
		groupingFactors.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedNumVars[]= groupingFactors.getSelection();
				dataManipulationManager.moveSelectedStringFromTo( groupingFactors, factorVarCombo);
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		Label lblAvailableFunctions = new Label(container, SWT.NONE);
		lblAvailableFunctions.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblAvailableFunctions.setText("Available Function(s):");
		new Label(container, SWT.NONE);
		
		Label lblFunctions = new Label(container, SWT.NONE);
		lblFunctions.setText("Functions:");
		
		functionCombo = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_functionCombo = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_functionCombo.heightHint = 49;
		gd_functionCombo.widthHint = 200;
		functionCombo.setLayoutData(gd_functionCombo);
		functionCombo.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedFunction[]= functionCombo.getSelection();
				for(int i=0; i< selectedFunction.length; i++){
					functionsCombo.add(selectedFunction[i]);
					functionList.add((String)functionCombo.getData(selectedFunction[i]));
				}
				functionCombo.remove(functionCombo.getSelectionIndices());
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		populateFunctionCombo();
		Button addFunctionBtn = new Button(container, SWT.NONE);
		addFunctionBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedFunction[]= functionCombo.getSelection();
				for(int i=0; i< selectedFunction.length; i++){
					functionsCombo.add(selectedFunction[i]);
					functionList.add((String)functionCombo.getData(selectedFunction[i]));
				}
				functionCombo.remove(functionCombo.getSelectionIndices());
			}
		});
		GridData gd_addFunctionBtn = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_addFunctionBtn.widthHint = 71;
		addFunctionBtn.setLayoutData(gd_addFunctionBtn);
		addFunctionBtn.setText("Add");
		
		functionsCombo = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_functionsCombo = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_functionsCombo.heightHint = 51;
		gd_functionsCombo.widthHint = 197;
		functionsCombo.setLayoutData(gd_functionsCombo);
		functionsCombo.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedFunction[]= functionsCombo.getSelection();
				for(int i=0; i< selectedFunction.length; i++){
					functionCombo.add(selectedFunction[i]);
					System.out.println("removing: "+ Integer.toString(functionCombo.getSelectionIndex()));
					functionList.remove(functionsCombo.getSelectionIndex());
				}
				functionsCombo.remove(functionsCombo.getSelectionIndices());
			}
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Group grpSave = new Group(container, SWT.NONE);
		grpSave.setText("Save");
		GridData gd_grpSave = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_grpSave.heightHint = 50;
		gd_grpSave.widthHint = 416;
		grpSave.setLayoutData(gd_grpSave);
		
		Button btnAddAggregatedVariables = new Button(grpSave, SWT.RADIO);
		btnAddAggregatedVariables.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toAppend = true;
			}
		});
		btnAddAggregatedVariables.setSelection(true);
		btnAddAggregatedVariables.setText("Add aggregated variables to the active data");
		btnAddAggregatedVariables.setBounds(21, 20, 372, 16);
		
		Button btnCreateNewData = new Button(grpSave, SWT.RADIO);
		btnCreateNewData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toAppend=false;
			}
		});
		btnCreateNewData.setText("Create new data file containing the aggregated variables only");
		btnCreateNewData.setBounds(21, 42, 372, 16);

		return container;
	}

	private void populateNumVarCombo() {
		for(String s:varInfo){
			String[] tmp = s.split(":");
			if(tmp[1].toLowerCase().equals("numeric")){//If numeric
				numVarCombo.add(tmp[0]);
				numVarCombo.setData(tmp[0], tmp[1]);
			}
		}
	}
	
	private void populateFactorVarCombo() {
		for(String s:varInfo){
			String[] tmp = s.split(":");
			if(tmp[1].toLowerCase().equals("factor")){//If factor
				factorVarCombo.add(tmp[0]);
				factorVarCombo.setData(tmp[0], tmp[1]);
			}
		}
	}
	
	private void populateFunctionCombo() {
		int ctr=0;
		for(String s:functionItems){
				functionCombo.add(functionItems[ctr]);
				functionCombo.setData(functionItems[ctr], functionEquiv[ctr]);
			ctr++;
		}
	}
	
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Reset", true);
		createButton(parent, IDialogConstants.OK_ID, "OK",
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
	 protected void buttonPressed(int buttonId) { //wen Reset button is pressed
	      if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
	    	  functionsCombo.removeAll();
	    	  numVarCombo.removeAll();
	    	  factorVarCombo.removeAll();
	    	  functionCombo.removeAll();
	    	  varsToAggregate.removeAll();
	    	  groupingFactors.removeAll();
	    	  functionList.clear();
	    	  populateNumVarCombo();
	    	  populateFactorVarCombo();
	    	  populateFunctionCombo();
	      }
    super.buttonPressed(buttonId);
	}
	@Override
	protected void okPressed(){
		String fileName = fileModel.getProjectFile().getAbsolutePath().toString().replaceAll("\\\\+", "/");
		String newFileName = fileName.replaceAll(".csv", "_aggregate.csv");
		
		//CALL THE RJAVA FUNCTION HERE
		inputNumVariables = varsToAggregate.getItems();
		inputFactorVariables = groupingFactors.getItems();
		function = functionList.toArray(new String[functionList.size()]);

		if(function.length <=0 || inputNumVariables.length <=0 || inputFactorVariables.length <=0){
			MessageDialog.openWarning(getShell(), "Invalid Input", "Please make sure that you have entered atleast one for each: variable, factor and function. Thank you.");
		}
		else{
			if(!toAppend){//create new file
				String fileNameExtension = dataManipulationManager.getManipulatedFileNameExtension("_aggregate", fileModel.getProjectFile().getAbsolutePath());
				String newFile =  fileModel.getProjectFile().getName().replaceAll(".csv", fileNameExtension);
				ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().aggregateData(fileName, newFileName, inputNumVariables, inputFactorVariables, function, toAppend);		
				close();
				ProjectExplorerManager projExpMan =  new ProjectExplorerManager();
//				TreeItem sortedTree = projExpMan.getTreeNodeByName(fileModel.getParentTreeItem(), newFile);
		 			
		 		PartStackHandler.execute(projExpMan.createNewProjectExplorerModel((ProjectExplorerTreeNodeModel) fileModel.getParentTreeItem().getData(), newFile));
		 			
	 		}
			else{//overwrite current file
				newFileName = fileName;
				PartStackHandler.setPartModified(fileModel.getProjectFile().getAbsolutePath());
				ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().aggregateData(fileName, newFileName, inputNumVariables, inputFactorVariables, function, toAppend);
				Table fileTable = DataManipulationManager.getActiveTable(fileModel.getProjectFile().getAbsolutePath());
				DataManipulationManager.removeTable(fileTable);
				close();
				WindowDialogControlUtil.closeAllWindowDialogOfFile(fileModel.getProjectFile().getAbsolutePath());
	 			System.out.println("overwrite and reopen");
				PartStackHandler.reOpenPart(fileModel);
			}
		
		}
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(679, 503);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}
