package com.android.client.esante.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.client.esante.R;
import com.android.client.esante.domain.RendezVous;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by informatique on 29/07/2016.
 */
public class RdvAdapter extends RecyclerView.Adapter<RdvAdapter.ViewHolder> {
    private ArrayList<RendezVous> rdvs;
    public Context mContext;

    public RdvAdapter(ArrayList<RendezVous> rdvs, Context mContext) {
        this.rdvs = rdvs;
        this.mContext = mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_rdv, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Calendar cal=getStringToDate(rdvs.get(position).getHeureRDV());
        String date=String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))+"/"+String.format("%02d", cal.get(Calendar.MONTH))+"/"+cal.get(Calendar.YEAR);
        String heure=cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE);
        String etat=rdvs.get(position).getValide() ==1 ? "ACCEPTER" : "EN ATTENTE";
        TextView txtDateRdv = viewHolder.dateRdv;
        TextView txtvalide=viewHolder.heureRdv;
        TextView txtValide=viewHolder.valide;

        txtDateRdv.setText(date);
        txtvalide.setText(heure);
        txtValide.setText(etat);

    }
    @Override
    public int getItemCount() {
        return rdvs.size();
    }
    public Calendar getStringToDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal=Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdf.parse(dateString);
            cal.setTime(date);
            return cal;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, View.OnLongClickListener {
        public TextView dateRdv;
        public TextView heureRdv;
        public TextView valide;
        public ViewHolder(View itemView) {
            super(itemView);
            dateRdv = (TextView) itemView.findViewById(R.id.dateRdv);
            heureRdv=(TextView)  itemView.findViewById(R.id.txtHeureRdv);
            valide = (TextView) itemView.findViewById(R.id.valide);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
        @Override
        public boolean onLongClick(View v) {

            return true;
        }
    }
}
