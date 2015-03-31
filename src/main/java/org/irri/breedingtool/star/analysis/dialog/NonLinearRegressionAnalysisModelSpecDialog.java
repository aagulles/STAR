package org.irri.breedingtool.star.analysis.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.star.analysis.dialog.NonLinearRegressionAnalysisParameters.ParameterModel;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;

public class NonLinearRegressionAnalysisModelSpecDialog extends Dialog {

	private Button btnOk;
	private String filePath = PartStackHandler.getActiveElementID();
	private DialogFormUtility listManager = new DialogFormUtility();
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private ArrayList<String> tableData = new ArrayList<String>();
	private Composite composite;
	private Label lblIndependentVariable;
	private Button btnParameter;
	private List lstParameter;
	private Label lblRighthandExpression;
	private Label lblOperators;
	private Composite composite_1;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btn0;
	private Button btnDot;
	private Button btnAddition;
	private Button btnMinus;
	private Button btnMultiply;
	private Button btnDivide;
	private Label lblFunction;
	private List cmboFunctions;
	private Button btnDel;
	private Button btnCaret;
	private Button btnIndepVar;
	private Button btnParameters;
	private Button btnFunctions;
	private String returnVal;
	private Text txtRightHand;
	public ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel> parameterList = new ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel>();
	
	private ArrayList<String> arrExpression = new ArrayList<String>();
	public String getReturnVal() {
		return returnVal;
	}
	public void setReturnVal(String returnVal) {
		this.returnVal = returnVal;
	}
	private String indepVar;
	private String[] paraNames;
	private String[] Functions = {"abs", "acos", "asin", "atan", "cos", "exp", "log", "log10", "sin", "sqrt", "tan"};
	private List lstIndependentVar;
	private Button btnOpen;
	private Button btnClose;
	private String regExpression;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NonLinearRegressionAnalysisModelSpecDialog(Shell parentShell, String indepVar,ArrayList<NonLinearRegressionAnalysisParameters.ParameterModel> parameterList , String regExpression) {
		
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE | SWT.SYSTEM_MODAL);
		this.indepVar = indepVar;
		this.parameterList = parameterList;
		this.regExpression = regExpression;
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Nonlinear Regression: Expression Building");
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		FillLayout fl_container = new FillLayout(SWT.HORIZONTAL);
		fl_container.marginHeight = 8;
		fl_container.marginWidth = 8;
		container.setLayout(fl_container);
		
		composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(5, false));
		
		lblRighthandExpression = new Label(composite, SWT.NONE);
		lblRighthandExpression.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblRighthandExpression.setText("Right-hand Expression:");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		txtRightHand = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		txtRightHand.setEditable(false);
		txtRightHand.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_txtRightHand = new GridData(SWT.FILL, SWT.CENTER, true, true, 5, 1);
		gd_txtRightHand.heightHint = 80;
		txtRightHand.setLayoutData(gd_txtRightHand);
		
		lblIndependentVariable = new Label(composite, SWT.NONE);
		lblIndependentVariable.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1));
		lblIndependentVariable.setText("Independent Variable:");
		
		btnIndepVar = new Button(composite, SWT.NONE);
		btnIndepVar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(lstIndependentVar.getSelectionCount()>0) {//if the purpose is to add
					arrExpression.add(lstIndependentVar.getItem(0));
					rebuildExpressionBox();
//					dataManipulationManager.moveSelectedStringFromListToText( lstIndependentVar, txtRightHand);
				}
			}
		});
		btnIndepVar.setImage(ResourceManager.getPluginImage("Star", "icons/play1.PNG"));
		btnIndepVar.setFont(SWTResourceManager.getFont("Wingdings 3", 9, SWT.NORMAL));
		new Label(composite, SWT.NONE);
		
		lblOperators = new Label(composite, SWT.NONE);
		lblOperators.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1));
		lblOperators.setText("Operators/Constants:");
		new Label(composite, SWT.NONE);
		
		lstIndependentVar = new List(composite, SWT.BORDER);
	
		GridData gd_lstIndependentVar = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_lstIndependentVar.heightHint = 15;
		lstIndependentVar.setLayoutData(gd_lstIndependentVar);
		new Label(composite, SWT.NONE);
		
		composite_1 = new Composite(composite, SWT.BORDER);
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 4);
		gd_composite_1.widthHint = 158;
		gd_composite_1.heightHint = 112;
		composite_1.setLayoutData(gd_composite_1);
		composite_1.setLayout(new GridLayout(5, false));
		
		btn1 = new Button(composite_1, SWT.NONE);
		btn1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("1");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn1 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn1.widthHint = 25;
		gd_btn1.heightHint = 25;
		btn1.setLayoutData(gd_btn1);
		btn1.setText("1");
		
		btn2 = new Button(composite_1, SWT.NONE);
		btn2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("2");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn2 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn2.widthHint = 25;
		gd_btn2.heightHint = 25;
		btn2.setLayoutData(gd_btn2);
		btn2.setText("2");
		
		btn3 = new Button(composite_1, SWT.NONE);
		btn3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("3");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn3 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn3.widthHint = 25;
		gd_btn3.heightHint = 25;
		btn3.setLayoutData(gd_btn3);
		btn3.setText("3");
		
		btn4 = new Button(composite_1, SWT.NONE);
		btn4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("4");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn4 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn4.widthHint = 25;
		gd_btn4.heightHint = 25;
		btn4.setLayoutData(gd_btn4);
		btn4.setText("4");
		
		btnAddition = new Button(composite_1, SWT.NONE);
		btnAddition.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("+");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btnAddition = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btnAddition.heightHint = 25;
		gd_btnAddition.widthHint = 25;
		btnAddition.setLayoutData(gd_btnAddition);
		btnAddition.setText("+");
		
		btn5 = new Button(composite_1, SWT.NONE);
		btn5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("5");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn5 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn5.widthHint = 25;
		gd_btn5.heightHint = 25;
		btn5.setLayoutData(gd_btn5);
		btn5.setText("5");
		
		btn6 = new Button(composite_1, SWT.NONE);
		btn6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("6");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn6 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn6.heightHint = 25;
		gd_btn6.widthHint = 25;
		btn6.setLayoutData(gd_btn6);
		btn6.setText("6");
		
		btn7 = new Button(composite_1, SWT.NONE);
		btn7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("7");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn7 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn7.heightHint = 25;
		gd_btn7.widthHint = 25;
		btn7.setLayoutData(gd_btn7);
		btn7.setText("7");
		
		btn8 = new Button(composite_1, SWT.NONE);
		btn8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("8");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn8 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn8.heightHint = 25;
		gd_btn8.widthHint = 25;
		btn8.setLayoutData(gd_btn8);
		btn8.setText("8");
		
		btnMultiply = new Button(composite_1, SWT.NONE);
		btnMultiply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("*");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btnMultiply = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btnMultiply.heightHint = 25;
		gd_btnMultiply.widthHint = 25;
		btnMultiply.setLayoutData(gd_btnMultiply);
		btnMultiply.setText("*");
		
		btn9 = new Button(composite_1, SWT.NONE);
		btn9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("9");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btn9 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btn9.heightHint = 25;
		gd_btn9.widthHint = 25;
		btn9.setLayoutData(gd_btn9);
		btn9.setText("9");
		
		btn0 = new Button(composite_1, SWT.NONE);
		btn0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("0");
					rebuildExpressionBox();
				}

			}
		});
		GridData gd_btn0 = new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1);
		gd_btn0.heightHint = 25;
		gd_btn0.widthHint = 25;
		btn0.setLayoutData(gd_btn0);
		btn0.setText("0");
		
		btnDot = new Button(composite_1, SWT.NONE);
		btnDot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add(".");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btnDot = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btnDot.heightHint = 25;
		gd_btnDot.widthHint = 25;
		btnDot.setLayoutData(gd_btnDot);
		btnDot.setText(".");
		
		btnCaret = new Button(composite_1, SWT.NONE);
		btnCaret.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("^");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btnCaret = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btnCaret.heightHint = 25;
		gd_btnCaret.widthHint = 25;
		btnCaret.setLayoutData(gd_btnCaret);
		btnCaret.setText("^");
		
		btnMinus = new Button(composite_1, SWT.NONE);
		btnMinus.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("-");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btnMinus = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btnMinus.widthHint = 25;
		btnMinus.setLayoutData(gd_btnMinus);
		btnMinus.setText("-");
		
		btnOpen = new Button(composite_1, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				arrExpression.add("(");
				rebuildExpressionBox();
			}
		});
		GridData gd_btnOpen = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btnOpen.heightHint = 25;
		gd_btnOpen.widthHint = 25;
		btnOpen.setLayoutData(gd_btnOpen);
		btnOpen.setText("(");
		
		btnClose = new Button(composite_1, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				arrExpression.add(")");
				rebuildExpressionBox();
			}
		});
		GridData gd_btnClose = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btnClose.heightHint = 25;
		gd_btnClose.widthHint = 25;
		btnClose.setLayoutData(gd_btnClose);
		btnClose.setText(")");
		
		btnDivide = new Button(composite_1, SWT.NONE);
		btnDivide.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtRightHand.getSelection()!= null){
					arrExpression.add("/");
					rebuildExpressionBox();
				}
			}
		});
		GridData gd_btnDivide = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_btnDivide.widthHint = 25;
		btnDivide.setLayoutData(gd_btnDivide);
		btnDivide.setText("/");
		
		btnDel = new Button(composite_1, SWT.NONE);
		btnDel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					if(arrExpression.isEmpty()) return;
					arrExpression.remove(arrExpression.size() - 1);
					rebuildExpressionBox();
			}
		});
		GridData gd_btnDel = new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1);
		gd_btnDel.widthHint = 27;
		btnDel.setLayoutData(gd_btnDel);
		btnDel.setText("Del");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnParameter = new Button(composite, SWT.NONE);
		btnParameter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				NonLinearRegressionAnalysisParameters dlgParam = new NonLinearRegressionAnalysisParameters(getShell(),parameterList);
			
				if(dlgParam.open() == Dialog.OK){
				  parameterList =  	dlgParam.getParameterList();
					System.out.println("inn");
					lstParameter.removeAll();
				  for(ParameterModel paramModel : parameterList){
					  lstParameter.add(paramModel.name);
						System.out.println("i: ");
				  }
				}
			}
		});
		btnParameter.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1));
		btnParameter.setText("Parameters:");
		
		btnParameters = new Button(composite, SWT.NONE);
		btnParameters.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(lstParameter.getSelectionCount()>0) {//if the purpose is to add
					arrExpression.add(lstParameter.getItem(lstParameter.getSelectionIndex()));
					rebuildExpressionBox();
				}
			}
		});
		btnParameters.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		btnParameters.setImage(ResourceManager.getPluginImage("Star", "icons/play1.PNG"));
		btnParameters.setFont(SWTResourceManager.getFont("Wingdings 3", 9, SWT.NORMAL));
		new Label(composite, SWT.NONE);
		
		lstParameter = new List(composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_lstParameter = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2);
		gd_lstParameter.heightHint = 96;
		gd_lstParameter.widthHint = 199;
		lstParameter.setLayoutData(gd_lstParameter);
		new Label(composite, SWT.NONE);
		
		lblFunction = new Label(composite, SWT.NONE);
		lblFunction.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1));
		lblFunction.setText("Functions:");
		
		btnFunctions = new Button(composite, SWT.NONE);
		btnFunctions.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(cmboFunctions.getSelection() != null){
					arrExpression.add(Functions[cmboFunctions.getSelectionIndex()]);
					rebuildExpressionBox();
				}
			}
		});
		btnFunctions.setImage(ResourceManager.getPluginImage("Star", "icons/play1.PNG"));
		btnFunctions.setFont(SWTResourceManager.getFont("Wingdings 3", 9, SWT.NORMAL));
		new Label(composite, SWT.NONE);
		
		cmboFunctions = new List(composite, SWT.BORDER | SWT.V_SCROLL);
		cmboFunctions.setItems(new String[] {"abs", "acos", "asin", "atan", "cos", "exp", "log", "log10", "sin", "sqrt", "tan"});
		GridData gd_cmboFunctions = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_cmboFunctions.heightHint = 48;
		gd_cmboFunctions.widthHint = 166;
		cmboFunctions.setLayoutData(gd_cmboFunctions);
		cmboFunctions.select(0);
//		listManager.initializeSingleButtonList(lstAvailableData, lstDependentVariables, btnAddDependentVariable);
//		listManager.initializeSingleButtonList(lstAvailableData, lstIndependentVariables, btnAddIndependentVariable);
//		listManager.initializeNumericList(lstAvailableData, filePath);
//		listManager.initializeSingleSelectionList(lstIndependentVar, txtRightHand, btnIndepVar);
		if(indepVar != null){
		lstIndependentVar.add(indepVar);
		}
		txtRightHand.setText(regExpression);
		for(ParameterModel param : parameterList){
			lstParameter.add(param.name);
		}
		return container;
	}
@Override
	protected void buttonPressed(int buttonID){
		if(buttonID == IDialogConstants.OK_ID) okPressed();
		else if(buttonID == IDialogConstants.RETRY_ID){
			txtRightHand.setText("");
		}
		else this.close();
	}
	
protected void	okPressed(){
	
	btnOk.setEnabled(false);
			
	System.out.println("okpress");

	if(txtRightHand.getText().equals("")){
		MessageDialog.openError(getParentShell(), "Error", "Please provide the Right-hand Expression");
		return;
	}else setReturnVal(txtRightHand.getText());
	close();

	}
	private void rebuildExpressionBox(){
		String finalExp = "";
		for(String val : arrExpression){
			finalExp = finalExp + val;
		}
		txtRightHand.setText(finalExp);
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 * @return 
	 */
	
	
	
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.RETRY_ID, "Reset", false);
		btnOk = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(404, 476);
	}
}
