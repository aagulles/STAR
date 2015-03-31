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
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;

public class CreateNewVariableHandler {
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
			absoluteFilePath = absoluteFilePath.replaceAll("\\\\", "/");
			String tmpFile = absoluteFilePath.replaceAll(".csv", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);
			ArrayList<String> varInfo = ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().getVariableInfo(absoluteFilePath, tmpFile, 1, "NULL");
			System.out.println(newFileModel.getProjectFile().getName());
			CreateNewVarDialog newVarDlg = new CreateNewVarDialog(Display.getCurrent().getActiveShell(), varInfo, newFileModel);
			newVarDlg.open();
		}catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}

	@Execute
	public static void execute(ProjectExplorerTreeNodeModel newFileModel, int fileFormat, String delimiter) {
		String absoluteFilePath = newFileModel.getProjectFile().getAbsolutePath();
		absoluteFilePath = absoluteFilePath.replaceAll("\\\\", "/");
		String tmpFile = absoluteFilePath.replaceAll(".csv", RJavaManagerInitializer.VARINFO_TMPFILE_EXTENSION);
		ArrayList<String> varInfo = ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().getVariableInfo(absoluteFilePath,tmpFile, fileFormat, delimiter);
		System.out.println(newFileModel.getProjectFile().getName());
		CreateNewVarDialog newVarDlg = new CreateNewVarDialog(Display.getCurrent().getActiveShell(), varInfo, newFileModel);
		newVarDlg.open();
	}
}
