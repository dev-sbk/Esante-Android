package com.android.client.esante.layout.docteur;
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
import com.android.client.esante.adapter.PatientAdapter;
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.PatientConverter;
import com.android.client.esante.domain.Patient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PatientFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Patient> patients = new ArrayList<Patient>();
    PatientAdapter adapter;
    RecyclerView rvPatients;
    private SwipeRefreshLayout swipeRefreshLayout;
    String id;
    public PatientFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);
        rvPatients = (RecyclerView) view.findViewById(R.id.rvPatient);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.from, R.color.subject);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new PatientAdapter(patients, getContext().getApplicationContext());
        rvPatients.setAdapter(adapter);
        rvPatients.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.shape_divider)));
        rvPatients.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        id = getArguments().getString("id");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new PatientDataSync().execute(id);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        String url = "/esante/mspatient/patients/"+id+"/";
        ApiService api = RetroClient.getApiService();
        Call<PatientConverter> call = api.patients(url);
        call.enqueue(new Callback<PatientConverter>() {
            @Override
            public void onResponse(Call<PatientConverter> call, Response<PatientConverter> response) {
                if (response.isSuccessful()) {
                    patients.clear();
                    ArrayList<Patient> data = response.body().getPatients();
                    if(data!=null){
                        for (int i = 0; i < data.size(); i++) {
                            patients.add(data.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<PatientConverter> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    public class PatientDataSync extends AsyncTask<String, Void, Void> {
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
            String url = "/esante/mspatient/patients/" + id+"/";
            ApiService api = RetroClient.getApiService();
            Call<PatientConverter> call = api.patients(url);
            call.enqueue(new Callback<PatientConverter>() {
                @Override
                public void onResponse(Call<PatientConverter> call, Response<PatientConverter> response) {
                    if (response.isSuccessful()) {
                        patients.clear();
                        ArrayList<Patient> data = response.body().getPatients();
                        if(data!=null){
                            for (int i = 0; i < data.size(); i++) {
                                patients.add(data.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<PatientConverter> call, Throwable t) {
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
