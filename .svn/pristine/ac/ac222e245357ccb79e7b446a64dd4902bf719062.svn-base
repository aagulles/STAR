package org.irri.breedingtool.datamanipulation.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.utility.WindowDialogControlUtil;

public class InsertColumnMenuHandler {

	@Inject
	private static EPartService service;
	@Inject
	private static MApplication application;
	@Inject
	private static EModelService modelService;
	static DataManipulationManager dataManipulationManager = new DataManipulationManager();

	@Execute
	public static void execute() {
		try{
			System.out.println("InsertColumnManuHandler");
			MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
			MStackElement selected = stack.getSelectedElement();
			String absoluteFilePath = selected.getElementId();
			File file = new File(absoluteFilePath);
			Table table = DataManipulationManager.getActiveTable(selected.getElementId());

			boolean selectedColumnExists = false;
			boolean colNameUsed =true;
			String displayMessage ="There's no selected column.\nDo you want to insert a new column after the last column?";
			int index = table.getColumnCount();
			String ifColUsed="";

			TableColumn selectedColumn = (TableColumn) table.getData("selectedColumn");
			String[] columnHeaderNames = (String[]) table.getData("columnHeaderNames");
			Listener columnHeaderListener = (Listener) table.getData("columnHeaderListener");

			try{
				if(selectedColumn.getImage()!=null)selectedColumnExists=true; 
			}catch (NullPointerException iae){

			} catch (SWTException se){

			}

			if(selectedColumnExists){
				index = (Integer) selectedColumn.getData("index");
				displayMessage ="Insert Column before "+ selectedColumn.getText() +"?";
			}
			if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Insert Column", displayMessage)){ //If user did not choose cancel from dialog Box
				while(colNameUsed){//ask column name from user
					InputDialog getcolNameDlg = new InputDialog(Display.getCurrent().getActiveShell(), "Insert Column", ifColUsed+"\nInsert New Column Name:", "NewColumn", null);
					getcolNameDlg.open();
					if(getcolNameDlg.getReturnCode()==0){ //if user entered a column name
						if(!dataManipulationManager.checkColumnNameIfUsed(getcolNameDlg.getValue(), columnHeaderNames)){
							ifColUsed =dataManipulationManager.variableNameInputValidation(getcolNameDlg.getValue());
							if(ifColUsed.equals("true")){
								colNameUsed=false;
								dataManipulationManager.insertTableColumn(table, index, getcolNameDlg.getValue());//there's no column selected
								TableColumn col = table.getColumn(index);
								col.addListener(SWT.Selection, columnHeaderListener);
								dataManipulationManager.addNewVariableNameToTmpVarInfo(file.getAbsolutePath(), getcolNameDlg.getValue());
								dataManipulationManager.saveFileChanges(file, table, dataManipulationManager.getActiveDataDelimiter(file.getAbsolutePath()));
								columnHeaderNames = dataManipulationManager.updateColHeaderNames(table, (String[]) table.getData("columnHeaderNames"));
								PartStackHandler.setPartModified(absoluteFilePath);
								WindowDialogControlUtil.closeAllWindowDialogOfFile(file.getAbsolutePath());
								((ToolItem) table.getData("saveBtn")).setEnabled(true);
							}
						}else ifColUsed = "Sorry, the column name \""+getcolNameDlg.getValue()+"\" already exist.\n";
					}
					else colNameUsed=false; //if user decides to exit
				}// end-of-while
			}// end-of-if
		} catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}

	@Execute
	public static void execute(File file, Table table) {
		try{
			boolean selectedColumnExists = false;
			boolean colNameUsed =true;
			String displayMessage ="There's no selected column.\nDo you want to insert a new column after the last column?";
			int index = table.getColumnCount();
			String ifColUsed="";

			TableColumn selectedColumn = (TableColumn) table.getData("selectedColumn");
			String[] columnHeaderNames = (String[]) table.getData("columnHeaderNames");
			Listener columnHeaderListener = (Listener) table.getData("columnHeaderListener");

			try{
				if(selectedColumn.getImage()!=null)selectedColumnExists=true; 
			}catch (NullPointerException iae){

			} catch (SWTException se){

			}
			if(selectedColumnExists){
				index = (Integer) selectedColumn.getData("index");
				displayMessage ="Insert Column before "+ selectedColumn.getText() +"?";
			}
			if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Insert Column", displayMessage)){ //If user did not choose cancel from dialog Box
				while(colNameUsed){//ask column name from user
					InputDialog getcolNameDlg = new InputDialog(Display.getCurrent().getActiveShell(), "Insert Column", ifColUsed+"\nInsert New Column Name:", "NewColumn", null);
					getcolNameDlg.open();
					if(getcolNameDlg.getReturnCode()==0){ //if user entered a column name
						if(!dataManipulationManager.checkColumnNameIfUsed(getcolNameDlg.getValue(), columnHeaderNames)){
							ifColUsed =dataManipulationManager.variableNameInputValidation(getcolNameDlg.getValue());
							if(ifColUsed.equals("true")){
								colNameUsed=false;
								dataManipulationManager.insertTableColumn(table, index, getcolNameDlg.getValue());//there's no column selected
								TableColumn col = table.getColumn(index);
								col.addListener(SWT.Selection, columnHeaderListener);
								dataManipulationManager.addNewVariableNameToTmpVarInfo(file.getAbsolutePath(), getcolNameDlg.getValue());
								dataManipulationManager.saveFileChanges(file, table, dataManipulationManager.getActiveDataDelimiter(file.getAbsolutePath()));
								columnHeaderNames = dataManipulationManager.updateColHeaderNames(table, (String[]) table.getData("columnHeaderNames"));
								PartStackHandler.setPartModified((String) table.getData("ID"));
								WindowDialogControlUtil.closeAllWindowDialogOfFile(file.getAbsolutePath());
								((ToolItem) table.getData("saveBtn")).setEnabled(true);
							}
						}else ifColUsed = "Sorry, the column name \""+getcolNameDlg.getValue()+"\" already exist.\n";
					}
					else colNameUsed=false; //if user decides to exit
				}// end-of-while
			}// end-of-if
		} catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error",  "The active tab in the Editor panel should contain a data file.");
		}
	}
}