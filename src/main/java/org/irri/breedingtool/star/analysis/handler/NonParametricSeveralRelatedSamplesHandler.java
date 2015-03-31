package org.irri.breedingtool.star.analysis.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.handler.DialogHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;
import org.irri.breedingtool.star.analysis.dialog.NonParametricSeveralRelatedSamplesDialog;
import org.irri.breedingtool.utility.GeneralUtility;


public class NonParametricSeveralRelatedSamplesHandler {

	@Execute
	public void launchDialog(final Shell parent, EModelService service,  MWindow window) {
		if(RJavaManagerInitializer.isProjectActive == false){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The Project Explorer should contain a project.");
			return;
		}	
		if(!GeneralUtility.isFileValidData(PartStackHandler.getActiveElementID())){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The active tab in the Editor panel should contain a data file.");
			return;
		}
		
		NonParametricSeveralRelatedSamplesDialog analysisDialog = new NonParametricSeveralRelatedSamplesDialog(parent);
		if(DialogHandler.openDialog(PartStackHandler.getActiveElementID() + this.getClass().getName() , analysisDialog)) analysisDialog.open();
		
	}
}
