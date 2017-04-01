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
		
		
		

		public String get_id() {
			return _id;
		}

		public void set_id(String _id) {
			this._id = _id;
		}

		public Double getLatitude() {
			return latitude;
		}

		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

		public Double getLongitude() {
			return longitude;
		}

		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getLastCommentText() {
			return lastCommentText;
		}

		public void setLastCommentText(String lastCommentText) {
			this.lastCommentText = lastCommentText;
		}

		public String getLastCommentDate() {
			return lastCommentDate;
		}

		public void setLastCommentDate(String lastCommentDate) {
			this.lastCommentDate = lastCommentDate;
		}

		public String getLastCommentUser_id() {
			return lastCommentUser_id;
		}

		public void setLastCommentUser_id(String lastCommentUser_id) {
			this.lastCommentUser_id = lastCommentUser_id;
		}

		public ArrayList<Comment> getComments() {
			return comments;
		}

		public void setComments(ArrayList<Comment> comments) {
			this.comments = comments;
		}

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
			

