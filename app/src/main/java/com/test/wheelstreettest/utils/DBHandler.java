package com.test.wheelstreettest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.wheelstreettest.model.QANDA;
import com.test.wheelstreettest.model.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harish on 5/9/2018.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "auth";
    private static final String TABLE_NAME = "user_details";
    private static final int DB_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String F_ID = "f_id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String DOB = "dob";
    private static final String DP = "dp";
    private static final String GENDER = "gender";
    private static final String MOBILE = "mobile";


    private static final String QANDATABLE = "qandatable";
    private static final String TYPE = "type";
    private static final String QUA = "qua";
    private static final String ANS = "ans";
    private static final String Q_ID = "q_id";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + QANDATABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + Q_ID + " TEXT,"
                + QUA + " TEXT,"
                + ANS + " TEXT,"
                + TYPE + " TEXT," + ")";

        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + F_ID + " TEXT," + NAME + " TEXT," + EMAIL + " TEXT," + DOB + " TEXT," + DP + " TEXT," + GENDER + " TEXT," + MOBILE + " TEXT" + ")";
        db.execSQL(createTable);
        db.execSQL(createTable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QANDATABLE);
        onCreate(db);
    }



    public void insertQandA(QANDA model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Q_ID, model.getQ_ID());
        values.put(QUA, model.getQUA());
        values.put(ANS, model.getANS());
        values.put(TYPE, model.getTYPE());
        db.insert(QANDATABLE, null, values);
        db.close();

    }


    public void insertIntoDb(UserDetails model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(F_ID, model.getId());
        values.put(NAME, model.getName());
        values.put(EMAIL, model.getEmail());
        values.put(DOB, model.getDob());
        values.put(DP, model.getDp());
        values.put(GENDER, "");
        values.put(MOBILE, "");
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public void UpdateIntoDb(UserDetails model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(F_ID, model.getId());
        values.put(NAME, model.getName());
        values.put(EMAIL, model.getEmail());
        values.put(DOB, model.getDob());
        values.put(DP, model.getDp());
        values.put(GENDER, model.getGender());
        values.put(MOBILE, model.getMobile());
        db.update(TABLE_NAME, values, F_ID + "=" + model.getId(), null);
        db.close();
    }

    public UserDetails getProfile() {

        String getQuary = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(getQuary, null);
        if (cursor.moveToFirst()) {

            UserDetails model = new UserDetails();
            model.setId(cursor.getString(1));
            model.setName(cursor.getString(2));
            model.setEmail(cursor.getString(3));
            model.setDob(cursor.getString(4));
            model.setDp(cursor.getString(5));
            model.setGender(cursor.getString(5));
            return model;
        }
        return null;
    }

    public void DeleteDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}
