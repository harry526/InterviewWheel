package com.test.wheelstreettest.network;

import android.content.Context;

import com.test.wheelstreettest.main.IPresenterView;
import com.test.wheelstreettest.model.AnswerModel;
import com.test.wheelstreettest.model.QuationsModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harish on 5/9/2018.
 */

public class RetrofitNetwork {


    private static String BASE_URL = "https://api.wheelstreet.com/v1/test/";


    public Api getClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        return api;
    }


    public void getQuations(final IPresenterView view) {
        Call<QuationsModel> call = getClient().getQuations();
        call.enqueue(new Callback<QuationsModel>() {
            @Override
            public void onResponse(Call<QuationsModel> call, Response<QuationsModel> response) {
                if (response.code() == 200) {
                    QuationsModel model = response.body();
                    view.onSuccess(model);
                } else {
                    view.onfailure();
                }
            }

            @Override
            public void onFailure(Call<QuationsModel> call, Throwable t) {
                view.onfailure();
            }
        });
    }

    public void SendAnswers(AnswerModel model,final IPresenterView view) {
        Call<ResponseBody> call = getClient().sendAnswers(model);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if(response.code()==200){
                   view.onSuccessAnswers();
               }
               else
               {
                   view.onfailure();

               }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onfailure();

            }
        });
    }

}
