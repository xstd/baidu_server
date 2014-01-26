package com.xstd.activeplugin.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtils {
	
	public static String inputStream2String(InputStream is) {

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i = -1;
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
			return baos.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
}
