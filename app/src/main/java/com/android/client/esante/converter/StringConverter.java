package com.android.client.esante.converter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by DEV-PC on 09/06/2017.
 */

public class StringConverter {
    @SerializedName("message")
    @Expose
    private String message=new String();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
