/*
 *@(#)com.MD5Test.java
 *
 *Copyright (c) 2014 SIC-CA LTD. All rights reserved. 
 */
package com.kaiyun.until;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {

	
	public static String encode(String str){
		StringBuffer sb = new StringBuffer();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		if (str != null && !"".equals(str)) {
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					sb.append("0");
				sb.append(Integer.toHexString(i));
			}
		}
		return sb.toString();
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String uuid = "5b2563d6-c974-a9bd-d4a1-d84d80020ecc";
		String type = "info";
		String timestamp = String.valueOf(System.currentTimeMillis());
		System.out.println(timestamp);
		System.out.println(MD5Util.encode(uuid+type+timestamp));
	}

}
