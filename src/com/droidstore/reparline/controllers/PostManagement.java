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

import com.droidstore.reparline.models.Post;
import com.droidstore.reparline.utils.Constant;

public class PostManagement {

	public static List<Post> getPostsOfIncidence(final String id)
			throws ConnectTimeoutException {

		FutureTask<List<Post>> f = new FutureTask<List<Post>>(
				new Callable<List<Post>>() {

					@Override
					public List<Post> call() throws Exception {

						// Cadena obtenida como respuesta
						String[] respStr = UtilsConnections.request(
								UtilsConnections.__GET, Constant.__BASEURL
										+ Constant.__POST + id, null);

						if (respStr[0].equals("200")) {
							// Creo un JSONArray a traves de la repuesta
							// obtenida
							JSONArray responseJSON = new JSONArray(respStr[1]);

							List<Post> posts = new ArrayList<Post>();

							for (int i = 0; i < responseJSON.length(); i++) {

								// Creo un jsonObject a aprtir de la posicion
								// del array donde me encuente
								JSONObject jsonObject = responseJSON
										.getJSONObject(i);

								posts.add(new Post(jsonObject
										.getString("post_id"), jsonObject
										.getString("description"), jsonObject
										.getString("date"), jsonObject
										.getString("username")));
							}

							return posts;
						} else {
							return null;
						}
					}
				});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(f);

		List<Post> respuesta = null;

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

	// Método para registrar un post
	@SuppressWarnings("finally")
	public static boolean createPost(final Post post)
			throws ConnectTimeoutException {
		FutureTask<Boolean> f = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// (1) Transformar el Todo en un JSONObject
						JSONObject object = new JSONObject();

						try {

							object.put("username", post.getUsername());
							object.put("description", post.getDescription());
							object.put("incidence_id", post.getIncidenceId());

							String[] respStr = UtilsConnections.request(
									UtilsConnections.__POST, Constant.__BASEURL
											+ Constant.__POST_POST,
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

		boolean postState = false;

		try {
			postState = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return postState;
		}
	}
}
