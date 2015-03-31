package org.irri.breedingtool.graphs.handler;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.graphs.dialog.ScatterPlotGraphDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.SingleEnvironmentDialog;
import org.irri.breedingtool.utility.WindowDialogControlUtil;

public class ScatterPlotGraphHandler {
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

			dataManipulationManager.saveFileChanges(newFileModel.getProjectFile(), DataManipulationManager.getActiveTable(absoluteFilePath),  DataManipulationManager.getActiveDataDelimiter(absoluteFilePath));
			File activeFile = new File(stack.getSelectedElement().getElementId());
			try{
				if(WindowDialogControlUtil.doesWindowDialogForFileExist("ScatterPlotGraph", activeFile.getAbsolutePath())){//if the Dialog was just Hidden
					WindowDialogControlUtil.showWindowDialog("ScatterPlotGraph",activeFile.getAbsolutePath());
				}
				else{//If the Dialog has been closed, create another
					ScatterPlotGraphDialog newDialog = new ScatterPlotGraphDialog(Display.getCurrent().getActiveShell(), "ScatterPlotGraph", newFileModel.getProjectFile());
					newDialog.open();
				}
			}
			catch(NullPointerException npe){//There's no created Dialog Yet
				ScatterPlotGraphDialog newDialog = new ScatterPlotGraphDialog(Display.getCurrent().getActiveShell(), "ScatterPlotGraph", newFileModel.getProjectFile());
				newDialog.open();
			}
			
		}catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}
}
