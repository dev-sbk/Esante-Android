package com.android.client.esante.util;
import android.util.Log;
import com.android.client.esante.activity.DocteurActivity;
import com.android.client.esante.activity.PatientActivity;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.StringConverter;
import java.text.SimpleDateFormat;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AppUtil {
    public static String getDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }
    public static  void registerToken(String token) {
        ApiService api = RetroClient.getApiService();
        if(PatientActivity.id>0 || DocteurActivity.id>0){
            Call<StringConverter> call = api.registerToken(token,PatientActivity.id, DocteurActivity.id );
            call.enqueue(new Callback<StringConverter>() {
                @Override
                public void onResponse(Call<StringConverter> call, Response<StringConverter> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null){
                            Log.v("Response",response.body().getMessage());
                        }
                    }
                }
                @Override
                public void onFailure(Call<StringConverter> call, Throwable t) {
                    Log.v("Failure",t.getMessage());
                }
            });
        }

    }
}
