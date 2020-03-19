package com.example.hr_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hr_app.R;

public class ValidateAbsenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_absence);
    }

    public void validateAccepted(View view){
        Intent intent = new Intent(this, HRMenuActivity.class);
        startActivity(intent);
    }

    public void validateDenied(View view){
        Intent intent = new Intent(this, HRMenuActivity.class);
        startActivity(intent);
    }
}
