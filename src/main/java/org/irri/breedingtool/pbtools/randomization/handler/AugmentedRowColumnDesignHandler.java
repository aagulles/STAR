package org.irri.breedingtool.pbtools.randomization.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;
import org.irri.breedingtool.star.design.dialog.AugmentedLatinSquareDesignDialog;
import org.irri.breedingtool.pbtools.randomization.dialog.AugmentedRowColumnDesignDialog;




public class AugmentedRowColumnDesignHandler {
	ProjectTreeComponent projectTreeComponent;
	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public void launchDesignDialog(final Shell parent, EModelService service,  MWindow window) {
				if(RJavaManagerInitializer.isProjectActive == false){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The Project Explorer should contain a project.");
			return;
		}	
		String dialogTitle = "Completely Randomization Design";
		String fileName = "data1.csv";
	
		if(RJavaManagerInitializer.isProjectActive == false){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The Project Explorer should contain a project.");
			return;
		}
		
		AugmentedRowColumnDesignDialog designDialog = new AugmentedRowColumnDesignDialog(parent);
		designDialog.open();
		
	}
}
