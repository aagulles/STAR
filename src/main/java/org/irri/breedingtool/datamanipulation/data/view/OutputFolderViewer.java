package org.irri.breedingtool.datamanipulation.data.view;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.irri.breedingtool.application.handler.DialogHandler;
import org.irri.breedingtool.application.handler.PartStackHandler;
import org.irri.breedingtool.application.model.ProjectExplorerTreeNodeModel;
import org.irri.breedingtool.manager.impl.ImageManager;
import org.irri.breedingtool.manager.impl.ProjectExplorerManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
public class OutputFolderViewer {

	@Inject
	public void setFilePath(@Named("folderPath") String fileP) {
		filePath = fileP;
	};
	private static String filePath;
	static ProjectExplorerTreeNodeModel fileModel;
	String[] imageList;
	Composite compositeImageView;
	ImageManager imageManager;
	Composite parentComposite;
	private TabFolder tabTxtFiles;
	private Label lblImagePlaceHolder;
	private CLabel activeImageThumbnail;
	private Composite compositeThumbnailHolder;

	/**
	 * @wbp.parser.entryPoint
	 */
	@Inject
	public OutputFolderViewer(final Composite parent) {
		parentComposite = new Composite(parent, SWT.BORDER);
		parentComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		CTabFolder tabFolder = new CTabFolder(parentComposite, SWT.BORDER);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		ArrayList<File> txtFiles = new ArrayList<File>();
		String[] txtExclude = new String[]{"ContrastAnalysisOutput.txt","PairwiseOutput.txt", "_varinfo.txt", "_original.txt" };
		for(int i = 0; i < fileModel.getProjectFile().listFiles().length; i++){
			if(fileModel.getProjectFile().listFiles()[i].getName().endsWith(".txt") ) {
				if(!fileModel.getProjectFile().listFiles()[i].getName().toLowerCase().endsWith("_varinfo.txt") || !fileModel.getProjectFile().listFiles()[i].getName().toLowerCase().endsWith("_original.txt")){
					txtFiles.add(fileModel.getProjectFile().listFiles()[i]);
				}
			}
		}

		if(!txtFiles.isEmpty()){
		System.out.println(fileModel.getProjectFile().getPath() + "\\Output.txt");
		tabFolder.setSelection(0);
		CTabItem tbiOutput = new CTabItem(tabFolder, SWT.NONE);
		tbiOutput.setText("Output");

		tabTxtFiles = new TabFolder(tabFolder, SWT.NONE);
		tbiOutput.setControl(tabTxtFiles);
		

		for (int i = 0; i < txtFiles.size(); i++) {
			TabItem tbtmTest = new TabItem(tabTxtFiles, SWT.NONE);
			tbtmTest.setText(txtFiles.get(i).getName());

			final StyledText stOutput = new StyledText(tabTxtFiles, SWT.BORDER
					| SWT.H_SCROLL | SWT.MULTI | SWT.V_SCROLL);
			tbtmTest.setControl(stOutput);
			stOutput.setIndent(5);
			stOutput.setEditable(false);
			stOutput.setFont(SWTResourceManager.getFont("Courier New", 10,
					SWT.NORMAL));
			try {
				stOutput.setText(TxtFileViewer.readFileAsString(txtFiles.get(i).getAbsolutePath(), null));
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
					String plainText = stOutput.getSelectionText();

					TextTransfer textTransfer = TextTransfer.getInstance();

					try{
					clipboard.setContents(new String[] { plainText },
							new Transfer[] { textTransfer, });
					clipboard.dispose();
					}catch(Exception mnpe){
						System.out.println("No text selected.");
					}
				}
			});
			mntmCopy.setText("Copy");
		}
		for(int i = 0; i < tabTxtFiles.getItemCount(); i++){
			if( !Arrays.asList(txtExclude).contains(tabTxtFiles.getItem(i).getText())){
				tabTxtFiles.setSelection(tabTxtFiles.getItem(i));
			}
		}
	}//end of if txt empty
		
		tabFolder.setSelection(0);
		
		imageManager = new ImageManager();
		final List<String> imageList = imageManager.listAllImage(fileModel.getProjectFile());
		if(imageList.size() == 0){
			return ;
		}
		CTabItem tbtmImageViewer = new CTabItem(tabFolder, SWT.NONE);
		tbtmImageViewer.setText("Graph");

		Composite compositeImageViewer = new Composite(tabFolder, SWT.NONE);
		tbtmImageViewer.setControl(compositeImageViewer);
		compositeImageViewer.setLayout(new GridLayout(1, false));

		ToolBar toolBar = new ToolBar(compositeImageViewer, SWT.FLAT | SWT.RIGHT);

		ToolItem zoomIn = new ToolItem(toolBar, SWT.NONE);
		zoomIn.setToolTipText("Zoom in");

		zoomIn.setImage(ResourceManager.getPluginImage("Star", "icons/zoom_in.png"));

		ToolItem zoomOut = new ToolItem(toolBar, SWT.NONE);
		zoomOut.setToolTipText("Zoom out");

		zoomOut.setImage(ResourceManager.getPluginImage("Star", "icons/zoom_out.png"));

		ToolItem zoomNormal = new ToolItem(toolBar, SWT.NONE);
		zoomNormal.setToolTipText("Zoom to normal size");

		zoomNormal.setImage(ResourceManager.getPluginImage("Star", "icons/zoom.png"));



		ToolItem exportFile = new ToolItem(toolBar, SWT.NONE);
		exportFile.setToolTipText("Export Image");

		exportFile.setImage(ResourceManager.getPluginImage("Star", "icons/picture_go.png"));

		final ScrolledComposite compositeImagePlaceHolder = new ScrolledComposite(compositeImageViewer, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		compositeImagePlaceHolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		compositeImagePlaceHolder.setExpandHorizontal(true);
		compositeImagePlaceHolder.setExpandVertical(true);

		lblImagePlaceHolder = new Label(compositeImagePlaceHolder, SWT.BORDER | SWT.WRAP | SWT.SHADOW_IN | SWT.CENTER);

		lblImagePlaceHolder.setImage(SWTResourceManager.getImage(imageList.get(0)));
		lblImagePlaceHolder.setData("imagePath",imageList.get(0));
		lblImagePlaceHolder.setData("sizeInt",1);

		Menu menu_1 = new Menu(lblImagePlaceHolder);
		lblImagePlaceHolder.setMenu(menu_1);

		MenuItem mntmCopyToClipboard = new MenuItem(menu_1, SWT.NONE);
		mntmCopyToClipboard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProjectExplorerManager projectExplorerManager = new ProjectExplorerManager();
				String imageString[] = new String[1];
				imageString[0] = lblImagePlaceHolder.getData("imagePath").toString();
				projectExplorerManager.copyToClipboard(imageString);

			}
		});
		mntmCopyToClipboard.setText("Copy to Clipboard");

		compositeImagePlaceHolder.setContent(lblImagePlaceHolder);

		compositeImagePlaceHolder.setMinSize(lblImagePlaceHolder.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		final ScrolledComposite scrolledCompositeThumbnailHolder = new ScrolledComposite(compositeImageViewer, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledCompositeThumbnailHolder = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_scrolledCompositeThumbnailHolder.widthHint = 137;
		scrolledCompositeThumbnailHolder.setLayoutData(gd_scrolledCompositeThumbnailHolder);
		scrolledCompositeThumbnailHolder.setExpandHorizontal(true);
		scrolledCompositeThumbnailHolder.setExpandVertical(true);

		compositeThumbnailHolder = new Composite(scrolledCompositeThumbnailHolder, SWT.NONE);
		compositeThumbnailHolder.setLayout(new GridLayout(60, false));

		lblImagePlaceHolder.addListener(SWT.FOCUSED, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				compositeImagePlaceHolder.setFocus();
			}
		});


		for(int i = 0; i < imageList.size(); i++){
			final CLabel lblImageThumbnail = new CLabel(compositeThumbnailHolder, SWT.BORDER_SOLID);
			try{
			Image imgThumb = new Image(Display.getCurrent(),imageList.get(i));

			lblImageThumbnail.setImage(imageManager.resizeImage(imgThumb, 120, 120));
			lblImageThumbnail.setData("imagePath", imageList.get(i));
			lblImageThumbnail.setData("imageIndex", i);
			lblImageThumbnail.setToolTipText(new File(imageList.get(i)).getName());
			if(i==0)activeImageThumbnail = lblImageThumbnail;
			lblImageThumbnail.addListener(SWT.MouseDown, new Listener(){
				@Override
				public void handleEvent(Event event) {
					compositeImagePlaceHolder.setFocus();
					if (new File(lblImageThumbnail.getData("imagePath").toString()).exists()) {
						activeImageThumbnail = lblImageThumbnail;
						for(int y = 0; y < compositeThumbnailHolder.getChildren().length; y++){
							compositeThumbnailHolder.getChildren()[y].setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
						}

						Image image = new Image(Display.getCurrent(), lblImageThumbnail
								.getData("imagePath").toString());
						lblImagePlaceHolder.setImage(image);
						lblImagePlaceHolder.setToolTipText(lblImageThumbnail.getData(
								"imagePath").toString());
						lblImagePlaceHolder.setData("imagePath", lblImageThumbnail
								.getData("imagePath").toString());
						lblImagePlaceHolder.setData("sizeInt", 1);
						lblImageThumbnail.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
						compositeImagePlaceHolder.setMinSize(lblImagePlaceHolder
								.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						compositeImagePlaceHolder.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
					}
					else{
						lblImagePlaceHolder.setText("File" + lblImageThumbnail.getData("imagePath").toString() + " does not exit.");
						lblImagePlaceHolder.getImage().dispose();
						lblImageThumbnail.getImage().dispose();
						lblImageThumbnail.dispose();
					}

				}

			});
		}catch(Exception e){
			System.out.println("output_folder_viewer catch exception, corrupted image.");
//			MessageDialog.openWarning(parentComposite.getShell(), "Corrupted File", "Please double check your variable inputs");
		}
		};

		tabFolder.setSelection(0);
		parentComposite.addListener(SWT.Resize, new Listener() {

			@Override
			public void handleEvent(Event event) {
				compositeImagePlaceHolder.getBounds().height = getPercentage( parentComposite.getBounds().height,79);
			}

		});

		scrolledCompositeThumbnailHolder.setContent(compositeThumbnailHolder);
		scrolledCompositeThumbnailHolder.setMinSize(compositeThumbnailHolder.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		compositeImagePlaceHolder.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(org.eclipse.swt.events.KeyEvent event) {
				System.out.println("KeyPressed: "+ KeyEvent.getKeyText(event.keyCode)+ " vs " + Integer.toString(KeyEvent.VK_LEFT ) + "and"+ Integer.toString(KeyEvent.VK_RIGHT) );
				int keyCode = event.keyCode;
				String direction="none";
				
				if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_KP_LEFT || keyCode == 0x1000003 || keyCode == 0x1000034){
					direction="prev";
				}
				else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_KP_RIGHT   || keyCode == 0x1000004 || keyCode == 0x1000036){
					direction="next";
				}
				int index=(Integer) activeImageThumbnail.getData("imageIndex");
				if(direction.equals("prev")&& index!=0){
					index--;
					scrolledCompositeThumbnailHolder.traverse(SWT.TRAVERSE_ARROW_PREVIOUS);
				}
				else if(direction.equals("next")&& index!=imageList.size()-1){
					index++;
					scrolledCompositeThumbnailHolder.traverse(SWT.TRAVERSE_ARROW_NEXT);
				}
				else direction="none";//wag na gumalaw, nasa dulo na
				
				if(!direction.equals("none")){
						System.out.println(Integer.toString(index));
					CLabel lblImageThumbnail = (CLabel) compositeThumbnailHolder.getChildren()[index];
					if(index!=imageList.size()-1 || index!=0){
						if (new File(lblImageThumbnail.getData("imagePath").toString()).exists()) {
							activeImageThumbnail = lblImageThumbnail;
							
							for(int y = 0; y < compositeThumbnailHolder.getChildren().length; y++){
								compositeThumbnailHolder.getChildren()[y].setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
							}
							
							Image image = new Image(Display.getCurrent(), lblImageThumbnail
									.getData("imagePath").toString());
							lblImagePlaceHolder.setImage(image);
							lblImagePlaceHolder.setToolTipText(lblImageThumbnail.getData(
									"imagePath").toString());
							lblImagePlaceHolder.setData("imagePath", lblImageThumbnail
									.getData("imagePath").toString());
							lblImagePlaceHolder.setData("sizeInt", 1);
							lblImageThumbnail.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
							compositeImagePlaceHolder.setMinSize(lblImagePlaceHolder
									.computeSize(SWT.DEFAULT, SWT.DEFAULT));
							compositeImagePlaceHolder.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
						}
						else{
							lblImagePlaceHolder.setText("File" + lblImageThumbnail.getData("imagePath").toString() + " does not exit.");
							lblImagePlaceHolder.getImage().dispose();
							lblImageThumbnail.getImage().dispose();
							lblImageThumbnail.dispose();
						}
					}
				}
//				else{// if user is pressing buttons other than right or left arrow keys, 
//					MessageDialog.openInformation(parent.getShell(), "Image Navigation", "PLease use the right and left arrow keys to traverse through the images.");
//				}
			}

			@Override
			public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});
		
		//		CTabItem tbtmPdfviewer = new CTabItem(tabFolder, SWT.NONE);
		//		tbtmPdfviewer.setText("PDFViewer");
		//
		//		Composite composite = new Composite(tabFolder, SWT.NONE);
		//		tbtmPdfviewer.setControl(composite);
		//		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		//
		//		Browser pdfBrowser = new Browser(composite, SWT.NONE);
		//		try {
		//			pdfBrowser.setUrl(new File(fileModel.getProjectFile().getPath() + "\\Output.pdf").toURI().toURL().toString());
		//		} catch (MalformedURLException e1) {
		//			// TODO Auto-generated catch block
		//			tbtmPdfviewer.dispose();
		//		}

		zoomIn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((Integer) lblImagePlaceHolder.getData("sizeInt") <= 3 && lblImagePlaceHolder.getImage().isDisposed() == false) {
					Image image = new Image(Display.getCurrent(),lblImagePlaceHolder.getData("imagePath").toString());
					int zoomInt = 1 + (Integer) lblImagePlaceHolder.getData("sizeInt");
					lblImagePlaceHolder.setImage(imageManager.resizeImage(image, image.getBounds().width * zoomInt,	image.getBounds().height * zoomInt));
					lblImagePlaceHolder.setData("sizeInt", zoomInt);
					image.dispose();
					compositeImagePlaceHolder.setMinSize(lblImagePlaceHolder.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				}
			}
		});
		
		zoomOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((Integer) lblImagePlaceHolder.getData("sizeInt") > 1 && lblImagePlaceHolder.getImage().isDisposed() == false) {
					Image image = new Image(Display.getCurrent(),lblImagePlaceHolder.getData("imagePath").toString());
					int zoomInt = (Integer) lblImagePlaceHolder.getData("sizeInt") - 1;
					lblImagePlaceHolder.setImage(imageManager.resizeImage(image, image.getBounds().width * zoomInt,	image.getBounds().height * zoomInt));
					lblImagePlaceHolder.setData("sizeInt", zoomInt);
					image.dispose();
					compositeImagePlaceHolder.setMinSize(lblImagePlaceHolder.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				}
			}
		});
		
		zoomNormal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblImagePlaceHolder.setImage(new Image(Display.getCurrent(),lblImagePlaceHolder.getData("imagePath").toString()));
				lblImagePlaceHolder.setData("sizeInt", 1);
				compositeImagePlaceHolder.setMinSize(lblImagePlaceHolder.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});
		
		exportFile.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlgExport = new DirectoryDialog(Display.getCurrent().getActiveShell());
				dlgExport.setText("Export Image to");

				dlgExport.setMessage("Select a Directory");

				String dirPath = dlgExport.open();
				if (dirPath != null) {
					if(new File(dirPath + "\\" + new File(lblImagePlaceHolder.getData("imagePath").toString()).getName()).exists()){
						if(!MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Confirm", "The file '" +  new File(lblImagePlaceHolder.getData("imagePath").toString()).getName().toString() + "' already exist in the folder. Do you want to overwrite the file? \n\n\n WARNING: This action is permanent" )) return;
					}
					imageManager.exportImageTo(new File(lblImagePlaceHolder.getData("imagePath").toString()), new File(dirPath));
				}
			}
		});

	}
	@Focus
	public void onFocus(MPart part){
		DialogHandler.setPartDialogMaximized(PartStackHandler.getActiveElementID());
	}

	private int getPercentage(int total, int percentage){
		return   (int) (((float) percentage / (float) 100) * total);
	}
	public static void setFileModel(ProjectExplorerTreeNodeModel fModel){
		fileModel = fModel;
	}
}
