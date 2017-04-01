package com.glogApps.glog;

import com.gordApps.glog.R;
import com.gordApps.glog.R.layout;
import com.gordApps.glog.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CreateZoneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_zone);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_zone, menu);
		return true;
	}

}
