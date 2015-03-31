package org.irri.breedingtool.datamanipulation.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;

public class InsertRowMenuHandler {
	static DataManipulationManager dataManipulationManager = new DataManipulationManager();

	@Inject
	private static EPartService service;
	@Inject
	private static MApplication application;
	@Inject
	private static EModelService modelService;

	public static String PackageName = "Star";
	@SuppressWarnings("restriction")
	@Execute
	public static void execute() {
		try{
			MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
			MStackElement selected = stack.getSelectedElement();
			String absoluteFilePath = selected.getElementId();

			Table table = DataManipulationManager.getActiveTable(selected.getElementId());
			ArrayList<String> tableData = (ArrayList<String>) table.getData("tableData");

			if(table != null){//there's an active data
				String displayMessage= "There's no selected row.\nDo you want to insert a new row after the last row?";
				if(table.getSelectionIndex()==-1){
					// message box no selected row
					if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Insert Row", displayMessage)){ //If user did not choose cancel from dialog Box
						dataManipulationManager.insertTableRow(table, table.getItemCount(), tableData);
					}
					((ToolItem) table.getData("saveBtn")).setEnabled(true);
					dataManipulationManager.updateRowNumberColumn(table);
				}else{
					int selectedRows[] = table.getSelectionIndices();
					for(int i=0; i<selectedRows.length; i++){
						dataManipulationManager.insertTableRow(table, selectedRows[i], tableData);
					}
					displayMessage = "Successfully inserted new row(s).";
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Insert Row", displayMessage);
					dataManipulationManager.updateRowNumberColumn(table);
					((ToolItem) table.getData("saveBtn")).setEnabled(true);
				}
				PartStackHandler.setPartModified(absoluteFilePath);
				dataManipulationManager.saveFileChanges(new File(absoluteFilePath), DataManipulationManager.getActiveTable(absoluteFilePath),  DataManipulationManager.getActiveDataDelimiter(absoluteFilePath));
			}
		}
		catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}

	@Execute
	public static void execute(Table table) {
		String displayMessage= "There's no selected row.\nDo you want to insert a new row after the last row?";
		ArrayList<String> tableData = (ArrayList<String>) table.getData("tableData");

		if(table.getSelectionIndex()==-1){
			// message box no selected row
			if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Insert Row", displayMessage)){ //If user did not choose cancel from dialog Box
				dataManipulationManager.insertTableRow(table, table.getItemCount(), tableData);
			}
			dataManipulationManager.updateRowNumberColumn(table);
			((ToolItem) table.getData("saveBtn")).setEnabled(true);
		}else{
			int selectedRows[] = table.getSelectionIndices();
			for(int i=0; i<selectedRows.length; i++){
				dataManipulationManager.insertTableRow(table, selectedRows[i], tableData);
			}
			displayMessage = "Successfully inserted new row(s).";
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Insert Row", displayMessage);
			dataManipulationManager.updateRowNumberColumn(table);
			((ToolItem) table.getData("saveBtn")).setEnabled(true);
		}
		PartStackHandler.setPartModified((String) table.getData("ID"));
	}
}
