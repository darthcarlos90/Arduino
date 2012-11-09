package com.Objetos;

public class Rutina {
	private String rutina;
	private int id;
	private String nombre;

	public Rutina(String rutina, int id) {
		this.rutina = rutina;
		this.id = id;
		nombre = "Rutina " + id;
	}
	
	public Rutina (String rutina){
		this.rutina = rutina;
		nombre = null;
	}

	public String getRutina() {
		return rutina;
	}

	public void setRutina(String rutina) {
		this.rutina = rutina;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		setNombre();
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre() {
		nombre = "Rutina " + id;
	}
}
