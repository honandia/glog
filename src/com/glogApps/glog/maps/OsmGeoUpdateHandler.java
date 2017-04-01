package com.glogApps.glog.maps;

import org.osmdroid.util.GeoPoint;

import com.glogApps.glog.ActivityPrincipal;
import com.gordApps.glog.R;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

public class OsmGeoUpdateHandler implements LocationListener
{

	int precisionOptima=10;//metros
	int precisionMinimaRequerida=30;//metros
	
    private ActivityPrincipal mMapActivity;

    public OsmGeoUpdateHandler(ActivityPrincipal aMapActivity)
    {
        this.mMapActivity = aMapActivity;

    }

    @Override
    public void onLocationChanged(Location location)
    {
        
    	Toast.makeText(mMapActivity,
                "latitude = " + location.getLatitude() * 1e6 + " longitude = " + location.getLongitude() * 1e6,
                Toast.LENGTH_SHORT).show();

        int latitude = (int) (location.getLatitude() * 1E6);
        int longitude = (int) (location.getLongitude() * 1E6);
        GeoPoint point = new GeoPoint(latitude, longitude);
        
        // Es llamado cuando cambia la ubicación
		
     		float precision=location.getAccuracy();
     		//x=Double.toString(location.getLatitude());
     		//y=Double.toString(location.getLongitude());
     		
     		//Comprobar precisión
     		if (precision>=precisionMinimaRequerida){
     			//Aún no tenemos la precisión mínima
     			//this.txtCoordenadas.setText(x+","+y);
     			//this.txtPrecision.setText(precision+" metros");
     			
     		}else{
     			//Ya lo tenemos, a compartiiiiir!
     			//linearLayoutCompartir.setVisibility(View.VISIBLE);
     			
     			
     		}
     	  mMapActivity.updateAreaPosition(point);
  //      mMapActivity.updateCarPosition(point); //actualizar mapa, marcadores etc
    }

    @Override
    public void onProviderDisabled(String provider)
    {
    	// Cuando se deshabilita el GPS
		
    			//Le reenviamos a los ajustes de GPS de Android
    			Intent intent=new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    			mMapActivity.startActivity(intent);
    			Toast.makeText(mMapActivity, "Habilite el GPS y regrese a "+mMapActivity.getResources().getString(R.string.app_name), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderEnabled(String provider)
    {
    	// Cuando se habilita el GPS
    	Toast.makeText(mMapActivity, "El GPS ha sido Habilitado :)", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    	//Es llamado cuando cambia el estado del GPS
		
    			switch (status) {
	    			case LocationProvider.AVAILABLE:
	    				Toast.makeText(mMapActivity, "El GPS está dandolo todo :)", Toast.LENGTH_LONG).show();
	    				break;
	    			case LocationProvider.OUT_OF_SERVICE:
	    				Toast.makeText(mMapActivity, "El GPS no está disponible :(", Toast.LENGTH_LONG).show();
	    				break;	
	    			default:
	    				break;
    			}	
    }

}
