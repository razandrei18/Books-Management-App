package com.example.internshipapp.login;

public class LoginModel {

    private String username;
    private String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginModel(){}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
