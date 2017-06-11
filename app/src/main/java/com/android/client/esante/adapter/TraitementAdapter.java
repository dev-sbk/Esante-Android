package com.android.client.esante.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.client.esante.R;
import com.android.client.esante.domain.Traitement;
import java.util.ArrayList;
/**
 * Created by informatique on 29/07/2016.
 */
public class TraitementAdapter extends RecyclerView.Adapter<TraitementAdapter.ViewHolder> {
    private ArrayList<Traitement> traitements;
    public Context mContext;
    public TraitementAdapter(ArrayList<Traitement> traitements, Context mContext) {
        this.traitements = traitements;
        this.mContext = mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_traitement, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TextView txtTraitement = viewHolder.txtTraitement;
        TextView txtNbJour=viewHolder.txtNbJour;
        TextView txtActive=viewHolder.txtActive;
        txtTraitement.setText(traitements.get(position).getTraitements());
        txtNbJour.setText(traitements.get(position).getNbJours() >0 ? String.valueOf(traitements.get(position).getNbJours()) : "0");
        txtActive.setText(traitements.get(position).getActif()> 0 ? "ACTIVE" : "NON ACTIVE");
    }
    @Override
    public int getItemCount() {
        return traitements.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, View.OnLongClickListener {
        public TextView txtTraitement;
        public TextView txtNbJour;
        public TextView txtActive;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTraitement = (TextView) itemView.findViewById(R.id.txtTraitement);
            txtNbJour=(TextView)  itemView.findViewById(R.id.txtNbJour);
            txtActive = (TextView) itemView.findViewById(R.id.active);
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
