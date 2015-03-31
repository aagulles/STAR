package org.irri.breedingtool.datamanipulation.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DefineLevelsDialog extends Dialog {
	private Table table;
	private String indexFactorName;
	private String[] reshapeVariableNames;
	private String targetVariable;
	private String[] variableLevels;
	private DataManipulationManager dataManipulationManager;
	private Button btnUseDefaultLevels;

	/**
	 * Create the dialog.
	 * @param parentShell
	 * @param targetVariables 
	 */
	public DefineLevelsDialog(Shell parentShell, String indexFactorName, String targetVariable, String[] reshapeVariableNames) {
		super(parentShell);
		this.targetVariable = targetVariable;
		this.indexFactorName = indexFactorName;
		this.reshapeVariableNames= reshapeVariableNames;
		dataManipulationManager = new DataManipulationManager();
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Define Levels");
		Composite container = (Composite) super.createDialogArea(parent);

		Label lblDefineLevelsOf = new Label(container, SWT.NONE);
		lblDefineLevelsOf.setText("Define Levels Of "+ indexFactorName+":");

		btnUseDefaultLevels = new Button(container, SWT.CHECK);

		btnUseDefaultLevels.setSelection(true);
		btnUseDefaultLevels.setText("Use Default Levels");

		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn targetVarNameCol = new TableColumn(table, SWT.NONE);
		targetVarNameCol.setWidth(104);
		targetVarNameCol.setText("Default Levels");

		TableColumn levelsCol = new TableColumn(table, SWT.NONE);
		levelsCol.setWidth(155);
		levelsCol.setText("New Levels");
		
		for(String s: reshapeVariableNames){
			final TableItem tableItem= new TableItem(table, SWT.CENTER);
			tableItem.setText(0, s);

			TableEditor editor = new TableEditor(table);
			final Text varLevel = new Text(table, SWT.NONE);
			if(btnUseDefaultLevels.getSelection()){
				tableItem.setText(1, s);
				varLevel.setText(s);
			}
			Listener textListener = new Listener() {
				public void handleEvent(final Event e) {
					switch (e.type) {
					case SWT.FocusOut: 
						tableItem.setText(1, varLevel.getText());
						break;
					}
				}
			};

			tableItem.setData("editor", editor);
			varLevel.addListener(SWT.FocusOut, textListener);
			varLevel.addListener(SWT.Traverse, textListener);
			varLevel.addListener(SWT.KeyDown, textListener);
			editor.grabHorizontal = true;
			editor.setEditor(varLevel, tableItem, 1);
		}
		
		btnUseDefaultLevels.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int ctr=0;
				for(TableItem ti: table.getItems()){
					TableEditor editor = (TableEditor)ti.getData("editor");
					if(btnUseDefaultLevels.getSelection()){
						((Text) editor.getEditor()).setText(reshapeVariableNames[ctr]);					
					}else{
						((Text) editor.getEditor()).setText("");
					}
					ctr++;
				}
			}
		});
		
		
		return container;
	}

	@Override
	protected void okPressed() {
		variableLevels = getCurrentLevels();
		if(variableLevels.length==reshapeVariableNames.length) close();
	}

	public String[] getCurrentLevels(){
		String[] varLevels = new String[reshapeVariableNames.length];
		String[] empty = {"null"};
		int ctr=0;

		for(String s: reshapeVariableNames){
			TableItem tableItem= table.getItem(ctr);
			if(tableItem.getText(1).equals("")){//if there's no level indicated for this variable
				System.out.println(tableItem.getText(1));
				MessageDialog.openWarning(getShell(), "Invalid Input", "Please indicate a level for "+ s +".");
				return empty;
			}
			else if(checkLevelsNameIfUsed(tableItem.getText(1), varLevels, ctr)){
				MessageDialog.openWarning(getShell(), "Invalid Input", "The name of the levels should be unique.");
				return empty;
			}
			else if(!dataManipulationManager.variableNameInputValidation(tableItem.getText(1)).equals("true")){
				MessageDialog.openWarning(getShell(), "Invalid Input", "'"+tableItem.getText(1)+"' is an invalid level name.");
				return empty;
			}
			else{
				varLevels[ctr]=tableItem.getText(1);
			}
			ctr++;
		}
		return varLevels;
	}

	public String[] getLevels(){
		return variableLevels;
	}

	public boolean checkLevelsNameIfUsed(String value, String[] columnHeaders, int index) {
		// TODO Auto-generated method stub
		for(int i=0; i<columnHeaders.length; i++){
			if(value.equals(columnHeaders[i]) && index!=i)return true;
		}
		return false;
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
				"Back", false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(461, 291);
	}



}
