package com.example.hr_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RequestAbsencesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestabsences);
    }
    public void sendAbsence(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
