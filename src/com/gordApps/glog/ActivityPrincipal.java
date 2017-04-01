package com.gordApps.glog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
//import org.osmdroid.views.MapView;

public class ActivityPrincipal extends ActionBarActivity {

	    
	//MapView mapView;
    LinearLayout searchPanel;
    Button searchButton;
    EditText searchText;
   
    public static final int SEARCH_ID = Menu.FIRST;

    private MapView myOpenMapView;
    private MapController mapController;
    
    LocationManager locationManager;
    
    OsmGeoUpdateHandler mUpdateHandler;
    
    //ArrayList<OverlayItem> overlayItemArray;
    
    ArrayList<Marker> markerArray;
    
    GeoPoint ptII;
    GeoPoint ptIS;
    GeoPoint ptDI;
    GeoPoint ptDS;
    
    public ArrayList<Zone> alZones;
    
    //obtiene el numero de telefono del usuario
    private String getPhoneNumber(){  
    	TelephonyManager mTelephonyManager;  
    	mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);   
    	return mTelephonyManager.getLine1Number();
    	}
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_principal);
    	
        //obtener el telefono
    	String phone = getPhoneNumber();
    	Toast.makeText(this, phone, Toast.LENGTH_LONG).show();
         
    	//Validar usuario
    		//Llama a la REST api y comprueba si el usuario esta registrado	
    	
    		//Si esta ejecutando la app por primera vez, se solicitara la info necesaria para poder continuar (nick, avatar...)
    		//y esa info se insertará en el server
    	
    	//Posicionamiento
    		//Obtener el punto actual de user
    	
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mUpdateHandler = new OsmGeoUpdateHandler(this);
        Location location = null;

        for (String provider : locationManager.getProviders(true))
        {
            location = locationManager.getLastKnownLocation(provider);
            if (location != null)
            {
                //location.setLatitude(MAP_DEFAULT_LATITUDE);
                //location.setLongitude(MAP_DEFAULT_LONGITUDE);
                locationManager.requestLocationUpdates(provider, 0, 0, mUpdateHandler);
                break;
            }
        }
        
        GeoPoint startPoint = new GeoPoint(43.38110, -3.21863);
               if(location != null){
              	 startPoint = new GeoPoint(location);
               } 			
    	
    	
   /* 	 locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
         //for demo, getLastKnownLocation from GPS only, not from NETWORK
         Location lastLocation 
          = locationManager.getLastKnownLocation(
            LocationManager.GPS_PROVIDER);
         */
         
  //       GeoPoint startPoint = new GeoPoint(43.38110, -3.21863);
  /*       if(lastLocation != null){
        	 startPoint = new GeoPoint(lastLocation);
         }*/ 			
    		//Obtener el area
    		//Obtener las zonas dentro del area
    	
    	//Dibujar
	    	 myOpenMapView = (MapView)findViewById(R.id.mapView);
	         myOpenMapView.setBuiltInZoomControls(false);	
	         
	       //Pintar area
	         mapController = (MapController) myOpenMapView.getController();
	         mapController.setZoom(18);	 
	         
	       //Centre map
	         mapController.setCenter(startPoint);	
    	
    		
	         
	         
	         //Determinar el area de acción
	         //int alcance=1500;
	         int alcance=1800;
	         
	          ptII= new GeoPoint(startPoint.getLatitudeE6()+alcance, startPoint.getLongitudeE6()-alcance);
	          
	          ptIS= new GeoPoint(startPoint.getLatitudeE6()-alcance, startPoint.getLongitudeE6()-alcance);
	          
	          ptDI= new GeoPoint(startPoint.getLatitudeE6()+alcance, startPoint.getLongitudeE6()+alcance);
	          
	          ptDS= new GeoPoint(startPoint.getLatitudeE6()-alcance, startPoint.getLongitudeE6()+alcance);
	          
	          
		         
	         //limitar el panning
	         BoundingBoxE6 bbox = new BoundingBoxE6(ptII.getLatitudeE6(),
	        		 								ptDS.getLatitudeE6(), 
	        		 								ptII.getLongitudeE6(), 
	        		 								ptDS.getLongitudeE6());
	    	 
	         
	         myOpenMapView.setScrollableAreaLimit(myOpenMapView.getBoundingBox());
	         
	         
	         //dibujar area de acción
	         PathOverlay myOverlay= new PathOverlay(Color.GREEN, this);
	         myOverlay.getPaint().setStyle(Style.FILL);
	         myOverlay.getPaint().setAlpha(128);

	         myOverlay.addPoint(ptII);
	         myOverlay.addPoint(ptDI);
	         myOverlay.addPoint(ptDS);
	         myOverlay.addPoint(ptIS);

	         myOpenMapView.getOverlays().add(myOverlay);
	         
	    
	         //*********************************
	         
	         
	         
	         Marker startMarker = new Marker(myOpenMapView);
	         startMarker.setPosition(startPoint);
	         startMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
	         
	            
	         startMarker.setIcon(getResources().getDrawable(R.drawable.emo_im_happy));
	         startMarker.setTitle("Estas aqui!");
	         
	         myOpenMapView.getOverlays().add(startMarker);
	         
	         //startMarker.setAlpha((float)0.5);
	         //startMarker.setSubDescription("Nombre de la zona");
	            
	         myOpenMapView.invalidate();//refrescar el mapa	
	          
	          
	          //Obtener y pintar zonas
	         
	         
	         TareaRAObtenerZonas tarea = new TareaRAObtenerZonas();
	 	    tarea.execute();
	 	    
	 	    
	         //pruebas pintando zonas ...
	    /*     overlayItemArray = new ArrayList<OverlayItem>();        
	         OverlayItem olItem = new OverlayItem("Zona", "holaaaaaaaaaaaa", new GeoPoint(43.38210, -3.21893));       
	         olItem.setMarker(getResources().getDrawable(R.drawable.ic_launcher));
	         
	         overlayItemArray.add(olItem);
	         
	         olItem = new OverlayItem("Hola", "prueba", new GeoPoint(43.38010, -3.21893));
	         olItem.setMarker(getResources().getDrawable(R.drawable.ic_launcher));
	         overlayItemArray.add(olItem);
	         
	         overlayItemArray.add(olItem);
	         overlayItemArray.add(new OverlayItem("Hola", "Zona de prueba", new GeoPoint(43.38010, -3.21793)));   
	         overlayItemArray.add(olItem);
	         overlayItemArray.add(new OverlayItem("hey", "probando...", new GeoPoint(43.38210, -3.21793)));
	        
	         
	         DefaultResourceProxyImpl defaultResourceProxyImpl = new DefaultResourceProxyImpl(this);
	         ItemizedIconOverlay<OverlayItem> myItemizedIconOverlay  = new ItemizedIconOverlay<OverlayItem>(overlayItemArray, null, defaultResourceProxyImpl);
	         
	         MyOwnItemizedOverlay overlay = new MyOwnItemizedOverlay(this, overlayItemArray);
	         
	         
	         myOpenMapView.getOverlays().add(overlay);  */
         
	     


	         
	         
	        
    	
    	//Eventos
    		//Al hacer click en alguna de las zonas disponibles se accederá al log correspondiente
    	
           
            
            searchPanel = (LinearLayout) findViewById(R.id.searchPanel);
            searchButton = (Button) findViewById(R.id.searchButton);
            searchText = (EditText) findViewById(R.id.searchText);
            
           
            
            
            searchButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String searchFor = searchText.getText().toString();
                    JSONArray results = searchLocation(searchFor);

                    if (results.length() > 0) {
                        try {
                            JSONObject firstResult = (JSONObject)results.get(0);
                            Double lat = firstResult.getDouble("lat");
                            Double lon = firstResult.getDouble("lon");
                            
                            GeoPoint point = new GeoPoint((int) (lat * 1E6),
                                                          (int) (lon * 1E6));
                            mapController.setZoom(15);
                            mapController.setCenter(point);

                            hideSearchPanel();

                           // mapView.invalidate();

                        } catch (JSONException e) {
                            Log.e("OnClickListener", e.getMessage());
                        }
                    } else {
                        Toast.makeText(view.getContext(), 
                                       "No results found", 
                                       Toast.LENGTH_SHORT).show();
                    }
                    
                    
                }
            });
            
            //setContentView(R.layout.activity_principal);
    	}

    	@Override
    	public boolean onCreateOptionsMenu(Menu menu) {
    		// Inflate the menu; this adds items to the action bar if it is present.
    		
    		//getMenuInflater().inflate(R.menu.activity_principal, menu);
    		//return true;
    		
    		boolean result = super.onCreateOptionsMenu(menu);
    	    menu.add(0, SEARCH_ID, 0, "Search");
    	    return result;
    	}

    	/*@Override
        public boolean onOptionsItemSelected(MenuItem item) {
            boolean result = super.onOptionsItemSelected(item);

            switch (item.getItemId()) {
                case SEARCH_ID:
                    searchPanel.setVisibility(View.VISIBLE);
                    break;
            }

            return result;
        }*/
    	
    	@Override
    	public boolean onOptionsItemSelected(MenuItem item) {
    	    switch(item.getItemId())
    	    {
    	        case R.id.action_settings:
    	            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();;
    	            break;
    	      //  case R.id.action_search:
    	      //      Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
    	      //      break;
    	        default:
    	            return super.onOptionsItemSelected(item);
    	    }
    	 
    	    return true;
    	}
    	
    	public JSONArray searchLocation(String query) {
            JSONArray results = new JSONArray();

            try {
                query = URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return results;
            }
            String url = "http://nominatim.openstreetmap.org/search?";
            url += "q=" + query;
            url += "&format=json";

            HttpGet httpGet = new HttpGet(url);
            DefaultHttpClient httpClient = new DefaultHttpClient();

            try {
                HttpResponse response = httpClient.execute(httpGet);
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
                results = new JSONArray(content);

            } catch (Exception e) {
                Log.e("searchLocation", 
                      "Error executing url: " + url + "; " + e.getMessage());
            }

            return results;
        }
    	
    	public void hideSearchPanel() {
            InputMethodManager imm =
                 (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                 searchText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            searchPanel.setVisibility(View.INVISIBLE);
        }
    

    	
    	
    	
    	
    	//******************
    	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    	private ProgressDialog mProgressDialog;

    	@Override
    	protected Dialog onCreateDialog(int id) {
    	    switch (id) {
    	    case DIALOG_DOWNLOAD_PROGRESS:
    	        mProgressDialog = new ProgressDialog(this);
    	        mProgressDialog.setMessage("Cargando zonas ...");
    	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	        mProgressDialog.setCancelable(false);
    	        mProgressDialog.show();
    	        return mProgressDialog;
    	    default:
    	    return null;
    	    }
    	}
    	//******************
    	
    	private class TareaRAObtenerZonas extends AsyncTask<String,Integer,Boolean> {
   		 
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
   	        
   	        HttpPost post = new HttpPost("http://restapiglog.herokuapp.com/zonesinarea");
   	         
   	        post.setHeader("content-type", "application/json");
   	        
	   	   //Construimos el objeto con coordenadas en formato JSON
   	        
   	        /*
   	         {
				"latitudeis" : 43.38010,
				"longitudeis":-3.21963,
				
				"latitudedi": 43.38210,
				"longitudedi": -3.21763
			}
      
   	         * */
	   	     JSONObject dato = new JSONObject();
	   	  
	   	     try {
	   	    	 
	   	    	 dato.put("latitudeis", ptIS.getLatitude());
				 dato.put("longitudeis", ptIS.getLongitude());
		   	     dato.put("latitudedi", ptDI.getLatitude());
		   	     dato.put("longitudedi", ptDI.getLongitude());
			
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
   	         
   	    //            comments = new String[respJSON.length()];
   	                alZones = new ArrayList<Zone>();
   	         
   	                
   	                Double latitude, longitude;
   	                String name,id,desc;
   	                ArrayList<Comment> alComment = new ArrayList<Comment>();
   	                
   	                for(int i=0; i<respJSON.length(); i++)
   	                {
   	                    JSONObject obj = respJSON.getJSONObject(i);
   	         
   	                    latitude = obj.getDouble("latitude");
   	                    longitude = obj.getDouble("longitude");
   	                    name = obj.getString("name");
   	                    desc = obj.getString("desc");
   	                    id = obj.getString("_id");
   	                    
   	                    
   	                   
   	                    
   	                    alZones.add(new Zone(latitude,longitude,name,desc,alComment,id));
   	                             
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
   	        	
   	        	//Dibujar las zonas que hemos recibido
   	        	
   	        	//overlayItemArray = new ArrayList<OverlayItem>();
   	        	markerArray = new ArrayList<Marker>();
   	        	
   	        	//OverlayItem olItem;
   	        	MyMarker marker;
   	        	
   	        	for(int i=0; i<alZones.size(); i++)
	                {
   	        			
   	        			//olItem = new OverlayItem(alZones.get(i).name, "Peperepepee", new GeoPoint(alZones.get(i).latitude, alZones.get(i).longitude));
   	        			//olItem.setMarker(getResources().getDrawable(R.drawable.ic_launcher));
   	        			
   	        			//overlayItemArray.add(olItem);
   	        			
   	        			marker = new MyMarker(myOpenMapView);
   	        			marker.setPosition(new GeoPoint(new GeoPoint(alZones.get(i).latitude, alZones.get(i).longitude)));
   	        			marker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
   	        			marker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
   	        			
   	        			marker.setTitle(alZones.get(i).name);
   	        			//marker.setAlpha((float)0.5);
   	        			marker.setSubDescription(alZones.get(i).desc);
   	        			
   	        			marker.setRelatedObject(alZones.get(i));
   	        			
   	        			
   	        			
   	        			markerArray.add(marker);
   	        			myOpenMapView.getOverlays().add(marker);
   	        		     			
   	        			
   	  	         
	                }  
   	        	
   	                 
	       
	         
	      /*   DefaultResourceProxyImpl defaultResourceProxyImpl = new DefaultResourceProxyImpl(ActivityPrincipal.this);
	         ItemizedIconOverlay<OverlayItem> myItemizedIconOverlay  = new ItemizedIconOverlay<OverlayItem>(overlayItemArray, null, defaultResourceProxyImpl);
	         
	         MyOwnItemizedOverlay overlay = new MyOwnItemizedOverlay(ActivityPrincipal.this, overlayItemArray);
	         
	         
	         myOpenMapView.getOverlays().add(overlay);*/
   	        	
	         myOpenMapView.invalidate();
   	    		

                  
                   dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
                   
   	        	Toast.makeText(ActivityPrincipal.this,"Zonas obtenidas!", Toast.LENGTH_SHORT).show();
   	        
   	        }
   	    }
   	}
    	
    	
}


