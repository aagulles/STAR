package org.irri.breedingtool.star.analysis.anova.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.DialogFormUtility;
import org.irri.breedingtool.utility.StarAnalysisUtilities;

public class PostHocDialog extends Dialog {

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */

	protected Object result;
	private String[] availableVars;
	private static String postHocVar[];
	private static String postHocOpt[];
	private String folderPath;
	private double sig;
	private String design;
	private DialogFormUtility lstManager = new DialogFormUtility();
	private List lstPostHocTest;
	private Composite cmpOptions;
	private Control btnOk;
	private boolean isSet = false;
	private boolean isScheffesTestEnabled = true;
	/**
	 * @wbp.parser.constructor
	 */
	public PostHocDialog(Shell parentShell, String[] vars, String folderP,
			double numSig, String designType, boolean set) {
		super(parentShell);
		setBlockOnOpen(false);
		isSet = set;
		setShellStyle(SWT.BORDER | SWT.MIN | SWT.RESIZE | SWT.PRIMARY_MODAL);
		availableVars = vars;
		folderPath = folderP;
		sig = numSig;
		design = designType;
	}

	public PostHocDialog(Shell parentShell, String[] vars, String folderP,
			double numSig, String designType, boolean set, boolean isScheffesTestEnabled) {
		super(parentShell);
		setBlockOnOpen(false);
		isSet = set;
		setShellStyle(SWT.BORDER | SWT.MIN | SWT.RESIZE | SWT.PRIMARY_MODAL);
		availableVars = vars;
		folderPath = folderP;
		sig = numSig;
		design = designType;
		this.isScheffesTestEnabled = isScheffesTestEnabled;
	}
	@Override
	public boolean close() {
		this.getShell().setVisible(false);
		return false;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout(3, false);
		gl_container.marginHeight = 8;
		gl_container.marginWidth = 8;
		container.setLayout(gl_container);

		Label label = new Label(container, SWT.NONE);
		label.setText("Response Variable(s) \r\nwith significant effect(s)");
		new Label(container, SWT.NONE);

		Label lblPostHocTests = new Label(container, SWT.NONE);
		lblPostHocTests.setText("Post Hoc Tests:");

		List lstAvailableVariables = new List(container, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstAvailableVariables = new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1);
		gd_lstAvailableVariables.heightHint = 174;
		gd_lstAvailableVariables.widthHint = 200;
		lstAvailableVariables.setLayoutData(gd_lstAvailableVariables);

		for (int i = 0; i < availableVars.length; i++) {
			lstAvailableVariables.add(availableVars[i]);
		}
		Button btnAddRemove = new Button(container, SWT.NONE);
		GridData gd_btnAddRemove = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnAddRemove.widthHint = 82;
		btnAddRemove.setLayoutData(gd_btnAddRemove);
		btnAddRemove.setText("Add");

		lstPostHocTest = new List(container, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI);
		GridData gd_lstPostHocTest_1 = new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1);
		gd_lstPostHocTest_1.heightHint = 190;
		gd_lstPostHocTest_1.widthHint = 200;
		lstPostHocTest.setLayoutData(gd_lstPostHocTest_1);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		cmpOptions = new Composite(container, SWT.NONE);
		cmpOptions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				4, 1));
		cmpOptions.setLayout(new GridLayout(1, false));

		Button btnLeastSignificantDifference = new Button(cmpOptions, SWT.CHECK);
		btnLeastSignificantDifference.setLayoutData(new GridData(SWT.FILL,
				SWT.FILL, true, false, 1, 1));
		btnLeastSignificantDifference
				.setText("Least Significant Difference (LSD) Test");
		btnLeastSignificantDifference.setData("LSD");

		Button btnDuncanMultipleRange = new Button(cmpOptions, SWT.CHECK);
		btnDuncanMultipleRange.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, false, 1, 1));
		btnDuncanMultipleRange.setText("Duncan Multiple Range test (DMRT)");
		btnDuncanMultipleRange.setData("duncan");

		Button btnTukeysHonestSignificant = new Button(cmpOptions, SWT.CHECK);
		btnTukeysHonestSignificant.setLayoutData(new GridData(SWT.FILL,
				SWT.FILL, true, false, 1, 1));
		btnTukeysHonestSignificant
				.setText("Tukey's Honest Significant Difference (HSD) Test");
		btnTukeysHonestSignificant.setData("HSD");

		Button btnStudentnewmannkeulssnkTest = new Button(cmpOptions, SWT.CHECK);
		btnStudentnewmannkeulssnkTest
				.setText("Student-Newmann-Keul's (SNK) Test");
		btnStudentnewmannkeulssnkTest.setData("SNK");

		Button btnScheffesTest = new Button(cmpOptions, SWT.CHECK);
		btnScheffesTest.setText("Scheffe's Test");
		btnScheffesTest.setData("scheffe");
		btnScheffesTest.setEnabled(isScheffesTestEnabled);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		lstManager.initializeSingleButtonList(lstAvailableVariables,
				lstPostHocTest, btnAddRemove);
		new Label(container, SWT.NONE);

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		btnOk = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected void okPressed() {
		if (lstPostHocTest.getItemCount() > 0
				&& lstManager.isCheckBoxesHasBoolean(cmpOptions) == true) {
			btnOk.setEnabled(false);

			postHocVar = lstPostHocTest.getItems();
			postHocOpt = lstManager.getCheckBoxesValue(cmpOptions);

			if(isScheffesTestEnabled == true){
				ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doPairwiseANOVA(folderPath.replace(File.separator, "/"), postHocVar,
						postHocOpt, design, sig, isSet);
				System.out.println("DoPairwiseAnova called.");
			}
			else{
				StarAnalysisUtilities.testVarArgs(folderPath.replace(File.separator, "/"), postHocVar,
						postHocOpt);
				ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doPairwiseBIBD(folderPath.replace(File.separator, "/"), postHocVar,
						postHocOpt);
				
			}
				
			ProjectExplorerManager projectMan = new ProjectExplorerManager();
			projectMan.refreshFolder(projectMan
					.getTreeNodeModelbyFilePath(folderPath));

			if (PartStackHandler.isOpen(folderPath + "PairwiseOutput.txt")) {
				PartStackHandler.reOpenPart(projectMan
						.getTreeNodeModelbyFilePath(folderPath
								+ "PairwiseOutput.txt"));
				System.out.println("IsOpen");
			} else {
				PartStackHandler.execute(projectMan
						.getTreeNodeModelbyFilePath(folderPath
								+ "PairwiseOutput.txt"));

			}
			btnOk.setEnabled(true);
			this.getShell().setVisible(false);
		} else {
			MessageDialog
					.openError(this.getShell(), "Error",
							"Must be aleast one variable selected and one option selected");
		}
	}

	static String[] getPostHocVar() {
		return postHocVar;
	}

	static String[] getPostHocOpt() {
		return postHocOpt;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(605, 555);
	}

}
