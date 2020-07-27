package com.example.internshipapp.login;

public interface Contract {

    interface LoginView{
         void onSuccesView();
         void onFailedUsername();
         void onFailedPassword();
    }

    interface LoginListener{
        void onSucces();
        void onFailed(String message);
    }
}
