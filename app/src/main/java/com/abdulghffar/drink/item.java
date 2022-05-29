package com.abdulghffar.drink;


public class item
{
    private String itemID, itemName, itemPrice, itemDescription, itemPicURL;

    public item (String itemID, String itemName, String itemPrice,
                 String itemDescription, String itemPicURL)
    {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.itemPicURL = itemPicURL;
    }

    public void setItemName (String itemName)
    {
        this.itemName = itemName;
    }

    public void setItemPrice (String itemPrice)
    {
        this.itemPrice = itemPrice;
    }

    public void setItemDescription (String itemDescription)
    {
        this.itemDescription = itemDescription;
    }

    public String getItemName ()
    {
        return itemName;
    }

    public String getItemPrice ()
    {
        return itemPrice;
    }

    public String getItemDescription ()
    {
        return itemDescription;
    }

    public String getItemID ()
    {
        return itemID;
    }

    public void setItemID (String itemID)
    {
        this.itemID = itemID;
    }

    public item (String itemName, String itemPrice, String itemDescription,
                 String itemPicURL)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.itemPicURL = itemPicURL;
    }

    public item ()
    {
    }

    public String getItemPicURL ()
    {
        return itemPicURL;
    }

    public void setItemPicURL (String itemPicURL)
    {
        this.itemPicURL = itemPicURL;
    }
}
