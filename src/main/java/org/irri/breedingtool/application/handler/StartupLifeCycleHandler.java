package org.irri.breedingtool.application.handler;


import java.io.File;
import java.io.IOException;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.dialog.SelectWorkspaceDialogNew;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.utility.GeneralUtility;

public class StartupLifeCycleHandler {

public  boolean isWorkspaceNew = false;
public  String newWorkspacePath = "";
	@PostContextCreate
	public void startup(IEclipseContext context) {
		ProjectExplorerManager projMan = new ProjectExplorerManager();
		final Shell shell = new Shell(SWT.INHERIT_NONE);
		if(!projMan.getWorkspacePath().equals(""))
		if( new File(projMan.getWorkspacePath()).exists() && GeneralUtility.isDirectoryWritable(projMan.getWorkspacePath())) return;
		System.out.println("Startup LifeCycle Triggered");
		
		if(!GeneralUtility.isDirectoryWritable(projMan.getWorkspacePath())){
			MessageDialog.openError(shell, "Workspace Error", "The workspace path: '" + projMan.getWorkspacePath() + "' is not a valid filepath.\n \nThe path does not exist OR permission denied");
		}
			
		SelectWorkspaceDialogNew dlg = new SelectWorkspaceDialogNew(shell);
		dlg.open();
		
		isWorkspaceNew = true;
		newWorkspacePath = dlg.workspacePath;
		if(dlg.isCancelled) return;
		if(isWorkspaceNew){
			
			
			File sampleProject = new File(dlg.workspacePath + File.separator + "SampleProject");
		
				sampleProject.mkdirs();
				try {
					projMan.copySampleProjectTo(sampleProject.getAbsolutePath());
//					ProjectTreeComponent	projectTreeComponent = new ProjectTreeComponent(ProjectExplorerView.projectTree, "SampleProject");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			

		
	}
		
	}

} 
