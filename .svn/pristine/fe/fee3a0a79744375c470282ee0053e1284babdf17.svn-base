package org.irri.breedingtool.projectexplorer.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;

public class DeleteProjectsDialog extends Dialog {
	private Tree dialogTree;
	private ArrayList<File> selectedProjects = new ArrayList<File>();
	private Label lblSelectProjectsTo;
	private Button btnDeleteMultiProjects;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DeleteProjectsDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));
		ProjectExplorerManager projMan = new ProjectExplorerManager();
		
		Label lblDeleteProject = new Label(container, SWT.NONE);
		lblDeleteProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDeleteProject.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		lblDeleteProject.setText("Delete Project: " + projMan.getLastOpenedProject());
		
		btnDeleteMultiProjects = new Button(container, SWT.CHECK);
		btnDeleteMultiProjects.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblSelectProjectsTo.setEnabled(btnDeleteMultiProjects.getSelection());
				dialogTree.setEnabled(btnDeleteMultiProjects.getSelection());
				
			}
		});
		btnDeleteMultiProjects.setText("I want to delete multiple projects");
		
		lblSelectProjectsTo = new Label(container, SWT.NONE);
		lblSelectProjectsTo.setEnabled(false);
		lblSelectProjectsTo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSelectProjectsTo.setText("Select Projects to be deleted:");
		
		CheckboxTreeViewer checkboxTreeViewer = new CheckboxTreeViewer(container, SWT.BORDER);
		dialogTree = checkboxTreeViewer.getTree();
		dialogTree.setEnabled(false);
		dialogTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
	
		File projectsDir = new File(ProjectTreeComponent.getCurrentWorkspacePath());
		
		for(File project : projectsDir.listFiles()){
			if(project.isDirectory()){
				TreeItem trtmNewTreeitem = new TreeItem(dialogTree, SWT.NONE);
				trtmNewTreeitem.setText(project.getName());
				trtmNewTreeitem.setData(project);
				
			}
		
		}
		return container;
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

	@Override
	protected void okPressed(){
		selectedProjects.clear();
		for(int i =0; i < dialogTree.getItemCount(); i++)
		{
			if(dialogTree.getItem(i).getChecked())
			selectedProjects.add((File) dialogTree.getItem(i).getData());
			
		}
		if(selectedProjects.isEmpty()) {
			if(!btnDeleteMultiProjects.getSelection()){
				ProjectExplorerManager projMan = new ProjectExplorerManager();
				File currProject = new File(ProjectTreeComponent.getCurrentWorkspacePath()+ File.separator  + projMan.getLastOpenedProject());
				selectedProjects.add(currProject);
				this.close();
			}
			return;
		}
		
		this.close();
		
	}
	public ArrayList<File> getProjectsSelected(){
		return selectedProjects;
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

}
