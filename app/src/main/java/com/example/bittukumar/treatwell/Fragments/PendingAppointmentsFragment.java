package com.example.bittukumar.treatwell.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bittukumar.treatwell.FinishedAppointmentsRecyclerView.FData;
import com.example.bittukumar.treatwell.FinishedAppointmentsRecyclerView.FRecycler_View_Adapter;
import com.example.bittukumar.treatwell.Network.VolleyStringRequest;
import com.example.bittukumar.treatwell.PendingAppointmentsRecyclerView.PData;
import com.example.bittukumar.treatwell.PendingAppointmentsRecyclerView.PRecycler_View_Adapter;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bittu Kumar on 24-11-2017.
 */

public class PendingAppointmentsFragment extends Fragment {
    private RecyclerView recyclerView;
    private PRecycler_View_Adapter adapter;
    List<PData> data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending_apointments, container, false);



    }

    @Override
    public void onStart() {
        super.onStart();
        data = new ArrayList<>();
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.pendingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAppointmentData();

    }

    private void getAppointmentData() {
        HashMap<String,String> params = new HashMap<>();

        VolleyStringRequest.request(getActivity(), AppConstants.pendingAppUrl,params,getAppDataResp);
    }

    VolleyStringRequest.OnStringResponse getAppDataResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            data = setData(response);
            adapter =new PRecycler_View_Adapter(data,getActivity());
            recyclerView.setAdapter(adapter);

        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };

    private List<PData> setData(String response) {
        List<PData> localData = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            String doc_name,doc_spec,hosp_name,hosp_addr,date,starttime,endtime,fee;
            for (int i = 0;i<jsonArray.length();i++)
            {
                JSONObject dataObject = jsonArray.getJSONObject(i);
                doc_name = dataObject.getString("DoctorName");
                doc_spec = dataObject.getString("DoctorSpeciality");
                hosp_name = dataObject.getString("HospitalName");
                hosp_addr = dataObject.getString("HospitalAddress");
                fee = dataObject.getString("Fees");
                date = dataObject.getString("Date");
                starttime = dataObject.getString("StartTime");
                endtime = dataObject.getString("EndTime");
                localData.add(new PData(doc_name,doc_spec,hosp_name,hosp_addr,fee,date,starttime,endtime));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return localData;
    }
}
