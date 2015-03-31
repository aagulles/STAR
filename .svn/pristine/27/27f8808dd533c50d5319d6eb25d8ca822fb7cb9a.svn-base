package org.irri.breedingtool.graphs.managers;

import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;

public class RowEntityModel{
	public int type;
	public Object value;
	public int max_value;
	public int min_value;
	public SelectionListener selectionListener;
	public ModifyListener modifyListener;
	public KeyListener keyListener;
	public int decimal = 0;
	public RowEntityModel(int type, Object value, int min, int max){
		this.type = type;
		this.value = value;
		max_value = max;
		min_value = min;
	}
	public RowEntityModel(int type, Object value, int min, int max,int dec){
		this.type = type;
		this.value = value;
		max_value = max;
		min_value = min;
		decimal = dec;
	}
	public RowEntityModel(int type, Object value){
		this.type = type;
		this.value = value;
	
	}
	public RowEntityModel(int type, Object value, int min, int max, SelectionListener selectionListener, ModifyListener modifyListener, KeyListener keyListener){
		this.type = type;
		this.value = value;
		max_value = max;
		min_value = min;
		this.selectionListener = selectionListener;
		this.modifyListener = modifyListener;
		this.keyListener = keyListener;
	}
	
	public RowEntityModel(int type, Object value, int min, int max,KeyListener keyListener){
		this.type = type;
		this.value = value;
		max_value = max;
		min_value = min;
	
		this.keyListener = keyListener;
	}
	public RowEntityModel(int type, Object value, int min, int max, SelectionListener selectionListener){
		this.type = type;
		this.value = value;
		max_value = max;
		min_value = min;
		this.selectionListener = selectionListener;
	
	}
	public RowEntityModel(int type, Object value, int min, int max, ModifyListener modifyListener){
		this.type = type;
		this.value = value;
		max_value = max;
		min_value = min;
	
		this.modifyListener = modifyListener;
	}
	
	
}

