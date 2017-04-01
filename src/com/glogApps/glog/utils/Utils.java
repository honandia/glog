package com.glogApps.glog.utils;

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
		return now.format("%d.%m.%Y %H:%M:%S");

	}

}

