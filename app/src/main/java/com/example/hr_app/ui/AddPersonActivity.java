package com.example.hr_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hr_app.R;

public class AddPersonActivity extends BaseHRActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_addperson, frameLayout);

        navigationView.setCheckedItem(position);
    }

    public void returnToList(View view){
        Intent intent = new Intent(this, PersonListActivity.class);
        startActivity(intent);
    }
}
