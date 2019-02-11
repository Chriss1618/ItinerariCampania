package com.itineraricampania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.itineraricampania.ActivityAccount.Login;




public class LogIn_Activity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Intent i = new Intent (this,Login.class);
        Thread timer = new Thread(){

                public void run () {

                    try{
                            sleep(2000);

                            } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    finally {
                        startActivity(i);
                        finish();
                     }
                    }

                };
                timer.start();
    }
}
