/**
 * @Filename: TLinxAESCoder.java
 * @Author：caiqf
 * @Date：2016-4-12
 */
package com.ttg.service.park.common.utils.tlinx;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Class: TLinxAESCoder.java
 * @Description: AES加解密类
 * @Author：caiqf
 * @Date：2016-4-12
 */
public class TLinxAESCoder {
	private static String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	private static String KEY_ALGORITHM = "AES";

	public static String decrypt(String sSrc, String sKey) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("ASCII"),
				KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(2, skeySpec);
		byte[] encrypted1 = hex2byte(sSrc);
		byte[] original = cipher.doFinal(encrypted1);
		return new String(original);
	}

	public static String encrypt(String sSrc, String sKey) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("ASCII"),
				KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(1, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());
		return byte2hex(encrypted);
	}

	private static byte[] hex2byte(String strhex) {
		if (strhex == null)
			return null;

		int l = strhex.length();
		if (l % 2 == 1)
			return null;

		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; ++i)
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);

		return b;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}

		return hs.toUpperCase();
	}
}
