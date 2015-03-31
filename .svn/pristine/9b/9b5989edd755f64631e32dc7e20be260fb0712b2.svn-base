package org.irri.breedingtool.projectexplorer.dialog;

import java.io.File;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;


public class ImportDataFromRDialog extends Dialog {

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	
	private String pkgName = RJavaManagerInitializer.PACKAGE_NAME;
	private List list;
	private String outputFolderPath;
	
	public ImportDataFromRDialog(Shell parentShell, String outputFolderPath) {
		super(parentShell);
		this.outputFolderPath =outputFolderPath;
		System.out.println("final folder path: "+outputFolderPath);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Import Data from R");
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Label lblDatasetInStar = new Label(container, SWT.NONE);
		lblDatasetInStar.setBounds(10, 10, 164, 17);
		lblDatasetInStar.setText("Dataset in "+pkgName+" Package");
		
		list = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		list.setBounds(10, 29, 201, 144);
		try{
		list.setItems(ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().getDataInPackage(pkgName));
		}catch(Exception e){
			MessageDialog.openWarning(getShell(), "Failed to load data from "+pkgName, "The package "+pkgName+" is empty.");
			getShell().close();
		}
		return container;
	}
	
	@Override
	protected void okPressed(){
			if(list.getSelectionCount()>0){
				for(int i=0; i< list.getSelectionCount(); i++){
					String dataset = list.getSelection()[i];
					String outputPath = outputFolderPath.replaceAll(Pattern.quote(File.separator ), "/");
					System.out.println("final folder path: "+outputPath);
					ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().loadDataInPackage(pkgName, dataset, outputPath+"/");
				}
			MessageDialog.openInformation(getShell(), "Imported data from package.", "Successfully imported data from "+pkgName+".");
			close();
			}
			else MessageDialog.openWarning(getShell(), "Invalid Input.", "Please select the file you want to import.");
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
		return new Point(227, 284);
	}
}
