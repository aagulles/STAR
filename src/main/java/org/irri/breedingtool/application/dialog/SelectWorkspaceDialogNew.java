package org.irri.breedingtool.application.dialog;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;


public class SelectWorkspaceDialogNew extends Dialog {

	public String workspacePath = "E:\\New Folder 4\\";
	private Text txtWorkspacePath;
	private Button btnCopySampleProject;
	private boolean isSampleProjectImported;
	public boolean isCancelled = true;
	private ProjectExplorerManager projMan = new ProjectExplorerManager();
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SelectWorkspaceDialogNew(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		
		Label lblSelectWorkSpace = new Label(area, SWT.NONE);
		lblSelectWorkSpace.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lblSelectWorkSpace.setText("Select WorkSpace");
		
		Label lblChooseAWorkspace = new Label(area, SWT.NONE);
		lblChooseAWorkspace.setText("Choose a workspace folder to use for this session.");
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblWorkspace = new Label(container, SWT.NONE);
		lblWorkspace.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWorkspace.setText("Workspace:");
		
		txtWorkspacePath = new Text(container, SWT.BORDER);
		txtWorkspacePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		if(projMan.getWorkspacePath().equals("NULL"))
			txtWorkspacePath.setText(System.getProperty("user.home"));
		else
		txtWorkspacePath.setText(projMan.getWorkspacePath());
		Button btnBrowseFolder = new Button(container, SWT.NONE);
		btnBrowseFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 DirectoryDialog dlg = new DirectoryDialog(getShell());

			        // Set the initial filter path according
			        // to anything they've selected or typed in
			        dlg.setFilterPath(txtWorkspacePath.getText());

			        // Change the title bar text
			        dlg.setText("Workspace");

			        // Customizable message displayed in the dialog
			        dlg.setMessage("Select a workspace directory to use");

			        // Calling open() will open and run the dialog.
			        // It will return the selected directory, or
			        // null if user cancels
			        String dir = dlg.open();
			        if (dir != null) {
			          // Set the text box to the new selection
			          txtWorkspacePath.setText(dir);
			        }
			}
		});
		btnBrowseFolder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnBrowseFolder.setText("Browse");
		new Label(container, SWT.NONE);
		
		btnCopySampleProject = new Button(container, SWT.CHECK);
		btnCopySampleProject.setSelection(true);
		btnCopySampleProject.setText("Import Sample Project");
		new Label(container, SWT.NONE);
//IF Sample project doesn't exist or IOException, disable and uncheck checkbutton. 
		try {
			File applicationPath = new File(new File(".").getCanonicalPath() + File.separator + "Projects" + File.separator + "SampleProject");
			if(!applicationPath.exists()) {
				btnCopySampleProject.setEnabled(false);
				btnCopySampleProject.setSelection(false);
			}
		} catch (IOException e1) {

			btnCopySampleProject.setEnabled(false);
			btnCopySampleProject.setSelection(false);
		}
		return area;
	}

	@Override
	protected void okPressed(){
		

		File workspace = new File(txtWorkspacePath.getText());
		
		if(!workspace.exists()){
			workspace.mkdirs();
			if(!workspace.exists()){
				MessageDialog.openError(getShell(), "Workspace Error", "The workspace path: '" +txtWorkspacePath.getText()+ "' is not a valid filepath.\n \nThe path does not exist OR permission denied");
				return;
			}
		}
		if(!txtWorkspacePath.getText().startsWith(workspace.getAbsolutePath()))
		{
			
			MessageDialog.openError(getShell(), "Workspace Error", "The workspace path: '" +txtWorkspacePath.getText()+ "' is not a valid filepath.");
			return;
		}
		else{
			System.out.println("DEBUG: " + txtWorkspacePath.getText() + " : " + workspace.getAbsolutePath());
		}
		projMan.saveWorkspacePath(txtWorkspacePath.getText());
		if(btnCopySampleProject.getSelection()) projMan.saveLastOpenedProject("SampleProject");
		
		
		isSampleProjectImported = btnCopySampleProject.getSelection();
		workspacePath = txtWorkspacePath.getText();
		isCancelled = false;
		
		close();
	}
	public boolean isDialogCancelled(){
		return isCancelled;
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(628, 245);
	}

}
