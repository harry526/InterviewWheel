package com.test.wheelstreettest.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;
import com.test.wheelstreettest.R;
import com.test.wheelstreettest.model.UserDetails;
import com.test.wheelstreettest.ui.activity.Authentication;
import com.test.wheelstreettest.ui.activity.MainActivity;
import com.test.wheelstreettest.utils.CircleTransform;
import com.test.wheelstreettest.utils.Constents;
import com.test.wheelstreettest.utils.DBHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harish on 5/9/2018.
 */

public class Profile extends AppCompatActivity {

    @BindView(R.id.propic)
    ImageView propic;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.dob)
    EditText dob;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;


    RadioButton radioButton;

    @BindView(R.id.floating)
    FloatingActionButton floating;
    DBHandler dbHandler;
    UserDetails userDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        dbHandler = new DBHandler(this);
        userDetails = dbHandler.getProfile();
//        Picasso.with(this).load(userDetails.getDp()).transform(new CircleTransform()).into(propic);
        try {
            Picasso.with(this).load(userDetails.getDp()).transform(new CircleTransform()).into(propic);
            name.setText(userDetails.getName());
            email.setText(userDetails.getEmail());
            dob.setText(userDetails.getDob());
            if (userDetails.getMobile()!=null) {
                if (!userDetails.getMobile().equals("")) {
                    mobile.setText(userDetails.getMobile());
                }
            }
            if (userDetails.getGender()!=null) {

                if (!userDetails.getGender().equals("")) {
                    if (userDetails.getGender().equalsIgnoreCase("male")) {
                        male.setChecked(true);
                    } else {
                        female.setChecked(true);
                    }
                }
            }
        }
        catch (NullPointerException e){

        }


    }

    @OnClick(R.id.floating)
    public void submit() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        String u_name = name.getText().toString();
        String u_email = email.getText().toString();
        String u_dob = dob.getText().toString();
        String u_radioButton;
        String u_mobile = mobile.getText().toString();
        if (radioButton!= null) {
            u_radioButton = radioButton.getText().toString();
        }
        else {
            u_radioButton="";
        }


        if (u_name.length() < 3) {
            Constents.showToast(this, Constents.name);
        } else if (u_dob.length() < 6) {
            Constents.showToast(this, Constents.dob);
        } else if (!u_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Constents.showToast(this, Constents.email);
        } else if (u_radioButton.equals("")) {
            Constents.showToast(this, Constents.gender);
        } else if (u_mobile.equals("")) {
            Constents.showToast(this, Constents.mobile);
        } else {

            UserDetails userDetail = new UserDetails();
            userDetail.setId(userDetails.getId());
            userDetail.setName(u_name);
            userDetail.setDob(u_dob);
            userDetail.setDp(userDetails.getDp());
            userDetail.setGender(u_radioButton);
            userDetail.setMobile(u_mobile);
            dbHandler.UpdateIntoDb(userDetail);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Constents.showToast(this, "Updated");
        }


    }


}
