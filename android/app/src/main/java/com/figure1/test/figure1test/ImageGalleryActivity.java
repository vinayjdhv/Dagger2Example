package com.figure1.test.figure1test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vinayjadhav on 2018-03-11.
 *
 * This activity displays a image list in recycle view
 */

public class ImageGalleryActivity extends AppCompatActivity {

    private ArrayList<ImageData> imageDataArrayList;
    private ImageListAdapter adapter;

    private LinearLayoutManager linearLayoutManager;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    /**
     * Following staring are used to build url to fetch gallery data
     *
     * API to get Random Image is deprecated, so using gallery data.
     */
    private String baseURL = "https://api.imgur.com/3/";
    private String urlGallery = "gallery/top/";
    private int pagingNumber = 1;
    private String getParam = "?showViral=true&mature=false&album_previews=false";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        imageDataArrayList = new ArrayList<>();
        setupData();
        setupRecycleView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * This function gets the data using Imgur APIs
     */
    public void setupData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = baseURL + urlGallery + pagingNumber + getParam;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                formatJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("LOG", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Client-ID e0d9382b96f900b");
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }


    /**
     * This finction will for mat JSON to extract image data from album data
     * @param response
     */

    private void formatJson(JSONObject response) {
        if (response != null) {
            try {
                // Getting JSON Array node
                JSONArray data = response.getJSONArray("data");

                // looping through All Contacts
                for (int i = 1; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);
                    JSONArray imagesJson;
                    try {
                        imagesJson = c.getJSONArray("images");
                    } catch (final JSONException e) {
                        Log.e("DATA PARSING", "Json parsing error: " + e.getMessage());
                        continue;
                    }

                    if (imagesJson == null) {
                        continue;
                    }

                    for (int j = 0; j < imagesJson.length(); j++) {
                        ImageData imageData = new ImageData();
                        JSONObject d = imagesJson.getJSONObject(j);
                        imageData.setId(d.getString("id"));
                        imageData.setTitle(d.getString("title"));
                        imageData.setDescription(d.getString("description"));
                        imageData.setLink(d.getString("link"));

                        imageDataArrayList.add(imageData);
                    }
                }
            } catch (final JSONException e) {
                Log.e("DATA PARSING", "Json parsing error: " + e.getMessage());
            }

            adapter.setImageDataArrayList(imageDataArrayList);
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * This will setup recycle view
     */

    public void setupRecycleView(){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    pagingNumber++;
                    setupData();
                    loading = true;
                }

            }
        });

        adapter = new ImageListAdapter(this, imageDataArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
}
