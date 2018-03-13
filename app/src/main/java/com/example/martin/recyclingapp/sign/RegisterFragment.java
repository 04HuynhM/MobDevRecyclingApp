package com.example.martin.recyclingapp.sign;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.martin.recyclingapp.MainActivity;
import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.db.AppSingleton;
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
        firebaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(AppSingleton.DB_USERS);

        EditText email = view.findViewById(R.id.edit_email_register);
        EditText password = view.findViewById(R.id.edit_password_register);
        EditText name = view.findViewById(R.id.edit_name_register);
        Button registerButton = view.findViewById(R.id.button_register);

        registerButton.setOnClickListener(v -> {
            registerUser(email.getText().toString().trim(),
                    password.getText().toString().trim());
        });

        return view;
    }

    private void registerUser(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        firebaseReference.child(firebaseAuth.getUid())
                                .setValue(new User(firebaseAuth.getUid(),
                                                "Charlie",
                                                "Tuna",
                                                email,
                                                27,
                                                User.GENDER_MALE
                        ));

                        Toast.makeText(getActivity(),
                                "Registration successful.",
                                Toast.LENGTH_SHORT)
                                .show();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(),
                                "Something went wrong.",
                                Toast.LENGTH_SHORT)
                                .show();
                        Log.v("Error", task.getResult().toString());
                    }
                });
    }
}
