package com.actividades.controlautomovil;

import java.util.ArrayList;
import java.util.Set;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.ArrayAdapter;

public class BluetoothManagerApplication extends Application {

	private boolean connectionEstablished;
	private BluetoothDevice connectedDevice;
	private BluetoothSocket bluetoothSocket;
	private Set<BluetoothDevice> pairedDevices;
	private ArrayList<BluetoothDevice> dispositivos;
	private ArrayAdapter<String> btArrayAdapter;
	private BluetoothAdapter mBluetoothAdapter;
	private String[] devices;

	public String[] getDevices() {
		return devices;
	}

	public void setDevices(String[] devices) {
		this.devices = devices;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		connectionEstablished = false;
		connectedDevice = null;
		bluetoothSocket = null;
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
	
	public void setConnectionEstablished(Boolean valor){
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

}