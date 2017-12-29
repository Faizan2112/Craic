package com.dreamworld.craic.configuration;

/**
 * Created by faizan on 15/11/2017.
 */

public class PostDataConfig {
    public static String[] posts;
    public static String[] post_icon;
    //public static Bitmap[] bitmaps;
    public static String[] date;
    public static String[] titel;
    public static String[] post_id;
    public static String[] subtitle;
    public static String[]   mainimageurl;
    public static String[] articlesummary;
    public static String[] articledescription;
    public static String[] articleconclution;
    public static int[] likes;
    public static int[] comments;
    public static int[] viewtype;








    public PostDataConfig (int i){
        posts = new String[i];
        post_icon = new String[i];
        //bitmaps = new Bitmap[i];
        date= new String[i];
        titel= new String[i];
        post_id= new String[i];
        subtitle= new  String[i] ;
        mainimageurl = new String[i];
        articlesummary = new String[i];
        articledescription = new String[i];
        articleconclution = new String[i];
        likes = new int[i];
        comments= new int[i];
        viewtype= new int[i];


    }
}
