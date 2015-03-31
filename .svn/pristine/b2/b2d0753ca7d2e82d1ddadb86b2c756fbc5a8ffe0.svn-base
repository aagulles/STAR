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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;

public class DeleteRowMenuHandler {
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

			String displayMessage;
			if(table.getSelectionIndex()==-1){
				// message box no selected row
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Delete Row", "Please select the row you want to delete.");
			}else{
				int numOfRowsSelected[] = table.getSelectionIndices();
				if(numOfRowsSelected.length > 1){ // multiple rows selected

					displayMessage="Are you sure you want to delete the selected rows?";
				}else{
					displayMessage="Are you sure you want to delete row "+ Integer.toString(table.getSelectionIndex()+1)+"?";
				}
				if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Delete Row", displayMessage)){
					table.remove(numOfRowsSelected);
					PartStackHandler.setPartModified(absoluteFilePath);
					dataManipulationManager.updateRowNumberColumn(table);
					((ToolItem) table.getData("saveBtn")).setEnabled(true);
				}	
			}

			dataManipulationManager.saveFileChanges(new File(absoluteFilePath), DataManipulationManager.getActiveTable(absoluteFilePath),  DataManipulationManager.getActiveDataDelimiter(absoluteFilePath));

		}catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}

	@Execute
	public static void execute(Table table) {
		String displayMessage= "There's no selected row.\nDo you want to insert a new row after the last row?";
		ArrayList<String> tableData = (ArrayList<String>) table.getData("tableData");

		if(table.getSelectionIndex()==-1){
			// message box no selected row
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Delete Row", "Please select the row you want to delete.");
		}else{
			int numOfRowsSelected[] = table.getSelectionIndices();
			if(numOfRowsSelected.length > 1){ // multiple rows selected

				displayMessage="Are you sure you want to delete the selected rows?";
			}else{
				displayMessage="Are you sure you want to delete row "+ Integer.toString(table.getSelectionIndex()+1)+"?";
			}
			if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Delete Row", displayMessage)){
				table.remove(numOfRowsSelected);
				PartStackHandler.setPartModified((String) table.getData("ID"));
				dataManipulationManager.updateRowNumberColumn(table);
				((ToolItem) table.getData("saveBtn")).setEnabled(true);
			}			
		}
	}
}
