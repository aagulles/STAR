package org.irri.breedingtool.projectexplorer.view;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.MenuHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.handler.StartupLifeCycleHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.ChooseDelimiterDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.dialog.ImportDataFromRDialog;
import org.irri.breedingtool.projectexplorer.dialog.ImportFromOtherProjectsDialog;
import org.irri.breedingtool.projectexplorer.dialog.MoveToDialog;
import org.irri.breedingtool.projectexplorer.dialog.NewProjectDialog;
import org.irri.breedingtool.projectexplorer.dialog.OpenProjectDialog;
import org.irri.breedingtool.projectexplorer.handler.DeleteProjectDialogHandler;
import org.irri.breedingtool.rjava.manager.RJavaManager;
import org.irri.breedingtool.utility.CheckFileExistValidator;
import org.irri.breedingtool.utility.GeneralUtility;


public class ProjectExplorerView {

	/**
	 *
	 * @wbp.parser.entryPoint
	 */

	ProjectTreeComponent projectTreeComponent;
	public static Tree rootTree;
	String projectLocation="";
	public static TreeItem projectTree;
	public static RJavaManager rJavaManager=new RJavaManager();
	public static boolean ACTIVATED = false;
	ProjectExplorerManager projectExplorerManager = new ProjectExplorerManager();
	public static Shell parentShell;

	@Inject
	public ProjectExplorerView(final Composite parent)  {

		RJavaManagerInitializer.Initialize();
		Platform.addLogListener(new ILogListener(){

			@Override
			public void logging(IStatus status, String plugin) {

				//				if(status.getMessage().toString().equals("Internal Error")){
				//				System.out.println("=====================START ERROR REPORT========================");
				//				System.out.println("--------------------Message---------");
				//				System.out.println(status.getMessage());
				//				System.out.println("-------------Error--------------");
				//				System.out.println(status.getException() + "The error");
				//				
				//				System.out.println("---------------Code--------------");
				//				System.out.println(status.getCode());
				//				System.out.println("=====================END ERROR REPORT========================");
				//				
				//				}
			}

		});
		parent.getShell().setMaximized(true);
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));



		Display display = Display.getCurrent();
		Clipboard clipboard = new Clipboard(display);
		clipboard.clearContents();

		rootTree = new Tree(composite, SWT.BORDER + SWT.MULTI);
	
		projectTree = new TreeItem(rootTree,0);
		if(!new File(ProjectTreeComponent.getCurrentWorkspacePath()+ "//" + projectExplorerManager.getLastOpenedProject()).exists()){
			projectTree.setText("No active project selected.");
			return;
		}
		projectTreeComponent = new ProjectTreeComponent(projectTree, projectExplorerManager.getLastOpenedProject());

		parent.getShell().setText(RJavaManagerInitializer.APPLICATION_TITLE + projectExplorerManager.getProjectPath());
		if(!new File(projectExplorerManager.getProjectPath()).exists()){
			parent.getShell().setText(RJavaManagerInitializer.APPLICATION_TITLE+ projectExplorerManager.getProjectPath());
			
			

		}

		parentShell = parent.getShell();

	}

	public void resetMenu(){
		setMenu();
	}
	private void setMenu(){
		Menu menu = new Menu(rootTree);
		rootTree.setMenu(menu);

		MenuItem menuItem;
		if(rootTree.getItemCount() == 0) return;
		if(projectTree == null || rootTree.getSelection()[0] == null) return;

		if(rootTree.getSelection()[0].getText().toString().length() > 3 ){
			if ( rootTree.getSelection()[0] == projectTree ) {
				menuItem = new MenuItem(menu, SWT.NONE);
				menuItem.setText("Rename Project");
				menuItem.addListener(SWT.Selection, listenerRenameProject());
				menuItem.setImage(ResourceManager.getPluginImage(
						projectExplorerManager.getPackageName(),
						"icons/renameFile.png"));
				menuItem = new MenuItem(menu, SWT.NONE);
				menuItem.setText("Delete Project");
				menuItem.addListener(SWT.Selection, listenerDeleteProject());
				menuItem.setImage(ResourceManager.getPluginImage(
						projectExplorerManager.getPackageName(),
						"icons/deleteProject.png"));
				menuItem = new MenuItem(menu, SWT.NONE);
				menuItem.setText("Export Project");
				menuItem.addListener(SWT.Selection, listenerExportProject());
			}


		}
		else{
			return;
		}
		File selectedFile = ((ProjectExplorerTreeNodeModel)rootTree.getSelection()[0].getData()).getProjectFile();
		ProjectExplorerManager projectExplorerManager = new ProjectExplorerManager();



		if(projectExplorerManager.getDataFolder(projectTree).equals(rootTree.getSelection()[0]) || selectedFile.getAbsolutePath().contains(((ProjectExplorerTreeNodeModel) projectExplorerManager.getDataFolder(projectTree).getData()).getProjectFile().getPath())){

			if(selectedFile.isDirectory()){
				menuItem = new MenuItem(menu, SWT.NONE);
				menuItem.setText("Import Data");
				menuItem.addListener(SWT.Selection,listenerImportData(rootTree.getSelection()[0]));
				menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/importData.png"));

				menuItem = new MenuItem(menu, SWT.NONE);
				menuItem.setText("Import Data from Other Project");
				menuItem.addListener(SWT.Selection,listenerImportDataFromOtherProject(rootTree.getSelection()[0]));
				menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/importFromOtherProjects.png"));



				menuItem = new MenuItem(menu, SWT.NONE);
				menuItem.setText("Import Data from Package");
				menuItem.addListener(SWT.Selection,listenerImportDataFromPackage(rootTree.getSelection()[0]));

				new MenuItem(menu, SWT.SEPARATOR);
			}

		}

		else{

		}




		TreeItem[] fileExeceptions = {projectExplorerManager.getDataFolder(projectTree), projectExplorerManager.getOutputFolder(projectTree),projectTree}; 
		if(compareTreeItemArray(fileExeceptions, rootTree.getSelection()) == false){



			if(rootTree.getSelectionCount() == 1){
				menuItem = new MenuItem(menu, SWT.NONE);
				menuItem.setText("Rename");
				menuItem.addListener(SWT.Selection,listenerRenameFile());
				menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/renameFile.png"));

			}
			menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText("Delete");
			menuItem.addListener(SWT.Selection,listenerDeleteFile());
			menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/deleteFile.png"));

			menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText("Copy");
			menuItem.addListener(SWT.Selection,listenerCopyFile());
			menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/copyFile.png"));



			menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText("Move to");
			menuItem.addListener(SWT.Selection,listenerMoveTo());
			menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/moveTo.png"));


		}

		if(rootTree.getSelection()[0] != projectTree){

			if(!GeneralUtility.isClipboardEmpty()){
				menuItem = new MenuItem(menu, SWT.NONE);
				menuItem.setText("Paste");
				menuItem.addListener(SWT.Selection,listenerPasteFile());
				menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/pasteFile.png"));

			}

			menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText("Create New Folder");
			menuItem.addListener(SWT.Selection,listenerCreateNewFolder());
			menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/newFolder.png"));


			menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText("Refresh");
			menuItem.addListener(SWT.Selection,listenerRefreshFolder());
			menuItem.setImage(ResourceManager.getPluginImage(projectExplorerManager.getPackageName(), "icons/refresh.png"));



		}




	}
	private Listener listenerCreateNewFolder(){
		Listener menuListener = new Listener(){
			public void handleEvent (Event event){
				ProjectExplorerManager projectMan = new ProjectExplorerManager();	

				if( !((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getData()).getProjectFile().isDirectory()){
					InputDialog dlgNewFolderFile = new InputDialog(Display.getCurrent().getActiveShell(),

							"New Folder", "Enter the new folder name", rootTree.getSelection()[0].getText(), new CheckFileExistValidator("New Folder", ((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getParentItem().getData()).getProjectFile().getPath(),false));

					dlgNewFolderFile.open();

					projectMan.createNewFolder(((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getParentItem().getData()).getProjectFile().getPath(),dlgNewFolderFile.getValue());
					projectMan.refreshFolder((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getParentItem().getData());

				}
				else{
					InputDialog dlgNewFolderFile = new InputDialog(Display.getCurrent().getActiveShell(),

							"New Folder", "Enter the new folder name", rootTree.getSelection()[0].getText(), new CheckFileExistValidator("New Folder", ((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getData()).getProjectFile().getPath(),false));
					dlgNewFolderFile.open();
					projectMan.createNewFolder(((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getData()).getProjectFile().getPath(),dlgNewFolderFile.getValue());
					projectMan.refreshFolder((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getData());
				}
			}
		};
		return menuListener;
	}

	private Listener listenerRenameProject(){
		Listener menuListener = new Listener(){
			public void handleEvent(Event event){
				ProjectExplorerManager projectMan = new ProjectExplorerManager();
				InputDialog dlgRenameFile = new InputDialog(Display.getCurrent().getActiveShell(),

						"Rename Project", "Enter the new Project Name", ProjectExplorerView.projectTree.getText(), new CheckFileExistValidator(ProjectExplorerView.projectTree.getText().toString(), ProjectTreeComponent.getCurrentWorkspacePath(), false));

				if (dlgRenameFile.open() == Window.OK){

					if(projectMan.renameProject(dlgRenameFile.getValue()))
					{
						ProjectTreeComponent	projectTreeComponent ;

						projectTreeComponent = new ProjectTreeComponent(ProjectExplorerView.projectTree, dlgRenameFile.getValue());
						PartStackHandler.removeAll();
					}



				}		
			}
		};
		return menuListener;
	}

	private Listener listenerExportProject(){
		Listener menuListener = new Listener(){
			public void handleEvent (Event event){
				DirectoryDialog dlg = new DirectoryDialog(Display.getCurrent().getActiveShell());




				dlg.setText("Export Project");


				dlg.setMessage("Select a Directory");


				String dir = dlg.open();
				if (dir != null) {
					boolean isOverwrite = true;

					String message="The Project: '" + new File(dir + File.separator + projectTree.getText().toString()).getName().toString() + "' already exist in the chosen folder. Do you want to overwrite the Project? \n\n\n WARNING: This action is permanent" ;
					if(new File(dir + File.separator + projectTree.getText().toString()).exists())
						isOverwrite=MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Confirm", message);
					if(isOverwrite){

						ProjectExplorerManager projectMan = new ProjectExplorerManager();
						if(new File(dir + File.separator + projectTree.getText().toString()).exists())projectMan.deleteFolder(new File(dir + File.separator + projectTree.getText().toString()));

						projectMan.copyDirectory(new File(projectMan.getProjectPath()), new File(dir + File.separator + projectTree.getText().toString()));
					}

				}
			}
		};
		return menuListener;
	}

	private Listener listenerMoveTo(){
		Listener menuListener = new Listener(){
			public void handleEvent (Event event){

				MoveToDialog moveToDialog = new MoveToDialog(Display.getCurrent().getActiveShell());
				File destinationFolder = moveToDialog.open();
				ProjectExplorerManager projectMan = new ProjectExplorerManager();
				String errMessage = null;
				if(destinationFolder == null) return;

				for(int i = 0; i < rootTree.getSelectionCount(); i++){

					if(!((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile().getPath().equals(destinationFolder.getPath())){
						if(PartStackHandler.isOpen(((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile().getAbsolutePath()) || PartStackHandler.isOpen(destinationFolder.getPath() + File.separator + rootTree.getSelection()[i].getText())){
							if(errMessage == null){
								errMessage = ((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile().getName();
							}
							else{
								errMessage = errMessage + ", " + ((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile().getName();
							}
						}
						else{
							//System.out.println(destinationFolder.getPath() + File.separator + rootTree.getSelection()[i].getText());
							if(((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile().isDirectory()){
								boolean toDelete = projectMan.copyDirectory(((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile(), new File(destinationFolder.getAbsolutePath() + File.separator + rootTree.getSelection()[i].getText()));
								if(toDelete)projectMan.deleteFolder(((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile());
							}
							else{
								Boolean toDelete = projectMan.copyFile(((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile(), new File(destinationFolder.getPath().toString() + 
										File.separator + ((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile().getName()));
								if(toDelete)((ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData()).getProjectFile().delete();

							}	
						}
						if(errMessage != null){
							MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error File Operation", "The file(s) [" + errMessage + "]" + " is still active in the application. Close the file(s) first.");
							return;
						}
					}
				}
				projectMan.refreshFolder((ProjectExplorerTreeNodeModel) projectMan.getDataFolder(projectTree).getData());
				projectMan.refreshFolder((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(projectTree).getData());

			}
		};
		return menuListener;
	}

	private Listener listenerRefreshFolder(){
		Listener menuListener = new Listener(){
			public void handleEvent (Event event){

				ProjectExplorerTreeNodeModel fileModel = (ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getData();
				if(!fileModel.getProjectFile().isDirectory()) 
					fileModel = (ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getParentItem().getData();
				ProjectExplorerManager projMan = new ProjectExplorerManager();
				projMan.refreshFolder(fileModel);

			}
		};
		return menuListener;
	}

	private Listener listenerPasteFile(){
		Listener menuListener = new Listener(){
			public void handleEvent (Event event){

				ProjectExplorerTreeNodeModel fileModel = (ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getData();
				if(!fileModel.getProjectFile().isDirectory()) 
					fileModel = (ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getParentItem().getData();
				ProjectExplorerManager projMan = new ProjectExplorerManager();
				projMan.pasteFromClipboard(fileModel.getProjectFile().getPath().toString(),projectTreeComponent.PROJECT_PATH);
				projMan.refreshFolder(fileModel);

			}
		};
		return menuListener;
	}
	private Listener listenerCopyFile(){

		Listener menuListener = new Listener(){
			public void handleEvent (Event event){

				TreeItem selection[] = rootTree.getSelection();
				String[] fileNames = new String[selection.length];

				fileNames[0] = null;

				for(int i = 0; i < selection.length; i++){
					System.out.println(fileNames[i]);
					ProjectExplorerTreeNodeModel fileModel = (ProjectExplorerTreeNodeModel) selection[i].getData();
					fileNames[i] = fileModel.getProjectFile().getPath();
				}
				projectExplorerManager.copyToClipboard(fileNames);
			}
		};
		return menuListener;


	}


	private Listener listenerRenameFile(){

		Listener menuListener = new Listener(){
			public void handleEvent (Event event){
				String filePath =  ((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getData()).getProjectFile().getPath();
				if(PartStackHandler.isOpen(filePath)){
					MessageDialog.openError(Display.getCurrent().getActiveShell(), "File Operation Error", "Could not rename file: " + new File(filePath).getName() + " . Close the file first" );
					return;
				}
				InputDialog dlgRenameFile = new InputDialog(Display.getCurrent().getActiveShell(),

						"Rename File", "Enter the new file name", rootTree.getSelection()[0].getText(), new CheckFileExistValidator(rootTree.getSelection()[0].getText().toString(), ((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getParentItem().getData()).getProjectFile().getPath(), false));

				if (dlgRenameFile.open() == Window.OK)
				{
					String newFileName;
					if(new File(filePath).isDirectory()){
						newFileName = dlgRenameFile.getValue();
					}
					else{
						newFileName = (dlgRenameFile.getValue().contains(GeneralUtility.getFileExtension(filePath))) ? dlgRenameFile.getValue() : dlgRenameFile.getValue() + "." + GeneralUtility.getFileExtension(filePath);

					}
					if(projectExplorerManager.renameFile(filePath, newFileName)) 
					{
						ProjectExplorerManager projectMan = new ProjectExplorerManager();
						projectMan.refreshFolder((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getParentItem().getData());
					}

					//rootTree.getSelection()[0].setText(dlgRenameFile.getValue());
				} 
			}
		};
		return menuListener;
	}
	private Listener listenerDeleteFile(){

		Listener menuListener = new Listener(){
			public void handleEvent(Event event){
				TreeItem[] selection = rootTree.getSelection();
				String errMessage = null;
				ArrayList<TreeItem> parentSelection = new ArrayList<TreeItem>();


				boolean confirm = false;
				if(selection.length <= 1) 
				{
					if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Confirm Delete", "Are you sure you want to delete '" + selection[0].getText() + "'?  \n\n\n This file will be moved to Trash/Recycle Bin" )) confirm = true;
				}
				else{
					if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Confirm Delete", "Are you sure you want to delete these " + selection.length + " elements?  \n\n\n These files will be moved to Trash/Recycle Bin" )) confirm = true;
				}
				if(confirm){
					for(int i = 0; i < selection.length; i++){
						if(PartStackHandler.isOpen(((ProjectExplorerTreeNodeModel) selection[i].getData()).getProjectFile().getAbsolutePath())){
							if(errMessage == null){
								errMessage = ((ProjectExplorerTreeNodeModel) selection[i].getData()).getProjectFile().getName();
							}
							else{
								errMessage = errMessage + ", " + ((ProjectExplorerTreeNodeModel) selection[i].getData()).getProjectFile().getName();
							}
						}
						else{
							if (!selection[i].isDisposed()) {
								ProjectExplorerTreeNodeModel fileModel = (ProjectExplorerTreeNodeModel) selection[i]
										.getData();
								if (projectExplorerManager
										.deleteFile(fileModel.getProjectFile()
												.getPath().toString()))
									parentSelection.add(selection[i].getParentItem());
							}
						}

					}
					if(errMessage != null )MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error File Operation", "The file(s) [" + errMessage + "]" + " is still active in the application. Close the files first.");
					else{
						for(int i = 0; i < parentSelection.size(); i++){
							if(!parentSelection.get(i).isDisposed())projectExplorerManager.refreshFolder((ProjectExplorerTreeNodeModel) parentSelection.get(i).getData());

						}
					}
				}
			}
		};
		return menuListener;
	}

	private Listener listenerNewProject(final MenuItem menuItem){
		Listener menuListener = new Listener(){
			public void handleEvent(Event event){
				NewProjectDialog newProjectDialog = new NewProjectDialog(Display.getCurrent().getActiveShell());


				if(	newProjectDialog.open() == 0){

					projectTreeComponent = new ProjectTreeComponent(projectTree, newProjectDialog.getGetProjectName());

				}
			}
		};
		return menuListener;

	}
	private Listener listenerDeleteProject(){
		Listener menuListener = new Listener(){
			public void handleEvent(Event event){
				DeleteProjectDialogHandler.deleteProject(Display.getCurrent().getActiveShell());
				resetMenu();
			}
		};
		return menuListener;

	}



	private Listener listenerOpenExistingProject(final MenuItem menuItem){
		Listener menuListener = new Listener() {
			public void handleEvent(Event event){
				OpenProjectDialog openProjectDialog = new OpenProjectDialog(Display.getCurrent().getActiveShell());
				if(	openProjectDialog.open() == 0){
					projectTreeComponent = new ProjectTreeComponent(projectTree, openProjectDialog.getGetProjectName());
				}
			}
		};
		return menuListener;
	}


	private Listener listenerImportData(final TreeItem treeItem){
		// Importing Data Process

		Listener menuListener = new Listener() {
			private boolean displayedWarningMessage=false;

			public void handleEvent(Event event){
				DataManipulationManager dataManipulationManager = new DataManipulationManager();
				FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.MULTI);
				fileDialog.setText("Import");
				String[] filterExt = { "*.csv", "*.rda", "*.txt"};
				fileDialog.setFilterPath(((ProjectExplorerTreeNodeModel)projectExplorerManager.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getAbsolutePath());
				fileDialog.setFilterExtensions(filterExt);
				String selectedFile = fileDialog.open();
				String source;
				if(selectedFile == null) return;

				StringBuffer buf = new StringBuffer();
				String[] files = fileDialog.getFileNames();
				for (int i = 0, n = files.length; i < n; i++) {
					buf.append(fileDialog.getFilterPath());
					if (buf.charAt(buf.length() - 1) != File.separatorChar) {
						buf.append(File.separatorChar);
					}
					buf.append(files[i]);
					buf.append(";");
				}
				source= buf.toString();

				String refreshFolderPath = null;
				String[] sources = source.split(";");
				if(sources[0].endsWith(".csv")){//csv file
					File destinationFolder = ((ProjectExplorerTreeNodeModel) treeItem.getData()).getProjectFile();
					ArrayList<File> sourceFileList = new ArrayList<File>();
					//getFilesToBeImported
					for(int i=0; i<sources.length; i++){
						System.out.println("source: "+ sources[i]);
						sourceFileList.add(new File(sources[i]));
					}
					File fileSources[] = sourceFileList.toArray(new File[sourceFileList.size()]);
					//getDestinationFolder
					if(!destinationFolder.isDirectory()){
						destinationFolder = new File(destinationFolder.getParentFile().getAbsolutePath() + File.separator );
					}
					else{
						destinationFolder = new File(destinationFolder.getAbsolutePath() + File.separator  );
					}

					projectExplorerManager.copyBatchFile(fileSources, destinationFolder);
					projectExplorerManager.refreshFolder((ProjectExplorerTreeNodeModel) projectExplorerManager.getTreeNodeModelbyFilePath(destinationFolder.getAbsolutePath()));
				}
				else{//if rda or txtFile
					for(String s : sources){//if there are multiple selected, import each file.
						String sourceFilePath = s;
						System.out.println("Selected File: "+ sourceFilePath);
						String outputFileName = new File(s).getName();
						File destination = ((ProjectExplorerTreeNodeModel) treeItem.getData()).getProjectFile();
						if(!destination.isDirectory()){
							destination = new File(destination.getParentFile().getAbsolutePath() + File.separator + outputFileName);
						}
						else{
							destination = new File(destination.getAbsolutePath() + File.separator  + outputFileName);
						}
						if(PartStackHandler.isOpen(projectExplorerManager.getProjectPath() + File.separator + "Data" + File.separator  + sourceFilePath)){
							MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error File Operation", "Could not import file: " + sourceFilePath + ". An active data with same filename was detected. Close the file first.");
							return ;
						}

						String destinationFilePath= destination.getAbsolutePath();
						String selectedFileName = new File(s).getName();
						refreshFolderPath = destinationFilePath;
						if(s.endsWith(".rda")){// If importing rda file
							destinationFilePath = destinationFilePath.replace(".rda",".csv");
							String newDestinationFilePath = destinationFilePath.replace(".csv", 
									dataManipulationManager.getManipulatedFileNameExtension("", destinationFilePath));
							sourceFilePath = s.replace(".rda", ".csv");
							if( !displayedWarningMessage && MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Convert RDA to CSV", "You have imported an R Data File.\n\nIt will automatically be converted to a csv file.")) displayedWarningMessage = true;
							if(displayedWarningMessage){
								if(!destinationFilePath.equals(newDestinationFilePath)){
									MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "File '"+new File(destinationFilePath).getName()+"' already exist.", "File name will be changed to '"+new File(newDestinationFilePath).getName()+"'.");
									ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().convertRdaToCsv(selectedFile.replaceAll(Pattern.quote(File.separator ), "/"), newDestinationFilePath.replaceAll(Pattern.quote(File.separator ), "/"));
								}
								else ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().convertRdaToCsv(selectedFile.replaceAll(Pattern.quote(File.separator ), "/"),destinationFilePath.replaceAll(Pattern.quote(File.separator ), "/"));
								projectExplorerManager.refreshFolder((ProjectExplorerTreeNodeModel) projectExplorerManager.getTreeNodeModelbyFilePath(new File(refreshFolderPath).getParentFile().getAbsolutePath()));
							}
						}
						else if(s.endsWith(".txt")){// If importing txt file
							destinationFilePath = destinationFilePath.replace(".txt",".csv");
							String newDestinationFilePath = destinationFilePath.replace(".csv", 

									dataManipulationManager.getManipulatedFileNameExtension("", destinationFilePath));
							sourceFilePath = s.replace(".txt", ".csv");
							if(!displayedWarningMessage && MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Convert TXT to CSV", "You have imported a txt file.\n\nIt will automatically be converted to a csv file.")) displayedWarningMessage = true;
							if(displayedWarningMessage){
								ChooseDelimiterDialog chooseDelimiter = new ChooseDelimiterDialog(Display.getCurrent().getActiveShell(), true, dataManipulationManager.getDisplayFileName(new File(s).getName()));
								chooseDelimiter.open();
								String delimiter = chooseDelimiter.getDelimiter();
								if(chooseDelimiter.getReturnCode()==0 && !delimiter.equals("")){//view as table
									if(!destinationFilePath.equals(newDestinationFilePath)){
										MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "File '"+new File(destinationFilePath).getName()+"' already exist.", "File name will be changed to '"+new File(newDestinationFilePath).getName()+"'.");
										dataManipulationManager.convertTxtToCsv(s,newDestinationFilePath,delimiter);
									}
									else dataManipulationManager.convertTxtToCsv(s,destinationFilePath,delimiter);
									projectExplorerManager.refreshFolder((ProjectExplorerTreeNodeModel) projectExplorerManager.getTreeNodeModelbyFilePath(new File(refreshFolderPath).getParentFile().getAbsolutePath()));
								}
							}
						}
					}
				}
			}
		};
		return menuListener;
	}
	private Listener listenerImportDataFromOtherProject(final TreeItem treeItem){
		// Importing Data Process

		Listener menuListener = new Listener() {
			public void handleEvent(Event event){

				ImportFromOtherProjectsDialog importDialog = new ImportFromOtherProjectsDialog(Display.getCurrent().getActiveShell());

				ArrayList<File> returnData = importDialog.open();
				if (returnData != null && !returnData.isEmpty()) {
					ProjectExplorerManager projectMan = new ProjectExplorerManager();
					String errMessage = null;
					for (int i = 0; i < returnData.size(); i++) {

						if(PartStackHandler.isOpen(projectMan.getProjectPath() + File.separator + "Data" + File.separator + returnData.get(i).getName())){


							if(errMessage == null) errMessage = returnData.get(i).getName();
							else errMessage = errMessage + ", " + returnData.get(i).getName();
							returnData.remove(i);
						}
					}
					for (int i = 0; i < returnData.size(); i++) {
						if (returnData.get(i).toString().endsWith(".csv")|| returnData.get(i).toString().endsWith(".rda")
								|| new File(returnData.get(i).toString())
						.isDirectory())
						{
							File destination = ((ProjectExplorerTreeNodeModel) treeItem.getData()).getProjectFile();
							if(!destination.isDirectory()) {
								destination = new File(destination.getParentFile().getAbsolutePath() + File.separator + returnData.get(i).getName());
							}
							else{
								destination = new File(destination.getAbsolutePath() + File.separator + returnData.get(i).getName());
							}
							projectMan.toImport(returnData.get(i).toString(),
									destination.getPath(), projectTreeComponent.PROJECT_PATH);
						}
					}
					projectExplorerManager
					.refreshFolder((ProjectExplorerTreeNodeModel) projectExplorerManager.getDataFolder(projectTree)
							.getData());

					if(errMessage != null){
						MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error File Operation", "The file(s) [" + errMessage + "]" + " is still active in the application. Close the files first.");

					}
				}

			}
		};
		return menuListener;
	}

	private Listener listenerImportDataFromPackage(final TreeItem treeItem){
		Listener menuListener = new Listener(){
			public void handleEvent (Event event){
				File destinationFolder = ((ProjectExplorerTreeNodeModel) treeItem.getData()).getProjectFile();

				if(!destinationFolder.isDirectory()){
					destinationFolder = new File(destinationFolder.getParentFile().getAbsolutePath());
				}
				else{
					destinationFolder = new File(destinationFolder.getAbsolutePath());
				}
				System.out.println("folder: "+ destinationFolder.getAbsolutePath());
				ImportDataFromRDialog importFromRDialog = new ImportDataFromRDialog(Display.getCurrent().getActiveShell(), destinationFolder.getAbsolutePath());
				try{
					importFromRDialog.open();
					projectExplorerManager.refreshFolder((ProjectExplorerTreeNodeModel) projectExplorerManager.getTreeNodeModelbyFilePath(destinationFolder.getAbsolutePath()));
				}catch(Exception e){}
			}
		};
		return menuListener;
	}

	private boolean compareTreeItemArray(TreeItem[] arrayA, TreeItem[] arrayB){
		boolean foundSwitch = false;
		boolean isFound = false;
		//outer loop for all the elements in arrayA[i]
		for(int i = 0; i < arrayA.length; i++)
		{
			//inner loop for all the elements in arrayB[j]
			for (int j = 0; j < arrayB.length;j++)
			{
				//compare arrayA to arrayB and output results
				if( arrayA[i] == arrayB[j])
				{
					foundSwitch = true;
					isFound = true;
					//					System.out.println( "arrayA element " + arrayA[i].getText() + " was found in arrayB" );
				}
			}
			if (foundSwitch == false)
			{
				//			System.out.println( "arrayA element " + arrayA[i].getText() + " was NOT found in arrayB" );
			}
			//set foundSwitch bool back to false
			foundSwitch = false;
		}
		return isFound;
	}
	@PostConstruct
	public void initProjectExplorerView(){

	

		rootTree.addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event e) {
				PartStackHandler.initISaveHandler();
				if(PartStackHandler.getActiveElementID() == null)	{
					if(Display.getCurrent().getActiveShell() != null)if(Display.getCurrent().getActiveShell().getText().length() < 3) Display.getCurrent().getActiveShell().setText(RJavaManagerInitializer.APPLICATION_TITLE);
					MenuHandler.setDataDependentMenuVisible(false);
				}

			}
		});


		//	setMenu();	


		//	projectTree.addListener (SWT.MouseDoubleClick, listener);
		rootTree.addListener (SWT.MouseDown, new Listener(){
			public void handleEvent(Event event){

				//		PartStackHandler.addRetainer();
				if(rootTree.getSelectionCount() > 0  && rootTree.getItem(0).getItemCount() > 0){

					setMenu();

				}
				else {
					try {
						rootTree.getMenu().dispose();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
			}

		});

		rootTree.setFocus();


		rootTree.addListener(SWT.MouseDoubleClick, new Listener(){
			public void handleEvent(Event event){
				TreeItem[] selection = rootTree.getSelection();
				if(rootTree.getSelectionCount() > 0){
					if(selection[0].getItemCount() == 0 ){ //if the treeItem is a leaf Node
						ProjectExplorerTreeNodeModel fileModel = (ProjectExplorerTreeNodeModel) selection[0].getData();

						if(!fileModel.getProjectFile().isDirectory()){ // check if It's not a directory
							System.out.println(fileModel.getProjectFile().getAbsolutePath().toString());
							try{
								PartStackHandler.execute(fileModel);
							}catch(SWTException e){}
							catch(NullPointerException ex){}
						}
					}
					else{
						
						File randomizationFile = new File(((ProjectExplorerTreeNodeModel)projectExplorerManager.getOutputFolder(projectTree).getData()).getProjectFile().getAbsolutePath() + File.separator + "Randomization");
						
						if(selection[0].getParentItem() ==projectExplorerManager.getOutputFolder(projectTree)){
								ProjectExplorerTreeNodeModel selectedNode = (ProjectExplorerTreeNodeModel) selection[0].getData();
								
							if(!selectedNode.getProjectFile().getAbsolutePath().contains(randomizationFile.getAbsolutePath())){
								ProjectExplorerTreeNodeModel fileModel = (ProjectExplorerTreeNodeModel) selection[0].getData();
							
								PartStackHandler.execute(fileModel);
							}
						}


						if(((ProjectExplorerTreeNodeModel) selection[0].getData()).getProjectFile().getPath().contains(((ProjectExplorerTreeNodeModel) projectExplorerManager.getOutputFolder(projectTree).getData()).getProjectFile().getPath())){
							ProjectExplorerTreeNodeModel selectedNode = (ProjectExplorerTreeNodeModel) selection[0].getData();
							
							if(!selectedNode.getProjectFile().getAbsolutePath().contains(randomizationFile.getAbsolutePath())){
								ProjectExplorerTreeNodeModel fileModel = (ProjectExplorerTreeNodeModel) selection[0].getData();

								PartStackHandler.execute(fileModel);

							}
						}



					}
				}
			}
		});


		DragSource ds = new DragSource(rootTree, DND.DROP_MOVE);
		ds.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		ds.addDragListener(new DragSourceAdapter() {

			public void dragStart(final DragSourceEvent event) {
				for(int i = 0; i < rootTree.getSelectionCount(); i++){
					if(rootTree.getSelection()[i] == projectTree || rootTree.getSelection()[i] == projectExplorerManager.getDataFolder(projectTree) || rootTree.getSelection()[i] == projectExplorerManager.getOutputFolder(projectTree)){
						//To cancel the drag if the TreeItem is == projectTree
						event.doit = false;

					}
				}
			}
			public void dragSetData(DragSourceEvent event) {

				String draggedFiles[] = new String[rootTree.getSelectionCount()];
				for(int i = 0; i < rootTree.getSelectionCount(); i++){

					ProjectExplorerTreeNodeModel nodeModel = (ProjectExplorerTreeNodeModel) rootTree.getSelection()[i].getData();
					draggedFiles[i] = nodeModel.getProjectFile().getPath();
				}
				event.data = draggedFiles;
			}
			public void dragFinished(final DragSourceEvent event) {
				ProjectExplorerManager projectExplorerManager = new ProjectExplorerManager();
				if(rootTree.getSelectionCount() > 0) projectExplorerManager.refreshFolder((ProjectExplorerTreeNodeModel) rootTree.getSelection()[0].getParentItem().getData());

			}
		});

		DropTarget dt = new DropTarget(rootTree, DND.DROP_MOVE);
		dt.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter() {
			public void drop(DropTargetEvent event) {
				if (event.data == null || event.item == null || event.item == projectTree) {
					event.detail = DND.DROP_NONE;
					return;
				}
				TreeItem item = (TreeItem) event.item;

				String draggedFiles[] = (String[]) event.data;
				System.out.println(item.getText());

				ProjectExplorerManager projectExplorerManager = new ProjectExplorerManager();
				ProjectExplorerTreeNodeModel nodeModel = (ProjectExplorerTreeNodeModel) item.getData();
				if(!nodeModel.getProjectFile().isDirectory()){
					//If destination node is not a directory
					nodeModel = (ProjectExplorerTreeNodeModel) nodeModel.getParentTreeItem().getData();
				}
				else{
					System.out.println("Occurenc");	
				}
				ArrayList<String> errorFiles = new ArrayList<String>();

				for(int i = 0; i < draggedFiles.length; i++){


					if (!PartStackHandler.isOpen(draggedFiles[i])) {
						System.out.println("Destination: "
								+ nodeModel.getProjectFile().getPath());
						System.out.println("Source: " + draggedFiles[i]);
						File sourceFile = new File(draggedFiles[i]);
						File destinationFolder = new File(nodeModel
								.getProjectFile().getPath()
								+ "//"
								+ sourceFile.getName());
						if (!destinationFolder.getParentFile().getPath()
								.toString()
								.equals(sourceFile.getParentFile().getPath())) {
							if (!sourceFile.isDirectory()) {

								if (projectExplorerManager.copyFile(sourceFile,
										destinationFolder)) {
									sourceFile.delete();
								}
							} else {
								if (projectExplorerManager.copyDirectory(
										sourceFile, destinationFolder)) {
									projectExplorerManager
									.deleteFolder(sourceFile);
								}
							}
						}
					}
					else{
						errorFiles.add(new File(draggedFiles[i]).getName());
					}
				}
				projectExplorerManager.refreshFolder(nodeModel);
				if(!errorFiles.isEmpty()){
					MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Cannot move files", "Error moving file(s) [" + GeneralUtility.combineArrayOfString(errorFiles.toArray(new String[errorFiles.size()]), ", ") + "]. Make sure all the files are not active before performing this operation");
				}
				//System.out.println("File Type: " + nodeModel.getProjectFile().getName());
			}
		});
	}
	
	public static void refreshApplicationTitle(){
		
		RJavaManagerInitializer.refreshApplicationTitle();
		Display.getCurrent().getActiveShell().setText(RJavaManagerInitializer.APPLICATION_TITLE + new ProjectExplorerManager().getProjectPath());
		System.out.println(RJavaManagerInitializer.APPLICATION_TITLE);
		
	}
}
