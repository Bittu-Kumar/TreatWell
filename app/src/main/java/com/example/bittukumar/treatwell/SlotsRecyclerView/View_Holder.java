package com.example.bittukumar.treatwell.SlotsRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bittukumar.treatwell.R;


//The adapters View Holder
public class View_Holder extends RecyclerView.ViewHolder {

    TextView startTimeTV;
    TextView endTimeTV;
    TextView feeTV;

    View_Holder(View itemView) {
        super(itemView);
        startTimeTV = (TextView)itemView.findViewById(R.id.starttimeTV);
        endTimeTV = (TextView)itemView.findViewById(R.id.endTimeTV);
        feeTV = (TextView)itemView.findViewById(R.id.feeTV);

    }

}
