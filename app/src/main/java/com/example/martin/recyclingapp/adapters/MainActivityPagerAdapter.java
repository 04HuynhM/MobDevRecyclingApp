package com.example.martin.recyclingapp.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.example.martin.recyclingapp.history.HistoryFragment;
import com.example.martin.recyclingapp.nearby.NearbyFragment;
import com.example.martin.recyclingapp.overall.OverallFragment;

/**
 * Created by charlie on 2018-02-02.
 */

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private Bundle bundle;

    public MainActivityPagerAdapter(FragmentManager fm, int numberOfTabs, Bundle bundle) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new OverallFragment();
                break;
            case 1:
                fragment = new NearbyFragment();
                break;
            case 2:
                fragment = new HistoryFragment();
                break;
            default:
                return null;
        }

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
