package com.Objetos;

/**
 * Clase que representa las rutinas guardadas.
 * 
 * @author Carlos Tirado
 * 
 */
public class Rutina {

	/**
	 * La rutina consta de 3 características, la rutina a ralizar, el id de la
	 * rutina, y el nombre que se le asigna a cada rutina.
	 */
	private String rutina;
	private int id;
	private String nombre;

	/**
	 * Constructor de la clase Rutina.
	 * 
	 * @param rutina
	 *            La rutina a realizar
	 * @param id
	 *            El id de la rutina
	 */
	public Rutina(String rutina, int id) {
		this.rutina = rutina;
		this.id = id;
		nombre = "Rutina " + id;
	}

	/**
	 * Constructor vacío que sólo guarda la rutina a realizar.
	 * 
	 * @param rutina
	 *            La rutina a realizar.
	 */
	public Rutina(String rutina) {
		this.rutina = rutina;
		nombre = null;
	}

	/**
	 * Método que regresa la rutina a ejecutar.
	 * 
	 * @return La rutina a ejecutar.
	 */
	public String getRutina() {
		return rutina;
	}

	/**
	 * Método que le asigna la rutina a ejecutar.
	 * 
	 * @param rutina
	 *            La rutina a ejecutar.
	 */
	public void setRutina(String rutina) {
		this.rutina = rutina;
	}

	/**
	 * Método que regresa el Id de la rutina.
	 * 
	 * @return El id de la rutina.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método que guarda el ID de la rutina.
	 * 
	 * @param id
	 *            EL Id a asignar a la rutina.
	 */
	public void setId(int id) {
		this.id = id;
		setNombre();
	}

	/**
	 * Método que regresa el nombre de la rutina.
	 * 
	 * @return EL nombre de la rutina.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que le asigna un nombre por default a la rutina.
	 */
	private void setNombre() {
		nombre = "Rutina " + id;
	}
}
