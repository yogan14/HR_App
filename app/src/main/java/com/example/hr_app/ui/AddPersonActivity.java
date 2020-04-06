package com.example.hr_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hr_app.R;
import com.example.hr_app.database.async.collaborator.CreateCollaborator;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.OnAsyncEventListener;
import java.util.Locale;
/**
 * AddPersonActivity
 * Activity to add a collaborator
 * firebase
 */


public class AddPersonActivity extends BaseHRActivity {

    private Button bAdd;

    private TextView tvName, tvService, tvMail, tvPassword;

    private String name, service, mail, password;

    /**
     * onCreate
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_addperson, frameLayout);

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

    public void setDisplay(){
        tvName = findViewById(R.id.name_field);
        tvService = findViewById(R.id.service_field);
        tvMail = findViewById(R.id.mail_field);
        tvPassword = findViewById(R.id.password_field);
        bAdd = findViewById(R.id.addCollaborator);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));
        bAdd.setOnClickListener(view -> addCollaborator());
    }
    /**
     * addCollaborator
     * when the add button is pressed, add the collaborator in the database and return to the collaboratorsActivity
     */
    public void addCollaborator() {

        tvName.setError(null);
        tvService.setError(null);
        tvMail.setError(null);
        tvPassword.setError(null);

        View focusView = null;

        name = tvName.getText().toString();
        service = tvService.getText().toString();
        mail = tvMail.getText().toString();
        password = tvPassword.getText().toString();

        boolean error = false;

        //check all the possibles mistakes
        if (TextUtils.isEmpty(name)) {
            tvName.setError(getString(R.string.empty_field));
            tvName.setText("");
            focusView = tvName;
            error = true;

        } else {
            if (TextUtils.isEmpty(service)) {
                tvService.setError(getString(R.string.empty_field));
                tvService.setText("");
                focusView = tvService;

                error = true;
            } else {
                if (TextUtils.isEmpty(mail)) {
                    tvMail.setError(getString(R.string.empty_field));
                    tvMail.setText("");
                    focusView = tvMail;

                    error = true;
                } else {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                        tvMail.setError(getString(R.string.invalid_mail));
                        tvMail.setText("");
                        focusView = tvMail;

                        error = true;
                    } else {
                        if (TextUtils.isEmpty(password)){
                            tvPassword.setError(getString(R.string.empty_field));
                            tvPassword.setText("");
                            focusView = tvPassword;

                            error = true;
                        } else {
                           if (password.length() < 5) {
                               tvPassword.setError(getString(R.string.error_password));
                               tvPassword.setText("");
                               focusView = tvPassword;

                               error = true;
                           }
                        }
                    }
                }
            }
        }

        //if one think isn't ok, request focus
        if (error) {
            focusView.requestFocus();
        } else {
            //add collaborator in the database
            Collaborator collaborator = new Collaborator(name, service, mail, password);

            new CreateCollaborator(getApplication(), new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    setResponse(true);
                }

                @Override
                public void onFailure(Exception e) {
                    setResponse(false);
                }
            }).execute(collaborator);




        }
    }

    /**
     * setResponse
     * if it's ok, start the intent, if not, say it
     * @param response - ok or not
     */
    private void setResponse(Boolean response) {
        //if true, start the intent and show a toast for inform the user
        if (response) {
            Toast toast = Toast.makeText(this, (getString(R.string.collaborator_created)), Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(AddPersonActivity.this, CollaboratorsActivity.class);
            startActivity(intent);
        } else {
            //if the mail already exist in the database, request focus and tell the error
            tvMail.setError(getString(R.string.error_used_email));
            tvMail.requestFocus();
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