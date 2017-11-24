package com.example.bittukumar.treatwell.PendingAppointmentsRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bittukumar.treatwell.R;


//The adapters View Holder
public class PView_Holder extends RecyclerView.ViewHolder {

    TextView doc_nameTV;
    TextView doc_specTV;
    TextView hosp_nameTV;
    TextView hosp_addrTV;
    TextView date_TV;
    TextView starttimeTV;
    TextView endtimeTV;
    TextView feeTV;

    PView_Holder(View itemView) {
        super(itemView);

        doc_nameTV = (TextView)itemView.findViewById(R.id.doc_name_pendingTV);
        doc_specTV = (TextView)itemView.findViewById(R.id.doc_spec_pendingTV);
        hosp_nameTV = (TextView)itemView.findViewById(R.id.hosp_name_pendingTV);
        hosp_addrTV = (TextView)itemView.findViewById(R.id.hosp_addr_pendingTV);
        date_TV = (TextView)itemView.findViewById(R.id.date_pendingTV);
        starttimeTV = (TextView)itemView.findViewById(R.id.startTime_pendingTV);
        endtimeTV = (TextView)itemView.findViewById(R.id.endTime_pendingTV);
        feeTV = (TextView)itemView.findViewById(R.id.real_fee_pendingTV);

    }

}
