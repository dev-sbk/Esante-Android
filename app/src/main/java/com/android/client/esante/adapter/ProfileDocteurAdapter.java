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

import com.android.client.esante.R;
import com.android.client.esante.domain.Docteur;
import java.util.ArrayList;
public class ProfileDocteurAdapter extends RecyclerView.Adapter<ProfileDocteurAdapter.ViewHolder> {
    private ArrayList<Docteur> docteurs;
    public Context mContext;
    public ProfileDocteurAdapter(ArrayList<Docteur> docteurs, Context mContext) {
        this.docteurs = docteurs;
        this.mContext = mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_docteur, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TextView txtLastName = viewHolder.txtLastName;
        TextView txtFirstName = viewHolder.txtFirstName;
        TextView txtSpecialite= viewHolder.txtSpecialite;
        TextView txtTel = viewHolder.txtTel;
        ImageView call=viewHolder.call;
        txtLastName.setText(docteurs.get(position).getLastName());
        txtFirstName.setText(docteurs.get(position).getFirstName());
        txtSpecialite.setText(docteurs.get(position).getSpecialite());
        txtTel.setText(docteurs.get(position).getTel());
        call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+docteurs.get(position).getTel()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mContext.startActivity(callIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return docteurs.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtLastName ;
        TextView txtFirstName;
        TextView txtSpecialite;
        TextView txtTel ;
        ImageView call;
        public ViewHolder(View itemView) {
            super(itemView);
            txtLastName = (TextView) itemView.findViewById(R.id.txtlastName);
            txtFirstName = (TextView) itemView.findViewById(R.id.txtfirstName);
            txtSpecialite = (TextView) itemView.findViewById(R.id.txtSpecialite);
            txtTel = (TextView) itemView.findViewById(R.id.txtTel);
            call=(ImageView) itemView.findViewById(R.id.call) ;

        }
    }
}
