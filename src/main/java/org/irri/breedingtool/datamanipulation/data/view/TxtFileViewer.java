package org.irri.breedingtool.datamanipulation.data.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.irri.breedingtool.application.handler.DialogHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.utility.GeneralUtility;
import org.eclipse.wb.swt.SWTResourceManager;

import com.sun.jna.platform.FileUtils;

public class TxtFileViewer {
	static File txtFile;
	static Scanner opnScanner;
	static StyledText styledText;
	private String outputFile;
	@Inject
	  public TxtFileViewer(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new BorderLayout(0, 0));
		ArrayList<String> lineArray = new ArrayList<String>();
		final StyledText stOutput = new StyledText(composite, SWT.BORDER | SWT.READ_ONLY + SWT.H_SCROLL + SWT.V_SCROLL);
			stOutput.setIndent(5);
		stOutput.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
		try {
			stOutput.setText(readFileAsString(txtFile.getAbsolutePath(), "utf-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Menu menu = new Menu(stOutput);
		stOutput.setMenu(menu);

		MenuItem mntmCopy = new MenuItem(menu, SWT.NONE);
		mntmCopy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Clipboard clipboard = new Clipboard(parent.getDisplay());
				String plainText =  stOutput.getSelectionText();


				TextTransfer textTransfer = TextTransfer.getInstance();

				clipboard.setContents(new String[]{plainText}, new Transfer[]{textTransfer, });
				clipboard.dispose();
			}
		});
		mntmCopy.setText("Copy");
		
		//System.out.println(Integer.toString(styledText.getLineSpacing()));
		ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(BorderLayout.NORTH);
		//styledText.s
		try {
			opnScanner = new Scanner(txtFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("file not found.");
		}
		 while( opnScanner.hasNext() ) {
	            // Read each line and display its value
			 	String newLine = opnScanner.nextLine();
			 	newLine = newLine.replaceAll(" ", "\t");
				try {
					styledText.setText(styledText.getText()+newLine+"\n");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
				
				}
		}
		 opnScanner.close();
	  }
	
	  public static void openTxtFile(ProjectExplorerTreeNodeModel fileM) {
		  	txtFile = new File(fileM.getProjectFile().getAbsolutePath());
	  }
	  
	  public static boolean saveTxtFileChanges(File file) {
		  String filename = null;
		  FileWriter writer = null;
		  
			try {
				writer = new FileWriter(file);
				String newLine = styledText.getText();
				newLine=newLine.replaceAll("\t", " ");
				writer.write(styledText.getText());
				if (writer != null)
					writer.close();
				return true;
			} catch (Exception e) {
				System.out.print(e+"\n");
				return false;
			}
	  }
	  public static String readFileAsString(String fileName, String charsetName)
			    throws java.io.IOException {
		  
		  	charsetName = GeneralUtility.getFileEncoding(fileName);
			  java.io.InputStream is = new java.io.FileInputStream(fileName);
			  try {
			    final int bufsize = 4096;
			    int available = is.available();
			    byte data[] = new byte[available < bufsize ? bufsize : available];
			    int used = 0;
			    while (true) {
			      if (data.length - used < bufsize) {
			        byte newData[] = new byte[data.length << 1];
			        System.arraycopy(data, 0, newData, 0, used);
			        data = newData;
			      }
			      int got = is.read(data, used, data.length - used);
			      if (got <= 0) break;
			      used += got;
			    }
			    return charsetName != null ? new String(data, 0, used, charsetName)
			                               : new String(data, 0, used);
			  } finally {
			    is.close();
			  }
			}
	  @Focus
	  public void onFocus(MPart part){
	  	DialogHandler.setPartDialogMaximized(PartStackHandler.getActiveElementID());
	  }
}

