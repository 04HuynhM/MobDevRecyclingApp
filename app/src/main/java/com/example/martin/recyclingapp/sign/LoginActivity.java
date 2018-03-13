package com.example.martin.recyclingapp.sign;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.martin.recyclingapp.MainActivity;
import com.example.martin.recyclingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("uid", firebaseAuth.getCurrentUser().getUid());
            startActivity(intent);
        }

        EditText emailEdit = findViewById(R.id.edit_email_login);
        EditText passEdit = findViewById(R.id.edit_password_login);
        Button signInButton = findViewById(R.id.button_sign_in_login);
        TextView registerText = findViewById(R.id.text_button_register_login);

        signInButton.setOnClickListener(v -> {
            firebaseAuth.signInWithEmailAndPassword(emailEdit.getText().toString().trim(), passEdit.getText().toString().trim())
                    .addOnCompleteListener(this, task -> {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.putExtra("uid", firebaseAuth.getCurrentUser().getUid());
                            startActivity(intent);
                        }
                    });
        });

        registerText.setOnClickListener(v ->
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_login, new RegisterFragment())
                    .addToBackStack(null)
                    .commit()
        );
    }
}
