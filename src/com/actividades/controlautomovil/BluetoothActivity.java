package com.actividades.controlautomovil;

import java.io.IOException;
import java.io.InputStream;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothActivity extends Activity {

	private static final int REQUEST_ENABLE_BT = 1;
	private Button buscar;
	ArrayAdapter<String> btArrayAdapter;
	ArrayList<BluetoothDevice> devices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth_layout);
		setBluetooth();
	}

	void setBluetooth() {
		TextView statusBluetoothTv = (TextView) findViewById(R.id.status_bluetooth);
		ListView lv_previous = (ListView) findViewById(R.id.dispositivosPrevios_view);

		ListView lv_found = (ListView) findViewById(R.id.dispositivosEncontrados_lv);
		buscar = (Button) findViewById(R.id.empezarBusqueda);
		devices = new ArrayList<BluetoothDevice>();

		final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		/*
		 * El objeto BluetoothAdapter es el objeto que representa al bluetooth
		 * del celular getDefaultAdapter nos va a regresar el bluetooth que
		 * tiene el celular. si regresa un null, significa que el celular no
		 * tiene bluetooth.
		 */

		if (mBluetoothAdapter == null) {
			showMessage("No cuentas con bluetooth");
			statusBluetoothTv.setText("Status Bluetooth: OFF");

		}
		/* Si no se cuenta con bluetooth en el celular, acabar esta actividad */

		statusBluetoothTv.setText("Status Bluetooth: Exists, OFF");
		// Mostrar que si se tiene bluetooth, pero esta apagado

		/*
		 * Ahora hay que ver si esta activado el bluetooth, en caso de que esté
		 * desactivado, se manda un intent para activarlo
		 */
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			statusBluetoothTv.setText("Status Bluetooth: ON");
		}
		// Si estaba desactivado y se acaba de activar, mandar el mensaje a
		// pantalla.
		if (mBluetoothAdapter.isEnabled()) {
			statusBluetoothTv.setText("Status Bluetooth: ON");
		}

		/*
		 * Código para ver dispositivos que ya han sido previamente conectados
		 * al dispositivo.
		 */

		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		// Para ver si hay dispositivos que ya se han conectado
		String[] devices;
		if (pairedDevices.size() > 0) {
			// Si si los hay, hay que desplegarlos
			devices = new String[pairedDevices.size()];
			int i = 0;
			for (BluetoothDevice device : pairedDevices) {
				// Agregar a la lista el nombre y la dirección de los
				// dispositivos
				devices[i] = (device.getName() + "\n" + device.getAddress());
				i++;
			}
			// Mostrarlos
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, devices);

			lv_previous.setAdapter(adapter);

			lv_previous.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					BluetoothDevice device = BluetoothActivity.this.devices
							.get(arg2);
					// if (device.getName() == "JY-MCU") {
					String direccion = "00001101-0000-1000-8000-00805f9b34fb";
					UUID uuid = UUID
							.fromString(direccion); // Standard
																					// SerialPortService
																					// ID
					BluetoothSocket mmSocket = null;
					
					try {
						showMessage("Se conecto 1");
						mmSocket = device
								.createRfcommSocketToServiceRecord(uuid);
					} catch (IOException e) {
						showMessage("No funciono!");
						e.printStackTrace();
					}
					try {
						showMessage("Se conecto 2");
						mmSocket.connect();
					} catch (IOException e) {
						showMessage("No funciono!");
						e.printStackTrace();
					}
					
					Intent intent = new Intent(BluetoothActivity.this, MainActivity.class);
					intent.putExtra("UUID",direccion );
					startActivity(intent);
					
					
				}
			});

		} else {
			// Si no hay previos, mostrar un anuncio
			lv_previous.setVisibility(View.GONE);
		}

		/*
		 * if (mBluetoothAdapter.startDiscovery() == true) {
		 * showMessage("Buscando Dispositivos"); final
		 * ArrayList<BluetoothDevice> devicesFound = new
		 * ArrayList<BluetoothDevice>(); // Create a BroadcastReceiver for
		 * ACTION_FOUND BroadcastReceiver mReceiver = new BroadcastReceiver() {
		 * public void onReceive(Context context, Intent intent) { String action
		 * = intent.getAction(); // When discovery finds a device if
		 * (BluetoothDevice.ACTION_FOUND.equals(action)) { // Get the
		 * BluetoothDevice object from the Intent BluetoothDevice device =
		 * intent .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		 * devicesFound.add(device); // Add the name and address to an array
		 * adapter to show // in a ListView
		 * 
		 * } } };
		 * 
		 * // Register the BroadcastReceiver IntentFilter filter = new
		 * IntentFilter(BluetoothDevice.ACTION_FOUND);
		 * registerReceiver(mReceiver, filter); // Don't forget to unregister //
		 * during onDestroy
		 * 
		 * String[] fdevs = new String[devicesFound.size()]; for (int i = 0; i <
		 * devicesFound.size(); i++) { fdevs[i] = devicesFound.get(i).getName()
		 * + devicesFound.get(i).getAddress(); } ArrayAdapter<String>
		 * arrayAdapterDevices = new ArrayAdapter<String>( this,
		 * android.R.layout.simple_list_item_1, fdevs);
		 * lv_found.setAdapter(arrayAdapterDevices);
		 * 
		 * }else{ showMessage("Algo falló :("); }*
		 */
		btArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		lv_found.setAdapter(btArrayAdapter);
		registerReceiver(ActionFoundReceiver, new IntentFilter(
				BluetoothDevice.ACTION_FOUND));

		final Intent discoverable = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		buscar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				btArrayAdapter.clear();
				startActivity(discoverable);
				mBluetoothAdapter.startDiscovery();
			}
		});

		lv_found.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BluetoothDevice device = BluetoothActivity.this.devices
						.get(arg2);
				// if (device.getName() == "JY-MCU") {
				UUID uuid = UUID
						.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
																				// SerialPortService
																				// ID
				BluetoothSocket mmSocket = null;
				OutputStream mmOutputStream = null;
				try {
					showMessage("Se conecto 1");
					mmSocket = device.createRfcommSocketToServiceRecord(uuid);
				} catch (IOException e) {
					showMessage("No funciono!");
					e.printStackTrace();
				}
				try {
					showMessage("Se conecto 2");
					mmSocket.connect();
				} catch (IOException e) {
					showMessage("No funciono!");
					e.printStackTrace();
				}
				try {
					mmOutputStream = mmSocket.getOutputStream();
				} catch (IOException e) {
					showMessage("No funciono!");
					e.printStackTrace();
				}
				try {
					InputStream mmInputStream = mmSocket.getInputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String msg = "A";
				msg += "\n";
				try {
					showMessage("Se mando mensaje!");
					mmOutputStream.write(msg.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// }
		});

	}

	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
		// finish();
	}

	/**
	 * El objeto ActionFoundReceiver es un broadcast receiver, que cada vez que
	 * se detecte un dispositivo bluetooth se va a avisar.
	 */
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
				devices.add(device);
				btArrayAdapter.notifyDataSetChanged();
				// UUID.fromString(device.getAddress());
			}
		}
	};

}
