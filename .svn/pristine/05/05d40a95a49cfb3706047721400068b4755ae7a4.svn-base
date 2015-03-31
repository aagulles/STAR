package org.irri.breedingtool.datamanipulation.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.GridData;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ReshapeLongToWideDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private List activeVarCombo, reshapeCombo, retainCombo, indexCombo;
	private Button addReshapeBtn, addRetainBtn, addIndexBtn;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ReshapeLongToWideDialog(Shell parentShell, ArrayList<String> varInfo, ProjectExplorerTreeNodeModel fileModel) {
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
		parent.getShell().setText("Reshape Data (Long to Wide): "+dataManipulationManager.getDisplayFileName(fileModel.getProjectFile().getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 3;

		Label lblVariablesInThe = new Label(container, SWT.NONE);
		lblVariablesInThe.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblVariablesInThe.setText("Variables in the active data");
		new Label(container, SWT.NONE);

		Label lblVariablesToReshape = new Label(container, SWT.NONE);
		lblVariablesToReshape.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblVariablesToReshape.setText("Variable(s) to Reshape");

		activeVarCombo= new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_list_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5);
		gd_list_1.widthHint = 166;
		activeVarCombo.setLayoutData(gd_list_1);
		populateActiveVarCombo();
		activeVarCombo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableButtons(true);
			}
		});

		addReshapeBtn = new Button(container, SWT.NONE); //RESHAPE
		addReshapeBtn.setEnabled(false);
		addReshapeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(addReshapeBtn.getText().equals("Add")){//if the purpose is to add
					if(activeVarCombo.getSelectionCount()>0){
						dataManipulationManager.moveSelectedStringFromTo( activeVarCombo, reshapeCombo);
						if(activeVarCombo.getItemCount()<1) enableButtons(false);
					}
				}else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( reshapeCombo, activeVarCombo);
				}
				addReshapeBtn.setEnabled(false);
			}
		});
		GridData gd_addReshapeBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addReshapeBtn.widthHint = 62;
		addReshapeBtn.setLayoutData(gd_addReshapeBtn);
		addReshapeBtn.setText("Add");

		reshapeCombo = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
		GridData gd_reshapeCombo = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_reshapeCombo.heightHint = 55;
		gd_reshapeCombo.widthHint = 180;
		reshapeCombo.setLayoutData(gd_reshapeCombo);
		reshapeCombo.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				dataManipulationManager.moveSelectedStringFromTo( reshapeCombo, activeVarCombo);
				addReshapeBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(reshapeCombo.getSelectionIndex()>-1){
					addReshapeBtn.setEnabled(true);
					activeVarCombo.setSelection(-1);
					String[] s=reshapeCombo.getSelection();
					addReshapeBtn.setText("Remove");
					addReshapeBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Label(container, SWT.NONE);

		Label lblVariablesToRetain = new Label(container, SWT.NONE);
		lblVariablesToRetain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblVariablesToRetain.setText("Variable(s) to Retain");

		addRetainBtn = new Button(container, SWT.NONE);//RETAIN
		addRetainBtn.setEnabled(false);
		addRetainBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(addRetainBtn.getText().equals("Add")){//if the purpose is to add
					if(activeVarCombo.getSelectionCount()>0){
						dataManipulationManager.moveSelectedStringFromTo( activeVarCombo, retainCombo);
						if(activeVarCombo.getItemCount()<1) enableButtons(false);
					}
				}else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( retainCombo, activeVarCombo);
				}
				addRetainBtn.setEnabled(false);
			}
		});
		addRetainBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addRetainBtn.setText("Add");

		retainCombo = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
		GridData gd_retainCombo = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_retainCombo.heightHint = 49;
		gd_retainCombo.widthHint = 180;
		retainCombo.setLayoutData(gd_retainCombo);
		retainCombo.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				dataManipulationManager.moveSelectedStringFromTo( retainCombo, activeVarCombo);
				addRetainBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(retainCombo.getSelectionIndex()>-1){
					addRetainBtn.setEnabled(true);
					activeVarCombo.setSelection(-1);
					String[] s=retainCombo.getSelection();
					addRetainBtn.setText("Remove");
					addRetainBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Label(container, SWT.NONE);

		Label lblIndexFactor = new Label(container, SWT.NONE);
		lblIndexFactor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblIndexFactor.setText("Index Factor:");

		addIndexBtn = new Button(container, SWT.NONE);//INDEX FACTOR
		addIndexBtn.setEnabled(false);
		addIndexBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(addIndexBtn.getText().equals("Add")){//if the purpose is to add
					if(activeVarCombo.getSelectionCount()>0){
						dataManipulationManager.moveSelectedStringFromTo( activeVarCombo, indexCombo);
						if(activeVarCombo.getItemCount()<1) enableButtons(false);
					}
				}else{//if the purpose is to remove
					dataManipulationManager.moveSelectedStringFromTo( indexCombo, activeVarCombo);
				}
				addIndexBtn.setEnabled(false);
			}
		});
		addIndexBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addIndexBtn.setText("Add");

		indexCombo = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
		GridData gd_indexCombo = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_indexCombo.heightHint = 55;
		gd_indexCombo.widthHint = 181;
		indexCombo.setLayoutData(gd_indexCombo);
		indexCombo.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				dataManipulationManager.moveSelectedStringFromTo( indexCombo, activeVarCombo);
				addIndexBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(indexCombo.getSelectionIndex()>-1){
					addIndexBtn.setEnabled(true);
					activeVarCombo.setSelection(-1);
					String[] s=indexCombo.getSelection();
					addIndexBtn.setText("Remove");
					addIndexBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		return container;
	}

	protected void enableButtons(boolean state) {
		// TODO Auto-generated method stub
		addReshapeBtn.setEnabled(state);
		addRetainBtn.setEnabled(state);
		addIndexBtn.setEnabled(state);
		
		addReshapeBtn.setText("Add");
		addRetainBtn.setText("Add");
		addIndexBtn.setText("Add");
		
		reshapeCombo.setSelection(-1);
		retainCombo.setSelection(-1);
		indexCombo.setSelection(-1);
	}

	private void populateActiveVarCombo() {
		for(String s:varInfo){
			String[] tmp = s.split(":");
			activeVarCombo.add(tmp[0]);
			activeVarCombo.setData(tmp[0], tmp[1]);
		}
	}
	@Override
	protected void okPressed(){
		String dataFileName = fileModel.getProjectFile().getAbsolutePath().toString().replaceAll("\\\\+", "/");
		String fileNameExtension = dataManipulationManager.getManipulatedFileNameExtension("_ReshapeToWide", fileModel.getProjectFile().getAbsolutePath());
		String newFileName = dataFileName.replaceAll(".csv", fileNameExtension);
		//CALL THE RJAVA FUNCTION HERE

		String[] reshapeVariables=reshapeCombo.getItems();
		String[] retainVariables=retainCombo.getItems();
		String[] indexFactor=indexCombo.getItems();

		if(retainVariables.length>0 && indexFactor.length>0 ){
			ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().toWide(dataFileName, newFileName, reshapeVariables, retainVariables, indexFactor);
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
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Reshape Long to Wide was Unsuccessful", "Failed to reshape variables from data.\n\n");
			}
		}
		else{
			MessageDialog.openWarning(getShell(), "Incomplete Input", "Please don't leave the \"VARIABLES TO RETAIN\" and \"INDEX FACTORS\" fields empty.");
		}
	}

	@Override
	protected void buttonPressed(int buttonId) { //wen Reset button is pressed
		if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
			addReshapeBtn.setEnabled(false);
			addRetainBtn.setEnabled(false);
			addIndexBtn.setEnabled(false);
			reshapeCombo.removeAll();
			retainCombo.removeAll();
			indexCombo.removeAll();
			activeVarCombo.removeAll();
			populateActiveVarCombo();
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
		button.setText("Reset");
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
		return new Point(583, 443);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

}
