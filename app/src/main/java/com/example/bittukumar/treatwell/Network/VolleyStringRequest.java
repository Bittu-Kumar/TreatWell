package com.example.bittukumar.treatwell.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ADMIN on 7/18/2016.
 */
public class VolleyStringRequest {
    private static ProgressDialog progressDialog;

    public static void request(final Context context, String url, final Map<String,String> params, final OnStringResponse onResponse) {
        if (progressDialog == null) {
            showProgressDialog(context);
        }
//        CookieManager cookieManager =new CookieManager();
//        CookieHandler.setDefault(cookieManager);
        Log.v("VollyURL    --->>", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                        if(response != null)
                        {
                            Log.v("Vollyresponse", response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if(jsonResponse.getBoolean("status"))
                                {
                                    onResponse.responseReceived(response);
                                }
                                else
                                {
                                    Toast.makeText(context,jsonResponse.getString("message"),Toast.LENGTH_LONG).show();
                                    onResponse.errorReceived(0,response);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                
                onResponse.errorReceived(1, error.getMessage());
                // TODO: 05-10-2017 handle errorRecieved correctly 
            }
        })
        {
            public Map<String,String> getParamslocal() {
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return getParamslocal();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String,String> headers =  new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
//                headers.put("Content-Type", "application/json;charset=utf-8");
                return headers;
            }

        };

        VolleyRequestQueue.getInstance(context).addToRequestQueue(stringRequest);
    }
    private static void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
//        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading...");
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (!progressDialog.isShowing() && !((Activity) context).isFinishing()) {
            progressDialog.show();
        }
    }



    public interface OnStringResponse {
        void responseReceived(String response);

        void errorReceived(int code, String message);
    }
}
