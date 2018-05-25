package com.test.wheelstreettest.network;

import com.test.wheelstreettest.model.AnswerModel;
import com.test.wheelstreettest.model.QuationsModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Harish on 5/9/2018.
 */

public interface Api {

    @GET("questions")
    Call<QuationsModel> getQuations();


    @Headers("Content-Type: application/json")
    @POST("answers")
    Call<ResponseBody> sendAnswers(@Body AnswerModel answers);
}
