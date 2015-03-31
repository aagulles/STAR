package org.irri.breedingtool.datamanipulation.data.view;

import java.io.File;

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
import org.irri.breedingtool.application.handler.DialogHandler;
import org.irri.breedingtool.application.handler.MenuHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;

public class ImageFileViewer {
	/**
	 * @wbp.parser.entryPoint
	 */
	@Inject
	public void setFilePath(@Named("filePath") String fileP) {
		filePath = fileP;
	};
	private  String filePath;
	@Inject
	  public ImageFileViewer(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		System.out.println(filePath + " <");
		 
			
		
		composite.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				try{
					Image image = new Image(e.display, new ImageData(filePath)); 
					GC gc = e.gc;			   
					gc.drawImage(image, 0, 0);
				}catch(Exception es){
					
				}
			}
		});
	  }
	@Focus
	public void onFocus(MPart part){
		DialogHandler.setPartDialogMaximized(PartStackHandler.getActiveElementID());
	}  
}
