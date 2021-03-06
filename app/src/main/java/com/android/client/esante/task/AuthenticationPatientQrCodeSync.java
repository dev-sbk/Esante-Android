package com.android.client.esante.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.client.esante.activity.PatientActivity;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.PatientConverter;
import com.android.client.esante.domain.Patient;
import com.android.client.esante.repository.EsanteRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationPatientQrCodeSync extends AsyncTask<String, Void, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    private EsanteRepository repository;
    public AuthenticationPatientQrCodeSync(Context context) {
        this.context = context;
        this.repository=new EsanteRepository(this.context);
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
        String code = params[0] + "/";
        String url = "/esante/mspatient/logincheck/" + code;
        ApiService api = RetroClient.getApiService();
        Call<PatientConverter> call = api.authentication(url);
        call.enqueue(new Callback<PatientConverter>() {
            @Override
            public void onResponse(Call<PatientConverter> call, Response<PatientConverter> response) {
                if (response.isSuccessful()) {
                    if(response.body().getPatients()!=null){
                        Patient pat = response.body().getPatients().get(0);
                        repository.addUser(pat);
                        Intent i = new Intent(context, PatientActivity.class);
                        i.putExtra("idPat", pat.getIdPatient());
                        if (pat.getLastName() != null)
                            i.putExtra("nom", pat.getLastName());
                        else i.putExtra("nom", "");
                        if (pat.getFirstName() != null)
                            i.putExtra("prenom", pat.getFirstName());
                        else i.putExtra("prenom", "");

                        if (pat.getEmail() != null)
                            i.putExtra("email", pat.getEmail());
                        else i.putExtra("email", "");
                        ((Activity) context).finish();
                        ((Activity) context).startActivity(i);
                    }else
                        Toast.makeText(context.getApplicationContext(),"ERREUR : QR CODE INVALIDATE",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PatientConverter> call, Throwable t) {
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
