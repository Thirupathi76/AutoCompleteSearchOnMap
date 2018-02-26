package com.vmax.searchmap;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GooglePlacesParser {
    public static final String KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION = "description";
    String JSON;
    ArrayList<GooglePlacesBean> placesList = new ArrayList();

    public GooglePlacesParser(String JSON) {
        this.JSON = JSON;
    }

    public ArrayList<GooglePlacesBean> getPlaces() {
        this.placesList = new ArrayList();
        try {
            JSONArray predictions = new JSONObject(this.JSON).getJSONArray("predictions");
            for (int i = 0; i < predictions.length(); i++) {
                GooglePlacesBean bean = new GooglePlacesBean();
                JSONObject prediction = predictions.getJSONObject(i);
                String description = prediction.getString(KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
                String placeId = prediction.getString("place_id");
                bean.setDescription(description);
                bean.setPlaceId(placeId);
                this.placesList.add(bean);
            }
        } catch (JSONException ex) {
            Log.e("GooglePlacesParser", "Error while parsing", ex);
        }
        return this.placesList;
    }
}
