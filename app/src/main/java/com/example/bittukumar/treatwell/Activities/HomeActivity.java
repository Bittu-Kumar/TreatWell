package com.example.bittukumar.treatwell.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bittukumar.treatwell.Fragments.FinishedApointmentsFragment;
import com.example.bittukumar.treatwell.Fragments.PendingAppointmentsFragment;
import com.example.bittukumar.treatwell.Fragments.ProfileFragment;
import com.example.bittukumar.treatwell.Fragments.SearchFragment;
import com.example.bittukumar.treatwell.Network.VolleyStringRequest;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.AppConstants;
import com.example.bittukumar.treatwell.Utils.Utils;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager manager;
    private ImageView homeIV;
    private ImageView searchIV;
    private ImageView historyIV;
    private ImageView profileIV;
    private ImageView logoutIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        manager = getSupportFragmentManager();
        changeFragment(new ProfileFragment());
        init();

    }

    private void init() {
        homeIV = (ImageView)findViewById(R.id.img_home);
        searchIV = (ImageView)findViewById(R.id.img_search);
        historyIV = (ImageView)findViewById(R.id.img_history);
        profileIV = (ImageView)findViewById(R.id.img_profile);
        logoutIV = (ImageView)findViewById(R.id.img_logout);
        homeIV.setOnClickListener(this);
        searchIV.setOnClickListener(this);
        historyIV.setOnClickListener(this);
        profileIV.setOnClickListener(this);
        logoutIV.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == profileIV)
        {
            changeFragment(new ProfileFragment());
        }
        else if (v == logoutIV)
        {
            logout();
        }
        else if(v == searchIV)
        {
            changeFragment(new SearchFragment());
        }
        else if (v == homeIV)
        {
            changeFragment(new ProfileFragment());

        }
        else if (v ==historyIV)
        {
            changeFragment(new FinishedApointmentsFragment());

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_money) {

            addMoneyDialog();
            return true;
        }
        if(id == R.id.action_logout)
        {
            logout();
            return true;
        }
        if (id ==R.id.action_pending)
        {
            changeFragment(new PendingAppointmentsFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void logout() {
        HashMap<String,String> params = new HashMap<>();
        VolleyStringRequest.request(HomeActivity.this, AppConstants.logoutURL,params,logoutResp);
    }

    VolleyStringRequest.OnStringResponse logoutResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            Utils.showSuccessToast(HomeActivity.this,"You are successfully logged out");
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };

    private void addMoneyDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_money_dialog_layout, null);
        dialogBuilder.setView(dialogView);

        final TextInputEditText add_moneyET = (TextInputEditText)dialogView.findViewById(R.id.add_moneyET);

        dialogBuilder.setTitle(R.string.add_money);
        dialogBuilder.setPositiveButton("Add", null);

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
                        String amount = add_moneyET.getText().toString();
                        if (TextUtils.isEmpty(amount))
                        {
                            add_moneyET.requestFocus();
                            add_moneyET.setError(getString(R.string.empty_add_money_error));
                        }
                        else
                        {
                            addMoney(amount);
                            b.dismiss();
                        }


                    }
                });
            }
        });

        b.show();

    }

    private void addMoney(String amount) {
        HashMap<String ,String > params = new HashMap<>();
        params.put("balance",amount);
        VolleyStringRequest.request(HomeActivity.this,AppConstants.addMoneyUrl,params,addMoneyResp);

    }
    VolleyStringRequest.OnStringResponse addMoneyResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            Utils.showSuccessToast(HomeActivity.this,"Balance added Successfully!!");
            changeFragment(new ProfileFragment());

        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };


    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment tmpFragment = manager.findFragmentById(R.id.content_frame);
        if (tmpFragment != null)
            transaction.replace(R.id.content_frame, fragment);
        else
            transaction.add(R.id.content_frame, fragment);
        transaction.commitAllowingStateLoss();
    }
    public void changeFragmentWithBundle(Bundle args,Fragment fragment)
    {
        fragment.setArguments(args);
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment tmpFragment = manager.findFragmentById(R.id.content_frame);
        if (tmpFragment != null)
            transaction.replace(R.id.content_frame, fragment);
        else
            transaction.add(R.id.content_frame, fragment);
        transaction.commitAllowingStateLoss();
    }

}
