package com.orangelabs.tuya2capi.tuya2cApi.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ZipUtil {
	
	public static byte[] zipBase64(String text) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DeflaterOutputStream deflater = new DeflaterOutputStream(out);
			
			deflater.write(text.getBytes(Charset.defaultCharset()));
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[]{};
	}
	
	private static byte[] unzipBase64(String zipedStr) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InflaterOutputStream Inflater = new InflaterOutputStream(out);
			
			Inflater.write(new BASE64Decoder().decodeBufferToByteBuffer(zipedStr).array());
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[]{};
	}
	
	public static String transferDBByteToString(byte[] byetFromDB) {
		BASE64Encoder base64 = new BASE64Encoder();
		String encryptContent = base64.encode(byetFromDB);
		byte[] unzipedbyte = unzipBase64(encryptContent);
		
		if (unzipedbyte.length > 0) {
			return new String(unzipedbyte,Charset.defaultCharset());
		} else {
			return "";
		}
	}

}
