package com.example.PrayerTimes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PrayersListAdapter extends ArrayAdapter<PrayerItem>{

    Context context; 
    int layoutResourceId;    
    PrayerItem data[] = null;
    
    public PrayersListAdapter(Context context, int layoutResourceId, PrayerItem[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtTime = (TextView)row.findViewById(R.id.txtTime);
            
            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }
        
        PrayerItem weather = data[position];
        holder.txtTitle.setText(weather.title);
        holder.txtTime.setText(weather.time);
        holder.imgIcon.setImageResource(weather.icon);
        
        return row;
    }
    
    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtTime;
    }
}
