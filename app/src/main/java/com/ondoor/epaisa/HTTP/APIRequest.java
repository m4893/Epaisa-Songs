package com.ondoor.epaisa.HTTP;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ondoor.epaisa.ApplicationController;
import com.ondoor.epaisa.Constants.Constant;
import com.ondoor.epaisa.InterFace.RequestCallBack;
import com.ondoor.epaisa.Utility.Utility;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 9/20/2016.
 */
public class APIRequest {


    Context context;

    static ProgressDialog progressDialog;

    public APIRequest(View view){

        this.context=view.getContext();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");

    }
    public APIRequest(View view, boolean cancelable){

        this.context=view.getContext();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(cancelable);

    }

    public APIRequest(View view, String progressBarMessage){

        this.context=view.getContext();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(progressBarMessage);

    }
    public APIRequest(View view, boolean cancelable, String progressBarMessage){

        this.context=view.getContext();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(progressBarMessage);
        progressDialog.setCancelable(cancelable);
    }

    private Map<String,String> getHeader(){

        Map<String, String> header = new HashMap<String, String>();


        
        return header;

    }

    public void post(String method, JSONObject jsonObject, final RequestCallBack requestCallBack){

        try {
            Log.e("jsonObject", "result  " +method+" - "+ jsonObject);
            if(!Utility.isConnectingToInternet(context))
                Toast.makeText(context,"Please check internet connection",Toast.LENGTH_LONG).show();
            else {
                progressDialog.show();

                HttpsTrustManager.allowAllSSL();
                JsonObjectRequest jsonRequest = new JsonObjectRequest
                        (Request.Method.POST, Constant.BASE_URL + method, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject json) {
                                try {
                                    progressDialog.dismiss();
                                    Log.e("result", "result  " + json);
                                    if (json != null) {
                                            requestCallBack.onResponse(json);
                                    } else
                                        Toast.makeText(context,"Please try again...",Toast.LENGTH_LONG).show();

                                } catch (Exception e) {
                                    Toast.makeText(context,"Please try again...",Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                progressDialog.dismiss();

                                error.printStackTrace();

                                Toast.makeText(context,"Please try again...",Toast.LENGTH_LONG).show();

                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        return getHeader();
                    }

                };

                int socketTimeout = 60000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonRequest.setRetryPolicy(policy);
                ApplicationController.getInstance().addToRequestQueue(jsonRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Please try again...",Toast.LENGTH_LONG).show();
        }

    }
}
