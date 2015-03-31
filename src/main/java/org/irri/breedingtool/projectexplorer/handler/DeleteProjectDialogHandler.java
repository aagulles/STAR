package org.irri.breedingtool.projectexplorer.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.dialog.DeleteProjectsDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;

import com.sun.jna.platform.FileUtils;



public class DeleteProjectDialogHandler {
	ProjectTreeComponent	projectTreeComponent ;
	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public static void deleteProject(final Shell parent) {
			
		
			if(ProjectExplorerView.rootTree.getItemCount() == 0) return;
			if(ProjectExplorerView.projectTree.getText().length() <= 3) return;
			ProjectExplorerManager projectMan = new ProjectExplorerManager();
			
			
			DeleteProjectsDialog dlgDeleteProjects = new DeleteProjectsDialog(Display.getCurrent().getActiveShell());
			dlgDeleteProjects.open();
			
			ArrayList<File> projectsSelected = dlgDeleteProjects.getProjectsSelected();
			if(projectsSelected.isEmpty()) return;
			OperationProgressDialog dlgProgress = new OperationProgressDialog(parent, "Sending Project(s) to Recycle/Trash bin...");
			dlgProgress.open();
			FileUtils fileUtil = FileUtils.getInstance();
			for(int i = 0; i < projectsSelected.size(); i++){
			
				if(projectsSelected.get(i).getName().equals(ProjectExplorerView.projectTree.getText())){
					ProjectExplorerView.projectTree.clearAll(true);
					ProjectExplorerView.projectTree.removeAll();
					
					ProjectExplorerView.projectTree.setText("...");
					RJavaManagerInitializer.isProjectActive = false;
					PartStackHandler.removeAll();
					projectMan.saveLastOpenedProject("null");
					Display.getDefault().getActiveShell().setText(RJavaManagerInitializer.APPLICATION_TITLE);
					
				}
				try {
					fileUtil.moveToTrash(new File[]{projectsSelected.get(i)});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						fileUtil.moveToTrash(new File[]{projectsSelected.get(i)});
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
			dlgProgress.close();
			
			//OpenProjectDialogHandler.launchCreateNewProjectWizard(Display.getCurrent().getActiveShell());
			
	}
}
