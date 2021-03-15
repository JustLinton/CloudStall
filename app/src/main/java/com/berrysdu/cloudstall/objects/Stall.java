package com.berrysdu.cloudstall.objects;

import java.io.Serializable;

public class Stall implements Serializable {
    public static final int TYPE_UNKOWN=0;
    public static final int TYPE_FOOD=1;
    public static final int TYPE_GROCERY=2;
    public static final int TYPE_BOOK=3;


    private Position position;
    private int ID;
    private int type;
    private String title;

    private String ownerName;

    public Stall(int ID_, Position position_, int type_,String title_){
        ID=ID_;position=position_;type=type_;title=title_;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getID() {
        return ID;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Position getPosition() {
        return position;
    }
}
