package com.itineraricampania.ActivityAccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.itineraricampania.LogIn_Activity;
import com.itineraricampania.Models.User;
import com.itineraricampania.R;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Singup extends AppCompatActivity implements
        View.OnClickListener

{


    private Button Singup_button;
    private EditText Singup_email;
    private EditText Singup_Pass;
    private EditText Singup_nome;
    private EditText Singup_cognome;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        Singup_button = findViewById(R.id.Singup_button);
        Singup_email = findViewById(R.id.Singup_email);
        Singup_Pass = findViewById(R.id.Singup_Pass);
        Singup_nome = findViewById(R.id.Singup_nome);
        Singup_cognome = findViewById(R.id.Singup_cognome);

        Singup_button.setOnClickListener(this);
    }


    public void createNewUser(String nome ,String cognome ,String email,String password) {
        FirebaseFirestore mDb = FirebaseFirestore.getInstance();

        DocumentReference newUserRef = mDb.collection("User")
                .document();

        User user = new User();

        user.setUserID(newUserRef.getId());
        user.setEmail(email);
        user.setPassword(password);
        user.setNome(nome);
        user.setCognome(cognome);

        newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //INSERIMENTO RIUSCITO
                    Toast.makeText(Singup.this,"Registrato", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Singup.this, Login.class);
                    startActivity(intent);


                }else{
                    //INSERIMENTO FALLITO
                    Intent intent = new Intent(Singup.this, Login.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == Singup_button ){
            registerUser();
        }
    }

    private void registerUser(){

        String nome = Singup_nome.getText().toString().trim();
        String cognome = Singup_cognome.getText().toString().trim();
        final String email = Singup_email.getText().toString().trim();
        String password = Singup_Pass.getText().toString().trim();


        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"INSERIRE EMAIL", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"INSERIRE PASSWORD", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(nome)){
            Toast.makeText(this,"INSERIRE NOME", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(cognome)){
            Toast.makeText(this,"INSERIRE COGNOME", Toast.LENGTH_SHORT).show();
            return;

        }





        progressDialog.setMessage("Registrando... ");
        progressDialog.show();



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.cancel();
                            Toast.makeText(Singup.this,"REGISTRAZIONE COMPLETATA",Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.

                            progressDialog.cancel();
                            Toast.makeText(Singup.this,"REGISTRAZIONE FALLITA",Toast.LENGTH_SHORT).show();


                        }


                    }
                });


            createNewUser(nome, cognome, email, password);




    }




}
