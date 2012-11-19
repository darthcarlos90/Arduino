package com.actividades.controlautomovil;

import java.util.ArrayList;
import java.util.Set;

import com.Objetos.Rutina;
import com.database.DatabaseHelper;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Clase que se encarga de la administraci�n de las conexiones con bluetooth,
 * b�sicamente esta clase se encarga de guardarlas para que las otras
 * actividades puedan usarlas. Tambi�n esta clase se encarga de la
 * administraci�n de los objetos de la base de datos, as� como sus conexiones y
 * querys.
 * 
 * @author Carlos Tirado
 * 
 */
public class CarritoManagerApplication extends Application {

	private boolean connectionEstablished;
	private BluetoothDevice connectedDevice;
	private BluetoothSocket bluetoothSocket;
	private Set<BluetoothDevice> pairedDevices;
	private ArrayList<BluetoothDevice> dispositivos;
	private ArrayAdapter<String> btArrayAdapter;
	private BluetoothAdapter mBluetoothAdapter;
	private String[] devices;
	private SQLiteDatabase database;
	private ArrayList<Rutina> rutinas;
	public static final String DB_NAME = "rutinas.sqlite";
	public static final String RUTINAS_TABLE = "rutinas";
	public static final String ID = "id";
	public static final String RUTINA = "rutina";

	/**
	 * M�todo que se hereda onCreate(). Inicializa variables que se pueden usar
	 * en 0. Los objetos son databaseHelper, connectionEstablished,
	 * ConnectedDevice, y el Socket.
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		database = databaseHelper.getWritableDatabase();
		cargarRutinas();
		connectionEstablished = false;
		connectedDevice = null;
		bluetoothSocket = null;
	}

	/**
	 * M�todo que se hereda. Cierra las conexiones a la base de datos.
	 */
	@Override
	public void onTerminate() {
		database.close();
		super.onTerminate();
	}

	/**
	 * M�todo que carga las rutinas de la base de datos, y los guarda en el
	 * ArrayList rutinas. Los nombres de las rutinas quedan guardados en el
	 * ArrayList nombreRutinas.
	 */
	private void cargarRutinas() {
		rutinas = new ArrayList<Rutina>();

		/*
		 * Se crea un cursosr que apunte a la base de datos, y se realiza el
		 * query.
		 */
		Cursor c = database.query(RUTINAS_TABLE, new String[] { ID, RUTINA },
				null, null, null, null, String.format("%s,%s", ID, RUTINA));

		/* Se mueve el cursor al primer resultado del query. */
		c.moveToFirst();

		/* Mientras no se apunte al �ltimo objeto. */
		if (!c.isAfterLast()) {
			do {
				/* Se guarda el string de la rutina en una variable. */
				String rutina = c.getString(1);
				int numero = c.getInt(0);
				/* Se crea un objeto rutina con su n�mero y su rutina. */
				Rutina r = new Rutina(rutina, numero);
				/* Se agrega la rutina a la lista de rutinas. */
				rutinas.add(r);
			} while (c.moveToNext());// Se mueve al siguiente elemento del
										// query.
		}
		// Al terminar, se cierra el cursor.
		c.close();
	}

	/**
	 * M�todo que carga las rutinas, los guarda en un arrayList y los devuelve.
	 * 
	 * @return un ArrayList con las rutinas.
	 */
	public ArrayList<Rutina> getRutinas() {
		cargarRutinas();
		return rutinas;
	}

	/**
	 * M�todo que agrega una rutina a la base de datos.
	 * 
	 * @param rutina
	 *            El string de caracteres de la rutina a agregarse.
	 */
	public void agregaRutina(Rutina rutina) {
		assert (null != rutina); // Se asegura de que la rutina no sea null.

		/*
		 * Se establece el nombre de la rutina que sea Rutina n+1, donde n es el
		 * n�mero de rutinas que existen en este momento.
		 */
		if (rutina.getNombre() == null) {
			rutina.setId(rutinas.size() + 1);
		}

		/*
		 * Se crea el objeto content values para agregar los valores que se
		 * agregar�n a la base de datos.
		 */
		ContentValues values = new ContentValues();

		/* Se indica en que columna se insertar�n qu� valores. */
		values.put(RUTINA, rutina.getNombre());

		/* Se hace el insert en la base de datos. */
		database.insert(RUTINAS_TABLE, null, values);

		/* Se agrega la rutina a la base de datos. */
		rutinas.add(rutina);
	}

	/**
	 * M�todo que elimina la r�tina de acuerdo al par�metro de entrada.
	 * 
	 * @param i
	 *            El entero que representa el n�mero de rutina a eliminar.
	 */
	public void deleteRutina(int idRutina) {
		/*
		 * Se hace un RawQuery indicando que elemento de la base de datos se
		 * desea eliminar.
		 */
		database.rawQuery("Delete from " + RUTINAS_TABLE + " where " + ID
				+ " = '" + idRutina + "'", null);

		/* Se elimina la rutina de la lista de rutinas. */
		rutinas.remove(idRutina);
	}

	/**
	 * M�todo que regresa un arreglo con los dispositivos guardados.
	 * 
	 * @return Un arreglo con los dispositivos guardados.
	 */
	public String[] getDevices() {
		return devices;
	}

	/**
	 * M�todo que guarda un arreglo de dispositivos en el arreglo de
	 * dispositivos que se tiene.
	 * 
	 * @param devices
	 *            El arreglo de dispositivos a guardar.
	 */
	public void setDevices(String[] devices) {
		this.devices = devices;
	}

	/**
	 * M�todo que regresa un ArrayAdapter para desplegar nombres de dispositivos
	 * bluetooth.
	 * 
	 * @return ArrayAdapter con los nombres.
	 */
	public ArrayAdapter<String> getBtArrayAdapter() {
		return btArrayAdapter;
	}

	/**
	 * M�todo que guarda un ArrayAdapter con nombres a desplegar de dispositivos
	 * bluetooth.
	 * 
	 * @param btArrayAdapter
	 *            El ArrayAdapter a guardar.
	 */
	public void setBtArrayAdapter(ArrayAdapter<String> btArrayAdapter) {
		this.btArrayAdapter = btArrayAdapter;
	}

	/**
	 * M�todo que regresa el BluetoothAdapter guardado.
	 * 
	 * @return El bluetoothAdapter guardado.
	 */
	public BluetoothAdapter getmBluetoothAdapter() {
		return mBluetoothAdapter;
	}

	/**
	 * M�todo que guarda un BluetoohAdapter.
	 * 
	 * @param mBluetoothAdapter
	 *            El bluetoothAdapter a guardar.
	 */
	public void setmBluetoothAdapter(BluetoothAdapter mBluetoothAdapter) {
		this.mBluetoothAdapter = mBluetoothAdapter;
	}

	/**
	 * M�todo que regresa una lista de dispositivos bluetooth.
	 * 
	 * @return Lista de dispositivos bluetooth.
	 */
	public ArrayList<BluetoothDevice> getDispositivos() {
		return dispositivos;
	}

	/**
	 * M�todo que guarda una lista de dispositivos bluetooth.
	 * 
	 * @param dispositivos
	 *            La lista de dispositivos bluetooth a guardar.
	 */
	public void setDispositivos(ArrayList<BluetoothDevice> dispositivos) {
		this.dispositivos = dispositivos;
	}

	/**
	 * M�todo que regresa true si la conexi�n con un dispositivo esta
	 * establecida, de otra manera regresar� false.
	 * 
	 * @return Un booleano representando el estado de la conexi�n.
	 */
	public boolean connectionCreated() {
		return connectionEstablished;
	}

	/**
	 * M�todo que guarda el estado de la conexi�n.
	 * 
	 * @param valor
	 *            Una variable booleana representando el estado de la conexi�n.
	 */
	public void setConnectionEstablished(Boolean valor) {
		connectionEstablished = valor;
	}

	/**
	 * M�todo que regresa el dispositivo bluetooth conectado en este momento.
	 * 
	 * @return El dispositivo con el que se estableci� la conexi�n.
	 */
	public BluetoothDevice getConnectedDevice() {
		return connectedDevice;
	}

	/**
	 * Guardar el dispositivo con el que se tiene la conexi�n.
	 * 
	 * @param newDevice
	 *            El dispositivo con el que se estableci� conexi�n.
	 */
	public void setDevice(BluetoothDevice newDevice) {
		connectedDevice = newDevice;
	}

	/**
	 * M�todo que guarda el socket con el que se estableci� conexi�n.
	 * 
	 * @param bs
	 *            El socket con el que se estableci� la conexi�n.
	 */
	public void setSocket(BluetoothSocket bs) {
		bluetoothSocket = bs;
	}

	/**
	 * M�todo que regresa el socket con el que se estableci� la conexi�n.
	 * 
	 * @return El socket con el que se estableci� conexi�n.
	 */
	public BluetoothSocket getSocket() {
		return bluetoothSocket;
	}

	/**
	 * M�todo que regresa un Set de los dispositivos con los que se ha conectado
	 * previamente.
	 * 
	 * @return El Set de dispositivos blouetooth.
	 */
	public Set<BluetoothDevice> getPairedDevices() {
		return pairedDevices;
	}

	/**
	 * M�todo que guarda un Set de dispositivos con los que previamente se hab�a
	 * establecido una conexi�n.
	 * 
	 * @param devices
	 *            El Set de dispositivos.
	 */
	public void setPairedDevices(Set<BluetoothDevice> devices) {
		pairedDevices = devices;
	}

}