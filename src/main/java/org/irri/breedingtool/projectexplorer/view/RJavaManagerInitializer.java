package org.irri.breedingtool.projectexplorer.view;

import java.io.File;

import org.irri.breedingtool.application.handler.MenuHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;

public class RJavaManagerInitializer {
	public static String APPLICATION_TITLE;
	public static String PACKAGE_NAME;
	public static String VARINFO_TMPFILE_EXTENSION="_varInfo.txt";
	public static String VARINFO_ORIGINALFILE_EXTENSION="_original.txt";
	public static boolean isStarInitialized = false;
	public static boolean isPBtoolsInitialized = false;
	public static boolean isProjectActive = false;
	public static void Initialize(){
	
		//WARNING! Do not Commit!

//		initStar();
		initPBTools();
		System.out.println("Initialized " + ((isStarInitialized) ? "STAR" : "PBTOOLS"));
		PartStackHandler.setStackNoCollapse();
	}
	private static void initStar(){
		ProjectExplorerView.rJavaManager.initStar();

		ProjectExplorerManager projMan = new ProjectExplorerManager();
		String workspaceName = new File(projMan.getProjectPath()).getName();
		APPLICATION_TITLE = "Statistical Tool for Agricultural Research - "  ;
		PACKAGE_NAME = "STAR";
		MenuHandler.deleteMenu("Analysis");
		MenuHandler.deleteMenu("Randomization");
		isStarInitialized = true;
	}
	private static void initPBTools(){
		ProjectExplorerManager projMan = new ProjectExplorerManager();	
		
		
		ProjectExplorerView.rJavaManager.initPBtool();
		String workspaceName = new File(projMan.getProjectPath()).getName();
		System.out.println(workspaceName);
		APPLICATION_TITLE = "Plant Breeding Tools - " ;
		PACKAGE_NAME = "PBTools";
		MenuHandler.deleteMenu("Graphs");
		MenuHandler.deleteMenu("Analyze");
		MenuHandler.deleteMenu("Design");
		isPBtoolsInitialized = true;
		
	}
	public static void refreshApplicationTitle(){
		ProjectExplorerManager projMan = new ProjectExplorerManager();
		
		String workspaceName = new File(projMan.getWorkspacePath()).getName();
		System.out.println(workspaceName);
		if(isPBtoolsInitialized)APPLICATION_TITLE = "Plant Breeding Tools - ";
		if(isStarInitialized) 	APPLICATION_TITLE = "Statistical Tool for Agricultural Research - ";
	}
}
