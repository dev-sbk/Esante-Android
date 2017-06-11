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
import com.android.client.esante.domain.Contact;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;
    public Context mContext;

    public ContactAdapter(ArrayList<Contact> contacts, Context mContext) {
        this.contacts = contacts;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Contact temp = contacts.get(position);
        TextView txtLastName = viewHolder.txtLastName;
        TextView txtFirstName = viewHolder.txtFirstName;
        TextView txtRelationShip = viewHolder.txtRelationShip;
        TextView txtTel = viewHolder.txtTel;
        ImageView call=viewHolder.call;

        txtLastName.setText(contacts.get(position).getFirstName());
        txtFirstName.setText(contacts.get(position).getLastName());
        txtRelationShip.setText(contacts.get(position).getRelationShip());
        txtTel.setText(contacts.get(position).getTel());

        call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+contacts.get(position).getTel()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mContext.startActivity(callIntent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return contacts.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtLastName ;
        TextView txtFirstName;
        TextView txtRelationShip;
        TextView txtTel ;
        ImageView call;
        public ViewHolder(View itemView) {
            super(itemView);
            txtLastName = (TextView) itemView.findViewById(R.id.txtlastName);
            txtFirstName = (TextView) itemView.findViewById(R.id.txtfirstName);
            txtRelationShip = (TextView) itemView.findViewById(R.id.txtRelationShip);
            txtTel = (TextView) itemView.findViewById(R.id.txtTel);
            call=(ImageView) itemView.findViewById(R.id.call) ;

        }

    }
}
