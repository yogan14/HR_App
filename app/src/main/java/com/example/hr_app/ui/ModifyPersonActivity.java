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
import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.collaborator.CollaboratorListViewModel;
import com.example.hr_app.viewmodel.collaborator.CollaboratorViewModel;

import java.util.Locale;

/**
 * ModifyPersonActivity
 * The activity to modify or delete a collaborator
 */
public class ModifyPersonActivity extends BaseHRActivity {

    private TextView tvName, tvService, tvMail, tvPassword;
    private Toast toast;
    private String idCollaborator, name, service, mailCollaborator;
    private CollaboratorViewModel viewModel;
    private CollaboratorListViewModel vm;
    private CollaboratorEntity oneCollaborator;

    /**
     * Create the activity
     * @param savedInstanceState - the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_modifyperson, frameLayout);


        navigationView.setCheckedItem(position);
        setDisplay();

    }

    /**
     * State when we return in the app
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        setDisplay();
    }

    public void setDisplay(){
        idCollaborator = ((BaseApp)this.getApplication()).getIdCollaborator();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));

        tvName = findViewById(R.id.name_field);
        tvService = findViewById(R.id.service_field);
        tvMail = findViewById(R.id.mail_field);
        tvPassword = findViewById(R.id.password_field);

        Button update = findViewById(R.id.update_button);
        update.setOnClickListener(view -> update(tvName.getText().toString(), tvService.getText().toString()));

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(view -> deleteButton());

        CollaboratorViewModel.Factory factory = new CollaboratorViewModel.Factory(getApplication(), idCollaborator);
        viewModel = ViewModelProviders.of(this,factory).get(CollaboratorViewModel.class);

        viewModel.getOneCollaborator().observe(this, collaborator ->  {
            if(collaborator!=null) {
                oneCollaborator = collaborator;
                findData();
            }
        });

        CollaboratorListViewModel.Factory factory2 = new CollaboratorListViewModel.Factory(getApplication());
        vm = ViewModelProviders.of(this, factory2).get(CollaboratorListViewModel.class);


    }

    /**
     * when the collaborator is set, extract the attribute of the collaborator and put it to the text views
     */
    private void findData() {
        name = oneCollaborator.getName();
        service = oneCollaborator.getService();
        mailCollaborator = oneCollaborator.getEmail();

        tvName.setText(name);
        tvService.setText(service);
        tvMail.setText(mailCollaborator);
    }

    /**
     * verify the fields, and if all is ok, modify the collaborator in the database
     * @param newName - the content of the name field
     * @param newService - the content of the service field
     */
    private void update(String newName, String newService) {

        tvName.setError(null);
        tvService.setError(null);

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

            //modify the collaborator in the database
            vm.update(oneCollaborator, new OnAsyncEventListener() {
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

        vm.delete(oneCollaborator, new OnAsyncEventListener() {
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
                toast = Toast.makeText(this, (getString(R.string.collaborator_updated)), Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(ModifyPersonActivity.this, CollaboratorsActivity.class);
                startActivity(intent);
            } else {
                toast = Toast.makeText(this, (getString(R.string.collaborator_deleted)), Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(ModifyPersonActivity.this, CollaboratorsActivity.class);
                startActivity(intent);
            }

        } else {
            toast = Toast.makeText(this, (getString(R.string.error)), Toast.LENGTH_LONG);
            toast.show();
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
