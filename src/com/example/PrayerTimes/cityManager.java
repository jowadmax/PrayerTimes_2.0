package com.example.PrayerTimes;

import java.util.Vector;

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

		// Receive the data passed from MainActivity
		Bundle params = getIntent().getExtras();
		String operationType = params.getString("operationType");

		// Construct the cities list
		Vector<String> items = new Vector<String>();
		if(operationType.equals("save"))
			items.add("<New City>");
		else
			items.add("<Delete City>");
		
		items.add("Evansville");
		items.add("Arlington");
		items.add("Baghdad");
		
		
		// Create a blank Dialog Builder
		AlertDialog.Builder builder = new AlertDialog.Builder(cityManager.this);
		builder.setOnCancelListener(this);

		// Set the dialog settings
		if(operationType.equals("save"))
			builder.setTitle("Choose a city to save to:");
		else
			builder.setTitle("Choose a city to load from:");		
		builder.setCancelable(true);

		// Assign the list and set the OnClickListener method
		builder.setItems(items.toArray(new String[0]), this);

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