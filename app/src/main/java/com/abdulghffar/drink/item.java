package com.abdulghffar.drink;

public class item {
    private String itemName;
    private float itemPrice;
    private String itemDescription;
    private String itemPicURL;


    public item(String itemName, float itemPrice, String itemDescription, String itemPicURL) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.itemPicURL = itemPicURL;
    }




    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemPicURL(String itemPicURL) {
        this.itemPicURL = itemPicURL;
    }

    public String getItemName() {
        return itemName;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemPicURL() {
        return itemPicURL;
    }
}
