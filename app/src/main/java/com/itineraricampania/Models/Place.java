package com.itineraricampania.Models;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Place {
    private String LuogoID;
    private @ServerTimestamp
    Date DataCreazione;
    private String regione;
    private String provincia;
    private String comune;
    private String luogo;
    private GeoPoint location;
    private String indirizzo;
    private String categoria;
    private String sottogategoria;
    private String descrizione;
    private String orari;
    private String informazioni;
    private String telefono;
    private String mail;
    private String sito;
    private String facebook;
    private String video;
    private String audio;
    private String img_1;
    private String img_2;
    private String img_3;
    private String img_4;
    private String acquisto;



    public Place(
            String LuogoID,
            Date DataCreazione,
            String regione,
            String provincia,
            String comune,
            String luogo,
            GeoPoint location,
            String indirizzo,
            String categoria,
            String sottogategoria,
            String descrizione,
            String orari,
            String informazioni,
            String telefono,
            String mail,
            String sito,
            String facebook,
            String video,
            String audio,
            String img_1,
            String img_2,
            String img_3,
            String img_4,
            String acquisto){

                this.LuogoID = LuogoID;
                this.DataCreazione = DataCreazione;
                this.regione = regione;
                this.provincia = provincia;
                this.comune = comune;
                this.luogo = luogo;
                this.location = location;
                this.indirizzo = indirizzo;
                this.categoria = categoria;
                this.sottogategoria = sottogategoria;
                this.descrizione = descrizione;
                this.orari = orari;
                this.informazioni = informazioni;
                this.telefono = telefono;
                this.mail = mail;
                this.sito = sito;
                this.facebook = facebook;
                this.video = video;
                this.audio = audio;
                this.img_1 = img_1;
                this.img_2 = img_2;
                this.img_3 = img_3;
                this.img_4 = img_4;
                this.acquisto = acquisto;

    }
    public Place(){


    }
    public Place callPlace(Place place){

        Place p;
        p= place;
        return p;
    }

    //ID
    public String getLuogoID() {
        return LuogoID;
    }
    public void setLuogoID(String luogoID) {
        LuogoID = luogoID;
    }

    //DATA
    public Date getDataCreazione() {
        return DataCreazione;
    }
    public void setDataCreazione(Date dataCreazione) {
        DataCreazione = dataCreazione;
    }
    //REGIONE
    public String getRegione() {
        return regione;
    }
    public void setRegione(String regione) {
        this.regione = regione;
    }

    //PROVINCIA
    public String getProvincia() {
        return provincia;
    }
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    //COMUNE
    public String getComune() {
        return comune;
    }
    public void setComune(String comune) {
        this.comune = comune;
    }

    //LUOGO
    public String getLuogo() {
        return luogo;
    }
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    //LOCATION
    public GeoPoint getLocation() {
        return location;
    }
    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    //INDIRIZZO
    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    //CATEGORIA
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    //SOTTOCATEGORIA
    public String getSottogategoria() {
        return sottogategoria;
    }
    public void setSottogategoria(String sottogategoria) {
        this.sottogategoria = sottogategoria;
    }

    //DESCRIZIONE
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    //ORARI
    public String getOrari() {
        return orari;
    }
    public void setOrari(String orari) {
        this.orari = orari;
    }

    //INFORMAZIONI
    public String getInformazioni() {
        return informazioni;
    }
    public void setInformazioni(String informazioni) {
        this.informazioni = informazioni;
    }

    //TELEFONO
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //MAIL
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    //SITO
    public String getSito() {
        return sito;
    }
    public void setSito(String sito) {
        this.sito = sito;
    }

    //FACEBOOK
    public String getFacebook() {
        return facebook;
    }
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    //VIDEO
    public String getVideo() {
        return video;
    }
    public void setVideo(String video) {
        this.video = video;
    }

    //AUDIO
    public String getAudio() {
        return audio;
    }
    public void setAudio(String audio) {
        this.audio = audio;
    }

    //IMG1
    public String getImg_1() {
        return img_1;
    }
    public void setImg_1(String img_1) {
        this.img_1 = img_1;
    }

    //IMG2
    public String getImg_2() {
        return img_2;
    }
    public void setImg_2(String img_2) {
        this.img_2 = img_2;
    }

    //IMG3
    public String getImg_3() {
        return img_3;
    }
    public void setImg_3(String img_3) {
        this.img_3 = img_3;
    }

    //IMG
    public String getImg_4() {
        return img_4;
    }
    public void setImg_4(String img_4) {
        this.img_4 = img_4;
    }

    //AQUISTO
    public String getAcquisto() {
        return acquisto;
    }
    public void setAcquisto(String acquisto) {
        this.acquisto = acquisto;
    }
}
