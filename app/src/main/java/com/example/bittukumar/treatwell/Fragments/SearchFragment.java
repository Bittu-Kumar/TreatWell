package com.example.bittukumar.treatwell.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import javax.xml.transform.dom.DOMLocator;

/**
 * Created by Bittu Kumar on 05-11-2017.
 */

public class SearchFragment extends Fragment {

    private String[] spinner_options;
    private ArrayList<String> specification_options = new ArrayList<String>();
    ;
    private Spinner spinner;
    private AutoCompleteTextView specification;
    private String selected_spec = "";
    private String selected_sort = "";
    private ArrayList<Doctor> arrayOfDoctor = new ArrayList<Doctor>();
    private LocationManager locationManager;
    //private LocationListener locationListener;
    private Location location;
    double xcord = 0;
    double ycord = 0;
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
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        //ycord = location.getLongitude();
        //xcord = location.getLatitude();
        spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        specification = (AutoCompleteTextView) getActivity().findViewById(R.id.specification);
        spinner_options = getResources().getStringArray(R.array.SortByOptions);
        doctorlist = (ListView) getActivity().findViewById(R.id.doctorview);
        //specification_options = getResources().getStringArray(R.array.SpecificationOptions);
        specification.setThreshold(0);
        update_specification_options();
        selected_spec = specification.getText().toString();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        ArrayAdapter<String> AdapterSortBy = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinner_options);
        spinner.setAdapter(AdapterSortBy);
        selected_sort = spinner_options[0];

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                Toast.makeText(getActivity(), "You select " + spinner_options[index], Toast.LENGTH_LONG).show();
                selected_sort = spinner_options[index];
                //configure();
                getDoctor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        specification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("utkarsh", specification.getText().toString());
                selected_spec = specification.getText().toString();
                Toast.makeText(getActivity(), "You select " + selected_spec, Toast.LENGTH_LONG).show();
                //configure();
                getDoctor();
            }
        });
        doctorlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("item", "song");

                Intent intent = new Intent(getContext(), BookingSlotsActivity.class);
                intent.putExtra("doc_id", arrayOfDoctor.get(i).doc_id);
                intent.putExtra("doc_fname", arrayOfDoctor.get(i).doc_fname);
                intent.putExtra("doc_mname", arrayOfDoctor.get(i).doc_mname);
                intent.putExtra("doc_lname", arrayOfDoctor.get(i).doc_lname);
                intent.putExtra("doc_rating", arrayOfDoctor.get(i).doc_rating);
                intent.putExtra("hosp_id", arrayOfDoctor.get(i).hosp_id);
                intent.putExtra("hosp_name", arrayOfDoctor.get(i).hosp_name);
                intent.putExtra("hosp_rating", arrayOfDoctor.get(i).hosp_rating);
                intent.putExtra("fees", arrayOfDoctor.get(i).fees);
                intent.putExtra("doctor_spec", selected_spec);
                intent.putExtra("hosp_address", arrayOfDoctor.get(i).hosp_address);
                getContext().startActivity(intent);

                //finish();
            }
        });


    }
    void getLocation() {
        try {
            locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, locationListener);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            xcord = location.getLatitude();
            ycord = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
            Log.e("not","working");
            showAlert();
        }
    };


    private void getDoctor() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("doctype", selected_spec);
        params.put("sortby", selected_sort);
        getLocation();
        params.put("xcord",Double.toString(xcord));
        params.put("ycord",Double.toString(ycord));

        Log.e("xcord", Double.toString(xcord));
        Log.e("ycord", Double.toString(ycord));
        VolleyStringRequest.request(getActivity(), AppConstants.searchUrl,params, docResp);
        return;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
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
                                        some.getString("fees"),
                                        some.getString("hosp_address"));
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
            specification_options = new ArrayList<String>();
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
            ArrayAdapter<String> AdapterSpecification = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, specification_options);
            specification.setAdapter(AdapterSpecification);

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
