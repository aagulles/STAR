package org.irri.breedingtool.star.design.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;

public class IncompleteBlockRectangularLatticeDesignDialog extends Dialog {

	private static String filePath = PartStackHandler.getActivePart().getElementId().toString(); 
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public IncompleteBlockRectangularLatticeDesignDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Randomization");
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		Label lblFactorialDesign = new Label(container, SWT.NONE);
		lblFactorialDesign.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblFactorialDesign.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lblFactorialDesign.setText("Rectangular Lattice Design");
		
		Label lblLbldialogtitle = new Label(container, SWT.NONE);
		lblLbldialogtitle.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
		lblLbldialogtitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblLbldialogtitle.setText("Incomplete Block Design");
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Group grpDesignParameters = new Group(container, SWT.NONE);
		grpDesignParameters.setLayout(new GridLayout(1, false));
		GridData gd_grpDesignParameters = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpDesignParameters.heightHint = 100;
		grpDesignParameters.setLayoutData(gd_grpDesignParameters);
		grpDesignParameters.setText("Design Parameters");
		
		Composite composite_1 = new Composite(grpDesignParameters, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_composite_1.heightHint = 49;
		composite_1.setLayoutData(gd_composite_1);
		
		Label lblNumberOfReplicated = new Label(composite_1, SWT.NONE);
		lblNumberOfReplicated.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfReplicated.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNumberOfReplicated.setText("Number of Treatments");
		
		final Spinner txtTotalTreatments = new Spinner(composite_1, SWT.BORDER);
		txtTotalTreatments.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		txtTotalTreatments.setMinimum(2);
		
		Label lblNumberOfReplicates = new Label(composite_1, SWT.NONE);
		lblNumberOfReplicates.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfReplicates.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNumberOfReplicates.setText("Number of Replicates");
		
		final Spinner txtTotalReplicates = new Spinner(composite_1, SWT.BORDER);
		txtTotalReplicates.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		txtTotalReplicates.setMinimum(2);
		
		Label lblNumberOfTrials = new Label(composite_1, SWT.NONE);
		lblNumberOfTrials.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.NORMAL));
		lblNumberOfTrials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNumberOfTrials.setText("Number of Trials");
		
		final Spinner txtTotalTrials = new Spinner(composite_1, SWT.BORDER);
		txtTotalTrials.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		txtTotalTrials.setMinimum(1);
		
		Group grpFieldBookFilename = new Group(container, SWT.NONE);
		grpFieldBookFilename.setLayout(new GridLayout(1, false));
		GridData gd_grpFieldBookFilename = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpFieldBookFilename.heightHint = 35;
		grpFieldBookFilename.setLayoutData(gd_grpFieldBookFilename);
		grpFieldBookFilename.setText("Field Book Filename");
		
		Label lblFileName = new Label(grpFieldBookFilename, SWT.NONE);
		lblFileName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblFileName.setFont(SWTResourceManager.getFont("Tahoma", 11, SWT.NORMAL));

		lblFileName.setText(new File(new File(filePath).getParent()).getName() + "/" + new File(filePath).getName());
		
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(369, 448);
	}

}
