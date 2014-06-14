package com.droidstore.reparline.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.droidstore.reparline.R;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.ConversionUtils;
import com.droidstore.reparline.utils.DialogUtils;
import com.droidstore.reparline.utils.MemoryUtils;
import com.droidstore.reparline.utils.PreferencesUtils;
import com.edmodo.cropper.CropImageView;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class PictureEditor extends ActionBarActivity implements OnClickListener {
	// Static final constants

	// Instance variables
	private int mAspectRatioX = Constant.DEFAULT_ASPECT_RATIO_VALUES;
	private int mAspectRatioY = Constant.DEFAULT_ASPECT_RATIO_VALUES;

	// Archivo que utilizo para trabajar con la imagen
	private File imageLab;

	// Views
	private BootstrapButton buttonRotateImage, buttonUploadImage;
	private CropImageView cropImageView;

	// Saves the state upon rotating the screen/restarting the activity
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putInt(Constant.ASPECT_RATIO_X, mAspectRatioX);
		bundle.putInt(Constant.ASPECT_RATIO_Y, mAspectRatioY);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	// Restores the state upon rotating the screen/restarting the activity
	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		mAspectRatioX = bundle.getInt(Constant.ASPECT_RATIO_X);
		mAspectRatioY = bundle.getInt(Constant.ASPECT_RATIO_Y);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Compruebo la variable para saber de donde hay que obtener la foto si
		// de la galeria o de la cámara
		if (getIntent().getBooleanExtra("gallery", false))
			// Si es de la galeria lanzo simplmente un intent para seleccionar
			// la foto
			startActivityForResult(
					new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
					99);
		else {

			// Si es de la camara tengo que crear el archivo que utilizare
			// temporalamente para guardarlo en disco
			File root = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "reparline" + File.separator
					+ "laboratorio" + File.separator);
			if (!root.exists()) {
				root.mkdirs();
			}
			imageLab = new File(root, "imagenEditada.jpg");
			startCameraActivity();

		}

		// Aplico la vista
		setContentView(R.layout.activity_picture_editor);

		// Inicializo las variables
		this.cropImageView = (CropImageView) findViewById(R.id.cropImageView);
		this.buttonRotateImage = (BootstrapButton) findViewById(R.id.buttonRotateImage);
		this.buttonUploadImage = (BootstrapButton) findViewById(R.id.buttonUploadImage);

		// Asigno los eventos
		this.buttonRotateImage.setOnClickListener(this);
		this.buttonUploadImage.setOnClickListener(this);

		// Aniado algunas configuraciones
		cropImageView.setAspectRatio(Constant.DEFAULT_ASPECT_RATIO_VALUES,
				Constant.DEFAULT_ASPECT_RATIO_VALUES);
		cropImageView.setFixedAspectRatio(true);
	}

	// Aqui controlo los resultados obtenidos
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Si el resultado es cancelacion pues vuelvo atras sin hacer nada
		if (resultCode == Activity.RESULT_CANCELED) {
			setResult(Activity.RESULT_CANCELED, new Intent(this, Main.class));
			finish();
		} else {
			// Si es distinto compruebo ahora el testigo para saber si vengo de
			// la camara o la galeria
			if (requestCode == 98) {

				Bitmap thumbnail = Bitmap.createScaledBitmap(BitmapFactory
						.decodeFile(this.imageLab.getAbsolutePath()), 400, 300,
						false);

				// Todo esto lo aplico para que no aparezca la imagen en
				// distinto angulo
				ExifInterface exif;
				try {
					exif = new ExifInterface(this.imageLab.getAbsolutePath());

					int orientation = exif.getAttributeInt(
							ExifInterface.TAG_ORIENTATION, 1);
					Matrix matrix = new Matrix();
					if (orientation == 6) {
						matrix.postRotate(90);
					} else if (orientation == 3) {
						matrix.postRotate(180);
					} else if (orientation == 8) {
						matrix.postRotate(270);
					}

					thumbnail = Bitmap.createBitmap(thumbnail, 0, 0,
							thumbnail.getWidth(), thumbnail.getHeight(),
							matrix, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Una vez acabo de hacer las operaciones la imagen la borro
				this.imageLab.delete();

				// Le aplico la imagen que he ledio a la vista
				this.cropImageView.setImageBitmap(thumbnail);
			} else {
				Uri selectedImage = data.getData();

				Bitmap picture = null;
				try {
					picture = ConversionUtils.decodeUri(selectedImage, this);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				this.cropImageView.setImageBitmap(picture);
			}
		}

	}

	// Este metodo se encarga de lanzar el activity de la camara
	protected void startCameraActivity() {

		Uri outputFileUri = Uri.fromFile(imageLab);

		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

		startActivityForResult(intent, 98);
	}

	// Controlo los eventos del metodo On click listener d los botones
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// Si es el boton de rotar simplemente roto la imagen
		case R.id.buttonRotateImage:
			this.cropImageView.rotateImage(Constant.ROTATE_NINETY_DEGREES);
			break;

		// Si es el boton de subir imagen lo que hago es comprobar si tengo
		// memoria suficiente o esta disponible, en caso de estarlo guardo en
		// disco la imagen
		case R.id.buttonUploadImage:
			if (MemoryUtils.isSdAvailable()) {
				MemoryUtils.saveImageToExternalStorage(this.cropImageView
						.getCroppedImage(), PreferencesUtils
						.getValueOfPreferences(this, "Reparline", "nameuser"));
				setResult(Activity.RESULT_OK);
				finish();
			}

			else
				DialogUtils.launchStorage(this);

			break;
		}
	}
}
