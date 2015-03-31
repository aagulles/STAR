package org.irri.breedingtool.utility;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.manager.api.IDataAnalysis;

public class WindowDialogControlUtil implements IDataAnalysis{

	private static ArrayList<Shell> dataAnalysisShells = new ArrayList<Shell>();
	
	public static void showWindowDialog(String analysis, String filePathID) {
		// TODO Auto-generated method stub
		for(Shell s: dataAnalysisShells){
			System.out.println("" + s.getData("filePathID").toString());
			if(s.getData("filePathID").toString().equals(filePathID)){
				if(s.getData("analysis").toString().equals(analysis)){
					s.setMinimized(false);
					s.setActive();
				}
			}
		}
	}
	
	public static void hideAllWindowDialog() {
		// TODO Auto-generated method stub
		for(Shell s: dataAnalysisShells){
			s.setMinimized(true);
		}
	}
	
	public static void addWindowDialogToList(Shell shell) {
		// TODO Auto-generated method stub
		dataAnalysisShells.add(shell);
	}
	
	public static void removeWindowDialogToList(Shell shell) {
		// TODO Auto-generated method stub
		dataAnalysisShells.remove(shell);
	}

	public static boolean doesWindowDialogForFileExist(String analysis,
			String filePathID) {
		// TODO Auto-generated method stub
		for(Shell s: dataAnalysisShells){
			if(s.getData("filePathID").toString().equals(filePathID)){
				if(s.getData("analysis").toString().equals(analysis)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void closeAllWindowDialogOfFile(String filePathID) {
		// TODO Auto-generated method stub
		ArrayList<Shell> shellsToBeClosed = new ArrayList<Shell>();
		
		//getAll the Shells that needs to be closed and put it in a to-close list
		for(Shell s: dataAnalysisShells){
			if(s.getData("filePathID").toString().equals(filePathID)){
				shellsToBeClosed.add(s);
			}
		}
		
		//close all the shells from the to-close list
		for(Shell s: shellsToBeClosed){
//				System.out.println("closing: "+s.getData("analysis").toString());
				dataAnalysisShells.remove(s);
				try{
				s.close();
				}catch(Exception e){
					System.out.println("caught exception at windowDialogControlUtil. :] "); 
				}
		}
	}
	
}
