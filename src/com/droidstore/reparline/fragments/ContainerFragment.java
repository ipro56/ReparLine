package com.droidstore.reparline.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droidstore.reparline.R;
import com.droidstore.reparline.utils.PreferencesUtils;

public class ContainerFragment extends Fragment {

	public static PublicFragment publicFragment = new PublicFragment();
	public static UserFragment userFragment = new UserFragment();

	public ContainerFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_container_fragment, container,
				false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (PreferencesUtils.getValueOfPreferences(getActivity(), "Reparline",
				"needed").equals(""))
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.containerFragment, publicFragment).commit();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public static void setPosition(int position, FragmentActivity a) {

		PreferencesUtils.savePreferences(a, "Reparline", "needed", "n");
		switch (position) {
		case 1:
			a.getSupportFragmentManager().beginTransaction()
					.replace(R.id.containerFragment, publicFragment).commit();
			break;

		case 3:
			a.getSupportFragmentManager().beginTransaction()
					.replace(R.id.containerFragment, userFragment).commit();
			break;
		}

	}
}
