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

    private ImageView imageViewUpcoming;
    private ImageView imageViewUpcomingTwo;
    private ImageView imageViewUpcomingThree;
    private ImageView imageViewInTheater;
    private TextView inTheaterMovieLabel;
    private TextView inTheaterMovieDateLabel;
    private RequestQueue mQueue;
    private TextView lbUpcomingMovieTitleOne;
    private TextView lbUpcomingMovieTitleTwo;
    private TextView lbUpcomingMovieTitleThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call Base for no displayed status bar
        NoStatusBar();

        mQueue = Volley.newRequestQueue(this);
        imageViewUpcoming=(ImageView) findViewById(R.id.ivComing1);
        imageViewUpcomingTwo=(ImageView) findViewById(R.id.ivUpComing2);
        imageViewUpcomingThree=(ImageView) findViewById(R.id.ivUpComing3);


        imageViewInTheater=(ImageView)findViewById(R.id.imInTheater);
        lbUpcomingMovieTitleOne=(TextView)findViewById(R.id.lbUpComingMovieOne);
        lbUpcomingMovieTitleTwo=(TextView)findViewById(R.id.lbUpComingMovieTwo);
        lbUpcomingMovieTitleThree=(TextView)findViewById(R.id.lbUpComingMovieThree);
        inTheaterMovieLabel=(TextView)findViewById(R.id.lbInTheaterMovieTitle);
        inTheaterMovieDateLabel=(TextView)findViewById(R.id.lbMovieInTheaterDate);

        imageViewUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDetailActivity();
            }
        });
        imageViewInTheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDetailActivity();
            }
        });
        RequestMainScreen();
    }



    public void OpenDetailActivity(){
        Intent intent = new Intent(this, activity_moviedetail.class);
        startActivity(intent);
    }
    public void RequestMainScreen(){

            String Url="https://api.themoviedb.org/3/movie/now_playing?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
            String Url1="https://api.themoviedb.org/3/movie/upcoming?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";

            //Request In Theater Movie
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =response.getJSONArray("results");

                    for(int i=0; i<1;i++){
                        JSONObject result =jsonArray.getJSONObject(i);

                        String movieTitle = result.getString("title");
                        String releaseDate = result.getString("release_date");
                        String poster=result.getString("poster_path");
                        String Address="https://image.tmdb.org/t/p/original"+poster;
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
                        String MovieTitle =result.getString("title");
                        String poster= result.getString("poster_path");
                        String poster1 ="https://image.tmdb.org/t/p/original"+poster;
                        lbUpcomingMovieTitleOne.setText(MovieTitle);
                        Glide.with(MainActivity.this).load(poster1).into(imageViewUpcoming);

                    }

                    for (int j=0;j<2; j++){
                        JSONObject result2 =jsonArray.getJSONObject(j);
                        String MovieTitle =result2.getString("title");
                        String poster= result2.getString("poster_path");
                        String poster1 ="https://image.tmdb.org/t/p/original"+poster;
                        lbUpcomingMovieTitleTwo.setText(MovieTitle);
                        Glide.with(MainActivity.this).load(poster1).into(imageViewUpcomingTwo);
                    }

                    for (int k=0;k<3; k++){
                        JSONObject result =jsonArray.getJSONObject(k);
                        String MovieTitle =result.getString("title");
                        String poster= result.getString("poster_path");
                        String poster1 ="https://image.tmdb.org/t/p/original"+poster;
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

            }
        });


        mQueue.add(request);
        mQueue.add(request1);
    }

}
