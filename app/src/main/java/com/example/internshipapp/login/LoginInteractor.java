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
    AlertDialog.Builder builder;

    public LoginInteractor(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public void loginPostRequest(final LoginModel loginModel) {
        builder = new AlertDialog.Builder(loginPresenter.getLoginActivityContext());
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
                Log.i("LOGINTAG ", "Error: " + error.networkResponse.data);
                if(error.networkResponse.statusCode == 404) {
                    parseJsonErrorLogin(error);
                }
                else {
                    builder.setTitle("Login Error");
                    displayLoginALert("Something went wrong, please try again later!");
                }
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

    public void parseJsonErrorLogin(VolleyError volleyError) {
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null && networkResponse.data != null) {
            String jsonError = new String(networkResponse.data);
            JSONObject errorJSONObject = null;
            try {
                errorJSONObject = new JSONObject(jsonError);
                builder.setTitle("Login Error!");
                displayLoginALert(errorJSONObject.getString("error"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayLoginALert(String message){
        builder.setMessage(message);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
