/**
 * @Filename: TLinxCodeTrans.java
 * @Author：caiqf
 * @Date：2016-4-12
 */
package com.ttg.service.park.common.utils.tlinx;

/**
 * @Class: TLinxCodeTrans.java
 * @Description: 字符转码类
 * @Author：caiqf
 * @Date：2016-4-12
 */
public class TLinxCodeTrans {
	public static String string2unicode(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); ++i) {
			if (str.charAt(i) < 256)
				sb.append("\\u00");
			else
				sb.append("\\u");

			sb.append(Integer.toHexString(str.charAt(i)));
		}
		return sb.toString();
	}

	public static String unicode2string(String unicode) {
		String[] asciis = unicode.split("\\\\u");
		String str = asciis[0];
		for (int i = 1; i < asciis.length; ++i) {
			String code = asciis[i];
			str = str + (char) Integer.parseInt(code.substring(0, 4), 16);
			if (code.length() > 4)
				str = str + code.substring(4, code.length());
		}
		return str;
	}
}
