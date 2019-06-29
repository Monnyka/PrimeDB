package com.nyka.primedb;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    MovieListAdapter mMovieAdapter;
    ArrayList<MovieListItem> mMovieList;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        NoStatusBar();

        mRecyclerView = findViewById(R.id.recyclerMovieList);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMovieList = new ArrayList<>();
        mQueue = Volley.newRequestQueue(this);

        RequestList();

    }

    private void RequestList() {

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=1469231605651a4f67245e5257160b5f&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject Movie =jsonArray.getJSONObject(i);

                        String imageUrl="https://image.tmdb.org/t/p/w185"+Movie.optString("poster_path");
                        String MovieTitle=Movie.optString("title");
                        String MovieRate=Movie.optString("vote_average");
                        String ReleaseDate=Movie.optString("release_date");
                        String MovieReleaseDate=ConvertDate(ReleaseDate);
                        mMovieList.add(new MovieListItem(imageUrl, MovieTitle, MovieRate, MovieReleaseDate));
                    }
                    mMovieAdapter =new MovieListAdapter(SearchActivity.this,mMovieList);
                    mRecyclerView.setAdapter(mMovieAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    public String ConvertDate(String ReleaseDate){

        String aa = ReleaseDate;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(aa);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd MMM, yyyy");
        String newDateString = spf.format(newDate);

        String newDateString1 = newDateString;
        return newDateString1;
    }
    
}
