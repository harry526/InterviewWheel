package com.test.wheelstreettest.main;

import com.test.wheelstreettest.model.QuationsModel;

/**
 * Created by Harish on 5/9/2018.
 */

public interface IPresenterView {
    void onSuccess(QuationsModel model);
    void onSuccessAnswers();
    void onfailure();
}
