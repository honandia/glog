package com.glogApps.glog.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
	
	public static String getDatePhone()

	{

		Time now = new Time();
		now.setToNow();
		//return now.format("%d.%m.%Y %H:%M:%S");
		return now.format("%Y-%d-%mT%H:%M:%S.000Z");

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

