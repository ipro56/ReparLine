package com.droidstore.reparline.fragments;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.droidstore.reparline.R;
import com.droidstore.reparline.controllers.UsersManagement;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.NetworkUtils;
import com.droidstore.reparline.utils.PreferencesUtils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class DialogEditPassword extends DialogFragment implements
		OnClickListener {

	// Views
	private BootstrapButton buttonOk, buttonCancel;
	private BootstrapEditText inputPassword, inputRePassword;
	private ProgressDialog dial;
	View v;
	private UpdateValue updateValue;

	public DialogEditPassword() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final Dialog dialog = new Dialog(getActivity());

		dialog.setCanceledOnTouchOutside(false);

		// Le asigno el titulo
		dialog.setTitle(getString(R.string.titleDialogPassword));

		dialog.getWindow().setGravity(Gravity.CENTER);
		v = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_fragment_edit_password, null);

		// Rescato la vista
		this.buttonCancel = (BootstrapButton) v
				.findViewById(R.id.buttonCancelEditPassword);
		this.buttonOk = (BootstrapButton) v
				.findViewById(R.id.buttonOkEditPassword);
		this.inputPassword = (BootstrapEditText) v
				.findViewById(R.id.inputPasswordDialogEditPassword);
		this.inputRePassword = (BootstrapEditText) v
				.findViewById(R.id.inputRePasswordDialogEditPassword);

		// Asigno los eventos
		this.buttonOk.setOnClickListener(this);
		this.buttonCancel.setOnClickListener(this);

		dialog.setContentView(v);

		dialog.getWindow()
				.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

		dialog.show();

		return dialog;

	}

	@Override
	public void onPause() {
		super.onPause();

		if (dial != null) {
			dial.dismiss();
		}

		if (getDialog() != null) {
			getDialog().dismiss();
		}

		dial = null;
	}

	@Override
	public void onResume() {
		super.onResume();

		if (this.updateValue != null) {
			if (this.updateValue.getStatus() == AsyncTask.Status.RUNNING) {
				dial = ProgressDialog.show(getActivity(),
						getString(R.string.titleProgress),
						getString(R.string.progressDialogPassword));
			}
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.buttonOkEditPassword:

			if (this.inputPassword.length() > 0) {
				if (this.inputPassword.getText().toString()
						.equals(this.inputRePassword.getText().toString())) {
					dial = ProgressDialog.show(getActivity(),
							getString(R.string.titleProgress),
							getString(R.string.progressDialogPassword));
					this.updateValue = new UpdateValue();
					this.updateValue.execute();
				} else {
					Crouton.makeText(getActivity(),
							getString(R.string.errorPassword), Style.ALERT,
							(ViewGroup) this.v).show();
					this.inputPassword.setText("");
					this.inputRePassword.setText("");
				}
			} else {
				Crouton.makeText(getActivity(),
						getString(R.string.errorPassword2), Style.ALERT,
						(ViewGroup) this.v).show();
			}

			break;
		case R.id.buttonCancelEditPassword:
			this.dismiss();
			break;
		}
	}

	private class UpdateValue extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {

			if (NetworkUtils.networkAvailable(getActivity())) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {

					boolean state = false;
					try {
						state = UsersManagement.changePassword(PreferencesUtils
								.getValueOfPreferences(getActivity(),
										"Reparline", "nameuser"), inputPassword
								.getText().toString());

						if (state)
							return 0;
						else
							return 4;
					} catch (ConnectTimeoutException e) {
						e.printStackTrace();
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

			if (dial != null) {
				dial.dismiss();
			}

			switch (result) {
			case 0:
				if (getDialog() != null) {
					getDialog().dismiss();

				}

				Crouton.makeText(getActivity(),
						getString(R.string.passwordChange), Style.CONFIRM,
						(ViewGroup) getView()).show();
				break;
			case 1:

				DialogUtils.launchNetworkDialog(getActivity());

				break;
			case 2:

				DialogUtils.launchServerDialog(getActivity());

				break;
			case 3:

				DialogUtils.launchTimeDialog(getActivity());
				break;

			case 4:

				Crouton.makeText(getActivity(),
						getString(R.string.errorServer), Style.ALERT,
						(ViewGroup) getView()).show();
				break;
			}

			if (dial != null) {
				dial.dismiss();
			}
		}
	}

}