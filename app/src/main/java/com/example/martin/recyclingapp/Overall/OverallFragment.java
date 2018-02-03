package com.example.martin.recyclingapp.Overall;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.martin.recyclingapp.R;

/**
 * Created by charlie on 2018-02-02.
 */

public class OverallFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overall, container, false);

        return view;
    }
}
