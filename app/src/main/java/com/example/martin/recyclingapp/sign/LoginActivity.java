package com.example.martin.recyclingapp.sign;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.recyclingapp.MainActivity;
import com.example.martin.recyclingapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("uid", firebaseAuth.getCurrentUser().getUid());
            startActivity(intent);
        }

        EditText emailEdit = findViewById(R.id.edit_email_login);
        EditText passEdit = findViewById(R.id.edit_password_login);
        Button signInButton = findViewById(R.id.button_sign_in_login);
        TextView registerText = findViewById(R.id.text_button_register_login);
        LoginButton fbLoginButton = findViewById(R.id.facebook_login_button);

        signInButton.setOnClickListener(v -> {
            firebaseAuth.signInWithEmailAndPassword(emailEdit.getText().toString().trim(), passEdit.getText().toString().trim())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("uid", firebaseAuth.getCurrentUser().getUid());
                            LoginActivity.this.startActivity(intent);
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

        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions("email", "public_profile");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        // USE THIS CODE TO GET YOUR HASH KEY AND ADD TO FACEBOOK DEVELOPER CONFIG
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.martin.recyclingapp",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("uid", user.getUid());
                    startActivity(intent);

                }
                else {
                    Toast.makeText(LoginActivity.this, "Authentication Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
