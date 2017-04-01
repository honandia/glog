package com.glogApps.glog;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.glogApps.glog.models.Nota;
import com.glogApps.glog.utils.DownloadImageTask;
import com.glogApps.glog.utils.Utils;
import com.gordApps.glog.R;



import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends ActionBarActivity {

	private ActionBar actionBar;
	private ProgressDialog mProgressDialog;
	
	private String userId;
	
	private TextView avatar ,email,facebook,twitter,web,phone;
	private ImageView avatarIV ,emailIV ,facebookIV ,twitterIV ,webIV ,phoneIV;
	
	public static final int DIALOG_LOADING_USER_CONTACT_INFO = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Datos del usuario");
		
		Bundle bundle = getIntent().getExtras();
		userId= bundle.getString("USER_ID");
		
		
		((TextView)findViewById(R.id.textUserName)).setText(userId);
		
		avatar = (TextView)findViewById(R.id.textAvatar);
		
		
		
		email = (TextView)findViewById(R.id.textGLogo);
		facebook = (TextView)findViewById(R.id.textFaceBook);
		twitter = (TextView)findViewById(R.id.textTwitter);
		web = (TextView)findViewById(R.id.textWeb);
		phone = (TextView)findViewById(R.id.textPhone);
		
		avatarIV = (ImageView)findViewById(R.id.imageViewOLogo);
      	emailIV = (ImageView)findViewById(R.id.imageViewEmail);
      	facebookIV = (ImageView)findViewById(R.id.ImageViewFacebook);
      	twitterIV = (ImageView)findViewById(R.id.ImageViewTwitter);
      	webIV = (ImageView)findViewById(R.id.ImageViewWeb);
      	phoneIV = (ImageView)findViewById(R.id.ImageViewPhone);
		
		TareaRAObtenerDatosContactoUsuario tarea = new TareaRAObtenerDatosContactoUsuario();
		tarea.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);

		return true;
	}
	
	

	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_LOADING_USER_CONTACT_INFO:
	        mProgressDialog = new ProgressDialog(this);
	        mProgressDialog.setMessage("Cargando datos de contacto del usuario...");
	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        mProgressDialog.setCancelable(false);
	        mProgressDialog.show();
	        return mProgressDialog;
	        
	    default:
	    	
	    return null;
	    }
	}
	
	private class TareaRAObtenerDatosContactoUsuario extends AsyncTask<String,Integer,Boolean> {
  		 
  		 @Override
  		    protected void onPreExecute() {
  		        super.onPreExecute();
  		        showDialog(DIALOG_LOADING_USER_CONTACT_INFO);
  		    }


  		    protected void onProgressUpdate(String... progress) {        
  		    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
  		    }
  		 		
  		protected Boolean doInBackground(String... params) {
  	 		
  			boolean resul = true;
  			
  			HttpClient httpClient = new DefaultHttpClient();
  	        
  	        HttpPut putReq = new HttpPut("http://restapiglog.herokuapp.com/userbyname");
  	         
  	        putReq.setHeader("content-type", "application/json");
  	        
	   	    JSONObject dato = new JSONObject();
	   	  
	   	    try {
	   	    	 dato.put("name", userId.toString());
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
       
  	                for(int i=0; i<respJSON.length(); i++)
  	                {
  	                    JSONObject obj = respJSON.getJSONObject(i);
  	                    
  	                    if (obj.has("avatar"))
  	                    	avatar.setText(obj.getString("avatar"));
  	                    else
  	                    	avatar.setText("");
  	                    
  	                    if (obj.has("email"))
  	                    	email.setText(obj.getString("email"));
	                    else
	                    	email.setText("");
  	                    if (obj.has("facebook"))
  	                    	facebook.setText(obj.getString("facebook"));
  	                    else
  	                    	facebook.setText("");
  	                
  	                    if (obj.has("twitter"))
  	                    	twitter.setText(obj.getString("twitter"));
  	                    else
  	                    	twitter.setText("");
  	                   
  	                    if (obj.has("web"))
  	                    	web.setText(obj.getString("web"));
  	                    else
  	                    	web.setText("");
  	                   
  	                    if (obj.has("phone"))
  	                    	phone.setText(obj.getString("phone"));
  	                    else
  	                    	phone.setText("");
  	               }         
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
  	        	
  	        	//avatar
  	        	if(!avatar.getText().toString().trim().equals(""))
  	        	{
  	        		
  	        		avatarIV.setVisibility(View.VISIBLE);
  	        		new DownloadImageTask(avatarIV).execute(avatar.getText().toString().trim());
  	        
  	        	}
  	        	
  	        	//email
  	        	if(!email.getText().toString().trim().equals(""))
  	        	{
  	        		
  	        		emailIV.setVisibility(View.VISIBLE);
  	        		emailIV.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							new AlertDialog.Builder(v.getContext())
						    .setTitle("Email")
						    .setMessage("¿Enviar un email a "+userId+" ?")
						    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						           
						        	Intent intentEmail = new Intent(Intent.ACTION_SEND);
						        	intentEmail.setType("message/rfc822");
						        	intentEmail.putExtra(Intent.EXTRA_EMAIL  , new String[]{email.getText().toString().trim()});
						        	intentEmail.putExtra(Intent.EXTRA_SUBJECT, "gLog");
						        	intentEmail.putExtra(Intent.EXTRA_TEXT   , "gLog ...");
									try {
										UserActivity.this.startActivity(Intent.createChooser(intentEmail, "Enviar mail..."));
									} catch (android.content.ActivityNotFoundException ex) {
									    Toast.makeText(UserActivity.this, "No hay clientes de email instalados.", Toast.LENGTH_SHORT).show();
									}
						        	
						        }
						     })
						    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // do nothing
						        }
						     })
						    .setIcon(R.drawable.ic_email)
						     .show();	
							
							
							
						}
					});
  	        	}
  	        	
  	        	//facebook
  	        	if(!facebook.getText().toString().trim().equals(""))
  	        	{
  	        		
  	        		facebookIV.setVisibility(View.VISIBLE);
  	        		facebookIV.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
									
							new AlertDialog.Builder(v.getContext())
						    .setTitle("Facebook")
						    .setMessage("¿Visitar el perfil de Facebook de "+userId+" ?")
						    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						           
						        	Intent intentFB = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+facebook.getText().toString().trim()));
						        	UserActivity.this.startActivity(intentFB);
						        	
						        }
						     })
						    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // do nothing
						        }
						     })
						    .setIcon(R.drawable.ic_facebook)
						     .show();	
						}
					});
  	        	}
  	        	//twitter
  	        	if(!twitter.getText().toString().trim().equals(""))
  	        	{
  	        		
  	        		twitterIV.setVisibility(View.VISIBLE);
  	        		twitterIV.setOnClickListener(new OnClickListener() {      			
						@Override
						public void onClick(View v) {
							
							new AlertDialog.Builder(v.getContext())
						    .setTitle("Twitter")
						    .setMessage("¿Visitar el perfil de Twitter de "+userId+" ?")
						    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						           
						        	Intent intentTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+twitter.getText().toString().trim()));
						        	UserActivity.this.startActivity(intentTwitter);
						        	
						        }
						     })
						    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // do nothing
						        }
						     })
						    .setIcon(R.drawable.ic_twitter)
						     .show();	
						}
					});
  	        	}
  	        	//web
  	        	if(!web.getText().toString().trim().equals(""))
  	        	{
  	        		
  	        		webIV.setVisibility(View.VISIBLE);
  	        		webIV.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							new AlertDialog.Builder(v.getContext())
						    .setTitle("Web")
						    .setMessage("¿Visitar la web de "+userId+" ?")
						    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						           
						        	Intent intentWeb = new Intent(Intent.ACTION_VIEW);
									intentWeb.setData(Uri.parse(web.getText().toString().trim()));
									UserActivity.this.startActivity(intentWeb);
						        	
						        }
						     })
						    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // do nothing
						        }
						     })
						    .setIcon(R.drawable.ic_web)
						    .show();
						}
					});
  	        	}
  	        	//phone
  	        	if(!phone.getText().toString().trim().equals(""))
  	        	{
  	        		
  	        		phoneIV.setVisibility(View.VISIBLE);
  	        		phoneIV.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							new AlertDialog.Builder(v.getContext())
						    .setTitle("Teléfono")
						    .setMessage("Número de teléfono: "+phone.getText().toString().trim())
						    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						           
						        	
						        	
						        }
						     })
						    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // do nothing
						        }
						     })
						    .setIcon(R.drawable.ic_web)
						    .show();
							
						}
					});
  	        	}
  	        	
                dismissDialog(DIALOG_LOADING_USER_CONTACT_INFO);
                  
  	        	Toast.makeText(UserActivity.this,"Datos de contacto obtenidos!", Toast.LENGTH_SHORT).show();
  	        
  	        }
  	    }
  	}

}
