package com.dreamworld.craic.configuration;

/**
 * Created by faizan on 28/12/2017.
 */

public class CommentConfiguration {
    public static String[] userName;
    public static String[] userComment;
    //public static Bitmap[] bitmaps;
    public static String[] userCommentDate;
    public static String[] userProfilePic;








    public CommentConfiguration (int i){
        userName = new String[i];
        userComment = new String[i];
        //bitmaps = new Bitmap[i];
        userCommentDate= new String[i];
        userProfilePic= new String[i];


    }
}
