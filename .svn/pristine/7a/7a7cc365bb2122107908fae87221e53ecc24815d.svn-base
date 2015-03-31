package org.irri.breedingtool.datamanipulation.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DeleteColumnsDialog extends Dialog {
	int selectionIndices[];
	private List list;
	private String inputText="";
	private String columnNames[];
	private Label lblAreYouSure;
	private Button checkBoxDeleteMulti;
	protected int ctr;
	private GridData gd_list;
	private Rectangle rect;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DeleteColumnsDialog(Shell parentShell, String selectedString, String[] columnNames) {
		super(parentShell);
		this.columnNames = columnNames;
		this.inputText = selectedString;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		lblAreYouSure = new Label(container, SWT.NONE);
		lblAreYouSure.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblAreYouSure.setText("Are you sure you want to delete column "+ inputText +"?");

		checkBoxDeleteMulti = new Button(container, SWT.CHECK);
		checkBoxDeleteMulti.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				lblAreYouSure.setText("Please select the columns you want to delete.");
				if(checkBoxDeleteMulti.getSelection()){
					rect = getShell().getBounds();
					gd_list.grabExcessVerticalSpace=true;
					list.setLayoutData(gd_list);
					getShell().setBounds(rect.x, rect.y, rect.width, rect.height+100);
					list.setVisible(true);
					list.setItems(columnNames);
					ctr=0;
					int checkedCol=0;
					for(String s: columnNames){
						if(s.equals(inputText)){
							checkedCol=ctr;
							break;
						}
						ctr++;
					}
					list.select(ctr);
					System.out.println(Integer.toString(ctr));
				}
				else{
					list.removeAll();
					list.setVisible(false);
					list.getBounds().height=0;
					lblAreYouSure.setText("Are you sure you want to delete column "+ inputText +"?");
					getShell().setBounds(rect);
				}
				parent.layout();
			}
		});
		checkBoxDeleteMulti.setText("I want to delete multiple columns.");
		if(columnNames.length==1)checkBoxDeleteMulti.setVisible(false);
		
		list = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL  | SWT.MULTI);
		gd_list = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_list.heightHint = 10;
		list.setLayoutData(gd_list);
		list.setVisible(false);
		list.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				list.select(ctr);
			}
		});
		return container;
	}
	
	public int[] getSelectedColumnIndices() {
		return selectionIndices;
	}
	
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Yes",
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				"No", false);
	}
	@Override
	protected void okPressed() {
		selectionIndices = list.getSelectionIndices();
		if(selectionIndices.length>0 && list.isVisible()){
			if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Delete Columns", "Are you sure you want to delete the selected column(s)?")){ //If user did not choose cancel from dialog Box
				setReturnCode(0);
				close();
			}
		}
		else {
			list.removeAll();
			selectionIndices = list.getSelectionIndices();
			setReturnCode(0);
			close();
		}
		
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(414, 204);
	}

}
