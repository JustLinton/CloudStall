package com.berrysdu.cloudstall.objects;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Item implements Serializable {

    public Item(int ID, String title, String description, float balance) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.balance = balance;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public Bitmap getPic() {
        return pic;
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
    private Bitmap pic;
}
