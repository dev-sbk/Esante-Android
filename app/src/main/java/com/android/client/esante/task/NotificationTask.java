package com.android.client.esante.task;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.client.esante.R;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.NotificationConverter;
import com.android.client.esante.domain.Notification;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;
/**
 * Created by DEV-PC on 09/06/2017.
 */
public class NotificationTask extends AsyncTask<String,Void,Void> {
     private Context context;

    public NotificationTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        String id = params[0] + "/";
        String url = "/esante/mspatient/notification/" + id;
        ApiService api = RetroClient.getApiService();
        Call<NotificationConverter> call = api.notification(url);
        call.enqueue(new Callback<NotificationConverter>() {
            @Override
            public void onResponse(Call<NotificationConverter> call, Response<NotificationConverter> response) {
                if (response.isSuccessful()) {
                    ArrayList<Notification> data = response.body().getNotifications();
                    if(data!=null){
                       for(int i=0;i<data.size();i++){
                           NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                           builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
                           builder.setLargeIcon(BitmapFactory.decodeResource((context.getResources()), R.drawable.rendez_vous));
                           builder.setContentTitle("Nouveau message");
                           builder.setContentText(data.get(i).getMessage());
                           builder.setSubText("Vois plus ");
                           android.app.NotificationManager notificationManager = (android.app.NotificationManager) (context.getSystemService(NOTIFICATION_SERVICE));
                           notificationManager.notify(i+1, builder.build());
                       }
                    }
                }
            }
            @Override
            public void onFailure(Call<NotificationConverter> call, Throwable t) {
                Log.v("Failure", t.getMessage());
                Toast.makeText(context, "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
