package org.irri.breedingtool.datamanipulation.handler;

import java.io.File;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.CreateNewVarDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;

public class SaveFileHandler {
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
			DataManipulationManager dataManipulationManager = new DataManipulationManager();
			Table table = DataManipulationManager.getActiveTable(selected.getElementId());
			String absoluteFilePath = selected.getElementId();
			dataManipulationManager.saveOriginal(new File(absoluteFilePath), DataManipulationManager.getActiveTable(absoluteFilePath),  DataManipulationManager.getActiveDataDelimiter(absoluteFilePath));
			PartStackHandler.setPartSaved(absoluteFilePath);
			((ToolItem) table.getData("saveBtn")).setEnabled(false);
		}
		catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
		//		if(dataManipulationManager.saveFileChanges(new File (selected.getElementId()), DataManipulationManager.getActiveTable(selected.getElementId()), ",")){
		//			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Insert Row", "Your changes have been saved.");
		//		}
		//		else{
		//			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error Saving File", "Please check if your file is open on another application.");
		//		}
	}
}	
