/**
 * @Filename: TLinxSHA256.java
 * @Author：caiqf
 * @Date：2016-4-12
 */
package com.ttg.service.park.common.utils.tlinx;

import java.security.MessageDigest;

/**
 * @Class: TLinxSHA256.java
 * @Description: SHA-256编码类
 * @Author：caiqf
 * @Date：2016-4-12
 */
public class TLinxSHA256 {
	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i",
			"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z" };

	public String sha256Encode(String message) {
		return Encode("SHA-256", message);
	}

	private String Encode(String code, String message) {
		String encode = null;
		try {
			encode = byteArrayToHexString(MessageDigest.getInstance(code)
					.digest(message.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encode;
	}

	private String byteArrayToHexString(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		for (byte byt : byteArray)
			sb.append(byteToHexString(byt));

		return sb.toString();
	}

	private String byteToHexString(byte byt) {
		int n = byt;
		if (n < 0)
			n += 256;
		return hexDigits[(n / hexDigits.length)]
				+ hexDigits[(n % hexDigits.length)];
	}
}
