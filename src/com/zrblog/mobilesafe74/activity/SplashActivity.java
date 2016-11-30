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
		// ��һ��ȥ�����嶥������ķ�ʽ��acitivtyȥ���������⡣
		// �ڶ��ַ�ʽ����AndroidManifest.xml���������⡣
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		initUI();
		initDate();

	}

	/**
	 * ��ȡ����ؼ���ʵ����UI
	 */
	private void initUI() {
		mTv_image_splash = (TextView) findViewById(R.id.tv_image_splash);
		mPb_version_name = (ProgressBar) findViewById(R.id.pb_version_name);
	}

	/**
	 * ���ؼ���ʼ�����ݡ�
	 */
	private void initDate() {
		// Ӧ�ð汾����
		getVersionName();

	}

	/**
	 * ��ȡӦ�õİ汾���ơ�
	 */
	private void getVersionName() {
		// 1.��ȡ��������packageManager
		PackageManager pm = getPackageManager();
		// 2.�Ӱ������߶����У���ȡָ�������Ļ�����Ϣ���汾���ƣ��汾�ţ�����0��ʾ������Ϣ��
		try {
			PackageInfo versionName = pm.getPackageInfo(getPackageName(), 0);
			mTv_image_splash.setText("�汾���ƣ�"+versionName.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
