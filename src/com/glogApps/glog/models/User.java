package com.glogApps.glog.models;

import android.graphics.drawable.BitmapDrawable;

public class User {
	
	private String name;
	private String password;
	private BitmapDrawable avatar;
	
	//getters setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BitmapDrawable getAvatar() {
		return avatar;
	}
	public void setAvatar(BitmapDrawable avatar) {
		this.avatar = avatar;
	}
	
	
	//constructors
	public User(String name, String password, BitmapDrawable avatar) {
		super();
		this.name = name;
		this.password = password;
		this.avatar = avatar;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
