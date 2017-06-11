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
import android.widget.Toast;

import com.android.client.esante.R;
import com.android.client.esante.adapter.DividerItemDecoration;
import com.android.client.esante.adapter.MaladieAdapter;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.converter.MaladieConverter;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.domain.Maladie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaladieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<Maladie> maladies=new ArrayList<Maladie>();
    MaladieAdapter adapter;
    RecyclerView rvMaladies;
    private SwipeRefreshLayout swipeRefreshLayout;
    String id;
    public MaladieFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_maladies, container, false);
        rvMaladies = (RecyclerView) view.findViewById(R.id.rvMaladies);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.from,R.color.subject);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new MaladieAdapter(maladies,getContext().getApplicationContext());
        rvMaladies.setAdapter(adapter);
        rvMaladies.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.shape_divider)));
        rvMaladies.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        id=getArguments().getString("id");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new MaladieDataSync().execute(id);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        String url = "/esante/mspatient/maladies/" + id+"/";
        ApiService api = RetroClient.getApiService();
        Call<MaladieConverter> call = api.getMaladiesByPatient(url);
        call.enqueue(new Callback<MaladieConverter>() {
            @Override
            public void onResponse(Call<MaladieConverter> call, Response<MaladieConverter> response) {
                if (response.isSuccessful()) {
                    maladies.clear();
                    ArrayList<Maladie> data=response.body().getMaladies();
                    if(data!=null){
                        for (int i=0;i<data.size();i++){
                            maladies.add(data.get(i));
                        }
                    }

                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<MaladieConverter> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public class MaladieDataSync extends AsyncTask<String, Void, Void> {
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
            String url = "/esante/mspatient/maladies/" + id;
            ApiService api = RetroClient.getApiService();
            Call<MaladieConverter> call = api.getMaladiesByPatient(url);
            call.enqueue(new Callback<MaladieConverter>() {
                @Override
                public void onResponse(Call<MaladieConverter> call, Response<MaladieConverter> response) {
                    if (response.isSuccessful()) {
                        maladies.clear();
                        ArrayList<Maladie> data=response.body().getMaladies();
                        if(data!=null){
                            for (int i=0;i<data.size();i++){
                                maladies.add(data.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<MaladieConverter> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            Log.v("Size maladie",""+maladies.size());
            super.onPostExecute(aVoid);
        }
    }




}
