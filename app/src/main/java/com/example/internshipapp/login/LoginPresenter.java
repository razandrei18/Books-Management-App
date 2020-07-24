package com.example.internshipapp.login;

import android.content.Context;
import android.text.TextUtils;
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
        }
        loginInteractor.loginPostRequest();
        loginInteractor.loginPresenter.onSucces();
    }

    @Override
    public void onSucces() {
        loginView.onSuccesView();
    }

    @Override
    public void onFailed(String message) {
        loginView.onFailedView(message);
    }

    public Context getLoginActivityContext() {
        return loginActivityContext;
    }

    private boolean checkLoginError(LoginModel loginModel) {
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();

        if (TextUtils.isEmpty(username) || !Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            loginView.onFailedView("The username is empty or the email is invalid");
            return true;
        }

        if (TextUtils.isEmpty(password) || password.length() < 2) {
            loginView.onFailedView("The password must have at least 6 characters!");
            return true;
        }
        return false;
    }
}
