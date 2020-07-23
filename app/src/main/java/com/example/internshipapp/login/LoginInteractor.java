package com.example.internshipapp.login;

import android.text.TextUtils;
import android.util.Patterns;

public class LoginInteractor {

    private Contract.LoginListener loginListener;

    public LoginInteractor(Contract.LoginListener loginListener){
        this.loginListener=loginListener;
    }

    public void login(LoginModel loginModel){
        if (hasError(loginModel)){
            return;
        }
        loginListener.onSucces();
    }

    private boolean hasError(LoginModel loginModel){
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();

        if (TextUtils.isEmpty(username) || !Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            loginListener.onFailed("The username is empty or the email is invalid");
            return true;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6 ){
            loginListener.onFailed("The password must have at least 6 characters!");
            return true;
        }
        return false;

    }
}
