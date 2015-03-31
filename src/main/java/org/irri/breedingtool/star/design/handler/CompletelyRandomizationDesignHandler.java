package org.irri.breedingtool.star.design.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;
import org.irri.breedingtool.star.design.dialog.CompletelyRandomizedDesignDialog;



public class CompletelyRandomizationDesignHandler {
	ProjectTreeComponent	projectTreeComponent ;
	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public void launchDesignDialog(final Shell parent) {
			
		if(RJavaManagerInitializer.isProjectActive == false){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The Project Explorer should contain a project.");
			return;
		}
		
		CompletelyRandomizedDesignDialog factorialDesignDialog = new CompletelyRandomizedDesignDialog(
				parent);
		factorialDesignDialog.open();
		
	}
}
