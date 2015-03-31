package org.irri.breedingtool.graphs.managers;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class GraphTableSample extends Dialog {
	private Table table;
	private GraphTableManager tableManager;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public GraphTableSample(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(3, false));
		
		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_table.widthHint = 432;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		
		table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    for (int i = 0; i < 6; i++) {
	      new TableColumn(table, SWT.NONE);
	    }
	    table.getColumn(0).setText("Task");
	    table.getColumn(1).setText("Progress");
	    table.getColumn(2).setText("Color");
	    table.getColumn(4).setText("Fill");
	    table.getColumn(5).setText("Combo");
	    ArrayList<Integer> header = new ArrayList<Integer>();
		
		header.add(GraphTableManager.ROW_STATIC_TEXT);
		header.add(GraphTableManager.ROW_TEXT);
		header.add(GraphTableManager.ROW_COLOR_PICKER);
		header.add(GraphTableManager.ROW_FILL_TYPE);
		header.add(GraphTableManager.ROW_SPINNER);
		header.add(GraphTableManager.ROW_COMBO);
		
		 tableManager = new GraphTableManager(table, header);
		 
		 Button btnNewButton = new Button(container, SWT.NONE);
		 btnNewButton.addSelectionListener(new SelectionAdapter() {
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {
		 		RowEntityModel newModel = new RowEntityModel(GraphTableManager.ROW_SPINNER, 5, 2,10);
		 		RowEntityModel comboModel = new RowEntityModel(GraphTableManager.ROW_COMBO, new String[]{"Hello","World","Combo Test!"});
		 		Object[] dataSample = new Object[]{"Test","Test", Display.getCurrent().getSystemColor(SWT.COLOR_BLUE),"solid",newModel,comboModel};
				tableManager.addItem(dataSample);
		 	}
		 });
		 btnNewButton.setText("Add Fields");
		 
		 Button btnNewButton_1 = new Button(container, SWT.NONE);
		 btnNewButton_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		 btnNewButton_1.setText("Flush Data");
		 
		 Button btnNewButton_2 = new Button(container, SWT.NONE);
		 btnNewButton_2.addSelectionListener(new SelectionAdapter() {
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {
		 		tableManager.deleteRow(tableManager.getDataToString().size() - 1);
		 	}
		 });
		 btnNewButton_2.setText("Remove Fields");
		Device device = Display.getCurrent ();
		Color red = new Color (device, 255, 0, 0);
		Device display = Display.getCurrent();
		RowEntityModel comboModel = new RowEntityModel(GraphTableManager.ROW_COMBO, new String[]{"Hello","World","Combo Test!"});
		Object[] dataSample = new Object[]{"Test","Test", display .getSystemColor(SWT.COLOR_CYAN),"solid",1,comboModel};
		tableManager.addItem(dataSample);
		dataSample = new Object[]{"Test","Test", display.getSystemColor(SWT.COLOR_BLUE),"solid",0,comboModel};
		tableManager.addItem(dataSample);
		dataSample = new Object[]{"Test","Test", display.getSystemColor(SWT.COLOR_DARK_MAGENTA),"solid",2,comboModel};
		tableManager.addItem(dataSample);
		dataSample = new Object[]{"Test","Test", display.getSystemColor(SWT.COLOR_GREEN),"solid",5,comboModel};
		tableManager.addItem(dataSample);
		 table.getColumn(0).pack();
		    table.getColumn(1).setWidth(128);
		    table.getColumn(2).setWidth(128);
		    table.getColumn(3).setWidth(128);
		    table.getColumn(4).setWidth(128);
		    table.getColumn(5).setWidth(128);
		    
		return container;
	}

	@Override
	protected void okPressed(){
		ArrayList<String[]> data = tableManager.getDataToString();
		
		for(int i = 0; i < data.size(); i++){
			System.out.println(arrToString(data.get(i)));
		}
	}
	public String arrToString(String[] a){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
		   result.append( a[i] );
		   //result.append( optional separator );
		}
		String mynewstring = result.toString();
		return mynewstring;
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
		return new Point(450, 300);
	}

}
