package com.itineraricampania.Menu_activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.itineraricampania.Main_menu;
import com.itineraricampania.Menu_activity.Viaggio.Lista_Luoghi_Libero;
import com.itineraricampania.Menu_activity.Viaggio.MapsActivity;
import com.itineraricampania.Menu_activity.Viaggio.MapsActivity_Genere;
import com.itineraricampania.R;

public class Viaggia extends AppCompatActivity {

    private static final String TAG = "Viaggia";
    private FirebaseFirestore mFirestore;
    private FusedLocationProviderClient mFusedLocationClient;

    public int museiINT = 0;
    public int teatriINT = 0;
    public int ristorantiINT = 0;
    public int piazzeINT = 0;
    public int chieseINT = 0;
    public int monumentiINT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viaggia);

        Button libero = findViewById(R.id.Libero);
        Button genere = findViewById(R.id.Genere);

        Intent intent =getIntent();
        museiINT = intent.getIntExtra(Main_menu.EXTRA_NUMBER,0);
        teatriINT = intent.getIntExtra(Main_menu.EXTRA_NUMBER,0);
        ristorantiINT = intent.getIntExtra(Main_menu.EXTRA_NUMBER,0);
        piazzeINT = intent.getIntExtra(Main_menu.EXTRA_NUMBER,0);
        chieseINT = intent.getIntExtra(Main_menu.EXTRA_NUMBER,0);
        monumentiINT = intent.getIntExtra(Main_menu.EXTRA_NUMBER,0);



            if(museiINT== 1) {

                Log.d(TAG, "onMenu: Musei = checked");
            }



            if(teatriINT== 1) {

                Log.d(TAG, "onMenu: Teatri = checked");
            }



            if(ristorantiINT== 1) {

                Log.d(TAG, "onMenu: Ristoranti = checked");
            }




            if(piazzeINT== 1) {

                Log.d(TAG, "onMenu: Piazze = checked");
            }


            if(chieseINT== 1) {

                Log.d(TAG, "onMenu: Chiese = checked");
            }


            if (monumentiINT == 1) {


                Log.d(TAG, "onMenu: Monumenti = checked");
            }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFirestore = FirebaseFirestore.getInstance();


        libero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastKnownLocation();


                Intent intent = new Intent(Viaggia.this, Lista_Luoghi_Libero.class);



                startActivity(intent);
                finish();
            }
        });

        genere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastKnownLocation();
                Intent intent = new Intent(Viaggia.this, Ricerca_Luogo.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void getLastKnownLocation() {



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()){
                    Location location = task.getResult();
                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(),location.getLongitude());

                    Log.d(TAG,"onComplete: latitude: " + geoPoint.getLatitude());
                    Log.d(TAG,"onComplete: latitude: " + geoPoint.getLongitude());
                }
            }
        });
    }
















}
