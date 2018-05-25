package com.test.wheelstreettest.main;

import com.facebook.Profile;
import com.facebook.login.LoginResult;

/**
 * Created by Harish on 5/9/2018.
 */

public interface IAuth {
    void successLog(Profile currentProfile, LoginResult loginResult);
}
