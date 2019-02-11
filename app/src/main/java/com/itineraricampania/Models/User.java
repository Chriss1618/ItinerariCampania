package com.itineraricampania.Models;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class User{

    private String UserID;
    private String Email;
    private String Password;
    private String nome;
    private String cognome;
    private @ServerTimestamp Date DataCreazione;

    public User(String UserID, String Email, String Password,String nome, String cognome,Date DataCreazione){
        this.UserID = UserID;
        this.Email = Email;
        this.Password = Password;
        this.nome = nome;
        this.cognome = cognome;
        this.DataCreazione = DataCreazione;

    }
    public User(){


    }
    //USERID
    public String getUserID(){
        return UserID;
    }
    public void setUserID(String userID) {
        UserID = userID;
    }

    //EMAIL
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }

    //PASSWORD
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }

    //NOME
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    //COGNOME
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    //DATACREAZIONE
    public Date getDataCreazione() {
        return DataCreazione;
    }
    public void setDataCreazione(Date dataCreazione) {
        DataCreazione = dataCreazione;
    }
}




