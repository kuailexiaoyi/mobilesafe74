package com.zrblog.mobilesafe74.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Administrator
 *自定义能够获取焦点的TextView。
 */
public class FocusTextView extends TextView {

	//使用在通过java代码创建控件
	public FocusTextView(Context context) {
		super(context);
	}
	//有系统调用（自带属性+上下文环境构造方法）
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//有系统调用（自带属性+上下文环境构造方法+布局文件中红定义样式文件构造方法）
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	//重新获取焦点的方法，由系统调用，调用的时候默认就能获取焦点
	public boolean isFocused(){
		return true;
	}
}
