package com.example.martin.recyclingapp.sign;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.db.ConstantsAndUtils;
import com.example.martin.recyclingapp.db.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * Created by charlie on 2018-03-11.
 */

public class RegisterFragment extends Fragment {

    private final String TAG = this.getClass().getName();

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseReference;

    private EditText email;
    private EditText password;
    private EditText name;
    private EditText surname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(ConstantsAndUtils.FIREBASE_USERS);

        email = view.findViewById(R.id.edit_email_register);
        password = view.findViewById(R.id.edit_password_register);
        name = view.findViewById(R.id.edit_name_register);
        surname = view.findViewById(R.id.edit_surname_register);
        TextView age = view.findViewById(R.id.text_content_age_register);
        TextView gender = view.findViewById(R.id.text_content_gender_register);
        Button registerButton = view.findViewById(R.id.button_register);

        NumberPicker.Formatter formatter = i -> {
            switch (i) {
                case User.GENDER_MALE:
                    return "Male";
                case User.GENDER_FEMALE:
                    return "Female";
                case User.GENDER_OTHER:
                    return "Other";
                default:
                    return "Error";
            }
        };

        age.setOnClickListener(v -> {
            MaterialNumberPicker agePicker = new MaterialNumberPicker.Builder(getActivity())
                    .minValue(0)
                    .maxValue(120)
                    .defaultValue(50)
                    .backgroundColor(Color.WHITE)
                    .separatorColor(Color.LTGRAY)
                    .textColor(Color.BLACK)
                    .textSize(20)
                    .enableFocusability(false)
                    .wrapSelectorWheel(true)
                    .build();

            new AlertDialog.Builder(getActivity())
                    .setTitle("Age")
                    .setView(agePicker)
                    .setPositiveButton(getString(android.R.string.ok), (dialog, which) ->
                            age.setText(String.valueOf(agePicker.getValue())))
                    .show();
        });

        gender.setOnClickListener(v -> {
            MaterialNumberPicker genderPicker = new MaterialNumberPicker.Builder(getActivity())
                    .minValue(User.GENDER_MALE)
                    .maxValue(User.GENDER_OTHER)
                    .defaultValue(User.GENDER_FEMALE)
                    .formatter(formatter)
                    .backgroundColor(Color.WHITE)
                    .separatorColor(Color.LTGRAY)
                    .textColor(Color.BLACK)
                    .textSize(20)
                    .enableFocusability(false)
                    .wrapSelectorWheel(true)
                    .build();

            new AlertDialog.Builder(getActivity())
                    .setTitle("Gender")
                    .setView(genderPicker)
                    .setPositiveButton(getString(android.R.string.ok), (dialog, which) ->
                            gender.setText(formatter.format(genderPicker.getValue())))
                    .show();
        });

        registerButton.setOnClickListener(v -> {
            try {
                registerUser(email.getText().toString().trim(),
                        password.getText().toString().trim(),
                        name.getText().toString().trim(),
                        surname.getText().toString().trim(),
                        Integer.valueOf(age.getText().toString().trim()),
                        getReversedFormat(gender.getText().toString().trim()));
            } catch (NumberFormatException e) {
                age.setError("");
                gender.setError("");
            }
        });

        return view;
    }

    private void registerUser(String email,
                              String password,
                              String name,
                              String surname,
                              int age,
                              int gender) {

        try {
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
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                this.password.setError("Password is too weak."); //resource string
                                this.password.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                this.email.setError("Invalid mail.");
                                this.email.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                this.email.setError("Email taken.");
                                this.email.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            this.name.setError("!");
            this.surname.setError("!");
        }
    }

    private int getReversedFormat(String s) {
        switch (s) {
            case "Male":
                return User.GENDER_MALE;
            case "Female:":
                return User.GENDER_FEMALE;
            case "Other":
                return User.GENDER_OTHER;
            default:
                return -1;
        }
    }
}
