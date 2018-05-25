package com.test.wheelstreettest.model;

/**
 * Created by Harish on 5/9/2018.
 */

public class UserDetails {

    private String id;
    private String name;
    private String email;
    private String dob;
    private String dp;
    private String gender;
    private String mobile;

    public UserDetails(){
    }

    public UserDetails(String id, String name, String email, String dob,String dp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.dp = dp;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getDp() {
        return dp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
