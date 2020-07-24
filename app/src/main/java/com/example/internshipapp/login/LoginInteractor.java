package com.example.internshipapp.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class LoginInteractor {

    private Contract.LoginListener loginListener;
    LoginPresenter loginPresenter;

    public LoginInteractor(Contract.LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void loginPostRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(loginPresenter.getLoginActivityContext());
        String url = "https://ancient-earth-13943.herokuapp.com/api/users/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TAGG", "Response " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAGG", "Error: " + error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    public Contract.LoginListener getLoginListener() {
        return loginListener;
    }
}
