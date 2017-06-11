package com.android.client.esante.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.client.esante.R;
import com.android.client.esante.layout.common.RdvFragment;
import com.android.client.esante.layout.patient.MaladieFragment;
import com.android.client.esante.layout.patient.PatientProfileFragment;
import com.android.client.esante.layout.patient.TraitementFragment;

import java.util.ArrayList;
import java.util.List;

public class PatientTabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_activity);
        Intent intent = getIntent();
        id=intent.getExtras().get("id").toString();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("key","PATIENT");

        Fragment fragmentProfile=new PatientProfileFragment();
        Fragment fragmentRDV=new RdvFragment();
        Fragment fragmentMaladies=new MaladieFragment();

        fragmentProfile.setArguments(bundle);
        fragmentRDV.setArguments(bundle);
        fragmentMaladies.setArguments(bundle);


        adapter.addFragment(fragmentProfile, "PROFILE");
        adapter.addFragment(fragmentRDV, "RDV");
        adapter.addFragment(fragmentMaladies, "DOOSIER");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
