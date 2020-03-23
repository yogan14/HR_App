package com.example.hr_app.ui;

import android.os.Bundle;

import com.example.hr_app.R;

public class MyAbsencesActivity extends BaseHRActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_myabsences, frameLayout);

        navigationView.setCheckedItem(position);
    }
}
