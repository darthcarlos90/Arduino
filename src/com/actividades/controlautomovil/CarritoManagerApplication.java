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
	private boolean pinzas;

	/**
	 * Método que se hereda onCreate(). Inicializa variables que se pueden usar
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
		pinzas = true;
	}

	/**
	 * Método que se hereda. Cierra las conexiones a la base de datos.
	 */
	@Override
	public void onTerminate() {
		database.close();
		super.onTerminate();
	}

	/**
	 * Método que carga las rutinas de la base de datos, y los guarda en el
	 * ArrayList rutinas. Los nombres de las rutinas quedan guardados en el
	 * ArrayList nombreRutinas.
	 */
	private void cargarRutinas() {
		rutinas = new ArrayList<Rutina>();
		Cursor c = database.query(RUTINAS_TABLE, new String[] { ID, RUTINA },
				null, null, null, null, String.format("%s,%s", ID, RUTINA));
		c.moveToFirst();
		if (!c.isAfterLast()) {
			do {
				String rutina = c.getString(1);
				int numero = c.getInt(0);
				Rutina r = new Rutina(rutina, numero);
				rutinas.add(r);
			} while (c.moveToNext());
		}

		c.close();
	}

	/**
	 * Método que carga las rutinas, los guarda en un arrayList y los devuelve.
	 * 
	 * @return un ArrayList con las rutinas.
	 */
	public ArrayList<Rutina> getRutinas() {
		cargarRutinas();
		return rutinas;
	}

	/**
	 * Método que agrega una rutina a la base de datos.
	 * 
	 * @param rutina
	 *            El string de caracteres de la rutina a agregarse.
	 */
	public void agregaRutina(Rutina rutina) {
		assert (null != rutina);
		if (rutina.getNombre() == null) {
			rutina.setId(rutinas.size() + 1);
		}
		ContentValues values = new ContentValues();
		values.put(RUTINA, rutina.getNombre());
		database.insert(RUTINAS_TABLE, null, values);
		rutinas.add(rutina);
	}

	/**
	 * Método que elimina la rútina de acuerdo al parámetro de entrada.
	 * 
	 * @param i
	 *            El entero que representa el número de rutina a eliminar.
	 */
	public void deleteRutina(int idRutina) {
		database.rawQuery("Delete from " + RUTINAS_TABLE + " where " + ID
				+ " = '" + idRutina + "'", null);
		rutinas.remove(idRutina);
	}

	public String[] getDevices() {
		return devices;
	}

	public void setDevices(String[] devices) {
		this.devices = devices;
	}

	public ArrayAdapter<String> getBtArrayAdapter() {
		return btArrayAdapter;
	}

	public void setBtArrayAdapter(ArrayAdapter<String> btArrayAdapter) {
		this.btArrayAdapter = btArrayAdapter;
	}

	public BluetoothAdapter getmBluetoothAdapter() {
		return mBluetoothAdapter;
	}

	public void setmBluetoothAdapter(BluetoothAdapter mBluetoothAdapter) {
		this.mBluetoothAdapter = mBluetoothAdapter;
	}

	public ArrayList<BluetoothDevice> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(ArrayList<BluetoothDevice> dispositivos) {
		this.dispositivos = dispositivos;
	}

	public boolean connectionCreated() {
		return connectionEstablished;
	}

	public void setConnectionEstablished(Boolean valor) {
		connectionEstablished = valor;
	}

	public BluetoothDevice getConnectedDevice() {
		return connectedDevice;
	}

	public void setDevice(BluetoothDevice newDevice) {
		connectedDevice = newDevice;
	}

	public void setSocket(BluetoothSocket bs) {
		bluetoothSocket = bs;
	}

	public BluetoothSocket getSocket() {
		return bluetoothSocket;
	}

	public Set<BluetoothDevice> getPairedDevices() {
		return pairedDevices;
	}

	public void setPairedDevices(Set<BluetoothDevice> devices) {
		pairedDevices = devices;
	}
	
	public void setPinzas(boolean pinzas){
		this.pinzas = pinzas;
	}
	
	public boolean tienePinzas(){
		return pinzas;
	}

}