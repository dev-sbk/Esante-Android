package com.android.client.esante.converter;

import com.android.client.esante.domain.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class PatientConverter {
    @SerializedName("data")
    @Expose
    private ArrayList<Patient> patients = new ArrayList<>();

    /**
     * @return The patients
     */
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * @param patients The contacts
     */
    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }
}
