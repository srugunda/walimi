package com.example.navigationdrawermateriallibrary.Model;

public class Product {
    private String category;
    private String date;
    private String description;
    private String image;
    private String pid;
    public String pname;
    private String price;
    private String time;
    private String userName;

    public Product(String category, String date, String description, String image, String pid, String pname, String price, String time, String userName) {
        this.category = category;
        this.date = date;
        this.description = description;
        this.image = image;
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.time = time;
        this.userName = userName;
    }

    public Product(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}


