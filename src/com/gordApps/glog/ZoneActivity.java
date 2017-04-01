package com.gordApps.glog;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;*/



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ZoneActivity extends ActionBarActivity {

	private ListView lstComments;
	//private String[] comments;
	
	
	
	private ArrayList<Comment> comments;
	
	private String idZone;
	private String nameZone;
	
	//******************
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	public static final int DIALOG_INSERT_COMENT = 1;
	
	private ProgressDialog mProgressDialog;
	
	//@+id/nameZoneLog
	//@+id/txtComment
	//@+id/btnGlog
	
	private TextView nameZoneLog;
	private EditText txtComment;
	
	
	private Comment comment;

	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_DOWNLOAD_PROGRESS:
	        mProgressDialog = new ProgressDialog(this);
	        mProgressDialog.setMessage("Cargando comentarios ...");
	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        mProgressDialog.setCancelable(false);
	        mProgressDialog.show();
	        return mProgressDialog;
	    
	    case DIALOG_INSERT_COMENT:
	        mProgressDialog = new ProgressDialog(this);
	        mProgressDialog.setMessage("Insertando comentario ...");
	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        mProgressDialog.setCancelable(false);
	        mProgressDialog.show();
	        return mProgressDialog;
	        
	    default:
	    return null;
	    }
	}
	//******************
	
	public void insertarComentario(View view){
		
		TareaRAInsertarComentario tarea = new TareaRAInsertarComentario();
		tarea.execute();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zone);
		
		//obtenemos el id de la zona
		
		Bundle bundle = getIntent().getExtras();
		idZone= bundle.getString("ZONE_ID");
		nameZone = bundle.getString("ZONE_NAME");
				
		
		nameZoneLog = (TextView) findViewById(R.id.nameZoneLog);
		

		nameZoneLog.setText(nameZone);
		
		//nameZoneLog.setText((CharSequence) nameZoneLog);
		
		
		txtComment = (EditText) findViewById(R.id.txtComment);
		
		lstComments = (ListView)findViewById(R.id.commentsZoneLog);
		
		//*************************
		//Obtener un listado de los comentarios

		
		
		
		
		
		
		
		
		/*if (savedInstanceState == null) {
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {
		    	idZone= null;
		    } else {
		    	idZone= extras.getString("STRING_I_NEED");
		    }
		} else {
			idZone= (String) savedInstanceState.getSerializable("ZONE_ID");
		}*/
		
		
		
		TareaRAObtenerComentarios tarea = new TareaRAObtenerComentarios();
	    tarea.execute();

	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zone, menu);
		return true;
	}
	
	private class TareaRAInsertarComentario extends AsyncTask<String,Integer,Boolean> {
		 
		 @Override
		    protected void onPreExecute() {
		        super.onPreExecute();
		        showDialog(DIALOG_INSERT_COMENT);
		    }


		    protected void onProgressUpdate(String... progress) {        
		    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		    }
		
		
		
		protected Boolean doInBackground(String... params) {
	 		
			boolean resul = true;
	 
			HttpClient httpClient = new DefaultHttpClient();
			 
			HttpPut put = new HttpPut("http://restapiglog.herokuapp.com/zone/"+idZone+"/comment");
			put.setHeader("content-type", "application/json");
			 
			try
			{
			
				
				//Construimos el objeto cliente en formato JSON
			    JSONObject dato = new JSONObject();
			 
			    comment = new Comment(txtComment.getText().toString(),"","Jaimito");
			    
			    dato.put("text", comment.text);
			    dato.put("date", comment.date);
			    dato.put("user_id", comment.user_id);
			 
			    StringEntity entity = new StringEntity(dato.toString());
			    put.setEntity(entity);
			 
			        HttpResponse resp = httpClient.execute(put);
			        String respStr = EntityUtils.toString(resp.getEntity());
			 
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

	        	comments.add(comment);
	        	
	        	ArrayAdapterComments adaptador = new ArrayAdapterComments(ZoneActivity.this, comments);
	        	
	        	 
                lstComments.setAdapter(adaptador);
              
               dismissDialog(DIALOG_INSERT_COMENT);
               
	        	Toast.makeText(ZoneActivity.this,"Comentario insertado", Toast.LENGTH_SHORT).show();

	        }
	    }
	}
	
	private class TareaRAObtenerComentarios extends AsyncTask<String,Integer,Boolean> {
		 
		 @Override
		    protected void onPreExecute() {
		        super.onPreExecute();
		        showDialog(DIALOG_DOWNLOAD_PROGRESS);
		    }


		    protected void onProgressUpdate(String... progress) {        
		    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		    }
		
		
		
		protected Boolean doInBackground(String... params) {
	 		
			boolean resul = true;
	 
	        HttpClient httpClient = new DefaultHttpClient();
	        
	        HttpGet del = new HttpGet("http://restapiglog.herokuapp.com/commentsinzone/"+idZone);
	         
	        del.setHeader("content-type", "application/json");
	         
	        try
	        {
	                HttpResponse resp = httpClient.execute(del);
	                String respStr = EntityUtils.toString(resp.getEntity());
	         
	                JSONArray respJSON = new JSONArray(respStr);
	         
	    //            comments = new String[respJSON.length()];
	                comments = new ArrayList<Comment>();
	         
	                for(int i=0; i<respJSON.length(); i++)
	                {
	                    JSONObject obj = respJSON.getJSONObject(i);
	         
	                    String text = obj.getString("text");
	                    String date = obj.getString("date");
	                    String user = obj.getString("user_id");
	                    
	                    comments.add(new Comment(text,date,user));
	                             
	                 //   comments[i] = "" + text;
	                    
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
	        	//Rellenamos la lista con los resultados
	        	
	 //       	lvMascotas=(ListView)findViewById(R.id.listMascotas);
	 //   		ArrayList<Mascota> arrayMascotas=utilidades.BBDDfake.getMascotas();
	 //   		ArrayAdapterMascotas adapter=new ArrayAdapterMascotas(this, arrayMascotas);
	 //   		lvMascotas.setAdapter(adapter);
	    		
	        	ArrayAdapterComments adaptador = new ArrayAdapterComments(ZoneActivity.this, comments);
	        	
	     /*   	ArrayAdapter<String> adaptador =
                        new ArrayAdapter<String>(ZoneActivity.this,
                        android.R.layout.simple_list_item_1, comments);*/
                        		
                        		
                        //		R.layout.comment, comments);
                
                //lstComments = (ListView)findViewById(R.id.commentsZoneLog); 
                
	        	lstComments.setAdapter(adaptador);
               
                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
                
	        	Toast.makeText(ZoneActivity.this,"Listado de comentarios obtenidos", Toast.LENGTH_SHORT).show();
	        	// lblResultado.setText("Insertado OK.");
	        }
	    }
	}
}
