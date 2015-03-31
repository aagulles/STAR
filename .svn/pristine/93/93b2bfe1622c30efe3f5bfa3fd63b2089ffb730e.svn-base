package org.irri.breedingtool.projectexplorer.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class ProgressDialog extends Dialog {
/**
* @param parentShell
*/
	private String getProjectName;
private ProgressBar progressBar;
	
	
	
	
public String getGetProjectName() {
	return getProjectName;
}

private void setGetProjectName(String getProjectName) {
	this.getProjectName = getProjectName;
}

public ProgressDialog(Shell parentShell) {
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
	
}

@Override
protected Control createDialogArea(Composite parent) {
	parent.getShell().setText("Create new STAR Project");

Composite container = (Composite) super.createDialogArea(parent);
GridLayout gridLayout = (GridLayout) container.getLayout();
gridLayout.numColumns = 2;
new Label(container, SWT.NONE);
new Label(container, SWT.NONE);
new Label(container, SWT.NONE);
new Label(container, SWT.NONE);
progressBar = new ProgressBar(container, SWT.NONE);
GridData gd_progressBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
gd_progressBar.widthHint = 433;
progressBar.setLayoutData(gd_progressBar);



return container;

}
}