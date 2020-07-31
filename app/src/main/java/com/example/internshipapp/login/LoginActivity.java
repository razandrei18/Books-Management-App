package com.example.internshipapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipapp.MainActivity;
import com.example.internshipapp.R;
import com.example.internshipapp.register.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.zip.Inflater;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity implements Contract.LoginView {
    private LoginPresenter loginPresenter;
    TextInputEditText usernameInput, passwordInput;
    TextView registerPageTextView;
    CircularProgressButton loginBtn;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this, this);
        usernameInput = findViewById(R.id.textInputUsername);
        passwordInput = findViewById(R.id.textInputPassword);
        registerPageTextView = findViewById(R.id.textViewRegister);
        loginBtn = findViewById(R.id.buttonLogin);
        builder = new AlertDialog.Builder(LoginActivity.this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressButtonAnimation();
                usernameInput.setEnabled(false);
                passwordInput.setEnabled(false);
                loginBtn.setEnabled(false);
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                LoginModel loginModel = new LoginModel(username, password);
                loginPresenter.start(loginModel);
            }
        });

        registerPageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccesView() {
        Toast.makeText(this, R.string.loginSuccessfully, Toast.LENGTH_SHORT).show();
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        loginBtn.setEnabled(true);
        hideProgressButtonAnimation();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailedUsername() {
        Toast.makeText(this, R.string.invalidUsernameErr, Toast.LENGTH_SHORT).show();
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        loginBtn.setEnabled(true);
        hideProgressButtonAnimation();
    }

    @Override
    public void onFailedPassword() {
        Toast.makeText(this, R.string.invalidPasswordError, Toast.LENGTH_SHORT).show();
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        loginBtn.setEnabled(true);
        hideProgressButtonAnimation();
    }

    @Override
    public void onLoginDefinedError(String message) {
        displayLoginALert(message);
    }

    @Override
    public void onLoginUndefinedError() {
        displayLoginALert(getString(R.string.loginUndefinedErrorText));
    }

    public void showProgressButtonAnimation() {
        loginBtn.startAnimation();

    }

    public void hideProgressButtonAnimation() {
        loginBtn.revertAnimation();
        loginBtn.setBackgroundResource(R.drawable.button);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus == true) {
            usernameInput.setEnabled(true);
            passwordInput.setEnabled(true);
            loginBtn.setEnabled(true);
            hideProgressButtonAnimation();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    public void displayLoginALert(String message) {
        builder.setTitle(R.string.loginAlertDialogTitle);
        builder.setMessage(message);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
