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

/**
 * Actividad que se encarga de crear y manejar las conexiones a bluetooth. La
 * mayoría de las clases heredan de esta clase para poder utilizar la conexión
 * de bluetooth para mandar mensajes.
 * 
 * @author Carlos
 * 
 */
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

	/**
	 * Método que muestra un mensaje de retroalimentación al usuario.
	 * 
	 * @param message
	 *            El String del mensaje que se quiere mostrar.
	 */
	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	/**
	 * Método que crea la conexión de bluetooth.
	 */
	public void createConnection() {
		/*
		 * El objeto BluetoothAdapter es el objeto que representa el bluetooth
		 * físico del dispositivo.
		 */
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		/*
		 * Se guarda el objeto bluetoothAdapter para poder usarla
		 * posteriormente.
		 */
		bma.setmBluetoothAdapter(mBluetoothAdapter);

		/*
		 * Ver si se cuenta con un dispositivo bluetooth, en caso de que no,
		 * avisarle al usuario.
		 */
		if (mBluetoothAdapter == null) {
			showMessage("No cuentas con bluetooth");
		}

		/* Ver si el dispositivo Bluetooth está activado. */
		if (mBluetoothAdapter.isEnabled()) {
			showMessage("Status Bluetooth: ON");
		}

		/*
		 * Ver si el bluetooth esta activado, en caso de que no, activarlo y
		 * avisarle al usuario de que se prendió el bluetooth.
		 */
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			showMessage("Status Bluetooth: ON");
		}
		/*
		 * Guardar los dispositivos que ya se han pariado en una lista para ser
		 * mostrados.
		 */
		pairedDevices = mBluetoothAdapter.getBondedDevices();
		/* Se guardan para ser usados posteriormente */
		bma.setPairedDevices(pairedDevices);
		/* Se guardan los dispositivos en un string */
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
		/* Se despliega y se guarda */
		btArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		bma.setBtArrayAdapter(btArrayAdapter);

	}

	/**
	 * Método que crea la conexión con el dispositivo que se introdujo como
	 * parámetro.
	 * 
	 * @param selectedDevice
	 *            El dispositivo con el que se quiere realizar una conexión.
	 */
	public void selectConnection(BluetoothDevice selectedDevice) {
		BluetoothDevice device = selectedDevice;

		/* Dirección default que tienen los dispositivos bluetooth. */
		String direccion = "00001101-0000-1000-8000-00805f9b34fb";

		/* Creación del UUID a partir de la dirección default. */
		UUID uuid = UUID.fromString(direccion);

		BluetoothSocket mmSocket; // Se crea el elemento mmSocket para
		try {
			/* Se crea la conexión con el dispositivo a través del UUID */
			mmSocket = device.createRfcommSocketToServiceRecord(uuid);

			showMessage("Se creo primera conexión!"); // Si no hubo error, se
														// otifica al usuarioF
			/* Se conecta con el dispositivo */
			mmSocket.connect();

			showMessage("Ya se conectó!");// Si no ocurrió error, se notifica al
											// usuario.
			/*
			 * Se guarda la conexión y se establece que la conexión ya está
			 * estaablecida.
			 */
			bma.setSocket(mmSocket);
			bma.setConnectionEstablished(true);

			finish();// Se termina la actividad una vez que se realizó la
						// conexión
		} catch (IOException e) {
			/* En caso de fallo, notificar al usuario y terminar la actividad. */
			showMessage("Algo falló conectándonos al dispositivo! :(");
			finish();
			e.printStackTrace();
		}

	}

	/**
	 * Método que se encarga de buscar dispositivos cercanos usando el
	 * bluetooth.
	 */
	public void startSearch() {
		btArrayAdapter.clear();
		bma.setBtArrayAdapter(btArrayAdapter);
		startActivity(discoverable);
		mBluetoothAdapter.startDiscovery();
	}

	/**
	 * Método que se encarga de mandar un mensaje a través del bluetooth. Los
	 * parámetros son el caracter que se desea mandar, y el socket que
	 * representa el dispositivo al que se le mandará el mensaje.
	 * 
	 * @param message
	 *            El caracter que será enviado.
	 * @param socket
	 *            La representación del dispositivo al que se le mandará el
	 *            mensaje.
	 */
	public void sendMessage(char message, BluetoothSocket socket) {
		OutputStream mmOutputStream = null; // El outputstream es por donde se
											// mandará el mensaje.
		try {
			/*
			 * Se obtiene el outputStream del socket por el que se va a mandar
			 * el mensaje.
			 */
			mmOutputStream = socket.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/*
		 * Se guarda el mensaje en un String, para que sea compatible con el
		 * método.
		 */
		String msg = "" + message;
		msg += "\n";
		try {
			// Se escribe el mensaje, y se notifica al usuario.
			mmOutputStream.write(msg.getBytes());
			showMessage("Se mando mensaje!");
		} catch (IOException e) {
			// En caso de que no se pueda mandar el mensaje, se notifica al
			// usuario.
			showMessage("No se pudo mandar el mensaje :(");
			e.printStackTrace();
		}
	}

	/**
	 * Método que obtiene el objeto aplicación para comunicarse con la
	 * aplicación.
	 * 
	 * @return Regresa el objeto Application de esta aplicación.
	 */
	public CarritoManagerApplication getApplicationManager() {
		CarritoManagerApplication bma = (CarritoManagerApplication) getApplication();
		return bma;
	}

	/**
	 * Método que regresa el dispositivo guardado en el índice index.
	 * 
	 * @param index
	 *            El índice en la lista de dispositivos.
	 * @return Se regresa el dispositivo en el índice index.
	 */
	public BluetoothDevice getDispositivo(int index) {
		return dispositivos.get(index);
	}

	/**
	 * Se regresa el dispositivo en el índice index de la lista de dispositivos
	 * previamente conectados.
	 * 
	 * @param index
	 *            El índice del dispositivo que se está buscando.
	 * @return El dispositivo que se está buscando.
	 */
	public BluetoothDevice getPairedDevice(int index) {
		BluetoothDevice[] devs = new BluetoothDevice[pairedDevices.size()];
		pairedDevices.toArray(devs);
		BluetoothDevice result = devs[index];
		return result;
	}

	/**
	 * Método que termina la conexión con el dispositivo.
	 */
	public void endConnection() {
		try {
			bma.getSocket().close();// Se cierra la conexión con el dispositivo.
			bma.setConnectionEstablished(false);// Se pone la variable de
												// conexión como falsa.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Elemento broadcast receiver, este elemento es utilizado cada vez que el
	 * celular notifique que encontró un dispositivo. El elemento broadcast
	 * recevier se encargará de guardar el elemento en la lista de dispositivos.
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

				/* Se agrega el nombre del dispositivo a la lista. */
				btArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress() + "\n");

				/* Si el dispositivo es diferente de null, se guarda. */
				if (device != null) {
					dispositivos.add(device);
				}
				btArrayAdapter.notifyDataSetChanged();
			}
		}
	};

}