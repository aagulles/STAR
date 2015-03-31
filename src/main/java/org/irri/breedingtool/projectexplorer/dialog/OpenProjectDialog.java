package org.irri.breedingtool.projectexplorer.dialog;

import java.io.File;
import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;

public class OpenProjectDialog extends Dialog {
/**
* @param parentShell
*/
	private String getProjectName = "";
	private List projectList;
	private Label lblDateCreated;
	
	
	
	
public String getGetProjectName() {
	return getProjectName;
}

private void setGetProjectName(String getProjectName) {
	this.getProjectName = getProjectName;
}
@Override
protected Point getInitialSize() {
	return new Point(429, 330);
}
public OpenProjectDialog(Shell parentShell) {
super(parentShell);

        //forces open method to block until window is closed.
setBlockOnOpen(true);

}

@Override
protected void createButtonsForButtonBar(Composite parent) {
 createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
  createButton(parent, IDialogConstants.CANCEL_ID,
      IDialogConstants.CANCEL_LABEL, false);
  
}
@Override
protected void okPressed(){
	 PartStackHandler.removeAll();
	   setGetProjectName( projectList.getItem(projectList.getSelectionIndex()).toString());
	  
	this.close();
	
}

@Override
protected Control createDialogArea(Composite parent) {
	parent.getShell().setText("Open Existing Project from the Workspace");

Composite container = (Composite) super.createDialogArea(parent);
new Label(container, SWT.NONE);
Listener listener = new Listener() {
    public void handleEvent(Event event) {
  	 
  	  switch (event.type) {
         case SWT.MouseDoubleClick:


	    	  okPressed();
	    	  
	    	    
        	 break;
         case SWT.MouseDown:
        	 String projectNameSelected = projectList.getItem(projectList.getSelectionIndex()).toString();
        	 ProjectExplorerManager projectExplorerManager = new ProjectExplorerManager();
        	 System.out.println(projectExplorerManager.getProjectDescription(projectNameSelected));
        	// projectDescription.setText(projectExplorerManager.getProjectDescription(projectNameSelected));
      		lblDateCreated.setText("Date Created: " + projectExplorerManager.getProjectDateCreated(projectNameSelected));
      		lblDateCreated.setVisible(true);
          	break;
  	  }
    }
  };

 projectList = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
projectList.addListener (SWT.MouseDoubleClick, listener);
projectList.addListener (SWT.MouseDown, listener);
File folder = new File(ProjectTreeComponent.getCurrentWorkspacePath());

File[] getDir = folder.listFiles();

Arrays.sort(getDir);

for (File fileEntry : getDir) 	if(fileEntry.isDirectory()){
	projectList.add(fileEntry.getName());

}


GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
gd_list.widthHint = 420;
gd_list.heightHint = 381;
projectList.setLayoutData(gd_list);
 lblDateCreated = new Label(container, SWT.NONE);
 lblDateCreated.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
lblDateCreated.setVisible(false);

lblDateCreated.setText("Date created:");




return container;

}
}