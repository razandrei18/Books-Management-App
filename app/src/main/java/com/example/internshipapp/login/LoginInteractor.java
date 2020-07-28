package com.example.internshipapp.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginInteractor {

    private Contract.LoginListener loginListener;
    LoginPresenter loginPresenter;
    public String loginDefinedErrorMessage;


    public LoginInteractor(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public void loginPostRequest(final LoginModel loginModel) {

        RequestQueue requestQueue = Volley.newRequestQueue(loginPresenter.getLoginActivityContext());
        String urlLogin = "https://ancient-earth-13943.herokuapp.com/api/users/login";
        StringRequest loginStringRequest = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String loginResponse) {
                Log.i("LOGINTAG", "Response " + loginResponse.toString());
                parseJsonResponseLogin(loginResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseJsonErrorLogin(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> loginParams = new HashMap<String, String>();
                loginParams.put("email", loginModel.getUsername());
                loginParams.put("password", loginModel.getPassword());
                return loginParams;
            }
        };
        requestQueue.add(loginStringRequest);
    }

    public void parseJsonResponseLogin(String jsonStr) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
            if (jsonObject.getString("success").equals("true")) {
                jsonObject.getString("token");
                loginPresenter.onSucces();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String parseJsonErrorLogin(VolleyError volleyError) {
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null && networkResponse.data != null) {
            String jsonError = new String(networkResponse.data);
            JSONObject errorJSONObject = null;
            try {
                errorJSONObject = new JSONObject(jsonError);
                loginPresenter.onFailed(errorJSONObject.getString("error"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return loginDefinedErrorMessage;
    }


}
