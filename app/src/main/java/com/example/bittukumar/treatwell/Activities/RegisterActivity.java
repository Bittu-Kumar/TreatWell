package com.example.bittukumar.treatwell.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bittukumar.treatwell.Network.VolleyStringRequest;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.AppConstants;
import com.example.bittukumar.treatwell.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameET;
    private EditText passwordET;
    private EditText firstNameET;
    private EditText middleNameET;
    private EditText lastNmaeET;
    private EditText emailET;
    private EditText dateofbirthET;
    private EditText bloodgroupET;
    private EditText phoneET;
    private Button registerBTN;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;
    private RadioGroup sexRadioGroup;
    private RadioButton selectedRadioButton;
    private Boolean userValid;
    private ImageView datepickerIV;

    // TODO: 06-11-2017 gender 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        calendar = Calendar.getInstance();
        usernameET = (EditText)findViewById(R.id.register_usernameET);
        passwordET = (EditText)findViewById(R.id.register_passwordET);
        firstNameET = (EditText)findViewById(R.id.register_firstnameET);
        middleNameET = (EditText)findViewById(R.id.rgister_middlenameET);
        lastNmaeET = (EditText)findViewById(R.id.register_lastnameET);
        emailET = (EditText)findViewById(R.id.register_emailET);
        dateofbirthET = (EditText)findViewById(R.id.register_dobET);
        dateofbirthET.setOnClickListener(this);
        bloodgroupET = (EditText)findViewById(R.id.register_bloodgroupET);
        phoneET = (EditText)findViewById(R.id.register_phoneET);
        registerBTN = (Button)findViewById(R.id.register_button);
        registerBTN.setOnClickListener(this);
        sexRadioGroup = (RadioGroup)findViewById(R.id.register_genderRG);
        datepickerIV = (ImageView)findViewById(R.id.datepickerIV);
        datepickerIV.setOnClickListener(this);
        init();
    }

    private void init() {
        userValid = false;
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

    }



    @Override
    public void onClick(View v) {
        if(v == registerBTN)
        {
            register();
        }
//        if(v == dateofbirthET)
//        {
//            new DatePickerDialog(RegisterActivity.this, date, calendar
//                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
//                    calendar.get(Calendar.DAY_OF_MONTH)).show();
//        }
        if (v == datepickerIV)
        {
            new DatePickerDialog(RegisterActivity.this, date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();

        }

    }

    private void checkUser() {
        String username = usernameET.getText().toString();
        if (TextUtils.isEmpty(username))
        {
            return;
        }
        HashMap<String ,String > params = new HashMap<>();
        params.put("username",username);
        VolleyStringRequest.request(RegisterActivity.this,AppConstants.checkUserUrl,params,checkUserResp);
    }

    VolleyStringRequest.OnStringResponse checkUserResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            userValid = true;


        }

        @Override
        public void errorReceived(int code, String message) {
            userValid = false;
            usernameET.requestFocus();
            usernameET.setError("This username already exists!");

        }
    };

    private void register() {

        String username,password,firstname,middlename,lastname,email,dob,bloodgroup,phone;
        username = usernameET.getText().toString();
        password = passwordET.getText().toString();
        firstname  = firstNameET.getText().toString();
        middlename = middleNameET.getText().toString();
        lastname = lastNmaeET.getText().toString();
        email = emailET.getText().toString();
        dob = dateofbirthET.getText().toString();
        bloodgroup = bloodgroupET.getText().toString();
        phone = phoneET.getText().toString();
        if(!valid_input(username,password,firstname,middlename,lastname,email,dob,bloodgroup,phone))
        {
            return;
        }
        selectedRadioButton = (RadioButton)findViewById(sexRadioGroup.getCheckedRadioButtonId());
        int gender;
        if(selectedRadioButton.getText().toString().equals("Male"))gender = 0;
        else if (selectedRadioButton.getText().toString().equals("Female"))gender = 1;
        else gender = 2;
        HashMap<String,String> params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        params.put("first_name",firstname);
        params.put("middle_name",middlename);
        params.put("last_name",lastname);
        params.put("email",email);
        params.put("phone",phone);
        params.put("b_grp",bloodgroup);
        params.put("dob",dob);
        params.put("gender",Integer.toString(gender));
        params.put("user_type","patient");
        VolleyStringRequest.request(RegisterActivity.this, AppConstants.registerUrl,params,registerResp);

    }
    private VolleyStringRequest.OnStringResponse registerResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            Utils.showSuccessToast(RegisterActivity.this,"Congratulations you are Successfully registered!!!");
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };

    private boolean valid_input(String username, String password, String firstname, String middlename, String lastname, String email, String dob, String bloodgroup, String phone) {

        boolean isvalid = true;
        if(TextUtils.isEmpty(username))
        {
            usernameET.setError(getString(R.string.empty_username_error_message));
            isvalid = false;

        }
        if(TextUtils.isEmpty(password))
        {
            passwordET.setError(getString(R.string.empty_password_error_message));
            isvalid = false;
        }
        if(TextUtils.isEmpty(firstname))
        {
            firstNameET.setError(getString(R.string.empty_first_name_error_message));
            isvalid = false;
        }
        if(TextUtils.isEmpty(lastname))
        {
            lastNmaeET.setError(getString(R.string.empty_lname_error));
            isvalid = false;

        }
        if(TextUtils.isEmpty(email))
        {
            emailET.setError(getString(R.string.empty_email_error));
            isvalid = false;
        }
        if(TextUtils.isEmpty(dob))
        {
            dateofbirthET.setError(getString(R.string.empty_dob_error));
            isvalid = false;
        }
        if(TextUtils.isEmpty(bloodgroup))
        {
            bloodgroupET.setError(getString(R.string.empty_bg_error));
            isvalid = false;
        }
        if(TextUtils.isEmpty(phone))
        {
            phoneET.setError(getString(R.string.empty_phone_error));
            isvalid = false;

        }
        if (sexRadioGroup.getCheckedRadioButtonId()==-1)
        {
            isvalid = false;
        }
        checkUser();
        if (!userValid)
        {
            isvalid = false;
        }
       return isvalid;
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateofbirthET.setText(sdf.format(calendar.getTime()));
    }


}
