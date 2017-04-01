package com.glogApps.glog.models;

import com.glogApps.glog.sqlite.MySQLiteOpenHelper;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

public class User {
	
	private static User mUser = null;
	
	private String name;
	private String password;
	
	private String avatar;
	private String email;
	private String facebook;
	private String twitter;
	private String phone;
	
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	//constructors
	public User(String name, String password, String avatar, String email,
			String facebook, String twitter, String phone) {
		super();
		this.name = name;
		this.password = password;
		this.avatar = avatar;
		this.email = email;
		this.facebook = facebook;
		this.twitter = twitter;
		this.phone = phone;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static User getInstance(Context context){
		if (mUser == null){
			mUser = new User();
		}
		
		return mUser;
	}
		
}
