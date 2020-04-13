package com.example.hr_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.absences.IUDAbsencesViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * RequestAbsenceActivity
 * the users can request their absences here
 */
public class RequestAbsencesActivity extends BaseHRActivity {

    private Toast toast;

    private EditText etStartAbsence, etEndAbsence;

    private Spinner sReason;

    private String startAbsence, endAbsence, reason, mail;

    private IUDAbsencesViewModel viewModel;

    private OnAsyncEventListener callback;

    /**
     * onCreate
     * On the creation of the activity
     * @param savedInstanceState - the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_requestabsences, frameLayout);



        navigationView.setCheckedItem(position);

        setDisplay();
    }

    /**
     * onResume
     * State when we return in the app
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        setDisplay();
    }

    /**
     * Set the display of the app
     */
    public void setDisplay(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));
    }

    /**
     * check if the request is valid and if yes, add it in the database
     */
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

        //is it an error ?
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
            } else {
                if(!isDateValid(startAbsence)) {
                    etStartAbsence.setError(getString(R.string.date_not_valid));
                    etStartAbsence.setText(startAbsence);
                    focusView = etStartAbsence;

                    error = true;
                } else {
                    if(!isDateValid(endAbsence)) {
                        etEndAbsence.setError(getString(R.string.date_not_valid));
                        etEndAbsence.setText(endAbsence);
                        focusView = etEndAbsence;

                        error = true;
                    } else {
                        if(!isDateOneBeforeDateTwo(startAbsence, endAbsence)) {
                            etEndAbsence.setError(getString(R.string.date_error_time));
                            etEndAbsence.setText(endAbsence);
                            focusView = etEndAbsence;

                            error = true;
                        }
                    }
                }
            }
        }

        //if an error occurs, focus on the it
        if (error) {
            focusView.requestFocus();
        } else {

            //else, create an absence in the database
            mail = ((BaseApp) this.getApplication()).getTheMail();

            AbsencesEntity absence = new AbsencesEntity();

            absence.setStartAbsence(startAbsence);
            absence.setEndAbsence(endAbsence);
            absence.setReason(reason);
            absence.setEmail(mail);

            IUDAbsencesViewModel.Factory factory = new IUDAbsencesViewModel.Factory(getApplication());
            viewModel = ViewModelProviders.of(this, factory).get(IUDAbsencesViewModel.class);

            viewModel.insert(absence, callback);

            toast = Toast.makeText(this, getString(R.string.created_request), Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    /**
     * check if the date is in a valid format
     * @param date - the date to check
     * @return if yes or not
     */
    public boolean isDateValid(String date) {
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
