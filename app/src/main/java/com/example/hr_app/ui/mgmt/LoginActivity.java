package com.example.hr_app.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.database.repository.CollaboratorRepository;
import com.example.hr_app.ui.BaseHRActivity;

import com.example.hr_app.ui.MainActivity;
import com.example.hr_app.ui.MenuActivity;

import java.util.ArrayList;
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

        ((BaseApp) this.getApplication()).setTheMail("Nobody connected");

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




       /* name = CR.getTest(getApplication());
        for(Collaborator c : name){
            if(c.getEmail().equals(loginCase)){
                if(c.getPassword().equals(pwdCase)){
                    id = c.getIdCollaborator();
                }
            }
        }
*/
        if (error) {
            focusView.requestFocus();
        } else {

            CR.getOneCollaborator(loginCase, getApplication()).observe(LoginActivity.this, collaborator -> {
                if (collaborator != null) {
                    if (collaborator.getPassword().equals(pwdCase)) {
                        ((BaseApp) this.getApplication()).setTheMail(collaborator.getEmail());
                        if(collaborator.getService().equals("HR")){
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }

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

    /*public void register(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }*/

    public void registerHR(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
