package com.android.client.esante.converter;

import com.android.client.esante.domain.Contact;
import com.android.client.esante.domain.Docteur;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class DocteurConverter {
    @SerializedName("data")
    @Expose
    private ArrayList<Docteur> docteurs = new ArrayList<Docteur>();

    public ArrayList<Docteur> getDocteurs() {
        return docteurs;
    }

    public void setDocteurs(ArrayList<Docteur> docteurs) {
        this.docteurs = docteurs;
    }
}
