package com.dreamworld.craic.model;

/**
 * Created by faizan on 31/07/2017.
 */

public class Model {
    public static final int TEXT_TYPE = 0;
    public static final int IMAGE_TYPE = 1;
    public static final int AUDIO_TYPE = 2;

    public int type;
    public int data;
    public String url;
    public String name;
    public String text;
    private int imageId ;
    private int likes ;
    private String headImage ;
    private String headTitel;
    private String date;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }



    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }



    public String getHeadTitel() {
        return headTitel;
    }

    public void setHeadTitel(String headTitel) {
        this.headTitel = headTitel;
    }



    public Model() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
