package com.droidstore.reparline.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.droidstore.reparline.utils.PreferencesUtils;

public class First extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (PreferencesUtils.getValueOfPreferences(this, "Reparline",
				"nameuser").equals(""))
			startActivity(new Intent(this, Login.class));
		else
			startActivity(new Intent(this, Main.class));

		finish();

	}

}
