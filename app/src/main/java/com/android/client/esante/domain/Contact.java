package com.android.client.esante.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DEV-PC on 08/06/2017.
 */

public class Contact {
    @SerializedName("id_contact")
    @Expose
    private int idPatient;
    @SerializedName("nom_contact")
    @Expose
    private String firstName;
    @SerializedName("prenom_contact")
    @Expose
    private String lastName;
    @SerializedName("Relation_ship")
    @Expose
    private String relationShip;
    @SerializedName("num_tel_contact")
    @Expose
    private String tel;
    @SerializedName("num_gsm_contact")
    @Expose
    private String gsm;

    public Contact() {

    }

    public Contact(int idPatient, String firstName, String lastName, String relationShip, String tel, String gsm) {
        this.idPatient = idPatient;
        this.firstName = firstName;
        this.lastName = lastName;
        this.relationShip = relationShip;
        this.tel = tel;
        this.gsm = gsm;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "idPatient=" + idPatient +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", relationShip='" + relationShip + '\'' +
                ", tel='" + tel + '\'' +
                ", gsm='" + gsm + '\'' +
                '}';
    }
}
