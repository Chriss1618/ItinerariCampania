package com.itineraricampania.Models;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Generi {

    private boolean museo;
    private boolean teatri;
    private boolean ritoranti;
    private boolean piazze;
    private boolean chiese;
    private boolean monumenti;


    public Generi(){

    }


    public boolean isMuseo() {
        return museo;
    }

    public void setMuseo(boolean museo) {
        this.museo = museo;
    }

    public boolean isTeatri() {
        return teatri;
    }

    public void setTeatri(boolean teatri) {
        this.teatri = teatri;
    }

    public boolean isRitoranti() {
        return ritoranti;
    }

    public void setRitoranti(boolean ritoranti) {
        this.ritoranti = ritoranti;
    }

    public boolean isPiazze() {
        return piazze;
    }

    public void setPiazze(boolean piazze) {
        this.piazze = piazze;
    }

    public boolean isChiese() {
        return chiese;
    }

    public void setChiese(boolean chiese) {
        this.chiese = chiese;
    }

    public boolean isMonumenti() {
        return monumenti;
    }

    public void setMonumenti(boolean monumenti) {
        this.monumenti = monumenti;
    }
}

