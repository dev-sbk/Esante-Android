package com.android.client.esante.layout.docteur;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.client.esante.R;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.DocteurConverter;
import com.android.client.esante.converter.StringConverter;
import com.android.client.esante.domain.Docteur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DocteurProfileFragment extends Fragment {
    TextView txtNom;
    TextView txtPrenom;
    TextView txtSpecialite;
    TextView txtTel;
    TextView txtEmail;
    EditText etNom;
    EditText etPrenom;
    EditText etTel;
    EditText etEmail;
    String id;
    private FloatingActionButton floatingActionButton;
    public DocteurProfileFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_docteur, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fabEditProfile);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPopup();
            }
        });
        txtNom=(TextView) view.findViewById(R.id.txtNom);
        txtPrenom=(TextView) view.findViewById(R.id.txtPrenom);
        txtSpecialite=(TextView) view.findViewById(R.id.txtSpecialite);
        txtTel=(TextView) view.findViewById(R.id.txtTel);
        txtEmail=(TextView) view.findViewById(R.id.txtEmail);
        id=getArguments().getString("id");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new ProfileSync().execute(getArguments().getString("id"));
    }

    private void loadPopup() {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.popup_profile_docteur, null);
         etNom=(EditText) promptsView.findViewById(R.id.edNom);
         etNom.setText(txtNom.getText());
         etPrenom=(EditText) promptsView.findViewById(R.id.etPrenom);
         etPrenom.setText(txtPrenom.getText());
         etTel=(EditText) promptsView.findViewById(R.id.etTel);
         etTel.setText(txtTel.getText());
         etEmail=(EditText) promptsView.findViewById(R.id.etEmail);
         etEmail.setText(txtEmail.getText());
         AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
         alert.setView(promptsView);
         alert.setTitle("Mette à jour votre profile");
         alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ApiService api = RetroClient.getApiService();
                Call<StringConverter> call=api.updateprofile(id,etNom.getText().toString(),etPrenom.getText().toString(),etTel.getText().toString(),etEmail.getText().toString());
                call.enqueue(new Callback<StringConverter>() {
                    @Override
                    public void onResponse(Call<StringConverter> call, Response<StringConverter> response) {
                        if(response.isSuccessful()){
                            if(response.body().getMessage()!=null){
                                String message=response.body().getMessage();
                                Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                                new ProfileSync().execute(id);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StringConverter> call, Throwable t) {
                        Log.v("Failure",t.getMessage());
                    }
                });
            }
        });
        alert.setNegativeButton("Annuler",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        alert.show();
    }
    class ProfileSync extends AsyncTask<String,Void,Void>{
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Chargement ...");
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            String id = params[0] + "/";
            String url = "/esante/mspatient/profiledocteur/" + id;
            ApiService api = RetroClient.getApiService();
            Call<DocteurConverter> call = api.profile(url);
            call.enqueue(new Callback<DocteurConverter>() {
                @Override
                public void onResponse(Call<DocteurConverter> call, Response<DocteurConverter> response) {
                    if (response.isSuccessful()) {
                        Docteur doc = response.body().getDocteurs().get(0);
                        txtNom.setText(doc.getLastName());
                        txtPrenom.setText(doc.getFirstName());
                        txtSpecialite.setText(doc.getSpecialite());
                        txtTel.setText(doc.getTel());
                        txtEmail.setText(doc.getEmail());
                    }
                }
                @Override
                public void onFailure(Call<DocteurConverter> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
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
}
