package com.itineraricampania.ActivityAccount;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.itineraricampania.Constants.Costants;
import com.itineraricampania.Main_menu;
import com.itineraricampania.R;

import static com.itineraricampania.Constants.Costants.*;
import static com.itineraricampania.Constants.Costants.ERROR_DIALOG_REQUEST;
import static com.itineraricampania.Constants.Costants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.itineraricampania.Constants.Costants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    //vars
    TextView txt;
    private EditText text_email, text_pass;
    private FirebaseAuth auth;
    private ProgressDialog progressdialog;

    //PERMESSI
    private boolean mLocationPermissionGranted = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        //Registrazione_Activity
        txt =  findViewById(R.id.registrati_link);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Login.this, Singup.class);
                startActivity(intent);
            }
        });

        //initialization_date
        auth = FirebaseAuth.getInstance();

        progressdialog = new ProgressDialog(this);
        text_email = findViewById(R.id.login_email);
        text_pass = findViewById(R.id.Login_Pass);
        Button button_login = findViewById(R.id.button);


        button_login.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String email = text_email.getText().toString();
                                                final String password = text_pass.getText().toString();

                                                if(TextUtils.isEmpty(email)){
                                                    Toast.makeText(getApplicationContext(),"Inserire Email!", Toast.LENGTH_SHORT).show();
                                                    return ;
                                                }

                                                if(TextUtils.isEmpty(password)){
                                                    Toast.makeText(getApplicationContext(),"Inserire Password!", Toast.LENGTH_SHORT).show();
                                                    return ;
                                                }

                                                progressdialog.setMessage("Accedendo... ");
                                                progressdialog.show();

                                                auth.signInWithEmailAndPassword(email,password)
                                                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if (!task.isSuccessful()){

                                                                    if (password.length()<6 ){
                                                                        Toast.makeText(Login.this, "Password", Toast.LENGTH_SHORT).show();

                                                                    }else{

                                                                        Toast.makeText(Login.this, "Errore Accesso", Toast.LENGTH_SHORT).show();
                                                                    }

                                                                }else{

                                                                    Intent intent = new Intent (Login.this, Main_menu.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            }


                                                        });

                                            }
                                        });

    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("L'applicazione necessita del GPS, vuoi attivarlo?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //getChatrooms();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Login.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Login.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

            if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        if (requestCode == PERMISSIONS_REQUEST_ENABLE_GPS) {
                if(mLocationPermissionGranted){
                    Log.d(TAG, "Ottimo");
                }
                else{
                    getLocationPermission();
                }
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                Toast.makeText(this, "Ottimo", Toast.LENGTH_SHORT).show();
            }else{

                getLocationPermission();
            }
        }
    }
}





