package com.example.bittukumar.treatwell.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
    String hospId;
    String DoctorId;
    private TextView dateTV;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_slots);

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
        params.put("docid","rossgeller");
        params.put("hospid","KEM");
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

    private void confirmDialog(int position)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookingSlotsActivity.this);
        LayoutInflater inflater = BookingSlotsActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.cofirm_slot_dialog_layout, null);
        dialogBuilder.setView(dialogView);

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

                    }
                });
            }
        });

        b.show();
    }

}
