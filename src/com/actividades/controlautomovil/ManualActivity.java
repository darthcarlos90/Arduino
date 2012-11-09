package com.actividades.controlautomovil;

import java.io.IOException;

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

public class ManualActivity extends BluetoothActivity {

	private CarritoManagerApplication bma;
	private Button adelante;
	private Button atras;
	private Button stahp;
	private Button izq;
	private Button der;
	private Button abrir;
	private Button cerrar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual);
		setupViews();

	}

	private void setupViews() {
		bma = getApplicationManager();
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

		stahp = (Button) findViewById(R.id.stop_btn);
		stahp.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				endConnection();
				finish();

			}
		});

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
		
		abrir = (Button) findViewById(R.id.abre_pinza_btn);
		abrir.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				try {
					bma.getSocket().getOutputStream().write('O');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		});
		
		cerrar = (Button) findViewById(R.id.cerrar_pinza_btn);
		cerrar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				try {
					bma.getSocket().getOutputStream().write('C');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

	}

	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

}
