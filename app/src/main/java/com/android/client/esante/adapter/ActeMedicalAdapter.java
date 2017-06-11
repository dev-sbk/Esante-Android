package com.android.client.esante.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.client.esante.R;
import com.android.client.esante.domain.ActeMedicale;
import com.android.client.esante.domain.Maladie;

import java.util.ArrayList;

/**
 * Created by informatique on 29/07/2016.
 */
public class ActeMedicalAdapter extends RecyclerView.Adapter<ActeMedicalAdapter.ViewHolder> {
    private ArrayList<ActeMedicale> actes;
    public Context mContext;

    public ActeMedicalAdapter(ArrayList<ActeMedicale> actes, Context mContext) {
        this.actes = actes;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_acte, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TextView txtTypeActe= viewHolder.typeActe;
        TextView txtHeureActe = viewHolder.heureActe;
        TextView txtDiagonostic=viewHolder.diagonisticActe;
        TextView txtRapportMedcin=viewHolder.rapportMedicin;
        txtTypeActe.setText(actes.get(position).getTypeActe());
        txtHeureActe.setText(actes.get(position).getHeureActe());
        txtDiagonostic.setText(actes.get(position).getDiagonisticActe());
        txtRapportMedcin.setText(actes.get(position).getRapportMedcin());
    }
    @Override
    public int getItemCount() {
        return actes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, View.OnLongClickListener {
        public TextView typeActe;
        public TextView heureActe;
        public TextView diagonisticActe;
        public TextView rapportMedicin;

        public ViewHolder(View itemView) {
            super(itemView);
            typeActe = (TextView) itemView.findViewById(R.id.maladie);
            heureActe = (TextView) itemView.findViewById(R.id.description);
            diagonisticActe = (TextView) itemView.findViewById(R.id.precoution);
            rapportMedicin=(TextView)  itemView.findViewById(R.id.counter);
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
