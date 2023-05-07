package com.example.curd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.curd.R;
import com.example.curd.model.StudentDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    List<StudentDetail> studentDetails;

    public StudentAdapter(List<StudentDetail> studentDetails) {
        this.studentDetails = studentDetails;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.design_student_data, parent, false);
        return new StudentAdapter.StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.firstName.setText(studentDetails.get(position).getFirstName());
        holder.lastName.setText(studentDetails.get(position).getLastName());
        holder.gender.setText(studentDetails.get(position).getGender());
        holder.marital.setText(studentDetails.get(position).getMarital());

    }

    @Override
    public int getItemCount() {
        return studentDetails.size();
    }


    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName, gender, marital;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.name);
            lastName = itemView.findViewById(R.id.lName);
            gender = itemView.findViewById(R.id.gender);
            marital = itemView.findViewById(R.id.Marital);
        }

    }
}
