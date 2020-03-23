package com.example.hr_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hr_app.R;

public class ValidateAbsenceActivity extends BaseHRActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_validate_absence, frameLayout);

        navigationView.setCheckedItem(position);
    }

    public void validateAccepted(View view){
        Intent intent = new Intent(this, BaseHRActivity.class);
        startActivity(intent);
    }

    public void validateDenied(View view){
        Intent intent = new Intent(this, BaseHRActivity.class);
        startActivity(intent);
    }
}
