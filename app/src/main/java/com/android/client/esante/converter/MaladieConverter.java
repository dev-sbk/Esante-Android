package com.android.client.esante.converter;

import com.android.client.esante.domain.Maladie;
import com.android.client.esante.domain.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class MaladieConverter {
    @SerializedName("data")
    @Expose
    private ArrayList<Maladie> maladies = new ArrayList<Maladie>();

    /**
     * @return The maladies
     */
    public ArrayList<Maladie> getMaladies() {
        return maladies;
    }

    /**
     * @param maladies The maladies
     */
    public void setMaladies(ArrayList<Maladie> maladies) {
        this.maladies = maladies;
    }
}
