package org.irri.breedingtool.application.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.dialog.AboutUsPBToolsDialog;
import org.irri.breedingtool.application.dialog.AboutUsStarDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;



public class AboutUsHandler {
	ProjectTreeComponent	projectTreeComponent ;
	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public void launchDesignDialog(final Shell parent, EModelService service,  MWindow window) {
				
	if(RJavaManagerInitializer.isStarInitialized){
		AboutUsStarDialog dlgAboutUs = new AboutUsStarDialog(parent);
		dlgAboutUs.open();
	}
	else{
		AboutUsPBToolsDialog dlgAboutUs = new AboutUsPBToolsDialog(parent);
		dlgAboutUs.open();
	}
	}
}
