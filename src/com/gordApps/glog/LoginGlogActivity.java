package com.gordApps.glog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LoginGlogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_glog);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_glog, menu);
		return true;
	}

}
