package com.itineraricampania;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.GeoPoint;
import com.itineraricampania.Models.UserGPS;

public class UpdatingLocationUser extends Service {

    private FusedLocationProviderClient mFusedLocationClient;
    private final static long UPDATE_INTERVAL = 4 * 1000;  /* 4 secs */
    private final static long FASTEST_INTERVAL = 2000; /* 2 sec */
    private static final String TAG = "UpdatingLocationUser";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: called.");



        getLastLocation();
        return START_NOT_STICKY;
    }

    public void getLastLocation(){
        LocationRequest mLocationRequestHighAccuracy = new LocationRequest();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(UPDATE_INTERVAL);
        mLocationRequestHighAccuracy.setFastestInterval(FASTEST_INTERVAL);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }


        mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult)  {

                UserGPS.getInstance().setLocationUser(locationResult.getLastLocation());


                GeoPoint geoPoint = new GeoPoint(UserGPS.getInstance().getLocationUser().getLatitude(), UserGPS.getInstance().getLocationUser().getLongitude());
                Log.d(TAG, "onMaps: \nUpdating  user location ." +
                        "\n latitude: " + geoPoint.getLatitude() +
                        "\n longitude: " + geoPoint.getLongitude());
            }

        },Looper.myLooper());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
