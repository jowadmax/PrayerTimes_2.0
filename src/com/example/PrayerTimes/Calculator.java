package com.example.PrayerTimes;

import java.util.ArrayList;

public class Calculator {

	private settingsBlob mySettings;
	
	// Class usable functions
	public Calculator(settingsBlob newSettings){
	this.mySettings = newSettings;
	}
	public void getTimes(ArrayList<Prayer> prayersList){
		// Start calculations time at 2:30am
		int time=9000;		
		//Loop through all Prayers objects
		for(int index=0; index < prayersList.size(); index++){

			if(prayersList.get(index).type == "exact"){
				//Calculate the time for the Prayer object
				Prayer temp = calculateExact(prayersList.get(index), time);
				
				//Store the info inside the object
				prayersList.get(index).prayerTime = temp.prayerTime;
				prayersList.get(index).timeTaken = temp.timeTaken;
				
				//Increase time counter
				time+= prayersList.get(index).timeTaken;
			}
			
			if(prayersList.get(index).type == "max"){
				//HACK: speed up the performance by skipping four hours after sunrise
				time+=4*3600;
				
				//Calculate the max angle from now till the sun gets to 0 degrees 
				Prayer temp = calculateMax(prayersList.get(index), 0 , time);
				
				//Store the info inside the object
				prayersList.get(index).prayerTime = temp.prayerTime;
				prayersList.get(index).timeTaken = temp.timeTaken;

				//Increase time counter
				time+= temp.timeTaken;
			}
			
		}
	}
	public String pretty(int second){
		String part="am";
		int hour = (int)second/3600,
			min = ((int)second/60) % 60,
			sec = second % 60;
		if(hour>12){
			hour-=12;
			part="pm";
		}
		String string = String.format("%02d:%02d:%02d%s",hour,min,sec,part);
		return string;
		
	}
	
	// getTimes helper functions
	private double calculateAngle(int second){
	    double 	B3 = mySettings.latitude,     // Latitude
	    		B4 = mySettings.longitude;    // Longitude
	    int		B5 = mySettings.timeZone,             // Offset
	    		year = mySettings.year, 
	    		month = mySettings.month,
	    		day = mySettings.day,
	    		hour = (int)second/3600,
	    		min = ((int)second/60) % 60,
	    		sec = second % 60;
	    	
	    //Equations are obtained from (http://www.esrl.noaa.gov/gmd/grad/solcalc/NOAA_Solar_Calculations_day.xls)
	    double E3 = second/(60*60*24.0); //(H:M:S -> Day fraction)
	    double F3 = julianDate(year, month, day, hour, min, sec, B5);
	    double G3 = (F3-2451545)/36525;
	    double I3 = mod(280.46646+G3*(36000.76983 + G3*0.0003032),360);
	    double J3 = 357.52911+G3*(35999.05029 - 0.0001537*G3);
	    double K3 = 0.016708634-G3*(0.000042037+0.0000001267*G3);
	    double L3 = Math.sin(toRadians(J3))*(1.914602-G3*(0.004817+0.000014*G3))+Math.sin(toRadians(2*J3))*(0.019993-0.000101*G3)+Math.sin(toRadians(3*J3))*0.000289;
	    double M3 = I3+L3;
	    double P3 = M3-0.00569-0.00478*Math.sin(toRadians(125.04-1934.136*G3));
	    double Q3 = 23+(26+((21.448-G3*(46.815+G3*(0.00059-G3*0.001813))))/60)/60;
	    double R3 = Q3+0.00256*Math.cos(toRadians(125.04-1934.136*G3));
	    double T3 = toDegrees(Math.asin(Math.sin(toRadians(R3))*Math.sin(toRadians(P3))));
	    double U3 = Math.tan(toRadians(R3/2))*Math.tan(toRadians(R3/2));
	    double V3 = 4*toDegrees(U3*Math.sin(2*toRadians(I3))-2*K3*Math.sin(toRadians(J3))+4*K3*U3*Math.sin(toRadians(J3))*Math.cos(2*toRadians(I3))-0.5*U3*U3*Math.sin(4*toRadians(I3))-1.25*K3*K3*Math.sin(2*toRadians(J3)));
	    double AB3 = mod(E3*1440+V3+4*B4-60*B5,1440);
	    double AC3 = AB3/4<0?AB3/4+180:AB3/4-180;
	    double AD3 = toDegrees(Math.acos(Math.sin(toRadians(B3))*Math.sin(toRadians(T3))+Math.cos(toRadians(B3))*Math.cos(toRadians(T3))*Math.cos(toRadians(AC3))));
	    double AE3 = 90-AD3;
	    double AF3 = AE3>85?0:AE3>5?58.1/Math.tan(toRadians(AE3))-0.07/Math.pow(Math.tan(toRadians(AE3)),3)+0.000086/Math.pow(Math.tan(toRadians(AE3)),5):AE3>-0.575?1735+AE3*(-518.2+AE3*(103.4+AE3*(-12.79+AE3*0.711))):-20.772/Math.tan(toRadians(AE3))/3600;
	    AF3/=3600;
	    double AG3 = AE3+AF3;

	    return AG3;
	}
	private Prayer calculateExact(Prayer prayer, int startingTime){
		int time=startingTime;
		double leastAbsolute=100;

		while(Math.abs(calculateAngle(time) - prayer.desiredAngle) < leastAbsolute){
			leastAbsolute = Math.abs(calculateAngle(time) - prayer.desiredAngle);
			prayer.prayerTime = time;
			time+=10;
		}
		
		prayer.timeTaken = time-startingTime; 
		return prayer;
	}
	private Prayer calculateMax(Prayer prayer, double to, int startingTime){
		double  angle, maxAngle = -400;
		int time = startingTime;
		
		angle = calculateAngle(time);
		while(angle > maxAngle &&  Math.abs(angle) > to){
			maxAngle = angle;
			prayer.prayerTime = time;
			time+=10;
			angle = calculateAngle(time);
		}
		prayer.timeTaken = time - startingTime;
		
		return prayer;
	}

	// calculateAngle helper functions
	private double julianDate(int year, int month, int day, int hour, int minute, int second, int offset){
		/* equation obtained from (http://bcn.boulder.co.us/y2k/y2kbcalc.htm) */
		double jd = (( 1461 * ( year + 4800 + ( month - 14 ) / 12 ) ) / 4 +
		      ( 367 * ( month - 2 - 12 * ( ( month - 14 ) / 12 ) ) ) / 12 -
		      ( 3 * ( ( year + 4900 + ( month - 14 ) / 12 ) / 100 ) ) / 4 +
		      day - 32075) -.5;

		hour-=offset;

		jd+=hour/24.0;
		jd+=minute/(24.0 * 60);
		jd+=second/(24.0 * 60 * 60);

		return jd;
		}
	private double mod(double number, double modNumber){
		while(number >= modNumber)
		    number-=modNumber;
		return number;
		}
	private	double toRadians(double num){
		return (num*Math.PI)/180.0;
		}
	private	double toDegrees(double num){
		return (num*180.0)/ Math.PI;
		}
}
