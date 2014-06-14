package com.droidstore.reparline.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Environment;

public class MemoryUtils {

	public static boolean isSdAvailable() {

		boolean sdAvailable = false;

		// Check the state of the Sd memory
		String estado = Environment.getExternalStorageState();

		if (estado.equals(Environment.MEDIA_MOUNTED)) {
			sdAvailable = true;
		} else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			sdAvailable = false;
		} else {
			sdAvailable = false;
		}
		return sdAvailable;
	}

	@SuppressLint("SimpleDateFormat")
	public static String saveImageToExternalStorage(Bitmap image,
			String filename) {

		File root = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "reparline" + File.separator + "profile"
				+ File.separator);
		if (!root.exists()) {
			root.mkdirs();
		}
		try {
			OutputStream fOut = null;
			File file = new File(root, filename + ".png");

			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();
			fOut = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();

			return file.getAbsolutePath();
		} catch (Exception e) {
			return "";
		}
	}

}
