package com.example.PrayerTimes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public ListView mainListView ;  
	public ArrayAdapter<String> listAdapter ;  
	private ListView listView1;

	//Make a calendar instance to get today's date and set it as initial value for the picker
	public Calendar now = Calendar.getInstance();
	
	int cyear = now.get(Calendar.YEAR);
	int cmonth = now.get(Calendar.MONTH);
	int cday = now.get(Calendar.DAY_OF_MONTH);
	
	//Initialize our settings blob
	settingsBlob mySettings = new settingsBlob(37.9747222, -87.5558333, -5, cyear, cmonth+1, cday);
	Calculator myTimeCalculator = new Calculator(mySettings);

	
	String city_string="Arlington, VA";
	
@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        // Initialize list of prayers to calculate times for
        final ArrayList<Prayer> prayersList = new ArrayList<Prayer>();
        prayersList.add(new Prayer(-18.0, "exact", "Fajr"));
        prayersList.add(new Prayer(-1.0, "exact", "Sunrise"));
        prayersList.add(new Prayer(0, "max", "Dhuhr"));
        prayersList.add(new Prayer(-1, "exact", "Sunset"));
        prayersList.add(new Prayer(-4.003, "exact", "Maghrib"));
    	
        // Calculate prayer times and store them inside the objects
    	myTimeCalculator.getTimes(prayersList);

    	// Loop over the objects showing their times
		/*for(int index=0;index<5;index++)
			Toast.makeText(getApplicationContext(), ""+ prayersList.get(index).name + " prayer time is : " + myTimeCalculator.pretty(prayersList.get(index).prayerTime),Toast.LENGTH_LONG).show();
		*/
    	
        final Weather weather_data[] = new Weather[]
                {
                    new Weather(R.drawable.fajr, "Fajr",		myTimeCalculator.pretty(prayersList.get(0).prayerTime)),
                    new Weather(R.drawable.sunrise, "Sunrise",	myTimeCalculator.pretty(prayersList.get(1).prayerTime)),
                    new Weather(R.drawable.duhr, "Duhr",		myTimeCalculator.pretty(prayersList.get(2).prayerTime)),
                    new Weather(R.drawable.sunset, "Sunset",	myTimeCalculator.pretty(prayersList.get(3).prayerTime)),
                    new Weather(R.drawable.maghrib, "Maghrib",	myTimeCalculator.pretty(prayersList.get(4).prayerTime)),
                    new Weather(R.drawable.midnight, "Midnight","12:23:00am")
                };
        Toast.makeText(getApplicationContext(), ""+ this,Toast.LENGTH_LONG).show();
        WeatherAdapter adapter = new WeatherAdapter(com.example.PrayerTimes.MainActivity.this, 
                R.layout.listview_item_row, weather_data);
        
        listView1 = (ListView)findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        
        
        // Find the ListView resource.   
        mainListView = (ListView) findViewById( R.id.list);
        
        // Create and populate a List of planet names.  
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",  
                                          "Jupiter", "Saturn", "Uranus", "Neptune"};    
        ArrayList<String> planetList = new ArrayList<String>();  
        planetList.addAll( Arrays.asList(planets) );  
          
        // Create ArrayAdapter using the planet list.  
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
        
        // Assign the adapter to the mainListView
//        mainListView.setAdapter( listAdapter );
        
        Button myButton = (Button)findViewById(R.id.button1);
        myButton.setOnClickListener(new View.OnClickListener() {
       	 public void onClick(View v) {
       		 
       		 final Dialog dialog = new Dialog(MainActivity.this);
             dialog.setContentView(R.layout.maindialog);
             dialog.setTitle("Choose Timezone:");
             dialog.setCancelable(true);
             
             //set up button
             Button button = (Button) dialog.findViewById(R.id.Button01);
             button.setOnClickListener(new View.OnClickListener() {
             @Override
                 public void onClick(View v) {
                     dialog.dismiss();
                 }
             });

             //now that the dialog is set up, it's time to show it    
             dialog.show();
         }

     });
        
        Button myButton2 = (Button)findViewById(R.id.button3);
        myButton2.setText("Prayer times table for "+city_string+" in "+ String.valueOf(now.get(Calendar.MONTH)+1)+"/"+String.valueOf(now.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(now.get(Calendar.YEAR)));
        myButton2.setOnClickListener(new View.OnClickListener() {
       	 public void onClick(View v) {
       		 //Make a datePicker dialog and initialize its listener and its onDateSet function.
       		 Dialog calender = new DatePickerDialog(MainActivity.this,  new DatePickerDialog.OnDateSetListener() {
       		// onDateSet method
       			 public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
       				 cyear=year;
       				 cmonth=monthOfYear;
       				 cday=dayOfMonth;
       				 //Do whatever you want with the variables you get here
       				Button myButton2 = (Button)findViewById(R.id.button3);
       		        myButton2.setText("Prayer times table for "+city_string+" in "+ String.valueOf(monthOfYear+1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year));
                    
       		        //Reconfigure out settings blob.
       		        myTimeCalculator.mySettings.year = cyear;
       		        myTimeCalculator.mySettings.month = cmonth+1;
       		        myTimeCalculator.mySettings.day = cday;
       		        
       		        //Recalculate Prayer times
       		        myTimeCalculator.getTimes(prayersList);
       		        
       		        // Re-set the times for prayers
       		        weather_data[0].time=myTimeCalculator.pretty(prayersList.get(0).prayerTime);
       		        weather_data[1].time=myTimeCalculator.pretty(prayersList.get(1).prayerTime);
       		        weather_data[2].time=myTimeCalculator.pretty(prayersList.get(2).prayerTime);
       		        weather_data[3].time=myTimeCalculator.pretty(prayersList.get(3).prayerTime);
       		        weather_data[4].time=myTimeCalculator.pretty(prayersList.get(4).prayerTime);
                    listView1.setAdapter(new WeatherAdapter(com.example.PrayerTimes.MainActivity.this, R.layout.listview_item_row, weather_data));
                    
       			 }
       			},  cyear, cmonth, cday);
       		calender.setTitle("Show prayer times for:");
       		calender.show(); 
       	 }
        });

        //Define the GPS checkBox
        CheckBox checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText latit = (EditText)findViewById(R.id.editText1);
				EditText longit = (EditText)findViewById(R.id.editText2);
				latit.setEnabled(!((CheckBox)v).isChecked());
				longit.setEnabled(!((CheckBox)v).isChecked());
			}
		});
        //Define the TimeZone checkbox
        CheckBox timezone_checkbox = (CheckBox)findViewById(R.id.checkBox2);
        timezone_checkbox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			EditText timezone_field = (EditText)findViewById(R.id.editText3);
			timezone_field.setEnabled(!((CheckBox)v).isChecked());
				
			}
		});
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Check if the menu selection is Set Timezone option
		if(item.getItemId()==R.id.menu_timezone){
			// Declare the timezones list
			final String[] items = {		"GMT -11:00","GMT -10:00","GMT -09:00","GMT -08:00",
       										"GMT -07:00","GMT -06:00","GMT -05:00","GMT -04:00", 
       										"GMT -03:00","GMT -02:00","GMT -01:00","GMT +00:00", 
       										"GMT +01:00","GMT +02:00","GMT +03:00","GMT +04:00", 
       										"GMT +05:00","GMT +06:00","GMT +07:00","GMT +08:00", 
       										"GMT +09:00","GMT +10:00","GMT +11:00"
       								};
			// Create a blank Dialog Builder
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			// Set the dialog settings
			builder.setTitle("Choose Timezone:");
			builder.setCancelable(true);
			
			// Assign the list and set the OnClickListener method
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					// Declare the offset integer
					int offset;
					// Get the actual offset out of the list selection
					if(item<11)
						offset=item-11;
   		    			else
   		    				if(item==11)
   		    					offset=0;
   		    				else
   		    					offset=item-11;
   		    	
					Toast.makeText(MainActivity.this, String.format("GMT %1$s timezone is selected!", offset), Toast.LENGTH_SHORT).show();
				}
				
			});
			// Create the alert out of the builder
			AlertDialog alert = builder.create();
			// Show the dialog
			alert.show();
		}

		// Check if the option is the City Manager
		if(item.getItemId()==R.id.menu_cityManager){
			
			
		}
	return true;
}

}
