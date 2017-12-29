package com.dreamworld.craic.model;

/**
 * Created by faizan on 10/11/2017.
 */

public class PostDetail {
    public static final int TEXT_TYPE = 0;
    public static final int IMAGE_TYPE = 1;
    public static final int AUDIO_TYPE = 2;

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getPost_icon() {
        return post_icon;
    }

    public void setPost_icon(String post_icon) {
        this.post_icon = post_icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainimageurl() {
        return mainimageurl;
    }

    public void setMainimageurl(String mainimageurl) {
        this.mainimageurl = mainimageurl;
    }

    public String getArticlesummary() {
        return articlesummary;
    }

    public void setArticlesummary(String articlesummary) {
        this.articlesummary = articlesummary;
    }

    public String getArticledescription() {
        return articledescription;
    }

    public void setArticledescription(String articledescription) {
        this.articledescription = articledescription;
    }

    public String getArticleconclution() {
        return articleconclution;
    }

    public void setArticleconclution(String articleconclution) {
        this.articleconclution = articleconclution;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getViewtype() {
        return viewtype;
    }

    public void setViewtype(int viewtype) {
        this.viewtype = viewtype;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    private String posts ;
    private String post_icon ;
    private String date ;
    private String titel ;
    private String post_id ;
    private String subtitle ;
    private String mainimageurl ;
    private String articlesummary ;
    private String articledescription ;
    private String articleconclution ;
    private int likes ;
    private int comments ;
    private int viewtype ;
    private int privacy ;
}
