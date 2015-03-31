package org.irri.breedingtool.star.analysis.anova.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.StarAnalysisUtilities;
import org.irri.breedingtool.utility.TableUtility;

public class ContrastDialog extends Dialog {

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	private String[] factorList;
	private String filePath;
	final String opt0 = "NONE";
	final String opt1 = "Compare with control";
	final String opt2 = "Orthogonal Polynomial Contrast";
	final String opt3 = "User Specified Contrast";
	int EDITABLECOLUMN = 0;
	private Map<String, Table> tableMap = new HashMap<String, Table>();
	private Table mainTable;
	private CTabFolder tabFolder;
	private String folderPath;
	private String[] respvar;
	private boolean isContrastReady = false;
	private boolean performCombined = false;

	public ContrastDialog(Shell parentShell, String[] factors, String fileP,
			String outputP, String[] respV, boolean combined) {
		super(parentShell);
		factorList = factors;
		filePath = fileP;
		respvar = respV;
		folderPath = outputP;
		performCombined = false;
		setShellStyle(SWT.MAX | SWT.RESIZE | SWT.PRIMARY_MODAL);
	}

	@Override
	public boolean close() {
		this.getShell().setVisible(false);
		return false;
	}

	boolean contrastReady() {
		return isContrastReady;
	}

	void contrastnotReady() {
		isContrastReady = false;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.marginHeight = 8;
		gridLayout.marginWidth = 8;

		mainTable = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		mainTable.setLinesVisible(true);
		mainTable.setHeaderVisible(true);
		mainTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));

		TableColumn tblclmnFactorName = new TableColumn(mainTable, SWT.NONE);
		tblclmnFactorName.setText("Factor Name");
		tblclmnFactorName.setWidth(86);

		TableColumn tblclmnPartitionType = new TableColumn(mainTable, SWT.NONE);
		tblclmnPartitionType.setWidth(204);
		tblclmnPartitionType.setText("Partition Type");

		TableColumn tblclmnLabel = new TableColumn(mainTable, SWT.NONE);
		tblclmnLabel.setWidth(110);
		tblclmnLabel.setText("Label");

		final TableEditor editor = new TableEditor(mainTable);

		TableColumn tblclmnValue = new TableColumn(mainTable, SWT.NONE);
		tblclmnValue.setWidth(129);
		tblclmnValue.setText("Value");

		tabFolder = new CTabFolder(container, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		for (int i = 0; i < factorList.length; i++) {
			if (DataManipulationManager.getEnvtLevels(factorList[i], new File(
					filePath)).length > 2) {

				TableItem item = new TableItem(mainTable, SWT.NONE);
				item.setText(new String[] { factorList[i], opt0 });

			}
		}

		mainTable.addListener(SWT.MouseDown, new Listener() {
			private DataManipulationManager dataManipulationManager = new DataManipulationManager();

			@Override
			public void handleEvent(Event e) {

				// 0 = VariableName(not editable)
				// 1 = Partition Type (Combo)
				// 2 = Label (not editable)
				// 3 = opt1: Combo | opt2: Spinner | opt3: Spinner
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				// Identify the selected row
				TableItem item = mainTable.getSelection()[0];
				if (item == null)
					return;
				Point mPoint = new Point(e.x, e.y);

				for (int i = 0; i < item.getParent().getColumnCount(); i++) {
					if (item.getBounds(i).contains(mPoint))
						EDITABLECOLUMN = i;
				}

				System.out.println(EDITABLECOLUMN);

				if (EDITABLECOLUMN == 0)
					EDITABLECOLUMN = 1;
				// The control that will be the editor must be a child of the
				// Table
				final String factorName = item.getText(0);

				if (item.getText(1).equals(opt0))
					EDITABLECOLUMN = 1;
				if (item.getText(1).equals(opt3))
					checkTableExist(factorName);
				if (EDITABLECOLUMN == 1) {
					Combo newEditorCombo = new Combo(mainTable, SWT.READ_ONLY);
					if (dataManipulationManager.isNumeric(
							filePath.replace(File.separator, "/"), factorName).equals(
							"TRUE"))
						newEditorCombo.setItems(new String[] { opt0, opt1,
								opt2, opt3 });
					else
						newEditorCombo
								.setItems(new String[] { opt0, opt1, opt3 });

					newEditorCombo.setText(item.getText(EDITABLECOLUMN));
					newEditorCombo.addModifyListener(new ModifyListener() {
						@Override
						public void modifyText(ModifyEvent me) {
							Combo text = (Combo) editor.getEditor();
							editor.getItem().setText(EDITABLECOLUMN,
									text.getText());
							if (text.getText().equals(opt1)) {
								editor.getItem().setText(2, "Control Level:");
								editor.getItem()
										.setText(
												3,
												DataManipulationManager
														.getEnvtLevels(
																factorName,
																new File(
																		filePath))[0]);
								deleteTable(factorName);
							} else if (text.getText().equals(opt2)) {
								editor.getItem().setText(2, "Degree:");
								editor.getItem().setText(3, "1");
								deleteTable(factorName);
							} else if (text.getText().equals(opt3)) {
								editor.getItem().setText(2, "No. of Contrast:");
								editor.getItem().setText(3, "1");
								addNewTable(factorName);
							} else {

								editor.getItem().setText(2, "");
								editor.getItem().setText(3, "");
								deleteTable(factorName);
							}
						}
					});

					newEditorCombo.setFocus();
					editor.setEditor(newEditorCombo, item, EDITABLECOLUMN);
				}
				if (EDITABLECOLUMN == 3) {
					if (item.getText(1).equals(opt2)
							|| item.getText(1).equals(opt3)) {
						Spinner newEditor = new Spinner(mainTable, SWT.NONE);
						newEditor.setIncrement(1);
						newEditor.setMaximum(DataManipulationManager
								.getEnvtLevels(factorName, new File(filePath)).length - 1);
						newEditor.setMinimum(1);
						newEditor.setSelection(Integer.valueOf(item
								.getText(EDITABLECOLUMN)));
						newEditor.addModifyListener(new ModifyListener() {
							@Override
							public void modifyText(ModifyEvent me) {
								Spinner text = (Spinner) editor.getEditor();
								editor.getItem().setText(EDITABLECOLUMN,
										text.getText());
								if (editor.getItem().getText(1).equals(opt3))
									setTableRows(tableMap.get(factorName),
											text.getSelection());
							}
						});
						// newEditor.selectAll();
						newEditor.setFocus();
						editor.setEditor(newEditor, item, EDITABLECOLUMN);
					}
					if (item.getText(1).equals(opt1)) {
						Combo newEditorCombo = new Combo(mainTable,
								SWT.READ_ONLY);

						newEditorCombo.setItems(DataManipulationManager
								.getEnvtLevels(factorName, new File(filePath)));
						newEditorCombo.setText(item.getText(EDITABLECOLUMN));
						newEditorCombo.addModifyListener(new ModifyListener() {
							@Override
							public void modifyText(ModifyEvent me) {
								Combo text = (Combo) editor.getEditor();
								editor.getItem().setText(EDITABLECOLUMN,
										text.getText());

							}
						});

						newEditorCombo.setFocus();
						editor.setEditor(newEditorCombo, item, EDITABLECOLUMN);
					}
				}

			}
		});
		// The editor must have the same size as the cell and must
		// not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 70;

		final ArrayList<Composite> factorContainers = new ArrayList<Composite>();

		return container;
	}

	private void createContentForUserSpecified(Composite factorContainer,
			final String factorName) {
		// Composite testCon = new Composite(xpndItemControl, SWT.NONE);

		String[] factorLevels = DataManipulationManager.getEnvtLevels(
				factorName, new File(filePath));

		tableMap.put(factorName, new Table(factorContainer, SWT.BORDER
				| SWT.FULL_SELECTION));

		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_table.heightHint = 135;
		tableMap.get(factorName).setLayoutData(gd_table);
		tableMap.get(factorName).setHeaderVisible(true);
		tableMap.get(factorName).setLinesVisible(true);

		TableColumn column1 = new TableColumn(tableMap.get(factorName),
				SWT.NONE);
		column1.setText("Label");
		column1.setWidth(86);
		for (String factorLevelName : factorLevels) {
			TableColumn column2 = new TableColumn(tableMap.get(factorName),
					SWT.NONE);
			column2.setWidth(50);
			column2.setText(factorLevelName);
		}

		setTableRows(tableMap.get(factorName), 1);

		final TableEditor editor = new TableEditor(tableMap.get(factorName));
		// editing the second column

		final Spinner sprLevel = new Spinner(tableMap.get(factorName), SWT.NONE);
		sprLevel.setIncrement(1);

		tableMap.get(factorName).addListener(SWT.MouseDown, new Listener() {
			private int EDITABLECOLUMN;

			@Override
			public void handleEvent(Event e) {

				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				// Identify the selected row
				TableItem item = tableMap.get(factorName).getSelection()[0];
				if (item == null)
					return;
				Point mPoint = new Point(e.x, e.y);

				for (int i = 0; i < item.getParent().getColumnCount(); i++) {
					if (item.getBounds(i).contains(mPoint))
						EDITABLECOLUMN = i;
				}

				System.out.println(EDITABLECOLUMN);

				if (EDITABLECOLUMN == 0)
					EDITABLECOLUMN = 1;
				// The control that will be the editor must be a child of the
				// Table

				Text newEditor = new Text(tableMap.get(factorName), SWT.NONE);
				newEditor.setText(item.getText(EDITABLECOLUMN));
				newEditor.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent me) {
						Text text = (Text) editor.getEditor();
						editor.getItem()
								.setText(EDITABLECOLUMN, text.getText());
					}
				});
				newEditor.addKeyListener(new KeyListener() {

					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						System.out.println(e.keyCode + " this");
						String[] charactersAllowed = new String[] { "0", "1",
								"2", "3", "4", "5", "6", "7", "8", "9", ".",
								"-", };
						ArrayList<String> charsAllowed = new ArrayList<String>();
						charsAllowed.addAll(Arrays.asList(new String[] { "0",
								"1", "2", "3", "4", "5", "6", "7", "8", "9",
								".", "-", }));
						if (!charsAllowed.contains(String.valueOf(e.character))) {
							if (e.keyCode != 8) {
								e.doit = false;
								return;

							}
						}
						Text text = (Text) editor.getEditor();
						editor.getItem()
								.setText(EDITABLECOLUMN, text.getText());
						System.out.println("textModified");

					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub

					}

				});

				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, EDITABLECOLUMN);

			}
		});
		// The editor must have the same size as the cell and must
		// not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 40;

	}

	private void deleteTable(String factorName) {
		for (CTabItem tItem : tabFolder.getItems()) {
			if (tItem.getText().equals(factorName)) {
				tItem.dispose();
				tableMap.remove(factorName);
			}
		}
	}

	private void addNewTable(String factorName) {
		if (checkTableExist(factorName))
			return;
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText(factorName);

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		createContentForUserSpecified(composite, factorName);
		tabFolder.setSelection(tabItem);
	}

	private boolean checkTableExist(String factorName) {

		for (CTabItem tItem : tabFolder.getItems()) {
			if (tItem.getText().equals(factorName)) {
				tabFolder.setSelection(tItem);
				return true;
			}
		}

		return false;
	}

	private void setTableRows(Table dialogTable, int levels) {

		if (dialogTable.getItemCount() < levels) {
			for (int i = dialogTable.getItemCount(); i < levels; i++) {
				TableItem item = new TableItem(dialogTable, SWT.NONE);
				ArrayList<String> row = rowSetter(dialogTable.getColumnCount());
				row.set(0, "C" + i);
				item.setText(row.toArray(new String[row.size()]));

			}
		} else if (dialogTable.getItemCount() > levels) {
			dialogTable.remove(levels, dialogTable.getItemCount() - 1);
		}
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	boolean validateTable(Table table) {
		boolean returnVal = false;
		double total = 0;

		for (TableItem item : table.getItems()) {
			for (int i = 1; i < table.getColumnCount(); i++) {

				double x = Double.valueOf(item.getText(i));
				System.out.println(Double.valueOf(item.getText(i)) + ":"
						+ item.getText(i) + "");
				total = total + x;
			}
			if (total != 0)
				return false;
			total = 0;
		}
		if (total == 0)
			return true;
		System.out.println("TOTAL: " + total);

		return false;
	}

	ArrayList<String> rowSetter(int total) {
		ArrayList<String> returnVal = new ArrayList<String>();
		returnVal.add("");
		for (int i = 1; i < total; i++) {
			returnVal.add("0");
		}
		return returnVal;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed() {
		File tempFolder = null;
		ArrayList<String> factor = new ArrayList<String>(), type = new ArrayList<String>(), level = new ArrayList<String>();
		String[] coeff = null;
		// Prioritize Table first
		for (TableItem item : mainTable.getItems()) {
			String factorName = item.getText(0);
			String partitionType = item.getText(1);
			String factorLevel = item.getText(3);
			if (partitionType.equals(opt3)) {

				factor.add(factorName);
				type.add("user");
				level.add(factorLevel);

			}
		}
		if (!factor.isEmpty()) {
			ArrayList<Table> tables = new ArrayList<Table>();
			for (int i = 0; i < factor.size(); i++) {
				if (!validateTable(tableMap.get(factor.get(i)))) {
					MessageDialog
							.openError(
									getShell(),
									"Table Error",
									"Table "
											+ factor.get(i)
											+ " is invalid. All input should be integer and table sum must be equal to zero.");
					return;
				}
				tables.add(tableMap.get(factor.get(i)));

			}
			tempFolder = new File(folderPath + File.separator +"temp");
			tempFolder.mkdir();
			coeff = TableUtility.saveTableToCsv(
					tables.toArray(new Table[tables.size()]),
					tempFolder.getAbsolutePath());
			for (int x = 0; x < coeff.length; x++) {
				coeff[x] = coeff[x].replace(File.separator , "/");
			}
		}

		for (TableItem item : mainTable.getItems()) {
			String factorName = item.getText(0);
			String partitionType = item.getText(1);
			String factorLevel = item.getText(3);
			if (partitionType.equals(opt1)) {

				factor.add(factorName);
				type.add("control");
				level.add(factorLevel);

			} else if (partitionType.equals(opt2)) {

				factor.add(factorName);
				type.add("orthoPoly");
				level.add(factorLevel);

			}
		}

		if (coeff == null) {
			coeff = factor.toArray(new String[factor.size()]);
		}
		StarAnalysisUtilities.testVarArgs(
				folderPath.replace(File.separator, "/"), respvar,
				factor.toArray(new String[factor.size()]),
				type.toArray(new String[factor.size()]),
				level.toArray(new String[factor.size()]), coeff,
				performCombined);
		ProjectExplorerView.rJavaManager.getSTARAnalysisManager().doContrast(
				folderPath.replace(File.separator, "/"), respvar,
				factor.toArray(new String[factor.size()]),
				type.toArray(new String[factor.size()]),
				level.toArray(new String[factor.size()]), coeff,
				performCombined);
		if (tempFolder != null)
			tempFolder.delete();
		MessageDialog.openInformation(getShell(), "Star Analysis","Contrast has been set");
		isContrastReady = true;
		getShell().setVisible(false);
		ProjectExplorerManager projMan = new ProjectExplorerManager();
		projMan.refreshFolder(projMan.getTreeNodeModelbyFilePath(folderPath));
		if (PartStackHandler
				.isOpen(folderPath +  File.separator +"ContrastAnalysisOutput.txt")) {
			PartStackHandler.reOpenPart(projMan
					.getTreeNodeModelbyFilePath(folderPath
							+ File.separator + "ContrastAnalysisOutput.txt"));
		} else {
			PartStackHandler.execute(projMan
					.getTreeNodeModelbyFilePath(folderPath
							+  File.separator + "ContrastAnalysisOutput.txt"));
		}

	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(571, 549);
	}
}
