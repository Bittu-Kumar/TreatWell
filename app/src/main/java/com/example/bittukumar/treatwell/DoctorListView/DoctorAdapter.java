package com.example.bittukumar.treatwell.DoctorListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.bittukumar.treatwell.R;

import java.util.ArrayList;

/**
 * Created by UTKARSH KUMAR on 11/23/2017.
 */

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    public DoctorAdapter(Context context, ArrayList<Doctor> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Doctor doctor = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_doc, parent, false);
        }
        // Lookup view for data population
        TextView docName = (TextView) convertView.findViewById(R.id.doc_name);
        TextView hosName = (TextView) convertView.findViewById(R.id.hos_name);
        // Populate the data into the template view using the data object
        docName.setText("Dr "+doctor.doc_fname+" "+doctor.doc_mname+" "+doctor.doc_lname);
        hosName.setText(doctor.hosp_name);
        // Return the completed view to render on screen
        return convertView;
    }
}