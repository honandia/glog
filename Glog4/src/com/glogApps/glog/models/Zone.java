package com.glogApps.glog.models;

import java.util.ArrayList;


public class Zone {

	
		public String _id;
	
		public Double latitude;
		public Double longitude;
		public String name;
		public String desc;
		
		public String lastCommentText;
		public String lastCommentDate;
		public String lastCommentUser_id;
		
		public ArrayList<Comment> comments;
		
		


		public Zone(String _id, Double latitude, Double longitude, String name,
				String desc, String lastCommentText, String lastCommentDate,
				String lastCommentUser_id, ArrayList<Comment> comments) {
			super();
			this._id = _id;
			this.latitude = latitude;
			this.longitude = longitude;
			this.name = name;
			this.desc = desc;
			
			this.lastCommentText = lastCommentText;
			this.lastCommentDate = lastCommentDate;
			this.lastCommentUser_id = lastCommentUser_id;
			
			this.comments = comments;
		}

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
			

