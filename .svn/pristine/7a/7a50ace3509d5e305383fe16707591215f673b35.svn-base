package org.irri.breedingtool.star.analysis.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.utility.DialogFormUtility;

public class PairedSampleDialog extends Dialog {
	private String filePath = PartStackHandler.getActiveElementID();
	private List lstNumericVariables;
	private Button btnSummaryStatistics;
	private Combo cmbAlternativeHypothesis;
	private Spinner txtConfidenceInterval;
	private Button btnAndersondarling;
	private Button btnCramervonMises;
	private Button btnLilliefors;
	private Button btnShapirofrancia;
	private Button btnShapirowilk;
	private DialogFormUtility listManager = new DialogFormUtility();
	private Button btnConfidenceInterval;
	private Group grpTestForNormality;
	private Button btnOkay;
	private Label lblDataSet;
	private Text txtDataSet1;
	private Label lblDataSet_1;
	private Text txtDataSet2;
	private Label lblNewLabel;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Button button;
	private Button button_1;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public PairedSampleDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
	}
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Paired Sample T-Test");
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
		
		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		
		TabItem tbtmTitleCase = new TabItem(tabFolder, SWT.NONE);
		tbtmTitleCase.setText("Title Case");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmTitleCase.setControl(composite);
		composite.setLayout(new GridLayout(5, false));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblNumericVariables = new Label(composite, SWT.NONE);
		lblNumericVariables.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblNumericVariables.setText("Numeric Variable(s)");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lstNumericVariables = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstNumericVariables = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 7);
		gd_lstNumericVariables.heightHint = 238;
		gd_lstNumericVariables.widthHint = 173;
		lstNumericVariables.setLayoutData(gd_lstNumericVariables);
		
		TabItem tbtmOption = new TabItem(tabFolder, SWT.NONE);
		tbtmOption.setText("Options");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmOption.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		Label lblAlternativeHypothesis = new Label(composite_1, SWT.NONE);
		lblAlternativeHypothesis.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAlternativeHypothesis.setText("Alternative Hypothesis:");
		
		cmbAlternativeHypothesis = new Combo(composite_1, SWT.READ_ONLY);
		cmbAlternativeHypothesis.setItems(new String[] {"less", "greater", "two.sided"});
		GridData gd_cmbAlternativeHypothesis = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_cmbAlternativeHypothesis.widthHint = 5;
		cmbAlternativeHypothesis.setLayoutData(gd_cmbAlternativeHypothesis);
		cmbAlternativeHypothesis.setText("two.sided");
		
		Group grpDisplay = new Group(composite_1, SWT.NONE);
		grpDisplay.setText("Display");
		grpDisplay.setLayout(new GridLayout(3, false));
		grpDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		btnSummaryStatistics = new Button(grpDisplay, SWT.CHECK);
		GridData gd_btnSummaryStatistics = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnSummaryStatistics.heightHint = 23;
		btnSummaryStatistics.setLayoutData(gd_btnSummaryStatistics);
		btnSummaryStatistics.setText("Summary Statistics");
		new Label(grpDisplay, SWT.NONE);
		
		
		btnConfidenceInterval = new Button(grpDisplay, SWT.CHECK);
		btnConfidenceInterval.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtConfidenceInterval.setEnabled(btnConfidenceInterval.getSelection());
				
			}
		});
		btnConfidenceInterval.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnConfidenceInterval.setText("Confidence Interval");
		
		txtConfidenceInterval = new Spinner(grpDisplay, SWT.BORDER);
		txtConfidenceInterval.setDigits(2);
		txtConfidenceInterval.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtConfidenceInterval.setMaximum(9900);
		txtConfidenceInterval.setMinimum(9000);
		txtConfidenceInterval.setSelection(9500);
		txtConfidenceInterval.setEnabled(false);
		
		Label label = new Label(grpDisplay, SWT.NONE);
		GridData gd_label = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_label.widthHint = 127;
		label.setLayoutData(gd_label);
		label.setText("%");
		
		grpTestForNormality = new Group(composite_1, SWT.NONE);
		grpTestForNormality.setText("Test for Normality");
		grpTestForNormality.setLayout(new GridLayout(1, false));
		grpTestForNormality.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		btnShapirowilk = new Button(grpTestForNormality, SWT.CHECK);
		btnShapirowilk.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnShapirowilk.setText("Shapiro-Wilk");
		btnShapirowilk.setData("swilk");
		
		btnShapirofrancia = new Button(grpTestForNormality, SWT.CHECK);
		btnShapirofrancia.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnShapirofrancia.setText("Shapiro-Francia");
		btnShapirofrancia.setData("sfrancia");
		
		btnLilliefors = new Button(grpTestForNormality, SWT.CHECK);
		btnLilliefors.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnLilliefors.setText("Lilliefors (Kolmogorov-Smirnov)");
		btnLilliefors.setData("ks");
		
		btnCramervonMises = new Button(grpTestForNormality, SWT.CHECK);
		btnCramervonMises.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnCramervonMises.setText("Cramer-Von Mises");
		btnCramervonMises.setData("cramer");
		
		btnAndersondarling = new Button(grpTestForNormality, SWT.CHECK);
		btnAndersondarling.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		btnAndersondarling.setText("Anderson-Darling");
		btnAndersondarling.setData("anderson");
		
		new Label(composite_1, SWT.NONE);
		
		Label label_1 = new Label(composite_1, SWT.NONE);

		listManager.initializeNumericList(lstNumericVariables, filePath);
		new Label(composite, SWT.NONE);
		
		lblDataSet = new Label(composite, SWT.NONE);
		lblDataSet.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblDataSet.setText("Data Set 1:");
		new Label(composite, SWT.NONE);
		
		button = new Button(composite, SWT.NONE);
		GridData gd_button = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 90;
		button.setLayoutData(gd_button);
		button.setText("Add");
		
		txtDataSet1 = new Text(composite, SWT.BORDER);
		txtDataSet1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtDataSet1.setEditable(false);
		GridData gd_txtDataSet1 = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_txtDataSet1.widthHint = 158;
		gd_txtDataSet1.heightHint = 14;
		txtDataSet1.setLayoutData(gd_txtDataSet1);
		new Label(composite, SWT.NONE);
		
		lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		new Label(composite, SWT.NONE);
		
		lblNewLabel_2 = new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblDataSet_1 = new Label(composite, SWT.NONE);
		lblDataSet_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblDataSet_1.setText("Data Set 2:");
		new Label(composite, SWT.NONE);
		
		button_1 = new Button(composite, SWT.NONE);
		GridData gd_button_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button_1.widthHint = 90;
		button_1.setLayoutData(gd_button_1);
		button_1.setText("Add");
		
		txtDataSet2 = new Text(composite, SWT.BORDER);
		txtDataSet2.setEditable(false);
		txtDataSet2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtDataSet2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblNewLabel = new Label(composite, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_lblNewLabel.heightHint = 90;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		new Label(composite, SWT.NONE);
		
		listManager.initializeNumericList(lstNumericVariables, filePath);
		
		return container;
	}
	
	
	protected void okPressed(){

			btnOkay.setEnabled(false);
	
	
			btnOkay.setEnabled(true);
	
		
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		btnOkay = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(533, 493);
	}

}
