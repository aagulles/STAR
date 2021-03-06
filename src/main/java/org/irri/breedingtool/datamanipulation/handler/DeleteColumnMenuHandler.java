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
import org.irri.breedingtool.datamanipulation.dialog.DeleteColumnsDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.utility.WindowDialogControlUtil;

public class DeleteColumnMenuHandler {

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
			MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
			MStackElement selected = stack.getSelectedElement();
			String absoluteFilePath = selected.getElementId();
			File file = new File(absoluteFilePath);
			Table table = DataManipulationManager.getActiveTable(selected.getElementId());
			String delimiter = DataManipulationManager.getActiveDataDelimiter(selected.getElementId());
			
			TableColumn selectedColumn = (TableColumn) table.getData("selectedColumn");
			String[] columnHeaderNames = (String[]) table.getData("columnHeaderNames");
			Listener columnHeaderListener = (Listener) table.getData("columnHeaderListener");
			
			boolean selectedColumnExists = false;
			try {if(selectedColumn.getImage()!=null)selectedColumnExists=true;}
			catch (NullPointerException iae){} catch (SWTException se){}
			if(selectedColumnExists){
				int index =(Integer) selectedColumn.getData("index");
				DeleteColumnsDialog getInputFromUserDlg = new DeleteColumnsDialog(Display.getCurrent().getActiveShell(), selectedColumn.getText(), columnHeaderNames);
				getInputFromUserDlg.open();
				int[] selectedColumnIndices = getInputFromUserDlg.getSelectedColumnIndices();
				if(getInputFromUserDlg.getReturnCode()==0){
					if(selectedColumnIndices.length > 1) {
						for(int i = 0; i<selectedColumnIndices.length; i++){
							int selColIndex = (selectedColumnIndices[i]+2)-(i+1);
							TableColumn selCol = table.getColumn(selColIndex);
							dataManipulationManager.deleteVariableFromVarInfoTmp(file.getAbsolutePath(), selCol.getText());
							dataManipulationManager.deleteTableColumn(table, selColIndex);
							System.out.println("delete "+ Integer.toString((selectedColumnIndices[i]+2)-(i+1)));
						}
						WindowDialogControlUtil.closeAllWindowDialogOfFile(file.getAbsolutePath());
						((ToolItem) table.getData("saveBtn")).setEnabled(true);
					}
					else {
						dataManipulationManager.deleteVariableFromVarInfoTmp(file.getAbsolutePath(), selectedColumn.getText());
						dataManipulationManager.deleteTableColumn(table, index);
						WindowDialogControlUtil.closeAllWindowDialogOfFile(file.getAbsolutePath());
						((ToolItem) table.getData("saveBtn")).setEnabled(true);
					}
					try {selectedColumn.setImage(null);}
					catch (SWTException swte){}
					PartStackHandler.setPartModified(file.getAbsolutePath());
					columnHeaderNames = dataManipulationManager.updateColHeaderNames(table, columnHeaderNames);
					dataManipulationManager.saveFileChanges(file, table, delimiter);
				}
			}else{
				// message box no selected column
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Delete Column", "Please select the column you want to delete.");
			}
		} catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}
	
	@Execute
	public static void execute(File file, Table table) {
		try{
			boolean selectedColumnExists = false;
			String delimiter = (String) table.getData("delimiter");
			TableColumn selectedColumn = (TableColumn) table.getData("selectedColumn");
			String[] columnHeaderNames = (String[]) table.getData("columnHeaderNames");
			Listener columnHeaderListener = (Listener) table.getData("columnHeaderListener");
			
			try {if(selectedColumn.getImage()!=null)selectedColumnExists=true;}
			catch (NullPointerException iae){} catch (SWTException se){}
			if(selectedColumnExists){
				int index =(Integer) selectedColumn.getData("index");
				DeleteColumnsDialog getInputFromUserDlg = new DeleteColumnsDialog(Display.getCurrent().getActiveShell(), selectedColumn.getText(), columnHeaderNames);
				getInputFromUserDlg.open();
				int[] selectedColumnIndices = getInputFromUserDlg.getSelectedColumnIndices();
				if(getInputFromUserDlg.getReturnCode()==0){
					if(selectedColumnIndices.length > 1) {
						for(int i = 0; i<selectedColumnIndices.length; i++){
							int selColIndex = (selectedColumnIndices[i]+2)-(i+1);
							TableColumn selCol = table.getColumn(selColIndex);
							dataManipulationManager.deleteVariableFromVarInfoTmp(file.getAbsolutePath(), selCol.getText());
							dataManipulationManager.deleteTableColumn(table, selColIndex);
							System.out.println("delete "+ Integer.toString((selectedColumnIndices[i]+2)-(i+1)));
						}
						WindowDialogControlUtil.closeAllWindowDialogOfFile(file.getAbsolutePath());
						((ToolItem) table.getData("saveBtn")).setEnabled(true);
					}
					else {
						dataManipulationManager.deleteVariableFromVarInfoTmp(file.getAbsolutePath(), selectedColumn.getText());
						dataManipulationManager.deleteTableColumn(table, index);
						WindowDialogControlUtil.closeAllWindowDialogOfFile(file.getAbsolutePath());
						((ToolItem) table.getData("saveBtn")).setEnabled(true);
					}
					try {selectedColumn.setImage(null);}
					catch (SWTException swte){}
					PartStackHandler.setPartModified(file.getAbsolutePath());
					columnHeaderNames = dataManipulationManager.updateColHeaderNames(table, columnHeaderNames);
					dataManipulationManager.saveFileChanges(file, table, delimiter);
				}
			}else{
				// message box no selected column
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Delete Column", "Please select the column you want to delete.");
			}
		} catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}
}