package com.glogApps.glog.maps;

import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.TextView;


import com.gordApps.glog.R;

public class MyMarkerCustomInfoWindow extends MarkerInfoWindow {

	public MyMarkerCustomInfoWindow(int layoutResId, MapView mapView) {
		super(layoutResId, mapView);
		// TODO Auto-generated constructor stub
	}
	
	public MyMarkerCustomInfoWindow(MapView mapView, String user_id, String comment, String date) {
        super(R.layout.my_bonuspack_bubble, mapView);
        
        	
	       
	        
	      //Comment
	        TextView lText=(TextView)mView.findViewById(R.id.textCommentMB);
	        lText.setText(comment);
	        //TextView lText=(TextView)item.findViewById(R.id.textCommentMB);
			
			//text.setText(arrayComments.get(position).text);
			
			//Date
			TextView lDate=(TextView)mView.findViewById(R.id.dateCommentMB);
			lDate.setText(date);
			
			//User
			TextView lUser=(TextView)mView.findViewById(R.id.userCommentMB);
			lUser.setText(user_id);
		
        	/*LayoutInflater inflater = LayoutInflater.from(context);
	        
			int id = R.layout.comment;

	        TableLayout tableLayout = (TableLayout) inflater.inflate(id, null, false);
	 
	        TextView textComment = (TextView) tableLayout.findViewById(R.id.textComment);
	        textComment.setText(comment);
	        
	        TextView textUser_id = (TextView) tableLayout.findViewById(R.id.userComment);
	        textUser_id.setText(user_id);
	        
	        TextView textDate = (TextView) tableLayout.findViewById(R.id.dateComment);
	        textDate.setText(date);
	        
	        LayoutInflater inflater2 = LayoutInflater.from(mView.getContext());
	        LinearLayout linearLayout = (LinearLayout) inflater2.inflate(R.layout.bonuspack_bubble, null, false);
        
	        //linearLayout.addView(tableLayout);
	        linearLayout.addView(new Button(context));*/
       
	        

        
	}
	

}
