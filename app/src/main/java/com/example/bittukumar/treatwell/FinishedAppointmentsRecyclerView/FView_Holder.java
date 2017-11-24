package com.example.bittukumar.treatwell.FinishedAppointmentsRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bittukumar.treatwell.R;


//The adapters View Holder
public class FView_Holder extends RecyclerView.ViewHolder {

    TextView doc_nameTV;
    TextView doc_specTV;
    TextView hosp_nameTV;
    TextView hosp_addrTV;
    TextView date_TV;
    TextView starttimeTV;
    TextView endtimeTV;
    TextView feeTV;

    FView_Holder(View itemView) {
        super(itemView);

        doc_nameTV = (TextView)itemView.findViewById(R.id.doc_name_finishedTV);
        doc_specTV = (TextView)itemView.findViewById(R.id.doc_spec_finishedTV);
        hosp_nameTV = (TextView)itemView.findViewById(R.id.hosp_name_finishedTV);
        hosp_addrTV = (TextView)itemView.findViewById(R.id.hosp_addr_finishedTV);
        date_TV = (TextView)itemView.findViewById(R.id.date_finishedTV);
        starttimeTV = (TextView)itemView.findViewById(R.id.startTime_finishedTV);
        endtimeTV = (TextView)itemView.findViewById(R.id.endTime_finishedTV);
        feeTV = (TextView)itemView.findViewById(R.id.real_fee_finishedTV);

    }

}
