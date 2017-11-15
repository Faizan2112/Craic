package com.dreamworld.craic.model;

/**
 * Created by faizan on 10/11/2017.
 */

public class UserDetail {
    private int loginid ;
    private String email ;
    private String password ;
    private String verified ;
    private String confirmcode ;
    private String firstname ;
    private String lastname ;
    private String username ;
    private String location ;
    private String dob ;
    private String friendlist ;
    private String likelist ;
    private String posts ;
    private int status ;

    public int getStatus() {
        return status;
    }

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getConfirmcode() {
        return confirmcode;
    }

    public void setConfirmcode(String confirmcode) {
        this.confirmcode = confirmcode;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(String friendlist) {
        this.friendlist = friendlist;
    }

    public String getLikelist() {
        return likelist;
    }

    public void setLikelist(String likelist) {
        this.likelist = likelist;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }





}
