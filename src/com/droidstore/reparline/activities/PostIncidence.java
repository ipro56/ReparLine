package com.droidstore.reparline.activities;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.droidstore.reparline.R;
import com.droidstore.reparline.controllers.IncidenceManagement;
import com.droidstore.reparline.models.Incidence;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.NetworkUtils;
import com.droidstore.reparline.utils.PreferencesUtils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class PostIncidence extends ActionBarActivity {

	// View
	private BootstrapEditText inputTitleIncidence, inputDescriptionIncidence;

	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_incidence);

		// Rescue the view
		inputTitleIncidence = (BootstrapEditText) findViewById(R.id.inputTitleNewIncidence);
		inputDescriptionIncidence = (BootstrapEditText) findViewById(R.id.inputDescriptionNewIncidence);

	}

	@Override
	public void onResume() {
		super.onResume();

		if (progress != null) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
	}

	public void launchThreadRegister() {
		progress = ProgressDialog.show(this, getString(R.string.titleProgress),
				getString(R.string.registerIncidence));

		new PostIncidenc().execute(new Incidence(inputTitleIncidence.getText()
				.toString(), inputDescriptionIncidence.getText().toString(),
				PreferencesUtils.getValueOfPreferences(this, "Reparline",
						"nameuser"), "1"));
	}

	// Controlo si he pulsado un boton del menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.check) {

			// Si el titulo contiene un tamaño superior a 15 caracteres lo
			// perimto, en cao contrario lo comunico mediante un mensaje de rror
			if (inputTitleIncidence.getText().toString().length() < 15) {
				Crouton.makeText(this, getString(R.string.errorTitleIncidence),
						Style.ALERT).show();
			} else {
				// Tambien compruebo el tamanio de la descripcion si es inferior
				// a 200 no lo permito
				if (inputDescriptionIncidence.getText().toString().length() < 200) {
					Crouton.makeText(this,
							getString(R.string.errorDescriptionIncidence),
							Style.ALERT).show();

				} else {
					// Si todo se cumple lanzo el dialogo de seleccion para
					// seleccionar el tipo de incidencia a registrar
					new DialogTypeIncidence().show(getSupportFragmentManager(),
							"dial");

				}
			}
		} else {
			finish();
		}

		return true;
	}

	private class PostIncidenc extends AsyncTask<Incidence, Void, Integer> {

		@Override
		protected Integer doInBackground(Incidence... params) {

			if (NetworkUtils.networkAvailable(PostIncidence.this)) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {
					try {

						boolean state = IncidenceManagement
								.createIncidencePublic(params[0]);

						if (state)
							return 0;
						else {
							return 4;
						}
					} catch (ConnectTimeoutException c) {
						return 3;
					}

				} else {
					return 2;
				}
			} else {
				return 1;

			}

		}

		@Override
		protected void onPostExecute(Integer result) {

			try {
				if (progress != null) {
					if (progress.isShowing()) {
						progress.dismiss();
					}
				}
			} catch (IllegalArgumentException i) {
			}
			switch (result) {
			case 0:

				Toast.makeText(PostIncidence.this,
						getString(R.string.incidenceOk), Toast.LENGTH_SHORT)
						.show();

				PostIncidence.this.finish();
				break;
			case 1:

				DialogUtils.launchNetworkDialog(PostIncidence.this);

				break;
			case 2:
				DialogUtils.launchServerDialog(PostIncidence.this);

				break;
			case 3:
				DialogUtils.launchTimeDialog(PostIncidence.this);

			case 4:

				Crouton.makeText(PostIncidence.this,
						getString(R.string.errorDefault), Style.ALERT).show();
			}

		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.check, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Dialogo apra seleccionar si lo que quiero registrar es una incidencia o
	// una reparacion
	public static class DialogTypeIncidence extends DialogFragment {

		public DialogTypeIncidence() {
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setTitle(getString(R.string.selectOption));

			builder.setAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, new String[] {
							getString(R.string.publicIncidence),
							getString(R.string.incidenceRepair) }),

			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					switch (which) {
					case 0:

						((PostIncidence) getActivity()).launchThreadRegister();
						getDialog().cancel();
						break;
					case 1:
						getDialog().cancel();
						break;

					}

				}
			});

			return builder.create();
		}

	}
}
