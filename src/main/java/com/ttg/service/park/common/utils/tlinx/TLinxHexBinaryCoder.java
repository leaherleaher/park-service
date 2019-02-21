/**
 * @Filename: TLinxHexBinaryCoder.java
 * @Author：caiqf
 * @Date：2016-7-29
 */
package com.ttg.service.park.common.utils.tlinx;

/**
 * @Class: TLinxHexBinaryCoder.java
 * @Description: 二进制与十六进制转换类
 * @Author：caiqf
 * @Date：2016-7-29
 */
public class TLinxHexBinaryCoder {
	public static String hex2binary(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
							hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	public static String binary2hex(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}
}
