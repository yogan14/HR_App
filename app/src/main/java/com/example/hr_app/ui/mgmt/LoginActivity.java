package com.example.hr_app.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.database.repository.CollaboratorRepository;

import com.example.hr_app.ui.MainActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText login, pwd;
    private CollaboratorRepository CR;
    private List<Collaborator> name;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.editText2);
        pwd = findViewById(R.id.editText);
        registerButton = findViewById(R.id.button2);
        registerButton.setOnClickListener(view -> register());


    }

    public void register() {
        login.setError(null);
        pwd.setError(null);
        CR = ((BaseApp) getApplication()).getCollaboratorRepository();
        String loginCase = login.getText().toString();
        String pwdCase = pwd.getText().toString();
        View focusView = null;
        boolean error = false;

        if (TextUtils.isEmpty(loginCase)) {
            login.setError("Login is empty");
            login.setText("");
            focusView = login;
            error = true;

        } else {
            if (TextUtils.isEmpty(pwdCase)) {
                pwd.setError("Password is empty");
                pwd.setText("");
                focusView = pwd;

                error = true;
            }
        }


        if (error) {
            focusView.requestFocus();
        } else {

            CR.getOneCollaborator(loginCase, getApplication()).observe(LoginActivity.this, collaborator -> {
                if (collaborator != null) {
                    if (collaborator.getPassword().equals(pwdCase)) {
                        ((BaseApp) this.getApplication()).setTheMail(collaborator.getEmail());
                        if(collaborator.getService().equals("HR")){
                            ((BaseApp) this.getApplication()).setHR(true);
                        } else {
                            ((BaseApp) this.getApplication()).setHR(false);
                        }

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        pwd.setError("Password incorrect");
                        pwd.requestFocus();
                        pwd.setText("");
                    }


                } else {
                    login.setError("Non-existent login");
                    login.requestFocus();
                    login.setText("");
                }
            });

        }

    }


    public void registerHR(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
