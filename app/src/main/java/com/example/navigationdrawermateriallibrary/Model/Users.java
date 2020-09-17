package com.example.navigationdrawermateriallibrary.Model;

public class Users {
    private String name;
    private String password;
    private String phoneNumber;

    public Users(String phoneNumber, String password, String userName) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Users(){
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
