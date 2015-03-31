package org.irri.breedingtool.datamanipulation.dialog;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class OperationProgressDialog extends ProgressMonitorDialog {

	private String title;
	//	private  bar;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public OperationProgressDialog(Shell parentShell, String title) {
		super(parentShell);
		this.title =title;
//		setOpenOnRun(true);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(title);
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		final ProgressBar bar = new ProgressBar (getShell(), SWT.SMOOTH | SWT.INDETERMINATE);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		getShell().getDisplay().timerExec(50, new Runnable() {
			int i = 0;
			public void run() {
				if (bar.isDisposed()) return;
				bar.setSelection(i++);
				if (i < bar.getMaximum()) getShell().getDisplay().timerExec(50, this);
				else if (i == bar.getMaximum()){
					i=0;
					bar.setSelection(i++);
					getShell().getDisplay().timerExec(50, this);
				}
			}
		});

			//		showProgressBar();
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
//				createButton(parent, IDialogConstants.OK_ID, "Run in Background...",
//						true);
		//		createButton(parent, IDialogConstants.CANCEL_ID,
		//				IDialogConstants.CANCEL_LABEL, false);
	}


	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(350, 175);
	}

}
