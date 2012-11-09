package com.actividades.controlautomovil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class BluetoothActivity extends Activity {

	private static final int REQUEST_ENABLE_BT = 1;
	private Set<BluetoothDevice> pairedDevices;
	private ArrayList<BluetoothDevice> dispositivos;
	private ArrayAdapter<String> btArrayAdapter;
	private Intent discoverable;
	private BluetoothAdapter mBluetoothAdapter;
	private CarritoManagerApplication bma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		bma = getApplicationManager();
		dispositivos = new ArrayList<BluetoothDevice>();
	}

	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	public void createConnection() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		bma.setmBluetoothAdapter(mBluetoothAdapter);
		if (mBluetoothAdapter == null) {
			showMessage("No cuentas con bluetooth");
		}

		if (mBluetoothAdapter.isEnabled()) {
			showMessage("Status Bluetooth: ON");
		}

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			showMessage("Status Bluetooth: ON");
		}

		pairedDevices = mBluetoothAdapter.getBondedDevices();
		bma.setPairedDevices(pairedDevices);

		String[] devices = null;
		if (pairedDevices.size() > 0) {
			devices = new String[pairedDevices.size()];
			int i = 0;
			for (BluetoothDevice device : pairedDevices) {
				devices[i] = (device.getName() + "\n" + device.getAddress());
				i++;
			}
		}
		bma.setDevices(devices);

		registerReceiver(ActionFoundReceiver, new IntentFilter(
				BluetoothDevice.ACTION_FOUND));
		discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

		btArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		bma.setBtArrayAdapter(btArrayAdapter);

	}

	public void selectConnection(BluetoothDevice selectedDevice) {
		BluetoothDevice device = selectedDevice;
		String direccion = "00001101-0000-1000-8000-00805f9b34fb";
		UUID uuid = UUID.fromString(direccion);
		BluetoothSocket mmSocket;
		try {
			mmSocket = device.createRfcommSocketToServiceRecord(uuid);
			showMessage("Se creo primera conexión!");
			mmSocket.connect();
			showMessage("Ya se conectó!");
			bma.setSocket(mmSocket);
			bma.setConnectionEstablished(true);
			finish();
		} catch (IOException e) {
			showMessage("Algo falló conectándonos al dispositivo! :(");
			finish();
			e.printStackTrace();
		}

	}

	public void startSearch() {
		btArrayAdapter.clear();
		bma.setBtArrayAdapter(btArrayAdapter);
		startActivity(discoverable);
		mBluetoothAdapter.startDiscovery();
	}

	public void sendMessage(char message, BluetoothSocket socket) {
		OutputStream mmOutputStream = null;
		try {
			mmOutputStream = socket.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String msg = "" + message;
		msg += "\n";
		try {
			showMessage("Se mando mensaje!");
			mmOutputStream.write(msg.getBytes());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public CarritoManagerApplication getApplicationManager() {
		CarritoManagerApplication bma = (CarritoManagerApplication) getApplication();
		return bma;
	}

	public BluetoothDevice getDispositivo(int index) {
		return dispositivos.get(index);
	}

	public BluetoothDevice getPairedDevice(int index) {
		BluetoothDevice[] devs = new BluetoothDevice[pairedDevices.size()];
		pairedDevices.toArray(devs);
		BluetoothDevice result = devs[index];
		return result;
	}

	public void endConnection() {
		try {
			bma.getSocket().close();
			bma.setConnectionEstablished(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// Cuando haya descubierto el dispositivo
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Recibir el objeto del intent, y guardarlo en otro objeto.
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				btArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress() + "\n");
				// + UUID.fromString(device.getAddress()).toString());
				if (device != null) {
					dispositivos.add(device);
				}
				btArrayAdapter.notifyDataSetChanged();
				// UUID.fromString(device.getAddress());
			}
		}
	};

}