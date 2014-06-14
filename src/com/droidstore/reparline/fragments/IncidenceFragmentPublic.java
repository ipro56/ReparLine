package com.droidstore.reparline.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.droidstore.reparline.R;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.ConversionUtils;
import com.droidstore.reparline.utils.ImageLoader;
import com.droidstore.reparline.utils.PreferencesUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class IncidenceFragmentPublic extends Fragment implements
		OnClickListener {

	// View
	private TextView valueTitleIncidenceFragmentPublic,
			valueDescriptionIncidenceFragmentPublic,
			valueDateIncidenceFragmentPublic,
			valueUsernameIncidenceFragmentPublic;
	private ImageView imageUserIncidenceFragmentPublic;

	private BootstrapButton buttonPrivateIncidence, buttonCloseIncidence;

	public IncidenceFragmentPublic() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_incidence_fragment_public,
				container, false);

		// Rescue the view
		valueTitleIncidenceFragmentPublic = (TextView) v
				.findViewById(R.id.titleIncidence);
		valueDescriptionIncidenceFragmentPublic = (TextView) v
				.findViewById(R.id.descriptionIncidence);
		valueDateIncidenceFragmentPublic = (TextView) v
				.findViewById(R.id.dateIndidence);
		valueUsernameIncidenceFragmentPublic = (TextView) v
				.findViewById(R.id.usernameIncidence);
		imageUserIncidenceFragmentPublic = (ImageView) v
				.findViewById(R.id.userImageIncidence);
		buttonCloseIncidence = (BootstrapButton) v
				.findViewById(R.id.buttonCloseIncidence);
		buttonPrivateIncidence = (BootstrapButton) v
				.findViewById(R.id.buttonPrivateIncidence);

		// events
		buttonCloseIncidence.setOnClickListener(this);
		buttonPrivateIncidence.setOnClickListener(this);

		// Get data from the intent
		Bundle data = getActivity().getIntent().getExtras();

		// Insert the data of the intent into view
		valueTitleIncidenceFragmentPublic.setText(data.getString("title"));
		valueDescriptionIncidenceFragmentPublic.setText(data
				.getString("description"));
		valueDateIncidenceFragmentPublic.setText(ConversionUtils
				.calculateDate(data.getString("date")));

		String nameUser = data.getString("username");

		if (!PreferencesUtils.getValueOfPreferences(getActivity(), "Reparline",
				"nameuser").equalsIgnoreCase(nameUser)) {
			buttonCloseIncidence.setVisibility(View.INVISIBLE);
			buttonPrivateIncidence.setVisibility(View.INVISIBLE);

		}

		valueUsernameIncidenceFragmentPublic.setText(nameUser);

		ImageLoader imageLoader = new ImageLoader(getActivity()
				.getApplicationContext());

		imageLoader.clearFileCache();

		imageLoader.DisplayImageProfile(Constant.__BASEURL
				+ Constant.__USER_IMAGES + nameUser + ".png",
				imageUserIncidenceFragmentPublic);

		return v;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.buttonPrivateIncidence:

			break;
		case R.id.buttonCloseIncidence:
			break;

		}

	}
}
