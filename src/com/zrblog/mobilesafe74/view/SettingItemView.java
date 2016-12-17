package com.zrblog.mobilesafe74.view;

import com.zrblog.mobilesafe74.R;

import android.R.array;
import android.R.bool;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {
	static String NAMESPECE = "http://schemas.android.com/apk/res/com.zrblog.mobilesafe74";
	private TextView mTv_update;
	private CheckBox mCb_choose;
	private TextView mTv_des;
	private String mDestitle;
	private String mDeson;
	private String mDesoff;

	public SettingItemView(Context context) {
		this(context, null);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		View view = View.inflate(context, R.layout.setting_item_view, this);
		mTv_update = (TextView) findViewById(R.id.tv_update);
		mTv_des = (TextView) findViewById(R.id.tv_des);
		mCb_choose = (CheckBox) findViewById(R.id.cb_choose);
		
		//获取自定义属性，或者原生性属性。
		initAttrs(attrs);
	}

	private void initAttrs(AttributeSet attrs) {
		mDestitle = attrs.getAttributeValue(NAMESPECE, "destitle");
		mDeson = attrs.getAttributeValue(NAMESPECE, "deson");
		mDesoff = attrs.getAttributeValue(NAMESPECE, "desoff");
		//设置标题
		mTv_update.setText(mDestitle);
		mTv_des.setText(mDesoff);
	}

	// 判断SettingItemView是否选中，通过chockbox的状态判断。
	public boolean isChecked() {
		return mCb_choose.isChecked();
	}

	// 设置SettingItemView选中。
	public void setChecked(boolean value) {
		mCb_choose.setChecked(value);
		if (value) {
			mTv_des.setText(mDesoff);
		} else {
			mTv_des.setText(mDeson);
		}
	}

}
