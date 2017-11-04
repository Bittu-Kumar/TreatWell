package com.example.bittukumar.treatwell.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class VolleyJsonRequest {

    private static ProgressDialog progressDialog;

    public static JsonObjectRequest request(final Context context, String url, JSONObject requestObject, final OnJsonResponse onResponse, final boolean isProgressShow)  {
        JsonObjectRequest jsObjRequest = null;
        HashMap<String, String> params = new HashMap<String, String>();
//        String creds = String.format("%s:%s", "partner01", "hjHSm518");
//        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//        params.put("Authorization", auth);
        params.put("id","00128");
        params.put("password","user1");
        Log.v("VollyURL    --->>", url);
        if (CheckNetwork.isInternetAvailable(context)) {
            try {
                if (progressDialog == null && isProgressShow) {
                    showProgressDialog(context);
                }
                if (requestObject != null)
                    Log.v("Vollyrequest", requestObject.toString());
                jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET,url, new JSONObject(params), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                    progressDialog = null;
                                }
                                if (response != null) {
                                    Log.v("Vollyresponse", response.toString());
                                    try {
                                        if(response.getBoolean("status"))
                                        {
                                            onResponse.responseReceived(response);
                                        }

                                        else {
                                            onResponse.errorReceived(response.toString());
                                        }
                                    } catch (JSONException e) {
                                        Log.e(TAG, "onResponse: ", e);
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

                                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                                if (error.networkResponse != null) {
                                    onResponse.errorReceived("Server is Temporarily Down");
                                } else
                                    Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {

//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap<>();
//                        headers.put("Content-Type", "application/json;charset=utf-8");
//                        return headers;
//                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return getHeadersForRequest();
                    }

                };
            } catch (ArithmeticException e) {
                Log.e("caught", "request: ", e);
            }
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        } else {
             Toast.makeText(context,"internet not available",Toast.LENGTH_SHORT).show();

        }
        return jsObjRequest;
    }


    private static Map<String, String> getHeadersForRequest() {
        HashMap<String, String> params = new HashMap<String, String>();
//        String creds = String.format("%s:%s", "partner01", "hjHSm518");
//        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//        params.put("Authorization", auth);
        params.put("id","00128");
        params.put("password","user1");
       /* if (!TextUtils.isEmpty(mRequestBody))
            params.put("checksum", ChecksumGenerator.generateCheckSum(mRequestBody));*/
        return params;
    }

    private static void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //progressDialog.setIndeterminateDrawable(ContextCompat.getDrawable(context,R.drawable.ep_progress));
        if (!progressDialog.isShowing() && !((Activity) context).isFinishing()) {
            progressDialog.show();
        }
    }

    public interface OnJsonResponse {
        void responseReceived(JSONObject jsonObj);

        void errorReceived(String message);
    }

}
