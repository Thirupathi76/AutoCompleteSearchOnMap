package com.vmax.searchmap;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String GOOGLE_PLACES_URL = "maps.googleapis.com/maps/api/place/autocomplete/json";
    public static final String PLACES_API_KEY = "AIzaSyD56Ffw-dzsr84bVUMHLEYDzQgJNgoc5bQ";
    public static final int SEARCH_RADIUS = 1000;
    SearchResultAdapter resultAdapter;
    List<SearchItemBean> results = new ArrayList();
    SearchItemBean search;
    AutoCompleteTextView autoText;
    ImageView searchBtn;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoText = findViewById(R.id.places_autocomplete);
        findViewById(R.id.search_icon).setOnClickListener(this);
        listView = findViewById(R.id.searchList);

        autoText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                results.clear();
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https").encodedAuthority(GOOGLE_PLACES_URL).appendQueryParameter("input", s.toString()).appendQueryParameter("location", "18.299356,80.208407").appendQueryParameter("radius", "1000").appendQueryParameter("key", PLACES_API_KEY);
                Log.e("URL", builder.build().toString());
                new FetchFromServerTask(0).execute(builder.build().toString());
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View view) {

        /*Intent detailActivity = new Intent(this, PlaceDetail.class);
        detailActivity.putExtra("placeId", search.getPlaceID());
        this.startActivity(detailActivity);*/
    }

    public class FetchFromServerTask extends AsyncTask<String, Void, String> {
        private int id;

        public FetchFromServerTask(int id) {
            this.id = id;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            StringBuilder builder = new StringBuilder();
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(30000);
                connection.setRequestMethod("GET");
                connection.connect();
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (MalformedURLException e) {
            } catch (IOException e2) {
                Log.e("Utils", "HTTP failed to fetch data");
                return null;
            }
            return builder.toString();//Utils.readStream(inputStream);
        }

        protected void onPostExecute(String string) {
            if (string != null && !string.equals("")) {
                ArrayList<GooglePlacesBean> placesList = new GooglePlacesParser(string).getPlaces();
                for (int i = 0; i < placesList.size(); i++) {
                    SearchItemBean bean = new SearchItemBean();
                    bean.setName(((GooglePlacesBean) placesList.get(i)).getDescription());
                    bean.setPlaceID(((GooglePlacesBean) placesList.get(i)).getPlaceId());
                    bean.setType("Google");
                    results.add(bean);
                }
                resultAdapter = new SearchResultAdapter(MainActivity.this, results);

//                ListView resultList = (ListView) findViewById(R.id.searchResult);
                listView.setAdapter(resultAdapter);
                resultAdapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        search = (SearchItemBean) results.get(i);
                        autoText.setText(search.getName());
                    }
                });
            }
        }
    }

}
