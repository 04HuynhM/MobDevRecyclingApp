package com.example.martin.recyclingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Martin on 11/02/2018.
 */

public class ResultFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
<<<<<<< Updated upstream
        View view = inflater.inflate(R.layout.fragment_result, container, false);
=======
<<<<<<< Updated upstream
        View view = inflater.inflate(R.layout.fragment_result2, container, false);
=======

        View view = inflater.inflate(R.layout.fragment_result, container, false);

        Bundle bundle = getArguments();
        String scannedBarcode = bundle.getString("barcode");




>>>>>>> Stashed changes
>>>>>>> Stashed changes
        return view;
    }



}
