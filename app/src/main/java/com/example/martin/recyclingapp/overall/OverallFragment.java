package com.example.martin.recyclingapp.overall;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.sign.LoginActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by charlie on 2018-02-02.
 */

public class OverallFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overall, container, false);
        TextView textView = view.findViewById(R.id.text_overall);

        // Test purposes, will be removed
        Button loginButton = view.findViewById(R.id.button_login_test_overall);
        Button logoutButton = view.findViewById(R.id.button_log_out_test_overall);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(OverallFragment.this.getActivity(), LoginActivity.class);
            OverallFragment.this.getActivity().startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {

            if(FirebaseAuth.getInstance().getCurrentUser() != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                AlertDialog dialog = builder.setTitle("Log Out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                textView.setText("");

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                    }
                });

                dialog.show();
            }
            else {
                Toast.makeText(getActivity(), "You are not signed in.", Toast.LENGTH_LONG).show();
            }

        });


        textView.setText(getArguments().getString("uid"));

        return view;
    }
}
