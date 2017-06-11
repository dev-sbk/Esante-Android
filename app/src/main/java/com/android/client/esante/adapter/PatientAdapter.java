package com.android.client.esante.adapter;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.client.esante.R;
import com.android.client.esante.activity.PatientTabsActivity;
import com.android.client.esante.domain.Patient;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private ArrayList<Patient> patients;
    public Context mContext;
    public PatientAdapter(ArrayList<Patient> patients, Context mContext) {
        this.patients = patients;
        this.mContext = mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_patient, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TextView txtLastName = viewHolder.txtLastName;
        TextView txtFirstName = viewHolder.txtFirstName;
        TextView txtEmail= viewHolder.txtEmail;
        TextView txtTel = viewHolder.txtTel;
        ImageView next=viewHolder.next;
        txtLastName.setText(patients.get(position).getLastName());
        txtFirstName.setText(patients.get(position).getFirstName());
        txtEmail.setText(patients.get(position).getEmail());
        txtTel.setText(patients.get(position).getTel());
        txtTel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+patients.get(position).getTel()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mContext.startActivity(callIntent);
            }
        });
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {

                }else{
                    Intent intent = new Intent(mContext, PatientTabsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",patients.get(position).getIdPatient());
                    mContext.startActivity(intent);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return patients.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtLastName ;
        TextView txtFirstName;
        TextView txtEmail;
        TextView txtTel ;
        ImageView next;
        private ItemClickListener clickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            txtLastName = (TextView) itemView.findViewById(R.id.txtlastName);
            txtFirstName = (TextView) itemView.findViewById(R.id.txtfirstName);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtTel = (TextView) itemView.findViewById(R.id.txtTel);
            next=(ImageView) itemView.findViewById(R.id.next) ;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getPosition(), true);
            return true;
        }

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }
    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }
}
