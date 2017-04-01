package com.gordApps.glog;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ZoneActivity extends Activity {

	private ListView lstComments;
	private String[] comments;
	
	//******************
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog mProgressDialog;

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
	    default:
	    return null;
	    }
	}
	//******************
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zone);
		
		//*************************
		//Obtener un listado de los comentarios
		
		
		TareaRAObtenerComentarios tarea = new TareaRAObtenerComentarios();
	    tarea.execute();
		
		/*
		try{
		    // Create a new HTTP Client
		    DefaultHttpClient defaultClient = new DefaultHttpClient();
		    // Setup the get request
		    URI uri = URI.create("http://restapiglog.herokuapp.com/comments");
		    HttpGet httpGetRequest = new HttpGet(uri);
		    
		   // httpGetRequest.setHeader("Content-type", "application/json");
		    
		    // Execute the request in the client
		    HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
		    // Grab the response
	//	    BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
	//	    String json = reader.readLine();
		    
		    String respStr = EntityUtils.toString(httpResponse.getEntity());
	    	 
	        JSONArray respJSON = new JSONArray(respStr);
	 
	        String[] comments = new String[respJSON.length()];
	 
	        for(int i=0; i<respJSON.length(); i++)
	        {
	            JSONObject obj = respJSON.getJSONObject(i);
	 
	            String text = obj.getString("text");
	            
	          //  int idCli = obj.getInt("Id");
	          //  String nombCli = obj.getString("Nombre");
	          //  int telefCli = obj.getInt("Telefono");
	 
	            comments[i] = i+1 +": "+text;
	        }
	 
	        //Rellenamos la lista con los resultados
	        ArrayAdapter<String> adaptador =
	                new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1, comments);
	 
	        lstComments = (ListView) findViewById(R.id.commentsZoneLog);
	        lstComments.setAdapter(adaptador);
	        
	        
		    
		//    Toast.makeText(this, json, Toast.LENGTH_SHORT).show();

		    // Instantiate a JSON object from the request response
		//    JSONObject jsonObject = new JSONObject(json);

		} catch(Exception e){
		    // In your production code handle any errors and catch the individual exceptions
		    e.printStackTrace();
		    Toast.makeText(this,"mierda pa ti", Toast.LENGTH_SHORT).show();
		}
		*/
		
		//*****************************
		//*****************************
		
		/*
		AsyncHttpClient client = new AsyncHttpClient();
	//	RequestParams rp = new RequestParams();
	//	rp.put("param1", value1);
	//	rp.put("param2", value2);
		client.get("http://restapiglog.herokuapp.com/comments", new JsonHttpResponseHandler(){	
			@Override
			 public void onSuccess(JSONArray jArrayComments){    				 
				//List <String> comments = new List <String>(); 
				String[] comments = new String[jArrayComments.length()];		    	 
	    	        for(int i=0; i<jArrayComments.length(); i++)
	    	        {
	    	            JSONObject obj = new JSONObject();
						try {
							obj = jArrayComments.getJSONObject(i);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    	 
	    	            String text = "";
						try {
							text = obj.getString("text");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    	            comments[i] = i+1 +": "+text;
	    	            //comments.add(i+1 +": "+text);
	    	        }
	    	 
	    	        //Rellenamos la lista con los resultados
	    	        lstComments = (ListView) findViewById(R.id.commentsZoneLog);
	    	        ArrayAdapter<String> adaptador = new ArrayAdapter<String> (lstComments.getContext(),R.id.commentsZoneLog, comments);
	    	        lstComments.setAdapter(adaptador);		
			 }   
			 @Override
			 public void onFailure(Throwable arg0){
			     
			 }
		});
		*/
		//**************************************
		//****************************************
		
		
/*		client.post("http://restapiglog.herokuapp.com/comments",rp, new JsonHttpResponseHandler(){
		 @Override
		 public void onSuccess(JSONObject jObject){    
		     
		 }   
		 @Override
		 public void onFailure(Throwable arg0){
		     
		 }
		}); */
	/*	
		HttpClient httpClient = new DefaultHttpClient();
   	 
    	HttpGet del = new HttpGet("http://restapiglog.herokuapp.com/comments");
    	 
    	del.setHeader("content-type", "application/json");
    	 
    	try
    	{
    	        HttpResponse resp = httpClient.execute(del);
    	        String respStr = EntityUtils.toString(resp.getEntity());
    	 
    	        JSONArray respJSON = new JSONArray(respStr);
    	 
    	        String[] comments = new String[respJSON.length()];
    	 
    	        for(int i=0; i<respJSON.length(); i++)
    	        {
    	            JSONObject obj = respJSON.getJSONObject(i);
    	 
    	            String text = obj.getString("text");
    	            
    	          //  int idCli = obj.getInt("Id");
    	          //  String nombCli = obj.getString("Nombre");
    	          //  int telefCli = obj.getInt("Telefono");
    	 
    	            comments[i] = i+1 +": "+text;
    	        }
    	 
    	        //Rellenamos la lista con los resultados
    	        ArrayAdapter<String> adaptador =
    	                new ArrayAdapter<String>(this,
    	                android.R.layout.simple_list_item_1, comments);
    	 
    	        lstComments = (ListView) findViewById(R.id.commentsZoneLog);
    	        lstComments.setAdapter(adaptador);
    	}
    	catch(Exception ex)
    	{
    	        Log.e("ServicioRest","Error!", ex);
    	}
    	*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zone, menu);
		return true;
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
	        
	        HttpGet del =
	            new HttpGet("http://restapiglog.herokuapp.com/comments");
	         
	        del.setHeader("content-type", "application/json");
	         
	        try
	        {
	                HttpResponse resp = httpClient.execute(del);
	                String respStr = EntityUtils.toString(resp.getEntity());
	         
	                JSONArray respJSON = new JSONArray(respStr);
	         
	                comments = new String[respJSON.length()];
	         
	                for(int i=0; i<respJSON.length(); i++)
	                {
	                    JSONObject obj = respJSON.getJSONObject(i);
	         
	                    String text = obj.getString("text");
	                             
	                    comments[i] = "" + text;
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
                ArrayAdapter<String> adaptador =
                        new ArrayAdapter<String>(ZoneActivity.this,
                        android.R.layout.simple_list_item_1, comments);
                        		
                        		
                        //		R.layout.comment, comments);
                
                lstComments = (ListView)findViewById(R.id.commentsZoneLog); 
                lstComments.setAdapter(adaptador);
               
                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
                
	        	Toast.makeText(ZoneActivity.this,"Listado de comentarios obtenidos", Toast.LENGTH_SHORT).show();
	        	// lblResultado.setText("Insertado OK.");
	        }
	    }
	}
/*	
	private class HTTPGet extends AsyncTask<String, Void, String> {

	    BufferedReader in = null;
	    String returned = null;

	    @Override
	    protected String doInBackground(String... params) {

	        try {

	            HttpClient client = new DefaultHttpClient();

	            URI website = new URI("http://restapiglog.herokuapp.com/comments");

	            HttpGet request = new HttpGet();

	            request.setURI(website);

	            HttpResponse response = client.execute(request);

	            in = new BufferedReader(new InputStreamReader(response
	                    .getEntity().getContent()));

	            StringBuffer sb = new StringBuffer("");

	            String l = "";

	            String nl = System.getProperty("line.separator");

	            while ((l = in.readLine()) != null) {

	                sb.append(l + nl);
	            }

	            in.close();
	            returned = sb.toString();

	        } catch (ClientProtocolException e) {

	            e.printStackTrace();
	        } catch (IOException e) {

	            e.printStackTrace();
	        } catch (URISyntaxException e) {

	            e.printStackTrace();
	        }
	        return null;
	        }

	    @Override
	    protected void onPostExecute(String result) {

	        super.onPostExecute(result);
	        
	        //Toast.makeText(this, (CharSequence)returned, Toast.LENGTH_SHORT).show();
	    }
	    }
*/
}
