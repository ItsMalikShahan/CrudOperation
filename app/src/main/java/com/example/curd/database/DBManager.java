package com.example.curd.database;

import static com.example.curd.database.DBHelper.FIRSTNAME;
import static com.example.curd.database.DBHelper.GENDER;
import static com.example.curd.database.DBHelper.LASTNAME;
import static com.example.curd.database.DBHelper.MARITAL;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.curd.R;
import com.example.curd.activities.StudentFormActivity;
import com.example.curd.model.StudentDetail;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    DBHelper dbHelper;
    Context context;
    SQLiteDatabase database;
    List<StudentDetail> list = new ArrayList<>();

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = DBHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertion(String firstName, String lastName, String marital, String gender) {

        ContentValues values = new ContentValues();
        values.put("firstName", firstName);
        values.put("lastname", lastName);
        values.put("marital", marital);
        values.put("gender", gender);
        database.insert("students", null, values);
    }

    public void updatingData(String firstName, String lastName, String marital, String gender) {
        ContentValues values = new ContentValues();
        values.put("lastName", lastName);
        values.put("marital", marital);
        values.put("gender", gender);
        Cursor cursor = database.rawQuery("Select * From students where firstname = ? ", new String[]{firstName});
        if (cursor.getCount() > 0) {
            database.update("students", values, "FIRSTNAME=?", new String[]{firstName});
            Toast.makeText(context, firstName+" Data Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show();

        }

    }

    public void removeData(String firstName) {
        Cursor deleteData = database.rawQuery("Select * from " + DBHelper.TABLENAME + " where " + FIRSTNAME + " =?", new String[]{firstName});
        if (deleteData.getCount() > 0) {
            database.delete("students", "FIRSTNAME=?", new String[]{firstName});
            Toast.makeText(context, firstName+" Record Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    public List<StudentDetail> getFirstNameData(String firstName) {

        Cursor cursor = database.rawQuery("Select * from " + DBHelper.TABLENAME + " Where " + FIRSTNAME + " =?", new String[]{firstName});
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                StudentDetail singleItem = new StudentDetail();
                singleItem.setFirstName(cursor.getString(cursor.getColumnIndex(DBHelper.FIRSTNAME)));
                singleItem.setLastName(cursor.getString(cursor.getColumnIndex(DBHelper.LASTNAME)));
                singleItem.setMarital(cursor.getString(cursor.getColumnIndex(DBHelper.MARITAL)));
                singleItem.setGender(cursor.getString(cursor.getColumnIndex(DBHelper.GENDER)));
                list.add(singleItem);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    @SuppressLint("Range")
    public List<StudentDetail> getAllData(String firstName) {

        Cursor cursor = database.rawQuery(" select * from " + DBHelper.TABLENAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                StudentDetail studentList = new StudentDetail();
                studentList.setFirstName(cursor.getString(cursor.getColumnIndex(FIRSTNAME)));
                studentList.setLastName(cursor.getString(cursor.getColumnIndex(LASTNAME)));
                studentList.setMarital(cursor.getString(cursor.getColumnIndex(MARITAL)));
                studentList.setGender(cursor.getString(cursor.getColumnIndex(GENDER)));
                list.add(studentList);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    @SuppressLint("Range")
    public List<StudentDetail> getData(String gender, String maritalStatus, String firstName, String lastName) {


        if (gender.equals("All") && maritalStatus.equals("All")) {
            Cursor cursor = database.rawQuery(" SELECT * FROM " + DBHelper.TABLENAME, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    StudentDetail studentList = new StudentDetail();
                    studentList.setFirstName(cursor.getString(cursor.getColumnIndex(DBHelper.FIRSTNAME)));
                    studentList.setLastName(cursor.getString(cursor.getColumnIndex(DBHelper.LASTNAME)));
                    studentList.setMarital(cursor.getString(cursor.getColumnIndex(DBHelper.MARITAL)));
                    studentList.setGender(cursor.getString(cursor.getColumnIndex(DBHelper.GENDER)));
                    list.add(studentList);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        else
            if (gender.equals("All")) {
            Cursor genderAll = database.rawQuery("Select * from " + DBHelper.TABLENAME + " Where " + MARITAL +  " =? ", new String[]{maritalStatus});
            if (genderAll != null) {
                genderAll.moveToFirst();
                while (!genderAll.isAfterLast()) {

                    StudentDetail singleItem = new StudentDetail();
                    singleItem.setFirstName(genderAll.getString(genderAll.getColumnIndex(DBHelper.FIRSTNAME)));
                    singleItem.setLastName(genderAll.getString(genderAll.getColumnIndex(DBHelper.LASTNAME)));
                    singleItem.setMarital(genderAll.getString(genderAll.getColumnIndex(DBHelper.MARITAL)));
                    singleItem.setGender(genderAll.getString(genderAll.getColumnIndex(DBHelper.GENDER)));
                    list.add(singleItem);
                    genderAll.moveToNext();
                }
                genderAll.close();
            }

        } else if (maritalStatus.equals("All")) {
            Cursor maritalAll = database.rawQuery("Select * from " + DBHelper.TABLENAME + " Where " + GENDER + " =? ", new String[]{gender});
            if (maritalAll != null) {
                maritalAll.moveToFirst();
                while (!maritalAll.isAfterLast()) {

                    StudentDetail singleItem = new StudentDetail();
                    singleItem.setFirstName(maritalAll.getString(maritalAll.getColumnIndex(DBHelper.FIRSTNAME)));
                    singleItem.setLastName(maritalAll.getString(maritalAll.getColumnIndex(DBHelper.LASTNAME)));
                    singleItem.setMarital(maritalAll.getString(maritalAll.getColumnIndex(DBHelper.MARITAL)));
                    singleItem.setGender(maritalAll.getString(maritalAll.getColumnIndex(DBHelper.GENDER)));
                    list.add(singleItem);
                    maritalAll.moveToNext();
                }
                maritalAll.close();
            }
        }
        else {
            String[] args = new String[]{maritalStatus, gender};

            String maritalGenderQuery = " select * from " + DBHelper.TABLENAME + " WHERE "
                    + DBHelper.MARITAL + " =? " + " AND " + DBHelper.GENDER + " =? ";
            Cursor cursor = database.rawQuery(maritalGenderQuery, args, null);

            if (cursor != null) {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {

                    StudentDetail studentList = new StudentDetail();
                    studentList.setFirstName(cursor.getString(cursor.getColumnIndex(DBHelper.FIRSTNAME)));
                    studentList.setLastName(cursor.getString(cursor.getColumnIndex(DBHelper.LASTNAME)));
                    studentList.setMarital(cursor.getString(cursor.getColumnIndex(DBHelper.MARITAL)));
                    studentList.setGender(cursor.getString(cursor.getColumnIndex(DBHelper.GENDER)));
                    list.add(studentList);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }

        return list;

    }
}

