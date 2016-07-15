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
	 * ������������
	 * 
	 * @param _url �̶�url
	 * @param areaId ����id
	 * @param type ��������
	 * @param date �ͻ�������
	 * @param appId �̶�������ͺű�ʶ
	 * @param privateKey ˽Կ
	 * 
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @return �������񷵻�json
	 */
	public String invokeWeatherApiBaseJavaNet(String _url,
			String areaId,
			String type,
			String date,
			String appId,
			String privateKey) throws IOException, InvalidKeyException, NoSuchAlgorithmException{
		
		//��������urlǰ׺
		String urlPre = _url+"?areaid="+areaId+"&type="+type+"&date="+date;
		
		//����urlƴ��appid
		String url = urlPre+"&appid="+appId.substring(0,6);
		
		//ƴpublicKey
		String publicKeyStr=urlPre+"&appid="+appId;
		
		//����key����
		String key = URLEncoder.encode(Base64.encodeBase64String(HMACSHA1.encrypt(publicKeyStr.getBytes(), privateKey.getBytes())));
		
		//����urlƴ��key
		URL uRL=new URL(url+"&key="+key);
		System.out.println("����url��"+uRL);//test be deleted
		
		//����url����
		URLConnection uc = uRL.openConnection();
		
		//����
		uc.connect();
		
		//��ȡ���ӵ�������
		InputStream is = uc.getInputStream();
		
		//��ȡ����reader
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf8"));
		
		//����ֵ����
		StringBuilder retSB = new StringBuilder();
		
		//��ȡ����
		String line;
		while ((line = br.readLine())!=null){
			retSB.append(line);
		}
		br.close();
		is.close();
		
		return retSB.toString();
	}
	
	
	
	
	/**
	 * ������������
	 * 
	 * @param _url �̶�url
	 * @param areaid ����ID
	 * @param type ��������  ����observ��ʵ����
	 * @param date �ͻ�������
	 * @param appid �̶�������ͺű�ʶ
	 * @param privateKey ��Կ
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
		//����urlǰ׺
		String urlPre = _url+"?areaid="+areaid+"&type="+type+"&date="+date;
		
		//��Կ
		String publicKeyStr=urlPre+"&appid="+appid;
		
		//����������key��base64���룬url����
		String key = URLEncoder.encode(Base64.encodeBase64String(HMACSHA1.encrypt(publicKeyStr.getBytes(), privateKey.getBytes())));
		
		//����urlƴ��key
		String requestUrl = urlPre+"&appid="+appid.substring(0,6)+"&key="+key;
		
		/**
		 * ������������
		 */
		HttpClient client = new HttpClient();
		
		HttpMethod method = new GetMethod(requestUrl);
		
		client.executeMethod(method);
		
		String retStr = new String(method.getResponseBody(),"utf8");
		
		return retStr;
	}

}
