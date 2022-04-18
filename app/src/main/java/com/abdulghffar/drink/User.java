package com.abdulghffar.drink;

public class User {
    private String uID;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String gender;

    public User(String uID, String email, String fullName, String phoneNumber,String gender) {
        this.uID = uID;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getuID() {
        return uID;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getGender() {
        return gender;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}
