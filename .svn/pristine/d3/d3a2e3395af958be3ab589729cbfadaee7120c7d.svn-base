package org.irri.breedingtool.datamanipulation.data.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.swt.internal.copy.ViewContentProvider;
import org.eclipse.e4.ui.workbench.swt.internal.copy.ViewLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.browser.Browser;
import org.irri.breedingtool.application.handler.DialogHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.eclipse.swt.layout.GridData;

public class PdfViewer {
	
	static ProjectExplorerTreeNodeModel treeNodeModel;
	static String pdfPath = "";
	
	

	/**
	 * @wbp.parser.entryPoint
	 */
	@Inject
	  public PdfViewer(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Browser browser = new Browser(composite, SWT.NONE);
		if(pdfPath.length() > 3) {

			try {
				browser.setUrl(new File(pdfPath).toURI().toURL().toString());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return;
		}
		try {
			browser.setUrl(treeNodeModel.getProjectFile().toURI().toURL().toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Focus
	public void onFocus(MPart part){
		DialogHandler.setPartDialogMaximized(PartStackHandler.getActiveElementID());
	}
	  public static void openPdfFile(ProjectExplorerTreeNodeModel fileM) {
		  	treeNodeModel = fileM;
	  }
	  public static void openPdfFile(String fileM) {
		  	pdfPath = fileM;
	  }
}
