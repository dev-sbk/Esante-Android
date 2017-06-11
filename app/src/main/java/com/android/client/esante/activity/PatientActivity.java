package com.android.client.esante.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.client.esante.R;
import com.android.client.esante.layout.common.RdvFragment;
import com.android.client.esante.layout.patient.ActeMedicaleFragment;
import com.android.client.esante.layout.patient.MaladieFragment;
import com.android.client.esante.layout.patient.PatientProfileFragment;
import com.android.client.esante.layout.patient.TraitementFragment;
import com.android.client.esante.task.NotificationTask;

public class PatientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtEmail;
    TextView txtName;
    String id,nom,prenom,email,code;
    private Toolbar toolbar;
    private  DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_actitvity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_patient).setVisible(false);
        navigationView.invalidate();
        navigationView.setNavigationItemSelectedListener(this);
        View header_layout=navigationView.getHeaderView(0);
        txtName = (TextView) header_layout.findViewById(R.id.txtName);
        txtEmail = (TextView) header_layout.findViewById(R.id.txtEmail);
        Intent intent = getIntent();
        id=intent.getExtras().get("idPat").toString();
        code=intent.getExtras().get("code").toString();
        nom=intent.getExtras().get("nom").toString();
        prenom=intent.getExtras().get("prenom").toString();
        email=intent.getExtras().get("email").toString();
        txtName.setText(nom+" "+prenom);
        txtEmail.setText(email);
        loadFragment(R.id.nav_profile);
        new NotificationTask(this).execute(id);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
          loadFragment(id);
        } else if (id == R.id.nav_maladies) {
            loadFragment(id);
        } else if (id == R.id.nav_rend_vous) {
            loadFragment(id);
        } else if (id == R.id.nav_traitement) {
            loadFragment(id);
        } else if (id == R.id.nav_acte_medicale) {
            loadFragment(id);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loadFragment(int index){
        Bundle bundle = new Bundle();
        Fragment fragment=null;
        switch(index){
            case R.id.nav_profile:
                fragment=new PatientProfileFragment();
                bundle.putString("id",id);
                fragment.setArguments(bundle);
                getSupportActionBar().setTitle("Profile ");
                break;
            case R.id.nav_maladies:
                fragment=new MaladieFragment();
                bundle.putString("id",id);
                fragment.setArguments(bundle);
                getSupportActionBar().setTitle("Maladies");
                break;
            case R.id.nav_rend_vous:
                fragment=new RdvFragment();
                bundle.putString("id",id);
                bundle.putString("key","PATIENT");
                fragment.setArguments(bundle);
                getSupportActionBar().setTitle("Rendez-Vous");
                break;
            case R.id.nav_traitement:
                fragment=new TraitementFragment();
                bundle.putString("id",id);
                fragment.setArguments(bundle);
                getSupportActionBar().setTitle("Traitement");
                break;
            case R.id.nav_acte_medicale:
                fragment=new ActeMedicaleFragment();
                bundle.putString("id",id);
                fragment.setArguments(bundle);
                getSupportActionBar().setTitle("Acte-Medicales");
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
