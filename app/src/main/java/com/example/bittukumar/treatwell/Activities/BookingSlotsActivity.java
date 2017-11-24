package com.example.bittukumar.treatwell.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.bittukumar.treatwell.Network.VolleyStringRequest;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.SlotsRecyclerView.CustomRVItemTouchListener;
import com.example.bittukumar.treatwell.SlotsRecyclerView.Data;
import com.example.bittukumar.treatwell.SlotsRecyclerView.RecyclerViewItemClickListener;
import com.example.bittukumar.treatwell.SlotsRecyclerView.Recycler_View_Adapter;
import com.example.bittukumar.treatwell.Utils.AppConstants;
import com.example.bittukumar.treatwell.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BookingSlotsActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    private Recycler_View_Adapter adapter;
    List<Data> data;
    private TextView dateTV;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;
    private TextView docNameTV,docSpecTV,hospNameTV,hospAddrTV;

    private String docid,docName,docSpec,hospid,hospName,hospAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_slots);

        Intent intent = getIntent();
        docid = intent.getStringExtra("doc_id");
        docName = intent.getStringExtra("doc_fname")+" "+intent.getStringExtra("doc_mname")+" "+intent.getStringExtra("doc_lname");
        docSpec = intent.getStringExtra("doctor_spec");
        hospid = intent.getStringExtra("hosp_id");
        hospName = intent.getStringExtra("hosp_name");
        hospAddr = intent.getStringExtra("hosp_address");

        docNameTV = (TextView)findViewById(R.id.doc_nameTV);
        docSpecTV = (TextView)findViewById(R.id.doc_specTV);
        hospNameTV = (TextView)findViewById(R.id.hosp_nameTV);
        hospAddrTV = (TextView)findViewById(R.id.hosp_addrTV);
        docNameTV.setText(docName);
        docSpecTV.setText(docSpec);
        hospNameTV.setText(hospName);
        hospAddrTV.setText(hospAddr);


        calendar = Calendar.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.slots_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookingSlotsActivity.this));
        dateTV = (TextView)findViewById(R.id.dateTV);
        dateTV.setOnClickListener(this);
        init();
    }

    @Override
    public void onClick(View v) {
        if (v == dateTV)
        {
            new DatePickerDialog(BookingSlotsActivity.this, date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }

    }

    private void init() {
        data = new ArrayList<>();
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

        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(BookingSlotsActivity.this, recyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                confirmDialog(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        updateLabel();
    }

    private void getData() {
        String date = dateTV.getText().toString();
        HashMap<String,String> params = new HashMap<>();
        params.put("docid",docid);
        params.put("hospid",hospid);
        params.put("date",date);

        VolleyStringRequest.request(BookingSlotsActivity.this, AppConstants.slotsUrl,params,slotsResp);


    }
    VolleyStringRequest.OnStringResponse slotsResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            data =  setSlotsData(response);
            adapter = new Recycler_View_Adapter(data,BookingSlotsActivity.this);
            recyclerView.setAdapter(adapter);


        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };

    private List<Data> setSlotsData(String response) {
        List<Data> localdata = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            String startTime,endTime,fee;
            for (int i=0;i<dataArray.length();i++)
            {
                JSONObject dataObject = dataArray.getJSONObject(i);
                startTime = dataObject.getString("StTime");
                endTime = dataObject.getString("EndTime");
                fee = dataObject.getString("Fees");
                localdata.add(new Data(startTime,endTime,fee));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return localdata;
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTV.setText(sdf.format(calendar.getTime()));
        getData();
    }

    private void confirmDialog(final int position)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookingSlotsActivity.this);
        LayoutInflater inflater = BookingSlotsActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.cofirm_slot_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        TextView doc_nameTV = (TextView)dialogView.findViewById(R.id.doc_name_confirmTV);
        TextView doc_specTV = (TextView)dialogView.findViewById(R.id.doc_spec_confirmTV);
        TextView hosp_nameTV = (TextView)dialogView.findViewById(R.id.hosp_name_confirmTV);
        TextView hosp_addrTV = (TextView)dialogView.findViewById(R.id.hosp_addr_confirmTV);
        TextView date_TV = (TextView)dialogView.findViewById(R.id.date_confirmTV);
        TextView starttimeTV = (TextView)dialogView.findViewById(R.id.startTime_confirmTV);
        TextView endtimeTV = (TextView)dialogView.findViewById(R.id.endTime_confirmTV);
        TextView feeTV = (TextView)dialogView.findViewById(R.id.real_fee_confirmTV);

        doc_nameTV.setText(docName);
        doc_specTV.setText(docSpec);
        hosp_nameTV.setText(hospName);
        hosp_addrTV.setText(hospAddr);
        String cdate = dateTV.getText().toString();
        date_TV.setText(cdate);
        starttimeTV.setText(data.get(position).startTime);
        endtimeTV.setText(data.get(position).endTime);
        feeTV.setText(data.get(position).fee);

        dialogBuilder.setPositiveButton("pay & confirm", null);

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                //pass

            }
        });
        final AlertDialog b = dialogBuilder.create();
        b.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        bookAppointment(position);
                        b.dismiss();

                    }
                });
            }
        });

        b.show();
    }

    private void bookAppointment(int position) {
        String cdate = dateTV.getText().toString();
        HashMap<String,String > params = new HashMap<>();
        params.put("hospid",hospid);
        params.put("docid",docid);
        params.put("date",cdate);
        params.put("start_time",data.get(position).startTime);
        params.put("fees",data.get(position).fee);

        VolleyStringRequest.request(BookingSlotsActivity.this,AppConstants.bookAppointmentUrl,params,bookAppointmnetResp);
    }

    VolleyStringRequest.OnStringResponse bookAppointmnetResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            Utils.showSuccessToast(BookingSlotsActivity.this,"Your Appointment has been successfully booked");
            getData();

        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };

}
