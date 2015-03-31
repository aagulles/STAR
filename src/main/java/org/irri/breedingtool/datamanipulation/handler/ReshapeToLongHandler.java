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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.CreateNewVarDialog;
import org.irri.breedingtool.datamanipulation.dialog.ReshapeLongToWideDialog;
import org.irri.breedingtool.datamanipulation.dialog.ReshapeWideToLongDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;

public class ReshapeToLongHandler {
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
			MUIElement selected = stack.getSelectedElement();
			String absoluteFilePath = selected.getElementId();
			dataManipulationManager.saveFileChanges(new File(absoluteFilePath), DataManipulationManager.getActiveTable(absoluteFilePath),  DataManipulationManager.getActiveDataDelimiter(absoluteFilePath));
			ProjectExplorerManager projExpMan = new ProjectExplorerManager();
			ProjectExplorerTreeNodeModel newFileModel = projExpMan.getTreeNodeModelbyFilePath(absoluteFilePath);
			ArrayList<String> varInfo=dataManipulationManager.getVarInfoFromFile(absoluteFilePath);
			System.out.println(newFileModel.getProjectFile().getName());
			ReshapeWideToLongDialog newDialog = new ReshapeWideToLongDialog(Display.getCurrent().getActiveShell(), varInfo, newFileModel);
			newDialog.open();
		}
		catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}
	public static void execute(ProjectExplorerTreeNodeModel newFileModel, int fileFormat, String delimiter) {
		// TODO Auto-generated method stub
		String absoluteFilePath = newFileModel.getProjectFile().getAbsolutePath();
		ArrayList<String> varInfo=dataManipulationManager.getVarInfoFromFile(absoluteFilePath);
		System.out.println(newFileModel.getProjectFile().getName());
		ReshapeWideToLongDialog newVarDlg = new ReshapeWideToLongDialog(Display.getCurrent().getActiveShell(), varInfo, newFileModel);
		newVarDlg.open();
	}
}
