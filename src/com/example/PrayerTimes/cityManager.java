package com.example.PrayerTimes;

import java.util.Vector;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public final class cityManager extends Activity implements OnItemClickListener, OnItemLongClickListener, OnCancelListener {


	private ListView cityList;
	private ArrayAdapter<String> cities;
	String operationType;
	Profile newProfile = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_listview);
		
		// Receive the data passed from MainActivity
		Bundle params = getIntent().getExtras();
		operationType = params.getString("operationType");
		
		// Set the dialog settings
		if(operationType.equals("save"))
			this.setTitle("Choose a city to save to:");
		else
			this.setTitle("Choose a city to load from:");		
		
		// Construct the cities list
		Vector<String> items = new Vector<String>();
		if(operationType.equals("save"))
			items.add("<New City>");
		else
			items.add("<Delete City>");
		
		items.add("Evansville");
		items.add("Arlington");
		items.add("Baghdad");

		cityList = (ListView) findViewById(R.id.list);
		cityList.setOnItemClickListener(this);
		cityList.setOnItemLongClickListener(this);
        cities = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items.toArray(new String[0]));
        cityList.setAdapter(cities);

        // If the state is save, then get the mainProfile from MainActivity
		if(operationType.equals("save"))
			newProfile = (Profile)params.getParcelableArrayList("profile").get(0);

        }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.v("MyActivity", "Hello!" + ((TextView)view).getText());
		this.finish();
	}


	@Override
	public void onCancel(DialogInterface dialog) {
		this.finish();
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return true;
	}
}