package com.example.zcab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mDriverButton,mPassengerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDriverButton=findViewById(R.id.btnDriver);
        mPassengerButton=findViewById(R.id.btnPassenger);
        mDriverButton.setOnClickListener(this);
        mPassengerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDriver:
                Intent intentDriver=new Intent(MainActivity.this,DriverLogin.class);
                startActivity(intentDriver);
                break;
            case R.id.btnPassenger:
                Intent intentPassenger=new Intent(MainActivity.this,PassengerLogin.class);
                startActivity(intentPassenger);
        }
    }
}