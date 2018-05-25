package com.test.wheelstreettest.main;

import com.test.wheelstreettest.model.AnswerModel;
import com.test.wheelstreettest.model.QuationsModel;
import com.test.wheelstreettest.network.RetrofitNetwork;

/**
 * Created by Harish on 5/9/2018.
 */

public class Presenter implements IPCalls,IPresenterView{

    private IMainView iMainView;
    private RetrofitNetwork network;

    public Presenter(IMainView iMainView){
        this.iMainView=iMainView;
        network=new RetrofitNetwork();
    }

    @Override
    public void attemptToGetQuations() {
        network.getQuations(this);
    }

    @Override
    public void sendAnswers(AnswerModel model) {

        network.SendAnswers(model,this);

    }

    @Override
    public void onSuccess(QuationsModel model) {
        iMainView.onSuccess(model);

    }

    @Override
    public void onSuccessAnswers() {
        iMainView.onSuccessAnswers();
    }

    @Override
    public void onfailure() {

        iMainView.onFailure();

    }
}
