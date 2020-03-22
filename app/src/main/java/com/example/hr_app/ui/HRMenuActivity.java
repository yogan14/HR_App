package com.example.hr_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hr_app.R;

public class HRMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmenu);
    }
    public void collaborator(View view){
        System.out.println("Nate's >> ");
        Intent intent = new Intent(this, PersonListActivity.class);
        startActivity(intent);
    }

    public void absenceValidate(View view){
        Intent intent = new Intent(this, ValidateAbsenceActivity.class);
        startActivity(intent);
    }
}
