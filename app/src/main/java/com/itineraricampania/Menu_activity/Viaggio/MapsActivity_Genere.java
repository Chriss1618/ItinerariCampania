package com.itineraricampania.Menu_activity.Viaggio;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.itineraricampania.Models.Locations;
import com.itineraricampania.Models.Place;
import com.itineraricampania.Models.UserGPS;
import com.itineraricampania.R;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity_Genere extends FragmentActivity implements OnMapReadyCallback {


    private static final String TAG = "MapsActivity_Genere";
    private Context context = MapsActivity_Genere.this;

    ArrayList<Place> Places = new ArrayList<>();
    private final static long UPDATE_INTERVAL = 4 * 1000;  /* 4 secs */
    private final static long FASTEST_INTERVAL = 2000; /* 2 sec */


    //maps
    private GoogleMap mMap;
    private String Luogo;
    private FusedLocationProviderClient mFusedLocationClient;
    Query query;
    private GeoApiContext mGeoApiContext = null;
    public Locations location = new Locations();
    private GeoPoint geoPoint;
    private Location LocationUser;
    private LatLngBounds mMapBoundary;

    //POPUP LUOGO
    Dialog dialogLuogo;
    ImageView Im_luogo;
    Button ok;
    TextView titoloLuogo;
    TextView indirizzoLuogo;

    //database
    private FirebaseFirestore mDb;
    private CollectionReference locationRef;


    private boolean isUpdatingLocationUser() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.codingwithmitch.googledirectionstest.services.LocationService".equals(service.service.getClassName())) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.");
                return true;
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.");
        return false;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps__genere);

        Intent inComingIntent=getIntent();
        Luogo =inComingIntent.getStringExtra("Luogo");
        Log.d(TAG," OnRicerca: Luogo "+ Luogo );

        mDb = FirebaseFirestore.getInstance();
        locationRef = mDb.collection("luogo");
        query = locationRef.whereEqualTo("Luogo", Luogo);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);




        dialogLuogo = new  Dialog(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);






    }

    @Override
    protected void onStart() {
        super.onStart();

        loadPlace();


    }

    public void loadPlace(){

        query.get()

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                        //LETTURA ELEMENT BY ELEMENT DEI LUOGHI
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            Places.add(documentSnapshot.toObject(Place.class));
                            final Place place = documentSnapshot.toObject(Place.class);

                            Log.d(TAG, "onMaps: latitude: " + place.getLocation().getLatitude());
                            Log.d(TAG, "onMaps: Longitude: " + place.getLocation().getLongitude());


                            //caricamento testi
                            final String imm_luogo = place.getImg_1();
                            String titolo_luogo = place.getLuogo();

                            String indirizzo_luogo = place.getIndirizzo();


                            //costruzione dialog
                            dialogLuogo.setContentView(R.layout.luogo);

                            //settaggio del Layout
                            Im_luogo = dialogLuogo.findViewById(R.id.image_Luogo);
                            titoloLuogo = dialogLuogo.findViewById(R.id.titolo_luogo);
                            indirizzoLuogo = dialogLuogo.findViewById(R.id.indirizzo_luogo);


                            indirizzoLuogo.setText(indirizzo_luogo);

                            titoloLuogo.setText(titolo_luogo);
                            Glide.with(context).load(imm_luogo).into(Im_luogo);
                            ok = dialogLuogo.findViewById(R.id.ok);
                            Log.d(TAG, "onMaps: IMG: " + imm_luogo);


                            //CHIUSURA DIALOGO
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialogLuogo.dismiss();
                                    ((ViewGroup) Im_luogo.getParent()).removeView(Im_luogo);
                                    IconGenerator iconGenerator = new IconGenerator(context.getApplicationContext());
                                    Glide.with(context).load(imm_luogo).into(Im_luogo);
                                    int markerWidth = (int) context.getResources().getDimension(R.dimen.custom_marker_image);
                                    int markerHeight = (int) context.getResources().getDimension(R.dimen.custom_marker_image);
                                    Im_luogo.setLayoutParams(new ViewGroup.LayoutParams(markerWidth, markerHeight));
                                    int padding = (int) context.getResources().getDimension(R.dimen.custom_marker_padding);
                                    Im_luogo.setPadding(padding, padding, padding, padding);
                                    iconGenerator.setContentView(Im_luogo);
                                    Bitmap icon = iconGenerator.makeIcon();

                                    mMap.addMarker(new MarkerOptions()


                                            .position(new LatLng(place.getLocation().getLatitude(), place.getLocation().getLongitude()))
                                            .title(place.getSottogategoria())
                                            .icon(BitmapDescriptorFactory.fromBitmap(icon)));

                                    calculateDirections(place.getLocation());

                                }
                            });


                            dialogLuogo.show();


                            location.setGeo_point(place.getLocation());
                            location.setTimestamp(null);
                            location.setPlace(place.getLuogoID());

                            Log.d(TAG, "onMaps: latitude of Location: " + place.getLocation().getLatitude());
                            Log.d(TAG, "onMaps: Longitude of Location: " + place.getLocation().getLongitude());

                            break;
                        }

                        }

                    }
                });

    }


    public void setCameraView(){

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

                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary,0));
                    //startLocationService();
                }
            }
        });






    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

        setCameraView();

        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_map_api_key))
                    .build();

        }
    }

    private void calculateDirections(GeoPoint geoPoint){
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

                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage() );

            }
        });
    }

    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: result routes: " + result.routes.length);

                for(DirectionsRoute route: result.routes){
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor( context ,R.color.Grey));
                    polyline.setClickable(true);

                }
            }
        });
    }


}
