package com.gordApps.glog;

import java.util.List;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyOwnItemizedOverlay extends ItemizedIconOverlay<OverlayItem> {
	
	protected Context mContext;

	public MyOwnItemizedOverlay(final Context context, final List<OverlayItem> aList) {
	     super(context, aList, new OnItemGestureListener<OverlayItem>() {
	            @Override public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
	                    return false;
	            }
	            @Override public boolean onItemLongPress(final int index, final OverlayItem item) {
	                    return false;
	            }
	          } );
	    // TODO Auto-generated constructor stub
	     mContext = context;
	}
	
	@Override 
	protected boolean onSingleTapUpHelper(final int index, final OverlayItem item, final MapView mapView) {
	    //Toast.makeText(mContext, "Item " + index + " has been tapped!", Toast.LENGTH_SHORT).show();
	
	/*	AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	    dialog.setTitle(item.getTitle());
	    dialog.setMessage(item.getSnippet());
	    
	    dialog.show();*/
	    
	    
	    Intent intent = new Intent(mContext,ZoneActivity.class);
	    mContext.startActivity(intent);
	    
		
	//	Intent intent = new Intent(this, DisplayMessageActivity.class);
	    return true;
	}
}