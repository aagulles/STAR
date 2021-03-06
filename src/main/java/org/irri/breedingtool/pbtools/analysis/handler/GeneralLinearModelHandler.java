package org.irri.breedingtool.pbtools.analysis.handler;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.commands.IHandler;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.pbtools.analysis.dialog.GeneralLinearModelDialog;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.OneStageMultiEnvironmentDialog;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.SingleEnvironmentDialog;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.TwoStageMultiEnvironmentDialog;
import org.irri.breedingtool.pbtools.analysis.matingdesign.dialog.GriffingMethodDialog;
import org.irri.breedingtool.pbtools.analysis.matingdesign.dialog.NorthCarolinaExperimentsDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
public class GeneralLinearModelHandler {
	ProjectTreeComponent	projectTreeComponent;
	GeneralLinearModelDialog analysisDialog;
	@Inject
	private static EModelService modelService;
	@Inject
	private static MApplication application;
	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public void execute(final Shell parent, EModelService service,  MWindow window) {
		DataManipulationManager dataManipulationManager = new DataManipulationManager();
		try{//check if there's an active File first
		MPartStack stack = (MPartStack) modelService.find("ViewerStack", application);
		MUIElement selected = stack.getSelectedElement();
		String absoluteFilePath = selected.getElementId();
		dataManipulationManager.saveFileChanges(new File(absoluteFilePath), DataManipulationManager.getActiveTable(absoluteFilePath),  DataManipulationManager.getActiveDataDelimiter(absoluteFilePath));
			File activeFile = new File(stack.getSelectedElement().getElementId());
			
			try{
				if(WindowDialogControlUtil.doesWindowDialogForFileExist("GeneralLinearModel", activeFile.getAbsolutePath())){//if the Dialog was just Hidden
					WindowDialogControlUtil.showWindowDialog("GeneralLinearModel",activeFile.getAbsolutePath());
				}
				else{//If the Dialog has been closed, create another
					analysisDialog = new GeneralLinearModelDialog(parent, "GeneralLinearModel", activeFile);
					analysisDialog.open();
				}
			}
			catch(NullPointerException npe){//There's no created Dialog Yet
				analysisDialog = new GeneralLinearModelDialog(parent, "GeneralLinearModel", activeFile);
				analysisDialog.open();
			}
		}
		catch(NullPointerException e){//prompt the user that there's no active File
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
		}
	}
}