package com.droidstore.reparline.activities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.droidstore.reparline.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MapLocation extends ActionBarActivity implements
		OnMyLocationChangeListener, OnMapLongClickListener, OnClickListener {

	// Atributos
	private GoogleMap map;
	private Marker markerPosition;
	private Geocoder geo;
	private LatLng myLocation;

	private BootstrapButton buttonSaveLocation, buttonCancelLocation;

	// View

	private TextView city, street;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_location);

		// Rescato el mapa
		this.map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		// Rescato los botones
		this.buttonSaveLocation = (BootstrapButton) findViewById(R.id.buttonSaveLocation);
		this.buttonCancelLocation = (BootstrapButton) findViewById(R.id.buttonCancelLocation);

		// Rescato los demas valores
		this.city = (TextView) findViewById(R.id.valueAddressCity);
		this.street = (TextView) findViewById(R.id.valueAddressStreet);

		// Le asigno los eventos
		this.map.setOnMapLongClickListener(this);
		this.map.setOnMyLocationChangeListener(this);
		this.buttonCancelLocation.setOnClickListener(this);
		this.buttonSaveLocation.setOnClickListener(this);

		// Activo le boton de mi localizacion
		this.map.setMyLocationEnabled(true);

		// Instancio el geocode
		this.geo = new Geocoder(this, new Locale("es", "ES"));

	}

	// Metodo que saca como valores el nombre del pueblo y la direccion
	private String[] obtenerValores(LatLng point) {
		String[] valores = new String[2];

		List<Address> address;

		try {
			address = geo.getFromLocation(point.latitude, point.longitude, 1);

			valores[0] = address.get(0).getLocality();
			valores[1] = address.get(0).getAddressLine(0);

			myLocation = point;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return valores;

	}

	@Override
	public void onMyLocationChange(Location arg0) {

		LatLng latLong = new LatLng(arg0.getLatitude(), arg0.getLongitude());

		if (this.markerPosition == null) {

			this.markerPosition = this.map.addMarker(new MarkerOptions()
					.position(latLong).draggable(true));
			String[] valores = this.obtenerValores(latLong);

			this.city.setText(valores[0]);
			this.street.setText(valores[1]);
		}

	}

	@Override
	public void onMapLongClick(LatLng loc) {

		if (this.markerPosition != null) {

			this.markerPosition.setPosition(loc);
			String[] valores = this.obtenerValores(loc);

			this.city.setText(valores[0]);
			this.street.setText(valores[1]);

		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.buttonSaveLocation:

			if (myLocation != null) {
				setResult(
						Activity.RESULT_OK,
						new Intent().putExtra("location", myLocation.latitude
								+ "," + myLocation.longitude));
				finish();
			} else
				Crouton.makeText(this,
						getString(R.string.serviceLocationUnavailable),
						Style.ALERT).show();
			break;

		case R.id.buttonCancelLocation:
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;

		}

	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
}
