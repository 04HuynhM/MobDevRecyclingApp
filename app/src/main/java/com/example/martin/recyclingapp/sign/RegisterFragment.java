package com.example.martin.recyclingapp.sign;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.db.ConstantsAndUtils;
import com.example.martin.recyclingapp.db.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by charlie on 2018-03-11.
 */

public class RegisterFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(ConstantsAndUtils.FIREBASE_USERS);

        EditText email = view.findViewById(R.id.edit_email_register);
        EditText password = view.findViewById(R.id.edit_password_register);
        EditText name = view.findViewById(R.id.edit_name_register);
        EditText surname = view.findViewById(R.id.edit_surname_register);
        EditText age = view.findViewById(R.id.edit_age_register);
        EditText gender = view.findViewById(R.id.edit_gender_register);
        Button registerButton = view.findViewById(R.id.button_register);

        //TODO ERROR HANDLING FOR THE EMAIL PASS CREDENTIALS
        registerButton.setOnClickListener(v -> {
                registerUser(email.getText().toString().trim(),
                        password.getText().toString().trim(),
                        name.getText().toString().trim(),
                        surname.getText().toString().trim(),
                        Integer.valueOf(age.getText().toString().trim()), //TODO change these to drop-downs
                        Integer.valueOf(gender.getText().toString().trim()));
        });

        return view;
    }

    private void registerUser(String email,
                              String password,
                              String name,
                              String surname,
                              int age,
                              int gender) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        firebaseReference
                                .child(firebaseAuth.getUid())
                                .setValue(new User(firebaseAuth.getUid(),
                                        name,
                                        surname,
                                        email,
                                        age,
                                        gender,
                                        null));


                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();

                        Toast.makeText(getActivity(),
                                "Registration successful.",
                                Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(
                                getActivity(),
                                "Something went wrong.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
