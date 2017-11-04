package com.example.bittukumar.treatwell.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameET;
    private EditText passwordET;
    private Button loginBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        usernameET = (EditText)findViewById(R.id.login_usernameET);
        passwordET = (EditText)findViewById(R.id.login_passwordET);
        loginBTN = (Button)findViewById(R.id.loginBTN);
        loginBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.loginBTN)
        {
            login();
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
        Utils.showSuccessToast(LoginActivity.this,"you are successfully logged in hj j h j j hj ");


    }
}
