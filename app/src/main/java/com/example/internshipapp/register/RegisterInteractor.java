package com.example.internshipapp.register;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.internshipapp.login.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterInteractor {
    private RegisterContract.RegisterListener registerListener;
    RegisterPresenter registerPresenter;
    public String registerDefinedErrorMessage;

    public RegisterInteractor(RegisterPresenter registerPresenter) {
        this.registerPresenter = registerPresenter;
    }

    public void registerPostRequest(final RegisterModel registerModel){
        RequestQueue requestQueue = Volley.newRequestQueue(registerPresenter.getRegisterActivityContext());
        String urlRegister = "https://ancient-earth-13943.herokuapp.com/api/users/register";
        StringRequest registerStringRequest = new StringRequest(Request.Method.POST, urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String registerResponse) {
                Log.i("LOGINTAG", "Response " + registerResponse.toString());
                parseJsonResponseRegister(registerResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("LOGINTAG", "Response " + error.networkResponse.data.toString());
                parseJsonErrorRegister(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> registerParams = new HashMap<String, String>();
                registerParams.put("email", registerModel.getEmail());
                registerParams.put("name", registerModel.getUsername());
                registerParams.put("password", registerModel.getPassword());
                registerParams.put("confirm", registerModel.getConfirmPassword());
                return registerParams;
            }
        };
        requestQueue.add(registerStringRequest);
    }

    public void parseJsonResponseRegister(String jsonString){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            if (jsonObject.get("message").equals("success")){
                registerPresenter.onSuccess();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String parseJsonErrorRegister(VolleyError registerError){
        NetworkResponse networkResponse = registerError.networkResponse;
        if (networkResponse != null && networkResponse.data !=null ){
            String jsonError = new String(networkResponse.data);
            JSONObject errorJSONObject = null;
            try {
                errorJSONObject = new JSONObject(jsonError);
                registerPresenter.onFailed(errorJSONObject.getString("error"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return registerDefinedErrorMessage;
    }
}
