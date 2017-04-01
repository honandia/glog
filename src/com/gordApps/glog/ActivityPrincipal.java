package com.gordApps.glog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.ExtendedOverlayItem;
import org.osmdroid.bonuspack.overlays.ItemizedOverlayWithBubble;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Style;
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

public class ActivityPrincipal extends Activity {

	    
	//MapView mapView;
    LinearLayout searchPanel;
    Button searchButton;
    EditText searchText;
   
    public static final int SEARCH_ID = Menu.FIRST;

    private MapView myOpenMapView;
    private MapController mapController;
    
    LocationManager locationManager;
    
    ArrayList<OverlayItem> overlayItemArray;
    
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
    	
         	locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
         	// getLastKnownLocation desde GPS, y no del NETWORK. 
      /*  	Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    		if(lastLocation != null){
    		updateLoc(lastLocation);
    		}
         	 */  	
    		
    		//Obtener el area
    		//Obtener las zonas dentro del area
    	
    	//Dibujar
	    	 myOpenMapView = (MapView)findViewById(R.id.mapView);
	         myOpenMapView.setBuiltInZoomControls(false);	
	         
	       //Pintar area
	         mapController = (MapController) myOpenMapView.getController();
	         mapController.setZoom(18);	
	         
	         GeoPoint startPoint = new GeoPoint(43.38110, -3.21863);
	         
	        
	         
	       //Centre map
	         mapController.setCenter(startPoint);	
	         //limitar el panning
	 //        BoundingBoxE6 bbox = new BoundingBoxE6(43.38110, -3.21863, 43.38110, -3.21863);
	 //        myOpenMapView.setScrollableAreaLimit(myOpenMapView.getBoundingBox());
	         					
         	
    	
    		//Pintar zonas
	         
	         //pruebas pintando sonas ...
	         overlayItemArray = new ArrayList<OverlayItem>();        
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
	         
	         
	         myOpenMapView.getOverlays().add(overlay);

	         //**************************
	  /*       Marker marker = new Marker(myOpenMapView);
	         marker.setPosition(new GeoPoint(43.38210, -3.21893));
	         marker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
	         
	         marker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
	         marker.setTitle("Zone");
	         marker.setSubDescription("Nombre de la zona");
	         marker.setAlpha((float)0.5);
	         */
	         

	         //GeoPoint pt1= startPoint;
	         
	         //Determinar el area de acción
	         int alcance=1000;
	         
	         GeoPoint ptII= new GeoPoint(startPoint.getLatitudeE6()-alcance, startPoint.getLongitudeE6()-alcance);
	         GeoPoint ptIS= new GeoPoint(startPoint.getLatitudeE6()-alcance, startPoint.getLongitudeE6()+alcance);
	         GeoPoint ptDI= new GeoPoint(startPoint.getLatitudeE6()+alcance, startPoint.getLongitudeE6()-alcance);
	         GeoPoint ptDS= new GeoPoint(startPoint.getLatitudeE6()+alcance, startPoint.getLongitudeE6()+alcance);

	         
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
	         myOpenMapView.getOverlays().add(startMarker);
	            
	         startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
	         startMarker.setTitle("Zone");
	         startMarker.setAlpha((float)0.5);
	         startMarker.setSubDescription("Nombre de la zona");
	            
	         myOpenMapView.invalidate();//refrescar el mapa	
    	
    	//Eventos
    		//Al hacer click en alguna de las zonas disponibles se accederá al log correspondiente
    	
           
            
            searchPanel = (LinearLayout) findViewById(R.id.searchPanel);
            searchButton = (Button) findViewById(R.id.searchButton);
            searchText = (EditText) findViewById(R.id.searchText);
            
           
            
            //myOpenMapView.setClickable(false);
            
            //myOpenMapView.setMultiTouchControls(false);
            
            
            //myOpenMapView.setEnabled(false);
            //myOpenMapView.getController().stopPanning();
                  
      
            
            
            //mapController.stopPanning();
           
            
            
            
           // GeoPoint startPoint = new GeoPoint(actualLocation.getLatitude(), actualLocation.getLongitude());
 
            
            
            
            
           // mapController.stopPanning();
            
            /*final ArrayList<ExtendedOverlayItem> osmPois = new ArrayList<ExtendedOverlayItem>();
            ItemizedOverlayWithBubble<ExtendedOverlayItem> osmPoisOverlay = new ItemizedOverlayWithBubble<ExtendedOverlayItem>(this, osmPois, myOpenMapView, new OsmInfoBubble(myOpenMapView));
            myOpenMapView.getOverlays().add(osmPoisOverlay);*/
     
            
            
            
            
            
            
            
            //**************
      /*      LocationManager locationManager = (LocationManager) 
            getSystemService(Context.LOCATION_SERVICE);
            
            //tvLongitude=(TextView)findViewById(R.id.lng);
            //tvLatitude=(TextView)findViewById(R.id.lat);

            locationManager =(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            Criteria c=new Criteria(); 
            //if we pass false than
            //it will check first satellite location than Internet and than Sim Network
            String provider=locationManager.getBestProvider(c, false);
            Location location=locationManager.getLastKnownLocation(provider);
            if(location!=null)
            {
                double lng=location.getLongitude();
                double lat=location.getLatitude();
                
                gPt.setLatitudeE6((int)lat);
                gPt.setLongitudeE6((int)lng);
                mapController.setCenter(gPt);
                //tvLongitude.setText(""+lng);
                //tvLatitude.setText(""+lat);
            }
            else
            {
                //tvLongitude.setText("No Provider");
                //tvLatitude.setText("No Provider");
            }
            
            
           // LocationManager milocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
           // LocationListener milocListener = new MiLocationListener();
            //milocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, milocListener);
            
            /*public class MiLocationListener implements LocationListener
            {
            public void onLocationChanged(Location loc)
            {
            loc.getLatitude();
            loc.getLongitude();
            String coordenadas = “Mis coordenadas son: ” + “Latitud = ” + loc.getLatitude() + “Longitud = ” + loc.getLongitude();
            Toast.makeText( getApplicationContext(),coordenadas,Toast.LENGTH_LONG).show();
            */
            
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

    	@Override
        public boolean onOptionsItemSelected(MenuItem item) {
            boolean result = super.onOptionsItemSelected(item);

            switch (item.getItemId()) {
                case SEARCH_ID:
                    searchPanel.setVisibility(View.VISIBLE);
                    break;
            }

            return result;
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
    
}


