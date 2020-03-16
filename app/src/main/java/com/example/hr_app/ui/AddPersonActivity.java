package com.example.hr_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hr_app.R;

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
