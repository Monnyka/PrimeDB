package com.nyka.primedb;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {

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
    RequestQueue mQueue;
    String IdMovie="";
    String IdMovieUpcomingOne="";
    String IdMovieUpcomingTwo="";
    String IdMovieUpcomingThree="";


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


        //Open Detail Screen
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




        RequestMainScreen();
    }
    public void OpenScreenMovieDetail(String passValue){

        Intent intent = new Intent(this, activity_moviedetail.class);
        intent.putExtra("movieID",passValue);
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
}