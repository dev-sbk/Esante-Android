package com.android.client.esante.layout.common;

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
import com.android.client.esante.api.ApiService;
import com.android.client.esante.api.RetroClient;
import com.android.client.esante.converter.RdvConverter;
import com.android.client.esante.domain.RendezVous;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RdvFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<RendezVous> rdvs = new ArrayList<RendezVous>();
    RdvAdapter adapter;
    RecyclerView rvMaladies;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;
    EditText dateRdv;
    EditText heureRdv;
    String id,idDoc,key,role;
    int active;
    public RdvFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rdv, container, false);
        rvMaladies = (RecyclerView) view.findViewById(R.id.rvRdvs);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fabAddRdv);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPopup();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.from, R.color.subject);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new RdvAdapter(rdvs, getContext().getApplicationContext());
        rvMaladies.setAdapter(adapter);
        rvMaladies.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.shape_divider)));
        rvMaladies.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        id= getArguments().getString("id");
        role=getArguments().getString("role");
        key=getArguments().getString("key");
        if(key.equals("0")) floatingActionButton.setVisibility(View.INVISIBLE);
        else{
            idDoc = getArguments().getString("idDoc");
            active=Integer.parseInt(getArguments().getString("active"));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new RdvDataSync().execute(id);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        ApiService api = RetroClient.getApiService();
        Call<RdvConverter> call = api.rdvs(id,role);
        call.enqueue(new Callback<RdvConverter>() {
            @Override
            public void onResponse(Call<RdvConverter> call, Response<RdvConverter> response) {
                if (response.isSuccessful()) {
                    rdvs.clear();
                    ArrayList<RendezVous> data = response.body().getRdvs();
                    if(data!=null){
                        for (int i = 0; i < data.size(); i++) {
                            rdvs.add(data.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<RdvConverter> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadPopup() {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.popup_add_rdv, null);
        dateRdv = (EditText) promptsView.findViewById(R.id.txtDateRdv);
        dateRdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);
                int month = mcurrentTime.get(Calendar.MONTH);
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        dateRdv.setText(year + "-" + monthOfYear + "-" + dayOfMonth);

                    }
                };
                DatePickerDialog dpDialog = new DatePickerDialog(getActivity(), listener, year, month, day);
                dpDialog.setTitle("Select Date");
                dpDialog.show();

            }
        });
        heureRdv = (EditText) promptsView.findViewById(R.id.txtHeureRdv);
        heureRdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        heureRdv.setText(selectedHour + ":" + selectedMinute + ":" + "00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(promptsView);
        alert.setTitle("Ajouter un rendez vous ");
        alert.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ApiService api = RetroClient.getApiService();
                Call<RdvConverter> call = api.addRdv(dateRdv.getText().toString(),heureRdv.getText().toString(),active,Integer.parseInt(idDoc),Integer.parseInt(id));
                call.enqueue(new Callback<RdvConverter>() {
                    @Override
                    public void onResponse(Call<RdvConverter> call, Response<RdvConverter> response) {
                        if (response.isSuccessful()) {
                           new RdvDataSync().execute(id);
                        }
                    }
                    @Override
                    public void onFailure(Call<RdvConverter> call, Throwable t) {
                        Log.v("Failure", "" + t.getMessage());
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
    public class RdvDataSync extends AsyncTask<String, Void, Void> {
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
            ApiService api = RetroClient.getApiService();
            Call<RdvConverter> call = api.rdvs(id,role);
            call.enqueue(new Callback<RdvConverter>() {
                @Override
                public void onResponse(Call<RdvConverter> call, Response<RdvConverter> response) {
                    if (response.isSuccessful()) {
                        rdvs.clear();
                        ArrayList<RendezVous> data = response.body().getRdvs();
                        if(data!=null){
                            for (int i = 0; i < data.size(); i++) {
                                rdvs.add(data.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<RdvConverter> call, Throwable t) {
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
