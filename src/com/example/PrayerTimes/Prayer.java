package com.example.PrayerTimes;

public class Prayer {
	double desiredAngle;
	int prayerTime;
	int timeTaken;
	String type; // "exact", "max"
	String direction; // "exact", "max"
	String name;
	Prayer(double desAng, String type, String name, String direction){
		this.desiredAngle = desAng;
		this.prayerTime = 0;
		this.type = type;
		this.name = name;
		this.direction = direction;
	}
}