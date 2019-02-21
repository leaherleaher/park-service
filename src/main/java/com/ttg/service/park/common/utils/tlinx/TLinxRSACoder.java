/**
 * @Filename: TLinxRSACoder.java
 * @Author：caiqf
 * @Date：2016-4-12
 */
package com.ttg.service.park.common.utils.tlinx;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class: TLinxRSACoder.java
 * @Description: RSA加解密类
 * @Author：caiqf
 * @Date：2016-4-12
 */
@SuppressWarnings("all")
public class TLinxRSACoder {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	public static Map<String, Object> initKey() throws Exception {
		Map keyMap = new HashMap(2);
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		keyMap.put("RSAPublicKey", (RSAPublicKey) keyPair.getPublic());
		keyMap.put("RSAPrivateKey", (RSAPrivateKey) keyPair.getPrivate());
		return keyMap;
	}

	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get("RSAPrivateKey");
		return new BASE64Encoder().encodeBuffer(key.getEncoded());
	}

	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get("RSAPublicKey");
		return new BASE64Encoder().encodeBuffer(key.getEncoded());
	}

	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(1, privateKey);

		return cipher.doFinal(data);
	}

	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(2, privateKey);

		return cipher.doFinal(data);
	}

	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(1, publicKey);

		return cipher.doFinal(data);
	}

	public static byte[] decryptByPublicKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(2, publicKey);

		return cipher.doFinal(data);
	}

	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(priKey);
		signature.update(data);

		return new BASE64Encoder().encodeBuffer(signature.sign());
	}

	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(pubKey);
		signature.update(data);

		return signature.verify(new BASE64Decoder().decodeBuffer(sign));
	}

	public static void main(String[] args) {
		try {
			Map keyMap = initKey();
			String publicKey = getPublicKey(keyMap).replace("\r\n", "");
			String privateKey = getPrivateKey(keyMap).replace("\r\n", "");
			System.err.println("公钥： \r" + publicKey);
			System.err.println("私钥： \r" + privateKey);

			System.err.println("私钥加密——公钥解密");
			String inputStr = "RSA非对称加密算法";
			byte[] encodedData = encryptByPrivateKey(inputStr.getBytes(),
					privateKey);
			System.err.println("加密前： " + inputStr);
			byte[] decodedData = decryptByPublicKey(encodedData, publicKey);
			System.err.println("解密后： " + new String(decodedData));

			System.err.println("\n私钥签名——公钥验证签名");
			String sign = sign(encodedData, privateKey).replace("\r\n", "");
			System.err.println("签名： \r" + sign);
			System.err.println("验证： \r" + verify(encodedData, publicKey, sign));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
