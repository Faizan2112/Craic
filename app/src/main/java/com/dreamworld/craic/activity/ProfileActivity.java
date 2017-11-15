package com.dreamworld.craic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dreamworld.craic.R;
import com.dreamworld.craic.configuration.Constants;

import static com.dreamworld.craic.activity.LoginActivity.savedeatail;
import static com.dreamworld.craic.activity.LoginActivity.loggedin;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile_menu_about:
                        Intent i = new Intent(getApplicationContext(), DisplayDownloadActivity.class);
                        startActivity(i);
                        //  Toast.makeText(getApplicationContext(), "downlod", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.profile_menu_logout:
                     clearSharedPreferences();
                        //   Toast.makeText(getApplicationContext(), "feed_btm_my_share", Toast.LENGTH_LONG).show();
                        break;

                }
                return false;  }
        });
    }

    private void clearSharedPreferences() {
        loggedin = false ;
        savedeatail = getSharedPreferences(Constants.LoginSharePreferenceName,MODE_PRIVATE);
        SharedPreferences.Editor editlogOot = savedeatail.edit();
        editlogOot.remove(Constants.LoginSharePreferenceEmail);
        editlogOot.putBoolean(Constants.LoginSharePreferenceLogValue,loggedin);
        editlogOot.apply();


        Intent logOut = new Intent (getApplicationContext(),LoginActivity.class);
        startActivity(logOut);
        finish();


    }
}
