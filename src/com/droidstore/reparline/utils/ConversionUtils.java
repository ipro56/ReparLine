package com.droidstore.reparline.utils;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class ConversionUtils {

	public static String getPathFromUri(Uri uri, Activity context) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, filePathColumn,
				null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		return filePath;
	}

	public static Bitmap decodeUri(Uri selectedImage, Activity context)

	throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(selectedImage), null, o);

		final int REQUIRED_SIZE = 250;

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(selectedImage), null, o2);

	}

	public static String calculateDate(String date) {
		// Crear 2 instancias de Calendar
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		// Establecer las fechas
		cal1.setTime(Timestamp.valueOf(date));
		cal2.setTime(new Date());

		// conseguir la representacion de la fecha en milisegundos, a la fecha 1
		// le sumo 2 horas en milisegundos porque mi servidor tiene 2 horas
		// menos

		long milis1 = cal1.getTimeInMillis() + 7200000;
		long milis2 = cal2.getTimeInMillis();

		// calcular la diferencia en milisengundos
		long diff = milis2 - milis1;

		// calcular la diferencia en segundos
		long diffSeconds = diff / 1000;

		// calcular la diferencia en minutos
		long diffMinutes = diff / (60 * 1000);

		// calcular la diferencia en horas
		long diffHours = diff / (60 * 60 * 1000);

		// calcular la diferencia en dias
		long diffDays = diff / (24 * 60 * 60 * 1000);

		// Calculo la diferencia en semana
		long diffWeek = diff / (604800000);

		// Calculo la diferencia en meses
		long diffMonth = diff / (262800000);
		diffMonth = diffMonth / 10;

		// Calculo la diferencia en año
		long diffYear = diff / (315360000);
		diffYear = diffYear / 100;

		String formatDate = "error";
		if (diffYear > 0) {

			if (diffYear == 1) {
				formatDate = "Hace " + diffYear + " Año";
			} else {
				formatDate = "Hace " + diffYear + " Años";

			}
		} else if (diffMonth > 0) {

			if (diffMonth == 1) {
				formatDate = "Hace " + diffMonth + " Mes";
			} else {
				formatDate = "Hace " + diffMonth + " Meses";

			}
		} else if (diffWeek > 0) {

			if (diffWeek == 1) {
				formatDate = "Hace " + diffWeek + " Semana";
			} else {
				formatDate = "Hace " + diffWeek + " Semanas";

			}
		} else if (diffDays > 0) {

			if (diffDays == 1) {
				formatDate = "Hace " + diffDays + " Dia";
			} else {
				formatDate = "Hace " + diffDays + " Dias";

			}
		} else if (diffHours > 0) {

			if (diffHours == 1) {
				formatDate = "Hace " + diffHours + " Hora";
			} else {
				formatDate = "Hace " + diffHours + " Horas";

			}
		} else if (diffMinutes > 0) {

			if (diffHours == 1) {
				formatDate = "Hace " + diffMinutes + " Minuto";
			} else {
				formatDate = "Hace " + diffMinutes + " Minutos";

			}
		} else if (diffSeconds > 0) {

			if (diffSeconds == 1) {
				formatDate = "Hace " + diffSeconds + " Segundo";
			} else {
				formatDate = "Hace " + diffSeconds + " Segundos";

			}
		}

		return formatDate;
	}
}
