package com.glogApps.glog.maps;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.views.MapView;

import com.glogApps.glog.ZoneActivity;
import com.glogApps.glog.models.Zone;

import android.content.Intent;
import android.view.MotionEvent;

public class MyMarker extends Marker{
	

	public MyMarker(MapView mapView) {
		super(mapView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onLongPress(MotionEvent event, MapView mapView) {
		
		
		Intent intent = new Intent(mapView.getContext(),ZoneActivity.class);
		
		Zone zone = (Zone) this.getRelatedObject();
		
		//pasamos el ID y el nombre de la zona qu vamos a consultar
		intent.putExtra("ZONE_ID", zone._id);
		intent.putExtra("ZONE_NAME", zone.name);
	
		mapView.getContext().startActivity(intent);
		
		
		return super.onLongPress(event, mapView);
	}
	
	
	
	
	
	

}
