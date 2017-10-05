package com.dreamworld.craic.model;

import java.io.File;

/**
 * Created by faizan on 26/07/2017.
 */
public class CreateList {

    private String image_title;
    private String image_id;

    public File getFile_id() {
        return file_id;
    }

    public void setFile_id(File file_id) {
        this.file_id = file_id;
    }

    private File file_id ;

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String android_version_name) {
        this.image_title = android_version_name;
    }

    public String getImage_ID() {
        return image_id;
    }

    public void setImage_ID(String android_image_url) {
        this.image_id = android_image_url;
    }
}
