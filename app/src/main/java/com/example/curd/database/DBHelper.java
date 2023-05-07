package com.example.curd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static DBHelper instance;
    public static final String TABLENAME = "students";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String MARITAL = "marital";
    public static final String GENDER = "gender";

    public DBHelper(@Nullable Context context) {
        super(context, "studentDetail.db", null, 5);
    }

    public static String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + TABLENAME + "("
            + FIRSTNAME + " TEXT NOT NULL," + LASTNAME +" TEXT NOT NULL," + MARITAL + " TEXT NOT NULL," + GENDER + " TEXT NOT NULL" + ")";

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists students");
        onCreate(DB);
    }
}
