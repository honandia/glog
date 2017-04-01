package com.glogApps.glog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.osmdroid.bonuspack.overlays.InfoWindow;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Marker.OnMarkerDragListener;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;

import com.glogApps.glog.emoji.Emoji;
import com.glogApps.glog.emoji.EmojiView;
import com.glogApps.glog.maps.Area;
import com.glogApps.glog.maps.CustomInfoWindow;
import com.glogApps.glog.maps.MyMarker;
import com.glogApps.glog.maps.MyMarkerCustomInfoWindow;
import com.glogApps.glog.maps.OsmGeoUpdateHandler;
import com.glogApps.glog.models.Comment;
import com.glogApps.glog.models.Zone;
import com.glogApps.glog.utils.Utils;
import com.gordApps.glog.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.osmdroid.views.MapView;

import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;

public class ActivityPrincipal extends ActionBarActivity implements LocationListener{

	    

    private MapView myOpenMapView;
    private MapController mapController;
    
    LocationManager locationManager;
    
    OsmGeoUpdateHandler mUpdateHandler;
    
    Location location;
    Location newestLocation;
    
    List<String> lProviders;
    
    //ArrayList<OverlayItem> overlayItemArray;
    
    ArrayList<Marker> markerArray;
    
    Area myArea;
    public static final int ALCANCE = 1800;
    
    GeoPoint startPoint;
    
    Marker startMarker;
    Marker newZoneMarker;
    CustomInfoWindow newZoneMarkerInfoWindow;
    String infoNewZone;
    
    Area newZoneArea;
    
    public static final int MAX_ZONES_IN_AREA = 15;
    private int zonesInArea;
    private int zoneIndex;
    
    
    /*GeoPoint ptII;
    GeoPoint ptIS;
    GeoPoint ptDI;
    GeoPoint ptDS;*/
    
    public ArrayList<Zone> alZones;
    
    public static final int DIALOG_LOADING_AREA = 0;
    //public static final int DIALOG_LOADING_LOCATION = 1;
    public static final int DIALOG_INFO_LOCATION = 1;
    
    
    //obtiene el numero de telefono del usuario
    private String getPhoneNumber(){  
    	TelephonyManager mTelephonyManager;  
    	mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);   
    	return mTelephonyManager.getLine1Number();
    	}
    
    public void updateAreaPosition(GeoPoint point){
    	
    	myOpenMapView.getOverlays().clear();
    	//Centre map
        mapController.setCenter(point);	
	      
        //alcance 
        myArea = new Area(ALCANCE, point);        
        myArea.draw(myOpenMapView, Color.GREEN, this); 
     
        //limitar el panning
        //myOpenMapView.setScrollableAreaLimit(myOpenMapView.getBoundingBox());
    
        zoneIndex=0;//inicializamos 
        zonesInArea=0;
        
        //Obtener y pintar zonas   
        TareaRAObtenerZonas tarea = new TareaRAObtenerZonas();
	    tarea.execute();
	    
	    //marcador de inicio
        startMarker = new Marker(myOpenMapView);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                   
        startMarker.setIcon(getResources().getDrawable(R.drawable.emo_im_happy));
        startMarker.setTitle("Estas aqui!");
        
        myOpenMapView.getOverlays().add(startMarker);
           
        myOpenMapView.invalidate();//refrescar el mapa
    	
    }
    
    public void newZone(GeoPoint point){
    	
    	newZoneArea.draw(myOpenMapView, Color.RED, myOpenMapView.getContext());
      
        newZoneMarker = new Marker(myOpenMapView);
        newZoneMarker.setPosition(new GeoPoint(point.getLatitudeE6()+ 50,point.getLongitudeE6()));
        newZoneMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                     
        newZoneMarkerInfoWindow = new CustomInfoWindow(myOpenMapView);
        
        newZoneMarkerInfoWindow.btn.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View view) {
        			 	            			
        			//obtenemos la información del lugar donde se creará el nuevo gLog
        			TareaOSMObtenerInfoGLog tarea = new TareaOSMObtenerInfoGLog();
        			tarea.execute();
        			
        			//vamos a la activity para la creación del gLog
        			
        		/*	Intent intent = new Intent(myOpenMapView.getContext(),CreateZoneActivity.class);	
        			
        			//pasamos el ID y el nombre de la zona qu vamos a consultar
        			intent.putExtra("LATITUDE_NEW_ZONE", newZoneMarker.getPosition().getLatitude());
        			intent.putExtra("LONGITUDE_NEW_ZONE", newZoneMarker.getPosition().getLongitude());
        		
        			myOpenMapView.getContext().startActivity(intent);*/
		
        		}
        		});
        
        newZoneMarker.setInfoWindow(newZoneMarkerInfoWindow);
         
        newZoneMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

        OnMarkerDragListener myDragListener = new OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker newZoneMarker) {
				
				newZoneMarker.hideInfoWindow();
			
			}
			
			@Override
			public void onMarkerDragEnd(Marker arg0) {
						
				if (newZoneArea.checkGeoPointInArea(newZoneMarker.getPosition()))
				{
					//marker is in area
					newZoneMarker.setTitle("¿Crear una zona en este punto?");
					newZoneMarker.showInfoWindow();
				}
				else
				{	
					startMarker.setTitle("Situa la nueva zona dentro del area permitida");
    		        startMarker.showInfoWindow();
				}	
			}
			
			@Override
			public void onMarkerDrag(Marker arg0) {
			}
		};
        
        newZoneMarker.setOnMarkerDragListener(myDragListener);      
        newZoneMarker.setDraggable(true);       
        myOpenMapView.getOverlays().add(newZoneMarker);
    }
    
    public void getCurrentLocation() {
    	
    	Criteria criteria = new Criteria();
    	criteria.setPowerRequirement(Criteria.ACCURACY_HIGH);
    	
        List<String> providers = locationManager.getProviders(criteria, true);
        if (providers.size() > 0) {
            newestLocation = null;
            for (String provider : providers) {
                location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    if (newestLocation == null) {
                        newestLocation = location;
                    } else {
                        if (location.getTime() > newestLocation.getTime()) {
                            newestLocation = location;
                        }
                    }
                    locationManager.requestLocationUpdates(provider, 0, 0, this);
                }
            }
        } else {
            LocationDialogFragment dialog = new LocationDialogFragment();
            dialog.show(getSupportFragmentManager(),
                LocationDialogFragment.class.getName());
        }
    }
    
    @Override
    public void onLocationChanged(Location location) {
        float bestAccuracy = -1f;
        if (location.getAccuracy() != 0.0f
            && (location.getAccuracy() < bestAccuracy) || bestAccuracy == -1f) {
            if (location.getAccuracy() < 5f ){//Const.MIN_ACCURACY) {
                locationManager.removeUpdates(this);
            }
        }
        bestAccuracy = location.getAccuracy();
    }
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
		LocationDialogFragment dialog = new LocationDialogFragment();
        dialog.show(getSupportFragmentManager(),
            LocationDialogFragment.class.getName());
        
		//Intent intent=new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		//getApplicationContext().startActivity(intent);
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_principal);
    	
         
         //**********************************************
         // cargar usuario guardado en sqlite, si no existe
         // pedir datos pa la creacion de un nuevo usuario
         // Cargar fragment de login o de registro
         //**********************************************
         
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
 //       mUpdateHandler = new OsmGeoUpdateHandler(this);
        //Location location = null;

   /*     float precision =100;
        int precisionMinimaRequerida=10;//metros
        while (location == null && (precision>=precisionMinimaRequerida))
        {
        	
	        for (String provider : locationManager.getProviders(true))
	        {
	        	
	            location = locationManager.getLastKnownLocation(provider);
	            precision=location.getAccuracy();
	            if (location != null)
	            {
	                //location.setLatitude(MAP_DEFAULT_LATITUDE);
	                //location.setLongitude(MAP_DEFAULT_LONGITUDE);
	                locationManager.requestLocationUpdates(provider, 1000, 0, mUpdateHandler);
	                break;
	            }
	        }
        }*/
        
        // solo para provider = GPS
        
      //  while (location == null)
     //   {
//     locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mUpdateHandler);
 //    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //}
    	
    /*	lProviders = locationManager.getProviders(true);
    	
    	TareaRAObtenerUbicacion tarea = new TareaRAObtenerUbicacion();
    	tarea.execute();*/
    	
    	getCurrentLocation();
    	
        startPoint = new GeoPoint(43.38110, -3.21863);          
        if(newestLocation != null){
              	 startPoint = new GeoPoint(newestLocation);
               }
               
		//Dibujar
        myOpenMapView = (MapView)findViewById(R.id.mapView);
		myOpenMapView.setBuiltInZoomControls(false);	
		   
		//Pintar area
		mapController = (MapController) myOpenMapView.getController();
		mapController.setZoom(18);
        
		
		mapController.setCenter(startPoint);
		//limitar el panning
        myOpenMapView.setScrollableAreaLimit(myOpenMapView.getBoundingBox());
    	updateAreaPosition(startPoint);
    	
	 	     
    	//Eventos
    		//Al hacer click en alguna de las zonas disponibles se accederá al log correspondiente

    	}
    
    
    	@Override
    	public boolean onCreateOptionsMenu(Menu menu) {
    		// Inflate the menu; this adds items to the action bar if it is present.
    		getMenuInflater().inflate(R.menu.activity_principal, menu);
    		return true;
    	}
    	
    	@Override
    	public boolean onOptionsItemSelected(MenuItem item) {
    	    switch(item.getItemId())
    	    {
    	        case R.id.menu_settings://Ajustes
    	        	
    	        	
    	        	Intent intentAjustes = new Intent(myOpenMapView.getContext(),SettingsActivity.class);	
    	        	myOpenMapView.getContext().startActivity(intentAjustes);
    	        	
    	            Toast.makeText(this, "Ajustes", Toast.LENGTH_SHORT).show();;
    	            break;
    	            
    	        case R.id.menu_view://mostrar la información de cada zona
  	        	
    	        	if (!markerArray.isEmpty())
    	        	{
	    	        	//oculta todos los bocadillos
		        		for (Marker markers : markerArray) {
		        			markers.hideInfoWindow();	
		        		}
	    	        	
		        		
	    	        	if (zoneIndex < zonesInArea)
	    	        	{
	    	        		mapController.animateTo(markerArray.get(zoneIndex).getPosition());
	    	        		markerArray.get(zoneIndex).showInfoWindow();
	    	        		zoneIndex ++;
	    	        	}
		        		else
		        		{
		        		zoneIndex = 0;
		        		mapController.animateTo(markerArray.get(zoneIndex).getPosition());
		        		markerArray.get(zoneIndex).showInfoWindow();
		        		zoneIndex ++;
		        		}
    	        	}
    	        	else
    	        	{
    	        		Toast.makeText(this, "No hay gLogs en esta área", Toast.LENGTH_SHORT).show();
    	        	}
    	        	break;
    	        	
    	        case R.id.menu_zone://Añadir nueva zona
    	        	
    	        	newZoneArea = new Area(300, startPoint);
    	        	
    	        	boolean gLogsInCreationArea =false;
    	        	for (Zone zone : alZones) {
    	        		if(newZoneArea.checkGeoPointInArea(new GeoPoint(zone.getLatitude(),zone.getLongitude()))){
    	        			//ya existe algun gLog dentro del area de creación
    	        			gLogsInCreationArea= true;
    	        		}
    	    		}
    	        	
    	        	if (!gLogsInCreationArea){
    	        		
    	        		if(zonesInArea <= MAX_ZONES_IN_AREA)
    	        		{
    	        			startMarker.setPosition(new GeoPoint(startMarker.getPosition().getLatitudeE6() + newZoneArea.getAlcance(), startMarker.getPosition().getLongitudeE6() + newZoneArea.getAlcance()));
    	        			startMarker.setTitle("Arrastra la nueva zona dentro del area de creación");
    	        	        startMarker.showInfoWindow();
    	        			newZone(startPoint);
    	        		}
    	        		else
    	        			Toast.makeText(this, "Se ha superado el límite de gLogs en esta área", Toast.LENGTH_SHORT).show(); 
    	        	}else Toast.makeText(this, "No se ha podido crear el nuevo gLog. Existe uno cerca", Toast.LENGTH_SHORT).show();
    	            
    	            break;
    	            
    	        case R.id.menu_refresh://refrescar area
    	        	
    	        	//Habria que volver a calcular el startPoint
    	        	lProviders = locationManager.getProviders(true);
    	        	
    	        	//TareaRAObtenerUbicacion tarea = new TareaRAObtenerUbicacion();
    	        	//tarea.execute();
    	        	
    	        	updateAreaPosition(startPoint);
    	        	break;
    	        	
    	        case R.id.menu_remid://acceder a comentarios archivados
    	        	
    	        	
    	        	Intent intent = new Intent(myOpenMapView.getContext(),SavedCommentsActivity.class);	
    	        	myOpenMapView.getContext().startActivity(intent);
    	            
    	        	Toast.makeText(this, "Archivados", Toast.LENGTH_SHORT).show();
    	            break;
    	        default:
    	            return super.onOptionsItemSelected(item);
    	    }
    	 
    	    return true;
    	}
    	
    	//******************
    	
    	private ProgressDialog mProgressDialog;

    	@Override
    	protected Dialog onCreateDialog(int id) {
    	    switch (id) {
    	    case DIALOG_LOADING_AREA:
    	        mProgressDialog = new ProgressDialog(this);
    	        mProgressDialog.setMessage("Cargando area ...");
    	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	        mProgressDialog.setCancelable(false);
    	        mProgressDialog.show();
    	        return mProgressDialog;
    	        
    	 /*   case DIALOG_LOADING_LOCATION:
    	        mProgressDialog = new ProgressDialog(this);
    	        mProgressDialog.setMessage("Calculando ubicacion...");
    	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	        mProgressDialog.setCancelable(false);
    	        mProgressDialog.show();
    	        return mProgressDialog;*/
    	        
    	    
    	    	
    	    case DIALOG_INFO_LOCATION:
    	        mProgressDialog = new ProgressDialog(this);
    	        mProgressDialog.setMessage("Obteniendo información de este punto...");
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
   		        showDialog(DIALOG_LOADING_AREA);
   		    }


   		    protected void onProgressUpdate(String... progress) {        
   		    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
   		    }
   		
   		
   		
   		protected Boolean doInBackground(String... params) {
   	 		
   			boolean resul = true;
   			
   			HttpClient httpClient = new DefaultHttpClient();
   	        
   	        HttpPost post = new HttpPost("http://restapiglog.herokuapp.com/zonesinarea");
   	        
   	     
   	     //HttpPost post = new HttpPost("http://localhost:3000/zonesinarea");
   	         
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
	   	    	 
	   	    	 dato.put("latitudeis", myArea.getPtIS().getLatitude());
				 dato.put("longitudeis", myArea.getPtIS().getLongitude());
		   	     dato.put("latitudedi", myArea.getPtDI().getLatitude());
		   	     dato.put("longitudedi", myArea.getPtDI().getLongitude());
			
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
   	                String name,id,desc,lastCommentText,lastCommentDate,lastCommentUser_id;
   	                ArrayList<Comment> alComment = new ArrayList<Comment>();
   	                
   	                for(int i=0; i<respJSON.length(); i++)
   	                {
   	                    JSONObject obj = respJSON.getJSONObject(i);
   	         
   	                    latitude = obj.getDouble("latitude");
   	                    longitude = obj.getDouble("longitude");
   	                    name = obj.getString("name");
   	                    desc = obj.getString("desc");
   	                    id = obj.getString("_id");
   	                    
   	                    //lastCommentText = obj.getString("lastCommentText");
   	                    
   	                    lastCommentText = obj.getString("lastCommentText");
   	                    lastCommentDate = obj.getString("lastCommentDate");
   	                    lastCommentUser_id = obj.getString("lastCommentUser_id");
   	                                   
   	                    
   	                    alZones.add(new Zone(id,latitude,longitude,name,desc,lastCommentText,lastCommentDate,lastCommentUser_id,alComment));
   	                             
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
   	        	//TextView bubbleDesc;
   	        	
   	        	zonesInArea = alZones.size();
   	        	
   	        	String lastCommentText="";
   	        	
   	        	for(int i=0; i<zonesInArea; i++)
	                {
   	        			
   	        			//olItem = new OverlayItem(alZones.get(i).name, "Peperepepee", new GeoPoint(alZones.get(i).latitude, alZones.get(i).longitude));
   	        			//olItem.setMarker(getResources().getDrawable(R.drawable.ic_launcher));
   	        			
   	        			//overlayItemArray.add(olItem);
   	        			
   	        			marker = new MyMarker(myOpenMapView);
   	        			marker.setPosition(new GeoPoint(new GeoPoint(alZones.get(i).latitude, alZones.get(i).longitude)));
   	        			marker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
   	        			marker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
   	        			
   	        			
   	        			marker.setSnippet(alZones.get(i).desc);
   	        			marker.setTitle(alZones.get(i).name);
   	        			//marker.setAlpha((float)0.5);
   	        			
   	        			
   	        			
   	        			lastCommentText = Utils.binaryToString(alZones.get(i).lastCommentText);
   	        			
   	        			//http://danielme.com/tip-android-29-layouts-dinamicos-anadir-vistas-programaticamente/
   	        			
   	  
   	        			MyMarkerCustomInfoWindow mInfo = new MyMarkerCustomInfoWindow(myOpenMapView,
   	        					alZones.get(i).lastCommentUser_id ,
   	        					lastCommentText,
   	        					alZones.get(i).lastCommentDate);
   	        	        	
   	        	        marker.setInfoWindow(mInfo);
   	        	        
   	        	        
   	        	         
   	        	        //layout.addView(tableLayout); 
   	        	        
   	        	        //marker.sett
   	        	      
   	        	        
   	        			
   	        			
   	        			
   	        			//Emoji.replaceEmoji(alZones.get(i).lastCommentText)
   	        			
   	        			//marker.setSubDescription(alZones.get(i).lastCommentUser_id + alZones.get(i).lastCommentDate +'\n'+ Emoji.replaceEmoji(lastCommentText));
   	        			
   	        			
   	        		
   	        			
   	        			marker.setRelatedObject(alZones.get(i));
   	        			
   	        			
   	        			
   	        			markerArray.add(marker);
   	        			myOpenMapView.getOverlays().add(marker);
   	        		     			
   	        			
   	  	         
	                }  
   	        	
   	                 
   	        	
	         myOpenMapView.invalidate();
   	    		

                  
                   dismissDialog(DIALOG_LOADING_AREA);
                   
   	        	Toast.makeText(ActivityPrincipal.this,"Zonas obtenidas!", Toast.LENGTH_SHORT).show();
   	        
   	        }
   	    }
   	}
    	
    //****************
    //****************
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
    //****************
    	private class TareaOSMObtenerInfoGLog extends AsyncTask<String,Integer,Boolean> {
      		 
      		 @Override
      		    protected void onPreExecute() {
      		        super.onPreExecute();
      		        showDialog(DIALOG_INFO_LOCATION);
      		    }


      		    protected void onProgressUpdate(String... progress) {        
      		    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
      		    }
      		
      		
      		
      		protected Boolean doInBackground(String... params) {
      	 		
      			boolean resul = true;
      			
      			final String requestString = "http://nominatim.openstreetmap.org/reverse?format=json&lat=" +
      	                Double.toString(newZoneMarker.getPosition().getLatitude()) + "&lon=" + Double.toString(newZoneMarker.getPosition().getLongitude()) + "&zoom=18&addressdetails=1";  
      			HttpClient httpClient = new DefaultHttpClient();
      	        
      	        HttpGet get = new HttpGet(requestString); 
      	         
      	        //post.setHeader("content-type", "application/json");
      	             
      	        try
      	        {
      	                HttpResponse resp = httpClient.execute(get);
      	                String respStr = EntityUtils.toString(resp.getEntity());
      	         
      	                JSONObject obj = new JSONObject(respStr);    
       	                
      	                
      	              
	      	            if (obj.has("display_name"))
	      	            	infoNewZone = obj.getString("display_name");
	  	                else
	  	                	infoNewZone = "";
      	              
      	                /*if(obj.has("address"))
      	                	city = obj.getJSONObject("address").getString("town");
      	                else if (obj.has("city"))
      	                	city = obj.getString("city");
      	                else
      	                	city = "";*/
       	                
       	                    
       	                    
       	                
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

                     
                      dismissDialog(DIALOG_INFO_LOCATION);
                      
                      Intent intent = new Intent(myOpenMapView.getContext(),CreateZoneActivity.class);	
          			
          			  //pasamos el ID y el nombre de la zona qu vamos a consultar
          				intent.putExtra("LATITUDE_NEW_ZONE", newZoneMarker.getPosition().getLatitude());
          				intent.putExtra("LONGITUDE_NEW_ZONE", newZoneMarker.getPosition().getLongitude());
          				
          				intent.putExtra("INFO_NEW_ZONE", infoNewZone);
          		
          				myOpenMapView.getContext().startActivity(intent);
                      
          				//Toast.makeText(ActivityPrincipal.this,"Info ontenida! "+city, Toast.LENGTH_SHORT).show();
      	        
      	        }
      	    }
      	}
    	
    //****************
    	
    	
	@Override
	protected void onResume() {
		super.onResume();
		//Proveedor de ubicación,refresco mínimo,metros mínimos, listener
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mUpdateHandler);
		
/*		Location location = null;
		for (String provider : locationManager.getProviders(true))
        {
        	
            location = locationManager.getLastKnownLocation(provider);
            if (location != null)
            {
                //location.setLatitude(MAP_DEFAULT_LATITUDE);
                //location.setLongitude(MAP_DEFAULT_LONGITUDE);
                locationManager.requestLocationUpdates(provider, 1000, 0, mUpdateHandler);
                break;
            }
        }*/
		
	}
	@Override
		protected void onPause() {
			super.onPause();
			//El GPS consume muchos recursos, mejor pararlo cuando no lo vayamos
			//a utilizar
	//		locationManager.removeUpdates(mUpdateHandler);
		}


							
    	
}


