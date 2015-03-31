package org.irri.breedingtool.projectexplorer.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.ResourceManager;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;

public class ImportFromOtherProjectsDialog extends Dialog {

	protected Object result;
	protected Shell shlMoveTo;
	protected ArrayList<File> returnData = new ArrayList<File>() ;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ImportFromOtherProjectsDialog(Shell parent) {
		super(parent, SWT.RESIZE);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public ArrayList<File> open() {
		createContents();
		shlMoveTo.open();
		shlMoveTo.layout();
		Display display = getParent().getDisplay();
		while (!shlMoveTo.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return returnData;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlMoveTo = new Shell(getParent(),  SWT.CLOSE + SWT.RESIZE + SWT.MIN);
		shlMoveTo.setSize(404, 517);
		shlMoveTo.setText("Import from Other Project");
		shlMoveTo.setLayout(new GridLayout(1, false));
		ProjectExplorerManager projectMan = new ProjectExplorerManager();
		
		final Tree tree = new Tree(shlMoveTo,  SWT.BORDER + SWT.MULTI);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TreeItem trtmProject = new TreeItem(tree, SWT.NONE);
		trtmProject.setImage(ResourceManager.getPluginImage("Star", "icons/package.png"));
		trtmProject.setText("Projects");
		
		Composite composite = new Composite(shlMoveTo, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));
		
		Button btnChooseFolder = new Button(composite, SWT.NONE);
		btnChooseFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for(int i = 0; i < tree.getSelectionCount(); i++)
				{
				
					if(tree.getSelection()[i].getData() != null ){
					if(tree.getSelection()[i].getParentItem().getData() != null) {
					if(!returnData.contains(((ProjectExplorerTreeNodeModel)tree.getSelection()[i].getParentItem().getData()).getProjectFile()))
						returnData.add(((ProjectExplorerTreeNodeModel)tree.getSelection()[i].getData()).getProjectFile());
					}
					else{
						returnData.add(((ProjectExplorerTreeNodeModel)tree.getSelection()[i].getData()).getProjectFile());
					}
					}
				}
				shlMoveTo.close();
				
			}
		});
		btnChooseFolder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnChooseFolder.setText("Import Data");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				returnData = null;
			shlMoveTo.close();
			}
		});
		btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		File projectFile = new File(ProjectTreeComponent.getCurrentWorkspacePath());
		
		
		projectMan.listFolder(projectFile, trtmProject);
		projectMan.listFiles(projectFile, trtmProject);
		trtmProject.setExpanded(true);
		for (int i = 0; i < trtmProject.getItemCount(); i++) {
			if(trtmProject.getItem(i).getText().equals(ProjectExplorerView.projectTree.getText())) trtmProject.getItem(i).dispose();
		}
		if(trtmProject.getItemCount() == 0){
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "", "There is no other project to import");
			return;
			
		}
		
	}
}
