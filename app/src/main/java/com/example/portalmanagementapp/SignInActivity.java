package com.example.portalmanagementapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portalmanagementapp.SubActivities.EnrollmentRegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register, Forget;
    private EditText Email, Password;
    private Button Login;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private boolean connected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        register = (TextView) findViewById(R.id.signup);
        Forget = (TextView) findViewById(R.id.forget);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.pass);
        Login = (Button) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Forget .setOnClickListener(this);
        register .setOnClickListener(this);
        Login.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if (connected){
                if (user != null) {
                    // User is signed in

                    if(user.isEmailVerified()) {
                        Intent i = new Intent(SignInActivity.this, blank.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        progressBar.setVisibility(View.GONE);
                    }
                }

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.signup:
                startActivity(new Intent(this, EnrollmentRegistrationActivity.class));
                break;
            case  R.id.login:
                userLogin();
                break;
        }
    }
    private void userLogin() {



       String _Email = Email.getText().toString().trim();
       String _Pass = Password.getText().toString().trim();
        if (_Email.isEmpty()){
            Email.setError("Enter your Email!");
            Email.requestFocus();
            return;
        }
        if (_Pass.isEmpty()){
            Password.setError("Enter your Password!");
            Password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(_Email, _Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (connected){
                    progressBar.setVisibility(View.VISIBLE);
                    if(task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user.isEmailVerified()) {
                            startActivity(new Intent(getApplicationContext(), blank.class));
                            progressBar.setVisibility(View.GONE);

                        } else {
                            user.sendEmailVerification();
                            Toast.makeText(getApplicationContext(), "Check your Email to Verify your Account!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Incorrect Email or Password!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}