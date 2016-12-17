package com.zrblog.mobilesafe74.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Administrator
 *�Զ����ܹ���ȡ�����TextView��
 */
public class FocusTextView extends TextView {

	//ʹ����ͨ��java���봴���ؼ�
	public FocusTextView(Context context) {
		super(context);
	}
	//��ϵͳ���ã��Դ�����+�����Ļ������췽����
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//��ϵͳ���ã��Դ�����+�����Ļ������췽��+�����ļ��к춨����ʽ�ļ����췽����
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	//���»�ȡ����ķ�������ϵͳ���ã����õ�ʱ��Ĭ�Ͼ��ܻ�ȡ����
	public boolean isFocused(){
		return true;
	}
}
