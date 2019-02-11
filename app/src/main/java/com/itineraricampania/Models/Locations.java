package com.itineraricampania.Models;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

public class Locations {

    private GeoPoint geo_point;
    private @ServerTimestamp Date timestamp;
    private String place;

    public Locations(GeoPoint geo_point, Date timestamp, String place) {
        this.geo_point = geo_point;
        this.timestamp = timestamp;
        this.place = place;
    }

    public Locations() {
    }

    public GeoPoint getGeo_point() {
        return geo_point;
    }
    public void setGeo_point(GeoPoint geo_point) {
        this.geo_point = geo_point;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "geo_point=" + geo_point +
                ", timestamp=" + timestamp +
                ", place=" + place +
                '}';
    }
}
