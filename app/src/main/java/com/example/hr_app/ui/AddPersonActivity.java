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
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.database.repository.CollaboratorRepository;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.absences.AbsenceListNotValidateViewModel;
import com.example.hr_app.viewmodel.collaborator.CollaboratorListViewModel;

public class AddPersonActivity extends BaseHRActivity {

    private Button bAdd;

    private TextView tvName, tvService, tvMail, tvPassword;

    private String name, service, mail, password;

    private CollaboratorRepository cr;

    private CollaboratorListViewModel viewModel;

    private OnAsyncEventListener callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_addperson, frameLayout);

        navigationView.setCheckedItem(position);

        tvName = findViewById(R.id.name_field);
        tvService = findViewById(R.id.service_field);
        tvMail = findViewById(R.id.mail_field);
        tvPassword = findViewById(R.id.password_field);
        bAdd = findViewById(R.id.addCollaborator);

        bAdd.setOnClickListener(view -> addCollaborator());
    }

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
                        }
                    }
                }
            }
        }

        if (error) {
            focusView.requestFocus();
        } else {

            Collaborator collaborator = new Collaborator(name, service, mail, password);
            cr = ((BaseApp) getApplication()).getCollaboratorRepository();

            CollaboratorListViewModel.Factory factory = new CollaboratorListViewModel.Factory(getApplication());
            viewModel = ViewModelProviders.of(this, factory).get(CollaboratorListViewModel.class);

            viewModel.insert(collaborator, callback);

            Toast toast = Toast.makeText(this, "request created", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, CollaboratorsActivity.class);
            startActivity(intent);
        }
    }
}