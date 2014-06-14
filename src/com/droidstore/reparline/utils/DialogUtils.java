package com.droidstore.reparline.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.droidstore.reparline.R;

public class DialogUtils {
	@SuppressWarnings("deprecation")
	public static void launchNetworkDialog(Activity context) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle("Atención");

		// Setting Dialog Message
		alertDialog.setMessage("No dispone de conexión wifi en estos momentos");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.wifi);

		alertDialog.setButton("Cerrar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	@SuppressWarnings("deprecation")
	public static void launchServerDialog(Activity context) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle("Atención");

		// Setting Dialog Message
		alertDialog.setMessage("El servidor no se encuentra disponible");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.server);

		alertDialog.setButton("Cerrar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	@SuppressWarnings("deprecation")
	public static void launchTimeDialog(Activity context) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle("Atención");

		// Setting Dialog Message
		alertDialog.setMessage("Tiempo de conexión con el servidor agotado");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.time);

		alertDialog.setButton("Cerrar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	@SuppressWarnings("deprecation")
	public static void launchStorage(Activity context) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle("Atención");

		// Setting Dialog Message
		alertDialog
				.setMessage("Memoria de solo lectura o espacio insuficiente");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.storage);

		alertDialog.setButton("Cerrar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
}
