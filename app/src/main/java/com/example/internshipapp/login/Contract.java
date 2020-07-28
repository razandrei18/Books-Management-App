package com.example.internshipapp.login;

public interface Contract {

    interface LoginView {
        void onSuccesView();

        void onFailedUsername();

        void onFailedPassword();

        void onLoginDefinedError(String message);

        void onLoginUndefinedError();
    }

    interface LoginListener {
        void onSucces();

        void onFailed(String message);
    }
}
