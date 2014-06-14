/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.droidstore.reparline.fragments;

import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droidstore.reparline.R;
import com.droidstore.reparline.adapters.AdapterListPost;
import com.droidstore.reparline.controllers.PostManagement;
import com.droidstore.reparline.models.Post;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.NetworkUtils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class PostFragment extends ListFragment implements OnRefreshListener {

	// View
	private SwipeRefreshLayout swipeRefresh;

	// Atributos

	private List<Post> listPosts;
	private AdapterListPost adapterListPost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.post_list_fragment, null);

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (adapterListPost == null) {
			onRefresh();
		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Nuevo Elemento: Swipe Refresh Layout
		swipeRefresh = (SwipeRefreshLayout) view
				.findViewById(R.id.swipeRefreshLayout2);
		swipeRefresh.setOnRefreshListener(this);

		swipeRefresh.setColorScheme(R.color.blueReparline,
				R.color.redReparline, R.color.greenReparline,
				R.color.yellowReparline);
	}

	@Override
	public void onRefresh() {
		swipeRefresh.setRefreshing(true);
		new LoadData().execute();

	}

	private class LoadData extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				if (NetworkUtils.networkAvailable(getActivity())) {

					if (NetworkUtils.isURLReachable(Constant.__BASEURL
							+ "/users")) {

						try {
							String id = (getActivity().getIntent().getExtras())
									.getString("id");
							listPosts = PostManagement.getPostsOfIncidence(id);

							if (listPosts != null)
								return 0;
							else
								return 4;
						} catch (ConnectTimeoutException e) {
							e.printStackTrace();
							return 3;
						}

					} else {
						return 2;
					}
				} else {
					return 1;

				}
			} catch (NullPointerException n) {

				return 5;
			}

		}

		@Override
		protected void onPostExecute(Integer result) {

			swipeRefresh.setRefreshing(false);

			switch (result) {
			case 0:
				try {
					adapterListPost = new AdapterListPost(getActivity(),
							listPosts);
					setListAdapter(adapterListPost);
				} catch (NullPointerException e) {
				}

				break;
			case 1:

				DialogUtils.launchNetworkDialog(getActivity());

				break;
			case 2:

				DialogUtils.launchServerDialog(getActivity());

				break;
			case 3:

				DialogUtils.launchTimeDialog(getActivity());
				break;

			case 4:

				Crouton.makeText(getActivity(),
						getString(R.string.errorServer), Style.ALERT,
						(ViewGroup) getView()).show();
				break;

			}

		}
	}

}