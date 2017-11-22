package com.example.bittukumar.treatwell.Fragments;

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
import android.widget.Spinner;
import android.widget.Toast;

//import com.example.bittukumar.treatwell.Activities.Doctor;
//import com.example.bittukumar.treatwell.Activities.DoctorsAdapter;
import com.example.bittukumar.treatwell.Activities.HomeActivity;
import com.example.bittukumar.treatwell.Network.VolleyStringRequest;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.AppConstants;
import com.example.bittukumar.treatwell.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bittu Kumar on 05-11-2017.
 */

public class SearchFragment extends Fragment {

    private String[] spinner_options;
    private String[] specification_options;
    private Spinner spinner;
    private AutoCompleteTextView specification;
    private String selected_spec="";
    private String selected_sort="";
    private Button searchbutton;
    private RecyclerView list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        searchbutton = (Button)getActivity().findViewById(R.id.searchbutton);
        spinner = (Spinner)getActivity().findViewById(R.id.spinner);
        specification = (AutoCompleteTextView)getActivity().findViewById(R.id.specification);
        spinner_options = getResources().getStringArray(R.array.SortByOptions);
        specification_options = getResources().getStringArray(R.array.SpecificationOptions);
        specification.setThreshold(0);

        ArrayAdapter<String> AdapterSortBy = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,spinner_options);
        spinner.setAdapter(AdapterSortBy);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                Toast.makeText(getActivity(), "You select "+spinner_options[index], Toast.LENGTH_LONG).show();
                selected_sort = spinner_options[index];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> AdapterSpecification = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,specification_options);
        specification.setAdapter(AdapterSpecification);
        specification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("utkarsh",specification.getText().toString());
                selected_spec =specification.getText().toString();
                Toast.makeText(getActivity(), "You select "+ selected_spec, Toast.LENGTH_LONG).show();

            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("doctype", selected_spec);
                params.put("sortby", selected_sort);

                VolleyStringRequest.request(getActivity(), AppConstants.searchUrl,params, loginResp);
            }
        });
    }
    private VolleyStringRequest.OnStringResponse loginResp = new VolleyStringRequest.OnStringResponse() {

        @Override
        public void responseReceived(String response) {
            Utils.showSuccessToast(getActivity(),"doctors recieved");
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
