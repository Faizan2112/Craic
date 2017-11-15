package com.dreamworld.craic.configuration;

import android.content.BroadcastReceiver;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Created by faizan on 08/11/2017.
 */

public class Constants {
    // shred prefrence key

    public static final String LoginSharePreferenceName = "loginSharePreferenceName";
    public static final String LoginSharePreferenceEmail = "loginSharePreferenceEmail";
    public static final String LoginSharePreferenceLogValue = "loginSharePreferenceLogValue";
    public static final String LoginSharePreferenceverified = "loginSharePreferenceverified";
    public static final String LoginSharePreferencefriendlist = "loginSharePreferencefriendlist";
    public static final String LoginSharePreferencelikelist = "loginSharePreferencelikelist";
    public static final String LoginSharePreferenceposts = "loginSharePreferenceposts";

    // keys
    public static final String LoginSharePreferenceemailkey = "email";
 //   public static final String LoginSharePreferenceLogValue = "verified";
    public static final String keyverified = "verified";
    public static final String keyfriendlist = "friendlist";
    public static final String keylikelist = "likelist";
    public static final String keyposts = "posts";
    public static final String keystatus = "status";
    public static final String keyresult = "success";
//
//urls
public static final String GET_All_Post_URL ="https://faizandream21.000webhostapp.com/PhotoUploadWithText/include/fetchpostdata.php";


    //boolen vraible

    public static boolean isNetConnected = false;


}
//layouts
//
//    public BroadcastReceiver mBroadcastReceiver;
//    public RecyclerView mRecyclerView;
//
//    public SwipeRefreshLayout mSwipeRefreshLayout;
//    public RecyclerView.Adapter mRecyclerViewAdapter;
//    public BottomNavigationView bottomNavigationView;
//
//    // class
//    public Config mConfigFile;
//    public TextToSpeech mListenText;
//
//    public TabLayout tabLayout;
//    public FragmentManager mFragmentManager;
//    public FragmentTransaction mFragmentTransaction;