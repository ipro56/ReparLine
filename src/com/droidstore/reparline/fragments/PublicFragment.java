package com.droidstore.reparline.fragments;

import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.droidstore.reparline.R;
import com.droidstore.reparline.activities.IncidenceView;
import com.droidstore.reparline.adapters.AdapterListPublic;
import com.droidstore.reparline.controllers.IncidenceManagement;
import com.droidstore.reparline.models.Incidence;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.NetworkUtils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class PublicFragment extends ListFragment implements OnRefreshListener {

	private SwipeRefreshLayout swipeRefresh;

	private AdapterListPublic adapterListPublic;

	private List<Incidence> listIncidence;

	public PublicFragment() {
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(getActivity(), IncidenceView.class);

		Incidence incide = ((Incidence) getListAdapter().getItem(position));
		intent.putExtra("username", incide.getUsername());
		intent.putExtra("description", incide.getDescription());
		intent.putExtra("title", incide.getTitle());
		intent.putExtra("date", incide.getDate());
		intent.putExtra("id", incide.getIncidence_id());

		startActivity(intent);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_public, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Nuevo Elemento: Swipe Refresh Layout
		swipeRefresh = (SwipeRefreshLayout) view
				.findViewById(R.id.swipeRefreshLayout);
		swipeRefresh.setOnRefreshListener(this);

		swipeRefresh.setColorScheme(R.color.blueReparline,
				R.color.redReparline, R.color.greenReparline,
				R.color.yellowReparline);

		if (adapterListPublic == null) {
			onRefresh();
		}

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
							listIncidence = IncidenceManagement
									.getPublicIncidences();

							if (listIncidence != null)
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

				adapterListPublic = new AdapterListPublic(getActivity(),
						listIncidence);
				setListAdapter(adapterListPublic);

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
