package com.zrblog.mobilesafe74.adapter;

import com.zrblog.mobilesafe74.R;
import com.zrblog.mobilesafe74.activity.HomeActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter{
	private String[] strs;
	private int[] imgs;
	private Context context;
	public HomeAdapter(Context context, String[] strs, int[] imgs) {
		this.strs = strs;
		this.imgs = imgs;
		this.context = context;
	}

	@Override
	public int getCount() {
		return strs.length;
	}

	@Override
	public Object getItem(int position) {
		return strs[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.gridview_home, null);
		ImageView iv_home = (ImageView) view.findViewById(R.id.iv_home);
		TextView tv_home = (TextView)view.findViewById(R.id.tv_home);
		iv_home.setImageResource(imgs[position]);
		tv_home.setText(strs[position]);
		return view;
	}

}
