package org.irri.breedingtool.application.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class AboutUsStarDialog extends Dialog {
	private Text txtStatisticalToolFor;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AboutUsStarDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.RESIZE | SWT.SYSTEM_MODAL);
	
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));
		
		Label lblIrri = new Label(container, SWT.NONE);
		lblIrri.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblIrri.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblIrri.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblIrri.setText("IRRI");
		
		Label lblInternationalRiceResearch = new Label(container, SWT.NONE);
		GridData gd_lblInternationalRiceResearch = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblInternationalRiceResearch.heightHint = 27;
		lblInternationalRiceResearch.setLayoutData(gd_lblInternationalRiceResearch);
		lblInternationalRiceResearch.setText("International Rice Research Institute");
		
		txtStatisticalToolFor = new Text(container, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_txtStatisticalToolFor = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtStatisticalToolFor.heightHint = 184;
		txtStatisticalToolFor.setLayoutData(gd_txtStatisticalToolFor);
		txtStatisticalToolFor.setText("Statistical Tool for Agricultural Research (STAR)\r\n\r\nVersion: 2.0.2\r\n\r\n(c) Copyright International Rice Research Institute (IRRI) 2013 - 2020 All rights reserved.\r\n\r\nFor updates, please visit http://bbi.irri.org\r\n\r\nFor any concerns, contact us through email at star.bbi@irri.org. \r\n");
		txtStatisticalToolFor.setBackground(SWTResourceManager.getColor(255, 255, 255));

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
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("About STAR");
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(605, 393);
	}
}
