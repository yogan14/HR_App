package com.example.hr_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.database.repository.AbsencesRepository;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.absences.AbsenceListNotValidateViewModel;
import com.example.hr_app.viewmodel.absences.AbsencesListOneCollViewModel;

public class RequestAbsencesActivity extends BaseHRActivity {

    private Toast toast;

    private EditText etStartAbsence, etEndAbsence;

    private Spinner sReason;

    private String startAbsence, endAbsence, reason, mail;

    private AbsenceListNotValidateViewModel viewModel;

    private AbsencesRepository ar;

    private OnAsyncEventListener callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_requestabsences, frameLayout);

        String s = ((BaseApp) this.getApplication()).getTheMail();

        navigationView.setCheckedItem(position);
    }

    public void sendAbsence(View view){

        etStartAbsence = findViewById(R.id.begining_date);
        etEndAbsence = findViewById(R.id.end_date);
        sReason = findViewById(R.id.cause_of_absence_spinner);

        etStartAbsence.setError(null);
        etEndAbsence.setError(null);
        View focusView = null;

        startAbsence = etStartAbsence.getText().toString();
        endAbsence = etEndAbsence.getText().toString();
        reason = sReason.getSelectedItem().toString();

        boolean error = false;

        if (TextUtils.isEmpty(startAbsence)) {
            etStartAbsence.setError(getString(R.string.empty_field));
            etStartAbsence.setText("");
            focusView = etStartAbsence;
            error = true;

        } else {
            if (TextUtils.isEmpty(endAbsence)) {
                etEndAbsence.setError(getString(R.string.empty_field));
                etEndAbsence.setText("");
                focusView = etEndAbsence;

                error = true;
            }
        }

        if (error) {
            focusView.requestFocus();
        } else {

            mail = ((BaseApp) this.getApplication()).getTheMail();
            Absences absence = new Absences(startAbsence, endAbsence, reason, mail);
            ar = ((BaseApp) getApplication()).getAbsenceRepository();

            AbsenceListNotValidateViewModel.Factory factory = new AbsenceListNotValidateViewModel.Factory( getApplication());
            viewModel = ViewModelProviders.of(this, factory).get(AbsenceListNotValidateViewModel.class);

            viewModel.insert(absence, callback);

            toast = Toast.makeText(this, "request created", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }

    }
}
