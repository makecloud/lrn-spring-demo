package com.liuyihui.springdemo.model01;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
/**
 * java实现HMAC_SHA1加密
 * 
 * @author liuyihui
 *
 */
public class HMACSHA1 {
	/**
	 * 加密算法
	 */
	private static final String HMAC_SHA1 = "HmacSHA1";
	
	/**
	 * 
	 * @param src 代加密的字节数据
	 * @param key 密钥
	 * @return 加密后的字节数据
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] encrypt(byte[] src ,byte[] key) throws NoSuchAlgorithmException, InvalidKeyException{
		
		SecretKeySpec signingKey=new SecretKeySpec(key, HMAC_SHA1);
		
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		
		byte[] cipherByte = mac.doFinal(src);
		
		return cipherByte;
	}
	/**
	 * 测试方法
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String publicKey="http://open.weather.com.cn/data/?areaid=101190101&type=index&date=201511130000&appid=382627ecb7964497 ";
		String privateKey="yunge_webapi_data";
		byte[] cipherStr = HMACSHA1.encrypt(publicKey.getBytes(), privateKey.getBytes());
		System.out.println(cipherStr);
	}
}
