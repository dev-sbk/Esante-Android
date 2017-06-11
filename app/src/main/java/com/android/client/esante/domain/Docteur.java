package com.android.client.esante.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Docteur {
    @SerializedName("id")
    @Expose
    private int idDocteur;
    @SerializedName("prenom")
    @Expose
    private String firstName;
    @SerializedName("nom")
    @Expose
    private String lastName;
    @SerializedName("nom_specialite")
    @Expose
    private String specialite;
    @SerializedName("telephone")
    @Expose
    private String tel;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    public Docteur() {
    }

    public Docteur(int idDocteur, String firstName, String lastName, String specialite, String tel, String email, String password) {
        this.idDocteur = idDocteur;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialite = specialite;
        this.tel = tel;
        this.email = email;
        this.password = password;
    }

    public int getIdDocteur() {
        return idDocteur;
    }

    public void setIdDocteur(int idDocteur) {
        this.idDocteur = idDocteur;
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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Docteur{" +
                "idDocteur=" + idDocteur +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialite='" + specialite + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
