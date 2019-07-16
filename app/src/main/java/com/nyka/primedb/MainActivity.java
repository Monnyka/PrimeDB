package com.nyka.primedb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MovieListAdapter.OnItemClickListener,TrendingAdapter.onClickItemListener {
    ImageView imageViewUpcoming;
    ImageView imageViewUpcomingTwo;
    ImageView imageViewUpcomingThree;
    ImageView imageViewInTheater;
    TextView inTheaterMovieLabel;
    TextView inTheaterMovieDateLabel;
    TextView lbUpcomingMovieTitleOne;
    TextView lbUpcomingMovieTitleTwo;
    TextView lbUpcomingMovieTitleThree;
    TextView txtPopularLastYear;
    TextView txtPopularLastYearRated;
    ImageView ivPopularLastYear;
    TextView lbMovieMostPopular;
    TextView lbMovieMostPopularReleaseDate;
    ImageView ivMostPopular;
    ImageView btnBrowse;
    Button btnExplore;
    RelativeLayout rlInTheater;
    RequestQueue mQueue;
    String IdMovie="";
    String IdMovieUpcomingOne="";
    String IdMovieUpcomingTwo="";
    String IdMovieUpcomingThree="";
    String popularAPIAddress="https://api.themoviedb.org/3/movie/now_playing?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
    RecyclerView tRecyclerView;
    TextView lbPopularOne;
    Button btnLogIn;
    String respondToken ="";
    String verifiedToken="";
    String sessionID="";
    TextView lbProfileName;
    String userName="";
    String urlRequestProfile="";

    TrendingAdapter mTrendingAdapter;
    ArrayList<trending> mTrendingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call Base for no displayed status bar
        NoStatusBar();

        mQueue = Volley.newRequestQueue(this);
        imageViewUpcoming=findViewById(R.id.ivComing1);
        imageViewUpcomingTwo=findViewById(R.id.ivUpComing2);
        imageViewUpcomingThree=findViewById(R.id.ivUpComing3);
        txtPopularLastYear=findViewById(R.id.lbPopularLastYear);
        txtPopularLastYearRated=findViewById(R.id.lbPopularLastYearRated);
        ivPopularLastYear=findViewById(R.id.ivPopularLastYear);
        imageViewInTheater=findViewById(R.id.imInTheater);
        lbUpcomingMovieTitleOne=findViewById(R.id.lbUpComingMovieOne);
        lbUpcomingMovieTitleTwo=findViewById(R.id.lbUpComingMovieTwo);
        lbUpcomingMovieTitleThree=findViewById(R.id.lbUpComingMovieThree);
        inTheaterMovieLabel=findViewById(R.id.lbInTheaterMovieTitle);
        inTheaterMovieDateLabel=findViewById(R.id.lbMovieInTheaterDate);
        lbMovieMostPopular=findViewById(R.id.lbMovieMostPopular);
        lbMovieMostPopularReleaseDate=findViewById(R.id.lbMovieMostPopularReleaseDate);
        ivMostPopular=findViewById(R.id.ivMostPopular);
        lbPopularOne=findViewById(R.id.lbPopularOne);
        btnBrowse=findViewById(R.id.imBrowse);
        btnExplore=findViewById(R.id.btnExplore);
        rlInTheater=findViewById(R.id.rlInTheater);
        btnLogIn=findViewById(R.id.btnLogIn);
        lbProfileName=findViewById(R.id.lbProfileName);
        tRecyclerView=findViewById(R.id.rvTrending);

        tRecyclerView.hasFixedSize();
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tRecyclerView.setLayoutManager (linearLayoutManager);

        //Snapper Scroll Item
        final SnapHelper snapHelper = new GravitySnapHelper(GravityCompat.START);
        snapHelper.attachToRecyclerView(tRecyclerView);

        mTrendingList = new ArrayList<>();
        //Open Detail Screen
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSearch();
            }
        });

        imageViewUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenScreenMovieDetail(IdMovieUpcomingOne);
            }
        });
        imageViewUpcomingTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenScreenMovieDetail(IdMovieUpcomingTwo);
            }
        });

        imageViewUpcomingThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenScreenMovieDetail(IdMovieUpcomingThree);
            }
        });

        imageViewInTheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenScreenMovieDetail(IdMovie);
            }
        });

        rlInTheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPopularMovie();
            }
        });
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSearch();
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getProfile();
        RequestTrending();
        RequestMainScreen();
    }
    public void OpenScreenMovieDetail(String passValue){

        Intent intent = new Intent(this, activity_moviedetail.class);
        intent.putExtra("movieID",passValue);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,findViewById(R.id.ivComing1), "imageViewPoster");
        startActivity(intent, optionsCompat.toBundle());
    }
    public void OpenSearch(){
        String title="What are you  looking for?";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("apiPopular",popularAPIAddress);
        intent.putExtra("ScreenTitle",title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void OpenPopularMovie(){
        String title="IN THEATER NOW";
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("apiPopular",popularAPIAddress);
        intent.putExtra("ScreenTitle",title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
    public void RequestMainScreen(){

            String Url="https://api.themoviedb.org/3/movie/now_playing?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
            String Url1="https://api.themoviedb.org/3/movie/upcoming?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
            String RequestPopularLastYear="https://api.themoviedb.org/3/discover/movie?api_key=1469231605651a4f67245e5257160b5f&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&year=2018&with_original_language=en";
            String RequestPopularThisYear="https://api.themoviedb.org/3/discover/movie?api_key=1469231605651a4f67245e5257160b5f&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&vote_average.gte=8&year=2019&with_original_language=en";

            //Request In Theater Movie
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =response.getJSONArray("results");

                    for(int i=0; i<1;i++){
                        JSONObject result =jsonArray.getJSONObject(i);

                        String movieID=result.getString("id");
                        String movieTitle = result.getString("title");
                        String releaseDate = result.getString("release_date");
                        String poster=result.getString("poster_path");
                        String Address="https://image.tmdb.org/t/p/original"+poster;
                        IdMovie=movieID;
                        inTheaterMovieLabel.setText(movieTitle);
                        inTheaterMovieDateLabel.setText(releaseDate);
                        Glide.with(MainActivity.this).load(Address).into(imageViewInTheater);
                    }

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

            //Request Upcoming Movie
        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, Url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =response.getJSONArray("results");

                    for (int i = 0; i<1; i++) {
                        JSONObject result =jsonArray.getJSONObject(i);
                        String MovieID = result.getString("id");
                        String MovieTitle =result.getString("title");
                        String poster= result.getString("poster_path");
                        String poster1 ="https://image.tmdb.org/t/p/original"+poster;
                        IdMovieUpcomingOne=MovieID;
                        lbUpcomingMovieTitleOne.setText(MovieTitle);
                        Glide.with(MainActivity.this).load(poster1).into(imageViewUpcoming);

                    }

                    for (int j=0;j<2; j++){
                        JSONObject result2 =jsonArray.getJSONObject(j);
                        String MovieID = result2.getString("id");
                        String MovieTitle =result2.getString("title");
                        String poster= result2.getString("poster_path");
                        String poster1 ="https://image.tmdb.org/t/p/original"+poster;
                        IdMovieUpcomingTwo=MovieID;
                        lbUpcomingMovieTitleTwo.setText(MovieTitle);
                        Glide.with(MainActivity.this).load(poster1).into(imageViewUpcomingTwo);
                    }

                    for (int k=0;k<3; k++){
                        JSONObject result =jsonArray.getJSONObject(k);
                        String MovieID = result.getString("id");
                        String MovieTitle =result.getString("title");
                        String poster= result.getString("poster_path");
                        String poster1 ="https://image.tmdb.org/t/p/original"+poster;
                        IdMovieUpcomingThree=MovieID;
                        lbUpcomingMovieTitleThree.setText(MovieTitle);
                        Glide.with(MainActivity.this).load(poster1).into(imageViewUpcomingThree);
                    }


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        JsonObjectRequest requestPopularLastYear = new JsonObjectRequest(Request.Method.GET, RequestPopularLastYear, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray jsonArray =response.getJSONArray("results");

                    for(int l=0;l<1;l++){
                        JSONObject result =jsonArray.getJSONObject(l);
                        String MovieTitle = result.getString("title");
                        String MovieRated= result.getString("vote_average");
                        String poster= result.getString("poster_path");
                        String poster1 ="https://image.tmdb.org/t/p/original"+poster;
                        Glide.with(MainActivity.this).load(poster1).into(ivPopularLastYear);
                        txtPopularLastYear.setText(MovieTitle);
                        txtPopularLastYearRated.setText(MovieRated);
                    }
            }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonObjectRequest requestPopularThisYear = new JsonObjectRequest(Request.Method.GET, RequestPopularThisYear, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray jsonArray =response.getJSONArray("results");

                    for(int l=0;l<1;l++){
                        JSONObject result =jsonArray.getJSONObject(l);
                        String MovieTitle = result.getString("title");
                        String MovieReleaseDate= result.getString("release_date");
                        String poster= result.getString("poster_path");
                        String poster1 ="https://image.tmdb.org/t/p/original"+poster;
                        Glide.with(MainActivity.this).load(poster1).into(ivMostPopular);
                        lbMovieMostPopular.setText(MovieTitle);
                        lbMovieMostPopularReleaseDate.setText(MovieReleaseDate);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(request);
        mQueue.add(request1);
        mQueue.add(requestPopularLastYear);
        mQueue.add(requestPopularThisYear);
    }
    public void RequestTrending(){
        String Url="https://api.themoviedb.org/3/movie/now_playing?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =response.getJSONArray("results");

                    for(int i=0; i<=5;i++){
                        JSONObject result =jsonArray.getJSONObject(i);
                        String movieID =result.getString("id");
                        String movieTitle = result.getString("title");
                        String releaseDate = result.getString("release_date");
                        String poster=result.getString("poster_path");
                        String Address="https://image.tmdb.org/t/p/original"+poster;
                        mTrendingList.add(new trending(movieTitle,Address,releaseDate,movieID));
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
    public void RequestAuth(){
        String request_token="https://api.themoviedb.org/3/authentication/token/new?api_key="+apiKey;
        String urlRequest_LogIn=requestRoute+"/3/authentication/token/validate_with_login?api_key="+apiKey;
        String urlCreateSession=requestRoute+"/3/authentication/session/new?api_key="+apiKey;

        JsonObjectRequest request_Token = new JsonObjectRequest(Request.Method.GET, request_token, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    respondToken =response.getString("request_token");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Request Fail"+error,Toast.LENGTH_LONG).show();
            }
        });

        JSONObject json = new JSONObject();
        try {
            json.put("username","nyka");
            json.put("password","nyka27777727");
            json.put("request_token", respondToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request_LonIn = new JsonObjectRequest(Request.Method.POST, urlRequest_LogIn, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                verifiedToken=response.optString("request_token");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Fail"+error,Toast.LENGTH_LONG).show();
            }
        });

        JSONObject jsonToken = new JSONObject();
        try {
            jsonToken.put("request_token",verifiedToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest create_sessionID = new JsonObjectRequest(Request.Method.POST, urlCreateSession, jsonToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    sessionID=response.getString("session_id");
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Toast.makeText(getApplicationContext(),"Request Session Fail : "+error,Toast.LENGTH_LONG).show();
            }
        });

        mQueue.add(request_Token);
        mQueue.add(request_LonIn);
        mQueue.add(create_sessionID);
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
//        Intent intent=new Intent(context,MainActivity.class);
//        context.startActivity(intent);
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