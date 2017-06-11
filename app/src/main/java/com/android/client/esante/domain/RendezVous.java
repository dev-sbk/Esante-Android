package com.android.client.esante.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;



public class RendezVous {
    @SerializedName("idRDV")
    @Expose
    private int idRDV;
    @SerializedName("dateRDV")
    @Expose
    private String dateRDV;
    @SerializedName("heureRDV")
    @Expose
    private String heureRDV;
    @SerializedName("valide")
    @Expose
    private int valide;
    @SerializedName("id_medecin")
    @Expose
    private int idMedcin;
    @SerializedName("id_patient")
    @Expose
    private int idPatient;

    public RendezVous() {
    }

    public RendezVous(String heureRDV, int valide, int idMedcin, int idPatient) {
        this.heureRDV = heureRDV;
        this.valide = valide;
        this.idMedcin = idMedcin;
        this.idPatient = idPatient;
    }

    public int getIdRDV() {
        return idRDV;
    }

    public void setIdRDV(int idRDV) {
        this.idRDV = idRDV;
    }

    public String getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(String dateRDV) {
        this.dateRDV = dateRDV;
    }

    public String getHeureRDV() {
        return heureRDV;
    }

    public void setHeureRDV(String heureRDV) {
        this.heureRDV = heureRDV;
    }

    public int getValide() {
        return valide;
    }

    public void setValide(int valide) {
        this.valide = valide;
    }

    public int getIdMedcin() {
        return idMedcin;
    }

    public void setIdMedcin(int idMedcin) {
        this.idMedcin = idMedcin;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "idRDV=" + idRDV +
                ", dateRDV='" + dateRDV + '\'' +
                ", heureRDV='" + heureRDV + '\'' +
                ", valide=" + valide +
                ", idMedcin=" + idMedcin +
                ", idPatient=" + idPatient +
                '}';
    }
}
