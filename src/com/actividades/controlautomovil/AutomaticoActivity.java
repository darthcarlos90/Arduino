package com.actividades.controlautomovil;

import java.io.IOException;
import java.util.ArrayList;

import com.Objetos.Rutina;
import com.actividades.controlautomovil.BluetoothActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Clase que se encarga de ejecutar las rutinas pre-definidas.
 * 
 * @author Carlos Tirado
 * 
 */
public class AutomaticoActivity extends BluetoothActivity {

	private CarritoManagerApplication cma;
	private Button playDefault;
	private static final String RUTINA_DEFAULT = "R";
	private Button agregaRutina;
	private ListView rutinasLv;
	private ArrayList<Rutina> rutinas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.automatico);
		cma = (CarritoManagerApplication) getApplication();
		setUpViews();
	}

	/**
	 * Cuando la actividad regrese a la pantalla principal, que se actualice la
	 * lista de la base de datos.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		loadList();
	}

	/**
	 * Cuando la actividad empiece, actualizar la lista con la base de datos.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		loadList();
	}

	/**
	 * Cuando se recarge la actividad, actualizar la lista con la base de datos.
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		loadList();
	}

	/**
	 * Cuando se reinicie la actividad, actualizar la lista con la base de
	 * datos.
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		loadList();
	}

	/**
	 * Método que recarla la lista haciendo un query hacia la base de datos.
	 */
	private void loadList() {
		rutinas = cma.getRutinas();// Método que de la Aplicación que realiza el
									// query a la base de datos.
		/* Se guardan los resultados del query en un array de Strings. */
		String[] nombres = new String[rutinas.size()];
		for (int i = 0; i < rutinas.size(); i++) {
			nombres[i] = rutinas.get(i).getNombre();
		}
		/*
		 * Se carga el array de strings en el ArrayAdapter para mostrarse en
		 * pantalla.
		 */
		ArrayAdapter aa = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, nombres);
		rutinasLv.setAdapter(aa);
	}

	/**
	 * Método que analiza el String de entrada que contiene la rutina, y empieza
	 * a mandar los pasos de la rutina.
	 * 
	 * @param rutina
	 *            La rutina que se va a ejecutar
	 */
	private void execRutina(String rutina) {
		/* Un for para analizar cada uno de los caracteres de la rutina */
		for (int i = 0; i < rutina.length(); i++) {
			/* Guardar el caracter en la posición i en la variable toSend */
			char toSend = rutina.charAt(i);
			try {
				/*
				 * Se usa el método get Socket para tomar el elemento socket de
				 * la conexión, y se manda el caracter vía bluetooth.
				 */
				cma.getSocket().getOutputStream().write(toSend);
				// Delay para que el Arduino analice el caracter y lo ejecute.
				Thread.sleep(200);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Método que inicializa los elementos de la interfaz.
	 */
	private void setUpViews() {
		/* ListView donde se van a mostrar las rutinas. */
		rutinasLv = (ListView) findViewById(R.id.rutinas);
		/*
		 * Set on Item click listener nos dice que hacer cuando le den click a
		 * cada elemento de la lista, en ste caos, ejecutan la rutina.
		 */
		rutinasLv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// Guardar la rutina en una variable
				Rutina r = rutinas.get(arg2);
				String rutina = r.getRutina();
				// Ejecutar la rutina
				execRutina(rutina);

			}
		});
		/* Ejecutar la rutina por default, que está guardada en el Arduino. */
		playDefault = (Button) findViewById(R.id.ejecutar_rutina_default);
		playDefault.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				execRutina(RUTINA_DEFAULT);
			}
		});
		/*
		 * Botón que inicializa la actividad donde se dan de alta las rutinas en
		 * la base de datos.
		 */
		agregaRutina = (Button) findViewById(R.id.add_rutina);
		agregaRutina.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(AutomaticoActivity.this,
						AgregarRutinaActivity.class);
				startActivity(intent);

			}
		});
		// Método que actualiza la lista con la base de datos.
		loadList();

	}

	/**
	 * Método que crea usa el menu menu_auto.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_auto, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Método que dice que hacer cuando opriman las opciones del Menú.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/* Este botón te lleva a la actividad de opciones del bluetooth */
		case R.id.menu_settings:
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
			break;
		/* Esta opción termina la conexión con el dispositivo Bluetooth. */
		case R.id.menu_closeConnection:
			endConnection();
			finish();
			break;
		/* Esta opción te lleva a la opción de manejar el carrito manualmente. */
		case R.id.goto_manual:
			Intent intent2 = new Intent(this, ManualActivity.class);
			startActivity(intent2);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
