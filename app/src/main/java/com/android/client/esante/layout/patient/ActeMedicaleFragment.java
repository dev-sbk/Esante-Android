package com.android.client.esante.layout.patient;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.client.esante.R;
import com.android.client.esante.adapter.ActeMedicalAdapter;
import com.android.client.esante.adapter.DividerItemDecoration;
import com.android.client.esante.adapter.MaladieAdapter;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.ActeMedicaleConverter;
import com.android.client.esante.converter.MaladieConverter;
import com.android.client.esante.domain.ActeMedicale;
import com.android.client.esante.domain.Maladie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActeMedicaleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<ActeMedicale> actes=new ArrayList<ActeMedicale>();
    ActeMedicalAdapter adapter;
    RecyclerView rvActes;
    private SwipeRefreshLayout swipeRefreshLayout;
    String id;
    public ActeMedicaleFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_actes, container, false);
        rvActes = (RecyclerView) view.findViewById(R.id.rvActes);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.from,R.color.subject);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new ActeMedicalAdapter(actes,getContext().getApplicationContext());
        rvActes.setAdapter(adapter);
        rvActes.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.shape_divider)));
        rvActes.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        id=getArguments().getString("id");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new ActeDataSync().execute(id);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        String url = "/esante/mspatient/actemedicales/" + id+"/";
        ApiService api = RetroClient.getApiService();
        Call<ActeMedicaleConverter> call = api.getActeByPatient(url);
        call.enqueue(new Callback<ActeMedicaleConverter>() {
            @Override
            public void onResponse(Call<ActeMedicaleConverter> call, Response<ActeMedicaleConverter> response) {
                if (response.isSuccessful()) {
                    actes.clear();
                    ArrayList<ActeMedicale> data=response.body().getActeMedicales();
                    if(data!=null){
                        for (int i=0;i<data.size();i++){
                            actes.add(data.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<ActeMedicaleConverter> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public class ActeDataSync extends AsyncTask<String, Void, Void> {
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
            String url = "/esante/mspatient/actemedicales/" + id;
            ApiService api = RetroClient.getApiService();
            Call<ActeMedicaleConverter> call = api.getActeByPatient(url);
            call.enqueue(new Callback<ActeMedicaleConverter>() {
                @Override
                public void onResponse(Call<ActeMedicaleConverter> call, Response<ActeMedicaleConverter> response) {
                    if (response.isSuccessful()) {
                        ArrayList<ActeMedicale> data=response.body().getActeMedicales();
                        if(data!=null){
                            for (int i=0;i<data.size();i++){
                                actes.add(data.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<ActeMedicaleConverter> call, Throwable t) {

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
