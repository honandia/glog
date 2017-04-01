package com.glogApps.glog.models;

import java.util.Date;

import android.text.format.Time;

public class Comment {
	private String text;
	private Time date;
	private String user_id;
	
	//getters setters
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Time getDate() {
		return date;
	}
	public void setDate(Time date) {
		this.date = date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	//constructors
	public Comment(String text, Time date,String user_id) {
		super();
		this.text = text;
		this.date = date;
		this.user_id = user_id;
	}
	public Comment() {
		super();
	}

}
