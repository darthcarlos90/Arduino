package com.actividades.controlautomovil;

import java.io.IOException;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ManualActivity extends BluetoothActivity implements
		SensorEventListener {

	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private BluetoothManagerApplication bma;
	private Button adelante;

	private final float NOISE = (float) 2.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual);
		setupViews();

	}

	private void setupViews() {
		bma = getApplicationManager();
		adelante = (Button)findViewById(R.id.adelante_btn);
		adelante.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				try {
					bma.getSocket().getOutputStream().write('A');
				} catch (IOException e) {
					showMessage("No se pudo mover hacia adelante :(");
					e.printStackTrace();
				}
				
			}
		});
		
	}

	public void setupSensors() {
		mInitialized = false;
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	/*
	 * public void boton(){ OutputStream mmOutputStream = null; BluetoothSocket
	 * mmSocket; BluetoothDevice device; /*mmSocket = device
	 * .createRfcommSocketToServiceRecord(uuid); try { //mmOutputStream =
	 * mmSocket.getOutputStream(); } catch (IOException e) {
	 * showMessage("No funciono!"); e.printStackTrace(); } try { InputStream
	 * mmInputStream = mmSocket.getInputStream(); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * String msg = "A"; msg += "\n"; try { showMessage("Se mando mensaje!");
	 * mmOutputStream.write(msg.getBytes()); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
		// finish();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {

		TextView tv = (TextView) findViewById(R.id.estadoX);
		TextView tv2 = (TextView) findViewById(R.id.estadoY);
		TextView tv3 = (TextView) findViewById(R.id.estadoZ);
		synchronized (this) {
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			if (!mInitialized) {
				mLastX = x;
				mLastY = y;
				mLastZ = z;
				// tv.append("0.0 ");
				// tv.append("0.0 ");
				// tv.append("0.0 ");
				mInitialized = true;
			} else {
				float deltaX = Math.abs(mLastX - x);
				float deltaY = Math.abs(mLastY - y);
				float deltaZ = Math.abs(mLastZ - z);
				if (deltaX < NOISE)
					deltaX = (float) 0.0;
				if (deltaY < NOISE)
					deltaY = (float) 0.0;
				if (deltaZ < NOISE)
					deltaZ = (float) 0.0;
				mLastX = x;
				mLastY = y;
				mLastZ = z;
				tv.setText("Eje X: " + Float.toString(deltaX));
				tv2.setText("Eje Y: " + Float.toString(deltaY));
				tv3.setText("Eje Z: " + Float.toString(deltaZ));
			}

		}

	}

}
