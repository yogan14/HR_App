package com.example.hr_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.async.absences.DeleteAbsences;
import com.example.hr_app.database.async.absences.UpdateAbsences;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.absences.OneAbsenceViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ModifyRequestAbsenceActivity extends BaseHRActivity {
    private TextView tvStartDate, tvEndDate;
    private Spinner sCause;
    private Toast toast;
    private Absences absence;
    private OneAbsenceViewModel viewModel;
    private String startAbsence, endAbsence, reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_modifyrequestabsences, frameLayout);

        navigationView.setCheckedItem(position);

        int absenceID = ((BaseApp)this.getApplication()).getTheID();

        sCause = findViewById(R.id.cause_of_absences_spinner);
        tvStartDate = findViewById(R.id.begining_date);
        tvEndDate = findViewById(R.id.end_date);

        Button update = findViewById(R.id.update_button);
        update.setOnClickListener(view -> {
            update(tvStartDate.getText().toString(), tvEndDate.getText().toString(), sCause.getSelectedItem().toString());
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(view -> {
            deleteButton();
        });

        OneAbsenceViewModel.Factory factory = new OneAbsenceViewModel.Factory(getApplication(), absenceID);
        viewModel = ViewModelProviders.of(this,factory).get(OneAbsenceViewModel.class);

        viewModel.getAbsence().observe(this, absences ->  {
            if(absences!=null) {
                absence = absences;
                findData();
            }
        });



    }



    private void findData() {
        startAbsence = absence.getStartAbsence();
        endAbsence = absence.getEndAbsence();
        reason = absence.getReason();

        tvStartDate.setText(startAbsence);
        tvEndDate.setText(endAbsence);
        sCause.setSelection(((ArrayAdapter<String>)sCause.getAdapter()).getPosition(reason));
    }

    public void update(String beginning, String end, String newCause) {

        tvStartDate.setError(null);
        tvEndDate.setError(null);
        View focusView = null;

        boolean error = false;

                if(!isDateValid(beginning)) {
                    tvStartDate.setError(getString(R.string.date_not_valid));
                    tvStartDate.setText(startAbsence);
                    focusView = tvStartDate;

                    error = true;
                } else {
                    if(!isDateValid(end)) {
                        tvEndDate.setError(getString(R.string.date_not_valid));
                        tvEndDate.setText(endAbsence);
                        focusView = tvEndDate;

                        error = true;
                    } else {
                        if(!isDateOneBeforeDateTwo(beginning, end)) {
                            tvEndDate.setError(getString(R.string.date_error_time));
                            tvEndDate.setText(endAbsence);
                            focusView = tvEndDate;

                            error = true;
                        }
                    }
                }

        if (error) {
            focusView.requestFocus();
        } else {

            if(!startAbsence.equals(beginning)){
                absence.setStartAbsence(beginning);
            }
            if(!endAbsence.equals(end)){
                absence.setEndAbsence(end);
            }
            if(!reason.equals(newCause)){
                absence.setReason(newCause);
            }

            new UpdateAbsences(getApplication(), new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    setResponse(true, "update");
                }

                @Override
                public void onFailure(Exception e) {
                    setResponse(false, "update");
                }
            }).execute(absence);
        }

    }

    private void deleteButton() {

        new DeleteAbsences(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true, "delete");
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false, "delete");
            }
        }).execute(absence);
    }


    private void setResponse(Boolean response, String type) {
        if (response) {
            if(type.equals("update")) {
                toast = Toast.makeText(this, (getString(R.string.absence_updated)), Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(this, (getString(R.string.absence_deleted)), Toast.LENGTH_LONG);
            }

            toast.show();
            Intent intent = new Intent(ModifyRequestAbsenceActivity.this, MyAbsencesActivity.class);
            startActivity(intent);
        } else {
            toast = Toast.makeText(this, (getString(R.string.error)), Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(ModifyRequestAbsenceActivity.this, MyAbsencesActivity.class);
            startActivity(intent);
        }
    }

    public static boolean isDateValid (String date) {
        // Définir le format date
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setLenient(false);
        try {
            Date d = format.parse(date);
        }
        // Date invalide
        catch (ParseException e) {
            return false;
        }
        // Renvoie true si la date est valide
        return true;
    }

    public boolean isDateOneBeforeDateTwo (String start, String end) {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date time1 = format.parse(start);
            Date time2 = format.parse(end);

            if (time1.compareTo(time2) < 0) {
                return true;
            }

            return false;

        } catch (ParseException e) {
            return false;
        }
    }
    public void setLanguage(String langue){
        Locale locale = new Locale(langue);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }
}
