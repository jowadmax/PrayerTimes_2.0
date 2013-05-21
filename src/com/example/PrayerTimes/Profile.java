package com.example.PrayerTimes;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {
	double 	savedLatitude,
	savedLongitude;
	int		savedTimezone;
	String	cityName;
	boolean	useGPS,
	useTimezone;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(savedLatitude);		
		dest.writeDouble(savedLongitude);
		dest.writeInt(savedTimezone);
		dest.writeString(cityName);
		dest.writeByte((byte) (useGPS ? 1 : 0));
		dest.writeByte((byte) (useTimezone ? 1 : 0));
	}
	public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
		public Profile createFromParcel(Parcel in) {
			return new Profile(in);
		}

		public Profile[] newArray(int size) {
			return new Profile[size];
		}
	};
	private Profile(Parcel in) {
		savedLatitude = in.readDouble(); 
		savedLongitude = in.readDouble(); 
		savedTimezone = in.readInt();
		cityName = in.readString();
		useGPS = in.readByte() == 1;
		useTimezone = in.readByte() == 1;
	}
	public Profile() {
		savedLatitude = 0.0;
		savedLongitude = 0.0;
		savedTimezone = 0;
		cityName = "Unnammed City";
		useGPS =  true;
		useTimezone = true;
	}

}