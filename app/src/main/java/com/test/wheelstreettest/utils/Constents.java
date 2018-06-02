package com.test.wheelstreettest.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.test.wheelstreettest.ui.activity.MainActivity;

/**
 * Created by Harish on 5/9/2018.
 */

public class Constents {
    public static final String EMAIL = "email";
    private ProgressDialog pb;
public static final String name="Please Enter Name";
public static final String dob="Please Enter Date of birth";
public static final String email="Please Enter Email";
public static final String gender="Please Select Gender";
public static final String mobile="Please Enter Mobile no.";

    public ProgressDialog showProgressdialog(Context context)
    {
        pb = new ProgressDialog(context);
        pb.setCancelable(false);
        pb.setMessage("Please Wait");
        pb.show();
        return pb;
    }

    public void dismissProgress()
    {
        pb.dismiss();
    }

    public static void showToast(Context ctx,String name){
        try{
            Toast.makeText(ctx,name,Toast.LENGTH_SHORT).show();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}
