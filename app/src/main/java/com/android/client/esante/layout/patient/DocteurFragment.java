package com.android.client.esante.layout.patient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.client.esante.R;
import com.android.client.esante.adapter.DividerItemDecoration;
import com.android.client.esante.adapter.MedecinAdapter;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.DocteurConverter;
import com.android.client.esante.domain.Docteur;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocteurFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Docteur> docteurs = new ArrayList<Docteur>();
    MedecinAdapter adapter;
    RecyclerView rvDocteur;
    private SwipeRefreshLayout swipeRefreshLayout;
    String id;
    public DocteurFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id = getArguments().getString("id");
        View view = inflater.inflate(R.layout.fragment_docteur, container, false);
        rvDocteur = (RecyclerView) view.findViewById(R.id.rvDocteur);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.from, R.color.subject);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new MedecinAdapter(docteurs, getContext().getApplicationContext(),id);
        rvDocteur.setAdapter(adapter);
        rvDocteur.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.shape_divider)));
        rvDocteur.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new DocteurDataSync().execute(id);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        String url = "/esante/mspatient/docteurs/"+id+"/";
        ApiService api = RetroClient.getApiService();
        Call<DocteurConverter> call = api.docteurs(url);
        call.enqueue(new Callback<DocteurConverter>() {
            @Override
            public void onResponse(Call<DocteurConverter> call, Response<DocteurConverter> response) {
                if (response.isSuccessful()) {
                    docteurs.clear();
                    ArrayList<Docteur> data = response.body().getDocteurs();
                    if(data!=null){
                        for (int i = 0; i < data.size(); i++) {
                            docteurs.add(data.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<DocteurConverter> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    public class DocteurDataSync extends AsyncTask<String, Void, Void> {
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
            String url = "/esante/mspatient/docteurs/"+id+"/";
            ApiService api = RetroClient.getApiService();
            Call<DocteurConverter> call = api.docteurs(url);
            call.enqueue(new Callback<DocteurConverter>() {
                @Override
                public void onResponse(Call<DocteurConverter> call, Response<DocteurConverter> response) {
                    if (response.isSuccessful()) {
                        docteurs.clear();
                        ArrayList<Docteur> data = response.body().getDocteurs();
                        if(data!=null){
                            for (int i = 0; i < data.size(); i++) {
                                docteurs.add(data.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<DocteurConverter> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
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
