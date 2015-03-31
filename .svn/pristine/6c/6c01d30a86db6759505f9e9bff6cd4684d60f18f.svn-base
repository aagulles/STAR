package org.irri.breedingtool.projectexplorer.handler;

import java.io.File;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;



public class ImportProjectDialogHandler {
	ProjectTreeComponent	projectTreeComponent ;
	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public void launchImportProject(final Shell parent) {

		DirectoryDialog dlg = new DirectoryDialog(Display.getCurrent().getActiveShell());

		// Set the initial filter path according
		// to anything they've selected or typed in


		dlg.setText("Import Project");


		dlg.setMessage("Select a directory");

		String dir = dlg.open();

		if (dir != null) {
			// Set the text box to the new selection
			File newProj = new File(dir + File.separator + "project.ini");
			if(!newProj.exists()) {
				MessageDialog.openError(Display.getCurrent().getActiveShell(), "Import Project Error", "The folder selected was not a valid project"); 
				return;
			}
			boolean isOverwrite = true;

			String message="The Project: '" + new File(dir).getName().toString() + "' already exist in the Project folder. Do you want to overwrite the Project? \n\n\n WARNING: This action is permanent" ;
			if(new File(ProjectTreeComponent.getCurrentWorkspacePath()+ new File(dir).getName()).exists())
			{
				if(new File(ProjectTreeComponent.getCurrentWorkspacePath()+ new File(dir).getName()).getAbsolutePath().equals(dir)) {
					MessageDialog.openError(parent, "Importing Project", "Error: Could not import the same project that already exist in the workspace.");
					return; //if the dir == the existing path 
				}
				isOverwrite=MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Confirm", message);
			}
			if(isOverwrite){
				OperationProgressDialog rInfo = new OperationProgressDialog(Display.getCurrent().getActiveShell(),  "Importing Project...");
				rInfo.open();

				ProjectExplorerManager projectMan = new ProjectExplorerManager();
				projectMan.deleteFolder( new File(ProjectTreeComponent.getCurrentWorkspacePath()+ new File(dir).getName()));
				projectMan.copyDirectory(new File(dir), new File(ProjectTreeComponent.getCurrentWorkspacePath()+ new File(dir).getName()));


				projectTreeComponent = new ProjectTreeComponent(ProjectExplorerView.projectTree, new File(ProjectTreeComponent.getCurrentWorkspacePath()+ new File(dir).getName()).getName().toString());
				rInfo.close();
			}
		}
	}
}
