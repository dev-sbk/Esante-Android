package com.android.client.esante.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Traitement {
    @SerializedName("id_treatment")
    @Expose
    private int idTraitement;
    @SerializedName("actif")
    @Expose
    private int nbJours;
    @SerializedName("idRDV")
    @Expose
    private int actif;
    @SerializedName("treatment")
    @Expose
    private String traitements;

    public Traitement(int idTraitement, int nbJours, int actif, String traitements) {
        this.idTraitement = idTraitement;
        this.nbJours = nbJours;
        this.actif = actif;
        this.traitements = traitements;
    }

    public int getIdTraitement() {
        return idTraitement;
    }

    public void setIdTraitement(int idTraitement) {
        this.idTraitement = idTraitement;
    }

    public int getNbJours() {
        return nbJours;
    }

    public void setNbJours(int nbJours) {
        this.nbJours = nbJours;
    }

    public int getActif() {
        return actif;
    }

    public void setActif(int actif) {
        this.actif = actif;
    }

    public String getTraitements() {
        return traitements;
    }

    public void setTraitements(String traitements) {
        this.traitements = traitements;
    }

    @Override
    public String toString() {
        return "Traitement{" +
                "idTraitement=" + idTraitement +
                ", nbJours=" + nbJours +
                ", actif=" + actif +
                ", traitements='" + traitements + '\'' +
                '}';
    }
}
