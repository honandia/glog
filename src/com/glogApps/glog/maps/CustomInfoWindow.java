package com.glogApps.glog.maps;

import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

import android.view.View;
import android.widget.Button;

import com.gordApps.glog.R;

public class CustomInfoWindow extends MarkerInfoWindow {
    
	public Button btn;
	
	public CustomInfoWindow(MapView mapView) {
            super(R.layout.bonuspack_bubble, mapView);
            
            btn = (Button)(mView.findViewById(R.id.bubble_moreinfo));
    }
	
    
       
    
    @Override public void onOpen(Object item){
        super.onOpen(item);
        mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
    }
    
    
    public void setButtonVisible(boolean visible){
    	if (visible)
    	{
    		mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
    	}
    	else
    	{
    		mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.INVISIBLE);
    	}
    	
    }
    
   
    
 
    
}