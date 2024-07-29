package com.example.demo;

public class orologio {
	String modello;
	String tipologia;
	double prezzo;
	String urlImage;
	
	String marca;
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	@Override
	public String toString() {
		return "orologio [modello=" + modello + ", tipologia=" + tipologia + ", prezzo=" + prezzo + ", urlImage="
				+ urlImage + ", marca=" + marca + "]";
	}
	
	
	
	
	
	
	
}
