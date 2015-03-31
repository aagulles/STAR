package org.irri.breedingtool.graphs.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.wb.swt.ResourceManager;

public class ChoosePlotSymbolsDialog extends Dialog {

	private String chosenSymbol="";
	private Shell parentShell;
	private Button[] symbolButtons = new Button[19];
	private int defaultSelected = 0;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ChoosePlotSymbolsDialog(Shell parentShell) {
		super(parentShell);
		this.parentShell = parentShell;
	}
	public ChoosePlotSymbolsDialog(Shell parentShell, int defaultSelected) {
		super(parentShell);
		this.parentShell = parentShell;
		this.defaultSelected = defaultSelected;
	}
	
	public String getChosenSymbol(){
		return chosenSymbol;
	}
	public void setDefaultSelected(int sel){
		defaultSelected = sel;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Specify plot symbol to be used.");
		Composite container = (Composite) super.createDialogArea(parent);
		
		Group grpDelimiterUsedIn = new Group(container, SWT.NONE);
		grpDelimiterUsedIn.setText("Plot Symbols:");
		grpDelimiterUsedIn.setLayout(new GridLayout(9, false));
		GridData gd_grpDelimiterUsedIn = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpDelimiterUsedIn.heightHint = 139;
		gd_grpDelimiterUsedIn.widthHint = 235;
		grpDelimiterUsedIn.setLayoutData(gd_grpDelimiterUsedIn);
		
		Button button = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol0.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[0] = button;
		
		Button button_0 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_0.setSelection(true);
		button_0.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol1.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[1] = button_0;
		
		Button button_1 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_1.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol2.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[2] = button_1;
		
		Button button_2 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_2.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol3.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[3] = button_2;
		
		Button button_3 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_3.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol4.png"));
		symbolButtons[4] = button_3;
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		
		Button button_4 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_4.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol5.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[5] = button_4;
		
		Button button_5 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_5.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol6.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[6] = button_5;
		
		Button button_6 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_6.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol7.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[7] = button_6;
		
		Button button_7 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_7.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol8.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[8] = button_7;
		
		Button button_8 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_8.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol9.png"));
		symbolButtons[9] = button_8;
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		
		Button button_9 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_9.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol10.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[10] = button_9;
		
		Button button_10 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_10.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol11.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[11] = button_10;
		
		Button button_11 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_11.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol12.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[12] = button_11;
		
		Button button_12 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_12.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol13.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[13] = button_12;
		
		Button button_13 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_13.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol14.png"));
		symbolButtons[14] = button_13;
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		
		Button button_14 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_14.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol15.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[15] = button_14;
		
		Button button_15 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_15.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol16.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[16] = button_15;
		
		Button button_16 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_16.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol17.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		symbolButtons[17] = button_16;
		
		Button button_17 = new Button(grpDelimiterUsedIn, SWT.RADIO);
		button_17.setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol18.png"));
		symbolButtons[18] = button_17;
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		setSelected(defaultSelected);
		return container;
	}
	private void setSelected(int selected){
		for(int i = 0; i < symbolButtons.length; i++){
			if(selected == i) symbolButtons[i].setSelection(true);
			else symbolButtons[i].setSelection(false);
		}
	}
	@Override
	protected void okPressed(){
		//CALL THE RJAVA FUNCTION HERE
		for(int i=0; i<20;i++){
			if(symbolButtons[i].getSelection()){
				chosenSymbol = Integer.toString(i);
				break;
			}
		}
		close();
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
		return new Point(455, 321);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}
