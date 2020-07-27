package com.example.internshipapp.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

public class LoginPresenter implements Contract.LoginListener {

    private Contract.LoginView loginView;
    private LoginInteractor loginInteractor = new LoginInteractor(this);
    private Context loginActivityContext;


    public LoginPresenter(Contract.LoginView lw, Context c) {
        this.loginView = lw;
        this.loginActivityContext = c;
    }

    public void start(LoginModel loginModel) {
        login(loginModel);
    }

    public void login(LoginModel loginModel) {
        if (checkLoginError(loginModel)) {
            return;
        } else {
            loginInteractor.loginPostRequest(loginModel);
        }
    }

    @Override
    public void onSucces() {
        loginView.onSuccesView();
    }

    @Override
    public void onFailed(String message) {
    }

    public Context getLoginActivityContext() {
        return loginActivityContext;
    }

    private boolean checkLoginError(LoginModel loginModel) {
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();

        if (TextUtils.isEmpty(username) || !Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            loginView.onFailedUsername();
            return true;
        }

        if (TextUtils.isEmpty(password) || password.length() < 3) {
            loginView.onFailedPassword();
            return true;
        }
        return false;
    }
}
