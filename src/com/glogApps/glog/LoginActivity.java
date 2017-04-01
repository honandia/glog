package com.glogApps.glog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;

import com.glogApps.glog.maps.MyMarker;
import com.glogApps.glog.maps.MyMarkerCustomInfoWindow;
import com.glogApps.glog.models.Comment;
import com.glogApps.glog.models.User;
import com.glogApps.glog.models.UserDB;
import com.glogApps.glog.models.Zone;
import com.glogApps.glog.sqlite.UsuarioDataSource;
import com.glogApps.glog.utils.Utils;
import com.gordApps.glog.R;
import com.gordApps.glog.R.id;
import com.gordApps.glog.R.layout;
import com.gordApps.glog.R.menu;
import com.gordApps.glog.R.string;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {


	

	private String mUser;

	// UI references.
	private EditText mUserView;
	
	private UsuarioDataSource usuarioDS;
	private ArrayList<UserDB> usuarioDB;
	private UserDB usuario;
	
	private User usuarioLogueado;
	
	
	private static final int EXISTE_USUARIO = 1;
	
	
	
	private ImageButton btnGlogLogin;
	private ProgressBar progressBarLogin;
	private TextView textUserLogin, textInfoLogin;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Quitar barra de titulo
		requestWindowFeature(Window.FEATURE_NO_TITLE);
				
		//Poner en pantalla completa
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.activity_login_user);

		progressBarLogin = (ProgressBar)findViewById(R.id.progressBarLogin);
		btnGlogLogin = (ImageButton)findViewById(R.id.btnGlogLogin);
		textUserLogin = (TextView)findViewById(R.id.textUserLogin);
		textInfoLogin = (TextView)findViewById(R.id.textInfoLogin);
		
		usuario = new UserDB();
		usuarioLogueado = User.getInstance(this);
		// comprobamos el usuario ya ha sido almacenado en BBDD
		usuarioDS = new UsuarioDataSource(this);
		
		usuarioDS.open();
		usuarioDB = usuarioDS.getUsuario();
		usuarioDS.close();
		
		
		switch (usuarioDB.size()) {
		case EXISTE_USUARIO:
			
			//obtenemos datos del usuario, creamos el objeto usuario con esos datos 
			//y permitimos hacer login...
			usuario = usuarioDB.get(0);
			usuarioLogueado.setName(usuario.getUsuario());
			textUserLogin.setText(usuarioLogueado.getName());
			textUserLogin.setEnabled(false);
			
			Toast.makeText(LoginActivity.this,"Puedes pulsar para acceder", Toast.LENGTH_SHORT).show();
			break;

		default:
			textUserLogin.setText("");
			textUserLogin.setEnabled(true);
			break;
		}
		
		
	}

	public void comprobarUsuario (View view){
		usuario.setUsuario(textUserLogin.getText().toString().trim());
		
		if(textUserLogin.getText().toString().trim()!="" && !textUserLogin.isEnabled())
		{
			//usuario registrado, accedemos a la aplicación
			Intent intent = new Intent(this,ActivityPrincipal.class);
			this.startActivity(intent);
		}
		else
		{
			showProgress(true);
			
			UserCheckLoginTask tareaCheckLogin = new UserCheckLoginTask();
			tareaCheckLogin.execute();
		}
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	
	private void showProgress(final boolean show) {
		if (show)
		{
			progressBarLogin.setVisibility(View.VISIBLE);
		//	progressBarLogin.animate();
			
			btnGlogLogin.setEnabled(false);
			textInfoLogin.setText("Comprobando datos...");
			textInfoLogin.setEnabled(false);
			textUserLogin.setEnabled(false);
			
		}
		else
		{
			progressBarLogin.setVisibility(View.INVISIBLE);
			btnGlogLogin.setEnabled(true);
			textInfoLogin.setText("Bienvenido");
			textInfoLogin.setEnabled(true);
			textUserLogin.setEnabled(true);
		}
	}

	public class UserCheckLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			
			boolean resul = true;
			

			//showProgress(true);
  			
  			HttpClient httpClient = new DefaultHttpClient();
  	        HttpPut putReq = new HttpPut("http://restapiglog.herokuapp.com/userbyname");
  	        putReq.setHeader("content-type", "application/json");
  	        
	   	    JSONObject dato = new JSONObject();
	   	    try {
	   	    	 dato.put("name", usuario.getUsuario());
	   	    } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	    
	   	    StringEntity entity;
			try {	
				entity = new StringEntity(dato.toString());
				putReq.setEntity(entity);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	     
  	         
  	        try
  	        {
  	                
  	        		JSONArray respJSON = new JSONArray(new String(EntityUtils.toString(httpClient.execute(putReq).getEntity())));
       
  	        		if (respJSON.length()==0)
  	        		{
  	        			//no extiste ese usuario
  	        			resul = false;
  	        		}
  	        		else
  	        		{
  	        			resul = true;
  	        			
  	        			//el usuaario ya existe, se cargan sus datos
	  	               /* for(int i=0; i<respJSON.length(); i++)
	  	                {
	  	                    JSONObject obj = respJSON.getJSONObject(i);
	  	                    
	  	                    
	  	                    if (obj.has("avatar"))
	  	                    	usuarioLogueado.setAvatar(obj.getString("avatar"));                 	
	  	                    else
	  	                    	usuarioLogueado.setAvatar("");
	  	                    if (obj.has("email"))
	  	                    	usuarioLogueado.setEmail(obj.getString("email"));
		                    else
		                    	usuarioLogueado.setEmail("");
	  	                    if (obj.has("facebook"))
	  	                    	usuarioLogueado.setFacebook(obj.getString("facebook"));
	  	                    else
	  	                    	usuarioLogueado.setFacebook("");
	  	                    if (obj.has("twitter"))
	  	                    	usuarioLogueado.setTwitter(obj.getString("twitter"));
	  	                    else
	  	                    	usuarioLogueado.setTwitter("");
	  	                    if (obj.has("web"))
	  	                    	usuarioLogueado.setWeb(obj.getString("web"));
	  	                    else
	  	                    	usuarioLogueado.setWeb("");
	  	                    if (obj.has("phone"))
	  	                    	usuarioLogueado.setPhone(obj.getString("phone"));
	  	                    else
	  	                    	usuarioLogueado.setPhone("");
	  	               }    */     
  	        		}
  	        }
  	        catch(Exception ex)
  	        {
  	                Log.e("ServicioRest","Error!", ex);
  	        }
  	 
  	        return resul;
		}

		@Override
		protected void onPostExecute(final Boolean yaExisteUser) {
			
			showProgress(false);

			
			
			if (yaExisteUser) {
				Toast.makeText(LoginActivity.this,textUserLogin.getText().toString().trim()+" no esta disponible. Prueba otro nombre.", Toast.LENGTH_SHORT).show();
				
			} else {
				//insertamos el nuevo usuario en bbdd local
				usuario.setUsuario(textUserLogin.getText().toString().trim());
				
				usuarioDS.open();
				usuarioDS.crearUsuario(usuario);
				usuarioDS.close();
				
				//tarea insertar usuario en server 
				TareaRAInsertarUser tareaInsertarUser = new TareaRAInsertarUser();
				tareaInsertarUser.execute();
			}
		}

		@Override
		protected void onCancelled() {
			
			showProgress(false);

		}
	}
	
	//*****
	//*****
	private class TareaRAInsertarUser extends AsyncTask<String,Integer,Boolean> {
  		 
  		protected Boolean doInBackground(String... params) {
  	 		
  			boolean resul = true;
  			
  			//showProgress(true);
  			
  			HttpClient httpClient = new DefaultHttpClient();
  	        HttpPost post = new HttpPost("http://restapiglog.herokuapp.com/user");
  	        post.setHeader("content-type", "application/json");
  	        
	   	   //Construimos el objeto del nuevo usuario en formato JSON
	   	     JSONObject dato = new JSONObject(); 	  
	   	     try {
	   	    	 dato.put("name", usuario.getUsuario());
	   	     } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	   	     }
	   	    
	   	    StringEntity entity;
			try {	
				entity = new StringEntity(dato.toString());
				post.setEntity(entity);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
  	        try
  	        {
  	                HttpResponse resp = httpClient.execute(post);
  	                String respStr = EntityUtils.toString(resp.getEntity());
  	         
  	                JSONArray respJSON = new JSONArray(respStr);
      
  	        }
  	        catch(Exception ex)
  	        {
  	                Log.e("ServicioRest","Error!", ex);
  	        }
  	 
  	        return resul;
  	    }
  	 
  	    protected void onPostExecute(Boolean result) {
  	 
  	        if (result)
  	        {
  	        	textUserLogin.setText(usuario.getUsuario().toString().trim());
  	        	usuarioLogueado.setName(usuario.getUsuario().toString().trim());
  	        	showProgress(false);
  	        	textUserLogin.setEnabled(false);
  	        	Toast.makeText(LoginActivity.this,"Usuario creado! Puedes pulsar para acceder", Toast.LENGTH_SHORT).show();
  	        
  	        }
  	    }
  	}
	//*****
	//*****
}
