package com.android.client.esante.task;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.android.client.esante.activity.DocteurActivity;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.DocteurConverter;
import com.android.client.esante.domain.Docteur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AuthenticationSync extends AsyncTask<String, String, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    public AuthenticationSync(Context context) {
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Authentification ...");
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(String... params) {
        String email = params[0];
        String password = params[1];
        ApiService api = RetroClient.getApiService();
        Call<DocteurConverter> call = api.authentication(email,password);
        call.enqueue(new Callback<DocteurConverter>() {
            @Override
            public void onResponse(Call<DocteurConverter> call, Response<DocteurConverter> response) {
                if (response.isSuccessful()) {
                    Log.v("Response",""+response.code());
                    if(response.body().getDocteurs()!=null){
                        Docteur doc = response.body().getDocteurs().get(0);
                        Intent i = new Intent(context, DocteurActivity.class);
                        i.putExtra("id", doc.getIdDocteur());
                        if (doc.getLastName() != null)
                            i.putExtra("nom", doc.getLastName());
                        else i.putExtra("nom", "");
                        if (doc.getFirstName() != null)
                            i.putExtra("prenom", doc.getFirstName());
                        else i.putExtra("prenom", "");
                        if (doc.getEmail() != null)
                            i.putExtra("email", doc.getEmail());
                        else i.putExtra("email", "");
                        ((Activity) context).finish();
                        ((Activity) context).startActivity(i);

                    }else
                        Toast.makeText(context.getApplicationContext(),"ERREUR : QR CODE INVALIDATE",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DocteurConverter> call, Throwable t) {
                Log.v("Failure",t.getMessage());
                Toast.makeText(context.getApplicationContext(),"Authentication Failure",Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        super.onPostExecute(aVoid);
    }
}
