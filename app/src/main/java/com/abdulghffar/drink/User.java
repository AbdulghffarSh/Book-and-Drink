package com.abdulghffar.drink;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private String uID;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String gender;
    private Map<String, String> address;
    private Map<String, Integer> cart;

    public User(String uID, String email, String fullName, String phoneNumber,
                String gender, Map<String, String> address, Map<String,
            Integer> cart) {
        this.uID = uID;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.cart = cart;
    }

    public String getuID() {
        return uID;
    }

    public User() {
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

    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }

    public Map<String, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<String, Integer> cart) {
        this.cart = cart;
    }

}
