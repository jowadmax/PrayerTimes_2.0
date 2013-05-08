package com.example.PrayerTimes;

public class Calculator {
	private settingsBlob mySettings;
	
	public Calculator(settingsBlob newSettings){
	this.mySettings = newSettings;
	}
	private int dayOfYear(int year, int month, int day){
		int doy=0;
		
		int months[] = {0,31,28,31,30,31,30,31,31,30,31,30};
		
		//Fill in the days of the past months
		for (int i=1 ; i<month ;i++)
			doy+=months[i];
		
		//Fill in the days of this month
		doy+=day;
		
		//Add one day if this is a leap year (and we're past February
		if ( ((year%4==0 && year!=((int)year/10)*10) || year%400==0 ) && month>2)
			doy++;
		
		return doy;
	}
	public double calculateAngle(int second){
		int doy = dayOfYear(mySettings.year, mySettings.month, mySettings.day);
		int hour, min, sec;
		
		hour = second/3600;
		min = (second/60) % 60;
		sec = second % 60;

		hour=hour-mySettings.timeZone;
		if(hour>23){
			hour-=24;
			doy++;
		}	    

		// Calculate the time in hour and its fractions
		hour+=(min/60.0)+(sec/3600);
		
		//Start calculations
		double y=(360/365.25)*(doy +hour/24); //OK (Degs)
		double D=0.006918 - 0.399912*Math.cos((double) (y/180.0*Math.PI)) + 0.070257*Math.sin(y/180.0*Math.PI) - 0.006758*Math.cos(2*y/180.0*Math.PI) + 0.000907*Math.sin(2*y/180.0*Math.PI) - 0.002697*Math.cos(3*y/180.0*Math.PI) + 0.00148*Math.sin(3*y/180.0*Math.PI); D=D*180.0/Math.PI; //OK (Degs)
		double TC = 0.004297+0.107029*Math.cos(y/180.0*Math.PI)-1.837877*Math.sin(y/180.0*Math.PI)-0.837378*Math.cos(2*y/180.0*Math.PI)-2.340475*Math.sin(2*y/180.0*Math.PI); //OK (Degs)
		double SHA = (hour-12)*15 + mySettings.longitude + TC; //OK (Degs)
 	   	double SZA = Math.acos(Math.sin(mySettings.latitude/180.0*Math.PI)*Math.sin(D/180.0*Math.PI)+Math.cos(mySettings.latitude/180.0*Math.PI)*Math.cos(D/180.0*Math.PI)*Math.cos(SHA/180.0*Math.PI)); SZA=SZA*180.0/Math.PI; //OK (Degs)
 	   	
 	   	//Return the sun angle in degrees
		return 90-SZA;
	}
}
