package com.android.client.esante.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActeMedicale {
    @SerializedName("idActe")
    @Expose
    private int idActe;
    @SerializedName("typeActe")
    @Expose
    private String typeActe;
    @SerializedName("heure")
    @Expose
    private String heureActe;
    @SerializedName("diagnosticActe")
    @Expose
    private String diagonisticActe;
    @SerializedName("rapport_medecin")
    @Expose
    private String rapportMedcin;
    public ActeMedicale() {
    }
    public ActeMedicale(int idActe, String typeActe, String heureActe, String diagonisticActe, String rapportMedcin) {
        this.idActe = idActe;
        this.typeActe = typeActe;
        this.heureActe = heureActe;
        this.diagonisticActe = diagonisticActe;
        this.rapportMedcin = rapportMedcin;
    }
    public int getIdActe() {
        return idActe;
    }

    public void setIdActe(int idActe) {
        this.idActe = idActe;
    }

    public String getTypeActe() {
        return typeActe;
    }

    public void setTypeActe(String typeActe) {
        this.typeActe = typeActe;
    }

    public String getHeureActe() {
        return heureActe;
    }

    public void setHeureActe(String heureActe) {
        this.heureActe = heureActe;
    }

    public String getDiagonisticActe() {
        return diagonisticActe;
    }

    public void setDiagonisticActe(String diagonisticActe) {
        this.diagonisticActe = diagonisticActe;
    }

    public String getRapportMedcin() {
        return rapportMedcin;
    }

    public void setRapportMedcin(String rapportMedcin) {
        this.rapportMedcin = rapportMedcin;
    }
}
