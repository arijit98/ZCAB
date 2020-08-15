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
import com.google.firebase.database.FirebaseDatabase;

public class DriverLogin extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;

    enum State {
        SIGNUP, LOGIN
    }

    private State state;
    private EditText mDriverEmail, mDriverPassword;
    private Button mDriverAccessButton;
    private TextView mDriverActivity, mDriverSwitch;
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        mAuth = FirebaseAuth.getInstance();
        state = State.LOGIN;
        mDriverActivity = findViewById(R.id.driverActivityText);
        mDriverSwitch = findViewById(R.id.driverSwitchText);
        mDriverEmail = findViewById(R.id.edtDriverEmail);
        mDriverPassword = findViewById(R.id.edtDriverPassword);
        mDriverAccessButton = findViewById(R.id.btnDriverAccess);
        loading = new ProgressDialog(this);

        mDriverSwitch.setOnClickListener(this);
        mDriverAccessButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDriverAccess:
                String email = mDriverEmail.getText().toString();
                String password = mDriverPassword.getText().toString();
                if (state == State.LOGIN) {
                    driverLogin(email, password);
                } else {
                    driverSignup(email, password);
                }
                break;
            case R.id.driverSwitchText:
                if (state == State.LOGIN) {
                    Toast.makeText(DriverLogin.this, "Sign Up Page", Toast.LENGTH_SHORT).show();
                    state = State.SIGNUP;
                    mDriverSwitch.setVisibility(View.INVISIBLE);
                    mDriverAccessButton.setText("Sign Up");
                    mDriverActivity.setText("DRIVER SIGN UP");
                } else {
                    Toast.makeText(DriverLogin.this, "Log In Page", Toast.LENGTH_SHORT).show();
                    state = State.LOGIN;
                }
                break;
        }

    }

    private void driverSignup(final String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(DriverLogin.this, "Enter your email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(DriverLogin.this, "Enter your Password", Toast.LENGTH_SHORT).show();
        } else {
            loading.setTitle("Signing Up");
            loading.setMessage("Please wait while we create your account...");
            loading.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(DriverLogin.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                        transitionToDriverMap();
                    } else {
                        Toast.makeText(DriverLogin.this, "Sign Up failed", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }
            });
        }
    }

    private void driverLogin(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(DriverLogin.this, "Enter your email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(DriverLogin.this, "Enter your Password", Toast.LENGTH_SHORT).show();
        } else {
            loading.setTitle("Log In");
            loading.setMessage("Please wait while we login to your account...");
            loading.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(DriverLogin.this, "Log In successful", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        transitionToDriverMap();
                    } else {
                        Toast.makeText(DriverLogin.this, "Log In failed", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }
            });
        }

    }

    private void transitionToDriverMap() {
        Intent intent = new Intent(DriverLogin.this, DriverMapsActivity.class);
        startActivity(intent);
    }

}