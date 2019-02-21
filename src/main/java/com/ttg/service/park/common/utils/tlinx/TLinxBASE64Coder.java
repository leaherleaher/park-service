/**
 * @Filename: TLinxBASE64Coder.java
 * @Author：caiqf
 * @Date：2016-4-12
 */
package com.ttg.service.park.common.utils.tlinx;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Class: TLinxBASE64Coder.java
 * @Description: BASE64加解密类
 * @Author：caiqf
 * @Date：2016-4-12
 */
public class TLinxBASE64Coder {
	public static String encode(String s) {
		try {
			String encodeStr = new BASE64Encoder().encode(s.getBytes());
			return encodeStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String decode(String s) {
		try {
			String decodeStr = new String(new BASE64Decoder().decodeBuffer(s));
			return decodeStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
