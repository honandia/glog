package com.gordApps.glog;

import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;

import android.content.Context;
import android.graphics.Paint.Style;

public class Area {
	
	//Representa un area
	private int alcance;
	
	private GeoPoint ptIS;
	private GeoPoint ptDI;
	private GeoPoint ptDS;
	private GeoPoint ptII;
	
	//getters setters
	public int getAlcance() {
		return alcance;
	}
	public void setAlcance(int alcance) {
		this.alcance = alcance;
	}
	public GeoPoint getPtII() {
		return ptII;
	}
	public void setPtII(GeoPoint ptII) {
		this.ptII = ptII;
	}
	public GeoPoint getPtIS() {
		return ptIS;
	}
	public void setPtIS(GeoPoint ptIS) {
		this.ptIS = ptIS;
	}
	public GeoPoint getPtDI() {
		return ptDI;
	}
	public void setPtDI(GeoPoint ptDI) {
		this.ptDI = ptDI;
	}
	public GeoPoint getPtDS() {
		return ptDS;
	}
	public void setPtDS(GeoPoint ptDS) {
		this.ptDS = ptDS;
	}
	//constructors
	public Area(int alcance, GeoPoint ptIS, GeoPoint ptDI, GeoPoint ptDS,
			GeoPoint ptII) {
		super();
		this.alcance = alcance;
		this.ptIS = ptIS;
		this.ptDI = ptDI;
		this.ptDS = ptDS;
		this.ptII = ptII;
	}
	
	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Area (int alcance, GeoPoint ptActual){
		
		this.alcance = alcance;
		
		this.ptII= new GeoPoint(ptActual.getLatitudeE6()+alcance, ptActual.getLongitudeE6()-alcance);
        
        this.ptIS= new GeoPoint(ptActual.getLatitudeE6()-alcance, ptActual.getLongitudeE6()-alcance);
        
        this.ptDI= new GeoPoint(ptActual.getLatitudeE6()+alcance, ptActual.getLongitudeE6()+alcance);
        
        this.ptDS= new GeoPoint(ptActual.getLatitudeE6()-alcance, ptActual.getLongitudeE6()+alcance);
		
	}
	
	//area drawing method
	public void draw(MapView myOpenMapView, int areaColor, Context mContext){
		
		PathOverlay myOverlay= new PathOverlay(areaColor, mContext);
	    myOverlay.getPaint().setStyle(Style.FILL);
	    myOverlay.getPaint().setAlpha(128);

	    
	    myOverlay.addPoint(this.ptII);
	    myOverlay.addPoint(this.ptDI);
	    myOverlay.addPoint(this.ptDS);
	    myOverlay.addPoint(this.ptIS);
	    myOverlay.addPoint(this.ptII);

	    myOpenMapView.getOverlays().add(myOverlay);
	    myOpenMapView.invalidate();

	}
	
	//checks if geopoint is in area
	
	public boolean checkGeoPointInArea(GeoPoint point){
		
		boolean pointInArea = false;
		 	 
		 
		 if((point.getLatitude()>this.getPtIS().getLatitude()
			&& point.getLatitude()<this.getPtDI().getLatitude())
		&& (point.getLongitude()>this.getPtIS().getLongitude()
			&& point.getLongitude()<this.getPtDI().getLongitude()))
		{
			//point is in area
			pointInArea = true;
		}

		return pointInArea;

	}
    
    
	
	

}
