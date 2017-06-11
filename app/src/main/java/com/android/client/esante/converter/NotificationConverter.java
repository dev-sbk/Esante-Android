package com.android.client.esante.converter;

import com.android.client.esante.domain.Notification;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by DEV-PC on 09/06/2017.
 */

public class NotificationConverter {
    @SerializedName("data")
    @Expose
    private ArrayList<Notification> notifications=new ArrayList<Notification>();
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
}
