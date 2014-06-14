package com.droidstore.reparline.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class UtilsConnections {

	public static final int __GET = 1;
	public static final int __POST = 2;

	public static String[] request(int tipoPeticion, String url, String json)
			throws ConnectTimeoutException {

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 6000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);

		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		HttpClient httpClient = new DefaultHttpClient(httpParameters);

		HttpUriRequest request = null;

		switch (tipoPeticion) {
		case __GET:
			request = new HttpGet(url);

			break;
		case __POST:
			request = new HttpPost(url);
			((HttpPost) request).setEntity(StringJson2StringEntity(json));
			break;
		default:
			return null;

		}
		request.setHeader("Content-Type", "application/json");

		HttpResponse response = null;
		String[] respStr = new String[2];
		try {
			response = httpClient.execute(request);

			respStr[0] = Integer.toString(response.getStatusLine()
					.getStatusCode());

			respStr[1] = EntityUtils.toString(response.getEntity(), "UTF-8");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return respStr;
	}

	private static StringEntity StringJson2StringEntity(String json) {
		StringEntity stringEntity = null;

		try {
			stringEntity = new StringEntity(json, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return stringEntity;

	}

}
