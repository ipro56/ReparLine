package com.droidstore.reparline.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.droidstore.reparline.R;
import com.droidstore.reparline.fragments.AlerDialogSession;
import com.droidstore.reparline.fragments.ContainerFragment;
import com.droidstore.reparline.fragments.NavigationDrawerFragment;
import com.droidstore.reparline.utils.PreferencesUtils;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class Main extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private AlerDialogSession alertDialogCloseSession;

	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (alertDialogCloseSession != null) {
			alertDialogCloseSession.dismiss();
			alertDialogCloseSession = null;
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if (position == 5) {

			alertDialogCloseSession = new AlerDialogSession();
			alertDialogCloseSession.show(getSupportFragmentManager(),
					"AlertDialogCloseSession");

		} else
			ContainerFragment.setPosition(position, this);

	}

	public void closeSesion() {
		PreferencesUtils.savePreferences(this, "Reparline", "nameuser", "");
		PreferencesUtils.savePreferences(this, "Reparline", "needed", "");

		finish();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.addIncidence:
			startActivity(new Intent(this, PostIncidence.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		PreferencesUtils.savePreferences(this, "Reparline", "needed", "");
		super.onBackPressed();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		ContainerFragment.userFragment.onActivityResult(requestCode,
				resultCode, data);
	}

}
