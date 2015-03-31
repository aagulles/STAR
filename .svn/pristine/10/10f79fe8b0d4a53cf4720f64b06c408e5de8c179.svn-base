package org.irri.breedingtool.application.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.data.view.FileTableViewer;
import org.irri.breedingtool.datamanipulation.data.view.OutputFolderViewer;
import org.irri.breedingtool.datamanipulation.data.view.PdfViewer;
import org.irri.breedingtool.datamanipulation.data.view.TxtFileViewer;
import org.irri.breedingtool.datamanipulation.dialog.ChooseDelimiterDialog;
import org.irri.breedingtool.datamanipulation.dialog.SaveablePartPromptDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.MPartContributionURI;



public class PartStackHandler {

	@Inject
	private static EPartService service;

	@Inject
	private static MApplication application;
	@Inject
	private static EModelService modelService;
	private static int triggerRetries = 0;
	public static String PackageName = "Star";
	@SuppressWarnings("restriction")
	public static void reOpen(MPart part) {
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		stack.getChildren().add(part);
	}
	public static void setStackNoCollapse(){

		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		//PartStackHandler.hideRetainer();

		stack.getTags().add(IPresentationEngine.NO_AUTO_COLLAPSE);
	}
	@SuppressWarnings("restriction")
	@Execute
	public static void execute(ProjectExplorerTreeNodeModel treeNodeModel) {

		if((MPart) modelService.find("retainer", application) == null){
//			MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Oh no!", "Retainer is gone! :(");
		}

		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		//PartStackHandler.hideRetainer();

		stack.getTags().add(IPresentationEngine.NO_AUTO_COLLAPSE);

		if(modelService.find(treeNodeModel.getProjectFile().getAbsolutePath(), application) != null){

			service.showPart((MPart) modelService.find(treeNodeModel.getProjectFile().getAbsolutePath(),application), PartState.ACTIVATE);
			service.activate((MPart) modelService.find(treeNodeModel.getProjectFile().getAbsolutePath(),application));
			service.showPart((MPart) modelService.find(treeNodeModel.getProjectFile().getAbsolutePath(),application), PartState.ACTIVATE);
			System.out.println("Show");
			return;
		}
		else{
			System.out.println("Continue");
		}

		ProjectExplorerManager projectMan = new ProjectExplorerManager();

		boolean toOpen = true;

		//Expand the folder 
		treeNodeModel.getParentTreeItem().setExpanded(true);
		if(treeNodeModel.getParentTreeItem().getParentItem() != null)treeNodeModel.getParentTreeItem().getParentItem().setExpanded(true);
		if(treeNodeModel.getProjectFile().getAbsolutePath().contains(((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getPath()))
			projectMan.getOutputFolder(ProjectExplorerView.projectTree).setExpanded(true);

		//Search if stack is already opened

		final MPart part = MBasicFactory.INSTANCE.createPart();
		DataManipulationManager dataManipulationManager = new DataManipulationManager();

		part.setLabel(dataManipulationManager.getDisplayFileName(treeNodeModel.getProjectFile().getAbsolutePath()));

		part.setElementId(treeNodeModel.getProjectFile().getPath());

		String delimiter;		
		if(treeNodeModel.getProjectFile().getName().endsWith(".txt")){  
			part.setAccessibilityPhrase("Table");
			part.setIconURI("platform:/plugin/"+PackageName+"/icons/page_white_text.png");
			//			System.out.println("opening text");
			
			
			//NOTE: Needs to be clean. Sometimes the path uses \\ instead of single \ only. 
			//		Making the condition to return false
			String projectPath = new File(projectMan.getProjectPath()).getAbsolutePath();
			
			if(treeNodeModel.getProjectFile().getPath().toString().contains(projectPath + File.separator +  "Output")){
				part.setAccessibilityPhrase("TxtFile");

				TxtFileViewer.openTxtFile(treeNodeModel);
				part.setContributionURI("bundleclass://"+PackageName+"/org.irri.breedingtool.datamanipulation.data.view.TxtFileViewer"); 



			}
			else{
				System.out.println(projectMan.getProjectPath() + File.separator + "Output" + " " + treeNodeModel.getProjectFile().getPath().toString());
				ChooseDelimiterDialog chooseDelimiter = new ChooseDelimiterDialog(Display.getCurrent().getActiveShell(), false, dataManipulationManager.getDisplayFileName(treeNodeModel.getProjectFile().getAbsolutePath()));
				chooseDelimiter.open();
				delimiter = chooseDelimiter.getDelimiter();

				if(chooseDelimiter.getReturnCode()==0){//view as table
					if(!delimiter.equals("")){
						part.setContributionURI(MPartContributionURI.FILE_TABLE_VIEWER_URI);
						FileTableViewer.openFile(treeNodeModel, "txt", delimiter);
					}
					else{
						part.setContributionURI(MPartContributionURI.PLAIN_TXT_VIEWER_URI);
						TxtFileViewer.openTxtFile(treeNodeModel);
					}


				}
				else{
					toOpen = false;
				}
			}


		}
		else if(treeNodeModel.getProjectFile().getName().endsWith(".png") || treeNodeModel.getProjectFile().getName().endsWith(".jpg")) {  
			part.setIconURI("platform:/plugin/"+PackageName+"/icons/picture.png");
			//	ImageFileViewer.createImage(fileModel.getProjectFile());
			part.setContributionURI(MPartContributionURI.IMAGE_FILE_VIEWER_URI); 
			part.getProperties().put("filePath", treeNodeModel.getProjectFile().getPath());
			part.setAccessibilityPhrase("Image");




		}
		else if(treeNodeModel.getProjectFile().getName().endsWith(".pdf") ) {  
			part.setIconURI("platform:/plugin/"+PackageName+"/icons/pdf_icon.png");
			//	ImageFileViewer.createImage(fileModel.getProjectFile());
			//			System.out.println( " <");
			PdfViewer.openPdfFile(treeNodeModel);
			part.getProperties().put("filePath", treeNodeModel.getProjectFile().getPath());

			part.setContributionURI(MPartContributionURI.PDF_VIEWER_URI); 
			part.setAccessibilityPhrase("PDF");




		}
		else if(treeNodeModel.getProjectFile().getName().endsWith(".csv")){  
			part.setAccessibilityPhrase("Table");

			part.setIconURI(MPartContributionURI.CSV_TABLE_VIEWER_ICON);
			System.out.println("opening csv");
			FileTableViewer.openFile(treeNodeModel, "csv", ",");
			//				System.out.println("System stack: " + treeNodeModel.getProjectFile().getName());

			part.setContributionURI(MPartContributionURI.FILE_TABLE_VIEWER_URI); 
			try{
				
				
			}catch(NullPointerException nse){
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Empty File", "Can't open an empty file:\n"+ treeNodeModel.getProjectFile().getAbsolutePath());
				return;
			}
		}
		else if(treeNodeModel.getProjectFile().getName().endsWith(".rda")){  
			if(MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Convert RDA to CSV", "You have opened an R Data File.\n Do you want to convert it to CSV?")){

				String dataFilePath=treeNodeModel.getProjectFile().getAbsolutePath();
				FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				fd.setText("Save as CSV File");
				fd.setFilterPath(dataFilePath);

				String[] filterExt = { "*.csv"};
				fd.setFilterExtensions(filterExt);
				String newFileName = fd.open();
				try{
					part.setLabel(fd.getFileName());
					part.setElementId(newFileName);

					String fileName=dataFilePath.replace(File.separator, "/");
					newFileName=newFileName.replace(File.separator, "/");

					ProjectExplorerView.rJavaManager.getRJavaDataManipulationManager().convertRdaToCsv(fileName, newFileName);

					part.setAccessibilityPhrase("Table");
					part.setIconURI(MPartContributionURI.CSV_TABLE_VIEWER_ICON);

					ProjectExplorerTreeNodeModel convertedFile = new ProjectExplorerTreeNodeModel(new File(newFileName), treeNodeModel.getTreeItem());
					FileTableViewer.openFile(convertedFile, "csv", ",");

					part.setContributionURI(MPartContributionURI.FILE_TABLE_VIEWER_URI);



					projectMan.refreshFolder((ProjectExplorerTreeNodeModel) projectMan.getDataFolder(ProjectExplorerView.projectTree).getData());
				}catch(NullPointerException npe){
					//					System.out.println("user cancelled csv conversion");
				}
			}
			else{//if the user doesn't want to convert
				MessageDialog.openError(Display.getCurrent().getActiveShell(), "Invalid File Type", "Sorry, can't open file "+ treeNodeModel.getProjectFile().getName());
			}
		}
		else if(treeNodeModel.getProjectFile().getPath().contains(((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getPath())){

			if(treeNodeModel.getProjectFile().getAbsolutePath().equals(((ProjectExplorerTreeNodeModel) projectMan.getOutputFolder(ProjectExplorerView.projectTree).getData()).getProjectFile().getAbsolutePath())) return;
			part.setAccessibilityPhrase("OutputFolder");

			part.setIconURI(MPartContributionURI.CSV_TABLE_VIEWER_ICON);
			//			System.out.println("opening csv");
			part.getProperties().put("folderPath", treeNodeModel.getProjectFile().getPath());

			//			System.out.println("System stack: " + treeNodeModel.getProjectFile().getName());
			OutputFolderViewer.setFileModel(treeNodeModel);
			part.setContributionURI(MPartContributionURI.OUTPUT_FOLDER_VIEWER_URI); 



		}
		else {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Unknown File Type", "Sorry, can't open file "+ treeNodeModel.getProjectFile().getName());
			return;
		}
		if(toOpen){
			part.setCloseable(true);
			stack.getChildren().add(part);
			treeNodeModel.setElementInStack(part);
			part.setObject(treeNodeModel);
			treeNodeModel.setElementInStack(part);

			try {
				try {
					service.showPart(part, PartState.ACTIVATE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					stack.setSelectedElement(part);
				}
			}catch(SWTException e){
				
			}catch (NullPointerException e) {
				// TODO Auto-generated catch block
				stack.setSelectedElement(part);
			}

			part.getTags().add(EPartService.REMOVE_ON_HIDE_TAG );
			if(part.getLabel().endsWith(".csv")){
				if(FileTableViewer.invalidDataDetected){
					treeNodeModel.getElementInStack().getParent().getChildren().remove(treeNodeModel.getElementInStack());
				}
			}
			//System.out.println(part.getElementId()+ "XS");
		}
	}
	@SuppressWarnings("restriction")
	public static void initISaveHandler(){
		final MWindow window = (MWindow) modelService.find("MainWindow", application);
		window.getContext().set(ISaveHandler.class, new ISaveHandler() {
			@SuppressWarnings("unused")
			public Save promptToSave(MPart dirtyPart) {
				Shell shell = (Shell) window.getContext()
						.get(IServiceConstants.ACTIVE_SHELL);
				Object[] elements = null;
				int style = SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL;


				MessageBox messageBox = new MessageBox(shell, style);
				messageBox.setMessage(dirtyPart.getLabel() + " has been modified. Save Changes?");
				switch (messageBox.open()) {

				case SWT.CANCEL:
					return Save.CANCEL;
				case SWT.YES:
					return Save.YES;

				case SWT.NO:
					return Save.NO;

				}
				return Save.CANCEL;
			}

			public Save[] promptToSave(Collection<MPart> dirtyParts) {
				List<MPart> parts = new ArrayList<MPart>(dirtyParts);
				Shell shell = (Shell) window.getContext()
						.get(IServiceConstants.ACTIVE_SHELL);
				Save[] response = new Save[dirtyParts.size()];
				Object[] elements = promptForSave(shell, parts);
				if (elements == null) {
					Arrays.fill(response, Save.CANCEL);
				} else {
					Arrays.fill(response, Save.NO);
					for (int i = 0; i < elements.length; i++) {
						response[parts.indexOf(elements[i])] = Save.YES;
					}
				}
				return response;
			}
		});
	}
	public static void retainPart(MPart part){

		service.activate((MPart) part);
		service.showPart((MPart)part, PartState.ACTIVATE);
		((MPart) part).setVisible(true); 
	}
	public static void reCreatePartSashHandler(){
		MPerspective mainPerspective = (MPerspective) modelService.find("MainPerspective", application);
		//Remove the remaining
		mainPerspective.getChildren().remove(0);
		MPartSashContainer mainPartSashContainer = MBasicFactory.INSTANCE.createPartSashContainer();
		mainPartSashContainer.setElementId("MainPartSashContainer");
		mainPerspective.getChildren().add(mainPartSashContainer);

		MPartStack projectExplorerStack = MBasicFactory.INSTANCE.createPartStack();
		MPartStack viewerStack = MBasicFactory.INSTANCE.createPartStack();
		projectExplorerStack.setElementId("ProjectExplorerStack");
		projectExplorerStack.setContainerData("2");
		MPart projExpPart = MBasicFactory.INSTANCE.createPart();
		projExpPart.setCloseable(false);
		projExpPart.setLabel("Project Explorer");

	}



	public static void setPartModified(String partID){
		MPart part = (MPart) modelService.find(partID, application);
		part.setDirty(true);
		
	}
	public static void setPartSaved(String partID){
		MPart part = (MPart) modelService.find(partID, application);
		part.setDirty(false);
		
	}




	public static List<MStackElement> getStackChildrens(){
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application); 
		return stack.getChildren();
	}
	public static ProjectExplorerTreeNodeModel getPartTreeModel(String partID){
		if (!isOpen(partID)) return null;
		MPart part = (MPart) modelService.find(partID, application);
		return (ProjectExplorerTreeNodeModel) part.getObject();
	}
	public static void removeAll(){
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		//		MPart retainer = MBasicFactory.INSTANCE.createPart();
		//		retainer.setVisible(false);
		//		retainer.setElementId("retainer");
		if(stack == null) return;
		//		if((MPart) modelService.find("retainer", application) == null)
		//		stack.getChildren().add(retainer);
		List stackChildren =	stack.getChildren();
		int listCount = stackChildren.size();
		//		System.out.println("Stack Childrens count: " + stackChildren.size());
		if(listCount <= 0) return;
		for(int i = listCount - 1; i >= 0; i--){
			MPart part = (MPart)stack.getChildren().get(i);
			if(!part.getElementId().equals("retainer"))
				stackChildren.remove(i);
		}


	}
	public static void addRetainer(){
		// FOR BUG FIXING

		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		if(modelService.find("retainer", application) != null) return;
		MPart retainer = MBasicFactory.INSTANCE.createPart();
		retainer.setVisible(false);
		retainer.setElementId("retainer");


		System.out.println("Retainer Added");

		stack.getChildren().add(retainer);
		service.hidePart(retainer);
	}
	public static void showRetainer(){
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		if(modelService.find("retainer", application) == null) {
			addRetainer();
			showRetainer();
			return;
		}
		service.showPart((MPart) modelService.find("retainer", application), PartState.ACTIVATE);


	}

	public static void hideRetainer(){
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		if(modelService.find("retainer", application) == null) {
			addRetainer();
			return;
		}

		service.hidePart((MPart) modelService.find("retainer", application));
		System.out.println("retainer hide");

	}

	public static boolean isOpen(String elementID){
		if(modelService == null) return false;
		MPart modelActivePart = (MPart) modelService.find(elementID, application);
		MPartStack modelPartStack = (MPartStack) modelService.find("ViewerStack", application);


		if(modelActivePart == null || !modelActivePart.isVisible()) 
		{
			return false;
		}
		return true;
	}


	public static void reOpenPart(ProjectExplorerTreeNodeModel nodeModel) {
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		MPart part =  (MPart) modelService.find(nodeModel.getProjectFile().getAbsolutePath(), application);
		stack.getChildren().remove(part);
		
		PartStackHandler.execute(nodeModel);


	}
	public static void closePart(ProjectExplorerTreeNodeModel nodeModel){
		//		System.out.println(nodeModel.getElementInStack().getParent().getElementId().toString());

		nodeModel.getElementInStack().getParent().getChildren().remove(nodeModel.getElementInStack());

	}

	public static MStackElement getActivePart(){
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		return stack.getSelectedElement();
	}

	public static String getActiveElementID(){
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		String ID;
		try {
			ID = stack.getSelectedElement().getElementId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return ID;
	}
	
	private static Object[] promptForSave(Shell parentShell,
			Collection<MPart> saveableParts) {
		SaveablePartPromptDialog dialog = new SaveablePartPromptDialog(
				parentShell, saveableParts);
		if (dialog.open() == Window.CANCEL) {
			return null;
		}

		return dialog.getCheckedElements();
	}
	public static void disableMenuItems(){
		MMenu stack = (MMenu) modelService.find("mnuAnalysis", application);
		stack.setEnabled(false);
		stack.setVisible(false);
	}
	public static void launchHelp(){
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);

		//Expand the folder
		//Search if stack is already opened
		MPart part = MBasicFactory.INSTANCE.createPart();
		part.setIconURI("platform:/plugin/"+PackageName+"/icons/pdf_icon.png");
		//	ImageFileViewer.createImage(fileModel.getProjectFile());
		//		System.out.println( " <");
		PdfViewer.openPdfFile( System.getProperty ("user.dir") + File.separator + "User's Manual.pdf");
		part.setLabel("User's Manual");
		part.setContributionURI(MPartContributionURI.PDF_VIEWER_URI);
		part.setAccessibilityPhrase("PDF");

		part.setCloseable(true);
		stack.getChildren().add(part);
		stack.setSelectedElement(part);
	}

} 