package com.actividades.controlautomovil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	
	String uuid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
			Intent intent = getIntent();
			uuid = intent.getExtras().getString("UUID");
		}catch(Exception e){
			uuid = null;
		}
	}
	
	public void manual(View v){
		Intent intent = new Intent(this, ManualActivity.class);
		intent.putExtra("UUID", uuid);
		startActivity(intent);
		
	}
	
	public void automatico (View v){
		Intent intent = new Intent(this, CirculoPruebaActivity.class);
		intent.putExtra("UUID", uuid);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.menu_settings:
			Intent intent = new Intent(this, BluetoothActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	

}
