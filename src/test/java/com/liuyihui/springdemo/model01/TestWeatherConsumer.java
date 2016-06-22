package com.liuyihui.springdemo.model01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

public class TestWeatherConsumer {
	private WeatherConsumer weatherConsumer=new WeatherConsumer();
	
	@Test
	public void useProp() throws FileNotFoundException, IOException{
		
		Properties prop=new Properties();
		prop.load(this.getClass().getClassLoader().getResourceAsStream("weather.properties"));
		System.out.println(prop.toString());
	}
	@Test
	public void testInvokeWeatherApiBaseJavaNet() throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		String url="http://open.weather.com.cn/data/";
		String areaId="101190101";
		String type="observe";
		String date="201606201000";
		String appId="382627ecb7964497";
		String privateKey="yunge_webapi_data";
		String invokeResult = weatherConsumer.invokeWeatherApiBaseJavaNet(url,areaId,type,date,appId,privateKey);
		System.out.println("testInvokeWeatherApiBaseJavaNet:"+invokeResult);
		
	}
	
	/**
	 * ≤‚ ‘ π”√ commons-httpclient 
	 * 
	 * @throws HttpException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	public void TestInvokeWeatherApiBaseHttpClient() throws HttpException, IOException, InvalidKeyException, NoSuchAlgorithmException{
		String url="http://open.weather.com.cn/data/";
		String areaId="101190101";
		String type="index";
		String date="201606211600";
		String appId="382627ecb7964497";
		String privateKey="yunge_webapi_data";
		String invokeResult = weatherConsumer.invokeWeatherApiBaseHttpClient(url,areaId,type,date,appId,privateKey);
		System.out.println("invokeWeatherApiBaseHttpClient:"+invokeResult);
		
	}
	@Test
	public void testBase64() throws InvalidKeyException, NoSuchAlgorithmException{
		String publicKey="http://open.weather.com.cn/data/?areaid=101190101&type=observe&date=201606201000&appid=382627ecb7964497";
		String privateKey="yunge_webapi_data";
		String base64Result = Base64.encodeBase64String(HMACSHA1.encrypt(publicKey.getBytes(), privateKey.getBytes()));
		System.out.println("base64Result:"+base64Result);
		String urlsuffix = URLEncoder.encode(base64Result);
		System.out.println("urlsuffix:"+urlsuffix);
	}
}
