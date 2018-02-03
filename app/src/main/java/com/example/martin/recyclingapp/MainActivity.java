package com.example.martin.recyclingapp;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.martin.recyclingapp.Adapters.MainActivityPagerAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout_main_activity);
        fab = findViewById(R.id.fab_scan_main_activity);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_overall));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_nearby));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_history));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.view_pager_main_activity);
        viewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);

        final MainActivityPagerAdapter pagerAdapter =
                new MainActivityPagerAdapter(getFragmentManager(),
                        tabLayout.getTabCount());

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO scan functionality
            }
        });
    }
}
