package com.example.internshipapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.internshipapp.R;
import com.google.android.material.textfield.TextInputEditText;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity implements Contract.LoginView {
    private LoginPresenter loginPresenter;
    TextInputEditText usernameInput, passwordInput;
    CircularProgressButton loginBtn;


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
                showProgressButton();
                usernameInput.setEnabled(false);
                passwordInput.setEnabled(false);
                loginBtn.setEnabled(false);
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                LoginModel loginModel = new LoginModel(username, password);
                loginPresenter.start(loginModel);
            }
        });
    }

    @Override
    public void onSuccesView() {
        Toast.makeText(this, R.string.loginSuccessfully, Toast.LENGTH_SHORT).show();
        usernameInput.setEnabled(false);
        passwordInput.setEnabled(false);
        loginBtn.setEnabled(false);
        hideProgressButton();
    }

    @Override
    public void onFailedUsername() {
        Toast.makeText(this, R.string.invalidUsernameErr, Toast.LENGTH_SHORT).show();
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        loginBtn.setEnabled(true);

        hideProgressButton();
    }

    @Override
    public void onFailedPassword() {
        Toast.makeText(this, R.string.invalidPasswordError, Toast.LENGTH_SHORT).show();
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        loginBtn.setEnabled(true);
        hideProgressButton();
    }

    public void showProgressButton() {
        loginBtn.startAnimation();
        loginBtn.setBackgroundResource(R.drawable.button);
    }

    public void hideProgressButton() {
        loginBtn.revertAnimation();
        loginBtn.setBackgroundResource(R.drawable.button);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus==true){
            usernameInput.setEnabled(true);
            passwordInput.setEnabled(true);
            loginBtn.setEnabled(true);
            hideProgressButton();
        }
        super.onWindowFocusChanged(hasFocus);
    }
}
