package com.example.hr_app.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hr_app.R;
import com.example.hr_app.database.repository.CollaboratorRepository;
import com.example.hr_app.ui.BaseHRActivity;
import com.example.hr_app.ui.MenuActivity;

public class LoginActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText login, pwd;
    private CollaboratorRepository CR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.editText2);
        pwd = findViewById(R.id.editText);
        registerButton = findViewById(R.id.button2);

    }

    private void register(){
        login.setError(null);
        pwd.setError(null);

        String loginCase = login.getText().toString();
        String pwdCase = pwd.getText().toString();
        View focus = null;

        if(TextUtils.isEmpty(loginCase)){
            login.setError("Login is empty");
            login.setText("");
            focus = login;

        }

        if(TextUtils.isEmpty(pwdCase)){
            pwd.setError("Password is empty");
            pwd.setText("");
            focus = pwd;
        }


        int test  =1;
        CR.getOneCollaborator(test, getApplication()).observe(LoginActivity.this, collaborator -> {

        });

    }

    public void register(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void registerHR(View view){
        Intent intent = new Intent(this, BaseHRActivity.class);
        startActivity(intent);
    }
}
