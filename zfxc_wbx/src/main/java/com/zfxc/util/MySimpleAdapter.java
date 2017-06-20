package com.zfxc.util;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MySimpleAdapter extends SimpleAdapter {
	public int width;
	public int height;
	public MySimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}
	
	public MySimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to,int width,int height) {
		super(context, data, resource, from, to);
		this.width=width;
		this.height=height;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	     View item = super.getView(position, convertView, parent);
	     item.setLayoutParams(new GridView.LayoutParams(width, height));
	     return item;
	}
}
