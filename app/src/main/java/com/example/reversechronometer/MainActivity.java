package com.example.reversechronometer;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ReverseChronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        //chronometer.setOverallDuration(120);
        //chronometer.setWarningDuration(20);
        //chronometer.setText("Valor inicial: " + chronometer.getOverallDuration());

    }

    @Override
    protected void onResume() {
        super.onResume();
        chronometer.run();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            chronometer.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
