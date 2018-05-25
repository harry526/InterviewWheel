package com.test.wheelstreettest.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.test.wheelstreettest.R;
import com.test.wheelstreettest.main.IAuth;
import com.test.wheelstreettest.model.UserDetails;
import com.test.wheelstreettest.utils.Constents;
import com.test.wheelstreettest.utils.DBHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Authentication extends AppCompatActivity implements IAuth {

    @BindView(R.id.login_button)
    LoginButton login_button;
    CallbackManager callbackManager;
    DBHandler dbHandler;
    UserDetails userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        dbHandler=new DBHandler(Authentication.this);
        userDetails=dbHandler.getProfile();
        if(userDetails!=null){
            loggedIn();
        }


        callbackManager = CallbackManager.Factory.create();
       // login_button.setReadPermissions(Arrays.asList(Constents.EMAIL));
        login_button.setReadPermissions(Arrays.asList( "email","public_profile"));
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {
                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            successLog(currentProfile,loginResult);
                            mProfileTracker.stopTracking();
                        }
                    };
                }
                else {
                    Profile profile = Profile.getCurrentProfile();
                    successLog(profile,loginResult);
                }


            }

            @Override
            public void onCancel() {
                Log.e("name","erro");            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("name",exception.getMessage());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void successLog(Profile currentProfile,LoginResult loginResult){

        final String bitmapImage= String.valueOf(currentProfile.getProfilePictureUri(500,500));

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.v("LoginActivity Response ", response.toString());

                        try {

                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                          //  String dob = object.getString("birthday");
                            UserDetails userDetails=new UserDetails(id,name,email,"",bitmapImage);
                            dbHandler.insertIntoDb(userDetails);
                            loggedIn();

                        } catch (JSONException e) {
                         //   loggedIn();
                            e.printStackTrace();

                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }


    public void loggedIn(){
        Intent intent=new Intent(Authentication.this, com.test.wheelstreettest.ui.fragment.Profile.class);
        startActivity(intent);
        finish();
    }


}
