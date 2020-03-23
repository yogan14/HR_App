package com.example.hr_app.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hr_app.R;

public class ModifyRequestAbsenceActivity extends BaseHRActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_modifyrequestabsences, frameLayout);

        navigationView.setCheckedItem(position);
    }
}
