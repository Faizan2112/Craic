package com.dreamworld.craic.configuration;

import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
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
    // get shre vlue

    // shred prefrence key

    public static final String LoginSharePreferenceName = "loginSharePreferenceName";
    public static final String LoginSharePreferenceEmail = "loginSharePreferenceEmail";
    public static final String LoginSharePreferenceLogValue = "loginSharePreferenceLogValue";
    public static final String LoginSharePreferenceverified = "loginSharePreferenceverified";
    public static final String LoginSharePreferencefriendlist = "loginSharePreferencefriendlist";
    public static final String LoginSharePreferencelikelist = "loginSharePreferencelikelist";
    public static final String LoginSharePreferenceposts = "loginSharePreferenceposts";
    public static final String LoginSharePreferenceposts_icon = "loginSharePreferencepostsicon";
    public static final String LoginSharePreferenceProfilePic = "loginSharePreferenceprofilepic";
    public static final String LoginSharePreferenceFirstname = "loginSharePreferencefirstname";
    public static final String LoginSharePreferenceLastname = "loginSharePreferencelastname";
    // keys
    public static final String LoginSharePreferenceemailkey = "email";
    //   public static final String LoginSharePreferenceLogValue = "verified";
    public static final String keyverified = "verified";
    public static final String keyfriendlist = "friendlist";
    public static final String keylikelist = "likelist";
    public static final String keyposts = "posts";
    public static final String keypost_icon = "post_icon";

    public static final String keystatus = "status";
    public static final String keyresult = "success";
    public static final String keyresultData = "result";
    public static final String keyprofilepic = "profile_picture";
    public static final String keyfirstname = "firstname";
    ;
    public static final String keylastname = "lastname";
    ;
    //
//urls
    public static final String GET_All_Post_URL = "https://faizandream21.000webhostapp.com/PhotoUploadWithText/include/fetchallpostdata.php";
    public static final String Post_data = "https://faizandream21.000webhostapp.com/PhotoUploadWithText/include/postdata.php";
    public static final String GET_All_COMMENT = "https://faizandream21.000webhostapp.com/PhotoUploadWithText/include/CommentData.php";
    public static final String POST_COMMENT = "https://faizandream21.000webhostapp.com/PhotoUploadWithText/include/PostComment.php";
// postsdata

    public static final String posts_posts = "posts";
    public static final String posts_post_icon = "post_icon";
    public static final String posts_date = "date";
    public static final String posts_titel = "titel";
    public static final String posts_post_id = "post_id";
    public static final String posts_subtitle = "subtitle";
    public static final String posts_mainimageurl = "mainimageurl";
    public static final String posts_articlesummary = "articlesummary";
    public static final String posts_articledescription = "articledescription";
    public static final String posts_articleconclution = "articleconclution";
    public static final String posts_likes = "likes";
    public static final String posts_comments = "comments";
    public static final String posts_viewtype = "viewtype";
    public static final String posts_privacy = "privacy";
    public static final String posts_profilepic = "profile_picture";
    public static final String posts_firstname = "firstname";
    ;
    public static final String posts_lastname = "lastname";

    //boolen vraible

    public static boolean isNetConnected = false;


    // CommentConstant
    public static final String comment_pic = "commentuserpic";
    public static final String comment_id = "commentid";
    public static final String comment_username = "commentusername";
    public static final String comment_usercomment = "comment";
    public static final String comment_date = "commentdate";


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