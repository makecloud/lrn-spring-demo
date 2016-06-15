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
	
	private String baseUrlStr="http://open.weather.com.cn/data/?areaid=101190101&type=index&date=201606150000&appid=382627";
	private String publicKey="http://open.weather.com.cn/data/?areaid=101190101&type=index&date=201606150000&appid=382627ecb7964497";
	private String privateKey="yunge_webapi_data";
	
	@Test
	public void useProp() throws FileNotFoundException, IOException{
		
		Properties prop=new Properties();
		prop.load(this.getClass().getClassLoader().getResourceAsStream("weather.properties"));
		System.out.println(prop.toString());
	}
	@Test
	public void baseJavaNet() throws IOException, InvalidKeyException, NoSuchAlgorithmException{
		
		String areaId;
		String type;
		String date;
		String appId;
		
		
		//生成url后缀
		String urlSuffix = URLEncoder.encode(Base64.encodeBase64String(HMACSHA1.encrypt(publicKey.getBytes(), privateKey.getBytes())));
		
		//url拼接密key
		URL url=new URL(baseUrlStr+"&key="+urlSuffix);
		System.out.println("完整url："+url);
		
		//建立url连接
		URLConnection uc = url.openConnection();
		
		//连接
		uc.connect();
		
		//获取连接的输入流
		InputStream is = uc.getInputStream();
		
		//获取输入reader
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf8"));
		
		//读取内容
		String line;
		while ((line = br.readLine())!=null){
			System.out.println(line);
		}
		br.close();
		is.close();
		
	}
	@Test
	public void testBase64() throws InvalidKeyException, NoSuchAlgorithmException{
		String base64Result = Base64.encodeBase64String(HMACSHA1.encrypt(publicKey.getBytes(), privateKey.getBytes()));
		System.out.println(base64Result);
		String urlsuffix = URLEncoder.encode(base64Result);
		System.out.println(urlsuffix);
	}
	/**
	 * 测试使用 commons-httpclient 
	 * 
	 * @throws HttpException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	public void baseHttpClient() throws HttpException, IOException, InvalidKeyException, NoSuchAlgorithmException{
		//加密生成密key并base64编码
		String base64ResultStr = Base64.encodeBase64String(HMACSHA1.encrypt(publicKey.getBytes(), privateKey.getBytes()));
		
		//生成url后缀
		String urlSuffix = URLEncoder.encode(base64ResultStr);
		
		String url = baseUrlStr+"&key="+urlSuffix;
		
		HttpClient httpClient = new HttpClient();
		
		HttpMethod method =  new GetMethod(url);
		
		httpClient.executeMethod(method);
		
		//打印服务器返回的状态
		System.out.println(method.getStatusCode());
		
		String response = new String (method.getResponseBody(),"utf8");
		//打印返回的信息
		System.out.println(response);
		
		//释放连接
		method.releaseConnection();
		
	}
	

	public static void main(String[] args) {
	}
}
