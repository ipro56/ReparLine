package com.droidstore.reparline.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.droidstore.reparline.utils.Constant;

public class UploadManagement {

	public static boolean uploadFile(String filePath) {
		final String _filePath = filePath;
		FutureTask<Boolean> f = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// TODO Auto-generated method stub
						// Creamos un objeto File a partri del
						// path del fichero que nos han proporcionado
						File f = new File(_filePath);

						// Creamos el cliente HTTP y la peticion POST
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(Constant.__BASEURL
								+ Constant.__USER_UPLOAD);

						// Esta variable nos servirdor para devolver el JSON
						// recogido
						// como
						// respuesta.
						@SuppressWarnings("unused")
						String strResponse = null;
						try {

							// Este objeto ser??? el que permita insertar el
							// fichero
							// dentro de la petici???n
							MultipartEntityBuilder multipartEntity = MultipartEntityBuilder
									.create();
							multipartEntity
									.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

							// A???adimos el fichero que vamos a enviar.
							// Los argumentos son:
							// - Nombre del par???metro que se envia
							// - Valor del par???metro que se envia (el File f
							// creado al
							// inicio del m???todo)
							// - El tipo de fichero que vamos a enviar. Usamos
							// el
							// gen???rico de flujo de bytes.
							// - El nombre que tendr??? el fichero en el
							// servidor.
							multipartEntity.addBinaryBody("file", f,
									ContentType.APPLICATION_OCTET_STREAM,
									f.getName());

							multipartEntity.setCharset(Charset.forName("UTF-8"));

							// A???adimos a la petici???n la entidad multiparte.
							httppost.setEntity(multipartEntity.build());

							// Ejecutamos la petici???n y recogemos la
							// respuesta.
							HttpResponse response = httpclient
									.execute(httppost);

							Log.e("test", "SC:"
									+ response.getStatusLine().getStatusCode());

							Log.e("ejempplo", response.getStatusLine()
									.getReasonPhrase());

							// Recogemos la respuesta como cadena para poder
							// devolverla

							HttpEntity resEntity = response.getEntity();
							strResponse = EntityUtils.toString(resEntity);

							if (response.getStatusLine().getStatusCode() == 200) {
								return true;
							} else {
								return false;
							}

						} catch (ClientProtocolException e) {
							Log.e("ClientProtocolException", e.getMessage());
							return false;
						} catch (IOException e) {
							Log.e("IOException", e.getMessage());
							return false;
						}
					}

				});

		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(f);

		boolean response = false;

		try {
			response = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}

		return response;

	}
}
