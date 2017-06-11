package com.android.client.esante.converter;

import com.android.client.esante.domain.Contact;
import com.android.client.esante.domain.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ContactConverter {
    @SerializedName("data")
    @Expose
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }
}
