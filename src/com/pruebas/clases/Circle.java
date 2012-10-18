package com.pruebas.clases;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Circle extends View{
	private float x;
	private float y;
	private final int r;
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	public Circle (Context context, float x, float y, int r){
		super (context);
		this.x = x;
		this.y = y;
		this.r = r;
		mPaint.setColor(Color.RED);
	}
	

}
