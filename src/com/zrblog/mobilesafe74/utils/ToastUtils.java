package com.zrblog.mobilesafe74.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	public static void show(Context context, String str) {
		Toast.makeText(context, str, 0).show();
	}

}
