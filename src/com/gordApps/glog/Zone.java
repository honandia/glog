package com.gordApps.glog;

import java.util.ArrayList;

public class Zone {

	

		public Double latitude;
		public Double longitude;
		public String name;
		public String desc;
		
		public ArrayList<Comment> comments;
		
		public String _id;


		public Zone(Double latitude, Double longitude, String name, String desc,
				ArrayList<Comment> comments, String _id) {
			super();
			this.latitude = latitude;
			this.longitude = longitude;
			this.name = name;
			this.desc = desc;
			this.comments = comments;
			this._id = _id;
		}

		public Zone() {
			super();
		}
}
			

