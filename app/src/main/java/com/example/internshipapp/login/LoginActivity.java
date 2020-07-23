package com.example.internshipapp.login;

import androidx.appcompat.app.AppCompatActivity;

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
        loginPresenter = new LoginPresenter(this);
        usernameInput = findViewById(R.id.textInputUsername);
        passwordInput = findViewById(R.id.textInputPassword);
        loginBtn = findViewById(R.id.buttonLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern = usernameInput.getText().toString();
                String pass = passwordInput.getText().toString();

                LoginModel loginModel = new LoginModel(usern, pass);
                loginPresenter.start(loginModel);
            }
        });
    }

    @Override
    public void onSucces() {
        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
