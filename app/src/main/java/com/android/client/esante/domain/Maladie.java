package com.android.client.esante.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Maladie {
    @SerializedName("idMaladie")
    @Expose
    private int idMaladie;
    @SerializedName("maladie")
    @Expose
    private String maladie;
    @SerializedName("precaution")
    @Expose
    private String precaution;
    @SerializedName("description")
    @Expose
    private String description;

    public Maladie() {
    }

    public Maladie(int idMaladie, String maladie, String precaution, String description) {
        this.idMaladie = idMaladie;
        this.maladie = maladie;
        this.precaution = precaution;
        this.description = description;
    }

    public int getIdMaladie() {
        return idMaladie;
    }

    public void setIdMaladie(int idMaladie) {
        this.idMaladie = idMaladie;
    }

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

    public String getPrecaution() {
        return precaution;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
