package com.example.bittukumar.treatwell.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bittukumar.treatwell.Network.VolleyStringRequest;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.AppConstants;
import com.example.bittukumar.treatwell.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameET;
    private EditText passwordET;
    private Button loginBTN;
    private TextView signupTV;
    CookieManager cookieManager = new CookieManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CookieHandler.setDefault(cookieManager);

        init();
    }

    private void init() {
        usernameET = (EditText)findViewById(R.id.login_usernameET);
        passwordET = (EditText)findViewById(R.id.login_passwordET);
        loginBTN = (Button)findViewById(R.id.loginBTN);
        loginBTN.setOnClickListener(this);
        signupTV = (TextView)findViewById(R.id.signupTV);
        signupTV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.loginBTN)
        {
            login();
        }
        else if(v == signupTV)
        {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        }

    }

    private void login() {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        if(TextUtils.isEmpty(username))
        {
            usernameET.setError(getString(R.string.empty_username_error_message));
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            passwordET.setError(getString(R.string.empty_password_error_message));
            return;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username",username);
        params.put("password",password);


        VolleyStringRequest.request(LoginActivity.this, AppConstants.loginUrl,params, loginResp);
//        startActivity(new Intent(LoginActivity.this,HomeActivity.class));


    }
    private VolleyStringRequest.OnStringResponse loginResp = new VolleyStringRequest.OnStringResponse() {

        @Override
        public void responseReceived(String response) {
            Utils.showSuccessToast(LoginActivity.this,"you are successfully logged in!!");
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();

        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };
}
