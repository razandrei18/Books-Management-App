package com.example.internshipapp.login;

public interface Contract {

    interface LoginView{
         void onSuccesView();
         void onFailedView(String message);
    }

    interface LoginListener{
        void onSucces();
        void onFailed(String message);
    }
}
