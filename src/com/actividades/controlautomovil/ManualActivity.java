package com.actividades.controlautomovil;

import java.io.IOException;

import com.actividades.controlautomovil.BluetoothActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Clase que representa el control manual.
 * 
 * @author Carlos Tirado
 * 
 */
public class ManualActivity extends BluetoothActivity {

	private CarritoManagerApplication bma;
	private Button adelante;
	private Button atras;
	private Button stahp;
	private Button izq;
	private Button der;
	private Button abrir;
	private Button cerrar;
	private Button pito;

	/**
	 * Método que se corre en cuando se crea la actividad.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual);
		setupViews();
	}

	/**
	 * Método que carga los elementos de la interfaz.
	 */
	private void setupViews() {
		bma = getApplicationManager();

		/*
		 * Botón adelante, manda un mensaje de que el carro se mueva hacia
		 * adelante al tocar el botón.
		 */
		adelante = (Button) findViewById(R.id.adelante_btn);
		adelante.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				try {
					bma.getSocket().getOutputStream().write('A');
				} catch (IOException e) {
					showMessage("No se pudo mover hacia adelante :(");
					e.printStackTrace();
				}
				return true;
			}
		});

		/*
		 * Botón atras, manda una señal de que el carrito debe de moverse hacia
		 * atrás.
		 */
		atras = (Button) findViewById(R.id.atras_btn);
		atras.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				try {
					bma.getSocket().getOutputStream().write('B');
				} catch (IOException e) {
					showMessage("No se pudo Mover hacia atras! :(");
					e.printStackTrace();
				}
				return true;
			}
		});

		/*
		 * Botón de parar, cierra la conexión y termina la actividad cuando se
		 * oprime el botón.
		 */
		stahp = (Button) findViewById(R.id.stop_btn);
		stahp.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				endConnection();
				finish();

			}
		});

		/*
		 * Botón de izquierda, manda una señal al carrito de que se mueva hacia
		 * la izquierda.
		 */
		izq = (Button) findViewById(R.id.izq_btn);
		izq.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				try {
					bma.getSocket().getOutputStream().write('I');
				} catch (IOException e) {
					showMessage("No se pudo mover a la izquierda :(");
					e.printStackTrace();
				}
				return true;
			}
		});

		/*
		 * Botón derecha, manda una señal al carrito de que se mueva a la
		 * derecha.
		 */
		der = (Button) findViewById(R.id.der_btn);
		der.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				try {
					bma.getSocket().getOutputStream().write('D');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});

		/* Botón abrir pinza, manda una señal al carrito de que abra la pinza. */
		abrir = (Button) findViewById(R.id.abre_pinza_btn);
		abrir.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				try {
					bma.getSocket().getOutputStream().write('O');
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});

		/*
		 * Botón de cerrar pinza, el encargado de mandar una señal al carrito de
		 * que cierre la pinza.
		 */
		cerrar = (Button) findViewById(R.id.cerrar_pinza_btn);
		cerrar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				try {
					bma.getSocket().getOutputStream().write('C');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		/*
		 * Botón de claxon, el encargado de hacer que el carrito toque el
		 * claxon.
		 */
		pito = (Button) findViewById(R.id.clackson);
		pito.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				try {
					bma.getSocket().getOutputStream().write('P');
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});

	}

	/**
	 * Método que muestra un mensaje al usuario.
	 */
	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_manual, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(this, CreateConnectionActivity.class);
			startActivity(intent);
			break;

		case R.id.menu_closeConnection:
			endConnection();
			finish();
			break;

		case R.id.goto_auto:
			Intent intent2 = new Intent(this, AutomaticoActivity.class);
			startActivity(intent2);
			break;

		}
		return super.onOptionsItemSelected(item);
	}

}
