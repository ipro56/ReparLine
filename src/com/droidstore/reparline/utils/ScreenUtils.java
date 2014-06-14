package com.droidstore.reparline.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class ScreenUtils {

	public static double getDiagonalInches(Activity context) {

		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		float widthInches = metrics.widthPixels / metrics.xdpi;
		float heightInches = metrics.heightPixels / metrics.ydpi;

		double diagonalInches = Math.sqrt((Math.pow(widthInches, 2) +

		Math.pow(heightInches, 2)));

		return diagonalInches;

	}

	public static Boolean isTablet(Activity context) {

		if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {

			return true;
		}
		return false;
	}
}
