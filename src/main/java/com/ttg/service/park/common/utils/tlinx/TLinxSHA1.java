/**
 * @Filename: TLinxSHA1.java
 * @Author：caiqf
 * @Date：2016-4-12
 */
package com.ttg.service.park.common.utils.tlinx;

import java.security.MessageDigest;

/**
 * @Class: TLinxSHA1.java
 * @Description: SHA-1编码类
 * @Author：caiqf
 * @Date：2016-4-12
 */
public class TLinxSHA1 {
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte[] messageDigest = digest.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte message : messageDigest) {
				String shaHex = Integer.toHexString(message & 0xFF);
				if (shaHex.length() < 2)
					hexString.append(0);

				hexString.append(shaHex);
			}
			System.out.println("sha:"+hexString.toString());
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String SHA1(String decript, String charsetName) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes(charsetName));
			byte[] messageDigest = digest.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte message : messageDigest) {
				String shaHex = Integer.toHexString(message & 0xFF);
				if (shaHex.length() < 2)
					hexString.append(0);

				hexString.append(shaHex);
			}
			System.out.println("sha:"+hexString.toString());
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
