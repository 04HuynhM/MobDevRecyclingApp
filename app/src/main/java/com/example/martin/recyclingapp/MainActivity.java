package com.example.martin.recyclingapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.example.martin.recyclingapp.adapters.MainActivityPagerAdapter;

public class MainActivity extends AppCompatActivity {

    Boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout_main_activity);
        FloatingActionButton fab = findViewById(R.id.fab_scan_main_activity);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_overall));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_nearby));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_history));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.view_pager_main_activity);
        viewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);

        Bundle bundle = new Bundle();
        bundle.putString("uid", getIntent().getStringExtra("uid"));

        final MainActivityPagerAdapter pagerAdapter =
                new MainActivityPagerAdapter(getFragmentManager(),
                        tabLayout.getTabCount(),
                        bundle);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivityForResult(intent, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0) {
            if(resultCode== CommonStatusCodes.SUCCESS) {
                if(data!=null) {

                    Barcode barcode = data.getParcelableExtra("barcode");
                    Bundle bundle = new Bundle();
                    bundle.putString("barcode", barcode.toString());

                    FragmentManager fragmentManager = getFragmentManager();
                    ResultFragment resultFragment = new ResultFragment();
                    resultFragment.setArguments(bundle);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                    transaction.replace(android.R.id.content, resultFragment)
                            .addToBackStack(null).commit();
                }
                else {
                    //TODO
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
