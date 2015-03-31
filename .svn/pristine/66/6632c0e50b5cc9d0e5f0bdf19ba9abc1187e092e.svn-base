package org.irri.breedingtool.datamanipulation.dialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Combo;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditVariableInfoDialog extends Dialog {

	protected static final int FACTOR = 1;
	private ArrayList<String> varInfo, newVarInfo;
	private ProjectExplorerTreeNodeModel newFileModel;
	private Table table;
	private boolean editedVarInfo=false;
	private boolean editedVariableNames=false;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	
	public EditVariableInfoDialog(Shell parentShell , ArrayList<String> varInfo, ProjectExplorerTreeNodeModel fileModel) {
		super(parentShell);
		this.varInfo = dataManipulationManager.getVarInfoFromFile(fileModel.getProjectFile().getAbsolutePath());
		newVarInfo = this.varInfo;
		newFileModel = fileModel;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Edit Variable Information: "+dataManipulationManager.getDisplayFileName(newFileModel.getProjectFile().getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();

		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.widthHint = 204;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(210);
		tblclmnNewColumn.setText("Variable Name");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(190);
		tblclmnNewColumn_1.setText("Variable Type");

		int ctr=0;
		for(String s:varInfo){ // populate variables

			String[] tmp = s.split(":");
			TableItem tableItem = new TableItem(table, SWT.CENTER);
			tableItem.setText(0, tmp[0]); //factor Name

			TableEditor editor = new TableEditor(table);
			CCombo varTypeList = new CCombo(table, SWT.READ_ONLY);
			varTypeList.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			editor.grabHorizontal = true;
			editor.setEditor(varTypeList, tableItem, 1);
			varTypeList.setData("varIndex", Integer.toString(ctr++));
			varTypeList.setData("varType", tmp[1]);
			varTypeList.setData("varName", tmp[0]);

			varTypeList.add("Numeric");
			varTypeList.add("Factor");
			if(tmp[1].toLowerCase().equals("numeric")) varTypeList.select(0); //then select numeric
			else varTypeList.select(1);// else, set factor as selected

			varTypeList.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					CCombo selectedCombo = (CCombo) e.getSource();
					int varIndex = Integer.parseInt((String) selectedCombo.getData("varIndex"));
					String varType = selectedCombo.getText();
					String varName = newVarInfo.get(varIndex).split(":")[0];
					if(varType.toLowerCase().equals("numeric")){
						String isNumeric = dataManipulationManager.isNumeric(newFileModel.getProjectFile().getAbsolutePath().replaceAll("\\\\","/"), varName);
						if(isNumeric.equals("TRUE")){
							newVarInfo.set(varIndex, varName+":"+varType);
							editedVarInfo=true;
						}
						else{
							MessageDialog.openWarning(getShell(), "Invalid type Assignment", "Sorry, "+varName+" cannot be assigned as a numeric variable.");
							selectedCombo.select(FACTOR);
						}
					}
					else{//if factor, directly assign the variable as a factor
						newVarInfo.set(varIndex, varName+":"+varType);
						editedVarInfo=true;
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
				}

			});
		}
		
		 final int EDITABLECOLUMN = 0;
		 table.addSelectionListener(new SelectionAdapter() {
		         @Override
		         public void widgetSelected(SelectionEvent e) {
		             // Clean up any previous editor control
		             final TableEditor editor = new TableEditor(table);
		             final int varIndex = table.getSelectionIndex();
		             // The editor must have the same size as the cell and must
		             // not be any smaller than 50 pixels.
		             editor.horizontalAlignment = SWT.LEFT;
		             editor.grabHorizontal = true;
		             editor.minimumWidth = 50;
		             Control oldEditor = editor.getEditor();
		             if (oldEditor != null)
		                 oldEditor.dispose();                

		             // Identify the selected row
		             TableItem item = (TableItem) e.item;
		             if (item == null)
		                 return;

		             // The control that will be the editor must be a child of the
		             // Table
		             Text newEditor = new Text(table, SWT.NONE);
		             newEditor.setText(item.getText(EDITABLECOLUMN));
		             final String originalText = item.getText(EDITABLECOLUMN);
		             newEditor.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							// TODO Auto-generated method stub
							   final Text text = (Text) editor.getEditor();
			                   text.addListener(SWT.FocusOut, new Listener() {
									public void handleEvent(final Event e) {
										try{
										if(!dataManipulationManager.variableNameInputValidation(text.getText()).equals("true")){
											 MessageDialog.openWarning(getShell(), "Invalid Input", dataManipulationManager.variableNameInputValidation(text.getText()));
											 editor.getItem().setText(EDITABLECOLUMN, originalText); 
											 text.dispose();
										}
										else if(text.getText().isEmpty()){
											 MessageDialog.openInformation(getShell(), "Invalid Input", "Sorry, you can't leave a variable name empty.");
											 editor.getItem().setText(EDITABLECOLUMN, originalText); 
											 text.dispose();
										}
										else if(!dataManipulationManager.checkColumnNameIfUsed(text.getText(), dataManipulationManager.getListItems(newVarInfo))){
											   System.out.println(text.getText());
											   String varName = text.getText();
											   String varType =newVarInfo.get(varIndex).split(":")[1];
											   System.out.println("varType: "+varType);
							                   newVarInfo.set(varIndex, varName+":"+varType);
							                   editedVariableNames = true;
								               editor.getItem().setText(EDITABLECOLUMN, text.getText());
								               System.out.println("SET VAR:"+Integer.toString(varIndex)+" to "+varName+":"+varType);
							                   text.dispose();
						                }
										else{
											 MessageDialog.openInformation(getShell(), "Invalid Input", "Sorry, the variable '"+ text.getText()+"' already exist.");
											 editor.getItem().setText(EDITABLECOLUMN, originalText); 
											 text.dispose();
										}
										}catch(Exception ex){}
									}
			                   });
			                  
						}
		             });         
		             newEditor.selectAll();
		             newEditor.setFocus();           
		             editor.setEditor(newEditor, item, EDITABLECOLUMN);      
		         }
		     });     
		System.out.println(Integer.toString(ctr));
		return container;
	}

	@Override
	protected void okPressed(){
		//CALL THE RJAVA FUNCTION HERE
		if(editedVarInfo || editedVariableNames){
//			if( MessageDialog.openConfirm(getShell(), "Save Changes", "Do you want to save the changes you've made to the variable information?")){//user selected OK
				DataManipulationManager.saveVarTempFile(newFileModel.getProjectFile().getAbsolutePath(), newVarInfo);
				varInfo = newVarInfo;
				if(editedVariableNames){
					Table table = DataManipulationManager.getActiveTable(newFileModel.getProjectFile().getAbsolutePath());
					String newColumnName[] = dataManipulationManager.getListItems(varInfo);
					for(int i=1; i<table.getColumnCount(); i++){
						TableColumn column =table.getColumn(i);
						column.setText(newColumnName[i-1]);
					}
					dataManipulationManager.saveFileChanges(newFileModel.getProjectFile(), table, ",");
					PartStackHandler.setPartModified((String) table.getData("ID"));
					((ToolItem) table.getData("saveBtn")).setEnabled(true);
					MessageDialog.openInformation(getShell(), "Edit Variable Information", "Your changes have been saved.");
				}
			WindowDialogControlUtil.closeAllWindowDialogOfFile(newFileModel.getProjectFile().getAbsolutePath());
		}else MessageDialog.openInformation(getShell(), "Exit", "No changes were made in the variable information.");
		close();
	}

	public ArrayList<String> getVarInfo(){
		//CALL THE RJAVA FUNCTION HERE
		return this.varInfo;
	}
	
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
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
		return new Point(496, 296);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
}
