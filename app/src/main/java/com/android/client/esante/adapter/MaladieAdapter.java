package com.android.client.esante.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


import com.android.client.esante.R;
import com.android.client.esante.domain.Maladie;

import org.w3c.dom.Text;

import java.util.ArrayList;
/**
 * Created by informatique on 29/07/2016.
 */
public class MaladieAdapter extends RecyclerView.Adapter<MaladieAdapter.ViewHolder> {
    private ArrayList<Maladie> maladies;
    public Context mContext;

    public MaladieAdapter(ArrayList<Maladie> maladies, Context mContext) {
        this.maladies = maladies;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_maladie, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Maladie temp = maladies.get(position);
        TextView txtMaladie = viewHolder.maladie;
        TextView txtDescription = viewHolder.description;
        TextView txtPrecaution=viewHolder.precaution;
        TextView txtCounter=viewHolder.counter;
        txtMaladie.setText(maladies.get(position).getMaladie());
        txtPrecaution.setText(maladies.get(position).getPrecaution());
        txtDescription.setText(maladies.get(position).getDescription());
        txtCounter.setText(""+(position+1));
    }
    @Override
    public int getItemCount() {
        return maladies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, View.OnLongClickListener {
        public TextView maladie;
        public TextView description;
        public TextView precaution;
        public TextView counter;

        public ViewHolder(View itemView) {
            super(itemView);
            maladie = (TextView) itemView.findViewById(R.id.maladie);
            description = (TextView) itemView.findViewById(R.id.description);
            precaution = (TextView) itemView.findViewById(R.id.precoution);
            counter=(TextView)  itemView.findViewById(R.id.counter);
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
