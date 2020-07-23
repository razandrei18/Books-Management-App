package com.example.internshipapp.login;

public class LoginPresenter implements Contract.LoginListener {

    private Contract.LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter(Contract.LoginView lw) {
        this.loginView = lw;
        loginInteractor = new LoginInteractor(this);
    }

    public void start(LoginModel loginModel){
        loginInteractor.login(loginModel);
    }

    @Override
    public void onSucces() {
        loginView.onSucces();
    }

    @Override
    public void onFailed(String message) {
        loginView.onFailed(message);
    }
}
