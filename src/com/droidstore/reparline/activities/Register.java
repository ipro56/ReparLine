package com.droidstore.reparline.activities;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.droidstore.reparline.R;
import com.droidstore.reparline.controllers.UsersManagement;
import com.droidstore.reparline.models.User;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.NetworkUtils;
import com.droidstore.reparline.utils.PreferencesUtils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Register extends ActionBarActivity implements OnClickListener {

	// Views
	private BootstrapEditText username, name, surname, password, rePassword,
			phone;
	private Button buttonRegister;

	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// Rescue the view
		this.username = (BootstrapEditText) findViewById(R.id.inputUsernameRegister);
		this.name = (BootstrapEditText) findViewById(R.id.inputNameRegister);
		this.surname = (BootstrapEditText) findViewById(R.id.inputSurnameRegister);
		this.password = (BootstrapEditText) findViewById(R.id.inputPasswordRegister);
		this.rePassword = (BootstrapEditText) findViewById(R.id.inputRepasswordRegister);
		this.phone = (BootstrapEditText) findViewById(R.id.inputPhoneRegister);

		this.buttonRegister = (Button) findViewById(R.id.buttonRegister);
		this.progress = new ProgressDialog(this);

		this.buttonRegister.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (progress != null) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
		}
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.buttonRegister) {

			// Compruebo que los datos necesarios no estén vacios
			if (this.username.getText().toString().length() == 0
					|| this.name.getText().toString().length() == 0
					|| this.password.getText().toString().length() == 0) {

				Crouton.makeText(Register.this,
						getString(R.string.errorDataVoid), Style.ALERT).show();

			} else {
				if (this.password.getText().toString()
						.equals(this.rePassword.getText().toString())) {
					progress = ProgressDialog.show(Register.this,
							getString(R.string.titleProgress),
							getString(R.string.registerUser), true);
					User user = new User();

					user.setUserName(this.username.getText().toString());

					user.setName(this.name.getText().toString());

					user.setSurname(this.surname.getText().toString());

					user.setPassword(this.password.getText().toString());

					user.setPhone(this.phone.getText().toString());

					new RegisterUser().execute(user);
				} else {

					Crouton.makeText(Register.this,
							getString(R.string.errorPassword), Style.ALERT)
							.show();
					this.password.setText("");
					this.rePassword.setText("");
				}
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	private class RegisterUser extends AsyncTask<User, Void, Integer> {

		@Override
		protected Integer doInBackground(User... params) {

			if (NetworkUtils.networkAvailable(Register.this)) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {
					try {

						boolean state = UsersManagement.Register(params[0]);

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

				PreferencesUtils
						.savePreferences(Register.this, "Reparline",
								"username", Register.this.username.getText()
										.toString());
				startActivity(new Intent(Register.this, Main.class));
				Register.this.finish();

				break;
			case 1:

				DialogUtils.launchNetworkDialog(Register.this);

				break;
			case 2:
				DialogUtils.launchServerDialog(Register.this);

				break;
			case 3:
				DialogUtils.launchTimeDialog(Register.this);

			case 4:

				Crouton.makeText(Register.this,
						getString(R.string.errorNameuserInUse), Style.ALERT)
						.show();

				Register.this.username.setText("");
			}

		}
	}

}
