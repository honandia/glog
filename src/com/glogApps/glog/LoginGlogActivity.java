package com.glogApps.glog;

import com.gordApps.glog.R;
import com.gordApps.glog.R.layout;
import com.gordApps.glog.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginGlogActivity extends Activity {

	TextView nick;
	Button btnLogin;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_glog);
		
		nick = (TextView)findViewById(R.id.txtNick);
		btnLogin = (Button)findViewById(R.id.btnEntrar);
		
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				//Toast.makeText(view.getContext(), "click!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(view.getContext(),ActivityPrincipal.class);
	//			intent.putExtra("USER_NICK", nick.getText().toString().trim());
				view.getContext().startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_glog, menu);
		return true;
	}
	

	
	/*public void loginGlog(){
		
		
		//vamos a la activity principal
		
		Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
		intent.putExtra("USER_NICK", nick.getText().toString().trim());
		getApplicationContext().startActivity(intent);

	}*/

}
