package com.android.client.esante.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DEV-PC on 09/06/2017.
 */

public class Notification {
    @SerializedName("idNotif")
    @Expose
    private int idNotif;
    @SerializedName("id_patient")
    @Expose
    private int idPatient;
    @SerializedName("id_medecin")
    @Expose
    private int idMedecin;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("actived")
    @Expose
    private int actived;

    public Notification() {
    }

    public Notification(int idNotif, int idPatient, int idMedecin, String message, int actived) {
        this.idNotif = idNotif;
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.message = message;
        this.actived = actived;
    }

    public int getIdNotif() {
        return idNotif;
    }

    public void setIdNotif(int idNotif) {
        this.idNotif = idNotif;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIdMedecin() {
        return idMedecin;
    }

    public void setIdMedecin(int idMedecin) {
        this.idMedecin = idMedecin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getActived() {
        return actived;
    }

    public void setActived(int actived) {
        this.actived = actived;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idNotif=" + idNotif +
                ", idPatient=" + idPatient +
                ", idMedecin=" + idMedecin +
                ", message='" + message + '\'' +
                ", actived=" + actived +
                '}';
    }
}
