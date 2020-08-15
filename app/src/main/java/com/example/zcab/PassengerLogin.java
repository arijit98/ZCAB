package com.example.zcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PassengerLogin extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;

    enum State {
        SIGNUP, LOGIN
    }

    private PassengerLogin.State state;
    private EditText mPassengerEmail, mPassengerPassword;
    private Button mPassengerAccessButton;
    private TextView mPassengerActivity, mPassengerSwitch;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_login);

        mAuth = FirebaseAuth.getInstance();
        state = PassengerLogin.State.LOGIN;
        mPassengerActivity = findViewById(R.id.passengerActivityText);
        mPassengerSwitch = findViewById(R.id.passengerSwitchText);
        mPassengerEmail = findViewById(R.id.edtPassengerEmail);
        mPassengerPassword = findViewById(R.id.edtPassengerPassword);
        mPassengerAccessButton = findViewById(R.id.btnPassengerAccess);
        loading = new ProgressDialog(this);

        mPassengerSwitch.setOnClickListener(this);
        mPassengerAccessButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPassengerAccess:
                String email = mPassengerEmail.getText().toString();
                String password = mPassengerPassword.getText().toString();
                if (state == PassengerLogin.State.LOGIN) {
                    passengerLogin(email, password);
                } else {
                    passengerSignup(email, password);
                }
                break;
            case R.id.passengerSwitchText:
                if (state == PassengerLogin.State.LOGIN) {
                    Toast.makeText(PassengerLogin.this, "Sign Up Page", Toast.LENGTH_SHORT).show();
                    state = PassengerLogin.State.SIGNUP;
                    mPassengerSwitch.setVisibility(View.INVISIBLE);
                    mPassengerAccessButton.setText("Sign Up");
                    mPassengerActivity.setText("PASSENGER SIGN UP");
                } else {
                    Toast.makeText(PassengerLogin.this, "Log In Page", Toast.LENGTH_SHORT).show();
                    state = PassengerLogin.State.LOGIN;
                }
                break;
        }

    }

    private void passengerSignup(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(PassengerLogin.this, "Enter your email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(PassengerLogin.this, "Enter your Password", Toast.LENGTH_SHORT).show();
        } else {
            loading.setTitle("Signing Up");
            loading.setMessage("Please wait while we create your account...");
            loading.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(PassengerLogin.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    } else {
                        Toast.makeText(PassengerLogin.this, "Sign Up failed", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }
            });
        }
    }

    private void passengerLogin(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(PassengerLogin.this, "Enter your email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(PassengerLogin.this, "Enter your Password", Toast.LENGTH_SHORT).show();
        } else {
            loading.setTitle("Log In");
            loading.setMessage("Please wait while we login to your account...");
            loading.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(PassengerLogin.this, "Log In successful", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    } else {
                        Toast.makeText(PassengerLogin.this, "Log In failed", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }
            });
        }
    }
//    private void transitionToPassengerMap(){
//        Intent intent=new Intent(PassengerLogin.this,DriverMapsActivity.class);
//        startActivity(intent);
//    }
}