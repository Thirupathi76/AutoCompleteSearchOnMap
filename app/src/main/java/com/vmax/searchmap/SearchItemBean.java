package com.vmax.searchmap;

import java.io.Serializable;

public class SearchItemBean implements Serializable {
    private String latitude;
    private String longitude;
    private String name;
    private String placeID;
    private String type;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlaceID() {
        return this.placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }
}
