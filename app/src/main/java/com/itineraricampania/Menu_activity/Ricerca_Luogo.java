package com.itineraricampania.Menu_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itineraricampania.Menu_activity.Viaggio.MapsActivity;
import com.itineraricampania.Menu_activity.Viaggio.MapsActivity_Genere;
import com.itineraricampania.Models.Place;
import java.util.ArrayList;
import com.itineraricampania.R;




public class Ricerca_Luogo extends AppCompatActivity {

    private static final String TAG = "Ricerca_Luogo";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    final ArrayList<String> luogo = new ArrayList<>();
    private FirebaseFirestore mDb;
    private CollectionReference locationRef;
    ArrayList<Place> Places = new ArrayList<>();
    private int i = 0;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ricerca__luogo);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        final ListView list = findViewById(R.id.list_item);
        final EditText thefilter = findViewById(R.id.search_filter);


        setSupportActionBar(toolbar);

        mDb = FirebaseFirestore.getInstance();
        locationRef = mDb.collection("luogo");

        locationRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        //LETTURA ELEMENT BY ELEMENT DEI LUOGHI
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Places.add(documentSnapshot.toObject(Place.class));
                            final Place place = documentSnapshot.toObject(Place.class);
                            Log.d(TAG,"onSearch: Luogo(place): " + place.getLuogo());
                            luogo.add(place.getLuogo());


                            Log.d(TAG,"onSearch: Luogo(array): " + luogo.get(i));
                            Log.d(TAG,"onSearch: indice: " );


                             adapter = new ArrayAdapter(Ricerca_Luogo.this , android.R.layout.simple_list_item_1, luogo);
                            list.setAdapter(adapter);


                            thefilter.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    (Ricerca_Luogo.this).adapter.getFilter().filter(s);
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d(TAG," OnRicerca: Luogo "+luogo.get(position));
                                    thefilter.setText(luogo.get(position) );
                                    mPreferences = PreferenceManager.getDefaultSharedPreferences(Ricerca_Luogo.this);
                                    mEditor = mPreferences.edit();
                                    mEditor.putString( luogo.get(position),"True");
                                    mEditor.commit();

                                    Intent intent = new Intent(Ricerca_Luogo.this,MapsActivity_Genere.class);
                                    intent.putExtra("Luogo",luogo.get(position));
                                    startActivity(intent);


                                }
                            });

                        }
                    }
                });





    }

}