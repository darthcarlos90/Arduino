package com.actividades.controlautomovil;

import com.Objetos.Rutina;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AgregarRutinaActivity extends Activity {
	/*
	 * Declaración de variables a usar
	 */
	private Button add;
	private String rutina;
	private Button adelante;
	private Button atras;
	private Button derecha;
	private Button izq;
	private Button abrir;
	private Button cerrar;
	private Button pito;
	private CarritoManagerApplication cma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_rutina);
		/*
		 * Se crea la variable Application para compartir la conexión entre
		 * todas las actividades.
		 */
		cma = (CarritoManagerApplication) getApplication();

		setupViews();
	}

	/**
	 * Método para usar el menu llamado menu_cancel
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_cancel, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Método que nos dice que hacer en caso de que opriman cada botón del menú.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Switch de las opciones del menu
		switch (item.getItemId()) {
		case R.id.cancelar:
			// En caso de oprimir la opción cancelar, terminar la actividad
			// actual.
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Método que se encarga de inicializar las variables de la interfaz, y
	 * "decirle qué hacer" a cada uno de los botones.
	 */
	private void setupViews() {
		rutina = new String(); // Variable donde se va a guardar la rutina a
								// ejecutar.
		add = (Button) findViewById(R.id.agregar_rutina_btn);
		// Botón que se encarga de guardar la rutina en la base de datos.
		add.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Rutina r = new Rutina(rutina);
				cma.agregaRutina(r);// Método en la clase
									// CarritoManagerApplication que guarda la
									// rutina en la base de datos
				finish(); // Terminar la actividad
			}
		});
		adelante = (Button) findViewById(R.id.agregar_adelante_btn);
		// Botón que agrega a la rutina un movimiento hacia adelante.
		adelante.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "AAAAA"; // Se concatena movimiento hacia adelante
									// para el string que se guardará en la base
									// de datos.
			}
		});
		atras = (Button) findViewById(R.id.agregar_atras_btn);
		// Botón que agrega el movimiento hacia atrás.
		atras.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "BBBBB";
			}
		});
		derecha = (Button) findViewById(R.id.agregar_der_btn);
		// Botón que agrega el movimiento hacia a la derecha.
		derecha.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "DDDDD";
			}
		});
		izq = (Button) findViewById(R.id.agregar_izq_btn);
		// Botón que agrega movimiento a la izquierda.
		izq.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "IIIII";
			}
		});
		abrir = (Button) findViewById(R.id.agregar_abre_pinza_btn);// Botón que
																	// agrega
																	// abertura
																	// de la
																	// pinza.
		cerrar = (Button) findViewById(R.id.agregar_cerrar_pinza_btn); // Botón
																		// que
																		// agrega
																		// el
																		// cierre
																		// de la
																		// pinza.
		abrir.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "A";

			}
		});

		cerrar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "C";

			}
		});

		pito = (Button) findViewById(R.id.pito);
		// Botón que hace sonar la bocina.
		pito.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rutina += "P";
			}
		});

	}

}
