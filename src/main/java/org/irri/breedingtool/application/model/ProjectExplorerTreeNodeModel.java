package org.irri.breedingtool.application.model;

import java.io.File;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.swt.widgets.TreeItem;

public class ProjectExplorerTreeNodeModel {

	private String elementID;
	private TreeItem treeItem;
	private TreeItem parentTreeItem;
	private MPart elementInStack;
	private File projectFile;
	private boolean modifiedStatus;
	private String fileType;

	public ProjectExplorerTreeNodeModel(File file, TreeItem leaf){

		this.treeItem = leaf;
		this.elementID=file.getPath();
		this.projectFile=file;
		this.parentTreeItem=leaf.getParentItem();
		this.modifiedStatus = false;
		if(file.isDirectory()){
			this.fileType="Folder";
		}else {
			this.fileType=file.getName().toString().substring(file.getName().toString().length() - 4);
		}

	}


	public String getElementID() {
		return elementID;
	}


	public TreeItem getTreeItem() {
		return treeItem;
	}

	public TreeItem getParentTreeItem() {
		return parentTreeItem;
	}


	public MStackElement getElementInStack() {
		return elementInStack;
	}

	public void setElementInStack(MPart elementInStack) {
		this.elementInStack = elementInStack;
	}

	public File getProjectFile() {
		return projectFile;
	}


	public boolean isModified() {
		return modifiedStatus;
	}

	public void setModifiedStatus(boolean modifiedStatus) {
		this.modifiedStatus = modifiedStatus;
	}

	public String getFileType() {
		return fileType;
	}


}
