package com.android.client.esante.converter;

import com.android.client.esante.domain.ActeMedicale;
import com.android.client.esante.domain.Maladie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class ActeMedicaleConverter {
    @SerializedName("data")
    @Expose
    private ArrayList<ActeMedicale> acteMedicales = new ArrayList<ActeMedicale>();

    /**
     * @return The maladies
     */
    public ArrayList<ActeMedicale> getActeMedicales() {
        return acteMedicales;
    }

    /**
     * @param acteMedicales The acteMedicales
     */
    public void setActeMedicales(ArrayList<ActeMedicale> acteMedicales) {
        this.acteMedicales = acteMedicales;
    }
}
