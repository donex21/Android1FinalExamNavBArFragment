package com.example.cebuschooldirectory;

public class User {
    public String fname, mname, lname, age, gender, address, contactNumber;

    public User(){

    }

    public User(String fname, String mname, String lname, String age, String gender, String address, String contactNumber) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.contactNumber = contactNumber;
    }
}
