package com.itineraricampania;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import com.itineraricampania.Menu_activity.Viaggia;

public class Main_menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final String EXTRA_NUMBER = "com.itinerari.EXTRA_NUMBER";
    private static final String TAG = "Main_menu";

    public int museiINT = 0;
    public int teatriINT = 0;
    public int ristorantiINT = 0;
    public int piazzeINT = 0;
    public int chieseINT = 0;
    public int monumentiINT = 0;

    Switch musei ;
    Switch teatri ;
    Switch ristoranti ;
    Switch piazze  ;
    Switch chiese ;
    Switch monumenti ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);

         musei = findViewById(R.id.MuseiS);
         teatri = findViewById(R.id.TeatriS);
         ristoranti = findViewById(R.id.RistorantiS);
         piazze = findViewById(R.id.PiazzeS);
         chiese = findViewById(R.id.ChieseS);
         monumenti = findViewById(R.id.MonumentiS);
        navigationView.setNavigationItemSelectedListener(this);






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {











            // Handle the camera action
            Intent intent = new Intent(Main_menu.this , Viaggia.class);

            intent.putExtra(EXTRA_NUMBER,museiINT);
            intent.putExtra(EXTRA_NUMBER,teatriINT);
            intent.putExtra(EXTRA_NUMBER,ristorantiINT);
            intent.putExtra(EXTRA_NUMBER,piazzeINT);
            intent.putExtra(EXTRA_NUMBER,chieseINT);
            intent.putExtra(EXTRA_NUMBER,monumentiINT);

            startActivity(intent);


        } else if (id == R.id.nav_hotel) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);




        return true;
    }

             @Override
             public void onClick(View v) {
                 if (v.getId() == R.id.MuseiS) {

                     if(musei.isChecked()) {
                         museiINT = 1;
                         Log.d(TAG, "onMenu: Musei = checked");
                     }
                 }
                 if (v.getId() == R.id.TeatriS) {

                     if(teatri.isChecked()) {
                         teatriINT = 1;
                         Log.d(TAG, "onMenu: Teatri = checked");
                     }
                 }

                 if (v.getId() == R.id.RistorantiS) {
                     if(ristoranti.isChecked()) {
                         ristorantiINT = 1;
                         Log.d(TAG, "onMenu: Ristoranti = checked");
                     }
                 }

                 if (v.getId() == R.id.PiazzeS) {

                     if(piazze.isChecked()) {
                         piazzeINT = 1;
                         Log.d(TAG, "onMenu: Piazze = checked");
                     }
                 }
                 if (v.getId() == R.id.ChieseS) {
                     if(chiese.isChecked()) {
                         chieseINT = 1;
                         Log.d(TAG, "onMenu: Chiese = checked");
                     }
                 }
                 if (v.getId() == R.id.MonumentiS) {
                     if (monumenti.isChecked()) {

                     monumentiINT = 1;
                     Log.d(TAG, "onMenu: Monumenti = checked");
                    }
                 }
             }
         }



