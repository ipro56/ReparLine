package com.droidstore.reparline.controllers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.droidstore.reparline.models.User;
import com.droidstore.reparline.utils.Constant;

public class UsersManagement {

	// Método para registrar un usuario
	@SuppressWarnings("finally")
	public static boolean Register(final User user)
			throws ConnectTimeoutException {
		FutureTask<Boolean> f = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// (1) Transformar el Todo en un JSONObject
						JSONObject object = new JSONObject();

						try {

							object.put("username", user.getUserName());
							object.put("name", user.getName());
							object.put("surname", user.getSurname());
							object.put("password", user.getPassword());
							object.put("phone", user.getPhone());
							object.put("address", user.getAddress());

							String[] respStr = UtilsConnections.request(
									UtilsConnections.__POST, Constant.__BASEURL
											+ Constant.__USER_REGISTER,
									object.toString());

							Log.e("codigo", respStr[0]);
							Log.e("respuesta", respStr[1]);

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

		boolean usuario = false;

		try {
			usuario = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return usuario;
		}
	}

	@SuppressWarnings("finally")
	public static boolean changePassword(final String username,
			final String password) throws ConnectTimeoutException {
		FutureTask<Boolean> f = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// (1) Transformar el Todo en un JSONObject
						JSONObject object = new JSONObject();

						try {

							object.put("username", username);
							object.put("password", password);

							String[] respStr = UtilsConnections.request(
									UtilsConnections.__POST, Constant.__BASEURL
											+ Constant.__USER_PASSWORD,
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

		boolean usuario = false;

		try {
			usuario = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return usuario;
		}
	}

	@SuppressWarnings("finally")
	public static boolean changeData(final String username, final String name,
			final String surname, final String phone)
			throws ConnectTimeoutException {
		FutureTask<Boolean> f = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// (1) Transformar el Todo en un JSONObject
						JSONObject object = new JSONObject();

						try {

							object.put("username", username);
							object.put("name", name);
							object.put("surname", surname);
							object.put("phone", phone);

							String[] respStr = UtilsConnections.request(
									UtilsConnections.__POST, Constant.__BASEURL
											+ Constant.__USER__UPDATE,
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

		boolean usuario = false;

		try {
			usuario = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return usuario;
		}
	}

	@SuppressWarnings("finally")
	public static boolean changeAddress(final String username,
			final String address) throws ConnectTimeoutException {
		FutureTask<Boolean> f = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// (1) Transformar el Todo en un JSONObject
						JSONObject object = new JSONObject();

						try {

							object.put("username", username);
							object.put("address", address);

							String[] respStr = UtilsConnections.request(
									UtilsConnections.__POST, Constant.__BASEURL
											+ Constant.__USER__ADDRESS,
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

		boolean usuario = false;

		try {
			usuario = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return usuario;
		}
	}

	@SuppressWarnings("finally")
	public static boolean login(final String username, final String password)
			throws ConnectTimeoutException {
		FutureTask<Boolean> f = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// (1) Transformar el Todo en un JSONObject
						JSONObject object = new JSONObject();

						try {

							object.put("username", username);
							object.put("password", password);

							String[] respStr = UtilsConnections.request(
									UtilsConnections.__POST, Constant.__BASEURL
											+ Constant.__USER_LOGIN,
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

		boolean usuario = false;

		try {
			usuario = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return usuario;
		}
	}

	public static User getUser(final String username)
			throws ConnectTimeoutException {

		FutureTask<User> f = new FutureTask<User>(new Callable<User>() {

			@Override
			public User call() throws Exception {

				// Cadena obtenida como respuesta
				String[] respStr = UtilsConnections.request(
						UtilsConnections.__GET, Constant.__BASEURL
								+ Constant.__USER + username, null);

				if (respStr[0].equals("200")) {
					// Creo un JSONObject a traves de la repuesta obtenida
					JSONObject responseJSON = new JSONObject(respStr[1]);

					// Creo un JSONArray que provine del objeto json
					// obtenido anteriormente
					User user = new User();
					user.setName(responseJSON.getString("name"));
					user.setSurname(responseJSON.getString("surname"));
					user.setPhone(responseJSON.getString("phone"));
					user.setAddress(responseJSON.getString("address"));

					return user;
				} else {
					return null;
				}
			}
		});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(f);

		User respuesta = null;

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
}
