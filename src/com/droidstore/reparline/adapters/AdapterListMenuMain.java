package com.droidstore.reparline.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.droidstore.reparline.R;

public class AdapterListMenuMain extends ArrayAdapter<String> {

	// Atributos
	private Activity context;
	private String[] list;

	// Views
	private TextView textview;

	public AdapterListMenuMain(Activity context, String[] objects) {
		super(context, R.layout.item_listview_drawer, objects);

		this.context = context;
		this.list = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = context.getLayoutInflater().inflate(
				R.layout.item_listview_drawer, null);

		textview = (TextView) v.findViewById(R.id.nameItemListviewDrawerLayout);

		textview.setText(list[position]);

		if (position == 4 || position == 5) {
			v.setBackgroundResource(R.drawable.selector_button_drawer);
			v.setPadding(45, 20, 20, 20);
			textview.setTextSize(14);

		}

		return v;
	}

}
