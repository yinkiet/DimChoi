package dimchoi.com.my;

import android.app.Application;

/**
 * Created by YinKiet on 5/2/2017.
 */

public class Global extends Application {

    public int LENGTH = 5;
    public String[] mPlaces;
    public String[] mPlaceDesc;
    public String[] imageID;

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

    public String[] getPlaceDesc() {
        return mPlaceDesc;
    }

    public void setImageID(String[] imageID) {
        this.imageID = imageID;
    }

    public String[] getImageID() {
        return imageID;
    }
}
