package com.glogApps.glog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;


import com.glogApps.glog.models.Comment;
import com.glogApps.glog.models.Zone;
import com.glogApps.glog.utils.ArrayAdapterComments;
import com.glogApps.glog.utils.Utils;
import com.gordApps.glog.R;
import com.gordApps.glog.R.drawable;
import com.gordApps.glog.R.layout;
import com.gordApps.glog.R.menu;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateZoneActivity extends ActionBarActivity {

	public static final int DIALOG_CREATE_ZONE = 0;
	private ProgressDialog mProgressDialog;
	
	Zone newZone;
	EditText name,desc;
	
	String infoNewZone;
	
	ActionBar actionBar;
	
	View doneButton;
	View cancelButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_zone);
		
		
		newZone = new Zone();
		//obtenemos coordenadas de la zona
		
		Bundle bundle = getIntent().getExtras();
		
		newZone.setLatitude(bundle.getDouble("LATITUDE_NEW_ZONE"));
		newZone.setLongitude(bundle.getDouble("LONGITUDE_NEW_ZONE"));
		
		infoNewZone = bundle.getString("INFO_NEW_ZONE");
		
		
	/*	
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		StringBuilder builder = new StringBuilder();
		try {
		    List<Address> address = geoCoder.getFromLocation(newZone.getLatitude(), newZone.getLongitude(), 1);
		    int maxLines = address.get(0).getMaxAddressLineIndex();
		    for (int i=0; i<maxLines; i++) {
		    String addressStr = address.get(0).getAddressLine(i);
		    builder.append(addressStr);
		    builder.append(" ");
		    }

		City = builder.toString(); //This is the complete address.
		} catch (IOException e) {
			
		}
		  catch (NullPointerException e) {}
		
		*/
		
		
		newZone.setLastCommentDate(Utils.getISODatePhone());
		
		
		
		//***************************
		newZone.setLastCommentText("Creada por Pepito el "+newZone.lastCommentDate);
		newZone.setLastCommentUser_id("Pepito");
		//***************************
		
		actionBar = getSupportActionBar();
	//	actionBar.setDisplayHomeAsUpEnabled(true);
	//	actionBar.setTitle("Crear nuevo gLog");
		
		//ActionBar actionBar = parentActivity.getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setSubtitle(null);
        //parentActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        actionBar.setCustomView(R.layout.settings_do_action_layout);
        cancelButton = actionBar.getCustomView().findViewById(R.id.cancel_button);
        cancelButton.setBackgroundColor(drawable.glogtheme_btn_default_holo_light);
       
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        doneButton = actionBar.getCustomView().findViewById(R.id.done_button);
        doneButton.setBackgroundColor(drawable.glogtheme_btn_default_holo_light);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	
            	crearNuevaZona(view);
            	//finish();
               
            	/* if (firstNameField.getText().length() != 0) {
                    saveName();
                    finishFragment();
                }*/
            }
        });

		//cancelar
		/**CREAR TABS**/
    /*    Tab tabCancelar = actionBar.newTab()
                    .setText("Cancelar");
        actionBar.addTab(tabCancelar);
        
        Tab tabAceptar = actionBar.newTab()
                .setText("Aceptar");
        actionBar.addTab(tabAceptar);*/
		
		
		
	}
	
	public void crearNuevaZona(View view){
		
		
		//comprobaciones campos de nombre y descripcion de la nueva zona
		
		name = (EditText)findViewById(R.id.txtNombreNZ);
		desc = (EditText)findViewById(R.id.txtDescNZ);
		
		newZone.setName(name.getText().toString());
		newZone.setDesc(desc.getText().toString() + " ,"+infoNewZone);
		
		TareaRAInsertarZona tarea = new TareaRAInsertarZona();
		tarea.execute();
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_zone, menu);
		return true;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_CREATE_ZONE:
	        mProgressDialog = new ProgressDialog(this);
	        mProgressDialog.setMessage("Creando la nueva zona ...");
	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        mProgressDialog.setCancelable(false);
	        mProgressDialog.show();
	        return mProgressDialog;
	        
	    default:
	    return null;
	    }
	}
	
	private class TareaRAInsertarZona extends AsyncTask<String,Integer,Boolean> {
		 
		 @Override
		    protected void onPreExecute() {
		        super.onPreExecute();
		        showDialog(DIALOG_CREATE_ZONE);
		    }


		    protected void onProgressUpdate(String... progress) {        
		    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		    }
		
		
		
		protected Boolean doInBackground(String... params) {
	 		
			boolean resul = true;
	 
			HttpClient httpClient = new DefaultHttpClient();
			 
			HttpPost post = new HttpPost("http://restapiglog.herokuapp.com/zone");
			post.setHeader("content-type", "application/json");
			 
			try
			{
			
				
				//Construimos el objeto cliente en formato JSON
			    JSONObject dato = new JSONObject();
			 		    
			    
			    dato.put("longitude", newZone.getLongitude());
			    dato.put("latitude", newZone.getLatitude());		  
			    dato.put("name", newZone.getName());
			    dato.put("desc", newZone.getDesc());
			    dato.put("lastCommentText", Utils.stringToBinary(newZone.getLastCommentText()));
			    dato.put("lastCommentDate", newZone.getLastCommentDate());
			    dato.put("lastCommentUser_id", newZone.getLastCommentUser_id());
			 
			    StringEntity entity = new StringEntity(dato.toString());
			    post.setEntity(entity);
			 
			        HttpResponse resp = httpClient.execute(post);
			     //   String respStr = EntityUtils.toString(resp.getEntity());
			 
			   //     if(respStr.equals("true"))
			  //          lblResultado.setText("Actualizado OK.");
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

	        /*	comments.add(comment);
	        	
	        	ArrayAdapterComments adaptador = new ArrayAdapterComments(ZoneActivity.this, comments);
	        	
	        	 
               lstComments.setAdapter(adaptador);*/
             
              dismissDialog(DIALOG_CREATE_ZONE);
              
              Toast.makeText(CreateZoneActivity.this,"Nueva zona creada", Toast.LENGTH_SHORT).show();
              

	        }
	    }
	}

}
