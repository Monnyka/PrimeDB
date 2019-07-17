package com.nyka.primedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

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

public class SearchActivity extends BaseActivity implements MovieListAdapter.OnItemClickListener,TrendingAdapter.onClickItemListener {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_TITLE = "movieTitle";
    public static final String EXTRA_RATE = "imageRate";
    public static final String EXTRA_RELEASE = "imageRelease";

    RecyclerView mRecyclerView;
    MovieListAdapter mMovieAdapter;
    ArrayList<MovieListItem> mMovieList;
    RequestQueue mQueue;
    String popularApi="";
    TextView ScreenTitle;
    EditText etSearch;
    String screenTitle="";
    RelativeLayout llSearch;
    String query="";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        NoStatusBar();



        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        ScreenTitle=findViewById(R.id.ScreenTitle);
        etSearch=findViewById(R.id.etSearch);
        mRecyclerView = findViewById(R.id.recyclerMovieList);
        llSearch=findViewById(R.id.llSearch);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMovieList = new ArrayList<>();
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        popularApi=intent.getStringExtra("apiPopular");
        screenTitle=intent.getStringExtra("ScreenTitle");
        ScreenTitle.setText(screenTitle);
            if(ScreenTitle.getText().equals("IN THEATER NOW")){
                llSearch.setVisibility(View.GONE);
            }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                query=etSearch.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                query=etSearch.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                query=etSearch.getText().toString();
                mMovieAdapter.clear();
                RequestSearch();
            }
        });

        RequestList();
    }

    private void RequestList() {

        String url = popularApi;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Movie = jsonArray.getJSONObject(i);
                        String imageUrl = "https://image.tmdb.org/t/p/w185" + Movie.optString("poster_path");
                        String MovieID = Movie.optString("id");
                        String MovieTitle = Movie.optString("title");
                        String MovieRate = Movie.optString("vote_average");
                        String ReleaseDate = Movie.optString("release_date");
                        String MovieReleaseDate = ConvertDate(ReleaseDate);
                        mMovieList.add(new MovieListItem(imageUrl, MovieTitle, MovieRate, MovieReleaseDate, MovieID));
                    }

                    mMovieAdapter = new MovieListAdapter(SearchActivity.this, mMovieList);
                    mRecyclerView.setAdapter(mMovieAdapter);
                    mMovieAdapter.setOnClickListener(SearchActivity.this);

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

    public String ConvertDate(String ReleaseDate) {
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

    public void OpenMainScreen() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void RequestSearch(){
        String urll="https://api.themoviedb.org/3/search/movie?api_key=1469231605651a4f67245e5257160b5f&query="+query;
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, urll, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Movie = jsonArray.getJSONObject(i);
                        String imageUrl = "https://image.tmdb.org/t/p/w185" + Movie.optString("poster_path");
                        String MovieID = Movie.optString("id");
                        String MovieTitle = Movie.optString("title");
                        String MovieRate = Movie.optString("vote_average");
                        String ReleaseDate =Movie.optString("release_date");
                        if(ReleaseDate!=null&& !ReleaseDate.equals("")) {
                            String MovieReleaseDate = ConvertDate(ReleaseDate);
                            mMovieList.add(new MovieListItem(imageUrl, MovieTitle, MovieRate, MovieReleaseDate, MovieID));
                        }

                    }
                    mMovieAdapter = new MovieListAdapter(SearchActivity.this, mMovieList);
                    mRecyclerView.setAdapter(mMovieAdapter);
                    mMovieAdapter.setOnClickListener(SearchActivity.this);

                     } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, activity_moviedetail.class);
        MovieListItem clickedItem = mMovieList.get(position);
        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getMovieTitle());
        detailIntent.putExtra(EXTRA_RATE, clickedItem.getMovieRate());
        detailIntent.putExtra(EXTRA_RELEASE, clickedItem.getMovieReleaseDate());
        detailIntent.putExtra("movieID", clickedItem.getMovieID());
        startActivity(detailIntent);
    }

}
