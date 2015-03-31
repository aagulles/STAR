package org.irri.breedingtool.utility;

import java.io.File;

import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;

public class PBToolAnalysisUtilities {
	
	
	public static String getOutputFolderName(String analysisType) {
		// TODO Auto-generated method stub
		String outputFolderName;
		if(analysisType.equals("NCI")) outputFolderName =  "NorthCarolinaI" ;
		else if(analysisType.equals("NCII")) outputFolderName = "NorthCarolinaII" ;
		else if(analysisType.equals("NCIII")) outputFolderName = "NorthCarolinaIII" ;
		else if(analysisType.equals("TripleTestCross"))outputFolderName = "TripleTestCross";
		else if(analysisType.equals("GriffingI")) outputFolderName = "DiallelGMI";
		else if(analysisType.equals("GriffingII")) outputFolderName = "DiallelGMII";
		else if(analysisType.equals("GriffingIII")) outputFolderName = "DiallelGMIII";
		else if(analysisType.equals("GriffingIV")) outputFolderName = "DiallelGMIV";
		else if(analysisType.equals("SingleEnv"))outputFolderName = "SingleSite";
		else if(analysisType.equals("twoStageMultiEnv")) outputFolderName = "TwoStageMultiSite";
		else if(analysisType.equals("oneStageMultiEnv"))outputFolderName = "OneStageMultiSite";
		else if(analysisType.equals("GMASummaryStat")) outputFolderName =  "GenerationMean";
		else if(analysisType.equals("GMARawData")) outputFolderName = "GenerationMeanRawData";
		else if(analysisType.equals("QTL")) outputFolderName = "QTL";
		else if(analysisType.equals("selectionIndex")) outputFolderName = "SelectionIndex";
		else if(analysisType.equals("stabilityModels")) outputFolderName = "stabilityModels"; 
		else if(analysisType.equals("MultiplicativeModels")) outputFolderName = "MultiplicativeModels"; 
		else if(analysisType.equals("GeneralLinearModel")) outputFolderName = "GeneralLinearModel";
		else outputFolderName = "unknown analysis";
		
		return "("+ outputFolderName+"_"+DataManipulationManager.getTimeStamp()+")\\";
	}

	public static File createOutputFolder(String fileName, String analysisType) {
		// TODO Auto-generated method stub
		ProjectExplorerManager projExpMan =  new ProjectExplorerManager();
		
		String dataFileName = fileName.replaceAll(".csv", "");
		String outputFolderPath = ((ProjectExplorerTreeNodeModel)projExpMan.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getAbsolutePath();
		String newFolderName = getOutputFolderName(analysisType);
		
		File outputFolder = new File(outputFolderPath+"//"+dataFileName+newFolderName);
		if(!outputFolder.exists()) outputFolder.mkdir();
		
		return outputFolder;
	}

	public static void openFolder(File outputFolder) {
		// TODO Auto-generated method stub
		ProjectExplorerManager projExpMan =  new ProjectExplorerManager();
		
		projExpMan.refreshFolder(projExpMan.getTreeNodeModelbyFilePath(outputFolder.getAbsolutePath()));
		ProjectExplorerTreeNodeModel newFileModel = projExpMan.getTreeNodeModelbyFilePath(outputFolder.getAbsolutePath());
		if(newFileModel.getElementInStack() == null){
			PartStackHandler.execute(newFileModel);
		}
		else{
			PartStackHandler.reOpenPart(newFileModel);
		}
		
	}

}
