package com.example.internshipapp.register;

public interface RegisterContract {
    interface RegisterView {
        void onSuccesView();
        void onFailedCredentials();
        void onFailedPassword();
        void onRegisterDefinedError(String message);
        void onRegisterUndefinedError();
    }

    interface RegisterListener{
        void onSuccess();
        void onFailed(String message);
    }
}
