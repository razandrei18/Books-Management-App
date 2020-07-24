package com.example.internshipapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.internshipapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements Contract.LoginView {
    private LoginPresenter loginPresenter;
    TextInputEditText usernameInput, passwordInput;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this, this);
        usernameInput = findViewById(R.id.textInputUsername);
        passwordInput = findViewById(R.id.textInputPassword);
        loginBtn = findViewById(R.id.buttonLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                LoginModel loginModel = new LoginModel(username, password);
                loginPresenter.start(loginModel);
            }
        });
    }

    @Override
    public void onSuccesView() {
        Toast.makeText(this, R.string.loginSuccessfully, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedView(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
