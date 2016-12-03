package com.zrblog.mobilesafe74.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zrblog.mobilesafe74.R;
import com.zrblog.mobilesafe74.utils.StreamUtils;
import com.zrblog.mobilesafe74.utils.ToastUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends Activity {

	protected static final int UDDATE_VERSION = 100;
	protected static final int ENTRY_HOME = 101;
	protected static final int URL_ERROR = 102;
	protected static final int JSON_ERROR = 103;
	protected static final int IO_ERROR = 104;
	private TextView mTv_image_splash;
	private ProgressBar mPb_version_name;
	private int mLocalVersionCode;
	private String mLocalVersionName;
	private String mVersionName;
	private String mVersionCode;
	private String mVersionDes;
	private String mDownloadUrl;

	private Handler mHandler = new Handler() {
		/*
		 * ���߳����жϱ��ذ汾�źͷ������汾�ŵĴ�С�� ������Ϣ�����̡߳�
		 * 
		 * ���մ����̷߳��͹�������Ϣ��
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UDDATE_VERSION:
				// ������ʾ����ʾ���¡�
				showUpdateDialog();
				break;
			case ENTRY_HOME:
				// ����������
				entryHome();
				break;
			case URL_ERROR:
				ToastUtils.show(SplashActivity.this, "URL �쳣�����飡");
				entryHome();
				break;
			case IO_ERROR:
				ToastUtils.show(SplashActivity.this, "IO �쳣�����飡");
				entryHome();
				break;
			case JSON_ERROR:
				ToastUtils.show(SplashActivity.this, "JSON �쳣�����飡");
				entryHome();
				break;

			default:
				break;
			}
		}

	};

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

	protected void showUpdateDialog() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��ʾ����").setMessage(mVersionDes)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ���ȷ������ʼͨ���������е����ص�ַ������apk�ļ���
						downloadApk();
					}
				}).setNegativeButton("ȡ��", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ��ת�������档
						entryHome();
					}
				}).show();
	}

	/**
	 * ��ת�������档
	 */
	protected void entryHome() {
		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(intent);
	}

	/**
	 * ����apk�ļ���
	 */
	protected void downloadApk() {
		// apk�������ӵ�ַ������apk����·����
		// 1.�ж�sd���Ƿ���ã��Ƿ�����ϡ�
//		if (Environment.getExternalStorageDirectory().equals(
//				Environment.MEDIA_MOUNTED)) {
//			// 2.��ȡsd���ļ�·����
//			// Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"mobilesafe74.apk";
//			String path = Environment.getExternalStorageDirectory()
//					.getAbsoluteFile() + File.separator + "mobilesafe74.apk";
		String path = Environment.getExternalStorageDirectory()+File.separator+"mobilesafe74.apk";

			// 3.�������󣬻�ȡapk�����ҷ��õ�ָ��·��
			HttpUtils httpUtils = new HttpUtils();
			// 4.�������󣬴��ݲ����������ص�ַ������Ӧ�÷��õ�λ�ã�
			Log.v("paht", path);
			Log.v("mDownloadUrl", mDownloadUrl);
			httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					// ���سɹ�
					Log.v("TAG", "���سɹ�������");
					File file = responseInfo.result;
					installApk(file);
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.v("TAG", "����ʧ�ܣ�����");
					// ����ʧ��
				}

				@Override
				public void onStart() {
					Log.v("TAG", "�ոտ�ʼ���أ�����");
					// ��ʼ���ء�
					super.onStart();
				}

				// ���ع����еķ���
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					Log.v("TAG", "�����У�����");
					Log.i("TAG", "Total"+Long.toString(total));
					Log.i("TAG", "Current"+Long.toString(current));
					super.onLoading(total, current, isUploading);
				}
			});
		}
//	}

	protected void installApk(File file) {
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.addCategory("android.intent.category.DEFAULT");
//		
//		intent.setData(Uri.fromFile(file));
//		
//		intent.setType("application/vnd.android.package-archive");
//		
//		startActivity(intent);
		
		//ϵͳӦ�ý���,Դ��,��װapk���
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		/*//�ļ���Ϊ����Դ
		intent.setData(Uri.fromFile(file));
		//���ð�װ������
		intent.setType("application/vnd.android.package-archive");*/
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
//		startActivity(intent);
		startActivity(intent);		
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
		// 1.��ȡӦ�ð汾����
		mLocalVersionName = getVersionName();
		mTv_image_splash.setText(mLocalVersionName);
		// 2.��ȡ���ذ汾��
		mLocalVersionCode = getVersionCode();

		/**
		 * 3.��ȡ�������汾�š� �ͻ��˷������󣬷����͸���Ӧ����json,xml�� ����200����ʾ����ɹ��������ķ�ʽ�����ݶ�ȡ������
		 * json���ݰ����� ���°汾���� �°汾������Ϣ �������汾�š� �°汾apk���ص�ַ��
		 * 
		 */
		checkVersion();
		System.out.println("checkVersion()");
	}

	/**
	 * ���ʷ��������ں�ʱ���������Ա����������������С� �ӷ�������ȡ�������汾�ţ����жϰ汾�Ŵ��ڱ��ذ汾�ţ�����С�ڱ��ذ汾�š�
	 */
	private void checkVersion() {

		// ��һ�ַ�����
		/*
		 * new Thread(){ public void run(){
		 * 
		 * } };
		 */

		// �ڶ��ַ�����
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection;
				Message message = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					// 1.��װurl��ַ
					URL url = new URL("http://192.168.32.60/update74.json");
					System.out.println(url.toString());
					// 2.����һ�����ӡ�
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					// 3.����ʱ
					connection.setConnectTimeout(8000);
					// 4.��ȡ��ʱ
					connection.setReadTimeout(8000);
					// 5.����post����ʽ��
					if (connection.getResponseCode() == 200) {
						// 6.�����ķ�ʽ���������ݶ�ȡ������
						InputStream is = connection.getInputStream();
						String json = StreamUtils.StreamtoString(is);
						// 7.json���ݽ��������ַ�����������ת��Ϊjson��ʽ���ݡ�
						JSONObject obj = new JSONObject(json);

						mVersionName = obj.getString("versionName");
						mVersionCode = obj.getString("versionCode");
						mVersionDes = obj.getString("versionDes");
						mDownloadUrl = obj.getString("downloadUrl");

						// Log.v("TAG", mVersionName);
						// Log.v("TAG", mVersionCode);
						// Log.v("TAG", mVersionDes);
						// Log.v("TAG", mDownloadUrl);

						// 8.�Ѿ��ɹ���ȡ���˷���������apk�İ汾���Ϳ��Ժͱ��ص�apk�汾���жԱ��ˡ�
						if (Integer.parseInt(mVersionCode) > mLocalVersionCode) {
							// 9.ִ����ʾ���ظ��²�����������Ҫ����UI�����Ǵ˴��������̣߳����ܸ���UI������Ҫ�õ�Android�е���Ϣ���ơ�
							message.what = UDDATE_VERSION;
						} else {
							// 10.��ת�������档
							message.what = ENTRY_HOME;

						}

					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					message.what = URL_ERROR;
				} catch (IOException e) {
					e.printStackTrace();
					message.what = IO_ERROR;
				} catch (JSONException e) {
					e.printStackTrace();
					message.what = JSON_ERROR;
				} finally {
					// ָ��˯��ʱ�䣬���������ʱ������4�룬��������
					// ������������ʱ��С��4�룬ǿ������˯��4�롣
					long endTime = System.currentTimeMillis();
					if ((endTime - startTime) < 4000) {
						try {
							Thread.sleep(4000 - (endTime - startTime));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(message);
				}
			}
		}).start();
	}

	/**
	 * ���ذ汾�š�
	 * 
	 * @return
	 */
	private int getVersionCode() {
		// 1.��ȡ��������packageManager
		PackageManager pm = getPackageManager();
		// 2.�Ӱ������߶����У���ȡָ�������Ļ�����Ϣ���汾���ƣ��汾�ţ�����0��ʾ������Ϣ��
		try {
			PackageInfo version = pm.getPackageInfo(getPackageName(), 0);
			return version.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * ��ȡӦ�õİ汾���ơ�
	 */
	private String getVersionName() {
		// 1.��ȡ��������packageManager
		PackageManager pm = getPackageManager();
		// 2.�Ӱ������߶����У���ȡָ�������Ļ�����Ϣ���汾���ƣ��汾�ţ�����0��ʾ������Ϣ��
		try {
			PackageInfo version = pm.getPackageInfo(getPackageName(), 0);
			return version.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
