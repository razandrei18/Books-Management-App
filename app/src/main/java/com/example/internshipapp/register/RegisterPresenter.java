package com.example.internshipapp.register;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.internshipapp.login.Contract;
import com.example.internshipapp.login.LoginInteractor;

public class RegisterPresenter implements RegisterContract.RegisterListener {

    private RegisterContract.RegisterView registerView;
    private RegisterInteractor registerInteractor = new RegisterInteractor(this);

    public Context getRegisterActivityContext() {
        return registerActivityContext;
    }

    private Context registerActivityContext;

    public RegisterPresenter(RegisterContract.RegisterView registerView, Context c){
        this.registerView = registerView;
        this.registerActivityContext = c;
    }

    public void start(RegisterModel registerModel){
        register(registerModel);
    }

    public void register(RegisterModel registerModel)
    {
        if(checkRegisterError(registerModel)){
            return;
        } else {
            registerInteractor.registerPostRequest(registerModel);
        }
    }

    @Override
    public void onSuccess() {
        registerView.onSuccesView();
    }

    @Override
    public void onFailed(String message) {
        if(message != null){
            registerView.onRegisterDefinedError(message);
        }
        else {
            registerView.onRegisterUndefinedError();
        }
    }

    private boolean checkRegisterError(RegisterModel registerModel) {
        String username = registerModel.getUsername();
        String email = registerModel.getEmail();
        String password = registerModel.getPassword();
        String confirmPassword = registerModel.getConfirmPassword();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerView.onFailedCredentials();
            return true;
        }
        if(confirmPassword.equals(password) && password.length() <3 && confirmPassword.length() <3){
            registerView.onFailedPassword();
            return true;
        }
        return false;
    }
}
