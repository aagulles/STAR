package org.irri.breedingtool.graphs.managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.irri.breedingtool.graphs.dialog.ChooseFillPatternDialog;
import org.irri.breedingtool.graphs.dialog.ChoosePlotSymbolsDialog;

public class GraphTableManager {
	//CONSTANTS
	public static final int ROW_STATIC_TEXT = 0;
	public static final int ROW_TEXT = 1;
	public static final int ROW_COLOR_PICKER = 2;
	public static final int ROW_FILL_TYPE = 3;
	public static final int ROW_NUMERIC = 4;
	public static final int ROW_SPINNER = 5;
	public static final int ROW_COMBO = 6;
	public static final int ROW_SYMBOL = 7;
	
	public int ROW_HEIGHT = 18;
	public int SYMBOL_HEIGHT = 0;
	public HashMap<String, Integer> tableMap = new HashMap<String,Integer>();

	private ArrayList<TableEditor[]> tableEditors = new ArrayList<TableEditor[]>();
	private int TABLE_INDEX = -1;

	private Table mainTable;

	private ArrayList<Integer> ROW_TYPES = new ArrayList<Integer>();

	private Object[] rowData = new Object[1];
	private ArrayList<Object[]> tableData = new ArrayList<Object[]>();


	public GraphTableManager(Table table, ArrayList<Integer> rowType){
		mainTable = table;
		ROW_TYPES = rowType;

		rowData = new Object[mainTable.getColumnCount()];
		mainTable.addListener(SWT.MeasureItem, new Listener() {
			   public void handleEvent(Event event) {
			      // height cannot be per row so simply set
			      event.height = ROW_HEIGHT;
			   }
			});

	}
	
	public void setAllEnabled(boolean flag){
		for(int i = 0; i < tableData.size(); i++){
			Object[] objectRow = tableData.get(i);
			for(Object obj: objectRow){
				if(obj instanceof Text) ((Text)obj).setEnabled(flag);
				else if(obj instanceof Spinner) ((Spinner)obj).setEnabled(flag);
				else if(obj instanceof Button) ((Button)obj).setEnabled(flag);
			}
		}
	}
	public void setColumnsDisabled(boolean flag,int... columns){
		for(int i = 0; i < tableData.size(); i++){
			Object[] objectRow = tableData.get(i);
			for(int x : columns ){
				if(objectRow[x] instanceof Text) ((Text)objectRow[x]).setEnabled(flag);
				else if(objectRow[x] instanceof Spinner) ((Spinner)objectRow[x]).setEnabled(flag);
				else if(objectRow[x] instanceof Button) ((Button)objectRow[x]).setEnabled(flag);
			}
		}
		
	}
	public void removeAll(){
	
		for(int i =  mainTable.getItemCount() - 1; i >= 0 ; i--){
			deleteRow(i);
		}
		
		
	}
	public void setTableIndex(int index){
		TABLE_INDEX = index;
	}
	public int getTableIndex(){
		return TABLE_INDEX;
	}
	

	public void setColumnWidths(int width){
		for(int i = 0; i < mainTable.getColumnCount(); i++){
			mainTable.getColumn(i).setWidth(width);
		}
	}
	public Object[] getRowObjects(int row){
		return tableData.get(row);
	}
	public void addItem(Object[] items){
		TableItem newItem = new TableItem(mainTable,SWT.NONE);
		Object[] newRowItem = new Object[ROW_TYPES.size()];
		TableEditor[] newEditors = new TableEditor[ROW_TYPES.size()];
		
		if(TABLE_INDEX != -1){
			tableMap.put(String.valueOf(items[TABLE_INDEX]), tableData.size());
		}
		
		for(int i = 0; i < ROW_TYPES.size(); i++){
			int TYPE = ROW_TYPES.get(i);
			
			switch(TYPE){
			case ROW_STATIC_TEXT : {
				newItem.setText(i, (String)items[i]);
				newRowItem[i] = items[i];
				newEditors[i] = null;
				break;
			}
			
			case ROW_TEXT : {
				
				
				
				Text textEditor = new Text(mainTable,SWT.NONE);
				if(items[i] instanceof RowEntityModel){
					RowEntityModel rowModel = (RowEntityModel)items[i];
					textEditor.setText((String)rowModel.value);
					if(rowModel.modifyListener != null) textEditor.addModifyListener(rowModel.modifyListener);
					if(rowModel.keyListener != null) textEditor.addKeyListener(rowModel.keyListener);
				}
				else{
					textEditor.setText((String)items[i]);
				}
				
				TableEditor editor = new TableEditor(mainTable);
				editor.grabHorizontal = editor.grabVertical = true;
				editor.setEditor(textEditor, newItem, i);
				newRowItem[i] = textEditor;
				newEditors[i] = editor;
				
				break;
			}
			
			case ROW_SYMBOL: {
				String defaultSymbolSelected;
				SelectionListener defaultSelectionListener = null;
				if(items[i] instanceof RowEntityModel){
					RowEntityModel rowModel = (RowEntityModel) items[i];
					defaultSymbolSelected = (String) rowModel.value;
					if(rowModel.selectionListener != null){
						defaultSelectionListener = rowModel.selectionListener;
					}
				}
				else{
					defaultSymbolSelected = (String) items[i];
					final int rowCol = i;
					defaultSelectionListener = new SelectionListener(){

						@Override
						public void widgetSelected(SelectionEvent e) {
							System.out.println(Integer.getInteger((String) ((Button)e.widget).getData("SELECTED")));
							ChoosePlotSymbolsDialog fillDlg = new ChoosePlotSymbolsDialog(Display.getCurrent().getActiveShell(),Integer.parseInt((String) ((Button)e.widget).getData("SELECTED")));
//								if(((Button)e.widget).getData("SELECTED") != null)
//									fillDlg.setDefaultSelected(Integer.getInteger((String) ((Button)e.widget).getData("SELECTED")));
//								else fillDlg.setDefaultSelected(0);
								
								fillDlg.open();
							if(fillDlg.getReturnCode() == 0){
								((Button)e.widget).setImage(ResourceManager.getPluginImage("Star", "icons/plotsymbol"+fillDlg.getChosenSymbol()+".png"));
//								System.out.println(fillDlg.getChosenPattern());
								
								((Button)e.widget).setImage(
										createImage(
												Display.getCurrent(),
												new Color(Display.getCurrent(),
												new RGB(228, 237, 216)), 
												ResourceManager.getPluginImage("Star", "icons/plotsymbol"+fillDlg.getChosenSymbol()+".png"), 
												mainTable.getColumn(rowCol).getWidth(), 
												mainTable.getColumn(rowCol).getWidth()
												));
								((Button)e.widget).setData("SELECTED", fillDlg.getChosenSymbol());
							}
						}
						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}

					};
				}
				Button button = new Button(mainTable,SWT.NONE);

				TableEditor editor = new TableEditor(mainTable);
				editor.grabHorizontal = editor.grabVertical = true;
				editor.setEditor(button, newItem, i);
				newEditors[i] = editor;
				button.setImage(
						createImage(
								Display.getCurrent(),
								new Color(Display.getCurrent(),
								new RGB(228, 237, 216)), 
								ResourceManager.getPluginImage("Star", "icons/plotsymbol"+defaultSymbolSelected+".png"), 
								mainTable.getColumn(i).getWidth(), 
								mainTable.getColumn(i).getWidth()
								));
				button.setData("SELECTED",defaultSymbolSelected);
				button.addSelectionListener(defaultSelectionListener);

				newRowItem[i] = button;					
				break;
				
			}
			
			case ROW_FILL_TYPE: {
				
				String defaultImageSelected;
				SelectionListener defaultSelectionListener = null;
				if(items[i] instanceof RowEntityModel){
					RowEntityModel rowModel = (RowEntityModel) items[i];
					defaultImageSelected = (String) rowModel.value;
					if(rowModel.selectionListener != null){
						defaultSelectionListener = rowModel.selectionListener;
					}
				
				}
				else{
					defaultImageSelected = (String) items[i];
					defaultSelectionListener = new SelectionListener(){

						@Override
						public void widgetSelected(SelectionEvent e) {
							ChooseFillPatternDialog fillDlg = new ChooseFillPatternDialog(Display.getCurrent().getActiveShell(), "");
							fillDlg.open();
							if(fillDlg.getReturnCode() == 0){
								((Button)e.widget).setImage(ResourceManager.getPluginImage("Star", "icons/"+fillDlg.getChosenPattern()+".png"));
//								System.out.println(fillDlg.getChosenPattern());
								((Button)e.widget).setData("SELECTED", fillDlg.getChosenPattern());
							}
						}
						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}

					};
				}
				Button button = new Button(mainTable,SWT.NONE);

				TableEditor editor = new TableEditor(mainTable);
				editor.grabHorizontal = editor.grabVertical = true;
				editor.setEditor(button, newItem, i);
				newEditors[i] = editor;
				button.setImage(ResourceManager.getPluginImage("Star", "icons/"+defaultImageSelected+".png"));
				button.setData("SELECTED",defaultImageSelected);
				button.addSelectionListener(defaultSelectionListener);

				newRowItem[i] = button;					
				break;
			}
			case ROW_COLOR_PICKER: {
				
				Color defaultColorSelected;
				SelectionListener defaultSelectionListener = null;
				if(items[i] instanceof RowEntityModel){
					RowEntityModel rowModel = (RowEntityModel) items[i];
					defaultColorSelected = (Color) rowModel.value;
					if(rowModel.selectionListener != null){
						defaultSelectionListener = rowModel.selectionListener;
					}
				
				}
				else{
					defaultColorSelected = (Color) items[i];
					defaultSelectionListener = new SelectionListener(){

						@Override
						public void widgetSelected(SelectionEvent e) {
							ColorDialog dlg = new ColorDialog(Display.getCurrent().getActiveShell());

						
							dlg.setText("Choose a Color");
							dlg.setRGB((RGB)e.widget.getData("SELECTED"));
							// Open the dialog and retrieve the selected color
							RGB rgb = dlg.open();
							if (rgb != null) {
								Color color = new Color (Display.getCurrent(),rgb);
								
								
								((Button)e.widget).setImage(createImage(Display.getCurrent(), color, 100));
								((Button)e.widget).setData("SELECTED", rgb);
								
							}

						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
							// TODO Auto-generated method stub

						}

					};
				}
				
				Button button = new Button(mainTable,SWT.NONE);

				TableEditor editor = new TableEditor(mainTable);
				
				editor.grabHorizontal = editor.grabVertical = true;
				editor.setEditor(button, newItem, i);
				newEditors[i] = editor;
				button.setImage(createImage(Display.getCurrent(), defaultColorSelected, 100));
				button.setData("SELECTED", defaultColorSelected.getRGB());
				button.addSelectionListener(defaultSelectionListener);
				newRowItem[i] = button;
				break;

			}
			case ROW_SPINNER: {

				
				
				
				Spinner spinner = new Spinner(mainTable,SWT.NONE);
				if(items[i] instanceof RowEntityModel){
					RowEntityModel rowModel = (RowEntityModel)items[i];
					spinner.setSelection((Integer)rowModel.value);
					spinner.setMaximum(rowModel.max_value);
					spinner.setMinimum(rowModel.min_value);
					spinner.setDigits(rowModel.decimal);
					
					if(rowModel.modifyListener != null) spinner.addModifyListener(rowModel.modifyListener);
					if(rowModel.keyListener != null) spinner.addKeyListener(rowModel.keyListener);
				}
				else{
					spinner.setSelection((Integer)items[i]);
					
				}
				
				TableEditor editor = new TableEditor(mainTable);
				editor.grabHorizontal = editor.grabVertical = true;
				editor.setEditor(spinner, newItem, i);
				newRowItem[i] = spinner;
				newEditors[i] = editor;
				
			
				break;
			}
			
		case ROW_COMBO: {
				
				CCombo combo = new CCombo(mainTable,SWT.NONE);
				if(items[i] instanceof RowEntityModel){
					RowEntityModel rowModel = (RowEntityModel)items[i];
					combo.setItems((String[]) rowModel.value);
					
					if(rowModel.modifyListener != null) combo.addModifyListener(rowModel.modifyListener);
					if(rowModel.selectionListener != null) combo.addSelectionListener(rowModel.selectionListener);
				}
				else{
					combo.setText((String)items[i]);
					
				}
				
				TableEditor editor = new TableEditor(mainTable);
				editor.grabHorizontal = editor.grabVertical = true;
				editor.setEditor(combo, newItem, i);
				newRowItem[i] = combo;
				newEditors[i] = editor;
				
			
				break;
			}

			}

		}
		tableData.add(newRowItem);
		tableEditors.add(newEditors);
	}

	public void deleteRow(int rowNum){
		
		for(TableEditor editor : tableEditors.get(rowNum)){
			if(editor != null)editor.getEditor().dispose();
		}
		tableEditors.remove(rowNum);
		mainTable.remove(rowNum);
		tableData.remove(rowNum);
	}
	public ArrayList<String[]> getDataToString(){

		ArrayList<String[]> returnVal = new ArrayList<String[]>();

		for(int i = 0; i < tableData.size(); i++){
			Object[] objectRow = tableData.get(i);
			String[] rowData = new String[ROW_TYPES.size()];

			for(int j = 0; j < ROW_TYPES.size(); j++){
				Object currObjectRow = objectRow[j];
				int TYPE = ROW_TYPES.get(j);
				
				switch(TYPE){
				case ROW_STATIC_TEXT : {
					rowData[j] = (String) currObjectRow;
					break;
				}
				case ROW_TEXT : {
					rowData[j] = ((Text) currObjectRow).getText();

					break;
				}
				case ROW_FILL_TYPE: {
					rowData[j] = ((Button) currObjectRow).getData("SELECTED").toString(); 

					break;
				}
				case ROW_COLOR_PICKER: {
					rowData[j] = ((RGB)((Button) currObjectRow).getData("SELECTED")).toString();
					break;

				}
				case ROW_SPINNER: {
					if(((Spinner)currObjectRow).getDigits() <= 0)
					rowData[j] = String.valueOf(((Spinner) currObjectRow).getSelection()  );
					else{
						Double sprVal =(double) ((Spinner) currObjectRow).getSelection();
						Double digits = ((Spinner) currObjectRow).getDigits() / sprVal;
						
						rowData[j] = String.valueOf(sprVal * digits );
							
					}
					break;
				}
				case ROW_COMBO: {
					rowData[j] = String.valueOf(((CCombo) currObjectRow).getText());
					break;
				}
				case ROW_SYMBOL: {
					rowData[j] = (((Button) currObjectRow).getData("SELECTED")).toString();
					break;
				}
				}
			}
			returnVal.add(rowData);
		}

		return returnVal;
	}
	
	public ArrayList<Object[]> getTableRowsObject(){

		ArrayList<Object[]> returnVal = new ArrayList<Object[]>();

		for(int i = 0; i < tableData.size(); i++){
			Object[] objectRow = tableData.get(i);
			Object[] rowData = new Object[ROW_TYPES.size()];

			for(int j = 0; j < ROW_TYPES.size(); j++){
				Object currObjectRow = objectRow[j];
				rowData[j] =  currObjectRow;
					
			
			}
			returnVal.add(rowData);
		}

		return returnVal;
	}

	private Image createImage(Display display, Color color, int x) {
		Image image = new Image(display, x, x);
		GC gc = new GC(image);
		gc.setBackground(color);
		Rectangle rect = image.getBounds();
		gc.fillRectangle(rect);
		if (color.equals(display.getSystemColor(SWT.COLOR_BLACK))) {
			gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		}
		gc.drawRectangle(rect.x, rect.y, rect.width - 1, rect.height - 1);
		gc.dispose();
		return image;
	}
	
	private Image createImage(Display display, Color color, Image overlayImage, int x, int columnWidth) {
		Image image = new Image(display, x, x);
		GC gc = new GC(image);
		gc.setBackground(color);
		Rectangle rect = image.getBounds();
		gc.fillRectangle(rect);
		if (color.equals(display.getSystemColor(SWT.COLOR_BLACK))) {
			gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		}
		gc.drawRectangle(rect.x, rect.y, rect.width - 1, rect.height - 1);
		int heightAuto =  (SYMBOL_HEIGHT != 0) ? SYMBOL_HEIGHT : (ROW_HEIGHT /2) ;
		System.out.println(columnWidth);
		gc.drawImage(overlayImage, (x / 2) / 2, heightAuto);
		    
		gc.dispose();
		return image;
	
	
	}

}
