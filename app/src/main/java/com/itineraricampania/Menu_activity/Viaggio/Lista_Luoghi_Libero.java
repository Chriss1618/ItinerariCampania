package com.itineraricampania.Menu_activity.Viaggio;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.model.DirectionsResult;
import com.itineraricampania.Menu_activity.Viaggia;
import com.itineraricampania.Models.Locations;
import com.itineraricampania.Models.Place;
import com.itineraricampania.Models.UserGPS;
import com.itineraricampania.R;

public class Lista_Luoghi_Libero extends AppCompatActivity {

    private static final String TAG = "Lista_Luoghi_Libero";
    private FirebaseFirestore mFirestore;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLngBounds mMapBoundary;
    private GeoApiContext mGeoApiContext = null;
    private Context context = Lista_Luoghi_Libero.this;
    private FirebaseFirestore mDb;
    private CollectionReference locationRef;
    private Location LocationUser;
    private Place Place ;
    private GeoPoint geoPoint;
    private Locations location;

    // LUOGO

    ImageView Im_luogo;
    TextView indirizzoLuogo;
    TextView orario;
    TextView contatto;
    TextView titoloLuogo;
    TextView descrizioneLuogo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista__luoghi);

        Button libero = findViewById(R.id.Libero);

        //settaggio del Layout
        Im_luogo = findViewById(R.id.image_Luogo);
        indirizzoLuogo = findViewById(R.id.indirizzo_luogo);
        orario = findViewById(R.id.orario_luogo);
        contatto = findViewById(R.id.contatto_luogo);
        titoloLuogo = findViewById(R.id.titolo_luogo);
        descrizioneLuogo = findViewById(R.id.descrizione_luogo);


        mDb = FirebaseFirestore.getInstance();
        locationRef = mDb.collection("luogo");



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFirestore = FirebaseFirestore.getInstance();



        loadPlace();


        libero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Lista_Luoghi_Libero.this, MapsActivity.class);



                startActivity(intent);
                finish();
            }
        });



    }

    public void loadPlace(){


        locationRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        //LETTURA ELEMENT BY ELEMENT DEI LUOGHI
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                            final Place place = documentSnapshot.toObject(Place.class);

                            Place = place;

                            Log.d(TAG,"onLuoghi: latitude: " + place.getLocation().getLatitude());
                            Log.d(TAG,"onLuoghi: Longitude: " + place.getLocation().getLongitude());
                            Log.d(TAG,"onLuoghi: img: " + place.getImg_1());
                            Log.d(TAG,"onLuoghi: orari: " + place.getOrari());
                            Log.d(TAG,"onLuoghi: contatto: " + place.getTelefono());
                            Log.d(TAG,"onLuoghi: titolo: " + place.getLuogo());






                            //caricamento testi
                            String imm_luogo = place.getImg_1();
                            String orario_luogo = place.getOrari();
                            String titolo_luogo = place.getLuogo();
                            String descrizione_luogo = place.getDescrizione();
                            String indirizzo_luogo = place.getIndirizzo();
                            String contatto_luogo= place.getTelefono();






                            Glide.with(context).load(imm_luogo).into(Im_luogo);
                            indirizzoLuogo.setText(indirizzo_luogo);
                            orario.setText(orario_luogo);
                            contatto.setText(contatto_luogo);
                            titoloLuogo.setText(titolo_luogo);
                            descrizioneLuogo.setText(descrizione_luogo);



                            Log.d(TAG,"onMaps: IMG: " + imm_luogo );












                            Log.d(TAG,"onMaps: latitude of Location: " + place.getLocation().getLatitude());
                            Log.d(TAG,"onMaps: Longitude of Location: " + place.getLocation().getLongitude());

                            //calculateDirections();
                            break;


                        }

                    }
                });

    }


    private void calculateDirections(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()){

                    LocationUser = task.getResult();
                    geoPoint = new GeoPoint(LocationUser.getLatitude(),LocationUser.getLongitude());
                    UserGPS.getInstance().setLocationUser(LocationUser);
                    UserGPS.getInstance().setGeoPoint(geoPoint);

                    UserGPS.getInstance().setGeoPoint(geoPoint);


                    Log.d(TAG, "onMaps: \nUserGPS  user location ." +
                            "\n latitude: " + UserGPS.getInstance().getLocationUser().getLatitude() +
                            "\n longitude: " + UserGPS.getInstance().getLocationUser().getLongitude());

                    double bottomBoundary = geoPoint.getLatitude() - .1;
                    double leftBoundary = geoPoint.getLongitude() - .1;
                    double topBoundary = geoPoint.getLatitude() + .1;
                    double rightBoundary = geoPoint.getLongitude() + .1;

                    mMapBoundary = new LatLngBounds(
                            new LatLng(bottomBoundary, leftBoundary),
                            new LatLng(topBoundary,rightBoundary)
                    );

                   // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary,0));
                    //startLocationService();
                }
            }
        });

        Log.d(TAG, "calculateDirections: calculating directions.");

        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                geoPoint.getLatitude(),
                geoPoint.getLongitude()
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(true);
        directions.origin(
                new com.google.maps.model.LatLng(
                        UserGPS.getInstance().getGeoPoint().getLatitude(),
                        UserGPS.getInstance().getGeoPoint().getLongitude()
                )
        );

        Log.d(TAG, "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "calculateDirections: routes: " + result.routes[0].toString());
                Log.d(TAG, "calculateDirections: duration: " + result.routes[0].legs[0].duration);
                Log.d(TAG, "calculateDirections: distance: " + result.routes[0].legs[0].distance);
                Log.d(TAG, "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());

            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage() );

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
