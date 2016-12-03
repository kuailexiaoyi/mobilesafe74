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
		 * 子线程中判断本地版本号和服务器版本号的大小。 发送消息到主线程。
		 * 
		 * 接收从子线程发送过来的消息。
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UDDATE_VERSION:
				// 跳出提示框，提示更新。
				showUpdateDialog();
				break;
			case ENTRY_HOME:
				// 进入主界面
				entryHome();
				break;
			case URL_ERROR:
				ToastUtils.show(SplashActivity.this, "URL 异常，请检查！");
				entryHome();
				break;
			case IO_ERROR:
				ToastUtils.show(SplashActivity.this, "IO 异常，请检查！");
				entryHome();
				break;
			case JSON_ERROR:
				ToastUtils.show(SplashActivity.this, "JSON 异常，请检查！");
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
		// 第一种去除窗体顶部标题的方式：acitivty去除顶部标题。
		// 第二种方式：在AndroidManifest.xml中设置主题。
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		initUI();
		initDate();

	}

	protected void showUpdateDialog() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示升级").setMessage(mVersionDes)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击确定，开始通过服务器中的下载地址，下载apk文件。
						downloadApk();
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 跳转到主界面。
						entryHome();
					}
				}).show();
	}

	/**
	 * 跳转到主界面。
	 */
	protected void entryHome() {
		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(intent);
	}

	/**
	 * 下载apk文件。
	 */
	protected void downloadApk() {
		// apk下载连接地址。放置apk所在路径。
		// 1.判断sd卡是否可用，是否挂载上。
//		if (Environment.getExternalStorageDirectory().equals(
//				Environment.MEDIA_MOUNTED)) {
//			// 2.获取sd的文件路径。
//			// Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"mobilesafe74.apk";
//			String path = Environment.getExternalStorageDirectory()
//					.getAbsoluteFile() + File.separator + "mobilesafe74.apk";
		String path = Environment.getExternalStorageDirectory()+File.separator+"mobilesafe74.apk";

			// 3.发送请求，获取apk，并且放置到指定路径
			HttpUtils httpUtils = new HttpUtils();
			// 4.发送请求，传递参数，（下载地址，下载应用放置的位置）
			Log.v("paht", path);
			Log.v("mDownloadUrl", mDownloadUrl);
			httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					// 下载成功
					Log.v("TAG", "下载成功！！！");
					File file = responseInfo.result;
					installApk(file);
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.v("TAG", "下载失败！！！");
					// 下载失败
				}

				@Override
				public void onStart() {
					Log.v("TAG", "刚刚开始下载！！！");
					// 开始下载。
					super.onStart();
				}

				// 下载过程中的方法
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					Log.v("TAG", "下载中！！！");
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
		
		//系统应用界面,源码,安装apk入口
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		/*//文件作为数据源
		intent.setData(Uri.fromFile(file));
		//设置安装的类型
		intent.setType("application/vnd.android.package-archive");*/
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
//		startActivity(intent);
		startActivity(intent);		
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
		// 1.获取应用版本名称
		mLocalVersionName = getVersionName();
		mTv_image_splash.setText(mLocalVersionName);
		// 2.获取本地版本号
		mLocalVersionCode = getVersionCode();

		/**
		 * 3.获取服务器版本号。 客户端发送请求，服务发送给响应。（json,xml） 返回200，表示请求成功，已流的方式将数据读取出来。
		 * json内容包括： 更新版本名称 新版本描述信息 服务器版本号。 新版本apk下载地址。
		 * 
		 */
		checkVersion();
		System.out.println("checkVersion()");
	}

	/**
	 * 访问服务器属于耗时操作，所以必须在子现在中运行。 从服务器获取服务器版本号，并判断版本号大于本地版本号，还是小于本地版本号。
	 */
	private void checkVersion() {

		// 第一种方法：
		/*
		 * new Thread(){ public void run(){
		 * 
		 * } };
		 */

		// 第二种方法：
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection;
				Message message = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					// 1.封装url地址
					URL url = new URL("http://192.168.32.60/update74.json");
					System.out.println(url.toString());
					// 2.开启一个连接。
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					// 3.请求超时
					connection.setConnectTimeout(8000);
					// 4.读取超时
					connection.setReadTimeout(8000);
					// 5.设置post请求方式。
					if (connection.getResponseCode() == 200) {
						// 6.以流的方式将，将数据读取下来。
						InputStream is = connection.getInputStream();
						String json = StreamUtils.StreamtoString(is);
						// 7.json数据解析，将字符串类型数据转换为json格式数据。
						JSONObject obj = new JSONObject(json);

						mVersionName = obj.getString("versionName");
						mVersionCode = obj.getString("versionCode");
						mVersionDes = obj.getString("versionDes");
						mDownloadUrl = obj.getString("downloadUrl");

						// Log.v("TAG", mVersionName);
						// Log.v("TAG", mVersionCode);
						// Log.v("TAG", mVersionDes);
						// Log.v("TAG", mDownloadUrl);

						// 8.已经成功获取到了服务器上面apk的版本，就可以和本地的apk版本进行对比了。
						if (Integer.parseInt(mVersionCode) > mLocalVersionCode) {
							// 9.执行提示下载更新操作。这里需要更新UI，但是此处属于子线程，不能更新UI，就需要用到Android中的消息机制。
							message.what = UDDATE_VERSION;
						} else {
							// 10.跳转到主界面。
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
					// 指定睡眠时间，请求网络的时长超过4秒，则不做处理。
					// 如果请求网络的时长小于4秒，强制让其睡眠4秒。
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
	 * 返回版本号。
	 * 
	 * @return
	 */
	private int getVersionCode() {
		// 1.获取包管理者packageManager
		PackageManager pm = getPackageManager();
		// 2.从包管理者对象中，获取指定包名的基本信息（版本名称，版本号），传0表示基本信息。
		try {
			PackageInfo version = pm.getPackageInfo(getPackageName(), 0);
			return version.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取应用的版本名称。
	 */
	private String getVersionName() {
		// 1.获取包管理者packageManager
		PackageManager pm = getPackageManager();
		// 2.从包管理者对象中，获取指定包名的基本信息（版本名称，版本号），传0表示基本信息。
		try {
			PackageInfo version = pm.getPackageInfo(getPackageName(), 0);
			return version.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
