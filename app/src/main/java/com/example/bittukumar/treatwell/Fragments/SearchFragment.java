package com.example.bittukumar.treatwell.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

//import com.example.bittukumar.treatwell.Activities.Doctor;
//import com.example.bittukumar.treatwell.Activities.DoctorsAdapter;
import com.example.bittukumar.treatwell.Activities.BookingSlotsActivity;
import com.example.bittukumar.treatwell.Activities.HomeActivity;
import com.example.bittukumar.treatwell.Activities.LoginActivity;
import com.example.bittukumar.treatwell.DoctorListView.Doctor;
import com.example.bittukumar.treatwell.DoctorListView.DoctorAdapter;
import com.example.bittukumar.treatwell.Network.VolleyStringRequest;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.AppConstants;
import com.example.bittukumar.treatwell.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bittu Kumar on 05-11-2017.
 */

public class SearchFragment extends Fragment {

    private String[] spinner_options;
    private ArrayList<String> specification_options = new ArrayList<String>();;
    private Spinner spinner;
    private AutoCompleteTextView specification;
    private String selected_spec="";
    private String selected_sort="";
    private ArrayList<Doctor> arrayOfDoctor = new ArrayList<Doctor>();

    // Button searchbutton;
    private ListView doctorlist;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //searchbutton = (Button)getActivity().findViewById(R.id.searchbutton);
        spinner = (Spinner)getActivity().findViewById(R.id.spinner);
        specification = (AutoCompleteTextView)getActivity().findViewById(R.id.specification);
        spinner_options = getResources().getStringArray(R.array.SortByOptions);
        doctorlist = (ListView) getActivity().findViewById(R.id.doctorview);
        //specification_options = getResources().getStringArray(R.array.SpecificationOptions);
        specification.setThreshold(0);
        update_specification_options();
        ArrayAdapter<String> AdapterSpecification = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,specification_options);
        specification.setAdapter(AdapterSpecification);
        selected_spec =specification.getText().toString();


        ArrayAdapter<String> AdapterSortBy = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,spinner_options);
        spinner.setAdapter(AdapterSortBy);
        selected_sort = spinner_options[0];

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                Toast.makeText(getActivity(), "You select "+spinner_options[index], Toast.LENGTH_LONG).show();
                selected_sort = spinner_options[index];
                getDoctor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        specification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("utkarsh",specification.getText().toString());
                selected_spec =specification.getText().toString();
                Toast.makeText(getActivity(), "You select "+ selected_spec, Toast.LENGTH_LONG).show();
                getDoctor();
            }
        });
        doctorlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("item","song");

                Intent intent = new Intent(getContext(), BookingSlotsActivity.class);
                intent.putExtra("doc_id",arrayOfDoctor.get(i).doc_id);
                intent.putExtra("doc_fname",arrayOfDoctor.get(i).doc_fname);
                intent.putExtra("doc_mname",arrayOfDoctor.get(i).doc_mname);
                intent.putExtra("doc_lname",arrayOfDoctor.get(i).doc_lname);
                intent.putExtra("doc_rating",arrayOfDoctor.get(i).doc_rating);
                intent.putExtra("hosp_id",arrayOfDoctor.get(i).hosp_id);
                intent.putExtra("hosp_name",arrayOfDoctor.get(i).hosp_name);
                intent.putExtra("hosp_rating", arrayOfDoctor.get(i).hosp_rating);
                intent.putExtra("fees", arrayOfDoctor.get(i).fees);
                getContext().startActivity(intent);

                //finish();
            }
        });
        /*searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("doctype", selected_spec);
                params.put("sortby", selected_sort);

                VolleyStringRequest.request(getActivity(), AppConstants.searchUrl,params, loginResp);
            }
        });*/
    }

    private void getDoctor() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("doctype", selected_spec);
        params.put("sortby", selected_sort);
        params.put("xcord",Double.toString(0));
        params.put("ycord",Double.toString(0));

        VolleyStringRequest.request(getActivity(), AppConstants.searchUrl,params, docResp);
        return;
    }


    private VolleyStringRequest.OnStringResponse docResp = new VolleyStringRequest.OnStringResponse() {

        @Override
        public void responseReceived(String response) {
            //Utils.showSuccessToast(getActivity(),"doclist recieved");
            //startActivity(new Intent(getActivity(),HomeActivity.class));
            DoctorAdapter arrayAdapter = new DoctorAdapter(getActivity(), arrayOfDoctor);
            arrayAdapter.clear();
            Log.e("doc response",response);
            JSONObject jObject  = null; // json
            JSONArray jArray = null;
            Doctor doctor = null;
            try {
                jObject = new JSONObject(response);
                jArray = jObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i =0 ; i<jArray.length() ; i++) {
                JSONObject some = null;
                try {
                    some = jArray.getJSONObject(i);
                    doctor = new Doctor(some.getString("doc_id"),
                                        some.getString("doc_fname"),
                                        some.getString("doc_mname"),
                                        some.getString("doc_lname"),
                                        some.getString("doc_rating"),
                                        some.getString("hosp_id"),
                                        some.getString("hosp_name"),
                                        some.getString("hosp_rating"),
                                        some.getString("fees"));
                    arrayAdapter.add(doctor);
                } catch (JSONException e) {
                    e.printStackTrace();
                }//arrayAdapter.add(newUser);
            }
            doctorlist.setAdapter(arrayAdapter);

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showSuccessToast(getActivity(),"no doc recieved");
        }
    };

    private void update_specification_options() {
        HashMap<String, String> params = new HashMap<String, String>();
        //params.put("doctype", selected_spec);
        //params.put("sortby", selected_sort);

        VolleyStringRequest.request(getActivity(), AppConstants.specUrl,params, specResp);
        return;
    }

    private VolleyStringRequest.OnStringResponse specResp = new VolleyStringRequest.OnStringResponse() {

        @Override
        public void responseReceived(String response) {
            //Utils.showSuccessToast(getActivity(),"spec recieved");
            //startActivity(new Intent(getActivity(),HomeActivity.class));
            /*ArrayList<Doctor> arrayOfUsers = new ArrayList<Doctor>();
            DoctorsAdapter arrayAdapter = new DoctorsAdapter(getActivity(), arrayOfUsers);
            */
            Log.e("spec response",response);
            JSONObject jObject  = null; // json
            JSONArray jArray = null;
            try {
                jObject = new JSONObject(response);
                jArray = jObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i =0 ; i<jArray.length() ; i++) {
                JSONObject some = null;
                try {
                    some = jArray.getJSONObject(i);
                    specification_options.add(some.getString("spec"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }//arrayAdapter.add(newUser);
            }
        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showSuccessToast(getActivity(),"no spec recieved");
        }
    };
    private VolleyStringRequest.OnStringResponse loginResp = new VolleyStringRequest.OnStringResponse() {

        @Override
        public void responseReceived(String response) {
            //Utils.showSuccessToast(getActivity(),"doctors recieved");
            //startActivity(new Intent(getActivity(),HomeActivity.class));
            /*ArrayList<Doctor> arrayOfUsers = new ArrayList<Doctor>();
            DoctorsAdapter arrayAdapter = new DoctorsAdapter(getActivity(), arrayOfUsers);

            for (int i =0 ; i<jArray.length() ; i++){
                JSONObject some = jArray.getJSONObject(i);
                if(some.getString("uid").compareTo(message) == 0){
                    continue;
                }
                User newUser = new User(jArray.getJSONObject(i).toString());
                arrayAdapter.add(newUser);
            }
            list.setAdapter(arrayAdapter);
            Log.e("Aasddd","asdasd");
            list.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {

                    return false; // ONLY if more data is actually being loaded; false otherwise.
                }
            });*/
        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showSuccessToast(getActivity(),"no doctors recieved");
        }
    };

}
