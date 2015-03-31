package org.irri.breedingtool.projectexplorer.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.projectexplorer.dialog.OpenProjectDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;



public class OpenProjectDialogHandler {
	
	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public static void launchCreateNewProjectWizard(final Shell parent) {
		OpenProjectDialog openProject = new OpenProjectDialog(parent);

	
		if(openProject.open() == 0) 	
	{ 
			ProjectTreeComponent	projectTreeComponent ;
			
		projectTreeComponent = new ProjectTreeComponent(ProjectExplorerView.projectTree, openProject.getGetProjectName());

	}
		
		 
	}
	
}
