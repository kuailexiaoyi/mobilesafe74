package com.zrblog.mobilesafe74.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

public class StreamUtils {

	/**
	 * ��ת��Ϊ�ַ�����
	 * 
	 * @param is
	 *            ������
	 * @return ��ת��Ϊ�ַ���������һ���ַ����������ݡ�
	 */
	public static String StreamtoString(InputStream is) {
		// 1.�ڶ�ȡ�Ĺ����У�����ȡ�����ݴ洢�������У�Ȼ��һ����ת��Ϊ�ַ������ء�
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// 2. ��������������û��Ϊֹ��
		byte[] str = new byte[1024];
		// 3.��¼��ȡ���ݵ���ʱ������
		int temp = -1;
		try {
			while ((temp = is.read(str)) != -1) {
				bos.write(str, 0, temp);
			}
			// ���ض�ȡ���ݡ�
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
