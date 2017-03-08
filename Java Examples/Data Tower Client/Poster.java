package com.runtime;

import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class Poster {
	
	
	
	public int Post(String address, String body){
		
		int success = 0;
		
		HttpPost request = new HttpPost(address);
		
		StringEntity entity = null;
		try {
			entity = new StringEntity(body);
			request.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//client needed to execute request	
		HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
	    HttpConnectionParams.setSoTimeout(httpParams, 20000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		
		int statusCode = 1;
		
		try {
			
			response = client.execute(request);
		
			statusCode = 1;
		
			statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
			
		}
		catch(Exception e){
			
			success = 1;
		}
		
		
		if(statusCode != 200)
			success = 1;
		
		return success;
		
	}
	
	
	
	
	
	
	
	
	public String get(String path){
		
		HttpGet request = new HttpGet(path);
		
		
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		StringEntity entity = null;
		HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
	    HttpConnectionParams.setSoTimeout(httpParams, 20000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		
		request.setHeader("Accept-Encoding","UTF-8");
		
		String result = "";
		
		try {
			//client needed to execute request	
			response = client.execute(request);
			result = IOUtils.toString(response.getEntity().getContent());
		
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
		
		} catch (Exception e1) {
			
			result = "REQUEST FAILED";
			
		}
		
		return result;
		
		
	}
	
	
	
}
