package org.irri.breedingtool.manager.api;

import java.io.File;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public interface IImageManager {

	
	public void drawThumbnails(List imageFiles, Canvas canvas, Composite container);
	public Image resizeImage(Image image, int width, int height);
	public void openImage(final String imPath, Canvas imagePlaceHolder);
	public List listAllImage(File folder);
	boolean exportImageTo(File source,File destinationFolder);
	
	
}
