package org.irri.breedingtool.datamanipulation.dialog;

import java.util.Collection;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class SaveablePartPromptDialog extends Dialog {

	private Collection<MPart> collection;

	private CheckboxTableViewer tableViewer;

	private Object[] checkedElements = new Object[0];

	public SaveablePartPromptDialog(Shell shell, Collection<MPart> collection) {
		super(shell);
		this.collection = collection;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);

		Label lblSelectTheData = new Label(parent, SWT.LEAD);
		lblSelectTheData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		lblSelectTheData.setText("Select the data to save:"); //$NON-NLS-1$

		tableViewer = CheckboxTableViewer.newCheckList(parent, SWT.SINGLE
				| SWT.BORDER);
		Table table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 250;
		data.widthHint = 300;
		tableViewer.getControl().setLayoutData(data);
		tableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MPart) element).getLocalizedLabel();
			}
		});
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(collection);
		tableViewer.setAllChecked(true);

		return parent;
	}

	@Override
	protected void okPressed() {
		checkedElements = tableViewer.getCheckedElements();
		super.okPressed();
	}

	public Object[] getCheckedElements() {
		return checkedElements;
	}

}