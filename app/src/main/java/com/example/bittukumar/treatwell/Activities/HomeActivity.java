package com.example.bittukumar.treatwell.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.bittukumar.treatwell.Fragments.ProfileFragment;
import com.example.bittukumar.treatwell.Fragments.SearchFragment;
import com.example.bittukumar.treatwell.R;
import com.example.bittukumar.treatwell.Utils.Utils;

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

            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
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
        if (id == R.id.action_settings) {

            return true;
        }
        if(id == R.id.action_logout)
        {
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


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
