package com.test.wheelstreettest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harish on 5/10/2018.
 */

public class AnswerModel {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("fbUserName")
    @Expose
    String fbUserName;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("gender")
    @Expose
    String gender;

    @SerializedName("age")
    @Expose
    String age;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("questions")
    @Expose
    List<AnsweredQuations> qlist;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<AnsweredQuations> getQlist() {
        return qlist;
    }

    public String getAge() {
        return age;
    }

    public String getFbUserName(String name) {
        return fbUserName;
    }

    public String getGender() {
        return gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setFbUserName(String fbUserName) {
        this.fbUserName = fbUserName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setQlist(List<AnsweredQuations> qlist) {
        this.qlist = qlist;
    }
}
