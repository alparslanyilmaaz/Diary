package com.example.diary;

public class MobileOs {
    String time , date , description, id, password, color, title, backColor;


    public MobileOs(String time, String date, String description, String id, String password, String color, String title, String backColor) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.description = description;
        this.password = password;
        this.color = color;
        this.title = title;
        this.backColor = backColor;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdName(){ return id; }
    public void setId(String id){this.id = id; }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password =  password;
    }

    public String getTextColor(){ return  color; }
    public void setTextColor(String color){this.color = color;}

    public String getTitle(){ return title;}
    public void setTitle(String title){this.title = title;}

    public String getBackColor(){ return backColor; }
    public void setBackColor(String backColor){this.backColor = backColor;}
}
