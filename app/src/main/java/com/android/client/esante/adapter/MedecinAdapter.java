package com.android.client.esante.adapter;
import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.client.esante.R;
import com.android.client.esante.activity.DocteurTabsActivity;

import com.android.client.esante.domain.Docteur;

import java.util.ArrayList;

public class MedecinAdapter extends RecyclerView.Adapter<MedecinAdapter.ViewHolder> {
    private ArrayList<Docteur> docteurs;
    public static  Context mContext;
    private String idPat;
    public MedecinAdapter(ArrayList<Docteur> docteurs, Context mContext,String idPat) {
        this.docteurs = docteurs;
        this.mContext = mContext;
        this.idPat=idPat;
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
        ImageView call=viewHolder.next;
        txtLastName.setText(docteurs.get(position).getLastName());
        txtFirstName.setText(docteurs.get(position).getFirstName());
        txtSpecialite.setText(docteurs.get(position).getSpecialite());
        txtTel.setText(docteurs.get(position).getTel());
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {

                }else{
                    Intent intent = new Intent(mContext, DocteurTabsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("idPat",idPat);
                    intent.putExtra("idDoc",docteurs.get(position).getIdDocteur());
                    intent.putExtra("role","PATIENT");
                    mContext.startActivity(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return docteurs.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtLastName ;
        TextView txtFirstName;
        TextView txtSpecialite;
        TextView txtTel ;
        ImageView next;
        private ItemClickListener clickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            txtLastName = (TextView) itemView.findViewById(R.id.txtlastName);
            txtFirstName = (TextView) itemView.findViewById(R.id.txtfirstName);
            txtSpecialite = (TextView) itemView.findViewById(R.id.txtSpecialite);
            txtTel = (TextView) itemView.findViewById(R.id.txtTel);
            next=(ImageView) itemView.findViewById(R.id.call) ;
            next.setImageDrawable(mContext.getResources().getDrawable(R.drawable.arrow));
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
