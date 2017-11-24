package com.example.bittukumar.treatwell.DoctorListView;

/**
 * Created by UTKARSH KUMAR on 11/23/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Doctor {
    public String doc_fname;
    public String doc_mname;
    public String doc_lname;
    public String hosp_name;
    public String doc_id;
    public String hosp_id;
    public String hosp_rating;
    public String doc_rating;
    public String fees;
    public String hosp_address;

    public Doctor(String doc_id, String doc_fname, String doc_mname, String doc_lname, String doc_ranking,  String hosp_id, String hosp_name, String hosp_rating, String fees, String hosp_address) {
        this.doc_fname = doc_fname;
        this.doc_mname = doc_mname;
        this.doc_lname = doc_lname;
        this.hosp_name = hosp_name;
        this.doc_id = doc_id;
        this.hosp_id = hosp_id;
        this.hosp_rating = hosp_rating;
        this.doc_rating = doc_rating;
        this.fees = fees;
        this.hosp_address=hosp_address;
    }
}
