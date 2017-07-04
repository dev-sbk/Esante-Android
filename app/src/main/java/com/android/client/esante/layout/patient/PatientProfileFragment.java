package com.android.client.esante.layout.patient;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.client.esante.R;
import com.android.client.esante.adapter.ContactAdapter;
import com.android.client.esante.adapter.DividerItemDecoration;
import com.android.client.esante.adapter.ProfileDocteurAdapter;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.ContactConverter;
import com.android.client.esante.converter.DocteurConverter;
import com.android.client.esante.converter.PatientConverter;
import com.android.client.esante.domain.Contact;
import com.android.client.esante.domain.Docteur;
import com.android.client.esante.domain.Patient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PatientProfileFragment extends Fragment {
    private ArrayList<Contact> contacts=new ArrayList<Contact>();
    private ArrayList<Docteur> docteurs=new ArrayList<Docteur>();
    TextView txtNom;
    TextView txtPrenom;
    TextView txtDateNaissance;
    TextView txtTel;
    TextView txtEmail;
    TextView txtSexe;
    TextView txtHair;
    TextView txtEyeColor;
    TextView txtHeight;
    TextView txtWeight;
    TextView txtBooldType;
    RecyclerView rvContacts,rvDocteurs;
    ContactAdapter adapter;
    ProfileDocteurAdapter docteurAdapter;
    public PatientProfileFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_patient, container, false);
        txtNom=(TextView) view.findViewById(R.id.txtNom);
        txtPrenom=(TextView) view.findViewById(R.id.txtPrenom);
        txtDateNaissance=(TextView) view.findViewById(R.id.txtDateNiassance);
        rvContacts = (RecyclerView) view.findViewById(R.id.rvContact);
        adapter = new ContactAdapter(contacts,view.getContext());
        rvContacts.setAdapter(adapter);
        rvContacts.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.shape_divider)));
        rvContacts.setLayoutManager(new LinearLayoutManager(view.getContext()));

        rvDocteurs = (RecyclerView) view.findViewById(R.id.rvPhysician);
        docteurAdapter = new ProfileDocteurAdapter(docteurs,view.getContext());
        rvDocteurs.setAdapter(docteurAdapter);
        rvDocteurs.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.shape_divider)));
        rvDocteurs.setLayoutManager(new LinearLayoutManager(view.getContext()));

        txtTel=(TextView) view.findViewById(R.id.txtTel);
        txtEmail=(TextView) view.findViewById(R.id.txtEmail);
        txtSexe=(TextView) view.findViewById(R.id.txtSexe);
        txtHair=(TextView) view.findViewById(R.id.txtHair);
        txtEyeColor=(TextView) view.findViewById(R.id.txtColorEye);
        txtHeight=(TextView) view.findViewById(R.id.txtHeight);
        txtWeight=(TextView) view.findViewById(R.id.txtWeight);
        txtBooldType=(TextView) view.findViewById(R.id.txtBloodType);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new ProfileSync().execute(getArguments().getString("id"));
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
            String url = "/esante/mspatient/profile/" + id;
            String urlContact = "/esante/mspatient/contacts/" + id;
            String urlDoc = "/esante/mspatient/docteurs/" + id;
            ApiService api = RetroClient.getApiService();
            Call<PatientConverter> call = api.authentication(url);
            call.enqueue(new Callback<PatientConverter>() {
                @Override
                public void onResponse(Call<PatientConverter> call, Response<PatientConverter> response) {
                    if (response.isSuccessful()) {
                        Patient pat = response.body().getPatients().get(0);
                        txtNom.setText(pat.getLastName());
                        txtPrenom.setText(pat.getFirstName());
                        txtDateNaissance.setText(pat.getBirthDay());
                        txtTel.setText(pat.getTel());
                        txtEmail.setText(pat.getEmail());
                        if(pat.getGender().equals("2"))
                           txtSexe.setText("Homme");
                        else
                            txtSexe.setText("Femme");
                        txtHair.setText(pat.getHair());
                        txtEyeColor.setText(pat.getEyeColor());
                        txtHeight.setText(String.valueOf(pat.getHeight()));
                        txtWeight.setText(String.valueOf(pat.getWeight()));
                        txtBooldType.setText(pat.getBoodType());
                    }
                }
                @Override
                public void onFailure(Call<PatientConverter> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            Call<ContactConverter> callContact = api.contacts(urlContact);
            callContact.enqueue(new Callback<ContactConverter>() {
                @Override
                public void onResponse(Call<ContactConverter> call, Response<ContactConverter> response) {
                    if (response.isSuccessful()) {
                        contacts.clear();
                        ArrayList<Contact> data = response.body().getContacts();
                        if(data!=null){
                            for (int i = 0; i < data.size(); i++) {
                                contacts.add(data.get(i));
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<ContactConverter> call, Throwable t) {

                }
            });
            Call<DocteurConverter> callDocteur = api.docteurs(urlDoc);
            callDocteur.enqueue(new Callback<DocteurConverter>() {
                @Override
                public void onResponse(Call<DocteurConverter> call, Response<DocteurConverter> response) {
                    if (response.isSuccessful()) {
                        docteurs.clear();
                        ArrayList<Docteur> data = response.body().getDocteurs();
                        if(data!=null) {
                            for (int i = 0; i < data.size(); i++) {
                                docteurs.add(data.get(i));
                            }
                        }
                        docteurAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<DocteurConverter> call, Throwable t) {

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
