package com.actividades.controlautomovil;

import com.actividades.controlautomovil.BluetoothActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends BluetoothActivity {

	private CarritoManagerApplication bma;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setup();
	}

	private void setup() {
		bma = (CarritoManagerApplication) getApplication();

		
	}

	public void manual(View v) {
		if (bma.connectionCreated() == false) {
			showMessage("Necesitas Empezar una conexión primero.");
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, ManualActivity.class);
			startActivity(intent);
		}

	}

	public void automatico(View v) {
		if (bma.connectionCreated() == false) {
			showMessage("Necesitas Empezar una conexión primero.");
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, AutomaticoActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

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