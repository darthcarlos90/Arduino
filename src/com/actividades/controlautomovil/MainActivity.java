package com.actividades.controlautomovil;

import com.actividades.controlautomovil.BluetoothActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Clase que representa la Actividad que se cargar� al momento en que empiece la
 * aplicaci�n.
 * 
 * @author Carlos Tirado
 * 
 */
public class MainActivity extends BluetoothActivity {

	private CarritoManagerApplication bma;

	/**
	 * M�todo que se hereda de la clase Activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setup();
	}

	/**
	 * M�todo que establece la conexi�n con la clase Application.
	 */
	private void setup() {
		bma = (CarritoManagerApplication) getApplication();

	}

	/**
	 * M�todo que se llama cuando se oprime el bot�n con la opci�n Manual.
	 * 
	 * @param v
	 *            El bot�n al que se le asign� este m�todo.
	 */
	public void manual(View v) {
		/* En caso de que la conexi�n no se hay creado */
		if (bma.connectionCreated() == false) {
			// Notificar al usuario
			showMessage("Necesitas Empezar una conexi�n primero.");
			/* Elmpezar la actividad para realizar una conexi�n. */
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
		} else {
			/* De otra manera empezar la actividad de control manual. */
			Intent intent = new Intent(this, ManualActivity.class);
			startActivity(intent);
		}

	}

	/**
	 * M�todo llamado cuando se oprime el bot�n Autom�tico.
	 * 
	 * @param v
	 *            El bot�n al que se le asignar� este m�todo.
	 */
	public void automatico(View v) {
		/*
		 * En caso de que no se haya creado la conexi�n, mandar al menu de crear
		 * conexiones.
		 */
		if (bma.connectionCreated() == false) {
			showMessage("Necesitas Empezar una conexi�n primero.");
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
			/* En caso contrario, empezar la actividad de control autom�tico. */
		} else {
			Intent intent = new Intent(this, AutomaticoActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * Cuando se oprima el bot�n de menu, este m�todo cargara el men� llamado
	 * activity_main.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Este m�todo decidir� que hacer en caso de que se haya precionado un
	 * bot�n.
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