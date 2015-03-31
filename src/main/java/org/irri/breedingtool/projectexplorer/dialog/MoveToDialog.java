package org.irri.breedingtool.projectexplorer.dialog;

import java.io.File;

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
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;

public class MoveToDialog extends Dialog {

	protected Object result;
	protected Shell shlMoveTo;
	protected File returnFolder;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public MoveToDialog(Shell parent) {
		super(parent, SWT.RESIZE);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public File open() {
		createContents();
		shlMoveTo.open();
		shlMoveTo.layout();
		Display display = getParent().getDisplay();
		while (!shlMoveTo.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return returnFolder;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlMoveTo = new Shell(getParent(),  SWT.CLOSE + SWT.RESIZE + SWT.MIN);
		shlMoveTo.setSize(427, 537);
		shlMoveTo.setText("Move to");
		shlMoveTo.setLayout(new GridLayout(1, false));
		ProjectExplorerManager projectMan = new ProjectExplorerManager();
		
		final Tree tree = new Tree(shlMoveTo, SWT.BORDER);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		final TreeItem leafData = new TreeItem(tree, SWT.NONE);
		leafData.setImage(ResourceManager.getPluginImage("Star", "icons/folder.png"));
		leafData.setText("Data");
		leafData.setExpanded(true);
		Composite composite = new Composite(shlMoveTo, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));
	
		Button btnChooseFolder = new Button(composite, SWT.NONE);
		btnChooseFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(tree.getSelection()[0] == leafData){
				//	MessageDialog.openError(Display.getCurrent().getActiveShell(), "Move To - Error", "Cannot select the main project folder. Select only the")
				}
				returnFolder = ((ProjectExplorerTreeNodeModel)tree.getSelection()[0].getData()).getProjectFile();
				shlMoveTo.close();
				
			}
		});
		btnChooseFolder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnChooseFolder.setText("Choose Folder");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				returnFolder = null;
			shlMoveTo.close();
			}
		});
		btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		File projectFile = new File(ProjectTreeComponent.getCurrentWorkspacePath()+ projectMan.getLastOpenedProject() + File.separator +"Data");
		leafData.setData(new ProjectExplorerTreeNodeModel(projectFile, leafData));
		
		
		projectMan.listFolder(projectFile, leafData);
		//trtmProject.getItem(1).dispose();
	}
}
