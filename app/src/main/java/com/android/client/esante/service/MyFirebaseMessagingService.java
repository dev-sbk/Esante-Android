package com.android.client.esante.service;

import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.support.v4.app.NotificationCompat;

import com.android.client.esante.R;
import com.android.client.esante.util.AppUtil;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

/**
 * Created by DEV-PC on 16/06/2017.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("message"));
    }

    private void showNotification(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        builder.setLargeIcon(BitmapFactory.decodeResource((this.getResources()), R.drawable.rendez_vous));
        builder.setContentTitle("LIFE - SAVER");
        builder.setContentText(message);
        builder.setSubText(AppUtil.getDate(new Date()));
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) (this.getSystemService(NOTIFICATION_SERVICE));
        notificationManager.notify(0, builder.build());
    }
}
