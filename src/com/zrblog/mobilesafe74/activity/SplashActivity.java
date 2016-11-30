package com.zrblog.mobilesafe74.activity;

import com.zrblog.mobilesafe74.R;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private TextView mTv_image_splash;
	private ProgressBar mPb_version_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 第一种去除窗体顶部标题的方式：acitivty去除顶部标题。
		// 第二种方式：在AndroidManifest.xml中设置主题。
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		initUI();
		initDate();

	}

	/**
	 * 获取所需控件，实例化UI
	 */
	private void initUI() {
		mTv_image_splash = (TextView) findViewById(R.id.tv_image_splash);
		mPb_version_name = (ProgressBar) findViewById(R.id.pb_version_name);
	}

	/**
	 * 给控件初始化数据。
	 */
	private void initDate() {
		// 应用版本名称
		getVersionName();

	}

	/**
	 * 获取应用的版本名称。
	 */
	private void getVersionName() {
		// 1.获取包管理者packageManager
		PackageManager pm = getPackageManager();
		// 2.从包管理者对象中，获取指定包名的基本信息（版本名称，版本号），传0表示基本信息。
		try {
			PackageInfo versionName = pm.getPackageInfo(getPackageName(), 0);
			mTv_image_splash.setText("版本名称："+versionName.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
