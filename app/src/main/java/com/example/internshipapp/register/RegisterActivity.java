package com.example.internshipapp.register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.internshipapp.R;
import com.example.internshipapp.login.LoginActivity;
import com.example.internshipapp.login.LoginModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.zip.Inflater;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView {
    RegisterPresenter registerPresenter;
    TextInputEditText username, email, password, confirmPassword;
    CircularProgressButton registerButton;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this, this);
        username = findViewById(R.id.textInputUsernameRegister);
        email = findViewById(R.id.textInputEmailRegister);
        password = findViewById(R.id.textInputPasswordRegister);
        confirmPassword = findViewById(R.id.textInputConfirmPasswordRegister);
        registerButton = findViewById(R.id.buttonRegister);
        builder = new AlertDialog.Builder(RegisterActivity.this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressButtonAnimation();
                username.setEnabled(false);
                email.setEnabled(false);
                password.setEnabled(false);
                confirmPassword.setEnabled(false);
                registerButton.setEnabled(false);
                String usernameField = username.getText().toString().trim();
                String emailField = email.getText().toString().trim();
                String passwordField = password.getText().toString().trim();
                String confirmPasswordField = confirmPassword.getText().toString().trim();
                RegisterModel registerModel = new RegisterModel(usernameField, emailField, passwordField, confirmPasswordField);
                registerPresenter.start(registerModel);
            }
        });
    }

    @Override
    public void onSuccesView() {
        Toast.makeText(this, R.string.registrSuccessfullyMessage, Toast.LENGTH_LONG).show();
        displaySuccessRegisterALert();
        username.setEnabled(true);
        email.setEnabled(true);
        password.setEnabled(true);
        confirmPassword.setEnabled(true);
        registerButton.setEnabled(true);
    }

    @Override
    public void onFailedCredentials() {
        displayFailedRegisterALert(getString(R.string.failedCredentialsRegisterMsg));
        username.setEnabled(true);
        email.setEnabled(true);
        password.setEnabled(true);
        confirmPassword.setEnabled(true);
        registerButton.setEnabled(true);
        hideProgressButtonAnimation();
    }

    @Override
    public void onFailedPassword() {
        displayFailedRegisterALert(getString(R.string.passwordFailedErrorMsg));
        username.setEnabled(true);
        email.setEnabled(true);
        password.setEnabled(true);
        confirmPassword.setEnabled(true);
        registerButton.setEnabled(true);
        hideProgressButtonAnimation();
    }

    @Override
    public void onRegisterDefinedError(String message) {
        displayFailedRegisterALert(message);
        username.setEnabled(true);
        email.setEnabled(true);
        password.setEnabled(true);
        confirmPassword.setEnabled(true);
        registerButton.setEnabled(true);
        hideProgressButtonAnimation();
    }

    @Override
    public void onRegisterUndefinedError() {
        displayFailedRegisterALert(getString(R.string.undefinedRegisterError));
        hideProgressButtonAnimation();
    }


    public void displayFailedRegisterALert(String message) {
        builder.setTitle(R.string.errorRegisterDialogTitle);
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

    public void displaySuccessRegisterALert(){
        builder.setTitle(R.string.successRegisterDialogTitle);
        builder.setMessage(R.string.successRegisterDialogMsg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showProgressButtonAnimation() {
        registerButton.startAnimation();
    }

    public void hideProgressButtonAnimation() {
        registerButton.revertAnimation();
        registerButton.setBackgroundResource(R.drawable.button);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
