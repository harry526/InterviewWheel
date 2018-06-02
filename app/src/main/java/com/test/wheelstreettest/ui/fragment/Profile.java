package com.test.wheelstreettest.ui.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.test.wheelstreettest.R;
import com.test.wheelstreettest.model.UserDetails;
import com.test.wheelstreettest.ui.activity.Authentication;
import com.test.wheelstreettest.ui.activity.MainActivity;
import com.test.wheelstreettest.utils.CircleTransform;
import com.test.wheelstreettest.utils.Constents;
import com.test.wheelstreettest.utils.DBHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    private static int IMG_RESULT = 1;
    RadioButton radioButton;
    DatePickerDialog.OnDateSetListener date;
    @BindView(R.id.floating)
    FloatingActionButton floating;
    DBHandler dbHandler;
    UserDetails userDetails;
    String ImageDecode;
    Calendar myCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        myCalendar = Calendar.getInstance();
        init();
    }


    @OnClick(R.id.propic)
    public void imagePick(View v) {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent, IMG_RESULT);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.dob)
    public void dobchange(View v){
        new DatePickerDialog(Profile.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void init() {
         date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dbHandler = new DBHandler(this);
        userDetails = dbHandler.getProfile();
//        Picasso.with(this).load(userDetails.getDp()).transform(new CircleTransform()).into(propic);
        try {
            if (userDetails.getDp().contains("#")) {
                String uri = userDetails.getDp().replaceAll("#", "");
                Picasso.with(this).load(Uri.parse(uri)).transform(new CircleTransform()).into(propic);
            } else {
                Picasso.with(this).load(userDetails.getDp()).transform(new CircleTransform()).into(propic);
            }
            name.setText(userDetails.getName());
            email.setText(userDetails.getEmail());
            dob.setText(userDetails.getDob());
            if (userDetails.getMobile() != null) {
                if (!userDetails.getMobile().equals("")) {
                    mobile.setText(userDetails.getMobile());
                }
            }
            if (userDetails.getGender() != null) {

                if (!userDetails.getGender().equals("")) {
                    if (userDetails.getGender().equalsIgnoreCase("male")) {
                        male.setChecked(true);
                    } else if (userDetails.getGender().equalsIgnoreCase("female")) {
                        female.setChecked(true);
                    }
                }
            }
        } catch (NullPointerException e) {

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
        if (radioButton != null) {
            u_radioButton = radioButton.getText().toString();
        } else {
            u_radioButton = "";
        }


        if (u_name.length() < 3) {
            Constents.showToast(this, Constents.name);
        } else if (u_dob.length() < 6) {
            Constents.showToast(this, Constents.dob);
        } else if (!u_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Constents.showToast(this, Constents.email);
        } else if (u_radioButton.equals("")) {
            Constents.showToast(this, Constents.gender);
        } else if (u_mobile.length()<10) {
            Constents.showToast(this, Constents.mobile);
        } else {

            UserDetails userDetail = new UserDetails();
            userDetail.setId(userDetails.getId());
            userDetail.setName(u_name);
            userDetail.setDob(u_dob);
            userDetail.setDp(userDetails.getDp());
            userDetail.setEmail(u_email);
            userDetail.setGender(u_radioButton);
            userDetail.setMobile(u_mobile);
            dbHandler.UpdateIntoDb(userDetail);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Constents.showToast(this, "Updated");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();
                userDetails.setDp(String.valueOf(URI) + "#");
                Picasso.with(this).load(URI).transform(new CircleTransform()).into(propic);


            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }


}
