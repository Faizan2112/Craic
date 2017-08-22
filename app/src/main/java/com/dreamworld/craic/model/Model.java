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
