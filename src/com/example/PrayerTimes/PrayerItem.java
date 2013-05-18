package com.example.PrayerTimes;
public class PrayerItem {
	public int icon;
	public String title;
	public String time;
	public PrayerItem(){
		super();
	}

	public PrayerItem(int icon, String title, String time) {
		super();
		this.icon = icon;
		this.title = title;
		this.time = time;

	}
}