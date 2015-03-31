package org.irri.breedingtool.utility;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;

import org.eclipse.swt.widgets.Text;
import org.irri.breedingtool.application.handler.DialogHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;

public class StarAnalysisUtilities {

	
	public static String createOutputFolder( String activeFilePath,String OutputFolder){
		ProjectExplorerManager projectMan = new ProjectExplorerManager();

		File activeFile = new File(activeFilePath);
		
		Calendar cldTime = Calendar.getInstance();
		File fileFolder  = new File(((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getPath() + File.separator +  activeFile.getName().replaceAll(".csv", "") + "(" + OutputFolder + "_" +cldTime.getTimeInMillis() + ")");
		if(!fileFolder.exists()) fileFolder.mkdir();



		return fileFolder.getPath() + File.separator;
	}
	public static int getTotalVariableLevels(String varName, String filePath){
		return DataManipulationManager.getEnvtLevels(varName, new File(filePath)).length;
	}

	public static String createOutputFile(String OutputFile){
		ProjectExplorerManager projectMan = new ProjectExplorerManager();

	
		Calendar cldTime = Calendar.getInstance();
		File fileFolder  = new File(((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getPath() + File.separator + OutputFile + "_" +cldTime.getTimeInMillis() + ".txt");
		



		return fileFolder.getPath() ;
	}
	
	public static void openAndRefreshFolder(String outputFile){
		ProjectExplorerManager projectMan = new ProjectExplorerManager();
		//projectMan.refreshFolder((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData());
		projectMan.refreshFolder((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData());
		
		if(!PartStackHandler.isOpen(outputFile)){
			
		PartStackHandler.execute(projectMan.getTreeNodeModelbyFilePath(outputFile));
		}
		else{
	PartStackHandler.reOpenPart(projectMan.getTreeNodeModelbyFilePath(outputFile));
		System.out.println(outputFile+" Part Re-Opened");
		}
		DialogHandler.setPartDialogMaximized("None");
		//if(new File(outputFile).exists()) PartStackHandler.closePart(projectMan.getTreeNodeModelbyFilePath(outputFile));
		
		

	}
	
	public static boolean factorsHasLevels(String[] factorList, String filePath){
		
		for(int i = 0; i < factorList.length; i++){
			if(DataManipulationManager.getEnvtLevels(factorList[i], new File(filePath)).length > 2 ) return true;
			
		}
					
		return false;
	}
	
	public static void testVarArgs( Object... parameters){
		System.out.println("---------------------- testVarArgs---------------");
		for(Object u: parameters)
		{
			
			if(u == null) System.out.println("Null");
			else
				System.out.println( (u.getClass().isArray())? Arrays.deepToString((Object[]) u) : u);


		}
		System.out.println("---------------------- EndtestVarArgs---------------");
	}

	public static  boolean validateTextFields( Text... fieldTexts){
		for(Text u: fieldTexts)
		{

			if(u.getText().equals("")) return false;

		}
		return true;
	}


}
