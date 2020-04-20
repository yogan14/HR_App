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
import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.absences.IUDAbsencesViewModel;
import com.example.hr_app.viewmodel.absences.OneAbsenceViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ModifyRequestAbsenceActivity
 * for modify a request of absence
 */
public class ModifyRequestAbsenceActivity extends BaseHRActivity {
    private TextView tvStartDate, tvEndDate;
    private Spinner sCause;
    private Toast toast;
    private AbsencesEntity absence;
    private OneAbsenceViewModel viewModel;
    private IUDAbsencesViewModel vm;
    private String startAbsence, endAbsence, reason;
    SharedPreferences sharedPreferences;

    /**
     * onCreate
     * Create the activity
     * @param savedInstanceState - the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_modifyrequestabsences, frameLayout);

        navigationView.setCheckedItem(position);
        setData();
    }

    /**
     * onResume
     * State when we return in the app
     */
    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }



    public void setData() {
        String absenceID = ((BaseApp)this.getApplication()).getIDAbsence();

        sCause = findViewById(R.id.cause_of_absences_spinner);
        tvStartDate = findViewById(R.id.begining_date);
        tvEndDate = findViewById(R.id.end_date);

        Button update = findViewById(R.id.update_button);
        update.setOnClickListener(view -> update(tvStartDate.getText().toString(), tvEndDate.getText().toString(), sCause.getSelectedItem().toString()));

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));



        OneAbsenceViewModel.Factory factory = new OneAbsenceViewModel.Factory(getApplication(), absenceID);
        viewModel = ViewModelProviders.of(this,factory).get(OneAbsenceViewModel.class);

        viewModel.getAbsence().observe(this, absences ->  {
            if(absences!=null) {
                absence = absences;
                findData();
            }
        });

        IUDAbsencesViewModel.Factory factory2 = new IUDAbsencesViewModel.Factory(getApplication());
        vm = ViewModelProviders.of(this, factory2).get(IUDAbsencesViewModel.class);

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(view -> deleteButton());
    }

    /**
     * when the collaborator is set, extract the attribute of the collaborator and put it to the text views
     */
    private void findData() {
        startAbsence = absence.getStartAbsence();
        endAbsence = absence.getEndAbsence();
        reason = absence.getReason();

        tvStartDate.setText(startAbsence);
        tvEndDate.setText(endAbsence);

        if(sharedPreferences.getString("pref_language", "English").equals("en-rUS")) {
            switch(reason) {

                case "Maladie":
                    reason = "Sickness";
                    break;
                case "Vacances":
                    reason = "Vacation";
                    break;
                case "Accident":
                    reason = "Accident";
                    break;
                case "Armée":
                    reason = "Army";
                    break;
                case "Maternité":
                    reason = "Maternity";
                    break;
                case "Paternité":
                    reason = "Paternity";
                    break;
                case "Autre":
                    reason = "Other";
                    break;
            }
        }
        if(sharedPreferences.getString("pref_language", "English").equals("fr")) {
            switch(reason) {

                case "Sickness":
                    reason = "Maladie";
                    break;
                case "Vacation":
                    reason = "Vacances";
                    break;
                case "Accident":
                    reason = "Accident";
                    break;
                case "Army":
                    reason = "Armée";
                    break;
                case "Maternity":
                    reason = "Maternité";
                    break;
                case "Paternity":
                    reason = "Paternité";
                    break;
                case "Other":
                    reason = "Autre";
                    break;
            }
        }

        sCause.setSelection(((ArrayAdapter<String>)sCause.getAdapter()).getPosition(reason));
    }

    /**
     * verify the fields, and if all is ok, modify the absence in the database
     * @param beginning - the content of the startAbsence field
     * @param end - the content of the endAbsence field
     * @param newCause - the content of the reason field
     */
    public void update(String beginning, String end, String newCause) {

        tvStartDate.setError(null);
        tvEndDate.setError(null);
        View focusView = null;

        boolean error = false;

        //check if it has some error in the fields
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

        //if some error, focus
        if (error) {
            focusView.requestFocus();
        } else {

            //modify the object absence
            if(!startAbsence.equals(beginning)){
                absence.setStartAbsence(beginning);
            }
            if(!endAbsence.equals(end)){
                absence.setEndAbsence(end);
            }
            if(!reason.equals(newCause)){
                absence.setReason(newCause);
            }

            //modify the database

            this.vm.update(absence, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    setResponse(true, "update");
                }

                @Override
                public void onFailure(Exception e) {
                    setResponse(false, "update");
                }
            });


        }

    }

    /**
     * delete a collaborator in the database
     */
    private void deleteButton() {

        vm.delete(absence, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true, "delete");
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false, "delete");
            }
        });
    }

    /**
     * setResponse
     * if it's ok, modify / delete and return to the list of collaborator, if not, say it and return to the list of collaborator
     * @param response - ok or not
     */
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

    /**
     * check if the date is in a valid format
     * @param date - the date to check
     * @return if yes or not
     */
    public static boolean isDateValid (String date) {
        // Set the date format
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setLenient(false);
        try {
            Date d = format.parse(date);
        }
        // Date not valid
        catch (ParseException e) {
            return false;
        }
        // send back true if the format is valid
        return true;
    }

    /**
     * check if the start date is really before the end date
     * @param start - the start date
     * @param end - the end date
     */
    public boolean isDateOneBeforeDateTwo (String start, String end) {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date time1 = format.parse(start);
            Date time2 = format.parse(end);

            return time1.compareTo(time2) < 0;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * setLanguage
     * Set the language from the settings
     * @param langue the language the user want
     */
    public void setLanguage(String langue){
        Locale locale = new Locale(langue);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }
}
