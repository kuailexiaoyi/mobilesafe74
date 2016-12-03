package com.zrblog.mobilesafe74.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

public class StreamUtils {

	/**
	 * 流转换为字符串。
	 * 
	 * @param is
	 *            流对象
	 * @return 流转换为字符串，返回一个字符串类型数据。
	 */
	public static String StreamtoString(InputStream is) {
		// 1.在读取的过程中，将读取的内容存储到缓存中，然后一次性转换为字符串返回。
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// 2. 读流操作，读到没有为止。
		byte[] str = new byte[1024];
		// 3.记录读取内容的临时变量。
		int temp = -1;
		try {
			while ((temp = is.read(str)) != -1) {
				bos.write(str, 0, temp);
			}
			// 返回读取数据。
			return bos.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
