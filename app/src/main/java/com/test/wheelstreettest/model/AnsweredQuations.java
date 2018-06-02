package com.test.wheelstreettest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harish on 5/10/2018.
 */

public class AnsweredQuations {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("answer")
    @Expose
    private Object answer;


    public String getId() {
        return id;
    }

    public Object getAnswer() {
        return answer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }
}
