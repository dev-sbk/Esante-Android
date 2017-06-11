package com.android.client.esante.layout.patient;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.client.esante.R;
import com.android.client.esante.adapter.DividerItemDecoration;
import com.android.client.esante.adapter.RdvAdapter;
import com.android.client.esante.adapter.TraitementAdapter;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.RdvConverter;
import com.android.client.esante.converter.TraitementConverter;
import com.android.client.esante.domain.RendezVous;
import com.android.client.esante.domain.Traitement;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraitementFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Traitement> traitements = new ArrayList<Traitement>();
    TraitementAdapter adapter;
    RecyclerView rvTraitement;
    private SwipeRefreshLayout swipeRefreshLayout;
    String id;
    public TraitementFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traitement, container, false);
        rvTraitement = (RecyclerView) view.findViewById(R.id.rvTraitement);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.from, R.color.subject);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new TraitementAdapter(traitements, getContext().getApplicationContext());
        rvTraitement.setAdapter(adapter);
        rvTraitement.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.shape_divider)));
        rvTraitement.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        id = getArguments().getString("id");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new TraitementDataSync().execute(id);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        String url = "/esante/mspatient/traitements/" + id + "/";
        ApiService api = RetroClient.getApiService();
        Call<TraitementConverter> call = api.getTraitementByPatient(url);
        call.enqueue(new Callback<TraitementConverter>() {
            @Override
            public void onResponse(Call<TraitementConverter> call, Response<TraitementConverter> response) {
                if (response.isSuccessful()) {
                    traitements.clear();
                    ArrayList<Traitement> data = response.body().getTraitements();
                    if(data!=null){
                        for (int i = 0; i < data.size(); i++) {
                            traitements.add(data.get(i));
                        }
                    }

                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<TraitementConverter> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public class TraitementDataSync extends AsyncTask<String, Void, Void> {
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
            String url = "/esante/mspatient/traitements/" + id;
            ApiService api = RetroClient.getApiService();
            Call<TraitementConverter> call = api.getTraitementByPatient(url);
            call.enqueue(new Callback<TraitementConverter>() {
                @Override
                public void onResponse(Call<TraitementConverter> call, Response<TraitementConverter> response) {
                    if (response.isSuccessful()) {
                        traitements.clear();
                        ArrayList<Traitement> data = response.body().getTraitements();
                        if(data!=null){
                            for (int i = 0; i < data.size(); i++) {
                                traitements.add(data.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                @Override
                public void onFailure(Call<TraitementConverter> call, Throwable t) {
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
