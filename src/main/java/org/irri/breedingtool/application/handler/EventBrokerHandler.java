package org.irri.breedingtool.application.handler;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;





public class EventBrokerHandler {
	private EventHandler testHandler;
	public String lastPartActiveID = "null";
	ProjectExplorerManager projMan = new ProjectExplorerManager();
	DataManipulationManager dataManipulationManager = new DataManipulationManager();
	@Inject
	private static EPartService service;
	@Inject
	private static MApplication application;
	@Inject
	private static EModelService modelService;
	@Inject private EPartService partService;
	public static void activate(){
		System.out.println("ACTIVATED");
	}

	@PostConstruct
	public void pc(IEventBroker eventBroker)
	{
		testHandler = new EventHandler() {
			
			@Override
			public void handleEvent(Event event) {
				final Object part = event.getProperty(UIEvents.EventTags.ELEMENT);
				boolean tbr =(Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
		  
				if (part instanceof MPart){
					if(((MPart) part).getElementId().equals("retainer")) return;
		    		// PartStackHandler.showRetainer();
		    	
					service.activate((MPart) part);
					service.showPart((MPart)part, PartState.ACTIVATE);
					
					//System.out.println("Part "+((MPart)part).getElementId()+" is "+(!tbr?"NOT":"")+" visible");
					if(!tbr){
					String elemId = ((MPart) part).getElementId();

						if((boolean)((MUILabel) part).getLabel().toString().startsWith("*")){
							  int style = SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL;
							    
							   
							    MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), style);
							    messageBox.setMessage("The file " + ((MUILabel) part).getLabel().replace("*", "") + " is modified. Would you like to save the file?");
							    int rc = messageBox.open();

							    switch (rc) {
							   
							    case SWT.CANCEL:
							    	service.activate((MPart) part);

							    	service.activate((MPart) part);

							    	service.activate((MPart) part);
							    	((MPart) part).setVisible(true);
							    	service.showPart((MPart)part, PartState.ACTIVATE);
							    	//Still making sure the part is still going to be activated when hit cancel...
							    
							    	    Runnable timer = new Runnable() {
							    	      public void run() {
							    	    		service.activate((MPart) part);
										    	((MPart) part).setVisible(true);
										    	service.showPart((MPart)part, PartState.ACTIVATE);
										    	    System.out.println("Timer Activated");    
							    	      }
							    	    };
							    	    Display.getCurrent().getActiveShell().getDisplay().timerExec(350, timer);
							    							    	    
							    	    
							      break;
							    case SWT.YES:
							    	
							    	try {
										Table table = DataManipulationManager.getActiveTable(elemId);
										DataManipulationManager.removeTable(table);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										//MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Oh no!", "table disposed");
									}
									WindowDialogControlUtil.closeAllWindowDialogOfFile(((MPart) part).getElementId());

							    	String originalTmpFilepath=((MPart) part).getElementId().replaceAll(".csv", RJavaManagerInitializer.VARINFO_ORIGINALFILE_EXTENSION);
							    	projMan.deleteFile(originalTmpFilepath);
							    	((MPart) part).getParent().getChildren().remove(part);
							      break;
							    case SWT.NO:
							    	 dataManipulationManager.keepOriginalFile(new File(elemId));
							    	Table table2;
							    	WindowDialogControlUtil.closeAllWindowDialogOfFile(((MPart) part).getElementId());

							    	
									try {
										table2 = DataManipulationManager.getActiveTable(elemId);
										DataManipulationManager.removeTable(table2);
									} catch (Exception e) {
										// TODO Auto-generated catch block
									//	MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Oh no!", "table disposed");
									}
							    	
									
							    	((MPart) part).getParent().getChildren().remove(part);
							      break;
							   
							    }
						
								
						}
						else{
							if(!((MPart) part).getElementId().equals("retainer"))
							((MPart) part).getParent().getChildren().remove(part);
						}
						
						
					
					}
					
				}
			}
		};
	//eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED, testHandler);
	}
	
	
	@Inject
	@Optional
	public void partActivation(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) 
	Event event) {
		// Do something
		//System.out.println(UIEvents.isCREATE(event));
	MWindow mainWindow = (MWindow) modelService.find("MainWindow", application);
	MPartStack ViewerStack = (MPartStack) modelService.find("ViewerStack", application);
	if(PartStackHandler.getActiveElementID() == null) return;
	
	
	ProjectExplorerManager projectMan = new ProjectExplorerManager();
	
		MPart activePart = (MPart) event.
				getProperty(UIEvents.EventTags.ELEMENT);
	if(ProjectExplorerView.rootTree.getItemCount() > 0){
		
		mainWindow.setLabel(RJavaManagerInitializer.APPLICATION_TITLE + projectMan.getWorkspacePath());
		
		
		 if(PartStackHandler.getActiveElementID() == null || ViewerStack.getChildren().isEmpty()){
			return;
		}
		 else if(PartStackHandler.getActiveElementID().toString().endsWith(".csv")){

			 mainWindow.setLabel(RJavaManagerInitializer.APPLICATION_TITLE + projectMan.getWorkspacePath());
				
				//		MenuHandler.setDesignMenuEnabled(true);
						MenuHandler.setDataDependentMenuVisible(true);
						//System.out.println("menu ACTIVATED");
						if(!lastPartActiveID.equals(PartStackHandler.getActiveElementID())) {
							
							lastPartActiveID = PartStackHandler.getActiveElementID();
						}
						
					}
		else{
//			MenuHandler.setDesignMenuEnabled(false);
			MenuHandler.setDataDependentMenuVisible(false);
			//System.out.println("menu deACTIVATED");
		}
		 System.out.println(PartStackHandler.getActiveElementID());
		 System.out.println(projectMan.getProjectPath());
		 
		 String newWindowTitle = RJavaManagerInitializer.APPLICATION_TITLE + PartStackHandler.getActiveElementID().replace("retainer", "");
		 
		 mainWindow.setLabel(newWindowTitle);
			
		 
	}
		if(!activePart.getElementId().toString().equals("ProjectExplorer"))
			if(activePart.getElementId().toString().length() > 1){
		
			
	
			}

	} 
} 