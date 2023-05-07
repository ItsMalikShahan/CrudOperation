package com.example.curd.model;

public class StudentDetail {
    String firstName;
    String lastName;
    String marital;
    String gender;

    public StudentDetail(String firstName, String lastName, String marital, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.marital = marital;
        this.gender = gender;
    }

    public StudentDetail() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
