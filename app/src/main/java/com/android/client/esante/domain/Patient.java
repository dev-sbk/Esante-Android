package com.android.client.esante.domain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Patient{
    @SerializedName("id_patient")
    @Expose
    private int idPatient;
    @SerializedName("id_medecin")
    @Expose
    private int idMedecin;
    @SerializedName("nom")
    @Expose
    private String lastName;
    @SerializedName("prenom")
    @Expose
    private String firstName;
    @SerializedName("date_N")
    @Expose
    private String birthDay;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("adresse")
    @Expose
    private String adress;
    @SerializedName("etat")
    @Expose
    private String etat;
    @SerializedName("numero_tel")
    @Expose
    private String tel;
    @SerializedName("sexe")
    @Expose
    private String gender;
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("cheveux")
    @Expose
    private String hair;
    @SerializedName("couleur_yeux")
    @Expose
    private String eyeColor;
    @SerializedName("taille")
    @Expose
    private double height;
    @SerializedName("poids")
    @Expose
    private double weight;
    @SerializedName("groupe_sanguin")
    @Expose
    private String boodType;
    public Patient() {
    }

    public Patient(int idPatient, String lastName, String firstName, String birthDay, String email, String adress, String etat, String tel, String gender, String qrCode, String hair, String eyeColor, double height, double weight, String boodType) {
        this.idPatient = idPatient;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDay = birthDay;
        this.email = email;
        this.adress = adress;
        this.etat = etat;
        this.tel = tel;
        this.gender = gender;
        this.qrCode = qrCode;
        this.hair = hair;
        this.eyeColor = eyeColor;
        this.height = height;
        this.weight = weight;
        this.boodType = boodType;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBoodType() {
        return boodType;
    }

    public void setBoodType(String boodType) {
        this.boodType = boodType;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", idMedecin=" + idMedecin +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", email='" + email + '\'' +
                ", adress='" + adress + '\'' +
                ", etat='" + etat + '\'' +
                ", tel='" + tel + '\'' +
                ", gender='" + gender + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", hair='" + hair + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", boodType='" + boodType + '\'' +
                '}';
    }
}
