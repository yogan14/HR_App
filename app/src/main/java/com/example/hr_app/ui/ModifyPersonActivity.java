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


import androidx.lifecycle.ViewModelProviders;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.async.collaborator.DeleteCollaborator;
import com.example.hr_app.database.async.collaborator.UpdateCollaborator;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.collaborator.CollaboratorViewModel;
import com.example.hr_app.viewmodel.collaborator.CollaboratorListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ModifyPersonActivity
 * The activity to modify or delete a collaborator
 */
public class ModifyPersonActivity extends BaseHRActivity {

    private TextView tvName, tvService, tvMail, tvPassword;
    private Toast toast;
    private String mailCollaborator, name, service, password;
    private CollaboratorViewModel viewModel;
    private Collaborator oneCollaborator;
    private CollaboratorListViewModel dvm;
    private List<Collaborator> collaboList;

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_modifyperson, frameLayout);

        navigationView.setCheckedItem(position);

        mailCollaborator = ((BaseApp)this.getApplication()).getMailCollaborator();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));

        tvName = findViewById(R.id.name_field);
        tvService = findViewById(R.id.service_field);
        tvMail = findViewById(R.id.mail_field);
        tvPassword = findViewById(R.id.password_field);

        Button update = findViewById(R.id.update_button);
        update.setOnClickListener(view -> {
            update(tvName.getText().toString(), tvService.getText().toString(), tvMail.getText().toString(), tvPassword.getText().toString());
        });

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(view -> {
            deleteButton();
        });

        CollaboratorViewModel.Factory factory = new CollaboratorViewModel.Factory(getApplication(), mailCollaborator);
        viewModel = ViewModelProviders.of(this,factory).get(CollaboratorViewModel.class);

        viewModel.getOneCollaborator().observe(this, collaborator ->  {
            if(collaborator!=null) {
                oneCollaborator = collaborator;
                findData();
            }
        });

    }

    /**
     * State when we return in the app
     */
    @Override
    protected void onResume()
    {
        super.onResume();
    }

    /**
     * when the collaborator is set, extract the attribute of the collaborator and put it to the text views
     */
    private void findData() {
        name = oneCollaborator.getName();
        service = oneCollaborator.getService();
        password = oneCollaborator.getPassword();

        tvName.setText(name);
        tvService.setText(service);
        tvMail.setText(mailCollaborator);
        tvPassword.setText(password);
    }

    /**
     * verify the fields, and if all is ok, modify the collaborator in the database
     * @param newName - the content of the name field
     * @param newService - the content of the service field
     * @param newMail - the content of the mail field
     * @param newPassword - the content of the password field
     */
    private void update(String newName, String newService, String newMail, String newPassword) {

        tvName.setError(null);
        tvService.setError(null);
        tvMail.setError(null);
        tvPassword.setError(null);

        View focusView = null;

        boolean error = false;

        //check if it has an error
        if (TextUtils.isEmpty(newName)) {
            tvName.setError(getString(R.string.empty_field));
            tvName.setText("");
            focusView = tvName;

            error = true;
        } else {
            if (TextUtils.isEmpty(newService)) {
                tvService.setError(getString(R.string.empty_field));
                tvService.setText("");
                focusView = tvService;

                error = true;
            } else {
                if (TextUtils.isEmpty(newMail)) {
                    tvMail.setError(getString(R.string.empty_field));
                    tvMail.setText("");
                    focusView = tvMail;

                    error = true;
                } else {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newMail).matches()) {
                        tvMail.setError(getString(R.string.invalid_mail));
                        tvMail.setText(newMail);
                        focusView = tvMail;

                        error = true;
                    } else {
                        if(mailExist(newMail)) {
                            tvMail.setError(getString(R.string.error_used_email));
                            tvMail.setText(newMail);
                            focusView = tvMail;

                            error = true;
                        } else {
                            if (TextUtils.isEmpty(newPassword)){
                                tvPassword.setError(getString(R.string.empty_field));
                                tvPassword.setText("");
                                focusView = tvPassword;

                                error = true;
                            } else {
                                if (newPassword.length() < 5) {
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
        }

        //if an error occurs, focus on the field
        if (error) {
            focusView.requestFocus();
        } else {

            //else, modify the object collaborator
            if(!name.equals(newName)){
                oneCollaborator.setName(newName);
            }
            if(!service.equals(newService)){
                oneCollaborator.setService(newService);
            }
            if(!mailCollaborator.equals(newMail)) {
                oneCollaborator.setEmail(newMail);
            }
            if(!password.equals(newPassword)) {
                oneCollaborator.setPassword(newPassword);
            }

            //modify the collaborator in the database
            new UpdateCollaborator(getApplication(), new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    setResponse(true, "update");
                }

                @Override
                public void onFailure(Exception e) {
                    setResponse(false, "update");
                }
            }).execute(oneCollaborator);
        }


    }

    /**
     * delete a collaborator in the database
     */
    private void deleteButton() {

        new DeleteCollaborator(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true, "delete");
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false, "delete");
            }
        }).execute(oneCollaborator);

    }

    /**
     * setResponse
     * if it's ok, modify / delete and return to the list of collaborator, if not, say it and return to the list of collaborator
     * @param response - ok or not
     */
    private void setResponse(Boolean response, String type) {
        if (response) {
            if(type.equals("update")) {
                toast = Toast.makeText(this, (getString(R.string.collaborator_updated)), Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(this, (getString(R.string.collaborator_deleted)), Toast.LENGTH_LONG);
            }

            toast.show();
            Intent intent = new Intent(ModifyPersonActivity.this, CollaboratorsActivity.class);
            startActivity(intent);
        } else {
            toast = Toast.makeText(this, (getString(R.string.error)), Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(ModifyPersonActivity.this, CollaboratorsActivity.class);
            startActivity(intent);
        }
    }

    /**
     * check if the mail exist already in the database
     * @param email - the mail for the check
     * @return
     */
    private boolean mailExist(String email) {

        collaboList = new ArrayList<>();

        //get a list of all the collaborators
        CollaboratorListViewModel.Factory factory = new CollaboratorListViewModel.Factory(getApplication());
        dvm = ViewModelProviders.of(this,factory).get(CollaboratorListViewModel.class);
        dvm.getAllCollabo().observe(this, (List<Collaborator> collaborators1) -> {
            if(collaborators1!=null){
                collaboList = collaborators1;
            }
        });

        //if the mail exist, return true, else, return false
        for (Collaborator collaborator: collaboList) {
            if(collaborator.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
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
