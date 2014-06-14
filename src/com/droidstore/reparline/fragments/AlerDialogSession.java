package com.droidstore.reparline.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.droidstore.reparline.R;
import com.droidstore.reparline.activities.Main;

// Dialogo emergente lanzado para confirmar que desea cerrar la sesion
public class AlerDialogSession extends DialogFragment {

	public AlerDialogSession() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setMessage(getString(R.string.messageAlertDialogCloseSession))
				.setTitle(getString(R.string.warning))
				.setPositiveButton(getString(R.string.accept),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								((Main) getActivity()).closeSesion();
								dialog.cancel();
							}
						})
				.setNegativeButton(getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();

							}
						});

		return builder.create();
	}
}