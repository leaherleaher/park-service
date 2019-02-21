/**
 * @Filename: TLinxMD5Encrypt.java
 * @Author：caiqf
 * @Date：2016-4-12
 */
package com.ttg.service.park.common.utils.tlinx;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Class: TLinxMD5Encrypt.java
 * @Description: MD5加密类
 * @Author：caiqf
 * @Date：2016-4-12
 */
public class TLinxMD5Encrypt {
	public static String md5(String text, String charset) {
		return DigestUtils.md5Hex(getContentBytes(text, charset));
	}

	private static byte[] getContentBytes(String content, String charset) {
		try {
			if ((charset == null) || ("".equals(charset)))
				return content.getBytes();

			return content.getBytes(charset);
		} catch (Exception e) {
		}
		return null;
	}
}
