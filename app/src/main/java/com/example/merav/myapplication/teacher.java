package com.example.merav.myapplication;

import java.util.ArrayList;
import java.util.List;

public class teacher extends users {

    protected float rank;
    protected int count;
    protected String area;
    protected String area_lower;
    protected String cost;
    protected List <String> profession=new ArrayList<>();
    protected String[]profession_lower;
    protected String phone;
    protected String image="https://firebasestorage.googleapis.com/v0/b/lessonsapp-37d29.appspot.com/o/WhatsApp%20Image%202018-12-19%20at%2012.33.35.jpeg?alt=media&token=971c0893-0593-4bd0-a94b-2f904cb9c0be";
    public teacher( String Mail ,String password, String name, String age, String area, String cost, String []profession,String phone,float rank){
        this.age=age;
        this.email=Mail;
        this.name=name;
        this.password=password;
        this.area=area;
        this.cost=cost;
        for(String s:profession){
            this.profession.add(s);
        }
        this.phone=phone;
        this.rank=rank;
        this.type="teacher";
        this.area_lower = area.toLowerCase()+"   ";
       // this.profession_lower = profession.toLowerCase()+"   ";
        this.name_lower = name.toLowerCase()+"   ";
    }
    public teacher(){}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setArea(String area) {
        this.area = area;
        this.area_lower = area.toLowerCase();
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }


    public List<String> getProfession() {
        return profession;
    }

    public void setProfession(List<String> profession) {
        this.profession = profession;
    }

    public String[] getProfession_lower() {
        return profession_lower;
    }

    public void setProfession_lower(String[] profession_lower) {
        this.profession_lower = profession_lower;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString() {
        return "Name: " + name+ '\n' +
                "Area: " + area + '\n' +
                "Cost: " + cost + '\n' +
                "Profession: " + profession + '\n' +
                "Phone number: " + phone + '\n';
    }
}
