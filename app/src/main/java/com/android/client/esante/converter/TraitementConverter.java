package com.android.client.esante.converter;
import com.android.client.esante.domain.Traitement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
public class TraitementConverter {
    @SerializedName("data")
    @Expose
    private ArrayList<Traitement> traitements = new ArrayList<Traitement>();

    public ArrayList<Traitement> getTraitements() {
        return traitements;
    }

    public void setTraitements(ArrayList<Traitement> traitements) {
        this.traitements = traitements;
    }
}
