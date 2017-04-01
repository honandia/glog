package com.glogApps.glog.models;

import java.util.Date;

public class Comment {
	public String text;
	public String date;
	public String user_id;
	
	public Comment(String text, String date,String user_id) {
		super();
		this.text = text;
		this.date = date;
		this.user_id = user_id;
	}
	public Comment() {
		super();
	}

}
