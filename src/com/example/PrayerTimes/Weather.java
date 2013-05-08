package com.example.PrayerTimes;
public class Weather {
    public int icon;
    public String title;
    public String time;
    public Weather(){
        super();
    }
    
    public Weather(int icon, String title, String time) {
        super();
        this.icon = icon;
        this.title = title;
        this.time = time;
        
    }
}