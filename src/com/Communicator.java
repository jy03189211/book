package com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

/*
 * 
 * 	piaru communications handler häsmäkkä
 * 
 * 
 * 
 */
public class Communicator implements Runnable{
	private Handler handler;
	public final String DatabaseURL = "somethinldgfkljsdfg";
	private URL url;
	
	public Communicator(Handler hand){
		handler = hand;
	}
	
	
	public void run(){
		try {
			JSONObject p = this.getJSON();
			Message msg = this.handler.obtainMessage();
			msg.obj = p;
			msg.what = 0;			
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void setURL(String url){
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 * couchdb-viewin hakeminen
	 */
	public void getView(String viewName, Boolean reduce){
		String url = DatabaseURL +"_design/pieru/_view/" + viewName;		
		if (reduce){
			url += "?group_level=1";
		}		
		setURL(url);
	}  
	/*
	 * 
	 * couchdb-view + avaimen perusteella filtteröinti
	 */
	public void getViewWithKey(String viewName, String key){
		String url = DatabaseURL +"_design/pieru/_view/" + viewName + "?key=\""+key+"\"";
		setURL(url);
	}
	
	/*
	 * 
	 *  id-numeron perusteella haetaan itemi databasesta
	 */	
	public void getItem(String id){
		setURL(DatabaseURL + id);		
	}	
	
	/*
	 * 
	 *  JEE-sonin hakeminen
	 */
	public JSONObject getJSON() throws Exception{		
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		InputStream f = c.getInputStream();
		InputStreamReader k = new InputStreamReader(f);
		BufferedReader in = new BufferedReader(k);
		
		String str="";
		String inStr;
		
		while ((inStr = in.readLine()) != null) {
			str = str + inStr;
		}
		
		in.close();
		c.disconnect();
				
		JSONObject result = new JSONObject(str);
		
		return result;
	}
}