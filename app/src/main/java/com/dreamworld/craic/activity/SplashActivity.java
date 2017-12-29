package com.dreamworld.craic.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.dreamworld.craic.MainActivity;
import com.dreamworld.craic.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        moveToMain();
    }

    private void moveToMain() {
        Handler movetoMain =  new Handler();
        movetoMain.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent moveIntent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(moveIntent);
                finish();

            }
        },3000);


    }
}



//    View decorView = getWindow().getDecorView();
//    // Hide the status bar.
//    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//                // Remember that you should never show the action bar if the
//                // status bar is hidden, so hide that too if necessary.
//                ActionBar actionBar = getActionBar();
//                actionBar.hide();
//// moving to other activity
//