package com.nyka.primedb;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewUpcoming;
    private ImageView imageViewInTheater;

    private TextView inTheaterMovieLabel;
    private TextView inTheaterMovieDateLabel;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Nostatusbar();

        mQueue = Volley.newRequestQueue(this);
        imageViewUpcoming=(ImageView) findViewById(R.id.ivComing1);
        imageViewInTheater=(ImageView)findViewById(R.id.imInTheater);
        inTheaterMovieLabel=(TextView)findViewById(R.id.lbInTheaterMovieTitle);
        inTheaterMovieDateLabel=(TextView)findViewById(R.id.lbMovieInTheaterDate);

        imageViewUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDetailActivity();
                finish();
            }
        });
        imageViewInTheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDetailActivity();
            }
        });

        LoadInTheaterData();


    }
    public void OpenDetailActivity(){
        Intent intent = new Intent(this, activity_moviedetail.class);
        startActivity(intent);
    }

    public void LoadInTheaterData(){

            String Url="https://api.themoviedb.org/3/movie/now_playing?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray =response.getJSONArray("results");

                    for(int i=0; i<1;i++){
                        JSONObject result =jsonArray.getJSONObject(i);

                        String movieTitle = result.getString("title");
                        String releaseDate = result.getString("release_date");
                        String poster =result.getString("poster_path");
                        inTheaterMovieLabel.setText(movieTitle);
                        inTheaterMovieDateLabel.setText(releaseDate);
                        String MovieImageAddress="http://image.tmdb.org/t/p/w185/"+poster;

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
        mQueue.add(request);
    }

    public void Nostatusbar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}
