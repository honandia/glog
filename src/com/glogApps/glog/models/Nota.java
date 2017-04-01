package com.glogApps.glog.models;

public class Nota {
	private long id;
	private String texto;

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
	
	@Override
	public String toString(){
		return texto;
	}
}
