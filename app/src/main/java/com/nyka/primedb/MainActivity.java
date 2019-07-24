package com.nyka.primedb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MovieListAdapter.OnItemClickListener,TrendingAdapter.onClickItemListener {
    RequestQueue mQueue;
    String popularAPIAddress=requestRoute+"/3/movie/now_playing"+api_key+"&language=en-US&page=1";
    String urlUpcoming=requestRoute+"/3/movie/upcoming"+api_key+"&language=en-US&page=1";
    String urlPopular=requestRoute+"/3/movie/popular"+api_key+"&language=en-US&page=1";
    RecyclerView tRecyclerView;
    Button btnLogIn;
    TextView lbProfileName;
    String userName="";
    String urlRequestProfile="";
    ImageView btnSearch;
    Button btnPopular;
    Button btnUpcoming;

    TrendingAdapter mTrendingAdapter;
    ArrayList<trending> mTrendingList;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoStatusBar();

        mQueue = Volley.newRequestQueue(this);
        btnLogIn=findViewById(R.id.btnLogIn);
        lbProfileName=findViewById(R.id.lbProfileName);
        tRecyclerView=findViewById(R.id.rvTrending);
        btnSearch=findViewById(R.id.btnSearch);
        btnPopular=findViewById(R.id.btnPopular);
        btnUpcoming=findViewById(R.id.btnUpcoming);

        tRecyclerView.hasFixedSize();
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tRecyclerView.setLayoutManager (linearLayoutManager);

        //Snapper Scroll Item
        final SnapHelper snapHelper = new GravitySnapHelper(GravityCompat.START);
        snapHelper.attachToRecyclerView(tRecyclerView);
        mTrendingList = new ArrayList<>();


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
        getProfile();
        RequestTrending();
    }
    public void OpenScreenMovieDetail(String passValue){

        Intent intent = new Intent(this, activity_moviedetail.class);
        intent.putExtra("movieID",passValue);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,findViewById(R.id.), "imageViewPoster");
//        startActivity(intent, optionsCompat.toBundle());
    }
    public void OpenSearch(){
        String title="What are you looking for?";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("api_link",popularAPIAddress);
        intent.putExtra("ScreenTitle",title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void OpenUpcoming(){
        String title="Upcoming Movie";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("api_link",urlUpcoming);
        intent.putExtra("ScreenTitle",title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void OpenPopularMovie(){
        String title="Now In Theater";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("api_link",urlPopular);
        intent.putExtra("ScreenTitle",title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
    public void RequestTrending(){
        String Url="https://api.themoviedb.org/3/movie/now_playing?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =response.getJSONArray("results");

                    for(int i=0; i<=10;i++){
                        JSONObject result =jsonArray.getJSONObject(i);
                        String movieID =result.getString("id");
                        String movieTitle = result.getString("title");
                        String releaseDate = result.getString("release_date");
                        String poster=result.getString("poster_path");
                        String Address="https://image.tmdb.org/t/p/original"+poster;
                        String genre=result.optString("genre");
                        mTrendingList.add(new trending(movieTitle,Address,releaseDate,movieID,genre));
                    }
                    mTrendingAdapter=new TrendingAdapter(MainActivity.this,mTrendingList);
                    tRecyclerView.setAdapter(mTrendingAdapter);
                    mTrendingAdapter.setOnClickListener(MainActivity.this);

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
    public void getProfile(){

        urlRequestProfile="https://api.themoviedb.org/3/account?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
        JsonObjectRequest request_Profile = new JsonObjectRequest(Request.Method.GET, urlRequestProfile, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                userName=response.optString("username");
                lbProfileName.setText(userName);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Fail: Please check your internet connection",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request_Profile);
    }

    public static void launch(Context context) {
//      Intent intent=new Intent(context,MainActivity.class);
//      context.startActivity(intent);
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent =new Intent(this,activity_moviedetail.class);
        trending clickItem =mTrendingList.get(position);
        intent.putExtra("movieID",clickItem.getTrendingID());
        startActivity(intent);
    }

}