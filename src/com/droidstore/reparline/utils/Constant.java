package com.droidstore.reparline.utils;

import java.io.File;

import android.os.Environment;

public class Constant {

	public static final String __BASEURL = "http://reparline.gopagoda.com";

	public static final String __USER = "/users?username=";

	public static final String __INCIDENCE = "/incidence";

	public static final String __POST = "/post?id=";

	public static final String __POST_POST = "/post/post";

	public static final String __INCIDENCE_POST = "/incidence/post";

	public static final String __USER_REGISTER = "/users/post";

	public static final String __USER_LOGIN = "/users/login";

	public static final String __USER_UPLOAD = "/users/upload";

	public static final String __USER_FILENAME = "/users/update/filename";

	public static final String __USER_PASSWORD = "/users/update/password";

	public static final String __USER__ADDRESS = "/users/update/address";

	public static final String __USER__UPDATE = "/users/update";

	public static final String __USER_IMAGES = "/images/";

	public static final int DEFAULT_ASPECT_RATIO_VALUES = 100;

	public static final int ROTATE_NINETY_DEGREES = 90;

	public static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";

	public static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";

	public static final String IMG_PATH = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "reparline"
			+ File.separator + "profile" + File.separator;

}
