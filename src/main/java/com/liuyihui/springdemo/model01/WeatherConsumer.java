package com.liuyihui.springdemo.model01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

public class WeatherConsumer {
	
	/**
	 * 调用天气服务
	 * 
	 * @param _url 固定url
	 * @param areaId 区域id
	 * @param type 数据类型
	 * @param date 客户端日期
	 * @param appId 固定分配的型号标识
	 * @param privateKey 私钥
	 * 
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @return 天气服务返回json
	 */
	public String invokeWeatherApiBaseJavaNet(String _url,
			String areaId,
			String type,
			String date,
			String appId,
			String privateKey) throws IOException, InvalidKeyException, NoSuchAlgorithmException{
		
		//定义请求url前缀
		String urlPre = _url+"?areaid="+areaId+"&type="+type+"&date="+date;
		
		//请求url拼接appid
		String url = urlPre+"&appid="+appId.substring(0,6);
		
		//拼publicKey
		String publicKeyStr=urlPre+"&appid="+appId;
		
		//生成key密文
		String key = URLEncoder.encode(Base64.encodeBase64String(HMACSHA1.encrypt(publicKeyStr.getBytes(), privateKey.getBytes())));
		
		//请求url拼接key
		URL uRL=new URL(url+"&key="+key);
		System.out.println("完整url："+uRL);//test be deleted
		
		//建立url连接
		URLConnection uc = uRL.openConnection();
		
		//连接
		uc.connect();
		
		//获取连接的输入流
		InputStream is = uc.getInputStream();
		
		//获取输入reader
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf8"));
		
		//返回值变量
		StringBuilder retSB = new StringBuilder();
		
		//读取内容
		String line;
		while ((line = br.readLine())!=null){
			retSB.append(line);
		}
		br.close();
		is.close();
		
		return retSB.toString();
	}
	
	
	
	
	/**
	 * 调用天气服务
	 * 
	 * @param _url 固定url
	 * @param areaid 区域ID
	 * @param type 数据类型  例：observ（实况）
	 * @param date 客户端日期
	 * @param appid 固定分配的型号标识
	 * @param privateKey 密钥
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws HttpException
	 * @throws IOException
	 */
	public String invokeWeatherApiBaseHttpClient(String _url,
			String areaid,
			String type,
			String date,
			String appid,
			String privateKey) throws InvalidKeyException, NoSuchAlgorithmException, HttpException, IOException{
		//请求url前缀
		String urlPre = _url+"?areaid="+areaid+"&type="+type+"&date="+date;
		
		//公钥
		String publicKeyStr=urlPre+"&appid="+appid;
		
		//加密生成密key并base64编码，url编码
		String key = URLEncoder.encode(Base64.encodeBase64String(HMACSHA1.encrypt(publicKeyStr.getBytes(), privateKey.getBytes())));
		
		//请求url拼接key
		String requestUrl = urlPre+"&appid="+appid.substring(0,6)+"&key="+key;
		
		/**
		 * 调用天气服务
		 */
		HttpClient client = new HttpClient();
		
		HttpMethod method = new GetMethod(requestUrl);
		
		client.executeMethod(method);
		
		String retStr = new String(method.getResponseBody(),"utf8");
		
		return retStr;
	}

}
