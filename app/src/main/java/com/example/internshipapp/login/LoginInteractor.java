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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginInteractor {

    private Contract.LoginListener loginListener;
    LoginPresenter loginPresenter;

    public LoginInteractor(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public void loginPostRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(loginPresenter.getLoginActivityContext());
        String url = "https://ancient-earth-13943.herokuapp.com/api/users/login";
        StringRequest loginStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String loginResponse) {
                Log.i("LOGINTAG", "Response " + loginResponse.toString());
                parseJsonResponseLogin(loginResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("LOGINTAG ", "Error: " + error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> loginParams = new HashMap<String, String>();
                LoginModel loginModel = new LoginModel();
                loginParams.put("email", loginModel.getUsername());
                loginParams.put("password", loginModel.getPassword());
                return loginParams;
            }
        };
        requestQueue.add(loginStringRequest);
    }

    public void parseJsonResponseLogin(String jsonStr){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
            if(jsonObject.get("success").equals("true")){
                Toast.makeText(loginPresenter.getLoginActivityContext(), "SUCCESS!!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(loginPresenter.getLoginActivityContext(), "FAILED!!", Toast.LENGTH_SHORT).show();
        }
    }
}
