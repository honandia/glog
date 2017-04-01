package com.gordApps.glog;

import java.util.Date;

public class Comment {
	public String text;
	public Date date;
	public String zone_id;
	public String user_id;
	
	public Comment(String text, Date date,String zone_id ,String user_id) {
		super();
		this.text = text;
		this.date = date;
		this.zone_id = zone_id;
		this.user_id = user_id;
	}
	public Comment() {
		super();
	}

}
