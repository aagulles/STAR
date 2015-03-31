package org.irri.breedingtool.pbtools.analysis.generationmean.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.datamanipulation.dialog.OperationProgressDialog;
import org.irri.breedingtool.manager.impl.DataManipulationManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.irri.breedingtool.pbtools.analysis.environment.dialog.SpecifyRowColDialog;
import org.irri.breedingtool.projectexplorer.view.ProjectExplorerView;
import org.irri.breedingtool.utility.PBToolAnalysisUtilities;
import org.irri.breedingtool.utility.WindowDialogControlUtil;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;

public class RawDataDialog extends Dialog {

	private ProjectExplorerTreeNodeModel fileModel;
	private ArrayList<String> varInfo;
	private String[] numericVariables;
	private String[] factorVariables;
	private File file;
	private DataManipulationManager dataManipulationManager = new DataManipulationManager();
	private FileDialog fd;

	//specify parameters
	private int selectionIndex = 0;
	private int designIndex = 1;
	private boolean basisCorrelation=true;
	private int percentSelected = 5;

	private String weightsFileName="";
	private String markersFileName="";
	private String qtlFileName ="";
	private ArrayList<String> userNotationList = new ArrayList<String>();
	private ArrayList<String> convertedNotationList = new ArrayList<String>();
	private Text levelOfSignificance;
	private Text fGenText;
	private Text bc1GenText;
	private Text bc2GenText;
	private String[] allVariables;
	private Group grpRecordingGenerationLevels;
	private List generationLevels;
	private Button btnP1;
	private Button btnP2;
	private Button btnF;
	private Button btnBc1;
	private Button btnBc2;
	private List notationList;
	private Button removeNotationBtn;
	private String analysisType;
	private Button addBtn;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public RawDataDialog(Shell parentShell, String analysisType, File file) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		setBlockOnOpen(false);
		this.analysisType = analysisType;
		this.file=file;
		setFactors();
		allVariables = dataManipulationManager.getListItems(varInfo);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		getShell().setData("analysis", analysisType);
		getShell().setData("filePathID",file.getAbsolutePath());
		WindowDialogControlUtil.addWindowDialogToList(getShell());

		parent.addDisposeListener(new DisposeListener(){
			@Override
			public void widgetDisposed(DisposeEvent e) {
				// TODO Auto-generated method stub
				WindowDialogControlUtil.removeWindowDialogToList(getShell());
			}

		});

		fd = new FileDialog(getShell(), SWT.OPEN);
		fd.setText("Choose File Input");

		String[] filterExt = {"*.csv"};
		fd.setFilterExtensions(filterExt);
		
		parent.getShell().setText("Generation Mean Analysis(Raw Data): "+dataManipulationManager.getDisplayFileName(file.getAbsolutePath()));
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 4;
		grpRecordingGenerationLevels = new Group(container, SWT.NONE);
		grpRecordingGenerationLevels.setLayout(new GridLayout(4, false));
		grpRecordingGenerationLevels.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		Label lblNewLabel_2 = new Label(grpRecordingGenerationLevels, SWT.NONE);
		lblNewLabel_2.setText("User's Notation");

		Label lblNewLabel_3 = new Label(grpRecordingGenerationLevels, SWT.WRAP);
		GridData gd_lblNewLabel_3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_3.widthHint = 159;
		gd_lblNewLabel_3.heightHint = 16;
		lblNewLabel_3.setLayoutData(gd_lblNewLabel_3);
		lblNewLabel_3.setText("Convert to General Notation of:");
		new Label(grpRecordingGenerationLevels, SWT.NONE);

		Label lblNotationList = new Label(grpRecordingGenerationLevels, SWT.NONE);
		GridData gd_lblNotationList = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNotationList.widthHint = 121;
		lblNotationList.setLayoutData(gd_lblNotationList);
		lblNotationList.setText("Notation List:       ");

		generationLevels = new List(grpRecordingGenerationLevels, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		generationLevels.setItems(allVariables);
		GridData gd_generationLevels = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_generationLevels.widthHint = 88;
		generationLevels.setLayoutData(gd_generationLevels);
		generationLevels.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notationList.setSelection(-1); // unselect everything from notation List
				removeNotationBtn.setEnabled(false); // disable remove button
				addBtn.setEnabled(true);
			}
		});
		Group group = new Group(grpRecordingGenerationLevels, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));
		group.setLayout(new GridLayout(4, false));

		btnP1 = new Button(group, SWT.RADIO);
		btnP1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnP1.setSelection(true);
		btnP1.setText("P1");
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);

		btnP2 = new Button(group, SWT.RADIO);
		btnP2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnP2.setText("P2");
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);

		btnF = new Button(group, SWT.RADIO);
		GridData gd_btnF = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnF.widthHint = 82;
		btnF.setLayoutData(gd_btnF);
		btnF.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnF.setText("F");

		Label lblGeneration = new Label(group, SWT.NONE);
		lblGeneration.setEnabled(true);
		lblGeneration.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblGeneration.setText("Generation:");

		fGenText = new Text(group, SWT.BORDER);
		fGenText.setEnabled(false);
		GridData gd_fGenText = new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1);
		gd_fGenText.widthHint = 15;
		fGenText.setLayoutData(gd_fGenText);

		btnBc1 = new Button(group, SWT.RADIO);
		btnBc1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnBc1.setText("BC1");

		Label label = new Label(group, SWT.NONE);
		label.setEnabled(true);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label.setText("Generation:");

		bc1GenText = new Text(group, SWT.BORDER);
		bc1GenText.setEnabled(false);
		bc1GenText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));

		btnBc2 = new Button(group, SWT.RADIO);
		btnBc2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateGeneralNotationText();
			}
		});
		btnBc2.setText("BC2");

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setEnabled(true);
		label_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label_1.setText("Generation:");

		bc2GenText = new Text(group, SWT.BORDER);
		bc2GenText.setEnabled(false);
		bc2GenText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));

		new Label(grpRecordingGenerationLevels, SWT.NONE);

		notationList = new List(grpRecordingGenerationLevels, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_notationList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_notationList.widthHint = 70;
		notationList.setLayoutData(gd_notationList);
		notationList.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//				List list=(List) e.getSource();
				if(notationList.getSelectionCount()>0){
					generationLevels.add(userNotationList.get(notationList.getSelectionIndex()));
					userNotationList.remove(userNotationList.get(notationList.getSelectionIndex()));
					convertedNotationList.remove(notationList.getSelectionIndex());
					notationList.remove(notationList.getSelectionIndex());
					removeNotationBtn.setEnabled(false);
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(notationList.getSelectionCount()>0){
					generationLevels.setSelection(-1); // unselect everything from generationLevels
					addBtn.setEnabled(false);
					removeNotationBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addBtn = new Button(grpRecordingGenerationLevels, SWT.NONE);
		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String genLevels[] = generationLevels.getSelection();
				if(genLevels.length <1) MessageDialog.openError(getShell(), "Error", "Please select a variable from the User's Notation list!");
				else if(btnF.getSelection() && fGenText.getText().isEmpty()) MessageDialog.openError(getShell(), "Error", "Please add a generation value for the F general notation!");
				else if(btnBc1.getSelection() && bc1GenText.getText().isEmpty()) MessageDialog.openError(getShell(), "Error", "Please add a generation value for the BC1 general notation!");
				else if(btnBc2.getSelection() && bc2GenText.getText().isEmpty()) MessageDialog.openError(getShell(), "Error", "Please add a generation value for the BC2 general notation!");
				else{
					//specify parameters
					try{
						if(btnF.getSelection()){
							if( Double.parseDouble(fGenText.getText())<1){//check level if 0<levelOfSignificance<1
								MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the generation value of F should be should be atleast 1.");
							}
						}
						else if(btnBc1.getSelection() ){
							if( Double.parseDouble(bc1GenText.getText())<1){//check level if 0<levelOfSignificance<1
								MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the generation value of BC1 should be should be atleast 1.");
							}
						}
						else if(btnBc2.getSelection()){
							if( Double.parseDouble(bc2GenText.getText())<1){//check level if 0<levelOfSignificance<1
								MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the generation value of BC2 should be should be atleast 1.");
							}
						}
						
						String convertedNotation = getConvertedGeneralNotationOf();
						userNotationList.add(genLevels[0]);
						convertedNotationList.add(convertedNotation);
						notationList.add(genLevels[0]+"->"+convertedNotation);
						generationLevels.remove(generationLevels.getSelectionIndex());
						generationLevels.setSelection(-1);
					}catch(NumberFormatException nfe){//if not a number
						MessageDialog.openWarning(getShell(), "Invalid Format", "Sorry, the generation value should be a number.");
					}
				}
			}
		});
		GridData gd_addBtn = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_addBtn.widthHint = 52;
		addBtn.setLayoutData(gd_addBtn);
		addBtn.setText("Add");

		removeNotationBtn = new Button(grpRecordingGenerationLevels, SWT.NONE);
		removeNotationBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(notationList.getSelectionCount()>0){
					generationLevels.add(userNotationList.get(notationList.getSelectionIndex()));
					userNotationList.remove(userNotationList.get(notationList.getSelectionIndex()));
					convertedNotationList.remove(notationList.getSelectionIndex());
					notationList.remove(notationList.getSelectionIndex());
					removeNotationBtn.setEnabled(false);
				}
			}
		});
		removeNotationBtn.setEnabled(false);
		GridData gd_removeNotationBtn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_removeNotationBtn.widthHint = 77;
		removeNotationBtn.setLayoutData(gd_removeNotationBtn);
		removeNotationBtn.setText("Remove");
		new Label(grpRecordingGenerationLevels, SWT.NONE);

		Label lblLevelOfSignificance = new Label(container, SWT.NONE);
		lblLevelOfSignificance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLevelOfSignificance.setText("Level of Significance:");

		levelOfSignificance = new Text(container, SWT.BORDER);
		levelOfSignificance.setText("0.05");
		GridData gd_levelOfSignificance = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_levelOfSignificance.widthHint = 50;
		levelOfSignificance.setLayoutData(gd_levelOfSignificance);
		new Label(container, SWT.NONE);

		return container;

	}

	protected String getConvertedGeneralNotationOf() {
		// TODO Auto-generated method stub
		if(btnP1.getSelection()) return "B0F0P1";
		else if(btnP2.getSelection()) return "B0F0P2"; 
		else if(btnF.getSelection()) return "B0F"+fGenText.getText()+"P0";
		else if(btnBc1.getSelection()) return "B"+bc1GenText.getText()+"F1P1";
		else  return "B"+bc2GenText.getText()+"F1P2";
	}

	protected void activateGeneralNotationText() {
		// TODO Auto-generated method stub
		fGenText.setEnabled(false);
		bc1GenText.setEnabled(false);
		bc2GenText.setEnabled(false);

		if(btnF.getSelection()) fGenText.setEnabled(true);
		else if(btnBc1.getSelection()) bc1GenText.setEnabled(true);
		else if(btnBc2.getSelection()) bc2GenText.setEnabled(true);
	}

	protected void setFactors() {
		// TODO Auto-generated method stub
		varInfo=dataManipulationManager.getVarInfoFromFile(file.getAbsolutePath());
		numericVariables=dataManipulationManager.getNumericVars(varInfo);
		factorVariables=dataManipulationManager.getFactorVars(varInfo);
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		//		createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Reset", true);
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed(){

		try{
			if( Double.parseDouble(levelOfSignificance.getText())<=0 || Double.parseDouble(levelOfSignificance.getText())>=1){//check level if 0<levelOfSignificance<1
				MessageDialog.openWarning(getShell(), "Invalid Input", "Sorry, the level of signifcance should have a value of between 0 and 1.");
			}else if(convertedNotationList.isEmpty()){
				MessageDialog.openWarning(getShell(), "Empty Field", "Please add variables to the Notation List.");
			}else{
				//specify parameters
				String[] usersNotation = userNotationList.toArray(new String[userNotationList.size()]); 
				String[] generalNotation= convertedNotationList.toArray(new String[convertedNotationList.size()]); 
				String alpha = levelOfSignificance.getText();			
				String dataFileName = file.getAbsolutePath().toString().replaceAll("\\\\+", "/");

				File outputFolder = PBToolAnalysisUtilities.createOutputFolder(file.getName(),analysisType);
				String newOutputFileName = outputFolder.getAbsolutePath().replaceAll("\\\\+", "/")+"/output.txt";
				OperationProgressDialog rInfo = new OperationProgressDialog(getShell(), "Generation Mean Analysis (Raw Data as input)");
				rInfo.open();
				ProjectExplorerView.rJavaManager.getPbToolAnalysisManager().doGenerationMeanRawData(dataFileName, newOutputFileName, usersNotation, generalNotation, alpha);
				rInfo.close();
				WindowDialogControlUtil.hideAllWindowDialog();

				PBToolAnalysisUtilities.openFolder(outputFolder);
			}
		}catch(NumberFormatException nfe){//if not a number
			MessageDialog.openWarning(getShell(), "Invalid Format", "The Level of Signifcance should have a numeric value of between 0 and 1.");
		}

	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(689, 409);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}