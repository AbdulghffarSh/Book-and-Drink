package com.abdulghffar.drink;

import java.util.Map;

public class order {

      String orderTimeStamp;
      Map<String,String> userAddress;
      boolean delivered;
      String total;
      String userId;
      String userPhoneNumber;
      Map <String,Integer> items;

    public order() {
    }

    public order(String orderTimeStamp, Map<String,String> userAddress, String userPhoneNumber, boolean delivered, String total, String userId, Map <String,Integer> items) {
        this.orderTimeStamp = orderTimeStamp;
        this.userAddress = userAddress;
        this.delivered = delivered;
        this.total = total;
        this.userId = userId;
        this.items = items;
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setOrderTimeStamp(String orderTimeStamp) {
        this.orderTimeStamp = orderTimeStamp;
    }


    public void setUserAddress(Map<String,String> userAddress) {
        this.userAddress = userAddress;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderTimeStamp() {
        return orderTimeStamp;
    }


    public Map<String,String> getUserAddress() {
        return userAddress;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public String getTotal() {
        return total;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}
