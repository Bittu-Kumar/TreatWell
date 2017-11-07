package com.example.bittukumar.treatwell.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bittukumar.treatwell.R;

/**
 * Created by Bittu Kumar on 06-11-2017.
 */

public class ProfileFragment  extends Fragment{

    private RadioGroup radioGroup;
    private RadioButton maleRB;
    private RadioButton femaleRB;
    private RadioButton otherRB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        radioGroup = (RadioGroup)getActivity().findViewById(R.id.profile_genderRG);
        maleRB = (RadioButton)getActivity().findViewById(R.id.profilemaleRB);
        femaleRB = (RadioButton)getActivity().findViewById(R.id.profilefemaleRB);
        otherRB = (RadioButton)getActivity().findViewById(R.id.profileothersRB);
        maleRB.setChecked(true);
    }
}
