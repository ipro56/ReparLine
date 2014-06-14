package com.droidstore.reparline.fragments;

import java.io.IOException;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.droidstore.reparline.R;
import com.droidstore.reparline.activities.MapLocation;
import com.droidstore.reparline.activities.PictureEditor;
import com.droidstore.reparline.controllers.UploadManagement;
import com.droidstore.reparline.controllers.UsersManagement;
import com.droidstore.reparline.models.User;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.ImageLoader;
import com.droidstore.reparline.utils.NetworkUtils;
import com.droidstore.reparline.utils.PreferencesUtils;
import com.google.android.gms.maps.model.LatLng;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class UserFragment extends Fragment implements OnClickListener {

	// View
	private BootstrapButton buttonChangeImage, buttonEditPassword,
			buttonChangeLocation, buttonSaveData;

	private BootstrapEditText inputNameFragmentUser, inputSurnameFragmentUser,
			inputPhoneFragmentUser;

	private ImageView userImage;

	private TextView valueCityLocationUser, valueStreetLocationUser;

	// Atributos
	private ProgressDialog progress;

	public UserFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_user, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Rescato la vista
		this.buttonChangeImage = (BootstrapButton) view
				.findViewById(R.id.buttonChangeImage);
		this.buttonEditPassword = (BootstrapButton) view
				.findViewById(R.id.buttonEditPassword);
		this.userImage = (ImageView) view.findViewById(R.id.userImageProfile);
		this.buttonSaveData = (BootstrapButton) view
				.findViewById(R.id.buttonSaveData);
		this.buttonChangeLocation = (BootstrapButton) view
				.findViewById(R.id.buttonChangeLocation);
		this.inputNameFragmentUser = (BootstrapEditText) view
				.findViewById(R.id.inputNameUserFragment);
		this.inputSurnameFragmentUser = (BootstrapEditText) view
				.findViewById(R.id.inputSurnameUserFragment);
		this.inputPhoneFragmentUser = (BootstrapEditText) view
				.findViewById(R.id.inputPhoneUserFragment);
		this.valueCityLocationUser = (TextView) view
				.findViewById(R.id.valueCityLocation);
		this.valueStreetLocationUser = (TextView) view
				.findViewById(R.id.valueStreetLocation);

		// Asigno los eventos
		this.buttonEditPassword.setOnClickListener(this);
		this.buttonChangeImage.setOnClickListener(this);
		this.buttonSaveData.setOnClickListener(this);
		this.buttonChangeLocation.setOnClickListener(this);

		String nameuser = PreferencesUtils.getValueOfPreferences(getActivity(),
				"Reparline", "nameuser");
		ImageLoader imageLoader = new ImageLoader(getActivity()
				.getApplicationContext());

		imageLoader.clearFileCache();

		imageLoader.DisplayImageProfile(Constant.__BASEURL
				+ Constant.__USER_IMAGES + nameuser + ".png", userImage);

	}

	@Override
	public void onResume() {
		super.onResume();
		if (inputNameFragmentUser.getText().toString().length() == 0) {
			progress = ProgressDialog.show(getActivity(),
					getString(R.string.titleProgress),
					getString(R.string.loading));
			new LoadData().execute();
		}

	}

	@Override
	public void onClick(View v) {

		// Controlo los eventos de los botones
		switch (v.getId()) {
		case R.id.buttonChangeImage:
			new DialogPicture().show(getActivity().getSupportFragmentManager(),
					"Dialog");
			break;
		case R.id.buttonEditPassword:
			new DialogEditPassword().show(getActivity()
					.getSupportFragmentManager(), "dialog");
			break;

		case R.id.buttonChangeLocation:

			getActivity().startActivityForResult(
					new Intent(getActivity(), MapLocation.class), 1);
			break;

		case R.id.buttonSaveData:

			if (inputNameFragmentUser.getText().toString().length() > 0) {
				progress = ProgressDialog.show(getActivity(),
						getString(R.string.titleProgress),
						getString(R.string.updateData));

				new UpdateValue().execute();
			} else {
				Crouton.makeText(getActivity(), getString(R.string.nameVoid),
						Style.ALERT, (ViewGroup) getView()).show();
			}
			break;
		}
	}

	public static class DialogPicture extends DialogFragment {

		public DialogPicture() {

		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setTitle(R.string.dialogMessage);

			builder.setAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, new String[] {
							getResources().getString(R.string.optionGallery),
							getResources().getString(R.string.optionCamera) }),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							Intent intent = new Intent(getActivity(),
									PictureEditor.class);

							if (which == 0) {
								intent.putExtra("gallery", true);
								getActivity().startActivityForResult(intent, 0);
							} else {
								intent.putExtra("gallery", false);
								getActivity().startActivityForResult(intent, 0);
							}
						}
					});

			return builder.create();
		}
	}

	// Controlo el resultado que devuelven los activity
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 0) {
				progress = ProgressDialog.show(getActivity(),
						getString(R.string.titleProgress),
						getString(R.string.UploadFile), true);

				new UploadFile().execute();
			}
			if (requestCode == 1) {

				String location = data.getStringExtra("location");
				progress = ProgressDialog.show(getActivity(),
						getString(R.string.titleProgress),
						getString(R.string.saveLocation), true);

				new UpdateLocation().execute(location);
			}

		}
	}

	private class UploadFile extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {

			if (NetworkUtils.networkAvailable(UserFragment.this.getActivity())) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {

					try {
						boolean state = UploadManagement
								.uploadFile(Constant.IMG_PATH
										+ PreferencesUtils
												.getValueOfPreferences(
														getActivity(),
														"Reparline", "nameuser")
										+ ".png");

						if (state)
							return 0;
						else
							return 3;

					} catch (NullPointerException n) {

						return 5;
					}

				} else {

					return 2;
				}
			} else {
				return 1;

			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		}

		@Override
		protected void onPostExecute(Integer result) {

			if (progress != null) {
				progress.dismiss();
				progress = null;
			}

			switch (result) {
			case 0:

				break;
			case 1:

				DialogUtils
						.launchNetworkDialog(UserFragment.this.getActivity());

				break;
			case 2:
				DialogUtils.launchServerDialog(UserFragment.this.getActivity());

				break;

			case 3:

				Crouton.makeText(getActivity(),
						"Error en el servidor, intentelo de nuevo", Style.ALERT);
			case 4:

				Crouton.makeText(getActivity(),
						getString(R.string.errorServer), Style.ALERT,
						(ViewGroup) getView()).show();
			case 5:
				Crouton.makeText(getActivity(),
						getString(R.string.errorDefault), Style.ALERT,
						(ViewGroup) getView()).show();
				break;

			}
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_SENSOR);

		}
	}

	private class UpdateLocation extends AsyncTask<String, Void, Integer> {

		private LatLng location;

		private Geocoder geo;

		@Override
		protected Integer doInBackground(String... params) {

			if (NetworkUtils.networkAvailable(getActivity())) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {
					boolean state = false;
					try {
						String[] locationString = params[0].split(",");
						this.location = new LatLng(
								Double.parseDouble(locationString[0]),
								Double.parseDouble(locationString[1]));
						state = UsersManagement.changeAddress(PreferencesUtils
								.getValueOfPreferences(getActivity(),
										"Reparline", "nameuser"), params[0]);
						this.geo = new Geocoder(getActivity());

						if (state)
							return 0;
						else
							return 4;
					} catch (ConnectTimeoutException e) {
						e.printStackTrace();
						return 3;
					} catch (NullPointerException n) {

						return 5;
					}

				} else {
					return 2;
				}
			} else {
				return 1;

			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		}

		@Override
		protected void onPostExecute(Integer result) {

			if (progress != null) {
				progress.dismiss();
				progress = null;

			}
			switch (result) {
			case 0:

				List<Address> address;
				try {
					address = geo.getFromLocation(location.latitude,
							location.longitude, 1);
					valueCityLocationUser.setText(address.get(0).getLocality());
					valueStreetLocationUser.setText(address.get(0)
							.getAddressLine(0));

					Crouton.makeText(getActivity(),
							getString(R.string.locationOk), Style.CONFIRM,
							(ViewGroup) getView()).show();
				} catch (IOException e) {

					Crouton.makeText(getActivity(),
							getString(R.string.serviceLocationUnavailable),
							Style.ALERT, (ViewGroup) getView()).show();
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
			case 5:

				Crouton.makeText(getActivity(),
						getString(R.string.errorDefault), Style.ALERT,
						(ViewGroup) getView()).show();
				break;
			}
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_SENSOR);

		}
	}

	private class UpdateValue extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {

			if (NetworkUtils.networkAvailable(getActivity())) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {

					boolean state = false;
					try {
						state = UsersManagement.changeData(PreferencesUtils
								.getValueOfPreferences(getActivity(),
										"Reparline", "nameuser"),
								inputNameFragmentUser.getText().toString(),
								inputSurnameFragmentUser.getText().toString(),
								inputPhoneFragmentUser.getText().toString());

						if (state)
							return 0;
						else
							return 4;
					} catch (ConnectTimeoutException e) {
						e.printStackTrace();
						return 3;
					} catch (NullPointerException n) {

						return 5;
					}

				} else {
					return 2;
				}
			} else {
				return 1;

			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		}

		@Override
		protected void onPostExecute(Integer result) {

			if (progress != null) {
				progress.dismiss();
				progress = null;

			}
			switch (result) {
			case 0:

				Crouton.makeText(getActivity(), getString(R.string.dataOk),
						Style.CONFIRM, (ViewGroup) getView()).show();
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

			case 5:

				Crouton.makeText(getActivity(),
						getString(R.string.errorDefault), Style.ALERT,
						(ViewGroup) getView()).show();
				break;
			}
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_SENSOR);

		}
	}

	private class LoadData extends AsyncTask<Void, Void, Integer> {

		private User user = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			if (NetworkUtils.networkAvailable(getActivity())) {

				if (NetworkUtils.isURLReachable(Constant.__BASEURL + "/users")) {

					try {
						user = UsersManagement.getUser(PreferencesUtils
								.getValueOfPreferences(getActivity(),
										"Reparline", "nameuser"));

						if (user != null)
							return 0;
						else
							return 4;
					} catch (ConnectTimeoutException e) {
						e.printStackTrace();
						return 3;
					} catch (NullPointerException n) {

						return 5;
					}

				} else {
					return 2;
				}
			} else {
				return 1;

			}

		}

		@Override
		protected void onPostExecute(Integer result) {

			if (progress != null) {
				progress.dismiss();
				progress = null;

			}

			switch (result) {
			case 0:

				inputNameFragmentUser.setText(user.getName());
				inputSurnameFragmentUser.setText(user.getSurname());
				inputPhoneFragmentUser.setText(user.getPhone());

				if (user.getAddress().length() > 0) {
					Geocoder geo = new Geocoder(getActivity());

					String[] location = user.getAddress().split(",");

					try {

						LatLng point = new LatLng(
								Double.parseDouble(location[0]),
								Double.parseDouble(location[1]));

						List<Address> address;
						address = geo.getFromLocation(point.latitude,
								point.longitude, 1);

						valueCityLocationUser.setText(address.get(0)
								.getLocality());
						valueStreetLocationUser.setText(address.get(0)
								.getAddressLine(0));
					} catch (IOException e) {

						Crouton.makeText(getActivity(),
								getString(R.string.serviceLocationUnavailable),
								Style.ALERT, (ViewGroup) getView()).show();

					} catch (NumberFormatException n) {
						Crouton.makeText(getActivity(),
								getString(R.string.serviceLocationUnavailable),
								Style.ALERT, (ViewGroup) getView()).show();

					}
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

			case 5:

				Crouton.makeText(getActivity(),
						getString(R.string.errorDefault), Style.ALERT,
						(ViewGroup) getView()).show();
				break;
			}
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_SENSOR);

		}
	}

}
