package com.dreamworld.craic.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.dreamworld.craic.R;
import com.dreamworld.craic.configuration.Config;
import com.dreamworld.craic.configuration.Constants;
import com.dreamworld.craic.pagers.MainPager;
import com.dreamworld.craic.pagers.TabFragment;

public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    //This is our tablayout
   private TabLayout tabLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction ;
    BroadcastReceiver mBroadcastReceiver;
    private RecyclerView mRecyclerView;
    public static boolean isNetConnected = false;
    private Config mConfigFile;
    TextToSpeech mListenText;
    SwipeRefreshLayout mSwipeRefreshLayout;
//Constants c = new Constants();

    private RecyclerView.Adapter mRecyclerViewAdapter;
    BottomNavigationView bottomNavigationView;


    //This is our viewPager
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    initializeTab();

    }

    private void initializeTab() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.home_main_container,new TabFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu_dowload:
                        Intent i = new Intent(getApplicationContext(), DisplayDownloadActivity.class);
                        startActivity(i);
                        //  Toast.makeText(getApplicationContext(), "downlod", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.main_menu_suggestion:
                        Intent is = new Intent(getApplicationContext(), SuggestionActivity.class);
                        startActivity(is);

                        //   Toast.makeText(getApplicationContext(), "feed_btm_my_share", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.main_menu_upload:
                        Intent in = new Intent(getApplicationContext(), UploadActivity.class);
                        startActivity(in);

                        //      Toast.makeText(getApplicationContext(), "UploadActivity", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.main_menu_profile:
                        Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intentProfile);
                        break;
                }
                return false;
            }
        });
     }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
/*   tabLayout = (TabLayout) findViewById(R.id.home_tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite"));
       // tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.home_pager);

        //Creating our pager adapter
        MainPager adapter = new MainPager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
    */