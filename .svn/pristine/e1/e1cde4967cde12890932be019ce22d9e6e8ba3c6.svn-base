package org.irri.breedingtool.projectexplorer.handler;

import java.io.File;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;
import org.irri.breedingtool.utility.CheckFileExistValidator;



public class RenameProjectDialogHandler {
	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public void launchImportProject(final Shell parent, EModelService service,  MWindow window) {
		if(ProjectExplorerView.rootTree.getItemCount() == 0) return;
		if(ProjectExplorerView.projectTree.getText().length() <= 3) return;
		ProjectExplorerManager projectMan = new ProjectExplorerManager();
		InputDialog dlgRenameFile = new InputDialog(Display.getCurrent().getActiveShell(),

				"Rename Project", "Enter the new Project Name", ProjectExplorerView.projectTree.getText(), new CheckFileExistValidator(ProjectExplorerView.projectTree.getText().toString(), ProjectTreeComponent.getCurrentWorkspacePath(), false));

		if (dlgRenameFile.open() == Window.OK){

			PartStackHandler.removeAll();
			if(renameDirectory( projectMan.getProjectPath(),new File( projectMan.getProjectPath()).getParent() + File.separator  + dlgRenameFile.getValue()))
			{
				ProjectTreeComponent	projectTreeComponent ;

				projectTreeComponent = new ProjectTreeComponent(ProjectExplorerView.projectTree, dlgRenameFile.getValue());

			}
			else{
				
			
			}



		}


	}

	public boolean renameDirectory(String fromDir, String toDir) {

		File from = new File(fromDir);

		if (!from.exists() || !from.isDirectory()) {

			System.out.println("Directory does not exist: " + fromDir);
			return false;
		}

		File to = new File(toDir);

		//Rename
		if (from.renameTo(to))
			return true;
		else
			return false;

	}

}
