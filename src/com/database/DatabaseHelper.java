package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Creación de variables para la base de datos
	public static final int VERSION = 1; // Versión de la base de datos
	public static final String DB_NAME = "rutinas.sqlite"; // Nombre del archivo
															// de la base de
															// datos
	public static final String RUTINAS_TABLE = "rutinas"; // Nombre de la tabla
															// que tendrá la
															// base de datos
	public static final String ID = "id"; // Nombre de la primer columna
	public static final String RUTINA = "rutina"; // Nombre de la segunda
													// columna

	/**
	 * Constructor de la clase.
	 * 
	 * @param context
	 *            El contexto bajo el que se creará la clase.
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		dropAndCreate(arg0);

	}

	/**
	 * Elimina y crea de nuevo el objeto base de datos.
	 * 
	 * @param arg0
	 *            El objeto base de datos a usar.
	 */
	private void dropAndCreate(SQLiteDatabase arg0) {
		arg0.execSQL("drop table if exists " + RUTINAS_TABLE + ";");
		createTables(arg0);

	}

	/**
	 * Método que crea la base de datos.
	 * 
	 * @param arg0
	 *            Objeto base de datos que se va a crear.
	 */
	private void createTables(SQLiteDatabase arg0) {
		arg0.execSQL("create table " + RUTINAS_TABLE + " (" + ID
				+ " integer primary key autoincrement not null," + RUTINA
				+ " text not null" + ");");
	}

	/**
	 * Método que se usa cuando se actualiza la base de datos.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// Método no se usará

	}

}
