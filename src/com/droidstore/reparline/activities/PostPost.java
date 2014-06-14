package com.droidstore.reparline.activities;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.droidstore.reparline.R;
import com.droidstore.reparline.controllers.PostManagement;
import com.droidstore.reparline.models.Post;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.NetworkUtils;
import com.droidstore.reparline.utils.PreferencesUtils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class PostPost extends ActionBarActivity {

	// View
	private BootstrapEditText inputDescriptionPost;

	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_post);

		// Rescue the view
		inputDescriptionPost = (BootstrapEditText) findViewById(R.id.inputDescriptionPost);

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

	private class RegisterPost extends AsyncTask<Post, Void, Integer> {

		@Override
		protected Integer doInBackground(Post... params) {

			if (NetworkUtils.networkAvailable(PostPost.this)) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {
					try {

						boolean state = PostManagement.createPost(params[0]);

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

				Toast.makeText(PostPost.this, getString(R.string.postOk),
						Toast.LENGTH_SHORT).show();

				PostPost.this.setResult(Activity.RESULT_OK);
				PostPost.this.finish();
				break;
			case 1:

				DialogUtils.launchNetworkDialog(PostPost.this);

				break;
			case 2:
				DialogUtils.launchServerDialog(PostPost.this);

				break;
			case 3:
				DialogUtils.launchTimeDialog(PostPost.this);

			case 4:

				Crouton.makeText(PostPost.this,
						getString(R.string.errorDefault), Style.ALERT).show();
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.check, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.check) {

			if (inputDescriptionPost.getText().toString().length() < 100)
				Crouton.makeText(this,
						getString(R.string.errorDescriptionPost), Style.ALERT)
						.show();

			else {
				String id_incidence = getIntent().getExtras().getString("id");
				String username = PreferencesUtils.getValueOfPreferences(this,
						"Reparline", "nameuser");

				Post p = new Post(id_incidence, inputDescriptionPost.getText()
						.toString(), username);

				progress = ProgressDialog.show(this,
						getString(R.string.titleProgress),
						getString(R.string.registerPost));

				new RegisterPost().execute(p);
			}
		} else {
			finish();
		}
		return true;
	}

}
