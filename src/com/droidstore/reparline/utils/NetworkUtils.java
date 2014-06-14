package com.droidstore.reparline.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtils {

	private static String url;

	// M�todo que comprueba si una url de un servidor se encuentra disponible
	static public boolean isURLReachable(String url) {

		NetworkUtils.url = url;
		FutureTask<Boolean> future = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {

						try {

							URL url = new URL(NetworkUtils.url);
							HttpURLConnection urlc = (HttpURLConnection) url
									.openConnection();
							urlc.setConnectTimeout(2500);
							urlc.connect();

							if (urlc.getResponseCode() == 200) {
								return true;
							} else {
								return false;

							}

						} catch (MalformedURLException e1) {
							return false;
						} catch (ConnectException e) {
							Log.i("*********", "No");

							return false;
						} catch (IOException e) {
							e.printStackTrace();
							return false;
						}
					}
				});

		ExecutorService exe = Executors.newSingleThreadExecutor();

		exe.execute(future);

		exe.shutdown();

		try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		}

	}

	// M�todo que me comprueba si tengo conexion a internet
	public static boolean networkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectMgr != null) {
			NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
			if (netInfo != null) {
				for (NetworkInfo net : netInfo) {
					if (net.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} else {
			Log.d("NETWORK", "No network available");
		}
		return false;
	}
}
