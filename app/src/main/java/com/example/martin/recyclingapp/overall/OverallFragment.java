package com.example.martin.recyclingapp.overall;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.sign.LoginActivity;

/**
 * Created by charlie on 2018-02-02.
 */

public class OverallFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overall, container, false);

        // Test purposes, will be removed
        Button loginButton = view.findViewById(R.id.button_login_test_overall);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
        });

        TextView textView = view.findViewById(R.id.text_overall);
        textView.setText(getArguments().getString("uid"));

        return view;
    }
}
