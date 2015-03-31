package org.irri.breedingtool.application.handler;

import java.util.Arrays;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;





public class MenuHandler {

	@Inject
	private static EPartService service;
	@Inject
	private static MApplication application;
	@Inject
	private static EModelService modelService;



	public static String PackageName = "Star";
	@SuppressWarnings("restriction")




	@Execute
	public static void execute() {
		//stack.setVisible(true);

	}
	@Execute
	public static void sssetDesignMenuEnabled(boolean value) {
		
		MMenu designMenu =  (MMenu) application.getChildren().get(0).getMainMenu().getChildren().get(1);
		MMenu designTestMenu =  (MMenu) application.getChildren().get(0).getMainMenu().getChildren().get(1);
		designMenu.setEnabled(value);
		for(int i = 0; i < designMenu.getChildren().size(); i++){
			if(designMenu.getChildren().get(i) instanceof MDirectMenuItem)
			{
				MDirectMenuItem directMenuTest =  (MDirectMenuItem) designMenu.getChildren().get(i);
				directMenuTest.setEnabled(value);
			
			
			}
			else if(designMenu.getChildren().get(i) instanceof MMenu){
				MMenu subMenuList = (MMenu) designMenu.getChildren().get(i);
				
				
				for(int x = 0; x < subMenuList.getChildren().size(); x++){
					
					MDirectMenuItem directMenu =  (MDirectMenuItem) subMenuList.getChildren().get(x);
					directMenu.setEnabled(value);
					
				}
				subMenuList.setEnabled(value);
			}
		}


		//GARBAGE CODES - Please do not delete 
		//		 MDirectMenuItem newItem = MMenuFactory.INSTANCE.createDirectMenuItem();
		//			newItem.setContributionURI("");
		//			newItem.setLabel("HEH");
		//			MMenu target = (MMenu) findMenuElement(application, "star.design.menu");
		//			target.getChildren().add(newItem);
		//				 
		//			 application.getChildren().get(0).getMainMenu().getChildren().get(1).notify();
		//			 application.getChildren().get(0).getMainMenu().setToBeRendered(true);
		//			 application.getChildren().get(0).getMainMenu().notify();
		//			 application.getChildren().get(0).getMainMenu().notifyAll();
		//		designMenu.setVisible(false);
		//		designMenu.getVisibleWhen();
		//		

		//		System.out.println(designMenu.getElementId());
		//		MMenu mainMenu = application.getChildren().get(0).getMainMenu();
		//		mainMenu.setToBeRendered(true);
		//		
		//		designMenu.setToBeRendered(true);
		//		 application.getChildren().get(0).getMainMenu().getChildren().remove(1);
		//		 designMenu = application.getChildren().get(0).getMainMenu().getChildren().get(1);
		//		 designMenu.getElementId();
		//		
		//		for(int i = 0; i < mainMenu.getChildren().size(); i++){
		//			System.out.println(mainMenu.getChildren().get(i).getElementId());
		//			//MMenuElement test = mainMenu.getC
		//		}
		//		
	}
	public static void setDataDependentMenuVisible(boolean value){
		String[] menus = new String[]{"Data","Analysis","Analyze"};
//		setOnlyVisibleMenu(menus,value);

	}
	public static void setOnlyVisibleMenu(String[] menus,boolean value){
		for(int x = 0; x < application.getChildren().get(0).getMainMenu().getChildren().size(); x++){
			MMenu menu =  (MMenu) application.getChildren().get(0).getMainMenu().getChildren().get(x);
			for(int i = 0; i < menu.getChildren().size(); i++)
			if(Arrays.asList(menus).contains(menu.getLabel().toString())){
				if(menu.getChildren().get(i) instanceof MDirectMenuItem)
				{
					((MDirectMenuItem) menu.getChildren().get(i)).setVisible(value);
				}
				else if(menu.getChildren().get(i) instanceof MMenu){
					
					setOnlyVisibleMenu((MMenu) menu.getChildren().get(i),value);
					menu.getChildren().get(i).setVisible(value);
				}
				else{
			
				}
			}
			else{
			
			}
		}
	}
	public static void deleteMenu(String menuLabel){
		for(int x = 0; x < application.getChildren().get(0).getMainMenu().getChildren().size(); x++){
			MMenu menu =  (MMenu) application.getChildren().get(0).getMainMenu().getChildren().get(x);
			if(menu.getLabel().toString().equals(menuLabel)) menu.setVisible(false);
		}
	}
	public static void setOnlyVisibleMenu(MMenu menu,boolean value){
		for(int i = 0; i < menu.getChildren().size(); i++){
			if(menu.getChildren().get(i) instanceof MDirectMenuItem)
			{
				((MDirectMenuItem) menu.getChildren().get(i)).setVisible(value);
			}
			else if(menu.getChildren().get(i) instanceof MMenu){
				
				setOnlyVisibleMenu((MMenu) menu.getChildren().get(i),value);
				menu.getChildren().get(i).setVisible(value);
			}
			else{
				
			}
		}
		
	}
	public static void setOnsslyVisibleDataMenu(String menuType){

		MMenu dataMenu =  (MMenu) application.getChildren().get(0).getMainMenu().getChildren().get(2);
		for(int i = 0; i < dataMenu.getChildren().size(); i++){
			if(dataMenu.getChildren().get(i) instanceof MDirectMenuItem)
			{
				MDirectMenuItem directDataMenu =  (MDirectMenuItem) dataMenu.getChildren().get(i);
				if(directDataMenu.getAccessibilityPhrase().toString().equals(menuType)){
					directDataMenu.setVisible(true);
				}
				else{
					directDataMenu.setVisible(false);
					
				}
			}
			else if(dataMenu.getChildren().get(i) instanceof MMenu){
				MMenu subMenuList = (MMenu) dataMenu.getChildren().get(i);
			
				if(subMenuList.getAccessibilityPhrase().toString().equals(menuType)){
					subMenuList.setVisible(true);
				}
				else{
					subMenuList.setVisible(false);
				}
				
				for(int x = 0; x < subMenuList.getChildren().size(); x++){
					
					if ( dataMenu.getChildren().get(i) instanceof MDirectMenuItem) {
						MDirectMenuItem directDataMenu = (MDirectMenuItem) dataMenu
								.getChildren().get(i);
						if (directDataMenu.getAccessibilityPhrase().toString()
								.equals(menuType)) {
							directDataMenu.setVisible(true);
						} else {
							directDataMenu.setVisible(false);

						}
					}
						
				}
		
			}
		}
	}
} 