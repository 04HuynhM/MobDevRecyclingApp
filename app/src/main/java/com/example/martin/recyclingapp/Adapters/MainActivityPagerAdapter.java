package com.example.martin.recyclingapp.Adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.example.martin.recyclingapp.History.HistoryFragment;
import com.example.martin.recyclingapp.Nearby.NearbyFragment;
import com.example.martin.recyclingapp.Overall.OverallFragment;

/**
 * Created by charlie on 2018-02-02.
 */

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public MainActivityPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                OverallFragment tabOverall = new OverallFragment();
                return tabOverall;
            case 1:
                NearbyFragment tabNearby = new NearbyFragment();
                return tabNearby;
            case 2:
                HistoryFragment tabHistory = new HistoryFragment();
                return tabHistory;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
