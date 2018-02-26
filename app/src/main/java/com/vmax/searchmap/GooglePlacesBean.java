package com.vmax.searchmap;

import java.io.Serializable;

public class GooglePlacesBean implements Serializable {
    private String description;
    private String placeId;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
