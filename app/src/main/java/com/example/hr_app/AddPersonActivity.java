package com.example.hr_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AddPersonActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);
    }

    public void returnToList(View view){
        Intent intent = new Intent(this, PersonListActivity.class);
        startActivity(intent);
    }
}