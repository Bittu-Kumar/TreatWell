package com.example.bittukumar.treatwell.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bittukumar.treatwell.Network.VolleyStringRequest;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.AppConstants;
import com.example.bittukumar.treatwell.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Bittu Kumar on 06-11-2017.
 */

public class ProfileFragment  extends Fragment implements View.OnClickListener{

    private RadioGroup radioGroup;
    private RadioButton maleRB;
    private RadioButton femaleRB;
    private RadioButton otherRB;
    private EditText nameET;
    private TextView balanceTV;
    private EditText emailET;
    private EditText phoneET;
    private EditText b_grpET;
    private TextView dobTV;
    private ImageView editProfileIV;
    private Button cancel;
    private Button save;
    private String profileDetails;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        getProfileDetails();
    }



    private void init() {
        radioGroup = (RadioGroup)getActivity().findViewById(R.id.profile_genderRG);
        maleRB = (RadioButton)getActivity().findViewById(R.id.profilemaleRB);
        femaleRB = (RadioButton)getActivity().findViewById(R.id.profilefemaleRB);
        otherRB = (RadioButton)getActivity().findViewById(R.id.profileothersRB);
        nameET = (EditText)getActivity().findViewById(R.id.nameET);
        balanceTV = (TextView) getActivity().findViewById(R.id.realbalanceTV);
        emailET = (EditText)getActivity().findViewById(R.id.emailET);
        phoneET = (EditText)getActivity().findViewById(R.id.phoneET);
        b_grpET = (EditText)getActivity().findViewById(R.id.b_grpET);
        dobTV = (TextView)getActivity().findViewById(R.id.real_dobTV);
        editProfileIV = (ImageView)getActivity().findViewById(R.id.edit_profileIV);
        editProfileIV.setOnClickListener(this);
        cancel = (Button)getActivity().findViewById(R.id.cancelbtnEditProfile);
        save = (Button)getActivity().findViewById(R.id.savebtnEditProfile);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        if(v ==editProfileIV)
        {
            nameET.setFocusableInTouchMode(true);
            emailET.setFocusableInTouchMode(true);
            phoneET.setFocusableInTouchMode(true);
            nameET.setFocusable(true);
            emailET.setFocusable(true);
            phoneET.setFocusable(true);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);

        }
        if (v == save)
        {
            editProfile();
        }
        if (v == cancel)
        {
            setProfile(profileDetails);
            save.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            nameET.setFocusable(false);
            emailET.setFocusable(false);
            phoneET.setFocusable(false);

        }

    }



    private void getProfileDetails() {
        HashMap<String,String> params = new HashMap<>();

        VolleyStringRequest.request(getActivity(), AppConstants.profileUrl,params,profileResp);
    }

    private VolleyStringRequest.OnStringResponse profileResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            profileDetails = response;
            setProfile(response);

        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };

    private void setProfile(String response) {
        try {
            JSONObject responseObject = new JSONObject(response);
            JSONObject jsonObject = responseObject.getJSONObject("data");
            String name = jsonObject.getString("first_name")+" " + jsonObject.getString("middle_name")+" "+ jsonObject.getString("last_name");
            nameET.setText(name);
//            balanceTV.setText(jsonObject.getString("balance"));
            emailET.setText(jsonObject.getString("email"));
            phoneET.setText(jsonObject.getString("phone"));
            b_grpET.setText(jsonObject.getString("b_grp"));
            dobTV.setText(jsonObject.getString("dob"));
            balanceTV.setText(jsonObject.getString("balance"));
            String gender = jsonObject.getString("gender");
            if(gender.equals("0"))
            {
                maleRB.setChecked(true);
                maleRB.setEnabled(true);
            }
            else if (gender.equals("1"))
            {
                femaleRB.setChecked(true);
                femaleRB.setEnabled(true);
            }
            else if(gender.equals("2"))
            {
                otherRB.setChecked(true);
                otherRB.setEnabled(true);
            }
            else
            {
                otherRB.setEnabled(true);
                otherRB.setChecked(true);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void editProfile() {
        String name, first,middle,last,phone,email;
        first = middle = last = "";
        name = nameET.getText().toString();
        phone = phoneET.getText().toString();
        email = emailET.getText().toString();
        String[] splitName = name.trim().split("\\s+");
        if (TextUtils.isEmpty(name))
        {
            nameET.requestFocus();
            nameET.setError(getString(R.string.empty_name_error));
            return;

        }

        if (TextUtils.isEmpty(email))
        {
            emailET.setError(getString(R.string.empty_email_error));
            return;
        }
        if(TextUtils.isEmpty(phone))
        {
            phoneET.setError(getString(R.string.empty_phone_error));
            return;
        }
        if (splitName.length>=1)
        {
            first = splitName[0];
        }
        if (splitName.length>=2)
        {
            middle = splitName[1];
        }
        if (splitName.length>=3)
        {
            last = splitName[2];
        }

        HashMap<String,String> params = new HashMap<>();
        params.put("first_name",first);
        params.put("middle_name",middle);
        params.put("last_name",last);
        params.put("phone",phone);
        params.put("email",email);
        VolleyStringRequest.request(getActivity(),AppConstants.editProfileUrl,params,editProfileResp);

    }
    VolleyStringRequest.OnStringResponse editProfileResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {

            save.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            nameET.setFocusable(false);
            emailET.setFocusable(false);
            phoneET.setFocusable(false);
            Utils.showSuccessToast(getActivity(),"Profile changes saved succeessfully");
            getProfileDetails();

        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };
}
