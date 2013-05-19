package com.example.PrayerTimes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;


public final class cityManager extends Activity implements OnClickListener, OnCancelListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final String[] items = { "Evansville", "Arlington" , "Baghdad"	};

		// Create a blank Dialog Builder
		AlertDialog.Builder builder = new AlertDialog.Builder(cityManager.this);
		builder.setOnCancelListener(this);

		// Set the dialog settings
		builder.setTitle("Choose a City:");
		builder.setCancelable(true);

		// Assign the list and set the OnClickListener method
		builder.setItems(items, this);

		// Show the dialog
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int item) {
		Log.v("MyActivity", "Hello!" + item);
		this.finish();
	}
	@Override
	public void onCancel(DialogInterface arg0) {
		this.finish();
	}
}