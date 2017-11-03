package com.dreamworld.craic.configuration;

/**
 * Created by faizan on 06/06/2017.
 */

import android.graphics.Bitmap;

public class Config {

    public static String[] names;
    public static String[] urls;
    //public static Bitmap[] bitmaps;
    public static int[] viewtype;
    public static int[] currentImage;
    public static String[] headImage;
    public static String[] date;
    public static int[] likes;
    public static String[] headTitel;



   // public static final String GET_URL = "http://faizandream21.000webhostapp.com/PhotoUploadWithText/getImage.php";
    public static final String GET_URL ="http://faizandream21.000webhostapp.com/PhotoUploadWithText/getviewType.php";
    public static final String UPDATELIKE_URL ="https://faizandream21.000webhostapp.com/PhotoUploadWithText/updatelike.php";
    public static final String UPDATEUNLIKE_URL ="https://faizandream21.000webhostapp.com/PhotoUploadWithText/updateunlike.php";
    public static final String SUGGESTION_URL ="https://faizandream21.000webhostapp.com/PhotoUploadWithText/suggestion.php";
    public static final String REGISTER_URL ="http://faizandream21.000webhostapp.com/PhotoUploadWithText/include/RegisterUser.php";
    public static final String LOGIN_URL ="http://faizandream21.000webhostapp.com/PhotoUploadWithText/include/Login.php";
    public static final String FORGOT_PASS_URL ="https://faizandream21.000webhostapp.com/PhotoUploadWithText/suggestion.php";


    public static final String TAG_IMAGE_URL = "url";
    public static final String TAG_IMAGE_NAME = "name";
    public static final String TAG_JSON_ARRAY="result";


    // shared prefrences keys
    public static final String SHARED_PREF_NAME = "prefrence_like";
    public static final boolean SHARED_PREF_LIKES = true;
    public static final String SHARED_PREF_IMAGE_ID = "myloginapp";



    public Config(int i){
        names = new String[i];
        urls = new String[i];
        //bitmaps = new Bitmap[i];
        viewtype = new int[i];
        currentImage = new int[i];
        headImage = new String[i];
        date = new  String[i] ;
        likes = new  int[i];
        headTitel = new String[i];
    }
}
