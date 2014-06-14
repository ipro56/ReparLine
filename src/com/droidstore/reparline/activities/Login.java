package com.droidstore.reparline.activities;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.droidstore.reparline.R;
import com.droidstore.reparline.controllers.UsersManagement;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.NetworkUtils;
import com.droidstore.reparline.utils.PreferencesUtils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Login extends FragmentActivity implements OnClickListener {

	// Views
	private Button enter;
	private TextView linkRegister;
	private EditText username, password;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Rescato la vista
		this.enter = (Button) findViewById(R.id.buttonEnterLogin);
		this.linkRegister = (TextView) findViewById(R.id.createAccountLogin);
		this.username = (EditText) findViewById(R.id.inputUserLogin);
		this.password = (EditText) findViewById(R.id.inputPasswordLogin);

		// Asigno los eventos
		this.enter.setOnClickListener(this);
		this.linkRegister.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.buttonEnterLogin:

			if (username.getText().toString().length() == 0
					|| password.getText().toString().length() == 0) {

				Crouton.makeText(Login.this,
						getString(R.string.errorPasswordandNameuser),
						Style.ALERT).show();
			} else {
				this.dialog = ProgressDialog.show(this,
						getString(R.string.titleProgress),
						getString(R.string.auth));

				new LoginUser().execute();
			}
			break;

		case R.id.createAccountLogin:
			startActivity(new Intent(this, Register.class));
			this.finish();
			break;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	private class LoginUser extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(Void... params) {

			if (NetworkUtils.networkAvailable(Login.this)) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {
					try {

						boolean state = UsersManagement.login(username
								.getText().toString(), password.getText()
								.toString());

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
				if (dialog != null) {
					if (dialog.isShowing()) {
						dialog.dismiss();
					}
				}
			} catch (IllegalArgumentException i) {
			}
			switch (result) {
			case 0:
				PreferencesUtils.savePreferences(Login.this, "Reparline",
						"nameuser", username.getText().toString());
				startActivity(new Intent(Login.this, Main.class));
				Login.this.finish();

				break;
			case 1:

				DialogUtils.launchNetworkDialog(Login.this);

				break;
			case 2:

				DialogUtils.launchServerDialog(Login.this);

				break;
			case 3:

				DialogUtils.launchTimeDialog(Login.this);

			case 4:
				Crouton.makeText(Login.this,
						getString(R.string.errorPasswordandNameuser),
						Style.ALERT).show();

			}

		}
	}

}
