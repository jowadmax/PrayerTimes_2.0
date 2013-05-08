package com.example.PrayerTimes;

// Define a settings struct to pass to the calculations method
public class settingsBlob{
	 public double latitude;
	 public double longitude;
	 public int timeZone;
	 public int year;
	 public int month;
	 public int day;
 	
	 public settingsBlob(double lat, double longit, int tz, int year, int month, int day){
		 this.latitude = lat;
		 this.longitude = longit;
		 this.timeZone = tz;
		 this.year = year;
		 this.month = month;
		 this.day = day;
		  }
}