package com.actividades.controlautomovil;

import com.Objetos.Rutina;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgregarRutinaActivity extends Activity {

	private Button add;
	private String rutina;
	private Button adelante;
	private Button atras;
	private Button derecha;
	private Button izq;
	private Button abrir;
	private Button cerrar;
	private CarritoManagerApplication cma;
	private boolean hayPinzas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_rutina);
		cma = (CarritoManagerApplication) getApplication();

		setupViews();
	}

	private void setupViews() {
		hayPinzas = cma.tienePinzas();
		rutina = new String();
		add = (Button) findViewById(R.id.agregar_rutina_btn);
		add.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Rutina r = new Rutina(rutina);
				cma.agregaRutina(r);
				finish();
			}
		});
		adelante = (Button) findViewById(R.id.agregar_adelante_btn);
		adelante.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "AAAAA";
			}
		});
		atras = (Button) findViewById(R.id.agregar_atras_btn);
		atras.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "BBBBB";
			}
		});
		derecha = (Button) findViewById(R.id.agregar_der_btn);
		derecha.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "DDDDD";
			}
		});
		izq = (Button) findViewById(R.id.agregar_izq_btn);
		izq.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "IIIII";
			}
		});
		abrir = (Button) findViewById(R.id.agregar_abre_pinza_btn);
		cerrar = (Button) findViewById(R.id.agregar_cerrar_pinza_btn);

		if (hayPinzas == false) {
			abrir.setVisibility(View.GONE);
			cerrar.setVisibility(View.GONE);
		}

	}

}
