package com.example.martin.recyclingapp.sign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.recyclingapp.MainActivity;
import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.db.AppDatabase;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }

        firebaseAuth = FirebaseAuth.getInstance();

        EditText emailEdit = findViewById(R.id.edit_email_login);
        EditText passEdit = findViewById(R.id.edit_password_login);
        Button signInButton = findViewById(R.id.button_sign_in_login);
        TextView registerText = findViewById(R.id.text_button_register_login);
        LoginButton fbLoginButton = findViewById(R.id.facebook_login_button);

        //TODO ERROR HANDLING
        signInButton.setEnabled(true);
        signInButton.setOnClickListener(v -> {
            signInButton.setEnabled(false);
            try {
                firebaseAuth.signInWithEmailAndPassword(emailEdit.getText().toString().trim(),
                        passEdit.getText().toString().trim())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {

                                final String UID = firebaseAuth.getCurrentUser().getUid();

                                Intent intent = new Intent(
                                        LoginActivity.this, MainActivity.class);

                                AppDatabase.getAppDatabase(this).syncUserWithFirebase(UID);

                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                signInButton.setEnabled(true);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                signInButton.setEnabled(true);
            }
        });
        registerText.setOnClickListener(v -> {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_login, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
        });

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        finish();
    }

    private void registerWithFacebook() {
        //TODO on facebook login attempt check if already registered if not call this
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
