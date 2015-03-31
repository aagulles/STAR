package org.irri.breedingtool.manager.api;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.widgets.TreeItem;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;

public interface IProjectExplorerManager {
	
	
	public ProjectExplorerTreeNodeModel createNewProjectExplorerModel(ProjectExplorerTreeNodeModel parentModel, String fileName);
	
	public boolean createNewProject(String projectName, String projectDescription);
	
	public String getProjectDescription(String projectName);
	
	public String getProjectDateCreated(String projectName);
	
	
	/**
	 * Get Leaf node of a Tree
	 * 
	 * @param leaf - Start leaf of a Treenode
	 * @param searchTreeNode - Name of the node to search
	 */
	public	TreeItem getTreeNodeByName(TreeItem leaf, String searchTreeNode);

	
	public ProjectExplorerTreeNodeModel getTreeNodeModelbyFilePath(String filPath);
	/**
	 * Returns the Data folder under the Project(root) folder
	 * 
	 * @param projectTree - the current project tree
	 */
	public TreeItem getDataFolder(TreeItem projectTree);

	/**
	 * Returns the Output folder under the Project(root) folder
	 * 
	 * @param projectTree - the current project tree
	 */
	public TreeItem getOutputFolder(TreeItem projectTree);

	/**
	 * Returns the last opened project via resources/star.ini
	 * 
	 */
	public String getLastOpenedProject();

	/**
	 * Returns the complete file path of resources/star.ini
	 * 
	 * 
	 */
	public String getApplicationINI();

	/**
	 * Return the package name
	 * 
	 * 
	 */
	public	 String getPackageName();
    public   String getProjectPath();
	/**
	 * Deletes the specified file
	 * 
	 * @param filePath - filePath of the file to be deleted
	 * @return boolean - if file successfully deleted
	 */
	public boolean deleteFile(String filePath);

	public boolean deleteFolder(File folder);
	/**
	 * Deletes a project
	 * 
	 * @param projectName - name of the project
	 * @return boolean - if project successfully deleted successfully deleted
	 */
	
	public	boolean deleteProject(String projectName);

	/**
	 * List all the folders from the specified directory/root folder
	 * 
	 * @param folder - root folder of the directory
	 * @param projectTree - the Current projectTree
	 * 
	 */
	public void listFolder(final File folder, TreeItem projectTree);

	/**
	 * List all the files from the specified directory/root folder
	 * 
	 * @param folder - root folder of the directory
	 * @param projectTree - the Current projectTree
	 * 
	 */
	public	void listFiles(final File folder, TreeItem projectTree);


	/**
	 * Copy a file to the destinationFolder
	 *  
	 * @param destinationFolder - the folder where the file will be copied
	 * @param sourceFile - the file that will be copied to the destination folder
	 */
	public boolean copyFile(File sourceFile, File destinationFile);
	public boolean copyBatchFile(File[] sourceFile, File destinationFile);
	
	
	
	/**
	 * Copy a directory to the destinationFolder
	 *  
	 * @param destinationFolder - the folder where the file will be copied
	 * @param sourceFile - the file that will be copied to the destination folder
	 */
	public boolean copyDirectory(File sourceDir, File destDir) ;
	
	/**
	 * Renames a file
	 * @param filePath - the path of the file that will be renamed
	 * @param newFileName - the new name of the file
	 * @return boolean - if successfully renamed the file
	 */
	public boolean renameFile(String filePath, String newFileName) throws IOException;

	/**
	 * Copy file(s) to the System Clipboard 
	 * @param fileName[] Path of the files
	 * 
	 */
	public void  copyToClipboard(String[] fileName);

	/**
	 * Retrieve the files from the clipboard and copy it under the specified folder path
	 * 
	 * @param folderPath - the folder which the files will be copied
	 * @param projectPath - the path of the current project
	 */
	public void pasteFromClipboard(String folderPath,String projectPath);

	/**
	 * Refresh a folder from the Project explorer
	 * 
	 * @param folderNode - the ProjectExplorerFileModel data (folderNode.getData())
	 */

	public	void refreshFolder(ProjectExplorerTreeNodeModel folderNode);

	/**
	 * Renames the name of the current project
	 * 
	 *  @param projectName - the project's name
	 *  @return boolean - success
	 */
	public	boolean renameProject(String projectName);

	/**
	 * Saves the opened project to the resources/star.ini
	 * 
	 * @param projectName - project's name to be saved in the INI file
	 * @return boolean - success 
	 */
	public  boolean saveLastOpenedProject(String projectName);

	/**
	 * Copies file to the folder 
	 * @param importDestination - the path of the file which will be copied to the project explorer
	 * @param optionalDestination - the destination path that the file will be copied. Note: optionalDestination can be null. 
	 * If null, the optionalDestination will be copied to the Data folder
	 * @param projectPath - the path of the project
	 * 	
	 *  
	 */
	public boolean toImport(String importDestination, String optionalDestination,String projectPath);

	/**
	 * Refresh the whole projectExplorer
	 * 
	 * @return boolean - success
	 */
	public	boolean redraw();

	/**
	 * Cleans all the nodes of project explorer
	 * 
	 * @param projectTree - the treeItem of the projectExplorer
	 * @return boolean - success
	 */
	public	boolean removeAllTreeItems(TreeItem projectTree);
	
	public void createNewFolder(String parentDestinationPath, String folderName);

	String getWorkspacePath();

	boolean saveWorkspacePath(String workspacePath);

	boolean copySampleProjectTo(String workspacePath) throws IOException;

}
