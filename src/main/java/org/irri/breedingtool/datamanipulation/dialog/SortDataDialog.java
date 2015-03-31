package org.irri.breedingtool.datamanipulation.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;

public class SortDataDialog extends Dialog {
	private Combo combo, combo_1, combo_2;
	private Button btnAscending, btnAscending1, btnAscending2, btnDescending, btnDescending1, btnDescending2;
	private Group grpSortBy1, grpSortBy2, grpSortBy3;
	private String[] sortChoices;
	private String[] sortOrder;
	private String[] columnHeaders;
	private File newFile;
	private int ctr = 0;
	private ProjectExplorerTreeNodeModel newFileModel;
	DataManipulationManager dataManipulationManager;
	private ArrayList<String> varInfo= new ArrayList<String>();;
	private ProjectExplorerTreeNodeModel fileModel;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SortDataDialog(Shell parentShell,ArrayList<String> varInfo, ProjectExplorerTreeNodeModel fileModel) {
		super(parentShell);
		this.varInfo = varInfo;
		this.fileModel = fileModel;
		dataManipulationManager = new DataManipulationManager();
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		setShellStyle(SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.RESIZE);
		setBlockOnOpen(false);
		getShell().setText("Sort: "+dataManipulationManager.getDisplayFileName(fileModel.getProjectFile().getAbsolutePath()));
		columnHeaders=dataManipulationManager.getListItems(varInfo);
		System.out.println(fileModel.getProjectFile().getName());
		newFileModel= fileModel;
		newFile = fileModel.getProjectFile();
		

		grpSortBy1 = new Group(container, SWT.NONE);
		GridData gd_grpSortBy1 = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_grpSortBy1.widthHint = 280;
		grpSortBy1.setLayoutData(gd_grpSortBy1);
		grpSortBy1.setText("Sort by");

		combo = new Combo(grpSortBy1, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setBounds(10, 21, 137, 28);
		combo.setItems(columnHeaders);

		btnAscending = new Button(grpSortBy1, SWT.RADIO);
		btnAscending.setBounds(159, 23, 102, 23);
		btnAscending.setText("ascending");
		btnAscending.setSelection(true);

		btnDescending = new Button(grpSortBy1, SWT.RADIO);
		btnDescending.setBounds(286, 26, 102, 23);
		btnDescending.setText("descending");

		grpSortBy2 = new Group(container, SWT.NONE);
		grpSortBy2.setEnabled(false);
		grpSortBy2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		grpSortBy2.setText("Then by");
		combo.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				grpSortBy2.setEnabled(true);
				ctr++;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		combo_1 = new Combo(grpSortBy2,SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_1.setBounds(10, 21, 133, 28);
		combo_1.setItems(columnHeaders);

		btnAscending1 = new Button(grpSortBy2, SWT.RADIO);
		btnAscending1.setSelection(true);
		btnAscending1.setText("ascending");
		btnAscending1.setBounds(159, 21, 95, 27);

		btnDescending1 = new Button(grpSortBy2, SWT.RADIO);
		btnDescending1.setText("descending");
		btnDescending1.setBounds(286, 26, 109, 23);

		grpSortBy3 = new Group(container, SWT.NONE);
		grpSortBy3.setEnabled(false);
		grpSortBy3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		grpSortBy3.setText("Then by");
		combo_1.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				grpSortBy3.setEnabled(true);
				ctr++;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		combo_2 = new Combo(grpSortBy3, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_2.setBounds(10, 21, 128, 28);
		combo_2.setItems(columnHeaders);
		combo_2.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				ctr++;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		btnAscending2 = new Button(grpSortBy3, SWT.RADIO);
		btnAscending2.setText("ascending");
		btnAscending2.setBounds(160, 21, 98, 27);
		btnAscending2.setSelection(true);
		btnDescending2 = new Button(grpSortBy3, SWT.RADIO);
		btnDescending2.setText("descending");
		btnDescending2.setBounds(288, 23, 115, 23);

		return container;
	}
	@Override
	protected void buttonPressed(int buttonId) { //wen Reset button is pressed
		if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
			combo.setItems(columnHeaders);
			combo_1.setItems(columnHeaders);
			grpSortBy2.setEnabled(true);
			combo_2.setItems(columnHeaders);
			grpSortBy3.setEnabled(true);
			ctr = 0;
		}
		super.buttonPressed(buttonId);
	}
	
	@Override
	protected void okPressed(){
		if(combo.getText().isEmpty() && combo_1.getText().isEmpty() && combo_2.getText().isEmpty()){ // check if everything's empty
			MessageDialog.openWarning(getShell(), "Data Sorting", "Please choose a variable to be sorted.");
		}
		else if(checkVariableMatch()){ //check if there are similar variables used
			MessageDialog.openWarning(getShell(), "Data Sorting", "Sorry, You can only use a variable once.");
		}
		else{ //CALL RJava Function here
			String dataFileName = newFile.getAbsolutePath().toString().replaceAll("\\\\+", "/");
			
			String fileNameExtension = dataManipulationManager.getManipulatedFileNameExtension("_sorted", newFile.getAbsolutePath());
			String newFile = dataFileName.replaceAll(".csv", fileNameExtension);
			String newFileName = newFileModel.getProjectFile().getName().replaceAll(".csv", fileNameExtension);
			
			sortChoices = new String[ctr];
			sortOrder = new String[ctr];
			getComboStrings();

			ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().sortCases(dataFileName, newFile, sortChoices, sortOrder);
			close();
			//	 			PartStackHandler.reOpenPart(fileModel);
			ProjectExplorerManager projExpMan =  new ProjectExplorerManager();
			TreeItem sortedTree = projExpMan.getTreeNodeByName(newFileModel.getParentTreeItem(), newFileName);
			try{

				if(((ProjectExplorerTreeNodeModel) sortedTree.getData()).getElementInStack() == null){//if the filename created hasn't been opened
					PartStackHandler.execute(projExpMan.createNewProjectExplorerModel((ProjectExplorerTreeNodeModel) newFileModel.getParentTreeItem().getData(), newFileName));
				}
				else{//re-open file
					PartStackHandler.reOpenPart((ProjectExplorerTreeNodeModel) sortedTree.getData());
				}
			}catch(NullPointerException npe){
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Sort was Unsuccessful", "Failed to sort data from file.");
			}
		}
	}
	
	public void getComboStrings(){
		int ctr=0;
		if(!combo.getText().isEmpty()){//check if there's an empty string
			sortChoices[ctr]=combo.getText();
			if(btnAscending.getSelection())sortOrder[ctr]="ascending";
			else sortOrder[ctr]="descending";
			ctr++;
		}
		if(!combo_1.getText().isEmpty()){
			sortChoices[ctr]=combo_1.getText();
			if(btnAscending1.getSelection())sortOrder[ctr]="ascending";
			else sortOrder[ctr]="descending";
			ctr++;
		}
		if(!combo_2.getText().isEmpty()){
			sortChoices[ctr]=combo_2.getText();
			if(btnAscending2.getSelection())sortOrder[ctr]="ascending";
			else sortOrder[ctr]="descending";
			ctr++;
		}
	}

	public boolean checkVariableMatch(){
		if(!combo_1.getText().isEmpty()){
			if(combo_1.getText().equals(combo.getText())){ //compare 1 & 2
				return true;
			}

		}
		if(!combo_2.getText().isEmpty()){
			if(combo_2.getText().equals(combo.getText())){//compare 3 & 1
				return true;
			}
			if(combo_2.getText().equals(combo_1.getText())){//compare 3 & 2
				return true;
			}
		}
		return false;
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
		return new Point(450, 300);
	}

}
