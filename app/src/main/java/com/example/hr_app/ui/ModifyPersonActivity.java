package com.example.hr_app.ui;

import android.content.Intent;
import android.os.Bundle;
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

public class ModifyPersonActivity extends BaseHRActivity {

    private TextView tvName, tvService, tvMail, tvPassword;
    private Toast toast;
    private String mailCollaborator, name, service, password;
    private CollaboratorViewModel viewModel;
    private Collaborator oneCollaborator;
    private CollaboratorListViewModel dvm;
    private List<Collaborator> collaboList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_modifyperson, frameLayout);

        navigationView.setCheckedItem(position);

        mailCollaborator = ((BaseApp)this.getApplication()).getMailCollaborator();



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

    private void findData() {
        name = oneCollaborator.getName();
        service = oneCollaborator.getService();
        password = oneCollaborator.getPassword();

        tvName.setText(name);
        tvService.setText(service);
        tvMail.setText(mailCollaborator);
        tvPassword.setText(password);
    }


    private void update(String newName, String newService, String newMail, String newPassword) {

        tvName.setError(null);
        tvService.setError(null);
        tvMail.setError(null);
        tvPassword.setError(null);

        View focusView = null;

        boolean error = false;

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

        if (error) {
            focusView.requestFocus();
        } else {

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

    private boolean mailExist(String email) {

        collaboList = new ArrayList<>();

        CollaboratorListViewModel.Factory factory = new CollaboratorListViewModel.Factory(getApplication());
        dvm = ViewModelProviders.of(this,factory).get(CollaboratorListViewModel.class);
        dvm.getAllCollabo().observe(this, (List<Collaborator> collaborators1) -> {
            if(collaborators1!=null){
                collaboList = collaborators1;
            }
        });

        for (Collaborator collaborator: collaboList) {
            if(collaborator.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }


}
