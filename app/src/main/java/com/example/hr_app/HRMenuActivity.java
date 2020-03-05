package com.example.hr_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HRMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmenu);
    }
    public void collaborator(View view){
        Intent intent = new Intent(this, PersonListActivity.class);
        startActivity(intent);
    }
}
