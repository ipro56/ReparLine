package com.droidstore.reparline.adapters;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.droidstore.reparline.R;
import com.droidstore.reparline.models.Incidence;
import com.droidstore.reparline.utils.ConversionUtils;

public class AdapterListPublic extends ArrayAdapter<Incidence> {

	// Atributos
	private Activity context;
	private List<Incidence> incidences;

	// Views
	private TextView titleItemPublic, usernameItemPublic, boxItemPublic;

	public AdapterListPublic(Activity context, List<Incidence> incidences) {
		super(context, R.layout.item_listfragment_public, incidences);

		this.context = context;
		this.incidences = incidences;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = context.getLayoutInflater().inflate(
				R.layout.item_listfragment_public, null);

		titleItemPublic = (TextView) v.findViewById(R.id.titleItemPublic);
		usernameItemPublic = (TextView) v
				.findViewById(R.id.nameUserDateItemPublic);
		boxItemPublic = (TextView) v.findViewById(R.id.boxCharacterItemPublic);

		titleItemPublic.setText(incidences.get(position).getTitle());
		usernameItemPublic.setText(context.getString(R.string.autor)
				+ " "
				+ incidences.get(position).getUsername()
				+ "\t\t"
				+ ConversionUtils.calculateDate(incidences.get(position)
						.getDate()));
		boxItemPublic.setText(incidences.get(position).getUsername()
				.substring(0, 1));

		boxItemPublic.setBackgroundColor(context.getResources().getColor(
				generateRandomColor()));
		return v;
	}

	public int generateRandomColor() {
		int valorEntero = (int) Math.floor(Math.random() * (0 - 5 + 1) + 5);

		switch (valorEntero) {
		case 1:

			return R.color.greenReparline;
		case 2:

			return R.color.yellowReparline;
		case 3:

			return R.color.blueReparline;
		case 4:

			return R.color.redReparline;
		default:
			return R.color.redReparline;
		}

	}
}
