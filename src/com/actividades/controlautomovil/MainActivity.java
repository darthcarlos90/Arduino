package com.actividades.controlautomovil;

import com.actividades.controlautomovil.BluetoothActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Clase que representa la Actividad que se cargará al momento en que empiece la
 * aplicación.
 * 
 * @author Carlos Tirado
 * 
 */
public class MainActivity extends BluetoothActivity {

	private CarritoManagerApplication bma;

	/**
	 * Método que se hereda de la clase Activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setup();
	}

	/**
	 * Método que establece la conexión con la clase Application.
	 */
	private void setup() {
		bma = (CarritoManagerApplication) getApplication();

	}

	/**
	 * Método que se llama cuando se oprime el botón con la opción Manual.
	 * 
	 * @param v
	 *            El botón al que se le asignó este método.
	 */
	public void manual(View v) {
		/* En caso de que la conexión no se hay creado */
		if (bma.connectionCreated() == false) {
			// Notificar al usuario
			showMessage("Necesitas Empezar una conexión primero.");
			/* Elmpezar la actividad para realizar una conexión. */
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
		} else {
			/* De otra manera empezar la actividad de control manual. */
			Intent intent = new Intent(this, ManualActivity.class);
			startActivity(intent);
		}

	}

	/**
	 * Método llamado cuando se oprime el botón Automático.
	 * 
	 * @param v
	 *            El botón al que se le asignará este método.
	 */
	public void automatico(View v) {
		/*
		 * En caso de que no se haya creado la conexión, mandar al menu de crear
		 * conexiones.
		 */
		if (bma.connectionCreated() == false) {
			showMessage("Necesitas Empezar una conexión primero.");
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
			/* En caso contrario, empezar la actividad de control automático. */
		} else {
			Intent intent = new Intent(this, AutomaticoActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * Cuando se oprima el botón de menu, este método cargara el menú llamado
	 * activity_main.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Este método decidirá que hacer en caso de que se haya precionado un
	 * botón.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_settings:
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
			break;

		case R.id.menu_closeConnection:
			endConnection();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}