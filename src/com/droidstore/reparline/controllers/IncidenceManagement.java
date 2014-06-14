package com.droidstore.reparline.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.droidstore.reparline.models.Incidence;
import com.droidstore.reparline.utils.Constant;

public class IncidenceManagement {

	public static List<Incidence> getPublicIncidences()
			throws ConnectTimeoutException {

		FutureTask<List<Incidence>> f = new FutureTask<List<Incidence>>(
				new Callable<List<Incidence>>() {

					@Override
					public List<Incidence> call() throws Exception {

						// Cadena obtenida como respuesta
						String[] respStr = UtilsConnections.request(
								UtilsConnections.__GET, Constant.__BASEURL
										+ Constant.__INCIDENCE, null);

						if (respStr[0].equals("200")) {
							// Creo un JSONArray a traves de la repuesta
							// obtenida
							JSONArray responseJSON = new JSONArray(respStr[1]);

							List<Incidence> incidences = new ArrayList<Incidence>();

							for (int i = 0; i < responseJSON.length(); i++) {

								// Creo un jsonObject a aprtir de la posicion
								// del array donde me encuente
								JSONObject jsonObject = responseJSON
										.getJSONObject(i);

								incidences.add(new Incidence(jsonObject
										.getString("incidence_id"), jsonObject
										.getString("title"), jsonObject
										.getString("description"), jsonObject
										.getString("username"), jsonObject
										.getString("date")));
							}

							return incidences;
						} else {
							return null;
						}
					}
				});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(f);

		List<Incidence> respuesta = null;

		try {
			respuesta = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}

		return respuesta;
	}

	// Método para registrar una incidencia
	@SuppressWarnings("finally")
	public static boolean createIncidencePublic(final Incidence incidence)
			throws ConnectTimeoutException {
		FutureTask<Boolean> f = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// (1) Transformar el Todo en un JSONObject
						JSONObject object = new JSONObject();

						try {

							object.put("username", incidence.getUsername());
							object.put("title", incidence.getTitle());
							object.put("description",
									incidence.getDescription());
							object.put("public", incidence.getIsPublic());

							String[] respStr = UtilsConnections.request(
									UtilsConnections.__POST, Constant.__BASEURL
											+ Constant.__INCIDENCE_POST,
									object.toString());

							if (respStr[0].equals("200")) {
								return true;
							} else {
								return false;
							}

						} catch (JSONException e) {
							e.printStackTrace();
							return false;
						}

					}

				});

		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(f);

		boolean incidenceState = false;

		try {
			incidenceState = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return incidenceState;
		}
	}
}
