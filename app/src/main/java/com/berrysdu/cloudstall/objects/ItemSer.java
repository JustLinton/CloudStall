package com.berrysdu.cloudstall.objects;

import java.io.Serializable;

public class ItemSer implements Serializable {
    public ItemSer(Item item_) {
        this.ID = item_.getID();
        this.title = item_.getTitle();
        this.description = item_.getDescription();
        this.balance = item_.getBalance();
    }



    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getBalance() {
        return balance;
    }

    private int ID;
    private String title;
    private String description;
    private float balance;
}