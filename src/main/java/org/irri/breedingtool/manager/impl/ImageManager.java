package org.irri.breedingtool.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.irri.breedingtool.manager.api.IImageManager;

public class ImageManager implements IImageManager{

	@Override
	public void drawThumbnails(List imageFiles, Canvas canvas,Composite container) {

	}

	@Override
	public Image resizeImage(Image image, int width, int height) {
		// TODO Auto-generated method stub
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, 
				image.getBounds().width, image.getBounds().height, 
				0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}
	public static void openImage(final String imPath, Composite composite){
		composite.setLayout(new FillLayout());
		composite.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				System.out.println("imPath: " + imPath);
				Image image = new Image(e.display, new ImageData(imPath)); 
				GC gc = e.gc;			   
				image.getBounds().height = 10;
				image.getBounds().width = 10;

				gc.drawImage(image, 0, 0);			 						
			}
		});
	}

	@Override
	public void openImage(final String imPath, Canvas imagePlaceHolder) {
		imagePlaceHolder.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Image image = new Image(Display.getCurrent(), imPath);

				e.gc.drawImage(image, 0, 0);        
			}
		});

	}
	@Override
	public boolean exportImageTo(File source, File destinationFolder){
		boolean retValue = false;
		
		try {
		
			InputStream is = new FileInputStream(source);
			OutputStream os = new FileOutputStream(new File(destinationFolder.getPath() + "\\" + source.getName()));

			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			is.close();
			os.close();

			boolean ko=source.renameTo(source);
			System.out.println(ko);
			System.out.println("Exported: "+source.getName());
			retValue = true;

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			retValue =  false;
			//	 e1.printStackTrace();
			//		System.out.println("File not found");
		} catch (IOException eg) {
			retValue =  false;

		}
		return retValue;
	}

	@Override
	public List listAllImage(File folder) {
		List<String> returnFiles=new ArrayList<String>();

		int x = 0;
		for(File fileEntry : folder.listFiles()){
			if(fileEntry.getName().toString().endsWith(".jpg") || fileEntry.getName().toString().endsWith(".png")){
				System.out.println(fileEntry);
				returnFiles.add(fileEntry.getPath());
			}

		}
		return returnFiles;
	}

}
