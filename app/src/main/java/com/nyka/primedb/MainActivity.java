package com.nyka.primedb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nyka.primedb.adapter.MovieListAdapter;
import com.nyka.primedb.adapter.OnAirTVAdapter;
import com.nyka.primedb.adapter.OnAirTodayAdapter;
import com.nyka.primedb.adapter.TrendingAdapter;
import com.nyka.primedb.adapter.UpcomingAdapter;
import com.nyka.primedb.adapter.YourWatchListAdapter;
import com.nyka.primedb.model.OnAirTVModel;
import com.nyka.primedb.model.YourWatchlistModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MovieListAdapter.OnItemClickListener, TrendingAdapter.onClickItemListener, OnAirTodayAdapter.OnItemClickListener, OnAirTVAdapter.OnItemClickListener, UpcomingAdapter.OnItemClickListener {
    RequestQueue mQueue;
    String popularAPIAddress = requestRoute + "/3/movie/now_playing" + api_key + "&language=en-US&page=1";
    String urlUpcoming = requestRoute + "/3/movie/upcoming" + api_key + "&language=en-US&page=1";
    String urlPopular = requestRoute + "/3/movie/popular" + api_key + "&language=en-US&page=1";
    RecyclerView tRecyclerView;
    Button btnLogIn;
    TextView lbProfileName;
    String userName = "";
    String urlRequestProfile = "";
    ImageView btnSearch;
    Button btnPopular;
    Button btnUpcoming;
    ImageView imProfile;

    RelativeLayout rlNoInternet;
    ScrollView svMainScreen;
    SpinKitView imSpin_kit;

    TextView title;
    BottomNavigationView bottomNav;


    //OnAirTV
    OnAirTVAdapter mOnAirTVAdapter;
    ArrayList<OnAirTVModel> mOnAirTVList;
    RecyclerView mRCOnAirTV;

    public static final String Extra_TVID = "tvID";
    public static String savesessionID = "sessionID";
    public String sessionID = "";
    public static final String SHARED_PREF = "Share_Pref";

    YourWatchListAdapter mYourWatchListAdapter;
    ArrayList<YourWatchlistModel> mYourWatchList;
    RecyclerView rv_Yourwatchlist;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoStatusBar();
        title=findViewById(R.id.title);
        rlNoInternet=findViewById(R.id.rlNoInternet);
        svMainScreen=findViewById(R.id.svMainScreen);
        imSpin_kit=findViewById(R.id.imSpin_kit);


        //LoadData();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        sessionID = sharedPreferences.getString(savesessionID, "");
        mQueue = Volley.newRequestQueue(this);
        btnLogIn = findViewById(R.id.btnLogIn);
        lbProfileName = findViewById(R.id.lbProfileName);
        tRecyclerView = findViewById(R.id.rvTrending);
        btnSearch = findViewById(R.id.btnSearch);
        btnPopular = findViewById(R.id.btnPopular);
        btnUpcoming = findViewById(R.id.btnUpcoming);
        imProfile = findViewById(R.id.imProfile);

        //Snapper Scroll Item
//        final SnapHelper snapHelper = new GravitySnapHelper(GravityCompat.START);
//        snapHelper.attachToRecyclerView(tRecyclerView);


        //OnAirTV
        mOnAirTVList = new ArrayList<>();
        mRCOnAirTV = findViewById(R.id.rcOnAirTV);
        mRCOnAirTV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btnPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPopularMovie();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSearch();
            }
        });
        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenUpcoming();
            }
        });
        imProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenProfileScreen();
            }
        });

        getProfile();
        RequestYourWatchList();
        RequestUpComingMovie();
        getOnAirToday();
        getOnAirTV();
        lbProfileName.setText(userName);


        //Bottom Navigate
        bottomNav=findViewById(R.id.bottomNavMain);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MovieFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;

            switch (menuItem.getItemId()){

                case R.id.nav_movie:
                    selectedFragment=new MovieFragment();
                    break;

                case R.id.nav_tv_series:
                    selectedFragment=new TVSeriesFragment();
                    break;

                case R.id.nav_you:
                    selectedFragment=new YouFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
            return true;
        }
    };

    public void OpenScreenMovieDetail(String passValue) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movieID", passValue);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//      ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,findViewById(R.id.), "imageViewPoster");
//      startActivity(intent, optionsCompat.toBundle());
    }
    public void OpenSearch() {
        String title = "What are you looking for?";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("api_link", popularAPIAddress);
        intent.putExtra("ScreenTitle", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void OpenUpcoming() {
        String title = "Upcoming Movie";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("api_link", urlUpcoming);
        intent.putExtra("ScreenTitle", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void OpenPopularMovie() {
        String title = "Now In Theater";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("api_link", urlPopular);
        intent.putExtra("ScreenTitle", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void OpenProfileScreen() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void RequestUpComingMovie() {

    }
    public void RequestYourWatchList() {

    }
    public void getProfile() {

        urlRequestProfile = "https://api.themoviedb.org/3/account?api_key=" + apiKey + "&session_id=" + sessionID;
        JsonObjectRequest request_Profile = new JsonObjectRequest(Request.Method.GET, urlRequestProfile, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                userName = response.optString("name");
                lbProfileName.setText(userName);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Request Fail: Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request_Profile);
    }
    public void getOnAirToday() {

    }
    public void getOnAirTV() {
        String url = "https://api.themoviedb.org/3/tv/on_the_air?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject results = jsonArray.getJSONObject(i);
                        String id = results.optString("id");
                        String title = results.optString("name");
                        String vote = results.optString("vote_average");
                        String image = "https://image.tmdb.org/t/p/w342" + results.optString("poster_path");

                        mOnAirTVList.add(new OnAirTVModel(image, title, vote, id));
                    }
                    mOnAirTVAdapter = new OnAirTVAdapter(MainActivity.this, mOnAirTVList);
                    mRCOnAirTV.setAdapter(mOnAirTVAdapter);
                    mOnAirTVAdapter.SetOnItemClickListener(MainActivity.this);

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
    public static void launch(Context context) {
//      Intent intent=new Intent(context,MainActivity.class);
//      context.startActivity(intent);
        context.startActivity(new Intent(context, MainActivity.class));
    }
    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(this, MovieDetailActivity.class);
//        trending clickItem = mTrendingList.get(position);
//        intent.putExtra("movieID", clickItem.getTrendingID());
//        startActivity(intent);
    }
    @Override
    public void OnItemClick(int position) {
//        Intent tvDetailIntent = new Intent(this, TVDetail.class);
//        OnAirTVModel clickItem = mOnAirTodayList.get(position);
//        tvDetailIntent.putExtra("tvID", clickItem.getId());
//        startActivity(tvDetailIntent);

    }
    @Override
    public void OnOnAirItemClick(int position) {
        Intent detailIntent=new Intent(this,TVDetail.class);
        OnAirTVModel clickItem=mOnAirTVList.get(position);
        detailIntent.putExtra("tvID",clickItem.getId());
        startActivity(detailIntent);

    }
    @Override
    public void OnUpcomingClickListener(int position) {
//        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
//        UpcomingModel clickItem=mUpComingList.get(position);
//        detailIntent.putExtra("movieID",clickItem.getMovieID());
//        startActivity(detailIntent);
    }
}














