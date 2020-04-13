package com.example.hr_app.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.database.repository.CollaboratorRepository;

import com.example.hr_app.ui.MainActivity;
import com.example.hr_app.viewmodel.collaborator.CollaboratorListViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    /**
     * Declaration of the variables
     */
    private Button registerButton;
    private EditText login, pwd;
    private CollaboratorRepository CR;
    private CollaboratorListViewModel viewModel;
    private List<CollaboratorEntity> collaborators;
    boolean check;


    /**
     * When the activity resume
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    /**
     * onCreate
     * On the creation of the activity
     * @param savedInstanceState - the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Set the text fields and the button
        login = findViewById(R.id.editText2);
        pwd = findViewById(R.id.editText);
        registerButton = findViewById(R.id.button2);
        registerButton.setOnClickListener(view -> register());

        CollaboratorListViewModel.Factory factory = new CollaboratorListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(CollaboratorListViewModel.class);


    }

    /**
     * register
     * Method to log in the application
     */
    public void register() {
        //reset the errors
        login.setError(null);
        pwd.setError(null);

        //Get a Collaborator repository that will be use to compare fields
        CR = ((BaseApp) getApplication()).getCollaboratorRepository();

        //Store the values in temporary fields
        String loginCase = login.getText().toString();
        String pwdCase = pwd.getText().toString();
        View focusView = null;
        boolean error = false;

        //Case when the login or/and password field are empty
        if (TextUtils.isEmpty(loginCase)) {
            login.setError(getString(R.string.login_empty));
            login.setText("");
            focusView = login;
            error = true;

        } else {
            if (TextUtils.isEmpty(pwdCase)) {
                pwd.setError(getString(R.string.password_empty));
                pwd.setText("");
                focusView = pwd;

                error = true;
            }
        }

        //Prevents the login to focus on the error(s)
        if (error) {
            focusView.requestFocus();
        } else {
            CR.signIn(loginCase,pwdCase,task -> {
                if(task.isSuccessful()){

                    if(isHR(loginCase)){
                        ((BaseApp) this.getApplication()).setHR(true);
                    } else {
                        ((BaseApp) this.getApplication()).setHR(false);
                    }

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {

                    login.setError(getString(R.string.invalid_account));
                    login.requestFocus();
                    pwd.setText("");
                }
            });
        }
    }

    public boolean isHR (String login){

        collaborators = new ArrayList<>();

        viewModel.getAllCollabo().observe(this, (List<CollaboratorEntity> collaborators1) -> {
            if(collaborators1!=null){
                collaborators = collaborators1;


            }
        });

            for (CollaboratorEntity c : collaborators) {
                if (c.getEmail().equals(login)) {
                    if (c.getService().equals("HR")) {
                        check = true;
                    } else {
                        check = false;
                    }
                }
            }

        return check;
    }
}
