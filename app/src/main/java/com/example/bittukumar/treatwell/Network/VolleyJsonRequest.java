package com.example.bittukumar.treatwell.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bittukumar.treatwell.Utils.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class VolleyJsonRequest {

    private static ProgressDialog progressDialog;

    public static JsonObjectRequest request(final Context context, String url, JSONObject requestObject, final OnJsonResponse onResponse, final boolean isProgressShow){
        JsonObjectRequest jsObjRequest = null;
        Log.v("VollyURL    --->>", url);
        if (CheckNetwork.isInternetAvailable(context)) {
            try {
                if (progressDialog == null && isProgressShow) {
                    showProgressDialog(context);
                }
                if (requestObject != null)
                    Log.v("Vollyrequest", requestObject.toString());
                jsObjRequest = new JsonObjectRequest
                        (url, requestObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                    progressDialog = null;
                                }
                                if (response != null) {
                                    Log.v("Vollyresponse", response.toString());
//                                    try {
//
//                                    } catch (JSONException e) {
//                                        Log.e(TAG, "onResponse: ", e);
//                                    }
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
                                    onResponse.errorReceived(error.networkResponse.statusCode, "Server is Temporarily Down");
                                } else
                                    Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return getHeadersForRequest();
                    }
                };
            } catch (ArithmeticException e) {
                Log.e("caught", "request: ", e);
            }
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        } else {
//            throw new InternetNotAvailableException(context.getString(R.string.internet_not_available));
        }
        return jsObjRequest;
    }


    private static Map<String, String> getHeadersForRequest() {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", "partner01", "hjHSm518");
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
       /* if (!TextUtils.isEmpty(mRequestBody))
            params.put("checksum", ChecksumGenerator.generateCheckSum(mRequestBody));*/
        return params;
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

    public interface OnJsonResponse {
        void responseReceived(JSONObject jsonObj);

        void errorReceived(int code, String message);
    }

}
