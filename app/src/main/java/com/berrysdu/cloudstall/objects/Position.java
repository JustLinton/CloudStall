package com.berrysdu.cloudstall.objects;

import java.io.Serializable;

public class Position implements Serializable {

    public Position(double latitude_,double longitude_){latitude=latitude_;longitude=longitude_;}

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private double latitude;
    private double longitude;
}
