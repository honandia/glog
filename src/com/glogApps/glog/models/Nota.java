package com.glogApps.glog.models;

public class Nota {
	private long id;
	private String texto;
	private String user;
	private String date;
	private String gLog;
	private String descGLog;
	private String placeGLog;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String gettexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getgLog() {
		return gLog;
	}

	public void setgLog(String gLog) {
		this.gLog = gLog;
	}

	public String getDescGLog() {
		return descGLog;
	}

	public void setDescGLog(String descGLog) {
		this.descGLog = descGLog;
	}

	public String getPlaceGLog() {
		return placeGLog;
	}

	public void setPlaceGLog(String placeGLog) {
		this.placeGLog = placeGLog;
	}

	@Override
	public String toString(){
		return texto;
	}
}
