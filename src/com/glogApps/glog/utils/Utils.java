package com.glogApps.glog.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;

import com.glogApps.glog.ApplicationLoader;

import android.os.Handler;
import android.text.format.Time;



public class Utils {
	
	public static float density = 1;
	private final static Integer lock = 1;
	public static Handler applicationHandler;
	public static int statusBarHeight = 0;
	
	public static int dp(int value) {
        return (int)(density * value);
    }
	
	static {
        density = ApplicationLoader.applicationContext.getResources().getDisplayMetrics().density;
	}
	
	public static void RunOnUIThread(Runnable runnable) {
        synchronized (lock) {
            applicationHandler.post(runnable);
        }
    }
	
	public static String TimeToTextAgo (Time time){
		
		StringBuilder textAgo = new StringBuilder();
		
		//Time now = new Time();
		//now.setToNow();
		Time now = ISODateToTime(getISODatePhone());
		long difMillis  = now.toMillis(true) - time.toMillis(true);
		
		
		//long years = difMillis / 31556926000;
		
		//long restoYears = difMillis % 31556926000;
		
		long seconds = difMillis / 1000;
		
		long hours = seconds / 3600;
		
		//*********
		//long days = seconds / (24 * 3600);
		//*********
		
		seconds -= hours*3600;
		
		long minutes = seconds / 60;
		
		seconds -= minutes*60;
		
		if (hours > 23)
		{
			textAgo.append(time.format("%d-%m-%Y"));
		}
		else
		{
			
			//textAgo.append("hace ");
			if (hours > 0)
			{
				textAgo.append("hace "+hours + " horas ");
				if (minutes > 0)
					textAgo.append("y "+minutes + " minutos");
			}
			else
			{
				if (minutes < 4)
				{
					textAgo.append("ahora mismo");
				}
				else
				{
					textAgo.append("hace "+minutes + " minutos");
				}
			}
			
			//if (seconds > 0)
			//	textAgo.append(seconds + "s");
		}
		
		
		
		return textAgo.toString();
	}
	public static Time ISODateToTime(String isoDate){
		
		Time date = new Time();
		
		String aux = new String();
		
		//2014-03-15T14:01:26Z --convierte el fomato ISODate a un objeto Time
		
		StringTokenizer tokens = new StringTokenizer(isoDate.trim(), ".");
		
		aux = tokens.nextToken();
		
		aux = aux.replace(':', '-').replace('T', '-');
		
		
		tokens = new StringTokenizer(aux,"-");
			
		int year = Integer.valueOf(tokens.nextToken());
		int month = Integer.valueOf(tokens.nextToken());
		int monthDay = Integer.valueOf(tokens.nextToken());
		int hour = Integer.valueOf(tokens.nextToken());
		int minute = Integer.valueOf(tokens.nextToken());
		int second = Integer.valueOf(tokens.nextToken());
		
		date.set(second, minute, hour, monthDay, month, year);
		
		
		return date;
		
	}
	
	public static String getISODatePhone()

	{

		Time now = new Time();
		now.setToNow();
		
		return now.format("%Y-%m-%dT%H:%M:%S.000Z");

	}
	
	public static JSONArray stringToBinary (String text){
		byte[] bArrText;
	    bArrText = text.getBytes();
	    
	    JSONArray binaryText = new JSONArray();
	    
	    for (int j = 0; j < bArrText.length; j++) {
            
	    	try {
				binaryText.put(j,bArrText[j]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//bArr[j] = (byte) binaryText.getInt(j);
        }
	    return binaryText;
	}
	
	public static String binaryToString (String bText){	
		
		JSONArray binaryText = null;
        byte[] bArr;
        
        try {
			binaryText = new JSONArray(bText);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   
		bArr = new byte[binaryText.length()];
		for (int j = 0; j < binaryText.length(); j++) {
		       
			try {
				bArr[j] = (byte) binaryText.getInt(j);
			} catch (JSONException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
		ByteArrayInputStream bis = new ByteArrayInputStream(bArr);
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		
		try {
			br = new BufferedReader(new InputStreamReader(bis));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return sb.toString();
	}

}

