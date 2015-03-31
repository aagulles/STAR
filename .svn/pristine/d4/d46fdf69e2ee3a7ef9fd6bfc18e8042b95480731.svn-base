package org.irri.breedingtool.pbtools.analysis.environment.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.irri.breedingtool.manager.impl.DataManipulationManager;

public class SpecifyRowColDialog extends Dialog {
	private String[] levelItems;
	private String row;
	private String column;
	private List colVar;
	private List rowVar;
	private Button addRowBtn;
	private DataManipulationManager dataManipulationManager= new DataManipulationManager();
	private Button addColBtn;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SpecifyRowColDialog(Shell parentShell, String[] levelItems) {
		super(parentShell);
		this.levelItems = levelItems;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		getShell().setText("Specify Row/Column Variables");
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblVariables = new Label(composite, SWT.NONE);
		lblVariables.setText("Variables:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		final List varList = new List(composite, SWT.BORDER);
		GridData gd_varList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_varList.widthHint = 103;
		varList.setItems(levelItems);
		varList.setLayoutData(gd_varList);
		varList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(varList.getSelectionCount()>0){
					rowVar.setSelection(-1);
					colVar.setSelection(-1);
					addRowBtn.setText("Add");
					addColBtn.setText("Add");
					if(rowVar.getItemCount()<1)addRowBtn.setEnabled(true);
					else addRowBtn.setEnabled(false);
					if(colVar.getItemCount()<1)addColBtn.setEnabled(true);
					else addColBtn.setEnabled(false);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		addRowBtn = new Button(composite, SWT.NONE);
		GridData gd_addRowBtn = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_addRowBtn.widthHint = 55;
		addRowBtn.setLayoutData(gd_addRowBtn);
		addRowBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(varList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = varList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						rowVar.add(selectedNumVars[i]);
					}
					varList.remove(varList.getSelectionIndices());
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = rowVar.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						varList.add(selectedNumVars[i]);
					}
					rowVar.remove(rowVar.getSelectionIndices());
				}
				addRowBtn.setEnabled(false);
			}
		});
		addRowBtn.setText("Add");
		
		Label lblRowVariable = new Label(composite, SWT.NONE);
		lblRowVariable.setText("Field Row Variable:");
		
		rowVar = new List(composite, SWT.BORDER);
		GridData gd_rowVar = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_rowVar.heightHint = 26;
		rowVar.setLayoutData(gd_rowVar);
		rowVar.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				dataManipulationManager.moveSelectedStringFromTo( rowVar, varList);
				addRowBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(rowVar.getSelectionIndex()>-1){
					addRowBtn.setEnabled(true);
					varList.setSelection(-1);
					String[] s=rowVar.getSelection();
					addRowBtn.setText("Remove");
					addRowBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		addColBtn = new Button(composite, SWT.NONE);
		addColBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addColBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(varList.getSelectionCount()>0) {//if the purpose is to add
					String selectedNumVars[] = varList.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						colVar.add(selectedNumVars[i]);
					}
					varList.remove(varList.getSelectionIndices());
				}
				else{//if the purpose is to remove
					String selectedNumVars[] = colVar.getSelection();
					for(int i=0; i< selectedNumVars.length; i++){
						varList.add(selectedNumVars[i]);
					}
					colVar.remove(colVar.getSelectionIndices());
				}
				addColBtn.setEnabled(false);
			}
		});
		addColBtn.setText("Add");
		
		Label lblColumnVariable = new Label(composite, SWT.NONE);
		lblColumnVariable.setText("Field Column Variable:");
		
		colVar = new List(composite, SWT.BORDER);
		GridData gd_colVar = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_colVar.heightHint = 26;
		colVar.setLayoutData(gd_colVar);
		colVar.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				dataManipulationManager.moveSelectedStringFromTo( colVar, varList);
				addColBtn.setEnabled(false);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(colVar.getSelectionIndex()>-1){
					addColBtn.setEnabled(true);
					varList.setSelection(-1);
					String[] s=colVar.getSelection();
					addColBtn.setText("Remove");
					addColBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		return container;
	}
	
	
	public String getHeatRow(){
		return row;
	}
	
	public String getHeatColumn(){
		return column;
	}
	@Override
	protected void okPressed(){
		if(rowVar.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add a Row variable.");
		else if(colVar.getItemCount()<1) MessageDialog.openError(getShell(), "Error", "Please add a Column variable.");
		else{
			row = rowVar.getItem(0);
			column = colVar.getItem(0);
			close();
		}
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
		return new Point(495, 314);
	}

}
