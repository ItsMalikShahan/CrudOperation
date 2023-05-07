package com.example.curd.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.curd.R;
import com.example.curd.adapter.StudentAdapter;
import com.example.curd.database.DBManager;
import com.example.curd.model.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentFormActivity extends AppCompatActivity {
    EditText firstName, lastName;
    Spinner marital;
    RadioGroup gender;
    RadioButton male, female, all, selectedRadioButton;
    Button insert, retrieve, update, delete, show;
    DBManager dbManager;
    RecyclerView recyclerData;
    List<StudentDetail> studentList;
    StudentAdapter studentAdapter;
    String f_name, l_name, marital_status, gender_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        firstName = findViewById(R.id.et_fistName);
        lastName = findViewById(R.id.LastName);
        marital = findViewById(R.id.Marital);
        gender = findViewById(R.id.rg_gender);
        male = findViewById(R.id.rb_male);
        female = findViewById(R.id.rb_female);
        insert = findViewById(R.id.submit);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        all = findViewById(R.id.rb_all);
        show = findViewById(R.id.btn_showData);
        recyclerData = findViewById(R.id.studentData);
        retrieve = findViewById(R.id.retrieve);

        dbManager = new DBManager(this);
        dbManager.open();

        String[] maritalList = new String[]{"Select", "All", "Single", "Widow", "Married", "Divorced"};
        ArrayAdapter<String> maritalAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, maritalList);
        maritalAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        marital.setAdapter(maritalAdapter);

        marital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                marital_status = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerData.setLayoutManager(layoutManager);
        recyclerData.setAdapter(studentAdapter);

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.clear();
                f_name = firstName.getText().toString();
                l_name = lastName.getText().toString();
                selectedRadioButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                gender_status = selectedRadioButton.getText().toString();
                if (marital_status.equals("Select")) {
                    Toast.makeText(StudentFormActivity.this, "Select Marital Status ", Toast.LENGTH_SHORT).show();
                } else {
                    studentList = (dbManager.getData(gender_status, marital_status, f_name, l_name));
                }
                studentAdapter = new StudentAdapter(studentList);
                recyclerData.setAdapter(studentAdapter);
                studentAdapter.notifyDataSetChanged();
                Log.e("dbCheck", "onClick: array size: " + studentList.size());
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.clear();
                String firstNameTxt = firstName.getText().toString();
                if (firstNameTxt.isEmpty()) {
                    Toast.makeText(StudentFormActivity.this, "Provide name to Show Data", Toast.LENGTH_SHORT).show();
                } else {
                    studentList = (dbManager.getFirstNameData(firstNameTxt));
                    if (studentList.isEmpty()) {
                        Toast.makeText(StudentFormActivity.this, firstNameTxt + " Record not Found", Toast.LENGTH_SHORT).show();
                    }
                    studentAdapter = new StudentAdapter(studentList);
                    recyclerData.setAdapter(studentAdapter);
                    studentAdapter.notifyDataSetChanged();
                    Log.e("dbCheck", "onClick: array size: " + studentList.size());
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.clear();
                String firstNameTxt = firstName.getText().toString();
                String lastNameTxt = lastName.getText().toString();
                selectedRadioButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                String genderTxt = selectedRadioButton.getText().toString();
                String maritalTxt = marital.getSelectedItem().toString();
                if (firstName.getText().toString().isEmpty()) {
                    firstName.setError("Provide first Name");
                } else if (lastName.getText().toString().isEmpty()) {
                    lastName.setError("Provide Last Name");
                } else if (maritalTxt.equals("Select") || maritalTxt.equals("All")) {
                    Log.e("check", "onClick: else marital ");
                    Toast.makeText(StudentFormActivity.this, "Select Marital Status", Toast.LENGTH_SHORT).show();
                } else if (genderTxt.equals("All")) {
                    Toast.makeText(StudentFormActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.updatingData(firstNameTxt, lastNameTxt, maritalTxt, genderTxt);
                    studentList = (dbManager.getAllData(firstNameTxt));
                        Log.e("Check", "onClick: size of list is : " + studentList.size());
                        studentAdapter = new StudentAdapter(studentList);
                        recyclerData.setAdapter(studentAdapter);
                        studentAdapter.notifyDataSetChanged();

                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.clear();
                String firstNameTxt = firstName.getText().toString();
                if (firstName.getText().toString().isEmpty()) {
                    Toast.makeText(StudentFormActivity.this, "Provide First Name", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.removeData(firstNameTxt);
                    studentList = (dbManager.getAllData(firstNameTxt));
                        Toast.makeText(StudentFormActivity.this, firstNameTxt + " Record Deleted", Toast.LENGTH_SHORT).show();
                        Log.e("Check", "onClick: size of list is : " + studentList.size());
                        studentAdapter = new StudentAdapter(studentList);
                        recyclerData.setAdapter(studentAdapter);
                        studentAdapter.notifyDataSetChanged();
                    }
                }

        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.clear();
                String firstNameTxt = firstName.getText().toString();
                String lastNameTxt = lastName.getText().toString();
                String maritalTxt = marital.getSelectedItem().toString();
                selectedRadioButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                String genderTxt = selectedRadioButton.getText().toString();
                if (firstName.getText().toString().isEmpty()) {
                    firstName.setError("Provide First Name");
                } else if (lastName.getText().toString().isEmpty()) {
                    lastName.setError("Provide Last Name");
                } else if (maritalTxt.equals("Select") || maritalTxt.equals("All")) {
                    Log.e("check", "onClick: else marital ");
                    Toast.makeText(StudentFormActivity.this, "Select Marital Status", Toast.LENGTH_SHORT).show();
                } else if (genderTxt.equals("All")) {
                    Toast.makeText(StudentFormActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.insertion(firstNameTxt, lastNameTxt, maritalTxt, genderTxt);
                    Toast.makeText(StudentFormActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                    studentList = (dbManager.getAllData(firstNameTxt));
                    Log.e("Check", "onClick: size of list is : " + studentList.size());
                    studentAdapter = new StudentAdapter(studentList);
                    recyclerData.setAdapter(studentAdapter);
                    studentAdapter.notifyDataSetChanged();

                }
            }
        });
    }
}