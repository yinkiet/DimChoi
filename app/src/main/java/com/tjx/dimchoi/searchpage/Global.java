package com.tjx.dimchoi.searchpage;

/**
 * Created by YinKiet on 5/2/2017.
 */

public class Global {

    public static int LENGTH = 5;
    public static String[] mPlaces;
    public static String[] mPlaceDesc;
    public static String[] imageID;

    public void setLength(int length) {
        this.LENGTH = length;
    }

    public int getLength() {
        return LENGTH;
    }

    public void setPlace(String[] mPlaces) {
        this.mPlaces = mPlaces;
    }

    public String[] getPlace() {
        return mPlaces;
    }

    public void setPlaceDesc(String[] mPlaceDesc) {
        this.mPlaceDesc = mPlaceDesc;
    }

    public String[] getmPlaceDesc() {
        return mPlaceDesc;
    }

    public void setImageID(String[] imageID) {
        this.imageID = imageID;
    }

    public String[] getImageID() {
        return imageID;
    }
}
