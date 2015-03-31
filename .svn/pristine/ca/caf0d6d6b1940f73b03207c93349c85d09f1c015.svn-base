package org.irri.breedingtool.application.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;

public class DialogHandler {

	public static HashMap<String, Dialog> dialogMap = new HashMap<String,Dialog>();

	public static boolean openDialog(final String dialogID, Dialog newShell){
	
		try {
			dialogMap.get(dialogID).getShell().setMinimized(false);
			return false;
		} catch (Exception e) {
			dialogMap.remove(dialogID);
			dialogMap.put(dialogID, newShell);
		}
		
		return true;
		
		
		

	}
	public static void closeAllPartDialogs(String partID){
		Iterator it = dialogMap.entrySet().iterator();
		System.out.println("Closing all dialogs for: " + partID);
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			if(pairs.getKey().toString().contains(partID)){
				((Dialog) pairs.getValue()).close();
				it.remove();
				dialogMap.remove(pairs.getKey());
				
			}
			System.out.println(pairs.getKey() + " = " + pairs.getValue());


			// avoids a ConcurrentModificationException
		}
	}
	public static void setPartDialogMaximized(String partID){
		Iterator it = dialogMap.entrySet().iterator();
			while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			if(pairs.getValue() != null  && ((Window) pairs.getValue()).getShell() != null){
			if( !((Window) pairs.getValue()).getShell().isDisposed()){
				if(pairs.getKey().toString().contains(partID)){
					((Dialog) pairs.getValue()).getShell().setMinimized(false);

				}
				else{

					((Dialog) pairs.getValue()).getShell().setMinimized(true);
				}
			}
			
			}
			else{
//				dialogMap.remove(pairs.getKey());
//				it.remove(); 	
			}
		}
	}
}
