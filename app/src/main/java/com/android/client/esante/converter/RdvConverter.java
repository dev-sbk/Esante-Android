package com.android.client.esante.converter;

import com.android.client.esante.domain.RendezVous;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class RdvConverter {
    @SerializedName("data")
    @Expose
    private ArrayList<RendezVous> rdvs = new ArrayList<RendezVous>();
    /**
     * @return The rdvs
     */
    public ArrayList<RendezVous> getRdvs() {
        return rdvs;
    }

    /**
     * @param rdvs The rdvs
     */
    public void setRdvs(ArrayList<RendezVous> rdvs) {
        this.rdvs = rdvs;
    }
}
