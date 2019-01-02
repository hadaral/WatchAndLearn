package com.example.merav.myapplication;

public class student extends users {


    public student( String Mail ,String password, String name, String age){
        this.age=age;
        this.email=Mail;
        this.name=name;
        this.password=password;
        this.type="student";
}

    public student() {
    }
}
