package com.actividades.controlautomovil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TipoCarroActivity extends Activity {
	
	private CarritoManagerApplication cma;
	private Button noPinza;
	private Button conPinza;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_car);
		setUpViews();
	}
	
	private void empiezaSiguienteActividad(){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	private void setUpViews(){
		cma = (CarritoManagerApplication) getApplication();
		noPinza = (Button) findViewById(R.id.sinPinzas);
		conPinza = (Button) findViewById(R.id.conPinzas);
		noPinza.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				cma.setPinzas(false);
				empiezaSiguienteActividad();
				
			}
		});
		
		noPinza.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			cma.setPinzas(true);
			empiezaSiguienteActividad();
			
				
			}
		});
		
	}

}
