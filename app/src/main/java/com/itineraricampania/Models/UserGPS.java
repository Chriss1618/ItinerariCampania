package com.itineraricampania.Models;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

public class UserGPS {

    private Location LocationUser;
    private GeoPoint geoPoint;


    //geoPoint
    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    //location
    public Location getLocationUser() {
        return LocationUser;
    }
    public void setLocationUser(Location locationUser) {
        LocationUser = locationUser;
    }

    private static final UserGPS holder = new UserGPS();
    public static UserGPS getInstance() {return holder;}
}
