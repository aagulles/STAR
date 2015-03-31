package org.irri.breedingtool.star.analysis.anova.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.handler.DialogHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.projectexplorer.view.ProjectTreeComponent;
import org.irri.breedingtool.projectexplorer.view.RJavaManagerInitializer;
import org.irri.breedingtool.star.analysis.anova.dialog.SplitSplitLatinSquareDesignDialog;
import org.irri.breedingtool.utility.GeneralUtility;

public class SplitSplitLatinSquareDesignHandler {
	ProjectTreeComponent projectTreeComponent;

	@Named(IServiceConstants.ACTIVE_SHELL)
	@Execute
	public void launchDesignDialog(final Shell parent, EModelService service,
			MWindow window) {
if(RJavaManagerInitializer.isProjectActive == false){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The Project Explorer should contain a project.");
			return;
		}	

		if (!GeneralUtility.isFileValidData(PartStackHandler
				.getActiveElementID())) {
			MessageDialog
					.openError(Display.getCurrent().getActiveShell(), "Error",
							"The active tab in the Editor panel should contain a data file.");
			return;
		}
		String dialogTitle = "Completely Randomization Design";
		String fileName = "data1.csv";

		SplitSplitLatinSquareDesignDialog analysisDialog = new SplitSplitLatinSquareDesignDialog(
				parent);
		if (DialogHandler.openDialog(PartStackHandler.getActiveElementID()
				+ this.getClass().getName(), analysisDialog))
			analysisDialog.open();

	}
}
