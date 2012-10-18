package com.actividades.controlautomovil;

import com.pruebas.clases.Circle;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

public class CirculoPruebaActivity extends Activity implements
		SensorEventListener {

	Circle c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pruebacirculo);

		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		sm.registerListener(this,
				sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);

		FrameLayout main = (FrameLayout) findViewById(R.id.canvas_view);
		c = new Circle(this, 30, 30, 25);
		main.addView(c);

		main.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				//c.setPosition(event.getX(), event.getY());
				return true;
			}
		});
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		// float z = event.values[2];

		//c.move(x, y);

	}

}
