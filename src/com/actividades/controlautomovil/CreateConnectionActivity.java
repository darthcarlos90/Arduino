package com.actividades.controlautomovil;

import java.util.ArrayList;

import com.actividades.controlautomovil.BluetoothActivity;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CreateConnectionActivity extends BluetoothActivity {

	private Button buscar;
	private ArrayList<BluetoothDevice> devices;
	private ArrayAdapter<String> btArrayAdapter;
	private CarritoManagerApplication bma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth_layout);
		setBluetooth();
	}

	void setBluetooth() {
		bma = getApplicationManager();
		ListView lv_previous = (ListView) findViewById(R.id.dispositivosPrevios_view);
		ListView lv_found = (ListView) findViewById(R.id.dispositivosEncontrados_lv);
		buscar = (Button) findViewById(R.id.empezarBusqueda);
		createConnection();
		devices = new ArrayList<BluetoothDevice>();
		String[] yolo = bma.getDevices();
		if (yolo != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, yolo);

			lv_previous.setAdapter(adapter);
			lv_previous.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					selectConnection(getPairedDevice(arg2));
				}
			});
		}

		buscar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				startSearch();
			}
		});
		btArrayAdapter = bma.getBtArrayAdapter();
		lv_found.setAdapter(btArrayAdapter);
		lv_found.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				selectConnection(getDispositivo(arg2));
			}
		});
	}
}