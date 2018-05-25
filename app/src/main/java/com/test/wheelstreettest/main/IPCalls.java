package com.test.wheelstreettest.main;

import com.test.wheelstreettest.model.AnswerModel;

/**
 * Created by Harish on 5/9/2018.
 */

public interface IPCalls {

    void attemptToGetQuations();
    void sendAnswers(AnswerModel model);
}
