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

public class ChooseFillPatternDialog extends Dialog {

	private String chosenPattern="";
	private Button btnForwardDiagonal, btnHorizontal, btnBackwardDiagonal, btnVertical;
	private Shell parentShell;
	private Button btnPlainTxt;
	private boolean importCalled;
	private String varName;
	private Button btnSolid;
	private Label label;
	private Label label_1;
	private Label label_2;
	private Label label_3;
	private Label label_4;
	private Label label_5;
	private int angle;
	private int lineDensity;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ChooseFillPatternDialog(Shell parentShell, String varName) {
		super(parentShell);
		this.parentShell = parentShell;
		this.importCalled = importCalled;
		this.varName = varName;
	}
	
	public String getChosenPattern(){
		return chosenPattern;
	}
	
	public int getPatternAngle(){
		return angle;
	}
	
	public int getLineDensity(){
		return lineDensity;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Specify fill pattern to be used.");
		Composite container = (Composite) super.createDialogArea(parent);
		
		Group grpDelimiterUsedIn = new Group(container, SWT.NONE);
		grpDelimiterUsedIn.setText("Fill Paterns");
		grpDelimiterUsedIn.setLayout(new GridLayout(2, false));
		GridData gd_grpDelimiterUsedIn = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpDelimiterUsedIn.heightHint = 139;
		gd_grpDelimiterUsedIn.widthHint = 235;
		grpDelimiterUsedIn.setLayoutData(gd_grpDelimiterUsedIn);
		
		btnSolid = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnSolid.setSelection(true);
		btnSolid.setText("Solid");
		
		label = new Label(grpDelimiterUsedIn, SWT.NONE);
		label.setImage(ResourceManager.getPluginImage("Star", "icons/solid.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		btnVertical = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnVertical.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnVertical.setText("Vertical");
		
		label_1 = new Label(grpDelimiterUsedIn, SWT.NONE);
		label_1.setImage(ResourceManager.getPluginImage("Star", "icons/vertical.png"));
		
		new Label(grpDelimiterUsedIn, SWT.NONE);
		
		label_2 = new Label(grpDelimiterUsedIn, SWT.NONE);
		
		btnHorizontal = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnHorizontal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnHorizontal.setText("Horizontal");
		
		label_5 = new Label(grpDelimiterUsedIn, SWT.NONE);
		label_5.setImage(ResourceManager.getPluginImage("Star", "icons/horizontal.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		
		btnForwardDiagonal = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnForwardDiagonal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnForwardDiagonal.setText("Forward Diagonal");
		
		label_3 = new Label(grpDelimiterUsedIn, SWT.NONE);
		label_3.setImage(ResourceManager.getPluginImage("Star", "icons/forward_diagonal.png"));
		new Label(grpDelimiterUsedIn, SWT.NONE);
		new Label(grpDelimiterUsedIn, SWT.NONE);
		
		btnBackwardDiagonal = new Button(grpDelimiterUsedIn, SWT.RADIO);
		btnBackwardDiagonal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnBackwardDiagonal.setText("Backward Diagonal");
		
		label_4 = new Label(grpDelimiterUsedIn, SWT.NONE);
		label_4.setImage(ResourceManager.getPluginImage("Star", "icons/backward_diagonal.png"));
		
		return container;
	}

	@Override
	protected void okPressed(){
		//CALL THE RJAVA FUNCTION HERE
		lineDensity = 10;
		if(btnForwardDiagonal.getSelection()){
			chosenPattern = "forward_diagonal";
			angle  = 135;
		}
		else if(btnHorizontal.getSelection()){
			angle = 0;
			chosenPattern = "horizontal";
		}
		else if(btnVertical.getSelection()){
			angle = 90;
			chosenPattern = "vertical";
		}
		else if(btnBackwardDiagonal.getSelection()){
			angle = 45;
			chosenPattern = "backward_diagonal";
		}
		else{
			angle=0;
			lineDensity = 100;
			chosenPattern = "solid";
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
		return new Point(458, 334);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}
