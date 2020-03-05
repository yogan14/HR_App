package com.example.hr_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PersonListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personlist);
    }
    public void addPerson(View view){
        Intent intent = new Intent(this, AddPersonActivity.class);
        startActivity(intent);
    }

    public void modifyPerson(View view){
        Intent intent = new Intent(this, ModifyPersonActivity.class);
        startActivity(intent);
    }
}
